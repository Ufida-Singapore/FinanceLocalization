package nc.bs.arap.sql;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import nc.bs.arap.util.SqlUtils;
import nc.bs.arap.utils.TmpTableCreator;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.jdbc.framework.util.DBConsts;
import nc.pub.report.utils.ReportSqlUtils;
import nc.utils.arap.ArapReportResource;
import nc.utils.fipub.SmartProcessor;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.arap.comreport.ReportTableEnum;
import nc.vo.arap.tally.TallyVO;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.ReportInitializeVO;
import nc.vo.fipub.report.ReportQueryCondVO;
import nc.vo.fipub.utils.SqlBuilder;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

/**
 * 应收/付欠款分析查询SQL生成器(应收、应付)<br>
 * 
 * @author 梁国荣<br>
 * @since V60<br>
 */
public class RecPayArrearAnaSQLCreator extends ArapBaseSqlCreator {

	private static String detailFields = "arap_tally.pk_billtype, arap_tally.pk_bill, arap_tally.billno, arap_tally.billdate";

	// 收付方向
	private String rec_pay_direct = null;

	private String tmpTblName = null;
	private String[] tmpTblColNames = null;
	private Integer[] tmpTblColTypes = null;

	private String tmpTblName2 = null;
	private String[] tmpTblColName2s = null;
	private Integer[] tmpTblColType2s = null;

	private List<ComputeTotal> allQryobjList = new ArrayList<ComputeTotal>();

	public void setParams(ReportQueryCondVO queryVO) {
		super.setParams(queryVO);
		// 获取收付方向
		rec_pay_direct = ((ReportInitializeVO) queryVO.getRepInitContext().getParentVO()).getRec_pay_direct();
	}

	public String[] getArrangeSqls() throws SQLException, BusinessException {
		List<String> sqlList = new ArrayList<String>();

		if (IArapReportConstants.ANA_DIRECT_RECABLE.equals(queryVO.getAnaDirect())
				|| IArapReportConstants.ANA_DIRECT_PAYABLE.equals(queryVO.getAnaDirect())) {
			// 查询应收/付余额
			sqlList.add(getRecablePayableBalSql()); // rn = 0; accageid = 2
		} else if (IArapReportConstants.ANA_DIRECT_RECABLE_REC.equals(queryVO.getAnaDirect())
				|| IArapReportConstants.ANA_DIRECT_PAYABLE_PAY.equals(queryVO.getAnaDirect())) {
			// 查询应收/付余额
			sqlList.add(getRecablePayableBalSql());
			// 查询收/付款余额(不含预收/付)
			sqlList.add(getRecPayBalSql(false)); // rn = 0; accageid = 3
		} else {
			// 查询应收/付余额
			sqlList.add(getRecablePayableBalSql());
			// 查询收/付款余额(含预收/付)
			sqlList.add(getRecPayBalSql(true)); // rn = 0; accageid = 4
		}

		// 准备结果数据(放入另一张临时表)
		if (!queryVO.isQueryDetail()) {
			sqlList.add(getCollectSql()); // rn = 0; accageid > 0
		}

		// 计算欠款合计
		sqlList.add(getArrearTotalSql()); // rn = 0; accageid = 1
		sqlList.add(getAdjustSql());

		if (!queryVO.isQueryDetail() && queryVO.getShowFrontN() > 0) {
			// 取得前N名
			sqlList.addAll(getFrontNSqls());
		}

		// 计算小计、合计
		sqlList.add(getTotalSqls());

		return sqlList.toArray(new String[0]);
	}

	public String getResultSql() throws SQLException, BusinessException {
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

			sqlBuffer.append("(case when isnull(v.").append(qryObjs[i]).append(
					", '~') = '~' then 1 else 0 end) as isnull").append(i);
		}

		sqlBuffer.append(", v.").append(PK_CURR).append(" ").append(PK_CURR).append(", (case when isnull(v.").append(PK_CURR)
			.append(", '~') = '~' then 1 else 0 end) as is_currtype_null"); // is_currtype_null

