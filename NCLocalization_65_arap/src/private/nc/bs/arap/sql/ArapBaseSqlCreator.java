package nc.bs.arap.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.fipub.queryobjreg.IReportQueryObjRegQuery;
import nc.itf.fipub.report.IPubReportConstants;
import nc.jdbc.framework.util.DBConsts;
import nc.pub.report.utils.ArapReportDataPowerMap;
import nc.pub.report.utils.ReportSqlUtils;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.arap.comreport.ReportTableEnum;
import nc.vo.bd.account.AccAsoaVO;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.QueryObjVO;
import nc.vo.fipub.report.ReportInitializeVO;
import nc.vo.fipub.report.ReportQueryCondVO;
import nc.vo.fipub.utils.SqlBuilder;
import nc.vo.ml.MultiLangContext;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

public abstract class ArapBaseSqlCreator implements IPubReportConstants {

	// 固定查询字段
	protected static final String fixedFields = "@Table.pk_group, @Table.pk_org";
	protected static final String PK_CURR = "pk_currtype";

	protected static final String ARAP_BALANCE_FLD = "ACCMONTH,ACCPERIOD,ACCYEAR,BILLCLASS,BILLSTATUS,COMPONDMD5,CUSTOMER,DEF1,DEF2,DEF3,DEF4,DEF5,DEF6,DR,EFFECTSTATUS,GLOBALLOCAL_MONEY_CR,GLOBALLOCAL_MONEY_DE,GROUPLOCAL_MONEY_CR,GROUPLOCAL_MONEY_DE,LOCAL_MONEY_CR,LOCAL_MONEY_DE,MONEY_CR,MONEY_DE,OBJTYPE,PK_BALANCE,PK_CURRTYPE,PK_DEPTID,PK_GROUP,PK_ORG,PK_ORG_V,PK_PSNDOC,QUANTITY_CR,QUANTITY_DE,SUPPLIER,TS";

	protected static final String zeroTime = " 00:00:00";
	protected static final String sTime = " 23:59:59";

	// 查询条件构成的VO
	protected ReportQueryCondVO queryVO = null;

	protected int qryObjLen = 0;

	// 查询对象
	protected String queryObjBaseBal = "";
	protected String groupByBaseBal = "";
	protected String queryObjBaseTally = "";
	protected String groupByBaseTally = "";
	protected String queryObjBaseExt = "";
	protected String queryObjOrderExt = "";

	// 是否外币金额式查询
	protected boolean beForeignCurrency = false;

	protected String joinTableSql = "";
	protected boolean beFirstJoin = true;
	private String compositeWhereSql = "";
	private boolean beFirstWhere = true;

	private UFBoolean beThroughBal = null;

	private String fromDummyTable = null;

	protected String sign = "#";

	protected static String bdTable = "bd";

