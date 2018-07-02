package nc.ui.arap.comreport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.fipub.queryobjreg.IReportQueryObjRegQuery;
import nc.itf.fipub.report.IPubReportConstants;
import nc.ui.fipub.comp.PubUIComboBox;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.utils.arap.ArapReportResource;
import nc.utils.fipub.FipubReportResource;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.fipub.report.QueryObjVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

/**
 * 收付帐表节点发布-查询对象选择面板
 *
 * @author 梁国荣
 * @since V60
 * Create at 2010-11-09
 *
 */
public class ArapReleaseQryObjSelectComp extends UIPanel {
	private static final long serialVersionUID = 1L;

	private String moduleid = null;

	// 查询对象选择表行数，暂时支持5个查询对象选择
	private final int qryObjTblRowCount = 5;
	// 查询对象选择表列数，暂时支持2列属性
	private final int qryObjTblColCount = 2;
	// 查询对象选择表
	private UITable qryObjTable = null;
	// 查询对象选择表列名
	private final List<String> colNameList = Arrays.asList(new String[] {ArapReportResource.getQryObjLbl(), ArapReportResource.getQryObjSeqLbl()});

	// 查询对象列表
	private List<QueryObjVO> qryObjVOList = new ArrayList<QueryObjVO>();
	private int qryObjVOSize = 0;
	public int getQryObjVOSize() {
		return qryObjVOSize;
	}

	private List<QueryObjVO> qryObjVOListCache = null;

	private UIComboBox reportFormatCombo = null; // 账页格式组合框(可选：金额式/外币金额式)
	private UIComboBox showFormatCombo = null; // 显示格式组合框(可选：名称/编码/名称+编码)
	// private UIComboBox directCombo = null; // 方向选择组合框(可选：收/付)
	// private UILabel directLabel = null;

	private UICheckBox showVoucherChk = null; // 是否显示凭证号
	private UICheckBox multiUnitSepChk = null; // 是否按单位列示
	private UICheckBox multiUnitMerChk = null; // 是否多单位合并

	private String reportType = null; // 报表类型

	// 显示收付方向的报表列表
	private final List<String> showDirectReportList = new ArrayList<String>();

	// 显示凭证号的报表列表
	private final List<String> showVoucherReportList = new ArrayList<String>();

	// 显示多单位选项的报表列表
	private final List<String> multiUnitReportList = new ArrayList<String>();

	// 没有查询对象的报表列表
	private final List<String> noQryObjReportList = new ArrayList<String>();

	private List<Object> selectQryObj = null;

	public ArapReleaseQryObjSelectComp(String moduleid) {
		setName("qryObjSelectPanel");
		setSize(new Dimension(780, 500));
		setPreferredSize(new Dimension(780, 500));
		setLayout(new BorderLayout());
		setVisible(true);
		this.moduleid = moduleid;
		initialize();
		initTable();
		initOther();
	}

	private void initialize() {
		showDirectReportList.add(IPubReportConstants.GENERAL_REPORT);
		showDirectReportList.add(IPubReportConstants.BALANCE_REPORT);
		showDirectReportList.add(IPubReportConstants.DETAIL_REPORT);

		showVoucherReportList.add(IPubReportConstants.DETAIL_REPORT);
		showVoucherReportList.add(IPubReportConstants.RECQRY_REPORT);
		showVoucherReportList.add(IPubReportConstants.PAYQRY_REPORT);

		multiUnitReportList.add(IPubReportConstants.YSZLFX_REPORT);
		multiUnitReportList.add(IPubReportConstants.YFZLFX_REPORT);
		multiUnitReportList.add(IPubReportConstants.SKYC_REPORT);
		multiUnitReportList.add(IPubReportConstants.FKYC_REPORT);
		multiUnitReportList.add(IPubReportConstants.YSQKFX_REPORT);
		multiUnitReportList.add(IPubReportConstants.SKFX_REPORT);

		noQryObjReportList.add(IPubReportConstants.ARALARM_REPORT);
		noQryObjReportList.add(IPubReportConstants.APALARM_REPORT);
		noQryObjReportList.add(IPubReportConstants.RECQRY_REPORT);
		noQryObjReportList.add(IPubReportConstants.PAYQRY_REPORT);
	}

