package nc.vo.arap.comreport;

import nc.vo.arap.pub.BillEnumCollection;

/**
 * 收付公共常量接口<br>
 * 
 * @author 梁国荣<br>
 * @since V60<br>
 */
public interface IArapReportConstants {
	
	// 是否显示日小计：支持UFBoolean值
	public static final String KEY_SHOW_DATE_TOTAL = "showDateTotalComb";
	// 已回冲的暂估单据不显示：支持UFBoolean值
	public static final String KEY_NOTSHOW_BACKESTI = "notshowBackEstiComb";
	// 是否是查询预收/预付款账龄：支持UFBoolean值
	public static final String KEY_PRE_REC_PAY_ACCAGE = "prerecpayaccage";
	// 无发生不显示&无发生无余额不显示
	public static final String KEY_NOOCC_NOSHOW = "nooccur_noshow";
	public static final String KEY_NOBAL_NOOCC_NOSHOW = "nobal_nooccur_noshow";
	// 预测构成：应收/付款、收/付款余额、预收/付余额
	public static final String KEY_FORECAST_COMPOSE_RECPAY = "recpaycheck";
	public static final String KEY_FORECAST_COMPOSE_RECPAYBAL = "recpaybalcheck";
	public static final String KEY_FORECAST_COMPOSE_PRERECPAYBAL = "prerecpaybalcheck";
	// 应收/付账龄分析：是否显示到期日
	public static final String KEY_SHOW_EXPIREDATE = "showexpiredate";

	// 账龄分析模式
	public static final String ACC_ANA_MODE_AGE = "byAccAge"; // 按账龄
	public static final String ACC_ANA_MODE_DATE = "byDate"; // 按日期

	// 账龄分析方向
	public static final String ACC_ANA_DIRECT_RECABLE_REC = "recable_rec"; // 应收-收款
	public static final String ACC_ANA_DIRECT_PAYABLE_PAY = "payable_pay"; // 应付-付款

	// 分析类型
	public static final String ACC_ANA_TYP_SETTLE = "settle"; // 结算日期
	public static final String ACC_ANA_TYP_DEADLINE = "deadline"; // 截止日期

	// 分析日期
	public static final String ACC_ANA_DATE_EXPIREDATE = "expiredate"; // 到日期
	public static final String ACC_ANA_DATE_BILLDATE = "billdate"; // 单据日期
	public static final String ACC_ANA_DATE_AUDITDATE = "auditdate"; // 审批日期
	public static final String ACC_ANA_DATE_EFFECTDATE = "effectdate"; // 生效日期
	public static final String ACC_ANA_DATE_WITHINDATE = "withindate"; // 内控到期日期
	public static final String ACC_ANA_DATE_RAISEDATE = "raisedate"; // 起算日期

	// 分析方式
	public static final String ACC_ANA_PATTERN_FINAL = "final"; // 最终余额
	public static final String ACC_ANA_PATTERN_POINT = "point"; // 点余额

	// 可替换表名
	public static final String REPLACE_TABLE = "@Table";

	// 全部单据状态
	public static final int I_BILL_STATUS_ALL = -1000;

	// 分析方向
	public static final String ANA_DIRECT_RECABLE = "recable"; // 应收
	public static final String ANA_DIRECT_RECABLE_REC = "recable_rec"; // 应收-收款
	public static final String ANA_DIRECT_RECABLE_REC_PRE = "recable_rec_pre"; // 应收-收款-预收
	public static final String ANA_DIRECT_PAYABLE = "payable"; // 应付
	public static final String ANA_DIRECT_PAYABLE_PAY = "payable_pay"; // 应付-付款
	public static final String ANA_DIRECT_PAYABLE_PAY_PRE = "payable_pay_pre"; // 应付-付款-预付

	// 系统标识
	public static final int SYS_FLAG_REC = 3; // 应收
	public static final int SYS_FLAG_PAY = 4; // 应付
	public static final String MODULEID_AR = "2006";
	public static final String MODULEID_AP = "2008";

	// 往来对象
	public static final int ASSO_OBJ_ALL = 9; // 所有
	public static final int ASSO_OBJ_CUS_SUP = 8; // 客商
	public static final int ASSO_OBJ_CUSTOMER = BillEnumCollection.ObjType.CUSTOMER.VALUE; // 客户
	public static final int ASSO_OBJ_SUPPLIER = BillEnumCollection.ObjType.SUPPLIER.VALUE; // 供应商
	public static final int ASSO_OBJ_DEPT = BillEnumCollection.ObjType.DEP.VALUE; // 部门
	public static final int ASSO_OBJ_BUSIMAN = BillEnumCollection.ObjType.PERSON.VALUE; // 业务员

	public static final String DEF = "def";
	public static final int BAL_DEF_LEN = 6;

	public static final int REPORT_QRYOBJ_SIZE = 5; // 同时支持的报表查询对象个数
	
	public static final String IS_MATCUSTCODE = "isShowMatCustCode"; // 是否显示物料客户码
	
	//add for 新加坡BDA 20161111
	public static final String IS_TOTALCODE = "isShowTotalCode"; // 是否显示小计
	
	public static final String IS_CANAFTERCODE = "isCancelAfterCode"; // 是否仅显示未核销
	
	public static final String IS_DJCODE = "isShowDjCode"; // 是否显示对应单据号信息
	
	public static final String IS_FINAL_BALANCE = "isFinalBalance"; //期末余额为零且本期无发生是否打印
	
	public static final String IS_COUNTER_FEE = "isCounterFee"; //是否显示手续费
	
	public static final String AGING_REF = "agingRef"; //账龄期间
	//add end
	
	// 总账与业务系统对账 对账内容
	public static final String STATE_BY_LOCAL = "statebylocal"; // 包括按原币对
	public static final String STATE_BY_ORI = "statebyori"; // 只按本币对
	public static final String GLBR = "glbr"; // 只按本币对
	public static final String GLBRSUM = "glbrsum"; // 只按本币对
	
	
}

// /:~