	public void setParams(ReportQueryCondVO queryVO) {
		this.queryVO = queryVO;

		List<QryObj> qryObjList = queryVO.getQryObjs();
		qryObjLen = qryObjList.size();
		QryObj qryObj = null;
		for (int i = 0; i < qryObjLen; i++) {
			qryObj = qryObjList.get(i);
			if (IBDMetaDataIDConst.CUSTSUPPLIER.equals(qryObj.getPk_bdinfo())) {
				// 客商
//				queryObjBaseBal += ("(case when arap_balance.customer = '~' then arap_balance.supplier else arap_balance.customer end) qryobj" + i + "pk, ");
				queryObjBaseBal += ("(case when (arap_balance.customer = '~' and arap_balance.supplier = '~') then '" + IPubReportConstants.NVLOBJ + "' when arap_balance.customer = '~' then arap_balance.supplier else arap_balance.customer end) qryobj" + i + "pk, ");
				groupByBaseBal += "(case when arap_balance.customer = '~' then arap_balance.supplier else arap_balance.customer end), ";
//				queryObjBaseTally += ("(case when arap_tally.customer = '~' then arap_tally.supplier else arap_tally.customer end) qryobj" + i + "pk, ");
				queryObjBaseTally += ("(case when (arap_tally.customer = '~' and arap_tally.supplier = '~') then '" + IPubReportConstants.NVLOBJ + "' when arap_tally.customer = '~' then arap_tally.supplier else arap_tally.customer end) qryobj" + i + "pk, ");
				groupByBaseTally += "(case when arap_tally.customer = '~' then arap_tally.supplier else arap_tally.customer end), ";
			} else {
//				查询对象值为空时，pk固定设为nvlobj
//				queryObjBaseBal += (qryObj.getBalFieldName() + " qryobj" + i + "pk, ");
				queryObjBaseBal += ("(case when isnull(" + qryObj.getBalFieldName() + ", '~') = '~' then '" + IPubReportConstants.NVLOBJ + "' else " + qryObj.getBalFieldName() + " end) as qryobj" + i + "pk, ");
				groupByBaseBal += (qryObj.getBalFieldName() + ", ");
//				queryObjBaseTally += (qryObj.getTallyFieldName() + " qryobj" + i + "pk, ");
				queryObjBaseTally += ("(case when isnull(" + qryObj.getTallyFieldName() + ", '~') = '~' then '" + IPubReportConstants.NVLOBJ + "' else " + qryObj.getTallyFieldName() + " end) as qryobj" + i + "pk, ");
				groupByBaseTally += (qryObj.getTallyFieldName() + ", ");
			}
			queryObjBaseExt += (" a.qryobj" + i + "pk, ");
			queryObjOrderExt += (" qryobj" + i + "pk, ");
		}

		queryObjBaseBal = queryObjBaseBal.substring(0, queryObjBaseBal.length() - 2);
		groupByBaseBal = groupByBaseBal.substring(0, groupByBaseBal.length() - 2);
		queryObjBaseTally = queryObjBaseTally.substring(0, queryObjBaseTally.length() - 2); 
		groupByBaseTally = groupByBaseTally.substring(0, groupByBaseTally.length() - 2);
		queryObjBaseExt = queryObjBaseExt.substring(0, queryObjBaseExt.length() - 2);
		queryObjOrderExt = queryObjOrderExt.substring(0, queryObjOrderExt.length() - 2);

		// 是否外币金额式查询
		beForeignCurrency = ACCOUNT_FORMAT_FOREIGN.equals(((ReportInitializeVO) queryVO
				.getRepInitContext().getParentVO()).getReportformat());

		// 处理收付方向的符号
		sign = ReportSqlUtils.getRecPayDirectSign(((ReportInitializeVO) queryVO.getRepInitContext()
				.getParentVO()).getRec_pay_direct());
	}

	public abstract String[] getArrangeSqls() throws SQLException, BusinessException;

	public abstract String getResultSql() throws SQLException, BusinessException;
	
	//add for 新加坡BDA 20161111
	public abstract String getResultDescSql() throws SQLException, BusinessException;
	//add end

	public abstract String[] getDropTableSqls() throws SQLException, BusinessException;

	protected abstract List<ComputeTotal> getAllQryObj();

	protected String getFromDummyTable() {
		if (StringUtils.isEmpty(fromDummyTable)) {
			if (SqlBuilder.getDatabaseType() == DBConsts.SQLSERVER) {
				fromDummyTable = " ";
			} else if (SqlBuilder.getDatabaseType() == DBConsts.ORACLE) {
				fromDummyTable = " from dual ";
			} else if (SqlBuilder.getDatabaseType() == DBConsts.DB2) {
				fromDummyTable = " from sysibm.sysdummy1 ";
			}
		}

		return fromDummyTable;
	}

