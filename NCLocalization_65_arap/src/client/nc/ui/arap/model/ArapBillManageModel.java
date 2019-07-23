package nc.ui.arap.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import nc.bs.arap.util.ArapBillVOUtils;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.arap.pub.IArapBillService;
import nc.ui.arap.bill.ArapBillUIUtil;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.RowOperationInfo;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.uap.busibean.exception.BusiBeanRuntimeException;

/**
 * @see
 * @author guodw
 * @version V6.0
 * @since V6.0 创建时间：2009-8-27 下午06:18:46
 */
public class ArapBillManageModel extends BillManageModel {
	
	Boolean isLink = false;
	
	/*收付款单合并状态标志 */
	Boolean combineFlag = false;
	
	public Boolean getCombineFlag() {
		return combineFlag;
	}

	public void setCombineFlag(Boolean combineFlag) {
		this.combineFlag = combineFlag;
	}

	public Boolean getIsLink() {
		return isLink;
	}

	public void setIsLink(Boolean isLink) {
		this.isLink = isLink;
	}

	private List<Integer> convert(Integer[] arrays) {
		List<Integer> ls = new ArrayList<Integer>(arrays.length);
		for (Integer arr : arrays) {
			ls.add(arr);
		}
		return ls;
	}

	@Override
	protected void deleteMultiRows() throws Exception {

		Object[] deletedObjects = new Object[this.getSelectedOperaRows().length];

		int toBeDeletedRows[] = new int[this.getSelectedOperaRows().length];
		for (int i = 0; i < deletedObjects.length; i++) {
			deletedObjects[i] = this.getData().get(this.getSelectedOperaRows()[i]);
			toBeDeletedRows[i] = this.getSelectedOperaRows()[i];
		}

		int oldSelectedRow = this.getSelectedRow();
		Object oldSelectedData = this.getSelectedData();
		for (Object deletedObject : deletedObjects) {
			this.getData().remove(deletedObject);
			this.convert(this.getSelectedOperaRows()).remove(0);
		}
		this.setSelectedRow(-1);
		this.fireEvent(new AppEvent(AppEventConst.DATA_DELETED, this, new RowOperationInfo(toBeDeletedRows, deletedObjects)));

		int oldSelectedDataIndex = this.findBusinessData(oldSelectedData);

		if (oldSelectedDataIndex == -1) {
			this.setSelectedRow(Math.min(oldSelectedRow, this.getData().size() - 1));
		} else {
			this.setSelectedRow(oldSelectedDataIndex);
		}
	}

	@Override
	protected void deleteSeletedRow() throws Exception {
		// 其次优先删除鼠标选择的记录
		Object deletedData = this.getSelectedData();
		int oldSelectedRow = this.getSelectedRow();
		if (deletedData != null) {
			this.getService().delete(deletedData);
			this.getData().remove(deletedData);
			this.setSelectedRow(-1);
			this.fireEvent(new AppEvent(AppEventConst.DATA_DELETED, this, new RowOperationInfo(oldSelectedRow, deletedData)));
			this.setSelectedRow(Math.min(oldSelectedRow, this.getData().size() - 1));
		}
	}

	IArapBillService getTempBillService() {
		return NCLocator.getInstance().lookup(IArapBillService.class);
	}

	private int bodyRow = -1;

	public int getBodyRow() {
		return bodyRow;
	}

	public void setBodyRow(int bodyRow) {
		this.bodyRow = bodyRow;
	}

	/** 交易类型 */
	private String transtype = "";

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	@Override
	public void setSelectedRowWithoutEvent(int selectedRow) {
		if (getSelectedRow() == selectedRow) { // 设置值和目标值重复，不再进行设置
		}
		setSelectedOldRow(getSelectedRow());
		super.setSelectedRowWithoutEvent(selectedRow);
	}

	@Override
	public void setSelectedRow(int selectedRow) {
		if (getSelectedRow() == selectedRow) { // 设置值和目标值重复，不再进行设置， 直接发送事件
			fireEvent(new AppEvent(AppEventConst.SELECTION_CHANGED, this, null));
			return;
		}
		setSelectedOldRow(getSelectedRow());
		super.setSelectedRow(selectedRow);
		refresh(selectedRow);
	}

	private int selectedOldRow = -1;

	public int getSelectedOldRow() {
		return selectedOldRow;
	}

	public void setSelectedOldRow(int selectedOldRow) {
		this.selectedOldRow = selectedOldRow;
	}

