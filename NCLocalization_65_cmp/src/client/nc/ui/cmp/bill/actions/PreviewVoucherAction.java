package nc.ui.cmp.bill.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.cmp.fieldmap.IBillFieldGet;
import nc.pubitf.bbd.CurrtypeQuery;
import nc.pubitf.cmp.settlement.ICmpSettlementPubQueryService;
import nc.ui.pub.link.DesBillGenerator;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.cmp.bill.AbstractBaseBillBodyVO;
import nc.vo.cmp.bill.AbstractBaseBillVO;
import nc.vo.cmp.bill.AssembleBillVO;
import nc.vo.cmp.bill.BillAggVO;
import nc.vo.cmp.bill.BillVO;
import nc.vo.cmp.bill.ChildrenAttribute2Parent;
import nc.vo.cmp.bill.RecBillAggVO;
import nc.vo.cmp.bill.RecBillVO;
import nc.vo.cmp.bill.TransformBillVO;
import nc.vo.cmp.cash.CashDepositVO;
import nc.vo.cmp.cash.CashDrawVO;
import nc.vo.cmp.curexchange.CurExchangeVO;
import nc.vo.cmp.pub.constant.CmpFieldConstant;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.cmp.util.StringUtils;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;

public class PreviewVoucherAction extends NCAction {

	private static final long serialVersionUID = 739515066551728238L;

	private BillManageModel model;
	private BillForm editor;
	
