package nc.bs.cmp.accountagiotage;

/**
 * 此处插入类型描述。
 * 创建日期：(2003-9-25 14:02:51)
 * @author：Administrator
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.naming.NamingException;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.cmp.util.SqlUtils;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pub.SystemException;
import nc.bs.sec.esapi.NCESAPI;
import nc.cmp.agiotage.utils.CmpAgiotageBusiUtils;
import nc.cmp.bill.util.SysInit;
import nc.cmp.pub.exception.ExceptionHandler;
import nc.cmp.tools.ContrastUtils;
import nc.itf.cmp.pub.Currency;
import nc.itf.org.IOrgConst;
import nc.itf.org.IOrgUnitQryService;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.pubitf.accperiod.AccountCalendar;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageAggVO;
import nc.vo.cmp.agiotage.CMPAgiotageBzVO;
import nc.vo.cmp.agiotage.CMPAgiotageDJVO;
import nc.vo.cmp.agiotage.CMPAgiotageVO;
import nc.vo.cmp.agiotage.CMPJe;
import nc.vo.cmp.bill.ChangeBillAggVO;
import nc.vo.cmp.bill.ChangeBillDetailVO;
import nc.vo.cmp.bill.ChangeBillVO;
import nc.vo.cmp.bill.CmpBillFieldGet;
import nc.vo.cmp.dj.DJZBVOConsts;
import nc.vo.ml.MultiLangContext;
import nc.vo.org.OrgVO;
import nc.vo.org.util.OrgTypeManager;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.tmpub.util.TMCurrencyUtil;

public class AccountAgiotageDMO {

	final String reportSegment = " SELECT  pk_account pk_account,SUM (gathering) - SUM (pay) primal_amount,"
			+ "SUM (gatheringlocal) - SUM (paylocal) local_amount, fundformcode formcode FROM ";
	final String groupby = " GROUP BY pk_account,fundformcode";

	/**
	 * AccountAgiotageDMO 构造子注解。
	 * 
	 * @exception javax.naming.NamingException
	 *                异常说明。
	 * @exception nc.bs.pub.SystemException
	 *                异常说明。
	 */
	public AccountAgiotageDMO() throws NamingException, SystemException {
	}

	// public Connection getConnection() throws Exception {
	// Connection conn = null;
	// conn = PersistenceManager.getInstance().getJdbcSession().getConnection();
	// return conn;
	// }

	protected PreparedStatement prepareStatement(Connection con, String sql) throws SQLException {
		return con.prepareStatement(sql);
	}

	protected String getOID() {
		return new SequenceGenerator().generate();
	}

	/**
	 * 根据币种、银行账户、现金账户查询账户、原币合计、本币合计
	 * 
	 * @param agioVO
	 * @return
	 * @throws BusinessException
	 * @author jiaweib
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	private List<CMPAgiotageDJVO> getAccountResult(CMPAgiotageVO agioVO) throws BusinessException {
		Hashtable pkAccidsHash = agioVO.getPkAccids(); /* 币种对应银行账户Pks */
		Hashtable pkManageAccidesHash = agioVO.getPkManageAccids();/* 币种对应管理账户Pks */
		List<String> pk_accountList = CmpAgiotageBusiUtils.getValueListByStringTOStringArrayMap(pkAccidsHash);
		List<String> pk_mngaccountList = CmpAgiotageBusiUtils.getValueListByStringTOStringArrayMap(pkManageAccidesHash);
		String[] accidPks = new String[pk_accountList.size()];
		pk_accountList.toArray(accidPks);
		// 得到管理帐号的pks
		String[] manageAccidPks = new String[pk_mngaccountList.size()];
		pk_mngaccountList.toArray(manageAccidPks);

		// 得到sql
		String accSql = getAccSqlSegment(accidPks, manageAccidPks);
		if (accSql == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
					"03607agi-0031")/* @res "必须选择账户！" */);
		}
		StringBuilder build = new StringBuilder();
		build.append("select pk_account,sum(primal_amount) primal, SUM (local_amount) local , formcode from(");
		build.append(this.getInitdataSqlSegment(agioVO, accSql));
