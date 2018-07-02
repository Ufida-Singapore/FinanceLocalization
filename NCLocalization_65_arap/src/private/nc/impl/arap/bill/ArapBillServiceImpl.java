package nc.impl.arap.bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.arap.bill.ArapBillCalUtil;
import nc.bs.arap.bill.ArapBillPubUtil;
import nc.bs.arap.bill.brdeal.BRConditionVO;
import nc.bs.arap.util.BillRetiveInfoUtils;
import nc.bs.arap.util.SqlUtils;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.arap.bill.IArapBillService;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.arap.initgatheringbill.IArapInitGatheringService;
import nc.itf.arap.initpayablebill.IArapInitPayableService;
import nc.itf.arap.initpaybill.IArapInitPaybillService;
import nc.itf.arap.initreceivable.IArapInitRecService;
import nc.itf.fipub.report.IPubReportConstants;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.cmp.settlement.ICmpSettlementPubQueryService;
import nc.ui.arap.cal.ScaleUtils;
import nc.utils.fipub.MemoryResultSetProcessor;
import nc.vo.arap.annotation.ArapBusinessDef;
import nc.vo.arap.annotation.BillBaseBiz;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.cal.BillMathCalculator;
import nc.vo.arap.gathering.AggGatheringBillVO;
import nc.vo.arap.global.ArapBillVOConsts;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.arap.pay.AggPayBillVO;
import nc.vo.arap.payable.AggPayableBillVO;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.arap.pub.BillEnumCollection.Direction;
import nc.vo.arap.receivable.AggReceivableBillVO;
import nc.vo.arap.termitem.TermVO;
import nc.vo.arappub.calculator.data.RelationItemForCal_Credit;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fipub.annotation.Business;
import nc.vo.fipub.annotation.BusinessType;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.fipub.report.AggReportInitializeVO;
import nc.vo.fipub.report.PubCommonReportMethod;
import nc.vo.fipub.report.QryObj;
import nc.vo.fipub.report.QueryObjVO;
import nc.vo.fipub.report.ReportInitializeItemVO;
import nc.vo.fipub.utils.ArrayUtil;
import nc.vo.gl.busirecon.query.BusiReconQryVO;
import nc.vo.gl.busirecon.query.BusiRuleVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.rs.MemoryResultSet;
import nc.vo.pubapp.calculator.Calculator;
import nc.vo.pubapp.calculator.Condition;
import nc.vo.pubapp.pattern.pub.MapList;

import org.apache.commons.lang.ArrayUtils;

public class ArapBillServiceImpl implements IArapBillService {

	/**
	 * 
	 * @author zhaoruic
	 * @since V6.0 2010-09-12
	 */
	public int updatePrintStatus(String table, String pkname, String pk,
			String printer, UFDate printtime) throws BusinessException {		
		int value = 0;
		String sql = "update "+table+" set officialprintdate = '"+printtime.toLocalString()
			+"',officialprintuser = '"+printer+"' where "+pkname+" = '"+pk+"'";
		
		BaseDAO dao = new BaseDAO();
		value = dao.executeUpdate(sql);
		
		return value;
	}

