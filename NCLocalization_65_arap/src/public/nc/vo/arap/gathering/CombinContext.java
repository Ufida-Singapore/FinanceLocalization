package nc.vo.arap.gathering;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * 合并显示变量 Add by bl 2015-04-24
 */
public class CombinContext {
	/**
	 * 收款单： 组织本币金额(贷方)、贷方原币金额
	 */
	public static final String[] COMBIN_GATHERING = new String[] {
			GatheringBillItemVO.LOCAL_MONEY_CR, GatheringBillItemVO.MONEY_CR,
			GatheringBillItemVO.SETTLEMONEY, GatheringBillItemVO.MONEY_BAL,
			GatheringBillItemVO.LOCAL_MONEY_BAL,
			GatheringBillItemVO.QUANTITY_BAL, GatheringBillItemVO.LOCAL_TAX_CR,
			GatheringBillItemVO.NOTAX_CR, GatheringBillItemVO.LOCAL_NOTAX_CR,
			GatheringBillItemVO.GROUPCREBIT, GatheringBillItemVO.GLOBALCREBIT,
			GatheringBillItemVO.GROUPBALANCE,
			GatheringBillItemVO.GLOBALBALANCE,
			GatheringBillItemVO.GROUPNOTAX_CRE,
			GatheringBillItemVO.GLOBALNOTAX_CRE,
			GatheringBillItemVO.OCCUPATIONMNY,
			GatheringBillItemVO.AGENTRECEIVEPRIMAL,
			GatheringBillItemVO.AGENTRECEIVELOCAL,
			GatheringBillItemVO.GROUPAGENTRECEIVELOCAL,
			GatheringBillItemVO.GLOBALAGENTRECEIVELOCAL,
			GatheringBillItemVO.POSTQUANTITY, GatheringBillItemVO.QUANTITY_CR };

	/**
	 * 付款款： 组织本币金额(借方)、借方原币金额
	 */
	public static final String[] COMBIN_PAYBILL = new String[] {
			PayBillItemVO.LOCAL_MONEY_DE, PayBillItemVO.MONEY_DE,
			PayBillItemVO.GROUPDEBIT, PayBillItemVO.GLOBALDEBIT,
			PayBillItemVO.GROUPBALANCE, PayBillItemVO.GLOBALBALANCE,
			PayBillItemVO.GROUPNOTAX_DE, PayBillItemVO.OCCUPATIONMNY,
			PayBillItemVO.LOCAL_MONEY_BAL, PayBillItemVO.QUANTITY_BAL,
			PayBillItemVO.LOCAL_TAX_DE, PayBillItemVO.NOTAX_DE,
			PayBillItemVO.LOCAL_NOTAX_DE, PayBillItemVO.AGENTRECEIVEPRIMAL,
			PayBillItemVO.AGENTRECEIVELOCAL,
			PayBillItemVO.GROUPAGENTRECEIVELOCAL,
			PayBillItemVO.GLOBALAGENTRECEIVELOCAL,
			PayBillItemVO.GLOBALNOTAX_DE, PayBillItemVO.QUANTITY_DE,
			PayBillItemVO.QUANTITY_CR, PayBillItemVO.MONEY_BAL,
			PayBillItemVO.POSTQUANTITY };
}
