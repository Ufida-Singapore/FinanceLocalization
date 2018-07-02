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
																							 * "����"
																							 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0022")/*
																								 * @res
																								 * "�ϴμ����ڼ�"
																								 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																								 * @res
																								 * "�ֽ��˻�"
																								 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																							 * @res
																							 * "�����˻�"
																							 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004045") /*
																							 * @res
																							 * "ѡ���־"
																							 */};

	private Hashtable<String, String[]> m_pkAccids = new Hashtable<String, String[]>();/* ���ֶ�Ӧѡ�������˻�Pks */

	private Hashtable<String, String[]> m_pkManegeAccids = new Hashtable<String, String[]>();/* ���ֶ�Ӧѡ�й����˻�Pks */

//	private String accwherePart = null;

//	private String manegeAccwherePart = null;

	private YearComboBox ivjCboYear = null;

	private String pk_orgbook;
	private PeriodComboBox ivjCboPeriod = null;
	private UILabel financeOrgQueryLabel = null;
	// ҵ��Ԫ
	private UIRefPane financeOrgQueryPanel = null;

	public AccountAgiotagePanel(AccountAgiotageUI ui) {
		super();
		this.ui = ui;
		initialize();
	}

	/**
	 * AgiotegePanel ������ע�⡣
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 */
	public AccountAgiotagePanel(java.awt.LayoutManager p0) {
		super(p0);
	}

	/**
	 * AgiotegePanel ������ע�⡣
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
	 * AgiotegePanel ������ע�⡣
	 * 
	 * @param p0
	 *            boolean
	 */
	public AccountAgiotagePanel(boolean p0) {
		super(p0);
	}

	/**
	 * �����˺�+�ֽ��˻� �������ڣ�(2003-9-30 14:42:22)
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
				m_refAccount.setRefNodeName("ʹ��Ȩ����");
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
	 * �ֽ��˻�
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
	 * ���� CboYear ����ֵ��
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
	 * ���ص�ǰ�û��ڵ�ǰ���ŵ�Ĭ�ϲ�������˲�����
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
	 * ���� CalDate ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIRefPane
	 */
	public nc.ui.pub.beans.UIRefPane getCalDate() {
		if (ivjCalDate == null) {
			try {
				ivjCalDate = new nc.ui.pub.beans.UIRefPane();
				ivjCalDate.setName("CalDate");
				ivjCalDate.setRefNodeName("����");
			} catch (java.lang.Throwable ex) {
				ExceptionHandler.consume(ex);

			}
		}
		return ivjCalDate;
	}

	/**
	 * ����������
	 * 
	 * @author��wyan
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
					String lastCalNdQj = (String) table.getModel().getValueAt(i, 2);// �ϴμ���Ļ����ȡ�����ڼ�
					String manageAccount = (String) table.getModel().getValueAt(i, 3);
					String account = (String) table.getModel().getValueAt(i, 4);
					if ((null == account || account.length() == 0)
							&& (manageAccount == null || manageAccount.length() == 0))
						throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"3607agi_0", "03607agi-0024")/* @res "��ѡ���˻�" */);
					voBz.setBzbm(bzbm);
					voBz.setBzmc(bzmc);
					voBz.setLastCalNdQj(lastCalNdQj);
					vSelBzbms.addElement(voBz);
				}
			}
			if (vSelBzbms.size() == 0) {
				throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
						"03607agi-0025")/* @res "��ѡ��Ҫ����������ı��֣�" */);
			}
			agiotageVo.setSelBzbm(vSelBzbms);
		} catch (DataValidateException ex) {
			throw ex;
		}
		return agiotageVo;
	}

	/**
	 * ����HeadPanel
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
						"UC000-0002560")/* @res "�ڼ�" */);
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
																												 * "���"
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
						"03607agi-0003")/* @res "������֯" */);
				financeOrgQueryLabel.setForeground(java.awt.Color.black);
				financeOrgQueryLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				financeOrgQueryLabel.setILabelType(0/** JavaĬ��(�Զ���) */
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
				financeOrgQueryPanel.setRefNodeName("������֯");
				//��ʾͣ����֯��Ϣ
				financeOrgQueryPanel.setDisabledDataButtonShow(true);
				financeOrgQueryPanel.setPK(ui.getPk_org());// panel
				//��ȡ��Ȩ�޵Ĳ�����֯
				
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

							// ���������˻��ӻ����ֽ��˻����յ���֯
							getBankAccount().setPK(null);
							getCashAccount().setPK(null);
							getBankAccount().setPk_org(ui.getPk_org());
							getCashAccount().setPk_org(ui.getPk_org());

							//���û���ڼ�
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
	 * ���� CboPeriod ����ֵ��
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
	 * ���� UIPanel4UILabelLayout ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UILabelLayout
	 */
	private nc.ui.pub.beans.UILabelLayout getUIPanel4UILabelLayout() {
		nc.ui.pub.beans.UILabelLayout ivjUIPanel4UILabelLayout = null;
		try {
			/* �������� */
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
	 * ��������Table�� �������ڣ�(2001-12-7 10:10:21)
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
	 * ����bodypanel
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
	 * ��ʼ��
	 * 
	 * @author jiaweib
	 * @since NC6.0
	 */
	private void initialize() {
		try {
			setName("AgiotageAccountPanel");
			setLayout(new java.awt.BorderLayout());
			setSize(609, 335);
			// ����headpanel
			add(getHeadPanel(), "North");
			// ����bodypanel
			add(getBodyPanel(), "Center");
			initTable();
			//��ʼ������ڼ���Ϣ
			initPeriod(this.getFinanceOrgPanel().getRefPK());
		} catch (java.lang.Throwable ex) {
			ExceptionHandler.consume(ex);
			ui.showErrorMessage(ex.getMessage());
		}
	}
	/**
	 * ��ʼ������ڼ���Ϣ
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
	 * ��Ҫ���ܣ���ʼ������table. ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-7 13:56:52) ����޸����ڣ�(2001-8-7
	 * 13:56:52)
	 * @throws BusinessException 
	 * @throws ComponentException 
	 * 
	 * @author��wyan
	 */
	@SuppressWarnings("unchecked")
	public void initTable() throws ComponentException, BusinessException {

		CMPAgiotageVO vo = new CMPAgiotageVO();
		Vector vData = null;
			// �ж��û��Ƿ�����Ĭ�ϲ�����֯
			if (ui.getPk_org() != null) {
				// ��֯ID
				String dwbm = ui.getPk_org();
				String sfbz = AccountAgiotageUI.getSysInfo().getSfbz();
				// ��ȡ��ǰ��֯���ұ���
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

				// ��ѯ����
				CMPRemoteTransferVO remoteData = CmpAgiotageProxy.getInstance().getIAccountAgiotagePrivate()
						.getInitDataAccount(vo);
				vData = remoteData.getTranData1(); // ȡ����ʾ����
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
																											 * "�����˻�"
																											 */)) {
						getBankAccount().getRefModel().addWherePart(" and pk_currtype='" + pk_currency + "' ");
						getBankAccount().getRefModel().clearData();
						boolean xzbz = ((Boolean) getValueAt(row, 5)).booleanValue();
						/* ѡ��ѡ���־���Ա༭�˻����� */
						if (xzbz) {
							return true;
						} else {
							return false;
						}
					} else if (name.trim().equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																												 * @res
																												 * "�ֽ��˻�"
																												 */)) {
						getCashAccount().getRefModel().addWherePart(
								" and pk_org='" + ui.getPk_org() + "' and pk_moneytype='" + pk_currency + "' ");
						getCashAccount().getRefModel().clearData();
						boolean xzbz = ((Boolean) getValueAt(row, 5)).booleanValue();
						/* ѡ��ѡ���־���Ա༭�˻����� */
						if (xzbz) {
							return true;
						} else {
							return false;
						}
					}
					if (name.trim().equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004045")/*
																											 * @res
																											 * "ѡ���־"
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
			/* �˻���ѡ���־�ɱ༭ */
			model.setColEditable(3, true);
			model.setColEditable(4, true);
			model.setColEditable(5, true);
			getBodyPanel().getTable().setModel(model);
			getBodyPanel().getTable().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			getBodyPanel().getTable().removeColumn(getBodyPanel().getTable().getColumnModel().getColumn(0));
			/* ���˻��������ռ��������� */

			if (ui.getPk_org() != null) {
				javax.swing.table.TableColumn account = getBodyPanel().getTable().getColumn(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																										 * @res
																										 * "�����˻�"
																										 */);
				account.setCellEditor(new refEditor(getBankAccount()));

				javax.swing.table.TableColumn mngaccount = getBodyPanel().getTable().getColumn(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																											 * @res
																											 * "�ֽ��˻�"
																											 */);
				mngaccount.setCellEditor(new refEditor(getCashAccount()));
			}

			/* ���˻��������ռ��������� */
			if (vData != null && vData.size() != 0)
				// setHasBzInfo(false);
				ui.updateButtonStatus("InputCond");
			else
				ui.updateButtonStatus("No Currency");

			/* �������Ĭ�ϼ��������ǵ�ǰ��½���� */
			String curDate = AccountAgiotageUI.getSysInfo().getCurRq();
			getCalDate().setValue(curDate);
		
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-7 10:26:27)
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
																									 * "�����˻�"
																									 */)
					&& !getBodyPanel().getTable().getColumnName(col).equals(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																												 * @res
																												 * "�ֽ��˻�"
																												 */)) {
				if (getBodyPanel().getTable().getCellEditor() != null)
					getBodyPanel().getTable().getCellEditor().stopCellEditing();
				return;
			}
			if (getBodyPanel().getTable().getColumnName(col).equals(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																									 * @res
																									 * "�����˻�"
																									 */)) {
				UIRefCellEditor refEditor = (UIRefCellEditor) editor;
				UIRefPane refPane = (UIRefPane) refEditor.getComponent();
				/* �����û�ѡ�е��˻����� */
				String[] pk_accounts = refPane.getRefPKs();
				// added by zhufeng 2013-8-22 �س������,getrefpksΪnull start
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
						// û��match��ʱ,��Ҫ�������ֵ,���յ�autocheckΪtrue,���ǲ���ԭ��Ϊʲô�����.
						refPane.getUITextField().setText(null);
						getBodyPanel().getTable().setValueAt(null, row, col);
					}
				}
				// added by zhufeng 2013-8-22  end
				/* �γɱ��ֺ��˻�Pk�Ķ�Ӧ */
				String bzbm = getBodyPanel().getTable().getModel().getValueAt(row, 0).toString();
				if (pk_accounts != null)
					m_pkAccids.put(bzbm, pk_accounts);
				else
					m_pkAccids.remove(bzbm);

				/* Table���к�Model�����������ص�Bzbm�� */