		sqlBuffer.append(", v.rn, v.accageid, v.accage");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", ").append(detailFields.replace(TallyVO.getDefaultTableName(), "v"));
		}
		sqlBuffer.append(", v.bal_qua, v.bal_ori, v.bal_loc, v.gr_bal_loc, v.gl_bal_loc");
		sqlBuffer.append(", 0 ").append(ORDER_MANAGE_VSEQ); // vseq

		// ================ from子句
		sqlBuffer.append(getFromResultTable(queryVO.isQueryDetail() ? getTmpTblName()
				: getTmpTblName2(), qryObjList, qryObjs));

		// ================ where子句
		sqlBuffer.append(" where ");
		// sqlBuffer.append(" v.bal_qua != 0 and ");
		sqlBuffer.append(" v.bal_ori != 0 ");
		if (ORG_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.bal_loc != 0 ");
		} else if (GROUP_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.gr_bal_loc != 0 ");
		} else if (GLOBLE_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
			sqlBuffer.append(" or v.gl_bal_loc != 0 ");
		}

		// ================ order by子句
		sqlBuffer.append(" order by ");
		sqlBuffer.append("is_org_null, code_org");
		for (int i = 0; i < qryObjList.size(); i++) {
			sqlBuffer.append(", isnull").append(i).append(", ").append(
					QRY_OBJ_PREFIX).append(i).append("code");
		}
		sqlBuffer.append(", is_currtype_null, pk_currtype, rn");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", billdate, billno");
		}

		return sqlBuffer.toString();
	}

	public List<String> getFrontNSqls() throws SQLException, BusinessException {
		List<String> sqlList = new ArrayList<String>();

		String[] qryObjs = getQueryObjs();

		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		sqlBuffer.append(getTmpTblName2());

		sqlBuffer.append(" select ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "v"));
		sqlBuffer.append(", ").append(queryObjBaseExt.replace("a.", "v."));

		sqlBuffer.append(", v.").append(PK_CURR).append(", -1 rn, v.accageid, v.accage"); // rn = -1
		sqlBuffer.append(", v.bal_qua, v.bal_ori, v.bal_loc, v.gr_bal_loc, v.gl_bal_loc");

		// ================ from子句
		sqlBuffer.append(" from ");
		sqlBuffer.append(getTmpTblName2()).append(" v ");
		sqlBuffer.append(" left outer join (");
		sqlBuffer.append(" select ").append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "r"));
		sqlBuffer.append(", r.").append(qryObjs[0]).append(", r.").append(PK_CURR);
		sqlBuffer.append(" from ");
		sqlBuffer.append("(");
		sqlBuffer.append("select mid.*, dense_rank() over (partition by mid.pk_org order by mid.bal_loc desc) rank from ");
		sqlBuffer.append(getTmpTblName2()).append(" mid ");
		sqlBuffer.append(" where mid.accageid = 1) r where r.rank <= ").append(queryVO.getShowFrontN());
		sqlBuffer.append(") frontN ");
		sqlBuffer.append(" on v.pk_org = ").append("frontN.pk_org ");
		sqlBuffer.append(" and v.").append(qryObjs[0]).append(" = frontN.").append(qryObjs[0]);
		sqlBuffer.append(" and coalesce(v.").append(PK_CURR).append(", '~') = coalesce(frontN.").append(PK_CURR).append(", '~')");

		sqlBuffer.append(" where ");
		sqlBuffer.append(" coalesce(frontN.").append(qryObjs[0]).append(", '~') != '~'");

		sqlList.add(sqlBuffer.toString());

		// 删除其余数据
		sqlBuffer = new StringBuffer(" delete from ");
		sqlBuffer.append(getTmpTblName2());
		sqlBuffer.append(" where rn >= 0 ");
		sqlList.add(sqlBuffer.toString());

		return sqlList;
	}

	public String getDistinctCurrency() throws SQLException, BusinessException {
		return null;
	}

	public String[] getDropTableSqls() throws SQLException, BusinessException {
		return new String[0];
	}

	/**
	 * 查询应收/付余额<br>
	 * 
	 * @return String<br>
	 * @throws SQLException<br>
	 * @throws BusinessException<br>
	 */
	private String getRecablePayableBalSql() throws SQLException, BusinessException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" insert into ").append(getTmpTblName());

		sqlBuffer.append(" select ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "arap_tally"));
		sqlBuffer.append(", ").append(queryObjBaseTally);
		sqlBuffer.append(beForeignCurrency ? ", arap_tally." : ", null ");
		sqlBuffer.append(PK_CURR).append(", ").append(detailFields);

		sqlBuffer.append(", 0 rn, 2 accageid, '");
		if (DIRECT_REC.equals(rec_pay_direct)) {
			sqlBuffer.append(ArapReportResource.getReceivableLbl()); // 应收款
		} else {
			sqlBuffer.append(ArapReportResource.getPayableLbl()); // 应付款
		}
		sqlBuffer.append("' accage, ");

		String moneyTable = ReportTableEnum.ARAP_TALLY.getCode();
		if (IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate()) || IArapReportConstants.ACC_ANA_DATE_WITHINDATE
				.equals(queryVO.getAnaDate())) {
			moneyTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
		}
		// 这里取负
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".quantity_cr - ")
				.append(moneyTable).append(".quantity_de) bal_qua, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".money_cr - ")
				.append(moneyTable).append(".money_de) bal_ori, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".local_money_cr - ")
				.append(moneyTable).append(".local_money_de) bal_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".grouplocal_money_cr - ")
				.append(moneyTable).append(".grouplocal_money_de) gr_bal_loc, ");
		sqlBuffer.append(sign).append("sum(").append(moneyTable).append(".globallocal_money_cr - ")
				.append(moneyTable).append(".globallocal_money_de) gl_bal_loc ");

		sqlBuffer.append(" from ").append(ReportTableEnum.ARAP_TALLY.getCode());
		sqlBuffer.append(getJoinTableSql());

		sqlBuffer.append(" where ").append(ReportSqlUtils.getFixedWhere());
		sqlBuffer.append(getCompositeWhereSql());

		if (IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern())) {
			// 按“点余额”分析
			sqlBuffer.append(" and ").append(ReportSqlUtils.getAnaDateField(queryVO.getAnaDate()));
			sqlBuffer.append(" <= '").append(queryVO.getDateline().toString()).append("' ");
			sqlBuffer.append(" and arap_tally.tallydate <= '").append(queryVO.getDateline()).append("' ");
		}

		String[] billClass = null;
		if (DIRECT_REC.equals(rec_pay_direct)) {
			billClass = new String[] { IBillFieldGet.YS, IBillFieldGet.ZS };
		} else {
			billClass = new String[] { IBillFieldGet.YF, IBillFieldGet.ZF };
		}
		sqlBuffer.append(SqlUtils.getInStr(" and arap_tally.billclass ", billClass, false)); // 查询方向
		sqlBuffer.append(ReportSqlUtils.getQueryObjSql(queryVO.getQryObjs(), false)); // 查询对象
		sqlBuffer.append(getCommonCondSql(false));

		sqlBuffer.append(" group by ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "arap_tally"));
		sqlBuffer.append(", ").append(groupByBaseTally);
		if (beForeignCurrency) {
			sqlBuffer.append(", arap_tally.").append(PK_CURR);
		}
		sqlBuffer.append(", ").append(detailFields);

		return sqlBuffer.toString();
	}

	/**
	 * 查询收款余额<br>
	 * 
	 * @param includePrePay 是否包含预收/付<br>
	 * @return String<br>
	 * @throws SQLException<br>
	 * @throws BusinessException<br>
	 */
	private String getRecPayBalSql(boolean includePrePay) throws SQLException, BusinessException {
		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		sqlBuffer.append(getTmpTblName());

		sqlBuffer.append(" select ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "arap_tally"));
		sqlBuffer.append(", ").append(queryObjBaseTally);
		sqlBuffer.append(beForeignCurrency ? ", arap_tally." : ", null ");
		sqlBuffer.append(PK_CURR).append(", ").append(detailFields).append(", ");
		sqlBuffer.append("0 rn, ");
		if (DIRECT_REC.equals(rec_pay_direct)) {
			sqlBuffer.append("(case when arap_tally.prepay = 'N' then 3 else 4 end) as accageid, ");
			sqlBuffer.append("(case when arap_tally.prepay = 'N' then '").append(ArapReportResource.getGatherLbl()) // 已收款
					.append("' else '").append(ArapReportResource.getPreGatherLbl()).append("' end) as accage, "); // 预收款
		} else {
			sqlBuffer.append("(case when arap_tally.prepay = 'N' then 3 else 4 end) as accageid, ");
			sqlBuffer.append("(case when arap_tally.prepay = 'N' then '").append(ArapReportResource.getPayLbl()) // 已付款
					.append("' else '").append(ArapReportResource.getPrePayLbl()).append("' end) as accage, "); // 预付款
		}
		String moneyTable = ReportTableEnum.ARAP_TALLY.getCode();
		if ((IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate()) // 分析日期：到期日
				|| IArapReportConstants.ACC_ANA_DATE_WITHINDATE.equals(queryVO.getAnaDate())) // 分析日期：内控到期日期
				&& IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern())) {
			moneyTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
		}
		sqlBuffer.append(sign).append("(sum(").append(moneyTable).append(".quantity_cr - ").append(moneyTable)
				.append(".quantity_de)) bal_qua, ");
		sqlBuffer.append(sign).append("(sum(").append(moneyTable).append(".money_cr - ").append(moneyTable)
				.append(".money_de)) bal_ori, ");
		sqlBuffer.append(sign).append("(sum(").append(moneyTable).append(".local_money_cr - ").append(moneyTable)
				.append(".local_money_de)) bal_loc, ");
		sqlBuffer.append(sign).append("(sum(").append(moneyTable).append(".grouplocal_money_cr - ").append(moneyTable)
				.append(".grouplocal_money_de)) gr_bal_loc, ");
		sqlBuffer.append(sign).append("(sum(").append(moneyTable).append(".globallocal_money_cr - ").append(moneyTable)
				.append(".globallocal_money_de)) gl_bal_loc ");

		sqlBuffer.append(" from ").append(ReportTableEnum.ARAP_TALLY.getCode());
		sqlBuffer.append(getJoinTableSql());

		sqlBuffer.append(" where ").append(ReportSqlUtils.getFixedWhere());
		sqlBuffer.append(getCompositeWhereSql());

		if (IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern())) {
			// 按点余额分析
			sqlBuffer.append(" and ").append(ReportSqlUtils.getAnaDateField(queryVO.getAnaDate()));
			sqlBuffer.append(" <= '").append(queryVO.getDateline().toString()).append("' ");
		}

		if (!includePrePay) {
			// 查询预收余额
			sqlBuffer.append(" and (coalesce(arap_tally.prepay, '~') = '~' or arap_tally.prepay = '")
					.append(UFBoolean.FALSE.toString()).append("') ");
		}

		String[] billClass = null;
		if (DIRECT_REC.equals(rec_pay_direct)) {
			billClass = new String[] { IBillFieldGet.SK };
		} else {
			billClass = new String[] { IBillFieldGet.FK };
		}
		sqlBuffer.append(SqlUtils.getInStr(" and arap_tally.billclass ", billClass, false)); // 查询方向
		sqlBuffer.append(ReportSqlUtils.getQueryObjSql(queryVO.getQryObjs(), false)); // 查询对象
		sqlBuffer.append(getCommonCondSql(false));

		sqlBuffer.append(" group by ");
		sqlBuffer.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE, "arap_tally"));
		sqlBuffer.append(", ").append(groupByBaseTally);
		if (beForeignCurrency) {
			sqlBuffer.append(", arap_tally.").append(PK_CURR);
		}
		sqlBuffer.append(", ").append(detailFields);
		sqlBuffer.append(", arap_tally.prepay");

		sqlBuffer.append(" having ");
		sqlBuffer.append(" sum(").append(moneyTable).append(".local_money_de - ").append(moneyTable).append(".local_money_cr) != 0");