	protected String getFromResultTable(String tmptable, List<QryObj> qryObjList, String[] qryObjs) {
		StringBuffer fromSqlBuffer = new StringBuffer();
		fromSqlBuffer.append(" from ").append(tmptable).append(" v ");
		fromSqlBuffer.append(" left outer join org_orgs on v.pk_org = org_orgs.pk_org ");
		for (int i = 0; i < qryObjList.size(); i++) {
			if (MDID_ACCOUNT.equals(qryObjList.get(i).getPk_bdinfo())) {
				// 会计科目
				String accAsoa = AccAsoaVO.getDefaultTableName();
				String bd2 = qryObjList.get(i).getBd_table();
				String bdNameFld = "dispname";
				fromSqlBuffer.append(" left outer join (select ").append(AccAsoaVO.PK_ACCASOA)
						.append(", ").append(qryObjList.get(i).getBd_codeField());
				fromSqlBuffer.append(", ").append(accAsoa + "." + bdNameFld +" as name");
				for (int k = 2; k <= 6; k++) {
					fromSqlBuffer.append(", " + accAsoa + "." + bdNameFld + k +" as name"+k);
				}
				fromSqlBuffer.append(" from ").append(bd2).append(" inner join ").append(accAsoa)
						.append(" on ").append(bd2).append(".").append(
								qryObjList.get(i).getBd_pkField()).append(" = ").append(accAsoa)
						.append(".").append(AccAsoaVO.PK_ACCOUNT).append(") ")
						.append(bdTable + i).append(" on v.").append(qryObjs[i]).append(" = ")
						.append(bdTable + i).append(".").append(AccAsoaVO.PK_ACCASOA);
			} else {
				fromSqlBuffer.append(" left outer join ").append(qryObjList.get(i).getBd_table())
						.append(" ").append(bdTable + i).append(" on v.").append(qryObjs[i])
						.append(" = ").append(bdTable + i).append(".").append(qryObjList.get(i).getBd_pkField());
			}
		}

		return fromSqlBuffer.toString();
	}

	protected String getMultiLangIndex() {
		int intIndex = MultiLangContext.getInstance().getCurrentLangSeq().intValue();
		return intIndex == 1 ? "" : String.valueOf(intIndex);
	}

	protected String getJoinTableSql() throws BusinessException {
		if (beFirstJoin) {
			StringBuffer sqlBuffer = new StringBuffer();

			// 处理到期日
			String joinTable = ReportTableEnum.ARAP_TALLYAGR.getCode();
			if (IArapReportConstants.ACC_ANA_DATE_EXPIREDATE.equals(queryVO.getAnaDate()) // 分析日期：到期日
					|| IArapReportConstants.ACC_ANA_DATE_WITHINDATE.equals(queryVO.getAnaDate())) { // 分析日期：内控到期日期
					// && (IArapReportConstants.ACC_ANA_PATTERN_POINT.equals(queryVO.getAnaPattern()) // 分析方式：点余额(适用于应收、应付账龄分析的点余额，最终余额不需要从协议表取得)
					// || IArapReportConstants.ACC_ANA_TYP_SETTLE.equals(queryVO.getAnaType()) // 分析类型：结算日期(适用于收款、付款账龄)
					// || IArapReportConstants.ACC_ANA_TYP_DEADLINE.equals(queryVO.getAnaType())) // 分析类型：截止日期(适用于收款、付款账龄)
				sqlBuffer.append(" inner join ").append(joinTable);
				sqlBuffer.append(" on arap_tally.pk_tally =  ").append(joinTable).append(".pk_tally ");
			}

			// 处理查询模板SQL
			String whereSql = queryVO.getWhereSql();
			joinTable = ReportTableEnum.ARAP_TALLYALL.getCode();
//			需要关联arap_tally_all表的三种可能
			if ((!StringUtils.isEmpty(whereSql) && whereSql.indexOf(joinTable) >= 0)  //where条件中涉及到了arap_tally_all表的字段
					|| !getBeThroughBal()	//查询条件中的查询对象有至少一个没有创建余额表
					|| UFBoolean.TRUE.equals(queryVO.getUserObject().get(IArapReportConstants.IS_MATCUSTCODE))) {	//勾选了是否显示客户物料码
				sqlBuffer.append(" left outer join ").append(joinTable);
				sqlBuffer.append(" on arap_tally.pk_bill = ").append(joinTable).append(".pk_bill ");
				sqlBuffer.append(" and arap_tally.pk_item = ").append(joinTable).append(".pk_item ");
			}
			joinTableSql = sqlBuffer.toString();
			beFirstJoin = false;
		}
		
		return joinTableSql;
	}

