package nc.ui.arap.comreport;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import nc.itf.fi.pub.Currency;
import nc.itf.fipub.report.IPubReportConstants;
import nc.itf.fipub.report.IReportQueryCond;
import nc.itf.org.IOrgConstBasic;
import nc.pubitf.accperiod.AccountCalendar;
import nc.ui.arap.pub.ArapUiUtil;
import nc.ui.bd.ref.model.AccPeriodDefaultRefModel;
import nc.ui.fipub.comp.ReportUiUtil;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.utils.fipub.FipubReportResource;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.ReportInitializeVO;
import nc.vo.fipub.report.ReportQueryCondVO;
import nc.vo.fipub.utils.RefConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.querytemplate.queryscheme.QuerySchemeObject;
import nc.vo.querytemplate.queryscheme.QuerySchemeVO;
import nc.vo.uif2.LoginContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ufida.dataset.IContext;
import com.ufida.report.anareport.FreeReportFucletContextKey;

/**
 * 收付往来帐表查询对话框<br>
 *
 * @author 梁国荣<br>
 * @since V60 2010-12-15<br>
 */
public class ArapReportDefaultQryDlg extends ArapBaseReportQryDlg {
	/**
	 * 序列化版本ID<br>
	 */
	private static final long serialVersionUID = 1L;

//	private static final String ASSO_OBJ_COMB = "assoObjComb"; // 往来对象
//	private static final String QRY_MODE_COMB = "qryModeComb"; // 查询方式
//	private static final String QRY_DIRECT_COMB = "qryDirectComb"; // 查询方向
//	private static final String QRY_SCOPE_COMB = "qryScopeComb"; // 查询范围
//	private static final String BILL_STATE_COMB = "billStateComb"; // 单据状态
//	private static final String BEGIN_TIME_REF = "beginTimeRef"; // 开始时间
//	private static final String AEND_TIME_REF = "endTimeRef"; // 结束时间
//	private static final String CURRENCY_REF = "currencyRef"; // 币种
//	private static final String FINANCIAL_ORG_REF = "financialOrgRef"; // 财务组织

	private static final String TIME_TYPE_LABEL = "timeTypeLabel";

	private static final List<String> diffReport = new ArrayList<String>();
	static {
		diffReport.add(IPubReportConstants.GENERAL_REPORT);
	}

	public ArapReportDefaultQryDlg(Container parent, IContext context,
			String strNodeCode, int iSysCode, TemplateInfo ti, String title) {
		super(parent, context, strNodeCode, iSysCode, ti, title);
	}

