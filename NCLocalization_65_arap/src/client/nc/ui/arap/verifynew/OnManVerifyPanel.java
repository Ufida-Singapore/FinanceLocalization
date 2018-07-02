package nc.ui.arap.verifynew;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import nc.bs.logging.Log;
import nc.funcnode.ui.FuncletInitData;
import nc.itf.fi.pub.Currency;
import nc.itf.fi.pub.FIBException;
import nc.pubitf.para.SysInitQuery;
import nc.ui.arap.pub.ArapBillWorkPageConst;
import nc.ui.arap.pub.ArapUiUtil;
import nc.ui.pub.beans.UIDesktopPane;
import nc.ui.pub.beans.UIInternalFrame;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.uif2.IFuncNodeInitDataListener;
import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.components.IAutoShowUpEventListener;
import nc.ui.uif2.components.ITabbedPaneAwareComponent;
import nc.ui.uif2.components.ITabbedPaneAwareComponentListener;
import nc.ui.uif2.components.TabbedPaneAwareCompnonetDelegate;
import nc.vo.arap.agiotage.ArapBusiDataVO;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.pub.UFDoubleTool;
import nc.vo.arap.sysinit.SysinitConst;
import nc.vo.arap.verify.AggverifyVO;
import nc.vo.arap.verify.VerifyMainVO;
import nc.vo.arap.verify.VerifyTool;
import nc.vo.arap.verifynew.MethodVO;
import nc.vo.arap.verifynew.Saver;
import nc.vo.arap.verifynew.VerifyFilter;
import nc.vo.bd.currinfo.CurrinfoVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.link.DefaultLinkData;
import nc.vo.verifynew.pub.DefaultAggregatedValueObject;
import nc.vo.verifynew.pub.DefaultVerifyRuleVO;
import nc.vo.verifynew.pub.DigitDeal;
import nc.vo.verifynew.pub.VerifyCom;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author twei
 * 
 */
public class OnManVerifyPanel extends VerifyAbstractPanel implements ITabbedPaneAwareComponent, IAutoShowUpComponent, IFuncNodeInitDataListener {

	private static final long serialVersionUID = 1L;
	private Hashtable debitDataMap;// 借方数据哈西表（嵌套哈西表，以单据pk为外层键，单据项pk为内层键，以VerifyVO为值）
	private Hashtable creditDataMap;// 贷方数据哈西表
	private VerifyListView listview;
	private VerifyCom com;// 核销组件

	/** BillListPanel，展现过滤出的待核销的单据 */
	private BillCardPanel jfBillListPanel;

	private BillCardPanel dfBillListPanel;

	/** UIInternalFrame，作为借贷方模板的容器 */
	private UIInternalFrame jfFrame;

	private UIInternalFrame dfFrame;

	private UIDesktopPane m_deskPane;

	public int deskFrameDisplayMode = 0;// 0横向平铺，1纵向平铺

	public int focus = 0;

	public MethodVO[] methods = null;

	private DefaultVerifyRuleVO[] rules = null;// 用户选择的核销规则

	private Integer displayMode;// 显示模式 0,按单据 其他，按单据表体分录

	private Integer verifySeq;// 核销顺序

	// 防止穿透时重复加载模板的判断变量
	private boolean m_isTempletLoaded = false;

	// 系统标志 应收应付报账中心
	private Integer systemCode;

	private String pkorg;

	public MethodVO[] allMethods;

	public boolean isUnSameVerify = false;

	private final IAutoShowUpComponent autoShowUpComponent;

	private final ITabbedPaneAwareComponent tabbedPaneAwareComponent;

	public OnManVerifyPanel() {
		super();
		initialize();
		onWindow_V();
		postInit();
		autoShowUpComponent = new AutoShowUpEventSource(this);
		tabbedPaneAwareComponent = new TabbedPaneAwareCompnonetDelegate();
	}

	public void setArapFlag(String arapFlag) {
		if (arapFlag.equals("ar")) {
			systemCode = ArapBillDealVOConsts.INT_ZERO;
		} else {
			systemCode = ArapBillDealVOConsts.INT_ONE;

		}
	}

	public VerifyListView getListview() {
		return listview;
	}

	public void setListview(VerifyListView listview) {
		this.listview = listview;
	}

	public Hashtable getM_debitData() {
		return debitDataMap;
	}

	public void setM_debitData(Hashtable mDebitData) {
		debitDataMap = mDebitData;
	}

	public Hashtable getM_creditData() {
		return creditDataMap;
	}

	public void setM_creditData(Hashtable mCreditData) {
		creditDataMap = mCreditData;
	}

	private void initialize() {
		setCurrentpage(ArapBillWorkPageConst.LISTPAGE);
		setLayout(new BorderLayout());
		getDeskPane().setBackground(new java.awt.Color(200, 200, 200));
		getDeskPane().add(getJfFrame());
		getDeskPane().add(getDfFrame());
		getDeskPane().setVisible(true);
		add(getDeskPane(), BorderLayout.CENTER);
	}

	public void dfAfterEdit(BillEditEvent en) {
		new DfEditListner().afterEdit(en);
	}

	public void jfAfterEdit(BillEditEvent en) {
		new JfEditListner().afterEdit(en);
	}

	private UIDesktopPane getDeskPane() {
		if (m_deskPane == null) {
			m_deskPane = new UIDesktopPane();
			m_deskPane.setName("m_deskPane");
			m_deskPane.addComponentListener(new ComponentListener() {
				public void componentHidden(ComponentEvent e) {
				}

				public void componentMoved(ComponentEvent e) {
				}

				public void componentResized(ComponentEvent e) {
					if (deskFrameDisplayMode == 0) {
						onWindow_H();
					} else if (deskFrameDisplayMode == 1) {
						onWindow_V();
					}
				}

				public void componentShown(ComponentEvent e) {
				}
			});
		}
		return m_deskPane;
	}

	private void initHxSeq() {
		if (verifySeq == null) {
			try {
				String hxSeq = SysInitBO_Client.getParaString(this.getPkorg(), "AR1");
				if (hxSeq.equals(SysinitConst.VERIFY_ZAO)) {
					verifySeq = Integer.valueOf(0);
				} else {
					verifySeq = Integer.valueOf(1);
				}

			} catch (Exception e) {
				ExceptionHandler.consume(e);
			}
		}
	}

	public String getPkorg() {
		return pkorg;
	}

	public void setPkorg(String pkorg) {
		this.pkorg = pkorg;
	}