	/**
	 * 功能：初始化查询对象列表
	 */
	private void initTable() {
		if (qryObjTable == null) {
			// 设置表模型
			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.setColumnIdentifiers(new String[] { colNameList.get(0), colNameList.get(1) });
			tableModel.setRowCount(qryObjTblRowCount);
			tableModel.setColumnCount(qryObjTblColCount);

			qryObjTable = new UITable(qryObjTblRowCount, qryObjTblColCount) {
				private static final long serialVersionUID = 1L;
				// 缓存单元格编辑器
				private final TableCellEditor[] cellEditors = new TableCellEditor[qryObjTblRowCount];

				@Override
				public TableCellEditor getCellEditor(int row, int column) {
					if (column == 0 && cellEditors[row] != null) {
						return cellEditors[row];
					}

					switch (column) {
					case 0:
						PubUIComboBox comboBox = new PubUIComboBox();
						comboBox.addItemListener(new ComboBoxItemListener());
						cellEditors[row] = new DefaultCellEditor(comboBox);
						return cellEditors[row];
					default:
						return null;
					}

				}

			};
			qryObjTable.setName("qryObjTable");
			qryObjTable.setModel(tableModel);
			qryObjTable.getTableHeader().setBackground(super.getBackground());
			qryObjTable.setVisible(true);

			JScrollPane scrollPane = new JScrollPane(qryObjTable);
			scrollPane.setPreferredSize(new Dimension(100, 145));
			add(scrollPane, BorderLayout.NORTH);
		}
	}

