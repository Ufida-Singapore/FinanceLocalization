package nc.bs.arap.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import nc.bs.mw.sqltrans.TempTable;
import nc.jdbc.framework.ConnectionFactory;
import nc.jdbc.framework.util.DBUtil;

public class TmpTableCreator {

	/**
	 * 创建临时表
	 * 
	 * @param tableName String
	 * @param colNames String[]
	 * @param colTypes Integer[]
	 * @return String
	 * @throws SQLException
	 */
	public static String createTmpTable(String tableName, String[] colNames, Integer[] colTypes)
			throws SQLException {
		String[] types = new String[colTypes.length];
		for (int i = 0; i < colTypes.length; i++) {
			switch (colTypes[i]) {
			case Types.VARCHAR:
				if(" brief".equals(colNames[i])){
					//update for 新加坡BDA 20161111
					types[i] = "varchar(4000)"; // 摘要字段长度设为300，和arap_tally表保持一致
					break;
				}
				types[i] = "varchar(100)";
				break;
			case Types.DECIMAL:
				types[i] = "decimal(28, 8)";
				break;
			case Types.INTEGER:
				types[i] = "integer";
				break;
			default:
				break;
			}
		}

		return createTmpTable(tableName, colNames, types);
	}

	/**
	 * 创建临时表
	 * 
	 * @param tableName
	 * @param colNames
	 * @param colTypes
	 * @return
	 * @throws SQLException
	 */
	public static String createTmpTable(String tableName, String[] colNames, String[] colTypes)
			throws SQLException {
		Connection conn = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		try {
			conn = ConnectionFactory.getConnection();
			stmt1 = conn.createStatement();
			
			StringBuffer colsBuffer = new StringBuffer();
			for (int i = 0; i < colTypes.length; i++) {
				colsBuffer.append(colNames[i]).append(" ").append(colTypes[i]).append(", ");
			}
			// colsBuffer.append("ts char(19)");

			String cols = colsBuffer.toString().substring(0, colsBuffer.length() - 2);
			tableName = new TempTable().createTempTable(conn, tableName, cols, null);

			
			// 如果临时表已经过时，字段过多或过少，需要重新创建临时表
			if (isTempTableOut(stmt1, colNames,tableName)) {
				String dropSql = "drop table " + tableName;
				stmt2 = conn.createStatement();
				stmt2.execute(dropSql);
				tableName = new TempTable().createTempTable(conn, tableName, cols, null);
			}
			
		} finally {
			try {
				if (stmt1 != null) {
					DBUtil.closeStmt(stmt1);
				}
				if (stmt2 != null) {
					DBUtil.closeStmt(stmt2);
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		return tableName;
	}

	private static boolean isTempTableOut(Statement stmt, String[] colNames, String tableName) {
		StringBuffer selectSql = new StringBuffer("select ");
		int j = 0;
		for (; j < colNames.length - 1; j++) {
			selectSql.append(colNames[j]).append(", ");
		}
		selectSql.append(colNames[j]);
		selectSql.append(" from ").append(tableName);

		try {
			java.sql.ResultSet resultSet = stmt.executeQuery(selectSql.toString());
			if (resultSet.getMetaData().getColumnCount() == colNames.length) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return true;
		}
	}

}

// /:~
