package nc.vo.arap.pub.enumeration;

import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * “待选汇总规则框”中待选项
 * Add by bl 2015-04-22
 */
public enum GatheringCombinRule {

    INVOICENO(GatheringBillItemVO.INVOICENO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000220")/* 发票号 */),
	SCOMMENT(GatheringBillItemVO.SCOMMENT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000426")/* 摘要*/),
	OBJTYPE(GatheringBillItemVO.OBJTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000344")/* 往来对象*/),
	PK_CURRTYPE(GatheringBillItemVO.PK_CURRTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000315")/* 币种 */),
	CUSTOMER(GatheringBillItemVO.CUSTOMER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000283")/* 客户 */),
	PK_DEPTID(GatheringBillItemVO.PK_DEPTID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000972")/* 部门*/),
	PK_PSNDOC(GatheringBillItemVO.PK_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000013")/* 业务员*/),
	PREPAY(GatheringBillItemVO.PREPAY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000037")/* 收款性质*/),	
	PK_RECPAYTYPE("pk_recpaytype", NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000127")/* 收款业务类型*/),
	PRODUCTLINE(GatheringBillItemVO.PRODUCTLINE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000044")/* 产品线*/),
	PK_SUBJCODE(GatheringBillItemVO.PK_SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000442")/* 收支项目*/),
	PAUSETRANSACT(GatheringBillItemVO.PAUSETRANSACT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000047")/* 挂起标志*/),
	SUBJCODE(GatheringBillItemVO.SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000662")/* 科目*/),
	PK_BALATYPE(GatheringBillItemVO.PK_BALATYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000712")/* 结算方式*/),
	CHECKNO(GatheringBillItemVO.CHECKNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000658")/* 票据号*/),	
	CHECKTYPE(GatheringBillItemVO.CHECKTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000660")/* 票据类型*/),
	CASHACCOUNT(GatheringBillItemVO.CASHACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000616")/* 现金账户*/),		
	RECACCOUNT(GatheringBillItemVO.RECACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000446")/* 收款银行账户*/),	
	PAYACCOUNT(GatheringBillItemVO.PAYACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000063")/* 付款银行账户*/),
    PROJECT(GatheringBillItemVO.PROJECT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001021")/* 项目 */), 
	ISREFUSED(GatheringBillItemVO.ISREFUSED, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000005")/* 是否被拒付 */), 
	COSTCENTER(GatheringBillItemVO.COSTCENTER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000105")/* 成本中心 */), 
    PAYREASON(GatheringBillItemVO.PAYREASON, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000113")/* 支付原因 */), 
	PK_GATHERBILL(GatheringBillItemVO.PK_GATHERBILL, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("gatherbill","2gather-000123")/* 客户收款单标识 */), 
	SUPPLIER(GatheringBillItemVO.SUPPLIER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000089")/* 供应商 */), 
	SETTLEMONEY(GatheringBillItemVO.SETTLEMONEY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000119")/* 应收金额 */), 
	SETTLECURR(GatheringBillItemVO.SETTLECURR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000120")/* 应收币种 */), 
	OUTSTORENO(GatheringBillItemVO.OUTSTORENO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000149")/* 出库单号 */), 
	POSTUNIT(GatheringBillItemVO.POSTUNIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000416")/* 报价计量单位 */), 
	POSTPRICENOTAX(GatheringBillItemVO.POSTPRICENOTAX, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000413")/* 报价单位无税单价 */), 
	POSTQUANTITY(GatheringBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000412")/* 报价单位数量 */), 
	POSTPRICE(GatheringBillItemVO.POSTPRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000410")/* 报价单位含税单价 */), 
	CHECKDIRECTION(GatheringBillItemVO.CHECKDIRECTION, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000659")/* 票据方向 */), 
	COORDFLAG(GatheringBillItemVO.COORDFLAG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000185")/* 单据协同状态 */), 
	EQUIPMENTCODE(GatheringBillItemVO.EQUIPMENTCODE, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000892")/* 设备编码 */), 
	CASHITEM(GatheringBillItemVO.CASHITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000615")/* 现金流量项目 */), 
	PAYFLAG(GatheringBillItemVO.PAYFLAG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000437")/* 支付状态 */), 
	BANKROLLPROJET(GatheringBillItemVO.BANKROLLPROJET, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000941")/* 资金计划项目 */), 
	BILLDATE(GatheringBillItemVO.BILLDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000190")/* 单据日期 */), 
	PAYMAN(GatheringBillItemVO.PAYMAN, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000433")/* 支付人 */), 
	PAYDATE(GatheringBillItemVO.PAYDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000435")/* 支付日期 */),
	PK_GROUP(GatheringBillItemVO.PK_GROUP, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000367")/* 所属集团 */),
	PK_BILLTYPE(GatheringBillItemVO.PK_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000197")/* 单据类型编码 */),
	BILLCLASS(GatheringBillItemVO.BILLCLASS, NCLangRes4VoTransl.getNCLangRes()
	        .getStrByID("receivablebill", "2UC000-000187")/* 单据大类 */),
	PK_TRADETYPE(GatheringBillItemVO.PK_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000015")/* 收款类型code */),
	PK_TRADETYPEID(GatheringBillItemVO.PK_TRADETYPEID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000041")/* 收款类型 */),
	PK_GATHERITEM(GatheringBillItemVO.PK_GATHERITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000024")/* 收款单行标识 */),
	QUANTITY_CR(GatheringBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000928")/* 贷方数量 */),
	LOCAL_MONEY_CR(GatheringBillItemVO.LOCAL_MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000026")/* 组织本币金额(贷方) */),
	MONEY_CR(GatheringBillItemVO.MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000927")/* 贷方原币金额 */),
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
	PRICE(GatheringBillItemVO.PRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000175")/* 单价 */),
	TAXPRICE(GatheringBillItemVO.TAXPRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000246")/* 含税单价 */),
	TAXRATE(GatheringBillItemVO.TAXRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000670")/* 税率 */),
	TAXNUM(GatheringBillItemVO.TAXNUM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000669")/* 税号 */),
//	TOP_BILLID(GatheringBillItemVO.TOP_BILLID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000003")/* 上层单据主键 */),
//	TOP_ITEMID(GatheringBillItemVO.TOP_ITEMID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000005")/* 上层单据行主键 */),
//	TOP_BILLTYPE(GatheringBillItemVO.TOP_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000004")/* 上层单据类型 */),
//	TOP_TRADETYPE(GatheringBillItemVO.TOP_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000002")/* 上层交易类型 */),
//	SRC_TRADETYPE(GatheringBillItemVO.SRC_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000572")/* 源头交易类型 */),
//	SRC_BILLTYPE(GatheringBillItemVO.SRC_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000581")/* 源头单据类型 */),
//	SRC_BILLID(GatheringBillItemVO.SRC_BILLID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000575")/* 源头单据主键 */),
//	SRC_ITEMID(GatheringBillItemVO.SRC_ITEMID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000582")/* 源头单据行主键 */),
	BUSIDATE(GatheringBillItemVO.BUSIDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000946")/* 起算日期 */),
	BILLNO(GatheringBillItemVO.BILLNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000186")/* 单据号 */),
	ROWNO(GatheringBillItemVO.ROWNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000183")/* 单据分录号 */),
	PK_PAYTERM(GatheringBillItemVO.PK_PAYTERM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000443")/* 收款协议 */),
	PK_ORG(GatheringBillItemVO.PK_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000031")/* 收款财务组织 */),
	PK_FIORG(GatheringBillItemVO.PK_FIORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000908")/* 财务组织 */),
	PK_PCORG(GatheringBillItemVO.PK_PCORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000161")/* 利润中心 */),
	SETT_ORG(GatheringBillItemVO.SETT_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000011")/* 结算财务组织 */),
	SETT_ORG_V(GatheringBillItemVO.SETT_ORG_V, NCLangRes4VoTransl.getNCLangRes()
		    .getStrByID("gatherbill", "2gather-000025")/* 结算财务组织版本 */),
	PK_PCORG_V(GatheringBillItemVO.PK_PCORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000102")/* 利润中心版本 */),
	PK_ORG_V(GatheringBillItemVO.PK_ORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000000")/* 收款财务组织版本 */),
	PK_FIORG_V(GatheringBillItemVO.PK_FIORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000910")/* 财务组织版本 */),
	SO_ORG(GatheringBillItemVO.SO_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000996")/* 销售组织 */),
	SO_ORG_V(GatheringBillItemVO.SO_ORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000103")/* 销售组织版本 */),
	SO_TRANSTYPE(GatheringBillItemVO.SO_TRANSTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000995")/* 销售渠道类型 */),
	SO_ORDERTYPE(GatheringBillItemVO.SO_ORDERTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001001")/* 销售订单类型 */),
	SO_PSNDOC(GatheringBillItemVO.SO_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000994")/* 销售业务员 */),
	SO_DEPTID(GatheringBillItemVO.SO_DEPTID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001002")/* 销售部门 */),
	ROWTYPE(GatheringBillItemVO.ROWTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000814")/* 行类型 */),
	DIRECTION(GatheringBillItemVO.DIRECTION, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000462")/* 方向 */),
	PK_SSITEM(GatheringBillItemVO.PK_SSITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000033")/* 事项审批单 */),
	RATE(GatheringBillItemVO.RATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000699")/* 组织本币汇率 */),
	ORDERCUBASDOC(GatheringBillItemVO.ORDERCUBASDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000878")/* 订单客户 */),
	INNERORDERNO(GatheringBillItemVO.INNERORDERNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000904")/* 调拨订单号 */),
	ASSETPACTNO(GatheringBillItemVO.ASSETPACTNO, NCLangRes4VoTransl.getNCLangRes()
	        .getStrByID("receivablebill", "2UC000-000929")/* 资产合同号 */),
	CONTRACTNO(GatheringBillItemVO.CONTRACTNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000234")/* 合同号 */),
	FREECUST(GatheringBillItemVO.FREECUST, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000454")/* 散户 */),
	FACARD(GatheringBillItemVO.FACARD, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000023")/* 固定资产卡片号 */),
	PURCHASEORDER(GatheringBillItemVO.PURCHASEORDER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000877")/* 订单号 */),
	GROUPRATE(GatheringBillItemVO.GROUPRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001016")/* 集团本币汇率 */),
	GLOBALRATE(GatheringBillItemVO.GLOBALRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000119")/* 全局本币汇率 */),
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
	PROJECT_TASK(GatheringBillItemVO.PROJECT_TASK, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001023")/* 项目任务 */),
	COMMPAYSTATUS(GatheringBillItemVO.COMMPAYSTATUS, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000401")/* 承付状态 */),
	COMMPAYTYPE(GatheringBillItemVO.COMMPAYTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000402")/* 承付型态 */),
	REFUSE_REASON(GatheringBillItemVO.REFUSE_REASON, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000045")/* 拒付理由 */),
	AGENTRECEIVEPRIMAL(GatheringBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000004")/* 托收原币金额 */),
	AGENTRECEIVELOCAL(GatheringBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000002")/* 托收组织本币 */),
	GROUPAGENTRECEIVELOCAL(GatheringBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000003")/* 托收集团本币 */),
    GLOBALAGENTRECEIVELOCAL(GatheringBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000051")/* 托收全局本币 */),
	COMMPAYER(GatheringBillItemVO.COMMPAYER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000053")/* 承付人 */),
	BANKRELATED_CODE(GatheringBillItemVO.BANKRELATED_CODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000299")/* 对账标识码 */),
	DEF30(GatheringBillItemVO.DEF30, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000772")/* 自定义项30 */),
	DEF29(GatheringBillItemVO.DEF29, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000770")/* 自定义项29 */),
	DEF28(GatheringBillItemVO.DEF28, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000769")/* 自定义项28 */),
	DEF27(GatheringBillItemVO.DEF27, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000768")/* 自定义项27 */),
	DEF26(GatheringBillItemVO.DEF26, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000767")/* 自定义项26 */),
	DEF25(GatheringBillItemVO.DEF25, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000766")/* 自定义项25 */),
	DEF24(GatheringBillItemVO.DEF24, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000765")/* 自定义项24 */),
	DEF23(GatheringBillItemVO.DEF23, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000764")/* 自定义项23 */),
	DEF22(GatheringBillItemVO.DEF22, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000763")/* 自定义项22 */),
	DEF21(GatheringBillItemVO.DEF21, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000762")/* 自定义项21 */),
	DEF20(GatheringBillItemVO.DEF20, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000761")/* 自定义项20 */),
	DEF19(GatheringBillItemVO.DEF19, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000759")/* 自定义项19 */),
	DEF18(GatheringBillItemVO.DEF18, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000758")/* 自定义项18 */),
	DEF17(GatheringBillItemVO.DEF17, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000757")/* 自定义项17 */),
	DEF16(GatheringBillItemVO.DEF16, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000756")/* 自定义项16 */),
	DEF15(GatheringBillItemVO.DEF15, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000755")/* 自定义项15 */),
	DEF14(GatheringBillItemVO.DEF14, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000754")/* 自定义项14 */),
	DEF13(GatheringBillItemVO.DEF13, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000753")/* 自定义项13 */),
    DEF12(GatheringBillItemVO.DEF12, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000752")/* 自定义项12 */),
	DEF11(GatheringBillItemVO.DEF11, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000751")/* 自定义项11 */),
	DEF10(GatheringBillItemVO.DEF10, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000750")/* 自定义项10 */),
	DEF9(GatheringBillItemVO.DEF9, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000788")/* 自定义项9 */),
	DEF8(GatheringBillItemVO.DEF8, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000787")/* 自定义项8 */),
	DEF7(GatheringBillItemVO.DEF7, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000786")/* 自定义项7 */),
    DEF6(GatheringBillItemVO.DEF6, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000785")/* 自定义项6 */),
	DEF5(GatheringBillItemVO.DEF5, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000784")/* 自定义项5 */),
	DEF4(GatheringBillItemVO.DEF4, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000782")/* 自定义项4 */),
	DEF3(GatheringBillItemVO.DEF3, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000771")/* 自定义项3 */),
	DEF2(GatheringBillItemVO.DEF2, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000760")/* 自定义项2 */),
	DEF1(GatheringBillItemVO.DEF1, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000749")/* 自定义项1 */),
	CHECKELEMENT(GatheringBillItemVO.CHECKELEMENT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000912")/* 责任核算要素 */);
			

	
	
	// 单据类型
	private String key;

	// 单据名称
	private String name;

	private GatheringCombinRule(String key, String name) {
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
