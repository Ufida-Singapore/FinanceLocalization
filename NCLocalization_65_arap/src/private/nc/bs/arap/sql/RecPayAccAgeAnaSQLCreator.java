package nc.bs.arap.sql;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.bs.arap.utils.TmpTableCreator;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.jdbc.framework.util.DBConsts;
import nc.pub.report.utils.ReportSqlUtils;
import nc.utils.arap.ArapReportResource;
import nc.utils.fipub.SmartProcessor;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.arap.comreport.ReportTableEnum;
import nc.vo.arap.tally.TallyVO;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.ReportQueryCondVO;
import nc.vo.fipub.utils.SqlBuilder;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;

import org.apache.commons.lang.StringUtils;

/**
 * 账龄分析查询SQL生成器(应收、应付)<br>
 * 
 * @author 梁国荣<br>
 * @since V60 2010-12-14<br>
 */
public class RecPayAccAgeAnaSQLCreator extends ArapBaseSqlCreator {
	private static final String detailFields = "arap_tally.pk_billtype, arap_tally.pk_bill, arap_tally.billno, arap_tally.billdate";

	private String tmpTblName = null;
	private String[] tmpTblColNames = null;
	private Integer[] tmpTblColTypes = null;

	private String tmpTblName2 = null;
	private String[] tmpTblColName2s = null;
	private Integer[] tmpTblColType2s = null;

	private List<ComputeTotal> allQryobjList = new ArrayList<ComputeTotal>();

	private boolean isShowExpireDate = false;