	protected List<Component> getComponentList() throws BusinessException {
		List<Component> normalCondCompList = super.getNormalCondCompList();
		if (normalCondCompList.size() > 0) {
			return normalCondCompList;
		}

		// 获取报表初始化上下文环境
		ReportInitializeVO reportInitializeVO = (ReportInitializeVO) getReportInitializeVO().getParentVO();
		// 获取报表类型
		String reportType = reportInitializeVO.getReporttype();
		// 获取收付方向
		String rec_pay_direct = getRecPayDirect();

		// 往来对象
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0001935")/*@res "往来对象"*/));
		UIComboBox assoObjComb = new UIComboBox();
		if (IPubReportConstants.DIRECT_REC.equals(rec_pay_direct)) {
			// 方向：收
			assoObjComb.addItem(new DefaultConstEnum(IPubReportConstants.ASSO_OBJ_CUSTOMER, FipubReportResource.getAssoObjCusLbl()));
		} else {
			// 方向：付
			assoObjComb.addItem(new DefaultConstEnum(IPubReportConstants.ASSO_OBJ_SUPPLIER, FipubReportResource.getAssoObjSupLbl()));
		}
		assoObjComb.addItems(new DefaultConstEnum[] {
				new DefaultConstEnum(IPubReportConstants.ASSO_OBJ_DEPT, FipubReportResource.getAssoObjDeptLbl()),
				new DefaultConstEnum(IPubReportConstants.ASSO_OBJ_BUSIMAN, FipubReportResource.getAssoObjPsnLbl()),
				new DefaultConstEnum(IPubReportConstants.ASSO_OBJ_CUS_SUP, FipubReportResource.getAssoObjCusSupLbl()) });
		normalCondCompList.add(assoObjComb);
		addComponent(ArapValidationUtil.ASSO_OBJ_COMB, assoObjComb);

		if (!diffReport.contains(reportType)) {
			// 查询方式
			normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0002777")/*@res "查询方式"*/));
			UIComboBox qryModeComb = new UIComboBox();
			qryModeComb.addItems(new DefaultConstEnum[] {
					new DefaultConstEnum(IPubReportConstants.QUERY_MODE_MONTH, FipubReportResource.getQueryModeMonthLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_MODE_DATE, FipubReportResource.getQueryModeDateLbl()) });
			qryModeComb.addItemListener(new ComboBoxItemListener());
			normalCondCompList.add(qryModeComb);
			addComponent(ArapValidationUtil.QRY_MODE_COMB, qryModeComb);
		}

		// 查询方向
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006agr","12006agr0004")/*@res "查询方向"*/));
		UIComboBox qryDirectComb = new UIComboBox();
		if (IPubReportConstants.DIRECT_REC.equals(rec_pay_direct)) {
			// 方向：收
			qryDirectComb.addItems(new DefaultConstEnum[] {
					new DefaultConstEnum(IPubReportConstants.QUERY_DIRECT_REC_GAT, FipubReportResource.getQueryDirRecivableGatLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_DIRECT_REC, FipubReportResource.getQueryDirRecivableLbl()) });
		} else {
			// 方向：付
			qryDirectComb.addItems(new DefaultConstEnum[] {
					new DefaultConstEnum(IPubReportConstants.QUERY_DIRECT_PAY_GAT, FipubReportResource.getQueryDirPayablePayLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_DIRECT_PAY, FipubReportResource.getQueryDirPayableLbl()) });
		}
		qryDirectComb.addItem(new DefaultConstEnum(IPubReportConstants.QUERY_DIRECT_ALL, FipubReportResource.getQueryDirAllLbl()));
		normalCondCompList.add(qryDirectComb);
		addComponent(ArapValidationUtil.QRY_DIRECT_COMB, qryDirectComb);

		// 查询范围
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006agr","12006agr0003")/*@res "查询范围"*/));
		UIComboBox qryScopeComb = new UIComboBox();
		if (IPubReportConstants.DIRECT_REC.equals(rec_pay_direct)) {
			// 方向：收
			qryScopeComb.addItems(new DefaultConstEnum[] {
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_ALL_REC, FipubReportResource.getQueryScopeAllRecivableLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_CONFIRM_REC, FipubReportResource.getQueryScopeConfirmRecivableLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_UNCONFIRM_REC, FipubReportResource.getQueryScopeUnconfirmRecivableLbl())});
		} else {
			// 方向：付
			qryScopeComb.addItems(new DefaultConstEnum[] {
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_ALL_PAY, FipubReportResource.getQueryScopeAllPayableLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_CONFIRM_PAY, FipubReportResource.getQueryScopeConfirmPayableLbl()),
					new DefaultConstEnum(IPubReportConstants.QUERY_SCOPE_UNCONFIRM_PAY, FipubReportResource.getQueryScopeUnconfirmPayableLbl())});
		}
		normalCondCompList.add(qryScopeComb);
		addComponent(ArapValidationUtil.QRY_SCOPE_COMB, qryScopeComb);

		if (diffReport.contains(reportType)) { // 总账表(为了界面友好性，将币种放在前面)
			// 币种
			normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0011")/*@res "币　　种"*/));
			UIRefPane currencyRef = new UIRefPane(RefConstant.REF_NODENAME_CURRENCY); // 币种档案
			normalCondCompList.add(currencyRef);
			addComponent(ArapValidationUtil.CURRENCY_REF, currencyRef);
		}

		// 开始时间
		UILabel timeTypeLabel = getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0043")/*@res "月　　份"*/);
		normalCondCompList.add(timeTypeLabel);
		addComponent(TIME_TYPE_LABEL, timeTypeLabel);
		UIRefPane beginTimeRef = new UIRefPane(RefConstant.REF_NODENAME_ACCPERIOD); // 会计期间档案
		normalCondCompList.add(beginTimeRef);
		addComponent(ArapValidationUtil.BEGIN_TIME_REF, beginTimeRef);

		// 结束时间
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003365")/*@res "至"*/));
		UIRefPane endTimeRef = new UIRefPane(RefConstant.REF_NODENAME_ACCPERIOD); // 会计期间档案
		normalCondCompList.add(endTimeRef);
		addComponent(ArapValidationUtil.END_TIME_REF, endTimeRef);

		if (!diffReport.contains(reportType)) { // 余额表、明细账(为了界面友好性，将币种放在后面)
			// 币种
			normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0011")/*@res "币　　种"*/));
			UIRefPane currencyRef = new UIRefPane(RefConstant.REF_NODENAME_CURRENCY); // 币种档案
			normalCondCompList.add(currencyRef);
			addComponent(ArapValidationUtil.CURRENCY_REF, currencyRef);
		}

		// 单据状态
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0000804")/*@res "单据状态"*/));
		UIComboBox billStateComb = new UIComboBox(); 
		billStateComb.addItems(new DefaultConstEnum[] {
				new DefaultConstEnum(IPubReportConstants.BILL_STATUS_ALL, FipubReportResource.getBillStatusAllLbl()),
				new DefaultConstEnum(IPubReportConstants.BILL_STATUS_SAVE, FipubReportResource.getBillStatusSaveLbl()),
				new DefaultConstEnum(IPubReportConstants.BILL_STATUS_CONFIRM, FipubReportResource.getBillStatusAuditLbl()),
				new DefaultConstEnum(IPubReportConstants.BILL_STATUS_EFFECT, FipubReportResource.getBillStatusEffectLbl())});
		normalCondCompList.add(billStateComb);
		addComponent(ArapValidationUtil.BILL_STATE_COMB, billStateComb);

		// 财务组织
		normalCondCompList.add(getShowLabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("arap","1arap0020")/*@res "财务组织"*/));
		UIRefPane financialOrgRef = new UIRefPane(RefConstant.REF_NODENAME_FINANCEORG); // 财务组织
		financialOrgRef.getRefUIConfig().setDisabledDataButtonShow(true);
		financialOrgRef.setPK(ReportUiUtil.getDefaultOrgUnit());
		financialOrgRef.setMultiSelectedEnabled(true);
		String[] permissionOrgs = ArapUiUtil.getPermissionOrgs(((LoginContext) getContext().getAttribute(FreeReportFucletContextKey.PRIVATE_CONTEXT)));
		//editor tianjlc 节点的组织类型为全局时，不设置数据权限
		if(!(permissionOrgs.length==1&&permissionOrgs[0].equals(IOrgConstBasic.GLOBEORG))){
			financialOrgRef.getRefModel().setFilterPks(permissionOrgs); // 数据权限控制
		}
		financialOrgRef.addValueChangedListener(new OrgChangedListener());
		normalCondCompList.add(financialOrgRef);
		addComponent(ArapValidationUtil.FINANCIAL_ORG_REF, financialOrgRef);

		normalCondCompList.add(getShowLabel(FipubReportResource.getLocalCurrencyTpyLbl()));
		UIComboBox locCurrTypeComb = new UIComboBox();
		locCurrTypeComb.addItem(new DefaultConstEnum(IPubReportConstants.ORG_LOCAL_CURRENCY, FipubReportResource.getOrgLocalCurrencyLbl()));
		if (Currency.isStartGroupCurr(ReportUiUtil.getPK_group())) {
			locCurrTypeComb.addItem(new DefaultConstEnum(IPubReportConstants.GROUP_LOCAL_CURRENCY, FipubReportResource.getGroupLocalCurrencyLbl()));
		}
		if (Currency.isStartGlobalCurr(null)) {
			locCurrTypeComb.addItem(new DefaultConstEnum(IPubReportConstants.GLOBLE_LOCAL_CURRENCY, FipubReportResource.getGlobleLocalCurrencyLbl()));
		}
		normalCondCompList.add(locCurrTypeComb);
		addComponent(LOCAL_CURRENCY_TYPE_COMB, locCurrTypeComb);

		setQueryPeriod(IPubReportConstants.QUERY_MODE_MONTH, null);

		return normalCondCompList;
	}

	protected List<Component> getExtendCondCompList() throws BusinessException {
		List<Component> extendCondCompList = super.getExtendCondCompList();
		if (extendCondCompList.size() > 0) {
			return extendCondCompList;
		}

		// 获取报表初始化上下文环境
		ReportInitializeVO reportInitializeVO = (ReportInitializeVO) getReportInitializeVO().getParentVO();
		// 获取报表类型
		String reportType = reportInitializeVO.getReporttype();

		if (IPubReportConstants.DETAIL_REPORT.equals(reportType)) {
			extendCondCompList.add(new UILabel());
			UICheckBox showDateTotalChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0044")/*@res "显示日小计"*/);
			showDateTotalChk.setSelected(true);
			extendCondCompList.add(showDateTotalChk);
			addComponent(IArapReportConstants.KEY_SHOW_DATE_TOTAL, showDateTotalChk);
			UICheckBox notshowBackEstiChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0045")/*@res "已回冲的暂估单据不显示"*/);
			notshowBackEstiChk.setSelected(false);
			extendCondCompList.add(notshowBackEstiChk);
			addComponent(IArapReportConstants.KEY_NOTSHOW_BACKESTI, notshowBackEstiChk);
		} else if (IPubReportConstants.BALANCE_REPORT.equals(reportType)) {
			UICheckBox noOccChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0046")/*@res "无发生不显示"*/);
			noOccChk.setSelected(false);
			extendCondCompList.add(new UILabel());
			extendCondCompList.add(noOccChk);
			addComponent(IArapReportConstants.KEY_NOOCC_NOSHOW, noOccChk);
			UICheckBox noBalOccChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0047")/*@res "无余额无发生不显示"*/);
			noBalOccChk.setSelected(false);
			extendCondCompList.add(noBalOccChk);
			addComponent(IArapReportConstants.KEY_NOBAL_NOOCC_NOSHOW, noBalOccChk);
		}

		return extendCondCompList;
	}

	/**
	 * 覆写父类方法，执行必要的业务校验
	 */
	protected String doBusiCheck(){
		// 执行父类校验
		String errMsg = super.doBusiCheck();
		if (!StringUtils.isEmpty(errMsg)) {
			return errMsg;
		}

		try {

			// 获取报表初始化上下文环境
			ReportInitializeVO reportInitializeVO = (ReportInitializeVO) getReportInitializeVO().getParentVO();
			// 获取报表类型
			String reportType = reportInitializeVO.getReporttype();
			// 获取报表查询条件VO
			ReportQueryCondVO queryCondVO = (ReportQueryCondVO) getReportQueryCondVO();

			UFDate beginDate = queryCondVO.getBeginDate();
			UFDate endDate = queryCondVO.getEndDate();

			// ☆查询日期不可为空
			if (beginDate == null || endDate == null) {
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0036")/*@res "查询日期不能为空！"*/;
			}

			// ☆明细账查询，要求查询期间不超过六个月
			if (IPubReportConstants.DETAIL_REPORT.equals(reportType)) {
				int days = UFDate.getDaysBetween(beginDate, endDate);
				boolean hasConcreteQryObjValue = false;
				for (QryObj qryObj : queryCondVO.getQryObjs()) {
					if (qryObj.getValues().size() > 0) {
						hasConcreteQryObjValue = true;
						break;
					}
				}

				if (hasConcreteQryObjValue == false && days > 183) {
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0048")/*@res "期间大于六个月，请选择查询对象具体值查询！"*/;
				}
			}

			// ☆查询开始时间不能早于系统启用时间
			return checkSystemStartDate(queryCondVO.getPk_orgs(), queryCondVO.getModuleId(), beginDate);
		} catch (BusinessException e) {
			errMsg = e.getMessage();
		}

		return errMsg;
	}

	/**
	 * 查询方式变化监听器<br>
	 *
	 * @author 梁国荣<br>
	 * @since V60<br>
	 */
	class ComboBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setQueryPeriod((String) ((DefaultConstEnum) e.getItem()).getValue(), null);
			}
		}
	}

	protected void handleOrgChangeEvent(ValueChangedEvent event) throws BusinessException {
		super.handleOrgChangeEvent(event);
		ReportQueryCondVO queryCondVO = (ReportQueryCondVO) getReportQueryCondVO();
		setQueryPeriod(queryCondVO.getQryMode(), queryCondVO);
	}

	/**
	 * 设置查询期间范围<br>
	 *
	 * @param qryMode 查询方式<br>
	 * @author 梁国荣<br>
	 */
	protected void setQueryPeriod(String qryMode, IReportQueryCond queryCondVO) {
		UFDate currBusiDate = ReportUiUtil.getBusiDate();
		UFDate beginDate = queryCondVO == null || queryCondVO.getBeginDate() == null ? currBusiDate : queryCondVO.getBeginDate();
		UFDate endDate = queryCondVO == null || queryCondVO.getEndDate() == null ? currBusiDate : queryCondVO.getEndDate();
		// 获取界面控件
		UILabel tempLabel = (UILabel) getComponent(TIME_TYPE_LABEL);
		UIRefPane tempBeginRefPane = (UIRefPane) getComponent(ArapValidationUtil.BEGIN_TIME_REF);
		UIRefPane tempEndRefPane = (UIRefPane) getComponent(ArapValidationUtil.END_TIME_REF);
		if (IPubReportConstants.QUERY_MODE_MONTH.equals(qryMode)) {
			tempLabel.setText(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0043")/*@res "月　　份"*/);
			tempBeginRefPane.setRefNodeName(RefConstant.REF_NODENAME_ACCPERIOD); // 会计期间档案
			tempEndRefPane.setRefNodeName(RefConstant.REF_NODENAME_ACCPERIOD); // 会计期间档案

			String[] pk_orgs = getPk_org();
			try {
				if (!ArrayUtils.isEmpty(pk_orgs)) {
					AccountCalendar calendar = AccountCalendar.getInstanceByPk_org(pk_orgs[0]);
					if(calendar.getYearVO() != null){
						String defaultpk_accperiodscheme = calendar.getYearVO().getPk_accperiodscheme();
						((AccPeriodDefaultRefModel) tempBeginRefPane.getRefModel()).setDefaultpk_accperiodscheme(defaultpk_accperiodscheme);
						((AccPeriodDefaultRefModel) tempEndRefPane.getRefModel()).setDefaultpk_accperiodscheme(defaultpk_accperiodscheme);
					}
					calendar.setDate(beginDate);
					String pk_accperiodmonthbegin = calendar.getMonthVO().getPk_accperiodmonth();
					calendar.setDate(endDate);
					String pk_accperiodmonthend = calendar.getMonthVO().getPk_accperiodmonth();

					tempBeginRefPane.setPK(pk_accperiodmonthbegin);
					tempEndRefPane.setPK(pk_accperiodmonthend);
				}
			} catch (BusinessException e) {
				ExceptionHandler.consume(e);
			}
		} else {
			tempLabel.setText(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0049")/*@res "日　　 期"*/);

			tempBeginRefPane.setRefNodeName(RefConstant.REF_NODENAME_CALENDAR); // 日历
			tempEndRefPane.setRefNodeName(RefConstant.REF_NODENAME_CALENDAR); // 日历

			tempBeginRefPane.setValueObj(beginDate); // 开始时间
			tempEndRefPane.setValueObj(endDate); // 结束时间
		}

		tempLabel.repaint();
	}
	
	/**
	 * 功能：设置常用查询条件VO
	 */
	protected void setQueryCond(ReportQueryCondVO qryCondVO) throws BusinessException {
		UIComboBox tempComboBox = null;

		// 往来对象
		qryCondVO.setAssoObj(((UIComboBox) getComponent(ArapValidationUtil.ASSO_OBJ_COMB)).getSelectdItemValue().toString());

		// 查询方式
		tempComboBox = (UIComboBox) getComponent(ArapValidationUtil.QRY_MODE_COMB);
		if (tempComboBox != null) {
			qryCondVO.setQryMode((String) tempComboBox.getSelectdItemValue());
		} else if (IPubReportConstants.GENERAL_REPORT.equals(((ReportInitializeVO)
				getReportInitializeVO().getParentVO()).getReporttype())) {
			qryCondVO.setQryMode(IPubReportConstants.QUERY_MODE_MONTH);
		}

		// 查询方向
		qryCondVO.setQryDirect((String) ((UIComboBox) getComponent(ArapValidationUtil.QRY_DIRECT_COMB)).getSelectdItemValue());

		// 查询范围
		qryCondVO.setQryScope((String) ((UIComboBox) getComponent(ArapValidationUtil.QRY_SCOPE_COMB)).getSelectdItemValue());

		// 开始日期
		Object beginVal = ((UIRefPane) getComponent(ArapValidationUtil.BEGIN_TIME_REF)).getValueObj();
		UFDate beginDate = null;
		if (beginVal == null || "".equals(beginVal)) {
			beginDate = null;
		} else if (beginVal instanceof UFDate) {
			beginDate = new UFDate(((UFDate) beginVal).toLocalString(), true);
		} else if (beginVal.getClass().isArray()) {
			Object[] arrObj = (Object[]) beginVal;
			beginDate = AccountCalendar.getInstanceByAccperiodMonth(arrObj[0].toString()).getMonthVO().getBegindate();
		} else {
			beginDate = new UFDate(beginVal.toString(), true);
		}
		qryCondVO.setBeginDate(beginDate);

		// 结束日期
		Object endVal = ((UIRefPane) getComponent(ArapValidationUtil.END_TIME_REF)).getValueObj();
		UFDate endDate = null;
		if (endVal == null || "".equals(endVal)) {
			endDate = null;
		} else if (endVal instanceof UFDate) {
			endDate = new UFDate(((UFDate) endVal).toLocalString(), false);
		} else if (endVal.getClass().isArray()) {
			Object[] arrObj = (Object[]) endVal;
			endDate = AccountCalendar.getInstanceByAccperiodMonth(arrObj[0].toString()).getMonthVO().getEnddate();
		} else {
			endDate = new UFDate(endVal.toString(), false);
		}
		qryCondVO.setEndDate(endDate);

		// 币种
		qryCondVO.setPk_currency(((UIRefPane) getComponent(ArapValidationUtil.CURRENCY_REF)).getRefPK());
		// 单据状态
		qryCondVO.setBillState((String) ((UIComboBox) getComponent(ArapValidationUtil.BILL_STATE_COMB)).getSelectdItemValue());

		qryCondVO.setLocalCurrencyType((String) ((UIComboBox) getComponent(LOCAL_CURRENCY_TYPE_COMB)).getSelectdItemValue());

		// 设置扩展查询条件
		setExtendQueryCond(qryCondVO);
	}

	/**
	 * 设置扩展查询条件
	 *
	 * @param qryCondVO
	 */
	protected void setExtendQueryCond(ReportQueryCondVO qryCondVO) {
		String[] extKey = { IArapReportConstants.KEY_SHOW_DATE_TOTAL, // 是否显示日小计
				IArapReportConstants.KEY_NOTSHOW_BACKESTI, // 已回冲的暂估单据不显示
				IArapReportConstants.KEY_NOOCC_NOSHOW,// 无发生不显示
				IArapReportConstants.KEY_NOBAL_NOOCC_NOSHOW // 无发生无余额不显示
		};

		UICheckBox chkBox = null;
		for (String key : extKey) {
			if (getComponent(key) != null) {
				chkBox = (UICheckBox) getComponent(key);
				qryCondVO.getUserObject().put(key, UFBoolean.valueOf(chkBox.isSelected()));
			}
		}
	}

	protected void resetUserReportQueryCondVO(IReportQueryCond queryCond) {
		ReportQueryCondVO queryCondVO = (ReportQueryCondVO) queryCond;
		((UIComboBox) getComponent(ArapValidationUtil.ASSO_OBJ_COMB)).setSelectedItem(Integer.parseInt(queryCondVO.getAssoObj())); // 往来对象
		if (getComponent(ArapValidationUtil.QRY_MODE_COMB) != null) {
			((UIComboBox) getComponent(ArapValidationUtil.QRY_MODE_COMB)).setSelectedItem(queryCondVO.getQryMode()); // 查询方式
		}
		((UIComboBox) getComponent(ArapValidationUtil.QRY_DIRECT_COMB)).setSelectedItem(queryCondVO.getQryDirect()); // 查询方向
		((UIComboBox) getComponent(ArapValidationUtil.QRY_SCOPE_COMB)).setSelectedItem(queryCondVO.getQryScope()); // 查询范围
		((UIComboBox) getComponent(ArapValidationUtil.BILL_STATE_COMB)).setSelectedItem(queryCondVO.getBillState()); // 单据状态
		((UIRefPane) getComponent(ArapValidationUtil.CURRENCY_REF)).setPK(queryCondVO.getPk_currency()); // 币种
		((UIRefPane) getComponent(ArapValidationUtil.FINANCIAL_ORG_REF)).setPKs(queryCondVO.getPk_orgs()); // 财务组织
		((UIComboBox) getComponent(LOCAL_CURRENCY_TYPE_COMB)).setSelectedItem(queryCondVO.getLocalCurrencyType()); // 返回币种
		//update for 新加坡BDA 20161111
		setQueryPeriod(queryCondVO.getQryMode(), queryCondVO);

		String[] extKey = {
				IArapReportConstants.KEY_SHOW_DATE_TOTAL, // 是否显示日小计
				IArapReportConstants.KEY_NOOCC_NOSHOW,// 无发生不显示
				IArapReportConstants.KEY_NOBAL_NOOCC_NOSHOW // 无发生无余额不显示
		};

		UICheckBox chkBox = null;
		for (String key : extKey) {
			if (getComponent(key) != null) {
				chkBox = (UICheckBox) getComponent(key);
				UFBoolean isselected = (UFBoolean) queryCondVO.getUserObject().get(key);
				if(isselected != null){ 
					chkBox.setSelected(isselected.booleanValue());
				}
			}
		}

		// =================================
		setPk_org(queryCondVO.getPk_orgs());
		// =================================
	}

	protected String[] getPk_org() {
		return ((UIRefPane) getComponent(ArapValidationUtil.FINANCIAL_ORG_REF)).getRefPKs();
	}

	public void setPk_org(String[] pkOrgs) {
		super.setPk_org(pkOrgs);
		String pk_org = null;
		if (pkOrgs != null && pkOrgs.length > 0) {
			pk_org = pkOrgs[0];
		}

		String[] refKey = { ArapValidationUtil.CURRENCY_REF, ArapValidationUtil.BEGIN_TIME_REF, ArapValidationUtil.END_TIME_REF };
		UIRefPane ref = null;
		for (String key : refKey) {
			ref = (UIRefPane) (getComponent(key));
			if (ref.getRefModel() != null) {
				ref.setPk_org(pk_org);
			}
		}
	}
	
	@Override
	public IQueryScheme getQuerySchemeByName(String name) {
		// TODO Auto-generated method stub
		IQueryScheme queryScheme = super.getQuerySchemeByName(name);
		QuerySchemeObject qsObj = findQsVoByName(name).getQSObject4Blob();
		String[] userDefineKeys = qsObj.getUserDefineKeys();
		if(userDefineKeys!=null && userDefineKeys.length>0){
			for(String userKey : userDefineKeys){
				queryScheme.put(userKey, qsObj.get(userKey));
			}
		}
		return queryScheme;
	}
	/**
	 * 父类中的方法，私有方法，不继承
	 * @param name
	 * @return
	 */
	private QuerySchemeVO findQsVoByName(String name) {
		if(StringUtils.isBlank(name)){
			return null;
		}
		QuerySchemeVO[] vos = getQuerySchemeVOs();
		if (vos == null || vos.length == 0) return null;
		for (QuerySchemeVO vo : vos) {
			if (vo.getCurLangNameValue().equals(name)) {
				return vo;
			}
		}
		return null;
	}
	protected boolean isShowLocalCurrencySelect() {
		return false;
	}

}

// /:~
