package nc.ui.gl.subjassbalancebooks;

/**
 * 此处插入类型说明。
 * 创建日期：(01-8-13 16:18:24)
 * @author：魏小炯
 */
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.gl.account.glconst.CurrTypeConst;
import nc.itf.glcom.para.GLParaAccessor;
import nc.ui.gl.accbook.CopyTwoTableUtil;
import nc.ui.gl.accbook.JTableTool;
import nc.ui.gl.accbook.TwoTableListSelectionListener;
import nc.ui.gl.view.LayoutPanel;
import nc.ui.glpub.IChartModel;
import nc.ui.pub.beans.UILabel;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.Currency;
import nc.vo.gl.accbook.AlignmentConst;
import nc.vo.gl.accbook.ColFormatVO;
import nc.vo.gl.balancebooks.BalanceBSKey;
import nc.vo.gl.balancebooks.BalanceBSVO;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.glcom.nodecode.GlNodeConst;
import nc.vo.org.AccountingBookVO;
import nc.vo.pub.BusinessException;

import org.apache.commons.lang.StringUtils;
@SuppressWarnings({"serial","deprecation"})
public class BalancebookUI extends nc.ui.pub.beans.UIPanel implements java.awt.event.ItemListener, javax.swing.event.ListSelectionListener {

	class MyMouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
		public void mouseDragged(java.awt.event.MouseEvent e) {
			int resizeColWidth = -1;
			int colModelIndex=-1;
		    int fixSize=fixModel.getVo().getFixedColSize();
			if (e.getSource() == fixTable.getTableHeader()) {
				if (fixTable.getTableHeader().getResizingColumn() != null)
				fixTable.setPreferredScrollableViewportSize(
					new java.awt.Dimension(
						fixTable.getColumnModel().getTotalColumnWidth(),
						fixTable.getHeight()));
     			 resizeColWidth = fixTable.getTableHeader().getResizingColumn().getWidth();
	    		 colModelIndex =fixTable.getTableHeader().getResizingColumn().getModelIndex();
	    		 fixModel.getVo().getColFormatVOs()[colModelIndex].setColWidth(resizeColWidth);
			}
			else
			{
				if(getMyTablePane().getTable()!=null 
						&& getMyTablePane().getTable().getTableHeader()!=null 
						&&getMyTablePane().getTable().getTableHeader().getResizingColumn()!=null
						)
				{
				resizeColWidth = getMyTablePane().getTable().getTableHeader().getResizingColumn().getWidth();
        		colModelIndex=getMyTablePane().getTable().getTableHeader().getResizingColumn().getModelIndex();
				}
				fixModel.getVo().getColFormatVOs()[fixSize+colModelIndex].setColWidth(resizeColWidth);
				
			}
		}
	}
	private nc.ui.pub.beans.UILabel ivjbillFormat = null;
	private nc.ui.pub.beans.UILabel ivjUILabel2 = null;
	private nc.ui.pub.beans.UITablePane ivjMyTablePane = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	nc.ui.pub.beans.UITable fixTable = new nc.ui.pub.beans.UITable();
	BalanceFixTableModel fixModel = new BalanceFixTableModel();
	protected int[] commonVis = new int[] {};//BalanceBSKey.K_AccCode, BalanceBSKey.K_AccName};
	protected int corpVis =BalanceBSKey.K_FINANCEORGNAME;
	protected int[] numberVis =
		new int[] {
			BalanceBSKey.K_InitQuant,
			BalanceBSKey.K_DebitQuant,
			BalanceBSKey.K_CreditQuant,
			BalanceBSKey.K_DebitAccumQuant,
			BalanceBSKey.K_CreditAccumQuant,
			BalanceBSKey.K_EndQuant};
	protected int currtypeVis = BalanceBSKey.K_CurType;
	protected int[] OriCurrTypeVis =
		new int[]{
			BalanceBSKey.K_InitAmount,
			BalanceBSKey.K_DebitAmount,
			BalanceBSKey.K_CreditAmount,
			BalanceBSKey.K_DebitAccumAmount,
			BalanceBSKey.K_CreditAccumAmount,
			BalanceBSKey.K_EndAmount};
	protected int[] LocCurrTypeVis =
		new int[]{
			BalanceBSKey.K_InitLocAmount,
			BalanceBSKey.K_DebitLocAmount,
			BalanceBSKey.K_CreditLocAmount,
			BalanceBSKey.K_DebitAccumLocAmount,
			BalanceBSKey.K_CreditAccumLocAmount,
			BalanceBSKey.K_EndLocAmount};
	protected int[] numberVis2 =
		new int[] {
			BalanceBSKey.K_InitDebitQuant,
			BalanceBSKey.K_InitCreditQuant,
			BalanceBSKey.K_DebitQuant,
			BalanceBSKey.K_CreditQuant,
			BalanceBSKey.K_DebitAccumQuant,
			BalanceBSKey.K_CreditAccumQuant,
			BalanceBSKey.K_EndDebitQuant,
			BalanceBSKey.K_EndCreditQuant};
	protected int[] OriCurrTypeVis2 =
		new int[]{
			BalanceBSKey.K_InitDebitAmount,
			BalanceBSKey.K_InitCreditAmount,
			BalanceBSKey.K_DebitAmount,
			BalanceBSKey.K_CreditAmount,
			BalanceBSKey.K_DebitAccumAmount,
			BalanceBSKey.K_CreditAccumAmount,
			BalanceBSKey.K_EndDebitAmount,
			BalanceBSKey.K_EndCreditAmount};
	protected int[] LocCurrTypeVis2 =
		new int[]{
			BalanceBSKey.K_InitDebitLocAmount,
			BalanceBSKey.K_InitCreditLocAmount,
			BalanceBSKey.K_DebitLocAmount,
			BalanceBSKey.K_CreditLocAmount,
			BalanceBSKey.K_DebitAccumLocAmount,
			BalanceBSKey.K_CreditAccumLocAmount,
			BalanceBSKey.K_EndDebitLocAmount,
			BalanceBSKey.K_EndCreditLocAmount};
	protected int[] AuxCurrTypeVis =
		new int[]{
			BalanceBSKey.K_InitAuxAmount,
			BalanceBSKey.K_DebitAuxAmount,
			BalanceBSKey.K_CreditAuxAmount,
			BalanceBSKey.K_DebitAccumAuxAmount,
			BalanceBSKey.K_CreditAccumAuxAmount,
			BalanceBSKey.K_EndAuxAmount
		};
	protected int[] AuxCurrTypeVis2 =
		new int[]{
			BalanceBSKey.K_InitDebitAuxAmount,
			BalanceBSKey.K_InitCreditAuxAmount,
			BalanceBSKey.K_DebitAuxAmount,
			BalanceBSKey.K_CreditAuxAmount,
			BalanceBSKey.K_DebitAccumAuxAmount,
			BalanceBSKey.K_CreditAccumAuxAmount,
			BalanceBSKey.K_EndDebitAuxAmount,
			BalanceBSKey.K_EndCreditAuxAmount
		};
	protected  nc.ui.gl.accbook.BillFormatVO format;
	private nc.ui.pub.beans.UIComboBox ivjcbFormat = null;
	private nc.ui.pub.beans.UILabel ivjlbCurrType = null;
	private nc.ui.pub.beans.UILabel ivjlbPeriod = null;
	private nc.ui.pub.beans.UILabel ivjUILabel3 = null;
	private IChartModel m_charModel;
	public BalanceTableModel m_model;
	private LayoutPanel ivjHeadPanel = null;
	private UILabel UILabel = null;
	private UILabel UILabelOrgNameAndCode = null;