	private void initOther() {
		UIPanel otherPanel = new UIPanel();
		otherPanel.setName("otherPanel");
		FlowLayout otherPanelLayout = new FlowLayout();
		otherPanelLayout.setAlignment(FlowLayout.LEFT);
		otherPanelLayout.setHgap(25);
		otherPanelLayout.setVgap(15);
		otherPanel.setLayout(otherPanelLayout);

		UIPanel generalPanel = new UIPanel();
		generalPanel.setName("generalPanel");
		FlowLayout generalPanelLayout = new FlowLayout();
		generalPanelLayout.setAlignment(FlowLayout.LEFT);
		generalPanelLayout.setHgap(20);
		generalPanelLayout.setVgap(15);
		generalPanel.setLayout(generalPanelLayout);
		// generalPanel.setBorder(BorderFactory.createEtchedBorder());

		Dimension labelSize = new Dimension(105, 30);
		UILabel accountFormatLabel = new UILabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003829")/*@res "账页格式"*/);
		accountFormatLabel.setHorizontalAlignment(SwingConstants.LEFT);
		accountFormatLabel.setSize(labelSize);
		accountFormatLabel.setPreferredSize(labelSize);

		UILabel showFormatLabel = new UILabel(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0002449")/*@res "显示格式"*/);
		showFormatLabel.setHorizontalAlignment(SwingConstants.LEFT);
		showFormatLabel.setSize(labelSize);
		showFormatLabel.setPreferredSize(labelSize);

		// directLabel = new UILabel("收付方向");
		// directLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		// directLabel.setPreferredSize(new Dimension(labelSize));

		Dimension compSize = new Dimension(105, 30);
		reportFormatCombo = new UIComboBox();
		reportFormatCombo.addItems(new DefaultConstEnum[] {
				//update for 新加坡BDA 20161111
				new DefaultConstEnum(IPubReportConstants.ACCOUNT_FORMAT_FOREIGN, FipubReportResource.getAccountFormatLocalLbl()),
				new DefaultConstEnum(IPubReportConstants.ACCOUNT_FORMAT_LOCAL, FipubReportResource.getAccountFormatForeignLbl()) });
		reportFormatCombo.setSize(compSize);
		reportFormatCombo.setPreferredSize(compSize);

		showFormatCombo = new UIComboBox();
		showFormatCombo.addItems(new DefaultConstEnum[] {
				new DefaultConstEnum(IPubReportConstants.SHOW_FORMAT_NAME, FipubReportResource.getShowFormatNameLbl()),
				new DefaultConstEnum(IPubReportConstants.SHOW_FORMAT_CODE, FipubReportResource.getShowFormatCodeLbl()),
				new DefaultConstEnum(IPubReportConstants.SHOW_FORMAT_NAME_CODE, FipubReportResource.getShowFormatNameCodeLbl()) });
		showFormatCombo.setSize(compSize);
		showFormatCombo.setPreferredSize(compSize);

		// directCombo = new UIComboBox();
		// directCombo.addItems(new DefaultConstEnum[] {
		//		new DefaultConstEnum(IPubReportConstants.DIRECT_REC, FipubReportResource.getDirectRecLbl()),
		//		new DefaultConstEnum(IPubReportConstants.DIRECT_PAY, FipubReportResource.getDirectPayLbl()) });
		// directCombo.setPreferredSize(compSize);

		generalPanel.add(accountFormatLabel);
		generalPanel.add(reportFormatCombo);
		generalPanel.add(new UILabel(" "));
		generalPanel.add(showFormatLabel);
		generalPanel.add(showFormatCombo);

		// generalPanel.add(directLabel);
		// generalPanel.add(directCombo);

		otherPanel.add(generalPanel);

		// ==============================================================

		UIPanel specialPanel = new UIPanel();
		specialPanel.setName("specialPanel");
		FlowLayout specialPanelLayout = new FlowLayout();
		specialPanelLayout.setAlignment(FlowLayout.LEFT);
		specialPanelLayout.setHgap(20);
		specialPanelLayout.setVgap(15);
		specialPanel.setLayout(specialPanelLayout);
		// specialPanel.setBorder(BorderFactory.createEtchedBorder());

		compSize = new Dimension(150, 30);
		showVoucherChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0037")/*@res "显示凭证号"*/);
		multiUnitSepChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0038")/*@res "按单位列示"*/);
		multiUnitMerChk = new UICheckBox(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0039")/*@res "多单位合并"*/);
		showVoucherChk.setPreferredSize(compSize);
		multiUnitSepChk.setPreferredSize(compSize);
		multiUnitMerChk.setPreferredSize(compSize);

		specialPanel.add(showVoucherChk);
		specialPanel.add(multiUnitSepChk);
		specialPanel.add(multiUnitMerChk);

		ButtonGroup multiGroup = new ButtonGroup();
		multiGroup.add(multiUnitSepChk);
		multiGroup.add(multiUnitMerChk);
		multiUnitSepChk.doClick();

		otherPanel.add(specialPanel);

		add(otherPanel, BorderLayout.CENTER);
	}

	/**
	 * 功能：返回查询对象的列表<br>
	 * 说明：奇数位置为查询对象，偶数位置为查询对象的次序<br>
	 *
	 * @return 查询对象的列表<br>
	 */
	public List<Object> getCheckSelectQryObj() {
		List<Object> qryObjList = new ArrayList<Object>();
		for (int i = 0; i < qryObjTblRowCount; i++) {
			Object qryObj = ((PubUIComboBox) ((DefaultCellEditor) qryObjTable.getCellEditor(i, 0))
					.getComponent()).getSelectdItem();
			if (qryObj == null) {
				continue;
			}

			qryObjList.add(qryObj);
			qryObjList.add(qryObjTable.getValueAt(i, 1));
		}

		return qryObjList;
	}

	public List<Object> getSelectQryObj() {
		return selectQryObj;
	}

	public void setSelectQryObj(List<Object> selectQryObj) {
		this.selectQryObj = selectQryObj;
	}

	/**
	 * 功能：获得其他选项条件<br>
	 *
	 * @return Map<String, String><br>
	 */
	public Map<String, String> getOtherConds() {
		Map<String, String> otherConds = new HashMap<String, String>();
		otherConds.put("accountFormat", reportFormatCombo.getSelectdItemValue().toString());
		otherConds.put("showFormat", showFormatCombo.getSelectdItemValue().toString());
		return otherConds;
	}

	/**
	 * 得到该查询节点定义的查询对象<br>
	 */
	public List<QueryObjVO> getUseableQryObj() {
		if (noQryObjReportList.contains(reportType)) {
			qryObjVOListCache = qryObjVOList;
			qryObjVOList = new ArrayList<QueryObjVO>();
		} else if (qryObjVOListCache != null && qryObjVOListCache.size() > 0) {
			qryObjVOList = qryObjVOListCache;
		} else {
			try {
				// 查询选择帐表类型注册的查询对象
				qryObjVOList = NCLocator.getInstance().lookup(IReportQueryObjRegQuery.class)
						.getRegisteredQueryObjWithTrue(IPubReportConstants.MODULE_ARAP);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				MessageDialog.showErrorDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0040")/*@res "错误"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0041")/*@res "获取查询对象时发生错误！"*/);
			}
		}

		// 设置可选查询对象的值
		setQryObjTableData();
		qryObjVOSize = qryObjVOList.size();
		return qryObjVOList;
	}