	/** 登陆公司pk */
	public String getPkGroup() {
		return ArapUiUtil.getPk_group();
	}

	/** 登陆操作员pk */
	private String getPkuser() {
		return ArapUiUtil.getPk_user();
	}

	/** 节点号,子类重写 */
	public String getNodeCode() {
		return "20060MV";
	}


	/** 借方Frame */
	public UIInternalFrame getJfFrame() {
		if (jfFrame == null) {
			jfFrame = new UIInternalFrame(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0187")/*
																															 * @res
																															 * "本方"
																															 */, true, true, true, true);
			jfFrame.setName("m_jfFrame");
			jfFrame.getContentPane().add(getJfBillListPanel());
			jfFrame.setAutoscrolls(true);
			jfFrame.setVisible(true);
			jfFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
				@Override
				public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
					OnManVerifyPanel.this.focus = 1;
				}
			});
		}
		return jfFrame;
	}

	public int getWidth2() {
		return this.getWidth();
	}

	public int getHeight2() {
		return this.getHeight();
	}

	/** 贷方Frame */
	public UIInternalFrame getDfFrame() {
		if (dfFrame == null) {
			dfFrame = new UIInternalFrame(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0188")/*
																															 * @res
																															 * "对方"
																															 */, true, true, true, true);
			dfFrame.setName("m_dfFrame");
			dfFrame.getContentPane().add(getDfBillListPanel());
			dfFrame.setAutoscrolls(true);
			dfFrame.setVisible(true);
			dfFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
				@Override
				public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
					OnManVerifyPanel.this.focus = 2;
				}
			});
		}
		return dfFrame;
	}

	/**
	 * 加载贷方单据模板，以展示借方数据
	 */
	public BillCardPanel getDfBillListPanel() {
		if (dfBillListPanel == null) {
			dfBillListPanel = new BillCardPanel();
			dfBillListPanel.setName("m_dfBillListPanel");
		}
		return dfBillListPanel;
	}

	/**
	 * 加载借方单据模板，以展示借方数据
	 */
	public BillCardPanel getJfBillListPanel() {
		if (jfBillListPanel == null) {
			jfBillListPanel = new BillCardPanel();
			jfBillListPanel.setName("m_jfBillListPanel");
		}
		return jfBillListPanel;
	}

	// 横向平铺
	private void onWindow_H() {
		getJfFrame().setBounds(0, 0, getWidth2(), getHeight2() / 2);
		getDfFrame().setBounds(0, getHeight2() / 2, getWidth2(), getHeight2() / 2);
		deskFrameDisplayMode = 0;
	}

	// 纵向平铺
	private void onWindow_V() {
		getJfFrame().setBounds(0, 0, getWidth2() / 2, getHeight2());
		getDfFrame().setBounds(getWidth2() / 2, 0, getWidth2() / 2, getHeight2());
		deskFrameDisplayMode = 1;
	}

	private ArapBusiDataVO[] getSelectedVOs(String zb_pk, Hashtable hash) {
		if (zb_pk == null || hash == null || hash.isEmpty()) {
			return null;
		}
		Vector v = getSelectedVOVect(zb_pk, hash);
		if (v == null || v.size() == 0) {
			return null;
		}
		ArapBusiDataVO[] vos = new ArapBusiDataVO[v.size()];
		v.copyInto(vos);
		return vos;
	}

	public Integer initDisplayMode() {
		if (displayMode == null) {
			try {
				String HxMode = null;
				if (this.systemCode.intValue() == 0)
					HxMode = SysInitBO_Client.getParaString(getPkorg(), "AR2");
				else if (this.systemCode.intValue() == 1)
					HxMode = SysInitBO_Client.getParaString(getPkorg(), "AP2");
				else
					HxMode = SysInitBO_Client.getParaString(getPkorg(), "EC2");
				if (HxMode.trim().equals(SysinitConst.VERIFY_BILL)) {
					displayMode = Integer.valueOf(0);
				} else {
					displayMode = Integer.valueOf(1);
				}
			} catch (Exception e) {
				ExceptionHandler.consume(e);
				displayMode = Integer.valueOf(1);
			}
		}
		return displayMode;
	}

	/**
	 * 用于界面穿透过程中如果没有加载模板需要在这里初始化
	 */
	@Override
	protected void postInit() {

		if (m_isTempletLoaded)
			return;
		try {
			// initDisplayMode();
			String pkcorp = getPkGroup();
			String pkuser = getPkuser();
			String nodecode = getNodeCode();
			// getDfBillListPanel().loadTemplet(nodecode, null, pkuser, pkcorp,
			// "DT");// ,billListData);
			getDfBillListPanel().loadTemplet(nodecode, null, pkuser, pkcorp, "onManDF");// ,billListData);
			getDfBillListPanel().getBodyPanel().setAutoAddLine(false);
			getDfBillListPanel().getBodyPanel().clearDefalutEditAction();

			getDfBillListPanel().getBodyItem(ArapBusiDataVO.XZBZ).setEdit(true);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.THIS_SETT).setEdit(false);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setEdit(false);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setEdit(false);

			getDfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setTatol(true);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.MONEY_BAL).setTatol(true);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setTatol(true);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.THIS_SETT).setTatol(true);
			getDfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setTatol(true);
			getDfBillListPanel().setTatolRowShow(true);
			getDfBillListPanel().addEditListener(new DfEditListner());
			// getDfBillListPanel().getBillModel().execLoadFormula();

			// getJfBillListPanel().loadTemplet(getNodeCode(), null,
			// getPkuser(), getPkGroup(), "DS");// ,billListData);
			getJfBillListPanel().loadTemplet(nodecode, null, pkuser, pkcorp, "onManJF");
			getJfBillListPanel().getBodyPanel().setAutoAddLine(false);
			getJfBillListPanel().getBodyPanel().clearDefalutEditAction();

			getJfBillListPanel().getBodyItem(ArapBusiDataVO.XZBZ).setEdit(true);
			// getJfBillListPanel().getHeadItem(ArapBusiDataVO.XZBZ).setEdit(true);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.THIS_SETT).setEdit(false);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setEdit(false);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setEdit(false);

			getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setTatol(true);

			getJfBillListPanel().getBodyItem(ArapBusiDataVO.MONEY_BAL).setTatol(true);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setTatol(true);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.THIS_SETT).setTatol(true);
			getJfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setTatol(true);
			getJfBillListPanel().setTatolRowShow(true);
			getJfBillListPanel().addEditListener(new JfEditListner());

			String[] strItems = new String[] { ArapBillDealVOConsts.DEFBODY, ArapBillDealVOConsts.DEFHEAD };
			String[] strPrefix = new String[] { "fbzyx", "zbzyx" };

			getDfBillListPanel().setShowThMark(true);
			getJfBillListPanel().setShowThMark(true);

			try {
				// 暂时注释掉
				// new CardDefShowUtil(getJfBillListPanel(), new
				// DefaultDefShowStrategyByBillItem()).showDefWhenRef(strItems,
				// strPrefix, new boolean []{false,false});
				// new CardDefShowUtil(getDfBillListPanel(), new
				// DefaultDefShowStrategyByBillItem()).showDefWhenRef(strItems,
				// strPrefix, new boolean []{false,false});
			} catch (Exception e) {
				ExceptionHandler.consume(e);
			}
			setZbjsVisible(false);

		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		m_isTempletLoaded = true;
		onWindow_H();
	}

	private void showMoneyByPara() {
		boolean needResetBillModel=false;
		if (isAnDanjv()) {
			if(getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).isShow()){
				getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).setShow(false);
				getDfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).setShow(false);
				getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setShow(true);
				getDfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setShow(true);
				needResetBillModel=true;
			}
		} else {
			if(!getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).isShow()){
				getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).setShow(true);
				getDfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_ITEMMONEY).setShow(true);
				getJfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setShow(false);
				getDfBillListPanel().getBodyItem(ArapBusiDataVO.SUM_HEADMONEY).setShow(false);
				needResetBillModel=true;
			}
		}
		if(needResetBillModel){
			BillScrollPane bsp = getJfBillListPanel().getBodyPanel();
			bsp.setTableModel(bsp.getTableModel());
	
			BillScrollPane bsp1 = getDfBillListPanel().getBodyPanel();
			bsp1.setTableModel(bsp1.getTableModel());
		}
	}

	public void setBczkVisible(boolean b) {
		getDfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setShow(b);
		getJfBillListPanel().getBodyItem(ArapBusiDataVO.DISCTION).setShow(b);
		BillScrollPane bsp = getDfBillListPanel().getBodyPanel();
		bsp.setTableModel(bsp.getTableModel());
		bsp = getJfBillListPanel().getBodyPanel();
		bsp.setTableModel(bsp.getTableModel());
	}

	public void setZbjsVisible(boolean b) {
		getDfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setShow(b);
		getJfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setShow(b);
		BillScrollPane bsp = getDfBillListPanel().getBodyPanel();
		bsp.setTableModel(bsp.getTableModel());

		bsp = getJfBillListPanel().getBodyPanel();
		bsp.setTableModel(bsp.getTableModel());
	}

	private Vector getSelectedVOVect(String zb_pk, Hashtable hash) {
		if (hash == null || hash.isEmpty()) {
			return null;
		}
		Object value = hash.get(zb_pk);
		return (Vector) value;
	}

	public Integer getHxSeq() {
		if (verifySeq == null) {
			try {
				String hxSeq = "";
				if (this.systemCode.intValue() == 0)
					hxSeq = SysInitQuery.getParaString(getPkorg(), "AR1");
				else if (this.systemCode.intValue() == 1)
					hxSeq = SysInitQuery.getParaString(getPkorg(), "AP1");
				else
					hxSeq = SysInitQuery.getParaString(getPkorg(), "EC1");
				if (hxSeq.equals(SysinitConst.VERIFY_ZAO)) {
					verifySeq = Integer.valueOf(0);
				} else {
					verifySeq = Integer.valueOf(1);
				}
			} catch (Exception e) {
				verifySeq = Integer.valueOf(1);
				ExceptionHandler.consume(e);
			}
		}
		return verifySeq;
	}

	private void showMoneyByObjtype(ArapBusiDataVO[] vos) {
		if (ArrayUtils.isEmpty(vos)) {
			return;
		}
		Map<String, List<ArapBusiDataVO>> billmap = new HashMap<String, List<ArapBusiDataVO>>();
		Set<String> objSet = new HashSet<String>();
		for (ArapBusiDataVO vo : vos) {
			String pk_bill = vo.getPk_bill();
			if (billmap.containsKey(pk_bill)) {
				billmap.get(pk_bill).add(vo);
			} else {
				List<ArapBusiDataVO> temp = new ArrayList<ArapBusiDataVO>();
				temp.add(vo);
				billmap.put(pk_bill, temp);
			}
		}
		for (String pk : billmap.keySet()) {
			objSet.clear();
			Map<String, List<ArapBusiDataVO>> samemap = new HashMap<String, List<ArapBusiDataVO>>();
			List<ArapBusiDataVO> list = billmap.get(pk);
			for (ArapBusiDataVO vo : list) {
				Integer objtype = vo.getObjtype();
				String objpk = "";
				if (objtype.intValue() == 0) {
					objpk = vo.getCustomer();
				} else if (objtype.intValue() == 1) {
					objpk = vo.getSupplier();
				} else if (objtype.intValue() == 2) {
					objpk = vo.getPk_deptid();
				} else if (objtype.intValue() == 3) {
					objpk = vo.getPk_psndoc();
				}
				objSet.add(objpk);
				if (samemap.containsKey(vo.getPk_bill() + objpk)) {
					samemap.get(vo.getPk_bill() + objpk).add(vo);
				} else {
					List<ArapBusiDataVO> temp = new ArrayList<ArapBusiDataVO>();
					temp.add(vo);
					samemap.put(vo.getPk_bill() + objpk, temp);
				}
			}
			if (objSet.size() > 1) {// 往来对象名称多个
				for (ArapBusiDataVO vo : list) {
					// 用表头金额显示 表体金额
					Integer objtype = vo.getObjtype();
					String objpk = "";
					if (objtype.intValue() == 0) {
						objpk = vo.getCustomer();
					} else if (objtype.intValue() == 1) {
						objpk = vo.getSupplier();
					} else if (objtype.intValue() == 2) {
						objpk = vo.getPk_deptid();
					} else if (objtype.intValue() == 3) {
						objpk = vo.getPk_psndoc();
					}
					List<ArapBusiDataVO> list2 = samemap.get(vo.getPk_bill() + objpk);
					Map<String, List<ArapBusiDataVO>> createItemMap = createItemMap(list2);
					UFDouble summoney = UFDouble.ZERO_DBL;
					// 表体PK相同的，只取一次表体金额
					for (String key : createItemMap.keySet()) {
						List<ArapBusiDataVO> list3 = createItemMap.get(key);
						summoney = summoney.add(list3.get(0).getSum_itemmoney());
					}
					vo.setSum_headmoney(summoney);
				}
			}
		}

	}

	private Map<String, List<ArapBusiDataVO>> createItemMap(List<ArapBusiDataVO> list2) {
		Map<String, List<ArapBusiDataVO>> itemMap = new HashMap<String, List<ArapBusiDataVO>>();
		for (ArapBusiDataVO vo : list2) {
			if (itemMap.containsKey(vo.getPk_item())) {
				itemMap.get(vo.getPk_item()).add(vo);
			} else {
				List<ArapBusiDataVO> temp = new ArrayList<ArapBusiDataVO>();
				temp.add(vo);
				itemMap.put(vo.getPk_item(), temp);
			}
		}
		return itemMap;

	}

	// 借贷方数据放置到模板
	public static void setDateToPanel(OnManVerifyPanel parentNormal, VerifyCom com) {
		ArapBusiDataVO[] dfvos = com.getM_creditdata();
		// 借方数据
		ArapBusiDataVO[] jfvos = com.getM_debitdata();

		// 按单据核销， 多表体，每个表体往来对象不同，显示的原币金额应是 表体的原币金额
		if (parentNormal.isAnDanjv()) {
			parentNormal.showMoneyByObjtype(dfvos);
			parentNormal.showMoneyByObjtype(jfvos);
		}
		Hashtable<String, Vector<ArapBusiDataVO>> h_df = VerifyTool.createHash(dfvos, parentNormal.isAnDanjv(), parentNormal.getHxSeq().intValue(), null);
		Hashtable<String, Vector<ArapBusiDataVO>> h_jf = VerifyTool.createHash(jfvos, parentNormal.isAnDanjv(), parentNormal.getHxSeq().intValue(), null);

		parentNormal.setM_creditData(h_df);
		parentNormal.setM_debitData(h_jf);
		ArapBusiDataVO[] jfDispvos = null;
		ArapBusiDataVO[] dfDispvos = null;

		jfDispvos = VerifyTool.sumVerifyVO(h_jf , parentNormal.getHxSeq().intValue());
		dfDispvos = VerifyTool.sumVerifyVO(h_df , parentNormal.getHxSeq().intValue());
		dealZbjs(parentNormal);
		// 根据往来对象显示往来对象名称
		parentNormal.showObjNameByObjtype(jfDispvos, dfDispvos);

		// 设置中币结算 精度
		setMid_MnyDigital(parentNormal);
		DefaultAggregatedValueObject jfvo = new DefaultAggregatedValueObject();
		jfvo.setChildrenVO(jfDispvos);
		parentNormal.getJfBillListPanel().getBillData().setBillValueObjectByMetaData(jfDispvos);

		parentNormal.getJfBillListPanel().getBillModel().execLoadFormula();
		// DefaultVerifyRuleVO[] ruleVOFromDlg =
		// parentNormal.getRuleVOFromDlg();
		DigitDeal digit = new DigitDeal();
		digit.setMoneyDigit(parentNormal.getJfBillListPanel());

		DefaultAggregatedValueObject dfvo = new DefaultAggregatedValueObject();
		dfvo.setChildrenVO(dfDispvos);
		parentNormal.getDfBillListPanel().getBillData().setBillValueObjectByMetaData(dfDispvos);

		digit.setMoneyDigit(parentNormal.getDfBillListPanel());
		// 暂时注释掉
		parentNormal.getDfBillListPanel().getBillModel().execLoadFormula();
		parentNormal.getDfBillListPanel().setShowThMark(true);
		parentNormal.getJfBillListPanel().setShowThMark(true);
		// parentNormal.getJfBillListPanel().getBodyPanel().clearDefalutEditAction();
		parentNormal.getDfBillListPanel().getBodyPanel().clearDefalutEditAction();
		// 根据参数（按 单据，按表体核销）来显示原币金额
		parentNormal.showMoneyByPara();
	}

	private void showObjNameByObjtype(ArapBusiDataVO[] jfDispvos, ArapBusiDataVO[] dfDispvos) {
		String[] objs = new String[] { ArapBusiDataVO.CUSTOMER, ArapBusiDataVO.SUPPLIER, ArapBusiDataVO.PK_DEPTID, ArapBusiDataVO.PK_PSNDOC };
		String showoby = "";
		boolean needResetBillModel=false;
		if (!ArrayUtils.isEmpty(jfDispvos)) {
			Integer objtype = jfDispvos[0].getObjtype();
			if (objtype == 0) {
				showoby = ArapBusiDataVO.CUSTOMER;
			} else if (objtype == 1) {
				showoby = ArapBusiDataVO.SUPPLIER;
			} else if (objtype == 2) {
				showoby = ArapBusiDataVO.PK_DEPTID;
			} else if (objtype == 3) {
				showoby = ArapBusiDataVO.PK_PSNDOC;
			}
			for (String st : objs) {
				boolean show = getJfBillListPanel().getBodyItem(st).isShow();
				if (st.equals(showoby)) {
					if(!show){ 
						getJfBillListPanel().getBodyItem(st).setShow(true);
						needResetBillModel=true;
					}
				} else {
					if (show) {
						getJfBillListPanel().getBodyItem(st).setShow(false);
						needResetBillModel=true;
					}
				}
			}
		}

		if (!ArrayUtils.isEmpty(dfDispvos)) {
			Integer objtype = dfDispvos[0].getObjtype();
			if (objtype == 0) {
				showoby = ArapBusiDataVO.CUSTOMER;
			} else if (objtype == 1) {
				showoby = ArapBusiDataVO.SUPPLIER;
			} else if (objtype == 2) {
				showoby = ArapBusiDataVO.PK_DEPTID;
			} else if (objtype == 3) {
				showoby = ArapBusiDataVO.PK_PSNDOC;
			}
			for (String st : objs) {
				boolean show = getDfBillListPanel().getBodyItem(st).isShow();
				if (st.equals(showoby)) {
					if(!show){
						getDfBillListPanel().getBodyItem(st).setShow(true);
						needResetBillModel=true;
					}
				} else {
					if (show) {
						getDfBillListPanel().getBodyItem(st).setShow(false);
						needResetBillModel=true;
					}
				}
			}
		}
		
		if(needResetBillModel){
			BillScrollPane bsp = getJfBillListPanel().getBodyPanel();
			bsp.setTableModel(bsp.getTableModel());
	
			BillScrollPane bsp1 = getDfBillListPanel().getBodyPanel();
			bsp1.setTableModel(bsp1.getTableModel());
		}
	}

	public boolean isAnDanjv() {
		try {
			return displayMode.intValue() == 0;
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		return true;
	}

	private static void dealZbjs(OnManVerifyPanel parentNormal) {
		if (parentNormal.isUnSameVerify != parentNormal.isUnSameVerify()) {
			DefaultVerifyRuleVO[] rulevos = parentNormal.initRuleVOs();
			if (rulevos != null) {
				parentNormal.setZbjsVisible(parentNormal.isUnSameVerify());
				parentNormal.setBczkVisible(!parentNormal.isUnSameVerify());
			}
		}
		parentNormal.isUnSameVerify = parentNormal.isUnSameVerify();
	}

	public boolean isUnSameVerify() {
		DefaultVerifyRuleVO rulevo = getUnSameVerifyRuleVO();
		if (rulevo != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasRBVerify() {
		DefaultVerifyRuleVO rulevo = getRBVerifyRuleVO();
		if (rulevo != null) {
			return true;
		}
		return false;
	}

	public DefaultVerifyRuleVO getUnSameVerifyRuleVO() {
		return getCom().getRuleVO("UN_SAME_VERIFY");
	}
	public DefaultVerifyRuleVO getRBVerifyRuleVO() {
		return getCom().getRuleVO("RED_BLUE");
	}

	private void setRules(DefaultVerifyRuleVO[] rules) {
		this.rules = rules;
	}

	private static void setMid_MnyDigital(OnManVerifyPanel parent) {

		// DefaultVerifyRuleVO[] rulevos = parent.getRuleVOFromDlg();
		DefaultVerifyRuleVO[] rulevos = parent.getRules();
		DefaultVerifyRuleVO rulevo = null;
		String pk_corp = ArapUiUtil.getPk_group();
		if (rulevos != null && rulevos[2] != null) {
			rulevo = rulevos[2];
		} else if (rulevos != null && rulevos[1] != null) {
			rulevo = rulevos[1];
		} else if (rulevos != null && rulevos[0] != null) {
			rulevo = rulevos[0];
		}
		// 取中币币种pk
		String mid_curr = rulevo.getM_verifyCurr();
		// 中币精度
		int mid_curr_dig = 2;
		if (mid_curr != null) {
			mid_curr_dig = Currency.getCurrDigit(mid_curr);
		}
		parent.getJfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setDecimalDigits(mid_curr_dig);
		parent.getDfBillListPanel().getBodyItem(ArapBusiDataVO.MID_SETT).setDecimalDigits(mid_curr_dig);
	}

	public void setSelected(BillModel model, Hashtable selctDataHash, boolean issel, boolean isjf) {
		ArapBusiDataVO[] sel_vos;

		if(!issel){
			for (int row = 0; row < model.getRowCount(); row++) {
				model.setValueAt(new Boolean(issel), row, ArapBusiDataVO.XZBZ);
				model.setValueAt(null, row, ArapBusiDataVO.DISCTION);
				model.setValueAt(null, row, ArapBusiDataVO.THIS_SETT);
				model.setValueAt(null, row, ArapBusiDataVO.MID_SETT);
			}
		}
		
		for (int row = 0; row < model.getRowCount(); row++) {
			if (isjf) {
				BillEditEvent en = new BillEditEvent(this, new Boolean(issel), ArapBusiDataVO.XZBZ, row);
				jfAfterEdit(en);
			} else {
				BillEditEvent en = new BillEditEvent(this, new Boolean(issel), ArapBusiDataVO.XZBZ, row);
				dfAfterEdit(en);
			}
		}
	}

	private String getKey(BillModel model, int row, boolean isjf) {
		String objtypepk = "";
		Integer objtype = (Integer) model.getValueAt(row, ArapBusiDataVO.OBJTYPE + IBillItem.ID_SUFFIX);
		if (objtype == 0) {
			objtypepk = model.getValueAt(row, ArapBusiDataVO.CUSTOMER + IBillItem.ID_SUFFIX).toString();
		} else if (objtype == 1) {
			objtypepk = model.getValueAt(row, ArapBusiDataVO.SUPPLIER + IBillItem.ID_SUFFIX).toString();
		} else if (objtype == 2) {
			objtypepk = model.getValueAt(row, ArapBusiDataVO.PK_DEPTID + IBillItem.ID_SUFFIX).toString();
		} else if (objtype == 3) {
			objtypepk = model.getValueAt(row, ArapBusiDataVO.PK_PSNDOC + IBillItem.ID_SUFFIX).toString();
		}
		if (isAnDanjv()) {
			return (String) model.getValueAt(row, ArapBusiDataVO.PK_BILL) + "_" + objtypepk;
		} else {
			String keys = (String) model.getValueAt(row, ArapBusiDataVO.PK_ITEM) + "_" + objtypepk;
			return keys;
		}
	}

	/**
	 * 原币转换成中币, 取原币到中币的汇率, 结果取中币的精度
	 * 
	 * @param ufd
	 *            要转换的金额
	 * @param hl
	 *            原币到中币的汇率
	 */
	public UFDouble bzHuansuanYB2ZB(UFDouble jsyb, UFDouble zbhl, String ybpk) {

		String zbpk = getUnSameVerifyRuleVO().getM_verifyCurr();
		UFDouble jszb = new UFDouble(0);
		try {
			CurrinfoVO currinfoVO = Currency.getCurrRateInfo(getPkorg(), ybpk, zbpk);
			if (currinfoVO.getConvmode() == 0) {
				jszb = jsyb.multiply(zbhl, Currency.getCurrDigit(zbpk));
			} else {
				jszb = jsyb.div(zbhl, Currency.getCurrDigit(zbpk));
			}
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
		}
		return jszb;
	}

	// 借方币种对中间币种汇率
	private UFDouble jfbz2zjbzHL() {
		if (isUnSameVerify()) {
			DefaultVerifyRuleVO rulevo = getUnSameVerifyRuleVO();
			return rulevo.getM_jfbz2zjbzHL();
		}
		return new UFDouble(0);
	}

	// 贷方币种对中间币种汇率
	private UFDouble dfbz2zjbzHL() {
		if (isUnSameVerify()) {
			DefaultVerifyRuleVO rulevo = getUnSameVerifyRuleVO();
			return rulevo.getM_dfbz2zjbzHL();
		}
		return new UFDouble(0);
	}

	private class JfEditListner implements BillEditListener {

		public void afterEdit(BillEditEvent e) {
			int row = e.getRow();
			BillModel model = getJfBillListPanel().getBillModel();
			String key = getKey(model, row, true);
			// 从哈西里面得到
			ArapBusiDataVO[] sel_vos = getSelectedVOs(key, getM_debitData());
			try {
				chuliJsje_xuanzhong(row, e, model, sel_vos);
			} catch (BusinessException e1) {
				model.setValueAt(UFBoolean.FALSE, row, ArapBusiDataVO.XZBZ);
				Log.getInstance(this.getClass()).error(e1.getMessage(), e1);
				showWarningMessage(e1.getMessage());
			}
		}

		public void bodyRowChange(BillEditEvent e) {
		}
	}

	private class DfEditListner implements BillEditListener {

		public void afterEdit(BillEditEvent e) {
			initHxSeq();
			int row = e.getRow();
			BillModel model = getDfBillListPanel().getBillModel();
			String key = getKey(model, row, false);
			// 从哈西里面得到
			ArapBusiDataVO[] sel_vos = getSelectedVOs(key, getM_creditData());
			try {
				chuliJsje_xuanzhong(row, e, model, sel_vos);
			} catch (BusinessException e1) {
				model.setValueAt(UFBoolean.FALSE, row, ArapBusiDataVO.XZBZ);
				Log.getInstance(this.getClass()).error(e1.getMessage(), e1);
				showWarningMessage(e1.getMessage());
			}
		}

		public void bodyRowChange(BillEditEvent e) {
		}
	}

	private void chuliJsje_xuanzhong(int row, BillEditEvent e, BillModel model, ArapBusiDataVO[] sel_vos) throws BusinessException {
		if (sel_vos == null || sel_vos.length == 0) {
			return;
		}
		UFDouble d_zjbzhl = UFDouble.ZERO_DBL;
		String bzpk = "";
		String key = e.getKey();
		if (key != null && key.equalsIgnoreCase(ArapBusiDataVO.XZBZ)) {
			Boolean isselected;
			if (e.getValue() instanceof Boolean) {
				isselected = (Boolean) e.getValue();
			} else {
				isselected = new Boolean(((UFBoolean) e.getValue()).booleanValue());
			}
			if (isselected) {
				model.setValueAt(model.getValueAt(row, ArapBusiDataVO.MONEY_BAL), row, ArapBusiDataVO.THIS_SETT);
				model.setValueAt(UFDouble.ZERO_DBL, row, ArapBusiDataVO.DISCTION);
				model.getItemByKey(ArapBusiDataVO.THIS_SETT).setEnabled(true);
				model.getItemByKey(ArapBusiDataVO.DISCTION).setEnabled(true);
				// guoyw
				model.setValueAt(UFBoolean.TRUE, row, ArapBusiDataVO.XZBZ);
				for (ArapBusiDataVO vo : sel_vos) {
					vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, vo.getOccupationmny());
					vo.setAttributeValue(ArapBusiDataVO.DISCTION, UFDouble.ZERO_DBL);
					vo.setAttributeValue(ArapBusiDataVO.XZBZ, UFBoolean.TRUE);
				}
				// 异币种核销
				if (isUnSameVerify()) {
					model.getItemByKey(ArapBusiDataVO.MID_SETT).setEnabled(true);
					UFDouble zbjs = UFDouble.ZERO_DBL;
					UFDouble bcjs = (UFDouble) model.getValueAt(row, ArapBusiDataVO.MONEY_BAL);
					if (isJieFang(sel_vos[0])) {
						bzpk = getUnSameVerifyRuleVO().getM_debitCurr();
						d_zjbzhl = jfbz2zjbzHL();
					} else {
						bzpk = getUnSameVerifyRuleVO().getM_creditCurr();
						d_zjbzhl = dfbz2zjbzHL();
					}
					zbjs = bzHuansuanYB2ZB(bcjs, d_zjbzhl, bzpk);
					model.setValueAt(zbjs, row, ArapBusiDataVO.MID_SETT);
					for (ArapBusiDataVO vo : sel_vos) {
						vo.setAttributeValue(ArapBusiDataVO.MID_SETT, bzHuansuanYB2ZB(vo.getOccupationmny(), d_zjbzhl, bzpk));
						vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, vo.getOccupationmny());
						vo.setAttributeValue(ArapBusiDataVO.DISCTION, UFDouble.ZERO_DBL);
						vo.setAttributeValue(ArapBusiDataVO.XZBZ, UFBoolean.TRUE);
					}
				}
			} else {
				model.setValueAt(UFDouble.ZERO_DBL, row, ArapBusiDataVO.THIS_SETT);
				model.setValueAt(UFDouble.ZERO_DBL, row, ArapBusiDataVO.MID_SETT);
				model.setValueAt(UFDouble.ZERO_DBL, row, ArapBusiDataVO.DISCTION);

				for (ArapBusiDataVO vo : sel_vos) {
					vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, UFDouble.ZERO_DBL);
					vo.setAttributeValue(ArapBusiDataVO.MID_SETT, UFDouble.ZERO_DBL);
					vo.setAttributeValue(ArapBusiDataVO.DISCTION, UFDouble.ZERO_DBL);
					vo.setAttributeValue(ArapBusiDataVO.XZBZ, UFBoolean.FALSE);
				}
			}
		} else {
			UFDouble money_bal = UFDouble.ZERO_DBL;
			for (ArapBusiDataVO svo : sel_vos) {
				money_bal = money_bal.add(svo.getOccupationmny());
			}
			if (isUnSameVerify()) {
				if (isJieFang(sel_vos[0])) {
					bzpk = getUnSameVerifyRuleVO().getM_debitCurr();
					d_zjbzhl = jfbz2zjbzHL();
				} else {
					bzpk = getUnSameVerifyRuleVO().getM_creditCurr();
					d_zjbzhl = dfbz2zjbzHL();
				}
			}
			if (key.equals(ArapBusiDataVO.THIS_SETT)) {
				UFDouble settle = new UFDouble(e.getValue().toString());
				UFDouble disction = model.getValueAt(row, ArapBusiDataVO.DISCTION) == null ? UFDouble.ZERO_DBL : (UFDouble) model.getValueAt(row, ArapBusiDataVO.DISCTION);
				if (money_bal.abs().compareTo(settle.abs().add(disction.abs())) < 0) {
					String errMsg = NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0",
							"02006ver-0194", null, new String[] { String.valueOf(row + 1) });
					throw new BusinessException(errMsg); // 第{0}行结算金额+折扣金额大于原币余额
				}
				if (money_bal.multiply(settle).compareTo(UFDouble.ZERO_DBL) < 0) {
					throw new BusinessException(NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0195")/*
																																	 * @res
																																	 * "结算金额和原币余额方向相反"
																																	 */);
				}
				UFDouble show_value = UFDouble.ZERO_DBL;
				
				//包括红蓝对冲时，需要分两步分配，第一步负数的（假如是核销正数的，反之亦然）
				if(hasRBVerify()){
					for (ArapBusiDataVO vo : sel_vos) {
						UFDouble value = UFDouble.ZERO_DBL;
						if (money_bal.multiply(vo.getOccupationmny()).doubleValue()<0){
							value = vo.getOccupationmny();
						}else {
							continue;
						}
						settle = settle.sub(value);
						vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, value);
						show_value = show_value.add(value);

						// 计算中币
						if (isUnSameVerify()) {
							vo.setAttributeValue(ArapBusiDataVO.MID_SETT, bzHuansuanYB2ZB(value, d_zjbzhl, bzpk));
						}
					}
				}
				
				for (ArapBusiDataVO vo : sel_vos) {
					boolean settleMore = settle.abs().compareTo(vo.getOccupationmny().abs()) > 0;
					UFDouble value = UFDouble.ZERO_DBL;
					
					if (money_bal.multiply(vo.getOccupationmny()).doubleValue()<0){
						continue;
					}
					if (settleMore) {
						value = vo.getOccupationmny();
					} else {
						value = settle; 
					}
					settle = settle.sub(value);
					vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, value);
					show_value = show_value.add(value);

					// 计算中币
					if (isUnSameVerify()) {
						vo.setAttributeValue(ArapBusiDataVO.MID_SETT, bzHuansuanYB2ZB(value, d_zjbzhl, bzpk));
					}
				}
				// guoyw
				model.setValueAt(show_value, row, ArapBusiDataVO.THIS_SETT);
				if (isUnSameVerify()) {
					UFDouble mid_money = bzHuansuanYB2ZB((UFDouble) model.getValueAt(row, ArapBusiDataVO.THIS_SETT), d_zjbzhl, bzpk);
					model.setValueAt(mid_money, row, ArapBusiDataVO.MID_SETT);
				}
			}
			if (key.equals(ArapBusiDataVO.DISCTION)) {
				UFDouble disction = new UFDouble(e.getValue().toString());
				UFDouble this_sett = model.getValueAt(row, ArapBusiDataVO.THIS_SETT) == null ? UFDouble.ZERO_DBL : (UFDouble) model.getValueAt(row, ArapBusiDataVO.THIS_SETT);
				if (money_bal.abs().compareTo(disction.abs().add(this_sett.abs())) < 0) {
					String errMsg = NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0",
							"02006ver-0197", null, new String[] { String.valueOf(row + 1), disction.add(this_sett).toString(), money_bal.setScale(4, 4).toString() });
					throw new BusinessException(errMsg); // 第{0}行，折扣金额+本次结算金额>原币余额。\n折扣金额+本次结算金额：{1}\n原币余额：{2}
				}
				if (money_bal.multiply(disction).compareTo(UFDouble.ZERO_DBL) < 0) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("2006ver_0", "02006ver-0198")/* @res "折扣金额和原币余额方向相反" */);
				}
				model.setValueAt(disction, row, ArapBusiDataVO.DISCTION);
				UFDouble[] objs = getZkbl(row, model);// 折扣率计算的除数与被除数
				int count = sel_vos.length;
				// 没有协议
				if (count == 1) {
					for (ArapBusiDataVO vo : sel_vos) {
						vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, this_sett);
						vo.setAttributeValue(ArapBusiDataVO.DISCTION, disction);
					}
				} else {
					for (ArapBusiDataVO vo : sel_vos) {
						// 多协议分摊折扣
						if ((disction.add(this_sett)).abs().compareTo(vo.getOccupationmny().abs()) < 0) {
							vo.setAttributeValue(ArapBusiDataVO.DISCTION, disction);
							vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, this_sett);
							break;
						} else {
							// 当前协议折扣金额
							UFDouble bc_distction = vo.getOccupationmny().multiply(objs[0]).div(objs[1]);
							// 当前协议核销金额
							UFDouble bc_settMoney = vo.getOccupationmny().sub(bc_distction);
							vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, bc_settMoney);
							vo.setAttributeValue(ArapBusiDataVO.DISCTION, bc_distction);
							// 剩余核销金额
							this_sett = this_sett.sub(bc_settMoney);
							disction = disction.sub(bc_distction);
						}
					}
				}

			}
			if (key.equals(ArapBusiDataVO.MID_SETT)) {
				UFDouble mid_sett = new UFDouble(e.getValue().toString());

				if (isJieFang(sel_vos[0])) {
					bzpk = getUnSameVerifyRuleVO().getM_debitCurr();
					d_zjbzhl = jfbz2zjbzHL();
				} else {
					bzpk = getUnSameVerifyRuleVO().getM_creditCurr();
					d_zjbzhl = dfbz2zjbzHL();
				}
				UFDouble this_js = bzHuansuanZB2YB(bzpk, mid_sett, d_zjbzhl);
				model.setValueAt(this_js, row, ArapBusiDataVO.THIS_SETT);

				UFDouble monbal_zb = bzHuansuanYB2ZB(money_bal, d_zjbzhl, bzpk);
				UFDouble mid_sett_1 = bzHuansuanYB2ZB(this_js, d_zjbzhl, bzpk);

				if (monbal_zb.abs().compareTo(mid_sett.abs()) < 0) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0199")/*
																																	 * @res
																																	 * "中币结算金额大于原币余额"
																																	 */);
				}
				if (monbal_zb.multiply(mid_sett).compareTo(UFDouble.ZERO_DBL) < 0) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0200")/*
																																	 * @res
																																	 * "中币结算金额和原币余额方向相反"
																																	 */);
				}
				UFDouble show_bcjs = UFDouble.ZERO_DBL;
				UFDouble show_zbjs = UFDouble.ZERO_DBL;
				for (ArapBusiDataVO vo : sel_vos) {
					UFDouble one_zbjs = bzHuansuanYB2ZB(vo.getOccupationmny(), d_zjbzhl, bzpk);
					boolean settleMore = mid_sett.abs().compareTo(one_zbjs.abs()) > 0;
					UFDouble value = UFDouble.ZERO_DBL;
					if (settleMore) {
						value = bzHuansuanYB2ZB(vo.getOccupationmny(), d_zjbzhl, bzpk);
					} else {
						value = mid_sett;
					}
					mid_sett = mid_sett.sub(value);
					UFDouble this_bcjs = bzHuansuanZB2YB(bzpk, value, d_zjbzhl);
					// 中币，用原币* 汇率 再转换一下，
					vo.setAttributeValue(ArapBusiDataVO.MID_SETT, bzHuansuanYB2ZB(this_bcjs, d_zjbzhl, bzpk));
					vo.setAttributeValue(ArapBusiDataVO.THIS_SETT, this_bcjs);
					show_bcjs = show_bcjs.add(this_bcjs);
					show_zbjs = show_zbjs.add(value);
				}
				model.setValueAt(show_zbjs, row, ArapBusiDataVO.MID_SETT);
				model.setValueAt(show_bcjs, row, ArapBusiDataVO.THIS_SETT);
			}
		}
	}

	private UFDouble[] getZkbl(int row, BillModel model) {
		//计算折扣率的除数与被除数。此时，如果先算完，会导致后面的折扣汇总金额与实际金额有差额，从而前台报错【借贷方不等，无法核销】  modify by zhaoyanf
		UFDouble[] objs = new UFDouble[2];
		objs[0] = UFDouble.ZERO_DBL;
		objs[1] = UFDouble.ONE_DBL;//为了防止后续计算被除数为0，设置默认值为1
		if (model == null || row < 0) {
			return objs;
		}
		UFDouble bcjs = (UFDouble) model.getValueAt(row, ArapBusiDataVO.THIS_SETT);
		UFDouble bczk = (UFDouble) model.getValueAt(row, ArapBusiDataVO.DISCTION);
		UFDouble sum_bcjs_bczk = UFDoubleTool.sum(bcjs, bczk);
		if (bczk == null || UFDoubleTool.isZero(bczk) || UFDoubleTool.isZero(sum_bcjs_bczk)) {
			return objs;
		}
		objs[0] = bczk;
		objs[1] = sum_bcjs_bczk;
//		UFDouble zkbl = bczk.div(sum_bcjs_bczk);
		return objs;
	}

	/**
	 * 中间转换成原币, 取原币到中间币种的汇率的倒数, 结果取原币的精度
	 * 
	 * @param ufd
	 *            要转换的金额
	 * @param hl
	 *            原币到中币的汇率
	 * @throws FIBException
	 */
	public UFDouble bzHuansuanZB2YB(String ybpk, UFDouble jszb, UFDouble hl) throws FIBException {

		String zbpk = getUnSameVerifyRuleVO().getM_verifyCurr();
		UFDouble jsyb = new UFDouble(0);
		try {
			CurrinfoVO currinfoVO = Currency.getCurrRateInfo(getPkorg(), ybpk, zbpk);
			if (currinfoVO.getConvmode() == 0) {
				jsyb = jszb.div(hl, Currency.getCurrDigit(ybpk));
			} else {
				jsyb = jszb.multiply(hl, Currency.getCurrDigit(ybpk));
			}
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
		}
		return jsyb;
	}

	public DefaultVerifyRuleVO[] initRuleVOs() {
		DefaultVerifyRuleVO[] rulevos = getListview().getQueryDlg(false).getRuleVO();
		getCom().setM_rule(rulevos);
		setRules(rulevos);
		return rulevos;
	}

	public DefaultVerifyRuleVO[] getRules() {
		return rules;
	}

	// 判断该vo在红冲过程中是在借方还是在贷方
	private boolean isJieFang(ArapBusiDataVO vo) {
		if (vo == null) {
			return false;
		}
		try {
			return Integer.valueOf(vo.getAttributeValue(ArapBusiDataVO.DIRECTION).toString()) == 1;
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		return false;
	}

	public VerifyCom getCom() {
		if (com == null) {
			com = new VerifyCom(new VerifyFilter(), new Saver(), allMethods);
		}
		return com;
	}

	public boolean isSelectedDF() {
		return getDfFrame().isSelected();
	}

	public boolean isSelectedJF() {
		return getJfFrame().isSelected();
	}

	public Integer getM_iSystem() {
		return systemCode;
	}

	protected void setM_iSystem(Integer system) {
		systemCode = system;
	}

	private int getCurrenDig(String bzpk) {
		try {
			return Currency.getCurrInfo(bzpk).getCurrdigit().intValue();
		} catch (Exception e) {
			ExceptionHandler.consume(e);
			return 2;// 默认2位
		}
	}

	// 实现页签自动切换

	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	public void showMeUp() {
		autoShowUpComponent.showMeUp();
	}

	public void addTabbedPaneAwareComponentListener(ITabbedPaneAwareComponentListener l) {
		tabbedPaneAwareComponent.addTabbedPaneAwareComponentListener(l);
	}

	public boolean canBeHidden() {
		return tabbedPaneAwareComponent.canBeHidden();
	}

	public boolean isComponentVisible() {
		return tabbedPaneAwareComponent.isComponentVisible();
	}

	public void setComponentVisible(boolean visible) {
		tabbedPaneAwareComponent.setComponentVisible(visible);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initData(FuncletInitData data) {
		if (data == null || data.getInitData() == null) {
			getListview().getBillListPanel().getBillListData().setHeaderValueObjectByMetaData(null);
			return;
		}
		DefaultLinkData initData = (DefaultLinkData) data.getInitData();
		Collection<Object> billVOs = initData.getBillVOs();
		AggverifyVO[] aggverifyVOs = billVOs.toArray(new AggverifyVO[0]);
		VerifyMainVO parentVO = (VerifyMainVO) aggverifyVOs[0].getParentVO();
		getListview().getBillListPanel().getBillListData().setHeaderValueObjectByMetaData(new VerifyMainVO[] { parentVO });
		new DigitDeal().setVerifyMainDigit(getListview().getBillListPanel());
		// getListview().setHeadTableHighLightByModelSelection();
		getListview().showMeUp();
	}
}