package nc.bs.fipub.timecontrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import javax.naming.NamingException;

import nc.bs.framework.common.NCLocator;
import nc.bs.mw.sqltrans.TempTable;
import nc.bs.pub.DataManageObject;
import nc.itf.fipub.timecontrol.ITimeControlQueryService;
import nc.jdbc.framework.crossdb.CrossDBConnection;
import nc.jdbc.framework.util.DBConsts;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.fipub.timecontrol.TimeCtrlDetail;
import nc.vo.fipub.timecontrol.TimeCtrlVO;
import nc.vo.fipub.timecontrol.TimeUnit;
import nc.vo.fipub.timecontrol.UnitDays;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author CongJinliang
 *
 * @since 2010-11-27
 *
 */
public class TimeControlUnitDMO extends DataManageObject {

	public TimeControlUnitDMO() throws NamingException {
		super();
	}

	public TimeControlUnitDMO(String dbName) throws NamingException {
		super(dbName);
	}

	/**
	 * 应收账龄临时表
	 *
	 * @author CongJinliang
	 * @since 2010-11-25
	 * @param timeCtrlPk 账龄方案主键
	 * @param baseDate 基准日期
	 * @return String 临时表
	 * @throws BusinessException
	 */
	public String createTimeCtrlTmpTable(String timeCtrlPk, UFDate baseDate) throws BusinessException {
		if (StringUtils.isEmpty(timeCtrlPk)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("201008v6_0","0201008v6-0048")/*@res "创建临时表出错：账龄方案为空！"*/);
		}