	@Override
	public void directlyAdd(Object obj) {
		super.directlyAdd(obj);
		setSelectedOperaRows(new int[] { getSelectedRow() });
	}

	public void directlyDelete(Object obj) throws Exception {
		if (getSelectedRow() == 0) {
			setSelectedOperaRows(new int[] {});
		} else {
			setSelectedOperaRows(new int[] { getSelectedRow() - 1 });
		}

		super.directlyDelete(obj);
	}

	public void directlyDelete(Object[] objs) throws Exception {
		if (getSelectedRow() == 0) {
			if (getData().size() == 1) {
				setSelectedOperaRows(new int[] {});
			} else {
				setSelectedOperaRows(new int[] { getData().size() - 1 });
			}
		} else {
			setSelectedOperaRows(new int[] { getSelectedRow() - 1 });
		}

		super.directlyDelete(objs);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addSelectedOperaRow(int selectedRow) {
		super.addSelectedOperaRow(selectedRow);
		refresh(selectedRow);
	}

	/**
	 * @param selectedRow
	 */
	private void refresh(int selectedRow) {
		if (getData() == null && getData().size() == 0 && selectedRow >= 0 && selectedRow < getData().size()) {
			return;
		}
		Object bill = getData().get(selectedRow);
		ArapBillVOUtils.dealVoScale(bill);
		ArapBillUIUtil.refreshChildVO2HeadVO((AggregatedValueObject) bill);
	}
	
	private void refresh(int[] selectedRows){
		if (getData() == null && getData().size() == 0 && selectedRows.length == 0) {
			return;
		}
		Object[] bills = new Object[selectedRows.length];
		for(int i = 0; i < selectedRows.length; i ++){
			bills[i] = getData().get(selectedRows[i]);
		}
		ArapBillVOUtils.dealVoScale(bills);
		ArapBillUIUtil.refreshChildVO2HeadVO(bills);
	}
	
	@Override
	public void setSelectedOperaRows(int[] selectedRows) {
		try {
			lazilyLoadChildren(selectedRows);
		} catch (BusinessException e) {
			throw new BusinessRuntimeException(e.getMessage(), e);
		}
		super.setSelectedOperaRows(selectedRows);
		refresh(selectedRows);
	}
	
	/**
	 * 通过被选中的单据列表，根据主表批量查询字表
	 * @param dataIndex 被选中的单据列表
	 * @throws BusinessException
	 */
	private void lazilyLoadChildren(int[] dataIndex) throws BusinessException{
		if (dataIndex == null || dataIndex.length == 0){
			return;
		}
		Map<String,AggregatedValueObject> aggMap = new HashMap<String,AggregatedValueObject>();
		for (int i = 0; i < dataIndex.length; i++) {       // 过滤已经存在子VO的单据
			Object data = getData().get(dataIndex[i]);
			if (super.isSupportLazilyLoad() && (data instanceof AggregatedValueObject)
					&& (null == ((AggregatedValueObject) data).getChildrenVO())) {
				AggregatedValueObject aggObj = (AggregatedValueObject) data;
				String primaryKey = aggObj.getParentVO().getPrimaryKey();
				aggMap.put(primaryKey, aggObj);
			} 
		}
		if (aggMap.size() == 0)
			return;
		
		BillAppModelService billAppModelService = (BillAppModelService) getService();
		BaseItemVO[] items;
		try {
			items = billAppModelService.queryChildrenByParentPks(aggMap.keySet().toArray(new String[]{}));  // 远程调用，批量查询
		} catch (BusinessException e) {
            Logger.error("懒加载子表信息发生异常", e);
            throw new BusiBeanRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("pubapp_0", "0pubapp-0141"), e);
		}
		LinkedMultiValueMap<String, BaseItemVO> multiMap = new LinkedMultiValueMap<>();
		for (int i = 0; i < items.length; i++) {
			multiMap.add(items[i].getParentPK(), items[i]);   // 根据父VO分组
		}
		for (String parentPK : aggMap.keySet()){
			aggMap.get(parentPK).setChildrenVO(multiMap.get(parentPK).toArray(new BaseItemVO[]{}));
		}
	}
	
	//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
	public void directSingleUpdate2(Object obj) {
		// 列表批量审批时，先过滤了一部分数据，当最后只有一条数据能成功时，无法刷新界面上的这条数据。
		int i = findBusinessData(obj);
		fireEvent(new AppEvent(AppEventConst.DATA_UPDATED, this, new RowOperationInfo(i, obj)));
	}
	//add end
}
