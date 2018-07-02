package nc.impl.arap.other;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import nc.bs.arap.accountage.AccountAgeAnalyzerFactory;
import nc.bs.arap.accountage.IAccountAgeAna;
import nc.bs.arap.sql.ArapBaseSqlCreator;
import nc.bs.arap.sql.RecPayStatementSQLCreator;
import nc.bs.framework.common.NCLocator;
import nc.itf.arap.other.IRecPayStatementBO;
import nc.itf.fipub.queryobjreg.IReportQueryObjRegQuery;
import nc.itf.fipub.report.IPubReportConstants;
import nc.md.data.access.NCObject;
import nc.md.persist.framework.MDPersistenceService;
import nc.pub.smart.context.SmartContext;
import nc.pub.smart.exception.SmartException;
import nc.pubitf.org.IDeptPubService;
import nc.pubitf.uapbd.ICustomerPubService;
import nc.pubitf.uapbd.IPsndocPubService;
import nc.pubitf.uapbd.ISupplierPubService;
import nc.utils.arap.ArapReportResource;
import nc.utils.fipub.FipubSqlExecuter;
import nc.vo.arap.annotation.AccountQueryBiz;
import nc.vo.arap.annotation.ArapBusinessDef;
import nc.vo.arap.comreport.ArapStatementCalculator;
import nc.vo.arap.comreport.IArapReportConstants;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.arap.tally.AggRecStatementVO;
import nc.vo.arap.tally.RecStatementItemVO;
import nc.vo.arap.tally.RecStatementVO;
import nc.vo.arap.utils.ArapReportQryObjUtil;
import nc.vo.bd.cust.CustSupplierVO;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.psn.PsndocVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.fipub.annotation.Business;
import nc.vo.fipub.annotation.BusinessType;
import nc.vo.fipub.report.PubCommonReportMethod;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.QueryObjVO;
import nc.vo.fipub.report.ReportInitializeVO;
import nc.vo.fipub.report.ReportQueryCondVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.rs.MemoryResultSet;
import nc.vo.pub.rs.MemoryResultSetMetaData;

import org.apache.commons.lang.StringUtils;

