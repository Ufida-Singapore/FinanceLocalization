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
	 * �û�Ȩ����Դ�ж�
	 */
	@Override
	protected boolean isUserhasPermission(AggregatedValueObject... bills) throws BusinessException {
		return true;
	}

	@Override
	protected void doBeforeInsert(AggregatedValueObject[] bills) throws BusinessException {
		ArapBillPubUtil.fillTradeTypeInfo(bills);
		 
		// ���ݶ��˶��˱�ʶ��
		BillBankUtils.fillSettleBankrelated_code(bills);
		// �������ڡ���������
		BillDateUtils.setBillDateByNow(bills);
		// ���õ��ݵĻ���ڼ䡢�����
		BillAccountCalendarUtils.setAccperiodYearMonth(bills);
		// ����Ĭ����Ϣ����汾��ŷ������
		ArapBillVOUtils.prepareDefaultInfo(bills);
		//��ֵ�������ͷ
		BillMoneyVUtils.sumAllVoBodyToHead(bills);
		// У�齻��У�����
		new CrossRuleCheckValidator().validate(bills);
		
		ArapBillPubUtil.resetDestVODoc(bills);
		ArapBillVOUtils.setMaterialInfo(bills);
		
		//add chenth 20161212 ֧�������Ѻ���  ����Ӧ�������ɵĸ�����ֹ�����������ʱ����Դͷid����Ϊ����һ��
		dealBankCharge(bills);
		//add end 
		
	}
	
	
	/**
	 * add chenth 20161212 ֧�������Ѱ��ۿۺ���  
	 *   <p>����Ӧ�������ɵĸ�����ֹ�������������ʱ��������Դͷ��Ϣ
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
					//add chenth ����ǡ������ѡ�
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
		// ��������������VO��״̬ modified by liaobx 2010-8-19 20:00:00 ���Ӷ��ո�Э���֧��
		childvostatmap = BillStatusUtils.enNewVOStauts(bills);
		beforeInserDefVal(bills);
		// ��������״̬���ո���Э��
		return super.doInsert(bills);
	}

	protected void beforeInserDefVal(AggregatedValueObject[] bills) {
		// ��������״̬Ĭ��ֵ
		BillStatusUtils.enCUDVODefVals(bills);
	}

	@Override
	protected void doAfterInsert(AggregatedValueObject[] bills) throws BusinessException {

		// ��ȡ�ո���Э��TermVO��map
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
