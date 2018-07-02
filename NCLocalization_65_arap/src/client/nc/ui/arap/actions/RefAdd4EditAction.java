package nc.ui.arap.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import nc.bs.arap.bill.ArapBillPubUtil;
import nc.bs.pf.pub.PfDataCache;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.funcnode.ui.action.INCAction;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.ui.arap.view.ArapBillCardForm;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pubapp.uif2app.view.BillOrgPanel;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.editor.BillForm;
import nc.util.mmf.framework.db.SqlInUtil;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.arap.paybill.RefAdd4EditUserObj;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDouble;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("restriction")
public class RefAdd4EditAction extends NCAction {

	protected static final long serialVersionUID = 1L;

	protected BillForm billform = null;

	protected String nodekey = null;
	
	protected String srcbilltype = null;
	
	protected String srcbilltypename = null;

	public String getNodekey() {
		return nodekey;
	}

	public void setNodekey(String nodekey) {
		this.nodekey = nodekey;
	}

	public String getSrcbilltype() {
		return srcbilltype;
	}
	
	public void setSrcbilltype(String srcbilltype) {
		this.srcbilltype = srcbilltype;
	}
	
	public String getSrcbilltypename() {
		return srcbilltypename;
	}
	
	public void setSrcbilltypename(String srcbilltypename) {
		this.srcbilltypename = srcbilltypename;
	}
	
	public RefAdd4EditAction(BillForm b, String curnodekey, String srcbilltype, String srcbilltypename) {
		super();
		billform = b;
		nodekey = curnodekey;
		this.srcbilltype = srcbilltype;
		this.srcbilltypename = srcbilltypename;
		setBtnName(srcbilltypename);
		putValue(Action.SHORT_DESCRIPTION, getBtnName());
		putValue(INCAction.CODE, IActionCode.SELFADD + srcbilltypename);
	}

	@Override
	public void doAction(final ActionEvent e) throws Exception {
		String cuserid = WorkbenchEnvironment.getInstance().getLoginUser().getCuserid();
		String pk_group = WorkbenchEnvironment.getInstance().getGroupVO().getPk_group();
		RefAdd4EditUserObj userObj = getUserObj();
		
		// 查询来源单据
		PfUtilClient.childButtonClickedWithBusi(srcbilltype, pk_group, cuserid, nodekey, billform, userObj, null, null);
		if (PfUtilClient.isCloseOK()) {
			// 单据来源参照返回单据聚合VO或数组
			AggregatedValueObject[] refvos = PfUtilClient.getRetVos(false);
			//合并界面数据和新参照的数据
			AggregatedValueObject newvo = combinVO(refvos);
			//设置界面数据
			billform.setValue(newvo);
			// 在设置完值之后再调用这个方法的目的是避免漏掉一些控制逻辑
			billform.setEditable(true);
			
			//新加的行置为新增态
			BillModel bm = billform.getBillCardPanel().getBillModel();
			CircularlyAccessibleValueObject[] items = newvo.getChildrenVO();
			for(int i=0; i<items.length; i++){
				if(items[i].getPrimaryKey() == null){
					bm.setRowState(i, BillModel.ADD);
				}
			}
			
			// 控制字段编辑
			this.controlFieldsEnable(new AggregatedValueObject[]{newvo});
			// 拉单单据控制财务组织可编辑
			if (billform instanceof ArapBillCardForm) {
				BillOrgPanel billOrgPanel = ((ArapBillCardForm) this.billform).getBillOrgPanel();
				billOrgPanel.setEnabled(true);
			}
			// 汇率字段可编辑(由编辑前事件监听控制)
			billform.getBillCardPanel().getHeadItem(IBillFieldGet.RATE).setEnabled(true);
		}
	}