public class RecPayStatementBOImpl extends FipubSqlExecuter implements IRecPayStatementBO {
	@Business(business = ArapBusinessDef.AccountQuery,subBusiness=AccountQueryBiz.StatementReport,description = "对账单查询（收/付）",type = BusinessType.NORMAL)	/*-=notranslate=-*/
	public List<AggRecStatementVO> query(ReportQueryCondVO qryCondVO, SmartContext context) throws BusinessException {
		try {
			ArapBaseSqlCreator sqlCreator = new RecPayStatementSQLCreator();
			sqlCreator.setParams(qryCondVO);
			reSetDept(qryCondVO);
			String[] arrangeSqls = sqlCreator.getArrangeSqls();
			String resultSql = sqlCreator.getResultSql();
			String[] dropTableSqls = sqlCreator.getDropTableSqls();

			// 构建临时表数据
			for (String sql : arrangeSqls) {
				executeUpdate(sql);
			}

			// 从临时表取得结果
			MemoryResultSet resultSet = (MemoryResultSet) executeQuery(resultSql, getResultProcessor());
			
			//add for 新加坡BDA 20161111
			//swb add
			String resultDescSql = sqlCreator.getResultDescSql();
			MemoryResultSet resultDescSet = (MemoryResultSet) executeQuery(resultDescSql, getResultProcessor());
			ArrayList<List<Object>> retList = resultDescSet.getResultArrayList();
			Map<String,Map<String,String>> showBillsMap = new HashMap<String,Map<String,String>>();
			for (List<Object> list : retList) {
				String key = String.valueOf(list.get(0));
				if(showBillsMap.get(key) == null) {
					Map<String,String> valMap = new HashMap<String,String>();
					String billno = "";
					String invoiceno = "";
					if(list.get(1)!=null && !StringUtils.isEmpty(String.valueOf(list.get(1)))){
						billno = " "+String.valueOf(list.get(1))+",";
					}
					valMap.put("billno", billno);
					if(list.get(2)!=null && !StringUtils.isEmpty(String.valueOf(list.get(2)))){
						invoiceno = " "+String.valueOf(list.get(2))+",";
					}
					valMap.put("invoiceno", invoiceno);
					showBillsMap.put(key, valMap);
				}else{
					Map<String,String> valMap = showBillsMap.get(key);
					String billno = valMap.get("billno");
					String invoiceno = valMap.get("invoiceno");
					if(list.get(1)!=null && billno.indexOf(" "+String.valueOf(list.get(1))+",") == -1){
						billno +=" "+String.valueOf(list.get(1))+",";
						valMap.put("billno", billno);
					}
					if(list.get(2)!=null && invoiceno.indexOf(" "+String.valueOf(list.get(2))+",") == -1){
						invoiceno +=" "+String.valueOf(list.get(2))+",";
						valMap.put("invoiceno", invoiceno);
					}
					showBillsMap.put(key, valMap);
				}
			}
			//add end
			
			// 删除临时表
			for (String sql : dropTableSqls) {
				executeUpdate(sql);
			}

			String pk_qryObj = null;
			if (BillEnumCollection.ObjType.CUSTOMER.VALUE.toString().equals(qryCondVO.getAssoObj())) {
				// 客户
				pk_qryObj = RecStatementItemVO.PK_CUSTOMER;
			} else if (BillEnumCollection.ObjType.DEP.VALUE.toString().equals(qryCondVO.getAssoObj())) {
				// 部门
				pk_qryObj = RecStatementItemVO.PK_DEPTID;
			} else if (BillEnumCollection.ObjType.PERSON.VALUE.toString().equals(qryCondVO.getAssoObj())) {
				// 业务员
				pk_qryObj = RecStatementItemVO.PK_PSNDOC;
			} else if (BillEnumCollection.ObjType.SUPPLIER.VALUE.toString().equals(qryCondVO.getAssoObj())) {
				// 供应商
				pk_qryObj = RecStatementItemVO.PK_SUPPLIER;
			} else if (BillEnumCollection.ObjType.CUSSUP.VALUE.toString().equals(qryCondVO.getAssoObj())) {
				// 客商
				if (IArapReportConstants.SYS_FLAG_REC == qryCondVO.getSyscode()) {
					pk_qryObj = RecStatementItemVO.PK_CUSTOMER;
				} else if (IArapReportConstants.SYS_FLAG_PAY == qryCondVO.getSyscode()) {
					pk_qryObj = RecStatementItemVO.PK_SUPPLIER;
				}
			}
			
			//add for 新加坡BDA 20161111
			Object ageingSchema = qryCondVO.getUserObject().get(IArapReportConstants.AGING_REF);
			Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
			if(ageingSchema != null){
				// 获取账龄分析器,并获取应收应付的账龄信息
				ReportQueryCondVO qryCondVOClone = (ReportQueryCondVO)qryCondVO.clone();
				ReportInitializeVO reportInitializeVO = (ReportInitializeVO)qryCondVOClone.getRepInitContext().getParentVO();
				if(reportInitializeVO == null)
					reportInitializeVO = new ReportInitializeVO();
				reportInitializeVO.setReportformat("foreign");
				IAccountAgeAna analyzer = null;
				if (IArapReportConstants.SYS_FLAG_REC == qryCondVO.getSyscode()) {
					analyzer = AccountAgeAnalyzerFactory.getAccountAgeAnalyzer(IPubReportConstants.YSZLFX_REPORT);
					reportInitializeVO.setRec_pay_direct("rec");//收
				} else if (IArapReportConstants.SYS_FLAG_PAY == qryCondVO.getSyscode()) {
					analyzer = AccountAgeAnalyzerFactory.getAccountAgeAnalyzer(IPubReportConstants.YFZLFX_REPORT);
					reportInitializeVO.setRec_pay_direct("pay");//付
				}
				qryCondVOClone.getRepInitContext().setParentVO(reportInitializeVO);
				
				//查询对象只考虑客户
				List<QueryObjVO> queryObjVOs = NCLocator.getInstance().lookup(
						IReportQueryObjRegQuery.class).getRegisteredQueryObj(
						IPubReportConstants.MODULE_ARAP);
				List<QryObj> listQryObj = new ArrayList<QryObj>();
				QryObj qryObj = null;
				for(QueryObjVO queryObjVO : queryObjVOs){
					if (IArapReportConstants.SYS_FLAG_REC == qryCondVO.getSyscode()) {
						if("客户".equals(queryObjVO.getDsp_objname())){
							qryObj = ArapReportQryObjUtil.convertQueryObjVO2QryObj(queryObjVO);
							listQryObj.add(qryObj);
							break;
						}
					} else if (IArapReportConstants.SYS_FLAG_PAY == qryCondVO.getSyscode()) {
						if("供应商".equals(queryObjVO.getDsp_objname())){
							qryObj = ArapReportQryObjUtil.convertQueryObjVO2QryObj(queryObjVO);
							listQryObj.add(qryObj);
						}
					}
				}
				
				qryCondVOClone.setQryObjs(listQryObj);
				qryCondVOClone.setAnaMode(IArapReportConstants.ACC_ANA_MODE_AGE);//分析模式：按账龄 IArapReportConstants.ACC_ANA_MODE_AGE
				qryCondVOClone.setAnaDate(IArapReportConstants.ACC_ANA_DATE_EXPIREDATE);//写死，分析日期按到期日期
				//qryCondVOClone.setAnaPattern(IArapReportConstants.ACC_ANA_PATTERN_POINT);//点余额，不能直接写死
				qryCondVOClone.setAccAgePlan(qryCondVOClone.getUserObject().get(IArapReportConstants.AGING_REF)+"");//账龄方案
				MemoryResultSet resultSetTemp = analyzer.getAccountAgeAnaResult(qryCondVOClone, context);
				ArrayList<List<Object>> retList1 = resultSetTemp.getResultArrayList();
				String key = null;
				List<?> oneLine = null;
				List<String> keyList = new ArrayList<String>();
				if (retList1 != null && retList1.size() > 0) {
					for (int i = 0; i < retList1.size(); i++) {
						oneLine = (List<?>) retList1.get(i);
						key = oneLine.get(0)+"#"+oneLine.get(1)+"#"+oneLine.get(5)+"#"+oneLine.get(9)+"#"+oneLine.get(16)+"#"+oneLine.get(18);
						if (resultMap.get(key) == null) {
							keyList.add(key);
							resultMap.put(key, new ArrayList<Object>());
						}
						resultMap.get(key).add(oneLine);
					}
				}
			}
			//add end
			
			List<AggRecStatementVO> arrangeResult = arrangeResult(resultSet, qryCondVO, pk_qryObj);
			for(int i = 0 ; i < arrangeResult.size(); i ++){
				AggRecStatementVO  VO = arrangeResult.get(i);
				RecStatementVO headVO = (RecStatementVO) VO.getParentVO();
				headVO.setPk_recstatement("h" + i);
				
				//add for 新加坡BDA 20161111
				//将"仅显示未核销"设置到表头，供打印模板使用 add by xudw  chenth
				Object objTemp = qryCondVO.getUserObject().get(IArapReportConstants.IS_CANAFTERCODE);
				String strTemp = String.valueOf(objTemp);
				UFBoolean iscancelaftercode = UFBoolean.valueOf(strTemp);
				headVO.setIscancelaftercode(iscancelaftercode);
				
				//将"截至日期"设置到表头，供打印模板使用 add by xudw
				headVO.setDeadline(qryCondVO.getDateline().toStdString());
				
				//将账龄信息设置到表头
				if(resultMap != null && !resultMap.isEmpty()){
					String pk_group = headVO.getPk_group();
					String pk_org = headVO.getPk_org();
					String pk_qryobj = headVO.getPk_qryobj();
					String pk_currtype = headVO.getPk_currtype();
					for(int k=0;k<8;k++){
						String keyTemp = pk_group+"#"+pk_org+"#"+pk_qryobj+"#"+pk_currtype+"#"+k+"#0";
						List<Object> oneLineTemp = resultMap.get(keyTemp);
						if(oneLineTemp != null && oneLineTemp.size() > 0){
							List ListObj = (List)oneLineTemp.get(0);
							String str = String.valueOf(ListObj.get(12));
							if(k==0){
								headVO.setPeriod1(new UFDouble(str,2));
							}else if(k==1){
								headVO.setPeriod2(new UFDouble(str,2));
							}else if(k==2){
								headVO.setPeriod3(new UFDouble(str,2));
							}else if(k==3){
								headVO.setPeriod4(new UFDouble(str,2));
							}else if(k==4){
								headVO.setPeriod5(new UFDouble(str,2));
							}else if(k==5){
								headVO.setPeriod6(new UFDouble(str,2));
							}else if(k==6){
								headVO.setPeriod7(new UFDouble(str,2));
							}else if(k==7){
								headVO.setPeriod8(new UFDouble(str,2));
							}
						}
					}
				}
				//add end
				
				RecStatementItemVO[] childrenVO = (RecStatementItemVO[])VO.getChildrenVO();
				for(int j = 0 ; j < childrenVO.length; j++){
					childrenVO[j].setPk_recstatement("h" + i);
					childrenVO[j].setPk_recstatementitem("b" + i + j);
					//add for 新加坡BDA 20161111
					//swb add
					if(showBillsMap.get(childrenVO[j].getBillno())!=null){
						Map<String,String> valMap = showBillsMap.get(childrenVO[j].getBillno());
						childrenVO[j].setInvoiceno(valMap.get("invoiceno").replaceAll(" ",""));
						childrenVO[j].setBillno2(valMap.get("billno").replaceAll(" ",""));
					}else{
						childrenVO[j].setBillno2("");
					}
					//add end
				}
				
				// Modified on 2018-01-29
				// 根据核销类型移除
				
				
			}
			return arrangeResult;
		} catch (SQLException e) {
			SmartException exp = handleExecuteException(e);
			throw new BusinessException(exp.getMessage(), exp);
		}
	}

