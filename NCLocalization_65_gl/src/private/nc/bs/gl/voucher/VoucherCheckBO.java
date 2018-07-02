package nc.bs.gl.voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NamingException;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.glcom.ass.assitem.cache.AccAssItemCache;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.pub.SystemException;
import nc.gl.glconst.systemtype.SystemtypeConst;
import nc.gl.utils.GLMultiLangUtil;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.gl.pub.GLStartCheckUtil;
import nc.itf.gl.pub.ICommAccBookPub;
import nc.itf.gl.pub.IFreevaluePub;
import nc.itf.gl.pub.IGlPeriod;
import nc.itf.gl.voucher.IVoucherConst;
import nc.itf.glcom.para.GLParaAccessor;
import nc.itf.glcom.para.GLParaValueConst;
import nc.itf.glcom.para.IGlPara;
import nc.itf.org.IOrgConst;
import nc.itf.org.IOrgEnumConst;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.bd.accessor.GeneralAccessorFactory;
import nc.pubitf.bd.accessor.IGeneralAccessor;
import nc.pubitf.org.IAccountingBookPubService;
import nc.pubitf.org.IBusiFuncQryPubService;
import nc.pubitf.org.IOrgRelationDataPubService;
import nc.ui.gl.datacache.GLParaDataCache;
import nc.vo.bd.accessor.IBDData;
import nc.vo.bd.accessor.NullGeneralAccessor;
import nc.vo.bd.accessor.bankaccsub.BankaccSubGeneralAccessor;
import nc.vo.bd.accessor.bankaccsub.BankaccsubBDDataVO;
import nc.vo.bd.account.AccAssVO;
import nc.vo.bd.account.AccountVO;
import nc.vo.bd.period.AccperiodVO;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.bd.vouchertype.VoucherTypeVO;
import nc.vo.fipub.freevalue.Module;
import nc.vo.fipub.freevalue.account.proxy.AccAssGL;
import nc.vo.fipub.utils.StrTools;
import nc.vo.fipub.vouchertyperule.RuleConst;
import nc.vo.fipub.vouchertyperule.VouchertypeRuleVO;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.AccountUtilGL;
import nc.vo.gateway60.itfs.CalendarUtilGL;
import nc.vo.gateway60.itfs.CloseAccBookUtils;
import nc.vo.gateway60.itfs.Currency;
import nc.vo.gateway60.itfs.CurrtypeGL;
import nc.vo.gateway60.itfs.VoucherTypeGL;
import nc.vo.gateway60.pub.GlBusinessException;
import nc.vo.gateway60.pub.VoucherTypeDataCache;
import nc.vo.gl.cashflowcase.CashflowcaseVO;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.gl.pubvoucher.GLParameterVO;
import nc.vo.gl.pubvoucher.OperationResultVO;
import nc.vo.gl.pubvoucher.VoucherKey;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.voucher.VoucherCheckConfigVO;
import nc.vo.gl.voucher.VoucherCheckMessage;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.balance.GLGeneralAccessUtil;
import nc.vo.glcom.balance.GlBalanceKey;
import nc.vo.glcom.balance.GlBalanceVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.glcom.constant.GLVoucherKindConst;
import nc.vo.glcom.exception.GLBusinessException;
import nc.vo.glcom.glperiod.GlPeriodVO;
import nc.vo.glcom.inteltool.CDataSource;
import nc.vo.glcom.nodecode.GlNodeConst;
import nc.vo.glcom.para.ParaMacro;
import nc.vo.glcom.sorttool.ISortTool;
import nc.vo.glcom.sorttool.ISortToolProvider;
import nc.vo.glcom.tools.GLPubProxy;
import nc.vo.glcom.tools.GLSystemControlTool;
import nc.vo.glcom.wizard.VoWizard;
import nc.vo.org.AccountingBookVO;
import nc.vo.org.SetOfBookVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.ICalendar;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

import org.apache.commons.lang.StringUtils;