class IvjEventHandler implements java.awt.event.ItemListener {
		public void itemStateChanged(java.awt.event.ItemEvent e) {
			if (e.getSource() == BalancebookUI.this.getcbFormat())
				connEtoC1(e);
		};
	};
/**
 * TricolAccbookUI 构造子注解。
 */
public BalancebookUI() {
	super();
	initialize();
}
/**
 * TricolAccbookUI 构造子注解。
 * @param p0 java.awt.LayoutManager
 */
public BalancebookUI(java.awt.LayoutManager p0) {
	
	super(p0);
}
/**
 * TricolAccbookUI 构造子注解。
 * @param p0 java.awt.LayoutManager
 * @param p1 boolean
 */
public BalancebookUI(java.awt.LayoutManager p0, boolean p1) {
	super(p0, p1);
}
/**
 * TricolAccbookUI 构造子注解。
 * @param p0 boolean
 */
public BalancebookUI(boolean p0) {
	super(p0);
}
/**
 * 此处插入方法说明。
 * 创建日期：(01-8-28 16:26:32)
 * @param arg1 java.awt.event.ItemEvent
 */
private void billType_ItemStateChanged(java.awt.event.ItemEvent arg1) {
	itemStateChanged(arg1);
}
/**
 * connEtoC1:  (billType.item.itemStateChanged(java.awt.event.ItemEvent) --> BalancebookUI.billType_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* 警告：此方法将重新生成。 */