	@SuppressWarnings("unchecked")
	private List<AggRecStatementVO> arrangeResult(MemoryResultSet mrs, ReportQueryCondVO qryCondVO,
			String pk_qryObj) throws SQLException, BusinessException {

		ArrayList<List<Object>> retList = mrs.getResultArrayList();

		if (retList == null || retList.size() == 0) {
			return new ArrayList<AggRecStatementVO>();
		}

		if (IPubReportConstants.GROUP_LOCAL_CURRENCY.equals(qryCondVO.getLocalCurrencyType())
				|| IPubReportConstants.GLOBLE_LOCAL_CURRENCY.equals(qryCondVO.getLocalCurrencyType())) {
			String[] targetFields = new String[] { "debt_loc", "credit_loc", "bal_loc" };
			String[] formulas = null;
			if (IPubReportConstants.GROUP_LOCAL_CURRENCY.equals(qryCondVO.getLocalCurrencyType())) {
				// 集团本币
				formulas = new String[] { "debt_loc->gr_debt_loc", "credit_loc->gr_credit_loc", "bal_loc->gr_bal_loc" };
			} else if (IPubReportConstants.GLOBLE_LOCAL_CURRENCY.equals(qryCondVO.getLocalCurrencyType())) {
				// 全局本币
				formulas = new String[] { "debt_loc->gl_debt_loc", "credit_loc->gl_credit_loc", "bal_loc->gl_bal_loc" };
			}
			mrs.setColumnByFormulate_type(targetFields, formulas);
		}

		String multiLangIndex = PubCommonReportMethod.getMultiLangIndex();
		String[] fields = new String[] { RecStatementItemVO.BILLTYPE };
		BilltypeVO billtypeVO = new BilltypeVO();
		String[] formulas = new String[] { RecStatementItemVO.BILLTYPE + "->iif(getColValue(" + billtypeVO.getTableName()
				+ ", billtypename" + multiLangIndex + ", pk_billtypecode, " + RecStatementItemVO.PK_BILLTYPE
				+ ")==null, " + "getColValue(" + billtypeVO.getTableName()
				+ ", billtypename, pk_billtypecode, " + RecStatementItemVO.PK_BILLTYPE + "), " + "getColValue("
				+ billtypeVO.getTableName() + ", billtypename" + multiLangIndex + ", pk_billtypecode, "
				+ RecStatementItemVO.PK_BILLTYPE + "))" };
		mrs.setColumnByFormulate_type(fields, formulas);

		MemoryResultSetMetaData metaData = mrs.getMetaData0();

		String[] dimenName = { 
				RecStatementItemVO.PK_GROUP, RecStatementItemVO.PK_ORG,
				RecStatementItemVO.PK_CUSTOMER, RecStatementItemVO.PK_SUPPLIER,
				RecStatementItemVO.PK_DEPTID, RecStatementItemVO.PK_PSNDOC,
				RecStatementItemVO.PK_CURRTYPE}; // 维度字段

		String[] otherName = { 
				RecStatementItemVO.PK_GROUP, RecStatementItemVO.PK_ORG,
				RecStatementItemVO.PK_CUSTOMER, RecStatementItemVO.PK_SUPPLIER,
				RecStatementItemVO.PK_DEPTID,RecStatementItemVO.PK_PSNDOC, 
				RecStatementItemVO.PK_CURRTYPE, RecStatementItemVO.MATCUSTCODE,
				RecStatementItemVO.BRIEF,RecStatementItemVO.BILLTYPE, 
				RecStatementItemVO.PK_TRADETYPEID, RecStatementItemVO.PK_BILL,
				//update for 新加坡BDA 20161111
				RecStatementItemVO.BILLNO,RecStatementItemVO.BILLNO2,RecStatementItemVO.BUSIDATE,RecStatementItemVO.ISCOUNTERFEE,RecStatementItemVO.TALLYDATE, 
				RecStatementItemVO.INVOICENO, RecStatementItemVO.DEALFLAG,
				RecStatementItemVO.PK_DEALNUM, 
				RecStatementItemVO.MARKPERIOD, RecStatementItemVO.DEBT_QUA,
				RecStatementItemVO.DEBT_ORI, RecStatementItemVO.DEBT_LOC,
				RecStatementItemVO.GR_DEBT_LOC, RecStatementItemVO.GL_DEBT_LOC,
				RecStatementItemVO.CREDIT_QUA, RecStatementItemVO.CREDIT_ORI,
				RecStatementItemVO.CREDIT_LOC,RecStatementItemVO.GR_CREDIT_LOC, 
				RecStatementItemVO.GL_CREDIT_LOC, RecStatementItemVO.PK_BILLTYPE, 
				RecStatementItemVO.PROJECT}; // 全字段
		int[] dimenIndex = new int[dimenName.length];
		int[] otherIndex = new int[otherName.length];
		for (int i = 0; i < dimenName.length; i++) {
			dimenIndex[i] = metaData.getNameIndex(dimenName[i]);
		}

		for (int i = 0; i < otherName.length; i++) {
			otherIndex[i] = metaData.getNameIndex(otherName[i]);
		}

		String key = null;
		List<?> oneLine = null;
		List<String> keyList = new ArrayList<String>();
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();

		// 按查询对象分组
		for (int i = 0; i < retList.size(); i++) {
			oneLine = (List<?>) retList.get(i);
			key = getKey(oneLine, dimenIndex);
			if (resultMap.get(key) == null) {
				keyList.add(key);
				resultMap.put(key, new ArrayList<Object>());
			}
			resultMap.get(key).add(oneLine);
		}

		List<String> qryObjKeyList = new ArrayList<String>(); // 批量查询“查询对象的值”
		Map<String, AggRecStatementVO> resultVOMap = new HashMap<String, AggRecStatementVO>();

		Object value = null;
		List<Object> oneObj = null;
		AggRecStatementVO aggVO = null;
		RecStatementVO headVO = null;
		RecStatementItemVO bodyVO = null;
		List<RecStatementItemVO> bodyVOs = null;
		
		//add for 新加坡BDA 20161111
		//期末余额为空且本期无发生是否显示 add by xudw
		Object obj = qryCondVO.getUserObject().get(IArapReportConstants.IS_FINAL_BALANCE);
		String str = String.valueOf(obj);
		UFBoolean isFinalBalance = UFBoolean.valueOf(str);
		
		//手续费收付款是否显示 add by xudw
		obj = qryCondVO.getUserObject().get(IArapReportConstants.IS_COUNTER_FEE);
		str = String.valueOf(obj);
		UFBoolean isCounterFee = UFBoolean.valueOf(str);
		//add end
		
		// 开始构造结果聚合VO
		int direct = qryCondVO.getSyscode() == IArapReportConstants.SYS_FLAG_REC ? 1 : -1;
		for (int i = 0; i < keyList.size(); i++) {
			oneObj = resultMap.get(keyList.get(i));
			aggVO = new AggRecStatementVO();

			bodyVOs = new ArrayList<RecStatementItemVO>();
			for (Object bodyLine : oneObj) {
				bodyVO = new RecStatementItemVO();
				// 循环设置各个属性值
				for (int j = 0; j < otherName.length; j++) {
					value = ((List<?>) bodyLine).get(otherIndex[j]);
					if (value == null || (value instanceof String && StringUtils.isEmpty(value.toString()))) {
						bodyVO.setAttributeValue(otherName[j], null);
					} else if (RecStatementItemVO.TALLYDATE.equalsIgnoreCase(otherName[j])) {
						//update for 新加坡BDA 20161111
						// 处理多时区问题
						String a = new UFDateTime((String) value).getDate().toStdString((TimeZone) qryCondVO.getUserObject().get("statementzone"));
						bodyVO.setAttributeValue(otherName[j], a);
					} else if (RecStatementItemVO.BUSIDATE.equalsIgnoreCase(otherName[j])) {
						// 处理多时区问题
						String a = new UFDateTime((String) value).getDate().toStdString((TimeZone) qryCondVO.getUserObject().get("statementzone"));
						bodyVO.setAttributeValue(otherName[j], a);
						//update for 新加坡BDA 20161111
					} else {
						bodyVO.setAttributeValue(otherName[j], value);
					}
				}

				if (!(UFDouble.ZERO_DBL.equals(bodyVO.getDebt_qua())
						&& UFDouble.ZERO_DBL.equals(bodyVO.getCredit_qua())
						&& UFDouble.ZERO_DBL.equals(bodyVO.getDebt_ori())
						&& UFDouble.ZERO_DBL.equals(bodyVO.getCredit_ori())
						&& UFDouble.ZERO_DBL.equals(bodyVO.getDebt_loc())
						&& UFDouble.ZERO_DBL.equals(bodyVO.getCredit_loc()))) {
					bodyVOs.add(bodyVO);
				}
			}

			if (bodyVOs.size() == 0) {
				continue;
			}

			RecStatementItemVO totalVO = new RecStatementItemVO(); // 合计行
			totalVO.setBrief(ArapReportResource.getAllTotalLbl()); // 总计
			totalVO.setDebt_qua(UFDouble.ZERO_DBL);
			totalVO.setDebt_ori(UFDouble.ZERO_DBL);
			totalVO.setDebt_loc(UFDouble.ZERO_DBL);
			totalVO.setGr_debt_loc(UFDouble.ZERO_DBL);
			totalVO.setGl_debt_loc(UFDouble.ZERO_DBL);
			totalVO.setCredit_qua(UFDouble.ZERO_DBL);
			totalVO.setCredit_ori(UFDouble.ZERO_DBL);
			totalVO.setCredit_loc(UFDouble.ZERO_DBL);
			totalVO.setGr_credit_loc(UFDouble.ZERO_DBL);
			totalVO.setGl_credit_loc(UFDouble.ZERO_DBL);

			int k = 0;
			RecStatementItemVO[] arrBodyVO = bodyVOs.toArray(new RecStatementItemVO[0]);
			bodyVOs = new ArrayList<RecStatementItemVO>();
			// see nc.bs.arap.sql.RecPayStatementSQLCreator
			// 期初：markperiod = 1、本期：markperiod = 2
			if (!(ArapReportResource.getPeriodBeginLbl().equals(arrBodyVO[0].getBrief()) || arrBodyVO[0]
					.getMarkperiod() == 1)) {
				// 不存在期初，显示构造一条期初记录
				bodyVO = new RecStatementItemVO();
				bodyVO.setPk_group(arrBodyVO[0].getPk_group());
				bodyVO.setPk_org(arrBodyVO[0].getPk_org());
				bodyVO.setPk_customer(arrBodyVO[0].getPk_customer());
				bodyVO.setPk_deptid(arrBodyVO[0].getPk_deptid());
				bodyVO.setPk_psndoc(arrBodyVO[0].getPk_psndoc());
				bodyVO.setPk_supplier(arrBodyVO[0].getPk_supplier());
				bodyVO.setPk_currtype(arrBodyVO[0].getPk_currtype());
				bodyVO.setBrief(ArapReportResource.getPeriodBeginLbl());
				bodyVO.setBal_qua(UFDouble.ZERO_DBL);
				bodyVO.setBal_ori(UFDouble.ZERO_DBL);
				bodyVO.setBal_loc(UFDouble.ZERO_DBL);
				bodyVO.setGr_bal_loc(UFDouble.ZERO_DBL);
				bodyVO.setGl_bal_loc(UFDouble.ZERO_DBL);

				bodyVOs.add(bodyVO);
			} else {
				// 已经存在期初记录
				k++;
				bodyVO = arrBodyVO[0];

				// 设置期末余额，并清除本期数据
				bodyVO.setBal_qua((bodyVO.getDebt_qua().sub(bodyVO.getCredit_qua())).multiply(direct));
				bodyVO.setDebt_qua(UFDouble.ZERO_DBL);
				bodyVO.setCredit_qua(UFDouble.ZERO_DBL);

				bodyVO.setBal_ori(bodyVO.getDebt_ori().sub(bodyVO.getCredit_ori()).multiply(direct));
				bodyVO.setDebt_ori(UFDouble.ZERO_DBL);
				bodyVO.setCredit_ori(UFDouble.ZERO_DBL);

				bodyVO.setBal_loc(bodyVO.getDebt_loc().sub(bodyVO.getCredit_loc()).multiply(direct));
				bodyVO.setDebt_loc(UFDouble.ZERO_DBL);
				bodyVO.setCredit_loc(UFDouble.ZERO_DBL);

				bodyVO.setGr_bal_loc(bodyVO.getGr_debt_loc().sub(bodyVO.getGr_credit_loc()).multiply(direct));
				bodyVO.setGr_debt_loc(UFDouble.ZERO_DBL);
				bodyVO.setGr_credit_loc(UFDouble.ZERO_DBL);

				bodyVO.setGl_bal_loc(bodyVO.getGl_debt_loc().sub(bodyVO.getGl_credit_loc()).multiply(direct));
				bodyVO.setGl_debt_loc(UFDouble.ZERO_DBL);
				bodyVO.setGl_credit_loc(UFDouble.ZERO_DBL);

				bodyVOs.add(bodyVO);
			}

			for (; k < arrBodyVO.length; k++) {
				// 计算每行期末余额
				arrBodyVO[k].setBal_qua(bodyVO.getBal_qua().add(arrBodyVO[k].getDebt_qua().sub(arrBodyVO[k].getCredit_qua()).multiply(direct)));
				arrBodyVO[k].setBal_ori(bodyVO.getBal_ori().add(arrBodyVO[k].getDebt_ori().sub(arrBodyVO[k].getCredit_ori()).multiply(direct)));
				arrBodyVO[k].setBal_loc(bodyVO.getBal_loc().add(arrBodyVO[k].getDebt_loc().sub(arrBodyVO[k].getCredit_loc()).multiply(direct)));
				arrBodyVO[k].setGr_bal_loc(bodyVO.getGr_bal_loc().add(arrBodyVO[k].getGr_debt_loc().sub(arrBodyVO[k].getGr_credit_loc()).multiply(direct)));
				arrBodyVO[k].setGl_bal_loc(bodyVO.getGl_bal_loc().add(arrBodyVO[k].getGl_debt_loc().sub(arrBodyVO[k].getGl_credit_loc()).multiply(direct)));
				bodyVO = arrBodyVO[k]; // 记录上一行记录
				
				//update for 新加坡BDA 20161111
				//add by xudw
				if(isCounterFee.equals(UFBoolean.FALSE)){//手续费收付款不显示
					if(bodyVO.getIscounterfee() == null || bodyVO.getIscounterfee().equals(UFBoolean.FALSE)){//不是手续费收付款
						bodyVOs.add(bodyVO);
					}
				}else{//手续费收付款显示
					bodyVOs.add(bodyVO);
				}

				//update for 新加坡BDA 20161111
				// 计算合计 add by xudw
				if(bodyVO.getIscounterfee() == null || bodyVO.getIscounterfee().equals(UFBoolean.FALSE)){//不是手续费收付款
					totalVO.setDebt_qua(totalVO.getDebt_qua().add(bodyVO.getDebt_qua()));
					totalVO.setDebt_ori(totalVO.getDebt_ori().add(bodyVO.getDebt_ori()));
					totalVO.setDebt_loc(totalVO.getDebt_loc().add(bodyVO.getDebt_loc()));
					totalVO.setGr_debt_loc(totalVO.getGr_debt_loc().add(bodyVO.getGr_debt_loc()));
					totalVO.setGl_debt_loc(totalVO.getGl_debt_loc().add(bodyVO.getGl_debt_loc()));
					totalVO.setCredit_qua(totalVO.getCredit_qua().add(bodyVO.getCredit_qua()));
					totalVO.setCredit_ori(totalVO.getCredit_ori().add(bodyVO.getCredit_ori()));
					totalVO.setCredit_loc(totalVO.getCredit_loc().add(bodyVO.getCredit_loc()));
					totalVO.setGr_credit_loc(totalVO.getGr_credit_loc().add(bodyVO.getGr_credit_loc()));
					totalVO.setGl_credit_loc(totalVO.getGl_credit_loc().add(bodyVO.getGl_credit_loc()));
				}
			}

			if (bodyVOs.size() == 1) {
				// 只有一行期初(数据库查询出来的)
				RecStatementItemVO itemVO = bodyVOs.get(0);
				if (UFDouble.ZERO_DBL.equals(itemVO.getBal_loc()) && UFDouble.ZERO_DBL.equals(itemVO.getBal_ori())) {
					continue;
				}
			}

			// 设置表头常规属性
			headVO = new RecStatementVO();
			headVO.setPk_group(((List<?>) oneObj.get(0)).get(metaData.getNameIndex(RecStatementItemVO.PK_GROUP)).toString());
			headVO.setPk_org(((List<?>) oneObj.get(0)).get(metaData.getNameIndex(RecStatementItemVO.PK_ORG)).toString());
			headVO.setPk_qryobj(((List<?>) oneObj.get(0)).get(metaData.getNameIndex(pk_qryObj)).toString());
			// headVO.setQryobj("查询对象");
			headVO.setPk_currtype(((List<?>) oneObj.get(0)).get(metaData.getNameIndex(RecStatementItemVO.PK_CURRTYPE)).toString());

			// 设置表头余额
			headVO.setBal_qua(bodyVO.getBal_qua());
			headVO.setBal_ori(bodyVO.getBal_ori());
			headVO.setBal_loc(bodyVO.getBal_loc());
			headVO.setGr_bal_loc(bodyVO.getGr_bal_loc());
			headVO.setGl_bal_loc(bodyVO.getGl_bal_loc());
			totalVO.setBal_qua(bodyVO.getBal_qua());
			totalVO.setBal_ori(bodyVO.getBal_ori());
			totalVO.setBal_loc(bodyVO.getBal_loc());
			totalVO.setGr_bal_loc(bodyVO.getGr_bal_loc());
			totalVO.setGl_bal_loc(bodyVO.getGl_bal_loc());
			bodyVOs.add(totalVO);

			aggVO.setParentVO(headVO);
			aggVO.setChildrenVO(bodyVOs.toArray(new CircularlyAccessibleValueObject[0]));
			
			//update for 新加坡BDA 20161111
			//edit by xudw
			if(isFinalBalance.equals(UFBoolean.FALSE)){
				if(!UFDouble.ZERO_DBL.equals(bodyVO.getBal_ori())
						|| !UFDouble.ZERO_DBL.equals(bodyVO.getBal_loc())
						|| !UFDouble.ZERO_DBL.equals(bodyVO.getGr_bal_loc())
						|| !UFDouble.ZERO_DBL.equals(bodyVO.getGl_bal_loc())){
					qryObjKeyList.add(headVO.getPk_qryobj());
					resultVOMap.put(keyList.get(i), aggVO);
				}
			}else{
				qryObjKeyList.add(headVO.getPk_qryobj());
				resultVOMap.put(keyList.get(i), aggVO);
			}
			//update end
		}

		List<AggRecStatementVO> resultVOList = new ArrayList<AggRecStatementVO>();
		if (qryObjKeyList.size() > 0) {
			// 批量构造查询对象名称
			SuperVO[] resultVos = getQryObjName(qryCondVO.getAssoObj(), qryObjKeyList);
			Map<String, String> qryObjNameMap = new HashMap<String, String>();
			String qryObjName = null;
			for (SuperVO vo : resultVos) {
				if (vo == null) {
					continue;
				}
				qryObjName = (String) vo.getAttributeValue("name" + multiLangIndex);
				if (StringUtils.isEmpty(qryObjName)) {
					qryObjName = (String) vo.getAttributeValue("name");
				}
				qryObjNameMap.put(vo.getPrimaryKey(), qryObjName);
			}

			AggRecStatementVO aggRecStatementVO = null;
			for (int i = 0; i < keyList.size(); i++) {
				aggRecStatementVO = resultVOMap.get(keyList.get(i));
				if (aggRecStatementVO == null) {
					continue;
				}
				headVO = (RecStatementVO) aggRecStatementVO.getParentVO();
				headVO.setQryobj(qryObjNameMap.get(headVO.getPk_qryobj()));
				// 还原查询对象顺序
				resultVOList.add(aggRecStatementVO);
			}
		}
		
		//update for 新加坡BDA 20161111
		//add kuangxw
		ArapStatementCalculator calculator = new ArapStatementCalculator();
		if(UFBoolean.TRUE == qryCondVO.getUserObject().get(IArapReportConstants.IS_TOTALCODE)){
			return calculator.computeDateSubTotal(resultVOList);
		}
		
		return resultVOList;//calculator.computeDateSubTotal(resultVOList);
		//update end
	}

