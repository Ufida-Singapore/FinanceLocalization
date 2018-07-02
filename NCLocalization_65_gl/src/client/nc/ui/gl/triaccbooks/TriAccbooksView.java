package nc.ui.gl.triaccbooks;

/**
 * �˴���������˵����
 * �������ڣ�(01-8-13 16:12:08)
 * @author��κС��
 */
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.itf.gl.extquery.IAccBookExtend;
import nc.itf.gl.extquery.IQueryExternalAdjust;
import nc.ui.gl.accbook.DisVisableClearBtn;
import nc.ui.gl.accbook.IBillModel;
import nc.ui.gl.accbook.JTableTool;
import nc.ui.gl.accbook.PrintDialog;
import nc.ui.gl.accbook.TableDataModel;
import nc.ui.gl.accbookextend.AccbookQueryAdjust;
import nc.ui.gl.accbookprint.IPrintCenterDealClass;
import nc.ui.gl.accbookprint.PrintJobDlg;
import nc.ui.gl.analysis.GlQueryVOContainer;
import nc.ui.gl.datacache.AccountCache;
import nc.ui.gl.datacache.GLParaDataCache;
import nc.ui.gl.gateway.glworkbench.GlWorkBench;
import nc.ui.gl.gateway60.fipub.FiPrintDirectProxy;
import nc.ui.gl.print.GlPrintEntry;
import nc.ui.glpub.IParent;
import nc.ui.glpub.IUiPanel;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.UISplitPane;
import nc.ui.pub.beans.UITree;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.uif2.components.TreePanel;
import nc.ui.uif2.model.HierachicalDataAppModel;
import nc.vo.bd.access.tree.AbastractTreeCreateStrategy;
import nc.vo.bd.account.AccountVO;
import nc.vo.bd.meta.GeneralBDObjectAdapterFactory;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gl.accbook.PrintCondVO;
import nc.vo.gl.print.GlPrintTempletmanageHeadVO;
import nc.vo.gl.triaccbooks.TriAccVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.glcom.nodecode.GlNodeConst;
import nc.vo.glcom.tools.BaseCorpChooser;
import nc.vo.glpub.IVoAccess;
import nc.vo.org.FinanceOrgVO;
import nc.vo.querytemplate.TemplateInfo;