/**
 * 此处插入类型说明。 创建日期：(2003-3-3 9:43:07)
 * 
 * @author：郭宝华
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class VoucherCheckBO implements ISortToolProvider {
	private ISortTool m_assSortTool;

	/**
	 * VoucherCheckBO 构造子注解。
	 */
	public VoucherCheckBO() {
		super();
	}


	private GlBalanceVO[] combineAss(GlBalanceVO[] vos, GlQueryVO m_qryVO)
			throws Exception {

		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();

		// 在此不指定分组信息,则不分组合计

		int[] intSortIndex;
		int intSumLimit = 1; // 指定合计的最小列号组合

		intSortIndex = new int[] { GlBalanceKey.GLBALANCE_PK_CURRTYPE,GlBalanceKey.GLBALANCE_ASSVOS };

		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();

		genTool.setLimitSumGen(intSumLimit);
		genTool.setSortIndex(intSortIndex);
		// genTool.setSpecialToolKey(new int[]{GlBalanceKey.K_AssVOs});
		genTool.setGetSortTool(this);

		nc.vo.glcom.inteltool.CSumTool sumTool = new nc.vo.glcom.inteltool.CSumTool();

		int sumIndex[] = new int[] { GlBalanceKey.GLBALANCE_LOCALCREDITAMOUNT,
				GlBalanceKey.GLBALANCE_LOCALDEBITAMOUNT,
				GlBalanceKey.GLBALANCE_DEBITQUANTITY,
				GlBalanceKey.GLBALANCE_CREDITQUANTITY,
				GlBalanceKey.GLBALANCE_CREDITGROUPAMOUNT,
				GlBalanceKey.GLBALANCE_DEBITGROUPAMOUNT,
				GlBalanceKey.GLBALANCE_CREDITGLOBALAMOUNT,
				GlBalanceKey.GLBALANCE_DEBITGLOBALAMOUNT,
				GlBalanceKey.GLBALANCE_DEBITAMOUNT,
				GlBalanceKey.GLBALANCE_CREDITAMOUNT
				};
		// 要进行合计的列
		sumTool.setSumIndex(sumIndex);

		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		outputTool.setRequireOutputDetail(false);
		outputTool.setSummaryCol(-1); // 设置备注信息内容及所对应的列

		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();

		Vector vecVos = new Vector();

		Object userData = null;
		for (int i = 0; i < vos.length; i++) {
			// userData = ((nc.vo.glcom.inteltool.AppendVO)
			// vos[i].getUserData()).clone();
			vos[i].setUserData(null);
			// vos[i].setValue(GlBalanceKey.GLBALANCE_ASSVOS,
			// m_qryVO.getAssVos());
			vecVos.addElement(vos[i]);
		}

		datasource.setSumVector(CDataSource.sortVector(vecVos, genTool, false));

		try {
			tt.setSumTool(sumTool);
			tt.setGenTool(genTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
		}

		Vector recVector = tt.getResultVector();

		GlBalanceVO[] VOs = new GlBalanceVO[recVector.size()];

		recVector.copyInto(VOs);

		for (int i = 0; i < VOs.length; i++)
			VOs[i].setUserData(userData);
		return VOs;
	}

	private static final Log log = Log.getInstance(VoucherCheckBO.class);

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-26 10:34:37)
	 * 
	 * @return java.lang.Boolean
	 * @param detail
	 *            nc.vo.gl.pubvoucher.DetailVO
	 * @param sysid
	 *            java.lang.String
	 * @param i
	 *            int
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	protected VoucherVO catOutSubjVoucher(VoucherVO voucher, HashMap tempaccsubj)
			throws Exception {
		if (voucher == null)
			return null;
		voucher.setIsOutSubj(nc.vo.pub.lang.UFBoolean.FALSE);
		if (voucher.getNumDetails() > 0) {
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				AccountVO acc = (AccountVO) tempaccsubj.get(voucher
						.getDetail(i).getPk_accasoa());
				if (acc != null && acc.getOutflag() != null
						&& acc.getOutflag().booleanValue()) {
					voucher.setIsOutSubj(nc.vo.pub.lang.UFBoolean.TRUE);
					break;
				}
			}
		}
		return voucher;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkALL(VoucherVO voucher,
			HashMap accsubjcache, GLParameterVO param) throws Exception {
		return null;// return checkBalanceControl(voucher, accsubjcache, param);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkAmountBalance(VoucherVO voucher,
			Integer controlmode) {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;
		if (voucher.getNumDetails() <= 0)
			return null;
		if (voucher.getIsOutSubj() != null
				&& voucher.getIsOutSubj().booleanValue())
			return null;
		HashMap currtype_map = new HashMap();
		try {
			nc.vo.bd.currtype.CurrtypeVO[] currtypes = getCurrency(voucher
					.getPk_org());
			if (currtypes == null || currtypes.length == 0)
				throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000571")/*
													 * @res "当前公司尚未设置币种信息！"
													 */);
			for (int i = 0; i < currtypes.length; i++) {
				currtype_map.put(currtypes[i].getPk_currtype(), currtypes[i]);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(e.getMessage());
		}
		OperationResultVO[] result = null;
		HashMap debit_map = new HashMap();
		HashMap credit_map = new HashMap();
		HashMap curr_map = new HashMap();
		UFDouble fracDebit = new UFDouble(0);
		UFDouble fracCredit = new UFDouble(0);
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			DetailVO detail = voucher.getDetail(i);
			curr_map.put(detail.getPk_currtype(), detail.getPk_currtype());
			fracDebit = (UFDouble) debit_map.get(detail.getPk_currtype());
			if (fracDebit == null)
				fracDebit = new UFDouble(0);
			fracCredit = (UFDouble) credit_map.get(detail.getPk_currtype());
			if (fracCredit == null)
				fracCredit = new UFDouble(0);
			fracDebit = fracDebit.add(detail.getDebitamount());
			fracCredit = fracCredit.add(detail.getCreditamount());
			debit_map.put(detail.getPk_currtype(), fracDebit);
			credit_map.put(detail.getPk_currtype(), fracCredit);
		}
		Iterator iterator = curr_map.keySet().iterator();
		Vector vecCurr = new Vector();
		while (iterator.hasNext()) {
			vecCurr.addElement(iterator.next());
		}
		for (int i = 0; i < vecCurr.size(); i++) {
			if (vecCurr.elementAt(i) == null)
				continue;
			nc.vo.bd.currtype.CurrtypeVO curr = (nc.vo.bd.currtype.CurrtypeVO) currtype_map
					.get(vecCurr.elementAt(i).toString());
			if (curr == null) {
				throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000572")/*
													 * @res "公司与币种不匹配！"
													 */);
			}
			
			int[] digitAndRoundtype = nc.itf.fi.pub.Currency.getCurrDigitAndRoundtype(vecCurr.elementAt(i).toString());
			
			fracDebit = (UFDouble) debit_map.get(vecCurr.elementAt(i)
					.toString());
			if (fracDebit == null)
				fracDebit = new UFDouble(0);
			fracCredit = (UFDouble) credit_map.get(vecCurr.elementAt(i)
					.toString());
			if (fracCredit == null)
				fracCredit = new UFDouble(0);
			if (!fracDebit.equals(fracCredit)) {
				switch (controlmode.intValue()) {
				case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 1;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgAmountNotBalance)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000573", null,
											new String[] { curr.getName() })/*
																			 * @res
																			 * "（{0}）"
																			 */;
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000332")/*
																 * @res "借方合计："
																 */
							+ fracDebit.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000333")/*
																 * @res "贷方合计："
																 */
							+ fracCredit.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000335")/*
																 * @res "合计差额："
																 */
							+ fracDebit
									.sub(fracCredit)
									.abs()
									.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription + "\n";
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
					break;
				}
				case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgAmountNotBalance)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000573", null,
											new String[] { curr.getName() })/*
																			 * @res
																			 * "（{0}）"
																			 */;
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000332")/*
																 * @res "借方合计："
																 */
							+ fracDebit.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000333")/*
																 * @res "贷方合计："
																 */
							+ fracCredit.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000335")/*
																 * @res "合计差额："
																 */
							+ fracDebit
									.sub(fracCredit)
									.abs()
									.setScale(digitAndRoundtype[0],digitAndRoundtype[1]);
					rs.m_strDescription = rs.m_strDescription + "\n";
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
					break;
				}
				default:
					throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000574")/*
														 * @res
														 * "无法识别的控制类型，可能是软件版本不一致。"
														 */);
				}
			}
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkAmountZero(VoucherVO voucher,
			Integer controlmode) {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;
		if (voucher.getNumDetails() <= 0)
			return null;
		if (voucher.getVoucherkind().intValue() == 3) {
			return null;
		}
		OperationResultVO[] result = null;
		Vector vecresult = new Vector();
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			DetailVO detail = voucher.getDetail(i);
			if (detail.getDebitamount().equals(new nc.vo.pub.lang.UFDouble(0))
					&& detail.getCreditamount().equals(
							new nc.vo.pub.lang.UFDouble(0))) {
				switch (controlmode.intValue()) {
				case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 1;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDetailAmountIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					vecresult.addElement(rs);
					break;
				}
				case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDetailAmountIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					vecresult.addElement(rs);
					break;
				}
				default:
					throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000574")/*
														 * @res
														 * "无法识别的控制类型，可能是软件版本不一致。"
														 */);
				}
			}
		}
		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 17:48:22)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	protected OperationResultVO[] checkBalance(VoucherVO voucher)
			throws Exception {
		if (voucher.getIsOutSubj() != null
				&& voucher.getIsOutSubj().booleanValue())
			return null;
		OperationResultVO[] result = null;
		if (voucher.getTotaldebit().sub(voucher.getTotalcredit()).abs()
				.compareTo(UFDouble.ZERO_DBL) > 0) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNotBalance);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		if (GLParaAccessor.isStartGroupCurr(voucher.getPk_group())
				.booleanValue()
				&& voucher.getTotaldebitgroup().sub(
						voucher.getTotalcreditgroup()).abs().compareTo(
						UFDouble.ZERO_DBL) > 0) {
			String para = GLParaAccessor.getGroupAmountCtrl(voucher
					.getPk_accountingbook());
			if (GLParaValueConst.GL116_INFO.equals(para)) { // 提示
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 1;
				rs.m_strPK = voucher.getPk_voucher();
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgGroupAmountNotBalance);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			} else if (GLParaValueConst.GL116_CTRL.equals(para)) { // 控制
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strPK = voucher.getPk_voucher();
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgGroupAmountNotBalance);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		}
		if (GLParaAccessor.isStartGlobalCurr().booleanValue()
				&& voucher.getTotaldebitglobal().sub(
						voucher.getTotalcreditglobal()).abs().compareTo(
						UFDouble.ZERO_DBL) > 0) {
			String para = GLParaAccessor.getGlobalAmountCtrl(voucher
					.getPk_accountingbook());
			if (GLParaValueConst.GL118_INFO.equals(para)) { // 提示
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 1;
				rs.m_strPK = voucher.getPk_voucher();
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgGlobalAmountNotBalance);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			} else if (GLParaValueConst.GL118_CTRL.equals(para)) { // 控制
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strPK = voucher.getPk_voucher();
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgGlobalAmountNotBalance);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		}
		return result;
	}

	/**
	 * 余额控制 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkBalanceControl(VoucherVO voucher)
	    throws Exception {
		Integer controlmode = NCLocator.getInstance().lookup(IGlPara.class)
				.getBalanceControlStyle(voucher.getPk_accountingbook());

		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;

		HashMap accsubjcache = getAccountMap(voucher, new HashMap());
		HashMap balancemap = new HashMap();
		// HashMap groupBalancemap = new HashMap();
		// HashMap globalBalancemap = new HashMap();
		HashMap tmp_subjmap = new HashMap();
		OperationResultVO[] result = null;
		Vector vecresult = new Vector();
		DetailVO[] details = voucher.getDetails();
		details = filterDetailForBalanceCtrl(details);
		DetailVO[] tmp_sumdetails = null;
		Vector vecdetails = new Vector();
		Vector vecSubjs = new Vector();

		UFDouble debitbalance = null;
		// UFDouble groupDebitbalance = null;
		// UFDouble globalDebitbalance = null;
		UFDouble t_debitbalance = null;
		
		//存储币种主键，用于校验按原币余额控制
		Set<String> currtypeSet = new HashSet<String>();

		OperationResultVO oprs = null;
		int infoLevel = 0;
		switch (controlmode.intValue()) {
		case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
			infoLevel = 1;
			break;
		}
		case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
			infoLevel = 2;
			break;
		}
		default:
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000574")/*
																				 * @res
																				 * "无法识别的控制类型，可能是软件版本不一致。"
																				 */);
		}

		if (details != null)
			for (int i = 0; i < details.length; i++) {
				AccountVO accvo = (AccountVO) accsubjcache.get(details[i]
						.getPk_accasoa());
				if (null != accvo
						&& !(accvo.getBalanflag() == null || !accvo
								.getBalanflag().booleanValue())) {
					DetailVO tmp_detail = (DetailVO) details[i].clone();
					tmp_detail.setUserData(null);
					vecdetails.addElement(tmp_detail);
				}
				currtypeSet.add(details[i].getPk_currtype());
			}
		if (vecdetails.size() > 0) {
			DetailVO[] t_details = new DetailVO[vecdetails.size()];
			vecdetails.copyInto(t_details);
			tmp_sumdetails = nc.vo.gl.vouchertools.DetailTool.sumDetails(
					t_details, new int[] { VoucherKey.D_PK_ACCSUBJ,VoucherKey.D_PK_CURRTYPE });
		}
		// AccperiodBO periodbo = new AccperiodBO();
		// nc.vo.bd.period.AccperiodVO accperiod =
		// periodbo.queryByYear(voucher.getPk_accountingbook(),
		// voucher.getYear());
		// String year = voucher.getYear();
		// String period =
		// accperiod.getVosMonth()[accperiod.getVosMonth().length -
		// 1].getMonth();
		// AccountCalendar calendar = AccountCalendar.get();
		AccountCalendar calendar = CalendarUtilGL
				.getAccountCalendarByAccountBook(voucher.getPk_accountingbook());
		calendar.set(voucher.getYear());
		String year = voucher.getYear();
		String period = calendar.getLastMonthOfCurrentYear().getAccperiodmth();

		if (voucher.getPk_voucher() != null) {
			DetailVO[] t_old_details = new DetailExtendDMO()
					.queryByVoucherPks(new String[] { voucher.getPk_voucher() });
			t_old_details = filterDetailForBalanceCtrl(t_old_details);
			getAccountMap(t_old_details, accsubjcache);
			Vector vecolddetails = new Vector();
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {
					AccountVO accvo = (AccountVO) accsubjcache
							.get(t_old_details[i].getPk_accasoa());
					if (accvo == null)
						continue;
					if (!(accvo.getBalanflag() == null || !accvo.getBalanflag()
							.booleanValue())) {
						vecolddetails.addElement(t_old_details[i].clone());
					}
				}
			}
			if (vecolddetails.size() > 0) {
				t_old_details = new DetailVO[vecolddetails.size()];
				vecolddetails.copyInto(t_old_details);
				t_old_details = nc.vo.gl.vouchertools.DetailTool.sumDetails(
						t_old_details, new int[] { VoucherKey.D_PK_ACCSUBJ,VoucherKey.D_PK_CURRTYPE });
			} else {
				t_old_details = null;
			}
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {
					String key = t_old_details[i].getPk_accasoa()+t_old_details[i].getPk_currtype();
					debitbalance = (UFDouble) balancemap.get(key);
					debitbalance = (debitbalance == null ? new UFDouble(0)
							: debitbalance).sub(
							t_old_details[i].getDebitamount()).add(
							t_old_details[i].getCreditamount());
					balancemap.put(key,
							debitbalance);
					if (tmp_subjmap.get(t_old_details[i].getPk_accasoa()) == null) {
						vecSubjs.addElement(t_old_details[i].getPk_accasoa());
						tmp_subjmap.put(t_old_details[i].getPk_accasoa(),
								t_old_details[i].getPk_accasoa());
					}
					currtypeSet.add(t_old_details[i].getPk_currtype());
				}
			}
		}
		String[] pk_accsubjs = null;
		if (tmp_sumdetails != null)
			for (int i = 0; i < tmp_sumdetails.length; i++) {
				if (tmp_subjmap.get(tmp_sumdetails[i].getPk_accasoa()) == null) {
					vecSubjs.addElement(tmp_sumdetails[i].getPk_accasoa());
					tmp_subjmap.put(tmp_sumdetails[i].getPk_accasoa(),
							tmp_sumdetails[i].getPk_accasoa());
				}
			}
		if (vecSubjs.size() > 0) {

			pk_accsubjs = new String[vecSubjs.size()];
			vecSubjs.copyInto(pk_accsubjs);
		}
		if (pk_accsubjs == null || pk_accsubjs.length == 0)
			return null;
		nc.vo.glcom.balance.GlQueryVO qryvo = new nc.vo.glcom.balance.GlQueryVO();
		qryvo.setPk_account(pk_accsubjs);
		qryvo.setYear(year);
		qryvo.setPeriod(period);
		qryvo.setIncludeUnTallyed(true);
		qryvo.setpk_accountingbook(new String[] { voucher
				.getPk_accountingbook() });
		qryvo
				.setGroupFields(new int[] { GlBalanceKey.GLBALANCE_PK_ACCASOA,nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE });
		qryvo.setSubjVersion(voucher.getPrepareddate().toStdString());
		GlBalanceVO[] rs = NCLocator.getInstance()
				.lookup(ICommAccBookPub.class).getEndBalance(qryvo);
		if (rs != null) {
			for (int i = 0; i < rs.length; i++) {
				String key = rs[i].getPk_accasoa()+rs[i].getPk_currtype();
				debitbalance = (rs[i].getDebitamount() == null ? new UFDouble(
						0)
						: rs[i].getDebitamount())
						.sub(rs[i].getCreditamount() == null ? new UFDouble(0)
								: rs[i].getCreditamount());
				t_debitbalance = (UFDouble) balancemap.get(key);
				t_debitbalance = (t_debitbalance == null ? new UFDouble(0)
						: t_debitbalance).add(debitbalance);
				balancemap.put(key, t_debitbalance);
			}
		}
		if (tmp_sumdetails != null)
			for (int i = 0; i < tmp_sumdetails.length; i++) {
				String key = tmp_sumdetails[i].getPk_accasoa()+tmp_sumdetails[i].getPk_currtype();
				debitbalance = (UFDouble) balancemap.get(key);
				debitbalance = (debitbalance == null ? new UFDouble(0)
						: debitbalance).add(tmp_sumdetails[i].getDebitamount())
						.sub(tmp_sumdetails[i].getCreditamount());
				balancemap.put(key, debitbalance);

			}
		for (int i = 0; i < pk_accsubjs.length; i++) {
            String[] currtypeStrs = currtypeSet.toArray(new String[0]);
			
			for (int j = 0; j < currtypeStrs.length; j++) {
                String key = pk_accsubjs[i]+currtypeStrs[j];
				debitbalance = (UFDouble) balancemap.get(key);
				if (debitbalance == null)
					continue;
				AccountVO accvo = (AccountVO) accsubjcache.get(pk_accsubjs[i]);
				if (accvo.getBalanorient().intValue() == IVoucherConst.ACCASOA_BALANCE_DEBIT) {
					if (debitbalance.compareTo(UFDouble.ZERO_DBL) < 0) {
						oprs = new OperationResultVO();
						oprs.m_intSuccess = infoLevel;
						oprs.m_strPK = voucher.getPk_voucher();
						oprs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountBalanceCtrl)
								+ nc.bs.ml.NCLangResOnserver.getInstance()
										.getStrByID("20021005",
												"UPP20021005-000577")/*
																	 * @res
																	 * " 科目::"
																	 */
								+ accvo.getCode()+" "+nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("20021005",
								"UPP20021005-000343")/*
													 * @res
													 * " 币种"
													 */+":"+Currency.getCurrInfo(currtypeStrs[j]).getCode();
						vecresult.addElement(oprs);
					}
				} else {
					if (debitbalance.compareTo(UFDouble.ZERO_DBL) > 0) {
						oprs = new OperationResultVO();
						oprs.m_intSuccess = infoLevel;
						oprs.m_strPK = voucher.getPk_voucher();
						oprs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountBalanceCtrl)
								+ nc.bs.ml.NCLangResOnserver.getInstance()
										.getStrByID("20021005",
												"UPP20021005-000577")/*
																	 * @res
																	 * " 科目"
																	 */
								+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("20021005",
								"UPP20021005-000343")/*
													 * @res
													 * " 币种"
													 */+"["+Currency.getCurrInfo(currtypeStrs[j]).getCode()+"]";
						vecresult.addElement(oprs);
					}
				}
			}
		}
		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;
}

	/**
	 * 辅助余额控制 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkAssBalanceControlNew(VoucherVO voucher,
			Boolean operatedirection) throws Exception {
		Integer controlmode = NCLocator.getInstance().lookup(IGlPara.class)
				.getBalanceControlStyle(voucher.getPk_accountingbook());
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;

		HashMap accsubjcache = getAccountMap(voucher, new HashMap());
		HashMap balancemap = new HashMap();
//		HashMap groupBalancemap = new HashMap();
//		HashMap globalBalancemap = new HashMap();
		HashMap accsubjmap = new HashMap();
		HashMap assmap = new HashMap();
		HashMap oldbalancemap = new HashMap();
//		HashMap oldGroupBalancemap = new HashMap();
//		HashMap oldGlobalBalancemap = new HashMap();
		HashMap<String, AssVO> asschecktypemap = new HashMap<String, AssVO>();
		AssVO[] queryass = null;
		Vector vecresult = new Vector();
		OperationResultVO[] result = null;

		String balabcekey = null;
		UFDouble debitbalance = null;
//		UFDouble groupDebitbalance = null;
//		UFDouble globalDebitbalance = null;

		OperationResultVO oprs = null;
		int infoLevel = 0;
		switch (controlmode.intValue()) {
		case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
			infoLevel = 1;
			break;
		}
		case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
			infoLevel = 2;
			break;
		}
		default:
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000574")/*
																				 * @res
																				 * "无法识别的控制类型，可能是软件版本不一致。"
																				 */);
		}

		DetailVO[] details = voucher.getDetails();
		details = filterDetailForBalanceCtrl(details);
		DetailVO[] t_old_details = voucher.getDetails();
		details = catAss(details);

		for (int i = 0; details != null && i < details.length; i++) {
			AccountVO accvo = (AccountVO) accsubjcache.get(details[i]
					.getPk_accasoa());
			if (accvo == null) {
				accvo = new AccountVO();
				accvo.setPk_accasoa(details[i].getPk_accasoa());
				accvo = AccountUtilGL.findAccountVOByPrimaryKey(details[i]
						.getPk_accasoa(), voucher.getPrepareddate()
						.toStdString());
				accsubjcache.put(details[i].getPk_accasoa(), accvo);

				// hurh
				Vector<AccAssVO> subassvec = AccAssGL.getAccAssVOsByAccount(
						voucher.getPk_accountingbook(), accvo, voucher
								.getPrepareddate().toStdString());
				if (subassvec != null && subassvec.size() > 0) {
					accvo.setAccass(subassvec.toArray(new AccAssVO[0]));
				}
			}
			if (accvo != null) {
				// Vector subassvec =
				// AccAssGL.getAccAssVOsByAccount(accvo,voucher.getYear(),voucher.getPeriod());
				AccAssVO[] accass = accvo.getAccass();
				if (accass != null && accass.length > 0) {
					AssVO assvo = matchAssBalance(accvo, details[i].getAss(),
							voucher);
					if (assvo == null) {
						continue;
					} else {
						balabcekey = details[i].getPk_accasoa()+details[i].getPk_currtype()
								+ assvo.getPk_Checktype()
								+ assvo.getPk_Checkvalue();
						debitbalance = balancemap.get(balabcekey) == null ? new UFDouble(
								0)
								: (UFDouble) balancemap.get(balabcekey);
						debitbalance = (debitbalance == null ? new UFDouble(0)
								: debitbalance)
								.add((details[i].getDebitamount() == null ? new UFDouble(
										0)
										: details[i].getDebitamount())
										.sub(details[i].getCreditamount() == null ? new UFDouble(
												0)
												: details[i]
														.getCreditamount()));
						balancemap.put(balabcekey, debitbalance);
						if (accsubjmap.get(accvo.getPk_accasoa()) == null) {
							accsubjmap.put(accvo.getPk_accasoa(), accvo
									.getPk_accasoa());
						}
						if (assmap.get(assvo.getPk_Checktype()
								+ assvo.getPk_Checkvalue()) == null) {
							assmap.put(assvo.getPk_Checktype()
									+ assvo.getPk_Checkvalue(), assvo);
						}
					}
				}
			}
		}

		if (voucher.getPk_voucher() != null) {
			t_old_details = new DetailExtendDMO()
					.queryByVoucherPks(new String[] { voucher.getPk_voucher() });
			t_old_details = filterDetailForBalanceCtrl(t_old_details);
			getAccountMap(t_old_details, accsubjcache);
			t_old_details = catAss(t_old_details);
			Vector vecolddetails = new Vector();
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {

					AccountVO accvo = (AccountVO) accsubjcache
							.get(t_old_details[i].getPk_accasoa());
					if (accvo == null) {
						accvo = new AccountVO();
						accvo.setPk_accasoa(t_old_details[i].getPk_accasoa());
						// accvo.setPk_glorgbook(t_old_details[i].getPk_glorgbook());
						accvo = AccountUtilGL.findAccountVOByPrimaryKey(
								t_old_details[i].getPk_accasoa(), voucher
										.getPrepareddate().toStdString());
						accsubjcache.put(t_old_details[i].getPk_accasoa(),
								accvo);

						// hurh
						Vector<AccAssVO> subassvec = AccAssGL
								.getAccAssVOsByAccount(voucher
										.getPk_accountingbook(), accvo, voucher
										.getPrepareddate().toStdString());
						if (subassvec != null && subassvec.size() > 0) {
							accvo.setAccass(subassvec.toArray(new AccAssVO[0]));
						}
					}
					if (accvo != null) {
						// Vector subassvec =
						// AccAssGL.getAccAssVOsByAccount(accvo,voucher.getYear(),voucher.getPeriod());
						AccAssVO[] accass = accvo.getAccass();
						if (accass != null && accass.length > 0) {
							AssVO assvo = matchAssBalance(accvo,
									t_old_details[i].getAss(), voucher);
							if (assvo == null) {
								continue;
							} else {
								balabcekey = t_old_details[i].getPk_accasoa()+t_old_details[i].getPk_currtype()
										+ assvo.getPk_Checktype()
										+ assvo.getPk_Checkvalue();
								debitbalance = oldbalancemap.get(balabcekey) == null ? new UFDouble(
										0)
										: (UFDouble) oldbalancemap
												.get(balabcekey);
								debitbalance = (debitbalance == null ? new UFDouble(
										0)
										: debitbalance)
										.add((t_old_details[i]
												.getDebitamount() == null ? new UFDouble(
												0)
												: t_old_details[i]
														.getDebitamount())
												.sub(t_old_details[i]
														.getCreditamount() == null ? new UFDouble(
														0)
														: t_old_details[i]
																.getCreditamount()));
								oldbalancemap.put(balabcekey, debitbalance);
								if (accsubjmap.get(accvo.getPk_accasoa()) == null) {
									accsubjmap.put(accvo.getPk_accasoa(), accvo
											.getPk_accasoa());
								}
								if (assmap.get(assvo.getPk_Checktype()
										+ assvo.getPk_Checkvalue()) == null) {
									assmap.put(assvo.getPk_Checktype()
											+ assvo.getPk_Checkvalue(), assvo);
								}
							}
						}
					}

				}
			}
		}

		AccountCalendar calendar = CalendarUtilGL
				.getAccountCalendarByAccountBook(voucher.getPk_accountingbook());
		calendar.set(voucher.getYear());
		String year = voucher.getYear();
		String period = calendar.getLastMonthOfCurrentYear().getAccperiodmth();

		String[] pk_accsubjs = null;
		if (accsubjmap != null && accsubjmap.size() > 0) {
			pk_accsubjs = new String[accsubjmap.size()];
			pk_accsubjs = (String[]) accsubjmap.values().toArray(pk_accsubjs);
		}
		AssVO[] V_ass = null;
		if (assmap != null && assmap.size() > 0) {
			V_ass = new AssVO[assmap.size()];
			V_ass = (AssVO[]) assmap.values().toArray(V_ass);
		}
		if (V_ass == null) {
			return null;
		}
		for (int i = 0; i < V_ass.length; i++) {
			if (asschecktypemap.get(V_ass[i].getPk_Checktype()) == null) {
				asschecktypemap.put(V_ass[i].getPk_Checktype(), V_ass[i]);
			} else {
				AssVO _ass = asschecktypemap.get(V_ass[i].getPk_Checktype());
				_ass.setPk_Checkvalue(_ass.getPk_Checkvalue() + ","
						+ V_ass[i].getPk_Checkvalue());
				asschecktypemap.put(V_ass[i].getPk_Checktype(), _ass);
			}
		}
		queryass = new AssVO[asschecktypemap.size()];
		queryass = asschecktypemap.values().toArray(queryass);
		nc.vo.glcom.balance.GlQueryVO qryvo = new nc.vo.glcom.balance.GlQueryVO();
		qryvo.setPk_account(pk_accsubjs);
		qryvo.setAssVos(queryass);
		qryvo.setYear(year);
		qryvo.setPeriod(period);
		qryvo.setIncludeUnTallyed(true);
		qryvo.setpk_accountingbook(new String[] { voucher
				.getPk_accountingbook() });
		qryvo.setBaseAccountingbook(voucher.getPk_accountingbook());
		// if (voucher.getVoucherkind().intValue() == 2) {
		// qryvo.setSubjVerisonPeriod(voucher.getUserData().toString());
		// } else {
		// qryvo.setSubjVerisonPeriod(voucher.getPeriod());
		// }
		if (voucher.getVoucherkind().intValue() == 2) {
			if (voucher.getPeriod().equals("00")) {
				// qryvo.setSubjVerisonPeriod(voucher.getUserData().toString());
				qryvo.setSubjVerisonPeriod(calendar
						.getFirstMonthOfCurrentYear().getAccperiodmth());
			} else {
				qryvo.setSubjVerisonPeriod(voucher.getPeriod());
			}

		} else {
			calendar.setDate(voucher.getPrepareddate());
			qryvo.setSubjVerisonPeriod(calendar.getMonthVO().getAccperiodmth());
		}
		qryvo.setSubjVersionYear(year);
		qryvo.setUseSubjVersion(true);
		qryvo.setSubjVersion(voucher.getPrepareddate().toStdString());

		qryvo.setGroupFields(new int[] {
				nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE,
				nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID });
		GlBalanceVO[] rs = NCLocator.getInstance()
				.lookup(ICommAccBookPub.class).getEndBalance(qryvo);

		// ??
		if (rs != null && rs.length > 0) {
			nc.ui.glcom.balance.GlAssDeal objTemp = new nc.ui.glcom.balance.GlAssDeal();
			// 从查询VO中取核算类型主键数组
			String[] strType = null;
			nc.vo.glcom.ass.AssVO[] assvos = qryvo.getAssVos();
			if (assvos != null && assvos.length != 0) {
				strType = new String[assvos.length];
				for (int i = 0; i < assvos.length; i++)
					strType[i] = assvos[i].getPk_Checktype();
			}

			objTemp.setMatchingIndex(GlBalanceKey.GLBALANCE_ASSID);
			objTemp.setAppendIndex(GlBalanceKey.GLBALANCE_ASSVOS);

			objTemp.dealWith(rs, strType); // 处理加工

			// if (isSummed)//需要对同辅助项记录合并吗？
			rs = combineAss(rs, qryvo);
		}
		for (int j = 0; j < rs.length; j++) {
			debitbalance = (rs[j].getDebitamount() == null ? new UFDouble(
					0) : rs[j].getDebitamount()).sub(rs[j]
					.getCreditamount() == null ? new UFDouble(0) : rs[j]
					.getCreditamount());
			AccountVO accvo = (AccountVO) accsubjcache.get(rs[j]
					.getPk_accasoa());
			if (accvo == null) {
				accvo = new AccountVO();
				accvo.setPk_accasoa(rs[j].getPk_accasoa());
				// 60x accvo.setPk_glorgbook(rs[j].getPk_glorgbook());
				accvo = AccountUtilGL.findAccountVOByPrimaryKey(rs[j]
						.getPk_accasoa(), voucher.getPrepareddate()
						.toStdString());
				// accvo = new
				// nc.bs.bd.b02.AccsubjBO().findByPrimaryKey(details[i].getPk_accasoa());
				accsubjcache.put(rs[j].getPk_accasoa(), accvo);
			}
			AssVO assvo = matchAssBalance(accvo, rs[j].getAssVos(), voucher);
			UFDouble oldbalance = null;
			UFDouble balance = null;
			UFDouble groupBalance = null;
			UFDouble globalBalance = null;
			if (assvo != null) {
				balabcekey = accvo.getPk_accasoa() + rs[j].getPk_currtype()+assvo.getPk_Checktype()
						+ assvo.getPk_Checkvalue();
				oldbalance = (UFDouble) oldbalancemap.get(balabcekey);
				balance = (UFDouble) balancemap.get(balabcekey);
			}
			debitbalance = debitbalance
					.sub(oldbalance == null ? new UFDouble(0) : oldbalance);
			debitbalance = debitbalance.add(balance == null ? new UFDouble(0)
					: balance);
			if (accvo != null
					&& accvo.getBalanorient().intValue() == IVoucherConst.ACCASOA_BALANCE_DEBIT) {
				if (debitbalance.compareTo(UFDouble.ZERO_DBL) < 0) {
					oprs = new OperationResultVO();
					oprs.m_intSuccess = infoLevel;
					oprs.m_strPK = voucher.getPk_voucher();
					oprs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000577")/*
																 * @res " 科目"
																 */
							+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("20021005",
							"UPP20021005-000343")/*
												 * @res
												 * " 币种"
												 */+"["+Currency.getCurrInfo(rs[j].getPk_currtype()).getCode()
												 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("20021005",
							"UPP20021005-000202")/*
												 * @res
												 * "辅助核算"
												 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
					vecresult.addElement(oprs);
				}
			} else {
				if (debitbalance.compareTo(UFDouble.ZERO_DBL) > 0) {
					oprs = new OperationResultVO();
					oprs.m_intSuccess = infoLevel;
					oprs.m_strPK = voucher.getPk_voucher();
					oprs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000577")/*
																 * @res " 科目"
																 */
							+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("20021005",
							"UPP20021005-000343")/*
												 * @res
												 * " 币种"
												 */+"["+Currency.getCurrInfo(rs[j].getPk_currtype()).getCode()
												 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
													.getStrByID("20021005",
															"UPP20021005-000202")/*
																				 * @res
																				 * "辅助核算"
																				 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
					vecresult.addElement(oprs);
				}
			}
		}

		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;

	}

	/**
	 * 根据科目和ass数组返回进行余额控制的ass
	 * 
	 * @param accsubj
	 * @param ass
	 * @return
	 */
	private AssVO matchAssBalance(AccountVO accsubj, AssVO[] ass, VoucherVO vo) {
		String pk_checktype = null;
		if (accsubj != null) {
			// if
			// (AccAssGL.getAccAssVOsByAccount(accsubj,vo.getYear(),vo.getPeriod())
			// != null &&
			// AccAssGL.getAccAssVOsByAccount(accsubj,vo.getYear(),vo.getPeriod()).size()
			// > 0) {
			// Vector assvec =
			// AccAssGL.getAccAssVOsByAccount(accsubj,vo.getYear(),vo.getPeriod());
			if (accsubj.getAccass() != null && accsubj.getAccass().length > 0) {
				AccAssVO[] asvos = accsubj.getAccass();
				for (AccAssVO asvo : asvos) {
					if (asvo.getIsbalancecontrol().booleanValue()) {
						pk_checktype = asvo.getPk_entity();
						break;
					}
				}
				if (pk_checktype != null) {
					for (int i = 0; ass != null && ass.length > 0
							&& i < ass.length; i++) {
						AssVO assVO = ass[i];
						if (assVO.getPk_Checktype().equals(pk_checktype)) {
							return assVO;
						}

					}
				} else {
					return null;
				}
			}
		} else {
			return null;
		}

		return null;
	}

	/**
	 * 余额控制 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkBalanceControl(VoucherVO voucher,
			HashMap accsubjcache, GLParameterVO param, Integer controlmode)
			throws Exception {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;

		HashMap balancemap = new HashMap();
//		HashMap groupBalancemap = new HashMap();
//		HashMap globalBalancemap = new HashMap();
		HashMap tmp_subjmap = new HashMap();
		OperationResultVO[] result = null;
		Vector vecresult = new Vector();
		DetailVO[] details = voucher.getDetails();
		details = filterDetailForBalanceCtrl(details);
		DetailVO[] tmp_sumdetails = null;
		Vector vecdetails = new Vector();
		Vector vecSubjs = new Vector();
		UFDouble debitbalance = null;
//		UFDouble groupDebitbalance = null;
//		UFDouble globalDebitbalance = null;
		
		//存储币种主键，用于校验按原币余额控制
		Set<String> currtypeSet = new HashSet<String>();
		int infoLevel = 0;
		OperationResultVO oprs = null;

		switch (controlmode.intValue()) {
		case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
			infoLevel = 1;
			break;
		}
		case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
			infoLevel = 2;
			break;
		}
		default:
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000574")/*
																				 * @res
																				 * "无法识别的控制类型，可能是软件版本不一致。"
																				 */);
		}

		if (details != null)
			for (int i = 0; i < details.length; i++) {
				AccountVO accvo = (AccountVO) accsubjcache.get(details[i]
						.getPk_accasoa());
				if (accvo == null) {
					// accvo.setPk_accasoa(details[i].getPk_accasoa());
					accvo = AccountUtilGL.findByPrimaryKey(details[i]
							.getPk_accasoa());
					// new nc.bs.bd.b02.AccsubjBO().queryByVO(accvo, new
					// Boolean(true))[0];
					// accvo = new
					// nc.bs.bd.b02.AccsubjBO().findByPrimaryKey(details[i].getPk_accasoa());
					accsubjcache.put(details[i].getPk_accasoa(), accvo);
				}
				if (accvo != null
						&& !(accvo.getBalanflag() == null || !accvo
								.getBalanflag().booleanValue())) {
					DetailVO tmp_detail = (DetailVO) details[i].clone();
					tmp_detail.setUserData(null);
					vecdetails.addElement(tmp_detail);
				}
				currtypeSet.add(details[i].getPk_currtype());
			}
		if (vecdetails.size() > 0) {
			DetailVO[] t_details = new DetailVO[vecdetails.size()];
			vecdetails.copyInto(t_details);
			tmp_sumdetails = nc.vo.gl.vouchertools.DetailTool.sumDetails(
					t_details, new int[] { VoucherKey.D_PK_ACCSUBJ,VoucherKey.D_PK_CURRTYPE });
		}
		// AccperiodBO periodbo = new AccperiodBO();
		// nc.vo.bd.period.AccperiodVO accperiod =
		// periodbo.queryByYear(voucher.getPk_accountingbook(),
		// voucher.getYear());
		// // nc.vo.bd.period.AccperiodVO
		// //
		// nowaccperiod=periodbo.queryAccyearByDate(voucher.getPk_accountingbook(),voucher.getPrepareddate());
		// String year = voucher.getYear();
		// String period =
		// accperiod.getVosMonth()[accperiod.getVosMonth().length -
		// 1].getMonth();
		AccountCalendar calendar = CalendarUtilGL
				.getAccountCalendarByAccountBook(voucher.getPk_accountingbook());
		calendar.set(voucher.getYear());
		String year = voucher.getYear();
		String period = calendar.getLastMonthOfCurrentYear().getAccperiodmth();

		if (voucher.getPk_voucher() != null) {
			DetailVO[] t_old_details = new DetailExtendDMO()
					.queryByVoucherPks(new String[] { voucher.getPk_voucher() });
			t_old_details = filterDetailForBalanceCtrl(t_old_details);
			getAccountMap(t_old_details, accsubjcache);
			Vector vecolddetails = new Vector();
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {
					AccountVO accvo = (AccountVO) accsubjcache
							.get(t_old_details[i].getPk_accasoa());
					if (accvo == null) {
						// accvo.setPk_accasoa(details[i].getPk_accasoa());
						accvo = AccountUtilGL.findAccountVOByPrimaryKey(
								t_old_details[i].getPk_accasoa(), voucher
										.getPrepareddate().toStdString());
						// accvo = new
						// nc.bs.bd.b02.AccsubjBO().findByPrimaryKey(t_old_details[i].getPk_accasoa());
						accsubjcache.put(t_old_details[i].getPk_accasoa(),
								accvo);
					}
					if (accvo != null
							&& !(accvo.getBalanflag() == null || !accvo
									.getBalanflag().booleanValue())) {
						vecolddetails.addElement(t_old_details[i].clone());
					}
					currtypeSet.add(t_old_details[i].getPk_currtype());
				}
			}
			if (vecolddetails.size() > 0) {
				t_old_details = new DetailVO[vecolddetails.size()];
				vecolddetails.copyInto(t_old_details);
				t_old_details = nc.vo.gl.vouchertools.DetailTool.sumDetails(
						t_old_details, new int[] { VoucherKey.D_PK_ACCSUBJ,VoucherKey.D_PK_CURRTYPE });
			} else {
				t_old_details = null;
			}
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {
					
					String oldKey = t_old_details[i].getPk_accasoa()+t_old_details[i].getPk_currtype();
					debitbalance = (UFDouble) balancemap.get(oldKey);
					debitbalance = (debitbalance == null ? new UFDouble(0)
							: debitbalance).sub(
							t_old_details[i].getDebitamount()).add(
							t_old_details[i].getCreditamount());
					balancemap.put(oldKey,debitbalance);

					if (tmp_subjmap.get(t_old_details[i].getPk_accasoa()) == null) {
						vecSubjs.addElement(t_old_details[i].getPk_accasoa());
						tmp_subjmap.put(t_old_details[i].getPk_accasoa(),
								t_old_details[i].getPk_accasoa());
					}
				}
			}
		}
		String[] pk_accsubjs = null;
		if (tmp_sumdetails != null)
			for (int i = 0; i < tmp_sumdetails.length; i++) {
				if (tmp_subjmap.get(tmp_sumdetails[i].getPk_accasoa()) == null) {
					vecSubjs.addElement(tmp_sumdetails[i].getPk_accasoa());
					tmp_subjmap.put(tmp_sumdetails[i].getPk_accasoa(),
							tmp_sumdetails[i].getPk_accasoa());
				}
			}
		if (vecSubjs.size() > 0) {

			pk_accsubjs = new String[vecSubjs.size()];
			vecSubjs.copyInto(pk_accsubjs);
		}
		if (pk_accsubjs == null || pk_accsubjs.length == 0)
			return null;
		nc.vo.glcom.balance.GlQueryVO qryvo = new nc.vo.glcom.balance.GlQueryVO();
		qryvo.setPk_account(pk_accsubjs);
		qryvo.setYear(year);
		qryvo.setPeriod(period);
		qryvo.setIncludeUnTallyed(true);
		qryvo.setpk_accountingbook(new String[] { voucher
				.getPk_accountingbook() });
		qryvo.setBaseAccountingbook(voucher.getPk_accountingbook());
		qryvo
				.setGroupFields(new int[] { nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE });

		// if (voucher.getVoucherkind().intValue() == 2) {
		// qryvo.setSubjVerisonPeriod(voucher.getUserData().toString());
		// } else {
		// qryvo.setSubjVerisonPeriod(voucher.getPeriod());
		// }
		if (voucher.getVoucherkind().intValue() == 2) {

			// //////////added by chengsc 2008-04-14
			// /////////////////////////////
			// 设置科目检查的期间数值，将subversionperiod值设置为当前期间
			String pk_orgbook = voucher.getPk_accountingbook();
			String[] str = GLPubProxy.getRemoteGlPara().getStartPeriod(
					pk_orgbook, "2002");
			String strPeriod = str[0].equals(year) ? str[1] : calendar
					.getFirstMonthOfCurrentYear().getAccperiodmth();
			qryvo.setSubjVerisonPeriod(strPeriod);
			calendar.set(year, strPeriod);
			qryvo.setSubjVersion(calendar.getMonthVO().getBegindate().toStdString());
			// /////////////////////////////////////////////////////////////////////
		} else {
			calendar.setDate(voucher.getPrepareddate());
			qryvo.setSubjVerisonPeriod(calendar.getMonthVO().getAccperiodmth());
			qryvo.setSubjVersion(voucher.getPrepareddate().toStdString());
		}
		qryvo.setSubjVersionYear(year);
		qryvo.setUseSubjVersion(true);

		GlBalanceVO[] rs = NCLocator.getInstance()
				.lookup(ICommAccBookPub.class).getEndBalance(qryvo);
		if (rs != null) {
			UFDouble t_debitbalance = null;
			for (int i = 0; i < rs.length; i++) {
				String balanceKey = rs[i].getPk_accasoa()+rs[i].getPk_currtype();
				debitbalance = (rs[i].getDebitamount() == null ? new UFDouble(
						0)
						: rs[i].getDebitamount()).sub(rs[i]
						.getCreditamount() == null ? new UFDouble(0)
						: rs[i].getCreditamount());
				t_debitbalance = (UFDouble) balancemap.get(balanceKey);
				t_debitbalance = (t_debitbalance == null ? new UFDouble(0)
						: t_debitbalance).add(debitbalance);
				balancemap.put(balanceKey, t_debitbalance);
			}
		}
		if (tmp_sumdetails != null) {
			for (int i = 0; i < tmp_sumdetails.length; i++) {
				String newKey = tmp_sumdetails[i].getPk_accasoa()+tmp_sumdetails[i].getPk_currtype();
				debitbalance = (UFDouble) balancemap.get(newKey);
				debitbalance = (debitbalance == null ? new UFDouble(0)
						: debitbalance).add(
						tmp_sumdetails[i].getDebitamount()).sub(
						tmp_sumdetails[i].getCreditamount());
				balancemap.put(newKey, debitbalance);
			}
		}

		for (int i = 0; i < pk_accsubjs.length; i++) {
			
			String[] currtypeStrs = currtypeSet.toArray(new String[0]);
			
			for(int j=0;j<currtypeStrs.length;j++) {
				
				String laseKey = pk_accsubjs[i]+currtypeStrs[j];	
				
				debitbalance = (UFDouble) balancemap.get(laseKey);
				if (debitbalance == null ) {
					continue;
				}
				AccountVO accvo = (AccountVO) accsubjcache.get(pk_accsubjs[i]);
				if (accvo == null) {
					accvo = new AccountVO();
					accvo.setPk_accasoa(details[i].getPk_accasoa());
					accvo = AccountUtilGL.findAccountVOByPrimaryKey(details[i]
					                                                        .getPk_accasoa(), voucher.getPrepareddate()
					                                                        .toStdString());
					// accvo = new
					// nc.bs.bd.b02.AccsubjBO().findByPrimaryKey(details[i].getPk_accasoa());
					accsubjcache.put(details[i].getPk_accasoa(), accvo);
				}
				if (accvo != null
						&& accvo.getBalanorient().intValue() == IVoucherConst.ACCASOA_BALANCE_DEBIT) {
					if (debitbalance.compareTo(UFDouble.ZERO_DBL) < 0) {
						oprs = new OperationResultVO();
						oprs.m_intSuccess = infoLevel;
						oprs.m_strPK = voucher.getPk_voucher();
						oprs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountBalanceCtrl)
						+ nc.bs.ml.NCLangResOnserver.getInstance()
						.getStrByID("20021005",
						"UPP20021005-000577")/*
						 * @res " 科目"
						 */
						+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
						.getStrByID("20021005",
						"UPP20021005-000343")/*
											 * @res
											 * " 币种"
											 */+"["+Currency.getCurrInfo(currtypeStrs[j]).getCode()+"]";
						vecresult.addElement(oprs);
					}
				} else {
					if (debitbalance.compareTo(UFDouble.ZERO_DBL) > 0) {
						oprs = new OperationResultVO();
						oprs.m_intSuccess = infoLevel;
						oprs.m_strPK = voucher.getPk_voucher();
						oprs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountBalanceCtrl)
						+ nc.bs.ml.NCLangResOnserver.getInstance()
						.getStrByID("20021005",
						"UPP20021005-000577")/*
						 * @res " 科目"
						 */
						+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
						.getStrByID("20021005",
						"UPP20021005-000343")/*
											 * @res
											 * " 币种"
											 */+"["+Currency.getCurrInfo(currtypeStrs[j]).getCode()+"]";
						vecresult.addElement(oprs);
					}
				}
			}
		}
		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;
	}


	private boolean isAssEqual(AssVO assVo1, AssVO assVo2) {
		if (!assVo1.getPk_Checktype().equals(assVo2.getPk_Checktype())) {
			return false;
		}

		String pk_Checkvalue1 = assVo1.getPk_Checkvalue();
		String pk_Checkvalue2 = assVo2.getPk_Checkvalue();

		if (pk_Checkvalue1 == null)
			pk_Checkvalue1 = "~";
		if (pk_Checkvalue2 == null)
			pk_Checkvalue2 = "~";
		return pk_Checkvalue1.equals(pk_Checkvalue2);
	}

	/**
	 * 根据科目和ass数组返回进行余额控制的ass
	 * 
	 * @param accsubj
	 * @param ass
	 * @return
	 */
	private AssVO matchAssBalance(AccAssVO accAssVo, AssVO[] ass) {

		if (accAssVo == null || ass == null)
			return null;

		if (!accAssVo.getIsbalancecontrol().booleanValue())
			return null;

		String pk_checktype = accAssVo.getPk_entity();
		if (pk_checktype != null) {
			for (int i = 0; i < ass.length; i++) {
				AssVO assVO = ass[i];
				if (assVO.getPk_Checktype().equals(pk_checktype)) {
					return assVO;
				}
			}
		}
		return null;
	}

	/**
	 * 科目辅助余额控制 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkAssBalanceControlNew(VoucherVO voucher,
			HashMap accsubjcache, GLParameterVO param, Integer controlmode)
			throws Exception {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;

		HashMap balancemap = new HashMap();
//		HashMap groupBalancemap = new HashMap();
//		HashMap globalBalancemap = new HashMap();
		HashMap oldbalancemap = new HashMap();
//		HashMap oldGroupBalancemap = new HashMap();
//		HashMap oldGlobalBalancemap = new HashMap();
		HashMap<String,Vector> accsubjmap = new HashMap<String,Vector>();
		HashMap assmap = new HashMap();
		HashMap<String, AssVO> asschecktypemap = new HashMap<String, AssVO>();
		Vector vecresult = new Vector();
		OperationResultVO[] result = null;
		AssVO[] queryass = null;

		String balabcekey = null;
		UFDouble debitbalance = null;
//		UFDouble groupDebitbalance = null;
//		UFDouble globalDebitbalance = null;

		OperationResultVO oprs = null;
		int infoLevel = 0;

		switch (controlmode.intValue()) {
		case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
			infoLevel = 1;
			break;
		}
		case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
			infoLevel = 2;
			break;
		}
		default:
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000574")/*
																				 * @res
																				 * "无法识别的控制类型，可能是软件版本不一致。"
																				 */);
		}

		DetailVO[] details = voucher.getDetails();
		details = filterDetailForBalanceCtrl(details);
		DetailVO[] t_old_details = voucher.getDetails();
		details = catAss(details);

		for (int i = 0; details != null && i < details.length; i++) {
			AccountVO accvo = (AccountVO) accsubjcache.get(details[i]
					.getPk_accasoa());
			if (accvo == null) {
				accvo = new AccountVO();
				accvo.setPk_accasoa(details[i].getPk_accasoa());
				// 60x accvo.setPk_glorgbook(details[i].getPk_glorgbook());
				accvo = AccountUtilGL.findAccountVOByPrimaryKey(details[i]
						.getPk_accasoa(), voucher.getPrepareddate()
						.toStdString());
				accsubjcache.put(details[i].getPk_accasoa(), accvo);

				// hurh
				Vector<AccAssVO> subassvec = AccAssGL.getAccAssVOsByAccount(
						voucher.getPk_accountingbook(), accvo, voucher
								.getPrepareddate().toStdString());
				if (subassvec != null && subassvec.size() > 0) {
					accvo.setAccass(subassvec.toArray(new AccAssVO[0]));
				}
			}
			if (accvo != null) {
				// Vector subassvec =
				// AccAssGL.getAccAssVOsByAccount(accvo,voucher.getYear(),voucher.getPeriod());
				// hurh
				AccAssVO[] accass = accvo.getAccass();
				if (accass != null && accass.length > 0) {
					//上面1651行“details = catAss(details);”已经处理过辅助核算了。这里循环重复取值造成效率低下，且是无意义的重复操作，所以注掉 
					//add by shipl 2015-9-1 9:01:57
//					if (details[i].getAss() != null) {
//						details[i].setAss(((IFreevaluePub) NCLocator
//								.getInstance().lookup(
//										IFreevaluePub.class.getName()))
//								.queryAssvosByid(details[i].getAssid(),
//										Module.GL));
//					}
					
					for(int j=0;j<accass.length;j++) {
						AssVO assvo = matchAssBalance(accass[j], details[i].getAss());
						if (assvo == null) {
							continue;
						} else {
							balabcekey = details[i].getPk_accasoa()+details[i].getPk_currtype()
							+ assvo.getPk_Checktype()
							+ assvo.getPk_Checkvalue();
							debitbalance = balancemap.get(balabcekey) == null ? new UFDouble(
									0)
							: (UFDouble) balancemap.get(balabcekey);
									debitbalance = (debitbalance == null ? new UFDouble(0)
									: debitbalance)
									.add((details[i].getDebitamount() == null ? new UFDouble(
											0)
									: details[i].getDebitamount())
									.sub(details[i].getCreditamount() == null ? new UFDouble(
											0)
									: details[i]
									          .getCreditamount()));
									balancemap.put(balabcekey, debitbalance);
									if (accsubjmap.get(accvo.getPk_accasoa()) == null) {
										Vector vector = new Vector();
										vector.add(assvo);
										
										accsubjmap.put(accvo.getPk_accasoa(), vector);
									}
									else {
										Vector vector = accsubjmap.get(accvo.getPk_accasoa());
										boolean equalFlag = false;
										for(int row=0;row<vector.size();row++) {
											Object object = vector.get(row);
											AssVO oldAssVo = (AssVO) object;
											if(isAssEqual(oldAssVo, assvo)) {
												equalFlag = true;
											}
										}
										//如果辅助核算相同则不添加
										if(!equalFlag)
											vector.add(assvo);
									}
									if (assmap.get(assvo.getPk_Checktype()
											+ assvo.getPk_Checkvalue()) == null) {
										assmap.put(assvo.getPk_Checktype()
												+ assvo.getPk_Checkvalue(), assvo);
									}
						}
					}
				}
			}
		}

		if (voucher.getPk_voucher() != null) {
			t_old_details = new DetailExtendDMO()
					.queryByVoucherPks(new String[] { voucher.getPk_voucher() });
			t_old_details = filterDetailForBalanceCtrl(t_old_details);
			getAccountMap(t_old_details, accsubjcache);
			t_old_details = catAss(t_old_details);
			Vector vecolddetails = new Vector();
			if (t_old_details != null) {
				for (int i = 0; i < t_old_details.length; i++) {

					AccountVO accvo = (AccountVO) accsubjcache
							.get(t_old_details[i].getPk_accasoa());
					if (accvo == null) {
						accvo = new AccountVO();
						accvo.setPk_accasoa(t_old_details[i].getPk_accasoa());
						// 60x
						// accvo.setPk_glorgbook(t_old_details[i].getPk_glorgbook());
						accvo = AccountUtilGL.findAccountVOByPrimaryKey(
								t_old_details[i].getPk_accasoa(), voucher
										.getPrepareddate().toStdString());
						accsubjcache.put(t_old_details[i].getPk_accasoa(),
								accvo);

						// hurh
						Vector<AccAssVO> subassvec = AccAssGL
								.getAccAssVOsByAccount(voucher
										.getPk_accountingbook(), accvo, voucher
										.getPrepareddate().toStdString());
						if (subassvec != null && subassvec.size() > 0) {
							accvo.setAccass(subassvec.toArray(new AccAssVO[0]));
						}
					}
					if (accvo != null) {
						// Vector subassvec =
						// AccAssGL.getAccAssVOsByAccount(accvo,voucher.getYear(),voucher.getPeriod());
						AccAssVO[] accass = accvo.getAccass();
						if (accass != null && accass.length > 0) {

							for (int j = 0; j < accass.length; j++) {
								AssVO assvo = matchAssBalance(accass[j],
										t_old_details[i].getAss());
								if (assvo == null) {
									continue;
								} else {
									balabcekey = t_old_details[i]
											.getPk_accasoa()
											+ t_old_details[i].getPk_currtype()
											+ assvo.getPk_Checktype()
											+ assvo.getPk_Checkvalue();
									debitbalance = oldbalancemap
											.get(balabcekey) == null ? new UFDouble(
											0) : (UFDouble) oldbalancemap
											.get(balabcekey);
									debitbalance = debitbalance
											.add(((t_old_details[i]
													.getDebitamount() == null ? new UFDouble(
													0) : t_old_details[i]
													.getDebitamount()).sub(t_old_details[i]
													.getCreditamount() == null ? new UFDouble(
													0) : t_old_details[i]
													.getCreditamount())));
									oldbalancemap.put(balabcekey, debitbalance);
									if (accsubjmap.get(accvo.getPk_accasoa()) == null) {
										Vector vector = new Vector();
										vector.add(assvo);
										accsubjmap.put(accvo.getPk_accasoa(),
												vector);
									} else {
										Vector vector = accsubjmap.get(accvo
												.getPk_accasoa());
										boolean equalFlag = false;
										for (int row = 0; row < vector.size(); row++) {
											Object object = vector.get(row);
											AssVO oldAssVo = (AssVO) object;
											if(isAssEqual(oldAssVo, assvo)) {
												equalFlag = true ;
											}
										}
										// 如果辅助核算相同则不添加
										if (!equalFlag)
											vector.add(assvo);

									}
								}
								if (assmap.get(assvo.getPk_Checktype()
										+ assvo.getPk_Checkvalue()) == null) {
									assmap.put(
											assvo.getPk_Checktype()
													+ assvo.getPk_Checkvalue(),
											assvo);
								}
							}
						}
					}

				}
			}
		}

		AccountCalendar calendar = CalendarUtilGL
				.getAccountCalendarByAccountBook(voucher.getPk_accountingbook());
		calendar.set(voucher.getYear());
		String year = voucher.getYear();
		String period = calendar.getLastMonthOfCurrentYear().getAccperiodmth();

		String[] pk_accsubjs = null;
		if (accsubjmap != null && accsubjmap.size() > 0) {
			pk_accsubjs = new String[accsubjmap.size()];
			pk_accsubjs = (String[]) accsubjmap.keySet().toArray(pk_accsubjs);
		}

		GlBalanceVO[] rs = null;
		Vector<GlBalanceVO> glv = new Vector<GlBalanceVO>();

		if (pk_accsubjs != null && pk_accsubjs.length > 0)
			for (int j = 0; j < pk_accsubjs.length; j++) {
				Vector<AssVO> assvo = null;

				if (accsubjmap.get(pk_accsubjs[j]) instanceof Vector) {
					assvo = (Vector<AssVO>) accsubjmap.get(pk_accsubjs[j]);
					
				} 
				for (Iterator iter = assvo.iterator(); iter.hasNext();) {
					AssVO element = (AssVO) iter.next();
					nc.vo.glcom.balance.GlQueryVO qryvo = new nc.vo.glcom.balance.GlQueryVO();
					qryvo.setPk_account(new String[] { pk_accsubjs[j] });

					qryvo.setAssVos(new AssVO[] { element });

					qryvo.setYear(year);
					qryvo.setPeriod(period);
					qryvo.setIncludeUnTallyed(true);
					qryvo.setpk_accountingbook(new String[] { voucher
							.getPk_accountingbook() });
					qryvo.setBaseAccountingbook(voucher.getPk_accountingbook());
					if (voucher.getVoucherkind().intValue() == 2) {
						if (voucher.getPeriod().equals("00")) {
							qryvo.setSubjVerisonPeriod(calendar
									.getFirstMonthOfCurrentYear()
									.getAccperiodmth());
						} else {
							qryvo.setSubjVerisonPeriod(voucher.getPeriod());
						}
						calendar.set(year, qryvo.getSubjVerisonPeriod());
						qryvo.setSubjVersion(calendar.getMonthVO().getBegindate().toStdString());
					} else {
						calendar.setDate(voucher.getPrepareddate());
						qryvo.setSubjVerisonPeriod(calendar.getMonthVO()
								.getAccperiodmth());
						qryvo.setSubjVersion(voucher.getPrepareddate()
								.toStdString());
					}
					qryvo.setSubjVersionYear(year);
					qryvo.setUseSubjVersion(true);
					
					qryvo.setGroupFields(new int[] {
							nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE,
							nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID });
					GlBalanceVO[] rs1 = NCLocator.getInstance().lookup(
							ICommAccBookPub.class).getEndBalance(qryvo);

					// ??
					if (rs1 != null && rs1.length > 0) {
						nc.ui.glcom.balance.GlAssDeal objTemp = new nc.ui.glcom.balance.GlAssDeal();
						// 从查询VO中取核算类型主键数组
						String[] strType = null;
						nc.vo.glcom.ass.AssVO[] assvos = qryvo.getAssVos();
						if (assvos != null && assvos.length != 0) {
							strType = new String[assvos.length];
							for (int i = 0; i < assvos.length; i++)
								strType[i] = assvos[i].getPk_Checktype();
						}

						objTemp.setMatchingIndex(GlBalanceKey.GLBALANCE_ASSID);
						objTemp.setAppendIndex(GlBalanceKey.GLBALANCE_ASSVOS);

						objTemp.dealWith(rs1, strType); // 处理加工

						// if (isSummed)//需要对同辅助项记录合并吗？
						rs1 = combineAss(rs1, qryvo);
						for (int i = 0; i < rs1.length; i++) {
							GlBalanceVO glb = rs1[i];
							glv.addElement(glb);
						}
					}
				}

			}
		rs = new GlBalanceVO[glv.size()];
		rs = (GlBalanceVO[]) glv.toArray(rs);

		for (int j = 0; j < rs.length; j++) {
			debitbalance = (rs[j].getDebitamount() == null ? new UFDouble(
					0) : rs[j].getDebitamount()).sub(rs[j]
					.getCreditamount() == null ? new UFDouble(0) : rs[j]
					.getCreditamount());
			AccountVO accvo = (AccountVO) accsubjcache.get(rs[j]
					.getPk_accasoa());
			if (accvo == null) {
				accvo = new AccountVO();
				accvo.setPk_accasoa(rs[j].getPk_accasoa());
				// accvo.setPk_glorgbook(rs[j].getPk_glorgbook());
				accvo = AccountUtilGL.findAccountVOByPrimaryKey(rs[j]
						.getPk_accasoa(), voucher.getPrepareddate()
						.toStdString());
				// accvo = new
				// nc.bs.bd.b02.AccsubjBO().findByPrimaryKey(details[i].getPk_accasoa());
				accsubjcache.put(rs[j].getPk_accasoa(), accvo);
			}
			//只有一个辅助核算
			AssVO assvo = rs[j].getAssVos()[0];
			UFDouble oldbalance = null;
//			UFDouble oldGroupBalance = null;
//			UFDouble oldGlobalBalance = null;
			UFDouble balance = null;
//			UFDouble groupBalance = null;
//			UFDouble globalBalance = null;
			if (assvo != null) {
				balabcekey = accvo.getPk_accasoa() +rs[j].getPk_currtype()+ assvo.getPk_Checktype()
						+ assvo.getPk_Checkvalue();
				oldbalance = oldbalancemap.get(balabcekey) == null ? UFDouble.ZERO_DBL
						: (UFDouble) oldbalancemap.get(balabcekey);
				balance = (UFDouble) balancemap.get(balabcekey) == null ? UFDouble.ZERO_DBL
						: (UFDouble) balancemap.get(balabcekey);
				balancemap.remove(balabcekey);
			}
			debitbalance = debitbalance
					.sub(oldbalance == null ? new UFDouble(0) : oldbalance);
			debitbalance = debitbalance.add(balance == null ? new UFDouble(0)
					: balance);
			if (accvo != null
					&& accvo.getBalanorient().intValue() == IVoucherConst.ACCASOA_BALANCE_DEBIT) {
				if (debitbalance.compareTo(UFDouble.ZERO_DBL) < 0) {
					if(StrTools.isEmptyStr(assvo.getCheckvaluename())){
						IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(assvo.getM_classid());
						if(accessor!=null)
							assvo.setCheckvaluename(GLGeneralAccessUtil.getIBDDataName(assvo.getM_classid(), accessor.getDocByPk(assvo.getPk_Checkvalue())));
					}
					oprs = new OperationResultVO();
					oprs.m_intSuccess = infoLevel;
					oprs.m_strPK = voucher.getPk_voucher();
					oprs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000577")/*
																 * @res " 科目"
																 */
							+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("20021005",
							"UPP20021005-000343")/*
												 * @res
												 * " 币种"
												 */+"["+Currency.getCurrInfo(rs[j].getPk_currtype()).getCode()
												 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
													.getStrByID("20021005",
															"UPP20021005-000202")/*
																				 * @res
																				 * "辅助核算"
																				 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
					vecresult.addElement(oprs);
				}
			} else {
				if (debitbalance.compareTo(UFDouble.ZERO_DBL) > 0) {
					if(StrTools.isEmptyStr(assvo.getCheckvaluename())){
						IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(assvo.getM_classid());
						if(accessor!=null)
							assvo.setCheckvaluename(GLGeneralAccessUtil.getIBDDataName(assvo.getM_classid(), accessor.getDocByPk(assvo.getPk_Checkvalue())));
					}
					oprs = new OperationResultVO();
					oprs.m_intSuccess = infoLevel;
					oprs.m_strPK = voucher.getPk_voucher();
					oprs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("20021005",
											"UPP20021005-000577")/*
																 * @res " 科目"
																 */
							+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("20021005",
							"UPP20021005-000343")/*
												 * @res
												 * " 币种"
												 */+"["+Currency.getCurrInfo(rs[j].getPk_currtype()).getCode()
												 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
													.getStrByID("20021005",
															"UPP20021005-000202")/*
																				 * @res
																				 * "辅助核算"
																				 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
					vecresult.addElement(oprs);
				}
			}

		}
		if (rs == null || rs.length == 0 || balancemap.size() > 0) {
			if (balancemap.size() > 0) {
				for (int i = 0; details != null && i < details.length; i++) {
					AccountVO accvo = (AccountVO) accsubjcache.get(details[i]
							.getPk_accasoa());
					if (accvo == null) {
						accvo = new AccountVO();
						accvo.setPk_accasoa(details[i].getPk_accasoa());
						// accvo.setPk_glorgbook(details[i].getPk_glorgbook());
						accvo = AccountUtilGL.findAccountVOByPrimaryKey(
								details[i].getPk_accasoa(), voucher
										.getPrepareddate().toStdString());
						accsubjcache.put(details[i].getPk_accasoa(), accvo);

						// hurh
						Vector<AccAssVO> subassvec = AccAssGL
								.getAccAssVOsByAccount(voucher
										.getPk_accountingbook(), accvo, voucher
										.getPrepareddate().toStdString());
						if (subassvec != null && subassvec.size() > 0) {
							accvo.setAccass(subassvec.toArray(new AccAssVO[0]));
						}
					}
					if (accvo != null) {
						// Vector subassvec =
						// AccAssGL.getAccAssVOsByAccount(accvo,voucher.getYear(),voucher.getPeriod());
						// hurh
						AccAssVO[] accass = accvo.getAccass();
						if (accass != null && accass.length > 0) {
							if (details[i].getAss() == null) {
								details[i].setAss(((IFreevaluePub) NCLocator
										.getInstance().lookup(
												IFreevaluePub.class.getName()))
										.queryAssvosByid(details[i].getAssid(),
												Module.GL));
							}
							
							for (int j = 0; j < accass.length; j++) {
								AssVO assvo = matchAssBalance(accass[j],
										details[i].getAss());
								if (assvo == null) {
									continue;
								} else {
								balabcekey = details[i].getPk_accasoa()+details[i].getPk_currtype()
										+ assvo.getPk_Checktype()
										+ assvo.getPk_Checkvalue();
								debitbalance = balancemap.get(balabcekey) == null ? new UFDouble(
										0)
										: (UFDouble) balancemap.get(balabcekey);
								if (accvo != null
										&& accvo.getBalanorient().intValue() == IVoucherConst.ACCASOA_BALANCE_DEBIT) {
									if (debitbalance
											.compareTo(UFDouble.ZERO_DBL) < 0) {
										if(StrTools.isEmptyStr(assvo.getCheckvaluename())){
											IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(assvo.getM_classid());
											if(accessor!=null)
												assvo.setCheckvaluename(GLGeneralAccessUtil.getIBDDataName(assvo.getM_classid(), accessor.getDocByPk(assvo.getPk_Checkvalue())));
										}
										oprs = new OperationResultVO();
										oprs.m_intSuccess = infoLevel;
										oprs.m_strPK = voucher.getPk_voucher();
										oprs.m_strDescription = new VoucherCheckMessage()
												.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
												+ nc.bs.ml.NCLangResOnserver
														.getInstance()
														.getStrByID("20021005",
																"UPP20021005-000577")/*
																					 * @res
																					 * "
																					 * 科目
																					 * "
																					 */
												+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
												.getStrByID("20021005",
												"UPP20021005-000343")/*
																	 * @res
																	 * " 币种"
																	 */+"["+Currency.getCurrInfo(details[i].getPk_currtype()).getCode()
																	 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
													.getStrByID("20021005",
															"UPP20021005-000202")/*
																				 * @res
																				 * "辅助核算"
																				 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
										vecresult.addElement(oprs);
									}
								} else {
									if (debitbalance
											.compareTo(UFDouble.ZERO_DBL) > 0) {
										if(StrTools.isEmptyStr(assvo.getCheckvaluename())){
											IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(assvo.getM_classid());
											if(accessor!=null)
												assvo.setCheckvaluename(GLGeneralAccessUtil.getIBDDataName(assvo.getM_classid(), accessor.getDocByPk(assvo.getPk_Checkvalue())));
										}
										oprs = new OperationResultVO();
										oprs.m_intSuccess = infoLevel;
										oprs.m_strPK = voucher.getPk_voucher();
										oprs.m_strDescription = new VoucherCheckMessage()
												.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalAccountAssBalanceCtrl)
												+ nc.bs.ml.NCLangResOnserver
														.getInstance()
														.getStrByID("20021005",
																"UPP20021005-000577")/*
																					 * @res
																					 * "
																					 * 科目
																					 * "
																					 */
												+ "["+accvo.getCode()+"] "+nc.bs.ml.NCLangResOnserver.getInstance()
												.getStrByID("20021005",
												"UPP20021005-000343")/*
																	 * @res
																	 * " 币种"
																	 */+"["+Currency.getCurrInfo(details[i].getPk_currtype()).getCode()
																	 +"] "+nc.bs.ml.NCLangResOnserver.getInstance()
													.getStrByID("20021005",
															"UPP20021005-000202")/*
																				 * @res
																				 * "辅助核算"
																				 */+"["+assvo.getChecktypename()+":"+assvo.getCheckvaluename()+"]";
										vecresult.addElement(oprs);
									}
								}
							}
						}
						}
					}
				}
			}
		}

		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-26 10:34:37)
	 * 
	 * @return java.lang.Boolean
	 * @param detail
	 *            nc.vo.gl.pubvoucher.DetailVO
	 * @param sysid
	 *            java.lang.String
	 * @param i
	 *            int
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	protected OperationResultVO[] checkDetail(VoucherVO voucher, String sysid,
			int i, HashMap tempaccsubj, GLParameterVO glparam,Map<String,AssVO[]> assMap,Map<String,List<String>> relationMap) throws Exception {
		OperationResultVO[] result = null;
		voucher.getDetail(i).setDetailindex(Integer.valueOf(i + 1));
		voucher.getDetail(i).setPk_org(voucher.getPk_org());
		voucher.getDetail(i).setPk_accountingbook(voucher.getPk_accountingbook());
		voucher.getDetail(i).setPk_glorg(voucher.getPk_org());
		voucher.getDetail(i).setPk_glbook(voucher.getPk_setofbook());
		DetailVO detail = voucher.getDetail(i);
		result = checkExplanatin(i, result, detail);
		result = checkBusiUnit(i, result, detail); // hurh 校验业务单元非空
		result = checkAccasoa(i, result, detail);
		result = checkLocalAmount(voucher, i, result, detail);
		result = checkCurrtype(voucher, i, glparam, result, detail);
		//校验业务日期
		result = checkVerifyDate(voucher, i, glparam, result, detail);
		
		//是否启用欧盟报表，如果未启用则将vat信息清空
        boolean isEurUse = false;
		AccountingBookVO accountBookVo = AccountBookUtil.getAccountingBookVOByPrimaryKey(voucher.getPk_accountingbook());
		if(accountBookVo != null) {
			String pk_group = accountBookVo.getPk_group();
			isEurUse = GLStartCheckUtil.checkEURStart(pk_group);
		}
		if(isEurUse) {
			result = checkVatDetail(i, result, detail);
		}else {
			detail.setVatdetail(null);
		}
		
		nc.vo.bd.account.AccountVO accvo = null;
		try {
			accvo = (nc.vo.bd.account.AccountVO) tempaccsubj.get(voucher
					.getDetail(i).getPk_accasoa());
		} catch (ClassCastException e) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoPowerSubj)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005", "UPP20021005-000578"/*
															 * @res
															 * "::第{0}条分录{科目主键："
															 */, null,
							new String[] { String.valueOf((i + 1)) })
					+ voucher.getDetail(i).getPk_accasoa() + "}";
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
			return result;
		}

		if (accvo == null) {
			result = checkAccasoaInfo(i, result);
			return result;
		}
		processUnit(voucher, i, accvo);
		result = checkControlSystem(voucher, i, result, accvo);
		result = checkEndAccasoa(i, result, accvo);
		processCashtype(voucher, i, accvo);
		OperationResultVO[] t_result = checkVoucherAbsolutely(voucher, accvo, i);
		result = OperationResultVO.appendResultVO(result, t_result);
		result = checkSealAccasoa(i, result, accvo);
		if (voucher.getDetail(i).getAssid() != null
				&& voucher.getDetail(i).getAssid().trim().equals("")) {
			voucher.getDetail(i).setAssid(null);
			voucher.getDetail(i).setAss(null);
		}
		result = checkAss(voucher, i, glparam, result, accvo);
		result = checkAssNotNull(voucher, i, result, accvo);
		//辅助核算合法性校验
		result = checkAssValidate(voucher, i, result,assMap,relationMap);
		result = checkOccurAmountAccasoa(voucher, i, result, detail, accvo);
		result = checkOutAccasoa(voucher, i, result, accvo);
		
		result = checkCashflow(voucher, i, result);//校验现金流量分析时，现金流量项目是否选择末级
		
		return result;
	}

	/**
	 * 
	 * 校验税务明细
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param i
	 * @param detail
	 * @return
	 * @see
	 * @since V6.1
	 */
	private OperationResultVO[] checkVatDetail(int i,
			OperationResultVO[] result, DetailVO detail) {
		if (detail.getVatdetail() != null
				&& (detail.getVatdetail().getMoneyamount() == null || UFDouble.ZERO_DBL
						.equals(detail.getVatdetail().getMoneyamount()))
				&& (detail.getVatdetail().getTaxamount() == null || UFDouble.ZERO_DBL
						.equals(detail.getVatdetail().getTaxamount()))) {

			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgVatAmountAllZero)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * 
	 * 校验业务单元非空
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param i
	 * @param result
	 * @param detail
	 * @return
	 * @see
	 * @since V6.0
	 * @hurh
	 */
	private OperationResultVO[] checkBusiUnit(int i,
			OperationResultVO[] result, DetailVO detail) {
		if (detail.getPk_unit() == null || detail.getPk_unit().equals("")) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgBusiUnitNotNull)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		if (detail.getPk_unit_v() == null || detail.getPk_unit_v().equals("")) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgBusiUnitVNotNull)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * 辅助核算校验
	 * 
	 * @param voucher
	 * @param i
	 * @param glparam
	 * @param result
	 * @param accvo
	 * @return
	 * @throws GLBusinessException
	 * @throws Exception
	 * @throws NamingException
	 * @throws SystemException
	 */
	private OperationResultVO[] checkAss(VoucherVO voucher, int i,
			GLParameterVO glparam, OperationResultVO[] result,
			nc.vo.bd.account.AccountVO accvo) throws GLBusinessException,
			Exception, NamingException, SystemException {

		// 如果重分类凭证则不检查辅助核算必填项
		boolean isCheckEmpty = true;
		if (SystemtypeConst.RECLASSIFY.equals(voucher.getPk_system())) {
			isCheckEmpty = false;
		}
		// hurh
		AccAssVO[] accass = accvo.getAccass();
		AssVO[] detailass = voucher.getDetail(i).getAss();
		if (accass != null && accass.length > 0) {
			if (voucher.getDetail(i).getAssid() == null) {
				if (detailass == null || detailass.length == 0) {
					for (AccAssVO accAssVO : accass) {
						if (accAssVO.getIsempty() != null
								&& !accAssVO.getIsempty().booleanValue()
								&& isCheckEmpty) {
							OperationResultVO rs = new OperationResultVO();
							rs.m_intSuccess = 2;
							rs.m_strDescription = new VoucherCheckMessage()
									.getVoucherMessage(VoucherCheckMessage.ErrMsgAssIsNull)
									+ nc.bs.ml.NCLangResOnserver.getInstance()
											.getStrByID(
													"20021005",
													"UPP20021005-000575"/*
																		 * @res
																		 * "::第{0}条分录"
																		 */,
													null,
													new String[] { String
															.valueOf(i + 1) });
							rs.m_strDescription = rs.m_strDescription
									+ AccAssItemCache
											.getAccAssitemNameByPK(accAssVO
													.getPk_entity());
							;
							result = OperationResultVO.appendResultVO(result,
									new OperationResultVO[] { rs });
						} else {
							detailass = new nc.vo.glcom.ass.AssVO[accass.length];
							for (int m = 0; m < detailass.length; m++) {
								detailass[m] = new nc.vo.glcom.ass.AssVO();
								detailass[m]
										.setPk_Checktype(((AccAssVO) accass[m])
												.getPk_entity());
							}
							IFreevaluePub freevalue = NCLocator.getInstance()
									.lookup(IFreevaluePub.class);
							;
							voucher.getDetail(i).setAssid(
									freevalue.getAssID(detailass, Boolean.TRUE,
											voucher.getPk_group(), Module.GL));
							voucher.getDetail(i).setAss(detailass);
						}
					}
				} else {
					HashMap tmpassmap = new HashMap();
					for (int j = 0; j < detailass.length; j++) {
						tmpassmap.put(detailass[j].getPk_Checktype(),
								detailass[j]);
					}
					Vector tmp_detailass = new Vector();
					detailass = new nc.vo.glcom.ass.AssVO[tmp_detailass.size()];
					tmp_detailass.copyInto(detailass);
					IFreevaluePub freevalue = NCLocator.getInstance().lookup(
							IFreevaluePub.class);
					voucher.getDetail(i).setAssid(
							freevalue.getAssID(detailass, Boolean.TRUE,
									voucher.getPk_group(), Module.GL));
					voucher.getDetail(i).setAss(detailass);
				}
			} else {
				if (detailass == null) {
					detailass = NCLocator
							.getInstance()
							.lookup(IFreevaluePub.class)
							.queryAssvosByid(voucher.getDetail(i).getAssid(),
									Module.GL);
					voucher.getDetail(i).setAss(detailass);
				}

				if (detailass == null || detailass.length == 0) {
					if (!glparam.Parameter_isfreevalueallownull.booleanValue()
							&& isCheckEmpty) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgAssIsNull)
								+ nc.bs.ml.NCLangResOnserver.getInstance()
										.getStrByID(
												"20021005",
												"UPP20021005-000575"/*
																	 * @res
																	 * "::第{0}条分录"
																	 */,
												null,
												new String[] { String
														.valueOf(i + 1) });
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					} else {
						detailass = new nc.vo.glcom.ass.AssVO[accass.length];
						for (int m = 0; m < detailass.length; m++) {
							detailass[m] = new nc.vo.glcom.ass.AssVO();
							detailass[m]
									.setPk_Checktype(((nc.vo.bd.account.AccAssVO) accass[m])
											.getPk_entity());
						}
						IFreevaluePub freevalue = NCLocator.getInstance()
								.lookup(IFreevaluePub.class);
						voucher.getDetail(i).setAssid(
								freevalue.getAssID(detailass, Boolean.TRUE,
										voucher.getPk_group(), Module.GL));
						voucher.getDetail(i).setAss(detailass);
					}
				} else {
					HashMap tmpassmap = new HashMap();
					for (int j = 0; j < detailass.length; j++) {
						if (tmpassmap.get(detailass[j].getPk_Checktype()) != null) {
							OperationResultVO rs = new OperationResultVO();
							rs.m_intSuccess = 2;
							rs.m_strDescription = new VoucherCheckMessage()
									.getVoucherMessage(VoucherCheckMessage.ErrMsgAssIDIsError)
									+ nc.bs.ml.NCLangResOnserver
											.getInstance()
											.getStrByID(
													"20021005",
													"UPP20021005-000580"/*
																		 * @res
																		 * "第{0}条分录,ID="
																		 */,
													null,
													new String[] { String
															.valueOf((i + 1)) })
									+ voucher.getDetail(i).getAssid();
							result = OperationResultVO.appendResultVO(result,
									new OperationResultVO[] { rs });
						}
						tmpassmap.put(detailass[j].getPk_Checktype(),
								detailass[j]);
					}
					Vector tmp_detailass = new Vector();
					for (int j = 0; j < accass.length; j++) {
						AccAssVO assVO = accass[j];
						nc.vo.glcom.ass.AssVO tmp_assvo = (nc.vo.glcom.ass.AssVO) tmpassmap
								.get(assVO.getPk_entity());
						if (tmp_assvo == null
								|| (StringUtils.isEmpty(tmp_assvo
										.getPk_Checkvalue()))
								|| StrTools.NULL.equals(tmp_assvo
										.getPk_Checkvalue())) {// 非空判断
							if (assVO.getIsempty() != null
									&& !assVO.getIsempty().booleanValue()
									&& isCheckEmpty) {
								OperationResultVO rs = new OperationResultVO();
								rs.m_intSuccess = 2;
								rs.m_strDescription = new VoucherCheckMessage()
										.getVoucherMessage(VoucherCheckMessage.ErrMsgAssItemIsNull)
										+ nc.bs.ml.NCLangResOnserver
												.getInstance()
												.getStrByID(
														"20021005",
														"UPP20021005-000575"/*
																			 * @res
																			 * "::第{0}条分录"
																			 */,
														null,
														new String[] { String
																.valueOf(i + 1) });
								result = OperationResultVO.appendResultVO(
										result, new OperationResultVO[] { rs });
								break;	//add by shipl 这里“发现为空的辅助核算后”即跳出循环，否则如果该科目有多的不能为空的辅助核算时，提示信息会重复
							} else {
								tmp_assvo = new nc.vo.glcom.ass.AssVO();
								tmp_assvo.setPk_Checktype(assVO.getPk_entity());
								tmp_detailass.addElement(tmp_assvo);
							}

						} else {
							tmp_detailass.addElement(tmp_assvo);
						}

					}
				}
			}
		}
		return result;
	}

	/**
	 * 表外科目校验
	 * 
	 * @param voucher
	 * @param i
	 * @param result
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkOutAccasoa(VoucherVO voucher, int i,
			OperationResultVO[] result, nc.vo.bd.account.AccountVO accvo) {
		if (voucher.getIsOutSubj() != null
				&& voucher.getIsOutSubj().booleanValue()
				&& (accvo.getOutflag() == null || !accvo.getOutflag()
						.booleanValue())) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgErrorSubjOutFlag)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * 科目发生额方向控制
	 * 
	 * @param voucher
	 * @param i
	 * @param result
	 * @param detail
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkOccurAmountAccasoa(VoucherVO voucher,
			int i, OperationResultVO[] result, DetailVO detail,
			nc.vo.bd.account.AccountVO accvo) {
		if (accvo.getIncurflag() != null
				&& accvo.getIncurflag().booleanValue()
				&& !voucher
						.getPk_system()
						.trim()
						.equals(SystemtypeConst.PROFIT_AND_LOSS_CARRIED_FORWARD)
				/*zhaoshya 汇兑损益需要校验科目控制方向*/
