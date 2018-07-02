package nc.ui.gl.subjassbalancebooks;

/**
 * 此处插入类型说明。
 * 创建日期：(2001-9-28 21:42:30)
 * @author：魏小炯
 */
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.gl.accbook.DisVisableClearBtn;
import nc.ui.gl.accbook.JTableTool;
import nc.ui.gl.accbook.PrintDialog;
import nc.ui.gl.datacache.BusiUnitDataCache;
import nc.ui.gl.gateway.glworkbench.GlWorkBench;
import nc.ui.gl.print.GlPrintEntry;
import nc.ui.glpub.IParent;
import nc.ui.glpub.UiManager;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.DataFormatUtilGL;
import nc.ui.pub.beans.UIDialogEvent;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gl.accbook.DataTypeConst;
import nc.vo.gl.balancebooks.BalanceBSKey;
import nc.vo.gl.balancebooks.BalanceBSVO;
import nc.vo.gl.print.GlPrintTempletmanageHeadVO;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.glcom.nodecode.GlNodeConst;
import nc.vo.glcom.tools.GLPubProxy;
import nc.vo.org.AccountingBookVO;
import nc.vo.org.FinanceOrgVO;
import nc.vo.org.OrgVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.querytemplate.TemplateInfo;
@SuppressWarnings({"serial","unchecked","rawtypes","deprecation"})
public class BalancebookView extends javax.swing.JPanel implements nc.ui.pub.beans.UIDialogListener, nc.ui.pub.print.IDataSource {
	private BalancebooksModel ivjBalancebooksModel = null;
	nc.vo.glcom.balance.GlQueryVO qryVO = null;
	private BalancebookUI ivjBalancebookUI = null;
	private QueryConditionDLG queryConditionDialog = null;
	//private QueryDlg ivjDlg = null;
	private IParent m_parent;
	private PrintDialog ivjPrintDlg = null;
	private ToftPanelView parentView = null;
	private int iState=0;
	private BalanceBSVO[] qryedData=null;