	/**
	 * 检测是否需要关联arap_tally_all表，如有需要，自动关联
	 * 
	 * @return
	 * @throws BusinessException
	 */
	protected String getJoinTableSql2() throws BusinessException {
		String allTable = ReportTableEnum.ARAP_TALLYALL.getCode();
		boolean mustAllTable = false;
		for (QryObj qryObj : queryVO.getQryObjs()) {
			if (qryObj.getTallyFieldName().indexOf(allTable) >= 0) {
				mustAllTable = true;
				break;
			}
		}

		String joinSql = this.getJoinTableSql();
		if (!mustAllTable) {
			return joinSql;
		}

		StringBuffer joinSqlBuffer = new StringBuffer(joinSql);
		if (StringUtils.isEmpty(joinSql) || joinSql.indexOf(allTable) < 0) {
			// 没有关联arap_tally_all表
			joinSqlBuffer.append(" left outer join ").append(allTable);
			joinSqlBuffer.append(" on arap_tally.pk_bill = ").append(allTable).append(".pk_bill ");
			joinSqlBuffer.append(" and arap_tally.pk_item = ").append(allTable).append(".pk_item ");
		}

		return joinSqlBuffer.toString();
	}

	protected String getCompositeWhereSqlForBal() throws BusinessException {
		String compositeWhereSql2 = getCompositeWhereSql();
		
		List<QueryObjVO> qryObjList = NCLocator.getInstance().lookup(IReportQueryObjRegQuery.class).getRegisteredQueryObjWithTrue(MODULE_ARAP);
		for (QueryObjVO vo : qryObjList) {
			if (vo.getIscreatebal().booleanValue()) {
				compositeWhereSql2=compositeWhereSql2.replaceAll(vo.getTallyfieldname(), vo.getBalfieldname());
			}
		}
		return compositeWhereSql2;
	}
	/**
	 * 获取综合where查询条件
	 * <li>处理协议到期日SQL
	 * <li>处理查询模板SQL
	 * <li>处理权限SQL
	 *  
	 * @return String
	 * @throws BusinessException 
	 */
	protected String getCompositeWhereSql() throws BusinessException {
		if (beFirstWhere) {
			StringBuffer sqlBuffer = new StringBuffer();

			// 处理查询模板SQL
			if (!StringUtils.isEmpty(queryVO.getWhereSql())) {
				sqlBuffer.append(" and ").append(queryVO.getWhereSql());
			}

			// 处理查询数据权限
			String powerSql = ReportSqlUtils.getDataPermissionSql(ReportSqlUtils
					.getUserIdForServer(), ReportSqlUtils.getPkGroupForServer(),
					ArapReportDataPowerMap.RESOURCE_IDS, FI_REPORT_REF_POWER,queryVO);

			if (!StringUtils.isEmpty(powerSql)) {
				sqlBuffer.append(powerSql);
			}

			compositeWhereSql = sqlBuffer.toString();
			beFirstWhere = false;
		}

		return compositeWhereSql;
	}