private void connEtoC1(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.billType_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * 返回 billFormat 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
protected nc.ui.pub.beans.UILabel getbillFormat() {
	if (ivjbillFormat == null) {
		try {
			ivjbillFormat = new nc.ui.pub.beans.UILabel();
			ivjbillFormat.setName("billFormat");
			ivjbillFormat.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000530")/*@res "账簿格式："*/);
			ivjbillFormat.setBounds(488, 32, 62, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjbillFormat;
}
/**
 * 返回 billType 特性值。
 * @return nc.ui.pub.beans.UIComboBox
 */
/* 警告：此方法将重新生成。 */
protected nc.ui.pub.beans.UIComboBox getcbFormat() {
	if (ivjcbFormat == null) {
		try {
			ivjcbFormat = new nc.ui.pub.beans.UIComboBox();
			ivjcbFormat.setName("cbFormat");
			ivjcbFormat.setBounds(558, 32, 100, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjcbFormat;
}
	public BalanceFixTableModel getFixModel() {
		return fixModel;
	}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-2 9:51:09)
 * @return nc.ui.gl.accbook.BillFormatVO
 */
public nc.ui.gl.accbook.BillFormatVO getFormat() {
	if(format == null)
		format = new nc.ui.gl.accbook.BillFormatVO();
	return format;
}
/**
 * 返回 HeadPanel 特性值。
 * @return nc.ui.pub.beans.UIPanel
 */
/* 警告：此方法将重新生成。 */
private LayoutPanel getHeadPanel() {
	if (ivjHeadPanel == null) {
		try {
			UILabelOrgNameAndCode = new UILabel();
			UILabelOrgNameAndCode.setText("");
			UILabelOrgNameAndCode.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			UILabel = new UILabel();
			UILabel.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000285")/*@res "主体账簿："*/);
			ivjHeadPanel = new LayoutPanel(2, 4, LayoutPanel.LEFT);
			ivjHeadPanel.setName("HeadPanel");
			ivjHeadPanel.setLableMinumWidth(0);
			ivjHeadPanel.add(1, 1, getUILabel2());
			ivjHeadPanel.add(1, 3, getUILabel3());
			ivjHeadPanel.add(2, 1, UILabel);
			ivjHeadPanel.add(2, 3, getbillFormat());
			
			ivjHeadPanel.setLableMinumWidth(LayoutPanel.PREFERRED_WIDTH);
			ivjHeadPanel.add(1, 2, LayoutPanel.PREFERRED_INSETS_H, getlbPeriod());
			ivjHeadPanel.add(1, 4, LayoutPanel.PREFERRED_INSETS_H, getlbCurrType());
			ivjHeadPanel.add(2, 2, LayoutPanel.PREFERRED_INSETS_H, UILabelOrgNameAndCode);
			ivjHeadPanel.add(2, 4, LayoutPanel.PREFERRED_INSETS_H, getcbFormat());
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjHeadPanel;
}
/**
 * 返回 cashType 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
public nc.ui.pub.beans.UILabel getlbCurrType() {
	if (ivjlbCurrType == null) {
		try {
			ivjlbCurrType = new nc.ui.pub.beans.UILabel();
			ivjlbCurrType.setName("lbCurrType");
			ivjlbCurrType.setBorder(new javax.swing.border.EtchedBorder());
			ivjlbCurrType.setText("");
			ivjlbCurrType.setBounds(558, 6, 100, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjlbCurrType;
}
/**
 * 返回 time 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
public nc.ui.pub.beans.UILabel getlbPeriod() {
	if (ivjlbPeriod == null) {
		try {
			ivjlbPeriod = new nc.ui.pub.beans.UILabel();
			ivjlbPeriod.setName("lbPeriod");
			ivjlbPeriod.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			ivjlbPeriod.setText("");
			ivjlbPeriod.setBounds(82, 6, 333, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjlbPeriod;
}
	/**
	 * @return 返回 m_model。
	 */
	public BalanceTableModel getM_model() {
		return m_model;
	}
/**
 * 返回 MyTablePane 特性值。
 * @return nc.ui.pub.beans.UITablePane
 */
/* 警告：此方法将重新生成。 */
public nc.ui.pub.beans.UITablePane getMyTablePane() {
	if (ivjMyTablePane == null) {
		try {
			ivjMyTablePane = new nc.ui.pub.beans.UITablePane();
			ivjMyTablePane.setName("MyTablePane");
			// user code begin {1}
			ivjMyTablePane.setAutoscrolls(true);
			//ivjMyTablePane.set
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMyTablePane;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-25 13:18:17)
 * @return int
 */
public int getSelectedRow() {
	return getMyTablePane().getTable().getSelectedRow();
}

/**
 * 返回 UILabel2 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
private nc.ui.pub.beans.UILabel getUILabel2() {
	if (ivjUILabel2 == null) {
		try {
			ivjUILabel2 = new nc.ui.pub.beans.UILabel();
			ivjUILabel2.setName("UILabel2");
			ivjUILabel2.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000478")/*@res "期  间："*/);
			ivjUILabel2.setBounds(14, 7, 46, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUILabel2;
}
/**
 * 返回 currType 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
private nc.ui.pub.beans.UILabel getUILabel3() {
	if (ivjUILabel3 == null) {
		try {
			ivjUILabel3 = new nc.ui.pub.beans.UILabel();
			ivjUILabel3.setName("UILabel3");
			ivjUILabel3.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000282")/*@res "币  种："*/);
			ivjUILabel3.setBounds(488, 6, 43, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUILabel3;
}
/**
 * 每当部件抛出异常时被调用
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
	 Log.getInstance(this.getClass().getName()).info("--------- 未捕捉到的异常 ---------");
	 nc.bs.logging.Logger.error(exception.getMessage(), exception);
}
private ColFormatVO[] initColFormatVO() {
	ColFormatVO[] colFormats=new ColFormatVO[48];
	int i = 0;

	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_AccCode);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000010")/*@res "科目编码"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_AccName);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000011")/*@res "科目名称"*/);
	colFormats[i].setColWidth(100);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;	
	
	//科目辅助余额表显示辅助核算列
	colFormats[i] = new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_ASSVOS);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0003936")/*@res "辅助核算"*/);
	colFormats[i].setColWidth(100);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_FINANCEORGNAME);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000131")/*@res "主体帐簿名称"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setMultiHeadStr(null);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_PKUNIT);
	colFormats[i].setColName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UCMD1-000396")/*@res "业务单元"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CurType);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000012")/*@res "币种"*/);
	colFormats[i].setColWidth(40);
	colFormats[i].setFrozen(true);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.LeftAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;		
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitOrient);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000134")/*@res "方向"*/);
	colFormats[i].setColWidth(33);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.CenterAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000459")/*@res "期初余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000459")/*@res "期初余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000459")/*@res "期初余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000459")/*@res "期初余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	///////////////////////////////////////////////////////////////////////////////
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitDebitQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000479")/*@res "期初借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitDebitAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000479")/*@res "期初借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitDebitAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000479")/*@res "期初借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitDebitLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000479")/*@res "期初借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitCreditQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000480")/*@res "期初贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitCreditAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000480")/*@res "期初贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitCreditAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000480")/*@res "期初贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_InitCreditLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000480")/*@res "期初贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	/////////////////////////////////////////////////////////////////////////////////
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000044")/*@res "本期借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000044")/*@res "本期借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000044")/*@res "本期借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000044")/*@res "本期借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000045")/*@res "本期贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000045")/*@res "本期贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000045")/*@res "本期贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000045")/*@res "本期贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	

	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAccumQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000046")/*@res "借方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAccumAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000046")/*@res "借方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAccumAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000046")/*@res "借方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_DebitAccumLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000046")/*@res "借方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAccumQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000047")/*@res "贷方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAccumAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000047")/*@res "贷方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAccumAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000047")/*@res "贷方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_CreditAccumLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000047")/*@res "贷方累计"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndOrient);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000134")/*@res "方向"*/);
	colFormats[i].setColWidth(33);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.CenterAlign);
	colFormats[i].setMultiHeadStr(null);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000048")/*@res "期末余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000048")/*@res "期末余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000048")/*@res "期末余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000048")/*@res "期末余额"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	////////////////////////////////////////////////////////////////////////////////
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndDebitQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000481")/*@res "期末借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndDebitAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000481")/*@res "期末借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndDebitAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000481")/*@res "期末借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndDebitLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000481")/*@res "期末借方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndCreditQuant);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000050")/*@res "数量"*/);
	colFormats[i].setColWidth(60);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000482")/*@res "期末贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndCreditAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000051")/*@res "原币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000482")/*@res "期末贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndCreditAuxAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000052")/*@res "辅币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000482")/*@res "期末贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	i++;	
	
	colFormats[i]=new ColFormatVO();
	colFormats[i].setColKey(BalanceBSKey.K_EndCreditLocAmount);
	colFormats[i].setColName(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000053")/*@res "本币"*/);
	colFormats[i].setColWidth(85);
	colFormats[i].setFrozen(false);
	colFormats[i].setVisiablity(true);
	colFormats[i].setAlignment(AlignmentConst.RightAlign);
	colFormats[i].setMultiHeadStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000482")/*@res "期末贷方"*/);
	colFormats[i].setDataType(nc.vo.gl.accbook.DataTypeConst.NUMBER);
	return colFormats;
}
/**
 * 初始化连接
 * @exception java.lang.Exception 异常说明。
 */
