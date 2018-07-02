package nc.ui.arap.query;

import java.awt.Container;

import nc.ui.arap.bill.ArapBillUIUtil;
import nc.ui.pub.pf.BillSourceVar;
import nc.ui.pubapp.billref.src.DefaultBillReferQuery;
import nc.ui.pubapp.uif2app.query2.QueryConditionDLGDelegator;
import nc.ui.pubapp.uif2app.query2.totalvo.QueryConditionVODealer;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.arap.paybill.RefAdd4EditUserObj;
import nc.vo.pub.BusinessException;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;
import nc.vo.querytemplate.TemplateInfo;

@SuppressWarnings("restriction")
public class ArapBillReferQuery extends DefaultBillReferQuery {

	public ArapBillReferQuery(Container c, TemplateInfo info) {
		super(c, info);
	}

	protected void initQueryConditionDLG(QueryConditionDLGDelegator dlgDelegator) {

		BillSourceVar billSrcVar2 = getBillSrcVar();
		String billType = billSrcVar2.getBillType();
		
		dlgDelegator.getQueryConditionDLG().getQryCondEditor().setDefQCVOProcessor(new ArapDefQCVOProcessor(dlgDelegator.getTempInfo()));
		BillManageModel mngmodel=(BillManageModel) ((BillForm)getContain()).getModel();
		String[] pkorgs = mngmodel.getContext().getPkorgs();
		
		mngmodel.getContext().setPkorgs(pkorgs);
		ArapBillUIUtil.initQueryConditionDLG(dlgDelegator, billSrcVar2.getFunNode(),mngmodel,billType);
		
		ArapBillQueryConditionDLGInitializer.initTemplateSpecial(dlgDelegator);
		
		//add chenth 20170219 新加坡项目 支持参照新增或修改的编辑界面可以再次参照数据
				Object userObj = billSrcVar2.getUserObj();
				if(userObj != null && userObj instanceof RefAdd4EditUserObj){
					RefAdd4EditUserObj refUserObj = (RefAdd4EditUserObj)userObj;
					QueryTempletTotalVO totalVO = null;
					try {
						totalVO = dlgDelegator.getQueryConditionDLG().getQryCondEditor().getTotalVO();
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RefAdd4EditCondVODeal dealDefaultValue = new RefAdd4EditCondVODeal();
					dealDefaultValue.setDefaultValue(null, "pk_org", refUserObj.getPk_org());
					dealDefaultValue.setDefaultValue(null, "bodys.supplier", refUserObj.getPk_supplier());
					dealDefaultValue.setDefaultValue(null, "bodys.customer", refUserObj.getPk_customer());
					QueryConditionVODealer queryCondVODealer = new QueryConditionVODealer();
					queryCondVODealer.addDealQueryCondVO(dealDefaultValue);
					QueryConditionVO[] conds = totalVO.getConditionVOs();
					queryCondVODealer.deal(conds);
					totalVO.setConditionVOs(conds);
				}
				//add end
	}

}