	@Override
	public void setParams(ReportQueryCondVO queryVO) {
		super.setParams(queryVO);
		Object obj = queryVO.getUserObject().get(IArapReportConstants.KEY_SHOW_EXPIREDATE);
		isShowExpireDate = obj == null ? false : ((UFBoolean) obj).booleanValue()
				&& IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate());
	}

	public String[] getArrangeSqls() throws SQLException, BusinessException {
		List<String> sqlList = null;
		if (IArapReportConstants.ACC_ANA_MODE_AGE.equals(queryVO.getAnaMode())) {
			// 分析模式：按账龄
			sqlList = getAccountAgeByAgeSql();
		} else if (IArapReportConstants.ACC_ANA_MODE_DATE.equals(queryVO.getAnaMode())) {
			// 分析模式：按日期
			sqlList = getAccountAgeByDateSql();
		}

		if (!queryVO.isQueryDetail()) {
			sqlList.add(getCollectSql()); // 明细汇总
		}
		
		sqlList.add(getRecPayTotal()); // 计算收/付款总额
		
		if (queryVO.isQueryDetail()) {
			sqlList.addAll(updateBill());
		}
		sqlList.add(getTotalSql()); // 计算小计、合计

		return sqlList.toArray(new String[0]);
	}
	/*
	 * 解决单据表体带收付款协议时,单据金额显示多行问题
	 */
	private List<String> updateBill() throws SQLException {
		List<String> updatebillList = new ArrayList<String>();
		String tmpTable = "";
		if (queryVO.isQueryDetail()) {
			tmpTable = getTmpTblName();
		} else { 
			tmpTable = getTmpTblName2();
		}
		
		String additionSql = " and (accage_ori != 0 "	; 
		if (ORG_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			additionSql += " or accage_loc != 0) ";
		} else if (GROUP_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			additionSql += " or gr_accage_loc != 0) ";
		} else if (GLOBLE_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			additionSql += " or gl_accage_loc != 0) ";
		}
		
		String setdirty = "update " + tmpTable + " set dr = 1 where accageid != -7 " + additionSql;
		String deletedirty = "delete from " + tmpTable + " where dr = 1 and accageid != -7 " + additionSql;
		String fields = "t.bill_qua,t.bill_ori,t.bill_loc,t.gr_bill_loc,t.gl_bill_loc"; 
		String[] qryObjs = queryObjOrderExt.split(",");
		StringBuffer selectfields = new StringBuffer();
		selectfields.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "t2"));
		for(String str : qryObjs){
			selectfields.append(", t2.").append(str.trim());
		}
		selectfields.append(", t2.").append(PK_CURR).append(", ");
		selectfields.append("t2.pk_billtype, t2.pk_bill, t2.billno, t2.billdate, ");
		selectfields.append("t2.expiredate, ");
		selectfields.append("t2.accageid, t2.accage, ");
		selectfields.append("0 rn, 0 dr, ");
		selectfields.append(fields).append(", ");
		selectfields.append("t2.accage_qua, ");
		selectfields.append("t2.accage_ori, ");
		selectfields.append("t2.accage_loc, ");
		selectfields.append("t2.gr_accage_loc, ");
		selectfields.append("t2.gl_accage_loc ");
		
		String innerfields = "sum(bill_qua) bill_qua,sum(bill_ori) bill_ori,sum(bill_loc) bill_loc,sum(gr_bill_loc) gr_bill_loc,sum(gl_bill_loc) gl_bill_loc ";
		String[] groupbys = new String[]{"pk_group","pk_org","billno","pk_billtype", "pk_bill",  "billdate"};
		String innersql = "select " + innerfields ;
		for(String str : groupbys){
			innersql = innersql + ", " + str;
		}
		if (beForeignCurrency) {
			innersql = innersql + ", pk_currtype " ;
		}
		if(isShowExpireDate){
			innersql = innersql + ", expiredate " ;
		}
		for(String str : qryObjs){
			innersql = innersql + ", " + str;
		}
		innersql = innersql + " from " + tmpTable ;
		innersql = innersql + " where accageid != -7 " + additionSql;
		innersql = innersql + " group by pk_group, pk_org, billno, pk_billtype, pk_bill,  billdate " ;
		if (beForeignCurrency) {
			innersql = innersql + ", pk_currtype ";
		}
		if(isShowExpireDate){
			innersql = innersql + ", expiredate";
		}
		for(String str : qryObjs){
			innersql = innersql + ", " + str;
		}
		String sql = new String();
		sql = " select " + selectfields + " from ( " + innersql + " ) t, " + tmpTable + " t2 ";
		sql = sql + " where t2.accageid != -7 and ";
		for(String str : groupbys){
			sql = sql + "t2." + str + " = t." + str + " and ";
		}
		if (beForeignCurrency) {
			sql = sql + "t2." + "pk_currtype" + " = t." + "pk_currtype" + " and ";
		}
		if(isShowExpireDate){
			sql = sql + "t2." + "expiredate" + " = t." + "expiredate" + " and ";
		}
		sql =  sql.substring(0, sql.length()-4);
		for(String str : qryObjs){
			sql = sql + " and (t2." + str.trim() + " = t." + str.trim() + " or (t2." + str.trim() + " is null and t." + str.trim() + " is null ))";
		}
		sql = "insert into " + tmpTable + " " + sql ;
		updatebillList.add(setdirty);
		updatebillList.add(sql);
		updatebillList.add(deletedirty);
		return updatebillList; 
	} 
	 
	public String getResultSql() throws SQLException, BusinessException {
		return getResultAccountAgeSql();
	}

	public String getDistinctCurrency() throws SQLException, BusinessException {
		return null;
	}

	public String[] getDropTableSqls() throws SQLException, BusinessException {
		return new String[0];
	}

	/**
	 * 按账龄方案分析账龄
	 * 
	 * @return
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private List<String> getAccountAgeByAgeSql() throws SQLException, BusinessException {
		List<String> sqlList = new ArrayList<String>();
		// 查询应收账龄分析(应付账龄分析)，根据分析方向，可能包括应收单、收款单(应付系统包括应付单、付款单)
		sqlList.add(getRecPayAgeByAccAgeSql());
		return sqlList;
	}

	/**
	 * 按日期区间分析账龄
	 * 
	 * @return
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private List<String> getAccountAgeByDateSql() throws SQLException, BusinessException {
		List<String> sqlList = new ArrayList<String>();
		// 查询应收账龄分析(应付账龄分析)，根据分析方向，可能包括应收单、收款单(应付系统包括应付单、付款单)
		sqlList.add(getRecPayAgeByDateSql());
		return sqlList;
	}

	/**
	 * 查询每个查询对象的汇总数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	private String getCollectSql() throws SQLException {
		String collFixedFields = fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", "");

		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		sqlBuffer.append(getTmpTblName2());

		sqlBuffer.append(" select ");
		sqlBuffer.append(collFixedFields).append(", ");
		sqlBuffer.append(queryObjOrderExt).append(", ");
		sqlBuffer.append(PK_CURR).append(", accageid, accage, ");
		sqlBuffer.append("0 rn, 0 dr, ");
		// sqlBuffer.append("sum(bill_qua) bill_qua, sum(bill_ori) bill_ori, sum(bill_loc) bill_loc, sum(gr_bill_loc) gr_bill_loc, sum(gl_bill_loc) gl_bill_loc, ");
		sqlBuffer.append("sum(accage_qua) accage_qua, sum(accage_ori) accage_ori, sum(accage_loc) accage_loc, sum(gr_accage_loc) gr_accage_loc, sum(gl_accage_loc) gl_accage_loc ");

		sqlBuffer.append(" from ");
		sqlBuffer.append(getTmpTblName());

		sqlBuffer.append(" group by ");
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR).append(", accageid, accage");

		return sqlBuffer.toString();
	}

	/**
	 * 查询每个查询对象的应收/付总额
	 * 
	 * @return
	 * @throws SQLException
	 */
	private String getRecPayTotal() throws SQLException {
		String collFixedFields = fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", "");
		String collDetailFields = detailFields.replace(ReportTableEnum.ARAP_TALLY.getCode() + ".", "");

		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(getTmpTblName());
		} else {
			sqlBuffer.append(getTmpTblName2());
		}

		sqlBuffer.append(" select ");
		sqlBuffer.append(collFixedFields).append(", ");
		sqlBuffer.append(queryObjOrderExt).append(", ");
		sqlBuffer.append(PK_CURR).append(", ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(collDetailFields).append(", ");
			sqlBuffer.append("expiredate, ");
		}
		sqlBuffer.append("-7 accageid, '").append(ArapReportResource.getBalanceLbl()).append("' accage, 0 rn, 0 dr, "); // 余额
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append("sum(bill_qua) bill_qua, sum(bill_ori) bill_ori, sum(bill_loc) bill_loc, sum(gr_bill_loc) gr_bill_loc, sum(gl_bill_loc) gl_bill_loc, ");
		}
		sqlBuffer.append("sum(accage_qua) accage_qua, sum(accage_ori) accage_ori, sum(accage_loc) accage_loc, sum(gr_accage_loc) gr_accage_loc, sum(gl_accage_loc) gl_accage_loc ");

		sqlBuffer.append(" from ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(getTmpTblName());
		} else {
			sqlBuffer.append(getTmpTblName2());
		}

		sqlBuffer.append(" group by ");
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR);
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", ").append(collDetailFields);
			sqlBuffer.append(", expiredate");
		}
		return sqlBuffer.toString();
	}
	

	/**
	 * 计算小计合计
	 * 为了处理单据金额行，把accage不等于-7的行的sum(单据值)赋给accage等于-7的行，保证交叉展开时单据金额能够正确显示
	 * @return
	 * @throws SQLException 
	 */
	private String getTotalSql() throws SQLException {
		String[] details = detailFields.replace(ReportTableEnum.ARAP_TALLY.getCode() + ".", "").split(",");

		// 构造小计、合计对象
		List<ComputeTotal> allQryObjs = getAllQryObj();
		List<String> computed = new ArrayList<String>();

		// 正式拼写SQL
		StringBuffer insertSql = new StringBuffer(" insert into ");
		if (queryVO.isQueryDetail()) {
			insertSql.append(getTmpTblName());
		} else {
			insertSql.append(getTmpTblName2());
		}

		StringBuffer sqlBuffer = new StringBuffer(" select ");
		for (ComputeTotal total : allQryObjs) {
			if (total.isDimension) {
				sqlBuffer.append(total.field).append(", ");
				computed.add(total.field);
			} else {
				sqlBuffer.append("null ").append(total.field).append(", ");
			}
		}
		if (queryVO.isQueryDetail()) {
			for (String d : details) {
				sqlBuffer.append("null ").append(d).append(", ");
			}
			sqlBuffer.append("null expiredate, ");
		}
		sqlBuffer.append("accageid, accage, ");
		for (int i = 0; i < computed.size(); i++) {
			sqlBuffer.append("grouping(").append(computed.get(i)).append(") + ");
		}
		sqlBuffer.append(SmartProcessor.MAX_ROW).append(" rn, 0 dr, ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append("sum(bill_qua) bill_qua, sum(bill_ori) bill_ori, sum(bill_loc) bill_loc, sum(gr_bill_loc) gr_bill_loc, sum(gl_bill_loc) gl_bill_loc, ");
		}
		sqlBuffer.append("sum(accage_qua) accage_qua, sum(accage_ori) accage_ori, sum(accage_loc) accage_loc, sum(gr_accage_loc) gr_accage_loc, sum(gl_accage_loc) gl_accage_loc ");

		sqlBuffer.append(" from ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(getTmpTblName());
		} else {
			sqlBuffer.append(getTmpTblName2());
		}

		sqlBuffer.append(" where accage_ori != 0 ");
		if (ORG_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or accage_loc != 0 ");
		} else if (GROUP_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or gr_accage_loc != 0 ");
		} else if (GLOBLE_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or gl_accage_loc != 0 ");
		}
		
		sqlBuffer.append(" group by ");
		switch (SqlBuilder.getDatabaseType()) {
		case DBConsts.SQLSERVER:
			for (int i = 0; i < computed.size(); i++) {
				sqlBuffer.append(computed.get(i)).append(", ");
			}
			sqlBuffer.append("accageid, accage");
			sqlBuffer.append(" with cube ");
			break;
		case DBConsts.DB2:
		case DBConsts.ORACLE:
		case DBConsts.POSTGRESQL:
			sqlBuffer.append("cube(");
			for (int i = 0; i < computed.size(); i++) {
				sqlBuffer.append(computed.get(i)).append(", ");
			}
			sqlBuffer.append("accageid, accage)");
			break;
		default:
			break;
		}

		sqlBuffer.append(" having ");
		for (int i = 0; i < computed.size() - 1; i++) {
			sqlBuffer.append("grouping(").append(computed.get(i)).append(") <= grouping(").append(
					computed.get(i + 1)).append(") and ");
		}
		sqlBuffer.append("grouping(").append(computed.get(0)).append(") = 0 "); // 集团不计算总计
		if (queryVO.getPk_orgs().length <= 1) {
			// 多业务单元查询才计算总计
			sqlBuffer.append(" and grouping(").append(allQryObjs.get(1).field).append(") = 0 ");
		}
		sqlBuffer.append(" and grouping(accageid) = 0 ");
		sqlBuffer.append(" and grouping(accage) = 0 ");

		StringBuffer totalSql = new StringBuffer();
		if(queryVO.isQueryDetail()){
//			totalSql.append(insertSql);
			totalSql.append(" select ");
			for (ComputeTotal total : allQryObjs) {
				totalSql.append("t1.").append(total.field).append(", ");
			}
				for (String d : details) {
					totalSql.append("t1.").append(d).append(", ");
				}
				totalSql.append("t1.expiredate, ");
			totalSql.append("t1.accageid, t1.accage, ");
			totalSql.append(" t1.rn, 0 dr,  ");
			totalSql.append("t2.bill_qua, t2.bill_ori, t2.bill_loc, t2.gr_bill_loc, t2.gl_bill_loc, ");
			totalSql.append("t1.accage_qua, t1.accage_ori, t1.accage_loc, t1.gr_accage_loc, t1.gl_accage_loc ");
			
			totalSql.append(" from ");
			totalSql.append("( ").append(sqlBuffer).append(" ) t1 , ( ").append(sqlBuffer).append(" and accageid = -7 ) t2");
			totalSql.append(" where t1.pk_group = t2.pk_group and t1.pk_org = t2.pk_org ");
			for (ComputeTotal total : allQryObjs) {
				totalSql.append(" and ( t1.").append(total.field).append(" = t2.").append(total.field).append(" or ( t1.").append(total.field).append(" is null and t2.").append(total.field).append(" is null ))");
			}
		}else{
			totalSql.append(insertSql).append(sqlBuffer);
		}
		return totalSql.toString();
	}

	/**
	 * 账龄分析明细SQL
	 * getSQLs()方法的包装器
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private String getResultAccountAgeSql() throws SQLException, BusinessException {
		StringBuffer sqlBuffer = new StringBuffer(" select ");

		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "v"));
		sqlBuffer.append(", (case when isnull(org_orgs.code, '~') = '~' then 1 else 0 end) as is_org_null"); // is_org_null
		sqlBuffer.append(", org_orgs.code code_org, coalesce(org_orgs.name").append(getMultiLangIndex()).append(", org_orgs.name) org"); // code_org, org

		String[] qryObjs = getQueryObjs();
		List<QryObj> qryObjList = queryVO.getQryObjs();

		for (int i = 0; i < qryObjList.size(); i++) {
			sqlBuffer.append(", v.").append(qryObjs[i]).append(", ");

			sqlBuffer.append(bdTable + i).append(".").append(qryObjList.get(i).getBd_codeField())
					.append(" ").append(QRY_OBJ_PREFIX).append(i).append("code, ");

			sqlBuffer.append("coalesce(").append(bdTable + i).append(".").append(
					qryObjList.get(i).getBd_nameField()).append(getMultiLangIndex()).append(", ")
					.append(bdTable + i).append(".").append(qryObjList.get(i).getBd_nameField())
					.append(") ").append(QRY_OBJ_PREFIX).append(i).append(", ");

			sqlBuffer.append("(case when isnull(").append(qryObjs[i]).append(
					", '~') = '~' then 1 else 0 end) as isnull").append(i);
		}
		sqlBuffer.append(", v.").append(PK_CURR).append(" ").append(PK_CURR).append(", (case when isnull(v.").append(PK_CURR)
				.append(", '~') = '~' then 1 else 0 end) as is_currtype_null"); // is_currtype_null
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", ").append(detailFields.replace(TallyVO.getDefaultTableName(), "v"));
			sqlBuffer.append(", v.expiredate");
			sqlBuffer.append(", v.bill_qua, v.bill_ori, v.bill_loc, v.gr_bill_loc, v.gl_bill_loc");
		}
		sqlBuffer.append(", v.accage_qua, v.accage_ori, v.accage_loc, v.gr_accage_loc, v.gl_accage_loc");
		sqlBuffer.append(", v.accageid, v.accage, v.rn, 0 ").append(ORDER_MANAGE_VSEQ);

		// =====================FROM子句=====================
		sqlBuffer.append(getFromResultTable(queryVO.isQueryDetail() ? getTmpTblName()
				: getTmpTblName2(), qryObjList, qryObjs));

		sqlBuffer.append(" where v.accage_ori != 0 ");
		if (ORG_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.accage_loc != 0 ");
		} else if (GROUP_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.gr_accage_loc != 0 ");
		} else if (GLOBLE_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.gl_accage_loc != 0 ");
		}

		sqlBuffer.append(" order by ");
		sqlBuffer.append("is_org_null, code_org");
		for (int i = 0; i < qryObjList.size(); i++) {
			sqlBuffer.append(", isnull").append(i).append(", ").append(QRY_OBJ_PREFIX).append(i).append("code");
		}
		sqlBuffer.append(", is_currtype_null, pk_currtype, rn");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", billdate, billno");
		}
		if (queryVO.isQueryDetail() && isShowExpireDate) {
			sqlBuffer.append(", expiredate"); 
		}

		return sqlBuffer.toString();
	}

	
	/**
	 * 查询应收账龄(应付账龄)
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private String getRecPayAgeByAccAgeSql() throws SQLException, BusinessException {
		// 构造截止日期
		UFDate dateline = null; 
		if (IArapReportConstants.ACC_ANA_PATTERN_FINAL.equals(queryVO.getAnaPattern())
				|| queryVO.getDateline() == null) {
			// 如果按照最终余额分析
			dateline = getBusisDate(false);
		} else {
			dateline = queryVO.getDateline();
		}

		// 获取账龄方案临时表
		String tmpTable = ReportSqlUtils.getTimeCtrlTmpTable(queryVO.getAccAgePlan(), dateline);
		String tmpTableAlias = ReportSqlUtils.getTimeCtrlTmpTableAlias();

		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
//		sqlBuffer.append(getTmpTblName());
		StringBuffer insertBuffer = new StringBuffer(" insert into ");
		insertBuffer.append(getTmpTblName());
		
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append(" select ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, TallyVO.getDefaultTableName()));
		sqlBuffer.append(", ").append(queryObjBaseTally).append(", ");
		sqlBuffer.append(beForeignCurrency ? "arap_tally." : "null ").append(PK_CURR).append(", ");

		sqlBuffer.append(detailFields).append(", ");
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		sqlBuffer.append("arap_tally.pk_item, ");
		//判断是否需要显示到期日
//				sqlBuffer.append(isShowExpireDate ? "arap_tally_agr.expiredate, " : "null expiredate, ");
		sqlBuffer.append(isShowExpireDate ? "substring(arap_tally_agr.expiredate,0,10) expiredate, " : "null expiredate, ");
				
		sqlBuffer.append(tmpTableAlias + ".propertyid accageid, " + tmpTableAlias + ".descr accage, ");
		sqlBuffer.append("0 rn, 0 dr, ");

		String moneyTable = ReportTableEnum.ARAP_TALLY.getCode();
		/****************************************************/
		/*************** 按到期日/内控到期日期分析 ***************/
		if (IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate())
				|| IArapReportConstants.ACC_ANA_DATE_WITHINDATE.equals(queryVO.getAnaDate())) {
			moneyTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
		}

		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".quantity_de + ").append(moneyTable).append(
						".quantity_cr else 0 end) bill_qua, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".money_de + ").append(moneyTable).append(
						".money_cr else 0 end) bill_ori, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".local_money_de + ").append(moneyTable).append(
						".local_money_cr else 0 end) bill_loc, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".grouplocal_money_de + ").append(moneyTable).append(
						".grouplocal_money_cr else 0 end) gr_bill_loc, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".globallocal_money_de + ").append(moneyTable).append(
						".globallocal_money_cr else 0 end) gl_bill_loc, ");

		/****************************************************/

		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".quantity_de - ").append(
				moneyTable).append(".quantity_cr) accage_qua, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".money_de - ").append(
				moneyTable).append(".money_cr) accage_ori, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".local_money_de - ").append(
				moneyTable).append(".local_money_cr) accage_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".grouplocal_money_de - ").append(
				moneyTable).append(".grouplocal_money_cr) gr_accage_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".globallocal_money_de - ").append(
				moneyTable).append(".globallocal_money_cr) gl_accage_loc ");

		sqlBuffer.append(" from ").append(ReportTableEnum.ARAP_TALLY.getCode());
		sqlBuffer.append(getJoinTableSql2());

		// 连接账龄方案(临时)表
		sqlBuffer.append(", ").append(tmpTable).append(tmpTableAlias);

		sqlBuffer.append(" where ").append(ReportSqlUtils.getFixedWhere());
		sqlBuffer.append(getCompositeWhereSql());

		// 分析日期
		String anaDateField = ReportSqlUtils.getAnaDateField(queryVO.getAnaDate());
		sqlBuffer.append(" and " + anaDateField + " >= " + tmpTableAlias + ".startdate ");
		sqlBuffer.append(" and " + anaDateField + " <= " + tmpTableAlias + ".enddate ");

		if (IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern())) {
			// 分析方式：点余额
			sqlBuffer.append(" and (").append(TallyVO.getDefaultTableName()).append(".").append(
					TallyVO.BILLDATE).append(" <= '" + dateline + "') ");
			//update chenth 20180404 汇兑损益的通过talldate判断，其他通过billdate
//			sqlBuffer.append(" and (").append(TallyVO.getDefaultTableName()).append(".").append(
//					TallyVO.TALLYDATE).append(" <= '" + dateline + "') ");
			sqlBuffer.append(" and (arap_tally.dealflag !=21  or ").append(TallyVO.getDefaultTableName()).append(".").append(
					TallyVO.TALLYDATE).append(" <= '" + dateline + "') ");
			//update end 
		}
		
		//add chenth 20180309 排除未审核的收款单/付款单
		sqlBuffer.append(" and ( ").append(TallyVO.PK_BILLTYPE).append(" not in ('F2', 'F3') or ")
		.append( TallyVO.BILLSTATUS).append(" != -1 ) ");
		//add chenth 20180327 账龄分析次月收款后，再查上个月的SOA 和上一个月查时保持一致
		sqlBuffer.append(" and (");
		sqlBuffer.append("   arap_tally.corbillclass not in('yf','ys','sk','fk') or arap_tally.corbillclass is null ");
		sqlBuffer.append("   or (arap_tally.corbillclass = 'sk' and pk_corbill in (select pk_gatherbill from ar_gatherbill where ar_gatherbill.billdate <= '" + dateline + "' )) ");
		//update chenth 20180407 应收核销应付，或者应付核销应收的，不按单据日期，按核销日期
		sqlBuffer.append("   or (arap_tally.corbillclass = 'yf' and arap_tally.tallydate <= '" + dateline + "' ) ");