	public PreviewVoucherAction() {
		super();
		setCode("PreVoucher");
		setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("cmpzhtx", "CMPbuttons_001"));
	}
	
	@Override
	public void doAction(ActionEvent arg0) throws Exception {
		Object[] vos = (Object[]) getModel().getSelectedOperaDatas();

		if (vos == null || vos.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("expensepub_0", "0expense-000005")/*
																																 * @
																																 * res
																																 * "请选中单据后再进行预览的操作!"
																																 */);
		}

		AggregatedValueObject[] selectedvos = Arrays.asList(vos).toArray(new AggregatedValueObject[0]);

		Collection<FipMessageVO[]> messagevos = new ArrayList<FipMessageVO[]>();
		try {
			for (AggregatedValueObject vo : selectedvos) {
				FipRelationInfoVO srcinfovo = new FipRelationInfoVO();
				SuperVO headvo = (SuperVO)vo.getParentVO();
				
				String pk_tradetype = null;
				if(headvo instanceof CashDepositVO || headvo instanceof CashDrawVO) //现金缴存单、现金支取单
					pk_tradetype = (String) headvo.getAttributeValue(CmpFieldConstant.BILLTYPECODE);
				else if(headvo instanceof CurExchangeVO || headvo instanceof TransformBillVO)	//外币兑换、划款结算
					pk_tradetype = (String) headvo.getAttributeValue("pk_billtypecode");
				else
					pk_tradetype = (String) headvo.getAttributeValue(IBillFieldGet.PK_TRADETYPE);
					
				srcinfovo.setPk_group((String) headvo.getAttributeValue(CmpFieldConstant.PK_GROUP));
				srcinfovo.setPk_org((String) headvo.getAttributeValue(CmpFieldConstant.PK_ORG));
				srcinfovo.setRelationID(headvo.getPrimaryKey());
				srcinfovo.setPk_billtype(pk_tradetype);
				srcinfovo.setPk_system(PfDataCache.getBillType(pk_tradetype).getSystemcode());
				
				
				srcinfovo.setPk_operator((String) headvo.getAttributeValue(CmpFieldConstant.BILLMAKER));
				String billno = null;
				if(headvo instanceof CashDepositVO || headvo instanceof CashDrawVO) //现金缴存单、现金支取单
					billno = (String) headvo.getAttributeValue(CmpFieldConstant.BILLNO);
				else if(headvo instanceof CurExchangeVO || headvo instanceof TransformBillVO)	//外币兑换、划款结算
					billno = (String) headvo.getAttributeValue("vbillno");
				else
					billno = (String) headvo.getAttributeValue("bill_no");
				srcinfovo.setFreedef1(billno);
				
				String memo = (String) headvo.getAttributeValue(CmpFieldConstant.MEMO);
				if (StringUtils.isNotNullWithTrim(memo) && memo.length() > 50) {
					srcinfovo.setFreedef2(memo.substring(0, 50));
				} else {
					srcinfovo.setFreedef2(memo);
				}
				
				String pk_curr = null;
				
				if(headvo instanceof CashDepositVO || headvo instanceof CashDrawVO) //现金缴存单、现金支取单
					pk_curr = (String) headvo.getAttributeValue(CmpFieldConstant.PK_CURRENCY);
				else
					pk_curr = (String) headvo.getAttributeValue(IBillFieldGet.PK_CURRTYPE);
				int currdigit = 2;
				try {
					currdigit = CurrtypeQuery.getInstance().getCurrdigit(pk_curr);
				} catch (Exception e) {
					
				}

				UFDouble sendmoney = UFDouble.ZERO_DBL;
				UFDouble money = null;
				if(headvo instanceof CashDepositVO || headvo instanceof CashDrawVO) //现金缴存单、现金支取单
					money = (UFDouble) headvo.getAttributeValue(IBillFieldGet.MONEY);
				else if(headvo instanceof CurExchangeVO)	//外币兑换
					money = (UFDouble) headvo.getAttributeValue("buyolcamount");
				else if(headvo instanceof TransformBillVO)	//划款结算
					money = (UFDouble) headvo.getAttributeValue("amount");
				else	
					money = (UFDouble) headvo.getAttributeValue("primal_money");
				if (money != null) {
					sendmoney = money;
				}
				sendmoney = sendmoney.setScale(currdigit, UFDouble.ROUND_HALF_UP);
				srcinfovo.setFreedef3(sendmoney.toString());
				
				FipMessageVO fipvo = new FipMessageVO();
				
				//收款结算录入、付款结算录入特殊处理
				if(headvo instanceof BillVO){
					BillAggVO clonebusiagg = new BillAggVO();
					SuperVO clonehead = (SuperVO) vo.getParentVO().clone();
					SuperVO[] items = (SuperVO[]) vo.getChildrenVO();
					nc.vo.cmp.bill.AssembleBillVO childitem = null;
					List<SuperVO> childrenList = new ArrayList<SuperVO>();
					for (CircularlyAccessibleValueObject item : items) {
						childitem = (AssembleBillVO) item.clone();
						childrenList.add(childitem);

					}
					clonebusiagg.setParentVO(clonehead);
					clonebusiagg.setChildrenVO(childrenList.toArray(new SuperVO[0]));
					fipvo.setBillVO(getBillSettleCombineVO(clonebusiagg));
				}
				else if(headvo instanceof RecBillVO){
					RecBillAggVO clonebusiagg = new RecBillAggVO();
					SuperVO clonehead = (SuperVO) vo.getParentVO().clone();
					SuperVO[] items = (SuperVO[]) vo.getChildrenVO();
					nc.vo.cmp.bill.AssembleBillVO childitem = null;
					List<SuperVO> childrenList = new ArrayList<SuperVO>();
					for (CircularlyAccessibleValueObject item : items) {
						childitem = (AssembleBillVO) item.clone();
						childrenList.add(childitem);

					}
					clonebusiagg.setParentVO(clonehead);
					clonebusiagg.setChildrenVO(childrenList.toArray(new SuperVO[0]));
					fipvo.setBillVO(getBillSettleCombineVO(clonebusiagg));
				}
				else
					fipvo.setBillVO(vo);
				fipvo.setMessageinfo(srcinfovo);
				messagevos.add(new FipMessageVO[]{fipvo});
			}

			DesBillGenerator.previewDesBill(getModel().getContext().getEntranceUI(), messagevos.toArray(new FipMessageVO[0][0]), null, new String[] { "C0" });
		} catch (Exception ex) {
			if (ex instanceof java.lang.reflect.InvocationTargetException) {
				ExceptionHandler.handleException((Exception) ((java.lang.reflect.InvocationTargetException) ex).getTargetException());
			} else {
				ExceptionHandler.handleException(ex);
			}
		}
	}

	private AggregatedValueObject getBillSettleCombineVO(AggregatedValueObject busiagg) throws BusinessException {

		SuperVO paretVO = (SuperVO) busiagg.getParentVO();
		String billPk = paretVO.getPrimaryKey();

		ICmpSettlementPubQueryService cmpSettlementPubQueryService = NCLocator.getInstance().lookup(ICmpSettlementPubQueryService.class);
		SettlementAggVO[] settlementAggVOs = cmpSettlementPubQueryService.queryBillsBySourceBillID(new String[] { billPk });
		for (SettlementAggVO settlementAggVO : settlementAggVOs) {
			SettlementHeadVO settlementHeadVO = (SettlementHeadVO) settlementAggVO.getParentVO();
			if (billPk.equals(settlementHeadVO.getPk_busibill())) {

				if (paretVO instanceof AbstractBaseBillVO) {
					((AbstractBaseBillVO) paretVO).setSettleHead(settlementHeadVO);
				}

				SuperVO[] billdetailVOs = (SuperVO[]) busiagg.getChildrenVO();
				List<SuperVO> list = new ArrayList<SuperVO>();
				list.addAll(Arrays.asList(billdetailVOs));

				SettlementBodyVO[] settlementBodyVOs = (SettlementBodyVO[]) settlementAggVO.getChildrenVO();
				for (SettlementBodyVO settlementBodyVO : settlementBodyVOs) {

					try {
						if (billdetailVOs.length > 0) {
							AbstractBaseBillBodyVO billdetailVO = (AbstractBaseBillBodyVO) billdetailVOs[0].getClass().newInstance();
							billdetailVO.setSettleBody(settlementBodyVO);
							list.add(billdetailVO);
						}
					} catch (Exception e) {
						throw ExceptionHandler.createException(e);
					}
				}

				busiagg.setChildrenVO(list.toArray(new SuperVO[] {}));
				break;
			}
		}
		
		ChildrenAttribute2Parent.setChildrenAttribute2Parent((AbstractBill) busiagg);
		return busiagg;
	}
	
	@Override
	protected boolean isActionEnable() {
		AggregatedValueObject selectedData = (AggregatedValueObject) getModel().getSelectedData();

		if (selectedData == null) {
			return false;
		}

//		if (selectedData.getParentVO().getAttributeValue(CmpFieldConstant.BILLSTATUS)!=null) {
//			return true;
//		}

		return true;
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