//				getBodyPanel().getTable().getModel().setValueAt(arrayToDelimitedString(refPane.getRefNames()), row, col + 1);
				refPane.getRefModel().setWherePart(null);
				refPane.getRefModel().setDisabledDataShow(true);

			} else if (getBodyPanel().getTable().getColumnName(col).equals(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0023")/*
																										 * @res
																										 * "�ֽ��˻�"
																										 */)) {
				UIRefCellEditor refEditor = (UIRefCellEditor) editor;
				UIRefPane refPane = (UIRefPane) refEditor.getComponent();
				/* �����û�ѡ�е��˻����� */
				String[] pk_manageAccounts = refPane.getRefPKs();
				/* �γɱ��ֺ��˻�Pk�Ķ�Ӧ */
				String bzbm = getBodyPanel().getTable().getModel().getValueAt(row, 0).toString();
				if (pk_manageAccounts != null)
					m_pkManegeAccids.put(bzbm, pk_manageAccounts);
				else
					m_pkManegeAccids.remove(bzbm);
				
				/* Table���к�Model�����������ص�Bzbm�� */
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
	 * ����ת��Ϊ�ַ������Զ��ŷָ�
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
	 * �����˻�������档 �������ڣ�(2003-9-25 16:32:31)
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

			String kjnd = (String) this.getCboYear().getSelectdItemValue(); /* �� */
			String kjqj = (String) this.getCboPeriod().getSelectdItemValue(); /* ������ */
			
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
						//�޸�����Ϊ��8������
//						startDate = monthVo.getBegindate().toLocalString();
						startDate = monthVo.getBegindate().toStdString();
						break;
					}
				}
			}
		
			// ����Ƿ��ѽ���
			NCLocator.getInstance().lookup(ISettleAccountService.class).checkSettleAccount(ui.getPk_org(),
					startDate);

			CMPAgiotageVO voCond = getInputCond();
			voCond.setDwbm(dwbm);
			voCond.setSfbz(sfbz);
			voCond.setPkAccids(getBzbmTopk_AccountsHashTable()); /* ���ֶ�Ӧ�����˻� */
			voCond.setPkManageAccids(getBzbmTopk_mngAccountsHashTable());
			voCond.setCalDate(date); /* �������� */
			voCond.setCalQj(kjqj);
			voCond.setCalNd(kjnd);
			voCond.setHsMode(isZFB);
			voCond.setUser(user);
			voCond.setDateBeg(begindate.toString());
			voCond.setDateEnd(enddate.toString());
			
