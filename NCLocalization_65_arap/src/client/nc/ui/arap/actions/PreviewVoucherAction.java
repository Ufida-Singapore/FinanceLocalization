package nc.ui.arap.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

import nc.bs.framework.common.NCLocator;
import nc.itf.arap.bill.IArapBillService;
import nc.ui.arap.model.ArapBillManageModel;
import nc.ui.pub.link.DesBillGenerator;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.payable.CombinCacheVO;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.BusinessException;

public class PreviewVoucherAction extends NCAction {

	private static final long serialVersionUID = 739515066551728238L;

	private BillManageModel model;
	private BillForm editor;
	
	public PreviewVoucherAction() {
		super();
		setCode("PreVoucher");
		setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0021"));
	}
	
	@Override
	public void doAction(ActionEvent arg0) throws Exception {
		Object[] vos = getModel().getSelectedOperaDatas();
		
		//卡片页面   合并或者明细合并后   weiningc  20171019  start
		if(vos == null) {
			Object vos_card = ((BillForm) editor).getValue() == null ? ((BillForm) editor).getModel().getSelectedData()
					: ((BillForm) editor).getValue();
			vos = new Object[] {vos_card};
		
		}
		if (vos == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("expensepub_0", "0expense-000005")/*
																																 * @
																																 * res
																																 * "请选中单据后再进行预览的操作!"
																																 */);
		}
		//合并显示不支持保存态预览凭证
		if(model instanceof ArapBillManageModel) {
			Boolean combineflag = ((ArapBillManageModel) model).getCombineFlag();
			if(combineflag) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0020")/*
																																 * @
																																 * res
																																 * "合并打印状态下无法预览凭证!"
																																 */);
			}
		}

		BaseAggVO[] selectedvos = Arrays.asList(vos).toArray(new BaseAggVO[0]);
		try {
			Collection<FipMessageVO[]> messagevos = NCLocator.getInstance().lookup(IArapBillService.class).previewFillMessage(selectedvos);
			DesBillGenerator.previewDesBill(getModel().getContext().getEntranceUI(), messagevos.toArray(new FipMessageVO[0][0]), null, new String[] { "C0" });
		} catch (Exception e) {
			if (e instanceof java.lang.reflect.InvocationTargetException) {
				ExceptionHandler.handleException((Exception) ((java.lang.reflect.InvocationTargetException) e).getTargetException());
			} else {
				ExceptionHandler.handleException(e);
			}
		}
		
//		Collection<FipMessageVO[]> messagevos = new ArrayList<FipMessageVO[]>();
//		try {
//			for (BaseAggVO vo : selectedvos) {
//				FipRelationInfoVO srcinfovo = new FipRelationInfoVO();
//				BaseBillVO parentVO = (BaseBillVO)vo.getParentVO();
//				srcinfovo.setPk_group(parentVO.getPk_group());
//				srcinfovo.setPk_org(parentVO.getPk_org());
//				srcinfovo.setRelationID(parentVO.getPrimaryKey());
//				srcinfovo.setPk_billtype(parentVO.getPk_tradetype());
//				srcinfovo.setPk_system(PfDataCache.getBillTypeInfo(parentVO.getPk_group(), parentVO.getPk_tradetype()).getSystemcode());
//				
//				
//				srcinfovo.setPk_operator(parentVO.getBillmaker());
//				srcinfovo.setFreedef1(parentVO.getBillno());
//				srcinfovo.setFreedef2(parentVO.getScomment());
//				UFDouble total = parentVO.getMoney();
//				total = total.setScale(Currency.getCurrDigit(parentVO.getPk_currtype()), UFDouble.ROUND_HALF_UP);
//				srcinfovo.setFreedef3(String.valueOf(total));
//				
//				FipMessageVO fipvo = new FipMessageVO();
//				fipvo.setBillVO(vo);
//				fipvo.setMessageinfo(srcinfovo);
//				messagevos.add(new FipMessageVO[]{fipvo});
//			}
//
//			DesBillGenerator.previewDesBill(getModel().getContext().getEntranceUI(), messagevos.toArray(new FipMessageVO[0][0]), null, new String[] { "C0" });
//		} catch (Exception ex) {
//			if (ex instanceof java.lang.reflect.InvocationTargetException) {
//				ExceptionHandler.handleException((Exception) ((java.lang.reflect.InvocationTargetException) ex).getTargetException());
//			} else {
//				ExceptionHandler.handleException(ex);
//			}
//		}
	}

	@Override
	protected boolean isActionEnable() {
		BaseAggVO selectedData = (BaseAggVO) getModel().getSelectedData();

		if (selectedData == null) {
			return false;
		}

		if (((BaseBillVO)selectedData.getParentVO()).getBillstatus()!=null) {
			return true;
		}

		return false;
	}
	
	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

	public BillForm getEditor() {
		return editor;
	}

	public void setEditor(BillForm editor) {
		this.editor = editor;
	}
}