	/**
	 * 此处插入方法说明。
	 * 创建日期：(2001-11-30 14:44:49)
	 */
	private void printAsItIs() throws Exception {
		nc.ui.pub.print.PrintDirectEntry printEntry =
			new nc.ui.pub.print.PrintDirectEntry(getPrintDlg());
		//设置标题
		printEntry.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002GL502","UPP2002GL502-000202")/*@res "科目辅助余额表"*/);

		nc.vo.gl.accbook.ColFormatVO[] formats =
			getBalancebookUI().getFixModel().getVo().getColFormatVOs();
		printEntry.setMargin(10,10,10,10);
		//设置行高
		int[] lineHeight = new int[getBalancebookUI().getFixModel().getRowCount() + 2];
		lineHeight[0] = 15;
		lineHeight[1] = 15;
		for (int i = 0; i < getBalancebookUI().getFixModel().getRowCount(); i++) {
		lineHeight[i + 2] = 15;
		}
		printEntry.setRowHeight(lineHeight);
		//Log.getInstance(this.getClass().getName()).info(getAssBalanceUI().getFixModel().getRowCount());

		//设置列名
		java.util.Vector vLine1 = new java.util.Vector();
		java.util.Vector vLine0 = new java.util.Vector();
		java.util.Vector align = new java.util.Vector();

		java.util.Vector vColWidths = new java.util.Vector();
		int fixCol = 0;
		java.util.Vector<Boolean> datatypeVct = new java.util.Vector<Boolean>();
		for (int i = 0; i < formats.length; i++) {
			if (formats[i].isVisiablity()) {
				vLine0.add(formats[i].getMultiHeadStr() == null ? formats[i].getColName() : formats[i].getMultiHeadStr());
				vLine1.add(formats[i].getColName());
				vColWidths.add(Integer.valueOf(formats[i].getColWidth()));
				if (formats[i].getAlignment() == nc.vo.gl.accbook.AlignmentConst.LeftAlign) {
					align.add(Integer.valueOf(0));
				} else
					if (formats[i].getAlignment() == nc.vo.gl.accbook.AlignmentConst.CenterAlign) {
						align.add(Integer.valueOf(1));
					} else
						if (formats[i].getAlignment() == nc.vo.gl.accbook.AlignmentConst.RightAlign) {
							align.add(Integer.valueOf(2));
						}
				if (formats[i].isFrozen() == true) {
					fixCol++;
				}
				if (formats[i].getDataType() == DataTypeConst.NUMBER) {
					datatypeVct.add(true);
				} else {
					datatypeVct.add(false);
				}
			}
		}
		String[] line1 = new String[vLine1.size()];
		String[] line0 = new String[vLine0.size()];
		int[] iAlignFlag = new int[line1.length];
		for (int i = 0; i < iAlignFlag.length; i++) {
			iAlignFlag[i] = ((Integer) align.elementAt(i)).intValue();
		}
		int[] colWidth = new int[vColWidths.size()];
		for (int i = 0; i < colWidth.length; i++) {
			colWidth[i] = ((Integer) vColWidths.elementAt(i)).intValue();
		}
		boolean[] numtoExcel = new boolean[datatypeVct.size()];
		for (int i = 0 ; i < numtoExcel.length; i ++) {
			numtoExcel[i] = datatypeVct.elementAt(i).booleanValue();
		}
		vLine1.copyInto(line1);
		vLine0.copyInto(line0);
		String[][] colNames = new String[2][];
		colNames[0] = line0;
		colNames[1] = line1;
		printEntry.setColNames(colNames);
		printEntry.setAlignFlag(iAlignFlag);

		//设置表头
		
		String[][] sTop = new String[2][1];
		sTop[0][0] = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000061")/*@res "期间"*/ +nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000313")+ getBalancebookUI().getlbPeriod().getText();
		sTop[1][0] = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000171")/*@res "币    种："*/ + getBalancebookUI().getlbCurrType().getText();
		printEntry.setTopStr(sTop);
		printEntry.setTopStrFixed(true);
		printEntry.setFixedRows(2);
		printEntry.setTopStrColRange(new int[] { line1.length - 1 });
		//设置表尾
		String[][] sBottom = new String[3][1];
		sBottom[0][0] =
			nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000172")/*@res "核算单位："*/+nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000313")
				+ getPk_orgName();

		sBottom[1][0] =
			nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000173")/*@res "制单人："*/ +nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000313")+ GlWorkBench.getLoginUserName();
		sBottom[2][0] = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000174")/*@res "打印时间："*/+nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000313") + DataFormatUtilGL.formatDateTime(new UFDateTime()).getValue();
		//sBottom[3][0] = "【用友软件】";
		printEntry.setBottomStr(sBottom);
		printEntry.setBottomStrAlign(new int[] { 0, 1 });
		printEntry.setBStrColRange(new int[] { line1.length - 1 });
		printEntry.setPageNumDisp(true);
		printEntry.setPageNumAlign(2);

		java.util.Vector vRange = new java.util.Vector();
		for (int i = 0; i < line0.length; i++) {
			if (line0[i].equals(line1[i])) {
				nc.ui.pub.print.datastruct.CellRange rangeTemp =
					new nc.ui.pub.print.datastruct.CellRange(0, i, 1, i);
				vRange.add(rangeTemp);
			}
		}
		int startCol = 0;
		int endCol = 0;
		boolean flag = false;
		while (startCol < line0.length) {
			flag = startCol == endCol ? false : true;
			if (endCol < line0.length && line0[startCol].equals(line0[endCol])) {
				if (startCol < line0.length - 1)
					endCol++;
				else
					startCol++;
			} else
				if (flag) {
					if ((endCol - startCol) != 1) {
						nc.ui.pub.print.datastruct.CellRange rangeTemp =
							new nc.ui.pub.print.datastruct.CellRange(0, startCol, 0, endCol - 1);
						vRange.add(rangeTemp);
					}
					startCol = endCol;
				}
		}
		nc.ui.pub.print.datastruct.CellRange[] range =
			new nc.ui.pub.print.datastruct.CellRange[vRange.size()];
		vRange.copyInto(range);
		printEntry.setCombinCellRange(range);

		//得到当前页的数据
		int iFixCount = 0;
		int iUNFixCount = 0;
		for (int i = 0; i < formats.length; i++) {
			if (formats[i].isVisiablity()) {
				if (i < getBalancebookUI().getFixModel().getColumnCount()) {
					iFixCount++;
				} else {
					iUNFixCount++;
				}
			}
		}
		Object[][] data = new Object[getBalancebookUI().getFixModel().getRowCount()][line1.length];
		int[] fixLocation = new int[iFixCount];
		int[] unFixLocation = new int[iUNFixCount];
		int iFixIndex = 0;
		int iUnFixIndex = 0;
		for (int i = 0; i < formats.length; i++) {
			if (formats[i].isVisiablity()) {
				if (i < getBalancebookUI().getFixModel().getColumnCount()) {
					fixLocation[iFixIndex] = i;
					iFixIndex++;
				} else {
					unFixLocation[iUnFixIndex] = i;
					iUnFixIndex++;
				}
			}
		}
		for (int i = 0; i < getBalancebookUI().getFixModel().getRowCount(); i++) {
			int iFIndex = 0;
			for (int j = 0; j < iFixCount; j++) {
				data[i][j] = getBalancebookUI().getFixModel().getValueAt(i, fixLocation[iFIndex]);
				iFIndex++;
			}
			int iUnFIndex = 0;
			for (int j = iFixCount; j < line1.length; j++) {
				data[i][j] =
					getBalancebookUI().getM_model().getValueAt(
						i,
						unFixLocation[iUnFIndex] - getBalancebookUI().getFixModel().getColumnCount());
				iUnFIndex++;
			}
		}
		printEntry.setData(data);
		printEntry.setFixedCols(fixCol);
		Log.getInstance(this.getClass().getName()).info("-------------" + fixCol + "------------------");
		printEntry.setColWidth(colWidth);
        //取消该方法调用UAP已经不支持,delete by Liyongru For v506 at 20090531
		printEntry.setColExcelAutoNumber(numtoExcel);

		printEntry.preview();
	}
	/**
	 * 返回 PrintDlg 特性值。
	 * @return nc.ui.gl.accbook.PrintDialog
	 */
	/* 警告：此方法将重新生成。 */
	public nc.ui.gl.accbook.PrintDialog getPrintDlg() {
		if (ivjPrintDlg == null) {
			try {
				ivjPrintDlg = new nc.ui.gl.accbook.PrintDialog(this);
				ivjPrintDlg.setName("PrintDlg");
				ivjPrintDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				// user code begin {1}
				ivjPrintDlg.addUIDialogListener(this);
				String pk_user = GlWorkBench.getLoginUser();
				GlPrintEntry printEntry = new GlPrintEntry(null, this);

				String pk_group = WorkbenchEnvironment.getInstance().getGroupVO().getPk_group();
				printEntry.setTemplateID(pk_group, GlNodeConst.GLNODE_SUBJASSBANLCE, pk_user, null,GlNodeConst.GLNODE_SUBJASSBANLCE,null);
				nc.vo.pub.print.PrintTempletmanageHeaderVO[] headvos = printEntry.getAllTemplates();
				nc.vo.gl.accbook.PrintCondVO condvo = new nc.vo.gl.accbook.PrintCondVO();
				condvo.setPk_acctingbook(getPk_acctingbook());
				condvo.setPrintModule(headvos);
				ivjPrintDlg.setPrintData(condvo);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPrintDlg;
	}
	
/**
 * BalancebookView 构造子注解。
 */
public BalancebookView() {
	super();
	initialize();
}

private String getPk_acctingbook() {
	return qryVO.getBaseAccountingbook();
}
//取得财物核算账簿对应报表组织的name
public String getPk_orgName() {
	FinanceOrgVO orgByPk_Accbook = AccountBookUtil.getOrgByPk_Accbook(getPk_acctingbook());
	if(orgByPk_Accbook != null)
		return orgByPk_Accbook.getName();
	return null;
}


/**
 * BalancebookView 构造子注解。
 */
public BalancebookView(ToftPanelView tempParentView) {
	super();
	setParentView(tempParentView);
	initialize();
}
/**
 * BalancebookView 构造子注解。
 * @param layout java.awt.LayoutManager
 */
public BalancebookView(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * BalancebookView 构造子注解。
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public BalancebookView(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * BalancebookView 构造子注解。
 * @param isDoubleBuffered boolean
 */
public BalancebookView(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
public void dialogClosed(UIDialogEvent e) {
	UiManager view=(UiManager)getIParent();
	if(e.getSource()==queryConditionDialog){
		if(e.m_Operation==UIDialogEvent.WINDOW_OK){
			queryConditionDialog.getResult();
			try{
				qryVO=normalPanel.getqryVO();
				qryedData = (BalanceBSVO[])getBalancebooksModel().dealQuery(qryVO);
				getBalancebookUI().setTableData(qryedData,qryVO);
				view.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021510","UPP20021510-000148")/*查询已结束*/);
				//resetFormat();
			}catch(Exception ex){
				Logger.error(ex.getMessage(), ex);
			}
			iState=BlncState.iStateQueryed;
		}
	}else if (e.getSource() == getPrintDlg()) {
		if (e.m_Operation == UIDialogEvent.WINDOW_OK) {
			try {
				nc.vo.gl.accbook.PrintCondVO printvo = new nc.vo.gl.accbook.PrintCondVO();
				getPrintDlg().setPrintData(printvo);
				printvo = getPrintDlg().getPrintData();
				if (printvo.isBlnPrintAsModule()) {
					print();
				} else {
					printAsItIs();
				}
			} catch (Exception err) {
				Log.getInstance(this.getClass().getName()).info(err.getMessage());
			}
		}
	}
}
/**
 *
 * 得到所有的数据项表达式数组
 * 也就是返回所有定义的数据项的表达式
 *
 */
public java.lang.String[] getAllDataItemExpress()
{

	return new String[] {
		"corp",
		"subjcode",
		"subjname",
		"currtype",
		"begindirection",
		"qtbegin",
		"begin",
		"fragbegin",
		"localbegin",
		"drquantity",
		"dr",
		"drfrag",
		"drlocal",
		"crquantity",
		"cr",
		"crfrag",
		"crlocal",
		"sumdrquantity",
		"sumdr",
		"sumdrfrag",
		"sumdrlocal",
		"sumcrquantity",
		"sumcr",
		"sumcrfrag",
		"sumcrlocal",
		"enddirection",
		"qtend",
		"end",
		"endfrag",
		"endlocal" };
}
public java.lang.String[] getAllDataItemNames() {
	return null;
}
/**
 * 返回 BalancebooksModel 特性值。
 * @return nc.ui.gl.balancebooks.BalancebooksModel
 */
/* 警告：此方法将重新生成。 */
private BalancebooksModel getBalancebooksModel() {
	if (ivjBalancebooksModel == null) {
		try {
			ivjBalancebooksModel = new nc.ui.gl.subjassbalancebooks.BalancebooksModel();
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBalancebooksModel;
}
/**
 * 返回 BalancebookUI 特性值。
 * @return nc.ui.gl.balancebooks.BalancebookUI
 */
/* 警告：此方法将重新生成。 */
public BalancebookUI getBalancebookUI() {
	if (ivjBalancebookUI == null) {
		try {
			ivjBalancebookUI = new nc.ui.gl.subjassbalancebooks.BalancebookUI();
			ivjBalancebookUI.setName("BalancebookUI");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBalancebookUI;
}
/**
 *
 * 返回依赖项的名称数组，该数据项长度只能为 1 或者 2
 * 返回 null : 		没有依赖
 * 长度 1 :			单项依赖
 * 长度 2 :			双向依赖
 *
 */
public java.lang.String[] getDependentItemExpressByExpress(java.lang.String itemName) {
	return null;
}
private QueryDialogPanel normalPanel = null;
public QueryConditionDLG getQueryConditionDialog() {
	if (this.queryConditionDialog == null) {
		this.queryConditionDialog = null;
		TemplateInfo tempinfo = new TemplateInfo();
		tempinfo.setPk_Org(GlWorkBench.getLoginGroup());
		tempinfo.setUserid(GlWorkBench.getLoginUser());
		tempinfo.setFunNode(GlNodeConst.GLNODE_SUBJASSBANLCE);
		queryConditionDialog = new QueryConditionDLG(this.getParent(),tempinfo) {
			@Override
			protected void onBtnOK() {
				if (!beforeClose())
					return;
				super.onBtnOK();
			}
		};
		normalPanel = new QueryDialogPanel(this);
		queryConditionDialog.setNormalPanel(normalPanel);
		queryConditionDialog.addUIDialogListener(this);
		queryConditionDialog.setResizable(true);
		queryConditionDialog.setVisibleNormalPanel(true);
		queryConditionDialog.setVisibleCandidatePanel(false);
		queryConditionDialog.setVisibleAdvancePanel(false);
		DisVisableClearBtn.hideClearBtn(queryConditionDialog);
		queryConditionDialog.setSize(800,650);
	}
	return queryConditionDialog;
}

private boolean beforeClose() {
	return ((QueryDialogPanel) queryConditionDialog.getNormalPanel()).bnOk_ActionPerformed();
}
public IParent getIParent()
{
	return m_parent;
}
public java.lang.String[] getItemValuesByExpress(java.lang.String itemExpress)
{
	String ret[] = null;
	boolean blnItemIntoIf = false;
	if (itemExpress.equals("headdatescope"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		String strYear = getBalancebooksModel().getQueryVO().getYear();
		String strPeriod = getBalancebooksModel().getQueryVO().getPeriod();
		String strEndPeriod = getBalancebooksModel().getQueryVO().getEndPeriod();
		ret[0] =nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000061")/*@res "期间"*/+"："+ strYear + "." + strPeriod +  "-"+ strYear + "."+ strEndPeriod ;
	}
	else if (itemExpress.equals("headcorp"))
	{
	    return new String[]{""};
	}
	else if (itemExpress.equals("corp_name"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		FinanceOrgVO orgByPk_Accbook = AccountBookUtil.getOrgByPk_Accbook(getPk_acctingbook());
		if(orgByPk_Accbook != null) {
			ret[0] = orgByPk_Accbook.getName();
		}
	}
	else if (itemExpress.equals("headglorgbook"))
	{
		String[] strPkorgbooks = this.qryVO.getpk_accountingbook();
		String strorgName = "";
		AccountingBookVO[] orgbookVOs = new AccountingBookVO[strPkorgbooks.length];
		try {
			for (int i = 0; i < strPkorgbooks.length; i++) {
			    orgbookVOs[i] = AccountBookUtil.getAccountingBookVOByPrimaryKey(strPkorgbooks[i]);
				strorgName = strorgName + "," + orgbookVOs[i].getName();
			}
		} catch (Exception ee) {
			handleException(ee);
		}
		strorgName = strorgName.substring(1);
		return new String[]{strorgName};
	}
	else if (itemExpress.equals("headcurrtype"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		ret[0] = getBalancebooksModel().getQueryVO().getCurrTypeName();
	}
	else if (itemExpress.equals("initcreditloctotal"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		ret[0] = gettotal(BalanceBSKey.K_InitCreditLocAmount);
	}
	else if (itemExpress.equals("initdebitloctotal"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		ret[0] = gettotal(BalanceBSKey.K_InitDebitLocAmount);
	}
	else if (itemExpress.equals("endcreditloctotal"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		ret[0] = gettotal(BalanceBSKey.K_EndCreditLocAmount);
	}
	else if (itemExpress.equals("enddebitloctotal"))
	{
		blnItemIntoIf = true;
		ret = new String[1];
		ret[0] = gettotal(BalanceBSKey.K_EndDebitLocAmount);
	}
	else
	{
		BalanceBSVO[] tempVos = getBalancebooksModel().getData();
		ret = new String[tempVos.length];
		for (int i = 0; i < tempVos.length; i++)
		{
			try
			{
				int iKey = 0;
				if (itemExpress.equals("glorgbookname"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_FINANCEORGNAME;
				}
				if (itemExpress.equals("corp"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CorpName;
				}
				else if (itemExpress.equals("subjcode"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_AccCode;
				}
				else if (itemExpress.equals("subjname"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_AccName;
				}
				else if (itemExpress.equals("currtype"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CurType;
				}
				else if (itemExpress.equals("begindirection"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitOrient;
				}
				else if (itemExpress.equals("qtbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitQuant;
				}
				else if (itemExpress.equals("begin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitAmount;
				}
				else if (itemExpress.equals("localbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitLocAmount;
				}
				else if (itemExpress.equals("drquantity"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitQuant;
				}
				else if (itemExpress.equals("dr"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitAmount;
				}
				else if (itemExpress.equals("drlocal"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitLocAmount;
				}
				else if (itemExpress.equals("crquantity"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditQuant;
				}
				else if (itemExpress.equals("cr"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditAmount;
				}
				else if (itemExpress.equals("crlocal"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditLocAmount;
				}
				else if (itemExpress.equals("sumdrquantity"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitAccumQuant;
				}
				else if (itemExpress.equals("sumdr"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitAccumAmount;
				}
				else if (itemExpress.equals("sumdrlocal"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_DebitAccumLocAmount;
				}
				else if (itemExpress.equals("sumcrquantity"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditAccumQuant;
				}
				else if (itemExpress.equals("sumcr"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditAccumAmount;
				}
				else if (itemExpress.equals("sumlocal"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_CreditAccumLocAmount;
				}
				else if (itemExpress.equals("enddirection"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndOrient;
				}
				else if (itemExpress.equals("qtend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndQuant;
				}
				else if (itemExpress.equals("end"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndAmount;
				}
				else if (itemExpress.equals("endlocal"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndLocAmount;
				}
				/////////期初借方
				else if (itemExpress.equals("drqtbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitDebitQuant;
				}
				else if (itemExpress.equals("drbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitDebitAmount;
				}
				else if (itemExpress.equals("drlocalbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitDebitLocAmount;
				}

				else if (itemExpress.equals("crqtbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitCreditQuant;
				}
				else if (itemExpress.equals("crbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitCreditAmount;
				}
				else if (itemExpress.equals("crlocalbegin"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_InitCreditLocAmount;
				}
				//////期末借方
				else if (itemExpress.equals("drqtend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndDebitQuant;
				}
				else if (itemExpress.equals("drend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndDebitAmount;
				}
				else if (itemExpress.equals("drlocalend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndDebitLocAmount;
				}

				else if (itemExpress.equals("crqtend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndCreditQuant;
				}
				else if (itemExpress.equals("crend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndCreditAmount;
				}
				else if (itemExpress.equals("crlocalend"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_EndCreditLocAmount;
				}
				else if(itemExpress.equals("assstring"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_ASSVOS;
				}
				else if (itemExpress.equals("pk_unit"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_PKUNIT_V;
				}
				Object objTemp = getBalancebookUI().m_model.getValue(i, iKey);
				//add by weiningc  633适配至65 _科目辅助余额表支持辅助核算分列显示及打印及上级科目显示编码 20171016 start
				if(itemExpress.equals("assid"))
				{
					blnItemIntoIf = true;
					iKey = BalanceBSKey.K_ASSID;
					objTemp = getBalancebookUI().m_model.data[i].getValue(iKey);
					if(objTemp.toString().contains("000000just_for_sumtool"))
						objTemp = null;
					//ret[i]="辅助核算主键";
				}
				//add by weiningc  633适配至65 _科目辅助余额表支持辅助核算分列显示及打印及上级科目显示编码 20171016 end
				
				if (itemExpress.equals("currtype"))
				{
					blnItemIntoIf = true;
					if(objTemp == null)
						objTemp = this.qryVO.getCurrTypeName();
				}else if (itemExpress.equals("pk_unit")&&objTemp!=null)
				{
					String pk_unit = (String)objTemp;
					OrgVO org = BusiUnitDataCache.getOrgByPk(pk_unit);
					if(null != org)
						objTemp = org.getName();
				}
				ret[i] = objTemp==null?"":objTemp.toString();
			}
			catch (Exception e)
			{
				ret[i] = "";
			}
		}
	}
	if (!blnItemIntoIf) {
		ret = null;
	}
	return ret;
}
/*
 *  返回该数据源对应的节点编码
 */
public java.lang.String getModuleName() {
	return nc.vo.glcom.nodecode.GlNodeConst.GLNODE_BALANCEBOOK;
}

private String gettotal(int key) {
	BalanceBSVO[] vos = getBalancebookUI().m_model.data;
	int len = getBalancebookUI().m_model.data.length;
	UFDouble dblRtn =UFDouble.ZERO_DBL;
	Object objTemp = null;
	try {
		for (int i = 0; i < len; i++) {
			String strCode1 = vos[i].getValue(BalanceBSKey.K_AccCode).toString();
			if (strCode1.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000075")/*@res "合计"*/) >= 0
				|| strCode1.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000118")/*@res "公司累计"*/) >= 0
				|| strCode1.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000119")/*@res "币种累计"*/) >= 0
				|| strCode1.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000115")/*@res "总计"*/) >= 0) {
				vos[i].setValue(BalanceBSKey.K_Remark, "dddd");
				continue;
			} else {
				for (int j = 0; j < len; j++) {
					String strCode2 = vos[j].getValue(BalanceBSKey.K_AccCode).toString();
					if ((!strCode2.equals(strCode1)) && strCode1.startsWith(strCode2)) {
						vos[i].setValue(BalanceBSKey.K_Remark, "dddd");
						continue;
					}
				}
			}
		}
		for (int i = 0; i < len; i++) {
			if (vos[i].getValue(BalanceBSKey.K_Remark) == null) {
				dblRtn =dblRtn.add(getBalancebookUI().m_model.data[i].getValue(key) == null? 0.0
							: new Double(getBalancebookUI().m_model.data[i].getValue(key).toString()).doubleValue());
			}
		}
		for (int i = 0; i < len; i++) {
			vos[i].setValue(BalanceBSKey.K_Remark, null);
		}

		nc.ui.glcom.numbertool.GlCurrAmountFormat format =
			new nc.ui.glcom.numbertool.GlCurrAmountFormat();
		objTemp = format.formatAmount(dblRtn, "00010000000000000001");
	} catch (Exception e) {
		Logger.error(e.getMessage(), e);
	}
	return objTemp.toString();
}
/**
 * 每当部件抛出异常时被调用
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
	// Log.getInstance(this.getClass().getName()).info("--------- 未捕捉到的异常 ---------");
	// nc.bs.logging.Logger.error(exception.getMessage(), exception);
}
/**
 * 多主体列示
 * @param exception java.lang.Throwable
 */
private void onMultiOrg() {

	try{
		UiManager view=(UiManager)getIParent();
		view.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023005","UPT20023005-000050")/*联查*/
				+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002GL502","UPP2002GL502-000203")/*@res "多单位列示"*/);
		int selRow = getBalancebookUI().getSelectedRow();
		if(selRow<0||getBalancebooksModel().m_dataVos==null||getBalancebooksModel().m_dataVos.length==0){
			return;
		}
		BalanceBSVO voData = (BalanceBSVO) getBalancebooksModel().getVO(selRow);
		String strCode = voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_AccCode).toString();
		Pattern pattern = Pattern.compile("[0-9]*");
		if(!pattern.matcher(strCode).matches()){
			return;
		}
		String pk_accusbj =(String)
			voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_PKACCASOA);
		GlQueryVO tempQryVO=(GlQueryVO)qryVO.clone();

		
//		AccountGLSimpleVO[] tempSubjs = AccountUtilGL.querySimpleAccountVOs(
//				qryVO.getpk_accountingbook(),strCode,strCode,"1","1",true,tempQryVO.getFormatVO().isBlSubjChooserShowOutsubj()
//				,"",qryVO.getSubjVersion());
		
//		if(tempSubjs!=null){
//			String[] pk_accusbjs = new String[tempSubjs.length];
//			String[] codes = new String[tempSubjs.length];
//			for(int i=0;i<pk_accusbjs.length;i++){
//				pk_accusbjs[i] = tempSubjs[i].getPk_accasoa();
//				codes[i] =  tempSubjs[i].getCode();
//			}
//			tempQryVO.setPk_account(pk_accusbjs);
//			tempQryVO.setAccountCodes(codes);
//		}else{
			tempQryVO.setPk_account(new String[]{pk_accusbj});
			tempQryVO.setAccountCodes(new String[]{strCode});
//		}
		tempQryVO.setQueryType("listOrg");//多主体列示
		tempQryVO.setAssVos((AssVO[])voData.getValue(BalanceBSKey.K_ASSVOS));
		tempQryVO.getFormatVO().setMultiCorpCombine(false);
		BalanceBSVO[] objData = (BalanceBSVO[])getBalancebooksModel().dealQuery(tempQryVO);

		getBalancebookUI().setTableData(objData,tempQryVO);
	}catch(Exception ex){
		Logger.error(ex.getMessage(), ex);
	}
	iState=BlncState.iStateMultiOrgQueryed;
}

/**
 * 多主体列示
 * @param exception java.lang.Throwable
 */
private void onReturn() {

	getBalancebooksModel().m_dataVos=qryedData;
	getBalancebooksModel().m_qryVO=qryVO;
	//getBalancebooksModel().m_qryVO=null;
	getBalancebookUI().setTableData(qryedData,qryVO);
	iState=BlncState.iStateQueryed;
	setButtonState();

}
/**
 * 初始化类。
 */
/* 警告：此方法将重新生成。 */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("BalancebookView");
		setLayout(new java.awt.BorderLayout());
		add(getBalancebookUI(), BorderLayout.CENTER);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	getBalancebookUI().setChartModel(getBalancebooksModel());
	setButtonState();
	// user code end
}
/*
 * 返回该数据项是否为数字项
 * 数字项可参与运算；非数字项只作为字符串常量
 * 如“数量”为数字项、“存货编码”为非数字项
 */
public boolean isNumber(java.lang.String itemExpress) {
	if (itemExpress.equals("qtbegin") || itemExpress.equals("begin") || itemExpress.equals("fragbegin") || itemExpress.equals("localbegin") || itemExpress.equals("drquantity") || itemExpress.equals("dr") || itemExpress.equals("drfrag") || itemExpress.equals("drlocal")
            || itemExpress.equals("crquantity") || itemExpress.equals("cr") || itemExpress.equals("crfrag") || itemExpress.equals("crlocal") || itemExpress.equals("sumdrquantity") || itemExpress.equals("sumdr") || itemExpress.equals("sumdrfrag") || itemExpress.equals("sumdrlocal")
            || itemExpress.equals("sumcrquantity") || itemExpress.equals("sumcr") || itemExpress.equals("sumfrag") || itemExpress.equals("sumlocal") || itemExpress.equals("qtend") || itemExpress.equals("end") || itemExpress.equals("endfrag") || itemExpress.equals("endlocal")
            || itemExpress.equals("drqtbegin") || itemExpress.equals("drbegin") || itemExpress.equals("drfragbegin") || itemExpress.equals("drlocalbegin") || itemExpress.equals("crqtbegin") || itemExpress.equals("crbegin") || itemExpress.equals("crfragbegin")
            || itemExpress.equals("crlocalbegin") || itemExpress.equals("drqtend") || itemExpress.equals("drend") || itemExpress.equals("drfragend") || itemExpress.equals("drlocalend") || itemExpress.equals("crqtend") || itemExpress.equals("crend") || itemExpress.equals("crfragend")
            || itemExpress.equals("crlocalend")) {
        return true;

    } else {
        return false;

    }
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-10 9:56:57)
 */
private void print()
{
	GlPrintEntry pEntry = new GlPrintEntry(getPrintDlg(), this);
	String pk_user = GlWorkBench.getLoginUser();
	String pk_group = WorkbenchEnvironment.getInstance().getGroupVO().getPk_group();
	pEntry.setTemplateID(pk_group, GlNodeConst.GLNODE_SUBJASSBANLCE, pk_user, null,GlNodeConst.GLNODE_SUBJASSBANLCE,null);
	
	nc.vo.pub.print.PrintTempletmanageHeaderVO[] templateHeaderVOs = pEntry.getAllTemplates();
	String tempName = (String) getPrintDlg().getModuleCombo().getItemAt(getPrintDlg().getModuleCombo().getSelectedIndex());
	String m_templateID = null;
	String nodekey = null;
	for (int i = 0; i < templateHeaderVOs.length; i++) {
		if (templateHeaderVOs[i].getVtemplatename().equals(tempName)) {
			m_templateID = templateHeaderVOs[i].getCtemplateid();
			nodekey = ((GlPrintTempletmanageHeadVO)templateHeaderVOs[i]).getNodekey();
			break;
		}
	}
	pEntry.setSelected(m_templateID,nodekey);
	if(getPrintDlg().getPrintData().isPrintView()){
		pEntry.preview();
	}else{
		pEntry.print();
	}
}
public void setIParent(IParent parent)
{
	m_parent = parent;
}
public void setButtonState()
{
	if (getParentView() == null)
		return;
	ToftPanelView parentView = (ToftPanelView) getParentView();

    if(iState==BlncState.iStateInit)
    {
    	for (int i = 0; parentView.getButtons() != null
		&& i < parentView.getButtons().length; i++) {
	ButtonObject btn = parentView.getButtons()[i];
	String tag = btn.getTag();
	if (tag.equals(ButtonKey.bnQRY))
		btn.setEnabled(true);

	else
	{
		btn.setEnabled(false);
	}
    	}
}
    else if(iState==BlncState.iStateQueryed)
    {

    	for (int i = 0; parentView.getButtons() != null
		&& i < parentView.getButtons().length; i++) {
	ButtonObject btn = parentView.getButtons()[i];
	String tag = btn.getTag();
	if (!tag.equals(ButtonKey.bnListMultiOrg))
	{   if(!tag.equals(ButtonKey.bnReturn))
		btn.setEnabled(true);
	else
	{
		btn.setEnabled(false);
	}
	}
	else
	{   if(!qryVO.getFormatVO().isMultiCorpCombine())
	{
	 	btn.setEnabled(false);
	}
	else
	{
		if(!tag.equals(ButtonKey.bnReturn))
		btn.setEnabled(true);
		else
			btn.setEnabled(false);

	}
	}
    	}

    }
    else if(iState==BlncState.iStateMultiOrgQueryed)
    {

    	for (int i = 0; parentView.getButtons() != null
		&& i < parentView.getButtons().length; i++) {
	ButtonObject btn = parentView.getButtons()[i];
	String tag = btn.getTag();
	if (!tag.equals(ButtonKey.bnListMultiOrg))
		btn.setEnabled(true);
	else
	{
		btn.setEnabled(false);
	}
    	}

    }
    	parentView.updateButtons();
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-9-28 21:58:47)
 * @param i int
 */
void tackleBnsEvent(String name) throws Exception
{
	UiManager view=(UiManager)getIParent();
	int selRow = -1;
	GlQueryVO qryVO = getBalancebooksModel().getQueryVO();
	GlQueryVO queryVO = getBalancebooksModel().getQueryVO();
	if(ButtonKey.bnQRY.equals(name)){
			//getDlg().showModal();
		getQueryConditionDialog().showModal();
	}else if(ButtonKey.bnListMultiOrg.equals(name)){
        onMultiOrg();
	}else if(ButtonKey.bnReturn.equals(name)){
		Map<String, JPanel> map = new HashMap<String, JPanel>();
		map.put(GlNodeConst.GLNODE_SUBJASSBANLCE, getBalancebookUI());
		try {
			JTableTool.INSTANCE.saveDynamicColumnWidth(map);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	    onReturn();
	}else if(ButtonKey.bnPrint.equals(name)){
			getPrintDlg().showme(GlNodeConst.GLNODE_SUBJASSBANLCE);
	}else if(ButtonKey.bnDetail.equals(name)){
		view.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023005","UPT20023005-000050")/*联查*/
				+nc.ui.ml.NCLangRes.getInstance().getStrByID("20023005","UPT20023005-000042")/*明细*/);
		selRow = getBalancebookUI().getSelectedRow();
		if(selRow<0||getBalancebooksModel().m_dataVos==null||getBalancebooksModel().m_dataVos.length==0){
			return;
		}
		BalanceBSVO voData = (BalanceBSVO) getBalancebooksModel().getVO(selRow);
		String strCode =
			voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_AccCode).toString();
		if (strCode.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000179")/*@res "累计"*/) >= 0
			|| strCode.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000115")/*@res "总计"*/) >= 0
			|| strCode.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000263")/*@res "科目合计"*/)>=0
			|| strCode.indexOf(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-001124")  /*@res "科目汇总" */) >= 0)
			return;
		nc.ui.glpub.IUiPanel panel =(nc.ui.glpub.IUiPanel) m_parent.showNext("nc.ui.gl.detail.DetailToftPanel");
		queryVO = (nc.vo.glcom.balance.GlQueryVO) queryVO.clone();

		if(!queryVO.isMultiCorpCombine()&&queryVO.getpk_accountingbook().length>1)
		{
		    queryVO.setpk_accountingbook(new String[]{(String)voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_PK_ACCOUNTINGBOOK)});
		}

		String[] startPeriod=GLPubProxy.getRemoteGlPara().getStartPeriod(qryVO.getBaseAccountingbook(), "2002");
		if (startPeriod[0].compareTo(queryVO.getYear()) == 0 && startPeriod[1].compareTo(queryVO.getPeriod()) > 0) {
			queryVO.setPeriod(startPeriod[1]);
		}
		queryVO.setEndYear(queryVO.getYear());
		queryVO.setPk_account(new String[] {(String) voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_PKACCASOA)});
		queryVO.setAccountCode(	new String[] {(String) voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_AccCode)});
		queryVO.getFormatVO().setMultiOrgCombine(queryVO.isMultiCorpCombine());
		String pk_loginGlorgbook = GlWorkBench.getDefaultMainOrg();
		String newBase = nc.vo.glcom.tools.BaseCorpChooser.getPk_BasCorp(queryVO.getpk_accountingbook(), pk_loginGlorgbook);
		queryVO.setBaseAccountingbook(newBase);
		queryVO.getFormatVO().setCombineOppositeSubj(true);

		queryVO.setNeedDetailFreeItem(true);
		queryVO.setNeedSubjFreeItem(true);
		//modify by zsh 2005.5.30/11:55
		if(queryVO.getpk_accountingbook().length>1)
		    queryVO.setBooksType(4);
		
		String pk_unit = (String)voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_PKUNIT);
		if(!StrTools.isEmptyStr(pk_unit)){
			queryVO.setPk_unis(new String[]{pk_unit});
		}

		//科目辅助余额表的明细需要加上辅助核算查询条件
		AssVO[] assvos = (AssVO[])voData.getValue(nc.vo.gl.balancebooks.BalanceBSKey.K_ASSVOS);
		if(assvos != null) //没有辅助核算的科目不用加
		{
			queryVO.setAssVos(assvos);
			queryVO.setUserData("subjass");
		}else{
			queryVO.setAssIDs(new String[]{StrTools.NULL}); //不带辅助核算查辅助核算为~的。
		}
		queryVO.getFormatVO().setOnlySelfSubjCode(true);
		panel.invoke(queryVO, "SetQueryVO");
	}
	setButtonState();
}
/**
 * @return the parentView
 */
public ToftPanelView getParentView() {
	return parentView;
}
/**
 * @param parentView the parentView to set
 */
public void setParentView(ToftPanelView parentView) {
	this.parentView = parentView;
}
}