package nc.ui.gl.subjassbalancebooks;

/**
 * 此处插入类型说明。
 * 创建日期：(01-8-17 14:33:39)
 * @author：魏小炯
 */
import nc.bs.logging.Logger;
import nc.ui.gl.gateway.glworkbench.GlWorkBench;
import nc.ui.gl.vouchertools.VoucherDataCenter;
import nc.ui.glcom.displayformattool.ShowContentCenter;
import nc.vo.gl.accbook.ColFormatVO;
import nc.vo.gl.balancebooks.BalanceBSKey;
import nc.vo.gl.balancebooks.BalanceBSVO;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.pub.lang.UFDouble;

@SuppressWarnings("serial")
public class BalanceTableModel extends javax.swing.table.AbstractTableModel {
	NewTableFormatTackle vo = new NewTableFormatTackle();
	BalanceBSVO[] data = new BalanceBSVO[0];
	nc.ui.glcom.numbertool.GlCurrAmountFormat format;
	nc.ui.glcom.numbertool.GlNumberFormat numFormat;
	private java.lang.String locCurrTypePK;
	private GlQueryVO qryVO;

	/**
	 * TriAccTableModel 构造子注解。
	 * 
	 * @param c
	 *            java.lang.Class
	 */
	public BalanceTableModel() {
		super();
	}

	/**
	 * getColumnCount 方法注解。
	 */
	public int getColumnCount() {
		return vo.getColFormatVOs().length - vo.getFixedColSize();
	}

	/**
	 * 返回列的名称
	 */
	public String getColumnName(int col) {
		return vo.getColFormatVOs()[col + vo.getFixedColSize()].getColName();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-12-6 15:30:08)
	 * 
	 * @return nc.ui.glcom.numbertool.GlCurrAmountFormat
	 */
	private nc.ui.glcom.numbertool.GlCurrAmountFormat getCurrAmountFormat() {
		if (format == null)
			format = new nc.ui.glcom.numbertool.GlCurrAmountFormat();
		return format;
	}

	/**
	 * 此处插入方法说明。 创建日期：(01-9-5 14:57:22)
	 * 
	 * @return nc.ui.gl.balancebooks.BalanceTableFormatVO
	 */
	public NewTableFormatTackle getFormatVO() {
		return vo;
	}

	private Integer digit = 0;

	private int getNumDigit(BalanceBSVO vo) {
		if (vo != null) {
			String pk_glorgbook;
			try {
				pk_glorgbook = (String) vo
						.getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK);

				String pk_accasoa = (String) vo
						.getValue(BalanceBSKey.K_PKACCASOA);

				String dateStr = null;
				if (getQryVO().isUseSubjVersion()) {
					dateStr = getQryVO().getSubjVersion();
				} else {
					dateStr = GlWorkBench.getBusiDate().toStdString();
				}
				if (pk_glorgbook != null && pk_accasoa != null) {
					digit = VoucherDataCenter.getQuantityPrecision(
							pk_glorgbook, pk_accasoa, dateStr);
				}
				return digit.intValue();
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
		return digit.intValue();
	}

	private nc.ui.glcom.numbertool.GlNumberFormat getNumFormat() {
		if (numFormat == null)
			numFormat = new nc.ui.glcom.numbertool.GlNumberFormat();
		return numFormat;
	}

	/**
	 * 此处插入方法说明。 创建日期：(01-8-22 15:26:34)
	 * 
	 * @return int
	 */
	public int getRowCount() {
		return data.length;
	}

	/**
	 * getValueAt 方法注解。
	 */
	public Object getValue(int arg1, int iKey) {
		Object objtemp = null;
		try {
			objtemp = data[arg1].getValue(iKey);
			switch (iKey) {
			case BalanceBSKey.K_InitOrient:
			case BalanceBSKey.K_EndOrient:
				Integer iTemp = (Integer) objtemp;
				objtemp = nc.vo.glcom.account.Balanorient.getOrientStr(iTemp.intValue());
				break;
			case BalanceBSKey.K_DebitAmount:
			case BalanceBSKey.K_CreditAmount:
			case BalanceBSKey.K_DebitAccumAmount:
			case BalanceBSKey.K_CreditAccumAmount:
			case BalanceBSKey.K_InitAmount:
			case BalanceBSKey.K_EndAmount:
			case BalanceBSKey.K_InitCreditAmount:
			case BalanceBSKey.K_EndCreditAmount:
			case BalanceBSKey.K_InitDebitAmount:
			case BalanceBSKey.K_EndDebitAmount:
				Object currtypePk = data[arg1]
						.getValue(BalanceBSKey.K_PkCurrType);
				if (currtypePk == null || currtypePk.toString().equals("")
						|| currtypePk.toString().equals("ZZZZZZZZZZZZZZZZZZZZ"))
					objtemp = getCurrAmountFormat().formatAmount(
							(UFDouble) objtemp, locCurrTypePK);
				else
					objtemp = getCurrAmountFormat().formatAmount(
							(UFDouble) objtemp, currtypePk.toString());
				break;

			case BalanceBSKey.K_DebitLocAmount:
			case BalanceBSKey.K_CreditLocAmount:
			case BalanceBSKey.K_DebitAccumLocAmount:
			case BalanceBSKey.K_CreditAccumLocAmount:
			case BalanceBSKey.K_InitLocAmount:
			case BalanceBSKey.K_EndLocAmount:
			case BalanceBSKey.K_InitCreditLocAmount:
			case BalanceBSKey.K_EndCreditLocAmount:
			case BalanceBSKey.K_InitDebitLocAmount:
			case BalanceBSKey.K_EndDebitLocAmount:
				if (objtemp != null) {
					objtemp = getCurrAmountFormat().formatAmount((UFDouble) objtemp, locCurrTypePK);
				}
				break;
			case BalanceBSKey.K_DebitQuant:
			case BalanceBSKey.K_CreditQuant:
			case BalanceBSKey.K_CreditAccumQuant:
			case BalanceBSKey.K_DebitAccumQuant:
			case BalanceBSKey.K_InitQuant:
			case BalanceBSKey.K_EndQuant:
			case BalanceBSKey.K_InitCreditQuant:
			case BalanceBSKey.K_EndCreditQuant:
			case BalanceBSKey.K_InitDebitQuant:
			case BalanceBSKey.K_EndDebitQuant:
				objtemp = getNumFormat().format((UFDouble) objtemp,
						getNumDigit(data[arg1]));
				break;
			case BalanceBSKey.K_ASSVOS:
				objtemp = ShowContentCenter.getShowAss((String) data[arg1]
						.getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK),
						(AssVO[]) objtemp);
				break;
			}
		} catch (Exception e) {
			return null;
		}
		return objtemp;
	}