//				&& !voucher.getPk_system().trim()
//						.equals(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES)
				&& !voucher.getPk_system().trim()
						.equals(SystemtypeConst.RECLASSIFY)
				&& voucher.getVoucherkind() != 1) {
			switch (accvo.getBalanorient().intValue()) {
			case 0: {
				if (!detail.getLocalcreditamount().equals(
						new nc.vo.pub.lang.UFDouble(0))) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjDebitCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
				break;
			}
			case 1: {
				if (!detail.getLocaldebitamount().equals(
						new nc.vo.pub.lang.UFDouble(0))) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjCreditCtrl)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
				break;
			}
			}
		}
		return result;
	}
	
	/**
	 * 辅助核算合法性校验
	 * @param voucher
	 * @param i
	 * @param result
	 * @return
	 */
	private OperationResultVO[] checkAssValidate(VoucherVO voucher, int i,OperationResultVO[] result,Map<String,AssVO[]> assMap,Map<String,List<String>> relationMap) {
		
		String assid = voucher.getDetail(i).getAssid();
		AssVO[] assVOs = voucher.getDetail(i).getAss();
		
		try{
			//校验辅助核算是否存在
//			if(StringUtils.isNotEmpty(assid)) {
//				AssVO[] assvos = null;
//				if(assMap.containsKey(assid)) {
//					assvos = assMap.get(assid);
//				}else {
//					assvos = NCLocator.getInstance().lookup(IFreevaluePub.class).queryAssvosByid(assid, Module.GL);
//					assMap.put(assid, assvos);
//				}
//				
//				if(assvos == null || assvos.length ==0) {
//					OperationResultVO rs = new OperationResultVO();
//					rs.m_intSuccess = 2;
//					rs.m_strDescription = nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005","UPT2002100573-900057"/** @res* "辅助核算错误[{0}] "*/,
//							null,
//							new String[] {assid})+nc.bs.ml.NCLangResOnserver.getInstance()
//							.getStrByID(
//									"20021005",
//									"UPP20021005-000575"/*
//														 * @res
//														 * "::第{0}条分录"
//														 */,
//									null,
//									new String[] { String
//											.valueOf(i + 1) });
//					result = OperationResultVO.appendResultVO(result,
//							new OperationResultVO[] { rs });
//				}
//			}
			if(StringUtils.isNotEmpty(assid) && assVOs != null && assVOs.length>0) {
				for(AssVO assVo:assVOs) {
					String pk_checkvalue = assVo.getPk_Checkvalue();
					if(StringUtils.isNotEmpty(pk_checkvalue) && !StrTools.NULL.equals(pk_checkvalue)) {
						OperationResultVO rs = new OperationResultVO();
						IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(assVo.getM_classid());
						//仅支持档案级的辅助核算校验
						if(accessor == null || accessor instanceof NullGeneralAccessor)
							continue;
						
						IBDData bdData = accessor.getDocByPk(pk_checkvalue);
						if(bdData == null) {
							rs.m_intSuccess = 2;
							rs.m_strDescription = nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005","UPT2002100573-900057"/** @res* "辅助核算错误[{0}] "*/,
									null,
									new String[] {assVo.getChecktypename()})+nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
							result = OperationResultVO.appendResultVO(result,
									new OperationResultVO[] { rs });
						}else{
							String code = bdData.getCode();
							//银行账户用子户
							IBDData docByCode = null; 
							if(bdData instanceof BankaccsubBDDataVO) {
								code = ((BankaccsubBDDataVO)bdData).getSubcode();
								if (code == null && IBDMetaDataIDConst.PSNBANKACCSUB.equals(assVo.getM_classid())){
									String sql = "select accnum from bd_bankaccsub where pk_bankaccbas in (select pk_bankaccbas from bd_bankaccbas where accclass=0) and accnum = '"+assVo.getCheckvaluecode() +"' ";
									Object obj = new BaseDAO().executeQuery(sql, new ArrayListProcessor());
									code = obj != null ? assVo.getCheckvaluecode() : null;
									docByCode = ((BankaccSubGeneralAccessor)accessor).getDocByAccnum(code, voucher.getDetail(i).getPk_currtype());
								}
							}

							//当根据编码查不到部门或者人员的时候，查询出委托给这个业务单元的组织， 看看这个档案所属组织是不是委托中的一个。
							if(assVo.getM_classid().equals(IBDMetaDataIDConst.PSNDOC)||
									assVo.getM_classid().equals(IBDMetaDataIDConst.DEPT)){
									if(voucher.getDetail(i).getPk_unit().equals(bdData.getPk_org())){
										continue;
									}
									if(relationMap==null)
										relationMap = new HashMap<String,List<String>>();
									//先从核算委托关系中找，找到就校验通过
									if(relationMap!=null&&relationMap.size()>0&&relationMap.get(voucher.getDetail(i).getPk_unit())!=null){
										List<String> relationList = relationMap.get(voucher.getDetail(i).getPk_unit());
										if(relationList.contains(bdData.getPk_org())){
											continue;
										}
									}
									boolean subContain = false;
									
									//直接的人员来源里没有就从委托核算的人员来源找。
									if(relationMap.get(voucher.getDetail(i).getPk_unit())!=null){
										for(String relation : relationMap.get(voucher.getDetail(i).getPk_unit())){
											if(relationMap.get(relation+"#all")!=null){
												List<String> relationList = relationMap.get(relation+"#all");
												if(relationList.contains(bdData.getPk_org())){
													subContain = true;
													break;
												}
											}
										}
									}
									if(!subContain){
										//委托关系中没有的，再从人员来源中找。
										if(relationMap!=null&&relationMap.size()>0&&relationMap.get(voucher.getDetail(i).getPk_unit()+"#all")!=null){
											List<String> relationList = relationMap.get(voucher.getDetail(i).getPk_unit()+"#all");
											if(relationList.contains(bdData.getPk_org())){
												continue;
											}
										}
										//人员档案
										if(assVo.getM_classid().equals(IBDMetaDataIDConst.PSNDOC)){
											//查询出该人员在制单日期的当天兼职的所有组织
											StringBuffer sqlOrgs = new StringBuffer();
											SQLParameter parameterOrgs = new SQLParameter();
											sqlOrgs.append("select pk_org from bd_psnjob where pk_psndoc = ? ");
											parameterOrgs.addParam(bdData.getPk());
											sqlOrgs.append(" and bd_psnjob.indutydate <= ? ");
											parameterOrgs.addParam(voucher.getPrepareddate().toString().substring(0, 10));
											sqlOrgs.append(" and (bd_psnjob.enddutydate >= ? ");
											parameterOrgs.addParam(voucher.getPrepareddate().toString().substring(0, 10));
											sqlOrgs.append(" or  bd_psnjob.enddutydate = '~' or bd_psnjob.enddutydate is null) ");
											ArrayList org_List = (ArrayList) new BaseDAO().executeQuery(sqlOrgs.toString(), parameterOrgs, new ColumnListProcessor());
											Boolean find = false;
											for (int j = 0; j < org_List.size(); j++) {
												if(voucher.getDetail(i).getPk_unit().equals(org_List.get(j))){
													find = true;
													break;
												}
											}
											if(relationMap!=null&&relationMap.size()>0&&relationMap.get(voucher.getDetail(i).getPk_unit())!=null){
												List<String> relationList = relationMap.get(voucher.getDetail(i).getPk_unit());
												for (int j = 0; j < org_List.size(); j++) {
													if(relationList.contains(org_List.get(j))){
														find = true;
														break;
													}
												}
											}
											if(find){	//找到了相关的人员
												continue;
											}
										}
										rs.m_intSuccess = 2;
										rs.m_strDescription = nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005","UPT2002100573-900057"/** @res* "辅助核算错误[{0}] "*/,
												null,
												new String[] {assVo.getChecktypename()})+nc.bs.ml.NCLangResOnserver.getInstance()
												.getStrByID(
														"20021005",
														"UPP20021005-000575"/*
																			 * @res
																			 * "::第{0}条分录"
																			 */,
														null,
														new String[] { String
																.valueOf(i + 1) });
										result = OperationResultVO.appendResultVO(result,
												new OperationResultVO[] { rs });
									}
									
							}else{
								docByCode = (docByCode == null) ? accessor.getDocByCode(voucher.getDetail(i).getPk_unit(), code):docByCode;
//								IBDData docByCode = accessor.getDocByCode(voucher.getDetail(i).getPk_unit(), code);
								if(docByCode == null || !pk_checkvalue.equals(docByCode.getPk())) {
									rs.m_intSuccess = 2;
									rs.m_strDescription = nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005","UPT2002100573-900057"/** @res* "辅助核算错误[{0}] "*/,
											null,
											new String[] {assVo.getChecktypename()})+nc.bs.ml.NCLangResOnserver.getInstance()
											.getStrByID(
													"20021005",
													"UPP20021005-000575"/*
																		 * @res
																		 * "::第{0}条分录"
																		 */,
													null,
													new String[] { String
															.valueOf(i + 1) });
									result = OperationResultVO.appendResultVO(result,
											new OperationResultVO[] { rs });
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {
			Logger.error(e);
		}
		
		return result;
	}

	/**
	 * 校验辅助核算非空
	 * 
	 * @param voucher
	 * @param i
	 * @param result
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkAssNotNull(VoucherVO voucher, int i,
			OperationResultVO[] result, nc.vo.bd.account.AccountVO accvo) {
		// hurh
		AccAssVO[] accass = accvo.getAccass();
		OperationResultVO rs = new OperationResultVO();
		if ((accass == null || accass.length <= 0)
				&& voucher.getDetail(i).getAssid() != null) {
			// for (AccAssVO accAssVO : v) {
			// if(!accAssVO.getIsempty().booleanValue()){
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgAssIsNotNull)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
			// }
			// }

		}
		return result;
	}

	/**
	 * 校验封存科目
	 * 
	 * @param i
	 * @param result
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkSealAccasoa(int i,
			OperationResultVO[] result, nc.vo.bd.account.AccountVO accvo) {
		if (accvo.getEnablestate() != IPubEnumConst.ENABLESTATE_ENABLE) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjSealed)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * 处理凭证非现金类科目
	 * 
	 * @param voucher
	 * @param i
	 * @param accvo
	 */
	private void processCashtype(VoucherVO voucher, int i,
			nc.vo.bd.account.AccountVO accvo) {
		if (accvo.getCashtype().intValue() != 2) {
			voucher.getDetail(i).setCheckdate(null);
			voucher.getDetail(i).setCheckno(null);
			voucher.getDetail(i).setCheckstyle(null);
			voucher.getDetail(i).setCheckstylename(null);
			voucher.getDetail(i).setBilltype(null);
		}
		if (accvo.getCashtype().intValue() == 1
				|| accvo.getCashtype().intValue() == 2
				|| accvo.getCashtype().intValue() == 3) {
			voucher.setSignflag(nc.vo.pub.lang.UFBoolean.TRUE);
		}
	}

	/**
	 * 校验末级科目
	 * 
	 * @param i
	 * @param result
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkEndAccasoa(int i,
			OperationResultVO[] result, nc.vo.bd.account.AccountVO accvo) {
		if (!accvo.getEndflag().booleanValue()) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjIsNotEndLevel)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}
	
	/**
	 * 校验现金流量分析是否为末级
	 * @param voucher
	 * @param i
	 * @param result
	 * @return
	 */
	private OperationResultVO[] checkCashflow(VoucherVO voucher, int i, OperationResultVO[] result) {
		
		if(voucher.getDetail(i) != null){
			CashflowcaseVO[] cashflows = voucher.getDetail(i).getCashFlow();
			IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor(IBDMetaDataIDConst.CASHFLOW );
			if(cashflows != null){
				String pk_org = voucher.getPk_org();
				Set<String> errorCodes = new HashSet<String>();
				if(cashflows != null){
					for (CashflowcaseVO cashflowcaseVO : cashflows) {
						if(cashflowcaseVO != null){
							IBDData data = accessor.getDocByPk(cashflowcaseVO.getPk_cashflow());
							if(data !=null && !accessor.isLeaf(pk_org, cashflowcaseVO.getPk_cashflow())){
								errorCodes.add(data.getCode());
							}
						}
					}
				}
				if(errorCodes.size()>0){
					
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ERRMSGCASHFLOWNOTEND)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 校验受控系统
	 * 
	 * @param voucher
	 * @param i
	 * @param result
	 * @param accvo
	 * @return
	 */
	private OperationResultVO[] checkControlSystem(VoucherVO voucher, int i,
			OperationResultVO[] result, nc.vo.bd.account.AccountVO accvo) {
		// if (accvo.getCtrlmodules() != null &&
		// accvo.getCtrlmodules().trim().length() != 0 &&
		// !accvo.getCtrlmodules().equals("CV")) {
		if (accvo.getCtrlmodules() != null
				&& accvo.getCtrlmodules().trim().length() != 0
				&& !accvo.getCtrlmodules().equals(
						SystemtypeConst.RECONCILEVOUCHER)) {
			StringTokenizer st = new StringTokenizer(accvo.getCtrlmodules(),
					",");
			boolean isctl = false;
			String str = null;
			while (st.hasMoreTokens()) {
				str = (String) st.nextToken();
				if (GLSystemControlTool.checkAccountControl(
						voucher.getPk_system(), str).booleanValue()) {
					isctl = true;
					break;
				}
			}
			if (!isctl) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjCtrlSysNotFit)
						+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
								"20021005", "UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */, null,
								new String[] { String.valueOf(i + 1) });
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		}
		return result;
	}

	/**
	 * 处理非数量凭证
	 * 
	 * @param voucher
	 * @param i
	 * @param accvo
	 */
	private void processUnit(VoucherVO voucher, int i,
			nc.vo.bd.account.AccountVO accvo) {
		if (accvo.getUnit() == null) {
			voucher.getDetail(i).setDebitquantity(new UFDouble(0));
			voucher.getDetail(i).setCreditquantity(new UFDouble(0));
			voucher.getDetail(i).setPrice(new UFDouble(0));
		}
	}

	/**
	 * 校验科目空信息
	 * 
	 * @param i
	 * @param result
	 * @return
	 */
	private OperationResultVO[] checkAccasoaInfo(int i,
			OperationResultVO[] result) {
		OperationResultVO rs = new OperationResultVO();
		rs.m_intSuccess = 2;
		rs.m_strDescription = new VoucherCheckMessage()
				.getVoucherMessage(VoucherCheckMessage.ErrMsgErrorSubj)
				+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
						"20021005", "UPP20021005-000575"/* @res "::第{0}条分录" */,
						null, new String[] { String.valueOf(i + 1) });
		result = OperationResultVO.appendResultVO(result,
				new OperationResultVO[] { rs });
		return result;
	}

	/**
	 * 校验币种
	 * 
	 * @param voucher
	 * @param i
	 * @param glparam
	 * @param result
	 * @param detail
	 * @return
	 */
	private OperationResultVO[] checkCurrtype(VoucherVO voucher, int i,
			GLParameterVO glparam, OperationResultVO[] result, DetailVO detail) {
		if (detail.getPk_currtype() == null
				|| detail.getPk_currtype().trim().length() == 0) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoCurrtype)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		} else {
			if (detail.getPk_currtype().equals(glparam.Parameter_pk_localcurr)) {
				if (detail.getDebitamount().equals(new UFDouble(0))
						&& detail.getCreditamount().equals(new UFDouble(0))) {
					voucher.getDetail(i).setDebitamount(
							detail.getLocaldebitamount());
					voucher.getDetail(i).setCreditamount(
							detail.getLocalcreditamount());
				}
			}
		}
		return result;
	}
	
	/**
	 * 校验业务日期
	 * @param voucher
	 * @param i
	 * @param glparam
	 * @param result
	 * @param detail
	 * @return
	 */
	private OperationResultVO[] checkVerifyDate(VoucherVO voucher, int i,
			GLParameterVO glparam, OperationResultVO[] result, DetailVO detail) {
		
		if(!StringUtils.isEmpty(detail.getVerifydate()) && voucher.getPrepareddate() != null) {
			if(voucher.getPrepareddate().beforeDate(new UFDate(new UFDateTime(detail.getVerifydate(), ICalendar.BASE_TIMEZONE).getMillis()))) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("glpub_0", "02002003-0199")
						+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
								"20021005",
								"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
								new String[] { String.valueOf(i + 1) });
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		} 
		return result;
	} 

	private boolean isGroupNull(String pk_group,DetailVO detailVO) {
		boolean startGroupCurr = Currency.isStartGroupCurr(pk_group);
		if(!startGroupCurr) {
			return true;
		}
		if(detailVO.getGroupdebitamount().equals(UFDouble.ZERO_DBL) && detailVO.getGroupcreditamount().equals(UFDouble.ZERO_DBL)) {
			return true;
		}
		return false;
	}
	
	private boolean isGlobeNull(DetailVO detailVO) {
		boolean startGlobalCurr = Currency.isStartGlobalCurr();
		if(!startGlobalCurr) {
			return true;
		}
		if(detailVO.getGlobaldebitamount().equals(UFDouble.ZERO_DBL) && detailVO.getGlobalcreditamount().equals(UFDouble.ZERO_DBL)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 只有汇兑损益凭证组织本币可以为空，包括总账汇兑损益及应收应付汇兑损益
	 * @param pk_system
	 * @return
	 */
	private boolean isCheckOrgAmount(String pk_system) {
//		if(SystemtypeConst.PROFIT_AND_LOSS_CARRIED_FORWARD.equalsIgnoreCase(pk_system))
		if(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES.equalsIgnoreCase(pk_system)) //修改为汇兑损益
			return false;
		if(SystemtypeConst.AR.equalsIgnoreCase(pk_system) || SystemtypeConst.AP.equalsIgnoreCase(pk_system)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验本币金额
	 * 
	 * @param voucher
	 * @param i
	 * @param result
	 * @param detail
	 * @return
	 */
	private OperationResultVO[] checkLocalAmount(VoucherVO voucher, int i,
			OperationResultVO[] result, DetailVO detail) {
		if (detail.getLocalcreditamount()
				.equals(new nc.vo.pub.lang.UFDouble(0))
				&& detail.getLocaldebitamount().equals(
						new nc.vo.pub.lang.UFDouble(0))
				&& detail.getVatdetail() == null) {

			if (isCheckOrgAmount(voucher.getPk_system())) {
				// hurh 汇兑损益的凭证组织本币空
				if (voucher.getVoucherkind().intValue() != 3) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalamountIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				} else if (detail.getCreditquantity().equals(
						new nc.vo.pub.lang.UFDouble(0))
						&& detail.getDebitquantity().equals(
								new nc.vo.pub.lang.UFDouble(0))) {
					if (!SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES
							.equals(voucher.getPk_system())) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgQuantityIsZero)
								+ nc.bs.ml.NCLangResOnserver.getInstance()
										.getStrByID(
												"20021005",
												"UPP20021005-000575"/*
																	 * @res
																	 * "::第{0}条分录"
																	 */,
												null,
												new String[] { String
														.valueOf(i + 1) });
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					}
				}
			} else if (isGroupNull(voucher.getPk_group(), detail)
					&& isGlobeNull(detail)) {
				// hurh 汇兑损益的凭证组织本币空
				if (voucher.getVoucherkind().intValue() != 3) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgLocalamountIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				} else if (detail.getCreditquantity().equals(
						new nc.vo.pub.lang.UFDouble(0))
						&& detail.getDebitquantity().equals(
								new nc.vo.pub.lang.UFDouble(0))) {
					if (!SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES
							.equals(voucher.getPk_system())) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgQuantityIsZero)
								+ nc.bs.ml.NCLangResOnserver.getInstance()
										.getStrByID(
												"20021005",
												"UPP20021005-000575"/*
																	 * @res
																	 * "::第{0}条分录"
																	 */,
												null,
												new String[] { String
														.valueOf(i + 1) });
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					}
				}
			}
		}
		return result;
	}

	/**
	 * 校验科目
	 * 
	 * @param i
	 * @param result
	 * @param detail
	 * @return
	 */
	private OperationResultVO[] checkAccasoa(int i, OperationResultVO[] result,
			DetailVO detail) {
		if (detail.getPk_accasoa() == null || detail.getPk_accasoa().equals("")) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoAccsubj)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * 校验摘要
	 * 
	 * @param i
	 * @param result
	 * @param detail
	 * @return
	 */
	private OperationResultVO[] checkExplanatin(int i,
			OperationResultVO[] result, DetailVO detail) {
		if (detail.getExplanation() == null
				|| detail.getExplanation().equals("")) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoExplanation)
					+ nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
							"20021005",
							"UPP20021005-000575"/* @res "::第{0}条分录" */, null,
							new String[] { String.valueOf(i + 1) });
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * @author zhaozh 2010-4-16 下午04:02:14 凭证必输项校验
	 * @param voucher
	 * @param accvo
	 * @param i
	 * @return
	 */
	private OperationResultVO[] checkVoucherAbsolutely(VoucherVO voucher,
			AccountVO accvo, int i) {
		OperationResultVO[] result = null;
		if (accvo.getUnit() != null) {
			if (accvo.getQuantity().booleanValue())
				if (voucher.getDetail(i).getDebitquantity()
						.equals(new UFDouble(0))
						&& voucher.getDetail(i).getCreditquantity()
								.equals(new UFDouble(0))) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgQuantityNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			if (accvo.getPrice().booleanValue())
				if (voucher.getDetail(i).getPrice().equals(new UFDouble(0))) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgPriceNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPT2002100573-900044"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
		}
		if (accvo.getInneracc() != null && accvo.getInneracc().booleanValue()) {
			if (accvo.getInnerinfo().booleanValue())
				if (voucher.getDetail(i).getInnerbusno() == null
						|| voucher.getDetail(i).getInnerbusdate() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgSubjInnerInfoCannotNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
		}
		if (accvo.getCashtype() != null && accvo.getCashtype().intValue() == 2) {
			if (null != accvo.getBalancetype()
					&& accvo.getBalancetype().booleanValue())
				if (voucher.getDetail(i).getCheckstyle() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgCheckstyleNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			if (null != accvo.getBankacc() && accvo.getBankacc().booleanValue())
				if (voucher.getDetail(i).getBankaccount() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgBankaccountNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			if (null != accvo.getBilltype()
					&& accvo.getBilltype().booleanValue())
				if (voucher.getDetail(i).getBilltype() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgBilltypeNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPT2002100573-900044"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			if (null != accvo.getBilldate()
					&& accvo.getBilldate().booleanValue())
				if (voucher.getDetail(i).getCheckdate() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgCheckdateNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			if (null != accvo.getBillnumber()
					&& accvo.getBillnumber().booleanValue())
				if (voucher.getDetail(i).getCheckno() == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgChecknoNotAllowNull)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkFracAmountBalance(VoucherVO voucher,
			Integer controlmode, GLParameterVO param) {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;
		if (voucher.getNumDetails() <= 0)
			return null;
		if (voucher.getIsOutSubj() != null
				&& voucher.getIsOutSubj().booleanValue())
			return null;
		if (param == null)
			param = new GLParameterVO();
		if (param.Parameter_islocalfrac == null) {
			try {
				param.Parameter_islocalfrac = AccountBookUtil
						.getIsAccAccountByPk(voucher.getPk_accountingbook()); // 60xnew
																				// UFBoolean(AccountBookUtil.getGlBookVOByPk_glOrgBook(voucher.getPk_accountingbook()).getPk_currchecksys()
																				// ==
																				// null);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new GlBusinessException(e.getMessage());
			}
		}
		if (!param.Parameter_islocalfrac.booleanValue())
			return null;
		OperationResultVO[] result = null;
		UFDouble fracDebit = new UFDouble(0);
		UFDouble fracCredit = new UFDouble(0);
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			DetailVO detail = voucher.getDetail(i);
			fracDebit = fracDebit.add(detail.getFracdebitamount());
			fracCredit = fracCredit.add(detail.getFraccreditamount());
		}
		if (!fracDebit.equals(fracCredit)) {
			switch (controlmode.intValue()) {
			case ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT: {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 0;
				result = new OperationResultVO[] { rs };
				break;
			}
			case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 1;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgFracAmountNotBalance);
				result = new OperationResultVO[] { rs };
				break;
			}
			case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgFracAmountNotBalance);
				result = new OperationResultVO[] { rs };
				break;
			}
			default:
				throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000574")/*
													 * @res
													 * "无法识别的控制类型，可能是软件版本不一致。"
													 */);
			}
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 19:05:10)
	 * 
	 * @return java.lang.Boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	protected OperationResultVO[] checkIsNoDetail(VoucherVO voucher)
			throws Exception {
		if (voucher.getDetails() == null || voucher.getNumDetails() == 0) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoDetails);
			return new OperationResultVO[] { rs };
		}
		return null;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 19:05:10)
	 * 
	 * @return java.lang.Boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	protected OperationResultVO[] checkInputAttachment(VoucherVO voucher)
			throws Exception {

		UFBoolean isInputAttachment = NCLocator.getInstance()
				.lookup(IGlPara.class)
				.isInputAttachment(voucher.getPk_accountingbook());
		if (isInputAttachment.booleanValue()) {
			if (voucher.getAttachment().intValue() == 0) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgAttachmentNotAllowNull);
				return new OperationResultVO[] { rs };
			}
		}
		return null;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-11-30 8:47:58)
	 */
	protected OperationResultVO[] checkPreparedDate(VoucherVO voucher,
			HashMap tempaccsubj) throws Exception {

		OperationResultVO[] result = null;
		UFDate date = voucher.getPrepareddate();
		String[] settledperiod = NCLocator.getInstance().lookup(IGlPara.class)
				.getSettlePeriod(voucher.getPk_accountingbook(), "2002");
		String[] startperiod = NCLocator.getInstance().lookup(IGlPara.class)
				.getStartPeriod(voucher.getPk_accountingbook(), "2002");
		String year;
		String period;

		if (SystemtypeConst.RECLASSIFY.equals(voucher.getPk_system())
				&& "00".equals(voucher.getPeriod())) {
			// 自定义转账生成年初重分类不做校验
			return null;
		}

		GlPeriodVO vo = NCLocator.getInstance().lookup(IGlPeriod.class)
				.getPeriod(voucher.getPk_accountingbook(), date);
		if (vo == null) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgDateNotFind);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		} else {
			year = vo.getYear();
			if (year == null) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgDateNotFind);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			} else {
				if (!year.equals(voucher.getYear())) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDateYearNotMatched);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
				period = vo.getMonth();
				if (period == null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDateNotFind);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				} else {
					// hurh 调整期凭证、损益结转凭证以外的凭证，需要校验是否关账、提前关账
					if (voucher.getVoucherkind().intValue() != GLVoucherKindConst.ADJUST
							&& !SystemtypeConst.PROFIT_AND_LOSS_CARRIED_FORWARD
									.equals(voucher.getPk_system())
							&& !SystemtypeConst.RECLASSIFY.equals(voucher
									.getPk_system())) {
						result = checkCloseAccbook(result, voucher, tempaccsubj);
					}
					if (!period.equals(voucher.getPeriod())) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgDateYearNotMatched);
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					}
					if (startperiod == null) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgGLSystemNotStartUp);
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					} else if (year.compareTo(startperiod[0]) < 0
							|| (year.compareTo(startperiod[0]) == 0 && period
									.compareTo(startperiod[1]) < 0)) {
						OperationResultVO rs = new OperationResultVO();
						rs.m_intSuccess = 2;
						rs.m_strDescription = new VoucherCheckMessage()
								.getVoucherMessage(VoucherCheckMessage.ErrMsgDateBeforeGLStartUp);
						result = OperationResultVO.appendResultVO(result,
								new OperationResultVO[] { rs });
					} else {
						if (voucher.getVoucherkind().intValue() == 1) {
							// AccperiodmonthVO[] regulationperiod = new
							// AccperiodmonthDMO().VOgetAdjustedPeriod(year,
							// date);
							// if (regulationperiod != null) {
							// for (int i = 0; i < regulationperiod.length; i++)
							// {
							// if (period.compareTo(settledperiod[1]) > 0)
							// break;
							// }
							// }
							// else {
							// OperationResultVO rs = new OperationResultVO();
							// rs.m_intSuccess = 2;
							// rs.m_strDescription = new
							// VoucherCheckMessage().getVoucherMessage(VoucherCheckMessage.ErrMsgAdjustPeriodNotFind);
							// result = OperationResultVO.appendResultVO(result,
							// new OperationResultVO[] { rs });
							// }
						}
						if (settledperiod == null || settledperiod.length == 0
								|| settledperiod[0] == null
								|| settledperiod[0].equals("")) {
							if (voucher.getVoucherkind().intValue() == 1) {
								OperationResultVO rs = new OperationResultVO();
								rs.m_intSuccess = 2;
								rs.m_strDescription = new VoucherCheckMessage()
										.getVoucherMessage(VoucherCheckMessage.ErrMsgDateHasNotBeenSettled);
								result = OperationResultVO.appendResultVO(
										result, new OperationResultVO[] { rs });
							}
							return result;
						}
						if (year.compareTo(settledperiod[0]) < 0
								|| (year.compareTo(settledperiod[0]) == 0 && period
										.compareTo(settledperiod[1]) <= 0)) {
							if (!(voucher.getVoucherkind().intValue() == 1
									|| (voucher.getVoucherkind().intValue() == GLVoucherKindConst.PLCF && voucher.getM_adjustperiod() != null && voucher.getM_adjustperiod().length()>2))) {
								//结账后1、调整期凭证可以保存；2、损益结转到调整期的凭证可以保存；其他的都不可以
								OperationResultVO rs = new OperationResultVO();
								rs.m_intSuccess = 2;
								rs.m_strDescription = new VoucherCheckMessage()
										.getVoucherMessage(VoucherCheckMessage.ErrMsgDateHasBeenSettled);
								result = OperationResultVO.appendResultVO(
										result, new OperationResultVO[] { rs });
							}
						} else {
							if (voucher.getVoucherkind().intValue() == 1) {
								OperationResultVO rs = new OperationResultVO();
								rs.m_intSuccess = 2;
								rs.m_strDescription = new VoucherCheckMessage()
										.getVoucherMessage(VoucherCheckMessage.ErrMsgDateHasNotBeenSettled);
								result = OperationResultVO.appendResultVO(
										result, new OperationResultVO[] { rs });
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * check had been closeaccbook
	 * 
	 * @param result
	 * @param pk_accountingbook
	 * @param period
	 * @param period
	 * @return
	 * @throws BusinessException
	 */
	private OperationResultVO[] checkCloseAccbook(OperationResultVO[] result,
			VoucherVO voucher, HashMap tempaccsubj) throws Exception {
		OperationResultVO resultVO = null;
		String periodnew = voucher.getYear() + "-" + voucher.getPeriod();
		if (CloseAccBookUtils.isCloseByAccountBookId(
				voucher.getPk_accountingbook(), periodnew)) {
			resultVO = new OperationResultVO();
			resultVO.m_intSuccess = 2;
			resultVO.m_strDescription = String.format(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"voucherprivate_0", "02002005-0066")/*
																 * @res
																 * "核算账簿在%s期间,已关账，不能保存凭证"
																 */, periodnew);
		} else if (CloseAccBookUtils.isPreCloseByAccountBookId(
				voucher.getPk_accountingbook(), periodnew)) {
			DetailVO[] details = voucher.getDetails();
			if (details == null) {
				voucher = new VoucherBO().queryByPk(voucher.getPk_voucher());
				details = voucher.getDetails();
			}
			if (tempaccsubj == null) {
				tempaccsubj = new HashMap();
			}
			if (tempaccsubj.size() <= 0) {
				String[] pk_accsubjs = new String[voucher.getNumDetails()];
				for (int i = 0; i < details.length; i++) {
					pk_accsubjs[i] = details[i].getPk_accasoa();
				}
				AccountVO[] accvos = getAccsubj(pk_accsubjs, voucher
						.getPrepareddate().toStdString());
				AccountVO voTemp = null;
				if (accvos != null) {
					for (int i = 0; i < accvos.length; i++) {
						voTemp = accvos[i];
						tempaccsubj.put(voTemp.getPk_accasoa(), voTemp);
					}
				}
			}
			StringBuilder msg = new StringBuilder();
			AccountVO accvo = null;
			for (DetailVO detail : details) {
				if (detail.getPk_accasoa() == null) { //    如果detail.getPk_accasoa()为空，则表示没有录入科目,直接退出，后面会有处理
					break;
				}
				
				accvo = (AccountVO) tempaccsubj.get(detail.getPk_accasoa());
				if (accvo == null) {
					accvo = AccountUtilGL.findAccountVOByPrimaryKey(detail
							.getPk_accasoa(), voucher.getPrepareddate()
							.toStdString());
					tempaccsubj.put(detail.getPk_accasoa(), accvo);
				}
				if (accvo.getAllowclose() != null
						&& accvo.getAllowclose().booleanValue()) { // 已提前关账，分录中包含提前关账的科目
					msg.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID(
									"voucherprivate_0",
									"02002005-0067",
									null,
									new String[] {
											"" + detail.getDetailindex(),
											accvo.getCode() + " "
													+ accvo.getName() })/*
																		 * @res
																		 * "第{0}条分录的科目【{1}】是提前关账的会计科目。\n"
																		 */);
					break;
				}
			}
			if (!"".equals(msg.toString())) {
				resultVO = new OperationResultVO();
				resultVO.m_intSuccess = 2;
				resultVO.m_strDescription = String.format(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"voucherprivate_0", "02002005-0070")/*
																	 * @res
																	 * "核算账簿在%s期间,已提前关账，且分录包含提前关账的会计科目，不能保存凭证：\n"
																	 */,
						periodnew)
						+ msg.toString();
			}
		}
		return OperationResultVO.appendResultVO(result, resultVO == null ? null
				: new OperationResultVO[] { resultVO });
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkQuantityZero(VoucherVO voucher,
			Integer controlmode, HashMap accsubjcache) {
		if (controlmode == null
				|| controlmode.intValue() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT)
			return null;
		if (voucher.getNumDetails() <= 0)
			return null;
		OperationResultVO[] result = null; // new
		// OperationResultVO[voucher.getNumDetails()];
		Vector vecresult = new Vector();
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			DetailVO detail = voucher.getDetail(i);
			AccountVO accvo = (AccountVO) accsubjcache.get(detail
					.getPk_accasoa());
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 0;
			if (accvo != null
					&& accvo.getUnit() != null
					&& detail.getDebitquantity().equals(
							new nc.vo.pub.lang.UFDouble(0))
					&& detail.getCreditquantity().equals(
							new nc.vo.pub.lang.UFDouble(0))) {
				switch (controlmode.intValue()) {
				case ParaMacro.VOUCHER_SAVE_PARAMETER_NOTICE: {
					rs.m_intSuccess = 1;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDetailQuantityIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					vecresult.addElement(rs);
					break;
				}
				case ParaMacro.VOUCHER_SAVE_PARAMETER_FORBID: {
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgDetailQuantityIsZero)
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID(
											"20021005",
											"UPP20021005-000575"/*
																 * @res
																 * "::第{0}条分录"
																 */,
											null,
											new String[] { String
													.valueOf(i + 1) });
					vecresult.addElement(rs);
					break;
				}
				default:
					throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000574")/*
														 * @res
														 * "无法识别的控制类型，可能是软件版本不一致。"
														 */);
				}
			}
		}
		if (vecresult.size() > 0) {
			result = new OperationResultVO[vecresult.size()];
			vecresult.copyInto(result);
		}
		return result;
	}

	/**
	 * 调整控制
	 */
	protected OperationResultVO[] checkRegulation(VoucherVO voucher)
			throws Exception {
		OperationResultVO[] result = null;
		// if (new VoucherExtendDMO().isExistLaterRegulationVoucher(voucher)) {
		// OperationResultVO rs = new OperationResultVO();
		// rs.m_intSuccess = 2;
		// rs.m_strDescription =
		// nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005",
		// "UPP20021005-000581")/*
		// * @res
		// * "日期以后的期间已经有调整期凭证，本期间禁止录入调整凭证。"
		// */;
		// result = new OperationResultVO[] { rs };
		// }
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	// protected boolean checkSubjFreevalueNull(String pk_subjfreevalue,
	// SubjfreevalueVO vo, GLFreeItemVO[] freeitems) throws Exception {
	// boolean b = false;
	// if (pk_subjfreevalue == null)
	// return false;
	// if (vo == null)
	// return true;
	// if (freeitems == null)
	// // freeitems = new
	// // GlfreeitemBO().getItemsByCorp(vo.getPk_glorgbook());
	// freeitems = FreeItemUtilGL.getItemsByOrg(vo.getPk_glorgbook());
	// for (int i = 0; i < freeitems.length; i++) {
	// if (freeitems[i] == null)
	// continue;
	// if (pk_subjfreevalue.equals(freeitems[i].getPk_glFreeItem())) {
	// Method getmethod = vo.getClass().getMethod(
	// "getSubjfreevalue" + (i + 1), new Class[] {});
	// Object o = getmethod.invoke(vo, new Object[] {});
	// if (o == null)
	// b = true;
	// break;
	// }
	// }
	// return b;
	// }

	protected OperationResultVO[] checkSubjRule(VoucherVO voucher,
			HashMap tempaccsubj) throws Exception {
		OperationResultVO[] result = null;
		try {
			if (voucher.getDetails() != null && voucher.getNumDetails() != 0) {
				SubjRuleCheckBO rulecheck = new SubjRuleCheckBO();
				result = rulecheck.checkSubjRule(voucher, tempaccsubj);
			}
		} catch (ClassNotFoundException e) {
			return null;
		} catch (NoClassDefFoundError e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-4-3 17:27:22)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	public OperationResultVO[] checkTimeOrdered(VoucherVO voucher)
			throws BusinessException {
		OperationResultVO[] result = null;
		try {
			if (new VoucherExtendDMO().checkTimeOrderedNo(voucher).intValue() > 0) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgVoucherTimeOrdered);
				result = new OperationResultVO[] { rs };
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			throw new BusinessException(e.getMessage());
		}

		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkVoucher(VoucherVO voucher,
			HashMap tempaccsubj) throws Exception {
		return checkVoucher(voucher, null, tempaccsubj);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkVoucher(VoucherVO voucher,
			VoucherCheckConfigVO configVO, HashMap tempaccsubj)
			throws Exception {

		if (voucher == null)
			return null;

		long tb = System.currentTimeMillis();
		OperationResultVO[] result = null;

		if (tempaccsubj == null) {
			tempaccsubj = new HashMap();
		}
		if (tempaccsubj.size() == 0) {
			getAccountMap(voucher, tempaccsubj);
		}
		if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName())
					.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::load accsubj:"
							+ (System.currentTimeMillis() - tb) + "ms");
			tb = System.currentTimeMillis();
		}
		GLParameterVO glparam = new GLParameterVO();
		glparam.Parameter_isfreevalueallownull = nc.vo.pub.lang.UFBoolean.TRUE;
		SetOfBookVO glbookvo = AccountBookUtil
				.getSetOfBookVOByPk_accountingBook(voucher
						.getPk_accountingbook());
		glparam.Parameter_islocalfrac = AccountBookUtil
				.getIsAccAccountByPk(voucher.getPk_accountingbook());// 60x new
																		// UFBoolean(glbookvo.getCurrtypesys().intValue()
																		// ==
																		// 1);
		glparam.Parameter_pk_localcurr = glbookvo.getPk_standardcurr();
		voucher = catOutSubjVoucher(voucher, tempaccsubj);
		// voucher = catInnerCustom(voucher);
		if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName())
					.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::load GLParam:"
							+ (System.currentTimeMillis() - tb) + "ms");
			tb = System.currentTimeMillis();
		}
		if (configVO != null
				&& configVO.isNeedNormalCheck() == null
				|| (configVO.isNeedNormalCheck() != null && configVO
						.isNeedNormalCheck().booleanValue())) {
			
			OperationResultVO[] rs1 = checkVoucherHead(voucher, tempaccsubj);
			result = OperationResultVO.appendResultVO(result, rs1);
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName())
						.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::check checkVoucherHead:"
								+ (System.currentTimeMillis() - tb) + "ms");
				tb = System.currentTimeMillis();
			}

			OperationResultVO[] rs2 = checkIsNoDetail(voucher);
			result = OperationResultVO.appendResultVO(result, rs2);
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName())
						.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::check checkIsNoDetail:"
								+ (System.currentTimeMillis() - tb) + "ms");
				tb = System.currentTimeMillis();
			}
			
			Set<String> assidSet = new HashSet<String>();
			Set<String> pk_unitSet = new HashSet<String>();
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				DetailVO detailVo = voucher.getDetail(i);
				String assid = detailVo.getAssid();
				if(StringUtils.isNotEmpty(assid) && !StrTools.NULL.equals(assid)) {
					assidSet.add(assid);
				}
				if(StringUtils.isNotEmpty(detailVo.getPk_unit())){
					pk_unitSet.add(detailVo.getPk_unit());
				}
			}
			//用于存储辅助核算缓存
			ConcurrentHashMap<String, AssVO[]> assMap = NCLocator.getInstance().lookup(IFreevaluePub.class).queryAssvosByAssids(assidSet.toArray(new String[0]), Module.GL);
			if(assMap == null) {
				assMap = new ConcurrentHashMap<String, AssVO[]>();
			}
			
			
			//先从核算委托关系中找，找到就校验通过
			IOrgRelationDataPubService service = NCLocator.getInstance().lookup(IOrgRelationDataPubService.class);
			Map<String,List<String>> relationMap = service.queryOrgUnitByPkFinanceorg(pk_unitSet.toArray(new String[0]));

			//委托关系中没有的，再从人员来源中找。
			IBusiFuncQryPubService busiService = NCLocator.getInstance().lookup(IBusiFuncQryPubService.class);
			Map<String, String[]> mapPkorgAndFuncs = new HashMap<String,String[]>();
			for(String pk_unit : pk_unitSet){
				mapPkorgAndFuncs.put(pk_unit, new String[]{IPubEnumConst.BUSIFUNCODEALL});
			}
			if(relationMap!=null){
				String[] relations = relationMap.keySet().toArray(new String[0]);
				for(String relation : relations){
					for(String unit : relationMap.get(relation)){
						mapPkorgAndFuncs.put(unit, new String[]{IPubEnumConst.BUSIFUNCODEALL});
					}
				}
				Map<String, List<String>> roleMap = busiService.queryAdminOrgsByBusiFunc(mapPkorgAndFuncs);
				if(roleMap!=null)
					relationMap.putAll(roleMap);
			}else{
				relationMap = busiService.queryAdminOrgsByBusiFunc(mapPkorgAndFuncs);
			}
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				OperationResultVO[] rs3 = checkDetail(voucher,
						voucher.getPk_system(), i, tempaccsubj, glparam,assMap,relationMap);
				result = OperationResultVO.appendResultVO(result, rs3);
				if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName())
							.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::check checkDetail"
									+ i
									+ ":"
									+ (System.currentTimeMillis() - tb) + "ms");
					tb = System.currentTimeMillis();
				}
			}
		}
		if (configVO == null)
			return result;

		if (configVO.getAmountAllowZero() == null) {
			configVO.setAmountAllowZero(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getAmountAllowZero(voucher.getPk_accountingbook()));
		}
		if (configVO.getAmountMustBalance() == null) {
			configVO.setAmountMustBalance(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getAmountMustBalance(voucher.getPk_accountingbook()));
		}
		// if (configVO.getFracMustBalance() == null) {
		// configVO.setFracMustBalance(NCLocator.getInstance().lookup(IGlPara.class).getFracMustBalance(voucher.getPk_accountingbook()));
		// }
		if (configVO.getQuantityAllowZero() == null) {
			configVO.setQuantityAllowZero(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getQuantityAllowZero(voucher.getPk_accountingbook()));
		}
		if (configVO.isVoucherTimeOrdered() == null) {
			configVO.setVoucherTimeOrdered(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.isVoucherTimeOrdered(voucher.getPk_accountingbook()));
		}
		if (configVO.getBalanceControl() == null) {
			configVO.setBalanceControl(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getBalanceControlStyle(voucher.getPk_accountingbook()));
		}
		// add by zzh
		if (configVO.getGroupBalanceControl() == null) {
			configVO.setGroupBalanceControl(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getGroupBalanceControl(voucher.getPk_accountingbook()));
		}
		if (configVO.getGlobalBalanceControl() == null) {
			configVO.setGlobalBalanceControl(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.getGlobalBalanceControl(voucher.getPk_accountingbook()));
		}
		if (configVO.getSecondBUBalanceControl() == null) {
			UFBoolean secondBUStart = GLParaAccessor.isSecondBUStart(voucher
					.getPk_accountingbook());
			UFBoolean buBalanceCheck = GLParaAccessor.isBUBalanceCheck(voucher
					.getPk_accountingbook());
			if (secondBUStart != null && secondBUStart.booleanValue()
					&& buBalanceCheck != null && buBalanceCheck.booleanValue()) {
				configVO.setSecondBUBalanceControl(UFBoolean.TRUE);
			} else {
				configVO.setSecondBUBalanceControl(UFBoolean.FALSE);
			}
		}
		if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName())
					.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::load GLParam2:"
							+ (System.currentTimeMillis() - tb) + "ms");
			tb = System.currentTimeMillis();
		}
		OperationResultVO[] rs5 = checkVoucherWithConfigVO(voucher, configVO,
				tempaccsubj, glparam);
		result = OperationResultVO.appendResultVO(result, rs5);
		if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName())
					.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::check checkVoucherWithConfigVO:"
							+ (System.currentTimeMillis() - tb) + "ms");
			tb = System.currentTimeMillis();
		}

		if (configVO.getSubjRuleControl() != null && configVO.getSubjRuleControl() == ParaMacro.VOUCHER_SAVE_PARAMETER_PERMIT) {
		} else{
			OperationResultVO[] rs6 = checkSubjRule(voucher, tempaccsubj);
			result = OperationResultVO.appendResultVO(result, rs6);
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName())
				.debug("CheckVoucher time report::nc.bs.gl.voucher.VoucherCheckBO.checkVoucher(VoucherVO, VoucherCheckConfigVO)::check checkSubjRule:"
						+ (System.currentTimeMillis() - tb) + "ms");
				tb = System.currentTimeMillis();
			}
		}

		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 13:48:25)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkVoucherHead(VoucherVO voucher,
			HashMap tempaccsubj) throws Exception {
		OperationResultVO[] result = null;

		// 2003-05-07 防止外系统传入数据错，补签字标志
		// if (voucher.getSignflag() == null)
		voucher.setSignflag(nc.vo.pub.lang.UFBoolean.FALSE);

		// 核算账簿是否启用
		AccountingBookVO bookVO = AccountBookUtil.getAccountingBookVOByPrimaryKey(voucher.getPk_accountingbook());
		if(bookVO.getAccounttype() == IOrgEnumConst.BOOKTYPE_REPORTBOOK) {
			// 检查liscence
			boolean accountBookOrgExceeded = NCLocator.getInstance()
			.lookup(IAccountingBookPubService.class)
			.isAccountBookOrgExceeded();
			if (accountBookOrgExceeded) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
				.getVoucherMessage(VoucherCheckMessage.ErrMsgAccBookNoPermission);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		}
		
		if (IPubEnumConst.ENABLESTATE_ENABLE != bookVO.getAccountenablestate()) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			if (IPubEnumConst.ENABLESTATE_INIT == bookVO
					.getAccountenablestate()) {
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgAccBookInit);
			} else if (IPubEnumConst.ENABLESTATE_DISABLE == bookVO
					.getAccountenablestate()) {
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgAccBookDisable);
			}
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}

		// 校验制单人是否拥有该财务核算账簿权限
		String pk_accountingbook = voucher.getPk_accountingbook();
		String pk_prepared = voucher.getPk_prepared();
		String pk_group = voucher.getPk_group();

		if (!SystemtypeConst.NC_USER.equals(pk_prepared)) {
			
			//modified by shipl 修改核算账簿权限校验的逻辑，提高效率 2015年11月5日17:53:50  start
			//判断当前用户是否有某一核算账簿的权限的处理逻辑：通过相关条件到数据库中查，如果没有查询结果则表明没有权限
			StringBuffer sql = new StringBuffer();
			SQLParameter parameter = new SQLParameter();
			
			//1.拼接SQL，并设置参数
			sql.append(" select sm_subject_org.subjectid, org.pk_org from org_orgs org, sm_subject_org where org.pk_org = sm_subject_org.pk_org and sm_subject_org.subjectid in ");
			sql.append(" (select pk_role from sm_user_role where cuserid = ? ");
			parameter.addParam(pk_prepared);//添加制单人主键
			String date = new UFDateTime().toString();
			sql.append(" and enabledate <= ? and (isnull((disabledate), '~') = '~' ");
			sql.append(" or disabledate > ?) and pk_role in (select funcperm.subjectid from sm_perm_func funcperm, sm_resp_func respfunc ");
			parameter.addParam(date);	//添加日期
			parameter.addParam(date);
			sql.append(" where respfunc.pk_responsibility = funcperm.ruleid and respfunc.busi_pk = ? ");
			parameter.addParam(GlNodeConst.GLNODE_VOUCHERPREPARE);	//功能编码
			sql.append(" and funcperm.pk_group = ?)) and (sm_subject_org.isrule <> 'Y' ");
			parameter.addParam(pk_group);	//集团主键
			sql.append(" or sm_subject_org.isrule is null) and org.orgtype26 = 'Y' and org.pk_org= ? ");
			parameter.addParam(pk_accountingbook);	//核算账簿主键
			//2.执行并获取结果
			Object access = new BaseDAO().executeQuery(sql.toString(), parameter, new ArrayListProcessor());
			//3.查不出数据表示：没有财务核算账簿权限
			if (access == null) {	
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgAccBookNoPermission);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
		}
		//midified by shipl 2015年11月5日17:54:19 end

		// 检查制单日期
		OperationResultVO[] rs1 = checkPreparedDate(voucher, tempaccsubj);
		result = OperationResultVO.appendResultVO(result, rs1);

		// 检查年度期间是否合法
		OperationResultVO[] rs2 = checkYearPeriod(voucher);
		result = OperationResultVO.appendResultVO(result, rs2);

		// 判断期间是否结账！

		// 检查凭证类别是否合法
		OperationResultVO[] rs3 = checkVoucherType(voucher);
		result = OperationResultVO.appendResultVO(result, rs3);

		// 检查凭证号是否重复

		// 检查调整凭证的控制
		if (voucher.getVoucherkind().intValue() == 1) {
			OperationResultVO[] rs4 = checkRegulation(voucher);
			result = OperationResultVO.appendResultVO(result, rs4);
		}
		// 检查凭证附单据数录入

		// 检查制单系统
		if (voucher.getPk_system() == null
				|| DapSystemDataCache.getInstance().getDapsystem(
						voucher.getPk_system()) == null) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoVoucherSystem);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });

		}

		// 冲销凭证，检查原凭证是否已经冲销
		if (voucher.getOffervoucher() != null) {
			boolean isoffered = GLPubProxy.getRemoteVoucher()
					.isOfferSetVoucher(voucher.getOffervoucher());
			if (isoffered && StringUtils.isEmpty(voucher.getPk_voucher())) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = OperationResultVO.ERROR;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrVoucherOffed);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });

			}
		}

		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-10-27 10:51:07)
	 * 
	 * @return java.lang.Boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected Boolean checkVoucherNo(VoucherVO voucher) throws Exception {
		VoucherVO vo1 = new VoucherVO();
		vo1.setPk_vouchertype(voucher.getPk_vouchertype());
		vo1.setNo(voucher.getNo());
		vo1.setPk_org(voucher.getPk_org());
		vo1.setPk_accountingbook(voucher.getPk_accountingbook());
		vo1.setYear(voucher.getYear());
		vo1.setPeriod(voucher.getPeriod());
		VoucherVO[] vo2 = new VoucherDMO().queryByVO(vo1, new Boolean(true));
		if (vo2 != null && vo2.length != 0
				&& !vo2[0].getPk_voucher().equals(voucher.getPk_voucher()))
			throw new GlBusinessException(
					new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
		return new Boolean(true);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-10-27 10:27:33)
	 * 
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkVoucherType(VoucherVO voucher)
			throws Exception {

		OperationResultVO[] result = null;
		if (voucher.getPk_vouchertype() == null) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgNoVouchertype);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		} else {
			VoucherTypeVO vo6;
			// vo6 =
			// VoucherTypeGL.findVoucherTypeVOByPKAndGlorgbook(voucher.getPk_vouchertype(),
			// voucher.getPk_accountingbook());
			// hurh
			vo6 = VoucherTypeDataCache.getInstance()
					.getVtBypkorgbookAndpkvt(voucher.getPk_accountingbook(),
							voucher.getPk_vouchertype());
			if (vo6 == null) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgVouchertypeNotFind);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			} else {
				if (vo6.getPk_vouchertype() == null) {// 60x
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgVouchertypeNotFind);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
				// 60x
				else if (vo6.getEnablestate() != 2) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgVouchertypeHasSeal);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				} else if (!StringUtils.isEmpty(vo6.getPk_vouchertype())) {
					result = OperationResultVO.appendResultVO(result,
							checkVTStyle(voucher, vo6));
				}
				result = OperationResultVO.appendResultVO(result,
						checkBalance(voucher));
			}
		}
		return result;
	}

	// 60 zhaozh 凭证约束规则校验
	private OperationResultVO[] checkVTStyle(VoucherVO voucher,
			VoucherTypeVO vo6) {
		// VouchertypeRuleVO rulevo =
		// VoucherTypeDataCache.getInstance().getRuleVOByVTPk(voucher.getPk_accountingbook(),vo6.getPk_vouchertype());
		// hurh 不使用缓存
		// modify by pangjsh --增加集团级、全局级凭证约束规则校验
		VouchertypeRuleVO rulevo = VoucherTypeGL.getRuleVOByVTPk(
				voucher.getPk_accountingbook(), vo6.getPk_vouchertype());
		OperationResultVO[] result = null;
		SetOfBookVO bookvo = null;
		if (null == rulevo || StringUtils.isEmpty(rulevo.getRuletype())
				|| rulevo.getRuletype().equals(RuleConst.TYPE_NONO))
			try {
				bookvo = AccountBookUtil.getSetOfBookVOByPk_accountingBook(voucher.getPk_accountingbook());
			} catch (BusinessException e) {
				Logger.error(e.getMessage());
			}
		if(bookvo!=null)
			rulevo = VoucherTypeGL.getRuleVOByVTPkAndPkOrg(bookvo.getPk_setofbook(), vo6.getPk_vouchertype(),voucher.getPk_group());
		
		if (null == rulevo || StringUtils.isEmpty(rulevo.getRuletype())
				|| rulevo.getRuletype().equals(RuleConst.TYPE_NONO))
			if(bookvo!=null)
				rulevo = VoucherTypeGL.getRuleVOByVTPkAndPkOrg(bookvo.getPk_setofbook(), vo6.getPk_vouchertype(),IOrgConst.GLOBEORG);
		if (null == rulevo || StringUtils.isEmpty(rulevo.getRuletype())
				|| rulevo.getRuletype().equals(RuleConst.TYPE_NONO))
			return result;
		
		
		String[] subjcodes = null;
		if (rulevo!=null && rulevo.getAccasoalist()!=null) {
			subjcodes = new String[rulevo.getAccasoalist().length];
		}
		for (int i = 0; i < rulevo.getAccasoalist().length; i++) {
			subjcodes[i] = rulevo.getAccasoalist()[i].getAccountcode();
		}
		
		
		IBDData[] bddatas = AccountUtilGL.getEndDocByCodesVersion(voucher.getPk_accountingbook(),subjcodes, voucher.getPrepareddate().toStdString());
		//非末级科目与末级科目同时设置时消去重复科目
		Map<String,IBDData> accountMap = new HashMap<String,IBDData>();
		for (IBDData bddata : bddatas) {
			accountMap.put(bddata.getPk(),bddata);
		}
		Map debitsubj = new HashMap();
		Map creditsubj = new HashMap();
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			if (voucher.getDetail(i).getLocalcreditamount()
					.equals(new nc.vo.pub.lang.UFDouble(0))) {
				if (debitsubj.get(voucher.getDetail(i).getPk_accasoa()) == null)
					debitsubj.put(voucher.getDetail(i).getPk_accasoa(), voucher
							.getDetail(i).getPk_accasoa());
			} else {
				if (creditsubj.get(voucher.getDetail(i).getPk_accasoa()) == null)
					creditsubj.put(voucher.getDetail(i).getPk_accasoa(),
							voucher.getDetail(i).getPk_accasoa());
			}
		}
		boolean tmpflag = false;
		;
		switch (Integer.valueOf(rulevo.getRuletype())) {
		case 1: // 借方必有
			for (IBDData bdData : accountMap.values()) {
				if (debitsubj.get(bdData.getPk()) != null) {
					tmpflag = true;
					break;
				}
			}
			if (!tmpflag) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgNotUseDebitSubj)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
										"2002gl55", "UPP2002gl55-002831",null,
										new String[]{rulevo.getCode(),GLMultiLangUtil.getMultiName(rulevo)}
										/* @res 凭证类别约束规则编码【{0}，名称【{1}】"*/);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
			break;
		case 2: // 贷方必有
			for (IBDData bdData : accountMap.values()) {
				if (creditsubj.get(bdData.getPk()) != null) {
					tmpflag = true;
					break;
				}
			}
			if (!tmpflag) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgNotUseCreditSubj)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
										"2002gl55", "UPP2002gl55-002831",null,
										new String[]{rulevo.getCode(),GLMultiLangUtil.getMultiName(rulevo)}
										/* @res 凭证类别约束规则编码【{0}，名称【{1}】"*/);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
			break;
		case 3: // 凭证必有
			for (IBDData bdData : accountMap.values()) {
				if (debitsubj.get(bdData.getPk()) != null
						|| creditsubj.get(bdData.getPk()) != null) {
					tmpflag = true;
					break;
				}
			}
			if (!tmpflag) {
				OperationResultVO rs = new OperationResultVO();
				rs.m_intSuccess = 2;
				rs.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgNotUseMustUseSubj)
						+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
										"2002gl55", "UPP2002gl55-002831",null,
										new String[]{rulevo.getCode(),GLMultiLangUtil.getMultiName(rulevo)}
										/* @res 凭证类别约束规则编码【{0}，名称【{1}】"*/);
				result = OperationResultVO.appendResultVO(result,
						new OperationResultVO[] { rs });
			}
			break;
		case 4: // 凭证必无
			for (IBDData bdData : accountMap.values()) {
				if (debitsubj.get(bdData.getPk()) != null
						|| creditsubj.get(bdData.getPk()) != null) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgUseMustNotUseSubj)
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
										"2002gl55", "UPP2002gl55-002831",null,
										new String[]{rulevo.getCode(),GLMultiLangUtil.getMultiName(rulevo)}
										/* @res 凭证类别约束规则编码【{0}，名称【{1}】"*/);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			}
			break;
		default: {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgErrVouchertypeSubjDef);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-25 10:12:05)
	 * 
	 * @return boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkVoucherWithConfigVO(VoucherVO voucher,
			VoucherCheckConfigVO configVO, HashMap accsubjcache,
			GLParameterVO param) throws Exception {

		OperationResultVO[] result = null;

		boolean isEGL = false;
		String pk_system = voucher.getPk_system();
		if (StringUtils.isNotEmpty(pk_system)
				&& SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES.equals(pk_system)) {
			isEGL = true;
		}

		// 当不是汇兑损益公式GL050才起作用
		if (configVO.getAmountAllowZero() != null && !isEGL) {
			OperationResultVO[] t_result = checkAmountZero(voucher,
					configVO.getAmountAllowZero());
			result = OperationResultVO.appendResultVO(result, t_result);
		}
		// 当不是汇兑损益公式GL051才起作用
		if (configVO.getAmountMustBalance() != null && !isEGL) {
			OperationResultVO[] t_result = checkAmountBalance(voucher,
					configVO.getAmountMustBalance());
			result = OperationResultVO.appendResultVO(result, t_result);
		}
		// if (configVO.getFracMustBalance() != null) {
		// OperationResultVO[] t_result = checkFracAmountBalance(voucher,
		// configVO.getFracMustBalance(), param);
		// result = OperationResultVO.appendResultVO(result, t_result);
		// }
		if (configVO.getQuantityAllowZero() != null) {
			OperationResultVO[] t_result = checkQuantityZero(voucher,
					configVO.getQuantityAllowZero(), accsubjcache);
			result = OperationResultVO.appendResultVO(result, t_result);
		}
		if (configVO.getBalanceControl() != null) {

			OperationResultVO[] t_result = checkBalanceControl(voucher,
					accsubjcache, param, configVO.getBalanceControl());
			result = OperationResultVO.appendResultVO(result, t_result);
			OperationResultVO[] a_result = checkAssBalanceControlNew(voucher,
					accsubjcache, param, configVO.getBalanceControl());
			result = OperationResultVO.appendResultVO(result, a_result);

		}
		// add by zzh
		// if(null != configVO.getGroupBalanceControl() &&
		// configVO.getGroupBalanceControl() == 2){
		// OperationResultVO[] t_result = checkGroupBalance(voucher);
		// result = OperationResultVO.appendResultVO(result, t_result);
		// }
		// if(null != configVO.getGlobalBalanceControl() &&
		// configVO.getGlobalBalanceControl() == 2){
		// OperationResultVO[] t_result = checkGlobalBalance(voucher);
		// result = OperationResultVO.appendResultVO(result, t_result);
		// }
		if (null != configVO.getSecondBUBalanceControl()
				&& configVO.getSecondBUBalanceControl().booleanValue()) {
			if (voucher.getVoucherkind() == null
					|| voucher.getVoucherkind() != 2) {
				OperationResultVO[] t_result = checkBUBalance(voucher);
				result = OperationResultVO.appendResultVO(result, t_result);
			}
		}
		return result;
	}

	/**
	 * 二级核算单位校验本币平衡
	 * 
	 * @param voucher
	 * @return
	 */
	private OperationResultVO[] checkBUBalance(VoucherVO voucher) {
		if (voucher.getIsOutSubj() != null
				&& voucher.getIsOutSubj().booleanValue())
			return null;
		DetailVO[] detailVOs = voucher.getDetails();
		HashMap<String, UFDouble> bumap = new HashMap<String, UFDouble>();
		// HashMap<String, String> namemap = new HashMap<String, String>();
		for (DetailVO vo : detailVOs) {
			if (bumap.containsKey(vo.getPk_unit())) {
				bumap.put(
						vo.getPk_unit(),
						bumap.get(vo.getPk_unit()).add(
								vo.getLocaldebitamount().sub(
										vo.getLocalcreditamount())));
			} else {
				bumap.put(vo.getPk_unit(),
						vo.getLocaldebitamount().sub(vo.getLocalcreditamount()));
				// namemap.put(vo.getPk_unit(), vo.getUnitname());
			}
		}
		OperationResultVO[] result = null;
		Set<String> bus = bumap.keySet();
		if (null != bus && bus.size() > 0)
			for (String bu : bus) {
				if (bumap.get(bu).abs().compareTo(new UFDouble(0.0000009)) > 0) {
					OperationResultVO rs = new OperationResultVO();
					rs.m_intSuccess = 2;
					// rs.m_strDescription = new
					// VoucherCheckMessage().getVoucherMessage(VoucherCheckMessage.ErrMsgSecondBUNotBalance)+
					// ":单位"+ namemap.get(bu);
					rs.m_strDescription = new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgSecondBUNotBalance);
					result = OperationResultVO.appendResultVO(result,
							new OperationResultVO[] { rs });
				}
			}
		return result;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-10-27 10:23:20)
	 * 
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	protected OperationResultVO[] checkYearPeriod(VoucherVO voucher)
			throws Exception {

		if (SystemtypeConst.RECLASSIFY.equals(voucher.getPk_system())
				&& "00".equals(voucher.getPeriod())) {
			// 自定义转账生成年初重分类不做校验
			return null;
		}

		OperationResultVO[] result = null;
		AccperiodVO vo1 = new AccperiodVO();
		vo1.setPeriodyear(voucher.getYear());
		AccountCalendar calendar = CalendarUtilGL
				.getAccountCalendarByAccountBook(voucher.getPk_accountingbook());
		try {
			calendar.set(voucher.getYear());
		} catch (InvalidAccperiodExcetion e) {
			Logger.error(e.getMessage(), e);
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgErrorYear);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
			return result;
		}
		try {
			calendar.set(voucher.getYear(), voucher.getPeriod());
		} catch (InvalidAccperiodExcetion e) {
			Logger.error(e.getMessage(), e);
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgErrorPeriod);
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;

	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-10-28 19:52:02)
	 * 
	 * @return nc.vo.bd.account.AccountVO[]
	 * @param strPk_corp
	 *            java.lang.String
	 */
	protected nc.vo.bd.account.AccountVO[] getAccsubj(String[] strPk_accsubj)
			throws Exception {
		if (strPk_accsubj == null || strPk_accsubj.length == 0)
			return null;
		String[] pk_accsubj = null;
		Vector vecaccsubj = new Vector();
		HashMap tempmap = new HashMap();
		for (int i = 0; i < strPk_accsubj.length; i++) {
			if (strPk_accsubj[i] != null
					&& tempmap.get(strPk_accsubj[i]) == null) {
				vecaccsubj.addElement(strPk_accsubj[i]);
				tempmap.put(strPk_accsubj[i], strPk_accsubj[i]);
			}
		}
		pk_accsubj = new String[vecaccsubj.size()];
		vecaccsubj.copyInto(pk_accsubj);
		nc.vo.bd.account.AccountVO[] accsubj = AccountUtilGL.queryByPks(pk_accsubj);
		return accsubj;
	}

	protected nc.vo.bd.account.AccountVO[] getAccsubj(String[] strPk_accsubj,
			String stddate) throws Exception {
		if (strPk_accsubj == null || strPk_accsubj.length == 0)
			return null;
		String[] pk_accsubj = null;
		Vector vecaccsubj = new Vector();
		HashMap tempmap = new HashMap();
		for (int i = 0; i < strPk_accsubj.length; i++) {
			if (strPk_accsubj[i] != null
					&& tempmap.get(strPk_accsubj[i]) == null) {
				vecaccsubj.addElement(strPk_accsubj[i]);
				tempmap.put(strPk_accsubj[i], strPk_accsubj[i]);
			}
		}
		pk_accsubj = new String[vecaccsubj.size()];
		vecaccsubj.copyInto(pk_accsubj);

		nc.vo.bd.account.AccountVO[] accsubj = AccountUtilGL.queryByPks(
				pk_accsubj, stddate);
		return accsubj;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-6-19 10:25:04)
	 * 
	 * @return nc.vo.bd.currtype.CurrtypeVO
	 * @param pk_corp
	 *            java.lang.String
	 */
	protected nc.vo.bd.currtype.CurrtypeVO[] getCurrency(String pk_org)
			throws Exception {
		// return new nc.bs.bd.b20.CurrtypeDMO().queryAll(pk_corp);
		return CurrtypeGL.queryAll(pk_org);
	}

	public ISortTool getSortTool(Object objCompared) {
		// TODO 自动生成方法存根

		try {
			if (m_assSortTool == null)
				m_assSortTool = new nc.ui.glcom.balance.CAssSortTool();

			return m_assSortTool;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			return null;
		}

	}

	private DetailVO[] catAss(DetailVO[] details) throws BusinessException,
			Exception {
		if (details == null || details.length == 0)
			return details;
		String[] Ids = null;
		Vector vecIds = new Vector();
		HashMap tempmap = new HashMap();
		for (int j = 0; j < details.length; j++) {
			if (details[j].getAssid() == null)
				continue;
			if (details[j].getAss() == null
					&& tempmap.get(details[j].getAssid()) == null) {
				vecIds.addElement(details[j].getAssid());
				tempmap.put(details[j].getAssid(), details[j].getAssid());
			}
		}
		if (vecIds.size() == 0) {
			VoWizard wizard = new VoWizard();

			wizard.setMatchingIndex(new int[] { VoucherKey.D_DETAILINDEX },
					null);

			wizard.sort(details, new int[] { VoucherKey.D_DETAILINDEX });
			return details;
		}
		Ids = new String[vecIds.size()];
		vecIds.copyInto(Ids);

		//
		nc.vo.fipub.freevalue.GlAssVO[] glAssVo = NCLocator.getInstance()
				.lookup(IFreevaluePub.class)
				.queryAllByIDs(Ids, null, Module.GL);

		if (glAssVo == null)
			throw new BusinessException("Error AssIDs::" + vecIds);
		HashMap assvocache = new HashMap();
		for (int i = 0; i < glAssVo.length; i++) {
			glAssVo[i].setAssID(glAssVo[i].getAssID().trim());
			assvocache.put(glAssVo[i].getAssID(), glAssVo[i].getAssVos());
		}

		for (int i = 0; i < details.length; i++) {
			if (details[i].getAssid() != null && details[i].getAss() == null) {
				details[i].setAss((nc.vo.glcom.ass.AssVO[]) assvocache
						.get(details[i].getAssid()));
			}
		}

		VoWizard wizard = new VoWizard();

		wizard.setMatchingIndex(new int[] { VoucherKey.D_DETAILINDEX }, null);

		wizard.sort(details, new int[] { VoucherKey.D_DETAILINDEX });

		return details;
	}

	/**
	 * 
	 * 是否需要对分录进行余额控制
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param details
	 * @return
	 * @see
	 * @since V6.0
	 * @hurh
	 */
	private DetailVO[] filterDetailForBalanceCtrl(DetailVO[] details) {
		if (details == null || details.length <= 0) {
			return new DetailVO[0];
		}
		DetailVO detail = details[0];
		if ((detail.getTempsaveflag() != null && detail.getTempsaveflag()
				.booleanValue())
				|| (detail.getDiscardflag() != null && detail.getDiscardflag()
						.booleanValue())
				|| detail.getErrmessage() != null
				|| detail.getErrmessage2() != null
				|| detail.getErrmessageh() != null) {
			details = new DetailVO[0];
		}
		return details;
	}

	/**
	 * 获取期初保存凭证时取科目版本的日期
	 * 
	 * @param voucher
	 * @return
	 */
	public String getStdDateForInitSave(VoucherVO voucher)
			throws BusinessException {
		if (null == voucher || null == voucher.getPrepareddate()) {
			return null;
		}
		if (voucher.getPrepareddate().toStdString().startsWith("0001")) {
			String[] startPeriod = GLParaDataCache.getInstance()
					.getStartPeriod(voucher.getPk_accountingbook(),
							GlNodeConst.NCGL);// 获取目的账簿的启用期间
			String accMonth = "01";
			if (voucher.getYear().equals(startPeriod[0])) {
				accMonth = startPeriod[1];
			}
			AccountCalendar calendar = CalendarUtilGL
					.getAccountCalendarByAccountBook(voucher
							.getPk_accountingbook());
			calendar.set(voucher.getYear(), accMonth);
			return calendar.getMonthVO().getBegindate().toStdString();
		}
		return voucher.getPrepareddate().toStdString();

	}

	/**
	 * 
	 * 方法说明：
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param voucher
	 * @return
	 * @see
	 * @since V6.0
	 * @hurh
	 */
	private HashMap getAccountMap(VoucherVO voucher, HashMap tempaccsubj) {
		if (tempaccsubj == null) {
			tempaccsubj = new HashMap();
		}
		if (voucher.getDetails().length <= 0) {
			return tempaccsubj;
		}
		try {
			HashSet<String> pkSet = new HashSet<String>();
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				pkSet.add(voucher.getDetail(i).getPk_accasoa());
			}
			AccountVO[] acc = AccountUtilGL.queryByPks(pkSet.toArray(new String[0]),
					getStdDateForInitSave(voucher));
			AccountVO voTemp = null;
			if (acc != null)
				for (int i = 0; i < acc.length; i++) {
					voTemp = acc[i];
					tempaccsubj.put(voTemp.getPk_accasoa(), voTemp);
				}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000582")/*
																				 * @
																				 * res
																				 * "科目信息有错！"
																				 */);
		}

		return tempaccsubj;
	}

	/**
	 * 
	 * 方法说明：
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param details
	 * @param tempaccsubj
	 * @return
	 * @see
	 * @since V6.0
	 * @hurh
	 */
	private HashMap getAccountMap(DetailVO[] details, HashMap tempaccsubj) {
		if (tempaccsubj == null) {
			tempaccsubj = new HashMap();
		}
		if (details == null || details.length <= 0) {
			return tempaccsubj;
		}
		try {
			String[] pk_accsubj = new String[details.length];
			for (int i = 0; i < details.length; i++) {
				pk_accsubj[i] = details[i].getPk_accasoa();
			}
			// AccountVO[] acc = getAccsubj(pk_accsubj,
			// details[0].getPrepareddate().toStdString());
			AccountVO[] acc = AccountUtilGL.queryByPks(pk_accsubj, details[0]
					.getPrepareddate().toStdString());

			// hurh 将辅助核算 挂到科目上，后面不再查询，提高效率
			// IAccountAssPubService accountassservice = (IAccountAssPubService)
			// NCLocator.getInstance().lookup(IAccountAssPubService.class.getName());
			// Map<String,List<AccAssVO>> asMap =
			// accountassservice.queryAllByAccPKs(pk_accsubj,details[0].getPrepareddate().toStdString());
			AccountVO voTemp = null;
			if (acc != null)
				for (int i = 0; i < acc.length; i++) {
					voTemp = acc[i];
					// voTemp.setAccass(asMap.get(voTemp.getPk_accasoa()) ==
					// null ? null :
					// asMap.get(voTemp.getPk_accasoa()).toArray(new
					// AccAssVO[0]));
					tempaccsubj.put(voTemp.getPk_accasoa(), voTemp);
				}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000582")/*
																				 * @
																				 * res
																				 * "科目信息有错！"
																				 */);
		}

		return tempaccsubj;
	}
}