/* 警告：此方法将重新生成。 */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getcbFormat().addItemListener(ivjEventHandler);
}
/**
 * 初始化类。
 */
/* 警告：此方法将重新生成。 */
private void initialize() {
	try {
		// user code begin {1}
		getcbFormat().addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000125")/*@res "数量金额式"*/);
		getcbFormat().addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000126")/*@res "金额式"*/);
		// user code end
		setName("TricolAccbookUI");
		setLayout(new java.awt.BorderLayout());
		add(getHeadPanel(), BorderLayout.NORTH);
		add(getMyTablePane(), BorderLayout.CENTER);
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	initTable();
	TwoTableListSelectionListener listner = new TwoTableListSelectionListener(fixTable,getMyTablePane().getTable());
	getMyTablePane().getTable().getSelectionModel().addListSelectionListener(listner);
	fixTable.getSelectionModel().addListSelectionListener(listner);
	CopyTwoTableUtil.getInstance().replaceCopy(fixTable, getMyTablePane().getTable());
	
	fixTable.getTableHeader().addMouseMotionListener(new MyMouseMotionAdapter());
	getMyTablePane().getTable().getTableHeader().addMouseMotionListener(new MyMouseMotionAdapter());
	resetFormat(getFormat());
	getcbFormat().addItemListener(this);
	// user code end
}
public void setBackBsVOs(BalanceBSVO[] vos,JTable p_table, GlQueryVO qryVO)
{
	//设置有辅助核算科目数据条的背景色

	TableColumnModel tcm = p_table.getColumnModel();
	
	TableColumn tm = null;
	TableCellRenderer tcr = null;

	for (int i = 0; i < tcm.getColumnCount(); i++)
	{

		tm = tcm.getColumn(i);

		tcr = tm.getCellRenderer();

		if (tcr == null)
		{
			BalanceBookCellRender tempTcr= new BalanceBookCellRender();
			tempTcr.setVos(vos);
			//tcr = (TableCellRenderer)tempTcr;
			tm.setCellRenderer(tcr);
		}
		else
		{
			((BalanceBookCellRender)tcr).setVos(vos);
			if(qryVO!=null)
			{
				((BalanceBookCellRender)tcr).setQryvo(qryVO);
				
			}
		}	
	}
}
/**
 * 此处插入方法说明。
 * 创建日期：(01-8-17 14:44:56)
 */
protected void initTable() {
	nc.ui.pub.beans.UITable table=getMyTablePane().getTable();
	m_model=new BalanceTableModel();

	//得到TableFormatVO
	ColFormatVO[] colVOs=initColFormatVO();
	NewTableFormatTackle vo=new NewTableFormatTackle();
	vo.setColFormatVO(colVOs);

	//往TableModel上设置TableFormatVO
	m_model.setFormatVO(vo);
	fixModel.setFormatVO(vo);

	//往Table上设置TableModel
	table.setModel(m_model);
	fixTable.setModel(fixModel);

	//设置Table,fixTable的格式
	vo.setColWidth(table);
	vo.setAlignment(table);
	vo.setVisiablity(table);
	vo.setMultiHead(table);

	vo.setFixColWidth(fixTable);
	vo.setFixAlignment(fixTable);
	vo.setFixVisiablity(fixTable);
	vo.setFixMultiHead(fixTable);

	((BalanceTableModel)table.getModel()).setFormatVO(vo);
	((BalanceFixTableModel)fixTable.getModel()).setFormatVO(vo);
	fixTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
	getMyTablePane().setRowHeaderView(fixTable);
	getMyTablePane().setCorner(javax.swing.JScrollPane.UPPER_LEFT_CORNER,fixTable.getTableHeader());
	table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
}
/**
 * 此处插入方法说明。
 * 创建日期：(01-8-22 15:06:22)
 * @param e java.awt.event.ItemEvent
 */
public void itemStateChanged(java.awt.event.ItemEvent e) {
	if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
		Map<String, JPanel> map = new HashMap<String, JPanel>();
		map.put(GlNodeConst.GLNODE_SUBJASSBANLCE, this);
		try {
			JTableTool.INSTANCE.saveDynamicColumnWidth(map);
		} catch (Exception ex) {
			Logger.error(ex.getMessage(),ex);
		}

		if (getcbFormat().getSelectedItem().toString().trim().equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000126")/*@res "金额式"*/)) {
			getFormat().setNumberFormat(false);
		} else {
			getFormat().setNumberFormat(true);
		}
		resetFormat(getFormat());
		nc.ui.pub.beans.UITable table=getMyTablePane().getTable();
		setBackBsVOs(((BalanceTableModel)table.getModel()).getData(), table, null);
	}
	return;
}
/**
 * 主入口点 - 当部件作为应用程序运行时，启动这个部件。
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		BalancebookUI aTricolAccbookUI;
		aTricolAccbookUI = new BalancebookUI();
		frame.setContentPane(aTricolAccbookUI);
		frame.setSize(aTricolAccbookUI.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		nc.bs.logging.Logger.error("nc.ui.pub.beans.UIPanel 的 main() 中发生异常");
		nc.bs.logging.Logger.error(exception.getMessage(), exception);
	}
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-9-26 15:40:36)
 */