//			if(voCond.getPkAccids().values().size()<1 && voCond.getPkManageAccids().values().size()<1){
//				throw new DataValidateException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
//						"3607agi_0", "03607agi-0024")/* @res "��ѡ���˻�" */);
//			}

			/* ȡ�ñ��ֵĻ���ͬʱ�������Ƿ�Ϸ� */
			CMPAgiotageVO agiotageVO = onCheckHl(voCond);

			agiotageVO.setPk_group(WorkbenchEnvironment.getInstance().getGroupVO().getPk_group());

			// У�飺����������ڼ䲻������ ÿ�����ֵ��ϴμ�������ȡ�����ڼ�
			validateKjNdQj(voCond);

			/* ���������� */
			voMsgs = CmpAgiotageProxy.getInstance().getIAccountAgiotagePrivate().onCalculateAccount(agiotageVO);
			/* ˢ�½�����ʾ */
			onUpdateView();
			
			//���ѡ����˻�
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
				//����жϣ��˻���Ϣ�Ƿ񱻻�����浥
				Hashtable m_pkAccids = voCond.getPkAccids();/* ���ֶ�Ӧ���˻� */
				Hashtable m_pkManageAccids = voCond.getPkManageAccids();/* ���ֶ�Ӧ�Ĺ����˻� */
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
					int countRedBack = 0; //�س���˻�������浥
					int countOtherBill = 0; //�����˻�������浥
					
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
								 * "ѡ�����Ļ����Ⱥͻ���ڼ������ϼ������ڼ䣬����ȡ���ñ��ֵ��ϴμ���Ļ�����棡"
								 */);
					}
					if(null != temDate 
							&& /*�޸�Ϊ����-1,��������ڼ���ڻ�����������ڼ�*/compareNdQj(calNd+"-"+calQj, String.valueOf(temDate.getYear()), temDate.getStrMonth()) < 1 
							&& countOtherBill > 0){
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0026")/*
						 * @res
						 * "ѡ�����Ļ����Ⱥͻ���ڼ������ϼ������ڼ䣬����ȡ���ñ��ֵ��ϴμ���Ļ�����棡"
						 */);
					}			
				}
			}
		}
	}

	/**
	 * @return ��ǰ�������ڼ�����С���ϴμ������  -1
	 *  ��ǰ�������ڼ����ȵ����ϴμ������    0 
	 *  ��ǰ�������ڼ����ȴ����ϴμ������    1
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
	 * ����ѡ�б��������Ϣ�� �������ڣ�(2003-9-25 16:39:54)
	 * 
	 * @return nc.vo.arap.agiotage.AgiotageVO
	 * @param agiotageVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 * @throws BusinessException
	 */
	private CMPAgiotageVO onCheckHl(CMPAgiotageVO agiotageVO) throws BusinessException {

		try {
			String dwbm = agiotageVO.getDwbm();
			boolean bState = true; /* ������ʷǷ���û������Ӧ�еĻ�����Ϊfalse */
			Vector<CMPAgiotageBzVO> vBzData = agiotageVO.getSelBzbm();
			agiotageVO.setLocal(Currency.getLocalCurrPK(dwbm));

			for (int i = 0; i < vBzData.size(); i++) {
				CMPAgiotageBzVO bzvo = vBzData.elementAt(i);
				String bzbm = bzvo.getBzbm(); /* ���ֱ��� */
				String bzmc = bzvo.getBzmc(); /* �������� */

				String LocalPk = Currency.getLocalCurrPK(dwbm);

				String currErrMsg = null; /* ���ֻ��ʷǷ���ʾ��Ϣ */
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
					
					// ����Դ���֣�Ŀ�ı��֣�ָ������ڼ䷽�������²�ѯ��������
					hl = Currency.getAdjustRateBoth(ui.getPk_org(), bzbm, pk_accperiodscheme, agiotageVO.getCalNd(),
							agiotageVO.getCalQj());
				} catch (NullPointerException e) {
					currErrMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0027")/*
																													 * @res
																													 * "ȡ�������ʣ����ܽ����ʻ��������"
																													 */;
				}

				if ((hl[1] == null) || (hl[1].equals(new UFDouble(0)))) {
					bState = false;
					currErrMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0028",null,new String[]{bzmc})/*
																												 * @res
																												 * "{0}�Ա����ڼ������δ���ã��޷�ִ�л��������㣡"
																												 */;
					throw new BusinessException(currErrMsg);
				}
				bzvo.setFbhl(hl[0]);
				bzvo.setBbhl(hl[1]);
				bzvo.setYbDig(Currency.getCurrDigit(bzbm));
				bzvo.setBbDig(Currency.getCurrDigit(LocalPk));
				bzvo.setState(bState);

				// ����������Ϣ
				CurrinfoVO currinfoVO = Currency.getCurrRateInfo(dwbm, bzbm, Currency.getLocalCurrPK(dwbm));
				// ��������ģʽ
				// 0-Դ���֡����ʣ�Ŀ�ı���;1-Դ���֡»��ʣ�Ŀ�ı���
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
	 * �������������ʾ�������ڡ� �������ڣ�(2003-11-12 11:35:21)
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
					String kjnd = (String) this.getCboYear().getSelectdItemValue(); /* �� */
					String kjqj = (String) this.getCboPeriod().getSelectdItemValue(); /* ������ */
					String jskjqj = kjnd + "-" + kjqj;
					getBodyPanel().getTable().getModel().setValueAt(jskjqj, i, 2);
				}

			}
		}
	}
}