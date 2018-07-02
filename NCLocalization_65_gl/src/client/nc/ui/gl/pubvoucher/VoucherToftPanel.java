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
 * 此处插入类型说明。 创建日期：(2003-1-16 15:50:23)
 *
 * @author：郭宝华
 */
public class VoucherToftPanel extends ToftPanel implements ValueChangeListener, IUiPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 4075910443879820191L;

	private VoucherPanel m_voucherpanel = null;

	private VoucherTabbedPane tabVoucher = null;

	private IParent m_parent; // 联查前一个界面的句柄

	// private ButtonObject[] m_buttons;
	// private ListModel model = new ListModel();

	protected boolean isUILoaded = false;

	private String strFunctionName;

	private HashMap button_function_cache = new HashMap();

	private HashMap function_button_cache = new HashMap();

	UIConfigVO uiconfigvo = null;

	/**
	 * VoucherView 构造子注解。
	 */
	public VoucherToftPanel() {
		super();
		initialize();
	}

	/***************************************************************************
	 * 功能: 如果UiManager或者前面的功能模块要监听本模块 的某些事件，它可以通过该方法添加。
	 *
	 * 参数: Object objListener 监听器 Object objUserdata 表识前面是何种监听器
	 *
	 * 返回值: 无
	 *
	 * 注： 该方法其实没有固定的要求，只要调用者和被调用 者之间存在该调用的相关协议，它就可使用该功能
	 **************************************************************************/
	public void addListener(java.lang.Object objListener, java.lang.Object objUserdata) {
		getVoucherPanel().addListener(objUserdata, objListener);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-9-25 17:38:03)
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
	 * 子类实现该方法，返回业务界面的标题。
	 *
	 * @version (00-6-6 13:33:25)
	 *
	 * @return java.lang.String
	 */
	public String getTitle() {
		return null;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-8-19 16:30:31)
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
		// 分录处理
		// m_bnDetail.addChildButton(m_bnAdd);
		// m_bnDetail.addChildButton(m_bnDelete);
		// m_bnDetail.addChildButton(m_bnCopy);
		// m_bnDetail.addChildButton(m_bnPaste);
		// m_bnDetail.addChildButton(m_bnQueryBalance);
		// m_bnDetail.addChildButton(m_bnVerify);
		// m_bnDetail.addChildButton(m_bnRTVerify);

		// //凭证处理
		// m_bnVoucher.addChildButton(m_bnClearerror);
		// m_bnVoucher.addChildButton(m_bnAbandon);
		// m_bnVoucher.addChildButton(m_bnDel);
		// m_bnVoucher.addChildButton(m_bnCopyVoucher);
		// m_bnVoucher.addChildButton(m_bnTempSave);
		// m_bnVoucher.addChildButton(m_bnImage);
		// //常用凭证处理
		// m_bnCommon.addChildButton(m_bnSetCommon);
		// m_bnCommon.addChildButton(m_bnLoadCommon);

		// 功能注册
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
	 * 初始化类。
	 */
	/* 警告：此方法将重新生成。 */
	private void initialize() {
		setLayout(new java.awt.BorderLayout());
		this.add(getTabVoucher(), "Center");
		setVoucherPanel(new VoucherPanel());
		getVoucherPanel().addListener("ToftPanel", this);
		// getVoucherPanel().addListener("ValueChangeListener",this);
		// getVoucherPanel().setEditable(false);

		// hurh 2011-04-28 增加监听
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
	 * hurh 覆盖此方法，posinit在界面加载完成后再调用
	 */
	@Override
	public void init() {
		setButtons(getButtons());
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-9-25 14:20:07)
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
		// free4 -- 无科目权限
		
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
	 * 功能: 如果UiManager或者前面的功能模块需要调用本模 块的某个方法以完成某个功能，它可以通过该方法 达到这一目标
	 *
	 * 参数: Object objData 所要传递的参数等信息 Object objUserData 所要传递的表示等信息
	 *
	 * 返回值: Object
	 *
	 * 注： 该方法其实没有固定的要求，只要调用者和被调用 者之间存在该调用的相关协议，它就可使用该功能
	 **************************************************************************/
	public java.lang.Object invoke(java.lang.Object objData, java.lang.Object objUserData) {
//		String userid = ClientEnvironment.getInstance().getUser().getPrimaryKey();
		String userid = GlWorkBench.getLoginUser();
		Boolean ispower = new Boolean(false);
		if (((String) objUserData).equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000037")/*
																														 * @res
																														 * "修改"
																														 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000055")/*@res "正在修改.."*/);
			getTabVoucher().setVoucherPanel(getVoucherPanel());
			getVoucherPanel().stopEditing();
			VoucherVO vo =null;

			try {
				String strVoucherPK = (String) objData;
				vo= VoucherDataBridge.getInstance().queryByPk(strVoucherPK);
				//getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000047")/*@res "提示"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000056")/*@res "凭证上有些科目没有权限,不能修改此凭证."*/);
				}
				if (vo.getPk_vouchertype() != null)
					setTitleText(VoucherDataCenter.getVouchertypeByPk_orgbook(vo.getPk_accountingbook(), vo.getPk_vouchertype()).getName());
			} catch (ClassCastException e) {
				 vo = (VoucherVO) objData;
				// getVoucherPanel().setVO(vo);
				ispower = VoucherDataBridge.getInstance().isAccsubjPower(vo, userid);
				if (ispower.booleanValue()) {
					nc.vo.fipub.utils.uif2.FiUif2MsgUtil.showUif2DetailMessage(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000047")/*@res "提示"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000056")/*@res "凭证上有些科目没有权限,不能修改此凭证."*/);

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
																																 * "浏览"
																																 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000057")/*@res "正在浏览.."*/);
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
																																 * "增加"
																																 */)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000058")/*@res "正在增加.."*/);

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
			nc.bs.logging.Logger.error("业务日期" + GlWorkBench.getBusiDate().toString());
			nc.bs.logging.Logger.error("当前服务器日期" + GlWorkBench.getBusiDate().toString());
			vo.setPeriod(GlWorkBench.getLoginPeriod());
			// vo.setNo(Integer.valueOf(0));
			// 减少凭证前后台访问接口
			//vo.setNo(Integer.valueOf(1));
			
			// hurh V60凭证新增时设置默认凭证类别
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

			// 制单人
			vo.setPk_prepared(GlWorkBench.getLoginUser());
			// 新增时创建人设置为制单人，重要，特殊数据权限使用，否则可能出现特殊数据权限校验错误
			vo.setCreator(vo.getPk_prepared());

			//vo.setPk_prepared(this.getClientEnvironment().getUser().getPrimaryKey());
			//vo.setPreparedname(this.getClientEnvironment().getUser().getUser_name());
			vo.setVoucherkind(Integer.valueOf(0));
			vo.setDiscardflag(nc.vo.pub.lang.UFBoolean.FALSE);
			vo.setDetailmodflag(nc.vo.pub.lang.UFBoolean.TRUE);
			vo.setVoucherkind(Integer.valueOf(0));
			java.util.Vector vecdetails = new java.util.Vector();
			vo.setDetail(vecdetails);
			
			// hurh 取凭证类别，方便根据凭证类别设置分录默认币种
			VoucherCell cell = (VoucherCell) ((VoucherModel)getVoucherPanel().getVoucherModel()).getView().getVoucherCellEditor(-1, Integer.valueOf(VoucherKey.V_PK_VOUCHERTYPE));
			vo.setPk_vouchertype(((UIRefPane) cell.getEditor()).getRefPK());

			getVoucherPanel().getVoucherUI().setEditable(true);
			getVoucherPanel().setVO(vo);
			getVoucherPanel().startEditing();
			// getVoucherPanel().setVO(vo);
			getVoucherPanel().getVoucherModel().setSaved(true);
			updateUI();
			// hurh v60提高效率，去掉日志操作
			// new VoucherOperatLogTool().InsertLogByVoucher((VoucherVO)getVoucherPanel().getVoucherModel().getParameter("vouchervo"), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002gl55","UC001-0000002")/*@res "增加"*/, GlNodeConst.GLNODE_VOUCHERPREPARE);
			// showHintMessage("增加结束");

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
	 * 功能: 在A功能模块调用B功能模块后，如果B功能模块关闭时 A功能模块通过该方法得到通知
	 *
	 * 参数: 无 返回值: 无
	 **************************************************************************/
	public void nextClosed() {
	}

	/**
	 * 子类实现该方法，响应按钮事件。
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
				//如果为返回操作时,将卡片态下的凭证列宽保存至缓存中  add by weiningc 20171129 
				if("EXIT".equals(func)) {
					saveVoucherCardColumsCache();
				}
				
				getCurrentVoucherPanel().doOperation(func);
			}
		} catch (GlBusinessException e) {
			// showHintMessage("操作失败");
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
			
//			//关闭影像查看窗口
//			IMasterModel model = getVoucherPanel().getVoucherModel();
//			if(model != null && model instanceof VoucherModel) {
//				VoucherModel voucherModel = (VoucherModel) model;
//				ViewImageOperationModel linkImage = (ViewImageOperationModel) voucherModel.getOperationmodels().get(VoucherFunctionRegister.FUNCTION_IMAGEVIEW);
//				if(linkImage != null){
//					linkImage.closeDlg();
//				}
//			}
			

			// if (getVoucher1().isVoucherEditing())
			// if (showYesNoMessage("当前浏览的凭证已被修改并且尚未保存，要保存吗？") == 4)
			// if (getFunctionName().equals("preparevoucher") ||
			// getFunctionName().equals("offsetvoucher") ||
			// getFunctionName().equals("voucherbridge"))
			// getVoucher1().doVoucherSave();
			// else if (getFunctionName().equals("checkvoucher"))
			// getVoucher1().doVoucherSaveError();
			
			// hurh GLUIManager统一处理
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
				nc.bs.logging.Logger.debug("取数据出错");
				throw new GlBusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("2002100555","UPP2002100555-000059")/*@res "取数据出错"*/);
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
																														 * "读取凭证格式时出现异常！！"
																														 */);
		}
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_ADDEMPTYDETAIL);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_CONFIRMSAVE);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_CLEAROTHERVOUCHERTAB);
		getVoucherPanel().installOperation(VoucherFunctionRegister.FUNCTION_REMOVETAB_BY_VOUCHERPK);
		// 守护线程//2.3为了稳定性暂时不添加
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
	 * 功能: 去除某个监听器
	 *
	 * 参数: Object objListener 监听器 Object objUserdata 标识前面是何种监听器
	 *
	 * 返回值: 无
	 *
	 * 注： 该方法其实没有固定的要求，只要调用者和被调用 者之间存在该调用的相关协议，它就可使用该功能
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
		 * vo.getDiscardflag().booleanValue()) { m_bnAbandon.setName("取消作废"); if
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
		 * m_bnAbandon.setName("作废"); }
		 *
		 * if (vo.getPk_casher() != null || vo.getPk_checked() != null ||
		 * vo.getPk_manager() != null) { m_bnAbandon.setEnabled(false);
		 * m_bnTempSave.setEnabled(false); m_bnDel.setEnabled(false); if
		 * (getFunctionName().equals("preparevoucher"))
		 * m_bnSave.setEnabled(false); m_bnAdd.setEnabled(false);
		 * m_bnDelete.setEnabled(false); m_bnCopy.setEnabled(false);
		 * m_bnPaste.setEnabled(false);
		 *
		 * if (vo.getPk_casher() == null) { m_bnSign.setName("签字");
		 * m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); if (vo.getSignflag() == null ||
		 * !vo.getSignflag().booleanValue() ||
		 * !glparam.Parameter_isrequirecashersign.booleanValue())
		 * m_bnSign.setEnabled(false); } else { m_bnSign.setName("取消签字"); if
		 * (!pk_user.equals(vo.getPk_casher())) { m_bnSign.setEnabled(false); } }
		 * if (vo.getPk_checked() == null) { m_bnCheck.setName("审核"); if
		 * (vo.getPk_prepared() != null && pk_user.equals(vo.getPk_prepared())) {
		 * m_bnCheck.setEnabled(false); } else if
		 * ((glparam.Parameter_isrequirecashersign.booleanValue() &&
		 * vo.getSignflag() != null && vo.getSignflag().booleanValue() &&
		 * vo.getPk_casher() == null) || vo.getPk_prepared().equals(pk_user)) {
		 * m_bnCheck.setEnabled(false); } } else { m_bnCheck.setName("取消审核");
		 * m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); m_bnSave.setEnabled(false);
		 * m_bnSign.setEnabled(false); if (!pk_user.equals(vo.getPk_checked())) {
		 * m_bnCheck.setEnabled(false); } } if (vo.getPk_manager() == null) {
		 * m_bnTally.setName("记账"); m_bnOffset.setEnabled(false); if
		 * (glparam.Parameter_isrequirecashersign.booleanValue() &&
		 * vo.getSignflag() != null && vo.getSignflag().booleanValue() &&
		 * vo.getPk_casher() == null) { m_bnTally.setEnabled(false); } else if
		 * (glparam.Parameter_istallyafterchecked.booleanValue() &&
		 * vo.getPk_checked() == null) { m_bnTally.setEnabled(false); } } else {
		 * m_bnTally.setName("取消记账"); m_bnSignerrorDetail.setEnabled(false);
		 * m_bnSignerrorVoucher.setEnabled(false); m_bnSave.setEnabled(false);
		 * m_bnSign.setEnabled(false); m_bnCheck.setEnabled(false);
		 * m_bnBalance.setEnabled(false); if
		 * (!pk_user.equals(vo.getPk_manager())) { m_bnTally.setEnabled(false); }
		 * else if (!glparam.Parameter_isuntallyable.booleanValue())
		 * m_bnTally.setEnabled(false); else if
		 * ((glparam.Parameter_settledperiod[0] +
		 * glparam.Parameter_settledperiod[1]).compareTo(vo.getYear() +
		 * vo.getPeriod()) >= 0) m_bnTally.setEnabled(false); } } else {
		 * m_bnSign.setName("签字"); m_bnCheck.setName("审核");
		 * m_bnTally.setName("记账"); m_bnOffset.setEnabled(false); if
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
	 * 此处插入方法说明。 创建日期：(2003-8-19 16:30:31)
	 *
	 * @param newM_voucherpanel
	 *            nc.ui.gl.uicfg.AbstractBasicPane
	 */
	public void setVoucherPanel(VoucherPanel newM_voucherpanel) {
		getTabVoucher().setVoucherPanel(newM_voucherpanel);
		m_voucherpanel = newM_voucherpanel;
	}

	/***************************************************************************
	 * 功能: 如果UiManager要显示某一个功能模块，它会调用 该模块的showMe方法以完成显示功能
	 *
	 * 参数: IParent parent 功能模块访问UiManager中的某些 数据的调用接口实现类 返回值: 无
	 **************************************************************************/
	public void showMe(nc.ui.glpub.IParent parent) {
		parent.getUiManager().add(this, this.getName());
		m_parent = parent;
		setFrame(parent.getFrame());
		init(((ToftPanel)parent.getUiManager()).getFuncletContext());

		// hurh 将功能节点上下文环境参数放到model中
		getVoucherPanel().getMasterModel().setParameter("FuncletContext", getFuncletContext());
		
		// hurh 2011-11-25 为VoucherPanel增加监听
		getVoucherPanel().addValueChangeListener(parent);
		getVoucherPanel().getVoucherUI().setParent(parent);

		postInit();
	}

	/**
	 * valueChanged 方法注解。
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
			// 如果模型上的核算账簿与新值不同，说明需要重新加载
			VoucherModel model = (VoucherModel)getVoucherPanel().getVoucherModel();
			if(evt.getNewValue() == null)
				return;
//			if(!evt.getNewValue().toString().equals(model.getPk_accountingbook())){
//				model.setPk_accountingbook(evt.getNewValue().toString());
			getVoucherPanel().setPk_accountingbook(evt.getNewValue().toString());
			isUILoaded = false;
			postInit();
			getVoucherPanel().setUIConfigVO(uiconfigvo);
			// 重新fire事件，主要是为刷新表头信息
			//model.fireValueChange(evt);
			if(((UFBoolean)model.getParameter("needNewVoucher")).booleanValue()){
				invoke(null, nc.ui.ml.NCLangRes.getInstance().getStrByID("20021005", "UPP20021005-000039")/*
						 * @res
						 * "增加"
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
	 * 使该界面的编辑按钮不可操作
	 */
	public void disableEdit(){
		ButtonObject[] buttons=this.getButtons();
		ArrayList<String> captions= new ArrayList<String>();
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000108")/*@res "新增"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000001")/*@res "保存"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000039")/*@res "删除"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("glpub_0","02002003-0064")/*@res "复制凭证"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000013")/*@res "删行"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000012")/*@res "增行"*/);
		captions.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000115")/*@res "保存新增"*/);
		for (int i = 0; i < buttons.length; i++) {
			if(captions.contains(buttons[i].getName()))
				buttons[i].setEnabled(false);
		}
		this.setButtons(buttons);
	}
	
	public void setLinkButtons(){
		ButtonObject[] buttons = getButtons();
		List<ButtonObject> buns = new LinkedList<ButtonObject>();
		String[] linkButtons = new String[]{"凭证", "现金流量分析", "原始凭单", "分录", "辅助信息",/*-=notranslate=-*/ 
				"联查", "联查单据", "联查余额", "联查序时账", "联查预算",/*-=notranslate=-*/
				"浏览", "首页", "上一页", "下一页", "末页",/*-=notranslate=-*/
				"打印", "返回"};/*-=notranslate=-*/
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
	 * 联查新增，会计平台生成凭证隐藏“系统暂存”按钮
	 * @author zhaoyangm 2013-06-13
	 */
	@SuppressWarnings("deprecation")
	public void setLinkAddButtons(){
		
		ButtonObject[] buttons = getButtons();
		List<ButtonObject> buns = new LinkedList<ButtonObject>();
		String[] toFilterButtons = new String[]{"系统暂存"};/*-=notranslate=-*/ 
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