//		sqlBuffer.append("   or (arap_tally.corbillclass = 'yf' and pk_corbill in (select pk_payablebill from ap_payablebill where ap_payablebill.billdate <= '" + dateline + "' )) ");
		//add chenth 20180405 考虑应付对账单
		sqlBuffer.append("   or (arap_tally.corbillclass = 'fk' and pk_corbill in (select pk_paybill from ap_paybill where ap_paybill.billdate <= '" + dateline + "' )) ");
		sqlBuffer.append("   or (arap_tally.corbillclass = 'ys' and arap_tally.tallydate <= '" + dateline + "' ) ");
//		sqlBuffer.append("   or (arap_tally.corbillclass = 'ys' and pk_corbill in (select pk_recbill from ar_recbill where ar_recbill.billdate <= '" + dateline + "' )) ");
		sqlBuffer.append(" )");
		//add end

		sqlBuffer.append(ReportSqlUtils.getBillClassSql(queryVO.getAnaDirect(), ReportTableEnum.ARAP_TALLY)); // 分析方向
		sqlBuffer.append(ReportSqlUtils.getQueryObjSql(queryVO.getQryObjs(), false)); // 查询对象
		sqlBuffer.append(getCommonCondSql(false));
		
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		if(sqlBuffer.indexOf(ReportTableEnum.ARAP_TALLYAGR.getCode())>=0){
			sqlBuffer.append(ReportSqlUtils.getOrgSql(queryVO.getPk_orgs(), ReportTableEnum.ARAP_TALLYAGR));
		}
		
		sqlBuffer.append(" group by ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, TallyVO.getDefaultTableName()));
		sqlBuffer.append(", ").append(groupByBaseTally);
		if (beForeignCurrency) {
			sqlBuffer.append(", arap_tally.").append(PK_CURR);
		}
		sqlBuffer.append(", ").append(detailFields);
		
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		sqlBuffer.append(",arap_tally.pk_item ");
		//判断是否需要显示临时表
//				sqlBuffer.append(isShowExpireDate ? ", arap_tally_agr.expiredate" : "");
		sqlBuffer.append(isShowExpireDate ? ", substring(arap_tally_agr.expiredate,0,10)" : "");


		sqlBuffer.append(", ").append(tmpTableAlias).append(".propertyid ");
		sqlBuffer.append(", ").append(tmpTableAlias).append(".descr ");

		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		return sqlBuffer.toString();
		getBillAmountSql(insertBuffer, sqlBuffer);
		return insertBuffer.toString();
	}

	/**
	 * 查询应收账龄(应付账龄)
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private String getRecPayAgeByDateSql() throws SQLException, BusinessException {
		// 获取账龄方案临时表
		String tmpTable = ReportSqlUtils.getDateAnalyzeTmpTable(queryVO.getDatas());
		String tmpTableAlias = ReportSqlUtils.getTimeCtrlTmpTableAlias();

		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
//		sqlBuffer.append(getTmpTblName());
		StringBuffer insertBuffer = new StringBuffer(" insert into ");
		insertBuffer.append(getTmpTblName());

		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append(" select ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, TallyVO.getDefaultTableName())).append(", ");
		sqlBuffer.append(queryObjBaseTally).append(", ");
		sqlBuffer.append(beForeignCurrency ? "arap_tally." : "null ").append(PK_CURR).append(", ");
		sqlBuffer.append(detailFields).append(", ");
		
		//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		sqlBuffer.append(" arap_tally.pk_item, ");
		
		sqlBuffer.append(isShowExpireDate ? "arap_tally_agr.expiredate, " : "null expiredate, ");
		sqlBuffer.append(tmpTableAlias + ".propertyid accageid, " + tmpTableAlias+ ".descr accage, ");
		sqlBuffer.append("0 rn, 0 dr, ");

		String moneyTable = ReportTableEnum.ARAP_TALLY.getCode();
		/****************************************************/
		/*************** 按到期日/内控到期日期分析 ***************/
		if (IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate())
				|| IArapReportConstants.ACC_ANA_DATE_WITHINDATE.equals(queryVO.getAnaDate())) {
			moneyTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
		}

		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".quantity_de + ").append(moneyTable).append(
						".quantity_cr else 0 end) bill_qua, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".money_de + ").append(moneyTable).append(
						".money_cr else 0 end) bill_ori, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".local_money_de + ").append(moneyTable).append(
						".local_money_cr else 0 end) bill_loc, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".grouplocal_money_de + ").append(moneyTable).append(
						".grouplocal_money_cr else 0 end) gr_bill_loc, ");
		sqlBuffer.append("sum(case when ").append(moneyTable).append(".pk_dealnum = '~' then ")
				.append(moneyTable).append(".globallocal_money_de + ").append(moneyTable).append(
						".globallocal_money_cr else 0 end) gl_bill_loc, ");

		/****************************************************/

		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".quantity_de - ").append(
				moneyTable).append(".quantity_cr) accage_qua, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".money_de - ").append(
				moneyTable).append(".money_cr) accage_ori, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".local_money_de - ").append(
				moneyTable).append(".local_money_cr) accage_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".grouplocal_money_de - ").append(
				moneyTable).append(".grouplocal_money_cr) gr_accage_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".globallocal_money_de - ").append(
				moneyTable).append(".globallocal_money_cr) gl_accage_loc ");
		
		// sqlBuffer.append(sign).append("sum(arap_tally.quantity_de - arap_tally.quantity_cr) accage_qua, "); // 数量余额(账龄用)
		// sqlBuffer.append(sign).append("sum(arap_tally.money_de - arap_tally.money_cr) accage_ori, "); // 原币余额(账龄用)
		// sqlBuffer.append(sign).append("sum(arap_tally.local_money_de - arap_tally.local_money_cr) accage_loc, "); // 本币余额(账龄用)
		// sqlBuffer.append(sign).append("sum(arap_tally.grouplocal_money_de - arap_tally.grouplocal_money_cr) gr_accage_loc, "); // 集团本币余额(账龄用)
		// sqlBuffer.append(sign).append("sum(arap_tally.globallocal_money_de - arap_tally.globallocal_money_cr) gl_accage_loc "); // 全局本币余额(账龄用)

		sqlBuffer.append(" from ").append(ReportTableEnum.ARAP_TALLY.getCode());
		/****************************************************/
		/*************** 按到期日/内控到期日期分析 ***************/
		sqlBuffer.append(getJoinTableSql2());
		/****************************************************/
		// 连接日期期间(临时)表
		sqlBuffer.append(", ").append(tmpTable).append(tmpTableAlias);

		sqlBuffer.append(" where ").append(ReportSqlUtils.getFixedWhere());
		sqlBuffer.append(getCompositeWhereSql());

		// 分析日期
		String anaDateField = ReportSqlUtils.getAnaDateField(queryVO.getAnaDate());
		sqlBuffer.append(" and " + anaDateField + " >= " + tmpTableAlias + ".startdate ");
		sqlBuffer.append(" and " + anaDateField + " <= " + tmpTableAlias + ".enddate ");

		sqlBuffer.append(ReportSqlUtils.getBillClassSql(queryVO.getAnaDirect(), ReportTableEnum.ARAP_TALLY)); // 分析方向
		sqlBuffer.append(ReportSqlUtils.getQueryObjSql(queryVO.getQryObjs(), false)); // 查询对象
		sqlBuffer.append(getCommonCondSql(false));
		
		//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		// 效率优化
		if(sqlBuffer.indexOf(ReportTableEnum.ARAP_TALLYAGR.getCode())>=0){
			sqlBuffer.append(ReportSqlUtils.getOrgSql(queryVO.getPk_orgs(), ReportTableEnum.ARAP_TALLYAGR));
		}

		sqlBuffer.append(" group by ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, TallyVO.getDefaultTableName()));
		sqlBuffer.append(", ").append(groupByBaseTally);
		if (beForeignCurrency) {
			sqlBuffer.append(", arap_tally.").append(PK_CURR);
		}
		
		//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
		sqlBuffer.append(",arap_tally.pk_item ");
				
		sqlBuffer.append(", ").append(detailFields);
		sqlBuffer.append(isShowExpireDate ? ", arap_tally_agr.expiredate" : "");
		sqlBuffer.append(", ").append(tmpTableAlias).append(".propertyid ");
		sqlBuffer.append(", ").append(tmpTableAlias).append(".descr ");

		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		return sqlBuffer.toString();
		getBillAmountSql(insertBuffer, sqlBuffer);
		return insertBuffer.toString();
	}
	

	//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
	private void getBillAmountSql(StringBuffer insertBuffer, StringBuffer sqlBuffer) {
		// begin
		// 账龄明细只计算有余额的分录行，单据金额为同一到期日的有余额的行的汇总和。add by zhaoyanf
		insertBuffer.append(" select pk_group,pk_org,");

		StringBuffer selQryObj = new StringBuffer();
		for (int i = 0; i < queryVO.getQryObjs().size(); i++) {
			selQryObj.append("qryobj").append(i).append("pk,");
		}
		insertBuffer.append(selQryObj);
		insertBuffer.append(PK_CURR).append(", ");
		insertBuffer.append(detailFields.replace("arap_tally.", "")).append(", ");
		insertBuffer.append(" expiredate, accageid,accage,rn, dr, ");
		insertBuffer.append(" sum(bill_qua) as bill_qua, sum(bill_ori) as bill_ori,sum(bill_loc) as bill_loc,sum(gr_bill_loc) as gr_bill_loc,sum(gl_bill_loc) as gl_bill_loc,");
		insertBuffer.append(" sum(accage_qua) accage_qua,sum(accage_ori) as accage_ori,sum(accage_loc) as accage_loc,sum(gr_accage_loc) as gr_accage_loc,sum(gl_accage_loc) as gl_accage_loc ");
		insertBuffer.append(" from ( ").append(sqlBuffer).append(") tmp where accage_qua<>0 or accage_ori<>0 or accage_loc<>0 or gr_accage_loc<>0 or gl_accage_loc<>0 group by pk_group,pk_org,");
		insertBuffer.append(selQryObj);
		insertBuffer.append(PK_CURR).append(", ");
		insertBuffer.append(detailFields.replace("arap_tally.", "")).append(", ");
		insertBuffer.append(" expiredate, accageid,accage,rn, dr ");
		// end
	}

	/**
	 * 获取临时表名<br>
	 * 
	 * @return String<br>
	 * @throws SQLException<br>
	 */
	public String getTmpTblName() throws SQLException {
		if (StringUtils.isEmpty(tmpTblName)) {
			String[] colNames = getTmpTblColNames();
			Integer[] colTypes = getTmpTblColTypes();
			String tableName = "tmp_arap_recaccage_" + qryObjLen;
			tmpTblName = TmpTableCreator.createTmpTable(tableName, colNames, colTypes);
		}

		return tmpTblName;
	}

	/**
	 * 获取临时表列<br>
	 * @return String[]<br>
	 */
	public String[] getTmpTblColNames() {
		if (tmpTblColNames == null) {
			// 查询对象个数
			int qryObjLen = queryVO.getQryObjs().size();

			StringBuffer otherColNameBuf = new StringBuffer();
			otherColNameBuf.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", ""));
			otherColNameBuf.append(", ").append(PK_CURR).append(", ");
			otherColNameBuf.append(detailFields.replace(TallyVO.getDefaultTableName() + ".", ""));
			otherColNameBuf.append(", expiredate");
			otherColNameBuf.append(", accageid, accage, rn");
			otherColNameBuf.append(", dr");
			otherColNameBuf.append(", bill_qua, bill_ori, bill_loc, gr_bill_loc, gl_bill_loc");
			otherColNameBuf.append(", accage_qua, accage_ori, accage_loc, gr_accage_loc, gl_accage_loc");

			String[] otherColNames = otherColNameBuf.toString().split(",");

			tmpTblColNames = new String[qryObjLen + otherColNames.length];

			tmpTblColNames[0] = otherColNames[0];
			tmpTblColNames[1] = otherColNames[1];

			for (int i = 0; i < qryObjLen; i++) {
				tmpTblColNames[i + 2] = QRY_OBJ_PREFIX + i + "pk";
			}

			System.arraycopy(otherColNames, 2, tmpTblColNames, qryObjLen + 2, otherColNames.length - 2);
		}

		return tmpTblColNames;
	}

	/**
	 * 获取临时表列类型<br>
	 * @return Integer[]<br>
	 */
	private Integer[] getTmpTblColTypes() {
		if (tmpTblColTypes == null || tmpTblColTypes.length == 0) {
			tmpTblColTypes = new Integer[getTmpTblColNames().length];
			int i = 0;
			for (; i < tmpTblColTypes.length - 14; i++) {
				tmpTblColTypes[i] = Types.VARCHAR;
			}

			tmpTblColTypes[i++] = Types.INTEGER; // 账龄ID
			tmpTblColTypes[i++] = Types.VARCHAR; // 账龄描述
			tmpTblColTypes[i++] = Types.INTEGER; // RN列
			
			tmpTblColTypes[i++] = Types.INTEGER; // DR列

			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;

			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
		}

		return tmpTblColTypes;
	}

	/**
	 * 获取临时表名<br>
	 * 
	 * @return String<br>
	 * @throws SQLException<br>
	 */
	public String getTmpTblName2() throws SQLException {
		if (StringUtils.isEmpty(tmpTblName2)) {
			String[] colNames = getTmpTblColName2s();
			Integer[] colTypes = getTmpTblColType2s();
			String tableName = "tmp_arap_recaccage2_" + qryObjLen;
			tmpTblName2 = TmpTableCreator.createTmpTable(tableName, colNames, colTypes);
		}

		return tmpTblName2;
	}

	/**
	 * 获取临时表列<br>
	 * 
	 * @return String[]<br>
	 */
	private String[] getTmpTblColName2s() {
		if (tmpTblColName2s == null) {
			// 查询对象个数
			int qryObjLen = queryVO.getQryObjs().size();

			StringBuffer otherColNameBuf = new StringBuffer();
			otherColNameBuf.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", ""));
			otherColNameBuf.append(", ").append(PK_CURR);
			otherColNameBuf.append(", accageid, accage, rn");
			
			otherColNameBuf.append(", dr");
			
			otherColNameBuf.append(", accage_qua, accage_ori, accage_loc, gr_accage_loc, gl_accage_loc");
			String[] otherColNames = otherColNameBuf.toString().split(",");

			tmpTblColName2s = new String[qryObjLen + otherColNames.length];

			tmpTblColName2s[0] = otherColNames[0];
			tmpTblColName2s[1] = otherColNames[1];

			for (int i = 0; i < qryObjLen; i++) {
				tmpTblColName2s[i + 2] = QRY_OBJ_PREFIX + i + "pk";
			}

			System.arraycopy(otherColNames, 2, tmpTblColName2s, qryObjLen + 2, otherColNames.length - 2);
		}

		return tmpTblColName2s;
	}

	/**
	 * 获取临时表列类型<br>
	 * 
	 * @return Integer[]<br>
	 */
	private Integer[] getTmpTblColType2s() {
		if (tmpTblColType2s == null || tmpTblColType2s.length == 0) {
			tmpTblColType2s = new Integer[getTmpTblColName2s().length];
			int i = 0;
			for (; i < tmpTblColType2s.length - 9; i++) {
				tmpTblColType2s[i] = Types.VARCHAR;
			}
			tmpTblColType2s[i++] = Types.INTEGER; // 账龄ID
			tmpTblColType2s[i++] = Types.VARCHAR; // 账龄描述
			tmpTblColType2s[i++] = Types.INTEGER; // RN列
			
			tmpTblColType2s[i++] = Types.INTEGER; // DR列

			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
		}

		return tmpTblColType2s;
	}

	protected List<ComputeTotal> getAllQryObj() {
		// 构造需要计算小计合计的对象
		if (allQryobjList.size() == 0) {
			List<String> dimensions = new ArrayList<String>();
			String[] fixedObjs = fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", "").split(",");
			for (int i = 0; i < fixedObjs.length; i++) {
				dimensions.add(fixedObjs[i].trim());
			}
			dimensions.addAll(Arrays.asList(getQueryObjs()));

			int i = 0;
			ComputeTotal total = null;
			for (; i < dimensions.size(); i++) {
				total = new ComputeTotal();
				total.field = dimensions.get(i);
				total.isDimension = true;
				allQryobjList.add(total);
			}

//			total = new ComputeTotal();
//			total.field = dimensions.get(i);
//			total.isDimension = beForeignCurrency;
//			allQryobjList.add(total);

			total = new ComputeTotal();
			total.field = "pk_currtype";
			total.isDimension = false;
			allQryobjList.add(total);
		}

		return allQryobjList;
	}

	private UFDate getBusisDate(boolean begin) {
		UFDateTime busiDateWithTime = new UFDateTime(InvocationInfoProxy.getInstance().getBizDateTime());
		return busiDateWithTime.getEndDate();
	}
	//add for 新加坡BDA 20161111
	@Override
	public String getResultDescSql() throws SQLException, BusinessException {
		// TODO 自动生成的方法存根
		return null;
	}
	//add end
	 
}

