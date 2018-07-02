package nc.bs.arap.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nc.bs.arap.bill.ArapBillPubUtil;
import nc.bs.arap.billact.BillBaseBSAction;
import nc.bs.arap.billact.IValidatorFactory;
import nc.bs.arap.billact.UpdateBatchBSAction;
import nc.bs.arap.busireg.ArapBillUpdateChecker;
import nc.bs.arap.busireg.BillUpdateChecker;
import nc.bs.arap.util.ArapBillVOUtils;
import nc.bs.arap.util.ArapBusiLogUtils;
import nc.bs.arap.util.BillAccountCalendarUtils;
import nc.bs.arap.util.BillBankUtils;
import nc.bs.arap.util.BillStatusUtils;
import nc.bs.arap.util.BillTermUtils;
import nc.bs.arap.validator.CrossRuleCheckValidator;
import nc.bs.arap.validator.IValidatorCode;
import nc.bs.arap.validator.ValidatorFactory;
import nc.bs.businessevent.BdUpdateEvent;
import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.EventDispatcher;
import nc.bs.businessevent.IEventType;
import nc.bs.dao.BaseDAO;
import nc.impl.pubapp.env.BSContext;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.pubitf.para.SysInitQuery;
import nc.util.fi.pub.SqlUtils;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.pub.ArapConstant;
import nc.vo.arap.pub.BillEnumCollection.BillSatus;
import nc.vo.arap.pub.BillEnumCollection.FromSystem;
import nc.vo.arap.sysinit.SysinitConst;
import nc.vo.arap.utils.StringUtil;
import nc.vo.fibd.RecpaytypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

import org.apache.commons.lang.ArrayUtils;

public abstract class BillUpdateBatchBSAction extends UpdateBatchBSAction {
	
	{
		validatorCode.add(IValidatorCode.CurrencyTypeValidator);
		validatorCode.add(IValidatorCode.BillStatusEditValidator);
		validatorCode.add(IValidatorCode.OriAmountNotNullValidator);
		validatorCode.add(IValidatorCode.CooperateBillAffirmValidator);
		validatorCode.add(IValidatorCode.BankAccountValidator);
		validatorCode.add(IValidatorCode.ObjectTypeValidator);
		validatorCode.add(IValidatorCode.MaxValueValidator);
	}

	public BillUpdateBatchBSAction() {
	}

	@Override
	protected IValidatorFactory getValidatorFactory() {
		ValidatorFactory vf = new ValidatorFactory();
		return vf;
	}