protected void recoveTableFormat() {
	nc.ui.pub.beans.UITable table=getMyTablePane().getTable();
	nc.ui.pub.beans.UITable fixTable=(nc.ui.pub.beans.UITable)(getMyTablePane().getRowHeader().getComponents()[0]);
	NewTableFormatTackle vo=((BalanceTableModel)table.getModel()).getFormatVO();
	ColFormatVO[] colFormats=vo.getColFormatVOs();

	for(int i=0;i<colFormats.length;i++)
		colFormats[i].setVisiablity(true);

	vo.setFixVisiablity(fixTable);
	//vo.setMultiHead(table);
	vo.setVisiablity(table);
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-9-26 15:53:56)
 * @param isMultiCorp boolean
 * @param isMultiCurrType boolean
 * @param isNumberFormat boolean
 */
private void resetFormat(nc.ui.gl.accbook.BillFormatVO format) {
    boolean isMultiCorp, isMultiCurrType, isNumberFormat, isLocAuxCurrType, isLocCurrType, isAuxCurrType;
    boolean isTwoWayBalance = false;
    isMultiCorp = format.isMultiOrg();
    isMultiCurrType = format.isMultiCurrType();
    isNumberFormat = format.isNumberFormat();
    isLocAuxCurrType = format.isLocAuxCurrType();
    isLocCurrType = format.isLocCurrType();
    isAuxCurrType = format.isAuxCurrType();
    isTwoWayBalance = format.isTwoWayBalance();
    boolean isShowBusi = format.isShowBusi();
    int[] invisCols = null;
    int len = 0;
    
    if (isTwoWayBalance) {
        len += 10;
    }
    else {
        len += 16;
    }

    if (!isMultiCorp)
        len += 1;
    if(!isShowBusi){
    	len+=1;
    }
    if (!isMultiCurrType)
        len += 1;
    if (!isNumberFormat) {
        if (isTwoWayBalance) {
            len += numberVis2.length;
        }
        else {
            len += numberVis.length;
        }
    }
    if (!isLocAuxCurrType) {
        if (isTwoWayBalance) {
            len += AuxCurrTypeVis2.length;
            if (isLocCurrType)
                len += OriCurrTypeVis2.length;
        }
        else {
            len += AuxCurrTypeVis.length;
            if (isLocCurrType)
                len += OriCurrTypeVis.length;
        }
    }
    else {
        if (isTwoWayBalance) {
            if (isLocCurrType)
                len += OriCurrTypeVis2.length + AuxCurrTypeVis2.length;
            if (isAuxCurrType)
                len += OriCurrTypeVis2.length + LocCurrTypeVis2.length;
        }
        else {
            if (isLocCurrType)
                len += OriCurrTypeVis.length + AuxCurrTypeVis.length;
            if (isAuxCurrType)
                len += OriCurrTypeVis.length + LocCurrTypeVis.length;
        }
    }

    invisCols = new int[len];
    int pos = 0;
    //////////////////////
    if (isTwoWayBalance) {
        invisCols[pos] = BalanceBSKey.K_InitOrient;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndOrient;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitLocAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndLocAmount;
        pos++;
    }
    else {
        invisCols[pos] = BalanceBSKey.K_InitDebitQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitDebitAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitDebitAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitDebitLocAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitCreditQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitCreditAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitCreditAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_InitCreditLocAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndDebitQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndDebitAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndDebitAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndDebitLocAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndCreditQuant;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndCreditAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndCreditAuxAmount;
        pos++;
        invisCols[pos] = BalanceBSKey.K_EndCreditLocAmount;
        pos++;
    }
    //////////////////////
    if (!isMultiCorp) {
        invisCols[pos] = corpVis;
        pos++;
    }
    
    if(!isShowBusi){
        invisCols[pos] = BalanceBSKey.K_PKUNIT;
        pos++;
    }
    
    if (!isMultiCurrType) {
        invisCols[pos] = currtypeVis;
        pos++;
    }
    if (!isNumberFormat) {
        if (isTwoWayBalance) {
            for (int i = 0; i < numberVis2.length; i++) {
                invisCols[pos] = numberVis2[i];
                pos++;
            }
        }
        else {
            for (int i = 0; i < numberVis.length; i++) {
                invisCols[pos] = numberVis[i];
                pos++;
            }
        }
    }
    if (!isLocAuxCurrType) {
        if (isTwoWayBalance) {
            for (int i = 0; i < AuxCurrTypeVis2.length; i++) {
                invisCols[pos] = AuxCurrTypeVis2[i];
                pos++;
            }
            if (isLocCurrType) {
                for (int k = 0; k < OriCurrTypeVis2.length; k++) {
                    invisCols[pos] = OriCurrTypeVis2[k];
                    pos++;
                }
            }
        }
        else {
            for (int i = 0; i < AuxCurrTypeVis.length; i++) {
                invisCols[pos] = AuxCurrTypeVis[i];
                pos++;
            }
            if (isLocCurrType) {
                for (int k = 0; k < OriCurrTypeVis.length; k++) {
                    invisCols[pos] = OriCurrTypeVis[k];
                    pos++;
                }
            }
        }
    }
    else {
        if (isTwoWayBalance) {
            if (isLocCurrType) {
                for (int k = 0; k < AuxCurrTypeVis2.length; k++) {
                    invisCols[pos] = AuxCurrTypeVis2[k];
                    pos++;
                }
                for (int i = 0; i < OriCurrTypeVis2.length; i++) {
                    invisCols[pos] = OriCurrTypeVis2[i];
                    pos++;
                }
            }
            if (isAuxCurrType) {
                for (int i = 0; i < OriCurrTypeVis2.length; i++) {
                    invisCols[pos] = OriCurrTypeVis2[i];
                    pos++;
                }
                for (int i = 0; i < LocCurrTypeVis2.length; i++) {
                    invisCols[pos] = LocCurrTypeVis2[i];
                    pos++;
                }
            }
        }
        else {
            if (isLocCurrType) {
                for (int k = 0; k < AuxCurrTypeVis.length; k++) {
                    invisCols[pos] = AuxCurrTypeVis[k];
                    pos++;
                }
                for (int i = 0; i < OriCurrTypeVis.length; i++) {
                    invisCols[pos] = OriCurrTypeVis[i];
                    pos++;
                }
            }
            if (isAuxCurrType) {
                for (int i = 0; i < OriCurrTypeVis.length; i++) {
                    invisCols[pos] = OriCurrTypeVis[i];
                    pos++;
                }
                for (int i = 0; i < LocCurrTypeVis.length; i++) {
                    invisCols[pos] = LocCurrTypeVis[i];
                    pos++;
                }
            }
        }
    }

    recoveTableFormat();
    setTableColVisibility(invisCols, false);

    Map<String, JPanel> map = new HashMap<String, JPanel>();
	map.put(GlNodeConst.GLNODE_SUBJASSBANLCE, this);
	try {
		JTableTool.INSTANCE.setDynamicColumnWidth(map);
	} catch (Exception ex) {
		Logger.error(ex.getMessage(),ex);
	}
    if (isNumberFormat)
        getcbFormat().setSelectedItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000125")/*@res "数量金额式"*/);
    else
        getcbFormat().setSelectedItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000126")/*@res "金额式"*/);
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-6 14:08:41)
 * @return nc.vo.gl.balancebooks.BalanceBSVO[]
 * @param vos nc.vo.gl.balancebooks.BalanceBSVO[]
 * @param qryVO nc.vo.glcom.balance.GlQueryVO
 */
