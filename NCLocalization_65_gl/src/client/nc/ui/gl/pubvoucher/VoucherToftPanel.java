package nc.ui.gl.pubvoucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import nc.bs.logging.Logger;
import nc.ui.gl.common.NCHoteKeyRegistCenter;
import nc.ui.gl.datacache.OrgVersionCache;
import nc.ui.gl.gateway.glworkbench.GlWorkBench;
import nc.ui.gl.uicfg.ValueChangedAdaptor;
import nc.ui.gl.uicfg.voucher.VoucherCell;
import nc.ui.gl.uicfg.voucher.VoucherTablePane;
import nc.ui.gl.voucher.reg.VoucherFunctionRegister;
import nc.ui.gl.vouchercard.IVoucherView;
import nc.ui.gl.vouchercard.VoucherButtonLoader;
import nc.ui.gl.vouchercard.VoucherModel;
import nc.ui.gl.vouchercard.VoucherPanel;
import nc.ui.gl.vouchercard.VoucherTabbedPane;
import nc.ui.gl.vouchercard.VoucherUI;
import nc.ui.gl.voucherdata.VoucherDataBridge;
import nc.ui.gl.vouchertools.VoucherDataCenter;
import nc.ui.glcom.period.GlPeriodForClient;
import nc.ui.glpub.IParent;
import nc.ui.glpub.IUiPanel;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.SeparatorButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.sm.clientsetup.ClientSetup;
import nc.ui.sm.clientsetup.ClientSetupCache;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.pub.GlBusinessException;
import nc.vo.gl.pubvoucher.VoucherKey;
import nc.vo.gl.pubvoucher.VoucherModflagTool;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.uicfg.ButtonRegistVO;
import nc.vo.gl.uicfg.SeparatorButtonRegistVO;
import nc.vo.gl.uicfg.UIConfigVO;
import nc.vo.gl.uicfg.voucher.VoucherConfigVO;
import nc.vo.gl.voucher.VoucherDataPreLoadVO;
import nc.vo.glcom.glperiod.GlPeriodVO;
import nc.vo.glcom.nodecode.GlNodeConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

/**
 * �˴���������˵���� �������ڣ�(2003-1-16 15:50:23)
 *
 * @author��������
 */