//		build.append(" UNION ALL ");
//		build.append(this.getTallyYearlySqlSegment(agioVO, accSql));
//		build.append(" UNION ALL ");
//		build.append(this.getTallyMensalSqlSegment(agioVO, accSql));
//		build.append(" UNION ALL ");
//		build.append(this.getTallyDailySqlSegment(agioVO, accSql));
		build.append(" UNION ALL ");
		build.append(this.getDetailSqlSegment(agioVO, accSql));
		build.append(") zb group by pk_account,formcode");

		List<CMPAgiotageDJVO> agiotageDJVOList = null;
		// 查数据库
		Connection con = null;
		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;
		try {
			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(build.toString());
			ResultSet rsQc = stmt.executeQuery();
			/* 将结果集转化为DJCLBVO */
			agiotageDJVOList = getAgiotageDJVOListByResultSet(rsQc, agioVO);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (con != null) {
					con.close();
				}
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}
		return agiotageDJVOList;
	}

	private Map<String, CMPAgiotageBzVO> getBzbmTOCMPAgiotageBzVOMap(Vector<CMPAgiotageBzVO> agioBzVOVec) {
		Map<String, CMPAgiotageBzVO> bzbmTOCMPAgiotageBzVOMap = new HashMap<String, CMPAgiotageBzVO>();
		for (CMPAgiotageBzVO agiotageBzVO : agioBzVOVec) {
			bzbmTOCMPAgiotageBzVOMap.put(agiotageBzVO.getBzbm(), agiotageBzVO);
		}
		return bzbmTOCMPAgiotageBzVOMap;
	}

	public List<CMPAgiotageDJVO> getAgiotageDJVOListByResultSet(ResultSet rs, CMPAgiotageVO agioVO)
			throws BusinessException {
		List<CMPAgiotageDJVO> vResult = new ArrayList<CMPAgiotageDJVO>();
		Vector<CMPAgiotageBzVO> agioBzVOVec = agioVO.getSelBzbm();
		Map<String, CMPAgiotageBzVO> bzbmTOCMPAgiotageBzVOMap = this.getBzbmTOCMPAgiotageBzVOMap(agioBzVOVec);
		Map<String, String> accountToBzbmMap = this.getAccountToBzbmMap(agioVO);
		CMPAgiotageBzVO bzvo = null;
		try {
			while (rs.next()) {
				CMPAgiotageDJVO voDj = new CMPAgiotageDJVO();
				// 类型编码 0-现金账户 1-银行账户
				int formcode = rs.getInt(4);

				String pk_mngaccount = rs.getString(1);
				String pk_account = rs.getString(1);

				if (formcode == 0) {
					// pk_mngaccount
					voDj.setPk_manageAccount(pk_mngaccount);
				} else if (formcode == 1) {
					// pk_account
					voDj.setPk_account(pk_account);
				} else {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
							"03607agi-0058")/* @res "账户类型错误" */);
				}

				// ybye :
				BigDecimal ybye = rs.getBigDecimal(2);
				UFDouble ybyeUFD = ybye == null ? new UFDouble(0) : new UFDouble(ybye);
//				voDj.setYbye(new UFDouble(0));
				//这里不要讲原币置为零,后面要根据它判断  modified by weiningc  start
				voDj.setYbye(ybyeUFD); //end
				String bzbm = null;
				if (pk_mngaccount != null) {
					bzbm = accountToBzbmMap.get(pk_mngaccount);
				} else {
					bzbm = accountToBzbmMap.get(pk_account);
				}
				bzvo = bzbmTOCMPAgiotageBzVOMap.get(bzbm);
				// bbye :
				BigDecimal bbye = rs.getBigDecimal(3);
				UFDouble bbyeUFD = bbye == null ? new UFDouble(0) : new UFDouble(bbye);
				bbyeUFD = bbyeUFD.setScale(bzvo.getBbDig(), UFDouble.ROUND_HALF_UP);
				/**
				 * 汇兑损益值=账户实时余额原币乘或除调整汇率-账户实时余额本币。
				 * 如果汇兑损益值大于0，则记收款本币金额；如果汇兑损益值小于0，则记付款本币金额。
				 */

				UFDouble agiotageJe = null;
				if (bzvo.getM_bBbbf() == 0) {
					agiotageJe = ybyeUFD.multiply(bzvo.getBbhl()).sub(bbyeUFD);
				} else {
					agiotageJe = ybyeUFD.div(bzvo.getBbhl()).sub(bbyeUFD);
				}
				voDj.setFbye(new UFDouble(0));
				voDj.setBbye(agiotageJe);
				vResult.add(voDj);
			}
		} catch (Exception ex) {
			throw ExceptionHandler.handleException(this.getClass(), ex);
		}

		return vResult;

	}

	private String getInitdataSqlSegment(CMPAgiotageVO agioVO, String accSql) {
		String sql = "SELECT  pk_account pk_account,init_primal primal_amount,init_local local_amount"
				+ ",formcode FROM cmp_initdata WHERE   pk_org = '" + NCESAPI.sqlEncode(agioVO.getDwbm()) + "' and isnull(dr,0) = 0 and "
				+ accSql;
		return sql;
	}

	private String getTallyYearlySqlSegment(CMPAgiotageVO agioVO, String accSql) {
		String sql = reportSegment + " cmp_tallyyearly WHERE pk_org = '" + agioVO.getDwbm()
				+ "' AND isnull(cyear,'1')<>'1' AND cyear < '" + agioVO.getCalNd() + "' and " + accSql + groupby;
		return sql;
	}

	private String getTallyMensalSqlSegment(CMPAgiotageVO agioVO, String accSql) {
		String sql = reportSegment + " cmp_tallymensal WHERE pk_org = '" + agioVO.getDwbm() + "'  AND cyear = '"
				+ agioVO.getCalNd() + "' AND cmonth <'" + agioVO.getCalQj() + "' and " + accSql + groupby;
		return sql;
	}

	private String getTallyDailySqlSegment(CMPAgiotageVO agioVO, String accSql) {
		String sql = reportSegment + " cmp_tallydaily WHERE pk_org = '" + agioVO.getDwbm() + "'  AND cyear = '"
				+ agioVO.getCalNd() + "' AND cmonth ='" + agioVO.getCalQj() 
				+ "' and cday <'" + agioVO.getDateBeg().substring(0,10)//截取日期格式yyyy-mm-dd
				+ "' and " + accSql + groupby;
		return sql;
	}

	private String getDetailSqlSegment(CMPAgiotageVO agioVO, String accSql) {
		String sql = "SELECT pk_account, SUM (isnull(recmoney,0)) - SUM(isnull(paymoney,0)) primal_amount,"
				+ "SUM(isnull(olcrecmoney,0)) - SUM (isnull(olcpaymoney,0)) local_amount, fundformcode formcode "
				+ "FROM cmp_bankaccdetail WHERE isnull(dr,0) = 0 AND pk_org ='" + NCESAPI.sqlEncode(agioVO.getDwbm())
//				+ "' and tallydate >= '" + agioVO.getDateBeg().substring(0,10)//截取日期格式yyyy-mm-dd
				+ "' and tallydate <='" + NCESAPI.sqlEncode(agioVO.getDateEnd()) 
				+ "' and useflag = 1 and "//取账面余额
				+ accSql + groupby;
		return sql;
	}

	// 得到账号的sql片段
	private String getAccSqlSegment(String[] accidPks, String[] manageAccidPks) throws BusinessException {
		String sql = null;
		String pk_mngaccountSql = null;
		if (!CmpAgiotageBusiUtils.isArrayIsNull(accidPks)) {
			sql = SqlUtils.getInStr("pk_account", accidPks);
		}
		if (!CmpAgiotageBusiUtils.isArrayIsNull(manageAccidPks)) {
			pk_mngaccountSql = SqlUtils.getInStr("pk_account", manageAccidPks);
		}
		if (sql == null && pk_mngaccountSql == null) {
			return null;
		}
		if (sql == null) {
			return pk_mngaccountSql;
		}
		if (pk_mngaccountSql == null) {
			return sql;
		} else {
			sql = " (" + sql + " or " + pk_mngaccountSql + " )";
		}
		return sql;
	}

	/**
	 * 主要功能：取得FB中的所有币种。 主要算法：为月末处理提供和损益初始化提供 异常描述： 创建日期：(2001-8-8 11:28:41)
	 * 最后修改日期：(2001-8-8 11:28:41)
	 * 
	 * @author：wyan
	 * @return java.util.Vector
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public Hashtable<String, String> getAllBzbm(CMPAgiotageVO vo) throws Exception {
		String sql = "SELECT DISTINCT zhda.pk_currtype,bz.name FROM cmp_initdata zhda INNER JOIN "
				+ "bd_currtype bz ON zhda.pk_currtype = bz.pk_currtype and bz.dr = 0 WHERE zhda.pk_org = '"
				+ NCESAPI.sqlEncode(vo.getDwbm()) + "' and zhda.pk_currtype != '" + NCESAPI.sqlEncode(vo.getBzbm())
				+ "' and zhda.dr=0 and isnull(zhda.approvedate,'1')<>'1' ";

		Hashtable<String, String> hTab = new Hashtable<String, String>();
		Connection con = null;
		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String bzbm = rs.getString(1);
				if (bzbm == null)
					bzbm = null;
				String bzmc = rs.getString(2);
				if (bzmc == null)
					bzmc = null;

				hTab.put(bzmc, bzbm);

			}

		} catch (Exception e) {
			ExceptionHandler.consume(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (con != null) {
					con.close();
				}
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}

		return hTab;
	}

	/**
	 * 主要功能：取得损益记录中的所有币种。 主要算法： 异常描述： 创建日期：(2001-8-8 11:28:41) 最后修改日期：(2001-8-8
	 * 11:28:41)
	 * 
	 * @author：wyan
	 * @return java.util.Vector
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public Hashtable<String, CMPAgiotageBzVO> getAllBzbmOfHisRecord(CMPAgiotageVO vo) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct zhda.pk_currtype,bz.name ");
		sql.append(" from cmp_initdata zhda ");
		sql.append(" inner join bd_currtype bz on bz.pk_currtype = zhda.pk_currtype and bz.dr = 0 ");
		sql.append(" where zhda.pk_org = '");
		sql.append(NCESAPI.sqlEncode(vo.getDwbm()));
		sql.append("'");
		sql.append(" and isnull(zhda.approvedate,'1')<>'1'");

		Hashtable<String, CMPAgiotageBzVO> hTab = new Hashtable<String, CMPAgiotageBzVO>();
		Connection con = null;
		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;

		try {

			String bbbm = Currency.getLocalCurrPK(vo.getDwbm()); /* 本币编码 */
			// int ybDig = TMCurrencyUtil.getCurrtypeDigit(vo.getBzbm()); /*
			// 原币默认小数位数 */
			int ybDig = 2; /* 原币默认小数位数 */
			int bbDig;
			int fbDig = /* 辅币默认小数位数 */
			bbDig = Currency.getCurrInfo(bbbm).getCurrdigit().intValue(); /* 本币默认小数位数 */

			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				CMPAgiotageBzVO bzvo = new CMPAgiotageBzVO();
				String bzbm = rs.getString(1);
				bzbm = (bzbm == null ? null : bzbm.trim());
				String bzmc = rs.getString(2).trim();
				bzmc = (bzmc == null ? null : bzmc.trim());
				bzvo.setBzbm(bzbm);
				bzvo.setFbDig(fbDig);
				bzvo.setBbDig(bbDig);
				hTab.put(bzmc, bzvo);
			}
			Enumeration<String> em = hTab.keys();
			while (em.hasMoreElements()) {
				try {
					CMPAgiotageBzVO bzvo = hTab.get(em.nextElement());
					ybDig = Currency.getCurrInfo(bzvo.getBzbm()).getCurrdigit().intValue();
					bzvo.setYbDig(ybDig);
				} catch (Exception ex) {
					throw ExceptionHandler.handleException(this.getClass(), ex);
				}

			}
		} catch (SQLException ex) {
			ExceptionHandler.consume(ex);
			throw ex;
		} catch (Exception ex) {
			ExceptionHandler.consume(ex);
			throw new SQLException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (con != null) {
					con.close();
				}
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}

		return hTab;
	}

	/**
	 * 账户汇兑损亦单据表头。 创建日期：(2003-9-28 14:45:08)
	 * 
	 * @return nc.vo.ep.dj.DJZBHeaderVO
	 * @param agioVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	private ChangeBillVO getDJHeaderVO(CMPAgiotageVO agioVO) throws Exception {

		ChangeBillVO vo = new ChangeBillVO();

		vo.setCreator(agioVO.getUser());
		vo.setBill_date(new UFDate(agioVO.getDateEnd()));
		vo.setCreationtime(new UFDateTime(agioVO.getDateEnd()));
		vo.setTrade_type("DR");
		vo.setPk_group(agioVO.getPk_group());
		vo.setPk_org(agioVO.getDwbm());
		vo.setPk_tradetypeid(this.getTradeTypeId(vo.getTrade_type(), vo.getPk_group()));
		vo.setBillclass(CmpBillFieldGet.HJ);
		vo.setBill_type("F6");
		vo.setPk_pcorg(agioVO.getDwbm());
		vo.setBill_status(Integer.valueOf(DJZBVOConsts.m_intDJStatus_Saved));
		vo.setBill_accounting_year(agioVO.getCalNd());
		vo.setBill_accounting_period(agioVO.getCalQj());
		vo.setAudit_accounting_year(agioVO.getCalNd());
		vo.setAudit_accounting_period(agioVO.getCalQj());
		vo.setApprove_date(new UFDate(agioVO.getDateEnd()));
		vo.setBillmaker_date(new UFDate(agioVO.getDateEnd()));
		vo.setDz_date(new UFDate(agioVO.getDateEnd()));
		vo.setEffect_date(new UFDate(agioVO.getDateEnd()));
		vo.setSource_flag("2");
		vo.setApprover(agioVO.getUser());
		vo.setBillmaker(agioVO.getUser());
		// vo.setPrimal_money(agioVO.getCalCe().getYb().abs());
		// vo.setLocal_money(agioVO.getCalCe().getBb().abs());

		// 负数为付款，正数为收款
		if (ArapCommonTool.isLessZero(agioVO.getCalCe().getBb())) {
			// 负数-付款
			vo.setPrimal_money(new UFDouble(0));
			vo.setLocal_money(new UFDouble(0));
			vo.setPay_primal(agioVO.getCalCe().getYb().abs());
			vo.setPay_local(agioVO.getCalCe().getBb().abs());
			// vo.setPk_oppaccount(agioVO.getPk_account());
		} else {
			// 正数-收款
			vo.setPrimal_money(agioVO.getCalCe().getYb().abs());
			vo.setLocal_money(agioVO.getCalCe().getBb().abs());
			vo.setPay_primal(new UFDouble(0));
			vo.setPay_local(new UFDouble(0));
			// vo.setPk_account(agioVO.getPk_account());
		}

		OrgVO orgVO = NCLocator.getInstance().lookup(IOrgUnitQryService.class).getOrg(vo.getPk_org());
		vo.setPk_org_v(orgVO.getPk_vid());

		// 判断当前组织是否利润中心
		boolean isLiaCenterType = OrgTypeManager.getInstance().isTypeOf(orgVO, IOrgConst.LIACENTERTYPE);

		if (isLiaCenterType) {
			vo.setPk_pcorg(vo.getPk_org());
			vo.setPk_pcorg_v(orgVO.getPk_vid());
		}

		return vo;
	}

	private String getTradeTypeId(String pk_billtype, String pk_group) throws BusinessException {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.pk_billtypeid from bd_billtype a ");
			sql.append("where a.pk_billtypecode='").append(pk_billtype).append("' ");
			sql.append("and a.pk_group = '").append(pk_group).append("'");
			sql.append("and isnull(a.dr,0)=0");

			Object o = new BaseDAO().executeQuery(sql.toString(), null, new ResultSetProcessor() {
				private static final long serialVersionUID = 1L;

				public Object handleResultSet(ResultSet rs) throws SQLException {
					String tradeType = null;
					while (rs.next()) {
						tradeType = rs.getString(1);
					}
					return tradeType;
				}
			});
			return o == null ? null : o.toString();
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	/**
	 * 账户汇兑损亦单据表体。 创建日期：(2003-9-28 14:45:41)
	 * 
	 * @return nc.vo.ep.dj.DJZBItemVO[]
	 * @param agioVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	private ChangeBillDetailVO getDJItemVO(CMPAgiotageVO agioVO, CMPAgiotageDJVO agiotageDJVO) throws Exception {

		ChangeBillDetailVO vo = new ChangeBillDetailVO();

		vo.setBillclass(CmpBillFieldGet.HJ);
		vo.setCreator(agioVO.getUser());
		vo.setPk_group(agioVO.getPk_group());
		vo.setTrade_type("DR");
		vo.setPk_tradetypeid(this.getTradeTypeId(vo.getTrade_type(), vo.getPk_group()));
		vo.setBill_date(new UFDate(agioVO.getDateEnd()));
		vo.setBill_type("F6");
		vo.setCreationtime(new UFDateTime(agioVO.getDateEnd()));
		vo.setPk_org(agioVO.getDwbm());
		vo.setBilldetail_no(Integer.valueOf(0));
		vo.setPk_pcorg(agioVO.getDwbm());
		vo.setPk_currtype(agioVO.getBzbm());

		// 组织本币汇率
		vo.setLocal_rate(TMCurrencyUtil.getOrgCurrRate(vo.getPk_org(), vo.getPk_currtype(), new UFDate()));
		// 集团本币汇率
		vo.setGroup_rate(TMCurrencyUtil.getGroupCurrRate(vo.getPk_group(), vo.getPk_org(), vo.getPk_currtype(),
				new UFDate()));
		// 全局本币汇率
		vo.setGlobal_rate(TMCurrencyUtil.getGlobalCurrRate(vo.getPk_org(), vo.getPk_currtype(), new UFDate()));

		// 负数为付款，正数为收款
		if (ArapCommonTool.isLessZero(agioVO.getCalCe().getBb())) {
			// 负数-付款
			// 付款原币
			vo.setPay_primal(agioVO.getCalCe().getYb().abs());
			// 付款组织本币金额
			vo.setPay_local(agioVO.getCalCe().getBb().abs());
			// 付款集团本币金额
			vo.setGroup_local_pay(TMCurrencyUtil.getGroupLocalMoney(vo.getPk_group(), vo.getPk_org(), vo
					.getPk_currtype(), vo.getPay_primal(), null, null, new UFDate()));
			// 付款全局本币金额
			vo.setGlobal_local_pay(TMCurrencyUtil.getGlobalLocalMoney(vo.getPk_org(), vo.getPk_currtype(), vo
					.getPay_primal(), null, null, new UFDate()));

			vo.setPk_oppaccount(agiotageDJVO.getPk_account());
			vo.setDirection(DJZBVOConsts.FX_DF);
		} else {
			// 正数-收款
			// 收款原币
			vo.setRec_primal(agioVO.getCalCe().getYb().abs());
			// 收款组织本币金额
			vo.setRec_local(agioVO.getCalCe().getBb().abs());
			// 收款集团本币金额
			vo.setGroup_local_rec(TMCurrencyUtil.getGroupLocalMoney(vo.getPk_group(), vo.getPk_org(), vo
					.getPk_currtype(), vo.getRec_primal(), null, null, new UFDate()));
			// 收款全局本币金额
			vo.setGlobal_local_rec(TMCurrencyUtil.getGlobalLocalMoney(vo.getPk_org(), vo.getPk_currtype(), vo
					.getRec_primal(), null, null, new UFDate()));

			vo.setPk_account(agiotageDJVO.getPk_account());
			vo.setDirection(DJZBVOConsts.FX_JF);
		}
		vo.setMon_account(agiotageDJVO.getPk_manageAccount());

		OrgVO orgVO = NCLocator.getInstance().lookup(IOrgUnitQryService.class).getOrg(vo.getPk_org());
		vo.setPk_org_v(orgVO.getPk_vid());

		// 判断当前组织是否利润中心
		boolean isLiaCenterType = OrgTypeManager.getInstance().isTypeOf(orgVO, IOrgConst.LIACENTERTYPE);

		if (isLiaCenterType) {
			vo.setPk_pcorg(vo.getPk_org());
			vo.setPk_pcorg_v(orgVO.getPk_vid());
		}

		return vo;
	}

	/**
	 * 主要功能：过滤符合条件的损益历史纪录 主要算法： 异常描述： 创建日期：(2001-8-10 14:31:15)
	 * 最后修改日期：(2001-8-10 14:31:15)
	 * 
	 * @author：wyan
	 * @return java.util.Vector
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	@SuppressWarnings("unchecked")
	public Vector<Vector<Comparable>> getHisRecord(CMPAgiotageVO vo, Hashtable hBzDig) throws Exception {
		// added by zhufeng 2013-8-6 币种名称需要取多语 start
		String currSqlStr = "bz.name";
		Integer defaultLangSeq = MultiLangContext.getInstance().getCurrentLangSeq();
		if (defaultLangSeq != 1){ 
			currSqlStr = currSqlStr + defaultLangSeq.intValue();
		}
		// added by zhufeng 2013-8-6  end
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct aa.pk_accountagiotage, aa.dbilldate, oper.user_name, " +
				currSqlStr +
				", ");
		sql.append("aa.vbillno, aa.receipt_localmoney,aa.pay_localmoney ");
		// sql.append(", zhda.pk_account ");
		sql.append("from cmp_initdata zhda , bd_currtype bz ,cmp_accountagiotage aa, sm_user oper ");
		sql.append("where bz.pk_currtype = zhda.pk_currtype and aa.pk_org = zhda.pk_org ");
		sql.append("and oper.cuserid = aa.creator ");
		sql.append("and isnull(zhda.approvedate,'~')<>'~' ");
		sql.append("and aa.pk_curr = bz.pk_currtype and aa.dr = 0 ");
		sql.append("and zhda.pk_org = '");
		sql.append(NCESAPI.sqlEncode(vo.getDwbm())).append("' ");
		sql.append(vo.getCurrency() == null ? "" : " and zhda.pk_currtype = '" + NCESAPI.sqlEncode(vo.getCurrency()) + "' ");
		sql.append(vo.getDateBeg() == null ? "" : " and aa.dbilldate >='" + NCESAPI.sqlEncode(vo.getDateBeg()) + "' ");
		sql.append(vo.getDateEnd() == null ? "" : " and aa.dbilldate <='" + NCESAPI.sqlEncode(vo.getDateEnd()) + "' ");
		sql.append(vo.getDjbhBeg() == null ? "" : " and aa.vbillno >='" + NCESAPI.sqlEncode(vo.getDjbhBeg()) + "' ");
		sql.append(vo.getDjbhEnd() == null ? "" : " and aa.vbillno <='" + NCESAPI.sqlEncode(vo.getDjbhEnd()) + "' ");
		sql.append(vo.getUser() == null ? "" : " and aa.creator ='" + NCESAPI.sqlEncode(vo.getUser()) + "' ");
		sql
				.append("and (aa.pk_bankaccount = zhda.pk_account or aa.pk_cashaccount = zhda.pk_account)");

		Vector<Vector<Comparable>> vData = new Vector<Vector<Comparable>>();
		Connection con = null;
		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;

		try {

			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			vData = onChangeToVO(rs, hBzDig, vo.getDwbm());

		} catch (Exception e) {
			ExceptionHandler.consume(e);
			throw new BusinessException(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (con != null) {
					con.close();
				}
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}

		return vData;
	}

	/**
	 * 主要功能：取得汇兑损益重币种选择界面的初始化数据。 主要算法： 异常描述： 创建日期：(2001-8-7 20:36:02)
	 * 最后修改日期：(2001-8-7 20:36:02)
	 * 
	 * @author：wyan
	 * @return nc.vo.arap.agiotage.AgiotageVO[]
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	@SuppressWarnings("unchecked")
	public Vector<Comparable> getInitCurrTypeInfo(CMPAgiotageVO vo) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct zhda.pk_currtype,bz.name ");
		sql.append(" from cmp_initdata zhda ");
		sql.append(" inner join bd_currtype bz on bz.pk_currtype = zhda.pk_currtype and bz.dr = 0 ");
		sql.append(" where zhda.pk_org = '");
		sql.append(vo.getDwbm());
		sql.append("' and zhda.pk_currtype = '");
		sql.append(vo.getBzbm());
		sql.append("'");
		sql.append(" and isnull(zhda.approvedate,'1')<>'1' ");
		Vector<Comparable> vdata = new Vector<Comparable>();
		// modified by zhufeng 2013-4-17 规避安全检查 start
//		Connection con = null;
//		PreparedStatement stmt = null;
//		PersistenceManager persistenceManager = null;
//
//		try {
//
//			persistenceManager = PersistenceManager.getInstance();
//			con = persistenceManager.getJdbcSession().getConnection();
//			stmt = con.prepareStatement(sql.toString());
//			ResultSet rs = stmt.executeQuery();
//
//			while (rs.next()) {
//
//				vdata = new Vector<Comparable>();
//				String bzbm = rs.getString(1);
//				vdata.addElement(bzbm != null ? bzbm.trim() : null);
//				String bzmc = rs.getString(2);
//				vdata.addElement(bzmc != null ? bzmc.trim() : null);
//				String auditdate = "";
//				vdata.addElement(auditdate);
//				String manageAcc = "";
//				vdata.addElement(manageAcc);
//				String acc = "";
//				vdata.addElement(acc);
//				Boolean xzbz = new Boolean(false);
//				vdata.addElement(xzbz);
//			}
//
//		} finally {
//			try {
//				if (stmt != null) {
//					stmt.close();
//				}
//			} catch (Exception e) {
//			}
//			try {
//				if (con != null) {
//					con.close();
//				}
//				if (persistenceManager != null) {
//					persistenceManager.release();
//				}
//			} catch (Exception e) {
//			}
//		}
		ArrayList<Object[]> resultList =  (ArrayList<Object[]>) ContrastUtils.getBasedDAO().executeQuery(sql.toString(), new ArrayListProcessor()) ;
		for (Object[] colArr : resultList) {
			vdata = new Vector<Comparable>();
			String bzbm = (String) colArr[0];
			vdata.addElement(bzbm != null ? bzbm.trim() : null);
			String bzmc = (String) colArr[1];
			vdata.addElement(bzmc != null ? bzmc.trim() : null);
			String auditdate = "";
			vdata.addElement(auditdate);
			String manageAcc = "";
			vdata.addElement(manageAcc);
			String acc = "";
			vdata.addElement(acc);
			Boolean xzbz = new Boolean(false);
			vdata.addElement(xzbz);
		}
		// modified by zhufeng 2013-4-17 end

		return vdata;
	}

	/**
	 * 从划账核算单中取上次计算期间
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author jiaweib
	 * @since NC6.0
	 */
	public UFDate getComputeDate(CMPAgiotageVO vo) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT max(coalesce(zb.billmaker_date,zb.billmaker_date,'')) djrq  ");
		sql.append(" FROM cmp_changebill zb  ");
		sql.append(" INNER JOIN cmp_changebilldetail fb ON zb.pk_changebill=fb.pk_changebill ");
		sql.append(" WHERE zb.pk_org='");
		sql.append(NCESAPI.sqlEncode(vo.getDwbm()));
		sql.append("' and fb.pk_currtype= '");
		sql.append(NCESAPI.sqlEncode(vo.getBzbm()));
		sql.append("'");
		sql.append(" and zb.effect_flag>0  ");
		sql.append(" and zb.trade_type='DR'  ");
		sql.append(" and zb.dr=0 ");
		UFDate date = null;
		Connection con = null;
		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String computeDate = rs.getString(1);
				date = (computeDate != null && computeDate.trim().length() != 0 ? new UFDate(computeDate.trim()) : null);
			}

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (con != null) {
					con.close();
				}
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}

		return date;
	}

	/**
	 * 期初表中取数,where 条件 key--pk_account,value-Vector<String> Vector序列( pk_account
	 * ,pk_mngaccount,auditing_date,realtime_primal,realtime_local) *
	 */
	private Hashtable<String, Vector<String>> getAccOpenDate(String[] pk_commonAccids, String dbField, String pk_org)
			throws Exception {
		Hashtable<String, Vector<String>> result = new Hashtable<String, Vector<String>>();
		if (pk_commonAccids == null || pk_commonAccids.length == 0) {
			return result;
		}

		String sql = "select pk_account ,approvedate,realtime_primal,realtime_local from cmp_initdata where ";
		if ("pk_account".equalsIgnoreCase(dbField)) {
			sql += "pk_account in (";
		}
		for (int i = 0; i < pk_commonAccids.length; i++) {
			if (i == pk_commonAccids.length - 1) {
				sql = sql + "'" + NCESAPI.sqlEncode(pk_commonAccids[i]) + "')";
			} else {
				sql = sql + "'" + NCESAPI.sqlEncode(pk_commonAccids[i]) + "',";
			}
		}
		sql += " and isnull(approvedate,'1')<>'1' ";
		sql += " and pk_org='" + NCESAPI.sqlEncode(pk_org) + "' ";

		PreparedStatement stmt = null;
		PersistenceManager persistenceManager = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			persistenceManager = PersistenceManager.getInstance();
			con = persistenceManager.getJdbcSession().getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String pk_account = rs.getString(1);
				String pk_mngaccount = rs.getString(1);
				String auditing_date = rs.getString(2);
				String realtime_primal = rs.getString(3);
				String realtime_local = rs.getString(4);
				Vector<String> temp = new Vector<String>();
				temp.add(pk_account);
				temp.add(pk_mngaccount);
				temp.add(auditing_date);
				temp.add(realtime_primal);
				temp.add(realtime_local);
				result.put(pk_account, temp);
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
				if (persistenceManager != null) {
					persistenceManager.release();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	// 校验计算日期是否在期初余额复核日期之前
	@SuppressWarnings("unchecked")
	private void validateAuditDate(CMPAgiotageVO agioVO) throws Exception {
		// 得到银行帐号的pks
		Hashtable pkAccidsHash = agioVO.getPkAccids(); /* 币种对应银行账户Pks */
		// 币种对应管理账户Pks */
		List<String> pk_accountList = CmpAgiotageBusiUtils.getValueListByStringTOStringArrayMap(pkAccidsHash);
		String[] accidPks = new String[pk_accountList.size()];
		pk_accountList.toArray(accidPks);

		Hashtable<String, Vector<String>> date = this.getAccOpenDate(accidPks, "pk_account", agioVO.getDwbm());

		// 校验计算日期是否在期初余额复核日期之前
		Set<String> key = date.keySet();
		Iterator<String> it1 = key.iterator();
		while (it1.hasNext()) {
			String tempAccid = it1.next();
			Vector<String> validateVec = date.get(tempAccid);
			UFDate d = new UFDate((String) validateVec.get(2));// 期初余额的复核日期
			if (new UFDate(agioVO.getDateEnd()).before(d)) {
				throw ExceptionHandler.createException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"3607agi_0", "03607agi-0040")/* @res "计算日期早于期初余额的复核日期" */);
			}
		}
	}

	/**
	 * 将一个币种对应多个账户的hashtable转化为一个账户对应一个币种的map
	 * 
	 * @param agioVO
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getAccountToBzbmMap(CMPAgiotageVO agioVO) {
		Hashtable pkAccidsHash = agioVO.getPkAccids(); /* 币种对应银行账户Pks */
		Hashtable pkManageAccidesHash = agioVO.getPkManageAccids();/* 币种对应管理账户Pks */
		Map<String, String> accountToBzbmMap = CmpAgiotageBusiUtils
				.getPk_AccountTOBzbmMapByBzbmToPk_accountHashTable(pkAccidsHash);
		Map<String, String> pk_mngaccountToBzbmMap = CmpAgiotageBusiUtils
				.getPk_AccountTOBzbmMapByBzbmToPk_accountHashTable(pkManageAccidesHash);
		accountToBzbmMap.putAll(pk_mngaccountToBzbmMap);
		return accountToBzbmMap;
	}

	private Map<String, Vector<CMPAgiotageDJVO>> getBzbmTOAgiotageDJVOVecMapByAgiotageDJVOList(
			List<CMPAgiotageDJVO> accountResult, Map<String, String> accountToBzbmMap) {
		Map<String, Vector<CMPAgiotageDJVO>> bzbmTOAgiotageDJVOVecMap = new HashMap<String, Vector<CMPAgiotageDJVO>>();
		Vector<CMPAgiotageDJVO> tempAgioDJVOVec = null;
		for (CMPAgiotageDJVO agiotageDJVO : accountResult) {
			String bzbm = null;
			if (agiotageDJVO.getPk_manageAccount() != null) {
				bzbm = accountToBzbmMap.get(agiotageDJVO.getPk_manageAccount());
			} else {
				bzbm = accountToBzbmMap.get(agiotageDJVO.getPk_account());
			}

			tempAgioDJVOVec = bzbmTOAgiotageDJVOVecMap.get(bzbm);
			if (tempAgioDJVOVec == null) {
				tempAgioDJVOVec = new Vector<CMPAgiotageDJVO>();
				bzbmTOAgiotageDJVOVecMap.put(bzbm, tempAgioDJVOVec);
			}
			tempAgioDJVOVec.add(agiotageDJVO);
		}
		return bzbmTOAgiotageDJVOVecMap;
	}

	/**
	 * 汇兑损益计算并生成汇兑损益单
	 * @param agioVO
	 * @return
	 * @throws Exception 
	 */
	public Object[] generateAccountAgiotageVO(CMPAgiotageVO agioVO) throws Exception {
		Object[] oRs = new Object[2];
		ArrayList<CMPAgiotageBzVO> voMsgs = new ArrayList<CMPAgiotageBzVO>();
		List<AccountAgiotageAggVO> aggVOList = new ArrayList<AccountAgiotageAggVO>();
		
		/*Singapore汇兑损益需求  增加参数生成确认汇兑损益单以及下月是否自动回冲*/
		UFBoolean isConfirmExchange = SysInit.getParaBoolean(agioVO.getDwbm(), "CMP94");
		UFBoolean isNextMonthRedBack = SysInit.getParaBoolean(agioVO.getDwbm(), "CMP95");
		
		// 校验
		validateAuditDate(agioVO);

		// 得到所有的结果集，只查一次数据库
		List<CMPAgiotageDJVO> accountResult = this.getAccountResult(agioVO);
		// 将一个币种对应多个账户的hashtable转化为一个账户对应一个币种的map
		Map<String, String> accountToBzbmMap = this.getAccountToBzbmMap(agioVO);
		// 转化为币种对应Vector<CMPAgiotageDJVO>
		Map<String, Vector<CMPAgiotageDJVO>> bzbmTOAgiotageDJVOVecMap = this
				.getBzbmTOAgiotageDJVOVecMapByAgiotageDJVOList(accountResult, accountToBzbmMap);
		
		// 根据币种循环,按照账户生成汇兑损益单
		for (int i = 0; i < agioVO.getSelBzbm().size(); i++) {
			/* 此处要检查汇率是否正确(包括主辅币核算是否有辅币汇率，汇率是否合法等) */
			CMPAgiotageBzVO bzvo = (CMPAgiotageBzVO) agioVO.getSelBzbm().elementAt(i);
			CMPAgiotageBzVO voMsg = new CMPAgiotageBzVO();
			voMsg.setBzmc(bzvo.getBzmc());
			String bzbm = bzvo.getBzbm();
			if (!bzvo.getState()) {
				voMsg.setMsg("Currency Error");
				voMsg.setState(true);
				voMsg.setCurrErrMsg(bzvo.getCurrErrMsg());
				voMsgs.add(voMsg);
				continue;
			}
			Vector<CMPAgiotageDJVO> djVOVec = bzbmTOAgiotageDJVOVecMap.get(bzbm);
			/* 判断选中币种是否存在需要计算损益账户 */
			if (djVOVec == null || djVOVec.size() == 0) {
				voMsg.setMsg("None");
				voMsg.setState(true);
				voMsgs.add(voMsg);
				continue;
			}
			for (int k = 0; k < djVOVec.size(); k++) {
				CMPAgiotageDJVO agioDJVO = djVOVec.elementAt(k);
				String pk_bank = agioDJVO.getPk_account();
				String pk_cashid = agioDJVO.getPk_manageAccount();
				UFDouble yb = agioDJVO.getYbye();
				UFDouble bb = agioDJVO.getBbye();
				CMPJe je = new CMPJe(bzvo.getBzbm(), yb, null, bb);
				UFDouble bbye = null;//bb == null ? new UFDouble(0) : new UFDouble(bb);
				/* 账户实时余额原币乘或除调整汇率-账户实时余额本币
				 * 格式化汇率变化后的余额以便和汇率变化前进行比较(变化前现在默认为2位小数) */
				//bbye = bbye.setScale(bzvo.getBbDig(), UFDouble.ROUND_HALF_UP);
				CMPJe ye = new CMPJe(bzvo.getBzbm(), yb, null, bbye);
				CMPJe ce = je.subtract(ye);
//				if (ce.isAllZero()) {
//					voMsg.setBzmc(bzvo.getBzmc());
//					voMsg.setMsg("No Ce");
//					voMsg.setState(true);
//					voMsgs.add(voMsg);
//					continue;
//				}
				//生成汇兑损益单
				CMPAgiotageVO tempVO = agioVO;
				tempVO.setCalCe(ce);
				tempVO.setBzbm(bzbm);
				
				if(null!=pk_bank&&!"".equals(pk_bank) && UFBoolean.TRUE.equals(isConfirmExchange) && yb.doubleValue() == 0) {
					Hashtable<String, String> bank = new Hashtable<String, String>();
					bank.put(bzbm, pk_bank);
					tempVO.setPkAccids(bank);
					tempVO.setPkManageAccids(null);
					tempVO.setMode(1); //确认已实现为1
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
				}
				else if(null!=pk_bank&&!"".equals(pk_bank) && yb.doubleValue() != 0 && UFBoolean.TRUE.equals(isNextMonthRedBack)){
					Hashtable<String, String> bank = new Hashtable<String, String>();
					bank.put(bzbm, pk_bank);
					tempVO.setPkAccids(bank);
					tempVO.setPkManageAccids(null);
					tempVO.setMode(2); //未确认已实现
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
					CMPAgiotageVO copyRedBackVO = (CMPAgiotageVO) tempVO.clone();
					setCopyRedVO(copyRedBackVO); //生成新的回冲VO
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(copyRedBackVO));
				}
				else if(null!=pk_bank&&!"".equals(pk_bank)){ 
					Hashtable<String, String> bank = new Hashtable<String, String>();
					bank.put(bzbm, pk_bank);
					tempVO.setPkAccids(bank);
					tempVO.setPkManageAccids(null);
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
				}
				
				if(null!=pk_cashid&&!"".equals(pk_cashid)&& UFBoolean.TRUE.equals(isConfirmExchange) && yb.doubleValue() == 0) {
					Hashtable<String, String> cash = new Hashtable<String, String>();
					cash.put(bzbm, pk_cashid);
					tempVO.setPkManageAccids(cash);
					tempVO.setPkAccids(null);
					tempVO.setMode(1); //确认已实现为1
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
				}
				else if(null!=pk_cashid&&!"".equals(pk_cashid)&& yb.doubleValue() != 0 && UFBoolean.TRUE.equals(isNextMonthRedBack)) {
					Hashtable<String, String> cash = new Hashtable<String, String>();
					cash.put(bzbm, pk_cashid);
					tempVO.setPkManageAccids(cash);
					tempVO.setPkAccids(null);
					tempVO.setMode(2); //未确认已实现
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
					CMPAgiotageVO copyRedBackVO = (CMPAgiotageVO) tempVO.clone();
					setCopyRedVO(copyRedBackVO); //生成新的回冲VO
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(copyRedBackVO));
				}
				else if(null!=pk_cashid&&!"".equals(pk_cashid)){
					Hashtable<String, String> cash = new Hashtable<String, String>();
					cash.put(bzbm, pk_cashid);
					tempVO.setPkManageAccids(cash);
					tempVO.setPkAccids(null);
					aggVOList.add(AccountAgiotageUtil.getInstance().changeVO(tempVO));
				}
				//如果原币不为零,这里强制设置为零
				if(agioDJVO.getYbye().doubleValue() != 0) {
					agioDJVO.setYbye(new UFDouble(0));
				}
			}
		}
		oRs[0] = aggVOList;//djzbvoVec;
		oRs[1] = voMsgs;

		return oRs;
	}
	/**
	 * 
	 * @param copyRedBackVO
	 * @throws InvalidAccperiodExcetion 
	 */
	private void setCopyRedVO(CMPAgiotageVO copyRedBackVO) throws InvalidAccperiodExcetion {
		AccountCalendar acc = null;
		acc = AccountCalendar.getInstanceByPk_org(copyRedBackVO.getDwbm());
		//设置为下个期间
		acc.setDate(new UFDate(copyRedBackVO.getDateBeg()));//期间开始日期
		acc.roll(AccountCalendar.MONTH, 1);
		
		copyRedBackVO.setQjBeg(acc.getMonthVO().getBegindate().toString());
		copyRedBackVO.setQjEnd(acc.getMonthVO().getEnddate().toString());
		copyRedBackVO.setCalQj(acc.getMonthVO().getAccperiodmth());
		copyRedBackVO.setDateBeg(acc.getMonthVO().getBegindate().toString());
		copyRedBackVO.setDateEnd(acc.getMonthVO().getEnddate().toString());
		copyRedBackVO.setMode(3); //回冲标志
		
		//回冲单据需要拿本币判断，这里取本币的负数
		UFDouble bb = copyRedBackVO.getCalCe().getBb();
		UFDouble redbb = bb.multiply(Double.valueOf(-1));
		copyRedBackVO.getCalCe().setBb(redbb);
	}

	/**
	 * 主要功能：进行汇兑损益的后台处理。 主要算法： 异常描述： 创建日期：(2001-8-8 15:05:11) 最后修改日期：(2001-8-8
	 * 15:05:11)
	 * 
	 * @author：wyan
	 * @return java.util.Vector
	 * @param agioVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public Object[] onCalculate(CMPAgiotageVO agioVO) throws Exception {

		Object[] oRs = new Object[3];
		Vector<ChangeBillAggVO> djzbvoVec = new Vector<ChangeBillAggVO>();
		ArrayList<CMPAgiotageBzVO> voMsgs = new ArrayList<CMPAgiotageBzVO>();

		// 校验
		validateAuditDate(agioVO);

		// 得到所有的结果集，只查一次数据库
		List<CMPAgiotageDJVO> accountResult = this.getAccountResult(agioVO);
		// 将一个币种对应多个账户的hashtable转化为一个账户对应一个币种的map
		Map<String, String> accountToBzbmMap = this.getAccountToBzbmMap(agioVO);
		// 转化为币种对应Vector<CMPAgiotageDJVO>
		Map<String, Vector<CMPAgiotageDJVO>> bzbmTOAgiotageDJVOVecMap = this
				.getBzbmTOAgiotageDJVOVecMapByAgiotageDJVOList(accountResult, accountToBzbmMap);

		// 根据币种循环,按照账户生成汇兑损益单
		for (int i = 0; i < agioVO.getSelBzbm().size(); i++) {

			/* 此处要检查汇率是否正确(包括主辅币核算是否有辅币汇率，汇率是否合法等) */
			CMPAgiotageBzVO bzvo = (CMPAgiotageBzVO) agioVO.getSelBzbm().elementAt(i);
			CMPAgiotageBzVO voMsg = new CMPAgiotageBzVO();
			voMsg.setBzmc(bzvo.getBzmc());

			if (!bzvo.getState()) {
				voMsg.setMsg("Currency Error");
				voMsg.setState(true);
				voMsg.setCurrErrMsg(bzvo.getCurrErrMsg());
				voMsgs.add(voMsg);
				continue;
			}
			Vector<CMPAgiotageDJVO> djVOVec = bzbmTOAgiotageDJVOVecMap.get(bzvo.getBzbm());
			/* 判断选中币种是否存在需要计算损益账户 */
			if (djVOVec == null || djVOVec.size() == 0) {
				voMsg.setMsg("None");
				voMsg.setState(true);
				voMsgs.add(voMsg);
				continue;
			}

			/* 检查账户是否存在损益,并得到单据表体 */
			Object[] oData = onCheckAccountDoc(bzvo, djVOVec, agioVO);
			CMPJe ItemsCe = (CMPJe) oData[0];
			/* 选择账户有差额的现象 */
			if (ItemsCe.isAllZero()) {
				voMsg.setBzmc(bzvo.getBzmc());
				voMsg.setMsg("No Ce");
				voMsg.setState(true);
				voMsgs.add(voMsg);
				continue;
			}
			ChangeBillDetailVO[] voItems = (ChangeBillDetailVO[]) oData[1];
			agioVO.setCalCe(ItemsCe);

			ChangeBillAggVO vo = new ChangeBillAggVO();
			vo.setParent(getDJHeaderVO(agioVO));
			vo.setChildrenVO(voItems);

			djzbvoVec.addElement(vo);
		}

		oRs[0] = djzbvoVec;
		oRs[1] = voMsgs;
		//huangzhen modify for accountagiotage
		oRs[2] = bzbmTOAgiotageDJVOVecMap;

		return oRs;
	}

	/**
	 * 取消账户汇兑损益记录。 创建日期：(2003-9-29 16:00:29)
	 * 
	 * @param vData
	 *            java.util.Vector
	 */
	public void onCancelOperation(String whereCond) throws Exception {

//		PreparedStatement stmt = null;
//		Connection con = null;
//		PersistenceManager persistenceManager = null;
//
//		try {
//			persistenceManager = PersistenceManager.getInstance();
//			con = persistenceManager.getJdbcSession().getConnection();
//
//			/* 根据Vouchid删除选中的账户汇兑损益记录 */
//			String sql = "update arap_djzb set dr = 1 where vouchid in (" + whereCond + ")";
//			stmt = con.prepareStatement(sql);
//			stmt.executeUpdate();
//
//			sql = "update arap_djfb set dr = 1 where vouchid in (" + whereCond + ")";
//			stmt = con.prepareStatement(sql);
//			stmt.executeUpdate();
//
//			sql = "update arap_djfkxyb set dr = 1 where vouchid in (" + whereCond + ")";
//			stmt = con.prepareStatement(sql);
//			stmt.executeUpdate();
//
//		} catch (Exception ex) {
//			throw ExceptionHandler.handleException(this.getClass(), ex);
//		} finally {
//			try {
//				if (stmt != null){
//					try {
//						stmt.close();
//					} catch (Exception e) {
//					}
//				}
//			} catch (Exception e) {
//			}
//			try {
//				if (con != null){
//					try {
//						con.close();
//					} catch (Exception e) {
//					}
//				}
//					
//				if (persistenceManager != null) {
//					persistenceManager.release();
//				}
//			} catch (Exception e) {
//			}
//		}
	}

	/**
	 * 主要功能：将resultset转化成为vo或其他形式 标志区分取消损益过滤和损益报告。 主要算法： 异常描述： 创建日期：(2001-8-10
	 * 15:37:21) 最后修改日期：(2001-8-10 15:37:21)
	 * 
	 * @author：wyan
	 * @return java.util.Vector
	 * @param rs
	 *            java.sql.ResultSet
	 * @param sign
	 *            java.lang.String
	 */
	@SuppressWarnings("unchecked")
	public Vector<Vector<Comparable>> onChangeToVO(ResultSet rs, Hashtable hBzDig, String pk_org) throws SQLException {

		Vector<Vector<Comparable>> vResult = new Vector<Vector<Comparable>>();
		try {
			//取组织本币精度
			int digit = TMCurrencyUtil.getCurrtypeDigit(TMCurrencyUtil.getOrgLocalCurrPK(pk_org));
			while (rs.next()) {
				Vector<Comparable> vData = new Vector<Comparable>();
				// id :
				String vouchid = rs.getString(1);
				vData.addElement(vouchid == null ? null : vouchid.trim());
				// 选中标志 :
				Boolean xzbz = new Boolean(false);
				vData.addElement(xzbz);
				// 期间 :
				String djrq = rs.getString(2);
				//如果为红冲的账户汇兑损益单,损益记录不显示 add by weiningc  20171205 start
				Boolean isRedBack = CmpAgiotageBusiUtils.isBackRedBill(djrq == null ? null : new UFDate(djrq.trim()),
						pk_org);
				if(isRedBack) {
					continue;  //暂时注释掉,测试用
				} //end
				
				String kjqj = CmpAgiotageBusiUtils.converDateToKJQJ(djrq == null ? null : new UFDate(djrq.trim()),
						pk_org);
				vData.addElement(kjqj);
				// 处理人
				String lrr = rs.getString(3);
				vData.addElement(lrr == null ? null : lrr.trim());
				// 币种编码
				String bzmc = rs.getString(4);
				vData.addElement(bzmc);
				CMPAgiotageBzVO bzvo = (CMPAgiotageBzVO) hBzDig.get(bzmc);

				// 单据编号 :
				String djbh = rs.getString(5);
				vData.addElement(djbh == null ? null : djbh.trim());
				
				BigDecimal receiveLocal = rs.getBigDecimal(6);
				UFDouble receiveLocalUFD = receiveLocal == null ? new UFDouble(0) : new UFDouble(receiveLocal
						.doubleValue(), 2);
				BigDecimal payLocal = rs.getBigDecimal(7);
				UFDouble bbjeUFD = new UFDouble(0, digit);
				UFDouble payLocalUFD = payLocal == null ? new UFDouble(0) : new UFDouble(payLocal.doubleValue(),digit);
				if (new UFDouble(0).equals(receiveLocalUFD) && !new UFDouble(0).equals(payLocalUFD)) {
					payLocalUFD = payLocalUFD.setScale(digit,UFDouble.ROUND_HALF_UP);
					bbjeUFD = payLocalUFD.multiply(new UFDouble(-1, digit),digit);
				} else if (!new UFDouble(0).equals(receiveLocalUFD) && new UFDouble(0).equals(payLocalUFD)) {
					receiveLocalUFD = receiveLocalUFD.setScale(digit,UFDouble.ROUND_HALF_UP);
					bbjeUFD = receiveLocalUFD;
				}
				vData.addElement(bbjeUFD);

				// String pk_account = rs.getString(8);
				// vData.addElement(pk_account == null ? "" : pk_account);
				vResult.addElement(vData);
			}
		} catch (Exception e) {
			ExceptionHandler.consume(e);
			throw new SQLException(e.getMessage());
		}

		// return filterRepeatData(vResult);
		return vResult;
	}

	// 对于存在管理帐户的情况，以管理帐户为准进行过滤
	@SuppressWarnings("unchecked")
	private Vector<Vector<Comparable>> filterRepeatData(Vector<Vector<Comparable>> vResult) {
		Vector<Comparable> flagVec = null;
		Vector<Comparable> tempVec = null;
		String vouchid = null;
		String pk_mngaccount = null;
		Map<String, Vector<Comparable>> vouchidMap = new HashMap<String, Vector<Comparable>>();
		for (int i = 0; i < vResult.size(); i++) {
			flagVec = vResult.get(i);
			vouchid = (String) flagVec.get(0);
			if (vouchidMap.containsKey(vouchid)) {
				(vouchidMap.get(vouchid)).set(7, ((UFDouble) vouchidMap.get(vouchid).get(7)).add((UFDouble) vResult
						.get(i).get(7)));
				(vouchidMap.get(vouchid)).set(6, ((UFDouble) vouchidMap.get(vouchid).get(6)).add((UFDouble) vResult
						.get(i).get(6)));
				vResult.remove(i);
				i = i - 1;
				continue;
			} else {
				vouchidMap.put(vouchid, flagVec);
			}
			pk_mngaccount = (String) flagVec.get(flagVec.size() - 1);
			for (int j = i + 1; j < vResult.size(); j++) {
				tempVec = vResult.get(j);
				if (vouchid.equals((String) tempVec.get(0))) {
					if ("".equalsIgnoreCase(pk_mngaccount)
							&& !"".equalsIgnoreCase((String) tempVec.get(tempVec.size() - 1))) {
						(vResult.get(j)).set(7, ((UFDouble) vResult.get(j).get(7))
								.add((UFDouble) vResult.get(i).get(7)));
						(vResult.get(j)).set(6, ((UFDouble) vResult.get(j).get(6))
								.add((UFDouble) vResult.get(i).get(6)));
						vResult.remove(i);
						vouchidMap.remove(vouchid);
						i = i == 0 ? 0 : i - 1;
						j = vResult.size();
					} else if (!"".equalsIgnoreCase(pk_mngaccount)
							&& "".equalsIgnoreCase((String) tempVec.get(tempVec.size() - 1))) {
						(vResult.get(i)).set(7, ((UFDouble) vResult.get(i).get(7))
								.add((UFDouble) vResult.get(j).get(7)));
						(vResult.get(i)).set(6, ((UFDouble) vResult.get(i).get(6))
								.add((UFDouble) vResult.get(j).get(6)));
						vResult.remove(j);
						j = vResult.size();
					}
				}
			}
		}
		return vResult;
	}

	/**
	 * 检查账户是否存在损益,并得到单据表体
	 * 
	 * @param voBz
	 * @param agioDJVOVec
	 * @param dwbm
	 * @return
	 * @throws Exception
	 * @author jiaweib
	 * @since NC6.0
	 */
	private Object[] onCheckAccountDoc(CMPAgiotageBzVO voBz, Vector<CMPAgiotageDJVO> agioDJVOVec, CMPAgiotageVO vo)
			throws Exception {

		Object[] oData = new Object[2];
		ChangeBillDetailVO[] voItems = null;
		Vector<ChangeBillDetailVO> vItems = new Vector<ChangeBillDetailVO>();
		CMPJe sumCe = new CMPJe(voBz.getBzbm());
		for (int k = 0; k < agioDJVOVec.size(); k++) {

			CMPAgiotageDJVO agioDJVO = agioDJVOVec.elementAt(k);
			String pk_initdata = agioDJVO.getVouchid();
			UFDouble yb = agioDJVO.getYbye();
			UFDouble bb = agioDJVO.getBbye();
			CMPJe je = new CMPJe(voBz.getBzbm(), yb, null, bb);
			UFDouble bbye = null;
			//bb == null ? new UFDouble(0) : new UFDouble(bb);
			/* 账户实时余额原币乘或除调整汇率-账户实时余额本币
			 * 格式化汇率变化后的余额以便和汇率变化前进行比较(变化前现在默认为2位小数) */
			//bbye = bbye.setScale(bzvo.getBbDig(), UFDouble.ROUND_HALF_UP);

			CMPJe ye = new CMPJe(voBz.getBzbm(), yb, null, bbye);
			CMPJe ce = je.subtract(ye);
			if (ce.isAllZero()) {
				continue;
			}
			sumCe = sumCe.add(ce);
			CMPAgiotageVO voData = new CMPAgiotageVO();
			voData.setDwbm(vo.getDwbm());
			voData.setBzbm(voBz.getBzbm());
			voData.setCalCe(ce);
			voData.setClbh(pk_initdata);
			voData.setPk_group(vo.getPk_group());
			voData.setUser(vo.getUser());
			voData.setDateEnd(vo.getDateEnd());

			ChangeBillDetailVO voItem = getDJItemVO(voData, agioDJVO);
			if (!(new UFDouble(0.00).equals(voItem.getRec_local()) && new UFDouble(0.00).equals(voItem.getPay_local()))) {
				vItems.addElement(voItem);
			}
			voItem.setLocal_rate(voBz.getBbhl());
		}
		voItems = new ChangeBillDetailVO[vItems.size()];
		if (vItems.size() > 0) {
			vItems.copyInto(voItems);
		}

		oData[0] = sumCe;
		oData[1] = voItems;
		return oData;
	}
}