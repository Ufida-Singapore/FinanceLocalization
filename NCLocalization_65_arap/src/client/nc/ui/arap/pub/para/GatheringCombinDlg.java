package nc.ui.arap.pub.para;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.para.ComplicatedParaContext;
import nc.itf.scmpub.reference.uap.para.SysParaInitQuery;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UITabbedPane;
import nc.ui.pub.para.ISysInitPanel2;
import nc.ui.pubapp.panel.AbstractParaListToListPanel;
import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.arap.pub.enumeration.GatheringCombinRule;
import nc.vo.arap.pub.enumeration.GatheringCombindefrule;
import nc.vo.arap.pub.res.ParameterList;
import nc.vo.arap.receivable.ReceivableBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.para.CheckParaVO;
import nc.vo.pub.para.SysInitVO;
import nc.vo.pubapp.pattern.pub.PubAppTool;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/***
 * 收款单单据合并规则参数设置对话框 Add by bl 2015-04-22
 */
public class GatheringCombinDlg extends UIPanel implements ISysInitPanel2 {

	/**
	 * 参数列表信息
	 * 
	 * @since 6.1
	 * @version 2013-03-20 09:34:59
	 * @author yixl
	 */
	protected static class ParaListItemInfo implements Serializable {

		private static final long serialVersionUID = -4047069847846625871L;

		// 显示值
		private String text;

		// 实际值
		private Object value;

		/**
		 * 空构造子
		 */
		public ParaListItemInfo() {
			//
		}

