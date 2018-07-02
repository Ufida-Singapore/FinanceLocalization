package nc.ui.cmp.accountagiotage;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.TableModel;

import nc.bd.accperiod.AccperiodmonthAccessor;
import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.exception.ComponentException;
import nc.cmp.agiotage.utils.CmpAgiotageProxy;
import nc.cmp.pub.exception.ExceptionHandler;
import nc.cmp.utils.OrgUnitsUtils;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.itf.cmp.pub.Currency;
import nc.itf.cmp.settleaccount.ISettleAccountService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.setting.defaultdata.OrgSettingAccessor;
import nc.ui.bd.manage.UIRefCellEditor;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.bd.ref.model.CashAccountRefModel;
import nc.ui.cmp.control.PeriodComboBox;
import nc.ui.cmp.control.YearComboBox;
import nc.ui.cmp.filter.MainOrgUserPermissionProccessor;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.vo.arap.transaction.DataValidateException;
import nc.vo.bd.currinfo.CurrinfoVO;
import nc.vo.bd.period.AccperiodVO;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageAggVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageVO;
import nc.vo.cmp.agiotage.CMPAgiotageBzVO;
import nc.vo.cmp.agiotage.CMPAgiotageVO;
import nc.vo.cmp.agiotage.CMPRemoteTransferVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.AppContext;
import nc.vo.tmpub.util.StringUtil;

public class AccountAgiotagePanel extends nc.ui.pub.beans.UIPanel {

	private AccountAgiotageUI ui;

	/**
	 *
	 */
	private static final long serialVersionUID = 5268782646027209399L;

	class refEditor extends UIRefCellEditor {
		/**
		 *
		 */
		private static final long serialVersionUID = -5002513599202361227L;

		public refEditor(UIRefPane pane) {
			super(pane);
		}
	}

	private nc.ui.pub.beans.UITablePane ivjUITablePane = null;

	private nc.ui.pub.beans.UIRefPane ivjCalDate = null;

	private nc.ui.pub.beans.UIRefPane m_refAccount = null;

	private nc.ui.pub.beans.UIRefPane m_refManageAccount = null;

	private nc.ui.pub.beans.UIPanel ivjUIPanel4 = null;
	private nc.ui.pub.beans.UILabel ivjUILabelPeriod = null;
	private nc.ui.pub.beans.UILabel ivjUILabelYear = null;

	private String[] m_sTabTitle = { "bzbm",
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0001755")/*
																							 * @res
																							 * "币种"
																							 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0022")/*
																								 * @res
																								 * "上次计算期间"
																								 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																								 * @res
																								 * "现金账户"
																								 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																							 * @res
																							 * "银行账户"
																							 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004045") /*
																							 * @res
																							 * "选择标志"
																							 */};

	private Hashtable<String, String[]> m_pkAccids = new Hashtable<String, String[]>();/* 币种对应选中银行账户Pks */

	private Hashtable<String, String[]> m_pkManegeAccids = new Hashtable<String, String[]>();/* 币种对应选中管理账户Pks */

//	private String accwherePart = null;

//	private String manegeAccwherePart = null;

	private YearComboBox ivjCboYear = null;

	private String pk_orgbook;
	private PeriodComboBox ivjCboPeriod = null;
	private UILabel financeOrgQueryLabel = null;
	// 业务单元
	private UIRefPane financeOrgQueryPanel = null;

	public AccountAgiotagePanel(AccountAgiotageUI ui) {
		super();
		this.ui = ui;
		initialize();
	}

	/**
	 * AgiotegePanel 构造子注解。
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 */
	public AccountAgiotagePanel(java.awt.LayoutManager p0) {
		super(p0);
	}

	/**
	 * AgiotegePanel 构造子注解。
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 * @param p1
	 *            boolean
	 */
	public AccountAgiotagePanel(java.awt.LayoutManager p0, boolean p1) {
		super(p0, p1);
	}

	/**
	 * AgiotegePanel 构造子注解。
	 * 
	 * @param p0
	 *            boolean
	 */
	public AccountAgiotagePanel(boolean p0) {
		super(p0);
	}