	private String getKey(List<?> oneLine, int[] indexs) {
		StringBuffer buff = new StringBuffer();
		for (int ind : indexs) {
			buff.append(oneLine.get(ind));
		}
		return buff.toString();
	}

	private SuperVO[] getQryObjName(String assoObj, List<String> keyList) throws BusinessException {
		String[] keyArray = keyList.toArray(new String[0]);

		String multiLangIndex = PubCommonReportMethod.getMultiLangIndex();

		SuperVO[] result = null;
		if (BillEnumCollection.ObjType.CUSTOMER.VALUE.toString().equals(assoObj)) {
			// 客户
			result = NCLocator.getInstance().lookup(ICustomerPubService.class).getCustomerVO(
					keyArray, new String[] { CustomerVO.PK_CUSTOMER, CustomerVO.NAME, CustomerVO.NAME + multiLangIndex });
		} else if (BillEnumCollection.ObjType.DEP.VALUE.toString().equals(assoObj)) {
			// 部门
			result = NCLocator.getInstance().lookup(IDeptPubService.class).queryDeptVOsByPKS(keyArray);
		} else if (BillEnumCollection.ObjType.PERSON.VALUE.toString().equals(assoObj)) {
			// 业务员
			result = NCLocator.getInstance().lookup(IPsndocPubService.class).queryPsndocByPks(
					keyArray, new String[] { PsndocVO.PK_PSNDOC, PsndocVO.NAME, PsndocVO.NAME + multiLangIndex });
		} else if (BillEnumCollection.ObjType.SUPPLIER.VALUE.toString().equals(assoObj)) {
			// 供应商
			result = NCLocator.getInstance().lookup(ISupplierPubService.class).getSupplierVO(
					keyArray, new String[] { SupplierVO.PK_SUPPLIER, SupplierVO.NAME, SupplierVO.NAME + multiLangIndex });
		} else if (BillEnumCollection.ObjType.CUSSUP.VALUE.toString().equals(assoObj)) {
			// 客商
			NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByPKs(CustSupplierVO.class, keyArray, new String[] { CustSupplierVO.PK_CUST_SUP, CustSupplierVO.NAME, CustSupplierVO.NAME + multiLangIndex }, false);
			if(ncObjects != null && ncObjects.length > 0){
				CustSupplierVO[] custsupVOs = new CustSupplierVO[ncObjects.length];
				for(int i =0 ; i< ncObjects.length; i++){
					CustSupplierVO custsupVO = new CustSupplierVO();
					custsupVO.setPk_cust_sup(((CustSupplierVO)ncObjects[i].getContainmentObject()).getPk_cust_sup());
					custsupVO.setAttributeValue("name" + multiLangIndex, ((CustSupplierVO)ncObjects[i].getContainmentObject()).getAttributeValue("name" + multiLangIndex));
					custsupVOs[i] = custsupVO;
				}
				return custsupVOs;
			}
		}
		
		return result;
	}

	private void reSetDept(ReportQueryCondVO qryConditionVO){ 
		try {
			String pk_dept = "";
			Map<String,String> map = new HashMap<String,String>();
			map = NCLocator.getInstance().lookup(IDeptPubService.class).getOIDSByDeptVIDS(new String[] {qryConditionVO.getDept()});
			pk_dept = map.get(qryConditionVO.getDept());
			qryConditionVO.setDept(pk_dept);	
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}

///:~