	/**
	 * 公共查询条件(通用条件子句)
	 * 
	 * @return
	 * @throws SQLException
	 * @throws BusinessException
	 */
	protected String getCommonCondSql(boolean beThroughBal) throws SQLException, BusinessException {
		ReportTableEnum table = beThroughBal ? ReportTableEnum.ARAP_BAL : ReportTableEnum.ARAP_TALLY;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(ReportSqlUtils.getCurrencySql(queryVO.getPk_currency(), table)); // 币种
		sqlBuffer.append(ReportSqlUtils.getEstimateBillTypeSql(queryVO.getQryScope(), table)); // 查询范围
		sqlBuffer.append(ReportSqlUtils.getBillStatusSql(queryVO.getBillState(), table)); // 单据状态
		sqlBuffer.append(ReportSqlUtils.getAssoObjSql(queryVO.getAssoObj(), table)); // 往来对象
		sqlBuffer.append(ReportSqlUtils.getOrgSql(queryVO.getPk_orgs(), table)); // 业务单元
		sqlBuffer.append(ReportSqlUtils.getGroupSql(queryVO.getPk_group(), table)); // 集团
		return sqlBuffer.toString();
	}

	protected boolean getBeThroughBalWithPermission() throws BusinessException {
		return getBeThroughBal(true);
	}

	protected boolean getBeThroughBalNoPermission() throws BusinessException {
		return getBeThroughBal(false);
	}
	
	protected boolean getBeThroughBal() throws BusinessException {
		return getBeThroughBal(false);
	}
	
	protected boolean getBeThroughBal(boolean withPermission) throws BusinessException{
		if (beThroughBal == null) {
			beThroughBal = UFBoolean.TRUE;
			if (!StringUtils.isEmpty(getCompositeWhereSql())) {
				beThroughBal = UFBoolean.FALSE;
				
				if(withPermission){
					// 处理查询数据权限
					String powerSql = ReportSqlUtils.getDataPermissionSql(ReportSqlUtils
							.getUserIdForServer(), ReportSqlUtils.getPkGroupForServer(),
							ArapReportDataPowerMap.RESOURCE_IDS, FI_REPORT_REF_POWER,queryVO);
					
					if(powerSql==null || powerSql.length()==0){
					}else{
						List<QueryObjVO> qryObjList = NCLocator.getInstance().lookup(IReportQueryObjRegQuery.class).getRegisteredQueryObjWithTrue(MODULE_ARAP);
						List<String> needs=new ArrayList<String>();
						for (QueryObjVO vo : qryObjList) {
							if (vo.getIscreatebal().booleanValue()) {
								needs.add(vo.getTallyfieldname());
							}
						}
						String[] split = powerSql.split(" ");
						for(String tab:split){
							if(tab.contains(".")){
								if(needs.contains(tab)){
									beThroughBal = UFBoolean.TRUE;
								}else{
									beThroughBal= UFBoolean.FALSE;
									break;
								}
							}
						}
					}
				}
			} else {
				List<QueryObjVO> qryObjList = NCLocator.getInstance().lookup(
						IReportQueryObjRegQuery.class).getRegisteredQueryObjWithTrue(MODULE_ARAP);

				Map<String, QueryObjVO> crtBalMap = new HashMap<String, QueryObjVO>();
				for (QueryObjVO vo : qryObjList) {
					if (vo.getIscreatebal().booleanValue()) {
						crtBalMap.put(vo.getBd_mdid() + vo.getTallyfieldname(), vo);
					}
				}

				for (QryObj qryObj : queryVO.getQryObjs()) {
					if (IBDMetaDataIDConst.CUSTSUPPLIER.equals(qryObj.getPk_bdinfo())) {
						beThroughBal = UFBoolean.FALSE;
						break;
					}
					if (crtBalMap.get(qryObj.getPk_bdinfo() + qryObj.getTallyFieldName()) == null) {
						beThroughBal = UFBoolean.FALSE;
						break;
					}
				}
			}
		}

		return beThroughBal.booleanValue();
	}
	protected String[] getQueryObjs() {
		String[] qryObjs = queryObjOrderExt.split(",");
		for (int i = 0; i < qryObjs.length; i++) {
			qryObjs[i] = qryObjs[i].trim();
		}

		return qryObjs;
	}

	public static class ComputeTotal {
		public String field = null;
		public boolean isDimension = false;
	}

}

// /:~