	/**
	 * 银行账号+现金账户 创建日期：(2003-9-30 14:42:22)
	 * 
	 * @return nc.ui.pub.beans.UIRefPane
	 */
	public UIRefPane getBankAccount() {

		if (m_refAccount == null) {
			try {
				m_refAccount = new nc.ui.pub.beans.UIRefPane();
				m_refAccount.setName("account");
				m_refAccount.setPreferredSize(new java.awt.Dimension(80, 22));
				m_refAccount.setFont(new java.awt.Font("dialog", 0, 12));
				m_refAccount.setRefInputType(0);
				m_refAccount.setRefNodeName("使用权参照");
				m_refAccount.setMultiSelectedEnabled(true);
				m_refAccount.getRefModel().setDisabledDataShow(true);
				m_refAccount.getRefModel().setDisabledDataShow(true);
				m_refAccount.setPk_org(ui.getPk_org());
				m_refAccount.getRefModel().addWherePart(
						" and pk_currtype!='" + Currency.getOrgLocalCurrPK(ui.getPk_org()) + "' ");
			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);

			}
		}
		return m_refAccount;
	}

	/**
	 * 现金账户
	 */
	public UIRefPane getCashAccount() {

		if (m_refManageAccount == null) {
			try {
				m_refManageAccount = new nc.ui.pub.beans.UIRefPane();
				m_refManageAccount.setName("manegeaccount");
				m_refManageAccount.setPreferredSize(new java.awt.Dimension(80, 22));
				m_refManageAccount.setFont(new java.awt.Font("dialog", 0, 12));
				m_refManageAccount.setRefInputType(0);
				m_refManageAccount.setRefModel(new CashAccountRefModel());
				m_refManageAccount.setMultiSelectedEnabled(true);
				m_refManageAccount.getRefModel().setDisabledDataShow(true);
				m_refManageAccount.getRefModel().setDisabledDataShow(true);

				m_refManageAccount.setPk_org(ui.getPk_org());
				m_refManageAccount.getRefModel().addWherePart(
						" and pk_moneytype!='" + Currency.getOrgLocalCurrPK(ui.getPk_org()) + "' ");

				// m_refManageAccount.getRefModel().addWherePart(
				// " and pk_org='" + ui.getPk_org() + "' and pk_moneytype!='"
				// + Currency.getOrgLocalCurrPK(ui.getPk_org()) + "' ");

			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);

			}
		}
		return m_refManageAccount;
	}

	/**
	 * 返回 CboYear 特性值。
	 * 
	 * @return nc.ui.cmpcom.control.YearComboBox
	 */
	public YearComboBox getCboYear() {
		if (ivjCboYear == null) {
			try {
				ivjCboYear = new YearComboBox();
				ivjCboYear.setName("CboYear");
				ivjCboYear.refresh(ui.getPk_org());
			} catch (Exception ivjExc) {
				ExceptionHandler.consume(ivjExc);
			}
		}
		return ivjCboYear;
	}

	/**
	 * 返回当前用户在当前集团的默认财务核算账簿主键
	 * 
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	public String getDefaultAccountingBookID() {

		if (pk_orgbook == null) {
			try {
				pk_orgbook = OrgSettingAccessor.getDefaultAccountingBookID();
			} catch (Exception e) {
				ExceptionHandler.consume(e);
			}
		}
		return pk_orgbook;
	}

	/**
	 * 返回 CalDate 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIRefPane
	 */
	public nc.ui.pub.beans.UIRefPane getCalDate() {
		if (ivjCalDate == null) {
			try {
				ivjCalDate = new nc.ui.pub.beans.UIRefPane();
				ivjCalDate.setName("CalDate");
				ivjCalDate.setRefNodeName("日历");
			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);

			}
		}
		return ivjCalDate;
	}

	/**
	 * 获得输入参数
	 * 
	 * @author：wyan
	 * @return nc.vo.arap.agiotage.AgiotageVO
	 */
	public CMPAgiotageVO getInputCond() throws DataValidateException {

		CMPAgiotageVO agiotageVo = new CMPAgiotageVO();
		try {
			UITable table = getBodyPanel().getTable();
			int rowCount = table.getRowCount();
			Vector<CMPAgiotageBzVO> vSelBzbms = new Vector<CMPAgiotageBzVO>();
			for (int i = 0; i < rowCount; i++) {
				boolean xzbz = ((Boolean) table.getModel().getValueAt(i, 5)).booleanValue();
				if (xzbz) {
					CMPAgiotageBzVO voBz = new CMPAgiotageBzVO();
					String bzbm = (String) table.getModel().getValueAt(i, 0);
					String bzmc = (String) table.getModel().getValueAt(i, 1);
					String lastCalNdQj = (String) table.getModel().getValueAt(i, 2);// 上次计算的会计年度、会计期间
					String manageAccount = (String) table.getModel().getValueAt(i, 3);
					String account = (String) table.getModel().getValueAt(i, 4);
					if ((null == account || account.length() == 0)
							&& (manageAccount == null || manageAccount.length() == 0))
						throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3607agi_0", "03607agi-0024")/* @res "请选择账户" */);
					voBz.setBzbm(bzbm);
					voBz.setBzmc(bzmc);
					voBz.setLastCalNdQj(lastCalNdQj);
					vSelBzbms.addElement(voBz);
				}
			}
			if (vSelBzbms.size() == 0) {
				throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
						"03607agi-0025")/* @res "请选择要试算汇兑损益的币种！" */);
			}
			agiotageVo.setSelBzbm(vSelBzbms);
		} catch (DataValidateException ex) {
			throw ex;
		}
		return agiotageVo;
	}

	/**
	 * 加载HeadPanel
	 * 
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	private nc.ui.pub.beans.UIPanel getHeadPanel() {
		if (ivjUIPanel4 == null) {
			try {
				ivjUIPanel4 = new nc.ui.pub.beans.UIPanel();
				ivjUIPanel4.setName("HeadPanel");
				ivjUIPanel4.setPreferredSize(new java.awt.Dimension(10, 60));
				ivjUIPanel4.setLayout(getUIPanel4UILabelLayout());


				getHeadPanel().add(getFinanceOrgLabel(), getFinanceOrgLabel().getName());
				getHeadPanel().add(getFinanceOrgPanel(), getFinanceOrgPanel().getName());
				
				getHeadPanel().add(getUILabelYear(), getUILabelYear().getName());
				getHeadPanel().add(getCboYear(), getCboYear().getName());

				getHeadPanel().add(getUILabelPeriod(), getUILabelPeriod().getName());
				getHeadPanel().add(getCboPeriod(), getCboPeriod().getName());

			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);
			}
		}
		return ivjUIPanel4;
	}

	private nc.ui.pub.beans.UILabel getUILabelPeriod() {
		if (ivjUILabelPeriod == null) {
			try {
				ivjUILabelPeriod = new UILabel();
				ivjUILabelPeriod.setName("labelPeriod");
				ivjUILabelPeriod.setText(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common",
						"UC000-0002560")/* @res "期间" */);
				ivjUILabelPeriod.setBounds(179, 27, 37, 22);
			} catch (Exception ivjExc) {
				ExceptionHandler.consume(ivjExc);
			}
		}
		return ivjUILabelPeriod;
	}

	private nc.ui.pub.beans.UILabel getUILabelYear() {
		if (ivjUILabelYear == null) {
			try {
				ivjUILabelYear = new UILabel();
				ivjUILabelYear.setName("labelYear1111");
				ivjUILabelYear
						.setText(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0001802")/*
																												 * @res
																												 * "年度"
																												 */);
				ivjUILabelYear.setBounds(179, 27, 37, 22);
			} catch (Exception ivjExc) {
				ExceptionHandler.consume(ivjExc);
			}
		}
		return ivjUILabelYear;
	}

	private nc.ui.pub.beans.UILabel getFinanceOrgLabel() {
		if (financeOrgQueryLabel == null) {
			try {
				financeOrgQueryLabel = new nc.ui.pub.beans.UILabel();
				financeOrgQueryLabel.setName("financeOrgQueryLabel");
				financeOrgQueryLabel.setText(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
						"03607agi-0003")/* @res "财务组织" */);
				financeOrgQueryLabel.setForeground(java.awt.Color.black);
				financeOrgQueryLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				financeOrgQueryLabel.setILabelType(0/** Java默认(自定义) */
				);
				financeOrgQueryLabel.setFont(new java.awt.Font("dialog", 0, 12));
				financeOrgQueryLabel.setBounds(179, 27, 37, 22);
			} catch (java.lang.Throwable e) {
				ExceptionHandler.consume(e);
			}
		}
		return financeOrgQueryLabel;
	}

	public UIRefPane getFinanceOrgPanel() {
		if (financeOrgQueryPanel == null) {
			try {
				financeOrgQueryPanel = new nc.ui.pub.beans.UIRefPane();
				financeOrgQueryPanel.setName("cwzz");
				financeOrgQueryPanel.setBounds(179, 27, 37, 22);
				financeOrgQueryPanel.setRefNodeName("财务组织");
				//显示停用组织信息
				financeOrgQueryPanel.setDisabledDataButtonShow(true);
				financeOrgQueryPanel.setPK(ui.getPk_org());// panel
				//获取有权限的财物组织
				
				MainOrgUserPermissionProccessor.proccessUserPermissionOrg(financeOrgQueryPanel);

				int initFlag = 0;
				financeOrgQueryPanel.addValueChangedListener(new ValueChangedListener() {
					@Override
					public void valueChanged(ValueChangedEvent event) {
						try {
							Object newValue = event.getNewValue();
							if (newValue == null) {
								return;
							}
							ui.setPk_Org(((String[]) newValue)[0]);

							// 设置银行账户子户、现金账户参照的组织
							getBankAccount().setPK(null);
							getCashAccount().setPK(null);
							getBankAccount().setPk_org(ui.getPk_org());
							getCashAccount().setPk_org(ui.getPk_org());

							//设置会计期间
							initPeriod(ui.getPk_org());
							
							try {
								getBankAccount().getRefModel().addWherePart(
										" and pk_currtype!='" + Currency.getOrgLocalCurrPK(ui.getPk_org()) + "' ");
								// getCashAccount().getRefModel().addWherePart(
								// " and pk_org='" + ui.getPk_org() +
								// "' and pk_moneytype!='"
								// + Currency.getOrgLocalCurrPK(ui.getPk_org()) +
								// "' ");
								getCashAccount().getRefModel().addWherePart(
										" and pk_moneytype!='" + Currency.getOrgLocalCurrPK(ui.getPk_org()) + "' ");
							} catch (BusinessException e) {
								ExceptionHandler.consume(e);
							}
							initTable();
						} catch (Exception e) {
							financeOrgQueryPanel.removeValueChangedListener(this);
							financeOrgQueryPanel.setPK(event.getOldValue()); 
							financeOrgQueryPanel.addValueChangedListener(this);
							ui.showErrorMessage(e.getMessage());
							
						} 
					}
				});
			} catch (java.lang.Throwable e) {
				ExceptionHandler.consume(e);
			}
		}
		return financeOrgQueryPanel;
	}


	/**
	 * 返回 CboPeriod 特性值。
	 * 
	 * @return nc.ui.glcom.control.PeriodComboBox
	 */
	private PeriodComboBox getCboPeriod() {
		if (ivjCboPeriod == null) {
			try {
				ivjCboPeriod = new PeriodComboBox();
				ivjCboPeriod.setName("CboPeriod");
				ivjCboPeriod.setLocation(223, 27);
				refreshPeriod();
			} catch (Exception ivjExc) {
				ExceptionHandler.consume(ivjExc);
			}
		}
		return ivjCboPeriod;
	}

	private void refreshPeriod() {
		ivjCboPeriod.refreshByOrg(ui.getPk_org(), getCboYear().getSelectedYear());

	}

	/**
	 * 返回 UIPanel4UILabelLayout 特性值。
	 * 
	 * @return nc.ui.pub.beans.UILabelLayout
	 */
	private nc.ui.pub.beans.UILabelLayout getUIPanel4UILabelLayout() {
		nc.ui.pub.beans.UILabelLayout ivjUIPanel4UILabelLayout = null;
		try {
			/* 创建部件 */
			ivjUIPanel4UILabelLayout = new nc.ui.pub.beans.UILabelLayout();
			ivjUIPanel4UILabelLayout.setBottom(0);
			ivjUIPanel4UILabelLayout.setColumns(4);
			ivjUIPanel4UILabelLayout.setRows(1);
			ivjUIPanel4UILabelLayout.setTop(30);
			ivjUIPanel4UILabelLayout.setVgap(10);
			ivjUIPanel4UILabelLayout.setLeft(10);
		} catch (java.lang.Throwable ex) {
			ExceptionHandler.consume(ex);

		}
		;
		return ivjUIPanel4UILabelLayout;
	}

	/**
	 * 重新生成Table。 创建日期：(2001-12-7 10:10:21)
	 * 
	 * @return nc.ui.pub.beans.UITable
	 */
	private UITable getUITable() {

		UITable table = new UITable() {
			private static final long serialVersionUID = -9180920389710214130L;

			@Override
			public void editingStopped(javax.swing.event.ChangeEvent e) {
				int row = editingRow;
				int col = editingColumn;
				javax.swing.table.TableCellEditor editor = getCellEditor();
				Object value = null;
				if (editor != null) {
					value = editor.getCellEditorValue();
					setValueAt(value, row, col);
					removeEditor();
					onAfterEdit(editor, row, col);
				}
			}
		};

		return table;
	}

	/**
	 * 加载bodypanel
	 * 
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	private nc.ui.pub.beans.UITablePane getBodyPanel() {
		if (ivjUITablePane == null) {
			try {
				ivjUITablePane = new nc.ui.pub.beans.UITablePane();
				ivjUITablePane.setName("UITablePane");
				ivjUITablePane.setTable(getUITable());
			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);
			}
		}
		return ivjUITablePane;
	}

	/**
	 * 初始化
	 * 
	 * @author jiaweib
	 * @since NC6.0
	 */
	private void initialize() {
		try {
			setName("AgiotageAccountPanel");
			setLayout(new java.awt.BorderLayout());
			setSize(609, 335);
			// 加载headpanel
			add(getHeadPanel(), "North");
			// 加载bodypanel
			add(getBodyPanel(), "Center");
			initTable();
			//初始化会计期间信息
			initPeriod(this.getFinanceOrgPanel().getRefPK());
		} catch (java.lang.Throwable ex) {
			ExceptionHandler.consume(ex);
			ui.showErrorMessage(ex.getMessage());
		}
	}
	/**
	 * 初始化会计期间信息
	 * @throws InvalidAccperiodExcetion 
	 */
	private void initPeriod(String pk_org) throws InvalidAccperiodExcetion{
		if(pk_org != null) {
				AccountCalendar calendar = AccountCalendar.getInstanceByPk_org(pk_org);
				calendar.setDate(AppContext.getInstance().getBusiDate());
				this.getCboYear().refresh(pk_org); 
				this.getCboYear().setSelectedYear(calendar.getYearVO().getPeriodyear());
				this.getCboPeriod().refreshByOrg(ui.getPk_org(),calendar.getYearVO().getPeriodyear()); 
				this.getCboPeriod().setSelectedPeriod(calendar.getMonthVO().getAccperiodmth());
			
		}
		
	}

	/**
	 * 主要功能：初始化界面table. 主要算法： 异常描述： 创建日期：(2001-8-7 13:56:52) 最后修改日期：(2001-8-7
	 * 13:56:52)
	 * @throws BusinessException 
	 * @throws ComponentException 
	 * 
	 * @author：wyan
	 */
	@SuppressWarnings("unchecked")
	public void initTable() throws ComponentException, BusinessException {

		CMPAgiotageVO vo = new CMPAgiotageVO();
		Vector vData = null;
			// 判断用户是否设置默认财物组织
			if (ui.getPk_org() != null) {
				// 组织ID
				String dwbm = ui.getPk_org();
				String sfbz = AccountAgiotageUI.getSysInfo().getSfbz();
				// 获取当前组织本币编码
				String bzbm = Currency.getOrgLocalCurrPK(ui.getPk_org());
				AccountCalendar ac = AccountCalendar.getInstanceByPk_org(ui.getPk_org());
				ac.setDate(AppContext.getInstance().getBusiDate());
				String QjBeg = ac.getMonthVO().getBegindate().toString();
				String QjEnd = ac.getMonthVO().getEnddate().toString();
				vo.setDwbm(dwbm);
				vo.setSfbz(sfbz);
				vo.setQjBeg(QjBeg);
				vo.setQjEnd(QjEnd);
				vo.setBzbm(bzbm);

				// 查询数据
				CMPRemoteTransferVO remoteData = CmpAgiotageProxy.getInstance().getIAccountAgiotagePrivate()
						.getInitDataAccount(vo);
				vData = remoteData.getTranData1(); // 取得显示数据
			}

			Vector vTitle = AccountAgiotageUI.converToVector(m_sTabTitle);
			nc.ui.pub.beans.table.NCTableModel model = new nc.ui.pub.beans.table.NCTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int col) {
					String name = getColumnName(col);
					String pk_currency = getValueAt(row, 0).toString();

					if (name.trim().equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																											 * @res
																											 * "银行账户"
																											 */)) {
						getBankAccount().getRefModel().addWherePart(" and pk_currtype='" + pk_currency + "' ");
						getBankAccount().getRefModel().clearData();
						boolean xzbz = ((Boolean) getValueAt(row, 5)).booleanValue();
						/* 选中选择标志可以编辑账户参照 */
						if (xzbz) {
							return true;
						} else {
							return false;
						}
					} else if (name.trim().equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																												 * @res
																												 * "现金账户"
																												 */)) {
						getCashAccount().getRefModel().addWherePart(
								" and pk_org='" + ui.getPk_org() + "' and pk_moneytype='" + pk_currency + "' ");
						getCashAccount().getRefModel().clearData();
						boolean xzbz = ((Boolean) getValueAt(row, 5)).booleanValue();
						/* 选中选择标志可以编辑账户参照 */
						if (xzbz) {
							return true;
						} else {
							return false;
						}
					}
					if (name.trim().equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004045")/*
																											 * @res
																											 * "选择标志"
																											 */)) {
						boolean xzbz = ((Boolean) getValueAt(row, 5)).booleanValue();
						if (xzbz) {
							String bzbm = getBodyPanel().getTable().getModel().getValueAt(row, 0).toString();
							m_pkAccids.remove(bzbm);
							m_pkManegeAccids.remove(bzbm);;
							
							
							setValueAt(null, row, 3);
							setValueAt(null, row, 4);
							getBankAccount().setPK(null);
							getCashAccount().setPK(null);
							getBankAccount().getRefModel().clearData();
							getCashAccount().getRefModel().clearData();
							
						}
						return true;
					} else {

						return false;
					}
				}
			};

			model.setDataVector(vData, vTitle);
			/* 账户和选择标志可编辑 */
			model.setColEditable(3, true);
			model.setColEditable(4, true);
			model.setColEditable(5, true);
			getBodyPanel().getTable().setModel(model);
			getBodyPanel().getTable().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			getBodyPanel().getTable().removeColumn(getBodyPanel().getTable().getColumnModel().getColumn(0));
			/* 把账户档案参照加入所在列 */

			if (ui.getPk_org() != null) {
				javax.swing.table.TableColumn account = getBodyPanel().getTable().getColumn(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																										 * @res
																										 * "银行账户"
																										 */);
				account.setCellEditor(new refEditor(getBankAccount()));

				javax.swing.table.TableColumn mngaccount = getBodyPanel().getTable().getColumn(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																											 * @res
																											 * "现金账户"
																											 */);
				mngaccount.setCellEditor(new refEditor(getCashAccount()));
			}

			/* 把账户档案参照加入所在列 */
			if (vData != null && vData.size() != 0)
				// setHasBzInfo(false);
				ui.updateButtonStatus("InputCond");
			else
				ui.updateButtonStatus("No Currency");

			/* 汇兑损益默认计算日期是当前登陆日期 */
			String curDate = AccountAgiotageUI.getSysInfo().getCurRq();
			getCalDate().setValue(curDate);
		
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-12-7 10:26:27)
	 * 
	 * @param editor
	 *            javax.swing.table.TableCellEditor
	 * @param row
	 *            int
	 * @param col
	 *            int
	 */
	private void onAfterEdit(javax.swing.table.TableCellEditor editor, int row, int col) {
		editor.stopCellEditing();
		if (editor instanceof UIRefCellEditor) {
			if (!getBodyPanel().getTable().getColumnName(col).equals(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																									 * @res
																									 * "银行账户"
																									 */)
					&& !getBodyPanel().getTable().getColumnName(col).equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																												 * @res
																												 * "现金账户"
																												 */)) {
				if (getBodyPanel().getTable().getCellEditor() != null)
					getBodyPanel().getTable().getCellEditor().stopCellEditing();
				return;
			}
			if (getBodyPanel().getTable().getColumnName(col).equals(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																									 * @res
																									 * "银行账户"
																									 */)) {
				UIRefCellEditor refEditor = (UIRefCellEditor) editor;
				UIRefPane refPane = (UIRefPane) refEditor.getComponent();
				/* 保存用户选中的账户数组 */
				String[] pk_accounts = refPane.getRefPKs();
				// added by zhufeng 2013-8-22 回车输入后,getrefpks为null start
				String inputText = refPane.getUITextField().getText();
				if (pk_accounts == null && StringUtil.isNotNull(inputText)){
					AbstractRefModel refModel = refPane.getRefModel();
					if (inputText.indexOf(",")>0) {
						refModel.matchData(refModel.getBlurFields(), inputText.split(","));
					} else {
						refModel.matchBlurData(inputText);
					}
					pk_accounts = refPane.getRefPKs();
					if (pk_accounts == null) {
						// 没有match上时,需要清除输入值,参照的autocheck为true,但是不明原因为什么不清除.
						refPane.getUITextField().setText(null);
						getBodyPanel().getTable().setValueAt(null, row, col);
					}
				}
				// added by zhufeng 2013-8-22  end
				/* 形成币种和账户Pk的对应 */
				String bzbm = getBodyPanel().getTable().getModel().getValueAt(row, 0).toString();
				if (pk_accounts != null)
					m_pkAccids.put(bzbm, pk_accounts);
				else
					m_pkAccids.remove(bzbm);

				/* Table中列和Model中列数差隐藏的Bzbm列 */
//				getBodyPanel().getTable().getModel().setValueAt(arrayToDelimitedString(refPane.getRefNames()), row, col + 1);
				refPane.getRefModel().setWherePart(null);
				refPane.getRefModel().setDisabledDataShow(true);

			} else if (getBodyPanel().getTable().getColumnName(col).equals(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																										 * @res
																										 * "现金账户"
																										 */)) {
				UIRefCellEditor refEditor = (UIRefCellEditor) editor;
				UIRefPane refPane = (UIRefPane) refEditor.getComponent();
				/* 保存用户选中的账户数组 */
				String[] pk_manageAccounts = refPane.getRefPKs();
				/* 形成币种和账户Pk的对应 */
				String bzbm = getBodyPanel().getTable().getModel().getValueAt(row, 0).toString();
				if (pk_manageAccounts != null)
					m_pkManegeAccids.put(bzbm, pk_manageAccounts);
				else
					m_pkManegeAccids.remove(bzbm);
				
				/* Table中列和Model中列数差隐藏的Bzbm列 */
				getBodyPanel().getTable().getModel().setValueAt(arrayToDelimitedString(refPane.getRefNames()), row, col + 1);
				refPane.getRefModel().setWherePart(null);
				refPane.getRefModel().setDisabledDataShow(true);
			} 
			
			
			
			
		}
		
		if (col == 5) {
			getBodyPanel().getTable().getModel().setValueAt(null, row, col + 1);
			this.getCashAccount().getRefModel().setSelectedData(null);
			this.getBankAccount().getRefModel().setSelectedData(null);
		}
	}

	/**
	 * 数组转换为字符串，以逗号分隔
	 * @param strs
	 * @return
	 */
	private String arrayToDelimitedString(String[] strs){
		
		if(strs == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<strs.length;i++){
			if(i!=strs.length-1){
				sb.append(strs[i]).append(",");
			}else{
				sb.append(strs[i]);
			}
		}
		return sb.toString();
	}
	
	private Hashtable<String, String[]> getBzbmTopk_AccountsHashTable() {
		Hashtable<String, String[]> bzbmTopk_AccountsHashTable = new Hashtable<String, String[]>();
		TableModel model = getBodyPanel().getTable().getModel();
		int rowCount = model.getRowCount();
		for (int row = 0; row < rowCount; row++) {
			String bzbm = model.getValueAt(row, 0).toString();
			Boolean isseleted = (Boolean) model.getValueAt(row, 5);
			if (isseleted) {
				if (m_pkAccids.get(bzbm) != null) {
					bzbmTopk_AccountsHashTable.put(bzbm, m_pkAccids.get(bzbm));
				}
			}
		}
		return bzbmTopk_AccountsHashTable;
	} 

	private Hashtable<String, String[]> getBzbmTopk_mngAccountsHashTable() {
		Hashtable<String, String[]> bzbmTopk_mngAccountsHashTable = new Hashtable<String, String[]>();
		TableModel model = getBodyPanel().getTable().getModel();
		int rowCount = model.getRowCount();
		for (int row = 0; row < rowCount; row++) {
			String bzbm = model.getValueAt(row, 0).toString();
			Boolean isseleted = (Boolean) model.getValueAt(row, 5);
			if (isseleted) {
				if (m_pkManegeAccids.get(bzbm) != null) {
					bzbmTopk_mngAccountsHashTable.put(bzbm, m_pkManegeAccids.get(bzbm));
				}
			}
		}
		return bzbmTopk_mngAccountsHashTable;
	}

	/**
	 * 计算账户汇兑损益。 创建日期：(2003-9-25 16:32:31)
	 */
	public java.util.ArrayList onCalculation() throws Exception {

		java.util.ArrayList voMsgs = new java.util.ArrayList();
		try {
			if (getBodyPanel().getTable().getCellEditor() != null)
				getBodyPanel().getTable().getCellEditor().stopCellEditing();
			String dwbm = ui.getPk_org();
			String sfbz = AccountAgiotageUI.getSysInfo().getSfbz();
			String user = AccountAgiotageUI.getSysInfo().getCurUser();
			boolean isZFB = AccountAgiotageUI.getSysInfo().getHsMode();
			// nc.vo.pub.lang.UFDate date = new
			// nc.vo.pub.lang.UFDate(getCalDate().getText());
			nc.vo.pub.lang.UFDate date = new nc.vo.pub.lang.UFDate(AccountAgiotageUI.getSysInfo().getCurRq());

			String kjnd = (String) this.getCboYear().getSelectdItemValue(); /* 年 */
			String kjqj = (String) this.getCboPeriod().getSelectdItemValue(); /* 单据月 */
			
			AccountCalendar accCal = AccountCalendar.getInstanceByPk_org(dwbm);
//			accCal.set(kjnd, kjqj);
			UFDate begindate = accCal.getMonthVO().getBegindate();
			UFDate enddate = accCal.getMonthVO().getEnddate();
			
			String startDate=kjnd+"-"+kjqj;
			
			AccountCalendar acc = null;
			try {
				acc = AccountCalendar.getInstanceByPk_org(dwbm);
			} catch (Exception e) {
				ExceptionHandler.consume(e);
			}

			if (acc != null) {
				AccperiodVO[] yearVos = acc.getYearVOsOfCurrentScheme();

				String pk_accperiod = "";

				for (AccperiodVO yearVo : yearVos) {
					if (startDate.startsWith(yearVo.getPeriodyear())) {
						pk_accperiod = yearVo.getPk_accperiod();
						break;
					}
				}

				AccperiodmonthVO[] monthVos = AccperiodmonthAccessor.getInstance().queryAllAccperiodmonthVOs(
						pk_accperiod);

				for (int i = 0; i < monthVos.length; i++) {
					AccperiodmonthVO monthVo = monthVos[i];

					if (monthVo.getYearmth().startsWith(startDate)) {
						begindate=monthVo.getBegindate();
						enddate =monthVo.getEnddate();
						//修改日期为东8区日期
//						startDate = monthVo.getBegindate().toLocalString();
						startDate = monthVo.getBegindate().toStdString();
						break;
					}
				}
			}
		
			// 检查是否已结账
			NCLocator.getInstance().lookup(ISettleAccountService.class).checkSettleAccount(ui.getPk_org(),
					startDate);

			CMPAgiotageVO voCond = getInputCond();
			voCond.setDwbm(dwbm);
			voCond.setSfbz(sfbz);
			voCond.setPkAccids(getBzbmTopk_AccountsHashTable()); /* 币种对应银行账户 */
			voCond.setPkManageAccids(getBzbmTopk_mngAccountsHashTable());
			voCond.setCalDate(date); /* 计算日期 */
			voCond.setCalQj(kjqj);
			voCond.setCalNd(kjnd);
			voCond.setHsMode(isZFB);
			voCond.setUser(user);
			voCond.setDateBeg(begindate.toString());
			voCond.setDateEnd(enddate.toString());
			
//			if(voCond.getPkAccids().values().size()<1 && voCond.getPkManageAccids().values().size()<1){
//				throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
//						"3607agi_0", "03607agi-0024")/* @res "请选择账户" */);
//			}

			/* 取得币种的汇率同时检查汇率是否合法 */
			CMPAgiotageVO agiotageVO = onCheckHl(voCond);

			agiotageVO.setPk_group(WorkbenchEnvironment.getInstance().getGroupVO().getPk_group());

			// 校验：计算会计年度期间不能早于 每个币种的上次计算会计年度、会计期间
			validateKjNdQj(voCond);

			/* 计算损益检查 */
			voMsgs = CmpAgiotageProxy.getInstance().getIAccountAgiotagePrivate().onCalculateAccount(agiotageVO);
			/* 刷新界面显示 */
			onUpdateView();
			
			//清除选择的账户
			m_pkAccids.clear();
			m_pkManegeAccids.clear();
			getBankAccount().setPK(null);
			getCashAccount().setPK(null);
			getBankAccount().getRefModel().clearData();
			getCashAccount().getRefModel().clearData();
			
			int rowCount=getBodyPanel().getTable().getModel().getRowCount();
			
			for(int i=0;i<rowCount;i++) {
				getBodyPanel().getTable().getModel().setValueAt(null, i, 3);
				getBodyPanel().getTable().getModel().setValueAt(null, i, 4);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return voMsgs;
	}

	private void validateKjNdQj(CMPAgiotageVO voCond) throws BusinessException {
		String calNd = voCond.getCalNd();
		String calQj = voCond.getCalQj();
		Vector<CMPAgiotageBzVO> selBzbm = voCond.getSelBzbm();
		for (CMPAgiotageBzVO agiotageBzVO : selBzbm) {
			int result = 0;
			String lastCalNdQj = agiotageBzVO.getLastCalNdQj();
			if (lastCalNdQj != null && !lastCalNdQj.trim().equals("")) {
				result = compareNdQj(lastCalNdQj, calNd, calQj);
			}
			if (result > -1) {
				//添加判断：账户信息是否被汇兑损益单
				Hashtable m_pkAccids = voCond.getPkAccids();/* 币种对应的账户 */
				Hashtable m_pkManageAccids = voCond.getPkManageAccids();/* 币种对应的管理账户 */
				Vector<String> pks = new Vector<String>();
				if(null != m_pkAccids && m_pkAccids.size()>0){
					String[] pkacces =  (String[]) m_pkAccids.get(agiotageBzVO.getBzbm());
					if(pkacces != null){
						for(String pkacc : (String[]) m_pkAccids.get(agiotageBzVO.getBzbm())){
							pks.add(pkacc);
						}
					}
				}
				if(null != m_pkManageAccids && m_pkManageAccids.size()>0){
					String[] pk_cashes = (String[]) m_pkManageAccids.get(agiotageBzVO.getBzbm());
					if(pk_cashes != null){
					for(String pkcash : pk_cashes){
						pks.add(pkcash);
					}
					}
				}
				AccountAgiotageAggVO[] accAgiVOs = CmpAgiotageProxy.getInstance().getIAccountAgiotageQueryService().queryAccAgiVOSByAccPKs(pks.toArray(new String []{}));
				if(null == accAgiVOs)
					continue;
				for(String pkacc : pks){
					UFDate temDate = null;
					int countRedBack = 0; //回冲的账户汇兑损益单
					int countOtherBill = 0; //其它账户汇兑损益单
					
					for(AccountAgiotageAggVO aggvo : accAgiVOs){
						AccountAgiotageVO vo = aggvo.getParentVO();
						if(pkacc.equals(vo.getPk_cashaccount()) || pkacc.equals(vo.getPk_bankaccount())){
							if(null == temDate || temDate.before(vo.getDbilldate())){
								temDate = vo.getDbilldate();
							}
						}
					}
					for(AccountAgiotageAggVO aggvo : accAgiVOs) {
						AccountAgiotageVO vo = aggvo.getParentVO();
						if(compareNdQj(String.valueOf(temDate.getYear()) + "-" + String.valueOf(temDate.getMonth()), 
								String.valueOf(vo.getDbilldate().getYear()), 
								String.valueOf(vo.getDbilldate().getMonth()))==0) {
							if(vo.getMemo().startsWith("3")) {
								countRedBack ++;
							} else
								countOtherBill ++;
						}
					}
					if(null != temDate && compareNdQj(calNd+"-"+calQj, String.valueOf(temDate.getYear()), temDate.getStrMonth())==-1) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0026")/*
								 * @res
								 * "选择计算的会计年度和会计期间早于上级计算期间，请先取消该币种的上次计算的汇兑损益！"
								 */);
					}
					if(null != temDate 
							&& /*修改为大于-1,即计算的期间大于或等于最后计算期间*/compareNdQj(calNd+"-"+calQj, String.valueOf(temDate.getYear()), temDate.getStrMonth()) < 1 
							&& countOtherBill > 0){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0026")/*
						 * @res
						 * "选择计算的会计年度和会计期间早于上级计算期间，请先取消该币种的上次计算的汇兑损益！"
						 */);
					}			
				}
			}
		}
	}

	/**
	 * @return 当前计算会计期间和年度小于上次计算年度  -1
	 *  当前计算会计期间和年度等于上次计算年度    0 
	 *  当前计算会计期间和年度大于上次计算年度    1
	 * */
	private int compareNdQj(String lastCalNdQj, String calNd, String calQj) {
		String[] split = lastCalNdQj.split("-");
		String lastCalNd = split[0];
		String lastCalQj = split[1];
		if (lastCalNd.equals(calNd) && lastCalQj.equals(calQj)) {
			return 0;
		}
		if (lastCalNd.compareTo(calNd) < 0) {
			return -1;
		}
		if (lastCalNd.equals(calNd) && lastCalQj.compareTo(calQj) < 0) {
			return -1;
		}
		return 1;
	}

	/**
	 * 处理选中币种相关信息。 创建日期：(2003-9-25 16:39:54)
	 * 
	 * @return nc.vo.arap.agiotage.AgiotageVO
	 * @param agiotageVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 * @throws BusinessException
	 */
	private CMPAgiotageVO onCheckHl(CMPAgiotageVO agiotageVO) throws BusinessException {

		try {
			String dwbm = agiotageVO.getDwbm();
			boolean bState = true; /* 如果汇率非法和没有设置应有的汇率置为false */
			Vector<CMPAgiotageBzVO> vBzData = agiotageVO.getSelBzbm();
			agiotageVO.setLocal(Currency.getLocalCurrPK(dwbm));

			for (int i = 0; i < vBzData.size(); i++) {
				CMPAgiotageBzVO bzvo = vBzData.elementAt(i);
				String bzbm = bzvo.getBzbm(); /* 币种编码 */
				String bzmc = bzvo.getBzmc(); /* 币种名称 */

				String LocalPk = Currency.getLocalCurrPK(dwbm);

				String currErrMsg = null; /* 币种汇率非法提示信息 */
				UFDouble[] hl = new UFDouble[] { null, null };
				try {
					String pk_accperiodscheme=null;
					AccountCalendar acc = null;
					try {
						acc = AccountCalendar.getInstanceByPk_org(dwbm);
						pk_accperiodscheme=acc.getLastMonthOfCurrentScheme().getPk_accperiodscheme();
					} catch (Exception e) {
						ExceptionHandler.consume(e);
					}
					
					// 根据源币种，目的币种，指定会计期间方案和年月查询调整汇率
					hl = Currency.getAdjustRateBoth(ui.getPk_org(), bzbm, pk_accperiodscheme, agiotageVO.getCalNd(),
							agiotageVO.getCalQj());
				} catch (NullPointerException e) {
					currErrMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0027")/*
																													 * @res
																													 * "取不到汇率，不能进行帐户汇兑损益"
																													 */;
				}

				if ((hl[1] == null) || (hl[1].equals(new UFDouble(0)))) {
					bState = false;
					currErrMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0028",null,new String[]{bzmc})/*
																												 * @res
																												 * "{0}对本币期间汇率尚未设置，无法执行汇兑损益计算！"
																												 */;
					throw new BusinessException(currErrMsg);
				}
				bzvo.setFbhl(hl[0]);
				bzvo.setBbhl(hl[1]);
				bzvo.setYbDig(Currency.getCurrDigit(bzbm));
				bzvo.setBbDig(Currency.getCurrDigit(LocalPk));
				bzvo.setState(bState);

				// 汇率折算信息
				CurrinfoVO currinfoVO = Currency.getCurrRateInfo(dwbm, bzbm, Currency.getLocalCurrPK(dwbm));
				// 设置折算模式
				// 0-源币种×汇率＝目的币种;1-源币种÷汇率＝目的币种
				if (currinfoVO != null) {
					bzvo.setM_bBbbf(currinfoVO.getConvmode());
				}

				bzvo.setCurrErrMsg(currErrMsg);
				vBzData.setElementAt(bzvo, i);
			}
			agiotageVO.setSelBzbm(vBzData);

		} catch (Exception ex) {
			ExceptionHandler.handleException(ex);
		}
		return agiotageVO;
	}

	/**
	 * 计算汇兑损益后显示计算日期。 创建日期：(2003-11-12 11:35:21)
	 */
	private void onUpdateView() {

		int iRow = getBodyPanel().getTable().getRowCount();
		for (int i = 0; i < iRow; i++) {
			Object oBzbm = getBodyPanel().getTable().getModel().getValueAt(i, 0);
			String sBzbm = oBzbm == null ? null : oBzbm.toString().trim();
			if (sBzbm == null || sBzbm.length() == 0)
				continue;
			else {
				
				boolean updateFlag = false;
				Object oData = m_pkAccids.get(sBzbm);
				
				if(oData != null){
					updateFlag = true;
				}
				
				oData = m_pkManegeAccids.get(sBzbm);
				if(oData != null){
					updateFlag = true;
				}
				
				if (updateFlag) {
					String kjnd = (String) this.getCboYear().getSelectdItemValue(); /* 年 */
					String kjqj = (String) this.getCboPeriod().getSelectdItemValue(); /* 单据月 */
					String jskjqj = kjnd + "-" + kjqj;
					getBodyPanel().getTable().getModel().setValueAt(jskjqj, i, 2);
				}

			}
		}
	}
}