	private RefAdd4EditUserObj getUserObj() throws BusinessException {
		AggregatedValueObject paybillvo = (AggregatedValueObject) billform.getValue();
		CircularlyAccessibleValueObject headVo = paybillvo.getParentVO();
		String pk_org = (String) headVo.getAttributeValue(IBillFieldGet.PK_ORG);
		String pk_supplier = (String) headVo.getAttributeValue(IBillFieldGet.SUPPLIER);
		String pk_customer = (String) headVo.getAttributeValue(IBillFieldGet.CUSTOMER);
		
		RefAdd4EditUserObj userObj = new RefAdd4EditUserObj();
		userObj.setPk_org(pk_org);
		userObj.setPk_supplier(pk_supplier);
		userObj.setPk_customer(pk_customer);
		
		CircularlyAccessibleValueObject[] items = paybillvo.getChildrenVO();
		if(items != null && items.length > 0){
			List<String> top_itemids = new ArrayList<String>();
			for(CircularlyAccessibleValueObject item : items){
				top_itemids.add((String) item.getAttributeValue(IBillFieldGet.TOP_ITEMID));
			}
			StringBuffer whereStr = new StringBuffer(" pk_payableitem not ");
			whereStr.append(new SqlInUtil(top_itemids).getInSql4Collection());
			userObj.setWhereStr(whereStr.toString());
			userObj.setPk_items(top_itemids);
		}
		
		return userObj;
	}

	private AggregatedValueObject combinVO(AggregatedValueObject[] refvos) throws BusinessException {
		String tradetype = billform.getNodekey();
		String pkBilltypeid = PfDataCache.getBillType(tradetype).getPk_billtypeid();
		AggregatedValueObject paybillvo = (AggregatedValueObject) billform.getValue();
		CircularlyAccessibleValueObject headVo = paybillvo.getParentVO();
		
		List<CircularlyAccessibleValueObject> itemList = new ArrayList<CircularlyAccessibleValueObject>();
		for (int i = 0; i < refvos.length; i++) {
			AggregatedValueObject vo = refvos[i];
			CircularlyAccessibleValueObject[] items = vo.getChildrenVO();

			for (int j = 0; j < items.length; j++) {
				BaseItemVO item = (BaseItemVO) items[j];
				//设置新增态
				item.setStatus(VOStatus.NEW);
				//设置表头主键和单据号
				if(nodekey.equals("D3")){//付款单
					item.setAttributeValue(PayBillItemVO.PK_PAYBILL, headVo.getPrimaryKey());
				}else if(nodekey.equals("D2")){//收款单
					item.setAttributeValue(GatheringBillItemVO.PK_GATHERBILL, headVo.getPrimaryKey());
				}
				item.setAttributeValue(IBillFieldGet.BILLNO, headVo.getAttributeValue(IBillFieldGet.BILLNO));
				
				//参考拉单设置值
				item.setAttributeValue(IBillFieldGet.PK_TRADETYPE, tradetype);
				item.setAttributeValue(IBillFieldGet.PK_TRADETYPEID, pkBilltypeid);
				item.setAttributeValue(IBillFieldGet.OCCUPATIONMNY, UFDouble.ZERO_DBL);
				item.setPrimaryKey(null);
				
				itemList.add(item);
				
			}
		}
		CircularlyAccessibleValueObject[] oldItems = paybillvo.getChildrenVO();
		CircularlyAccessibleValueObject[] addItems = itemList.toArray(new CircularlyAccessibleValueObject[itemList.size()]);
		BaseItemVO[] newItems = new BaseItemVO[oldItems.length + itemList.size()];
		System.arraycopy(oldItems, 0, newItems, 0, oldItems.length);
		System.arraycopy(addItems, 0, newItems, oldItems.length, addItems.length);
		paybillvo.setChildrenVO(newItems);
		
		
		//汇总表头金额
		ArapBillPubUtil.processMoneyOnlySum(paybillvo);
		
		return paybillvo;
	}

