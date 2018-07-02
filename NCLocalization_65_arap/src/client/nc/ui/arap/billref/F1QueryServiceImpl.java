package nc.ui.arap.billref;

import java.util.ArrayList;
import java.util.List;

import nc.bs.arap.util.ArapPermissionUtils;
import nc.bs.framework.common.NCLocator;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.arap.payable.IArapPayableBillQueryService;
import nc.ui.arap.bill.ArapBillUIUtil;
import nc.ui.pub.pf.BillSourceVar;
import nc.ui.pubapp.billref.src.view.SourceRefDlg;
import nc.ui.pubapp.uif2app.query2.model.IRefQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.payable.AggPayableBillVO;
import nc.vo.arap.payable.PayableBillItemVO;
import nc.vo.arap.paybill.RefAdd4EditUserObj;
import nc.vo.arap.pub.BillEnumCollection.InureSign;
import nc.vo.pub.AggregatedValueObject;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("restriction")
public class F1QueryServiceImpl implements IRefQueryService{
	
	
	private SourceRefDlg dlg;
	@Override
	public Object[] queryByWhereSql(String whereSql) throws Exception {
		String effectstatus = IBillFieldGet.EFFECTSTATUS;
		if (StringUtils.isEmpty(whereSql))
			whereSql = effectstatus + "=" + InureSign.OKINURE.VALUE;
		else
			whereSql += " and " + effectstatus + "=" + InureSign.OKINURE.VALUE;
		whereSql = whereSql + " and dr = 0 and isinit = 'N' ";
		whereSql += getBalanceSql();
		//增加判断权限的sql
		whereSql =  whereSql + ArapPermissionUtils.getDataPermissionSql("F1");
		
		AggPayableBillVO[] vos =  NCLocator.getInstance().lookup(IArapPayableBillQueryService.class).queryVOsByWhere(whereSql);
		ArapBillUIUtil.refreshChildVO2HeadVO(vos);
		
		//add chenth 20170221新加坡项目 支持参照新增或修改的编辑界面可以再次参照数据
		AggregatedValueObject[] newvos = processVOs(vos);
		//add end
				
		return newvos;
	}
	

	private String getBalanceSql() {
		String wherePart = "not exists (" +
						"select ap_payableitem.pk_payableitem from ap_payableitem ap_payableitem " +
						"where ap_payablebill.pk_payablebill= ap_payableitem.pk_payablebill and " +
						"( money_bal = 0 or occupationmny = 0 ))";
		return " and " + wherePart;
	}


	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme) throws Exception {
		List<String> pk_busitypes = ((nc.ui.pubapp.billref.src.DefaultBillReferQuery)this.getDlg().getQueyDlg()).getBusitypes();
		queryScheme.put(IBillFieldGet.PK_BUSITYPE, pk_busitypes);
		AggPayableBillVO[] vos =  NCLocator.getInstance().lookup(IArapPayableBillQueryService.class).queryViewVOsByScheme(queryScheme);
		ArapBillUIUtil.refreshChildVO2HeadVO(vos);
		
		//add chenth 20170221新加坡项目 支持参照新增或修改的编辑界面可以再次参照数据
		AggregatedValueObject[] newvos = processVOs(vos);
		//add end
		
		return newvos;
	}


	/**
	 * add chenth 20170221新加坡项目 支持参照新增或修改的编辑界面可以再次参照数据
	 * @param vos
	 * @return
	 */

	private AggregatedValueObject[] processVOs(AggregatedValueObject[] vos) {
		BillSourceVar bsVar = this.getDlg().getBillSourceVar();
		if(vos != null && vos.length > 0
				&& bsVar != null && bsVar.getUserObj() != null 
				&& bsVar.getUserObj() instanceof RefAdd4EditUserObj){
			RefAdd4EditUserObj refUserObj = (RefAdd4EditUserObj)bsVar.getUserObj();
			List<String> pk_items = refUserObj.getPk_items();
			if(pk_items != null && pk_items.size() > 0){
				List<AggregatedValueObject> newVos = new ArrayList<AggregatedValueObject>();
				for(AggregatedValueObject vo : vos){
					BaseItemVO[] oldItems = (BaseItemVO[]) vo.getChildrenVO();
					List<BaseItemVO> newItems = new ArrayList<BaseItemVO>();
					for(BaseItemVO itemVo : oldItems){
						if(!pk_items.contains(itemVo.getPrimaryKey())){
							newItems.add(itemVo);
						}
					}
					if(newItems.size() > 0){
						vo.setChildrenVO(newItems.toArray(new BaseItemVO[newItems.size()]));
						newVos.add(vo);
					}
				}
				return newVos.toArray(new AggregatedValueObject[newVos.size()]);
			}
		}
		return vos;
	}


	public SourceRefDlg getDlg() {
		return dlg;
	}


	public void setDlg(SourceRefDlg dlg) {
		this.dlg = dlg;
	}

}
