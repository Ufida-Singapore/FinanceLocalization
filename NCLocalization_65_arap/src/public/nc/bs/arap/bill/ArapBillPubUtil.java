package nc.bs.arap.bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.arap.util.ArapBillType2TableMapping;
import nc.bs.arap.util.BillMoneyVUtils;
import nc.bs.arap.util.SqlUtils;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.arap.bill.IArapBillService;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.bd.recpaytype.IRecpaytypeQueryService;
import nc.itf.bd.userdefitem.IUserdefitemQryService;
import nc.itf.fi.pub.Currency;
import nc.itf.fi.pub.SysInit;
import nc.itf.org.IFinanceOrgQryService;
import nc.itf.uap.pf.IPFBillItfDef;
import nc.itf.uap.pf.IPFWorkflowQry;
import nc.jdbc.framework.processor.BaseProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.arap.bill.IArapBillPubQueryService;
import nc.pubitf.org.IOrgUnitPubService;
import nc.pubitf.rbac.IDataPermissionPubService;
import nc.pubitf.setting.defaultdata.OrgSettingAccessor;
import nc.pubitf.uapbd.ICashflowPubService;
import nc.pubitf.uapbd.IFundPlanPubService;
import nc.pubitf.uapbd.IInoutBusiClassPubService;
import nc.vo.arap.annotation.ArapBusinessDef;
import nc.vo.arap.annotation.BillBaseBiz;
import nc.vo.arap.basebill.ArapVOFactory;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.basebill.IArapItemFieldVO;
import nc.vo.arap.bill.util.ArapH2BMapping;
import nc.vo.arap.cal.BillMathCalculator;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.global.ArapBillVOConsts;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.arap.pub.ArapBillTypeInfo;
import nc.vo.arap.pub.ArapConstant;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.arap.pub.BillEnumCollection.BillSatus;
import nc.vo.arap.pub.BillEnumCollection.FromSystem;
import nc.vo.arap.pub.BillEnumCollection.InureSign;
import nc.vo.arap.sysinit.SysinitConst;
import nc.vo.arap.termitem.TermVO;
import nc.vo.arappub.calculator.data.RelationItemForCal_Credit;
import nc.vo.arappub.calculator.data.RelationItemForCal_Debit;
import nc.vo.bd.bankaccount.BankAccSubVO;
import nc.vo.bd.bankaccount.BankAccbasVO;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.defdoc.DefdocVO;
import nc.vo.bd.period.AccperiodVO;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.bd.psn.PsndocVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.bd.userdefrule.UserdefitemVO;
import nc.vo.fibd.RecpaytypeVO;
import nc.vo.fipub.annotation.Business;
import nc.vo.fipub.annotation.BusinessType;
import nc.vo.fipub.billcode.FinanceBillCodeInfo;
import nc.vo.fipub.billcode.FinanceBillCodeUtils;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.ml.MultiLangContext;
import nc.vo.org.DeptVO;
import nc.vo.org.FinanceOrgVO;
import nc.vo.pf.change.BillItfDefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.IColumnMeta;
import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.calculator.Calculator;
import nc.vo.pubapp.calculator.Condition;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.MathTool;
import nc.vo.pubapp.scale.ScaleUtils;
import nc.vo.util.BDVisibleUtil;

public class ArapBillPubUtil {

	public static boolean isSystemCode(String nodeCode) {
		List<String> systemCodes = new ArrayList<String>();
		systemCodes.add("20060RBR");
		systemCodes.add("20060GBR");
		systemCodes.add("20060RBM");
		systemCodes.add("20060ARO");
		systemCodes.add("20060RO");
		systemCodes.add("20060GBM");
		systemCodes.add("20080PBR");
		systemCodes.add("20080EBR");
		systemCodes.add("20080PBM");
		systemCodes.add("20080EBM");
		systemCodes.add("20080APO");
		systemCodes.add("20080PO");

		if (systemCodes.contains(nodeCode)) {
			return true;
		}
		return false;
	}

	public static void checkTransTypeEnable(AggregatedValueObject[] bills) throws BusinessException {
		for (AggregatedValueObject bill : bills) {
			BilltypeVO billTypevo = PfDataCache.getBillType(((BaseAggVO) bill).getHeadVO().getPk_tradetype());
			if (null == billTypevo || (billTypevo.getIsLock() != null && billTypevo.getIsLock().booleanValue())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0353")/* @res "交易类型已停用！" */);
			}
		}
	}

	public static String getMultiLangIndex() {
		int intIndex = MultiLangContext.getInstance().getCurrentLangSeq().intValue();
		return intIndex == 1 ? "" : String.valueOf(intIndex);
	}

	private static volatile IDataPermissionPubService dataPermissionQryService = null;

	/* 根据主表辅表信息拼装单据vo数组 */
	public static BaseAggVO[] assemBaseAggVOs(List<? extends BaseBillVO> vHead, List<? extends BaseItemVO> vItems) {
		BaseBillVO header = null;
		BaseItemVO item = null;
		BaseAggVO[] vos = null;
		Hashtable<String, ArrayList<BaseItemVO>> hashItems = new Hashtable<String, ArrayList<BaseItemVO>>();

		if ((vHead != null) && (vHead.size() > 0) && (vItems != null) && (vItems.size() > 0)) {
			ArrayList<BaseItemVO> oTemp;
			ArrayList<BaseItemVO> al = null;
			for (int i = 0; i < vItems.size(); i++) {
				item = vItems.get(i);
				oTemp = hashItems.get(item.getParentPK());
				if (oTemp == null) {
					al = new ArrayList<BaseItemVO>();
				} else {
					al = oTemp;
				}
				al.add(item);
				hashItems.put(item.getParentPK(), al);
			}
			vos = new BaseAggVO[vHead.size()];
			for (int i = 0; i < vHead.size(); i++) {
				BaseAggVO dj = ArapVOFactory.getAggVOInstance((vHead.get(i)).getPk_billtype());
				header = vHead.get(i);
				dj.setParentVO(header);
				al = hashItems.get(header.getPrimaryKey());
				if ((al != null) && (al.size() > 0)) {
					BaseItemVO[] items = new BaseItemVO[al.size()];
					items = al.toArray(items);
					dj.setChildrenVO(items);
				}
				vos[i] = dj;
			}
		}
		return vos;
	}

	/**
	 * 取得上层来源单据pk
	 *
	 * @param children
	 * @return
	 * @see
	 * @since V6.0
	 */
	public static List<String> getTopBillPKs(BaseItemVO[] children) {
		if ((null == children) || (children.length == 0)) {
			return null;
		}
		List<String> ls = new ArrayList<String>();
		for (BaseItemVO item : children) {
			if (!StringUtil.isEmptyWithTrim(item.getTop_itemid())) {
				ls.add(item.getTop_itemid());
			}
		}
		return ls;
	}

	/**
	 * 子表pk与子表vo的映射
	 *
	 * @param children
	 * @return
	 * @see
	 * @since V6.0
	 */
	public static Map<String, BaseItemVO> getMapByChildren(BaseItemVO[] children) {
		Map<String, BaseItemVO> ret = new HashMap<String, BaseItemVO>();
		for (BaseItemVO item : children) {
			ret.put(item.getPrimaryKey(), item);
		}
		return ret;
	}

	public static BaseAggVO[] getWriteBackBillVO(BaseAggVO[] bill, UFDate current, String userid) throws BusinessException {
		List<BaseAggVO> ret = new ArrayList<BaseAggVO>();
		for (BaseAggVO t : bill) {
			try {
				BaseAggVO writeBackBillVO = getWriteBackBillVO(t, current, userid);
				ret.add(writeBackBillVO);
			} catch (InvalidAccperiodExcetion e) {
				throw ExceptionHandler.createException(e.getMessage());
			}
		}
		return ret.toArray(new BaseAggVO[0]);
	}