private BalanceBSVO[] resetRecordFormat(BalanceBSVO[] vos, GlQueryVO qryVO) {
	return vos;
}
public void setChartModel(IChartModel charModel)
{
	m_charModel=charModel;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-9-25 14:58:55)
 * @param colsNum int[]
 */
protected void setTableColVisibility(int[] colsNum, boolean isVisiabled)
{
	nc.ui.pub.beans.UITable table = getMyTablePane().getTable();
	nc.ui.pub.beans.UITable fixTable = (nc.ui.pub.beans.UITable) (getMyTablePane().getRowHeader().getComponents()[0]);
	NewTableFormatTackle vo = ((BalanceTableModel) table.getModel()).getFormatVO();
	ColFormatVO[] colFormats = vo.getColFormatVOs();

	for (int i = 0; i < colsNum.length; i++)
	{
		for (int j = 0; j < colFormats.length; j++)
		{
			if (colFormats[j].getColKey() == colsNum[i])
			{
				colFormats[j].setVisiablity(isVisiabled);
				break;
			}
		}
	}

	vo.setFixVisiablity(fixTable);
	vo.setVisiablity(table);
	vo.setMultiHead(table);
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-9-25 10:03:29)
 * @param vos nc.vo.gl.triaccbooks.TriAccbookVO[]
 */
