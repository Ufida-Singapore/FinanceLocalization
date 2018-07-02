package nc.ui.arap.billref;

import java.util.ArrayList;
import java.util.List;

import nc.bs.arap.util.ArapPermissionUtils;
import nc.bs.framework.common.NCLocator;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.arap.receivable.IArapReceivableBillQueryService;
import nc.ui.arap.bill.ArapBillUIUtil;
import nc.ui.pub.pf.BillSourceVar;
import nc.ui.pubapp.billref.src.view.SourceRefDlg;
import nc.ui.pubapp.uif2app.query2.model.IRefQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.paybill.RefAdd4EditUserObj;
import nc.vo.arap.pub.BillEnumCollection.InureSign;
import nc.vo.arap.receivable.AggReceivableBillVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("restriction")
public class FOQueryServiceImpl implements IRefQueryService {

	private SourceRefDlg dlg;
	
	public SourceRefDlg getDlg() {
		return dlg;
	}

	public void setDlg(SourceRefDlg dlg) {
		this.dlg = dlg;
	}

	@Override
	public Object[] queryByWhereSql(String whereSql) throws Exception {
		String effectstatus = IBillFieldGet.EFFECTSTATUS;
		if (StringUtils.isEmpty(whereSql))
			whereSql = effectstatus + "=" + InureSign.OKINURE.VALUE;
		else
			whereSql += " and " + effectstatus + "=" + InureSign.OKINURE.VALUE;
		whereSql = whereSql + " and dr = 0 and isinit = 'N' ";
		whereSql = whereSql + getBalanceSql();
		// 增加判断权限的sql
		whereSql = whereSql + ArapPermissionUtils.getDataPermissionSql("F0");
		// whereSql += " and "+
		// IBillFieldGet.getInstance().getFieldName(IBillFieldGet.MONEY_BAL)
		// + " != 0 ";
		AggReceivableBillVO[] vos = NCLocator.getInstance().lookup(IArapReceivableBillQueryService.class).queryVOsByWhere(whereSql);
		ArapBillUIUtil.refreshChildVO2HeadVO(vos);
		
		//add chenth 20170221新加坡项目 支持参照新增或修改的编辑界面可以再次参照数据
		AggregatedValueObject[] newvos = processVOs(vos);
		//add end
		
		return newvos;
	}

	private String getBalanceSql() {
		String wherePart = "not exists (" + "select ar_recitem.pk_recitem from ar_recitem ar_recitem " + "where ar_recbill.pk_recbill = ar_recitem.pk_recbill and "
				+ "(money_bal = 0 or occupationmny = 0))";
		return " and " + wherePart;
	}

	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme) throws Exception {
		List<String> pk_busitypes = ((nc.ui.pubapp.billref.src.DefaultBillReferQuery)this.getDlg().getQueyDlg()).getBusitypes();
		
		if(((nc.ui.pubapp.billref.src.DefaultBillReferQuery)this.getDlg().getQueyDlg()).getBillSrcVar().getBillType().equals(IBillFieldGet.F0)){
			BilltypeVO vo = PfDataCache.getBillType(((nc.ui.pubapp.billref.src.DefaultBillReferQuery)this.getDlg().getQueyDlg()).getBillSrcVar().getCurrBillOrTranstype());
			if(vo.getPk_billtypecode().equals(IBillFieldGet.F3) || vo.getParentbilltype().equals(IBillFieldGet.F3)){
				queryScheme.put(IBillFieldGet.ISREDAR, UFBoolean.valueOf(true));
			}
		}
		
		queryScheme.put(IBillFieldGet.PK_BUSITYPE, pk_busitypes);
		AggReceivableBillVO[] vos = NCLocator.getInstance().lookup(IArapReceivableBillQueryService.class).queryViewVOsByScheme(queryScheme);
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

}