	/**
	 * 生成红字冲销单据
	 *
	 * @param bill
	 *            单据聚合VO
	 * @param current
	 *            当前日期
	 * @param userid
	 *            用户id
	 * @return
	 * @throws InvalidAccperiodExcetion
	 * @see
	 * @since V6.0
	 */
	@SuppressWarnings( { "unchecked" })
	@Business(business = ArapBusinessDef.BillBase, subBusiness = BillBaseBiz.RedContrast,  description = "根据来源单据，生成红冲的单据", type = BusinessType.TOOL)/*-=notranslate=-*/
	public static <T extends BaseAggVO> BaseAggVO getWriteBackBillVO(T bill, UFDate current, String userid) throws InvalidAccperiodExcetion {

		String[] itemQuantityKeys = new String[] { IBillFieldGet.QUANTITY_DE, IBillFieldGet.QUANTITY_CR, IBillFieldGet.QUANTITY_BAL };
		String[] itemMnyKeys = new String[] { IBillFieldGet.MONEY_DE, IBillFieldGet.LOCAL_MONEY_DE, IBillFieldGet.LOCAL_MONEY_CR, IBillFieldGet.MONEY_CR, IBillFieldGet.MONEY_BAL, IBillFieldGet.LOCAL_MONEY_BAL,
				IBillFieldGet.LOCAL_TAX_DE, IBillFieldGet.NOTAX_DE, IBillFieldGet.LOCAL_NOTAX_DE, IBillFieldGet.LOCAL_TAX_CR, IBillFieldGet.NOTAX_CR, IBillFieldGet.LOCAL_NOTAX_CR, IBillFieldGet.GROUPBALANCE,
				IBillFieldGet.GLOBALBALANCE, IBillFieldGet.GROUPDEBIT, IBillFieldGet.GLOBALDEBIT, IBillFieldGet.GROUPTAX_DE, IBillFieldGet.GLOBALTAX_DE, IBillFieldGet.GROUPNOTAX_DE, IBillFieldGet.GLOBALNOTAX_DE, IBillFieldGet.GROUPCREBIT,
				IBillFieldGet.GLOBALCREBIT, IBillFieldGet.GROUPTAX_CRE, IBillFieldGet.GLOBALTAX_CRE, IBillFieldGet.GROUPNOTAX_CRE, IBillFieldGet.GLOBALNOTAX_CRE, IBillFieldGet.OCCUPATIONMNY
				,IBillFieldGet.CALTAXMNY,IBillFieldGet.NOSUBTAX};
		String[] clearItemKeys = new String[] { IBillFieldGet.PAYDATE, IBillFieldGet.PAYMAN, IBillFieldGet.PAYFLAG, IBillFieldGet.BILLNO, IBillFieldGet.OCCUPATIONMNY ,"ts"};
		// 票据号也不能带到红字单上
		String[] clearHeadKeys = new String[] { IBillFieldGet.SETTLENUM, IBillFieldGet.PAYDATE, IBillFieldGet.PAYMAN, IBillFieldGet.SETTLEFLAG, IBillFieldGet.BILLNO, IBillFieldGet.APPROVESTATUS, IBillFieldGet.ISFLOWBILL, IBillFieldGet.ISFROMINDEPENDENT};
		T vo = (T) bill.clone();
		BaseBillVO parent = (BaseBillVO) vo.getParentVO();
		String billno = parent.getBillno();
		String pk_bill = parent.getPrimaryKey();
		BaseItemVO[] children = (BaseItemVO[]) vo.getChildrenVO();

		UFDouble NEGATIVE = new UFDouble("-1");
		UFDouble ZERO = new UFDouble("0");

		// 注释?
		AccountCalendar calendar = AccountCalendar.getInstanceByPk_org(parent.getPk_org());
		calendar.setDate(current);
		AccperiodVO accperiod = calendar.getYearVO();
		accperiod.setAccperiodmonth(new AccperiodmonthVO[] { calendar.getMonthVO() });

		String periodYear = accperiod.getPeriodyear();
		String periodMonth = accperiod.getAccperiodmonth()[0].getAccperiodmth();

		// 清空表头项
		for (String key : clearHeadKeys) {
			parent.setAttributeValue(key, null);
		}
		
		for (int i = 0; i < children.length; i++) {
			if(parent.getSrc_syscode()!=null && (parent.getSrc_syscode().equals(FromSystem.AM.VALUE) || parent.getSrc_syscode().equals(FromSystem.CT.VALUE))){
				children[i].setSrc_billid(null);
				children[i].setSrc_billtype(null);
				children[i].setSrc_itemid(null);
				children[i].setSrc_tradetype(null);
			}
		}
		
		parent.setBillmaker(userid);
		parent.setTransientFlag(ArapBillVOConsts.ACT_RED_BILL);
		parent.setPrimaryKey(null);
		parent.setApprover(InvocationInfoProxy.getInstance().getUserId());
		long bizDateTime = InvocationInfoProxy.getInstance().getBizDateTime();
		parent.setApprovedate(new UFDateTime(bizDateTime));
		parent.setBilldate(current);
		parent.setBusidate(current);
		parent.setSignyear(periodYear);
		parent.setSignperiod(periodMonth);
		parent.setSigndate(current);
		parent.setSignuser(userid);
		parent.setApprovestatus(BillEnumCollection.ApproveStatus.PASSING.VALUE.intValue());

		parent.setIsmandatepay(UFBoolean.FALSE);
		parent.setSettletype(ArapBillVOConsts.m_intJSZXZF_NONE);
		parent.setEffectstatus(ArapBillVOConsts.m_intSXBZ_VALID);
		parent.setSrc_syscode(parent.getSyscode());
		parent.setIsreded(ArapBillVOConsts.RED);
		parent.setTs(null);

		parent.setGlobalrate(parent.getGlobalrate() == null ? UFDouble.ZERO_DBL : parent.getGlobalrate());
		parent.setGrouprate(parent.getGrouprate() == null ? UFDouble.ZERO_DBL : parent.getGrouprate());

		try {
			boolean reset=false;
			for(int i = 0; i < children.length; i++){
				if(!Currency.getLocalCurrPK(children[i].getPk_org()).equals(children[i].getPk_currtype())){
					children[i].setRate(null);
					reset=true;
				}
				if(!children[i].getMoney_bal().equals(children[i].getMoney_cr().add(children[i].getMoney_de())) && children[i].getMoney_bal().compareTo(UFDouble.ZERO_DBL)!=0){
					if (children[i].getDirection().equals(BillEnumCollection.Direction.CREDIT.VALUE)) {
						children[i].setMoney_cr(children[i].getMoney_bal());
						// 防止本币不重算，设置本币为0
						children[i].setLocal_money_cr(UFDouble.ZERO_DBL);
					} else if (children[i].getDirection().equals(BillEnumCollection.Direction.DEBIT.VALUE)) {
						children[i].setMoney_de(children[i].getMoney_bal());
						// 防止本币不重算，设置本币为0
						children[i].setLocal_money_de(UFDouble.ZERO_DBL);
					}
					reset=true;
				}
			}
			if(reset){
				ArapBillPubUtil.processMoney(vo);
			}
		} catch (BusinessException e) {
			ExceptionHandler.handleRuntimeException(e);
		}
		for (int i = 0; i < children.length; i++) {

			children[i].setTop_itemid(children[i].getPrimaryKey());
			children[i].setTop_billid(pk_bill);
			children[i].setTop_billtype(parent.getPk_billtype());
			children[i].setTop_tradetype(parent.getPk_tradetype());
			children[i].setParentPK(null);
			children[i].setPrimaryKey(null);
			children[i].setBusidate(current);
			children[i].setBilldate(current);
			children[i].setIsverifyfinished(UFBoolean.FALSE);
			children[i].setStatus(VOStatus.NEW);
			children[i].setConfernum(null);
			//红冲需要清空的字段
			children[i].setSettlemoney(children[i].getOccupationmny());
			children[i].setSettlecurr(children[i].getPk_currtype());
			for (String key : clearItemKeys) {
				children[i].setAttributeValue(key, null);
			}

			for (String key : itemQuantityKeys) {
				UFDouble value = (UFDouble) children[i].getAttributeValue(key);
				if (null != value && !value.equals(ZERO)) {
					children[i].setAttributeValue(key, value.multiply(NEGATIVE));
				}
			}

			for (String key : itemMnyKeys) {
				UFDouble value = (UFDouble) children[i].getAttributeValue(key);
				if (null != value && !value.equals(ZERO)) {
					children[i].setAttributeValue(key, value.multiply(NEGATIVE));
				}
			}

			UFDouble balmoney = null;
			UFDouble orgmoney = null;
			UFDouble groupmoney = null;
			UFDouble globalmoney = null;
			if (children[i].getDirection().equals(BillEnumCollection.Direction.CREDIT.VALUE)) {
				UFDouble moneyCr = children[i].getSettlemoney().multiply(NEGATIVE);
				UFDouble moneyCrOrg = children[i].getLocal_money_cr();
				UFDouble moneyCrGroup = children[i].getGroupcrebit();
				UFDouble moneyCrGlobal = children[i].getGlobalcrebit();
				balmoney = null == moneyCr ? UFDouble.ZERO_DBL : moneyCr;
				orgmoney = null == moneyCrOrg ? UFDouble.ZERO_DBL : moneyCrOrg;
				groupmoney = null == moneyCrGroup ? UFDouble.ZERO_DBL : moneyCrGroup;
				globalmoney = null == moneyCrGlobal ? UFDouble.ZERO_DBL : moneyCrGlobal;
			} else if (children[i].getDirection().equals(BillEnumCollection.Direction.DEBIT.VALUE)) {
				UFDouble moneyCr = children[i].getSettlemoney().multiply(NEGATIVE);
				UFDouble moneyCrOrg = children[i].getLocal_money_de();
				UFDouble moneyCrGroup = children[i].getGroupdebit();
				UFDouble moneyCrGlobal = children[i].getGlobaldebit();
				balmoney = null == moneyCr ? UFDouble.ZERO_DBL : moneyCr;
				orgmoney = null == moneyCrOrg ? UFDouble.ZERO_DBL : moneyCrOrg;
				groupmoney = null == moneyCrGroup ? UFDouble.ZERO_DBL : moneyCrGroup;
				globalmoney = null == moneyCrGlobal ? UFDouble.ZERO_DBL : moneyCrGlobal;
			}

			children[i].setMoney_bal(balmoney);
			children[i].setOccupationmny(balmoney);
			children[i].setLocal_money_bal(orgmoney);
			children[i].setGroupbalance(groupmoney);
			children[i].setGlobalbalance(globalmoney);

			UFDouble realQuantityde = null == children[i].getQuantity_de() ? UFDouble.ZERO_DBL : children[i].getQuantity_de();
			UFDouble realQuantitycr = null == children[i].getQuantity_cr() ? UFDouble.ZERO_DBL : children[i].getQuantity_cr();
			children[i].setQuantity_bal(realQuantitycr.add(realQuantityde));

			children[i].setGlobalrate(children[i].getGlobalrate() == null ? UFDouble.ZERO_DBL : children[i].getGlobalrate());
			children[i].setGrouprate(children[i].getGrouprate() == null ? UFDouble.ZERO_DBL : children[i].getGrouprate());
			children[i].setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0463")/*
			 * @res
			 * "红冲原单据号:"
			 */+ billno);
		}
		
		
		
		BillMoneyVUtils.sumBodyToHead(parent, children);
		// 状态
		vo.getHeadVO().setBillstatus(BillSatus.Save.VALUE);
		vo.getHeadVO().setEffectstatus(InureSign.NOINURE.VALUE);