	public void updateUIOptions() {
		// directLabel.setVisible(false);
		// directCombo.setVisible(false);
		showVoucherChk.setVisible(false);
		multiUnitSepChk.setVisible(false);
		multiUnitMerChk.setVisible(false);

		// if (showDirectReportList.contains(reportType)) {
		//     directLabel.setVisible(true);
		//     directCombo.setVisible(true);
		// }

		if (showVoucherReportList.contains(reportType)) {
			showVoucherChk.setVisible(true);
			showVoucherChk.setSelected(false);
		}

		if (multiUnitReportList.contains(reportType)) {
			multiUnitSepChk.setVisible(true);
			multiUnitMerChk.setVisible(true);
			multiUnitSepChk.doClick();
		}

		this.repaint();
	}

	/**
	 * 设置可选查询对象的值<br>
	 */
	private void setQryObjTableData() {
		String prop = QueryObjVO.DSP_OBJNAME;
		for (int i = 0; i < qryObjTblRowCount; i++) {
			((PubUIComboBox) ((DefaultCellEditor) qryObjTable.getCellEditor(i, 0)).getComponent())
					.addItems(qryObjVOList, prop, true);
		}
	}

	/**
	 * 功能：设置查询对象顺序<br>
	 */
	private void setQryObjOrder() {
		int order = 1;
		for (int i = 0; i < qryObjTblRowCount; i++) {
			if (StringUtils.isEmpty((String) qryObjTable.getCellEditor(i, 0).getCellEditorValue())) {
				qryObjTable.setValueAt("", i, 1);
				continue;
			}

			qryObjTable.setValueAt(order, i, 1);
			order++;
		}
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * 获取账页格式
	 *
	 * @return String
	 */
	public String getReportFormat() {
		return reportFormatCombo.getSelectdItemValue().toString();
	}

	/**
	 * 获取显示格式
	 *
	 * @return String
	 */
	public String getShowFormat() {
		return showFormatCombo.getSelectdItemValue().toString();
	}

	/**
	 * 获取收付方向
	 *
	 * @return String
	 */
	public String getRecPayDirect() {
		String direct = null;
		if (IArapReportConstants.MODULEID_AR.equals(moduleid)) {
			direct = IPubReportConstants.DIRECT_REC;
		} else if (IArapReportConstants.MODULEID_AP.equals(moduleid)) {
			direct = IPubReportConstants.DIRECT_PAY;
		}

		return direct;
	}

	/**
	 * 是否显示凭证号
	 *
	 * @return String
	 */
	public UFBoolean getShowVoucher() {
		if (showVoucherReportList.contains(reportType)) {
			return UFBoolean.valueOf(showVoucherChk.isSelected());
		}
		return null;
	}

	/**
	 * 获取多组织显示方式
	 *
	 * @return String 0：无/1：按单位列示/2：多组织合并
	 */
	public int getMultiUnitShowMode() {
		if (multiUnitReportList.contains(reportType)) {
			if (multiUnitSepChk.isSelected()) {
				return 1;
			} else if (multiUnitMerChk.isSelected()) {
				return 2;
			}
		}
		return 0;
	}

	class ComboBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setQryObjOrder();
			}
		}
	}

}

// /:~