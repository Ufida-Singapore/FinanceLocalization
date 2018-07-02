package nc.vo.arap.pub.enumeration;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * 求和、求平均字段
 * Add by bl 2015-04-22
 */
public enum PayBillCombindefrule {

	LOCAL_MONEY_DE(PayBillItemVO.LOCAL_MONEY_DE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("paybill", "2paybill-000013")/* 组织本币金额(借方)*/),	
	MONEY_DE(PayBillItemVO.MONEY_DE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000105")/* 借方原币金额*/),
	GROUPDEBIT(PayBillItemVO.GROUPDEBIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000007")/* 集团本币金额(借方) */),
	GLOBALDEBIT(PayBillItemVO.GLOBALDEBIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000005")/* 全局本币金额(借方) */),
	GROUPBALANCE(PayBillItemVO.GROUPBALANCE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001014")/* 集团本币余额 */),
	GLOBALBALANCE(PayBillItemVO.GLOBALBALANCE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000117")/* 全局本币余额 */),
	GROUPNOTAX_DE(PayBillItemVO.GROUPNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000042")/* 集团本币无税金额(借方) */),
    OCCUPATIONMNY(PayBillItemVO.OCCUPATIONMNY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000004")/* 预占用核销原币余额 */),
	LOCAL_MONEY_BAL(PayBillItemVO.LOCAL_MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000698")/* 组织本币余额 */),
	QUANTITY_BAL(PayBillItemVO.QUANTITY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000460")/* 数量余额 */),
	LOCAL_TAX_DE(PayBillItemVO.LOCAL_TAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000018")/* 税额 */),
	NOTAX_DE(PayBillItemVO.NOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000030")/* 借方原币无税金额 */),
	LOCAL_NOTAX_DE(PayBillItemVO.LOCAL_NOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000026")/* 组织本币无税金额(借方) */),
	AGENTRECEIVEPRIMAL(PayBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000010")/* 托收原币金额 */),
	AGENTRECEIVELOCAL(PayBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000001")/* 托收组织本币 */),
	GROUPAGENTRECEIVELOCAL(PayBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000009")/* 托收集团本币 */),
	GLOBALAGENTRECEIVELOCAL(PayBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000048")/* 托收全局本币 */),
	GLOBALNOTAX_DE(PayBillItemVO.GLOBALNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000029")/* 全局本币无税金额(借方) */),
	QUANTITY_DE(PayBillItemVO.QUANTITY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000106")/* 借方数量 */),
	QUANTITY_CR(PayBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000928")/* 贷方数量 */),
	MONEY_BAL(PayBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000207")/* 原币余额 */),
	POSTQUANTITY(PayBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000412")/* 报价单位数量 */);

	// 单据类型
	private String key;

	// 单据名称
	private String name;

	private PayBillCombindefrule(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getKey() {
		return this.key;
	}

	public String getName() {
		return this.name;
	}

}