		return vo;
	}

	public static void processMoney(AggregatedValueObject bill) {
		new CalcMoneyUtil().processMoney((BaseAggVO) bill);
	}
	/**
	 * 处理表头的原币金额,本币金额 处理表体的原币余额,本币余额
	 *
	 * @param bill
	 * @see
	 * @since V6.0
	 */
	public static void processMoney1(AggregatedValueObject bill) {

		try {
			CircularlyAccessibleValueObject head = bill.getParentVO();
			CircularlyAccessibleValueObject[] items = bill.getChildrenVO();
			if (ArrayUtils.isEmpty(items)) {
				return;
			}
			String billClass = (String) head.getAttributeValue(IBillFieldGet.BILLCLASS);
			UFDouble money = null;
			UFDouble local_money = null;
			UFDouble groupmoney = null;
			UFDouble globalmoney = null;

			Object pk_headorg = head.getAttributeValue(IBillFieldGet.PK_ORG);
			if (null == pk_headorg) {
				ExceptionUtils.wrappBusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0466")/*
				 * @res
				 * "当前单据财务组织为空，请检查上游单据或者单据转换规则！"
				 */);
			}
			String localpkCurrtype = Currency.getOrgLocalCurrPK(pk_headorg.toString());
			if (null == localpkCurrtype) {
				ExceptionUtils.wrappBusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0467")/*
				 * @res
				 * "无法获取当前单据财务组织本位币！请清理缓存后再试。"
				 */);
			}
			for (CircularlyAccessibleValueObject item : items) {
				item.setAttributeValue(IBillFieldGet.PK_ORG, pk_headorg);
				if (null == (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE)) {
					item.setAttributeValue(IBillFieldGet.PK_CURRTYPE, localpkCurrtype);
				}
			}
			if (null == head.getAttributeValue(IBillFieldGet.PK_CURRTYPE)) {
				head.setAttributeValue(IBillFieldGet.PK_CURRTYPE, localpkCurrtype);
			}

			String pk_org = head.getAttributeValue(IBillFieldGet.PK_ORG).toString();
			String pkCurrtype = Currency.getOrgLocalCurrPK(pk_org);
			String pk_group = (String) (head.getAttributeValue(IBillFieldGet.PK_GROUP) == null ? InvocationInfoProxy.getInstance().getGroupId() : head.getAttributeValue(IBillFieldGet.PK_GROUP));
			String pkCurrtype_group = Currency.getOrgLocalCurrPK(pk_group);
			String pkCurrtype_global = Currency.getOrgLocalCurrPK(IBillFieldGet.PK_GLOBAL);

			Condition cond = new Condition();
			cond.setCalOrigCurr(false);
			cond.setIsCalLocalCurr(true);
			cond.setIsChgPriceOrDiscount(false);
			cond.setIsFixNchangerate(false);
			cond.setIsFixNqtunitrate(false);
			cond.setGroupLocalCurrencyEnable(ArapBillCalUtil.isUseGroupMoney(pk_group));
			cond.setGlobalLocalCurrencyEnable(ArapBillCalUtil.isUseGlobalMoney());
			cond.setOrigCurToGlobalMoney(ArapBillCalUtil.isOrigCurToGlobalMoney());
			cond.setOrigCurToGroupMoney(ArapBillCalUtil.isOrigCurToGroupMoney(pk_group));
			try {
				cond.setIsTaxOrNet(BillMathCalculator.getProior(0, pk_org));
			} catch (Exception e) {
				nc.bs.logging.Log.getInstance("arapExceptionLog").error(e);
			}

			List<String> list = Arrays.asList(IBillFieldGet.F0, IBillFieldGet.F1, IBillFieldGet.F2, IBillFieldGet.F3, "21", "Z2", "Z3", "Z4", "Z5");
			List<String> Ctlist = Arrays.asList("Z2", "Z3", "Z4", "Z5");
			if (IBillFieldGet.ZS.equals(billClass) || IBillFieldGet.YS.equals(billClass) || IBillFieldGet.FK.equals(billClass)) {

				for (CircularlyAccessibleValueObject item : items) {

					if (null == item.getAttributeValue(IBillFieldGet.TAXTYPE)) {
						item.setAttributeValue(IBillFieldGet.TAXTYPE, BillEnumCollection.TaxType.TAXOUT.VALUE);
					}

					Boolean isTaxFirst = true;
					// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0",
					// "02006pub-0468")/* @res "含税价格优先"
					// */.equals(SysInitQuery.getParaString(pk_org, "AR21"));
					UFDouble[] rates = ArapBillCalUtil.getRate(item.getAttributeValue(IBillFieldGet.PK_CURRTYPE).toString(), pk_org, pk_group, item.getAttributeValue(IBillFieldGet.BILLDATE) == null ? new UFDate() : (UFDate) item
							.getAttributeValue(IBillFieldGet.BILLDATE), head.getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString());
					UFDouble orgRate = rates[0];
					UFDouble GROUP_RATE = rates[1];
					UFDouble GlOBAL_RATE = rates[2];

					if (item.getAttributeValue(IBillFieldGet.MONEY_DE) == null || item.getAttributeValue(IBillFieldGet.MONEY_DE).equals(UFDouble.ZERO_DBL)) {
						continue;
					}

					String top_billtype = (String) item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE);

					boolean isNeedCalLocal = false;
					if ((top_billtype != null && !list.contains(top_billtype)) && null != head.getAttributeValue(IBillFieldGet.SETT_ORG) && head.getAttributeValue(IBillFieldGet.PK_ORG).equals(head.getAttributeValue(IBillFieldGet.SETT_ORG))) {
						item.setAttributeValue(IBillFieldGet.RATE, item.getAttributeValue(IBillFieldGet.RATE) == null ? orgRate : item.getAttributeValue(IBillFieldGet.RATE));
					} else {
						isNeedCalLocal = true;
						item.setAttributeValue(IBillFieldGet.RATE, orgRate);
					}

					if(((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)) == null || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)).compareTo(UFDouble.ZERO_DBL) == 0){
						isNeedCalLocal = true;
					}
					//					if (isNeedCalLocal || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)) == null || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)).compareTo(UFDouble.ZERO_DBL) == 0
					//							|| MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE)).compareTo(item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)) != 0) {
					//						if (isTaxFirst) {
					//							UFDouble newLocalMoneyDe = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_DE, newLocalMoneyDe);
					//							UFDouble newLocalMoneytax = UFDouble.;
					//							item.setAttributeValue(IBillFieldGet.LOCAL_TAX_DE, newLocalMoneytax);
					//							item.setAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE)));
					//						} else {
					//							UFDouble newLocalMoneyNotax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_DE), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE, newLocalMoneyNotax);
					//							UFDouble newLocalMoneytax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.TAX_DE), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_TAX_DE, newLocalMoneytax);
					//							item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_DE, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE)));
					//						}
					//					}
					item.setAttributeValue(IBillFieldGet.GROUPRATE, GROUP_RATE);
					String NC001 = SysInit.getParaString(pk_group, "NC001");
					UFDouble newGroupdebit = UFDouble.ZERO_DBL;
					UFDouble newGroupMoneyNotax = UFDouble.ZERO_DBL;
					UFDouble newGroupMoneyTax = UFDouble.ZERO_DBL;

					if (SysinitConst.NC001_BASE_ORIG.equals(NC001)) {
						newGroupdebit = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						newGroupMoneyNotax = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_DE), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
					} else if (SysinitConst.NC001_NOT_USED.equals(NC001)) {

					} else {
						newGroupdebit = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						newGroupMoneyNotax = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
					}

					if (!SysinitConst.NC001_NOT_USED.equals(NC001)) {
						newGroupMoneyTax = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
					}

					item.setAttributeValue(IBillFieldGet.GROUPDEBIT, newGroupdebit);
					item.setAttributeValue(IBillFieldGet.GROUPNOTAX_DE, newGroupMoneyNotax);
					item.setAttributeValue(IBillFieldGet.GROUPTAX_DE, newGroupMoneyTax);

					item.setAttributeValue(IBillFieldGet.GLOBALRATE, GlOBAL_RATE);
					String NC002 = SysInit.getParaString(IBillFieldGet.PK_GLOBAL, "NC002");
					UFDouble newGlobaldebit = UFDouble.ZERO_DBL;
					UFDouble newGlobalMoneyNotax = UFDouble.ZERO_DBL;
					UFDouble newGlobalMoneyTax = UFDouble.ZERO_DBL;
					if (SysinitConst.NC002_BASE_ORIG.equals(NC002)) {
						newGlobaldebit = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
						newGlobalMoneyNotax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_DE), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
					} else if (SysinitConst.NC002_NOT_USED.equals(NC002)) {

					} else {

						newGlobaldebit = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALRATE),
							new UFDate());
						newGlobalMoneyNotax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
					}

					if(!SysinitConst.NC002_NOT_USED.equals(NC002)){
						//始终按照组织本位币计税
						newGlobalMoneyTax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALRATE),
							new UFDate());
					}


					item.setAttributeValue(IBillFieldGet.GLOBALDEBIT, newGlobaldebit);
					item.setAttributeValue(IBillFieldGet.GLOBALNOTAX_DE, newGlobalMoneyNotax);
					item.setAttributeValue(IBillFieldGet.GLOBALTAX_DE, newGlobalMoneyTax);

					if (isTaxFirst) {
						item.setAttributeValue(IBillFieldGet.GROUPNOTAX_DE, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.GROUPDEBIT), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPTAX_DE)));
						item.setAttributeValue(IBillFieldGet.GLOBALNOTAX_DE, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALDEBIT), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALTAX_DE)));
					} else {
						item.setAttributeValue(IBillFieldGet.GROUPDEBIT, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.GROUPNOTAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPTAX_DE)));
						item.setAttributeValue(IBillFieldGet.GLOBALDEBIT, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALNOTAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALTAX_DE)));
					}

					item.setAttributeValue(IBillFieldGet.MONEY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE));
					item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE));
					item.setAttributeValue(IBillFieldGet.QUANTITY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_DE) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_DE));
					item.setAttributeValue(IBillFieldGet.GROUPBALANCE, (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPDEBIT) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPDEBIT));
					item.setAttributeValue(IBillFieldGet.GLOBALBALANCE, (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALDEBIT) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALDEBIT));
					if (null != item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE) && Ctlist.contains(item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE))) {
						if (null != item.getAttributeValue(IBillFieldGet.TAXPRICE) && ((UFDouble) item.getAttributeValue(IBillFieldGet.TAXPRICE)).compareTo(UFDouble.ZERO_DBL) != 0
								&& ((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE)).compareTo(UFDouble.ZERO_DBL) != 0) {
							UFDouble quantity = (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_DE);
							UFDouble newquantity = ((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE)).div(((UFDouble) item.getAttributeValue(IBillFieldGet.TAXPRICE)));
							if (null != quantity) {
								newquantity=newquantity.setScale(quantity.getPower(), UFDouble.ROUND_HALF_UP);
							}
							item.setAttributeValue(IBillFieldGet.QUANTITY_DE, newquantity);
							item.setAttributeValue(IBillFieldGet.QUANTITY_BAL, newquantity);
						}
					}
					doReCalculatorDe(isNeedCalLocal,pk_group, cond, item);
				}

				money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_DE, items);
				local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_DE, items);

				groupmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GROUPDEBIT, items);
				globalmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GLOBALDEBIT, items);

			} else if (IBillFieldGet.ZF.equals(billClass) || IBillFieldGet.YF.equals(billClass) || IBillFieldGet.SK.equals(billClass)) {

				for (CircularlyAccessibleValueObject item : items) {

					if (null == item.getAttributeValue(IBillFieldGet.TAXTYPE)) {
						item.setAttributeValue(IBillFieldGet.TAXTYPE, BillEnumCollection.TaxType.TAXOUT.VALUE);
					}

					Boolean isTaxFirst = true;

					UFDouble[] rates = ArapBillCalUtil.getRate((String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pk_org, pk_group, item.getAttributeValue(IBillFieldGet.BILLDATE) == null ? new UFDate() : (UFDate) item
							.getAttributeValue(IBillFieldGet.BILLDATE), head.getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString());
					UFDouble orgRate = rates[0];
					UFDouble GROUP_RATE = rates[1];
					UFDouble GlOBAL_RATE = rates[2];

					if (item.getAttributeValue(IBillFieldGet.MONEY_CR) == null || ((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR)).equals(UFDouble.ZERO_DBL)) {
						continue;
					}

					String top_billtype = (String) item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE);
					boolean isNeedCalLocal = false;

					if ((top_billtype != null && !list.contains(top_billtype)) && null != head.getAttributeValue(IBillFieldGet.SETT_ORG) && head.getAttributeValue(IBillFieldGet.PK_ORG).equals(head.getAttributeValue(IBillFieldGet.SETT_ORG))) {
						item.setAttributeValue(IBillFieldGet.RATE, (UFDouble) item.getAttributeValue(IBillFieldGet.RATE) == null ? orgRate : (UFDouble) item.getAttributeValue(IBillFieldGet.RATE));
					} else {
						isNeedCalLocal = true;
						item.setAttributeValue(IBillFieldGet.RATE, orgRate);
					}

					if(((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)) == null || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)).compareTo(UFDouble.ZERO_DBL) == 0){
						isNeedCalLocal = true;
					}

					//					if (isNeedCalLocal || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)) == null || ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)).compareTo(UFDouble.ZERO_DBL) == 0
					//							|| needReCalCulateCR(item)) {
					//						if (isTaxFirst) {
					//							UFDouble newLocalMoneyCr = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_CR, newLocalMoneyCr);
					//							UFDouble newLocalMoneyTax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.TAX_CR), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_TAX_CR, newLocalMoneyTax);
					//							item.setAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR)));
					//						} else {
					//
					//							UFDouble newLocalMoneyNotax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_CR), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR, newLocalMoneyNotax);
					//							UFDouble newLocalMoneyTax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.TAX_CR), (UFDouble) item
					//									.getAttributeValue(IBillFieldGet.RATE), new UFDate());
					//							item.setAttributeValue(IBillFieldGet.LOCAL_TAX_CR, newLocalMoneyTax);
					//							item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_CR, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR)));
					//						}
					//					}
					item.setAttributeValue(IBillFieldGet.GROUPRATE, GROUP_RATE);
					String NC001 = SysInit.getParaString(pk_group, "NC001");
					UFDouble newGroupcredit = UFDouble.ZERO_DBL;
					UFDouble newGroupMoneyNotax = UFDouble.ZERO_DBL;
					UFDouble newGroupMoneyTax = UFDouble.ZERO_DBL;
					if (SysinitConst.NC001_BASE_ORIG.equals(NC001)) {
						newGroupcredit = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						newGroupMoneyNotax = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_CR), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());

					} else if (SysinitConst.NC001_NOT_USED.equals(NC001)) {
					} else {
						newGroupcredit = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						newGroupMoneyNotax = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());

					}

					newGroupMoneyTax = Currency.getAmountByOpp(pk_group, pkCurrtype, pkCurrtype_group, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());

					item.setAttributeValue(IBillFieldGet.GROUPCREBIT, newGroupcredit);
					item.setAttributeValue(IBillFieldGet.GROUPNOTAX_CRE, newGroupMoneyNotax);
					item.setAttributeValue(IBillFieldGet.GROUPTAX_CRE, newGroupMoneyTax);

					item.setAttributeValue(IBillFieldGet.GLOBALRATE, GlOBAL_RATE);
					String NC002 = SysInit.getParaString(IBillFieldGet.PK_GLOBAL, "NC002");
					UFDouble newGlobalcredit = UFDouble.ZERO_DBL;
					UFDouble newGlobalMoneyNotax = UFDouble.ZERO_DBL;
					UFDouble newGlobalMoneyTax = UFDouble.ZERO_DBL;

					if (SysinitConst.NC002_BASE_ORIG.equals(NC002)) {
						newGlobalcredit = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
						newGlobalMoneyNotax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.NOTAX_CR), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
					} else if (SysinitConst.NC002_NOT_USED.equals(NC002)) {
					} else {

						newGlobalcredit = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
						newGlobalMoneyNotax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR), (UFDouble) item
							.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
					}
					newGlobalMoneyTax = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, pkCurrtype, pkCurrtype_global, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALRATE),
						new UFDate());


					item.setAttributeValue(IBillFieldGet.GLOBALCREBIT, newGlobalcredit);
					item.setAttributeValue(IBillFieldGet.GLOBALNOTAX_CRE, newGlobalMoneyNotax);
					item.setAttributeValue(IBillFieldGet.GLOBALTAX_CRE, newGlobalMoneyTax);

					if (isTaxFirst) {
						item.setAttributeValue(IBillFieldGet.GROUPNOTAX_CRE, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.GROUPCREBIT), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPTAX_CRE)));
						item.setAttributeValue(IBillFieldGet.GLOBALNOTAX_CRE, MathTool.sub((UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALCREBIT), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALTAX_CRE)));
					} else {
						item.setAttributeValue(IBillFieldGet.GROUPCREBIT, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.GROUPNOTAX_CRE), (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPTAX_CRE)));
						item.setAttributeValue(IBillFieldGet.GLOBALCREBIT, MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALNOTAX_CRE), (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALTAX_CRE)));
					}

					item.setAttributeValue(IBillFieldGet.MONEY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR));
					item.setAttributeValue(IBillFieldGet.LOCAL_MONEY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR));
					item.setAttributeValue(IBillFieldGet.QUANTITY_BAL, (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_CR) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_CR));
					item.setAttributeValue(IBillFieldGet.GROUPBALANCE, (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPCREBIT) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.GROUPCREBIT));
					item.setAttributeValue(IBillFieldGet.GLOBALBALANCE, (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALCREBIT) == null ? UFDouble.ZERO_DBL : (UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALCREBIT));
					if (null != item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE) && Ctlist.contains(item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE))) {
						if (null != item.getAttributeValue(IBillFieldGet.TAXPRICE) && ((UFDouble) item.getAttributeValue(IBillFieldGet.TAXPRICE)).compareTo(UFDouble.ZERO_DBL) != 0
								&& ((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR)).compareTo(UFDouble.ZERO_DBL) != 0) {
							UFDouble quantity = (UFDouble) item.getAttributeValue(IBillFieldGet.QUANTITY_CR);
							UFDouble newquantity = ((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR)).div(((UFDouble) item.getAttributeValue(IBillFieldGet.TAXPRICE)));
							if (null != quantity) {
								newquantity=newquantity.setScale(quantity.getPower(), UFDouble.ROUND_HALF_UP);
							}
							item.setAttributeValue(IBillFieldGet.QUANTITY_CR, newquantity);
							item.setAttributeValue(IBillFieldGet.QUANTITY_BAL, newquantity);
						}
					}
					doReCalculatorCR(isNeedCalLocal,pk_group, cond, item);
				}

				// 计算托收金额 本币字段
				for (CircularlyAccessibleValueObject item : items) {
					if (null != item.getAttributeValue(IBillFieldGet.AGENTRECEIVEPRIMAL)) {

						UFDouble newLocalMoneytax = Currency.getAmountByOpp(pk_org, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.AGENTRECEIVEPRIMAL), (UFDouble) item
							.getAttributeValue(IBillFieldGet.RATE), new UFDate());
						item.setAttributeValue(IBillFieldGet.AGENTRECEIVELOCAL, newLocalMoneytax);

						String NC001 = SysInit.getParaString(pk_group, "NC001");
						UFDouble newGroupMoney = UFDouble.ZERO_DBL;
						pkCurrtype = Currency.getOrgLocalCurrPK(pk_group);
						if (SysinitConst.NC001_BASE_ORIG.equals(NC001)) {
							newGroupMoney = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.AGENTRECEIVEPRIMAL), (UFDouble) item
								.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						} else if (SysinitConst.NC001_NOT_USED.equals(NC001)) {
						} else {
							newGroupMoney = Currency.getAmountByOpp(pk_group, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.AGENTRECEIVELOCAL), (UFDouble) item
								.getAttributeValue(IBillFieldGet.GROUPRATE), new UFDate());
						}
						item.setAttributeValue(IBillFieldGet.GROUPAGENTRECEIVELOCAL, newGroupMoney);

						String NC002 = SysInit.getParaString(IBillFieldGet.PK_GLOBAL, "NC002");
						UFDouble newGlobalMoney = UFDouble.ZERO_DBL;
						pkCurrtype = Currency.getOrgLocalCurrPK(IBillFieldGet.PK_GLOBAL);
						if (SysinitConst.NC002_BASE_ORIG.equals(NC002)) {
							newGlobalMoney = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.AGENTRECEIVEPRIMAL),
								(UFDouble) item.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());
						} else if (SysinitConst.NC002_NOT_USED.equals(NC002)) {
						} else {

							newGlobalMoney = Currency.getAmountByOpp(IBillFieldGet.PK_GLOBAL, (String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pkCurrtype, (UFDouble) item.getAttributeValue(IBillFieldGet.AGENTRECEIVELOCAL), (UFDouble) item
								.getAttributeValue(IBillFieldGet.GLOBALRATE), new UFDate());

						}
						item.setAttributeValue(IBillFieldGet.GLOBALAGENTRECEIVELOCAL, newGlobalMoney);

					}
				}

				money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_CR, items);
				local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_CR, items);
				groupmoney = ArapBillPubUtil.sumB(IBillFieldGet.GROUPCREBIT, items);
				globalmoney = ArapBillPubUtil.sumB(IBillFieldGet.GLOBALCREBIT, items);
			}

			head.setAttributeValue(IBillFieldGet.MONEY, money);
			head.setAttributeValue(IBillFieldGet.LOCAL_MONEY, local_money);
			head.setAttributeValue(IBillFieldGet.RATE, items[0].getAttributeValue(IBillFieldGet.RATE));
			head.setAttributeValue(IBillFieldGet.GROUPRATE, items[0].getAttributeValue(IBillFieldGet.GROUPRATE));
			head.setAttributeValue(IBillFieldGet.GLOBALRATE, items[0].getAttributeValue(IBillFieldGet.GLOBALRATE));
			head.setAttributeValue(IBillFieldGet.GROUP_LOCAL_H, groupmoney);
			head.setAttributeValue(IBillFieldGet.GLOBAL_LOCAL_H, globalmoney);

		} catch (BusinessException e1) {
			ExceptionUtils.wrappException(e1);
		}

	}

	private static void doReCalculatorDe(boolean isNeedCalLocal, String pk_group, Condition cond, CircularlyAccessibleValueObject item) {
		boolean isInterNational = BillEnumCollection.BuySellType.OUT_SELL.VALUE.equals((item.getAttributeValue(IBillFieldGet.BUYSELLFLAG)));
		cond.setInternational(isInterNational);
		if(isNeedCalLocal || needReCalCulateDE(item)){
			Calculator tool = new Calculator(new CircularlyAccessibleValueObject[] { item }, RelationItemForCal_Debit.getInstance());
			tool.calculate(cond, IBillFieldGet.MONEY_DE, new ScaleUtils(pk_group));
		}
	}

	private static void doReCalculatorCR(boolean isNeedCalLocal, String pk_group, Condition cond, CircularlyAccessibleValueObject item) {
		boolean isInterNational = BillEnumCollection.BuySellType.OUT_BUY.VALUE.equals((item.getAttributeValue(IBillFieldGet.BUYSELLFLAG)));
		cond.setInternational(isInterNational);
		if(isNeedCalLocal || needReCalCulateCR(item)){
			Calculator tool = new Calculator(new CircularlyAccessibleValueObject[] { item }, RelationItemForCal_Credit.getInstance());
			tool.calculate(cond, IBillFieldGet.MONEY_CR, new ScaleUtils(pk_group));
		}
	}

	/**
	 * 1. 国外业务： 如果本币无税<>本币金额 ， 则需要重算
	 * 2. 国内业务： 如果本币无税+本币税金<>本币金额 ， 则需要重算
	 * @param item
	 * @return 是否需要重新计算金额
	 */
	public static boolean needReCalCulateCR(CircularlyAccessibleValueObject item) {
		boolean isInterNational = BillEnumCollection.BuySellType.OUT_BUY.VALUE.equals((item.getAttributeValue(IBillFieldGet.BUYSELLFLAG)));
		boolean isInterNation = isInterNational && ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR)).compareTo(item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR))!= 0;
		boolean notInterNation = !isInterNational && !(MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_CR), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR)).compareTo(item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)) == 0);
		return isInterNation||notInterNation;
	}

	/**
	 * 
	 * 同上 needReCalCulateCR
	 * @param item
	 * @return
	 */
	public static boolean needReCalCulateDE(CircularlyAccessibleValueObject item) {
		boolean isInterNational = BillEnumCollection.BuySellType.OUT_SELL.VALUE.equals((item.getAttributeValue(IBillFieldGet.BUYSELLFLAG)));
		boolean isInterNation = isInterNational && ((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE)).compareTo(item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE))!= 0;
		boolean notInterNation = !isInterNational && !(MathTool.add((UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_NOTAX_DE), (UFDouble) item.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE)).compareTo(item.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)) == 0);
		return isInterNation||notInterNation;
	}

	@SuppressWarnings("unused")
	private static UFDouble sumB(String Bkey, BaseItemVO[] items) {

		// 表头key金额
		UFDouble subValue = ArapCommonTool.getZero();

		for (BaseItemVO item : items) {
			if ((null != item) && (item.getStatus() != VOStatus.DELETED)) {
				try {
					subValue = subValue.add((item.getAttributeValue(Bkey) == null ? ArapCommonTool.getZero() : ((UFDouble) item.getAttributeValue(Bkey))));
				} catch (Exception e1) {
					ExceptionHandler.consume(e1);
				}
			}
		}
		return subValue;
	}

	/**
	 * 设置金额和汇率，但金额不重新计算。目前用于资产接口的单据
	 *
	 * @param bill
	 */
	public static void processMoneyWithoutCal(AggregatedValueObject bill) {

		CircularlyAccessibleValueObject[] items = bill.getChildrenVO();
		CircularlyAccessibleValueObject head = bill.getParentVO();
		String pk_billtype = head.getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString();
		String pk_org = head.getAttributeValue(IBillFieldGet.PK_ORG).toString();
		String pk_group = head.getAttributeValue(IBillFieldGet.PK_GROUP).toString();

		// 设置表体汇率
		for (CircularlyAccessibleValueObject item : items) {

			UFDouble[] rates = ArapBillCalUtil.getRate((String) item.getAttributeValue(IBillFieldGet.PK_CURRTYPE), pk_org, pk_group, item.getAttributeValue(IBillFieldGet.BILLDATE) == null ? new UFDate() : (UFDate) item
					.getAttributeValue(IBillFieldGet.BILLDATE), item.getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString());

			item.setAttributeValue(IBillFieldGet.RATE, rates[0]);
			item.setAttributeValue(IBillFieldGet.GROUPRATE, rates[1]);
			item.setAttributeValue(IBillFieldGet.GLOBALRATE, rates[2]);
		}

		UFDouble money = null;
		UFDouble local_money = null;
		UFDouble groupmoney = null;
		UFDouble globalmoney = null;

		if (IBillFieldGet.F0.equals(pk_billtype) || IBillFieldGet.F3.equals(pk_billtype)) {
			money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_DE, items);
			local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_DE, items);
			groupmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GROUPDEBIT, items);
			globalmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GLOBALDEBIT, items);
		} else if (IBillFieldGet.F1.equals(pk_billtype) || IBillFieldGet.F2.equals(pk_billtype)) {
			money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_CR, items);
			local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_CR, items);
			groupmoney = ArapBillPubUtil.sumB(IBillFieldGet.GROUPCREBIT, items);
			globalmoney = ArapBillPubUtil.sumB(IBillFieldGet.GLOBALCREBIT, items);
		}

		head.setAttributeValue(IBillFieldGet.MONEY, money);
		head.setAttributeValue(IBillFieldGet.LOCAL_MONEY, local_money);
		head.setAttributeValue(IBillFieldGet.GROUP_LOCAL_H, groupmoney);
		head.setAttributeValue(IBillFieldGet.GLOBAL_LOCAL_H, globalmoney);
		head.setAttributeValue(IBillFieldGet.RATE, items[0].getAttributeValue(IBillFieldGet.RATE));
		head.setAttributeValue(IBillFieldGet.GROUPRATE, items[0].getAttributeValue(IBillFieldGet.GROUPRATE));
		head.setAttributeValue(IBillFieldGet.GLOBALRATE, items[0].getAttributeValue(IBillFieldGet.GLOBALRATE));
	}

	/**
	 * 设置金额和汇率，但金额不重新计算。目前用于资产接口的单据
	 *
	 * @param bill
	 */
	public static void processMoneyOnlySum(AggregatedValueObject bill) {

		CircularlyAccessibleValueObject[] items = bill.getChildrenVO();
		CircularlyAccessibleValueObject head = bill.getParentVO();
		String pk_billtype = head.getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString();

		UFDouble money = null;
		UFDouble local_money = null;
		UFDouble groupmoney = null;
		UFDouble globalmoney = null;

		if (IBillFieldGet.E0.equals(pk_billtype) || IBillFieldGet.F0.equals(pk_billtype) || IBillFieldGet.F3.equals(pk_billtype)) {
			money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_DE, items);
			local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_DE, items);
			groupmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GROUPDEBIT, items);
			globalmoney = ArapBillPubUtil.sumB(IArapItemFieldVO.GLOBALDEBIT, items);
		} else if (IBillFieldGet.E1.equals(pk_billtype) || IBillFieldGet.F1.equals(pk_billtype) || IBillFieldGet.F2.equals(pk_billtype)) {
			money = ArapBillPubUtil.sumB(IArapItemFieldVO.MONEY_CR, items);
			local_money = ArapBillPubUtil.sumB(IArapItemFieldVO.LOCAL_MONEY_CR, items);
			groupmoney = ArapBillPubUtil.sumB(IBillFieldGet.GROUPCREBIT, items);
			globalmoney = ArapBillPubUtil.sumB(IBillFieldGet.GLOBALCREBIT, items);
		}

		head.setAttributeValue(IBillFieldGet.MONEY, money);
		head.setAttributeValue(IBillFieldGet.LOCAL_MONEY, local_money);
		head.setAttributeValue(IBillFieldGet.GROUP_LOCAL_H, groupmoney);
		head.setAttributeValue(IBillFieldGet.GLOBAL_LOCAL_H, globalmoney);
		head.setAttributeValue(IBillFieldGet.RATE, items[0].getAttributeValue(IBillFieldGet.RATE));
		head.setAttributeValue(IBillFieldGet.GROUPRATE, items[0].getAttributeValue(IBillFieldGet.GROUPRATE));
		head.setAttributeValue(IBillFieldGet.GLOBALRATE, items[0].getAttributeValue(IBillFieldGet.GLOBALRATE));
	}

	public static UFDouble sumB(String Bkey, CircularlyAccessibleValueObject[] items) {

		// 表头key金额
		UFDouble subValue = ArapCommonTool.getZero();

		for (CircularlyAccessibleValueObject item : items) {
			if ((null != item) && (item.getStatus() != VOStatus.DELETED)) {
				try {
					subValue = subValue.add((item.getAttributeValue(Bkey) == null ? ArapCommonTool.getZero() : ((UFDouble) item.getAttributeValue(Bkey))));
				} catch (Exception e1) {
					ExceptionHandler.consume(e1);
				}
			}
		}
		return subValue;
	}

	// /**
	// * @deprecated instead of FinanceBillCodeUtils
	// * nc.bs.cmp.util.CmpBillPubUtil.getBillCodeUtil
	// * (AggregatedValueObject... aggvos) liaobx 2010-6-27 11:40:00
	// */
	// @Deprecated
	// @SuppressWarnings("deprecation")
	// public static FinanceBillCodeUtils getBillCodeUtil(BaseBillVO parent) {
	// FinanceBillCodeUtils util = null;
	// FinanceBillCodeInfo info = new FinanceBillCodeInfo(
	// IArapBillFieldVO.PK_BILLTYPE, IArapBillFieldVO.BILLNO,
	// IArapBillFieldVO.PK_GROUP, IArapBillFieldVO.PK_ORG, parent
	// .getTableName());
	// util = new FinanceBillCodeUtils(info);
	// return util;
	// }

	public static String getDataSource() {
		return InvocationInfoProxy.getInstance().getUserDataSource();
	}

	public static FinanceBillCodeUtils getBillCodeUtil(AggregatedValueObject... aggvos) {
		AggregatedValueObject aggvo = aggvos[0];
		String pk_billtype = IBillFieldGet.PK_BILLTYPE;
		String billno = IBillFieldGet.BILLNO;
		String pk_group = IBillFieldGet.PK_GROUP;
		String pk_org = IBillFieldGet.PK_ORG;
		// 从元数据中获取tableName
		SuperVO superVO = (SuperVO) aggvo.getParentVO();
		IVOMeta meta = superVO.getMetaData();
		String tableName = null;
		if (meta != null) {
			IColumnMeta column = meta.getPrimaryAttribute().getColumn();
			if (column != null) {
				tableName = column.getTable().getName();
			}
		}
		return new FinanceBillCodeUtils(new FinanceBillCodeInfo(pk_billtype, billno, pk_group, pk_org, tableName));
	}

	/**
	 * 更新时间
	 *
	 * @param bill
	 * @throws BusinessException
	 */
	public static void updateTS(BaseAggVO bill) throws BusinessException {
		AggregatedValueObject aggvo = ArapBillPubUtil.getMDQueryService().queryBillOfVOByPK(bill.getClass(), bill.getParentVO().getPrimaryKey(), false);
		bill.setParentVO(aggvo.getParentVO());
	}

	/**
	 * 批量更新时间
	 *
	 * @param bills
	 * @throws BusinessException
	 */
	public static void updateBatchTS(AggregatedValueObject... aggbills) throws BusinessException {
		List<String> pks = new ArrayList<String>();
		if ((null == aggbills) || (aggbills.length == 0)) {
			return;
		}
		for (AggregatedValueObject bill : aggbills) {
			pks.add(bill.getParentVO().getPrimaryKey());
		}
		Collection<?> aggvos = ArapBillPubUtil.getMDQueryService().queryBillOfVOByPKs(aggbills[0].getClass(), pks.toArray(new String[0]), false);
		AggregatedValueObject[] aggvostmp = (AggregatedValueObject[]) aggvos.toArray(new AggregatedValueObject[aggvos.size()]);
		for (int i = 0; i < aggbills.length; i++) {
			aggbills[i].setParentVO(aggvostmp[i].getParentVO());
		}
	}

	/**
	 * 通过单据大类判断是否应该欧结算信息
	 *
	 * @param head
	 * @return
	 */
	public static boolean hasSettleInfo(BaseBillVO head) {
		String billClass = head.getBillclass();
		return ("fk".equals(billClass) || "sk".equals(billClass)) && !UFBoolean.TRUE.equals(head.getIsinit());

	}

	public static ResultSetProcessor getResultSetProcessor() {
		ResultSetProcessor processor = new ResultSetProcessor() {
			private static final long serialVersionUID = 1L;

			public Object handleResultSet(ResultSet rs) throws SQLException {
				Map<String, Integer> result = new HashMap<String, Integer>();
				while (rs.next()) {
					result.put(rs.getString(1), rs.getInt(2));
				}
				return result;
			}

		};
		return processor;
	}

	/**
	 *是否是arap的单据类型
	 * <p>
	 *
	 * @author liaobx 2010-8-31 上午10:44:11
	 * @since 6.0
	 * @version 6.0
	 * @param top_pk_billtype
	 * @param pk_billtype
	 * @return
	 */
	public static boolean isFromArap(BaseBillVO vo,BaseItemVO item) {
		if(vo.getSrc_syscode() == FromSystem.XTDJ.VALUE.intValue())
			return false;

		String top_pk_billtype=item.getTop_billtype();
		String pk_billtype=item.getPk_billtype();
		
		if (StringUtil.isEmpty(top_pk_billtype)) {
			return false;
		}
		if (StringUtil.isEmpty(pk_billtype)) {
			return false;
		}
//		if (pk_billtype.equals(top_pk_billtype)) {// 红冲
//			return false;
//		}
		if ((pk_billtype + top_pk_billtype).equals("F0F1")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F1F0")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F2F3")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F3F2")) {// 协同
			//return false;直接借记退回自动核销的场景，需要等同拉单的处理，协同的情况通过表头的来源系统做判断
		}
		return isArapBilltype(top_pk_billtype);// 拉单
	}

	/**
	 *是否是arap的单据类型
	 * <p>
	 * 废弃， 不再使用， 使用上面的方法判断 isFromArap(BaseBillVO vo,BaseItemVO item)
	 * @author liaobx 2010-8-31 上午10:44:11
	 * @since 6.0
	 * @version 6.0
	 * @param top_pk_billtype
	 * @param pk_billtype
	 * @return 
	 */
	@Deprecated
	public static boolean isFromArap(String top_pk_billtype, String pk_billtype) {
		if (StringUtil.isEmpty(top_pk_billtype)) {
			return false;
		}
		if (StringUtil.isEmpty(pk_billtype)) {
			return false;
		}
		if (pk_billtype.equals(top_pk_billtype)) {// 红冲
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F0F1")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F1F0")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F2F3")) {// 协同
			return false;
		}
		if ((pk_billtype + top_pk_billtype).equals("F3F2")) {// 协同
			return false;
		}
		return isArapBilltype(top_pk_billtype);// 拉单
	}
	/**
	 * @param pk_billtype
	 */
	public static boolean isArapBilltype(String pk_billtype) {
		if (StringUtil.isEmpty(pk_billtype)) {
			return false;
		}
		String clone = pk_billtype.trim();
		if (IBillFieldGet.F0.equals(clone) || IBillFieldGet.F1.equals(clone) || IBillFieldGet.F2.equals(clone) || IBillFieldGet.F3.equals(clone)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是应收系统
	 *
	 * @param pk_billtype
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isARSysBilltype(String pk_billtype) {
		if (StringUtil.isEmpty(pk_billtype)) {
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0469")/*
			 * @res
			 * "单据类型为空 "
			 */);
		}
		String clone = pk_billtype.trim();
		if (IBillFieldGet.F0.equals(clone) || IBillFieldGet.F2.equals(clone) || IBillFieldGet.E0.equals(clone) || IBillFieldGet.F1C.equals(clone) || IBillFieldGet.F3C.equals(clone)) {
			return true;
		} else if (IBillFieldGet.F1.equals(clone) || IBillFieldGet.F3.equals(clone) || IBillFieldGet.E1.equals(clone) || IBillFieldGet.F0S.equals(clone) || IBillFieldGet.F2S.equals(clone)) {
			return false;
		}
		throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0470")/*
		 * @res
		 * "单据类型错误, 不是收付单据"
		 */);
	}

	/**
	 * 借贷方向
	 *
	 * @param pk_billtype
	 * @return 如果应收(包括暂估)/付款 借方(true), 应付(包括暂估)/收款 贷方(false)
	 * @throws BusinessException
	 */
	public static boolean getDirection(String pk_billtype) throws BusinessException {
		if (StringUtil.isEmpty(pk_billtype)) {
			ExceptionHandler.createAndThrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0469")/**
			 * @res
			 *      "单据类型为空 "
			 */
			);
		}
		String clone = pk_billtype.trim();
		if (IBillFieldGet.F0.equals(clone) || IBillFieldGet.E0.equals(clone) || IBillFieldGet.F3.equals(clone)) {
			return true;
		} else if (IBillFieldGet.F1.equals(clone) || IBillFieldGet.E1.equals(clone) || IBillFieldGet.F2.equals(clone)) {
			return false;
		}
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0470")/*
		 * @res
		 * "单据类型错误, 不是收付单据"
		 */);
	}

	/**
	 * 判断是贷方还是借方
	 *
	 * @param billType
	 * @return 0 贷方；1借方；9未知；
	 * @author wuzhwa
	 * @since v6.0
	 */
	public static int isDf(String billType) {
		/**
		 * 目前涉及内容: 借方：F0，F3 贷方：F1，F2
		 */
		if (billType.equals(IBillFieldGet.F1) || billType.equals(IBillFieldGet.E1) || billType.equals(IBillFieldGet.F2)) {
			return 0;
		} else if (billType.equals(IBillFieldGet.F0) || billType.equals(IBillFieldGet.E0) || billType.equals(IBillFieldGet.F3)) {
			return 1;
		}

		return 9;
	}

	public static BaseItemVO translate(TermVO termvo, BaseItemVO srcvo) {
		BaseItemVO clone = (BaseItemVO) srcvo.clone();
		clone.setGlobalbalance(termvo.getGlobalbalance());
		clone.setGlobalcrebit(termvo.getGlobalcredit());
		clone.setGlobaldebit(termvo.getGlobaldebit());
		clone.setGroupbalance(termvo.getGroupbalance());
		clone.setGroupcrebit(termvo.getGroupcredit());
		clone.setGroupdebit(termvo.getGroupdebit());
		clone.setLocal_money_bal(termvo.getLocal_money_bal());
		clone.setLocal_money_cr(termvo.getLocal_money_cr());
		clone.setLocal_money_de(termvo.getLocal_money_de());
		clone.setMoney_bal(termvo.getMoney_bal());
		clone.setMoney_cr(termvo.getMoney_cr());
		clone.setMoney_de(termvo.getMoney_de());
		clone.setQuantity_bal(termvo.getQuantity_bal());
		clone.setQuantity_cr(termvo.getQuantity_cr());
		clone.setQuantity_de(termvo.getQuantity_de());

		clone.setLocal_notax_cr(null);
		clone.setLocal_notax_de(null);
		clone.setLocal_tax_cr(null);
		clone.setLocal_tax_de(null);

		return null;
	}

	public static String[] getBillPKs(AggregatedValueObject[] bills) throws BusinessException {
		List<String> ls = new ArrayList<String>();
		for (AggregatedValueObject bill : bills) {
			ls.add(bill.getParentVO().getPrimaryKey());
		}
		return ls.toArray(new String[ls.size()]);
	}

	public static String[] getBillPKs(BaseAggVO bill) {
		List<String> ls = new ArrayList<String>();
		ls.add(bill.getPrimaryKey());
		return ls.toArray(new String[ls.size()]);
	}

	/**
	 * 返回元数据查询服务对象
	 */
	private static IMDPersistenceQueryService getMDQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}

	public static IDataPermissionPubService getDataPermissionQryService() {
		if (ArapBillPubUtil.dataPermissionQryService == null) {
			ArapBillPubUtil.dataPermissionQryService = (IDataPermissionPubService) NCLocator.getInstance().lookup(IDataPermissionPubService.class.getName());
		}
		return ArapBillPubUtil.dataPermissionQryService;
	}

	public static UFDate getArapCreateDate(String pk_org, String pk_billtype) {
		boolean isAr = false;
		if (!StringUtil.isEmpty(pk_billtype)) {
			isAr = ArapBillPubUtil.isARSysBilltype(pk_billtype);
		}

		// 组织变化， 修改单据
		UFDate billdate = ArapBillPubUtil.getArapCreateDate(isAr, pk_org);

		return billdate;
	}

	/**
	 * 取得启动日期 用于期初单据
	 *
	 * @param isAr
	 *            TRUE取AR日期 FALSE 取AP日期
	 * @param pk_org
	 *            制定组织
	 * @author miaohl
	 * @return
	 */
	public static UFDate getArapCreateDate(boolean isAr, String pk_org) {
		UFDate ufDate = getOrgModuleperiodDate(isAr, pk_org);
		UFDate dateBefore = null;
		if(ufDate != null){
			dateBefore = ufDate.getDateBefore(1);
		}
		return dateBefore;
	}

	/**
	 * 获取组织根据组织的启用日期（日期）
	 */
	private static Map<String, UFDate> dateMap = new HashMap<String, UFDate>();
	public static UFDate getOrgModuleperiodDate(boolean isAr, String pk_org) {
		dateMap.clear();
		String key = String.valueOf(isAr)+"_"+pk_org;
		UFDate retDate = dateMap.get(key);
		if(null != retDate){
			return retDate;
		}
		
		String createDate = null;
		String moduleCode = ArapConstant.AP_MODULE_ID;
		if (isAr) {
			moduleCode = ArapConstant.AR_MODULE_ID;
		}
		IOrgUnitPubService orgUnitPubService = NCLocator.getInstance().lookup(IOrgUnitPubService.class);
		String createdrt = null;
		try {
			createdrt = orgUnitPubService.getOrgModulePeriodByOrgIDAndModuleID(pk_org, moduleCode);

			if (createdrt != null) {
				String initEnabledYear = createdrt.substring(0, 4); /* 启用年 */
				String initEnabledMonth = createdrt.substring(5, 7); /* 启用月 */
				AccountCalendar instanceByPkOrg = AccountCalendar.getInstanceByPk_org(pk_org);
				instanceByPkOrg.set(initEnabledYear, initEnabledMonth);
				UFDate createDate2 = instanceByPkOrg.getMonthVO().getBegindate();
				dateMap.put(key, createDate2);
				return createDate2;
			}
		} catch (BusinessException e) {
			ExceptionHandler.consume(e);

			if (createdrt != null) {
				createDate = createdrt.trim() + "-01";
			}
		}
		if (null == createDate) {
			return null;
			// 这里抛异常会导致组织不可编辑 by wangdongd
		}
		UFDate ufDate = new UFDate(createDate);
		return ufDate;
	}

	/**
	 *作用: <b>取得启动日期 用于期初单据(获取登陆用户的默认组织, 如果在组织为空, 则返回当前UFDate)</b></br>
	 * 命名解释:<b></b></br>
	 *
	 * @author liaobx 2010-8-11 下午06:54:14
	 * @since 6.0
	 * @param isAr
	 * @return
	 */
	public static UFDate getArapCreateDate(boolean isAr) {
		String pk_org = null;
		try {
			pk_org = OrgSettingAccessor.getDefaultOrgUnit();
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		if (pk_org == null) {
			pk_org = IBillFieldGet.PK_GLOBAL;
		}

		return ArapBillPubUtil.getArapCreateDate(isAr, pk_org);
	}

	/**
	 * 填充期初暂估单据日期为启用日期
	 *
	 * @param aggvos
	 */
	public static void fillInitEstInfo(AggregatedValueObject[] aggvos) {
		for (AggregatedValueObject vo : aggvos) {
			UFBoolean isInit = vo.getParentVO().getAttributeValue(IBillFieldGet.ISINIT) == null ? UFBoolean.FALSE : (UFBoolean) vo.getParentVO().getAttributeValue(IBillFieldGet.ISINIT);
			if (!isInit.booleanValue())
				continue;
			if (null == vo.getParentVO().getAttributeValue(IBillFieldGet.BILLCLASS))
				continue;
			String billclass = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.BILLCLASS);

			String pk_org = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.PK_ORG);
			UFDate date = ArapBillPubUtil.getArapCreateDate(IBillFieldGet.ZS.equals(billclass), pk_org);
			
			if(vo.getParentVO().getAttributeValue(IBillFieldGet.BILLDATE)==null || ((UFDate)vo.getParentVO().getAttributeValue(IBillFieldGet.BILLDATE)).after(date)){
				vo.getParentVO().setAttributeValue(IBillFieldGet.BILLDATE, date);
				for (CircularlyAccessibleValueObject item : vo.getChildrenVO()) {
					item.setAttributeValue(IBillFieldGet.BILLDATE, date);
				}
			}
			
			for (CircularlyAccessibleValueObject item : vo.getChildrenVO()) {
				if(item.getAttributeValue(IBillFieldGet.BUSIDATE)==null  || ((UFDate)item.getAttributeValue(IBillFieldGet.BUSIDATE)).after(date)){
					item.setAttributeValue(IBillFieldGet.BUSIDATE, date);
				}
			}
		}
	}

	/**
	 *作用: <b> 判断是否是拥有待做事务的人</b></br> 适用范围:</br> Ⅰ:<b> </b></br> Ⅱ:<b> </b></br>
	 * Ⅲ :<b> </b></br> Ⅳ:<b> </b></br> 命名解释:<b></b></br>
	 *
	 * @author liaobx 2010-8-11 下午01:55:06
	 * @since 6.0
	 * @param aggvos
	 * @return
	 */
	public static boolean checkIsMan(AggregatedValueObject[] aggvos) {
		if (ArrayUtils.isEmpty(aggvos)) {
			return false;
		}
		AggregatedValueObject aggvo = aggvos[0];
		if (aggvo != null) {
			CircularlyAccessibleValueObject parentVO = aggvo.getParentVO();
			try {
				String pk_bill = parentVO.getPrimaryKey();
				String pk_tradetype = (String) parentVO.getAttributeValue(IBillFieldGet.PK_TRADETYPE);
				String userId = InvocationInfoProxy.getInstance().getUserId();
				if (NCLocator.getInstance().lookup(IPFWorkflowQry.class).isCheckman(pk_bill, pk_tradetype, userId)) {
					return true;
				}
			} catch (BusinessException e) {
				ExceptionHandler.consume(e);
			}
		}
		return false;
	}

	/**
	 * modified by liaobx 2010-7-26 13:34:00 转换objects到aggvos
	 *
	 * @param values
	 *            ojbect数组
	 * @return 转换objects到aggvos
	 */
	public static AggregatedValueObject[] convertObjetcsToAggs(Object... values) {
		AggregatedValueObject[] aggs = new AggregatedValueObject[values.length];
		// int index = 0;
		// for (Object o : values) {
		// aggs[index++] = (AggregatedValueObject) o;
		// }
		//
		System.arraycopy(values, 0, aggs, 0, aggs.length);
		return aggs;
	}

	public static void dealRedFlag(AggregatedValueObject[] bills) {
		for (AggregatedValueObject bill : bills) {
			String pk_billType = bill.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString();
			Object top_billType = bill.getChildrenVO()[0].getAttributeValue(IBillFieldGet.TOP_BILLTYPE);
			if (null != top_billType && pk_billType.equals(top_billType)) {
				String pk_bill = bill.getChildrenVO()[0].getAttributeValue(IBillFieldGet.TOP_BILLID).toString();
				try {
					NCLocator.getInstance().lookup(IArapBillService.class).updateIsredFlag(ArapBillType2TableMapping.getParentTable(pk_billType), ArapBillType2TableMapping.getParentTablePK(pk_billType), pk_bill);
				} catch (BusinessException e) {
					ExceptionUtils.wrappException(e);
				}
			}
		}

	}

	/**
	 * 补充交易类型为空 设置未默认
	 *
	 * @param bills
	 */
	public static void fillTradeTypeInfo(AggregatedValueObject[] bills) {
		for (AggregatedValueObject bill : bills) {
			String pk_tradetype;
			Integer src_syscode = (Integer) bill.getParentVO().getAttributeValue(IBillFieldGet.SRC_SYSCODE);
			if (src_syscode != null) {
				if (null == bill.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPE)) {
					// 为来自报销管理的单据，调用接口对应关系：

					if (src_syscode.intValue() == BillEnumCollection.FromSystem.WSBX.VALUE.intValue()) {
						IPFBillItfDef itfDefService = NCLocator.getInstance().lookup(IPFBillItfDef.class);
						BillItfDefVO defVO = new BillItfDefVO();
						defVO.setSrc_billtype("264X");
						defVO.setDest_billtype((String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE));
						defVO.setPk_group((String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_GROUP));
						try {
							BillItfDefVO[] billItfDefVOs = itfDefService.getBillItfDef(defVO);
							if (!ArrayUtils.isEmpty(billItfDefVOs)) {
								bill.getParentVO().setAttributeValue(IBillFieldGet.PK_TRADETYPE, billItfDefVOs[0].getDest_transtype());
							}
						} catch (BusinessException e) {
							Log.getInstance("ArapBillPubUtil").error(e);
						}
					}
				}

				if (src_syscode.intValue() == BillEnumCollection.FromSystem.CT.VALUE.intValue()) {

					IPFBillItfDef itfDefService = NCLocator.getInstance().lookup(IPFBillItfDef.class);
					BillItfDefVO defVO = new BillItfDefVO();
					defVO.setSrc_billtype(ArapBillDealVOConsts.BILLTYPE_Z2);
					defVO.setDest_billtype((String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE));
					defVO.setPk_group((String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_GROUP));
					try {
						BillItfDefVO[] billItfDefVOs = itfDefService.getBillItfDef(defVO);
						if (!ArrayUtils.isEmpty(billItfDefVOs)) {
							bill.getParentVO().setAttributeValue(IBillFieldGet.PK_TRADETYPE, billItfDefVOs[0].getDest_transtype());
						}
					} catch (BusinessException e) {
						Log.getInstance("ArapBillPubUtil").error(e);
					}
				}

				pk_tradetype = (String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPE);
				if (bill.getChildrenVO() != null && (StringUtils.isEmpty(pk_tradetype) || "~".equals(pk_tradetype))) {
					Set<String> childScomments = new HashSet<String>();
					for (CircularlyAccessibleValueObject item : bill.getChildrenVO()) {
						pk_tradetype = (String) item.getAttributeValue(IBillFieldGet.PK_TRADETYPE);
						if (StringUtils.isEmpty(pk_tradetype) || "~".equals(pk_tradetype)) {
							continue;
						}
						childScomments.add(pk_tradetype);
					}

					Iterator<String> iterator = childScomments.iterator();
					if (iterator.hasNext()) {
						bill.getParentVO().setAttributeValue(IBillFieldGet.PK_TRADETYPE, iterator.next());
					}
				}

				//设置默认的交易类型和单据类型,后续修改为多个交易类型和单据类型的默认值（单据大类，所属系统等）
				if(bill.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPE)==null){
					bill.getParentVO().setAttributeValue(IBillFieldGet.PK_TRADETYPE, ArapBillTypeInfo.getInstance((BaseAggVO)bill).getDefaultTransType());
				}

				if (null != bill.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPE)) {
					pk_tradetype = (String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPE);

					BilltypeVO billTypevo = PfDataCache.getBillType(pk_tradetype);
					if (billTypevo == null) {
						String pk_group = (String) bill.getParentVO().getAttributeValue(IBillFieldGet.PK_GROUP);
						billTypevo = PfDataCache.getBillTypeInfo(pk_group, pk_tradetype);
					}
					bill.getParentVO().setAttributeValue(IBillFieldGet.PK_TRADETYPEID, billTypevo.getPk_billtypeid());
					for (BaseItemVO vo : (BaseItemVO[]) bill.getChildrenVO()) {
						vo.setAttributeValue2(IBillFieldGet.PK_BILLTYPE, bill.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString());
						vo.setAttributeValue2(IBillFieldGet.PK_TRADETYPE, pk_tradetype);
						vo.setAttributeValue2(IBillFieldGet.PK_TRADETYPEID, billTypevo.getPk_billtypeid());
					}

				}

			}

		}
	}

	public static void fillTradeTypeInfo(AggregatedValueObject bill) {
		ArapBillPubUtil.fillTradeTypeInfo(new AggregatedValueObject[] { bill });
	}

	public static boolean isRedBill(AggregatedValueObject bill) {
		for (CircularlyAccessibleValueObject item : bill.getChildrenVO()) {
			if (null == item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE))
				return false;
			if (item.getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(item.getAttributeValue(IBillFieldGet.TOP_BILLTYPE)))
				return true;
		}
		return false;
	}

	public static void fillNeedFlds(AggregatedValueObject destVO) {
		if (null == destVO.getParentVO().getAttributeValue(IBillFieldGet.EFFECTSTATUS)) {
			destVO.getParentVO().setAttributeValue(IBillFieldGet.EFFECTSTATUS, BillEnumCollection.InureSign.NOINURE.VALUE);
		}
		if (null == destVO.getParentVO().getAttributeValue(IBillFieldGet.BILLSTATUS)) {
			destVO.getParentVO().setAttributeValue(IBillFieldGet.BILLSTATUS, BillEnumCollection.BillSatus.Save.VALUE);
		}
		if (IBillFieldGet.F2.equals(destVO.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE)) || IBillFieldGet.F3.equals(destVO.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE))) {
			for (CircularlyAccessibleValueObject item : destVO.getChildrenVO()) {
				if (null == item.getAttributeValue(IBillFieldGet.PREPAY)) {
					item.setAttributeValue(IBillFieldGet.PREPAY, BillEnumCollection.PreSigns.notPre.VALUE);
				}
			}
		}

		BaseBillVO headvo = (BaseBillVO) destVO.getParentVO();
		if (headvo.getSyscode() == null || headvo.getSyscode().toString().trim().length() == 0 || headvo.getSrc_syscode() == 0) {
			if (ArapBillPubUtil.isARSysBilltype(headvo.getPk_billtype())) {
				headvo.setSyscode(FromSystem.AR.VALUE);
			} else {
				headvo.setSyscode(FromSystem.AP.VALUE);
			}
		}
	}

	/**
	 * 设置表体第一行的一些field数值， 到表头显示
	 *
	 * @param aggVOs
	 *            聚合VO
	 * @param items
	 *            要被在表头显示的fieldcode ， 请使用IBillFieldGet的常量
	 */
	public static void refreshChildVO2HeadVO(AggregatedValueObject... aggVOs) {
		if (null == aggVOs || aggVOs.length == 0)
			return;

		Set<String> escape = new HashSet<String>();
		escape.add(IBillFieldGet.RATE);

		for (int i = 0; i < aggVOs.length; i++) {
			AggregatedValueObject aggVO = aggVOs[i];
			if (aggVO == null) {
				continue;
			}
			CircularlyAccessibleValueObject parentVO = aggVO.getParentVO();
			CircularlyAccessibleValueObject[] childrenVO = aggVO.getChildrenVO();
			CircularlyAccessibleValueObject childVO = null != childrenVO && childrenVO.length > 0 ? childrenVO[0] : null;
			if(childVO != null){
				for (Entry<String, String> entry : getHTOBMap().entrySet()) {
					String headKey = entry.getKey();
					String bodyKey = entry.getValue();
					Object atrrValue = childVO.getAttributeValue(bodyKey);
					if (atrrValue == null || StringUtils.isEmpty(atrrValue.toString())) {
						continue;
					} else {
						parentVO.setAttributeValue(headKey, atrrValue);
					}
					
				}
			}
			if(childrenVO!=null){
				UFDouble money=UFDouble.ZERO_DBL;
				UFDouble local_money=UFDouble.ZERO_DBL;
				for (CircularlyAccessibleValueObject vo : childrenVO) {
					UFDouble money_de = (vo.getAttributeValue(IBillFieldGet.MONEY_DE)==null?UFDouble.ZERO_DBL:(UFDouble) vo.getAttributeValue(IBillFieldGet.MONEY_DE));
					UFDouble money_cr = (vo.getAttributeValue(IBillFieldGet.MONEY_CR)==null?UFDouble.ZERO_DBL:(UFDouble) vo.getAttributeValue(IBillFieldGet.MONEY_CR));
					money=money.add(money_de.add(money_cr));
					UFDouble local_money_de = (vo.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE)==null?UFDouble.ZERO_DBL:(UFDouble) vo.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE));
					UFDouble local_money_cr = (vo.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR)==null?UFDouble.ZERO_DBL:(UFDouble) vo.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR));
					local_money=local_money.add(local_money_de.add(local_money_cr));
				}
				parentVO.setAttributeValue(IBillFieldGet.MONEY, money);
				parentVO.setAttributeValue(IBillFieldGet.LOCAL_MONEY, local_money);	
			}
		}
	}

	private static volatile Map<String, String> htobMap;

	/**
	 * @return
	 */
	private static Map<String, String> getHTOBMap() {
		if (htobMap == null) {
			htobMap = new ArapH2BMapping().getHTOBMap();
		}
		return htobMap;
	}

	/**
	 * 校验财务组织是否停用
	 *
	 * @param bills
	 */
	public static void checkFinanceOrgEnable(AggregatedValueObject[] bills) {
		String[] financeogIDs = new String[bills.length];
		int i = 0;
		for (AggregatedValueObject bill : bills) {
			financeogIDs[i] = ((BaseAggVO) bill).getHeadVO().getPk_org();
			i++;
		}
		FinanceOrgVO[] financeOrgVOs = null;
		try {
			HashSet<String> set = new HashSet<String>(Arrays.asList(financeogIDs));
			financeOrgVOs = NCLocator.getInstance().lookup(IFinanceOrgQryService.class).getFinanceOrgVOs(set.toArray(new String[0]));
			if(null == financeOrgVOs){
				ExceptionHandler.createAndThrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0","02006pub-0277")/*@res "财务组织为空，不能操作单据"*/);
			}
			if (set.size() != financeOrgVOs.length) {
				ExceptionHandler.createAndThrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0","02006arappub0523-0015")/*@res "财务组织已停用"*/);
			} else {
				for (FinanceOrgVO financeOrgVO : financeOrgVOs) {
					if (2 != financeOrgVO.getEnablestate()) {
						ExceptionHandler.createAndThrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0","02006arappub0523-0016")/*@res "财务组织："*/
							+ financeOrgVO.getName() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0","02006arappub0523-0017")/*@res "已停用"*/);
					}
				}
			}
		} catch (BusinessException e) {
			ExceptionHandler.handleRuntimeException(e);
		}
	}
	/**
	 * 批量设置上下游单据的档案过滤
	 * @param destVOs
	 * @throws BusinessException 
	 */
	public static AggregatedValueObject[] resetDestVODoc(AggregatedValueObject[] destVOs) throws BusinessException{
		
		if (ArrayUtils.isEmpty(destVOs)) {
			return destVOs;
		}
		BaseAggVO billVo = (BaseAggVO) destVOs[0];
		String pk_group = billVo.getHeadVO().getPk_group();
		// 获取表体自定义属性
		UserdefitemVO[] buserdefitemVOs = null;
		// 获取表头自定义属性
		UserdefitemVO[] tuserdefitemVOs = null;
		try {
			buserdefitemVOs = NCLocator.getInstance().lookup(IUserdefitemQryService.class).queryUserdefitemVOsByUserdefruleCode(IBillFieldGet.ARAP_B, pk_group);
			tuserdefitemVOs = NCLocator.getInstance().lookup(IUserdefitemQryService.class).queryUserdefitemVOsByUserdefruleCode(IBillFieldGet.ARAP_H, pk_group);
		} catch (BusinessException e) {
			ExceptionHandler.handleRuntimeException(e);
		}
         //按pk_org分组
		Map<String ,List<AggregatedValueObject> > orgMap =new HashMap<String, List<AggregatedValueObject>>();
		for(AggregatedValueObject destVo : destVOs){
			String pk_org = ((BaseAggVO) destVo).getHeadVO().getPk_org();
			if(orgMap.get(pk_org)==null){
				orgMap.put(pk_org, new ArrayList<AggregatedValueObject>());
			}
			orgMap.get(pk_org).add(destVo);
		}
		//根据pk_org批量处理档案过滤
		for(String pk_org : orgMap.keySet()){
			// 表头自定义属性不为空，则查询引用自定义档案，根据级别过滤
			if (tuserdefitemVOs != null && tuserdefitemVOs.length > 0) {
				setDefDoc(tuserdefitemVOs, orgMap.get(pk_org),pk_org,pk_group, 0);
			}
			// 表体自定义属性不为空，则查询引用自定义档案，根据级别过滤
			if (buserdefitemVOs != null && buserdefitemVOs.length > 0) {
				setDefDoc(buserdefitemVOs, orgMap.get(pk_org),pk_org,pk_group, 1);
			}
			//flag 0 ：过滤表头 1：过滤表体  2：过滤单据（表头表体）
			resetRecpaytype(orgMap.get(pk_org), pk_org, pk_group,IBillFieldGet.PK_RECPAYTYPE,1);
			resetSupplies(orgMap.get(pk_org), pk_org, pk_group, IBillFieldGet.SUPPLIER, 2);
			resetCustomers(orgMap.get(pk_org), pk_org, pk_group, IBillFieldGet.CUSTOMER, 2);
			resetCustomers(orgMap.get(pk_org), pk_org, pk_group, IBillFieldGet.ORDERCUBASDOC, 2);
//			resetCashflow(orgMap.get(pk_org), pk_org, pk_group,IBillFieldGet.CASHITEM,2);
//			resetInoutBusiClass(orgMap.get(pk_org), pk_org, pk_group,IBillFieldGet.PK_SUBJCODE,2);
//			resetFundPlan(orgMap.get(pk_org), pk_org, pk_group,IBillFieldGet.BANKROLLPROJET,2);
//			resetDept(orgMap.get(pk_org), pk_org, pk_group, IBillFieldGet.PK_DEPTID_V, 2);
//			resetPsndoc(orgMap.get(pk_org), pk_org, pk_group, IBillFieldGet.PK_PSNDOC, 2);
		}
		return destVOs;
	}
	
	public static void resetRecpaytype(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef){
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null&&pksList.size()>0){
			String pks[] = pksList.toArray(new String[pksList.size()]);
			RecpaytypeVO[] result =null;
			List<String> list = new ArrayList<String>();
			try {
				result = NCLocator.getInstance().lookup(IRecpaytypeQueryService.class).queryRecpaytype(" dr=0 and "+SqlUtils.getInStr(IBillFieldGet.PK_RECPAYTYPE, pks, true));
				if(result!=null){
					for(RecpaytypeVO vo:result){
						list.add(vo.getPk_recpaytype());
					}
				}
				resetDocValue( voList, defField, 1, list);
				
				List<String> pksListNew = getPksLists( voList, defField, isHeadDef);
				
				if(pksList.size()!=pksListNew.size()){
					throw new BusinessException("收付款单单据表体收付款业务类型不能为空!");
				}
			} catch (Exception e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	
	/**
	 * -----------------------------以下档案批量过滤-------------------------------------
	 */
	public static String DOC = "def";
	public static String BD_DEFDOC = "bd_defdoc";
	public static String BDMETADATA_DEPT_V = "b26fa3cb-4087-4027-a3b6-c83ab2a086a9";
	public static void setDefDoc(UserdefitemVO[] userdefitemVOs,List<AggregatedValueObject>  voList,String pk_org,String pk_group,int isHeadDef) throws BusinessException{
		//获得自定义档案的mdclass
		List<String> pksList =new ArrayList<String>(userdefitemVOs.length);
		for(UserdefitemVO buserdefitemVO :userdefitemVOs){
			pksList.add(buserdefitemVO.getClassid());
		}
		Map<String, String> map = null;
		try {
			map = NCLocator.getInstance().lookup(IArapBillPubQueryService.class).getClassMap(pksList);
		} catch (BusinessException e) {
			ExceptionHandler.handleRuntimeException(e);
		}
		//判断自定义档案类型,过滤字段值
		for(UserdefitemVO buserdefitemVO :userdefitemVOs){
			for(Entry<String, String> businessEntity : map.entrySet()){
				if(buserdefitemVO.getClassid().equals(businessEntity.getKey())){
					if(businessEntity.getValue()==null || "".equals(businessEntity.getValue())){ //自定义属性引用基本类型组件（Defaulttablename为空） 则不过滤
					}else if(BD_DEFDOC.equals(businessEntity.getValue())){ //自定义属性引用自定义档案 需要处理组织级别
							ArapBillPubUtil.resetBaseDefDoc(voList, pk_org, pk_group, DOC+buserdefitemVO.getPropindex(),isHeadDef);	
					}
//					else if(IBDMetaDataIDConst.FUNDPLAN.equals(businessEntity.getKey())){ //过滤资金计划项目
//						ArapBillPubUtil.resetFundPlan(voList, pk_org, pk_group,DOC+buserdefitemVO.getPropindex(),isHeadDef);
//					}else if(IBDMetaDataIDConst.CASHFLOW.equals(businessEntity.getKey())){ //过滤现金流量项目可见
//						ArapBillPubUtil.resetCashflow(voList, pk_org, pk_group,DOC+buserdefitemVO.getPropindex(),isHeadDef);
//					}else if(IBDMetaDataIDConst.INOUTBUSICLASS.equals(businessEntity.getKey())){ //过滤收支项目可见
//						ArapBillPubUtil.resetInoutBusiClass(voList, pk_org, pk_group,DOC+buserdefitemVO.getPropindex(),isHeadDef);
//					}else if(BDMETADATA_DEPT_V.equals(businessEntity.getKey())){//部门
//						ArapBillPubUtil.resetDept(voList, pk_org, pk_group, DOC+buserdefitemVO.getPropindex(), isHeadDef);
//					}else if(IBDMetaDataIDConst.PSNDOC.equals(businessEntity.getKey())){//业务员
//						ArapBillPubUtil.resetPsndoc(voList, pk_org, pk_group, DOC+buserdefitemVO.getPropindex(), isHeadDef);
//					}
					break;	
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void resetBaseDefDoc(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef) throws BusinessException{
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null &&pksList.size()>0){
			String condition = " AND ( (PK_ORG ='"+pk_org+"' AND PK_GROUP ='"+
			pk_group+"' ) OR (PK_ORG ='"+pk_group+"' AND PK_GROUP ='"+pk_group+"') OR PK_ORG = 'GLOBLE00000000000000')";
			String sql =nc.bs.arap.util.SqlUtils.getInStr("PK_DEFDOC", pksList.toArray(new String[pksList.size()]))+ condition;
			List<String> list = new ArrayList<String>();
			try {
				Collection<DefdocVO>	defdocVOs = (Collection<DefdocVO>)MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(DefdocVO.class,sql,false);
				for(DefdocVO defdocVO:defdocVOs){
					list.add(defdocVO.getPk_defdoc());
				}
				resetDocValue( voList, defField, isHeadDef, list);
			} catch (MetaDataException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void resetDept(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef) throws
	BusinessException{
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null&&pksList.size()>0){
			String[] pks = pksList.toArray(new String[pksList.size()]);
			String condition = " AND ( (PK_ORG ='"+pk_org+"' AND PK_GROUP ='"+
			pk_group+"' ) OR (PK_ORG ='"+pk_group+"' AND PK_GROUP ='"+pk_group+"') OR PK_ORG = 'GLOBLE00000000000000')";
			String sql = nc.bs.arap.util.SqlUtils.getInStr("PK_VID", pks)+ condition;
			List<String> list = new ArrayList<String>();
			try {
					Collection<DeptVO>	deptVOs= (Collection<DeptVO>)MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(DeptVO.class,sql,false);
			         for(DeptVO deptVO:deptVOs){
			        	 if(null != deptVO.getPk_dept()){
			        		 list.add(deptVO.getPk_dept());
			        	 }
			        	 list.add(deptVO.getPk_vid());
			        	 
			         }
					resetDocValue( voList, defField, isHeadDef, list);
					if(IBillFieldGet.PK_DEPTID_V.equals(defField)){
					  resetDocValue( voList, IBillFieldGet.PK_DEPTID, isHeadDef, list);
					}
			} catch (MetaDataException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void resetPsndoc(List<AggregatedValueObject> voList, String pk_org, String pk_group, String defField, int isHeadDef) throws BusinessException {
		List<String> pksList = getPksLists(voList, defField, isHeadDef);
		if (pksList != null && pksList.size() > 0) {
			String[] pks = pksList.toArray(new String[pksList.size()]);
			StringBuffer psnDocSql = new StringBuffer("SELECT BD_PSNDOC.");
			psnDocSql.append(PsndocVO.PK_PSNDOC);
			psnDocSql.append(" FROM BD_PSNDOC LEFT JOIN BD_PSNJOB ON BD_PSNDOC.PK_PSNDOC = BD_PSNJOB.PK_PSNDOC WHERE ");

			String condition = " AND ( (BD_PSNDOC.PK_ORG ='" + pk_org + "' AND BD_PSNDOC.PK_GROUP ='" + pk_group + "' ) OR (BD_PSNDOC.PK_ORG ='" + pk_group
					+ "' AND BD_PSNDOC.PK_GROUP ='" + pk_group + "') OR BD_PSNDOC.PK_ORG = 'GLOBLE00000000000000' OR BD_PSNJOB.PK_ORG = '" + pk_org + "')";
			String sql = nc.bs.arap.util.SqlUtils.getInStr("BD_PSNDOC.PK_PSNDOC", pks) + condition;
			psnDocSql.append(sql);

			try {

				List<String> list = (List<String>) new BaseDAO().executeQuery(psnDocSql.toString(), new BaseProcessor() {

					private static final long serialVersionUID = 1L;
					List<String> resultList = new ArrayList<String>();

					@Override
					public Object processResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							resultList.add(rs.getString(1));
						}
						return resultList;
					}

				});
				resetDocValue(voList, defField, isHeadDef, list);
			} catch (DAOException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	public static void resetFundPlan(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef){
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null&&pksList.size()>0){
			String pks[] = pksList.toArray(new String[pksList.size()]);
			String[] result =null;
//			String condition = nc.bs.arap.util.SqlUtils.getInStr("PK_FUNDPLAN", pks)+" AND ( (PK_ORG ='"+pk_org+"' AND PK_GROUP ='"+
//			pk_group+"' ) OR (PK_ORG ='"+pk_group+"' AND PK_GROUP ='"+pk_group+"') OR PK_ORG = 'GLOBLE00000000000000')";
			List<String> list = new ArrayList<String>();
			try {
				result = NCLocator.getInstance().lookup(IFundPlanPubService.class).queryFundPlanPkByCondition(pks,pk_group,pk_org);
				if(result!=null){
					list =new ArrayList<String>(Arrays.asList(result));	
				}
				resetDocValue( voList, defField, 1, list);
			} catch (BusinessException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	public static void resetCashflow(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef){
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null&&pksList.size()>0){
			String[] pks = pksList.toArray(new String[pksList.size()]);
//			String condition = " AND ( (PK_ORG ='"+pk_org+"' AND PK_GROUP ='"+
//			pk_group+"' ) OR (PK_ORG ='"+pk_group+"' AND PK_GROUP ='"+pk_group+"') OR PK_ORG = 'GLOBLE00000000000000')";
//			String sql = nc.bs.arap.util.SqlUtils.getInStr("PK_CASHFLOW", pks)+ condition;
			String[] result =null;
			List<String> list = new ArrayList<String>();
			try {
			     result = NCLocator.getInstance().lookup(ICashflowPubService.class).queryCashFlowPkByCondition(pks,pk_group,pk_org); 
					if(result!=null){
						list =new ArrayList<String>(Arrays.asList(result));	
					}
				resetDocValue( voList, defField, isHeadDef, list);	 
			} catch (BusinessException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	
	public static void resetInoutBusiClass(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef){
		List<String> pksList = getPksLists( voList, defField, isHeadDef);
		if(pksList!=null&&pksList.size()>0){
			String pks[] = pksList.toArray(new String[pksList.size()]);
			String[] result =null;
			List<String> list = new ArrayList<String>();
			if(pk_group == null){
				pk_group = "";
			}
			//调整收支项目新方法
//			String condition = nc.bs.arap.util.SqlUtils.getInStr("PK_INOUTBUSICLASS", pks)+" AND ( (PK_ORG ='"+pk_org+"' AND PK_GROUP ='"+
//			pk_group+"' ) OR (PK_ORG ='"+pk_group+"' AND PK_GROUP ='"+pk_group+"') OR PK_ORG = 'GLOBLE00000000000000')";
			try {
				result = NCLocator.getInstance().lookup(IInoutBusiClassPubService.class).queryInoutBusiClassPkByCondition(pks,pk_group,pk_org);
				if(result!=null){
					list =new ArrayList<String>(Arrays.asList(result));	
				}
				resetDocValue( voList, defField, isHeadDef, list);
			} catch (BusinessException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	/**
	 * 批量处理客户级别可见过滤
	 * @param voList
	 * @param pk_org
	 * @param pk_group
	 * @throws BusinessException 
	 */
	@SuppressWarnings("unchecked")
	public static void resetCustomers(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef) throws BusinessException{
		List<String> pksList =getPksLists(voList, defField, isHeadDef);
		if(pksList!=null &&pksList.size()>0){
			String condition = BDVisibleUtil.getRefVisibleCondition(pk_group, pk_org,
			          IBDMetaDataIDConst.CUSTOMER, false);
			String sql = nc.bs.arap.util.SqlUtils.getInStr("PK_CUSTOMER", pksList.toArray(new String[pksList.size()]))+" and "+condition;
			List<String> list = new ArrayList<String>();
			try {
				Collection<CustomerVO>	customerOrgVOs = (Collection<CustomerVO>)MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(CustomerVO.class,sql,false);
				for(CustomerVO customerOrgVO :customerOrgVOs){
					list.add(customerOrgVO.getPk_customer());
				}
				resetDocValue(voList, defField, isHeadDef, list);
			} catch (MetaDataException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	/**
	 * 批量处理供应商级别可见过滤
	 * @param voList
	 * @param pk_org
	 * @param pk_group
	 * @param defField
	 * @param isHeadDef
	 * @throws BusinessException 
	 */
	@SuppressWarnings("unchecked")
	public static void resetSupplies(List<AggregatedValueObject>  voList,String pk_org,String pk_group,String defField,int isHeadDef) throws BusinessException{
		List<String> pksList =getPksLists(voList, defField, isHeadDef);
		if(pksList!=null &&pksList.size()>0){
			String condition = BDVisibleUtil.getRefVisibleCondition(pk_group, pk_org,
			          IBDMetaDataIDConst.SUPPLIER, false);
			String sql = nc.bs.arap.util.SqlUtils.getInStr("PK_SUPPLIER", pksList.toArray(new String[pksList.size()]))+" and "+ condition;
			List<String> list =new ArrayList<String>();
			
			try {
				// Collection<SupplierVO> supplierOrgVOs = (Collection<SupplierVO>)MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(SupplierVO.class,sql,false);
				// String fileter_sql = "select pk_supplier from (select pk_supplier from bd_supplier where" + sql + ") where " +  nc.bs.arap.util.SqlUtils.getInStr("PK_SUPPLIER", pksList.toArray(new String[pksList.size()]));
				StringBuffer fileter_sql = new StringBuffer();
				//Sqlserver 会有问题 增加别名处理 add by weiningc 20190729 start
				fileter_sql.append(" pk_supplier in (select pk_supplier from (select pk_supplier from bd_supplier where ")
					.append(sql).append(" )a where ")
					.append(nc.bs.arap.util.SqlUtils.getInStr("a.pk_supplier", pksList.toArray(new String[pksList.size()])))
					.append(" ) ");
				//end
				Collection<SupplierVO> supplierOrgVOs = 
						new BaseDAO().retrieveByClause(SupplierVO.class, fileter_sql.toString(), new String[]{SupplierVO.PK_SUPPLIER});
				for(SupplierVO supplierorgvo : supplierOrgVOs){
					list.add(supplierorgvo.getPk_supplier());
				}
				resetDocValue(voList, defField, isHeadDef, list);
			} catch (MetaDataException e) {
				ExceptionHandler.handleRuntimeException(e);
			}
		}
	}
	/**
	 * 批量取得过滤字段的pk
	 * @param voList
	 * @param defField
	 * @param isHeadDef
	 * @return
	 */
	public static  List<String> getPksLists(List<AggregatedValueObject>  voList,String defField,int flag){
		List<String> pksList = new ArrayList<String>();
		if(flag==0){
			for(AggregatedValueObject vo:voList){
				BaseAggVO bill = (BaseAggVO) vo;
				if(!StringUtil.isEmpty((String)bill.getHeadVO().getAttributeValue(defField)))
					if(!pksList.contains((String)bill.getHeadVO().getAttributeValue(defField)))
					pksList.add((String)bill.getHeadVO().getAttributeValue(defField));
			}
		}else if(flag==1){
			for(AggregatedValueObject vo:voList){
				BaseAggVO bill = (BaseAggVO) vo;
				for(BaseItemVO itemVo: bill.getItems()){
					if(!nc.vo.arap.utils.StringUtil.isEmpty((String)itemVo.getAttributeValue(defField)))
						if(!pksList.contains((String)itemVo.getAttributeValue(defField)))
							pksList.add((String)itemVo.getAttributeValue(defField));
				}
			}
		}else if(flag==2){
			for(AggregatedValueObject vo:voList){
				BaseAggVO bill = (BaseAggVO) vo;
				if(!StringUtil.isEmpty((String)bill.getHeadVO().getAttributeValue(defField)))
					if(!pksList.contains((String)bill.getHeadVO().getAttributeValue(defField)))
					pksList.add((String)bill.getHeadVO().getAttributeValue(defField));
				for(BaseItemVO itemVo: bill.getItems()){
					if(!nc.vo.arap.utils.StringUtil.isEmpty((String)itemVo.getAttributeValue(defField)))
						if(!pksList.contains((String)itemVo.getAttributeValue(defField)))
							pksList.add((String)itemVo.getAttributeValue(defField));
				}
			}	
		}
		return pksList;
	}
	/**
	 * 批量过滤字段
	 * @param voList
	 * @param defField
	 * @param isHeadDef
	 * @param list
	 * @param flag 0:修改表头 1：修改表体 2：修改单据
	 */
	public static void resetDocValue(List<AggregatedValueObject>  voList,String defField,int flag,List<String> list){
			if(flag==0){
				for(AggregatedValueObject vo:voList){
					BaseBillVO bill = ((BaseAggVO) vo).getHeadVO();
					if(!StringUtil.isEmpty((String)bill.getAttributeValue(defField)))
						if(!list.contains((String)bill.getAttributeValue(defField)))
							bill.setAttributeValue(defField, null);
				}	
			}else if(flag==1){
				for(AggregatedValueObject vo:voList){
					BaseAggVO bill = (BaseAggVO) vo;
					for(BaseItemVO itemVo: bill.getItems()){
						if(!nc.vo.arap.utils.StringUtil.isEmpty((String)itemVo.getAttributeValue(defField)))
							if(!list.contains((String)itemVo.getAttributeValue(defField)))
								itemVo.setAttributeValue(defField, null);
					}
			}
		}else if(flag==2){
			for(AggregatedValueObject vo:voList){
				BaseAggVO bill = (BaseAggVO) vo;
				if(!StringUtil.isEmpty((String)bill.getHeadVO().getAttributeValue(defField)))
					if(!list.contains((String)bill.getHeadVO().getAttributeValue(defField)))
						bill.getHeadVO().setAttributeValue(defField, null);
				for(BaseItemVO itemVo: bill.getItems()){
					if(!nc.vo.arap.utils.StringUtil.isEmpty((String)itemVo.getAttributeValue(defField)))
						if(!list.contains((String)itemVo.getAttributeValue(defField)))
							itemVo.setAttributeValue(defField, null);
				}
			}
		}
	}
	/**
	 * 检查供应商 收款银行账户在保存之前是否被删除
	 * @param supplierMap 表体行供应商
	 * @param recaccountMap 表体行收款银行账户
	 * @throws BusinessException
	 */
	public static void checkSupplierAndrecaccount(Map<String, String> supplierMap, Map<String, String> recaccountMap) throws BusinessException {
		if (supplierMap.isEmpty()) {
			return;
		}
		Set<String> supplierPKs = supplierMap.keySet();

		SupplierVO[] supplierInfo = getSupplierInfo(supplierPKs.toArray(new String[]{}));
		if (supplierInfo == null) {
			Logger.error("can not find any supplier info!");
		} else if (supplierInfo.length != supplierPKs.size()) {
			for (SupplierVO info : supplierInfo) {
				if (info != null) {
					supplierMap.remove(info.getPk_supplier());
				}
			}
		} else {
			supplierMap.clear();
		}
		if (!supplierMap.isEmpty()) {
			List<String> errorSup = new ArrayList<String>();
			for (Entry<String, String> entry : supplierMap.entrySet()) {
				errorSup.add(entry.getValue());
			}
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0", "02006arappub0523-0057", null, errorSup.toArray(new String[0]))/*
																																												 * @res
																																												 * "供应商{0}未在系统中建立,不能生成单据!"
																																												 */);
		}

		if (!recaccountMap.isEmpty()) {
			Set<String> recAccPKs = recaccountMap.keySet();
			String[] recaccountInfo = getrecaccountInfo(recAccPKs.toArray(new String[]{}));
			if (recaccountInfo == null) {
				Logger.error("can not find any receivable account!");
			} else if (recaccountInfo.length != recAccPKs.size()) {
				for (String info : recaccountInfo) {
					if (info != null) {
						recaccountMap.remove(info);
					}
				}
			} else {
				recaccountMap.clear();
			}
			if (!recaccountMap.isEmpty()) {
				List<String> errorSup = new ArrayList<String>();
				for (Entry<String, String> entry : recaccountMap.entrySet()) {
					if(!StringUtil.isEmpty(entry.getKey()) && !StringUtil.isEmpty(entry.getValue())){
						errorSup.add(entry.getValue());
					}
				}
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0", "02006arappub0523-0062", null,
						errorSup.toArray(new String[0]))/*
														 * @res "银行帐号{0}未在系统中建立,不能生成单据!"
														 */);
			}
		}
	}

	public static String[] getrecaccountInfo(String[] pk_recaccount) throws BusinessException {

		List<String> returnPK = new ArrayList<String>();
		if (null == pk_recaccount) {
			return null;
		}
		Map<String, BankAccSubVO> queryBankAccSubByPkMap = NCLocator.getInstance().lookup(nc.pubitf.uapbd.IBankaccPubQueryService.class).queryBankAccSubByPk(
				new String[] { "PK_BANKACCSUB", "PK_BANKACCBAS" }, pk_recaccount);
		if (queryBankAccSubByPkMap != null && !queryBankAccSubByPkMap.isEmpty()) {
			Set<Entry<String, BankAccSubVO>> entrySet = queryBankAccSubByPkMap.entrySet();
			for (Entry<String, BankAccSubVO> entry : entrySet) {
				if (entry.getValue() != null) {
					returnPK.add(entry.getKey());
				}
			}

		} else {
			BankAccbasVO[] queryBankaccsByPks = NCLocator.getInstance().lookup(nc.pubitf.uapbd.IBankaccPubQueryService.class).queryBankaccsByPks(pk_recaccount);
			if (!ArrayUtils.isEmpty(queryBankaccsByPks)) {
				for (BankAccbasVO vo : queryBankaccsByPks) {
					returnPK.add(vo.getPk_bankaccbas());
				}
			}
		}
		return returnPK.toArray(new String[0]);
	}

	public static SupplierVO[] getSupplierInfo(String[] pk_supplier) throws BusinessException {

		if (null == pk_supplier) {
			return null;
		}

		return NCLocator.getInstance().lookup(nc.pubitf.uapbd.ISupplierPubService.class).getSupplierVO(pk_supplier, new String[] { "name", "pk_supplier" });
	}
	
}