	/**
	 * getValueAt 方法注解。
	 */
	public Object getValueAt(int arg1, int arg2) {
		int iKey = vo.getColFormatVOs()[arg2 + vo.getFixedSize()].getColKey();
		//add by weiningc  633适配至65 _科目辅助余额表支持辅助核算分列显示及打印及上级科目显示编码 20171016 start
		//add chenth 20161207 辅助核算分列显示
		ColFormatVO colFormatVO = vo.getColFormatVOs()[arg2 + vo.getFixedSize()];
		String colName = colFormatVO.getColName();
		//辅助核算列从100开始
		if(colFormatVO.getColKey() >= 100 ){
			try {
				AssVO[] assVOs = (AssVO[])data[arg1].getValue(BalanceBSKey.K_ASSVOS);
				if(assVOs != null){
					for(AssVO assvo : assVOs){
						if(assvo.getChecktypename().equals(colName)){
							return ShowContentCenter.getShowAss(
									(String)data[arg1].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK)
									, new AssVO[]{assvo});
						}
					}
				}
			} catch (Exception e) {
				return null;
			}
		}
		//add by weiningc  633适配至65 _科目辅助余额表支持辅助核算分列显示及打印及上级科目显示编码 20171016 end
		return getValue(arg1, iKey);
	}

	/**
	 * 此处插入方法说明。 创建日期：(01-8-23 14:07:07)
	 * 
	 * @param p_data
	 *            nc.vo.gl.balancebooks.BalanceVO
	 */
	public void setData(BalanceBSVO[] p_data) {
		data = p_data;
	}
	/**
	 * 此处插入方法说明。 创建日期：(01-9-5 14:57:52)
	 * 
	 * @param p_vo
	 *            nc.ui.gl.balancebooks.BalanceTableFormatVO
	 */
	public void setFormatVO(NewTableFormatTackle p_vo) {
		vo = p_vo;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-12-15 9:43:28)
	 * 
	 * @param newLocCurrTypePK
	 *            java.lang.String
	 */
	public void setLocCurrTypePK(java.lang.String newLocCurrTypePK) {
		locCurrTypePK = newLocCurrTypePK;
	}

	/**
	 * @return the data
	 */
	public BalanceBSVO[] getData() {
		return data;
	}

	public void setQryVO(GlQueryVO qryVO) {
		this.qryVO = qryVO;
	}

	public GlQueryVO getQryVO() {
		return qryVO;
	}
}
