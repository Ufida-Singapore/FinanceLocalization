package nc.vo.arap.pub.enumeration;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * “待选汇总规则框”中待选项
 * Add by bl 2015-04-22
 */
public enum PayBillCombinRule {
	
	INVOICENO(PayBillItemVO.INVOICENO, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000220")/* 发票号 */),		
	SCOMMENT(PayBillItemVO.SCOMMENT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000426")/* 摘要 */),	
    OBJTYPE(PayBillItemVO.OBJTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000344")/* 往来对象*/),	
	SUPPLIER(PayBillItemVO.SUPPLIER, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000089")/* 供应商*/),
	CUSTOMER(PayBillItemVO.CUSTOMER, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000283")/* 客户 */),
	PK_DEPTID(PayBillItemVO.PK_DEPTID, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000972")/* 部门*/),
	PK_PSNDOC(PayBillItemVO.PK_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000013")/* 业务员*/),	
	PK_TRADETYPEID(PayBillItemVO.PK_TRADETYPEID, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("paybill", "2paybill-000126")/* 付款业务类型*/),
	PREPAY(PayBillItemVO.PREPAY, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("paybill", "2paybill-000011")/* 付款性质*/),
	PK_CURRTYPE(PayBillItemVO.PK_CURRTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000315")/* 币种 */),
	CHECKNO(PayBillItemVO.CHECKNO, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000658")/* 票据号*/),	
	CHECKTYPE(PayBillItemVO.CHECKTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000660")/* 票据类型*/),
	CASHACCOUNT(PayBillItemVO.CASHACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000616")/* 现金账户*/),	
	RECACCOUNT(PayBillItemVO.RECACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000446")/* 收款银行账户*/),	
	PAYACCOUNT(PayBillItemVO.PAYACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000063")/* 付款银行账户*/),
	PRODUCTLINE(PayBillItemVO.PRODUCTLINE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000044")/* 产品线*/),
	PAUSETRANSACT(PayBillItemVO.PAUSETRANSACT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("gatherbill", "2gather-000047")/* 挂起标志*/),
	PK_SUBJCODE(PayBillItemVO.PK_SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000442")/* 收支项目*/),
	SUBJCODE(PayBillItemVO.SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000662")/* 科目*/),
	PK_BALATYPE(PayBillItemVO.PK_BALATYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000712")/* 结算方式*/),
	PROJECT(PayBillItemVO.PROJECT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-001021")/* 项目*/),	
	PK_BILLTYPE(PayBillItemVO.PK_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000197")/* 单据类型编码 */),
	BILLCLASS(PayBillItemVO.BILLCLASS, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000187")/* 单据大类 */),
	PK_TRADETYPE(PayBillItemVO.PK_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000050")/* 付款类型code */),
	PK_PAYITEM(PayBillItemVO.PK_PAYITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000047")/* 付款单行标识 */),
	BUSIDATE(PayBillItemVO.BUSIDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000946")/* 起算日期 */),
	BILLNO(PayBillItemVO.BILLNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000186")/* 单据号 */),
	ROWTYPE(PayBillItemVO.ROWTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000814")/* 行类型 */),
	DIRECTION(PayBillItemVO.DIRECTION, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000462")/* 方向 */),
	PK_SSITEM(PayBillItemVO.PK_SSITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000033")/* 事项审批单 */),
	RATE(PayBillItemVO.RATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000699")/* 组织本币汇率 */),
	MONEY_DE(PayBillItemVO.MONEY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000105")/* 借方原币金额 */),
	LOCAL_MONEY_DE(PayBillItemVO.LOCAL_MONEY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000013")/* 组织本币金额(借方) */),
	QUANTITY_DE(PayBillItemVO.QUANTITY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000106")/* 借方数量 */),
	QUANTITY_CR(PayBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000928")/* 贷方数量 */),
	MONEY_BAL(PayBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000207")/* 原币余额 */),
	PU_ORG(PayBillItemVO.PU_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000980")/* 采购组织 */),
	PU_ORG_V(PayBillItemVO.PU_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000100")/* 采购组织版本主键 */),
	SETT_ORG_V(PayBillItemVO.SETT_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000104")/* 结算财务组织版本主键 */),
	PK_PCORG_V(PayBillItemVO.PK_PCORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000102")/* 利润中心版本主键 */),
	PK_ORG_V(PayBillItemVO.PK_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000041")/* 付款财务组织版本主键 */),
	PK_FIORG_V(PayBillItemVO.PK_FIORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000911")/* 财务组织版本主键 */),
	PU_PSNDOC(PayBillItemVO.PU_PSNDOC, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000976")/* 采购业务员 */),
	PU_DEPTID(PayBillItemVO.PU_DEPTID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000984")/* 采购部门 */),
	MATERIAL(PayBillItemVO.MATERIAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000594")/* 物料 */),
	POSTUNIT(PayBillItemVO.POSTUNIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000416")/* 报价计量单位 */),
	POSTPRICENOTAX(PayBillItemVO.POSTPRICENOTAX, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000413")/* 报价单位无税单价 */),
	POSTQUANTITY(PayBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000412")/* 报价单位数量 */),
	POSTPRICE(PayBillItemVO.POSTPRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000410")/* 报价单位含税单价 */),
	CHECKDIRECTION(PayBillItemVO.CHECKDIRECTION, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000659")/* 票据方向 */),
	COORDFLAG(PayBillItemVO.COORDFLAG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000185")/* 单据协同状态 */),
	EQUIPMENTCODE(PayBillItemVO.EQUIPMENTCODE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000892")/* 设备编码 */),
	CASHITEM(PayBillItemVO.CASHITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000615")/* 现金流量项目 */),
	PAYFLAG(PayBillItemVO.PAYFLAG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000437")/* 支付状态 */),
	BANKROLLPROJET(PayBillItemVO.BANKROLLPROJET, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000941")/* 资金计划项目 */),
	BILLDATE(PayBillItemVO.BILLDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000190")/* 单据日期 */),
	PAYMAN(PayBillItemVO.PAYMAN, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000433")/* 支付人 */),
	PAYDATE(PayBillItemVO.PAYDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000435")/* 支付日期 */),
	PK_GROUP(PayBillItemVO.PK_GROUP, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000367")/* 所属集团 */),
	PK_PCORG(PayBillItemVO.PK_PCORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000161")/* 利润中心 */),
	SETT_ORG(PayBillItemVO.SETT_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000715")/* 结算财务组织 */),
	PK_ORG(PayBillItemVO.PK_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000028")/* 付款财务组织 */),
	PK_FIORG(PayBillItemVO.PK_FIORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000908")/* 财务组织 */),
	DEF17(PayBillItemVO.DEF17, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000757")/* 自定义项17 */),
	DEF16(PayBillItemVO.DEF16, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000756")/* 自定义项16 */),
	DEF15(PayBillItemVO.DEF15, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000755")/* 自定义项15 */),
	DEF14(PayBillItemVO.DEF14, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000754")/* 自定义项14 */),
	DEF13(PayBillItemVO.DEF13, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000753")/* 自定义项13 */),
	DEF12(PayBillItemVO.DEF12, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000752")/* 自定义项12 */),
	DEF11(PayBillItemVO.DEF11, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000751")/* 自定义项11 */),
	DEF10(PayBillItemVO.DEF10, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000750")/* 自定义项10 */),
	DEF9(PayBillItemVO.DEF9, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000788")/* 自定义项9 */),
	DEF8(PayBillItemVO.DEF8, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000787")/* 自定义项8 */),
	DEF7(PayBillItemVO.DEF7, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000786")/* 自定义项7 */),
	DEF4(PayBillItemVO.DEF4, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000782")/* 自定义项4 */),
	DEF3(PayBillItemVO.DEF3, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000771")/* 自定义项3 */),
	DEF2(PayBillItemVO.DEF2, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000760")/* 自定义项2 */),
	DEF1(PayBillItemVO.DEF1, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000749")/* 自定义项1 */),
	CHECKELEMENT(PayBillItemVO.CHECKELEMENT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000912")/* 责任核算要素 */),
	GROUPRATE(PayBillItemVO.GROUPRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001016")/* 集团本币汇率 */),
	GLOBALRATE(PayBillItemVO.GLOBALRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000119")/* 全局本币汇率 */),
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
	DEF6(PayBillItemVO.DEF6, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000785")/* 自定义项6 */),
	DEF5(PayBillItemVO.DEF5, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000784")/* 自定义项5 */),
	ISREFUSED(PayBillItemVO.ISREFUSED, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000012")/* 是否被拒付 */),
	COSTCENTER(PayBillItemVO.COSTCENTER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000106")/* 成本中心 */),
	PAYREASON(PayBillItemVO.PAYREASON, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000113")/* 支付原因 */),
	PK_PAYBILL(PayBillItemVO.PK_PAYBILL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000122")/* 付款单标识 */),
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
	PRICE(PayBillItemVO.PRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000175")/* 单价 */),
	TAXPRICE(PayBillItemVO.TAXPRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000002")/* 含税单价 */),
	TAXRATE(PayBillItemVO.TAXRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000670")/* 税率 */),
	TAXNUM(PayBillItemVO.TAXNUM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000669")/* 税号 */),
//	TOP_BILLTYPE(PayBillItemVO.TOP_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000004")/* 上层单据类型 */),
//	TOP_TRADETYPE(PayBillItemVO.TOP_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000002")/* 上层交易类型 */),
//		SRC_TRADETYPE(PayBillItemVO.SRC_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000572")/* 源头交易类型 */),
//		SRC_BILLTYPE(PayBillItemVO.SRC_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000581")/* 源头单据类型 */),
//		SRC_BILLID(PayBillItemVO.SRC_BILLID, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000575")/* 源头单据主键 */),
//		SRC_ITEMID(PayBillItemVO.SRC_ITEMID, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000582")/* 源头单据行主键 */),
//		PK_PAYTERM(PayBillItemVO.PK_PAYTERM, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000055")/* 付款协议 */),
//	TOP_TERMCH(PayBillItemVO.TOP_TERMCH, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("paybill", "2paybill-000014")/* 来源付款协议行 */),
	ORDERCUBASDOC(PayBillItemVO.ORDERCUBASDOC, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000019")/* 订单供应商 */),
	INNERORDERNO(PayBillItemVO.INNERORDERNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000904")/* 调拨订单号 */),
	BANKRELATED_CODE(PayBillItemVO.BANKRELATED_CODE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000299")/* 对账标识码 */),
	PROJECT_TASK(PayBillItemVO.PROJECT_TASK, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001023")/* 项目任务 */),
	COMMPAYTYPE(PayBillItemVO.COMMPAYTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000006")/* 承付类型 */),
	COMMPAYSTATUS(PayBillItemVO.COMMPAYSTATUS, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000021")/* 承付状态 */),
	REFUSE_REASON(PayBillItemVO.REFUSE_REASON, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000043")/* 拒付理由 */),
	ASSETPACTNO(PayBillItemVO.ASSETPACTNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000929")/* 资产合同号 */),
	CONTRACTNO(PayBillItemVO.CONTRACTNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000234")/* 合同号 */),
	FREECUST(PayBillItemVO.FREECUST, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000454")/* 散户 */),
	FACARD(PayBillItemVO.FACARD, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000025")/* 固定资产卡片号 */),
	PURCHASEORDER(PayBillItemVO.PURCHASEORDER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000877")/* 订单号 */),
	AGENTRECEIVEPRIMAL(PayBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000010")/* 托收原币金额 */),
	AGENTRECEIVELOCAL(PayBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000001")/* 托收组织本币 */),
	GROUPAGENTRECEIVELOCAL(PayBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000009")/* 托收集团本币 */),
	GLOBALAGENTRECEIVELOCAL(PayBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000048")/* 托收全局本币 */),
	COMMPAYER(PayBillItemVO.COMMPAYER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000049")/* 承付人 */),
	OUTSTORENO(PayBillItemVO.OUTSTORENO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000149")/* 出库单号 */),
	DEF30(PayBillItemVO.DEF30, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000772")/* 自定义项30 */),
	DEF29(PayBillItemVO.DEF29, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000770")/* 自定义项29 */),
	DEF28(PayBillItemVO.DEF28, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000769")/* 自定义项28 */),
	DEF27(PayBillItemVO.DEF27, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000768")/* 自定义项27 */),
	DEF26(PayBillItemVO.DEF26, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000767")/* 自定义项26 */),
	DEF25(PayBillItemVO.DEF25, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000766")/* 自定义项25 */),
	DEF24(PayBillItemVO.DEF24, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000765")/* 自定义项24 */),
	DEF23(PayBillItemVO.DEF23, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000764")/* 自定义项23 */),
	DEF22(PayBillItemVO.DEF22, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000763")/* 自定义项22 */),
	DEF21(PayBillItemVO.DEF21, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000762")/* 自定义项21 */),
	DEF20(PayBillItemVO.DEF20, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000761")/* 自定义项20 */),
	DEF19(PayBillItemVO.DEF19, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000759")/* 自定义项19 */),
	DEF18(PayBillItemVO.DEF18, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000758")/* 自定义项18 */),
	TOP_BILLID(PayBillItemVO.TOP_BILLID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000003")/* 上层单据主键 */),
	TOP_ITEMID(PayBillItemVO.TOP_ITEMID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000005")/* 上层单据行主键 */),
	GLOBALNOTAX_DE(PayBillItemVO.GLOBALNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000029")/* 全局本币无税金额(借方) */);
			
	// 单据类型
	private String key;

	// 单据名称
	private String name;

	private PayBillCombinRule(String key, String name) {
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