		Connection conn = null;
		try {
			conn = getConnection();
			((CrossDBConnection) conn).setSqlTrans(true);
			((CrossDBConnection) conn).setAddTimeStamp(false);

			String tableName = createTimeCtrlTmpTable(conn);

			initTimeCtrlData(conn, tableName, baseDate, timeCtrlPk);

			return tableName;
		} catch (Exception e) {
			throw ExceptionHandler.handleException(e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 实际创建账龄方案临时表，生成数据库表<br>
	 *
	 * @param con 数据库连接<br>
	 * @return String 临时表名<br>
	 * @throws SQLException<br>
	 */
	private String createTimeCtrlTmpTable(Connection con) throws SQLException {
		String oid = "";
		String tempTableName = null;

		if (((CrossDBConnection) con).getDatabaseType() == DBConsts.ORACLE) {
			tempTableName = "fipubtmp_timectrl";
		} else {
			tempTableName = "fipubtmp_timectrl" + oid;
		}

		String createTableSQL = "PROPERTYID integer, DESCR varchar(50), STARTUNIT integer, ENDUNIT integer, STARTDATE varchar(19), ENDDATE varchar(19), STARTVALUE integer, ENDVALUE integer";
		return new TempTable().createTempTable(con, tempTableName, createTableSQL, "");
	}

	/**
	 * 初始化临时表数据
	 *
	 * @param conn 数据库连接
	 * @param tableName 临时表名
	 * @param baseDate 基准日期
	 * @param timeCtrlPk 账龄方案PK
	 * @throws BusinessException
	 * @throws SQLException
	 */
	private void initTimeCtrlData(Connection conn, String tableName, UFDate baseDate,
			String timeCtrlPk) throws BusinessException, SQLException {

		// 得到账龄方案信息
		TimeCtrlVO timeCtrlVO = NCLocator.getInstance().lookup(ITimeControlQueryService.class)
				.queryTimeCtrlByPk(timeCtrlPk);
		TimeCtrlDetail[] timeCtrlDetails = timeCtrlVO.getItems();

		if (ArrayUtils.isEmpty(timeCtrlDetails)) {
			return;
		}

		Arrays.sort(timeCtrlDetails, new Comparator<TimeCtrlDetail>() {
			@Override
			public int compare(TimeCtrlDetail detail1, TimeCtrlDetail detail2) {
				if (detail1 == null || detail2 == null) {
					return 0;
				}

				return detail1.getPropertyid().compareTo(detail2.getPropertyid());
			}

		});

		int unit = timeCtrlVO.getUnit();
		int days = timeCtrlVO.getDays();

		StringBuffer insertSql = new StringBuffer(" insert into " + tableName);
		insertSql.append(" (PROPERTYID, DESCR, STARTUNIT, ENDUNIT, STARTDATE, ENDDATE, STARTVALUE, ENDVALUE) ");
		insertSql.append(" values (?, ?, ?, ?, ?, ?, ?, ?)");
		Integer startUnit = null;
		Integer endUnit = null;

		((CrossDBConnection) conn).setAddTimeStamp(false);
		PreparedStatement pstmt = null;
		try {
			int i = 0;
			pstmt = prepareStatement(conn, insertSql.toString());

			if (timeCtrlDetails[0].getStartunit() != null) {
				i = 1;
				startUnit = null;
				endUnit = timeCtrlDetails[0].getStartunit() - 1;
				pstmt.setInt(i++, timeCtrlDetails[0].getPropertyid() - 1);
				pstmt.setString(i++, getTimeCtrlDesc(null, unit, startUnit, endUnit + 1));
//				pstmt.setInt(i++, startUnit == null ? -50000 : startUnit);
				pstmt.setInt(i++, -50000);
				if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
					pstmt.setInt(i++, endUnit == null ? 50000 : endUnit);
				} else {
					pstmt.setInt(i++, endUnit == null ? 50000 : endUnit + 1);
				}
				pstmt.setString(i++, getStartDate(baseDate, unit, days, endUnit));
				pstmt.setString(i++, getEndDate(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getStartValue(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getEndValue(baseDate, unit, days, endUnit));
				pstmt.addBatch();
			}

			for (TimeCtrlDetail timeCtrlDetail : timeCtrlDetails) {
				i = 1;
				startUnit = timeCtrlDetail.getStartunit();
				endUnit = timeCtrlDetail.getEndunit();
				pstmt.setInt(i++, timeCtrlDetail.getPropertyid());
				pstmt.setString(i++, getTimeCtrlDesc(timeCtrlDetail.getDescr(), unit, startUnit, endUnit));
				if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
					pstmt.setInt(i++, startUnit == null ? -50000 : startUnit);
					pstmt.setInt(i++, endUnit == null ? 50000 : endUnit);
				} else {
					pstmt.setInt(i++, startUnit == null ? -50000 : (startUnit <= 0 ? startUnit + 1 : startUnit));
					pstmt.setInt(i++, endUnit == null ? 50000 : (endUnit < 0 ? endUnit + 1 : endUnit));
				}
				pstmt.setString(i++, getStartDate(baseDate, unit, days, endUnit));
				pstmt.setString(i++, getEndDate(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getStartValue(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getEndValue(baseDate, unit, days, endUnit));
				pstmt.addBatch();
			}

			if (timeCtrlDetails[timeCtrlDetails.length - 1].getEndunit() != null) {
				i = 1;
				startUnit = timeCtrlDetails[timeCtrlDetails.length - 1].getEndunit() + 1;
				endUnit = null;
				pstmt.setInt(i++, timeCtrlDetails[timeCtrlDetails.length - 1].getPropertyid() + 1);
				pstmt.setString(i++, getTimeCtrlDesc(null, unit, startUnit, endUnit));
				pstmt.setInt(i++, startUnit == null ? -50000 : startUnit);
//				pstmt.setInt(i++, endUnit == null ? 50000 : endUnit);
				pstmt.setInt(i++, 50000);
				pstmt.setString(i++, getStartDate(baseDate, unit, days, endUnit));
				pstmt.setString(i++, getEndDate(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getStartValue(baseDate, unit, days, startUnit));
				pstmt.setInt(i++, getEndValue(baseDate, unit, days, endUnit));
				pstmt.addBatch();
			}

			executeBatch(pstmt);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * @param baseDate 基准日期
	 * @param unit 单位
	 * @param days 单位天数
	 * @param endUnit 截至账期
	 */
	private String getStartDate(UFDate baseDate, int unit, int days, Integer endUnit) {
		if (endUnit == null) {
			return "1900-01-01 00:00:00";
		}
		
		String startDate = null;
		if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(baseDate.getMillis());
			c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * endUnit - 1);
			startDate = new UFDate(c.getTimeInMillis() + 1000).toString();
		}else{
			int month = baseDate.getMonth();
			int year = baseDate.getYear();
			month = month - endUnit + 1;
			if(month <= 0){
				month = 12+month;
				year = year - 1;
			}
			if(month < 10){
				startDate = year + "-0"+ month + "-01 00:00:00";
			}else{
				startDate = year + "-"+ month + "-01 00:00:00";
			}
		}
		return startDate;
		
		
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(baseDate.getMillis());
//		if (UnitDays.valueOf(days).equals(UnitDays.CALENDAR)) {
//			switch (TimeUnit.valueOf(unit)) {
//			case MONTH:
//				unit = Calendar.MONTH;
//				break;
//			case YEAR:
//				unit = Calendar.YEAR;
//				break;
//			}
//			c.add(unit, -1 * (endUnit < 0 ? endUnit + 1 : endUnit));
//			c.add(Calendar.DAY_OF_YEAR, -1);
//		} else {
//			if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
//				c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * endUnit - 1);
//			} else {
//				c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * (endUnit < 0 ? endUnit + 1 : endUnit) - 1);
//			}
//		}
//
//		return new UFDate(c.getTimeInMillis() + 1000).toString();
	}

	private String getEndDate(UFDate baseDate, int unit, int days, Integer startUnit) {
		if (startUnit == null) {
			return "9999-12-31 23:59:59";
		}
		
		String endDate = null;
		if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(baseDate.getMillis());
			c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * startUnit);
			endDate = new UFDate(c.getTime()).toString();
		}else{
			int month = baseDate.getMonth();
			int year = baseDate.getYear();
			month = month - startUnit + 1;
			if(month <= 0){
				month = 12+month;
				year = year - 1;
			}
			String strMonth = null;
			if(month < 10){
				strMonth = "0"+ month;
			}else{
				strMonth = "" + month;
			}
			endDate = year + "-"+ strMonth + "-01";
			endDate = year + "-"+ strMonth + "-" +new UFDate(endDate).getDaysMonth() + " 23:59:59";
		}
		return endDate;
//
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(baseDate.getMillis());
//		if (UnitDays.valueOf(days).equals(UnitDays.CALENDAR)) {
//			switch (TimeUnit.valueOf(unit)) {
//			case MONTH:
//				unit = Calendar.MONTH;
//				break;
//			case YEAR:
//				unit = Calendar.YEAR;
//				break;
//			}
//			c.add(unit, -1 * (startUnit > 0 ? startUnit - 1 : startUnit));
//			c.add(Calendar.DAY_OF_YEAR, -1);
//		} else {
//			if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
//				c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * startUnit);
//			} else {
//				c.add(Calendar.DAY_OF_YEAR, -1 * getDays(days) * (startUnit > 0 ? startUnit - 1 : startUnit) - 1);
//			}
//		}
//
//		return new UFDate(c.getTime()).toString();
	}

	private int getStartValue(UFDate baseDate, int unit, int days, Integer startUnit) {
		int startValue = Integer.MIN_VALUE;
		if (startUnit == null) {
			startValue = Integer.MIN_VALUE;
		} else if (UnitDays.valueOf(days).equals(UnitDays.CALENDAR)) {
			startValue = baseDate.getDaysAfter(new UFDate(getEndDate(baseDate, unit, days, startUnit))) - 1;
		} else {
			if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
				startValue = getDays(days) * startUnit - 1;
			} else {
				startValue = getDays(days) * (startUnit > 0 ? startUnit - 1 : startUnit);
			}
		}

		return startValue;
	}

	private int getEndValue(UFDate baseDate, int unit, int days, Integer endUnit) {
		int endValue = Integer.MIN_VALUE;
		if (endUnit == null) {
			endValue = Integer.MAX_VALUE;
		} else if (UnitDays.valueOf(days).equals(UnitDays.CALENDAR)) {
			endValue = baseDate.getDaysAfter(new UFDate(getStartDate(baseDate, unit, days, endUnit)));
		} else {
			if (TimeUnit.valueOf(unit) == TimeUnit.DAY) {
				endValue = getDays(days) * endUnit;
			} else {
				endValue = getDays(days) * (endUnit < 0 ? endUnit + 1 : endUnit);
			}
		}

		return endValue;
	}

	private int getDays(int days) {
		return UnitDays.valueOfDays(UnitDays.valueOf(days));
	}

	private String getTimeUnitDesc(int timeUnit) {
		String desc = null;
		switch (TimeUnit.valueOf(timeUnit)) {
		case DAY:
			desc = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("201008v6_0","0201008v6-0049")/*@res "天"*/;
			break;
		case MONTH:
			desc = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0002494")/*@res "月"*/;
			break;
		case YEAR:
			desc = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0001787")/*@res "年"*/;
			break;
		}

		return desc;
	}

	private String getTimeCtrlDesc(String desc, int timeUnit, Integer begin, Integer end) {
		if (StringUtils.isEmpty(desc)) {
			String timeUnitDesc = getTimeUnitDesc(timeUnit);
			if (begin != null && end != null) {
				desc = begin.toString() + timeUnitDesc + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0000626")/*@res "到"*/ + end + timeUnitDesc;
			} else if (begin != null) {
				desc = begin.toString() + timeUnitDesc + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("201008v6_0","0201008v6-0050")/*@res "以上"*/;
			} else if (end != null) {
				desc = end.toString() + timeUnitDesc + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("201008v6_0","0201008v6-0051")/*@res "以内"*/;
			}
		}

		return desc;
	}

}

// /:~