public void setTableData(nc.vo.gl.balancebooks.BalanceBSVO[] vos, nc.vo.glcom.balance.GlQueryVO qryVO)
{
	Map<String, JPanel> map = new HashMap<String, JPanel>();
	map.put(GlNodeConst.GLNODE_SUBJASSBANLCE, this);
	try {
		JTableTool.INSTANCE.saveDynamicColumnWidth(map);
		
		//add chenth 20161206 辅助核算分列显示  add by weiningc  633适配至65  start
		List<ColFormatVO> assCols = new ArrayList<ColFormatVO>();
		Map<String,Integer> assColKeyMap = new HashMap<String,Integer>();
		ColFormatVO colFormatVo = null;
		//定义辅助核算列从100开始
		int assColKey=100;
		for(BalanceBSVO vo : vos){
			AssVO[] assvos = ((AssVO[])vo.getValue(BalanceBSKey.K_ASSVOS));
			if(vo.getValue(BalanceBSKey.K_ASSID) != null && assvos != null
					&& assvos.length > 0){
				for(AssVO assvo : assvos){
					String pk_checktype = assvo.getPk_Checktype();
					if(!assColKeyMap.containsKey(pk_checktype)){
						colFormatVo=new ColFormatVO();
						colFormatVo.setColKey(assColKey);
						colFormatVo.setColName(assvo.getChecktypename());
						colFormatVo.setColWidth(60);
						colFormatVo.setFrozen(true);
						colFormatVo.setVisiablity(true);
						colFormatVo.setAlignment(AlignmentConst.LeftAlign);
						colFormatVo.setMultiHeadStr(null);
						
						assCols.add(colFormatVo);
						assColKeyMap.put(pk_checktype, assColKey);
						assColKey++;
					}
				}
			}
		}
		ColFormatVO[] addcolVOs = assCols.toArray(new ColFormatVO[assCols.size()]);
		if(addcolVOs.length>0){
			ColFormatVO[] colVOs=initColFormatVO();
			ColFormatVO[] newcolVOs = new ColFormatVO[colVOs.length + addcolVOs.length];
			System.arraycopy(colVOs, 0, newcolVOs, 0, colVOs.length);
			System.arraycopy(addcolVOs, 0, newcolVOs, colVOs.length, addcolVOs.length);
			getMyTablePane().getTable().getColumnModel();
			
			nc.ui.pub.beans.UITable table=getMyTablePane().getTable();
			//得到TableFormatVO
			NewTableFormatTackle vo=((BalanceTableModel)table.getModel()).getFormatVO();
			vo.setColFormatVO(newcolVOs);
			
			m_model=new BalanceTableModel();
			fixModel = new BalanceFixTableModel();
 
			//往TableModel上设置TableFormatVO
			m_model.setFormatVO(vo);
			fixModel.setFormatVO(vo);

			//往Table上设置TableModel
			table.setModel(m_model);
			fixTable.setModel(fixModel);

			//设置Table,fixTable的格式
			vo.setColWidth(table);
			vo.setAlignment(table);
			vo.setVisiablity(table);
			vo.setMultiHead(table);

			vo.setFixColWidth(fixTable);
			vo.setFixAlignment(fixTable);
			vo.setFixVisiablity(fixTable);
			vo.setFixMultiHead(fixTable);
			//add chenth 20161206 辅助核算分列显示  add by weiningc  633适配至65  end
		}
	} catch (Exception ex) {
		Logger.error(ex.getMessage(),ex);
	}

	//格式化界面
	if (qryVO.getpk_accountingbook().length > 1 && !qryVO.isMultiCorpCombine())
		getFormat().setMultiOrg(true);
	else
		getFormat().setMultiOrg(false);

	getFormat().setLocAuxCurrType(qryVO.isLocalFrac());
	getFormat().setLocCurrType(false);
	getFormat().setAuxCurrType(false);
	if (qryVO.getCurrTypeName().equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE))
		getFormat().setMultiCurrType(true);
	else
	{
		getFormat().setMultiCurrType(false);
		if (qryVO.getCurrTypeName().equals(nc.gl.account.glconst.CurrTypeConst.QUERY_LOC_CURRTYPE()))
			getFormat().setLocCurrType(true);
		if (qryVO.getCurrTypeName().equals(nc.gl.account.glconst.CurrTypeConst.AUX_CURRTYPE))
			getFormat().setAuxCurrType(true);
	}
	getFormat().setTwoWayBalance(qryVO.getFormatVO().isTwoWayBalance());
	if (vos != null && vos.length != 0)
	{
		//填充无余额无发生的科目项
		if (qryVO.isShowZeroAmountRec())
			vos = resetRecordFormat(vos, qryVO);
		//格式化数据项
		//resetDataFormat(vos,qryVO);
	}
	else
	{
		vos = new nc.vo.gl.balancebooks.BalanceBSVO[0];
	}
	nc.ui.pub.beans.UITable table=getMyTablePane().getTable();
	
	((BalanceTableModel) getMyTablePane().getTable().getModel()).setData(vos);
	fixModel.setData(vos);
	try{		
		//根据返回币种类型，查询币种pk
		String pk_locCurrType=null;
		String currReturnTypeName = qryVO.getReturn_CurrtypeName();
		String pk_group = WorkbenchEnvironment.getInstance().getGroupVO().getPk_group();
		String pk_acctingbook = qryVO.getBaseAccountingbook()==null?qryVO.getpk_accountingbook()[0]:qryVO.getBaseAccountingbook();
		if(!StringUtils.isEmpty(currReturnTypeName) && (CurrTypeConst.LOC_CURRTYPE().equals(currReturnTypeName) ||CurrTypeConst.GROUP_CURRTYPE().equals(currReturnTypeName) ||CurrTypeConst.GLOBAL_CURRTYPE().equals(currReturnTypeName))) {//组织本币
			try {
				pk_locCurrType = Currency.getCurrtypePKByStr(currReturnTypeName,pk_acctingbook,pk_group);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}
		}
		
		((BalanceTableModel) getMyTablePane().getTable().getModel()).setLocCurrTypePK(pk_locCurrType);
		((BalanceTableModel) getMyTablePane().getTable().getModel()).setQryVO(qryVO);
	}catch(Exception e){}

	getlbPeriod().setText(qryVO.getYear() + StrTools.PERIODSPLIT + qryVO.getPeriod() + StrTools.PERIODSCOPESPLIT + qryVO.getYear() + StrTools.PERIODSPLIT + qryVO.getEndPeriod());
	getlbCurrType().setText(qryVO.getCurrTypeName());

	getUILabelOrgNameAndCode().setText(getOrgNames(qryVO));
	try {
		if(GLParaAccessor.isSecondBUStart(qryVO.getpk_accountingbook()[0]).booleanValue()
				&&!qryVO.isMultiBusi()&&qryVO.getpk_accountingbook().length==1)
			getFormat().setShowBusi(true);
		else
			getFormat().setShowBusi(false);
	} catch (BusinessException e) {
		handleException(e);
	}
	resetFormat(getFormat());
	//设置满足条件的记录前景色
	
	 setBackBsVOs(vos, table,qryVO);
     setBackBsVOs(vos, fixTable,qryVO);
     
	getMyTablePane().invalidate();
	getMyTablePane().repaint();
	this.repaint();
}
public void valueChanged(javax.swing.event.ListSelectionEvent e)
{
	try
	{
		if (e.getSource() == fixTable.getSelectionModel())
		{
			int i = fixTable.getSelectedRow();
			getMyTablePane().getTable().setRowSelectionInterval(i, i); //getSelectionModel().set
			getMyTablePane().getTable().scrollRectToVisible(getMyTablePane().getTable().getCellRect(i,getMyTablePane().getTable().getSelectedColumn(),true));
			m_charModel.setCurrentIndex(i);

		}
		if (e.getSource() == getMyTablePane().getTable().getSelectionModel())
		{
			int i = getMyTablePane().getTable().getSelectedRow();
			fixTable.setRowSelectionInterval(i, i); //getSelectionModel().set
			m_charModel.setCurrentIndex(i);
		}
	}
	catch (Exception ex)
	{
nc.bs.logging.Logger.error(ex.getMessage(), ex);
	}
}
    /**
     * @return 返回 uILabelOrgNameAndCode。
     */
    private UILabel getUILabelOrgNameAndCode() {
        return UILabelOrgNameAndCode;
    }
    private String getOrgNames(nc.vo.glcom.balance.GlQueryVO qryVO) {
    	String[] strPkorgbooks = qryVO.getpk_accountingbook();
    	String strorgName = "";
    	AccountingBookVO[] orgbookVOs = new AccountingBookVO[strPkorgbooks.length];
    	try {
    		for (int i = 0; i < strPkorgbooks.length; i++) {
    		    orgbookVOs[i] = AccountBookUtil.getGlOrgBookVOByPrimaryKey(strPkorgbooks[i]);
    		    strorgName = strorgName + "," + orgbookVOs[i].getCode()+ orgbookVOs[i].getName();
    		}
    	} catch (Exception ee) {
    		handleException(ee);
    	}
    	strorgName = strorgName.substring(1);

        return strorgName;
    }

}