	private void controlFieldsEnable(AggregatedValueObject[] vos) {
		if (ArrayUtils.isEmpty(vos)) {
			return;
		}
		if (isNeedMoneyControl((BaseAggVO) vos[0])) {
			// 设置金额字段不可编辑
			setMoneyItemEnabled(false);
		}
		if (isNeedCurrControl((BaseAggVO) vos[0])) {
			// 设置金额字段不可编辑
			setItemEnble(new String[] { BaseItemVO.PK_CURRTYPE, BaseItemVO.RATE }, false);
		}
		// 付款单 从应收单拉单
		// 不通过（转单过来没有控制往来对象、币种不可修改）
		if ((IBillFieldGet.F3.equals(((BaseAggVO) vos[0]).getHeadVO().getPk_billtype())) && IBillFieldGet.F0.equals(((BaseAggVO) vos[0]).getItems()[0].getTop_billtype())) {

			setItemEnble(new String[] { BaseItemVO.PK_CURRTYPE, BaseItemVO.OBJTYPE }, false);
		}
	}

	/**
	 * @param vos
	 * @return 拉单，本系统拉单不控制， 外系统拉单控制
	 */
	private boolean isNeedMoneyControl(BaseAggVO vos) {
		BaseBillVO vo = (BaseBillVO) vos.getParentVO();
		if (vo.getSrc_syscode() == null || vo.getSrc_syscode().intValue() == BillEnumCollection.FromSystem.AR.VALUE.intValue()
				|| vo.getSrc_syscode().intValue() == BillEnumCollection.FromSystem.AP.VALUE.intValue()
				|| vo.getSrc_syscode().intValue() == BillEnumCollection.FromSystem.CT.VALUE.intValue()
				|| vo.getSrc_syscode().intValue() == BillEnumCollection.FromSystem.CMP.VALUE.intValue()) {
			return false;
		}
		return true;
	}

	/**
	 * 币种汇率控制
	 */
	private boolean isNeedCurrControl(BaseAggVO baseAggVO) {
		String topBilltype = baseAggVO.getItems()[0].getTop_billtype();
		if (StringUtils.isNotEmpty(topBilltype) && topBilltype.trim().startsWith("4A")) {
			return true;
		}
		return false;
	}

	private void setMoneyItemEnabled(boolean flag) {
		String[] itemArray = {
				BaseItemVO.MONEY_DE,
				BaseItemVO.LOCAL_MONEY_DE,
				BaseItemVO.LOCAL_NOTAX_CR,
				BaseItemVO.NOTAX_DE,
				BaseItemVO.LOCAL_NOTAX_DE,
				BaseItemVO.MONEY_CR,
				BaseItemVO.LOCAL_MONEY_CR,
				BaseItemVO.LOCAL_TAX_CR,
				BaseItemVO.NOTAX_CR,
				BaseItemVO.LOCAL_NOTAX_CR };

		for (String itemName : itemArray) {
			BillItem item = this.billform.getBillCardPanel().getBodyItem(itemName);
			if (item != null) {
				item.setEnabled(flag);
			}
		}
	}

	/**
	 * @param flag
	 * @param billItems
	 */
	private void setItemEnble(String[] billItems, boolean flag) {
		for (String itemName : billItems) {
			BillItem item = this.billform.getBillCardPanel().getBodyItem(itemName);
			if (item != null) {
				item.setEnabled(flag);
			}
			item = this.billform.getBillCardPanel().getHeadItem(itemName);
			if (item != null) {
				item.setEnabled(flag);
			}
		}
	}
	
	@Override
	public boolean isEnabled() 
	{
//		Boolean isflowbill = (Boolean) this.billform.getBillCardPanel().getHeadItem(IBillFieldGet.ISFLOWBILL).getValueObject();
//		return isflowbill == null ? true : isflowbill ;
		return true;
	} 

	//TODO 拉式生成的单据按钮才可用
	//head.setAttributeValue(IBillFieldGet.ISFLOWBILL, UFBoolean.TRUE); // 拉式生成单据
}