@SuppressWarnings({ "deprecation", "serial","rawtypes", "unchecked" })
public class TriAccbooksView extends nc.ui.pub.beans.UIPanel implements
		nc.ui.pub.beans.UIDialogListener, PropertyChangeListener,
		IPrintCenterDealClass {
	private TriAccbookUI ivjTriAccbook = null;

	TriAccList dataList = new TriAccList();

	private IParent m_parent;

	java.util.Hashtable printHash;

	GlQueryVO qryVO;

	nc.ui.gl.accbook.PrintDialog printdlg = null;

	private ToftPanelView parentView=null;

	private int iIndex = -1;

	private GlQueryVOContainer glQryCtner = null;

	private TriAccbooksModelSttl ivjMdlSttl= null;

	private  boolean  blOldDebug = true;
	private QueryConditionDLG queryConditionDialog=null;//��ѯ���
	
	
ArrayList<IAccBookExtend> extendInterfaces = new ArrayList<IAccBookExtend>();
	
	

	/**
	 * TricolAccbooksView ������ע�⡣
	 */
	public TriAccbooksView() {
		super();
		initialize();
	}

	/**
	 * TricolAccbooksView ������ע�⡣
	 */
	public TriAccbooksView(ToftPanelView view) {
		super();
		setParentView(view);
		initialize();
	}


	/**
	 * TricolAccbooksView ������ע�⡣
	 *
	 * @param p0
	 *            java.awt.LayoutManager
	 */
	public TriAccbooksView(java.awt.LayoutManager p0) {
		super(p0);
	}

	private void printASControlOfMa()
	{
		    try{
		    FiPrintDirectProxy printProxy=new FiPrintDirectProxy();
		    printProxy.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002gl55","UPP2002gl55-000650")/*@res "����ʽ����"*/);
		    String bookName = qryVO.getBaseAccountingbook();
			FinanceOrgVO orgByPk_Accbook = AccountBookUtil.getOrgByPk_Accbook(bookName);
			if(orgByPk_Accbook != null) {
				bookName = orgByPk_Accbook.getName();
			}
		    printProxy.setCorp_name(bookName);
		    printProxy.printDirect(getTriAccbook(),getPrintDlg());//����panel
		    }
		    catch(Exception e)
		    {
		    	Logger.error(e.getMessage(),e);
		    }

	}
	/**
	 * TricolAccbooksView ������ע�⡣
	 *
	 * @param p0
	 *            java.awt.LayoutManager
	 * @param p1
	 *            boolean
	 */
	public TriAccbooksView(java.awt.LayoutManager p0, boolean p1) {
		super(p0, p1);
	}

	/**
	 * TricolAccbooksView ������ע�⡣
	 *
	 * @param p0
	 *            boolean
	 */
	public TriAccbooksView(boolean p0) {
		super(p0);
	}
	/**
	 *
	 *
	 */
	public TriAccbooksModelSttl getIvjMdlSttl() {
		if(ivjMdlSttl==null)
		{
			ivjMdlSttl = new TriAccbooksModelSttl();
		}
		return ivjMdlSttl;
	}
	/**
	 * ��Ҫ��ҳʱ�Ĳ�ѯ��ʽ added wj for efficiency
	 *
	 */
	public void query(GlQueryVO newQryVO) throws Exception {

		TriAccBookRltGetter getter = new TriAccBookRltGetter();
		GlQueryVOContainer glQryCtner = getter.query(newQryVO);
		setGlQryCtner(glQryCtner);
	}
	//�������������
	public GlQueryVOContainer getGlQryCtner() {
		return glQryCtner;
	}
	public void dialogClosed(nc.ui.pub.beans.UIDialogEvent e) {


		if (e.getSource() == getQueryConditionDialog()) {
			if (e.m_Operation == nc.ui.pub.beans.UIDialogEvent.WINDOW_CANCEL)
			{
				return;
			}
			if (e.m_Operation == nc.ui.pub.beans.UIDialogEvent.WINDOW_OK) {
				query();
			}
		}
		setButtonState();
	}
	

	public void query(){
		try {
			qryVO = ((QueryDialogPanel)getQueryConditionDialog().getNormalPanel()).getqryVO();
			setQueryVO(qryVO);
		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
		}
		((ToftPanel)getIParent().getUiManager()).showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021510","UPP20021510-000148")/*��ѯ�ѽ���*/);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-4-30 11:53:06)
	 *
	 * @param subjCode
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	public void fetch(String subjCode) throws java.lang.Exception {
		TableDataModel data = (TableDataModel) dataList.fetch(subjCode);
		getTriAccbook().refresh(data, dataList.getQuerryVO());
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-28 10:23:25)
	 */
	public void first1() throws Exception {
		TableDataModel data = (TableDataModel) dataList.first();
		setButtonState();
		while (!dataList.getQuerryVO().getFormatVO().isShowZeroAmountRec()
				&& !dataList.isEndRecord() && data.getData().length == 0)
			data = (TableDataModel) dataList.next();
		getTriAccbook().refresh(data, dataList.getQuerryVO());
		selectTreeNode();

	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-28 10:23:25)
	 */
	public void first() throws Exception {
		if (qryVO == null)
			return;
		iIndex = 0;
		if (getGlQryCtner().getSize() > 0) {
			TriAccVO[] allBvos=(TriAccVO[])getGlQryCtner().getVos();

			TriAccVO[] tempBvos = null;
			GlQueryVO tempQryVO = getGlQryCtner().getQueryAt(iIndex);
			//����û��QUERYVVOCONTAINER���Ԫ��
			if(tempQryVO==null)
			{
				String pk_subj = (String)getGlQryCtner().getVKeys().elementAt(iIndex);
				 tempQryVO=(GlQueryVO)qryVO.clone();
				String subjcode= AccountCache.getInstance().getAccountVOByPK(qryVO.getBaseAccountingbook(),pk_subj).getCode();
				tempQryVO.setPk_account(new String[]{pk_subj});
				tempQryVO.setAccountCodes(new String[]{subjcode});

				tempBvos=TriAccResultGetter.getBlnVOsByQryVOs(tempQryVO,allBvos);
				tempBvos=getIvjMdlSttl().getSttlData(tempBvos,tempQryVO);
				getGlQryCtner().setElementAt(tempQryVO,tempBvos,iIndex);

			}
			else
			{
			 tempBvos = (TriAccVO[])getGlQryCtner().getBalaceVOsAt(iIndex);
			}

			Boolean isLocalAux = GLParaDataCache.getInstance().IsLocalFrac(
					tempQryVO.getBaseAccountingbook());
			String pk_AuxCurrType = null, pk_LocCurrType = null;
			if (isLocalAux.booleanValue()) {
				pk_AuxCurrType = GLParaDataCache.getInstance().PkFracCurr(
						tempQryVO.getBaseAccountingbook());
				pk_LocCurrType = GLParaDataCache.getInstance().PkLocalCurr(
						tempQryVO.getBaseAccountingbook());
			}


			TableDataModel dataModel = new TableDataModel(pk_LocCurrType,pk_AuxCurrType
					,nc.vo.gl.triaccbooks.TriAccKey.K_PK_GLORGBOOK
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_Corp
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_CurrType,qryVO.getSubjVersion());

			dataModel.setData(tempBvos);
			getTriAccbook().refresh(dataModel,tempQryVO);
		} else {
			getTriAccbook().refresh(null, null);
		}
//		resetFormat();
//		showHintMessage();
		setButtonState();
	}
	public void getDataByLocation(int location) throws Exception {
		if (qryVO == null)
			return;
		TableDataModel data = (TableDataModel) dataList
				.getDataByLocation(location);
		getTriAccbook().refresh(data, dataList.getQuerryVO());
	}


	public QueryConditionDLG getQueryConditionDialog(){
		if(this.queryConditionDialog== null){
			this.queryConditionDialog=null;
			TemplateInfo tempinfo = new TemplateInfo();
			tempinfo.setPk_Org(GlWorkBench.getLoginGroup());
			tempinfo.setUserid(GlWorkBench.getLoginUser());
			tempinfo.setFunNode(GlNodeConst.GLNODE_TRIACCBOOKS);
			queryConditionDialog = new QueryConditionDLG(this.getParent(),tempinfo){
				@Override
				protected void onBtnOK() {
					// TODO Auto-generated method stub
					if(!beforeClose())
						return;
					super.onBtnOK();
				}
			};
			QueryDialogPanel normalPanel=new QueryDialogPanel(this, null,GlNodeConst.GLNODE_TRIACCBOOKS);
			queryConditionDialog.setNormalPanel(normalPanel);
			queryConditionDialog.addUIDialogListener(this);
			queryConditionDialog.setResizable(true);
			queryConditionDialog.setVisibleCandidatePanel(false);
			queryConditionDialog.setVisibleAdvancePanel(false);
			queryConditionDialog.setSize(830, 530);
			DisVisableClearBtn.hideClearBtn(queryConditionDialog);
		}
		return queryConditionDialog;
	}
	
	
	private boolean beforeClose(){
		return ((QueryDialogPanel)queryConditionDialog.getNormalPanel()).bnOk_ActionPerformed();
	}

	public IParent getIParent() {
		return m_parent;
	}

	private PrintDialog getPrintDlg() {
		if (printdlg == null) {
			try {
				printdlg = new PrintDialog(this);
				printdlg.setName("printDlg");
				printdlg
						.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				// user code begin {1}
				printdlg.addUIDialogListener(this);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return printdlg;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-27 10:12:18)
	 *
	 * @return java.util.Hashtable
	 */
	public java.util.Hashtable getPrintHash() {
		return printHash;
	}

	/**
	 * ���� TriAccbook ����ֵ��
	 *
	 * @return nc.ui.gl.triaccbooks.TriAccbook
	 */
	/* ���棺�˷������������ɡ� */
	protected TriAccbookUI getTriAccbook() {
		if (ivjTriAccbook == null) {
			try {
				ivjTriAccbook = new nc.ui.gl.triaccbooks.TriAccbookUI();
				ivjTriAccbook.setName("TriAccbook");
				ivjTriAccbook.setBounds(1, 2, 770, 418);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjTriAccbook;
	}

	/**
	 * ÿ�������׳��쳣ʱ������
	 *
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
		Log.getInstance(this.getClass().getName()).info("--------- δ��׽�����쳣 ---------");
		nc.bs.logging.Logger.error(exception.getMessage(), exception);
	}

	/**
	 * ��ʼ���ࡣ
	 */
	/* ���棺�˷������������ɡ� */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("TriAccbooksView");
			setLayout(new java.awt.BorderLayout());
			setSize(770, 418);
//			add(getTriAccbook(), "Center");
			add(getUISplitPane1());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		getTriAccbook().setInfo(dataList);
		getTriAccbook().addPropertyChangeListener(this);
		setButtonState();
		registerExtInterfaces();
		// user code end
	}
	
	
	private UISplitPane ivjUISplitPane1 = null;
	private TreePanel panelLeft = null;
	private AccountVO[] accountVOs;
	
	public AccountVO[] getAccountVOs() {
		return accountVOs;
	}

	public void setAccountVOs(AccountVO[] accountVOs) {
		this.accountVOs = accountVOs;
	}
	
	/**
	 * <p>��������ʽ��ϸ����ҳ�棬����ǻ�ƿ�Ŀ�����Ҳ�����������</p>
	 * @user ����
	 * @version V63
	 * @since V63 ����ʱ�䣺2013-8-14
	 */
	public UISplitPane getUISplitPane1() {
		if (ivjUISplitPane1 == null) {
			try {
				ivjUISplitPane1 = new UISplitPane(1);
				ivjUISplitPane1.setName("UISplitPane1");
				ivjUISplitPane1.setDividerSize(3);
				ivjUISplitPane1.setAutoscrolls(true);
				ivjUISplitPane1.setDividerLocation(210);
				ivjUISplitPane1.setPreferredSize(new java.awt.Dimension(782, 469));
				ivjUISplitPane1.setContinuousLayout(true);
				ivjUISplitPane1.setMinimumSize(new java.awt.Dimension(30, 30));
				getUISplitPane1().add(getUIScrollPaneLeft(), JSplitPane.LEFT);
				getUISplitPane1().add(getTriAccbook(), JSplitPane.RIGHT);
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUISplitPane1;
	}
	
	/**
	 * <p>��������ʽ��������ƿ�Ŀ��ҳ��</p>
	 * @user ����
	 * @version V63
	 * @since V63 ����ʱ�䣺2013-8-14
	 */
	public TreePanel getUIScrollPaneLeft() {
		if (panelLeft == null) {
			try {
				GeneralBDObjectAdapterFactory boadatorfactory = new GeneralBDObjectAdapterFactory();
				boadatorfactory.setMode("VO");
				HierachicalDataAppModel model = new HierachicalDataAppModel();
				model.setBusinessObjectAdapterFactory(boadatorfactory);
				model.setTreeCreateStrategy(new AbastractTreeCreateStrategy(){
					@Override
					public boolean isCodeTree() {
						return true;
					}
					
					@Override
					public String getCodeRule() {
						
						//���ݺ����˲���ȡ��Ŀ��Ŀ������򣬴˴�����Ƕ�������˲���������һ����ͬ
						int[] codes = AccountCache.getInstance().getAccountLevelScheme(qryVO.getpk_accountingbook()[0]);
						StringBuilder rule = new StringBuilder();
						for(int i=0;i<codes.length;i++){//������������������AbastractTreeCreateStrategy��ʶ���ģʽ��4/2/2/2/2
							rule.append(Integer.valueOf(codes[i]));
							rule.append("/");
						}
						return rule.substring(0, rule.length()-1);
					}
					
					@Override
					public DefaultMutableTreeNode getRootNode() {
						
						return new DefaultMutableTreeNode(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UCMD1-000335")/*@res "��ƿ�Ŀ"*/);
					}
					
					@Override
					public Object getCodeValue(Object obj) {
						return ((AccountVO)obj).getCode();
					}
				});
				
				panelLeft = new TreePanel();
				panelLeft.setModel(model);
				panelLeft.getTree().addTreeSelectionListener(new TreeSelectionListener(){
					
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						try {
							//��Ŀ���ڵ㷢���仯ʱ�����Ҳ���Ϣ
							TreePath treePath = ((UITree)e.getSource()).getSelectionPath();
							if(treePath != null){
								DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
								if(treeNode != null && treeNode.getUserObject() instanceof AccountVO){
									String code = ((AccountVO)(treeNode).getUserObject()).getCode();
									for(int i = 0; i < accountVOs.length ; i++){
										if(accountVOs[i].getCode().equals(code)){
											getDataByLocation(i);
											break;
										}
									}
								}
							}
						} catch (Exception e1) {
							handleException(e1);
						}
					}
				});
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
			panelLeft.getModel().initModel(null);
		}
		return panelLeft;
	}
	
	/*
	 *���ݵ�ǰ��Ŀ���ÿ�Ŀ�����ĸ��ڵ㱻ѡ�У�����ϸ�˷�ҳʱ���ô˷���
	 */
	private void selectTreeNode(){
		
		String curSubj = dataList.getQuerryVO().getAccountCode()[0];
		TreePanel treePanel = getUIScrollPaneLeft();
		HierachicalDataAppModel model = (HierachicalDataAppModel)treePanel.getModel();
		JTree accountTree = treePanel.getTree();
		
		//���Ƴ�ע��ļ��������������ýڵ�ѡ��ʱ�����²�ѯ����
		TreeSelectionListener[] listeners = accountTree.getTreeSelectionListeners();
		for(TreeSelectionListener lis : listeners){
			accountTree.removeTreeSelectionListener(lis);
		}
		
		for(int i=0;i<accountVOs.length;i++){
			if(curSubj.equals(accountVOs[i].getCode())){
				model.setSelectedData(accountVOs[i]);
				break;
			}
		}
		
		for(TreeSelectionListener lis : listeners){
			accountTree.addTreeSelectionListener(lis);
		}
		accountTree.scrollPathToVisible(accountTree.getSelectionPath());
	}	
	
	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-28 10:23:42)
	 */
	public void last1() throws Exception {
		TableDataModel data = (TableDataModel) dataList.last();
		setButtonState();
		while (!dataList.getQuerryVO().getFormatVO().isShowZeroAmountRec()
				&& !dataList.isStartRecord() && data.getData().length == 0)
			data = (TableDataModel) dataList.prev();
		getTriAccbook().refresh(data, dataList.getQuerryVO());
		selectTreeNode();
		setButtonState();
	}
	/**
	 * �������һҳ��Ч���Ż��󣩡�
	 */
	public void last() throws Exception {
		if (qryVO == null)
			return;
		if(getGlQryCtner()==null || getGlQryCtner().getSize()<=0)
			return ;
		iIndex = getGlQryCtner().getSize()-1;

			TriAccVO[] allBvos=(TriAccVO[])getGlQryCtner().getVos();

			TriAccVO[] tempBvos = null;
			GlQueryVO tempQryVO = getGlQryCtner().getQueryAt(iIndex);
			//����û��QUERYVVOCONTAINER���Ԫ��
			if(tempQryVO==null)
			{
				String pk_subj = (String)getGlQryCtner().getVKeys().elementAt(iIndex);
				 tempQryVO=(GlQueryVO)qryVO.clone();
				String subjcode= AccountCache.getInstance().getAccountVOByPK(qryVO.getBaseAccountingbook(),pk_subj).getCode();
				tempQryVO.setPk_account(new String[]{pk_subj});
				tempQryVO.setAccountCodes(new String[]{subjcode});

				tempBvos=TriAccResultGetter.getBlnVOsByQryVOs(tempQryVO,allBvos);
				tempBvos=getIvjMdlSttl().getSttlData(tempBvos,tempQryVO);
				getGlQryCtner().setElementAt(tempQryVO,tempBvos,iIndex);

			}
			else
			{
			 tempBvos = (TriAccVO[])getGlQryCtner().getBalaceVOsAt(iIndex);
			}

			Boolean isLocalAux = GLParaDataCache.getInstance().IsLocalFrac(
					tempQryVO.getBaseAccountingbook());
			String pk_AuxCurrType = null, pk_LocCurrType = null;
			if (isLocalAux.booleanValue()) {
				pk_AuxCurrType = GLParaDataCache.getInstance().PkFracCurr(
						tempQryVO.getBaseAccountingbook());
				pk_LocCurrType = GLParaDataCache.getInstance().PkLocalCurr(
						tempQryVO.getBaseAccountingbook());
			}


			TableDataModel dataModel = new TableDataModel(pk_LocCurrType,pk_AuxCurrType
					,nc.vo.gl.triaccbooks.TriAccKey.K_PK_GLORGBOOK
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_Corp
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_CurrType,qryVO.getSubjVersion());

			dataModel.setData(tempBvos);
			getTriAccbook().refresh(dataModel,tempQryVO);


		setButtonState();
	}

	/**
	 * ����ڵ� - ��������ΪӦ�ó�������ʱ���������������
	 *
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			javax.swing.JFrame frame = new javax.swing.JFrame();
			TriAccbooksView aTriAccbooksView;
			aTriAccbooksView = new TriAccbooksView();
			frame.setContentPane(aTriAccbooksView);
			frame.setSize(aTriAccbooksView.getSize());
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			frame.show();
			java.awt.Insets insets = frame.getInsets();
			frame.setSize(frame.getWidth() + insets.left + insets.right, frame
					.getHeight()
					+ insets.top + insets.bottom);
			frame.setVisible(true);
		} catch (Throwable exception) {
			nc.bs.logging.Logger.error("nc.ui.pub.beans.UIPanel �� main() �з����쳣");
			nc.bs.logging.Logger.error(exception.getMessage(), exception);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-25 15:34:38)
	 */
	public void next() throws Exception {
		if (qryVO == null)
			return;
		if(getGlQryCtner()==null || getGlQryCtner().getSize()<=0)
			return ;
		iIndex = iIndex+1;
		if(iIndex>=getGlQryCtner().getSize())
			return ;


			TriAccVO[] allBvos=(TriAccVO[])getGlQryCtner().getVos();

			TriAccVO[] tempBvos = null;
			GlQueryVO tempQryVO = getGlQryCtner().getQueryAt(iIndex);
			//����û��QUERYVVOCONTAINER���Ԫ��
			if(tempQryVO==null)
			{
				String pk_subj = (String)getGlQryCtner().getVKeys().elementAt(iIndex);
				 tempQryVO=(GlQueryVO)qryVO.clone();
				String subjcode= AccountCache.getInstance().getAccountVOByPK(qryVO.getBaseAccountingbook(),pk_subj).getCode();
				tempQryVO.setPk_account(new String[]{pk_subj});
				tempQryVO.setAccountCodes(new String[]{subjcode});

				tempBvos=TriAccResultGetter.getBlnVOsByQryVOs(tempQryVO,allBvos);
				tempBvos=getIvjMdlSttl().getSttlData(tempBvos,tempQryVO);
				getGlQryCtner().setElementAt(tempQryVO,tempBvos,iIndex);

			}
			else
			{
			 tempBvos = (TriAccVO[])getGlQryCtner().getBalaceVOsAt(iIndex);
			}

			Boolean isLocalAux = GLParaDataCache.getInstance().IsLocalFrac(
					tempQryVO.getBaseAccountingbook());
			String pk_AuxCurrType = null, pk_LocCurrType = null;
			if (isLocalAux.booleanValue()) {
				pk_AuxCurrType = GLParaDataCache.getInstance().PkFracCurr(
						tempQryVO.getBaseAccountingbook());
				pk_LocCurrType = GLParaDataCache.getInstance().PkLocalCurr(
						tempQryVO.getBaseAccountingbook());
			}


			TableDataModel dataModel = new TableDataModel(pk_LocCurrType
					,pk_AuxCurrType,nc.vo.gl.triaccbooks.TriAccKey.K_PK_GLORGBOOK
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_Corp
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_CurrType
					,qryVO.getSubjVersion());

			dataModel.setData(tempBvos);
			getTriAccbook().refresh(dataModel,tempQryVO);


		setButtonState();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-25 15:34:38)
	 */
	public void next1() throws Exception {
		// TriAccVO[] data = (TriAccVO[]) dataList.next();
		TableDataModel data = (TableDataModel) dataList.next();
		setButtonState();
		while (!dataList.getQuerryVO().getFormatVO().isShowZeroAmountRec()
				&& !dataList.isEndRecord() && data.getData().length == 0)
			data = (TableDataModel) dataList.next();
		getTriAccbook().refresh(data, dataList.getQuerryVO());
		selectTreeNode();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-28 10:22:50)
	 */
	public void prev1() throws Exception {

		TableDataModel data = (TableDataModel) dataList.prev();
		setButtonState();
		while (!dataList.getQuerryVO().getFormatVO().isShowZeroAmountRec()
				&& !dataList.isStartRecord() && data.getData().length == 0)
			data = (TableDataModel) dataList.prev();
		getTriAccbook().refresh(data, dataList.getQuerryVO());
		selectTreeNode();
		setButtonState();


	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-28 10:22:50)
	 */
	public void prev() throws Exception {
		if (qryVO == null)
			return;
		if(getGlQryCtner()==null || getGlQryCtner().getSize()<=0)
			return ;
		if(iIndex-1<0)
			return ;
		iIndex=iIndex-1;


			TriAccVO[] allBvos=(TriAccVO[])getGlQryCtner().getVos();

			TriAccVO[] tempBvos = null;
			GlQueryVO tempQryVO = getGlQryCtner().getQueryAt(iIndex);
			//����û��QUERYVVOCONTAINER���Ԫ��
			if(tempQryVO==null)
			{
				String pk_subj = (String)getGlQryCtner().getVKeys().elementAt(iIndex);
				 tempQryVO=(GlQueryVO)qryVO.clone();
				String subjcode= AccountCache.getInstance().getAccountVOByPK(qryVO.getBaseAccountingbook(),pk_subj).getCode();
				tempQryVO.setPk_account(new String[]{pk_subj});
				tempQryVO.setAccountCodes(new String[]{subjcode});

				tempBvos=TriAccResultGetter.getBlnVOsByQryVOs(tempQryVO,allBvos);
				tempBvos=getIvjMdlSttl().getSttlData(tempBvos,tempQryVO);
				getGlQryCtner().setElementAt(tempQryVO,tempBvos,iIndex);

			}
			else
			{
			 tempBvos = (TriAccVO[])getGlQryCtner().getBalaceVOsAt(iIndex);
			}

			Boolean isLocalAux = GLParaDataCache.getInstance().IsLocalFrac(
					tempQryVO.getBaseAccountingbook());
			String pk_AuxCurrType = null, pk_LocCurrType = null;
			if (isLocalAux.booleanValue()) {
				pk_AuxCurrType = GLParaDataCache.getInstance().PkFracCurr(
						tempQryVO.getBaseAccountingbook());
				pk_LocCurrType = GLParaDataCache.getInstance().PkLocalCurr(
						tempQryVO.getBaseAccountingbook());
			}


			TableDataModel dataModel = new TableDataModel(pk_LocCurrType,pk_AuxCurrType
					,nc.vo.gl.triaccbooks.TriAccKey.K_PK_GLORGBOOK
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_Corp
					,nc.vo.gl.triaccbooks.TriAccKey.K_Pk_CurrType,qryVO.getSubjVersion());

			dataModel.setData(tempBvos);
			getTriAccbook().refresh(dataModel,tempQryVO);


		setButtonState();
	}

	public void print() throws Exception {
		
		GlPrintEntry printEntry = new GlPrintEntry(getPrintDlg());
		String pk_user = GlWorkBench.getLoginUser();
		String pk_group = WorkbenchEnvironment.getInstance().getGroupVO().getPk_group();
		printEntry.setTemplateID(pk_group, GlNodeConst.GLNODE_TRIACCBOOKS, pk_user, null,
				GlNodeConst.GLNODE_TRIACCBOOKS,  null);
		nc.vo.pub.print.PrintTempletmanageHeaderVO[] headvos = printEntry
				.getAllTemplates();
		nc.vo.gl.accbook.PrintCondVO condvo = new nc.vo.gl.accbook.PrintCondVO();
		condvo.setPrintModule(headvos);
		getPrintDlg().setPrintData(condvo);
		int i = getPrintDlg().showme(GlNodeConst.GLNODE_TRIACCBOOKS);
		if (i != 1)
			return;
		nc.vo.gl.accbook.PrintCondVO printvo = getPrintDlg().getPrintData();
		printEntry.setSelected(printvo.getPrintModule()[0].getCtemplateid(),((GlPrintTempletmanageHeadVO)printvo.getPrintModule()[0]).getNodekey());
		/** ������ӡ */
		if (printvo.isBlnScopeAll() ||printvo.isBlnOutputToExcel()) {
			Vector vDataSouce = new Vector();
			first1();
			GlQueryVO queryvo=(GlQueryVO)dataList.getQuerryVO().clone();
			Object objdata= dataList.getCurrentData();
			if(dataList.getCurrentData()!=null)
			{
				IVoAccess[] ivoaccess= (IVoAccess[])dataList.getCurrentData();
				if(ivoaccess.length>0)
				{
			TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(queryvo,(IVoAccess[])objdata);
			vDataSouce.addElement(datasource);
			}
			}
			if(dataList.getAccSubjsCodes().length>1)
			{
			do {
				next1();
				GlQueryVO  newqueryvo= new GlQueryVO();
				newqueryvo=(GlQueryVO)dataList.getQuerryVO().clone();
				 objdata= dataList.getCurrentData();
				if(objdata!=null)
				{
					IVoAccess[] ivoaccess= (IVoAccess[])objdata;
					if(ivoaccess.length>0)
					{
				TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(newqueryvo,(IVoAccess[])objdata);
				vDataSouce.addElement(datasource);
				}
				}
				/** ��ӡ��һҳ */

			} while (!dataList.isEndRecord());
			}
			TriAccbookPrintDataSource[] printDataSourcees=null;
			if(vDataSouce.size()>0)
			{
				printDataSourcees = new TriAccbookPrintDataSource[vDataSouce.size()];
				vDataSouce.copyInto(printDataSourcees);

				for(int j = 0; j < printDataSourcees.length; j++)//�����ӡ����
					printEntry.setDataSource(printDataSourcees[j]);
				if(printvo.isBlnOutputToExcel()){
				    printEntry.exportExcelFile();
				    return ;
				}else{
					if(getPrintDlg().getPrintData().isPrintView()){
						printEntry.preview();
					}else{
						printEntry.print();
					}
				}
			}


		} else {
			if(printvo.isBlnPrintAsModule())
			{
				GlQueryVO queryvo=dataList.getQuerryVO();
				Object objdata= dataList.getCurrentData();
				TriAccbookPrintDataSource datasource=null;
				if(dataList.getCurrentData()!=null)
				{
					IVoAccess[] ivoaccess= (IVoAccess[])dataList.getCurrentData();
					if(ivoaccess.length>0){
						datasource = new TriAccbookPrintDataSource(queryvo,(IVoAccess[])objdata);
					}
				}
				printEntry.setDataSource(datasource);
				if(getPrintDlg().getPrintData().isPrintView()){
					printEntry.preview();
				}else{
					printEntry.print();
				}
			}else{
				printASControlOfMa();
			}
		}
		//
		if(printvo.isBlnOutputToExcel()){
		    printEntry.exportExcelFile();
		    return ;
		}

		((ToftPanel)getIParent().getUiManager()).showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("commonres","UC001-0000007")/*��ӡ*/+nc.ui.ml.NCLangRes.getInstance().getStrByID("commonres","UCH026")/*�ѽ���*/);


	}

	/**
	 * This method gets called when a bound property is changed.
	 *
	 * @param evt
	 *            A PropertyChangeEvent object describing the event source and
	 *            the property that has changed.
	 */
	public void propertyChange(java.beans.PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("subjChange")) {
			try {
				if(evt.getNewValue()!=null)
				fetch(evt.getNewValue().toString());

			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-13 17:42:45)
	 *
	 * @param model
	 *            nc.ui.gl.accbook.IBillModel
	 */
	public void setBillModel(IBillModel model) {
		dataList.setBillModel(model);
	}

	public void setIParent(IParent parent) {
		m_parent = parent;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-27 10:12:18)
	 *
	 * @param newPrintHash
	 *            java.util.Hashtable
	 */
	public void setPrintHash(java.util.Hashtable newPrintHash) {
		printHash = newPrintHash;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-13 19:26:31)
	 *
	 * @param newQuerryVO
	 *            nc.vo.glcom.balance.GlQueryVO
	 */
	public void setQueryVO(nc.vo.glcom.balance.GlQueryVO newQuerryVO) {
		try {
			qryVO = (nc.vo.glcom.balance.GlQueryVO) newQuerryVO.clone();

			if(blOldDebug)
			{
				dataList.setQuerryVO(newQuerryVO);
				setAccountVOs(dataList.getAccsubj());
				getUIScrollPaneLeft().getModel().initModel(dataList.getAccsubj());//��ʼ��������ϸ������Ŀ��
				first1();
			}
			else
			{
				query(newQuerryVO);
				first();
			}

		} catch (Exception ex) {
nc.bs.logging.Logger.error(ex.getMessage(), ex);
		}
	}

	protected void tackleBnsEvent(String tag) throws Exception {

		if (tag.equals(ButtonKey.bnQRY))
			getQueryConditionDialog().showModal();
		else if (tag.equals(ButtonKey.bnNext))
			if(blOldDebug)
				next1();
			else
			next();
		else if (tag.equals(ButtonKey.bnPriv))
			if(blOldDebug)
				prev1();
			else
			prev();
		else if (tag.equals(ButtonKey.bnFirst))
			if(blOldDebug)
				first1();
			else
			first();
		else if (tag.equals(ButtonKey.bnEnd))
			if(blOldDebug)
				last1();
			else
			last();
		else if (tag.equals(ButtonKey.bnPrint))
			print();
		else if (tag.equals(ButtonKey.bnLC)) {
			if(getIParent().getClass().getName().equals("nc.ui.gl.triaccbooks.UiManager"))
			{
				if(getIParent().getUiManager()!=null)
				{
					((ToftPanel)getIParent().getUiManager()).showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023010","UPT20023010-000050")/*����*/+nc.ui.ml.NCLangRes.getInstance().getStrByID("20023010","UPT20023010-000007")/*��ϸ*/);
				}
			}


			int selRow = getTriAccbook().getSelectedRow();
			if (selRow >= 0) {
				TriAccVO voData = ((TriAccVO[]) dataList.getCurrentData())[selRow];
				if (voData.getExplanation().equals(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
								"UPP20023030-000254")/* @res "���ºϼ�" */)) {
					GlQueryVO queryVO = (GlQueryVO) qryVO.clone();
					queryVO.setPeriod(voData.getAccPeriod());
					queryVO.setYear(voData.getAccYear());
					queryVO.setEndYear(queryVO.getYear());
					queryVO.setEndPeriod(voData.getAccPeriod());
					//���һ���ڼ�ֱ��ȡԭʼ��ѯvo�Ľ����ڼ䣬�����ڼ���ϵ�����
					if(qryVO.getEndYear().equals(queryVO.getEndYear())
							&&qryVO.getEndPeriod().substring(0,2).equals(queryVO.getEndPeriod())){
						queryVO.setEndPeriod(qryVO.getEndPeriod());
					}else{
						queryVO.setDefaultAdjust(true);
					}
//					queryVO.setEndPeriod(getEndMonth(voData.getAccPeriod(),queryVO.getBaseAccountingbook()));
//					if(!qryVO.getPeriod().equals(qryVO.getEndPeriod())&&!qryVO.getEndPeriod().equals(queryVO.getPeriod())){
//						if(qryVO.getEndPeriod().substring(0,2).equals(queryVO.getPeriod())){
//							queryVO.setEndPeriod(qryVO.getEndPeriod());
//						}else{
//							queryVO.setDefaultAdjust(true);
//						}
//					}
					
					if (!queryVO.isMultiCorpCombine())
					{
						Object orgbook = voData
								.getValue(nc.vo.gl.triaccbooks.TriAccKey.K_PK_GLORGBOOK);
						queryVO
								.setpk_accountingbook(new String[] { (orgbook == null || orgbook
										.toString().equals("")) ? queryVO
										.getpk_accountingbook()[0] : orgbook
										.toString() });
						queryVO.setBaseAccountingbook(queryVO.getpk_accountingbook()[0]);
					}
					else
					{

						String tempPk_glorgbook = BaseCorpChooser.getPk_BasCorp(queryVO.getpk_accountingbook(),queryVO.getBaseAccountingbook());
						queryVO.setBaseAccountingbook(tempPk_glorgbook);
						queryVO.getFormatVO().setMultiOrgCombine(true);
					}

					queryVO.setPk_account(new String[] { (String) voData
									.getValue(nc.vo.gl.triaccbooks.TriAccKey.K_Pk_AccSubj) });
					queryVO.setAccountCode(new String[] { (String) voData
									.getValue(nc.vo.gl.triaccbooks.TriAccKey.K_AccCode) });
					queryVO.getFormatVO().setCombineOppositeSubj(true);
					IUiPanel panel = (IUiPanel) m_parent.showNext("nc.ui.gl.detail.DetailToftPanel");
					panel.invoke(queryVO, "SetQueryVO");
				}
			}
		} else if (tag.equals(ButtonKey.bnReturn)) {
			//��Ŀ������������ʽ���˷��ؼ�ס�п�
			Map<String, JPanel> map = new HashMap<String, JPanel>();
			map.put(GlNodeConst.GLNODE_TRIACCBOOKS,getTriAccbook());
			try {
				JTableTool.INSTANCE.saveDynamicColumnWidth(map);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
			m_parent.closeMe();
		}
	}

	/**
	 * @return ���� dataList��
	 */
	protected TriAccList getDataList() {
		return dataList;
	}

	public void PrintAtCenter(GlQueryVO newQuerryVO, PrintCondVO printvo,PrintJobDlg dlg)
			throws Exception {

		qryVO = newQuerryVO;

		setPrintHash(new java.util.Hashtable());
		GlPrintEntry printEntry = new GlPrintEntry(dlg, null);
		printEntry.setTemplateID(GlWorkBench.getLoginGroup(), GlNodeConst.GLNODE_TRIACCBOOKS, GlWorkBench.getLoginUser(), null,GlNodeConst.GLNODE_TRIACCBOOKS, /*OrgnizeTypeVO.ZHUZHANG_TYPE*/ null);
		printEntry.setSelected(printvo.getPrintModule()[0].getCtemplateid(),((GlPrintTempletmanageHeadVO)printvo.getPrintModule()[0]).getNodekey());

		/** ������ӡ */
		/** �ж��Ƿ��һҳ�������л�����һҳ */

		// first();
		// *************first page*****************
		if (qryVO == null)
			return;



       ///////////////////////////////////////////
		Vector vDataSouce = new Vector();
		IBillModel billModel = new TriAccbooksModel();
		dataList.setBillModel(billModel);
		dataList.setQuerryVO(qryVO);
		setAccountVOs(dataList.getAccsubj());
		if (!dataList.isStartRecord())
		first1();
		GlQueryVO  newqueryvo= new GlQueryVO();
		newqueryvo=(GlQueryVO)dataList.getQuerryVO().clone();
//		GlQueryVO queryvo=dataList.getQuerryVO();
		Object objdata= dataList.getCurrentData();
		if(dataList.getCurrentData()!=null)
		{
			IVoAccess[] ivoaccess= (IVoAccess[])dataList.getCurrentData();
			if(ivoaccess.length>0)
			{
				TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(newqueryvo,(IVoAccess[])objdata);
				vDataSouce.addElement(datasource);
			}
		}

		if (!dataList.isEndRecord()){
			do {
				next1();
				newqueryvo= new GlQueryVO();
				newqueryvo=(GlQueryVO)dataList.getQuerryVO().clone();
//				 queryvo=dataList.getQuerryVO();
				 objdata= dataList.getCurrentData();
				if(objdata!=null)
				{
					IVoAccess[] ivoaccess= (IVoAccess[])objdata;
					if(ivoaccess.length>0)
					{
						TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(newqueryvo,(IVoAccess[])objdata);
						vDataSouce.addElement(datasource);
					}
				}
				/** ��ӡ��һҳ */
			} while (!dataList.isEndRecord());
		}

		TriAccbookPrintDataSource[] printDataSourcees=null;
		if(vDataSouce.size()>0)
		{
			printDataSourcees = new TriAccbookPrintDataSource[vDataSouce.size()];
			vDataSouce.copyInto(printDataSourcees);

			for(int j = 0; j < printDataSourcees.length; j++)//�����ӡ����
				printEntry.setDataSource(printDataSourcees[j]);

//			printEntry.print();//��ʼ��ӡ

			printEntry.preview();//��ʼ��ӡ
		}


	}

	public void setButtonState() {
		if(blOldDebug)
		{

			if(getParentView()==null)
				return ;
			ToftPanelView parentView=(ToftPanelView)getParentView();
			int iIndex = -1;
			int iLength = 0;
			if(getDataList()==null || getDataList().getAccSubjsCodes()==null)
			{

				for (int i = 0;parentView.getButtons()!=null && i < parentView.getButtons().length; i++) {
					ButtonObject btn=parentView.getButtons()[i];
					String tag = btn.getTag();
					 if (tag.equals(ButtonKey.bnNext))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnPriv))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnFirst))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnEnd))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnPrint))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnLC))
						 btn.setEnabled(false);
				}
				return ;
			}
			else
			{
				iLength=getDataList().getAccSubjsCodes().length;
			}

			 iIndex = getDataList().getIndex();
			 if(iIndex==-1)
		        {
				for (int i = 0; i < parentView.getButtons().length; i++) {
					ButtonObject btn=parentView.getButtons()[i];
					String tag = btn.getTag();
					 if (tag.equals(ButtonKey.bnNext))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnPriv))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnFirst))
						 btn.setEnabled(false);
					else if (tag.equals(ButtonKey.bnEnd))
						 btn.setEnabled(false);

				}
		        }

			 else if(iIndex==0)
	        {
			for (int i = 0; i < parentView.getButtons().length; i++) {
				ButtonObject btn=parentView.getButtons()[i];
				String tag = btn.getTag();
				 if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(true);
    			else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(true);

			}
	        }
	        else if(iIndex==iLength-1)
	        {
	        	for (int i = 0; i < parentView.getButtons().length; i++) {
	    			ButtonObject btn=parentView.getButtons()[i];
	    			String tag = btn.getTag();
	    			 if (tag.equals(ButtonKey.bnNext))
	    				 btn.setEnabled(false);
	    			else if (tag.equals(ButtonKey.bnPriv))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnFirst))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnEnd))
	    				 btn.setEnabled(false);
	    			else if (tag.equals(ButtonKey.bnPrint))
						 btn.setEnabled(true);
					else if (tag.equals(ButtonKey.bnLC))
						 btn.setEnabled(true);

	    		}

	        }
	        else
	        {

	        	for (int i = 0; i < parentView.getButtons().length; i++) {
	    			ButtonObject btn=parentView.getButtons()[i];
	    			String tag = btn.getTag();
	    			 if (tag.equals(ButtonKey.bnNext))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnPriv))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnFirst))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnEnd))
	    				 btn.setEnabled(true);
	    			else if (tag.equals(ButtonKey.bnPrint))
						 btn.setEnabled(true);
					else if (tag.equals(ButtonKey.bnLC))
						 btn.setEnabled(true);
	    		}
	        }
			parentView.updateButtons();
	}
	else{
		if(getParentView()==null)
			return ;
		ToftPanelView parentView=(ToftPanelView)getParentView();
		int iLength = 0;
		if(getGlQryCtner()==null || getGlQryCtner().getSize()<=0)
		{

			for (int i = 0;parentView.getButtons()!=null && i < parentView.getButtons().length; i++) {
				ButtonObject btn=parentView.getButtons()[i];
				String tag = btn.getTag();
				if (tag.equals(ButtonKey.bnQRY))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(false);

			}

		return ;

		}
		else if(getGlQryCtner().getSize()==1)
		{

			for (int i = 0;parentView.getButtons()!=null && i < parentView.getButtons().length; i++) {
				ButtonObject btn=parentView.getButtons()[i];
				String tag = btn.getTag();
				if (tag.equals(ButtonKey.bnQRY))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(false);

			}

		return ;

		}
		else {

		 if(iIndex==-1)
	        {
			for (int i = 0; i < parentView.getButtons().length; i++) {
				ButtonObject btn=parentView.getButtons()[i];
				String tag = btn.getTag();
				if (tag.equals(ButtonKey.bnQRY))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(false);
			}
	        }

		 else if(iIndex==0)
        {
		for (int i = 0; i < parentView.getButtons().length; i++) {
			ButtonObject btn=parentView.getButtons()[i];
			String tag = btn.getTag();
			if (tag.equals(ButtonKey.bnQRY))
				 btn.setEnabled(true);
			else if (tag.equals(ButtonKey.bnLC))
				 btn.setEnabled(true);
			else if (tag.equals(ButtonKey.bnPrint))
				 btn.setEnabled(true);
			else if (tag.equals(ButtonKey.bnNext))
				 btn.setEnabled(true);
			else if (tag.equals(ButtonKey.bnPriv))
				 btn.setEnabled(false);
			else if (tag.equals(ButtonKey.bnFirst))
				 btn.setEnabled(false);
			else if (tag.equals(ButtonKey.bnEnd))
				 btn.setEnabled(true);

		}
        }
        else if(iIndex==iLength-1)
        {
        	for (int i = 0; i < parentView.getButtons().length; i++) {
    			ButtonObject btn=parentView.getButtons()[i];
    			String tag = btn.getTag();
    			if (tag.equals(ButtonKey.bnQRY))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(false);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(false);

    		}

        }
        else
        {

        	for (int i = 0; i < parentView.getButtons().length; i++) {
    			ButtonObject btn=parentView.getButtons()[i];
    			String tag = btn.getTag();
    			if (tag.equals(ButtonKey.bnQRY))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnLC))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnPrint))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnNext))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnPriv))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnFirst))
					 btn.setEnabled(true);
				else if (tag.equals(ButtonKey.bnEnd))
					 btn.setEnabled(true);

    		}

        }
		}

		parentView.updateButtons();
		}
	}

	public ToftPanelView getParentView() {
		return parentView;
	}

	public void setParentView(ToftPanelView parentView) {
		this.parentView = parentView;
	}

	public void setGlQryCtner(GlQueryVOContainer glQryCtner) {
		this.glQryCtner = glQryCtner;
	}

	public boolean isBlOldDebug() {
		return blOldDebug;
	}

	public Vector PrintAtCenter(GlQueryVO newQuerryVO, PrintCondVO printvo, boolean needReturn) throws Exception {
		// TODO Auto-generated method stub
		qryVO = newQuerryVO;

		setPrintHash(new java.util.Hashtable());
		GlPrintEntry printEntry = new GlPrintEntry(null, null);
		printEntry.setTemplateID(GlWorkBench.getLoginGroup(), GlNodeConst.GLNODE_TRIACCBOOKS, GlWorkBench.getLoginUser(), null,GlNodeConst.GLNODE_TRIACCBOOKS, /*OrgnizeTypeVO.ZHUZHANG_TYPE*/ null);
		printEntry.setSelected(printvo.getPrintModule()[0].getCtemplateid(),((GlPrintTempletmanageHeadVO)printvo.getPrintModule()[0]).getNodekey());


		/** ������ӡ */
		/** �ж��Ƿ��һҳ�������л�����һҳ */

		// first();
		// *************first page*****************
		if (qryVO == null)
			return null;



       ///////////////////////////////////////////
		Vector vDataSouce = new Vector();
		IBillModel billModel = new TriAccbooksModel();
		dataList.setBillModel(billModel);
		dataList.setQuerryVO(qryVO);
		if (!dataList.isStartRecord())
		first1();
		GlQueryVO  newqueryvo= new GlQueryVO();
		newqueryvo=(GlQueryVO)dataList.getQuerryVO().clone();
//		GlQueryVO queryvo=dataList.getQuerryVO();
		Object objdata= dataList.getCurrentData();
		if(dataList.getCurrentData()!=null)
		{
			IVoAccess[] ivoaccess= (IVoAccess[])dataList.getCurrentData();
			if(ivoaccess.length>0)
			{
		TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(newqueryvo,(IVoAccess[])objdata);
		vDataSouce.addElement(datasource);
		}
		}

		if (!dataList.isEndRecord()){
			do {
				next1();
				newqueryvo= new GlQueryVO();
				newqueryvo=(GlQueryVO)dataList.getQuerryVO().clone();
//				 queryvo=dataList.getQuerryVO();
				 objdata= dataList.getCurrentData();
				if(objdata!=null)
				{
					IVoAccess[] ivoaccess= (IVoAccess[])objdata;
					if(ivoaccess.length>0)
					{
				TriAccbookPrintDataSource datasource = new TriAccbookPrintDataSource(newqueryvo,(IVoAccess[])objdata);
				vDataSouce.addElement(datasource);
				}
				}
				/** ��ӡ��һҳ */

			} while (!dataList.isEndRecord());
		}
		if(vDataSouce.size()>0)
		{
			return vDataSouce;
		}else{
			return null;
		}


	}
	
	public IAccBookExtend getExtendInterface(String interfaceClsName) {
		for(IAccBookExtend extend : extendInterfaces) {
			if(extend.getClass().getName().equals(interfaceClsName))
				return extend;
		}
		
		return null;
	}
	
	private void registerExtInterfaces() {
		//
		IQueryExternalAdjust adjustQuery = AccbookQueryAdjust.factory(qryVO, getTriAccbook());
		extendInterfaces.add(adjustQuery);
	}
}