package nc.bs.arap.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import nc.bs.arap.bill.ArapBillPubUtil;
import nc.bs.arap.billact.IValidatorFactory;
import nc.bs.arap.billact.InsertBatchBSAction;
import nc.bs.arap.util.ArapBillVOUtils;
import nc.bs.arap.util.BillAccountCalendarUtils;
import nc.bs.arap.util.BillBankUtils;
import nc.bs.arap.util.BillDateUtils;
import nc.bs.arap.util.BillMoneyVUtils;
import nc.bs.arap.util.BillStatusUtils;
import nc.bs.arap.util.BillTermUtils;
import nc.bs.arap.util.SqlUtils;
import nc.bs.arap.validator.CrossRuleCheckValidator;
import nc.bs.arap.validator.IValidatorCode;
import nc.bs.arap.validator.ValidatorFactory;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.vo.fibd.RecpaytypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

public abstract class BillInsertBatchBSAction extends InsertBatchBSAction {

	protected Map<String, Integer> childvostatmap = null;

	{
		validatorCode.add(IValidatorCode.BankAccountValidator);
		validatorCode.add(IValidatorCode.TradeTypeValidator);
	}

	public BillInsertBatchBSAction() {
	}

	/**
	 * 用户权限资源判断
	 */
	@Override
	protected boolean isUserhasPermission(AggregatedValueObject... bills) throws BusinessException {
		return true;
	}

	@Override
	protected void doBeforeInsert(AggregatedValueObject[] bills) throws BusinessException {
		ArapBillPubUtil.fillTradeTypeInfo(bills);
		 
		// 单据对账对账标识码
		BillBankUtils.fillSettleBankrelated_code(bills);
		// 单据日期、起算日期
		BillDateUtils.setBillDateByNow(bills);
		// 设置单据的会计期间、会计年
		BillAccountCalendarUtils.setAccperiodYearMonth(bills);
		// 补充默认信息，多版本，欧盟数据
		ArapBillVOUtils.prepareDefaultInfo(bills);
		//设值表体金额到表头
		BillMoneyVUtils.sumAllVoBodyToHead(bills);
		// 校验交叉校验规则
		new CrossRuleCheckValidator().validate(bills);
		
		ArapBillPubUtil.resetDestVODoc(bills);
		ArapBillVOUtils.setMaterialInfo(bills);
		
		//add chenth 20161212 支持手续费核销  参照应付单生成的付款单，手工增加手续费时，把源头id设置为其中一个
		dealBankCharge(bills);
		//add end 
		
	}
	
	
	/**
	 * add chenth 20161212 支持手续费按折扣核销  
	 *   <p>参照应付单生成的付款单，手工增加手续费行时，设置其源头信息
	 * @param bills
	 * @throws BusinessException 
	 */
	private void dealBankCharge(AggregatedValueObject[] bills) throws BusinessException {
		if(ArrayUtils.isEmpty(bills)){
			return ;
		}
		List<String> recpaytypePks =new ArrayList<String>();
		Map<String,RecpaytypeVO> recpaytypeMap =new HashMap<String,RecpaytypeVO>();
		String top_itemid = null;
		String top_billid = null;
		String top_billtype = null;
		String top_tradetype = null;
		String pk_recpaytype = null;
		String settlecurr = null;
		String def1 = null;
		for(AggregatedValueObject vo: bills){
			for(CircularlyAccessibleValueObject cvo:vo.getChildrenVO()){
				pk_recpaytype = (String)cvo.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE);
				if(pk_recpaytype != null){
					recpaytypePks.add(pk_recpaytype);
				}
				if(top_itemid == null){
					top_billid = (String)cvo.getAttributeValue(IBillFieldGet.TOP_BILLID);
					top_itemid = (String)cvo.getAttributeValue(IBillFieldGet.TOP_ITEMID);
					top_billtype = (String)cvo.getAttributeValue(IBillFieldGet.TOP_BILLTYPE);
					top_tradetype = (String)cvo.getAttributeValue(IBillFieldGet.TOP_TRADETYPE);
					settlecurr = (String)cvo.getAttributeValue(IBillFieldGet.SETTLECURR);
					def1 = (String)cvo.getAttributeValue("def1");
				}	
			}
		}
		if(recpaytypePks.size() > 0){
			Collection<RecpaytypeVO> typevos = new BaseDAO().retrieveByClause(RecpaytypeVO.class, SqlUtils.getInStr("pk_recpaytype", recpaytypePks.toArray(new String[]{})), new String[]{RecpaytypeVO.PK_RECPAYTYPE, RecpaytypeVO.ISBANKCHARGES});
			for(RecpaytypeVO typevo : typevos){
				recpaytypeMap.put(typevo.getPk_recpaytype(), typevo);
			}
			for(AggregatedValueObject vo:bills){
				SuperVO[] items=(SuperVO[]) vo.getChildrenVO();
				for(SuperVO item:items){
					//add chenth 如果是“手续费”
					pk_recpaytype = (String) item.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE);
					UFBoolean isbankcharge = recpaytypeMap.get(pk_recpaytype).getIsbankcharges();
					if(UFBoolean.TRUE.equals(isbankcharge)){
						item.setAttributeValue(IBillFieldGet.TOP_BILLID, top_billid);
						item.setAttributeValue(IBillFieldGet.TOP_ITEMID, top_itemid);
						item.setAttributeValue(IBillFieldGet.TOP_BILLTYPE, top_billtype);
						item.setAttributeValue(IBillFieldGet.TOP_TRADETYPE, top_tradetype);
						item.setAttributeValue(IBillFieldGet.SETTLEMONEY, UFDouble.ZERO_DBL);
						item.setAttributeValue(IBillFieldGet.SETTLECURR, settlecurr);
						item.setAttributeValue("def1", def1);
						item.setAttributeValue("def30", isbankcharge);
					}
				}
			}
		}
	}

	@Override
	protected AggregatedValueObject[] doInsert(AggregatedValueObject[] bills) throws BusinessException {
		// 设置新增操作的VO的状态 modified by liaobx 2010-8-19 20:00:00 增加对收付协议的支持
		childvostatmap = BillStatusUtils.enNewVOStauts(bills);
		beforeInserDefVal(bills);
		// 缓存新政状态的收付款协议
		return super.doInsert(bills);
	}

	protected void beforeInserDefVal(AggregatedValueObject[] bills) {
		// 设置新增状态默认值
		BillStatusUtils.enCUDVODefVals(bills);
	}

	@Override
	protected void doAfterInsert(AggregatedValueObject[] bills) throws BusinessException {

		// 获取收付款协议TermVO的map
		BillTermUtils.dealBillTerm(bills, childvostatmap, null);
		BillTermUtils.retBodyTermVOS(bills);

	}

	@Override
	protected IValidatorFactory getValidatorFactory() {
		ValidatorFactory vf = new ValidatorFactory();
		return vf;
	}

	@Override
	protected void setBillCode(AggregatedValueObject[] bills) throws BusinessException {
		ArapBillPubUtil.getBillCodeUtil(bills).createBillCode(bills);
	}
	
}