		/**
		 * 构造子
		 * 
		 * @param value
		 * @param text
		 */
		public ParaListItemInfo(Object value, String text) {
			this.value = value;
			this.text = text;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof ParaListItemInfo)) {
				return false;
			}
			ParaListItemInfo other = (ParaListItemInfo) obj;
			return this.value.equals(other.getValue());
		}

		/**
		 * 获得显示值
		 * 
		 * @return 显示值
		 */
		public String getText() {
			return this.text;
		}

		/**
		 * 获得实际值
		 * 
		 * @return 实际值
		 */
		public Object getValue() {
			return this.value;
		}

		@Override
		public int hashCode() {
			if (this.value == null) {
				return 0;
			}
			return this.value.hashCode();
		}

		/**
		 * 设置显示值
		 * 
		 * @param text
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * 设置实际值
		 * 
		 * @param value
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.text == null ? "" : this.text;
		}
	}

	private static final int HEIGHT1 = 650;

	private static final long serialVersionUID = 222937895245122719L;

	private static final int WIDTH1 = 300;

	private GatheringCombinDefPanel AvgPanel;

	/** 求平均的字段 */
	private GatheringCombinDlg.ParaListItemInfo[] AvgrightItems;

	private UITabbedPane bottomPanel;

	private GatheringCombinDefPanel combinPanel;

	private SysInitVO initVO;

	private GatheringCombinDlg.ParaListItemInfo[] leftItems;

	private Map<String, String> mapKeyName;

	private String pk_org;

	private UIPanel radioButtonPanel;

	private GatheringCombinDlg.ParaListItemInfo[] rightItems;

	private GatheringCombinDefPanel SumPanel;

	/** 求和的字段 */
	private GatheringCombinDlg.ParaListItemInfo[] SumrightItems;

	private GatheringCombinDlg.ParaListItemInfo[] vbdefleftItems;

	/**
	 * 
	 * @param pk_org
	 */
	public GatheringCombinDlg(String pk_org) {
		this.pk_org = pk_org;
		this.setSize(GatheringCombinDlg.WIDTH1, GatheringCombinDlg.HEIGHT1);
		this.setLayout(new BorderLayout());
		this.initlize();
		this.add(this.getBottomPanel(), BorderLayout.NORTH);
		this.add(this.getRadioButtonPanel(), BorderLayout.LINE_START);
	}

	/**
	 * 获得底部pannel
	 * 
	 * @return UITabbedPane
	 */
	public UITabbedPane getBottomPanel() {
		if (null == this.bottomPanel) {
			this.combinPanel = new GatheringCombinDefPanel(this.leftItems,
					this.rightItems);
			this.AvgPanel = new GatheringCombinDefPanel(this.vbdefleftItems,
					this.AvgrightItems);
			this.SumPanel = new GatheringCombinDefPanel(this.vbdefleftItems,
					this.SumrightItems);

			UITabbedPane p = new UITabbedPane();
			p.setFont(new Font("Dialog", Font.PLAIN, 12));
			p.add(this.combinPanel, NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("summaryext", "summaryext-0015")/* 汇总规则 */);
			p.add(this.SumPanel,
					NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
							"summaryext-0016")/* 求和 */);
			p.add(this.AvgPanel,
					NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
							"summaryext-0017")/* 求平均 */);

			this.bottomPanel = p;
		}
		return this.bottomPanel;
	}

	/**
	 * 
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getKeyNameRela() {
		if (null == this.mapKeyName) {
			this.mapKeyName = new LinkedHashMap<String, String>();
			for (GatheringCombinRule rule : GatheringCombinRule.values()) {
				this.mapKeyName.put(rule.getKey(), rule.getName());
			}
			for (GatheringCombindefrule rule : GatheringCombindefrule.values()) {
				this.mapKeyName.put(rule.getKey(), rule.getName());
			}
		}
		return this.mapKeyName;
	}

	@Override
	public UIPanel getPanel(ComplicatedParaContext context) {
		return this;
	}

	@Override
	public SysInitVO[] getSysInitVOs() {
		Object[] retArray = new ParaListItemInfo[0];
		StringBuffer value = new StringBuffer();
		Object[] rightobj = this.combinPanel.getRightData();
		if (null == rightobj || rightobj.length == 0) {
			return null;
		}
		retArray = rightobj;
		String retvalue = this.getSysInitVOValue(retArray);

		retvalue = this.getCombinValue(retvalue);
		value.append(retvalue + ParameterList.BIGSPLITKEY);
		if (ArrayUtils.isEmpty(this.SumPanel.getRightData())) {
			value.append(ParameterList.BIGSPLITKEY);
		} else {
			retArray = this.SumPanel.getRightData();
			retvalue = this.getSysInitVOValue(retArray);
			value.append(retvalue + ParameterList.BIGSPLITKEY);
		}
		if (ArrayUtils.isEmpty(this.AvgPanel.getRightData())) {
			value.append(ParameterList.BIGSPLITKEY);
		} else {
			retArray = this.AvgPanel.getRightData();
			retvalue = this.getSysInitVOValue(retArray);
			value.append(retvalue + ParameterList.BIGSPLITKEY);
		}

		this.initVO.setValue(value.toString());
		return new SysInitVO[] { this.initVO };
	}

	/**
	 * 获得参数信息
	 * 
	 * @param retArray
	 * @return String
	 */
	public String getSysInitVOValue(Object[] retArray) {

		StringBuilder values = new StringBuilder();
		for (Object obj : retArray) {
			values.append(((ParaListItemInfo)obj).getValue()).append(",");
		}
		return values.toString();
	}

	/**
	 * 初始化
	 */
	public void initlize() {
		SysInitVO[] initVOs = SysParaInitQuery.querySysInit(this.pk_org,
				this.getParamValueCode());

		for (SysInitVO vo : initVOs) {
			if (StringUtils.isNotBlank(vo.getInitcode())
					&& vo.getInitcode().endsWith(
							AbstractParaListToListPanel.ENDWORD)
					|| vo.getInitcode().endsWith(
							AbstractParaListToListPanel.ENDWORD.toUpperCase())) {
				this.initVO = vo;
			}
		}
		if(initVO != null){
			this.initLeftItems(this.initVO);
			this.initrightItems(this.initVO);
		}
	}

	/**
	 * 
	 * 
	 * @param bottomPanel
	 */
	public void setBottomPanel(UITabbedPane bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	/**
	 * 
	 * 
	 * @return UIPanel
	 */
	protected UIPanel getRadioButtonPanel() {
		if (this.radioButtonPanel == null) {
			this.radioButtonPanel = new UIPanel();
			this.radioButtonPanel.setLayout(new FlowLayout());
		}
		return this.radioButtonPanel;
	}

	private String getCombinValue(String retvalue) {
		String ret = retvalue;
		if (ret.indexOf(ReceivableBillItemVO.INVOICENO) != -1) {
			if (ret.indexOf(ReceivableBillItemVO.INVOICENO) == -1) {
				ret += ReceivableBillItemVO.INVOICENO + ",";
			}
		}
		return ret;
	}

	private String getParamValueCode() {
		return ParameterList.AR101.getCode();
	}

	private void initLeftItems(SysInitVO initVO2) {
		String value = initVO2.getValue();
		String[] allrightValues = new String[0];
		if (!PubAppTool.isNull(value)) {
			allrightValues = value.split(ParameterList.BIGSPLITKEY);
		}
		if (null == this.leftItems) {
			String rightvalue = allrightValues.length > 0 ? allrightValues[0]
					: null;
			Set<String> rightvalueSet = new HashSet<String>();
			if(rightvalue !=null && rightvalue.length()>0){
				for(String s : rightvalue.split(ParameterList.SPLITKEY)){
					rightvalueSet.add(s);
				}
			}
			GatheringCombinRule[] fields = GatheringCombinRule.class
					.getEnumConstants();
			List<GatheringCombinDlg.ParaListItemInfo> infolist = new ArrayList<GatheringCombinDlg.ParaListItemInfo>();
			for (GatheringCombinRule field : fields) {
				if (rightvalueSet != null
						&& rightvalueSet.contains(field.getKey())) {
					continue;
				}
				GatheringCombinDlg.ParaListItemInfo info = new ParaListItemInfo();
				info.setText(field.getName());
				info.setValue(field.getKey());
				infolist.add(info);
			}
			this.leftItems = infolist
					.toArray(new GatheringCombinDlg.ParaListItemInfo[infolist
							.size()]);
		}

		if (null == this.vbdefleftItems) {
			String rightvalue1 = allrightValues.length > 1 ? allrightValues[1]
					: null;
			String rightvalue2 = allrightValues.length > 2 ? allrightValues[2]
					: null;
			
			Set<String> rightvalueSet2 = new HashSet<String>();
			if(rightvalue1 !=null && rightvalue1.length()>0){
				for(String s : rightvalue1.split(ParameterList.SPLITKEY)){
					rightvalueSet2.add(s);
				}
			}
			if(rightvalue2 !=null && rightvalue2.length()>0){
				for(String s : rightvalue2.split(ParameterList.SPLITKEY)){
					rightvalueSet2.add(s);
				}
			}

			GatheringCombindefrule[] fields = GatheringCombindefrule.class
					.getEnumConstants();
			List<GatheringCombinDlg.ParaListItemInfo> infolist = new ArrayList<GatheringCombinDlg.ParaListItemInfo>();
			for (GatheringCombindefrule field : fields) {
				if (rightvalueSet2 !=null && rightvalueSet2.contains(field.getKey())) {
					continue;
				}
				GatheringCombinDlg.ParaListItemInfo info = new ParaListItemInfo();
				info.setText(field.getName());
				info.setValue(field.getKey());
				infolist.add(info);
			}
			this.vbdefleftItems = infolist
					.toArray(new GatheringCombinDlg.ParaListItemInfo[infolist
							.size()]);
		}
	}

	private void initrightItems(SysInitVO initVO2) {
		String value = initVO2.getValue();
		String[] allrightValues = new String[0];
		// 汇总规则
		String[] rightValues = new String[0];
		if (!PubAppTool.isNull(value)) {
			allrightValues = value.split(ParameterList.BIGSPLITKEY);
		}
		if (null == this.rightItems) {
			if (allrightValues.length > 0) {
				if (!PubAppTool.isNull(allrightValues[0])) {
					rightValues = allrightValues[0]
							.split(ParameterList.SPLITKEY);
					this.rightItems = new ParaListItemInfo[rightValues.length];
					for (int i = 0; i < rightValues.length; i++) {
						String key = rightValues[i];
						this.rightItems[i] = new ParaListItemInfo();
						String name = this.getKeyNameRela().get(key);
						this.rightItems[i].setText(name);
						this.rightItems[i].setValue(key);
					}
				}
			}
		}
		if (null == this.SumrightItems) {
			if (allrightValues.length > 1) {
				// 求和
				if (!PubAppTool.isNull(allrightValues[1])) {
					rightValues = allrightValues[1]
							.split(ParameterList.SPLITKEY);
					this.SumrightItems = new ParaListItemInfo[rightValues.length];
					for (int i = 0; i < rightValues.length; i++) {
						String key = rightValues[i];
						this.SumrightItems[i] = new ParaListItemInfo();
						String name = this.getKeyNameRela().get(key);
						this.SumrightItems[i].setText(name);
						this.SumrightItems[i].setValue(key);
					}
				}
			}
		}
		if (null == this.AvgrightItems) {
			if (allrightValues.length > 2) {
				// 求平均
				if (!PubAppTool.isNull(allrightValues[2])) {
					rightValues = allrightValues[2]
							.split(ParameterList.SPLITKEY);
					this.AvgrightItems = new ParaListItemInfo[rightValues.length];
					for (int i = 0; i < rightValues.length; i++) {
						String key = rightValues[i];
						this.AvgrightItems[i] = new ParaListItemInfo();
						String name = this.getKeyNameRela().get(key);
						this.AvgrightItems[i].setText(name);
						this.AvgrightItems[i].setValue(key);
					}
				}
			}
		}
	}

	@Override
	public CheckParaVO check() {
		CheckParaVO paravo = new CheckParaVO();
		Object[] retArray = new Object[0];
		String errMsg = "";
		Object[] rightobj = this.combinPanel.getRightData();
		if (null == rightobj || rightobj.length == 0) {
			errMsg = NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
					"summaryext-0004")/* 请选择合并条件 */;
			paravo.setErrMsg(errMsg.toString());
			paravo.setLegal(false);
			return paravo;
		}
		retArray = rightobj;
		String retvalue = this.getSysInitVOValue(retArray);

		List<String> errmeg = new ArrayList<String>();
		String ret = retvalue;
		if (ret.length() > 0
				&& ret.indexOf(GatheringBillItemVO.INVOICENO) == -1) {
			errmeg.add(NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"receivablebill", "2UC000-000220")/* 发票号 */);
			ret += GatheringBillItemVO.INVOICENO + ",";
		}

		if (ret.length() > 0
				&& ret.indexOf(GatheringBillItemVO.PK_CURRTYPE) == -1) {
			errmeg.add(NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"receivablebill", "2UC000-000315")/* 币种 */);
			ret += GatheringBillItemVO.PK_CURRTYPE + ",";
		}

		if (ret.length() > 0 && ret.indexOf(GatheringBillItemVO.CUSTOMER) == -1) {
			errmeg.add(NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"receivablebill", "2UC000-000283")/* 客户 */);
			ret += GatheringBillItemVO.CUSTOMER + ",";
		}

		if (errmeg.size() > 0) {
			StringBuffer errbuffer = new StringBuffer();
			for (String s : errmeg) {
				errbuffer.append(NCLangRes.getInstance().getStrByID(
						"summaryext", "summaryext-0018", null,
						new String[] { s })/* {0}、 */);
			}
			errbuffer.deleteCharAt(errbuffer.length() - 1);
			errMsg = NCLangRes.getInstance().getStrByID("summaryext",
					"summaryext-0019", null,
					new String[] { errbuffer.toString() });/* 以下字段：{0}是必选条件 */
		}
		if (errMsg.length() > 0) {
			paravo.setErrMsg(errMsg.toString());
			paravo.setLegal(false);
			return paravo;
		}
		return null;
	}
}