//		sqlBuffer.append("sum(").append(moneyTable).append(".quantity_de - ").append(moneyTable).append(".quantity_cr) != 0 ");
//		sqlBuffer.append(" or sum(").append(moneyTable).append(".money_de - ").append(moneyTable).append(".money_cr) != 0 ");
//		if (ORG_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
//			sqlBuffer.append(" or sum(").append(moneyTable).append(".local_money_de - ").append(moneyTable).append(".local_money_cr) != 0");
//		} else if (GROUP_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
//			sqlBuffer.append(" or sum(").append(moneyTable).append(".grouplocal_money_de - ").append(moneyTable).append(".grouplocal_money_cr) != 0 ");
//		} else if (GLOBLE_LOCAL_CURRENCY.equals(queryVO.getLocalCurrencyType())) {
//			sqlBuffer.append(" or sum(").append(moneyTable).append(".globallocal_money_de - ").append(moneyTable).append(".globallocal_money_cr) != 0 ");
//		}

		return sqlBuffer.toString();
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
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR);
		sqlBuffer.append(", 0 rn");
		sqlBuffer.append(", accageid, accage");
		// 应收款
		sqlBuffer.append(", sum(bal_qua) bal_qua");
		sqlBuffer.append(", sum(bal_ori) bal_ori");
		sqlBuffer.append(", sum(bal_loc) bal_loc");
		sqlBuffer.append(", sum(gr_bal_loc) gr_bal_loc");
		sqlBuffer.append(", sum(gl_bal_loc) gl_bal_loc");

		sqlBuffer.append(" from ");
		sqlBuffer.append(getTmpTblName());

		sqlBuffer.append(" group by ");
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR);
		sqlBuffer.append(", accageid, accage");

		return sqlBuffer.toString();
	}

	private String getArrearTotalSql() throws SQLException {
		String collFixedFields = fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", "");
		String collDetailFields = detailFields.replace(TallyVO.getDefaultTableName() + ".", "");

		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		sqlBuffer.append(queryVO.isQueryDetail() ? getTmpTblName() : getTmpTblName2());

		sqlBuffer.append(" select ");
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR);
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", ").append(collDetailFields);
		}
		sqlBuffer.append(", rn, 1 accageid, '").append(ArapReportResource.getArrearTotalLbl()).append("' accage, ");
		sqlBuffer.append("-sum(bal_qua) bal_qua, "); // 这里取负
		sqlBuffer.append("-sum(bal_ori) bal_ori, ");
		sqlBuffer.append("-sum(bal_loc) bal_loc, ");
		sqlBuffer.append("-sum(gr_bal_loc) gr_bal_loc, ");
		sqlBuffer.append("-sum(gl_bal_loc) gl_bal_loc ");

		sqlBuffer.append(" from ");
		sqlBuffer.append(queryVO.isQueryDetail() ? getTmpTblName() : getTmpTblName2());

		sqlBuffer.append(" group by ");
		sqlBuffer.append(collFixedFields);
		sqlBuffer.append(", ").append(queryObjOrderExt);
		sqlBuffer.append(", ").append(PK_CURR);
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(", ").append(collDetailFields);
		}
		sqlBuffer.append(", rn");

		return sqlBuffer.toString();
	}

	private String getAdjustSql() throws SQLException {
		StringBuffer sqlBuffer = new StringBuffer(" update ");
		sqlBuffer.append(queryVO.isQueryDetail() ? getTmpTblName() : getTmpTblName2());
		sqlBuffer.append(" set ");
		sqlBuffer.append("bal_qua = -bal_qua, ");
		sqlBuffer.append("bal_ori = -bal_ori, ");
		sqlBuffer.append("bal_loc = -bal_loc, ");
		sqlBuffer.append("gr_bal_loc = -gr_bal_loc, ");
		sqlBuffer.append("gl_bal_loc = -gl_bal_loc ");

		sqlBuffer.append(" where ");
		sqlBuffer.append(" accageid = 2 ");

		return sqlBuffer.toString();
	}

	/**
	 * 计算小计合计
	 * 
	 * @return
	 * @throws SQLException 
	 */
	private String getTotalSqls() throws SQLException {
		// 构造小计、合计对象
		List<ComputeTotal> allQryObjs = getAllQryObj();
		List<String> computed = new ArrayList<String>();

		// 正式拼写SQL
		StringBuffer sqlBuffer = new StringBuffer(" insert into ");
		sqlBuffer.append(queryVO.isQueryDetail() ? getTmpTblName() : getTmpTblName2());

		sqlBuffer.append(" select ");
		int i = 0;
		for (; i < allQryObjs.size(); i++) {
			if (allQryObjs.get(i).isDimension) {
				sqlBuffer.append(allQryObjs.get(i).field).append(", ");
				computed.add(allQryObjs.get(i).field);
			} else {
				sqlBuffer.append("null ").append(allQryObjs.get(i).field).append(", ");
			}
		}
		if (queryVO.isQueryDetail()) {
			// 明细查询
			String[] details = detailFields.replace(TallyVO.getDefaultTableName() + ".", "").split(",");
			for (String d : details) {
				sqlBuffer.append("null ").append(d).append(", ");
			}
		}
		i = 0;
		for (; i < computed.size(); i++) {
			sqlBuffer.append("grouping(").append(computed.get(i)).append(") + ");
		}
		sqlBuffer.append(SmartProcessor.MAX_ROW).append(" rn, ");
		sqlBuffer.append("accageid, accage, ");
		sqlBuffer.append("sum(bal_qua) bal_qua, sum(bal_ori) bal_ori, sum(bal_loc) bal_loc, sum(gr_bal_loc) gr_bal_loc, sum(gl_bal_loc) gl_bal_loc ");

		sqlBuffer.append(" from ");
		if (queryVO.isQueryDetail()) {
			sqlBuffer.append(getTmpTblName());
		} else {
			sqlBuffer.append(getTmpTblName2());
		}

		sqlBuffer.append(" group by ");
		switch (SqlBuilder.getDatabaseType()) {
		case DBConsts.SQLSERVER:
			i = 0;
			for (; i < computed.size(); i++) {
				sqlBuffer.append(computed.get(i)).append(", ");
			}
			sqlBuffer.append("accageid, accage");
			sqlBuffer.append(" with cube ");
			break;
		case DBConsts.DB2:
		case DBConsts.ORACLE:
			sqlBuffer.append("cube(");
			i = 0;
			for (; i < computed.size(); i++) {
				sqlBuffer.append(computed.get(i)).append(", ");
			}
			sqlBuffer.append("accageid, accage)");
			break;
		default:
			break;
		}

		sqlBuffer.append(" having ");
		i = 0;
		for (; i < computed.size() - 1; i++) {
			sqlBuffer.append("grouping(").append(computed.get(i)).append(") <= grouping(").append(
					computed.get(i + 1)).append(") and ");
		}
		sqlBuffer.append("grouping(").append(allQryObjs.get(0).field).append(") = 0 "); // 集团不计算总计
		if (queryVO.getPk_orgs().length <= 1) {
			// 多业务单元查询才计算总计
			sqlBuffer.append(" and grouping(").append(allQryObjs.get(1).field).append(") = 0 ");
		}
		sqlBuffer.append(" and grouping(accageid) = 0 ");
		sqlBuffer.append(" and grouping(accage) = 0 ");

		return sqlBuffer.toString();
	}

	/**
	 * 构造需要计算小计合计的对象
	 * 
	 * @return
	 */
	protected List<ComputeTotal> getAllQryObj() {
		// 构造需要计算小计合计的对象
		if (allQryobjList.size() == 0) {
			List<String> dimensions = new ArrayList<String>();

			dimensions.add("pk_group");
			dimensions.add("pk_org");

			String[] qryobjs = queryObjOrderExt.split(",");
			for (int i = 0; i < qryobjs.length; i++) {
				dimensions.add(qryobjs[i].trim());
			}

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

	/**
	 * 获取临时表名
	 * 
	 * @return
	 * @throws SQLException
	 */
	private String getTmpTblName() throws SQLException {
		if (StringUtils.isEmpty(tmpTblName)) {
			String[] colNames = getTmpTblColNames();
			Integer[] colTypes = getTmpTblColTypes();
			String tableName = "tmp_arap_recpayarrear_" + qryObjLen;
			tmpTblName = TmpTableCreator.createTmpTable(tableName, colNames, colTypes);
		}
		return tmpTblName;
	}

	private String[] getTmpTblColNames() {
		if (tmpTblColNames == null) {
			// 查询对象个数
			int qryObjLen = queryVO.getQryObjs().size();

			StringBuffer otherColNameBuf = new StringBuffer();
			otherColNameBuf.append(fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", ""));
			otherColNameBuf.append(", ").append(PK_CURR).append(", ");
			otherColNameBuf.append(detailFields.replace(TallyVO.getDefaultTableName() + ".", ""));
			otherColNameBuf.append(", rn, accageid, accage");
			otherColNameBuf.append(", bal_qua, bal_ori, bal_loc, gr_bal_loc, gl_bal_loc");

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

	private Integer[] getTmpTblColTypes() {
		if (tmpTblColTypes == null) {
			tmpTblColTypes = new Integer[getTmpTblColNames().length];

			int i = 0;
			for (; i < tmpTblColTypes.length - 8; i++) {
				tmpTblColTypes[i] = Types.VARCHAR;
			}

			tmpTblColTypes[i++] = Types.INTEGER;
			tmpTblColTypes[i++] = Types.INTEGER;
			tmpTblColTypes[i++] = Types.VARCHAR;

			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;
			tmpTblColTypes[i++] = Types.DECIMAL;

		}

		return tmpTblColTypes;
	}

	private String getTmpTblName2() throws SQLException {
		if (StringUtils.isEmpty(tmpTblName2)) {
			String[] colNames = getTmpTblColName2s();
			Integer[] colTypes = getTmpTblColType2s();
			String tableName = "tmp_arap_recpayarrear2_" + qryObjLen;
			tmpTblName2 = TmpTableCreator.createTmpTable(tableName, colNames, colTypes);
		}

		return tmpTblName2;
	}

	private String[] getTmpTblColName2s() {
		if (tmpTblColName2s == null) {
			// 查询对象个数
			int qryObjLen = queryVO.getQryObjs().size();

			String otherColName = fixedFields.replace(IArapReportConstants.REPLACE_TABLE + ".", "");
			otherColName += (", " + PK_CURR);
			otherColName += ", rn, accageid, accage";
			otherColName += ", bal_qua, bal_ori, bal_loc, gr_bal_loc, gl_bal_loc";
			String[] otherColNames = otherColName.split(",");

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

	private Integer[] getTmpTblColType2s() {
		if (tmpTblColType2s == null) {
			tmpTblColType2s = new Integer[getTmpTblColName2s().length];
			int i = 0;
			for (; i < tmpTblColType2s.length - 8; i++) {
				tmpTblColType2s[i] = Types.VARCHAR;
			}

			tmpTblColType2s[i++] = Types.INTEGER; // rn
			tmpTblColType2s[i++] = Types.INTEGER; // accageid
			tmpTblColType2s[i++] = Types.VARCHAR; // accage

			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
			tmpTblColType2s[i++] = Types.DECIMAL;
		}

		return tmpTblColType2s;
	}

	protected String getJoinTableSql2() throws BusinessException {
		if (beFirstJoin) {
			StringBuffer sqlBuffer = new StringBuffer();

			// 处理到期日,最终余额不需要从协议表取得
			String joinTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
			if ((IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate()) // 分析日期：到期日
					|| IArapReportConstants.ACC_ANA_DATE_WITHINDATE.equals(queryVO.getAnaDate())) // 分析日期：内控到期日期
					&& IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern())) { 
				sqlBuffer.append(" inner join ").append(joinTable);
				sqlBuffer.append(" on arap_tally.pk_tally =  ").append(joinTable).append(".pk_tally ");
			}

			// 处理查询模板SQL
			String whereSql = queryVO.getWhereSql();
			joinTable = ReportTableEnum.ARAP_TALLYALL.getCode();
			if ((!StringUtils.isEmpty(whereSql) && whereSql.indexOf(joinTable) >= 0) || !getBeThroughBal()) {
				sqlBuffer.append(" left outer join ").append(joinTable);
				sqlBuffer.append(" on arap_tally.pk_bill = ").append(joinTable).append(".pk_bill ");
				sqlBuffer.append(" and arap_tally.pk_item = ").append(joinTable).append(".pk_item ");
			}
			if(UFBoolean.TRUE.equals(queryVO.getUserObject().get(IArapReportConstants.IS_MATCUSTCODE))){
				sqlBuffer.append(" left outer join ").append(joinTable);
				sqlBuffer.append(" on arap_tally.pk_bill = ").append(joinTable).append(".pk_bill ");
				sqlBuffer.append(" and arap_tally.pk_item = ").append(joinTable).append(".pk_item ");
			}
			joinTableSql = sqlBuffer.toString();
			beFirstJoin = false;
		}
		
		return joinTableSql;
	}
	//add for 新加坡BDA 20161111
	@Override
	public String getResultDescSql() throws SQLException, BusinessException {
		// TODO 自动生成的方法存根
		return null;
	}
	//add end
	
}

// /:~