	@Override
	@Business(business = ArapBusinessDef.BillBase, subBusiness = BillBaseBiz.RedContrast,  description = "删除红冲单据回写原单据是否红冲状态", type = BusinessType.NORMAL)/*-=notranslate=-*/
	public void updateIsredFlag(String table, String pkfield, String primarykey)
			throws BusinessException {
		String sql = "update " + table + " set isreded = 'N' where " +  pkfield + " = '" +primarykey + "'";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}

	
	public int updateParentVO(BaseBillVO vo) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		MDPersistenceService.lookupPersistenceService().saveBill(vo);
		return dao.updateVO(vo);	
	}
	
	public String updateCancelConfirmInfo(String table, String pkfield, String primarykey)  throws BusinessException  {
		int i = 0;
		String ts = null;
		String sql = "update " + table + " set coordflag = 0 , billstatus = '"+ArapBillVOConsts.m_intDJStatus_Unconfirmed+"' where " +  pkfield + " = '" +primarykey + "'";
		BaseDAO dao = new BaseDAO();
		i =dao.executeUpdate(sql);
		if(i>0) {
			sql = "select ts from "+table+" where " +  pkfield + " = '" +primarykey + "'";
			ts = (String)dao.executeQuery(sql, new ColumnProcessor(1));
		}
 
		return ts;
	}
	
	public void updateConfirmInfo(String table, String pkfield, String primarykey)  throws BusinessException  {

		String sql = "update " + table + " set coordflag = 0 , billstatus = '"+ArapBillVOConsts.m_intDJStatus_Unconfirmed+"' where " +  pkfield + " = '" +primarykey + "'";
		BaseDAO dao = new BaseDAO();
		dao.executeUpdate(sql);
	}	
	
	/**
	 * 根据pk取得余额表初始环境变量的聚合VO
	 * @param pk
	 * @return
	 * @throws DAOException
	 */
	public AggReportInitializeVO getAggReportInitializeVO (String pk) throws DAOException {
		
		AggReportInitializeVO aggReportInitializeVO = null;
		BaseDAO dao = new BaseDAO();
		
		aggReportInitializeVO = (AggReportInitializeVO)dao.retrieveByPK(AggReportInitializeVO.class, pk);
		
		return aggReportInitializeVO;
	}
	
	/**
	 * 根据元数据id查询余额表中对应的查询对象属性。由于余额表可分为客户余额表和部门余额表，该表对应的元数据不同，故可以通过元数据的id进行区分。
	 * 另外，客户部门余额表本部分不涉及，所以通过count(*) =1过滤掉
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public List<QryObj> getReportQryObj(String bd_mdid) {
		String sql = "select * from fipub_reportinitialize_b where pk_reportinitialize in ( select b.pk_reportinitialize " +
				"from fipub_reportinitialize a, fipub_reportinitialize_b b where a.pk_reportinitialize = b.pk_reportinitialize " +
				"and reporttype = '" + IPubReportConstants.BALANCE_REPORT + "' group by b.pk_reportinitialize having count(*) = 1 )	and bd_mdid = '" +bd_mdid+"'";

		BaseDAO dao = new BaseDAO();
		final List<QryObj> result = new ArrayList<QryObj>();

		try {
			dao.executeQuery(sql, new ResultSetProcessor() {
				private static final long serialVersionUID = 1L;

				public Object handleResultSet(ResultSet rs) throws SQLException {
					while (rs.next()) {
						QryObj qryObj = new QryObj();
						qryObj.setOwnModule(IPubReportConstants.MODULE_ARAP);
						qryObj.setDspName(rs.getString(ReportInitializeItemVO.DSP_OBJNAME));
						qryObj.setOriginTbl(rs.getString(ReportInitializeItemVO.QRY_OBJTABLENAME));
						qryObj.setOriginFld(rs.getString(ReportInitializeItemVO.QRY_OBJFIELDNAME));
						qryObj.setPk_bdinfo(rs.getString(ReportInitializeItemVO.BD_MDID));
						qryObj.setIncludeLowLevel(false);

						qryObj.setBd_name(rs.getString(ReportInitializeItemVO.BD_REFNAME));
						qryObj.setBd_table(rs.getString(ReportInitializeItemVO.BD_TABLENAME));
						qryObj.setBd_pkField(rs.getString(ReportInitializeItemVO.BD_PKFIELD));
						qryObj.setBd_codeField(rs.getString(ReportInitializeItemVO.BD_CODEFIELD));
						qryObj.setBd_nameField(rs.getString(ReportInitializeItemVO.BD_NAMEFIELD));

						qryObj.setBillFieldName(rs.getString(ReportInitializeItemVO.BILLFIELDNAME));
						qryObj.setTallyFieldName(rs.getString(ReportInitializeItemVO.TALLYFIELDNAME));
						qryObj.setBalFieldName(rs.getString(ReportInitializeItemVO.BALFIELDNAME));

						result.add(qryObj);
					}

					return rs;
				}
			});
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}

		return result;
	}
	
	public List<QryObj> getReportQryObjForIUFO(String bd_mdid) {
		String sql = "select * from fipub_queryobj where ownmodule = 'arap' and bd_mdid = '" +bd_mdid+"'";

		BaseDAO dao = new BaseDAO();
		final List<QryObj> result = new ArrayList<QryObj>();

		try {
			dao.executeQuery(sql, new ResultSetProcessor() {
				private static final long serialVersionUID = 1L;

				public Object handleResultSet(ResultSet rs) throws SQLException {
					while (rs.next()) {
						QryObj qryObj = new QryObj();
						qryObj.setOwnModule(IPubReportConstants.MODULE_ARAP);
						qryObj.setDspName(rs.getString(QueryObjVO.DSP_OBJNAME));
						qryObj.setOriginTbl(rs.getString(QueryObjVO.QRY_OBJTABLENAME));
						qryObj.setOriginFld(rs.getString(QueryObjVO.QRY_OBJFIELDNAME));
						qryObj.setPk_bdinfo(rs.getString(QueryObjVO.BD_MDID));
						qryObj.setIncludeLowLevel(false);

						qryObj.setBd_name(rs.getString(QueryObjVO.BD_REFNAME));
						qryObj.setBd_table(rs.getString(QueryObjVO.BD_TABLENAME));
						qryObj.setBd_pkField(rs.getString(QueryObjVO.BD_PKFIELD));
						qryObj.setBd_codeField(rs.getString(QueryObjVO.BD_CODEFIELD));
						qryObj.setBd_nameField(rs.getString(QueryObjVO.BD_NAMEFIELD));

						qryObj.setBillFieldName(rs.getString(QueryObjVO.BILLFIELDNAME));
						qryObj.setTallyFieldName(rs.getString(QueryObjVO.TALLYFIELDNAME));
						qryObj.setBalFieldName(rs.getString(QueryObjVO.BALFIELDNAME));

						result.add(qryObj);
					}

					return rs;
				}
			});
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}

		return result;
	}
	
	/**
	 * 根据元数据id查询对应余额表中的nodecode
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public String getNodeCodeByMdid( String bd_mdid ) {
		String sql = "select a.nodecode from fipub_reportinitialize a, fipub_reportinitialize_b b where a.pk_reportinitialize in "
				+ "( select b.pk_reportinitialize from fipub_reportinitialize a, fipub_reportinitialize_b b where a.pk_reportinitialize "
				+ "= b.pk_reportinitialize and reporttype = '" + IPubReportConstants.BALANCE_REPORT + "' group by b.pk_reportinitialize having count(*) = 1 ) "
				+ "and a.pk_reportinitialize = b.pk_reportinitialize	and b.bd_mdid = '" + bd_mdid + "'";

		BaseDAO dao = new BaseDAO();
		String result = null;

		try {
			result = (String) dao.executeQuery(sql, new ColumnProcessor(1));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 根据元数据id查询对应余额表中的初始环境参数ReportInitializeVO
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @throws BusinessException
	 * @since V6.0 2010-12-28
	 */
	public AggReportInitializeVO getReportInitializeVOByMdid(String bd_mdid) throws BusinessException {
		String sql = "select a.pk_reportinitialize from fipub_reportinitialize a, fipub_reportinitialize_b b where a.pk_reportinitialize in "
				+ "( select b.pk_reportinitialize from fipub_reportinitialize a, fipub_reportinitialize_b b where a.pk_reportinitialize "
				+ "= b.pk_reportinitialize and reporttype = '" + IPubReportConstants.BALANCE_REPORT
				+ "' group by b.pk_reportinitialize having count(*) = 1 ) "
				+ "and a.pk_reportinitialize = b.pk_reportinitialize	and b.bd_mdid = '" 	+ bd_mdid + "'";

		Object result = new BaseDAO().executeQuery(sql, new ArrayListProcessor());

		if (result == null || !(result instanceof List<?>) || ((List<?>) result).size() == 0) {
			return null;
		}

		List<?> resultList = (List<?>) result;
		String pk_reportinitialize = (String) (((Object[]) resultList.get(0))[0]);

		return MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPK(
				AggReportInitializeVO.class, pk_reportinitialize, false);
	}

	/**
	 * 根据nodecode取得注册节点id
	 * 
	 * @param nodeCode
	 * @return
	 * @throws BusinessException
	 */
	public String getFuncIdByNodeCode(String nodeCode) throws BusinessException {
		String sql = "select cfunid from sm_funcregister where funcode = '" + nodeCode + "'";
		BaseDAO dao = new BaseDAO();
		return (String) dao.executeQuery(sql, new ColumnProcessor(1));
	}
	
	/**
	 * 根据条件查询单据类型
	 * @param where
	 * @return
	 * @throws BusinessException
	 */
	public BilltypeVO[] queryBillTypeByWhereStr(String where) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		Collection<?> cl = dao.retrieveByClause(BilltypeVO.class, where);
		BilltypeVO[] billTypeVO = (BilltypeVO[]) ArapCommonTool.changeCollection2Array(cl, BilltypeVO.class);

		return billTypeVO;
	}

	/**
	 * 根据交易类型查询其上级单据类型
	 * @param tradeType
	 * @param pk_group
	 * @return
	 */
	public String getParentBillTypeByTradeType( String tradeType,String pk_group ) {
		String sql = "select parentbilltype from bd_billtype where pk_billtypecode = '" + tradeType
				+ "' and pk_group = '" + pk_group + "'";

		BaseDAO dao = new BaseDAO();
		String result = null;

		try {
			result = (String)dao.executeQuery(sql,new ColumnProcessor(1));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}		
		return result;
	}
	
	/**
	 * 判断期初单据是不是做了转移并账、核销、坏账、损益等操作，如果做了不能修改和删除，返回false， 如果没做否则返回true
	 * @param pk_bill
	 * @return
	 */
	public boolean isInitBillCanEditOrDelete( String[] pk_bills ) {
		if (!ArrayUtils.isEmpty(pk_bills)) {
			BaseDAO dao = new BaseDAO();
			try {
				String sql = "select count(1) from arap_tally where " + SqlUtils.getInStr("pk_bill", pk_bills) + " and pk_dealnum != '~'";
				Integer result = (Integer)dao.executeQuery(sql,new ColumnProcessor(1));
				if(result!= null && result.intValue()>0) {
					return false;
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}		
		}
		
		
		return true;
	}

	@Override
	public MemoryResultSet[] queryBrBillForGL(BusiReconQryVO queryVO,BusiRuleVO ruleVO) throws SQLException, BusinessException {
		BRConditionVO brConditionVO = (BRConditionVO)ruleVO.getBrQueryObj();
		String billTypes[] = brConditionVO.getBilltypecodes();
		MemoryResultSet[] resultSets = new MemoryResultSet[billTypes.length];
		for(int i = 0; i < billTypes.length; i++){
			String[] tableName = getTableName(billTypes[i]);
			String sql = "select ";
			sql = sql + tableName[1] + getPkBillField(billTypes[i]) + " as pk_bill, " + tableName[1] + ".pk_org, " 
//				+ tableName[1] + "." + getBrDate() + " as brdate, " 
				+ tableName[1] + ".billdate, " + tableName[1] + ".pk_billtype, " 
				+ tableName[1] + ".pk_tradetype, " + tableName[1] + ".billno, " + tableName[1] + ".pk_currtype, " + tableName[1] + ".postunit, sum(" 
				+ tableName[1] + ".quantity_bal) as quantity_bal, sum(" + tableName[1] + ".money_bal) as money_bal, sum(" + tableName[1] + ".local_money_bal) as local_money_bal, sum(" 
				+ tableName[1] + ".groupbalance) as groupbalance, sum(" + tableName[1] + ".globalbalance) as globalbalance";
			sql = sql + " from " + tableName[2];
			sql = sql + " where " + tableName[1] + ".objtype = " + brConditionVO.getObjtype() + " and " + tableName[1] + ".pk_billtype = '" + billTypes[i] + "' and " 
				+ tableName[1] + ".billdate >= '" + queryVO.getBeginDate() + "' and " + tableName[1] + ".billdate <= '" + queryVO.getEndDate() + "' and " + tableName[1] + ".dr = 0 and "
				+ tableName[1] + ".pk_group = '" + queryVO.getPk_group() + "' and " + tableName[1] + ".pk_org = '" + queryVO.getPk_org() + "' and " 
				+ SqlUtils.getInStr(tableName[1] + ".pk_currtype ", queryVO.getPk_currtype(), true) ;
			sql = sql + getFreeValues(ruleVO);
			
			if(brConditionVO.getPk_tradetypes() != null && brConditionVO.getPk_tradetypes().length > 0){
				sql = sql + " and " + SqlUtils.getInStr(tableName[1] + ".pk_tradetype ", brConditionVO.getTradetypecodes(), true) ;
			}
			if(brConditionVO.getSubjcodes() != null && brConditionVO.getSubjcodes().length > 0){
				sql = sql + " and " + SqlUtils.getInStr(tableName[1] + ".subjcode ", brConditionVO.getSubjcodes(), true);
			}
			if(brConditionVO.getSubjcode_hs() != null && brConditionVO.getSubjcode_hs().length > 0){
				sql = sql + " and " + SqlUtils.getInStr(tableName[0] + ".subjcode ", brConditionVO.getSubjcode_hs(), true);
			}
			sql = sql + " and " + tableName[0] + ".effectstatus = 10 ";
			sql = sql + " group by " + tableName[1] + getPkBillField(billTypes[i]) + ", " + tableName[1] + ".pk_org, " + tableName[1] + ".billdate, " + tableName[1] + ".pk_billtype, "
				+ tableName[1] + ".pk_tradetype, " + tableName[1] + ".billno, " + tableName[1] + ".pk_currtype, " + tableName[1] + ".postunit";
			
			doQuery(resultSets, i, sql);
		}
		return resultSets;
	}

	private void doQuery(MemoryResultSet[] resultSets, int i, String sql)
			throws SQLException, MetaDataException {
		BaseDAO dao = new BaseDAO();
		try {
			MemoryResultSet resultSet = (MemoryResultSet)dao.executeQuery(sql,new MemoryResultSetProcessor());
			if(resultSet!= null && resultSet.getResultArrayList() != null && resultSet.getResultArrayList().size() > 0) {
				// 插入业务单元名称
				PubCommonReportMethod.insertNameColumn(resultSet, IPubReportConstants.BUSINESS_UNIT, "pk_org", "org");
				// 插入【单据类型】名称
				PubCommonReportMethod.insertNameColumn(resultSet, IPubReportConstants.BILLTYPE, "pk_billtype", "billtype");
				// 插入【交易类型】名称
				PubCommonReportMethod.insertNameColumn(resultSet, IPubReportConstants.BILLTYPE, "pk_tradetype", "tradetype");
				resultSets[i] = resultSet;
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
		}
	}
	
	private String getFreeValues(BusiRuleVO ruleVO){
		String wheresql = "";
		String[] freeTypes = ruleVO.getBrFreeTypes();
		String[] freeValues = ruleVO.getBrFreeValues();
		if(freeTypes != null && freeValues != null && freeTypes.length == freeValues.length){
			for(int i = 0; i < freeTypes.length; i++){
				wheresql = wheresql + " and " + freeTypes[i] + " = '" + freeValues[i] + "' "; 
			}
		}
		return wheresql;
	}

	private String getPkBillField(String billType){
		//主键field
		String relationIDField = "";
		if(IBillFieldGet.F0.equals(billType)){
			relationIDField = ".pk_recbill"; 
		}else if(IBillFieldGet.F1.equals(billType)){
			relationIDField = ".pk_payablebill"; 
		}else if(IBillFieldGet.F2.equals(billType)){
			relationIDField = ".pk_gatherbill"; 
		}else if(IBillFieldGet.F3.equals(billType)){
			relationIDField = ".pk_paybill"; 
		}else if(IBillFieldGet.E0.equals(billType)){
			relationIDField = ".pk_estirecbill"; 
		}else if(IBillFieldGet.E1.equals(billType)){
			relationIDField = ".pk_estipayablebill"; 
		}
		return relationIDField;
	}
	private String[] getTableName(String billType){
		String[] tableName = new String[3];
		if(IBillFieldGet.F0.equals(billType)){
			tableName[0] = "ar_recbill"; 
			tableName[1] = "ar_recitem"; 
			tableName[2] = "ar_recbill inner join ar_recitem on ar_recbill.pk_recbill = ar_recitem.pk_recbill"; 
		}else if(IBillFieldGet.F1.equals(billType)){
			tableName[0] = "ap_payablebill"; 
			tableName[1] = "ap_payableitem"; 
			tableName[2] = "ap_payablebill inner join ap_payableitem on ap_payablebill.pk_payablebill = ap_payableitem.pk_payablebill"; 
		}else if(IBillFieldGet.F2.equals(billType)){
			tableName[0] = "ar_gatherbill"; 
			tableName[1] = "ar_gatheritem"; 
			tableName[2] = "ar_gatherbill inner join ar_gatheritem on ar_gatherbill.pk_gatherbill = ar_gatheritem.pk_gatherbill"; 
		}else if(IBillFieldGet.F3.equals(billType)){
			tableName[0] = "ap_paybill"; 
			tableName[1] = "ap_payitem"; 
			tableName[2] = "ap_paybill inner join ap_payitem on ap_paybill.pk_paybill = ap_payitem.pk_paybill"; 
		}else if(IBillFieldGet.E0.equals(billType)){
			tableName[0] = "ar_estirecbill"; 
			tableName[1] = "ar_estirecitem"; 
			tableName[2] = "ar_estirecbill inner join ar_estirecitem on ar_estirecbill.pk_estirecbill = ar_estirecitem.pk_estirecbill"; 
		}else if(IBillFieldGet.E1.equals(billType)){
			tableName[0] = "ap_estipayablebill"; 
			tableName[1] = "ap_estipayableitem"; 
			tableName[2] = "ap_estipayablebill inner join ap_estipayableitem on ap_estipayablebill.pk_estipayablebill = ap_estipayableitem.pk_estipayablebill"; 
		}
		return tableName;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] save(AggregatedValueObject[] bills, String pkBilltype)
			throws BusinessException {
		Object[] bill =new Object[bills.length];
		int i=0;
		for(AggregatedValueObject vo :bills){
			Object object = null;
			try {
				if(IBillFieldGet.F0.equals(pkBilltype)){
					 NCLocator.getInstance().lookup(IArapInitRecService.class).save( (AggReceivableBillVO)vo);
				}else if(IBillFieldGet.F1.equals(pkBilltype)){
					 NCLocator.getInstance().lookup(IArapInitPayableService.class).save( (AggPayableBillVO)vo);
				}else if(IBillFieldGet.F2.equals(pkBilltype)){
					 NCLocator.getInstance().lookup(IArapInitGatheringService.class).save( (AggGatheringBillVO)vo);
				}else if(IBillFieldGet.F3.equals(pkBilltype)){
					 NCLocator.getInstance().lookup(IArapInitPaybillService.class).save( (AggPayBillVO)vo);
				}
			}catch (Exception exception) {
				object = exception.getMessage();
			}
			bill[i] = object;
			i++;
		}
		return bill;
	}

	@Override
	public Collection<FipMessageVO[]> previewFillMessage(
			BaseAggVO[] bills) throws BusinessException{
		// 收付单据传会计平台
		BaseBillVO headVO = bills[0].getHeadVO();
		String pkbilltype = (String) headVO.getAttributeValue(IBillFieldGet.PK_BILLTYPE);
		if (IBillFieldGet.F2.equals(pkbilltype) || IBillFieldGet.F3.equals(pkbilltype)) {
			ICreateCorpQueryService qryservice = NCLocator.getInstance().lookup(ICreateCorpQueryService.class);
			String pkgroup = (String) headVO.getAttributeValue(IBillFieldGet.PK_GROUP);
			if (qryservice.isEnabled(pkgroup, IBillFieldGet.CMP)) {
				AggregatedValueObject[] settvos = NCLocator.getInstance().lookup(ICmpSettlementPubQueryService.class).queryBillsBySourceBillID(BaseAggVO.getParentPks(bills));
				return fipMsgWithSettInfo(bills, settvos);
			} else {
				fipMessage(bills);
			}
		}else if (IBillFieldGet.F1.equals(pkbilltype)) {
			List<String> listKeys=new ArrayList<String>();
			for(AggregatedValueObject bill:bills){
				listKeys.add(bill.getParentVO().getPrimaryKey());
			}
			List<AggregatedValueObject> newList=new ArrayList<AggregatedValueObject>();
			try {
				Collection<TermVO> terms = (Collection<TermVO>) MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(TermVO.class,
						SqlUtils.getInStr(TermVO.PK_BILL, listKeys.toArray(new String[]{}), true), true);
				for(AggregatedValueObject vo : bills){
					List<BaseItemVO> newItems=new ArrayList<BaseItemVO>();
					for(CircularlyAccessibleValueObject sitem:vo.getChildrenVO()){
						int i=0;
						for(TermVO term:terms){
							if(term.getPk_item().equals(sitem.getPrimaryKey())){
								i++;
								BaseItemVO newItem=(BaseItemVO) sitem.clone();
								for(String attr:term.getAttributeNames()){
									if(attr.equals(IBillFieldGet.PREPAY)){
										newItem.setPrepay(term.getPrepay().booleanValue()?1:0);
									}else if(attr.equals(IBillFieldGet.SUPPLIER) || attr.equals(IBillFieldGet.PK_BALATYPE)){
										continue;
									}else{
										newItem.setAttributeValue(attr, term.getAttributeValue(attr));
									}
								}
								if(i>1){
									newItem.setLocal_tax_cr(UFDouble.ZERO_DBL);
									newItem.setLocal_notax_cr(UFDouble.ZERO_DBL);
									newItem.setNotax_cr(UFDouble.ZERO_DBL);
									newItem.setGlobalnotax_cre(UFDouble.ZERO_DBL);
									newItem.setGlobaltax_cre(UFDouble.ZERO_DBL);
									newItem.setGroupnotax_cre(UFDouble.ZERO_DBL);
									newItem.setGlobaltax_cre(UFDouble.ZERO_DBL);
								}
								newItems.add(newItem);
							}
						}
					}
					AggPayableBillVO bill=new AggPayableBillVO();
					bill.setParentVO((CircularlyAccessibleValueObject) vo.getParentVO().clone());
					bill.setChildrenVO(newItems.toArray(new BaseItemVO[]{}));
					newList.add(bill);
				}
			} catch (SQLException e) {
				ExceptionHandler.handleException(e);
			}
			return fipMessage(newList.toArray(new AggregatedValueObject[]{}));
		} else {
			return fipMessage(bills);
		}
		return null;
	}
	
	<T extends AggregatedValueObject> Collection<FipMessageVO[]> fipMsgWithSettInfo(T[] bills, AggregatedValueObject[] settles) throws BusinessException {
		if (null == bills || bills.length == 0) {
			return null;
		}

		AggregatedValueObject[] clonebills = bills.clone();
		if (!(ArrayUtils.isEmpty(settles))) {
			BaseAggVO[] splitVobySettinfo = splitVobySettinfo(ArrayUtil.convertSupers2Subs((AggregatedValueObject[]) bills, BaseAggVO.class), settles);
			for (int j = 0; j < (Math.min(clonebills.length, splitVobySettinfo.length)); j++) {
				clonebills[j] = splitVobySettinfo[j];
			}
		}
		return fipMessage(clonebills);
	}
	
	private BaseAggVO[] splitVobySettinfo(BaseAggVO[] vos, AggregatedValueObject[] settles) {
		Map<String, AggregatedValueObject> map = constMap(settles, "pk_busibill");
		List<BaseAggVO> retvos = new ArrayList<BaseAggVO>();
		for (BaseAggVO io : vos) {
			AggregatedValueObject settvo = (AggregatedValueObject) map.get(io.getHeadVO().getPrimaryKey());
			if (settvo == null) {
				retvos.add(io);
			} else {
				retvos.add(splitVobySettinfo(io, settvo));
			}
		}
		BaseAggVO[] array = retvos.toArray(new BaseAggVO[0]);
		ArapBillPubUtil.refreshChildVO2HeadVO(array);
		calTax(array);
		return array;
	}
	
	private Map<String, AggregatedValueObject> constMap(AggregatedValueObject[] bills, String key) {
		Map<String, AggregatedValueObject> map = new HashMap<String, AggregatedValueObject>();
		for (AggregatedValueObject bill : bills) {
			map.put((String) bill.getParentVO().getAttributeValue(key), bill);
		}
		return map;
	}
	
	public BaseAggVO splitVobySettinfo(BaseAggVO vo, AggregatedValueObject settle) {
		if (settle == null) {
			throw new IllegalArgumentException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0580")/* @res "结算不能为空" */);
		}
		CircularlyAccessibleValueObject[] settitemvos = settle.getChildrenVO();
		BaseItemVO[] itemvos = vo.getItems();
		MapList<String, SuperVO> tables = new MapList<String, SuperVO>();

		for (CircularlyAccessibleValueObject item : settitemvos) {
			tables.put((String) item.getAttributeValue("pk_billdetail"), (SuperVO) item);
		}

		List<BaseItemVO> splits = new ArrayList<BaseItemVO>();
		Set<String> settleDetailSet = new HashSet<String>();
		for (BaseItemVO iterator : itemvos) {
			if(tables.get(iterator.getPrimaryKey()) == null  || tables.get(iterator.getPrimaryKey()).size()==0){
				splits.add(iterator);
			}
			for (SuperVO jo : tables.get(iterator.getPrimaryKey()) == null ? new ArrayList<SuperVO>() : tables.get(iterator.getPrimaryKey())) {
				if (!settleDetailSet.contains(jo.getPrimaryKey())) {
					BaseItemVO copy = copy(iterator, jo);
					splits.add(copy);
					settleDetailSet.add(jo.getPrimaryKey());
				}
			}
		}

		BaseAggVO clone = (BaseAggVO) vo.clone();
		clone.getHeadVO().setSettleHead((SuperVO) settle.getParentVO());
		clone.setChildrenVO(splits.toArray(new BaseItemVO[0]));
		clone.setSettlevo(settle);
		return clone;
	}
	
	/**
	 * @param assist
	 *            辅助信息
	 * @param main
	 *            主要信息（金额）
	 * @param settleDetailList
	 * @return
	 */
	private BaseItemVO copy(BaseItemVO assist, SuperVO main) {
		BaseItemVO clone = (BaseItemVO) assist.clone();
		// FK付款单是借方
		// SK收款单是贷方
		clone.setMoney_de((UFDouble) main.getAttributeValue("pay"));
		clone.setMoney_cr((UFDouble) main.getAttributeValue("receive"));
		clone.setLocal_money_cr((UFDouble) main.getAttributeValue("receivelocal"));
		clone.setLocal_money_de((UFDouble) main.getAttributeValue("paylocal"));

		if (isDirectionDebt(clone)) {
			clone.setMoney_bal((UFDouble) main.getAttributeValue("pay"));
			clone.setLocal_money_bal((UFDouble) main.getAttributeValue("paylocal"));
			clone.setGroupbalance((UFDouble) main.getAttributeValue("grouppaylocal"));
			clone.setGlobalbalance((UFDouble) main.getAttributeValue("globalpaylocal"));
			clone.setOccupationmny((UFDouble) main.getAttributeValue("pay"));
			clone.setGroupdebit((UFDouble) main.getAttributeValue("grouppaylocal"));
			clone.setGlobaldebit((UFDouble) main.getAttributeValue("globalpaylocal"));
		} else {
			clone.setMoney_bal((UFDouble) main.getAttributeValue("receive"));
			clone.setLocal_money_bal((UFDouble) main.getAttributeValue("receivelocal"));
			clone.setGroupbalance((UFDouble) main.getAttributeValue("groupreceivelocal"));
			clone.setGlobalbalance((UFDouble) main.getAttributeValue("globalreceivelocal"));
			clone.setOccupationmny((UFDouble) main.getAttributeValue("receive"));
			clone.setGroupcrebit((UFDouble) main.getAttributeValue("groupreceivelocal"));
			clone.setGlobalcrebit((UFDouble) main.getAttributeValue("globalreceivelocal"));
		}
		clone.setStatus(VOStatus.NEW);
		clone.setSettleBody(main);
		return clone;
	}
	
	/**
	 * @param baseItemVO
	 * @return
	 */
	private static boolean isDirectionDebt(BaseItemVO baseItemVO) {
		int direction = baseItemVO.getDirection().intValue();
		// 是否是借方 正数1
		boolean isdebt = direction == Direction.DEBIT.VALUE.intValue();
		return isdebt;
	}
	
	private static void calTax(BaseAggVO[] baseAggVOs) {
		Condition cond = new Condition();
		cond.setCalOrigCurr(false);
		cond.setIsCalLocalCurr(true);
		cond.setIsChgPriceOrDiscount(false);
		cond.setIsFixNchangerate(false);
		cond.setIsFixNqtunitrate(false);
		String pk_group = baseAggVOs[0].getHeadVO().getPk_group();
		String pk_org = baseAggVOs[0].getHeadVO().getPk_org();
		cond.setGroupLocalCurrencyEnable(ArapBillCalUtil.isUseGroupMoney(pk_group));
		cond.setGlobalLocalCurrencyEnable(ArapBillCalUtil.isUseGlobalMoney());
		cond.setOrigCurToGlobalMoney(ArapBillCalUtil.isOrigCurToGlobalMoney());
		cond.setOrigCurToGroupMoney(ArapBillCalUtil.isOrigCurToGroupMoney(pk_group));
		boolean isInterNational = BillEnumCollection.BuySellType.OUT_BUY.VALUE.equals((baseAggVOs[0].getHeadVO().getAttributeValue(IBillFieldGet.BUYSELLFLAG)))
				|| BillEnumCollection.BuySellType.OUT_SELL.VALUE.equals((baseAggVOs[0].getHeadVO().getAttributeValue(IBillFieldGet.BUYSELLFLAG)));
		cond.setInternational(isInterNational);
		try {
			cond.setIsTaxOrNet(BillMathCalculator.getProior(0, pk_org));
		} catch (Exception e) {
			nc.bs.logging.Log.getInstance("arapExceptionLog").error(e);
		}
		for (BaseAggVO bill : baseAggVOs) {
			for (BaseItemVO item : bill.getItems()) {
				Calculator tool = new Calculator(new CircularlyAccessibleValueObject[] {
					item
				}, RelationItemForCal_Credit.getInstance());
				tool.calculate(cond, IBillFieldGet.MONEY_CR, new ScaleUtils(pk_group));
			}

		}
	}
	
	<T extends AggregatedValueObject> Collection<FipMessageVO[]> fipMessage(T[] bills) throws BusinessException {
		new BillRetiveInfoUtils().retryInfos(bills);
		Collection<FipMessageVO[]> messagevos = new ArrayList<FipMessageVO[]>();
		for (int i = 0; i < bills.length; i++) {
			CircularlyAccessibleValueObject head = bills[i].getParentVO();
			UFDate effectdate = (UFDate) head.getAttributeValue(IBillFieldGet.EFFECTDATE);
			String pk_tradetype = (String) head.getAttributeValue(IBillFieldGet.PK_TRADETYPE);
			String pk_group = (String) head.getAttributeValue(IBillFieldGet.PK_GROUP);
			FipRelationInfoVO reVO = new FipRelationInfoVO();
			reVO.setPk_group((String) head.getAttributeValue(IBillFieldGet.PK_GROUP));
			reVO.setPk_org((String) head.getAttributeValue(IBillFieldGet.PK_ORG));
			reVO.setRelationID(head.getPrimaryKey());
			reVO.setPk_system(PfDataCache.getBillTypeInfo(pk_group, pk_tradetype).getSystemcode());
			reVO.setBusidate(effectdate == null ? new UFDate() : effectdate);
			reVO.setPk_billtype(pk_tradetype);
			reVO.setPk_operator(InvocationInfoProxy.getInstance().getUserId());

			reVO.setFreedef1((String) head.getAttributeValue(IBillFieldGet.BILLNO));
			reVO.setFreedef2((String) head.getAttributeValue(IBillFieldGet.SCOMMENT));
			UFDouble money = (UFDouble) head.getAttributeValue(IBillFieldGet.MONEY);
			if (null == head.getAttributeValue(IBillFieldGet.PK_CURRTYPE)) {
				head.setAttributeValue(IBillFieldGet.PK_CURRTYPE, bills[i].getChildrenVO()[0].getAttributeValue(IBillFieldGet.PK_CURRTYPE));
			}
			money = money.setScale(nc.itf.fi.pub.Currency.getCurrDigit((String) head.getAttributeValue(IBillFieldGet.PK_CURRTYPE)), UFDouble.ROUND_HALF_UP);
			reVO.setFreedef3(money.toString());
			reVO.setDefdoc1((String) head.getAttributeValue(IBillFieldGet.PK_BUSITYPE));

			FipMessageVO messageVO = new FipMessageVO();
			messageVO.setBillVO(bills[i]);
			messageVO.setMessagetype(FipMessageVO.MESSAGETYPE_ADD);
			messageVO.setMessageinfo(reVO);
			messagevos.add(new FipMessageVO[]{messageVO});
		}
		return messagevos;
	}
}