public class VoucherToftPanel extends ToftPanel implements ValueChangeListener, IUiPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 4075910443879820191L;

	private VoucherPanel m_voucherpanel = null;

	private VoucherTabbedPane tabVoucher = null;

	private IParent m_parent; // ����ǰһ������ľ��

	// private ButtonObject[] m_buttons;
	// private ListModel model = new ListModel();

	protected boolean isUILoaded = false;

	private String strFunctionName;

	private HashMap button_function_cache = new HashMap();

	private HashMap function_button_cache = new HashMap();

	UIConfigVO uiconfigvo = null;

	/**
	 * VoucherView ������ע�⡣
	 */
	public VoucherToftPanel() {
		super();
		initialize();
	}

	/***************************************************************************
	 * ����: ���UiManager����ǰ��Ĺ���ģ��Ҫ������ģ�� ��ĳЩ�¼���������ͨ���÷�����ӡ�
	 *
	 * ����: Object objListener ������ Object objUserdata ��ʶǰ���Ǻ��ּ�����
	 *
	 * ����ֵ: ��
	 *
	 * ע�� �÷�����ʵû�й̶���Ҫ��ֻҪ�����ߺͱ����� ��֮����ڸõ��õ����Э�飬���Ϳ�ʹ�øù���
	 **************************************************************************/
	public void addListener(java.lang.Object objListener, java.lang.Object objUserdata) {
		getVoucherPanel().addListener(objUserdata, objListener);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-9-25 17:38:03)
	 */
	public void close() {
		if (m_parent != null)
			m_parent.closeMe();
		else
			this.getFrame().getApplet().destroy();
	}

	public void doFunction(String functioncode) {
		getVoucherPanel().doOperation(functioncode);
	}

	public String getFunctionName() {
		if (strFunctionName == null) {
			try {
				strFunctionName = this.getParameter("functionname");
			} catch (Exception e) {
				strFunctionName = "voucherbridge";
			}
			if (strFunctionName == null)
				strFunctionName = "voucherbridge";
		}
		return strFunctionName;
	}

	/**
	 * ����ʵ�ָ÷���������ҵ�����ı��⡣
	 *
	 * @version (00-6-6 13:33:25)
	 *
	 * @return java.lang.String
	 */
	public String getTitle() {
		return null;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-8-19 16:30:31)
	 *
	 * @return nc.ui.gl.uicfg.AbstractBasicPane
	 */
	public VoucherPanel getVoucherPanel() {
		return getTabVoucher().getFirstVoucherPanel();
	}

	public VoucherPanel getCurrentVoucherPanel() {
		return getTabVoucher().getCurrentVoucherPanel();
	}

	private void initButtons() {
		// ��¼����
		// m_bnDetail.addChildButton(m_bnAdd);
		// m_bnDetail.addChildButton(m_bnDelete);
		// m_bnDetail.addChildButton(m_bnCopy);
		// m_bnDetail.addChildButton(m_bnPaste);
		// m_bnDetail.addChildButton(m_bnQueryBalance);
		// m_bnDetail.addChildButton(m_bnVerify);
		// m_bnDetail.addChildButton(m_bnRTVerify);

		// //ƾ֤����
		// m_bnVoucher.addChildButton(m_bnClearerror);
		// m_bnVoucher.addChildButton(m_bnAbandon);
		// m_bnVoucher.addChildButton(m_bnDel);
		// m_bnVoucher.addChildButton(m_bnCopyVoucher);
		// m_bnVoucher.addChildButton(m_bnTempSave);
		// m_bnVoucher.addChildButton(m_bnImage);
		// //����ƾ֤����
		// m_bnCommon.addChildButton(m_bnSetCommon);
		// m_bnCommon.addChildButton(m_bnLoadCommon);

		// ����ע��
		button_function_cache.clear();
		function_button_cache.clear();

		// installButtonFunction(m_bnCash,
		// VoucherFunctionRegister.FUNCTION_CASHFLOW);
		// installButtonFunction(m_bnImage,
		// VoucherFunctionRegister.FUNCTION_VOUCHERIMAGE);
		// installButtonFunction(m_bnQueryBalance,
		// VoucherFunctionRegister.FUNCTION_QUERYBALANCE);
		// installButtonFunction(m_bnVerify,
		// VoucherFunctionRegister.FUNCTION_VERIFYNO);
		// installButtonFunction(m_bnRTVerify,
		// VoucherFunctionRegister.FUNCTION_REALTIMEVERIFY);
		// installButtonFunction(m_bnBalance,
		// VoucherFunctionRegister.FUNCTION_CHECKSTYLE);
	}

	/**
	 * ��ʼ���ࡣ
	 */
	/* ���棺�˷������������ɡ� */
	private void initialize() {
		setLayout(new java.awt.BorderLayout());
		this.add(getTabVoucher(), "Center");
		setVoucherPanel(new VoucherPanel());
		getVoucherPanel().addListener("ToftPanel", this);
		// getVoucherPanel().addListener("ValueChangeListener",this);
		// getVoucherPanel().setEditable(false);

		// hurh 2011-04-28 ���Ӽ���
		getVoucherPanel().getVoucherModel().addValueChangeListener(this);
	}

	public void installButtonFunction(ButtonObject button, String functioncode) {
		if (functioncode == null)
			return;
		getCurrentVoucherPanel().installOperation(functioncode);
		button_function_cache.put(button, functioncode);
		function_button_cache.put(functioncode, button);
	}
	
	/**
	 * hurh ���Ǵ˷�����posinit�ڽ��������ɺ��ٵ���
	 */
	@Override
	public void init() {
		setButtons(getButtons());
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-9-25 14:20:07)
	 *
	 * @param buttons
	 *            nc.vo.gl.uicfg.ButtonRegistVO[]
	 */
	public void installButtons(ButtonObject parentButton, ButtonRegistVO[] buttons) {
		if (buttons == null)
			return;
		ButtonObject[] bbs = new ButtonObject[buttons.length];
		for (int i = 0; i < buttons.length; i++) {
			if(buttons[i] instanceof SeparatorButtonRegistVO){
				bbs[i] = new SeparatorButtonObject();
			}else{
				bbs[i] = new ButtonObject(buttons[i].getButtonName(), buttons[i].getButtonName(), 2);
			}
			installButtonFunction(bbs[i], buttons[i].getButtonFunction());
			bbs[i].setHint(buttons[i].getButtonTag());
			bbs[i].setCode(buttons[i].getButtonFuncCode() == null ? buttons[i].getButtonName() : buttons[i].getButtonFuncCode());
			if (buttons[i].getChildButton() != null) {
				installButtons(bbs[i], buttons[i].getChildButton());
			}
		}
		if (parentButton == null){
			NCHoteKeyRegistCenter.buildAction(this, bbs);
			setButtons(bbs);
		}
		else
			parentButton.addChileButtons(bbs);
	}

	public void installFunction(String functioncode) {
		getVoucherPanel().installOperation(functioncode);
	}

	public void openVoucher(VoucherVO voucher, boolean canEdit) {
		if (voucher == null)
			return;
		// free4 -- �޿�ĿȨ��
		
		// hurh
		getVoucherPanel().addValueChangeListener(m_parent);
		getVoucherPanel().getVoucherUI().setParent(m_parent);
		
		if (!canEdit)
			getVoucherPanel().setEditable(false);
		getVoucherPanel().setM_modulecode(getModuleCode());
		getVoucherPanel().setVO(voucher);
		getTabVoucher().setVoucherPanel(getVoucherPanel());
		if (!canEdit)
			getVoucherPanel().setEditable(false);
		updateUI();
	}
	/***************************************************************************
	 * ����: ���UiManager����ǰ��Ĺ���ģ����Ҫ���ñ�ģ ���ĳ�����������ĳ�����ܣ�������ͨ���÷��� �ﵽ��һĿ��
	 *
	 * ����: Object objData ��Ҫ���ݵĲ�������Ϣ Object objUserData ��Ҫ���ݵı�ʾ����Ϣ
	 *
	 * ����ֵ: Object
	 *
	 * ע�� �÷�����ʵû�й̶���Ҫ��ֻҪ�����ߺͱ����� ��֮����ڸõ��õ����Э�飬���Ϳ�ʹ�øù���
	 **************************************************************************/
	public java.lang.Object invoke(java.lang.Object objData, java.lang.Object objUserData) {
//		String userid = ClientEnvironment.getInstance().getUser().getPrimaryKey();
		String userid = GlWorkBench.getLoginUser();
		Boolean ispower = new Boolean(false);
		if (((String) objUserData).equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000037")/*
																														 * @res
																														 * "�޸�"
																														 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000055")/*@res "�����޸�.."*/);
			getTabVoucher().setVoucherPanel(getVoucherPanel());
			getVoucherPanel().stopEditing();
			VoucherVO vo =null;

			try {
				String strVoucherPK = (String) objData;
				vo= VoucherDataBridge.getInstance().queryByPk(strVoucherPK);
				//getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000047")/*@res "��ʾ"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000056")/*@res "ƾ֤����Щ��Ŀû��Ȩ��,�����޸Ĵ�ƾ֤."*/);
				}
				if (vo.getPk_vouchertype() != null)
					setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			} catch (ClassCastException e) {
				 vo = (VoucherVO) objData;
				// getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000047")/*@res "��ʾ"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000056")/*@res "ƾ֤����Щ��Ŀû��Ȩ��,�����޸Ĵ�ƾ֤."*/);

				}
				if (vo.getPk_vouchertype() != null)
					setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			} catch (GlBusinessException e) {
				throw e;
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new GlBusinessException(e.getMessage(), e);
			}
			try {
				getVoucherPanel().startEditing();
			} catch (GlBusinessException e) {
				Logger.error(e.getMessage(), e);
				throw new GlBusinessException(e.getMessage(), e);
			}

			// vo = (VoucherVO) objData;
			if (vo!=null&&ispower.booleanValue()) {
				vo = VoucherDataBridge.getInstance().filterDetailByAccsubjPower(vo, userid);
				getVoucherPanel().setVO(vo);
				getVoucherPanel().setEditable(false);
				updateUI();
				return null;
			}
			getVoucherPanel().setVO(vo);
			updateUI();
		} else if (((String) objUserData).equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000038")/*
																																 * @res
																																 * "���"
																																 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000057")/*@res "�������.."*/);
			getTabVoucher().setVoucherPanel(getVoucherPanel());
			getVoucherPanel().stopEditing();

			try {
				String strVoucherPK = (String) objData;
				VoucherVO vo = VoucherDataBridge.getInstance().queryByPk(strVoucherPK);
				getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					vo = VoucherDataBridge.getInstance().filterDetailByAccsubjPower(vo, userid);
					getVoucherPanel().setVO(vo);
				}
				if (vo.getPk_vouchertype() != null)
					setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			} catch (ClassCastException e) {
				VoucherVO vo = (VoucherVO) objData;
				getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					vo = VoucherDataBridge.getInstance().filterDetailByAccsubjPower(vo, userid);
					getVoucherPanel().setVO(vo);
				}
				if (vo.getPk_vouchertype() != null)
					setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			} catch (GlBusinessException e) {
				throw e;
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new GlBusinessException(e.getMessage(), e);
			}
			getVoucherPanel().setEditable(false);
			updateUI();
		} else if (((String) objUserData).equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000039")/*
																																 * @res
																																 * "����"
																																 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000058")/*@res "��������.."*/);

			getTabVoucher().setVoucherPanel(getVoucherPanel());
			getVoucherPanel().stopEditing();
			VoucherVO vo = new VoucherVO();
			//vo.setPk_accountingbook(GlWorkBench.getDefaultMainOrg());
			vo.setPk_accountingbook(getVoucherPanel().getPk_accountingbook());
			vo.setPk_org(AccountBookUtil.getPk_orgByAccountBookPk(vo.getPk_accountingbook()));

			vo.setPk_group(GlWorkBench.getDefaultGroup());
		//	vo.setPk_financeorg(this.getParameter(key));
		//	vo.setCorpname(this.getClientEnvironment().getCorporation().getName());
			vo.setPk_system(GlNodeConst.NCGL);
			vo.setPrepareddate(GlWorkBench.getBusiDate());
			vo.setCreationtime(new UFDateTime(GlWorkBench.getDefaultDate().getMillis()));
			nc.bs.logging.Logger.error("ҵ������" + GlWorkBench.getBusiDate().toString());
			nc.bs.logging.Logger.error("��ǰ����������" + GlWorkBench.getBusiDate().toString());
			vo.setPeriod(GlWorkBench.getLoginPeriod());
			// vo.setNo(Integer.valueOf(0));
			// ����ƾ֤ǰ��̨���ʽӿ�
			//vo.setNo(Integer.valueOf(1));
			
			// hurh V60ƾ֤����ʱ����Ĭ��ƾ֤���
			//vo.setNo(Integer.valueOf(0));
			vo.setNo(null);

			vo.setModifyflag(VoucherModflagTool.getVoucherHeadModFlag(VoucherModflagTool.DEFAULT_CTRL));
			vo.setAttachment(Integer.valueOf(0));
			vo.setTotalcredit(new nc.vo.pub.lang.UFDouble(0));
			vo.setTotaldebit(new nc.vo.pub.lang.UFDouble(0));
			vo.setTotalcreditglobal(UFDouble.ZERO_DBL);
			vo.setTotaldebitglobal(UFDouble.ZERO_DBL);
			vo.setTotalcreditgroup(UFDouble.ZERO_DBL);
			vo.setTotaldebitgroup(UFDouble.ZERO_DBL);
			vo.setYear(GlWorkBench.getLoginYear());
			vo.setPeriod(GlWorkBench.getLoginPeriod());

			if(vo.getPk_accountingbook() != null){
				String pk_org = AccountBookUtil.getPk_orgByAccountBookPk(vo.getPk_accountingbook());
				vo.setPk_org(pk_org);
				HashMap<String, String> versionMap = null;
				try {
					//versionMap = GlOrgUtils.getNewVIDSByOrgIDSAndDate(new String[]{pk_org}, vo.getPrepareddate());
					versionMap = OrgVersionCache.getInstance().getNewVIDSByOrgIDSAndDate(new String[]{pk_org}, vo.getPrepareddate().asEnd());
			
					GlPeriodVO pvo = new GlPeriodForClient().getPeriod(vo.getPk_accountingbook(), vo.getPrepareddate());
					if(pvo.getYear() != null){
						vo.setYear(pvo.getYear());
					}
					if(pvo.getMonth() != null){
						vo.setPeriod(pvo.getMonth());
					}
				} catch (BusinessException e) {
					Logger.error(e.getMessage(), e);
					throw new GlBusinessException(e.getMessage(), e);
				}
				if(versionMap != null){
					vo.setPk_org_v(versionMap.get(pk_org));
				}
			}

			// �Ƶ���
			vo.setPk_prepared(GlWorkBench.getLoginUser());
			// ����ʱ����������Ϊ�Ƶ��ˣ���Ҫ����������Ȩ��ʹ�ã�������ܳ�����������Ȩ��У�����
			vo.setCreator(vo.getPk_prepared());

			//vo.setPk_prepared(this.getClientEnvironment().getUser().getPrimaryKey());
			//vo.setPreparedname(this.getClientEnvironment().getUser().getUser_name());
			vo.setVoucherkind(Integer.valueOf(0));
			vo.setDiscardflag(nc.vo.pub.lang.UFBoolean.FALSE);
			vo.setDetailmodflag(nc.vo.pub.lang.UFBoolean.TRUE);
			vo.setVoucherkind(Integer.valueOf(0));
			java.util.Vector vecdetails = new java.util.Vector();
			vo.setDetail(vecdetails);
			
			// hurh ȡƾ֤��𣬷������ƾ֤������÷�¼Ĭ�ϱ���
			VoucherCell cell = (VoucherCell) ((VoucherModel)getVoucherPanel().getVoucherModel()).getView().getVoucherCellEditor(-1, Integer.valueOf(VoucherKey.V_PK_VOUCHERTYPE));
			vo.setPk_vouchertype(((UIRefPane) cell.getEditor()).getRefPK());

			getVoucherPanel().getVoucherUI().setEditable(true);
			getVoucherPanel().setVO(vo);
			getVoucherPanel().startEditing();
			// getVoucherPanel().setVO(vo);
			getVoucherPanel().getVoucherModel().setSaved(true);
			updateUI();
			// hurh v60���Ч�ʣ�ȥ����־����
			// new VoucherOperatLogTool().InsertLogByVoucher((VoucherVO)getVoucherPanel().getVoucherModel().getParameter("vouchervo"), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002gl55","UC001-0000002")/*@res "����"*/, GlNodeConst.GLNODE_VOUCHERPREPARE);
			// showHintMessage("���ӽ���");

		} else if (((String) objUserData).equals("addMessageListener")) {
			// this.removeMessageListener((MessageListener) objData);
			// this.addMessageListener((MessageListener) objData);
		} else if(objUserData.toString().equals("setvouchers")){
			VoucherVO voucher = ((VoucherVO[])objData)[0];
			getVoucherPanel().setVO(voucher);
			updateUI();
		} else {
			try {
				if (objUserData.toString().equals("setVoucherPk")) {
					getVoucherPanel().setEditable(false);
					// VoucherVO[] vouchers =
					// GLPubProxy.getRemoteVoucher().queryByPks((String[])
					// objData);
					// addVouchers(vouchers);
					// setCurrentIndex(new int[] { 0 }, true);
				} else {
					// m_bnAdd.setVisible(true);
					// m_bnDelete.setVisible(true);
					// m_bnCopy.setVisible(true);
					// m_bnPaste.setVisible(true);
					// m_bnSave.setVisible(true);
					// m_bnClearerror.setVisible(true);
				}
				// addListener(model, "IPeerListener");
				// addListener(model, "addPropertyChangeListener");
				// model.invoke(objData, objUserData);
			} catch (Exception e) {
				//nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this,null,e.getMessage());
				Logger.error(e.getMessage(), e);
				throw new GlBusinessException(e.getMessage(), e);
			}
		}
		return null;
	}

	/***************************************************************************
	 * ����: ��A����ģ�����B����ģ������B����ģ��ر�ʱ A����ģ��ͨ���÷����õ�֪ͨ
	 *
	 * ����: �� ����ֵ: ��
	 **************************************************************************/
	public void nextClosed() {
	}

	/**
	 * ����ʵ�ָ÷�������Ӧ��ť�¼���
	 *
	 * @version (00-6-1 10:32:59)
	 *
	 * @param bo
	 *            ButtonObject
	 */
	public void onButtonClicked(nc.ui.pub.ButtonObject bo) {
		ShowStatusBarMsgUtil.showStatusBarMsg(" ",this);
		try {
			String func = (String) button_function_cache.get(bo);
			if (func != null){
				//���Ϊ���ز���ʱ,����Ƭ̬�µ�ƾ֤�п�����������  add by weiningc 20171129 
				if("EXIT".equals(func)) {
					saveVoucherCardColumsCache();
				}
				
				getCurrentVoucherPanel().doOperation(func);
			}
		} catch (GlBusinessException e) {
			// showHintMessage("����ʧ��");
			Logger.error(e.getMessage(), e);
			nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, null, e.getMessage());
		} catch (Exception e) {
			nc.bs.logging.Logger.error(e.getMessage(), e);
			nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, null, e.getMessage());
		}
	}
	
	public void saveVoucherCardColumsCache() {
		try {
			IVoucherView voucherUi = getVoucherPanel().getVoucherUI();
			if(voucherUi != null && voucherUi instanceof VoucherUI) {
				VoucherUI voucherCardUi = (VoucherUI) voucherUi;
				VoucherTablePane voucherTablePane = voucherCardUi.getVoucherTablePane();
				TableColumnModel columnModel = voucherTablePane.getUITablePane().getTable().getColumnModel();
				int columnCount = columnModel.getColumnCount();
				
				ClientSetup currentClientSetup = ClientSetupCache.getCurrentClientSetup();
				for(int i=0;i<columnCount;i++) {
					TableColumn column = columnModel.getColumn(i);
					Object identifier = column.getIdentifier();
					int width = column.getWidth();
					currentClientSetup.put(VoucherTablePane.class.getName()+identifier, Integer.valueOf(width));
				}
				
				int rowHeight = voucherTablePane.getUITablePane().getTable().getRowHeight();
				currentClientSetup.put(VoucherTablePane.class.getName()+"getRowHeight", Integer.valueOf(rowHeight));
			}
		}catch(Exception e) {
			Logger.error(e);
		}
	}

	public boolean onClosing() {
		saveVoucherCardColumsCache();
		try {
			if (getFunctionName().equals("preparevoucher") || getFunctionName().equals("voucherbridge") || getFunctionName().equals("offsetvoucher") || getFunctionName().equals("checkvoucher")) {
				Object result = getVoucherPanel().doOperation(VoucherFunctionRegister.FUNCTION_CONFIRMSAVE);
				if (result != null && result.toString().equals("cancel")) {
					return false;
				}
			}
			
//			//�ر�Ӱ��鿴����
//			IMasterModel model = getVoucherPanel().getVoucherModel();
//			if(model != null && model instanceof VoucherModel) {
//				VoucherModel voucherModel = (VoucherModel) model;
//				ViewImageOperationModel linkImage = (ViewImageOperationModel) voucherModel.getOperationmodels().get(VoucherFunctionRegister.FUNCTION_IMAGEVIEW);
//				if(linkImage != null){
//					linkImage.closeDlg();
//				}
//			}
			

			// if (getVoucher1().isVoucherEditing())
			// if (showYesNoMessage("��ǰ�����ƾ֤�ѱ��޸Ĳ�����δ���棬Ҫ������") == 4)
			// if (getFunctionName().equals("preparevoucher") ||
			// getFunctionName().equals("offsetvoucher") ||
			// getFunctionName().equals("voucherbridge"))
			// getVoucher1().doVoucherSave();
			// else if (getFunctionName().equals("checkvoucher"))
			// getVoucher1().doVoucherSaveError();
			
			// hurh GLUIManagerͳһ����
//			nc.ui.gl.vouchertools.VoucherDataCenter.getInstance().clearAll();
//			ValueChangedAdaptor.clearListener(m_parent);
		} catch (GlBusinessException e) {
			nc.bs.logging.Logger.error(e.getMessage(), e);
			nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this,null,e.getMessage());
			return false;
		} catch (Exception e) {
			nc.bs.logging.Logger.error(e.getMessage(), e);
			nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this,null,e.getMessage());
			return false;
		} finally {
			nc.ui.gl.vouchertools.VoucherDataCenter.getInstance().clearAll();
			ValueChangedAdaptor.clearListener(m_parent);
		}
		return true;
	}

	protected void postInit() {
		if (isUILoaded){
			try {
//				UIConfigVO v = VoucherConfigVO.getSysDefaultVO(getModuleCode());
//				ButtonRegistVO[] buttons = v.getButtons();
//				if (buttons != null)
//					installButtons(null, buttons);

				// hurh
				UIConfigVO v = GLUIConfigVOTool.loadConfigVO(isUILoaded, getVoucherPanel().getPk_accountingbook(), getModuleCode());
				ButtonRegistVO[] buttons = v.getButtons();
				if (buttons != null)
					installButtons(null, buttons);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				//eat exception, the voucher panel should be opened anyway...
			}
			return;
		}
		try {
			//UIConfigVO v = GLPubProxy.getRemoteVoucherConfig().load(VoucherDataCenter.getClientPk_orgbook(), ClientEnvironment.getInstance().getUser().getPrimaryKey(), getModuleCode(), this.getClass().getName(), null, null);
			//UIConfigVO v = VoucherConfigVO.getSysDefaultVO(GlNodeConst.GLNODE_VOUCHERPREPARE,GlWorkBench.getDefaultGroup());

			// hurh
			UIConfigVO v = GLUIConfigVOTool.loadConfigVO(isUILoaded, getVoucherPanel().getPk_accountingbook(), getModuleCode());

			VoucherDataPreLoadVO addCard = new VoucherDataPreLoadVO();
			//addCard.setPk_glorgbook(VoucherDataCenter.getClientPk_orgbook());
			addCard.setPk_glorgbook(getVoucherPanel().getPk_accountingbook());
			addCard.setUserid(GlWorkBench.getLoginUser());
			addCard.setModulecode(getModuleCode());
			addCard.setDate(GlWorkBench.getBusiDate());

			// IVoucherLoad voucherload
			// =((IVoucherLoad)NCLocator.getInstance().lookup(IVoucherLoad.class.getName()));
			// UIConfigVO v =voucherload.addCardLoad(addCard).getUiconfig();
			// UIConfigVO v =
			// VoucherPreData.getInstance().getUIConfigVO(addCard);
			// ValueChangedAdaptor.getInstance().clearListeners();
			if (v == null) {
				nc.bs.logging.Logger.debug("ȡ���ݳ���");
				throw new GlBusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000059")/*@res "ȡ���ݳ���"*/);
			} else {
				nc.bs.logging.Logger.debug("UIConfigVO=" + v);
			}
			getVoucherPanel().setUIConfigVO(v);
			ButtonRegistVO[] buttons = v.getButtons();
			if (buttons != null)
				installButtons(null, buttons);
			uiconfigvo = v;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000040")/*
																														 * @res
																														 * "��ȡƾ֤��ʽʱ�����쳣����"
																														 */);
		}
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_ADDEMPTYDETAIL);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_CONFIRMSAVE);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_CLEAROTHERVOUCHERTAB);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_REMOVETAB_BY_VOUCHERPK);
		// �ػ��߳�//2.3Ϊ���ȶ�����ʱ�����
		// getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_StartDaemonThread);
		// getVoucherPanel().doOperation(VoucherFunctionRegister.FUNCTION_StartDaemonThread);
		prepareButtons();
		isUILoaded = true;
	}

	public void prepareButtons() {
		if (getButtons() == null) {
			ButtonObject[] t_buttons = VoucherButtonLoader.getButtons(getFunctionName());
			NCHoteKeyRegistCenter.buildAction(this, t_buttons);
			setButtons(t_buttons);
		}
	}

	/***************************************************************************
	 * ����: ȥ��ĳ��������
	 *
	 * ����: Object objListener ������ Object objUserdata ��ʶǰ���Ǻ��ּ�����
	 *
	 * ����ֵ: ��
	 *
	 * ע�� �÷�����ʵû�й̶���Ҫ��ֻҪ�����ߺͱ����� ��֮����ڸõ��õ����Э�飬���Ϳ�ʹ�øù���
	 **************************************************************************/
	public void removeListener(java.lang.Object objListener, java.lang.Object objUserdata) {
	}

	public void resetButtonState(VoucherVO vo) {
		/*
		 * if (m_buttons == null) return; for (int i = 0; i < m_buttons.length;
		 * i++) { m_buttons[i].setEnabled(true); if
		 * (m_buttons[i].getChildCount() > 0) { for (int j = 0; j <
		 * m_buttons[i].getChildCount(); j++) { ((ButtonObject)
		 * m_buttons[i].getChildren().elementAt(j)).setEnabled(true); } } }
		 * //">" " <" String station = getVoucher1().getListIndex(); if
		 * (station.equals("single")) { m_bnForward.setEnabled(false);
		 * m_bnNext.setEnabled(false); } else if (station.equals("first")) {
		 * m_bnForward.setEnabled(false); } else if (station.equals("last")) {
		 * m_bnNext.setEnabled(false); }
		 *
		 * GLParameterVO glparam =
		 * VoucherDataCenter.getInstance().getGlparameter(); String pk_user =
		 * getClientEnvironment().getUser().getPrimaryKey(); if
		 * (glparam.Parameter_isselfedit.booleanValue() &&
		 * !pk_user.equals(vo.getPk_prepared())) { m_bnAdd.setEnabled(false);
		 * m_bnDelete.setEnabled(false); m_bnCopy.setEnabled(false);
		 * //m_bnCopyVoucher.setEnabled(false); m_bnPaste.setEnabled(false);
		 * m_bnAbandon.setEnabled(false); m_bnDel.setEnabled(false);
		 * m_bnClearerror.setEnabled(false); m_bnTempSave.setEnabled(false); if
		 * (getFunctionName().equals("preparevoucher")) {
		 * m_bnSave.setEnabled(false); } } //edit if (vo.getErrmessage() ==
		 * null) m_bnClearerror.setEnabled(false); else {
		 * m_bnAbandon.setEnabled(false); m_bnCheck.setEnabled(false);
		 * m_bnTally.setEnabled(false); m_bnOffset.setEnabled(false); } if
		 * (vo.getDetailmodflag() != null &&
		 * !vo.getDetailmodflag().booleanValue()) { m_bnAdd.setEnabled(false);
		 * m_bnDelete.setEnabled(false); m_bnCopy.setEnabled(false);
		 * m_bnPaste.setEnabled(false); } if (vo.getIsmatched() != null &&
		 * vo.getIsmatched().booleanValue()) { m_bnAbandon.setEnabled(false);
		 * m_bnDel.setEnabled(false); } if (vo.getDiscardflag() != null &&
		 * vo.getDiscardflag().booleanValue()) { m_bnAbandon.setName("ȡ������"); if
		 * (!pk_user.equals(vo.getPk_prepared())) m_bnAbandon.setEnabled(false);
		 * else if ( glparam.Parameter_settledperiod != null &&
		 * (vo.getYear().compareTo(glparam.Parameter_settledperiod[0]) < 0 ||
		 * (vo.getYear().compareTo(glparam.Parameter_settledperiod[0]) == 0 &&
		 * vo.getPeriod().compareTo(glparam.Parameter_settledperiod[1]) <= 0)))
		 * m_bnAbandon.setEnabled(false); m_bnAdd.setEnabled(false);
		 * m_bnDelete.setEnabled(false); m_bnCopy.setEnabled(false);
		 * m_bnPaste.setEnabled(false); m_bnSave.setEnabled(false);
		 * m_bnTempSave.setEnabled(false);
		 * m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false);
		 * m_bnClearerror.setEnabled(false); m_bnSign.setEnabled(false);
		 * m_bnCheck.setEnabled(false); m_bnTally.setEnabled(false);
		 * m_bnBalance.setEnabled(false); m_bnBill.setEnabled(false); } else {
		 * m_bnAbandon.setName("����"); }
		 *
		 * if (vo.getPk_casher() != null || vo.getPk_checked() != null ||
		 * vo.getPk_manager() != null) { m_bnAbandon.setEnabled(false);
		 * m_bnTempSave.setEnabled(false); m_bnDel.setEnabled(false); if
		 * (getFunctionName().equals("preparevoucher"))
		 * m_bnSave.setEnabled(false); m_bnAdd.setEnabled(false);
		 * m_bnDelete.setEnabled(false); m_bnCopy.setEnabled(false);
		 * m_bnPaste.setEnabled(false);
		 *
		 * if (vo.getPk_casher() == null) { m_bnSign.setName("ǩ��");
		 * m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); if (vo.getSignflag() == null ||
		 * !vo.getSignflag().booleanValue() ||
		 * !glparam.Parameter_isrequirecashersign.booleanValue())
		 * m_bnSign.setEnabled(false); } else { m_bnSign.setName("ȡ��ǩ��"); if
		 * (!pk_user.equals(vo.getPk_casher())) { m_bnSign.setEnabled(false); } }
		 * if (vo.getPk_checked() == null) { m_bnCheck.setName("���"); if
		 * (vo.getPk_prepared() != null && pk_user.equals(vo.getPk_prepared())) {
		 * m_bnCheck.setEnabled(false); } else if
		 * ((glparam.Parameter_isrequirecashersign.booleanValue() &&
		 * vo.getSignflag() != null && vo.getSignflag().booleanValue() &&
		 * vo.getPk_casher() == null) || vo.getPk_prepared().equals(pk_user)) {
		 * m_bnCheck.setEnabled(false); } } else { m_bnCheck.setName("ȡ�����");
		 * m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); m_bnSave.setEnabled(false);
		 * m_bnSign.setEnabled(false); if (!pk_user.equals(vo.getPk_checked())) {
		 * m_bnCheck.setEnabled(false); } } if (vo.getPk_manager() == null) {
		 * m_bnTally.setName("����"); m_bnOffset.setEnabled(false); if
		 * (glparam.Parameter_isrequirecashersign.booleanValue() &&
		 * vo.getSignflag() != null && vo.getSignflag().booleanValue() &&
		 * vo.getPk_casher() == null) { m_bnTally.setEnabled(false); } else if
		 * (glparam.Parameter_istallyafterchecked.booleanValue() &&
		 * vo.getPk_checked() == null) { m_bnTally.setEnabled(false); } } else {
		 * m_bnTally.setName("ȡ������"); m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); m_bnSave.setEnabled(false);
		 * m_bnSign.setEnabled(false); m_bnCheck.setEnabled(false);
		 * m_bnBalance.setEnabled(false); if
		 * (!pk_user.equals(vo.getPk_manager())) { m_bnTally.setEnabled(false); }
		 * else if (!glparam.Parameter_isuntallyable.booleanValue())
		 * m_bnTally.setEnabled(false); else if
		 * ((glparam.Parameter_settledperiod[0] +
		 * glparam.Parameter_settledperiod[1]).compareTo(vo.getYear() +
		 * vo.getPeriod()) >= 0) m_bnTally.setEnabled(false); } } else {
		 * m_bnSign.setName("ǩ��"); m_bnCheck.setName("���");
		 * m_bnTally.setName("����"); m_bnOffset.setEnabled(false); if
		 * (vo.getSignflag() == null || !vo.getSignflag().booleanValue() ||
		 * !glparam.Parameter_isrequirecashersign.booleanValue())
		 * m_bnSign.setEnabled(false); else { m_bnCheck.setEnabled(false);
		 * m_bnTally.setEnabled(false); } if (vo.getPk_prepared() != null &&
		 * pk_user.equals(vo.getPk_prepared())) { m_bnCheck.setEnabled(false); }
		 * if (!glparam.Parameter_istallyafterchecked.booleanValue()) {
		 * m_bnCheck.setEnabled(false); } else { m_bnTally.setEnabled(false); }
		 * if (vo.getPk_voucher() == null) { m_bnAbandon.setEnabled(false);
		 * m_bnDel.setEnabled(false); m_bnClearerror.setEnabled(false);
		 * m_bnCopyVoucher.setEnabled(false); m_bnSign.setEnabled(false);
		 * m_bnCheck.setEnabled(false); m_bnTally.setEnabled(false);
		 * m_bnOffset.setEnabled(false); m_bnBill.setEnabled(false);
		 * m_bnImage.setEnabled(false); } else { if
		 * (getFunctionName().equals("offsetvoucher"))
		 * m_bnSave.setEnabled(false); } } if (vo.getPk_system() == null ||
		 * vo.getPk_system().equals("GL")) { m_bnBill.setEnabled(false); }
		 * updateButtons();
		 */
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-8-19 16:30:31)
	 *
	 * @param newM_voucherpanel
	 *            nc.ui.gl.uicfg.AbstractBasicPane
	 */
	public void setVoucherPanel(VoucherPanel newM_voucherpanel) {
		getTabVoucher().setVoucherPanel(newM_voucherpanel);
		m_voucherpanel = newM_voucherpanel;
	}

	/***************************************************************************
	 * ����: ���UiManagerҪ��ʾĳһ������ģ�飬������� ��ģ���showMe�����������ʾ����
	 *
	 * ����: IParent parent ����ģ�����UiManager�е�ĳЩ ���ݵĵ��ýӿ�ʵ���� ����ֵ: ��
	 **************************************************************************/
	public void showMe(nc.ui.glpub.IParent parent) {
		parent.getUiManager().add(this, this.getName());
		m_parent = parent;
		setFrame(parent.getFrame());
		init(((ToftPanel)parent.getUiManager()).getFuncletContext());

		// hurh �����ܽڵ������Ļ��������ŵ�model��
		getVoucherPanel().getMasterModel().setParameter("FuncletContext", getFuncletContext());
		
		// hurh 2011-11-25 ΪVoucherPanel���Ӽ���
		getVoucherPanel().addValueChangeListener(parent);
		getVoucherPanel().getVoucherUI().setParent(parent);

		postInit();
	}

	/**
	 * valueChanged ����ע�⡣
	 */
	public void valueChanged(nc.ui.gl.uicfg.ValueChangeEvent evt) {
		switch (((Integer) evt.getKey()).intValue()) {
		case VoucherKey.V_VOUCHERTYPENAME: {
			if(evt.getNewValue() != null){
				setTitleText(evt.getNewValue().toString());
			}
			return;
		}
		case VoucherKey.P_VOUCHER: {
			if (evt.getNewValue() == null) {
				m_parent.closeMe();
				return;
			}
			VoucherVO vo = (VoucherVO) evt.getNewValue();
			if (vo.getPk_vouchertype() != null && vo.getPk_accountingbook()!=null)
				setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			resetButtonState(vo);
			this.showHintMessage("");
			return;
		}
		case VoucherKey.V_PK_ACCOUNTINGBOOK:
			// ���ģ���ϵĺ����˲�����ֵ��ͬ��˵����Ҫ���¼���
			VoucherModel model = (VoucherModel)getVoucherPanel().getVoucherModel();
			if(evt.getNewValue() == null)
				return;
//			if(!evt.getNewValue().toString().equals(model.getPk_accountingbook())){
//				model.setPk_accountingbook(evt.getNewValue().toString());
			getVoucherPanel().setPk_accountingbook(evt.getNewValue().toString());
			isUILoaded = false;
			postInit();
			getVoucherPanel().setUIConfigVO(uiconfigvo);
			// ����fire�¼�����Ҫ��Ϊˢ�±�ͷ��Ϣ
			//model.fireValueChange(evt);
			if(((UFBoolean)model.getParameter("needNewVoucher")).booleanValue()){
				invoke(null, nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000039")/*
						 * @res
						 * "����"
						 */);
			}
//			}
			return;
		}
	}

	public VoucherTabbedPane getTabVoucher() {
		if (tabVoucher == null) {
			tabVoucher = new VoucherTabbedPane();
			tabVoucher.setToftPanel(this);
		}
		return tabVoucher;
	}

	private VoucherPanel createVoucherPanel() {
		if (uiconfigvo == null) {
			uiconfigvo = VoucherConfigVO.getSysDefaultVO(getModuleCode(), null, null);
		}
		VoucherPanel vp = m_voucherpanel;
		vp.setUIConfigVO(uiconfigvo);
		// vp.addListener("ToftPanel", this);
		return vp;
	}
	/**
	 * ʹ�ý���ı༭��ť���ɲ���
	 */
	public void disableEdit(){
		ButtonObject[] buttons=this.getButtons();
		ArrayList<String> captions= new ArrayList<String>();
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000108")/*@res "����"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000001")/*@res "����"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000039")/*@res "ɾ��"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("glpub_0","02002003-0064")/*@res "����ƾ֤"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000013")/*@res "ɾ��"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000012")/*@res "����"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000115")/*@res "��������"*/);
		for (int i = 0; i < buttons.length; i++) {
			if(captions.contains(buttons[i].getName()))
				buttons[i].setEnabled(false);
		}
		this.setButtons(buttons);
	}
	
	public void setLinkButtons(){
		ButtonObject[] buttons = getButtons();
		List<ButtonObject> buns = new LinkedList<ButtonObject>();
		String[] linkButtons = new String[]{"ƾ֤", "�ֽ���������", "ԭʼƾ��", "��¼", "������Ϣ",/*-=notranslate=-*/ 
				"����", "���鵥��", "�������", "������ʱ��", "����Ԥ��",/*-=notranslate=-*/
				"���", "��ҳ", "��һҳ", "��һҳ", "ĩҳ",/*-=notranslate=-*/
				"��ӡ", "����"};/*-=notranslate=-*/
		HashMap<String, String> map = new HashMap<String, String>();
		for(String str : linkButtons){
			map.put(str, str);
		}
		
		ButtonObject[] tmpBtns = null;
		for(ButtonObject btn : buttons){
			if (map.get(btn.getCode()) != null) {
				buns.add(btn);
				if (btn.getChildCount() > 0) {
					tmpBtns = btn.getChildButtonGroup();
					for(ButtonObject child : tmpBtns){
						if(map.get(child.getCode()) == null){
							btn.removeChildButton(child);
						}
					}
				}
			}
		}
		super.setButtons(buns.toArray(new ButtonObject[0]));
	}
	
	/**
	 * �������������ƽ̨����ƾ֤���ء�ϵͳ�ݴ桱��ť
	 * @author zhaoyangm 2013-06-13
	 */
	@SuppressWarnings("deprecation")
	public void setLinkAddButtons(){
		
		ButtonObject[] buttons = getButtons();
		List<ButtonObject> buns = new LinkedList<ButtonObject>();
		String[] toFilterButtons = new String[]{"ϵͳ�ݴ�"};/*-=notranslate=-*/ 
		HashMap<String, String> map = new HashMap<String, String>();
		for(String str : toFilterButtons){
			map.put(str, str);
		}
		
		ButtonObject[] tmpBtns = null;
		for(ButtonObject btn : buttons){
			if (map.get(btn.getCode()) == null) {
				buns.add(btn);
				if (btn.getChildCount() > 0) {
					tmpBtns = btn.getChildButtonGroup();
					for(ButtonObject child : tmpBtns){
						if(map.get(child.getCode()) != null){
							btn.removeChildButton(child);
						}
					}
				}
			}
		}
		super.setButtons(buns.toArray(new ButtonObject[0]));
	}
}