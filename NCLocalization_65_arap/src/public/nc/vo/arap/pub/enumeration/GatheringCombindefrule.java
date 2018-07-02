package nc.vo.arap.pub.enumeration;

import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * 求和、求平均字段
 * Add by bl 2015-04-22
 */
public enum GatheringCombindefrule {

	LOCAL_MONEY_CR(GatheringBillItemVO.LOCAL_MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000026")/* 组织本币金额(贷方)*/),		
	MONEY_CR(GatheringBillItemVO.MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000927")/* 贷方原币金额*/),
	SETTLEMONEY(GatheringBillItemVO.SETTLEMONEY, NCLangRes4VoTransl.getNCLangRes()
		    .getStrByID("gatherbill", "2gather-000119")/* 应收金额 */), 
	MONEY_BAL(GatheringBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000207")/* 原币余额 */),
	LOCAL_MONEY_BAL(GatheringBillItemVO.LOCAL_MONEY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000698")/* 组织本币余额 */),
	QUANTITY_BAL(GatheringBillItemVO.QUANTITY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000460")/* 数量余额 */),
	LOCAL_TAX_CR(GatheringBillItemVO.LOCAL_TAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000010")/* 税额 */),
	NOTAX_CR(GatheringBillItemVO.NOTAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000043")/* 贷方原币无税金额 */),
	LOCAL_NOTAX_CR(GatheringBillItemVO.LOCAL_NOTAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000032")/* 组织本币无税金额(贷方) */),
	GROUPCREBIT(GatheringBillItemVO.GROUPCREBIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000021")/* 集团本币金额(贷方) */),
	GLOBALCREBIT(GatheringBillItemVO.GLOBALCREBIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000039")/* 全局本币金额(贷方) */),
	GROUPBALANCE(GatheringBillItemVO.GROUPBALANCE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001014")/* 集团本币余额 */),
	GLOBALBALANCE(GatheringBillItemVO.GLOBALBALANCE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000117")/* 全局本币余额 */),
	GROUPNOTAX_CRE(GatheringBillItemVO.GROUPNOTAX_CRE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000019")/* 集团本币无税金额(贷方) */),
	GLOBALNOTAX_CRE(GatheringBillItemVO.GLOBALNOTAX_CRE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000055")/* 全局本币无税金额(贷方) */),
	OCCUPATIONMNY(GatheringBillItemVO.OCCUPATIONMNY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000017")/* 预占用原币余额 */),
	AGENTRECEIVEPRIMAL(GatheringBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000004")/* 托收原币金额 */),
	AGENTRECEIVELOCAL(GatheringBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000002")/* 托收组织本币 */),
	GROUPAGENTRECEIVELOCAL(GatheringBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000003")/* 托收集团本币 */),
    GLOBALAGENTRECEIVELOCAL(GatheringBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000051")/* 托收全局本币 */),
	POSTQUANTITY(GatheringBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000412")/* 报价单位数量 */), 
	QUANTITY_CR(GatheringBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000928")/* 贷方数量 */);
					
			
			
			
  // 单据类型
  private String key;

  // 单据名称
  private String name;

  private GatheringCombindefrule(String key, String name) {
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