	@Override
	protected void doAfterFireEvent(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {

		int updateType = getUpdateType(bills, orginBills);

		if (updateType == TEMP_2_TEMP) {
		} else if (updateType == TEMP_2_SAVE) {
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_INSERT_AFTER, bills));
		} else if (updateType == SAVE_2_TEMP) {
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_DELETE_AFTER, orginBills));
		} else if (updateType == SAVE_2_SAVE) {
			afterEventSave2Save(bills, orginBills);
		}
	}

	/**
	 * 给期初单据重写
	 */
	protected void afterEventSave2Save(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {
		EventDispatcher.fireEvent(new BdUpdateEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_UPDATE_AFTER, orginBills, bills));
	}

	@Override
	protected void doBeforeFireEvent(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {

		int updateType = getUpdateType(bills, orginBills);

		// 预算控制，将单据的预算控制信息写到session
		BSContext.getInstance().setSession(ArapConstant.ARAP_TBB_ACTIONNAME, ArapConstant.ARAP_NTB_EDIT_KEY);
		if (bills != null && bills.length > 0 && ((BaseAggVO) bills[0]).isAlarmPassed()) {
			BSContext.getInstance().setSession(ArapConstant.ARAP_TBB_FLAG, ArapConstant.ARAP_TBB_NOCHECK);
		}

		if (updateType == TEMP_2_TEMP) {
		} else if (updateType == TEMP_2_SAVE) {
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_DELETE_BEFORE, orginBills));
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_DELETE_AFTER, orginBills));
			
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_INSERT_BEFORE, bills));
		} else if (updateType == SAVE_2_TEMP) {
			EventDispatcher.fireEvent(new BusinessEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_DELETE_BEFORE, bills));
		} else if (updateType == SAVE_2_SAVE) {
			beforeEventSave2Save(bills, orginBills);
		}
	}

	/**
	 * 给期初单据重写
	 */
	protected void beforeEventSave2Save(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {
		EventDispatcher.fireEvent(new BdUpdateEvent((String) this.tmpMap.get(BillBaseBSAction.BEANID), IEventType.TYPE_UPDATE_BEFORE, orginBills, bills));
	}

	private static int TEMP_2_TEMP = 0; // 暂存，未确认->暂存，未确认
	private static int TEMP_2_SAVE = 1; // 暂存，未确认->保存
	private static int SAVE_2_TEMP = 2; // 保存->暂存，未确认
	private static int SAVE_2_SAVE = 3; // 保存->保存

	/**
	 * @return
	 */
	public int getUpdateType(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) {
		int oldStatus = ((BaseBillVO) orginBills[0].getParentVO()).getBillstatus().intValue();
		int newStatus = ((BaseBillVO) bills[0].getParentVO()).getBillstatus().intValue();

		if (oldStatus == BillSatus.Tempeorary.VALUE.intValue() || oldStatus == BillSatus.UnComfirm.VALUE.intValue()) {
			if (newStatus == BillSatus.Tempeorary.VALUE.intValue() || newStatus == BillSatus.UnComfirm.VALUE.intValue()) {
				return TEMP_2_TEMP;
			} else {
				return TEMP_2_SAVE;
			}
		} else {
			if (newStatus == BillSatus.Tempeorary.VALUE.intValue() || newStatus == BillSatus.UnComfirm.VALUE.intValue()) {
				return SAVE_2_TEMP;
			} else {
				return SAVE_2_SAVE;
			}
		}
	}

	protected Map<String, Integer> childvostatmap = null;

	@Override
	protected void doAfterUpdate(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {

		// 修改保存所涉及的4中状态vo
		BillTermUtils.dealBillTerm(bills, childvostatmap, orginBills);
		BillTermUtils.retBodyTermVOS(bills);
		// 单据对账对账标识码
		BillBankUtils.fillSettleBankrelated_code(bills);
		// 录入业务日志
		ArapBusiLogUtils.insertSmartBusiLogs(ArapConstant.ARAP_ACTION_EDIT, (BaseAggVO) bills[0], (BaseAggVO) orginBills[0], ArapConstant.SYS_NAME);
	}

	@Override
	protected void doBeforeUpdate(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {
		ArapBillPubUtil.fillTradeTypeInfo(bills);
		for (AggregatedValueObject bill : bills) {
			ArapBillPubUtil.processMoneyOnlySum(bill);
		}

		// 补充默认信息，多版本，欧盟数据..
		ArapBillVOUtils.prepareDefaultInfo(bills, true);

		int updateType = getUpdateType(bills, orginBills);

		for (AggregatedValueObject bill : bills) {
			bill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		if (updateType == TEMP_2_SAVE || updateType == SAVE_2_SAVE) {
			for (AggregatedValueObject bill : bills) {
				for (CircularlyAccessibleValueObject item : bill.getChildrenVO()) {
					if (item.getStatus() != VOStatus.NEW && item.getStatus() != VOStatus.DELETED) {
						item.setStatus(VOStatus.UPDATED);
					}
				}
			}
		}
		if (updateType == TEMP_2_SAVE || updateType == SAVE_2_SAVE) {
			BillAccountCalendarUtils.setAccperiodYearMonth(bills);
		}
		checkIsCorrdBillMoneyControl(bills, orginBills);
		checkOtherSystemBill(bills, orginBills);
		// 校验交叉校验规则
		new CrossRuleCheckValidator().validate(bills);
		//add chenth 20161216 支持手续费核销  参照应付单生成的付款单，手工增加手续费时，把源头id设置为其中一个
		//added by weiningc 迁移到V65 start
		dealBankCharge(bills);
		//add end  
		//added by weiningc 迁移到V65 start迁移到V65 end
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
						if(item.getAttributeValue(IBillFieldGet.TOP_ITEMID) == null){
							item.setAttributeValue(IBillFieldGet.TOP_BILLID, top_billid);
							item.setAttributeValue(IBillFieldGet.TOP_ITEMID, top_itemid);
							item.setAttributeValue(IBillFieldGet.TOP_BILLTYPE, top_billtype);
							item.setAttributeValue(IBillFieldGet.TOP_TRADETYPE, top_tradetype);
						}
						item.setAttributeValue(IBillFieldGet.SETTLEMONEY, UFDouble.ZERO_DBL);
						item.setAttributeValue(IBillFieldGet.SETTLECURR, settlecurr);
						item.setAttributeValue("def1", def1);
						item.setAttributeValue("def30", isbankcharge);
					}
				}
			}
		}
	}

	/**
	 * 查询控制当前单据是否允许修改
	 * 
	 * @param bills
	 * @param orginBills
	 * @throws BusinessException
	 */
	private void checkOtherSystemBill(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {
		// 查询 修改检查插件
//		Integer syscode = ((BaseBillVO)bills[0].getParentVO()).getSyscode();
//		List<BillUpdateChecker> pluginChecks = ArapBusiPluginCenter.getAllBillUpdateCheckPlugins(syscode);
		ArapBillUpdateChecker defaultCheck = new ArapBillUpdateChecker();
		try {
			int i = 0;
			for (AggregatedValueObject newbill : bills) {
				
				BaseAggVO bill = (BaseAggVO) newbill;
				BaseAggVO oriBill = (BaseAggVO) orginBills[i++];
				BaseItemVO[] vosOld = (BaseItemVO[]) oriBill.getChildrenVO();
				
				String top_billtype = vosOld[0].getTop_billtype();
				// 过滤出当前需要执行的校验类
				List<BillUpdateChecker> executeCheckers = new ArrayList<BillUpdateChecker>();
//				boolean checkDefaultRule = true;
//				for (BillUpdateChecker billUpdateChecker : pluginChecks) {
//					if(billUpdateChecker.isMatch(bill)){
//						executeCheckers.add(billUpdateChecker);
//						checkDefaultRule = billUpdateChecker.excuteDefaultCheck();
//					}
//				}
//				// 加入收付默认修改检查规则
//				if(checkDefaultRule){
					executeCheckers.add(defaultCheck);
//				}
				
				// 检查单据行变更情况
				Map<String, List<BaseItemVO>> rowsChangeMap = getBillRowsChangeMap(bill, oriBill);
				List<BaseItemVO> addlines = rowsChangeMap.get(BillUpdateChecker.BodyAddVOs);
				List<BaseItemVO> deletelines = rowsChangeMap.get(BillUpdateChecker.BodyDeleteVOs);
				
				// 检查单据字段变更情况
				Map<String,Map<String, Object[]>> bodyfieldchange = getBodyFieldValueChangeMap(bill,oriBill);
				Map<String, Object[]> headfieldchange = getHeadFieldValueChangeMap(bill,oriBill);
				
				for (BillUpdateChecker billUpdateChecker : executeCheckers) {
					if(billUpdateChecker.canUpdateBillWithoutCtrl(bill)){
						// 可随意修改当前单据
						continue;
					}
					if(!addlines.isEmpty()){
						// 校验增行
						String errmsg = billUpdateChecker.canAddLine(top_billtype,addlines);
						if(!StringUtil.isEmpty(errmsg)){
							throw new BusinessException(errmsg);
						}
					}
					if(!deletelines.isEmpty()){
						// 校验删行
						String errmsg = billUpdateChecker.canDeleteLine(top_billtype,deletelines);
						if(!StringUtil.isEmpty(errmsg)){
							throw new BusinessException(errmsg);
						}
					}
					if(!bodyfieldchange.isEmpty()){
						// 校验表体行字段值变更
						String errmsg = billUpdateChecker.canUpdateBodyFieldValue(bodyfieldchange, bill, oriBill);
						if(!StringUtil.isEmpty(errmsg)){
							throw new BusinessException(errmsg);
						}
					}
					if(!headfieldchange.isEmpty()){
						// 校验表头值变更
						String errmsg = billUpdateChecker.canUpdateHeadFieldValue(headfieldchange, bill, oriBill);
						if(!StringUtil.isEmpty(errmsg)){
							throw new BusinessException(errmsg);
						}
					}
				}
			}
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	/**
	 * 检查表头字段值变更情况
	 * 
	 * @param bill
	 * @param oriBill
	 * @return
	 */
	private Map<String, Object[]> getHeadFieldValueChangeMap(
			BaseAggVO bill, BaseAggVO oriBill) throws BusinessException{
		Map<String, Object[]> headfieldchange = new HashMap<String, Object[]>();
		 
		BaseBillVO pvo = (BaseBillVO) bill.getParentVO();
		BaseBillVO pvoold = (BaseBillVO) oriBill.getParentVO();
		// 主表值变更检查
		IBean bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(
				pvo.getClass().getName());
		List<IAttribute> attrs = bean.getAttributesOfModel();
		for (IAttribute attr : attrs) {
			if(attr.isCalculation()){
				// 计算属性不处理
				continue;
			}
			String fieldcode = attr.getName();
			if(BaseBillVO.UPDATEIGNORE_LIST.contains(fieldcode)){
				// 表头不需要校验的字段，不处理
				continue;
			}
			Object value = pvo.getAttributeValue(fieldcode);
			Object oldvalue = pvoold.getAttributeValue(fieldcode);
			if (!simpEquals(value, oldvalue)) {
				headfieldchange.put(fieldcode, new Object[]{value,oldvalue});
			}
		}
		return headfieldchange;
	}
	/**
	 * 检查表体字段值变更情况
	 * 
	 * @param bill
	 * @param oriBill
	 * @return
	 */
	private Map<String, Map<String, Object[]>> getBodyFieldValueChangeMap(
			BaseAggVO bill, BaseAggVO oriBill)  throws BusinessException{
		Map<String, Map<String, Object[]>> map = new HashMap<String, Map<String,Object[]>>();
		BaseItemVO[] vosNew = (BaseItemVO[]) bill.getChildrenVO();
		BaseItemVO[] vosOld = (BaseItemVO[]) oriBill.getChildrenVO();
		Map<String, BaseItemVO> mapOld = new HashMap<String, BaseItemVO>();
		for (BaseItemVO vo : vosOld) {
			mapOld.put(vo.getPrimaryKey(), vo);
		}
		// 子表值变更检查
		IBean bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(
				vosNew[0].getClass().getName());
		List<IAttribute> attrs = bean.getAttributesOfModel();
		
		for (BaseItemVO itemvo : vosNew) {
			if(itemvo.getPrimaryKey() == null){
				// 新增行
				continue;
			}
			Map<String, Object[]> bodyfieldchange = new HashMap<String, Object[]>();
			map.put(itemvo.getPrimaryKey(), bodyfieldchange);
			BaseItemVO itemvoOld = mapOld.get(itemvo.getPrimaryKey());
			for (IAttribute attr : attrs) {
				if(attr.isCalculation()){
					// 计算属性不处理
					continue;
				}
				String fieldcode = attr.getName();
				Object oldvalue = itemvoOld.getAttributeValue(fieldcode);
				Object value = itemvo.getAttributeValue(fieldcode);
				if (!simpEquals(value, oldvalue)) {
					bodyfieldchange.put(fieldcode, new Object[]{value,oldvalue});
				}
			}
		}
		
		return map;
	}

	/**
	 * 检查当前单据行变更情况
	 * 
	 * @param bills
	 * @param orginBills
	 * @return
	 * @throws BusinessException
	 */
	private Map<String,List<BaseItemVO>> getBillRowsChangeMap(AggregatedValueObject bill, AggregatedValueObject orginBill) throws BusinessException {
		Map<String,List<BaseItemVO>> map = new HashMap<String, List<BaseItemVO>>();
		List<BaseItemVO> addlist = new ArrayList<BaseItemVO>();
		List<BaseItemVO> deletelist = new ArrayList<BaseItemVO>();
		map.put(BillUpdateChecker.BodyAddVOs, addlist);
		map.put(BillUpdateChecker.BodyDeleteVOs, deletelist);
		
		BaseItemVO[] vosOld = (BaseItemVO[]) orginBill.getChildrenVO();
		Map<String, BaseItemVO> mapOld = new HashMap<String, BaseItemVO>();
		BaseItemVO[] vosNew = (BaseItemVO[]) bill.getChildrenVO();

		for (BaseItemVO vo : vosOld) {
			//add chenth 20161216 手工增行的手续费在新增保存时赋值了，如果修改时删除不需要校验不能删除。
			//add by weiningc  20171012 V633适配至V65版本  start
			if(UFBoolean.TRUE.toString().equals(vo.getAttributeValue("def30"))){
				continue;
			}
			//add end
			//add by weiningc  20171012 V633适配至V65版本  end
			mapOld.put(vo.getPrimaryKey(), vo);
		}
		for (BaseItemVO vo : vosNew) {
			if (vo.getPrimaryKey() == null) {
				addlist.add(vo);
			} else {
				mapOld.remove(vo.getPrimaryKey());
			}
		}
		if (mapOld.size() > 0) {
			for (Entry<String, BaseItemVO> deletentry : mapOld.entrySet()) {
				deletelist.add(deletentry.getValue());
			}
		}
		return map;
	}


	private boolean simpEquals(Object s1, Object s2) {
		if (s1 == null && s2 == null) {
			return true;
		} else if (s1 == null || s2 == null) {
			return false;
		} else {
			return s1.equals(s2);
		}
	}

	/**
	 * @param bills
	 * @param orginBills
	 *        协同单据控制总金额
	 * @throws BusinessException
	 */
	private void checkIsCorrdBillMoneyControl(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {
		try {

			int i = 0;
			for (AggregatedValueObject bill : bills) {
				AggregatedValueObject oriBill = orginBills[i++];
				BaseBillVO billVO = (BaseBillVO) bill.getParentVO();
				BaseBillVO billOriVO = (BaseBillVO) oriBill.getParentVO();
				if (billVO.getSrc_syscode() == FromSystem.XTDJ.VALUE.intValue()) {
					if (isCorrdBillMoneyControl(billVO)) {
						if (!billVO.getMoney().equals(billOriVO.getMoney())) {
							throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0434")/* @res "协同单据进行总金额控制，不能修改单据的总金额!" */);
						}

						CircularlyAccessibleValueObject[] childrenVO = bill.getChildrenVO();
						CircularlyAccessibleValueObject[] orichildrenVO = oriBill.getChildrenVO();
						for (int j = 0; j < Math.min(childrenVO.length, orichildrenVO.length); j++) {
							if (!childrenVO[j].getAttributeValue(IBillFieldGet.PK_CURRTYPE).equals(orichildrenVO[j].getAttributeValue(IBillFieldGet.PK_CURRTYPE))) {
								throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0316_0", "02006arappub0316-0029")/* @res "协同单据不能修改币种" */);
							}
						}
					}
				}
			}

		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	/**
	 * @param headVO
	 * @return 协同单据是否控制总金额
	 * @throws BusinessException
	 */
	private boolean isCorrdBillMoneyControl(BaseBillVO headVO) throws BusinessException {
		UFBoolean flag = UFBoolean.FALSE;
		boolean exp = headVO.getSyscode().intValue() == FromSystem.AR.VALUE.intValue() || ArapBillPubUtil.isARSysBilltype(headVO.getPk_billtype());
		flag = SysInitQuery.getParaBoolean(headVO.getPk_org(), exp ? SysinitConst.AR18 : SysinitConst.AP18);
		return flag.booleanValue();

	}

	@Override
	protected AggregatedValueObject[] doUpdate(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) throws BusinessException {

		// 设置更新表体VO的状态 modified by liaobx 2010-8-19 20:00:00 vostatus
		// 已经缓存到childvostatmap
		childvostatmap = BillStatusUtils.enUpdateVOStauts(bills, orginBills);
		// 设置默认值, 设置CUD状态的VO的默认默认值
		beforeUpdateDefValue(bills, orginBills);
		return super.doUpdate(bills, orginBills);
	}

	protected void beforeUpdateDefValue(AggregatedValueObject[] bills, AggregatedValueObject[] orginBills) {
		BillStatusUtils.enCUDVODefVals(bills, true, orginBills);
	}

}