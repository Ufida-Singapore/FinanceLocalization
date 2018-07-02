package nc.ui.gl.subjassbalancebooks;

/**
 * �˴���������˵���� �������ڣ�(01-8-13 16:13:27)
 *
 * @author��κС��
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import nc.bs.logging.Logger;
import nc.gl.account.glconst.CurrTypeConst;
import nc.gl.utils.GLMultiLangUtil;
import nc.itf.glcom.para.GLParaAccessor;
import nc.ui.gl.accbook.IBillModel;
import nc.ui.gl.asscompute.CAccountGenToolElement;
import nc.ui.gl.datacache.GLParaDataCache;
import nc.ui.glpub.IChartModel;
import nc.vo.bd.account.AccountVO;
import nc.vo.fipub.freevalue.GlAssVO;
import nc.vo.fipub.freevalue.Module;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.accountbook.GlOrgUtils;
import nc.vo.gateway60.itfs.AccTypeGL;
import nc.vo.gateway60.itfs.AccountUtilGL;
import nc.vo.gateway60.itfs.Currency;
import nc.vo.gateway60.itfs.CurrtypeGL;
import nc.vo.gateway60.org.OrgUtil;
import nc.vo.gl.balancebooks.BalanceBSKey;
import nc.vo.gl.balancebooks.BalanceBSVO;
import nc.vo.gl.balancebooks.BalanceResultVO;
import nc.vo.glcom.account.Balanorient;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.balance.GlBalanceKey;
import nc.vo.glcom.balance.GlBalanceVO;
import nc.vo.glcom.balance.GlQueryVO;
import nc.vo.glcom.inteltool.IGenToolElement;
import nc.vo.glcom.inteltool.IGenToolElementProvider;
import nc.vo.glcom.inteltool.ZeroUFdoubleConstant;
import nc.vo.glcom.tools.GLPubProxy;
import nc.vo.org.AccountingBookVO;
import nc.vo.org.OrgVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class BalancebooksModel implements IGenToolElementProvider, IBillModel,
		IChartModel, nc.vo.glcom.sorttool.ISortToolProvider {
	private boolean bQryAllGlorgbookAllAccsubj = false;
	protected GlQueryVO m_qryVO = null;

	protected BalanceBSVO[] m_dataVos;

	IGenToolElement m = null;

	IGenToolElement corpGenElement = null;

	int m_currentIndex = -1;

	protected java.util.HashMap numberMap = new java.util.HashMap();
	//�����Ŀ����������Կ�Ŀ�����ӳ�䡣 �����õ���Ŀ�ĵط��滻��
	private HashMap<String,AccountVO> accMap = new HashMap<String,AccountVO>();
	//update chenth 20161214 ֧�ֶ���  633������65 weiningc  start
//	private String accSum = "��Ŀ����";/*-=notranslate=-*/ 
	private String accSum = nc.ui.ml.NCLangRes.getInstance()
			.getStrByID("20023030", "UPP20023030-001124")  /*@res "��Ŀ����" */;
	//update chenth 20161214 ֧�ֶ���  633������65 weiningc  end
	private String bookSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-000471")/* @res "�����ʲ��ۼ�" */;
	private String unitSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("gl20111017public_v1_0","02002001-0012")/** @res "ҵ��Ԫ�ۼ�" */;
	private String currSum =nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-000119")/** @res* "�����ۼ�"*/;
	private String allSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-000115")/** @res* "�ܼ�"*/;
	private String subjSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-000263")/** @res* "��Ŀ�ϼ�"*/;
	private String subjTypeSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-000474")/** @res* "��Ŀ�����ۼ�"*/;
	/**
	 * TricolAccbooksModel ������ע�⡣
	 */
	public BalancebooksModel() {
		super();
	}


	private BalanceBSVO[] accSubjPlusCurrCompute(BalanceBSVO[] vos)
			throws Exception {

		Vector vecVO = new Vector();
		if (vos == null || vos.length == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			vos[i].setUserData(null);
			vecVO.addElement(vos[i]);
		}
		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();
		// ָ�����ڷ��������к�
		int intSortIndex[] = new int[] { BalanceBSKey.K_AccCode };
		genTool.setSortIndex(intSortIndex);
		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();
		datasource.setSumVector(nc.vo.glcom.inteltool.CDataSource.sortVector(vecVO, genTool, false));
		/** ָ�����ڷ��������к� */
		genTool.setSortIndex(intSortIndex);
		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		outputTool.setRequireOutputDetail(true);

		String[] strSummary, strInitSummary;
		strSummary = new String[] {subjSum};
		strInitSummary = new String[] { nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("20023030", "UPP20023030-000473") /* @res "��Ŀ�ڳ�" */};

		outputTool.setSimpleSummary(true);
		outputTool.setInitSummary(strInitSummary); // ���ñ�ע��Ϣ���ݼ�����Ӧ����
		outputTool.setSummary(strSummary); // ���ñ�ע��Ϣ���ݼ�����Ӧ����

		outputTool.setSummaryCol(BalanceBSKey.K_AccCode); // ���ñ�ע��Ϣ���ݼ�����Ӧ����
		outputTool.setGenTool(genTool);

		nc.vo.glcom.inteltool.CUFDoubleSumTool sumTool = new nc.vo.glcom.inteltool.CUFDoubleSumTool();
		int sumIndex[] = { BalanceBSKey.K_InitDebitQuant,
				BalanceBSKey.K_InitDebitAmount,
				BalanceBSKey.K_InitDebitAuxAmount,
				BalanceBSKey.K_InitDebitLocAmount,
				BalanceBSKey.K_InitCreditQuant,
				BalanceBSKey.K_InitCreditAmount,
				BalanceBSKey.K_InitCreditAuxAmount,
				BalanceBSKey.K_InitCreditLocAmount, BalanceBSKey.K_DebitQuant,
				BalanceBSKey.K_DebitAmount, BalanceBSKey.K_DebitAuxAmount,
				BalanceBSKey.K_DebitLocAmount, BalanceBSKey.K_CreditQuant,
				BalanceBSKey.K_CreditAmount, BalanceBSKey.K_CreditAuxAmount,
				BalanceBSKey.K_CreditLocAmount, BalanceBSKey.K_DebitAccumQuant,
				BalanceBSKey.K_DebitAccumAmount,
				BalanceBSKey.K_DebitAccumAuxAmount,
				BalanceBSKey.K_DebitAccumLocAmount,
				BalanceBSKey.K_CreditAccumQuant,
				BalanceBSKey.K_CreditAccumAmount,
				BalanceBSKey.K_CreditAccumAuxAmount,
				BalanceBSKey.K_CreditAccumLocAmount,
				BalanceBSKey.K_EndDebitQuant, BalanceBSKey.K_EndDebitAmount,
				BalanceBSKey.K_EndDebitAuxAmount,
				BalanceBSKey.K_EndDebitLocAmount,
				BalanceBSKey.K_EndCreditQuant, BalanceBSKey.K_EndCreditAmount,
				BalanceBSKey.K_EndCreditAuxAmount,
				BalanceBSKey.K_EndCreditLocAmount };
		/** Ҫ���кϼƵ��� */
		sumTool.setSumIndex(sumIndex);

		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();
		try {
			tt.setGenTool(genTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
			tt.setSumTool(sumTool);
			tt.setTotalSumTool(sumTool);

			Vector recVector = tt.getResultVector();

			vos = new BalanceBSVO[recVector.size()];
			recVector.copyInto(vos);

			for (int i = 0; i < vos.length; i++) {
				if (vos[i].getValue(BalanceBSKey.K_AccCode).equals(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
								"UPP20023030-000263")/* @res "��Ŀ�ϼ�" */)) {
					vos[i].setValue(BalanceBSKey.K_InitDebitAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_InitCreditAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_DebitAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_CreditAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_DebitAccumAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_CreditAccumAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_EndDebitAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_EndCreditAmount,
							ZeroUFdoubleConstant.DFDB_ZERO);
					vos[i].setValue(BalanceBSKey.K_CurType, "");
					vos[i].setValue(BalanceBSKey.K_PKACCASOA, null);
				}
			}
			return vos;
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * addBillInfo ����ע�⡣
	 */
	public void addBillInfo(nc.ui.gl.accbook.IBillInfo infoCenter) {
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 15:43:46)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	protected BalanceBSVO[] addNoBalanceAndNoOccor(BalanceBSVO[] vos,
			nc.vo.glcom.balance.GlQueryVO p_qryVO) throws Exception {
		if (!p_qryVO.getFormatVO().isShowZeroAmountRec()) {
			return vos;
		}
		// �޷����������ʾ
		BalanceBSVO[] blankVos = makeBlankRec(vos, p_qryVO);
		Vector vTwoVos = new Vector();
		for (int i = 0; i < blankVos.length; i++) {
			vTwoVos.addElement(blankVos[i]);
		}
		if (vos != null && vos.length > 0) {
			for (int i = 0; i < vos.length; i++) {
				vTwoVos.addElement(vos[i]);
			}
		}
		BalanceBSVO[] bsVos = new BalanceBSVO[vTwoVos.size()];
		vTwoVos.copyInto(bsVos);

		return bsVos;
	}

	protected boolean voIsEmpty(BalanceBSVO vo) throws Exception {
		if ((vo.getValue(BalanceBSKey.K_CreditAccumAmount) == null || ((Double) vo
				.getValue(BalanceBSKey.K_CreditAccumAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditAccumAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditAccumAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditAccumLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditAccumLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditAccumQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditAccumQuant))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAccumAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAccumAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAccumAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAccumAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAccumLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAccumLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAccumQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAccumQuant))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_CreditQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_CreditQuant)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitAuxAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitLocAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_DebitQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_DebitQuant)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndCreditAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndCreditAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndCreditAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndCreditAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndCreditLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndCreditLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndCreditQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndCreditQuant)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndDebitAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndDebitAmount)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndDebitAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndDebitAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndDebitLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndDebitLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_EndDebitQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_EndDebitQuant)).doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitCreditAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitCreditAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitCreditAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitCreditAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitCreditLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitCreditLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitCreditQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitCreditQuant))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitDebitAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitDebitAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitDebitAuxAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitDebitAuxAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitDebitLocAmount) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitDebitLocAmount))
						.doubleValue() == 0.0)
				&& (vo.getValue(BalanceBSKey.K_InitDebitQuant) == null || ((Double) vo
						.getValue(BalanceBSKey.K_InitDebitQuant)).doubleValue() == 0.0)) {
			return true;
		}
		return false;
	}

	/**
	 * addVO ����ע�⡣
	 */
	public void addVO(java.lang.Object objValue) throws java.lang.Exception {
	}


	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-6-25 16:21:16)
	 *
	 * @return BalanceBSVO
	 * @param vo1
	 *            BalanceBSVO
	 * @param vo2
	 *            BalanceBSVO
	 */
	private BalanceBSVO addVO(BalanceBSVO vo1, BalanceBSVO vo2)
			throws Exception {
		if (vo1 == null)
			return vo2;
		if (vo2 == null)
			return vo1;
		vo1.setValue(BalanceBSKey.K_CreditAccumAmount, add(vo1
				.getValue(BalanceBSKey.K_CreditAccumAmount), vo2
				.getValue(BalanceBSKey.K_CreditAccumAmount)));
		vo1.setValue(BalanceBSKey.K_CreditAccumLocAmount, add(vo1
				.getValue(BalanceBSKey.K_CreditAccumLocAmount), vo2
				.getValue(BalanceBSKey.K_CreditAccumLocAmount)));
		vo1.setValue(BalanceBSKey.K_CreditAccumQuant, add(vo1
				.getValue(BalanceBSKey.K_CreditAccumQuant), vo2
				.getValue(BalanceBSKey.K_CreditAccumQuant)));
		vo1.setValue(BalanceBSKey.K_CreditAmount, add(vo1
				.getValue(BalanceBSKey.K_CreditAmount), vo2
				.getValue(BalanceBSKey.K_CreditAmount)));
		vo1.setValue(BalanceBSKey.K_CreditLocAmount, add(vo1
				.getValue(BalanceBSKey.K_CreditLocAmount), vo2
				.getValue(BalanceBSKey.K_CreditLocAmount)));
		vo1.setValue(BalanceBSKey.K_CreditQuant, add(vo1
				.getValue(BalanceBSKey.K_CreditQuant), vo2
				.getValue(BalanceBSKey.K_CreditQuant)));

		vo1.setValue(BalanceBSKey.K_DebitAccumAmount, add(vo1
				.getValue(BalanceBSKey.K_DebitAccumAmount), vo2
				.getValue(BalanceBSKey.K_DebitAccumAmount)));
		vo1.setValue(BalanceBSKey.K_DebitAccumLocAmount, add(vo1
				.getValue(BalanceBSKey.K_DebitAccumLocAmount), vo2
				.getValue(BalanceBSKey.K_DebitAccumLocAmount)));
		vo1.setValue(BalanceBSKey.K_DebitAccumQuant, add(vo1
				.getValue(BalanceBSKey.K_DebitAccumQuant), vo2
				.getValue(BalanceBSKey.K_DebitAccumQuant)));
		vo1.setValue(BalanceBSKey.K_DebitAmount, add(vo1
				.getValue(BalanceBSKey.K_DebitAmount), vo2
				.getValue(BalanceBSKey.K_DebitAmount)));
		vo1.setValue(BalanceBSKey.K_DebitLocAmount, add(vo1
				.getValue(BalanceBSKey.K_DebitLocAmount), vo2
				.getValue(BalanceBSKey.K_DebitLocAmount)));
		vo1.setValue(BalanceBSKey.K_DebitQuant, add(vo1
				.getValue(BalanceBSKey.K_DebitQuant), vo2
				.getValue(BalanceBSKey.K_DebitQuant)));

		vo1.setValue(BalanceBSKey.K_EndCreditAmount, add(vo1
				.getValue(BalanceBSKey.K_EndCreditAmount), vo2
				.getValue(BalanceBSKey.K_EndCreditAmount)));

		vo1.setValue(BalanceBSKey.K_EndCreditLocAmount, add(vo1
				.getValue(BalanceBSKey.K_EndCreditLocAmount), vo2
				.getValue(BalanceBSKey.K_EndCreditLocAmount)));
		vo1.setValue(BalanceBSKey.K_EndCreditQuant, add(vo1
				.getValue(BalanceBSKey.K_EndCreditQuant), vo2
				.getValue(BalanceBSKey.K_EndCreditQuant)));

		vo1.setValue(BalanceBSKey.K_EndDebitAmount, add(vo1
				.getValue(BalanceBSKey.K_EndDebitAmount), vo2
				.getValue(BalanceBSKey.K_EndDebitAmount)));
		vo1.setValue(BalanceBSKey.K_EndDebitLocAmount, add(vo1
				.getValue(BalanceBSKey.K_EndDebitLocAmount), vo2
				.getValue(BalanceBSKey.K_EndDebitLocAmount)));
		vo1.setValue(BalanceBSKey.K_EndDebitQuant, add(vo1
				.getValue(BalanceBSKey.K_EndDebitQuant), vo2
				.getValue(BalanceBSKey.K_EndDebitQuant)));

		vo1.setValue(BalanceBSKey.K_InitCreditAmount, add(vo1
				.getValue(BalanceBSKey.K_InitCreditAmount), vo2
				.getValue(BalanceBSKey.K_InitCreditAmount)));

		vo1.setValue(BalanceBSKey.K_InitCreditLocAmount, add(vo1
				.getValue(BalanceBSKey.K_InitCreditLocAmount), vo2
				.getValue(BalanceBSKey.K_InitCreditLocAmount)));
		vo1.setValue(BalanceBSKey.K_InitCreditQuant, add(vo1
				.getValue(BalanceBSKey.K_InitCreditQuant), vo2
				.getValue(BalanceBSKey.K_InitCreditQuant)));

		vo1.setValue(BalanceBSKey.K_InitDebitAmount, add(vo1
				.getValue(BalanceBSKey.K_InitDebitAmount), vo2
				.getValue(BalanceBSKey.K_InitDebitAmount)));

		vo1.setValue(BalanceBSKey.K_InitDebitLocAmount, add(vo1
				.getValue(BalanceBSKey.K_InitDebitLocAmount), vo2
				.getValue(BalanceBSKey.K_InitDebitLocAmount)));
		vo1.setValue(BalanceBSKey.K_InitDebitQuant, add(vo1
				.getValue(BalanceBSKey.K_InitDebitQuant), vo2
				.getValue(BalanceBSKey.K_InitDebitQuant)));
		return vo1;
	}
	
	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-6-25 16:24:33)
	 *
	 * @return nc.vo.pub.lang.UFDouble
	 * @param value1
	 *            java.lang.Object
	 * @param value2
	 *            java.lang.Object
	 */
	private nc.vo.pub.lang.UFDouble add(Object value1, Object value2) {
		UFDouble d1 = null, d2 = null;
		if (value1 instanceof UFDouble)
			d1 = (UFDouble) value1;
		else
			d1 = new UFDouble(value1 == null ? "" : value1.toString());

		if (value2 instanceof UFDouble)
			d2 = (UFDouble) value2;
		else
			d2 = new UFDouble(value2 == null ? "" : value2.toString());

		return d1.add(d2);
	}
	
	/***
	 * �˴����뷽��˵���� �������ڣ�(2015-8-5 11:24:24)
	 * ��Ŀ�������������ϲ���ѯʱʹ��
	 * ˵������ȡAccountVOʱ������AccountCache�� ֱ�Ӵ�accMap�����ȡ��
	 * 	        Ϊ�����ѯ"����ѯ�Ķ������"���ǹ��еĿ�Ŀʱ����ָ���쳣����ɲ�ѯ���Ϊ�յ����
	 * �߼�����PK��ͨ��PK��ȡ��û��PK��ͨ����Ŀ�����ȡ��
	 * @param accountPk
	 * @param accountCode
	 * @return AccountVO
	 */
	private AccountVO getAccountVOFromAccMap(String accountPk, String accountCode) {
		HashMap<String, AccountVO> tmpAccMap = getAccMap();
		AccountVO rtnAvvountVO = null;
		if(tmpAccMap.isEmpty()){
			return null;
		}
		//��ͨ����Ŀ��������	
		if(accountPk != null && !("".equals(accountPk))){
			rtnAvvountVO = tmpAccMap.get(accountPk);
		}
		//���ͨ������û�ҵ�������ͨ����Ŀ������
		if(rtnAvvountVO == null && accountCode != null && !("".equals(accountCode))){
			rtnAvvountVO = tmpAccMap.get(accountCode);
		}
		
		return rtnAvvountVO;
	}
	
	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 15:43:46)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	protected BalanceBSVO[] adjustContent(BalanceBSVO[] vos,
			nc.vo.glcom.balance.GlQueryVO p_qryVO) throws Exception {
		// �Ӳ�ѯ����й�����Ҫ��ļ�¼
		String[] subjsCode = p_qryVO.getAccountCode();
		Vector vecTemp = new Vector();
		for (int i = 0; i < subjsCode.length; i++) {
			vecTemp.addElement(subjsCode[i]);
		}

		Vector tmpResult = new Vector();

		for (int j = 0; vos != null && j < vos.length; j++) {
			String strCode = (String) vos[j].getValue(BalanceBSKey.K_AccCode);
			String strName = (String) vos[j].getValue(BalanceBSKey.K_AccName);
			if (strName == null) {
				strName = "";
			}
			if (strName.indexOf(bookSum) >= 0
					|| strName.indexOf(currSum) >= 0
					|| strName.indexOf(allSum) >= 0
					|| strName.indexOf(subjSum) >= 0
					|| strName.indexOf(accSum) >= 0
				    || strName.indexOf(unitSum) >= 0
					|| strName.indexOf(subjTypeSum) >= 0)
				vos[j].setValue(BalanceBSKey.K_BothOrient, new Boolean(true));
			if (strName.indexOf(allSum) >= 0
					//add chenth 20161214 Bug �л���Ӣ�� ���еĿ�Ŀ�ϼƶ��ܵ������
					//add by weiningc 20171013  633������65 start
					&& strName.indexOf(subjSum) < 0) {
				//add by weiningc 20171013  633������65 end
				vos[j].setValue(BalanceBSKey.K_FINANCEORGCODE, "zzzz");
				vos[j].setValue(BalanceBSKey.K_FINANCEORGNAME, "");
				vos[j].setValue(BalanceBSKey.K_subjTypeIndex, "Z");
				vos[j].setValue(BalanceBSKey.K_PkCurrType,"ZZZZZZZZZZZZZZZZZZZZ");
				vos[j].setValue(BalanceBSKey.K_AccCode,allSum);
				vos[j].setValue(BalanceBSKey.K_AccName, "");
				vos[j].setValue(BalanceBSKey.K_PKACCASOA, null);
				vos[j].setValue(BalanceBSKey.K_PKUNIT_V, null);
				vos[j].setValue(BalanceBSKey.K_PKUNIT, null);
				vos[j].setValue(BalanceBSKey.K_UNITCODE, "ZZZZZZZZZZZZZ");
			}
			if (strName.indexOf(bookSum) >= 0
					|| strName.indexOf(currSum) >= 0
					|| strName.indexOf(subjTypeSum) >= 0
					|| strName.indexOf(accSum) >= 0/*-=notranslate=-*/
					|| strName.indexOf(subjSum) >= 0
					|| strName.indexOf(unitSum) >= 0) {
				String subStr = vos[j].getValue(BalanceBSKey.K_AccName).toString();
				Object code = vos[j].getValue(BalanceBSKey.K_AccCode);
				if(/*strName.indexOf(subjSum) >= 0||*/ strName.indexOf(accSum) >= 0){  //��������ʱ���Ŀ�ϼ�Ҫ�����ű���Ŀ
					vos[j].setValue(BalanceBSKey.K_AccName, code);
				}else if(strName.indexOf(subjSum) >= 0){
					if(p_qryVO.isMultiCorpCombine()){
						vos[j].setValue(BalanceBSKey.K_AccName, "");
						vos[j].setValue(BalanceBSKey.K_ASSVOS, null);
						vos[j].setValue(BalanceBSKey.K_ASSID, null);
					}else{
						vos[j].setValue(BalanceBSKey.K_AccName, "");
						vos[j].setValue(BalanceBSKey.K_ASSVOS, null);
						vos[j].setValue(BalanceBSKey.K_ASSID, null);
					}
				}else{
					vos[j].setValue(BalanceBSKey.K_AccName, "");
				}
				
				if (strName.indexOf(unitSum) >= 0) {
					vos[j].setValue(BalanceBSKey.K_PkCurrType,"ZZZZZZZZZZZZZZZZZZZZ");
					vos[j].setValue(BalanceBSKey.K_subjTypeIndex, "Z");
				}
				
				if(strName.indexOf(subjSum) >= 0){
					vos[j].setValue(BalanceBSKey.K_AccCode, code);
				}else{
					vos[j].setValue(BalanceBSKey.K_AccCode, subStr);
				}
				
				vos[j].setValue(BalanceBSKey.K_PKACCASOA, null);
				if (strName.indexOf(bookSum) >= 0)
					vos[j].setValue(BalanceBSKey.K_PkCurrType,"ZZZZZZZZZZZZZZZZZZZZ");
				if (strName.indexOf(currSum) >= 0) {
					vos[j].setValue(BalanceBSKey.K_AccCode,"ZZZZZZZZZZZZZZZZZZZZ");
					vos[j].setValue(BalanceBSKey.K_subjTypeIndex, "Z");
				}
			}
			if (!bQryAllGlorgbookAllAccsubj) {
				//add by weiningc  633������65 ȡ��  ����ʾ��Ŀ�ϼ���  20171017 start
				//add chenth 20170420 for Shanda ����ʾ��Ŀ�ϼ���
				if(strName.indexOf(subjSum) >= 0){
					continue;
				}
				//add by weiningc  633������65 ȡ��  ����ʾ��Ŀ�ϼ���  20171017 end
				
				if(vecTemp.contains(strCode)){
					if(p_qryVO.getFormatVO().isShowUpSubj()&&strCode!=null
							&&vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK)!= null
//							&&!AccountCache.getInstance().getAccountVOByCode(
//									vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK).toString()
//											, strCode,p_qryVO.getSubjVersion()).getEndflag().booleanValue()){
									&&!getAccountVOFromAccMap((String)vos[j].getValue(BalanceBSKey.K_PKACCASOA),
											strCode).getEndflag().booleanValue()){
						vos[j].setValue(BalanceBSKey.K_ASSVOS,null);
						vos[j].setValue(BalanceBSKey.K_ASSID,null);
						tmpResult.addElement(vos[j]);
					}else if(strCode!=null
							&&vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK)!= null
//							&&AccountCache.getInstance().getAccountVOByCode(
//									vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK).toString()
//											, strCode,p_qryVO.getSubjVersion()).getEndflag().booleanValue())
							&&(getAccountVOFromAccMap((String)vos[j].getValue(BalanceBSKey.K_PKACCASOA),
									strCode)!=null
									&&getAccountVOFromAccMap((String)vos[j].getValue(BalanceBSKey.K_PKACCASOA),
									strCode).getEndflag().booleanValue())){
						tmpResult.addElement(vos[j]);
					}
					
				}
				else if ( strName.indexOf(bookSum) >= 0
						|| strName.indexOf(currSum) >= 0
						|| strName.indexOf(allSum) >= 0
						|| strName.indexOf(subjTypeSum) >= 0
						|| strName.indexOf(unitSum) >= 0
						|| (vecTemp.contains(strCode) 
								&& (strName.indexOf(subjSum) >= 0|| strName.indexOf(accSum) >= 0))
			            ) {
					tmpResult.addElement(vos[j]);
				}else{
					if(p_qryVO.getFormatVO().isShowUpSubj()){
						if (!(strName.indexOf(bookSum) >= 0
								|| strName.indexOf(currSum) >= 0
								|| strName.indexOf(allSum) >= 0
								|| strName.indexOf(subjTypeSum) >= 0
								|| strName.indexOf(unitSum) >= 0
								||strName.indexOf(subjSum)>=0)){
							if(strCode!=null
									&&vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK)!= null
//									&&!AccountCache.getInstance().getAccountVOByCode(
//											vos[j].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK).toString()
//													, strCode,p_qryVO.getSubjVersion()).getEndflag().booleanValue())
											&&!getAccountVOFromAccMap((String)vos[j].getValue(BalanceBSKey.K_PKACCASOA),
													strCode).getEndflag().booleanValue()){
								vos[j].setValue(BalanceBSKey.K_ASSVOS,null);
								vos[j].setValue(BalanceBSKey.K_ASSID,null);
								tmpResult.addElement(vos[j]);
							
							}
						}
					}
				}
			} else {
				tmpResult.addElement(vos[j]);
			}
		}
		bQryAllGlorgbookAllAccsubj = false;
		vos = new BalanceBSVO[tmpResult.size()];
		tmpResult.copyInto(vos);
		if (p_qryVO.getpk_accountingbook().length > 1
				&& !p_qryVO.isMultiCorpCombine())
			sort(vos, new int[] { BalanceBSKey.K_FINANCEORGCODE,
					BalanceBSKey.K_PkCurrType }); // , BalanceBSKey.K_AccCode
		else
			sort(vos, new int[] { BalanceBSKey.K_PkCurrType }); // ,BalanceBSKey.K_AccCode

		for (int j = 0; vos != null && j < vos.length; j++) {
			String strName = (String) vos[j].getValue(BalanceBSKey.K_AccCode);
			if (strName.indexOf(bookSum) >= 0) {
				vos[j].setValue(BalanceBSKey.K_AccCode,bookSum);
			}
			if (strName.indexOf(unitSum) >= 0) {
				vos[j].setValue(BalanceBSKey.K_AccCode,unitSum);
			}
			if (strName.indexOf("ZZZZZZZZZZZZZZZZZZZZ") >= 0) {
				vos[j].setValue(BalanceBSKey.K_AccCode,currSum);
			}
		}
		if (!p_qryVO.getCurrTypeName().equals(
				nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())
				&& !p_qryVO.getCurrTypeName().equals(
						nc.gl.account.glconst.CurrTypeConst
								.QUERY_LOC_CURRTYPE())
				&& vos != null
				&& vos.length != 0) {

			for (int j = 0; vos != null && j < vos.length; j++) {
				String strCode = (String) vos[j]
						.getValue(BalanceBSKey.K_AccCode);
				if (strCode.indexOf(bookSum) >= 0
						|| strCode.indexOf(currSum) >= 0
						|| strCode.indexOf(allSum) >= 0
						|| strCode.indexOf(subjTypeSum) >= 0
						|| strCode.indexOf(unitSum) >= 0
						|| strCode.indexOf(subjSum) >= 0) {
					vos[j].setValue(BalanceBSKey.K_PkCurrType,
							vos[0].getValue(BalanceBSKey.K_PkCurrType));

				}
			}
		}

		// �൥λ��ʾ���൥λ����ѯһ����Ŀʱ������ʾ"��˾�ۼ�"��
		if (p_qryVO.getAccountCodes().length == 1
				&& !p_qryVO.isMultiCorpCombine()
				&& p_qryVO.getpk_accountingbook().length > 1
				&& !p_qryVO.getCurrTypeName().equals(
						nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
			Vector vTemp = new java.util.Vector();
			for (int i = 0; i < vos.length; i++) {
				if (!vos[i].getValue(BalanceBSKey.K_AccCode).equals(bookSum)) {
					vTemp.addElement(vos[i]);
				}
			}
			vos = new BalanceBSVO[vTemp.size()];
			vTemp.copyInto(vos);
		}
		if (!p_qryVO.isMultiCorpCombine()
				&& p_qryVO.getpk_accountingbook().length > 1
				&& p_qryVO.getCurrTypeName().equals(
						nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
			for (int i = 0; i < vos.length; i++) {
				if (vos[i].getValue(BalanceBSKey.K_AccCode).equals(bookSum)
						|| vos[i].getValue(BalanceBSKey.K_AccCode).equals(allSum)) {
					vos[i].setValue(BalanceBSKey.K_InitDebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_InitCreditAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_DebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_CreditAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_DebitAccumAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_CreditAccumAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_EndDebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_EndCreditAmount,UFDouble.ZERO_DBL);
				}
			}
		}
		if (p_qryVO.getCurrTypeName().equals(
				nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
			for (int i = 0; i < vos.length; i++) {
				if (vos[i].getValue(BalanceBSKey.K_AccCode).equals(allSum)) {
					vos[i].setValue(BalanceBSKey.K_InitDebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_InitCreditAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_DebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_CreditAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_DebitAccumAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_CreditAccumAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_EndDebitAmount,UFDouble.ZERO_DBL);
					vos[i].setValue(BalanceBSKey.K_EndCreditAmount,UFDouble.ZERO_DBL);
				}
			}
		}
		// ������������ԭ�ҡ�����
		if (p_qryVO.getCurrTypeName().equals(
				nc.gl.account.glconst.CurrTypeConst.QUERY_LOC_CURRTYPE())) {
			for (int i = 0; i < vos.length; i++) {
				vos[i].setValue(BalanceBSKey.K_InitDebitAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_InitCreditAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_DebitAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_CreditAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_DebitAccumAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_CreditAccumAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_EndDebitAmount,UFDouble.ZERO_DBL);
				vos[i].setValue(BalanceBSKey.K_EndCreditAmount,UFDouble.ZERO_DBL);
			}
		}
		return vos;
	}

	protected BalanceBSVO[] adjustGlorgbook(BalanceBSVO[] vos, GlQueryVO qryVO) {
		if (vos == null || vos.length == 0)
			return vos;

		String[] pk_orgs = qryVO.getpk_accountingbook();
		try {
			if (pk_orgs.length > 1 && !qryVO.isMultiCorpCombine()) {
				AccountingBookVO[] glorgbookvos = AccountBookUtil
						.getGlOrgBookVOByPrimaryKeys(qryVO
								.getpk_accountingbook());
				for (int i = 0; i < vos.length; i++) {
					for (int k = 0; k < pk_orgs.length; k++) {
						if (pk_orgs[k].equals(vos[i]
								.getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK))) {
							vos[i].setValue(BalanceBSKey.K_FINANCEORGNAME,
									glorgbookvos[k].getName());
							vos[i].setValue(BalanceBSKey.K_FINANCEORGCODE,
									glorgbookvos[k].getCode());
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return vos;
	}

	protected BalanceBSVO[] adjustCurrType(BalanceBSVO[] vos, GlQueryVO qryVO) {
		if (vos == null || vos.length == 0)
			return vos;

		String pk_currtype = qryVO.getPk_currtype();
		String currTypeName = qryVO.getCurrTypeName();
		try {
			nc.vo.bd.currtype.CurrtypeVO currVO;
			if (pk_currtype == null
					&& currTypeName.equals(CurrTypeConst.ALL_CURRTYPE())) {
				nc.vo.bd.currtype.CurrtypeVO[] currVOs = Currency
						.getAllCurrency();

				if (currVOs != null && currVOs.length != 0) {
					Map<String, String> curymap = new HashMap<String, String>();
					for (int i = 0; i < currVOs.length; i++) {
						curymap.put(currVOs[i].getPk_currtype(),
								currVOs[i].getName());
					}
					for (int i = 0; i < vos.length; i++) {
						vos[i].setValue(BalanceBSKey.K_CurType,
								curymap.get(vos[i]
										.getValue(BalanceBSKey.K_PkCurrType)));
					}
				}
			} else {
				if (currTypeName.equals(CurrTypeConst.QUERY_LOC_CURRTYPE())) {
					String pk_locCurrType = GLParaDataCache.getInstance()
							.PkLocalCurr(qryVO.getBaseAccountingbook());
					currVO = Currency.getCurrInfo(pk_locCurrType);
				} else
					currVO = Currency.getCurrInfo(pk_currtype);
				for (int i = 0; i < vos.length; i++) {
					vos[i].setValue(BalanceBSKey.K_PkCurrType,
							currVO.getPk_currtype());
					vos[i].setValue(BalanceBSKey.K_CurType, currVO.getName());
				}
			}
		} catch (Exception e) {
		}
		return vos;
	}

	private BalanceBSVO[] adjustSubjNameByCode(BalanceBSVO[] param,
			GlQueryVO qryVO) throws Exception {
		// ���ݿ�Ŀ����������տ�Ŀ����
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				if (param[i].getValue(BalanceBSKey.K_AccCode) != null
						&& param[i].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK) != null) {
					String subjcode = param[i].getValue(BalanceBSKey.K_AccCode).toString();
					String pk_glorgbook = param[i].getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK).toString();
					String name = param[i].getValue(BalanceBSKey.K_AccName).toString();
					String subjname = null;
//					if (AccountCache.getInstance().getAccountVOByCode(
//							pk_glorgbook, subjcode, qryVO.getSubjVersion()) != null) {
					if (getAccountVOFromAccMap((String)param[i].getValue(BalanceBSKey.K_PKACCASOA),
							(String)param[i].getValue(BalanceBSKey.K_AccCode)) != null){
//						subjname = GLMultiLangUtil.getMultiDispName(AccountCache.getInstance()
//								.getAccountVOByCode(pk_glorgbook, subjcode,qryVO.getSubjVersion()));
						subjname = GLMultiLangUtil.getMultiDispName(getAccountVOFromAccMap((String)param[i].getValue(BalanceBSKey.K_PKACCASOA),
								(String)param[i].getValue(BalanceBSKey.K_AccCode)));
						if (name.indexOf(subjSum) >= 0) {
							// ������Ŀ�ϼơ��еĿ�Ŀ�����滻������
							param[i].setValue(BalanceBSKey.K_AccName,name);
						}else if(name.indexOf(accSum) >= 0){
							param[i].setValue(BalanceBSKey.K_AccName,subjname+ accSum);
						} else{
							param[i].setValue(BalanceBSKey.K_AccName,subjname);
						}
					}
				}
			}

		}
		return param;
	}

	private BalanceBSVO[] adjustPkSubjByCode(BalanceBSVO[] param,
			GlQueryVO qryVO,HashMap<String, AccountVO> accMap) throws Exception {
		// ���ݱ��뽫PK����Ϊ��׼��ĿPK
		String pk_baseOrgbook = qryVO.getBaseAccountingbook();
		for (int i = 0; i < param.length; i++) {
			if (param[i].getValue(BalanceBSKey.K_AccCode) != null) {
				String subjcode = param[i].getValue(BalanceBSKey.K_AccCode).toString();
				if (pk_baseOrgbook != null&& accMap.get(subjcode) != null) {
					String pk_basesubj = accMap.get(subjcode).getPk_accasoa();
					param[i].setValue(BalanceBSKey.K_PKACCASOA, pk_basesubj);
				}
				param[i].setValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK, qryVO.getBaseAccountingbook());
			}
		}
		return param;
	}

	protected BalanceBSVO[] twoWayBalance(BalanceBSVO[] param, boolean inEndData)
			throws Exception {
		if (param != null && param.length != 0) {
			for (int i = 0; i < param.length; i++) {
				if (!inEndData) {
					String strName = (String) param[i]
							.getValue(BalanceBSKey.K_AccName);
					if (strName != null
							&& (strName.equals(bookSum)
									|| strName.equals(unitSum)
									|| strName.equals(currSum)
									|| strName.equals(allSum) 
								    || strName.indexOf(subjTypeSum) >= 0)) {
						continue;
					}
				}
				int iInit = ((Integer) param[i].getValue(BalanceBSKey.K_InitOrient)).intValue();
				int iEnd = ((Integer) param[i].getValue(BalanceBSKey.K_EndOrient)).intValue();
				UFDouble dIDQ = param[i].getValue(BalanceBSKey.K_InitDebitQuant) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitDebitQuant);
				UFDouble dICQ = param[i].getValue(BalanceBSKey.K_InitCreditQuant) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitCreditQuant);
				UFDouble dIDA = param[i].getValue(BalanceBSKey.K_InitDebitAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitDebitAmount);
				UFDouble dICA = param[i].getValue(BalanceBSKey.K_InitCreditAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitCreditAmount);
				UFDouble dIDLA = param[i].getValue(BalanceBSKey.K_InitDebitLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitDebitLocAmount);
				UFDouble dICLA = param[i].getValue(BalanceBSKey.K_InitCreditLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_InitCreditLocAmount);
				UFDouble dEDQ = param[i].getValue(BalanceBSKey.K_EndDebitQuant) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndDebitQuant);
				UFDouble dECQ = param[i].getValue(BalanceBSKey.K_EndCreditQuant) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndCreditQuant);
				UFDouble dEDA = param[i].getValue(BalanceBSKey.K_EndDebitAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndDebitAmount);
				UFDouble dECA = param[i].getValue(BalanceBSKey.K_EndCreditAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndCreditAmount);
				UFDouble dEDLA = param[i].getValue(BalanceBSKey.K_EndDebitLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndDebitLocAmount);
				UFDouble dECLA = param[i].getValue(BalanceBSKey.K_EndCreditLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
						: (UFDouble) param[i].getValue(BalanceBSKey.K_EndCreditLocAmount);
				switch (iInit) {
				case Balanorient.DEBIT:
					param[i].setValue(BalanceBSKey.K_InitDebitQuant,dIDQ.sub(dICQ));
					param[i].setValue(BalanceBSKey.K_InitDebitAmount,dIDA.sub(dICA));
					param[i].setValue(BalanceBSKey.K_InitDebitLocAmount,dIDLA.sub(dICLA));
					param[i].setValue(BalanceBSKey.K_InitCreditQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					break;
				case Balanorient.CREDIT:
					param[i].setValue(BalanceBSKey.K_InitDebitQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitDebitAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitDebitLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditQuant,dICQ.sub(dIDQ));
					param[i].setValue(BalanceBSKey.K_InitCreditAmount,dICA.sub(dIDA));
					param[i].setValue(BalanceBSKey.K_InitCreditLocAmount,dICLA.sub(dIDLA));
					break;
				case Balanorient.TWOWAY:
					param[i].setValue(BalanceBSKey.K_InitDebitQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitDebitAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitDebitLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_InitCreditLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					break;
				}
				switch (iEnd) {
				case Balanorient.DEBIT:
					param[i].setValue(BalanceBSKey.K_EndDebitQuant,dEDQ.sub(dECQ));
					param[i].setValue(BalanceBSKey.K_EndDebitAmount,dEDA.sub(dECA));
					param[i].setValue(BalanceBSKey.K_EndDebitLocAmount,dEDLA.sub(dECLA));
					param[i].setValue(BalanceBSKey.K_EndCreditQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					break;
				case Balanorient.CREDIT:
					param[i].setValue(BalanceBSKey.K_EndDebitQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndDebitAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndDebitLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditQuant,dECQ.sub(dEDQ));
					param[i].setValue(BalanceBSKey.K_EndCreditAmount,dECA.sub(dEDA));
					param[i].setValue(BalanceBSKey.K_EndCreditLocAmount,dECLA.sub(dEDLA));
					break;
				case Balanorient.TWOWAY:
					param[i].setValue(BalanceBSKey.K_EndDebitQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndDebitAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndDebitLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditQuant,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					param[i].setValue(BalanceBSKey.K_EndCreditLocAmount,ZeroUFdoubleConstant.DFDB_ZERO);
					break;
				}
			}
			return param;
		}
		return null;
	}

	private int[] getComputeSortIndex(GlQueryVO p_qryVO) {
		int intSortIndex[] = null;
		String currTypeName = p_qryVO.getCurrTypeName();

		if (currTypeName != null
				&& currTypeName.equals(nc.gl.account.glconst.CurrTypeConst
						.ALL_CURRTYPE())) {
			/** ���б��� */
			if (!m_qryVO.isMultiCorpCombine()
					&& m_qryVO.getpk_accountingbook().length > 1) {
				// ���ּӿ�Ŀ
				intSortIndex = new int[] { BalanceBSKey.K_FINANCEORGCODE,
						BalanceBSKey.K_PkCurrType, BalanceBSKey.K_AccCode };
			}
			/** �൥λ�ϲ� */
			else {
				if (isBuSupport()) {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						// ���ּӿ�Ŀ
						intSortIndex = new int[] { BalanceBSKey.K_UNITCODE,
								BalanceBSKey.K_PkCurrType,
								BalanceBSKey.K_AccCode };
					} else {
						intSortIndex = new int[] { BalanceBSKey.K_UNITCODE,
								BalanceBSKey.K_PkCurrType,
								BalanceBSKey.K_subjTypeName,
								BalanceBSKey.K_AccCode };
					}
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						// ���ּӿ�Ŀ
						intSortIndex = new int[] { BalanceBSKey.K_PkCurrType,
								BalanceBSKey.K_AccCode };
					} else {
						intSortIndex = new int[] { BalanceBSKey.K_PkCurrType,
								BalanceBSKey.K_subjTypeName,
								BalanceBSKey.K_AccCode };
					}
				}
			}
		} else {
			if (!m_qryVO.isMultiCorpCombine()
					&& m_qryVO.getpk_accountingbook().length > 1) {
				intSortIndex = new int[] { BalanceBSKey.K_FINANCEORGCODE,
						BalanceBSKey.K_AccCode };
			} else {
				if (isBuSupport()) {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						intSortIndex = new int[] { BalanceBSKey.K_UNITCODE,
								BalanceBSKey.K_AccCode };
					} else {
						intSortIndex = new int[] { BalanceBSKey.K_UNITCODE,
								BalanceBSKey.K_subjTypeName,
								BalanceBSKey.K_AccCode };
					}
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						intSortIndex = new int[] { BalanceBSKey.K_AccCode };
					} else {
						intSortIndex = new int[] { BalanceBSKey.K_subjTypeName,
								BalanceBSKey.K_AccCode };
					}
				}
			}
		}
		return intSortIndex;
	}

	private int[] getComPuterSumIndex() {
		int sumIndex[] = { BalanceBSKey.K_InitDebitQuant,
				BalanceBSKey.K_InitDebitAmount,
				BalanceBSKey.K_InitDebitAuxAmount,
				BalanceBSKey.K_InitDebitLocAmount,
				BalanceBSKey.K_InitCreditQuant,
				BalanceBSKey.K_InitCreditAmount,
				BalanceBSKey.K_InitCreditAuxAmount,
				BalanceBSKey.K_InitCreditLocAmount, BalanceBSKey.K_DebitQuant,
				BalanceBSKey.K_DebitAmount, BalanceBSKey.K_DebitAuxAmount,
				BalanceBSKey.K_DebitLocAmount, BalanceBSKey.K_CreditQuant,
				BalanceBSKey.K_CreditAmount, BalanceBSKey.K_CreditAuxAmount,
				BalanceBSKey.K_CreditLocAmount, BalanceBSKey.K_DebitAccumQuant,
				BalanceBSKey.K_DebitAccumAmount,
				BalanceBSKey.K_DebitAccumAuxAmount,
				BalanceBSKey.K_DebitAccumLocAmount,
				BalanceBSKey.K_CreditAccumQuant,
				BalanceBSKey.K_CreditAccumAmount,
				BalanceBSKey.K_CreditAccumAuxAmount,
				BalanceBSKey.K_CreditAccumLocAmount,
				BalanceBSKey.K_EndDebitQuant, BalanceBSKey.K_EndDebitAmount,
				BalanceBSKey.K_EndDebitAuxAmount,
				BalanceBSKey.K_EndDebitLocAmount,
				BalanceBSKey.K_EndCreditQuant, BalanceBSKey.K_EndCreditAmount,
				BalanceBSKey.K_EndCreditAuxAmount,
				BalanceBSKey.K_EndCreditLocAmount };
		return sumIndex;
	}

	// �ϼ���Ϣ
		private String[] getComputerInitSummary(GlQueryVO p_qryVO) {
			String currTypeName = p_qryVO.getCurrTypeName();
			String[] strInitSummary = null;

			if (currTypeName != null
					&& currTypeName
							.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
				if (!m_qryVO.isMultiCorpCombine()
						&& m_qryVO.getpk_accountingbook().length > 1) {
					strInitSummary = new String[] {
							nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
									"UPP20023030-000472")/* @res "�����ʲ��ڳ�" */,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
									"UPP20023030-000475")/* @res "�������" */,subjSum};
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						if(isBuSupport()){
							strInitSummary = new String[] {unitSum,
									nc.ui.ml.NCLangRes.getInstance().getStrByID(
											"20023030", "UPP20023030-000475")/*
																			 * @res
																			 * "�������"
																			 */,subjSum};
						}else{
							strInitSummary = new String[] {
									nc.ui.ml.NCLangRes.getInstance().getStrByID(
											"20023030", "UPP20023030-000475")/*
																			 * @res
																			 * "�������"
																			 */,subjSum};
						}
					} else {
						strInitSummary = new String[] {
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"20023030", "UPP20023030-000475")/*
																		 * @res
																		 * "�������"
																		 */,
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"20023030", "UPP20023030-000476")/*
																		 * @res
																		 * "��Ŀ�������"
																		 */,subjSum};
					}
				}
			} else {
				if (!m_qryVO.isMultiCorpCombine()
						&& m_qryVO.getpk_accountingbook().length > 1) {
					strInitSummary = new String[] {
							nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
									"UPP20023030-000477")/* @res "�����ʲ����" */,
							nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030",
									"UPP20023030-000263") /* @res "��Ŀ�ϼ�" */};
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						if(isBuSupport()){
							strInitSummary = new String[] { unitSum,subjSum};
						}else{
							strInitSummary = new String[] { subjSum};
						}
					} else {
						strInitSummary = new String[] {
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"20023030", "UPP20023030-000476")/*
																		 * @res
																		 * "��Ŀ�������"
																		 */,subjSum};
					}
				}
			}
			return strInitSummary;
		}

		// �ϼ���Ϣ
		// //��ͬ�������㣬��ͬ��ĿҲ�ۼ� 07-09-22
		private String[] getComputerSummary(GlQueryVO p_qryVO) {
			String currTypeName = p_qryVO.getCurrTypeName();
			String[] strSummary = null;

			if (currTypeName != null
					&& currTypeName
							.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE)) {

				if (!m_qryVO.isMultiCorpCombine()
						&& m_qryVO.getpk_accountingbook().length > 1) {

					strSummary = new String[] {bookSum,currSum,subjSum};
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						if(isBuSupport()){
							strSummary = new String[] {unitSum,currSum,subjSum};
						}else{
							strSummary = new String[] {currSum,subjSum};
						}
					} else {
						strSummary = new String[] {currSum,subjTypeSum,subjSum};
					}
				}
			} else {
				if (!m_qryVO.isMultiCorpCombine()
						&& m_qryVO.getpk_accountingbook().length > 1) {
					strSummary = new String[] {bookSum,subjSum};
				} else {
					if (!m_qryVO.getFormatVO().isSumbySubjType()) {
						if(isBuSupport()){
							strSummary = new String[] {unitSum,subjSum};
						}else{
							strSummary = new String[] {subjSum};
						}
					} else {
						strSummary = new String[] {subjTypeSum,subjSum};
					}
				}
			}
			return strSummary;
		}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 19:52:44)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	private BalanceBSVO[] compute(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		Vector vecVO = new Vector();
		if (vos == null || vos.length == 0)
			return null;
		// ����п�Ŀ���С�ƣ��򲹿�Ŀ�����Ϣ��
		vos = fillSubjTypeInfo(vos, p_qryVO);
		for (int i = 0; i < vos.length; i++) {
			vos[i].setUserData(null);
			vecVO.addElement(vos[i]);
		}

		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();

		/** ָ�����ڷ��������к� */

		int intSortIndex[] = null;
		intSortIndex = getComputeSortIndex(p_qryVO);

		genTool.setSortIndex(intSortIndex);
		genTool.setGenRatio(100);
		genTool.setSpecialToolKey(new int[] { BalanceBSKey.K_AccCode });
		genTool.setGenToolElementProvider(this);

		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();
		datasource.setSumVector(nc.vo.glcom.inteltool.CDataSource.sortVector(vecVO, genTool, false));

		genTool.setLimitSumGen(0);

		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		outputTool.setRequireOutputDetail(true); // ��Ϊtrue�����Ͽ�Ŀ�ϼƣ���Ҫ����ԭʼ�Ĵ�����������Ϣ�ļ�¼

		outputTool.setGenTool(genTool);

		/** ���ñ�ע��Ϣ���ݼ�����Ӧ���� */
		outputTool.setInitSummary(getComputerInitSummary(p_qryVO));
		/** ���ñ�ע��Ϣ���ݼ�����Ӧ���� */
		outputTool.setSummary(getComputerSummary(p_qryVO));
		/** ���ñ�ע��Ϣ���ݼ�����Ӧ���� */
		outputTool.setSummaryCol(BalanceBSKey.K_AccName);
		outputTool.setSimpleSummary(false);

		nc.vo.glcom.inteltool.CUFDoubleSumTool sumTool = new nc.vo.glcom.inteltool.CUFDoubleSumTool();
		int sumIndex[] = getComPuterSumIndex();

		/** Ҫ���кϼƵ��� */

		sumTool.setSumIndex(sumIndex);

		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();
		try {
			tt.setGenTool(genTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
			tt.setSumTool(sumTool);
			tt.setTotalSumTool(sumTool);

			Vector recVector = tt.getResultVector();
			vos = new BalanceBSVO[recVector.size()];
			recVector.copyInto(vos);
			return vos;
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}
	}

	private int[] getQryGroupField(GlQueryVO p_qryVO) {
		int[] iGroupField = null;

		String currTypeName = p_qryVO.getCurrTypeName();

		if (currTypeName != null
				&& currTypeName
						.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())){
			if(isBuSupport()){
				iGroupField = new int[] {
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_PK_ACCOUNTINGBOOK,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_UNIT};
			}else{
				iGroupField = new int[] {
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_PK_ACCOUNTINGBOOK,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_CURRTYPE,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID };
			}
		}else{
			if(isBuSupport()){
				iGroupField = new int[] {
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_PK_ACCOUNTINGBOOK,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_UNIT};
			}else{
				iGroupField = new int[] {
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_PK_ACCOUNTINGBOOK,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ACCOUNT,
						nc.vo.glcom.balance.GLQueryKey.K_GLQRY_ASSID };
			}
		}

		return iGroupField;
	}

	private BalanceBSVO[] getJoinedBalancevos(GlBalanceVO[] initBalanceVOs,
			GlBalanceVO[] occurBalanceVOs, GlBalanceVO[] accumOccurBalanceVOs,
			int returnCurrtype) throws Exception {
		BalanceBSVO[] balanceVOs = null;
		nc.vo.glcom.wizard.VoWizard wizard = new nc.vo.glcom.wizard.VoWizard();

		int[] intIndexBalance = new int[] {
				BalanceBSKey.K_PKACCASOA,
				BalanceBSKey.K_PK_ACCOUNTINGBOOK,
				BalanceBSKey.K_PkCurrType,
				BalanceBSKey.K_ASSID,
				BalanceBSKey.K_PKUNIT}; // 07-9-19����
		int[] intIndexInit = new int[] {
				nc.vo.glcom.balance.GlBalanceKey.GLBALANCE_PK_ACCASOA,
				nc.vo.glcom.balance.GlBalanceKey.GLBALANCE_PK_ACCOUNTINGBOOK,
				nc.vo.glcom.balance.GlBalanceKey.GLBALANCE_PK_CURRTYPE,
				nc.vo.glcom.balance.GlBalanceKey.GLBALANCE_ASSID,
				nc.vo.glcom.balance.GlBalanceKey.GLBALANCE_BUSIUNIT}; // 07-9-19����
		wizard.setMatchingIndex(intIndexBalance, intIndexInit);

		wizard.setAppendIndex(new int[] { BalanceBSKey.K_PKACCASOA,
				BalanceBSKey.K_AccCode, BalanceBSKey.K_AccName,
				BalanceBSKey.K_AccOrient, BalanceBSKey.K_BothOrient,
				BalanceBSKey.K_PK_ACCOUNTINGBOOK,BalanceBSKey.K_PKUNIT,
				BalanceBSKey.K_FINANCEORGNAME, BalanceBSKey.K_PkCurrType,
				BalanceBSKey.K_ASSID, BalanceBSKey.K_InitDebitQuant,
				BalanceBSKey.K_InitDebitAmount,
				BalanceBSKey.K_InitDebitAuxAmount,
				BalanceBSKey.K_InitDebitLocAmount,
				BalanceBSKey.K_InitCreditQuant,
				BalanceBSKey.K_InitCreditAmount,
				BalanceBSKey.K_InitCreditAuxAmount,
				BalanceBSKey.K_InitCreditLocAmount }, new int[] {
				GlBalanceKey.GLBALANCE_PK_ACCASOA,
				GlBalanceKey.GLBALANCE_SUBJCODE,
				GlBalanceKey.GLBALANCE_SUBJNAME,
				GlBalanceKey.GLBALANCE_ACCORIENT,
				GlBalanceKey.GLBALANCE_BOTHORITEN,
				GlBalanceKey.GLBALANCE_PK_ACCOUNTINGBOOK,
				GlBalanceKey.GLBALANCE_BUSIUNIT,
				GlBalanceKey.GLBALANCE_GLORGNAME,
				GlBalanceKey.GLBALANCE_PK_CURRTYPE,
				GlBalanceKey.GLBALANCE_ASSID,
				GlBalanceKey.GLBALANCE_DEBITQUANTITY,
				GlBalanceKey.GLBALANCE_DEBITAMOUNT,
				GlBalanceKey.GLBALANCE_FRACDEBITAMOUNT,
				// GlBalanceKey.GLBALANCE_LOCALDEBITAMOUNT,
				GlBalanceKey.getLocDebitKey(returnCurrtype),
				GlBalanceKey.GLBALANCE_CREDITQUANTITY,
				GlBalanceKey.GLBALANCE_CREDITAMOUNT,
				GlBalanceKey.GLBALANCE_FRACCREDITAMOUNT,
				// GlBalanceKey.GLBALANCE_LOCALCREDITAMOUNT
				GlBalanceKey.getLocCreditKey(returnCurrtype),

		});

		wizard.setLeftClass(BalanceBSVO.class);
		// �ڳ�����
		if (initBalanceVOs != null && initBalanceVOs.length != 0) {
			Object[] obj = wizard.concat(balanceVOs, initBalanceVOs, true);
			balanceVOs = new BalanceBSVO[obj.length];
			System.arraycopy(obj, 0, balanceVOs, 0, obj.length);
		}

		// ��������
		if (occurBalanceVOs != null && occurBalanceVOs.length != 0) {
			wizard.setAppendIndex(new int[] { BalanceBSKey.K_PKACCASOA,
					BalanceBSKey.K_AccCode, BalanceBSKey.K_AccName,
					BalanceBSKey.K_AccOrient, BalanceBSKey.K_BothOrient,
					BalanceBSKey.K_PK_ACCOUNTINGBOOK,BalanceBSKey.K_PKUNIT,
					BalanceBSKey.K_FINANCEORGNAME, BalanceBSKey.K_PkCurrType,
					BalanceBSKey.K_ASSID, BalanceBSKey.K_DebitQuant,
					BalanceBSKey.K_DebitAmount, BalanceBSKey.K_DebitAuxAmount,
					BalanceBSKey.K_DebitLocAmount, BalanceBSKey.K_CreditQuant,
					BalanceBSKey.K_CreditAmount,
					BalanceBSKey.K_CreditAuxAmount,
					BalanceBSKey.K_CreditLocAmount }, new int[] {
					GlBalanceKey.GLBALANCE_PK_ACCOUNT,
					GlBalanceKey.GLBALANCE_SUBJCODE,
					GlBalanceKey.GLBALANCE_SUBJNAME,
					GlBalanceKey.GLBALANCE_ACCORIENT,
					GlBalanceKey.GLBALANCE_BOTHORITEN,
					GlBalanceKey.GLBALANCE_PK_ACCOUNTINGBOOK,
					GlBalanceKey.GLBALANCE_BUSIUNIT,
					GlBalanceKey.GLBALANCE_GLORGNAME,
					GlBalanceKey.GLBALANCE_PK_CURRTYPE,
					GlBalanceKey.GLBALANCE_ASSID,
					GlBalanceKey.GLBALANCE_DEBITQUANTITY,
					GlBalanceKey.GLBALANCE_DEBITAMOUNT,
					GlBalanceKey.GLBALANCE_FRACDEBITAMOUNT,
					// GlBalanceKey.GLBALANCE_LOCALDEBITAMOUNT,
					GlBalanceKey.getLocDebitKey(returnCurrtype),
					GlBalanceKey.GLBALANCE_CREDITQUANTITY,
					GlBalanceKey.GLBALANCE_CREDITAMOUNT,
					GlBalanceKey.GLBALANCE_FRACCREDITAMOUNT,
					// GlBalanceKey.GLBALANCE_LOCALCREDITAMOUNT
					GlBalanceKey.getLocCreditKey(returnCurrtype),

			});
			Object[] obj = wizard.concat(balanceVOs, occurBalanceVOs, true);
			balanceVOs = new BalanceBSVO[obj.length];
			System.arraycopy(obj, 0, balanceVOs, 0, obj.length);
		}

		// �ۼƷ�������
		if (accumOccurBalanceVOs != null && accumOccurBalanceVOs.length != 0) {
			wizard.setAppendIndex(new int[] { BalanceBSKey.K_PKACCASOA,
					BalanceBSKey.K_AccCode, BalanceBSKey.K_AccName,
					BalanceBSKey.K_AccOrient, BalanceBSKey.K_BothOrient,
					BalanceBSKey.K_PK_ACCOUNTINGBOOK,BalanceBSKey.K_PKUNIT,
					BalanceBSKey.K_FINANCEORGNAME, BalanceBSKey.K_PkCurrType,
					BalanceBSKey.K_ASSID, BalanceBSKey.K_DebitAccumQuant,
					BalanceBSKey.K_DebitAccumAmount,
					BalanceBSKey.K_DebitAccumAuxAmount,
					BalanceBSKey.K_DebitAccumLocAmount,
					BalanceBSKey.K_CreditAccumQuant,
					BalanceBSKey.K_CreditAccumAmount,
					BalanceBSKey.K_CreditAccumAuxAmount,
					BalanceBSKey.K_CreditAccumLocAmount }, new int[] {
					GlBalanceKey.GLBALANCE_PK_ACCOUNT,
					GlBalanceKey.GLBALANCE_SUBJCODE,
					GlBalanceKey.GLBALANCE_SUBJNAME,
					GlBalanceKey.GLBALANCE_ACCORIENT,
					GlBalanceKey.GLBALANCE_BOTHORITEN,
					GlBalanceKey.GLBALANCE_PK_ACCOUNTINGBOOK,
					GlBalanceKey.GLBALANCE_BUSIUNIT,
					GlBalanceKey.GLBALANCE_GLORGNAME,
					GlBalanceKey.GLBALANCE_PK_CURRTYPE,
					GlBalanceKey.GLBALANCE_ASSID,
					GlBalanceKey.GLBALANCE_DEBITQUANTITY,
					GlBalanceKey.GLBALANCE_DEBITAMOUNT,
					GlBalanceKey.GLBALANCE_FRACDEBITAMOUNT,
					// GlBalanceKey.GLBALANCE_LOCALDEBITAMOUNT,
					GlBalanceKey.getLocDebitKey(returnCurrtype),
					GlBalanceKey.GLBALANCE_CREDITQUANTITY,
					GlBalanceKey.GLBALANCE_CREDITAMOUNT,
					GlBalanceKey.GLBALANCE_FRACCREDITAMOUNT,
					// GlBalanceKey.GLBALANCE_LOCALCREDITAMOUNT
					GlBalanceKey.getLocCreditKey(returnCurrtype) });
			Object[] obj = wizard
					.concat(balanceVOs, accumOccurBalanceVOs, true);
			balanceVOs = new BalanceBSVO[obj.length];
			System.arraycopy(obj, 0, balanceVOs, 0, obj.length);
		}

		return balanceVOs;
	}

	/**
	 * dealQuery ����ע�⡣
	 */
	public Object dealQuery(GlQueryVO p_qryVO) throws Exception {
		accMap.clear();
		m_qryVO = (GlQueryVO) p_qryVO.clone();
		// �����ڳ�����ĩ������������

		p_qryVO.setGroupFields(getQryGroupField(p_qryVO));
		// ���ݲ�ѯ������GLQUERYVO
		// ûɶ��zhaoshya��ֱ��ע�͡�
		// �ڳ�
		GlBalanceVO[] initBalanceVOs = null;
		// ����
		GlBalanceVO[] occurBalanceVOs = null;
		// �ۼƷ���
		GlBalanceVO[] accumOccurBalanceVOs = null;
		//
		Vector vInit = new Vector();
		Vector vOccur = new Vector();
		Vector vAccum = new Vector();
		//for (int i = 0; i < qryVOs.length; i++) {
			// ��̨ȡ��
			BalanceResultVO balanceVo = GLPubProxy
					.getRemoteCommAccBookPub().getBalance((GlQueryVO) p_qryVO.clone());
			// �ڳ�
			initBalanceVOs = balanceVo.bsVo[0];
			for (int j = 0; initBalanceVOs != null && j < initBalanceVOs.length; j++) {
				initBalanceVOs[j].setPeriod(p_qryVO.getPeriod());
				if(StrTools.NULL.equals(initBalanceVOs[j].getAssid())){
					initBalanceVOs[j].setAssID(null);
				}
				vInit.addElement(initBalanceVOs[j]);
			}
			// ����
			occurBalanceVOs = balanceVo.bsVo[1];
			for (int j = 0; occurBalanceVOs != null
					&& j < occurBalanceVOs.length; j++) {
				if(StrTools.NULL.equals(occurBalanceVOs[j].getAssid())){
					occurBalanceVOs[j].setAssID(null);
				}
				vOccur.addElement(occurBalanceVOs[j]);
			}
			// �ۼƷ�����
			accumOccurBalanceVOs = balanceVo.bsVo[2];
			for (int j = 0; accumOccurBalanceVOs != null
					&& j < accumOccurBalanceVOs.length; j++) {
				if(StrTools.NULL.equals(accumOccurBalanceVOs[j].getAssid())){
					accumOccurBalanceVOs[j].setAssID(null);
				}
				vAccum.addElement(accumOccurBalanceVOs[j]);
			}
		//}
		initBalanceVOs = new GlBalanceVO[vInit.size()];
		occurBalanceVOs = new GlBalanceVO[vOccur.size()];
		accumOccurBalanceVOs = new GlBalanceVO[vAccum.size()];
		
		vInit.copyInto(initBalanceVOs);
		vOccur.copyInto(occurBalanceVOs);
		vAccum.copyInto(accumOccurBalanceVOs);

		BalanceBSVO[] balanceVOs = null;
		balanceVOs = getJoinedBalancevos(initBalanceVOs, occurBalanceVOs,
				accumOccurBalanceVOs, p_qryVO.getM_Return_CurrType());
		// ��������޷����ռ�¼
		balanceVOs = addNoBalanceAndNoOccor(balanceVOs, p_qryVO);
		// ɾ�޷�����¼
		balanceVOs = filterOfNoOccor(balanceVOs, p_qryVO);
		
		if(balanceVOs!=null&&balanceVOs.length>0){
			HashSet<String> pkset = new HashSet<String>();
			for(BalanceBSVO vo : balanceVOs){
				if (!StrTools.isEmptyStr((String)vo.getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK))) {
					pkset.add(vo.getValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK).toString());
				}
			}
			for(String key : pkset){
				if(!StrTools.isEmptyStr(key)){
					AccountVO[] accVos = AccountUtilGL.queryAccountVOs(key, p_qryVO.getSubjVersion());
					for(AccountVO vo : accVos){
						if(vo!=null){
							accMap.put(vo.getCode(), vo);
							accMap.put(vo.getPk_accasoa(), vo);
						}
					}
				}
			}
			

		}
			
			
		
		// ����ĩ���
		balanceVOs = endBalance(balanceVOs, p_qryVO);
		// ���丨������AssVO[]������
		balanceVOs = addAssVOAndSort(balanceVOs, p_qryVO);
		// �������
		balanceVOs = filterByBalanceOrient(balanceVOs, p_qryVO);
		// ���˫����ʾʱ�������������(ĩ����Ŀ����)
		if (p_qryVO.getFormatVO().isTwoWayBalance()) {
			balanceVOs = twoWayBalance(balanceVOs, true);
		}
		// �������ʲ���Ϣ
		String[] pk_temporgbooks = p_qryVO.getpk_accountingbook();

		if (pk_temporgbooks.length > 1 && !p_qryVO.isMultiCorpCombine()) {
			balanceVOs = adjustGlorgbook(balanceVOs, p_qryVO);
		}
		
		balanceVOs = filterMoreAcc(balanceVOs, p_qryVO);
		
		if (isBuSupport()) {
			adjustUnit(balanceVOs, p_qryVO);
			balanceVOs = processBusiUnit(balanceVOs);
		}
		
		// С�ƣ��ϼơ�
		balanceVOs = compute(balanceVOs, p_qryVO);
		// ���˫����ʾʱ�������������(ȫ������)
		if (p_qryVO.getFormatVO().isTwoWayBalance()) {
			balanceVOs = twoWayBalance(balanceVOs, false);

			DoWhlTwoBlc(balanceVOs, p_qryVO);
		}
		// ��������Ϣ
		balanceVOs = adjustCurrType(balanceVOs, p_qryVO);

		// ����ϼ���Ŀ��¼
		balanceVOs = adjustSubjSumBSVO(balanceVOs, p_qryVO);
		
		if (!p_qryVO.isMultiCorpCombine()) {
			balanceVOs = adjustSubjNameByCode(balanceVOs, p_qryVO);
			//��Ŀ������ʾ���ն������˲���ʾ  modified by weiningc
//			balanceVOs = adjustPkSubjByCode(balanceVOs, p_qryVO,accMap);
		} else {
			balanceVOs = adjustPkSubjByCode(balanceVOs, p_qryVO,accMap);
		}
		// �޳���������
		balanceVOs = adjustContent(balanceVOs, p_qryVO);
		// ȥ���ǻ�׼��λû�еĿ�Ŀ��¼
		if (p_qryVO.isShowZeroAmountRec()) {
			balanceVOs = filterOfSubj(balanceVOs);
		}
		//���µ�������
		balanceVOs = sortByCondition(balanceVOs, p_qryVO);

		// ����ϼ��еĸ�������
		balanceVOs = adjustAssInfo(balanceVOs);
		
		//������ϲ�����bug������1001��Ŀ�ϼ�  ���޷���ɿ�Ŀ����+��Ŀ�ϼƣ���ߵ�����
		if (p_qryVO.isMultiCorpCombine()) {
			balanceVOs = adjustSumAccMultiCorpCombine(balanceVOs,p_qryVO,accMap);
			sortMultBookByCondition(balanceVOs, p_qryVO);
		}
		if (balanceVOs!=null&&balanceVOs.length>0) {
			for(int i=0;i<balanceVOs.length;i++){
				BalanceBSVO vo = balanceVOs[i];
				Object subName = vo.getValue(BalanceBSKey.K_AccName);
				if(subName==null||subName.equals("")){
					Object subCode = vo.getValue(BalanceBSKey.K_AccCode);
					String pk_accountingbook = (String)vo.getValue(BalanceBSKey.K_AccName);
					if(pk_accountingbook==null||pk_accountingbook.equals("")){
						pk_accountingbook = p_qryVO.getBaseAccountingbook();
					}
					if(subCode!=null&&!subCode.equals("")){
						Pattern pattern = Pattern.compile("[0-9]*");
						if(pattern.matcher(subCode.toString()).matches()){
//							AccountVO accVO = AccountCache.getInstance().getAccountVOByCode(
//									pk_accountingbook, subCode.toString(), p_qryVO.getSubjVersion());
							AccountVO accVO = getAccountVOFromAccMap((String)vo.getValue(BalanceBSKey.K_PKACCASOA),
									(String)vo.getValue(BalanceBSKey.K_AccCode));
							if(accVO!=null){
								vo.setValue(BalanceBSKey.K_AccName, GLMultiLangUtil.getMultiDispName(accVO)+subjSum);
							}else{
								vo.setValue(BalanceBSKey.K_AccName, balanceVOs[i+1].getValue(BalanceBSKey.K_AccName)+subjSum);
							}
						}
					}
				}
				//del chenth 20161210  ������ҲҪ��ʾ���������   delete by weiningc  ���ﲻ������   20171019  start
				if(vo.getValue(BalanceBSKey.K_AccName)!=null){
					if(vo.getValue(BalanceBSKey.K_AccName).toString().indexOf(accSum)>=0
							||vo.getValue(BalanceBSKey.K_AccName).toString().indexOf(subjSum)>=0){
//						vo.setValue(BalanceBSKey.K_AccCode,vo.getValue(BalanceBSKey.K_AccName)
//								.toString().replace(accSum, nc.ui.ml.NCLangRes.getInstance()
//										.getStrByID("20023030", "UPP20023030-001124")  /*@res "��Ŀ����" */));
						String subjname = GLMultiLangUtil.getMultiDispName(getAccountVOFromAccMap((String)vo.getValue(BalanceBSKey.K_PKACCASOA),
								(String)vo.getValue(BalanceBSKey.K_AccCode))) + nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030", "UPP20023030-001124")  /*@res "��Ŀ����" */;
						vo.setValue(BalanceBSKey.K_AccName, subjname);
					}
				}
				//del chenth 20161210  ������ҲҪ��ʾ���������   delete by weiningc  ���ﲻ������   20171019  end
			}
		}
		m_dataVos = balanceVOs;
		return m_dataVos;
	}
	
	/**
	 * ��������������������
	 * @param balanceVOs
	 * @param p_qryVO
	 * @return
	 * @throws Exception
	 */
	private BalanceBSVO[] sortByCondition(BalanceBSVO[] balanceVOs,GlQueryVO p_qryVO) throws Exception{
		String currTypeName = p_qryVO.getCurrTypeName();
		// ��Ŀ����С��ʱ����������
		if (p_qryVO.getFormatVO().isSumbySubjType()) {
			int iSortIndex[] = null;
			if (p_qryVO.getFormatVO().isSumbySubjType()) {
				if (currTypeName != null
						&& currTypeName.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
					iSortIndex = new int[] { BalanceBSKey.K_PkCurrType,BalanceBSKey.K_subjTypeIndex,BalanceBSKey.K_AccCode,BalanceBSKey.K_AccName,BalanceBSKey.K_ASSVOS };
				} else {
					iSortIndex = new int[] { BalanceBSKey.K_subjTypeIndex,BalanceBSKey.K_AccCode,BalanceBSKey.K_AccName,BalanceBSKey.K_ASSVOS };
				}
			}
			for(BalanceBSVO vo : balanceVOs){
				String strName = (String)vo.getValue(BalanceBSKey.K_AccCode);
				if(strName!=null&&(strName.indexOf(accSum)>=0
						||strName.indexOf(subjSum)>=0)){
					vo.setValue(BalanceBSKey.K_AccCode, vo.getValue(BalanceBSKey.K_AccName));
					vo.setValue(BalanceBSKey.K_AccName, strName);
				}
			}
			sort(balanceVOs, iSortIndex);
			for(BalanceBSVO vo : balanceVOs){
				String strName = (String)vo.getValue(BalanceBSKey.K_AccName);
				if(strName!=null&&(strName.indexOf(accSum)>=0)){
					//vo.setValue(BalanceBSKey.K_AccCode,strName);
					vo.setValue(BalanceBSKey.K_AccName, strName);
					vo.setValue(BalanceBSKey.K_ASSVOS, null);
				}else if(strName!=null&&(strName.indexOf(subjSum)>=0)){
					vo.setValue(BalanceBSKey.K_AccCode,strName);
					vo.setValue(BalanceBSKey.K_AccName, "");
					vo.setValue(BalanceBSKey.K_Remark, "");
				}
			}
		} else if (currTypeName != null
				&& currTypeName.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())
				&& !p_qryVO.getFormatVO().isCurrTypePlusSubj()) {
			if (balanceVOs != null && balanceVOs.length > 0) {
				BalanceBSVO aSumUpVo = balanceVOs[balanceVOs.length - 1];
				Vector vsubjPlusCurr = new Vector();
				for (int i = 0; i < balanceVOs.length - 1; i++) {
					if (!balanceVOs[i].getValue(BalanceBSKey.K_AccCode).toString()
							.equals(currSum)
							// �ɸ��������ѯ����ġ�xx��Ŀ�ϼơ��ж���
							&& balanceVOs[i].getValue(BalanceBSKey.K_AccCode)
									.toString().indexOf(subjSum) < 0
					        &&!"".equals(balanceVOs[i].getValue(BalanceBSKey.K_AccName))) {
						vsubjPlusCurr.addElement(balanceVOs[i]);
					}
				}
				BalanceBSVO[] vos0 = new BalanceBSVO[vsubjPlusCurr.size()];
				vsubjPlusCurr.copyInto(vos0);
				// ��Ŀ�ϼ�֮ǰ����Ŀ+��������+��������
				int[] intSortIndex = new int[] { BalanceBSKey.K_AccCode,BalanceBSKey.K_ASSVOS, BalanceBSKey.K_PkCurrType };
				for(BalanceBSVO vo : vos0){
					String strCode = (String)vo.getValue(BalanceBSKey.K_AccCode);
					if(strCode!=null&&strCode.indexOf(accSum)>=0){
						vo.setValue(BalanceBSKey.K_AccCode, vo.getValue(BalanceBSKey.K_AccName));
						vo.setValue(BalanceBSKey.K_AccName, strCode);
					}
				}
				sort(vos0, intSortIndex);
				vos0 = accSubjPlusCurrCompute(vos0);
				vos0[vos0.length - 1] = aSumUpVo;
				for(BalanceBSVO vo : vos0){
					String strName = (String)vo.getValue(BalanceBSKey.K_AccName);
					if(strName!=null&&strName.indexOf(accSum)>=0
							&&!subjSum.equals(vo.getValue(BalanceBSKey.K_AccCode))){
						vo.setValue(BalanceBSKey.K_AccName, "");
						vo.setValue(BalanceBSKey.K_AccCode, strName);
					}else if(strName!=null&&strName.indexOf(accSum)>=0
							&&subjSum.equals(vo.getValue(BalanceBSKey.K_AccCode))){
						vo.setValue(BalanceBSKey.K_AccName, "");
					}
				}
				balanceVOs = vos0;

			}
		}else{
			//if(p_qryVO.isShowUpSubj()){
			/**
			 * ��Ŀ�ϼ������Ҫ����Ϊ�˱�֤�����ڿ�Ŀ���棬��Ҫ�ѱ��룬���Ƶ�����
			 * ͬʱ���� ��Ŀ���룬��Ŀ���ƣ�������������
			 */
				for(BalanceBSVO vo : balanceVOs){
					String strCode = (String)vo.getValue(BalanceBSKey.K_AccCode);
					if(strCode!=null&&strCode.indexOf(accSum)>=0){
						vo.setValue(BalanceBSKey.K_AccCode, vo.getValue(BalanceBSKey.K_AccName));
						vo.setValue(BalanceBSKey.K_AccName, "");
						vo.setValue(BalanceBSKey.K_Remark, strCode);
					}else if(strCode!=null&&strCode.indexOf(subjSum)>=0){
						vo.setValue(BalanceBSKey.K_AccCode, vo.getValue(BalanceBSKey.K_AccName));
						vo.setValue(BalanceBSKey.K_AccName, "0000");//����ط�������zsyzsy ��ȷ����Ŀ�ϼƽ����ſ�Ŀ
						vo.setValue(BalanceBSKey.K_Remark, strCode);
					}else{
						vo.setValue(BalanceBSKey.K_AccName, "zsyzsy"+vo.getValue(BalanceBSKey.K_AccName));
					}
				}
				int[] sortIndex = getComputeSortIndex(p_qryVO);
				int[] newSortIndex = new int[sortIndex.length+2];
				for(int i=0;i<sortIndex.length;i++){
					newSortIndex[i] = sortIndex[i];
				}
				newSortIndex[newSortIndex.length-2] = BalanceBSKey.K_AccName;
				newSortIndex[newSortIndex.length-1] = BalanceBSKey.K_ASSVOS;
				sort(balanceVOs,newSortIndex);
				for(BalanceBSVO vo : balanceVOs){
					String strmarkName = (String)vo.getValue(BalanceBSKey.K_Remark);
					if(strmarkName!=null&&(strmarkName.indexOf(subjSum)>=0)){
						vo.setValue(BalanceBSKey.K_AccCode,strmarkName);
						vo.setValue(BalanceBSKey.K_AccName, "");
						vo.setValue(BalanceBSKey.K_Remark, "");
					}else if(strmarkName!=null&&(strmarkName.indexOf(accSum)>=0)){
						//vo.setValue(BalanceBSKey.K_AccCode,strmarkName);
						vo.setValue(BalanceBSKey.K_AccName, strmarkName);
						vo.setValue(BalanceBSKey.K_Remark, "");
					}else{
						String accName = (String)vo.getValue(BalanceBSKey.K_AccName);
						if(accName!=null){
							vo.setValue(BalanceBSKey.K_AccName,accName.replace("zsyzsy", "") );
						}
					}
				}
			//}
		}
		return balanceVOs;
	}
	
	/**
	 * ��������������������
	 * @param balanceVOs
	 * @param p_qryVO
	 * @return
	 * @throws Exception
	 */
	private BalanceBSVO[] sortMultBookByCondition(BalanceBSVO[] balanceVOs,GlQueryVO p_qryVO) throws Exception{
	/**
	 * ��Ŀ�ϼ������Ҫ����Ϊ�˱�֤�����ڿ�Ŀ���棬��Ҫ�ѱ��룬���Ƶ�����
	 * ͬʱ���� ��Ŀ���룬��Ŀ���ƣ�������������
	 */
		for(BalanceBSVO vo : balanceVOs){
			Object subName = vo.getValue(BalanceBSKey.K_AccName);
			if(subName==null||subName.equals("")){
				Object subCode = vo.getValue(BalanceBSKey.K_AccCode);
				if(subCode!=null&&!subCode.equals("")){
					Pattern pattern = Pattern.compile("[0-9]*");
					if(pattern.matcher(subCode.toString()).matches()){
						vo.setValue(BalanceBSKey.K_Remark, "subjSum"+subCode);
						vo.setValue(BalanceBSKey.K_AccName, "0000");
					}
				}
			}else if(subName!=null&&subName.toString().indexOf(accSum)>=0){
				vo.setValue(BalanceBSKey.K_Remark, subName);
				vo.setValue(BalanceBSKey.K_AccName, "");
			}else{
				vo.setValue(BalanceBSKey.K_AccName, "zsyzsy"+vo.getValue(BalanceBSKey.K_AccName));
			}
		}
		int[] sortIndex = getComputeSortIndex(p_qryVO);
		int[] newSortIndex = new int[sortIndex.length+2];
		for(int i=0;i<sortIndex.length;i++){
			newSortIndex[i] = sortIndex[i];
		}
		newSortIndex[newSortIndex.length-2] = BalanceBSKey.K_AccName;
		newSortIndex[newSortIndex.length-1] = BalanceBSKey.K_ASSVOS;
		sort(balanceVOs,newSortIndex);
		for(BalanceBSVO vo : balanceVOs){
			String strmarkName = (String)vo.getValue(BalanceBSKey.K_Remark);
			if(strmarkName!=null&&(strmarkName.indexOf("subjSum")>=0)){
				vo.setValue(BalanceBSKey.K_AccCode,strmarkName.replace("subjSum", ""));
				vo.setValue(BalanceBSKey.K_AccName, "");
				vo.setValue(BalanceBSKey.K_Remark, "");
			}else if(strmarkName!=null&&(strmarkName.indexOf(accSum)>=0)){
				vo.setValue(BalanceBSKey.K_AccName, strmarkName);
				vo.setValue(BalanceBSKey.K_Remark, "");
			}else{
				String accName = (String)vo.getValue(BalanceBSKey.K_AccName);
				if(accName!=null){
					vo.setValue(BalanceBSKey.K_AccName,accName.replace("zsyzsy", "") );
				}
			}
		}
		return balanceVOs;
	}
	
	//���˶��������
	private BalanceBSVO[] filterMoreAcc(BalanceBSVO[] balanceVOs,GlQueryVO p_qryVO) throws Exception{
		//ȥ��ûѡ�Ŀ�Ŀ�� ����Ŀ����һ����1001 100101 100102
		//�����ѡ1001��100101  ��ô100102�����ݲ���Ҫ�㵽1001��
		if(balanceVOs!=null&&balanceVOs.length>0){
			List<BalanceBSVO> result = new ArrayList<BalanceBSVO>();
			String[] subjsCode = p_qryVO.getAccountCode();
			Vector vecTemp = new Vector();
			for (int i = 0; i < subjsCode.length; i++) {
				vecTemp.addElement(subjsCode[i]);
			}
			for(int i=0;i<balanceVOs.length;i++){
				String strCode = (String) balanceVOs[i].getValue(BalanceBSKey.K_AccCode);
				if("listOrg".equals(p_qryVO.getQueryType())){
					if(p_qryVO.getAssVos()==null){
						if(strCode!=null&&vecTemp.contains(strCode)
								&&"000000just_for_sumtool".equals(balanceVOs[i].getValue(BalanceBSKey.K_ASSID))){
							result.add(balanceVOs[i]);
						}	
					}else{
						if(strCode!=null&&vecTemp.contains(strCode)){
							result.add(balanceVOs[i]);
						}		
					}
				}else{
					if(strCode!=null&&vecTemp.contains(strCode)){
						result.add(balanceVOs[i]);
					}
				}
			}
			balanceVOs = result.toArray(new BalanceBSVO[0]);
		}
		return balanceVOs;
	}
	
	/**
	 * ������ϲ���ѯ��ʱ�����bug�� ��Ҫ���Ǹ����ܹ������⣬
	 * 2���˲��ֱ� ��Ŀ  1001           ����һ��ֻ��1001 ���ҿ�ʼ�����������㣬�����ӵ�2�������¼���ˡ�
	 * 				   100101
	 * 					100102   ���������������Ļ�   1001�ϼƾͻᶪʧ
	 * 
	 * ������Щ��ͨ�����Ѿ����ںϼ��ˣ����ֲ���Ҫ�ٴκϼƣ�����������
	 * @param balanceVOs
	 * @param p_qryVO
	 * @throws Exception
	 */
	private BalanceBSVO[] adjustSumAccMultiCorpCombine(BalanceBSVO[] balanceVOs,GlQueryVO p_qryVO,HashMap<String, AccountVO> accMap) throws Exception{
		List<BalanceBSVO> balList = new ArrayList<BalanceBSVO>();
		BalanceBSVO compute = null;
		String preSubjCode = "";
		//��������Ҫ�ǲ������������ȱ�ٿ�Ŀ�ϼƣ�����Ѿ��кϼƵľͲ��ڻ��ܿ�Ŀ�ϼ�
		Set<String> hasSum = new HashSet<String>();
		for(BalanceBSVO vo :balanceVOs){
			//ǰ��adjustcontent�����Ѿ�������Ŀ�ϼ���Ϊ�� ��Ŀ����Ϳ�Ŀ����Ϊ�ա�  �������������ݾ���Ϊ�ǿ�Ŀ�ϼ��С�
			Object subName = vo.getValue(BalanceBSKey.K_AccName);
			if(subName==null||subName.equals("")){
				Object subCode = vo.getValue(BalanceBSKey.K_AccCode);
				if(subCode!=null&&!subCode.equals("")){
					Pattern pattern = Pattern.compile("[0-9]*");
					if(pattern.matcher(subCode.toString()).matches()){
						hasSum.add(subCode.toString());
					}
				}
			}
		}
		if (balanceVOs != null && balanceVOs.length > 0) {
			for (int i = 0; i < balanceVOs.length; i++) {
				if (balanceVOs[i].getValue(BalanceBSKey.K_AccCode) != null) {
					String subjcode = balanceVOs[i].getValue(BalanceBSKey.K_AccCode).toString();
					String code = balanceVOs[i].getValue(BalanceBSKey.K_AccCode).toString();
					String subjName = (String)balanceVOs[i].getValue(BalanceBSKey.K_AccName);
					subjcode=subjcode.replaceFirst(accSum,"");
					subjcode=subjcode.replaceFirst(subjSum,"");
					AccountVO acc =	accMap.get(subjcode);
					if(acc!=null&&subjName!=null&&subjName.length()>0){
						balanceVOs[i].setValue(BalanceBSKey.K_AccName,
										GLMultiLangUtil.getMultiDispName(acc));
					}
					if(subjName.indexOf(accSum)>=0){
						//��Ŀ���������ÿ�Ŀ���������
						String subjname = null;
						if (acc!= null) {
							subjname = GLMultiLangUtil.getMultiDispName(acc);
							balanceVOs[i].setValue(BalanceBSKey.K_AccCode,subjcode);
							balanceVOs[i].setValue(BalanceBSKey.K_AccName,
										subjname+accSum);
						}
					}
					if(preSubjCode!=null&&preSubjCode.length()>0
							&&code.indexOf(subjSum)<0){
						//���б������һ�б���һ�£����ۼ�
						if(code.equals(preSubjCode)){
							addVO(compute, balanceVOs[i]);
						}else{
							//ũ�����󣺱��б������һ�б��벻ͬ���ұ��б��벻�ǿ�Ŀ�ϼ�������
							//����ũ�Ѻ�����Ҫ��ѿ�Ŀ�ϼ�Ų������Ŀ�����棬������������hasSum�жϡ�
							if(code.indexOf(subjSum)<0){
								if(compute!=null&&balanceVOs[i-1].getValue(BalanceBSKey.K_ASSVOS)!=null
										&&!hasSum.contains(compute.getValue(BalanceBSKey.K_AccCode)))
									balList.add(compute);
							}
							compute = null;
						}
					}
					balList.add(balanceVOs[i]);
					if(subjName!=null
							&&subjName.length()>0
							&&compute==null
							&&subjName.indexOf(subjSum)<0
							&&subjName.indexOf(accSum)<0){
						preSubjCode = code;
						compute = (BalanceBSVO)balanceVOs[i].clone();
						compute.setValue(BalanceBSKey.K_ASSID, null);
						compute.setValue(BalanceBSKey.K_ASSVOS, null);
						if(acc!=null&&subjName!=null&&subjName.length()>0){
							compute.setValue(BalanceBSKey.K_AccCode, acc.getCode());
						}
						compute.setValue(BalanceBSKey.K_AccName, "");
					}
				}else{
					balList.add(balanceVOs[i]);
				}
			}
		}
		return balList.toArray(new BalanceBSVO[0]);
	}


	public String getCheckValueType(AssVO[] assvos) {
		String[] list = null;
		HashMap<String, AssVO> assmap = new HashMap<String, AssVO>();
		list = new String[assvos.length];
		for (int k = 0; k < assvos.length; k++) {
			assmap.put(assvos[k].getPk_Checktype().trim(), assvos[k]);
			list[k] = assvos[k].getPk_Checktype() == null ? "": assvos[k].getPk_Checktype().trim();
		}
		java.util.Arrays.sort(list);
		StringBuffer paramValue = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			paramValue.append((((AssVO) assmap.get(list[i])).getPk_Checktype() == null ? ""
							: ((AssVO) assmap.get(list[i])).getPk_Checktype().trim())
							+ (((AssVO) assmap.get(list[i])).getPk_Checkvalue() == null ? ""
									: ((AssVO) assmap.get(list[i])).getPk_Checkvalue().trim()));
		}
		return paramValue.toString().trim();
	}

	private BalanceBSVO[] resetAssID(BalanceBSVO[] vos) throws Exception {
		if (vos == null || vos.length == 0) {
			return vos;
		}
		HashMap<String, String> assmap = new HashMap<String, String>();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getValue(BalanceBSKey.K_ASSVOS) != null
					&& ((AssVO[]) vos[i].getValue(BalanceBSKey.K_ASSVOS)).length > 0) {
				assmap.put(getCheckValueType((AssVO[]) vos[i]
						.getValue(BalanceBSKey.K_ASSVOS)), (String) vos[i]
						.getValue(BalanceBSKey.K_ASSID));
			}
		}
		for (int j = 0; j < vos.length; j++) {
			if (vos[j].getValue(BalanceBSKey.K_ASSVOS) != null
					&& ((AssVO[]) vos[j].getValue(BalanceBSKey.K_ASSVOS)).length > 0) {
				String assid = assmap.get(getCheckValueType((AssVO[]) vos[j]
						.getValue(BalanceBSKey.K_ASSVOS)));
				vos[j].setValue(BalanceBSKey.K_ASSID, assid);
			}
		}

		return vos;
	}

	/**
	 * ��ÿ����¼���丨������AssVO������Ϣ������Ŀ+������������
	 * 
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	private BalanceBSVO[] addAssVOAndSort(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		if (vos == null || vos.length == 0) {
			return null;
		}
		String assid = null;
		String subjCode = "";

		ArrayList<String> assidList = new ArrayList<String>();
		String[] assids = null; // ��������id
		for (int i = 0; i < vos.length; i++) {
			assid = (String) vos[i].getValue(BalanceBSKey.K_ASSID);
			subjCode = (String) vos[i].getValue(BalanceBSKey.K_AccCode);
			if (assid != null
					&& !assid.trim().equals("000000just_for_sumtool")
					&& subjCode.indexOf(allSum) < 0) {
				if (!assidList.contains(assid)) // �����ظ����
					assidList.add(assid);
			}
		}
		if (assidList.size() > 0) {
			assids = new String[assidList.size()];
			assidList.toArray(assids);
			// һ�β�ѯ���еĸ�������vo
			GlAssVO[] glAssvos = GLPubProxy.getRemoteFreevaluePub()
					.queryAllByIDs(assids, null, Module.GL);
			for (int i = 0; i < vos.length; i++) {
				assid = (String) vos[i].getValue(BalanceBSKey.K_ASSID);
				subjCode = (String) vos[i].getValue(BalanceBSKey.K_AccCode);
				if (assid != null
						&& !assid.trim().equals("000000just_for_sumtool")
						&& subjCode.indexOf(allSum) < 0) {
					for (int j = 0; glAssvos != null && j < glAssvos.length; j++) {
						if (glAssvos[j].getAssID().equals(assid)) {
							vos[i].setValue(BalanceBSKey.K_ASSVOS,
									glAssvos[j].getAssVos());
							break;
						}
					}
				}
			}
		}
		// �������ϲ� added By liyongru For V506 at 20090609
		vos = resetAssID(vos);
		// ����
		int[] intSortIndex = new int[] { BalanceBSKey.K_AccCode,BalanceBSKey.K_ASSVOS };
		sort(vos, intSortIndex);
		vos = combineAss(vos, p_qryVO);
		return vos;
	}

	/**
	 * �Բ�ѯ�������ϼ��еĸ�������
	 * 
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	private BalanceBSVO[] adjustAssInfo(BalanceBSVO[] vos) throws Exception {
		if (vos == null || vos.length == 0) {
			return null;
		}
		for (int j = 0; vos != null && j < vos.length; j++) {
			String strName = (String) vos[j].getValue(BalanceBSKey.K_AccCode);
			String subjName = (String) vos[j].getValue(BalanceBSKey.K_AccName);
			if (strName.indexOf(accSum) >= 0 || strName.indexOf(currSum) >= 0
					|| strName.indexOf(allSum) >= 0
					|| strName.indexOf(subjTypeSum) >= 0
					|| strName.indexOf(bookSum) >= 0
					|| strName.indexOf(unitSum) >= 0
					|| "".equals(subjName)) {
				vos[j].setValue(BalanceBSKey.K_ASSVOS, null); // ����ϼ��еĸ�������
			}
			if (strName.indexOf(allSum) >= 0) {
				vos[j].setValue(BalanceBSKey.K_FINANCEORGNAME, null); // ����ܼ��еĺ����˲�
				vos[j].setValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK, null); // ����ܼ��еĺ����˲�
			}
		}
		return vos;
	}

	/**
	 * 1.�����ѯ����ѡ��ķǶ�����Ŀʵ�ʷ��������ݣ�����ܵ��ϼ���Ŀ��������computeֻ���������ϼ���Ŀ���ϼơ���¼����Ҫ����Ϊ�ϼ���Ŀ��¼
	 * ����ѡ���100901��Ŀ��Ӧ���ܵ�1009��Ŀ�������һ����¼����1009��Ŀ�ϼ� -- --�����ֽ׶�������Ҫ����1009 --
	 * --����Ҫ����ǰ��Ϊ���� 2.û�и�������Ŀ�Ŀ����ʾ��Ŀ����
	 * 
	 * ע�⣺���˲��ϲ���ѯ �� ��Ӧ�Ŀ�Ŀ���β�һ���п��ܻ������
	 * 
	 * A�˲�    1403  ����������
	 * B�˲�   140301 140302  ����������    
	 * �ϲ���ѯʱ ������1��1403��Ŀ�ϼƣ� ����������2���˲��ĺϼ����� ���Ҳ�ĳ��˿�Ŀ����
	 * 
	 */
	private BalanceBSVO[] adjustSubjSumBSVO(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		if (vos != null && vos.length > 0) {
			String subjSum = nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000263")/** @res* "��Ŀ�ϼ�"*/;
			ArrayList<BalanceBSVO> bbsList = new ArrayList<BalanceBSVO>();
			for (int i = 0; i < vos.length; i++) {
				String strName = (String) vos[i]
						.getValue(BalanceBSKey.K_AccName);
				if (i < (vos.length - 2) //�����ڶ��в����жϡ�
						&& strName.indexOf(subjSum) >= 0 //�Ƿ��ǿ�Ŀ�ϼ���
						//��һ�л��ǿ�Ŀ�ϼƣ���ô����϶����ϼ���Ŀ��
						&& vos[i + 1].getValue(BalanceBSKey.K_AccName).toString().indexOf(subjSum) >= 0) {
					int startSumVOIndex = i + 1;
					String nameTemp = "";
					do // �ϼ���Ŀ�ϼƼ�¼����   ����14010101  ��140101��1401�����ϼ� ʹ��do while��ѭ����
					{
						BalanceBSVO upSubjBlnBSVO = vos[startSumVOIndex];
						// ȥ������Ŀ�ϼơ� 631��ʼ��Ҫ��ʾ�ϼ���Ŀ�� ��߸ĳɿ�Ŀ���ܣ����˲��ҿ�Ŀ���β�һ��ʱ�������֡�
						nameTemp = upSubjBlnBSVO.getValue(BalanceBSKey.K_AccName).toString().replaceFirst(subjSum,accSum);
						upSubjBlnBSVO.setValue(BalanceBSKey.K_AccName, nameTemp);
						upSubjBlnBSVO.setValue(BalanceBSKey.K_ASSID,"000000just_for_sumtool");
						startSumVOIndex++;
					} while (startSumVOIndex < vos.length
							&& vos[startSumVOIndex].getValue(BalanceBSKey.K_AccName).toString().indexOf(subjSum) >= 0);
				}
				// û�и�������Ŀ�Ŀ�����п�Ŀ����
				if (!(vos[i].getValue(BalanceBSKey.K_ASSID) != null
						&& vos[i].getValue(BalanceBSKey.K_ASSID).equals("000000just_for_sumtool") 
						&& strName.indexOf(subjSum) >= 0)){
					bbsList.add(vos[i]);
				}
			}
			BalanceBSVO[] newvos = new BalanceBSVO[bbsList.size()];
			bbsList.toArray(newvos);
			return newvos;
		}
		return null;
	}

	/**
	 * ���������ϼƼ�¼�� �������ڣ�(2001-10-8 19:52:44) WJ MODIFYED 2006-07-25
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	private BalanceBSVO[] endBalance(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		Vector vecVO = new Vector();
		if (vos == null || vos.length == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			// ����¼��Ϊnull��assid������ʱ���ݣ��Ա�����ʹ��sumTool
			if (vos[i].getValue(BalanceBSKey.K_ASSID) == null)
				vos[i].setValue(BalanceBSKey.K_ASSID, "000000just_for_sumtool");
			vecVO.addElement(vos[i]);
		}

		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();

		/** ָ�����ڷ��������к� */
		int intSortIndex[] = null;
		String currTypeName = p_qryVO.getCurrTypeName();

		if (currTypeName != null
				&& currTypeName
						.equals(nc.gl.account.glconst.CurrTypeConst.ALL_CURRTYPE())) {
			/** ���б��� */
			// ����BalanceBSKey.K_ASSID��2007-9-19
			if(isBuSupport()){
				intSortIndex = new int[] { BalanceBSKey.K_PK_ACCOUNTINGBOOK,BalanceBSKey.K_PKUNIT,
						BalanceBSKey.K_PkCurrType, BalanceBSKey.K_AccCode,
						BalanceBSKey.K_ASSID };
			}else{
				intSortIndex = new int[] { BalanceBSKey.K_PK_ACCOUNTINGBOOK,
						BalanceBSKey.K_PkCurrType, BalanceBSKey.K_AccCode,
						BalanceBSKey.K_ASSID };
			}
		} else {
			if(isBuSupport()){
				intSortIndex = new int[] { BalanceBSKey.K_PK_ACCOUNTINGBOOK,BalanceBSKey.K_PKUNIT,
						BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID };
			}else{
				intSortIndex = new int[] { BalanceBSKey.K_PK_ACCOUNTINGBOOK,
						BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID };
			}
		}

		genTool.setSortIndex(intSortIndex);
		genTool.setLimitSumGen(intSortIndex.length - 1);
		genTool.setUpSumGen(0);
		nc.vo.glcom.inteltool.UFDCBalanceTool balanceTool = new nc.vo.glcom.inteltool.UFDCBalanceTool();
		int[] balanceIndex;
		String[] relateExpression;

		balanceIndex = new int[] { BalanceBSKey.K_EndCreditAmount,
				BalanceBSKey.K_EndCreditAuxAmount,
				BalanceBSKey.K_EndCreditLocAmount,
				BalanceBSKey.K_EndCreditQuant, BalanceBSKey.K_EndDebitAmount,
				BalanceBSKey.K_EndDebitAuxAmount,
				BalanceBSKey.K_EndDebitLocAmount, BalanceBSKey.K_EndDebitQuant };
		/** ȷ�����������к� */

		/** ���>1000 �����Ӧ����һ��ȡ�����־λ,�����0��1000֮��,��ȡ���е���Ӧ�����־λ */
		/** ��� <0,�򲻿��Ǳ�־λ */
		relateExpression = new String[] {
				"[" + (BalanceBSKey.K_InitCreditAmount) + "]+["
						+ BalanceBSKey.K_CreditAmount + "]",
				"[" + (BalanceBSKey.K_InitCreditAuxAmount) + "]+["
						+ BalanceBSKey.K_CreditAuxAmount + "]",
				"[" + (BalanceBSKey.K_InitCreditLocAmount) + "]+["
						+ BalanceBSKey.K_CreditLocAmount + "]",
				"[" + (BalanceBSKey.K_InitCreditQuant) + "]+["
						+ BalanceBSKey.K_CreditQuant + "]",
				"[" + (BalanceBSKey.K_InitDebitAmount) + "]+["
						+ BalanceBSKey.K_DebitAmount + "]",
				"[" + (BalanceBSKey.K_InitDebitAuxAmount) + "]+["
						+ BalanceBSKey.K_DebitAuxAmount + "]",
				"[" + (BalanceBSKey.K_InitDebitLocAmount) + "]+["
						+ BalanceBSKey.K_DebitLocAmount + "]",
				"[" + (BalanceBSKey.K_InitDebitQuant) + "]+["
						+ BalanceBSKey.K_DebitQuant + "]", };

		/** ����������漰���� */
		balanceTool.setBalanceIndex(balanceIndex);
		balanceTool.setRelateIndex(relateExpression);

		nc.vo.glcom.inteltool.CUFDoubleSumTool sumTool = new nc.vo.glcom.inteltool.CUFDoubleSumTool();
		int sumIndex[] = { BalanceBSKey.K_InitDebitQuant,
				BalanceBSKey.K_InitDebitAmount,
				BalanceBSKey.K_InitDebitAuxAmount,
				BalanceBSKey.K_InitDebitLocAmount,
				BalanceBSKey.K_InitCreditQuant,
				BalanceBSKey.K_InitCreditAmount,
				BalanceBSKey.K_InitCreditAuxAmount,
				BalanceBSKey.K_InitCreditLocAmount, BalanceBSKey.K_DebitQuant,
				BalanceBSKey.K_DebitAmount, BalanceBSKey.K_DebitAuxAmount,
				BalanceBSKey.K_DebitLocAmount, BalanceBSKey.K_CreditQuant,
				BalanceBSKey.K_CreditAmount, BalanceBSKey.K_CreditAuxAmount,
				BalanceBSKey.K_CreditLocAmount, BalanceBSKey.K_DebitAccumQuant,
				BalanceBSKey.K_DebitAccumAmount,
				BalanceBSKey.K_DebitAccumAuxAmount,
				BalanceBSKey.K_DebitAccumLocAmount,
				BalanceBSKey.K_CreditAccumQuant,
				BalanceBSKey.K_CreditAccumAmount,
				BalanceBSKey.K_CreditAccumAuxAmount,
				BalanceBSKey.K_CreditAccumLocAmount,
				BalanceBSKey.K_EndDebitQuant, BalanceBSKey.K_EndDebitAmount,
				BalanceBSKey.K_EndDebitAuxAmount,
				BalanceBSKey.K_EndDebitLocAmount,
				BalanceBSKey.K_EndCreditQuant, BalanceBSKey.K_EndCreditAmount,
				BalanceBSKey.K_EndCreditAuxAmount,
				BalanceBSKey.K_EndCreditLocAmount };
		/** Ҫ���кϼƵ��� */

		sumTool.setSumIndex(sumIndex);

		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		// if(!m_qryVO.isMultiCorpCombine())
		outputTool.setRequireOutputDetail(false);

		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();
		datasource.setSumVector(nc.vo.glcom.inteltool.CDataSource.sortVector(vecVO, genTool, false));

		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();
		try {
			if (intSortIndex != null)
				tt.setGenTool(genTool);
			tt.setBalanceTool(balanceTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
			tt.setSumTool(sumTool);
			Vector recVector = tt.getResultVector();
			vos = new BalanceBSVO[recVector.size()];
			recVector.copyInto(vos);
			return vos;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}
	}

	private BalanceBSVO[] fillSubjTypeInfo(BalanceBSVO[] vos, GlQueryVO qryVO) {
		if (vos == null || vos.length == 0
				|| !qryVO.getFormatVO().isSumbySubjType())
			return vos;
		try {
			for (int i = 0; i < vos.length; i++) {
				String pk_accasoa = (String)vos[i].getValue(BalanceBSKey.K_PKACCASOA);
				if(!StrTools.isEmptyStr(pk_accasoa)){
					if(accMap.get(pk_accasoa)!=null){
						vos[i].setValue(BalanceBSKey.K_Pk_subjType,accMap.get(pk_accasoa).getPk_acctype());
					}
				}
			}
			// �Խӿ�Ŀ����
			nc.vo.bd.accsystem.AccTypeVO[] subjtypeVo = AccTypeGL
					.getAccTypeVosBYOrg(qryVO.getBaseAccountingbook());
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < subjtypeVo.length; j++) {
					if (vos[i].getValue(BalanceBSKey.K_Pk_subjType).equals(subjtypeVo[j].getPk_acctype())) {
						vos[i].setValue(BalanceBSKey.K_subjTypeIndex,subjtypeVo[j].getCode());
						vos[i].setValue(BalanceBSKey.K_subjTypeName,subjtypeVo[j].getName());
						break;
					}
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return vos;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-6-21 13:49:05)
	 * 
	 * @return nc.vo.gl.balancesubjass.BalanceSubjAssBSVO[]
	 * @param vos
	 *            nc.vo.gl.balancesubjass.BalanceSubjAssBSVO[]
	 * @param orient
	 *            int
	 */
	private BalanceBSVO[] filterByBalanceOrient(BalanceBSVO[] vos,
			GlQueryVO p_qryVO) throws Exception {
		int orient = p_qryVO.getFormatVO().getBalanceOrient();

		if (vos == null || vos.length == 0 || orient == Balanorient.TWOWAY)
			return vos;
		Vector result = new Vector();
		if (orient == Balanorient.EQUAL) {
			orient = Balanorient.TWOWAY;
		}
		for (int i = 0; i < vos.length; i++) {
			int endOri = Integer.valueOf(vos[i].getValue(BalanceBSKey.K_EndOrient)
					.toString()).intValue();
			if (orient == endOri) {
				result.addElement(vos[i]);
				vos[i].setUserData(null);
			}
		}
		vos = new BalanceBSVO[result.size()];
		result.copyInto(vos);
		return vos;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 15:43:46)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	protected BalanceBSVO[] filterOfNoOccor(BalanceBSVO[] vos,
			nc.vo.glcom.balance.GlQueryVO p_qryVO) throws Exception {
		// �Ӳ�ѯ����й����޷����ļ�¼
		if (p_qryVO.getFormatVO().isShowHasBalanceButZeroOccur() || vos == null) {
			return vos;
		}
		Vector vTemp = new java.util.Vector();
		for (int i = 0; i < vos.length; i++) {
			if (!((vos[i].getValue(BalanceBSKey.K_DebitQuant) == null 
							|| vos[i].getValue(BalanceBSKey.K_DebitQuant).equals(UFDouble.ZERO_DBL))
					&& (vos[i].getValue(BalanceBSKey.K_DebitAmount) == null 
							|| vos[i].getValue(BalanceBSKey.K_DebitAmount).equals(UFDouble.ZERO_DBL))
					&& (vos[i].getValue(BalanceBSKey.K_DebitLocAmount) == null 
							|| vos[i].getValue(BalanceBSKey.K_DebitLocAmount).equals(UFDouble.ZERO_DBL))
					&& (vos[i].getValue(BalanceBSKey.K_CreditQuant) == null 
							|| vos[i].getValue(BalanceBSKey.K_CreditQuant).equals(UFDouble.ZERO_DBL))
					&& (vos[i].getValue(BalanceBSKey.K_CreditAmount) == null 
							|| vos[i].getValue(BalanceBSKey.K_CreditAmount).equals(UFDouble.ZERO_DBL))
					&& (vos[i].getValue(BalanceBSKey.K_CreditLocAmount) == null 
							|| vos[i].getValue(BalanceBSKey.K_CreditLocAmount).equals(UFDouble.ZERO_DBL))
							)) {
				vTemp.addElement(vos[i]);
			}
		}
		vos = new BalanceBSVO[vTemp.size()];
		vTemp.copyInto(vos);
		return vos;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 15:43:46)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	protected BalanceBSVO[] filterOfSubj(BalanceBSVO[] vos) throws Exception {
		// �Ӳ�ѯ����й��˷ǻ�׼��λû�еĿ�Ŀ��¼
		if (vos == null) {
			return vos;
		}
		Vector vTemp = new java.util.Vector();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getValue(BalanceBSKey.K_AccName) == null
					|| !(vos[i].getValue(BalanceBSKey.K_AccName)
							.equals(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("20023030",
											"UPP20023030-000270")/* @res "�޴˿�Ŀ" */))) {
				vTemp.addElement(vos[i]);
			}
		}
		vos = new BalanceBSVO[vTemp.size()];
		vTemp.copyInto(vos);
		return vos;
	}

	/**
	 * getCurrentIndex ����ע�⡣
	 */
	public int getCurrentIndex() throws java.lang.Exception {
		return m_currentIndex;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-18 15:05:09)
	 * 
	 * @return BalanceBSVO[]
	 */
	public BalanceBSVO[] getData() {
		return m_dataVos;
	}

	/**
	 * getGenToolElement ����ע�⡣
	 */
	public nc.vo.glcom.inteltool.IGenToolElement getGenToolElement(
			java.lang.Object objKey) {
		if (objKey.equals(Integer.valueOf(BalanceBSKey.K_AccCode))) {
			if (m == null) {
				try {
					m = new CAccountGenToolElement(this.getQueryVO().getBaseAccountingbook());
				} catch (Exception e) {
					Logger.error(e.getMessage(), e);
				}
			} else {
				String pk_toolorgbook = ((CAccountGenToolElement) m).getpk_accountingbook();
				String pk_thisorgbook = this.getQueryVO().getBaseAccountingbook();
				if (!pk_thisorgbook.equals(pk_toolorgbook)) {
					try {
						m = new CAccountGenToolElement(this.getQueryVO().getBaseAccountingbook());
					} catch (Exception e) {
						Logger.error(e.getMessage(), e);
					}
				}
			}
			return m;
		}

		if (objKey.equals(Integer.valueOf(BalanceBSKey.K_PkCorp))) {
			if (corpGenElement == null) {
				corpGenElement = new nc.ui.gl.asscompute.CCorpGenToolElement();
				((nc.ui.gl.asscompute.CCorpGenToolElement) corpGenElement)
						.setCorpScheme(new int[] { 3, 2, 2, 2 });
			}
			return corpGenElement;
		}
		return null;
	}

	/**
	 * dealQuery ����ע�⡣
	 */
	public GlQueryVO getQueryVO() {
		return m_qryVO;
	}

	/**
	 * getSize ����ע�⡣
	 */
	public int getSize() throws java.lang.Exception {
		return m_dataVos.length;
	}

	/**
	 * getSortTool ����ע�⡣
	 */
	public nc.vo.glcom.sorttool.ISortTool getSortTool(
			java.lang.Object objCompared) {
		// ������������������(����)
		return new nc.ui.glcom.balance.CAssSortTool1();
	}

	/**
	 * getValue ����ע�⡣
	 */
	public java.lang.Object getValue(int intKey) throws java.lang.Exception {
		return m_dataVos[getCurrentIndex()].getValue(intKey);
	}

	/**
	 * getVO ����ע�⡣
	 */
	public java.lang.Object getVO(int iIndex) throws java.lang.Exception {
		return m_dataVos[iIndex];
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-8 15:43:46)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos
	 *            BalanceBSVO[]
	 */
	protected BalanceBSVO[] makeBlankRec(BalanceBSVO[] ovos,
			nc.vo.glcom.balance.GlQueryVO p_qryVO) throws Exception {
		int iCurrCount = 1;
		nc.vo.bd.currtype.CurrtypeVO[] currTypes = null;
		if (p_qryVO.getCurrTypeName().equals(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("20023030","UPP20023030-000116")/* @res "���б���" */)) {
			currTypes = CurrtypeGL.queryAll(null);
			iCurrCount = currTypes.length;
		}
		
		int iUnitCount = 1;
		if(isBuSupport()){
			iUnitCount = p_qryVO.getPk_unis().length;
		}
		BalanceBSVO[] vos = null;
		Vector tmpVos = new Vector();
		if (p_qryVO.isMultiCorpCombine()) {
			for (int j = 0; j < iCurrCount; j++) {
				for (int k = 0; k < p_qryVO.getPk_account().length; k++) {
					BalanceBSVO zeroRec = new BalanceBSVO();
					Set<String> notExist = new HashSet<String>();
					if (!hasBsvoOfAccPk(ovos, p_qryVO.getPk_account()[k])){ // �ж�Ӧ��Ŀ���ݵľͲ��ò����ˣ���Ȼ��endbalance�������޷��ϲ��ռ�¼
						notExist.add(p_qryVO.getPk_account()[k]);
					}
					if(notExist.size()>0){
						List<AccountVO> res = new ArrayList<AccountVO>();
						for(String pk : notExist){
							if(accMap.get(pk)!=null){
								res.add(accMap.get(pk));
							}
						}
						AccountVO[] accs = res.toArray(new AccountVO[0]);
						if(accs!=null){
							for(AccountVO accVoTemp : accs){
								if (accVoTemp != null) {
									zeroRec.setValue(BalanceBSKey.K_CurType,currTypes == null ? p_qryVO.getCurrTypeName(): currTypes[j].getName());
									zeroRec.setValue(BalanceBSKey.K_PkCurrType,currTypes == null ? p_qryVO.getPk_currtype(): currTypes[j].getPk_currtype());
									zeroRec.setValue(BalanceBSKey.K_PKACCASOA,p_qryVO.getPk_account()[k]);
									zeroRec.setValue(BalanceBSKey.K_AccCode,accVoTemp.getCode());
									zeroRec.setValue(BalanceBSKey.K_AccName,accVoTemp.getDispname());
									zeroRec.setValue(BalanceBSKey.K_AccOrient,accVoTemp.getBalanorient());
									zeroRec.setValue(BalanceBSKey.K_BothOrient,accVoTemp.getBothorient());
									tmpVos.addElement(zeroRec);
								}
							}
						}
					}
				}
			}
		} else {
			for (int i = 0; i < p_qryVO.getpk_accountingbook().length; i++) {
				for(int l=0;l<iUnitCount;l++){
					for (int j = 0; j < iCurrCount; j++) {
						for (int k = 0; k < p_qryVO.getPk_account().length; k++) {
							BalanceBSVO zeroRec = new BalanceBSVO();
							Set<String> notExist = new HashSet<String>();
							if (!hasBsvoOfAccPk(ovos, p_qryVO.getPk_account()[k])){ // �ж�Ӧ��Ŀ���ݵľͲ��ò����ˣ���Ȼ��endbalance�������޷��ϲ��ռ�¼
								notExist.add(p_qryVO.getPk_account()[k]);
							}
							if(notExist.size()>0){
								List<AccountVO> res = new ArrayList<AccountVO>();
								for(String pk : notExist){
									if(accMap.get(pk)!=null){
										res.add(accMap.get(pk));
									}
								}
								AccountVO[] accs = res.toArray(new AccountVO[0]);
								if(accs!=null){
									for(AccountVO accVoTemp : accs){
										if (accVoTemp != null) {
											zeroRec.setValue(BalanceBSKey.K_PK_ACCOUNTINGBOOK,p_qryVO.getpk_accountingbook()[i]);
											zeroRec.setValue(BalanceBSKey.K_PKUNIT,p_qryVO.getPk_unis()[l]);
											zeroRec.setValue(BalanceBSKey.K_CurType,currTypes == null 
													? p_qryVO.getCurrTypeName() : currTypes[j].getName());
											zeroRec.setValue(BalanceBSKey.K_PkCurrType,currTypes == null 
													? p_qryVO.getPk_currtype() : currTypes[j].getPk_currtype());
											zeroRec.setValue(BalanceBSKey.K_PKACCASOA,p_qryVO.getPk_account()[k]);
											zeroRec.setValue(BalanceBSKey.K_AccCode,accVoTemp.getCode());
											zeroRec.setValue(BalanceBSKey.K_AccName,accVoTemp.getDispname());
											zeroRec.setValue(BalanceBSKey.K_AccOrient,accVoTemp.getBalanorient());
											zeroRec.setValue(BalanceBSKey.K_BothOrient,accVoTemp.getBothorient());
											tmpVos.addElement(zeroRec);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		vos = new BalanceBSVO[tmpVos.size()];
		tmpVos.copyInto(vos);
		return vos;
	}

	/**
	 * �ж�ԭ�Ƚ����¼���Ƿ��и�����Ŀ�ļ�¼
	 * 
	 * @param ovos
	 *            ԭ�Ƚ����¼����
	 * @param pk_accsubj
	 *            ������Ŀpk
	 * @return 2007-11-1
	 */
	private boolean hasBsvoOfAccPk(BalanceBSVO[] ovos, String pk_accsubj)
			throws Exception {
		if (ovos == null)
			return false;
		for (int i = 0; i < ovos.length; i++) {
			if (ovos[i].getValue(BalanceBSKey.K_PKACCASOA).equals(pk_accsubj))
				return true;
		}
		return false;
	}

	/**
	 * modifyVO ����ע�⡣
	 */
	public void modifyVO(java.lang.Object objValue) throws java.lang.Exception {
	}

	/**
	 * removeVO ����ע�⡣
	 */
	public void removeVO(java.lang.Object objValue) throws java.lang.Exception {
	}

	/**
	 * setCurrentIndex ����ע�⡣
	 */
	public void setCurrentIndex(int currentIndex) throws java.lang.Exception {
		m_currentIndex = currentIndex;
	}

	/**
	 * setValue ����ע�⡣
	 */
	public void setValue(int intKey, java.lang.Object objValue)
			throws java.lang.Exception {
	}

	public void sort(BalanceBSVO[] vosToSorted, int[] iIndex) {
		nc.vo.glcom.shellsort.CShellSort objShellSort = new nc.vo.glcom.shellsort.CShellSort();
		nc.vo.glcom.sorttool.CVoSortTool objVoSortTool = new nc.vo.glcom.sorttool.CVoSortTool();
		objVoSortTool.setGetSortTool(this);
		objVoSortTool.setSortIndex(iIndex);
		try {
			objShellSort.sort(vosToSorted, objVoSortTool, false);
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
		}
	}

	private BalanceBSVO[] combineAss(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		if (vos == null || vos.length == 0)
			return vos;
		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();
		// �ڴ˲�ָ��������Ϣ,�򲻷���ϼ�
		int[] intSortIndex = null;
		int intSumLimit = 1;

		if (p_qryVO.getCurrTypeName() != null
				&& p_qryVO.getCurrTypeName().trim()
						.equals(CurrTypeConst.ALL_CURRTYPE())) {
			if (p_qryVO.getpk_accountingbook().length > 1) {
				if(p_qryVO.isMultiCorpCombine()){
					intSumLimit = 2;
					intSortIndex = new int[] {BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID,
							BalanceBSKey.K_PkCurrType };
				}else{
					intSumLimit = 3;
					intSortIndex = new int[] { BalanceBSKey.K_FINANCEORGCODE,
							BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID,
							BalanceBSKey.K_PkCurrType };
				}
			} else {
				if(isBuSupport()){
					intSumLimit = 3;
					intSortIndex = new int[] {BalanceBSKey.K_PKUNIT, BalanceBSKey.K_AccCode,
							BalanceBSKey.K_ASSID, BalanceBSKey.K_PkCurrType };
				}else{
					intSumLimit = 2;
					intSortIndex = new int[] { BalanceBSKey.K_AccCode,
								BalanceBSKey.K_ASSID, BalanceBSKey.K_PkCurrType };
				}
			}
		} else {
			if (p_qryVO.getpk_accountingbook().length > 1) {
				if(p_qryVO.isMultiCorpCombine()){
					intSumLimit = 1;
					intSortIndex = new int[] { BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID };
				}else{
					intSumLimit = 2;
					intSortIndex = new int[] { BalanceBSKey.K_FINANCEORGCODE,
							BalanceBSKey.K_AccCode, BalanceBSKey.K_ASSID };
				}
			} else {
				if(isBuSupport()){
					intSumLimit = 2;
					intSortIndex = new int[] { BalanceBSKey.K_PKUNIT, BalanceBSKey.K_AccCode,
							BalanceBSKey.K_ASSID };
				}else{
					intSumLimit = 1;
					intSortIndex = new int[] { BalanceBSKey.K_AccCode,
							BalanceBSKey.K_ASSID };
				}
			}
		}
		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();
		genTool.setLimitSumGen(intSumLimit);
		genTool.setSortIndex(intSortIndex);
		genTool.setGetSortTool(this);
		nc.vo.glcom.inteltool.CSumTool sumTool = new nc.vo.glcom.inteltool.CSumTool();
		int sumIndex[] = getComPuterSumIndex();
		// Ҫ���кϼƵ���
		sumTool.setSumIndex(sumIndex);
		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		outputTool.setRequireOutputDetail(false);
		outputTool.setSummaryCol(-1); // ���ñ�ע��Ϣ���ݼ�����Ӧ����
		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();
		Vector vecVos = new Vector();
		for (int i = 0; i < vos.length; i++) {
			vos[i].setUserData(null);
			vecVos.addElement(vos[i]);
		}
		datasource.setSumVector(nc.vo.glcom.inteltool.CDataSource.sortVector(vecVos, genTool, false));
		try {
			tt.setSumTool(sumTool);
			tt.setGenTool(genTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
		}
		Vector recVector = tt.getResultVector();
		BalanceBSVO[] VOs = new BalanceBSVO[recVector.size()];
		recVector.copyInto(VOs);
		return VOs;
	}

	/**
	 * ���˫����ʾʱ�����¸��ݲ�ѯ��Ŀ���κϼ����ݡ� �������ڣ�(2002-7-4 14:00:39)
	 * 
	 * @return java.lang.String
	 * @param code
	 *            java.lang.String
	 */
	private BalanceBSVO[] DoWhlTwoBlc(BalanceBSVO[] vos, GlQueryVO p_qryVO)
			throws Exception {
		if (vos == null || vos.length == 0)
			return null;
		Vector v = new Vector();
		Vector vSubSum = new Vector();
		int iSbjLvl = getHghLvl(vos);
		for (int i = 0; i < vos.length; i++) {
			String strName = (String) vos[i].getValue(BalanceBSKey.K_AccName);
			CAccountGenToolElement cSbjGtlelmnt = new CAccountGenToolElement();
			if (strName != null
					&& (strName.indexOf(bookSum) >= 0
							|| strName.indexOf(currSum) >= 0
							|| strName.indexOf(subjTypeSum) >= 0
							|| strName.indexOf(unitSum) >= 0)) {
				if (v != null && v.size() > 0) {
					BalanceBSVO[] tempvos = new BalanceBSVO[v.size()];
					v.copyInto(tempvos);
					BalanceBSVO tempvo = cmptWhlTwoBlc(tempvos);
					vos[i].setValue(BalanceBSKey.K_EndDebitAmount,tempvo.getValue(BalanceBSKey.K_EndDebitAmount));
					vos[i].setValue(BalanceBSKey.K_EndDebitLocAmount,tempvo.getValue(BalanceBSKey.K_EndDebitLocAmount));
					vos[i].setValue(BalanceBSKey.K_EndCreditAmount,tempvo.getValue(BalanceBSKey.K_EndCreditAmount));
					vos[i].setValue(BalanceBSKey.K_EndCreditLocAmount,tempvo.getValue(BalanceBSKey.K_EndCreditLocAmount));
					vos[i].setValue(BalanceBSKey.K_InitCreditAmount,tempvo.getValue(BalanceBSKey.K_InitCreditAmount));
					vos[i].setValue(BalanceBSKey.K_InitCreditLocAmount,tempvo.getValue(BalanceBSKey.K_InitCreditLocAmount));
					vos[i].setValue(BalanceBSKey.K_InitDebitAmount,tempvo.getValue(BalanceBSKey.K_InitDebitAmount));
					vos[i].setValue(BalanceBSKey.K_InitDebitLocAmount,tempvo.getValue(BalanceBSKey.K_InitDebitLocAmount));
					vSubSum.addElement(vos[i]);
				}
				v.removeAllElements();
			} else if (strName.equals(allSum)) {
				BalanceBSVO[] tempvos = null;
				if (v.size() == 0 && vSubSum.size() == 0) {
					continue;
				} else if (v.size() == 0 && vSubSum.size() > 0) {
					tempvos = new BalanceBSVO[vSubSum.size()];
					vSubSum.copyInto(tempvos);
				} else {
					tempvos = new BalanceBSVO[v.size()];
					v.copyInto(tempvos);
				}
				BalanceBSVO tempvo = cmptWhlTwoBlc(tempvos);
				vos[i].setValue(BalanceBSKey.K_EndDebitAmount,tempvo.getValue(BalanceBSKey.K_EndDebitAmount));
				vos[i].setValue(BalanceBSKey.K_EndDebitLocAmount,tempvo.getValue(BalanceBSKey.K_EndDebitLocAmount));
				vos[i].setValue(BalanceBSKey.K_EndCreditAmount,tempvo.getValue(BalanceBSKey.K_EndCreditAmount));
				vos[i].setValue(BalanceBSKey.K_EndCreditLocAmount,tempvo.getValue(BalanceBSKey.K_EndCreditLocAmount));
				vos[i].setValue(BalanceBSKey.K_InitCreditAmount,tempvo.getValue(BalanceBSKey.K_InitCreditAmount));
				vos[i].setValue(BalanceBSKey.K_InitCreditLocAmount,tempvo.getValue(BalanceBSKey.K_InitCreditLocAmount));
				vos[i].setValue(BalanceBSKey.K_InitDebitAmount,tempvo.getValue(BalanceBSKey.K_InitDebitAmount));
				vos[i].setValue(BalanceBSKey.K_InitDebitLocAmount,tempvo.getValue(BalanceBSKey.K_InitDebitLocAmount));
			} else {
				if(strName.indexOf(subjSum)>=0){
					String strSbjCd = (String) vos[i].getValue(BalanceBSKey.K_AccCode);
					int iHghLvl = cSbjGtlelmnt.getSumGen(strSbjCd);
					if (iHghLvl == iSbjLvl) {
						v.addElement(vos[i]);
					}
				}
			}

		}
		return vos;

	}

	/**
	 * ȡ����Ŀ��߼��Ρ� �������ڣ�(2001-10-8 19:52:44)
	 * 
	 * @return BalanceBSVO[]
	 * @param vos BalanceBSVO[]
	 */
	private int getHghLvl(BalanceBSVO[] vos) throws Exception {

		int iSbjLevl = -1;
		for (int i = 0; i < vos.length; i++) {

			String strName = (String) vos[i].getValue(BalanceBSKey.K_AccName);
			CAccountGenToolElement cSbjGtlelmnt = new CAccountGenToolElement();
			if (strName != null
					&& (strName.indexOf(bookSum) >= 0
							|| strName.indexOf(currSum) >= 0
							|| strName.equals(allSum) 
							|| strName.indexOf(subjTypeSum) >= 0
							|| strName.indexOf(unitSum) >= 0)) {
				continue;
			} else {
				String strSbjCd = (String) vos[i].getValue(BalanceBSKey.K_AccCode);
				int iHghLvl = cSbjGtlelmnt.getSumGen(strSbjCd);
				if (i == 0) {
					iSbjLevl = iHghLvl;
				} else {
					if (iHghLvl < iSbjLevl) {
						iSbjLevl = iHghLvl;
					}
				}
			}
		}

		return iSbjLevl;
	}

	private BalanceBSVO cmptWhlTwoBlc(BalanceBSVO[] vos) throws Exception {
		if (vos == null || vos.length == 0)
			return null;
		BalanceBSVO tempvo = new BalanceBSVO();

		UFDouble initdEDA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initdECA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initdEDAA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initdECAA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initdEDLA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initdECLA = ZeroUFdoubleConstant.DFDB_ZERO;

		UFDouble initDEInitCA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initDEInitCAA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initDEInitCLA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initDEInitDA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initDEInitDAA = ZeroUFdoubleConstant.DFDB_ZERO;
		UFDouble initDEInitDLA = ZeroUFdoubleConstant.DFDB_ZERO;

		for (int i = 0; i < vos.length; i++) {

			UFDouble dEDA = vos[i].getValue(BalanceBSKey.K_EndDebitAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i].getValue(BalanceBSKey.K_EndDebitAmount);
			UFDouble dECA = vos[i].getValue(BalanceBSKey.K_EndCreditAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_EndCreditAmount);
			UFDouble dEDAA = vos[i].getValue(BalanceBSKey.K_EndDebitAuxAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_EndDebitAuxAmount);
			UFDouble dECAA = vos[i].getValue(BalanceBSKey.K_EndCreditAuxAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_EndCreditAuxAmount);
			UFDouble dEDLA = vos[i].getValue(BalanceBSKey.K_EndDebitLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_EndDebitLocAmount);
			UFDouble dECLA = vos[i].getValue(BalanceBSKey.K_EndCreditLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_EndCreditLocAmount);

			UFDouble dEInitCA = vos[i]
					.getValue(BalanceBSKey.K_InitCreditAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitCreditAmount);

			UFDouble dEInitCAA = vos[i]
					.getValue(BalanceBSKey.K_InitCreditAuxAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitCreditAuxAmount);
			UFDouble dEInitCLA = vos[i]
					.getValue(BalanceBSKey.K_InitCreditLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitCreditLocAmount);

			UFDouble dEInitDA = vos[i].getValue(BalanceBSKey.K_InitDebitAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitDebitAmount);
			UFDouble dEInitDAA = vos[i]
					.getValue(BalanceBSKey.K_InitDebitAuxAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitDebitAuxAmount);
			UFDouble dEInitDLA = vos[i]
					.getValue(BalanceBSKey.K_InitDebitLocAmount) == null ? ZeroUFdoubleConstant.DFDB_ZERO
					: (UFDouble) vos[i]
							.getValue(BalanceBSKey.K_InitDebitLocAmount);

			initdEDA = initdEDA.add(dEDA);
			initdECA = initdECA.add(dECA);
			initdEDAA = initdEDAA.add(dEDAA);
			initdECAA = initdECAA.add(dECAA);
			initdEDLA = initdEDLA.add(dEDLA);
			initdECLA = initdECLA.add(dECLA);

			initDEInitCA = initDEInitCA.add(dEInitCA);
			initDEInitCAA = initDEInitCAA.add(dEInitCAA);
			initDEInitCLA = initDEInitCLA.add(dEInitCLA);

			initDEInitDA = initDEInitDA.add(dEInitDA);
			initDEInitDAA = initDEInitDAA.add(dEInitDAA);
			initDEInitDLA = initDEInitDLA.add(dEInitDLA);

		}
		tempvo.setValue(BalanceBSKey.K_EndDebitAmount, initdEDA);
		tempvo.setValue(BalanceBSKey.K_EndCreditAmount, initdECA);
		tempvo.setValue(BalanceBSKey.K_EndDebitAuxAmount, initdEDAA);
		tempvo.setValue(BalanceBSKey.K_EndCreditAuxAmount, initdECAA);
		tempvo.setValue(BalanceBSKey.K_EndDebitLocAmount, initdEDLA);
		tempvo.setValue(BalanceBSKey.K_EndCreditLocAmount, initdECLA);

		tempvo.setValue(BalanceBSKey.K_InitCreditAmount, initDEInitCA);
		tempvo.setValue(BalanceBSKey.K_InitCreditAuxAmount, initDEInitCAA);
		tempvo.setValue(BalanceBSKey.K_InitCreditLocAmount, initDEInitCLA);

		tempvo.setValue(BalanceBSKey.K_InitDebitAmount, initDEInitDA);
		tempvo.setValue(BalanceBSKey.K_InitDebitAuxAmount, initDEInitDAA);
		tempvo.setValue(BalanceBSKey.K_InitDebitLocAmount, initDEInitDLA);

		return tempvo;
	}
	
	// ����ҵ��Ԫ����
	protected boolean isBuSupport() {
		boolean support = false;
		try {
			if (m_qryVO != null
					&& !m_qryVO.isMultiBusi()
					&& m_qryVO.getpk_accountingbook().length == 1
					&& GLParaAccessor.isSecondBUStart(
							m_qryVO.getBaseAccountingbook()).booleanValue()) {
				support = true;
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return support;
	}
	
	/**
	 * ����ҵ��Ԫ��汾��
	 * 
	 * @param vos
	 * @return
	 */
	protected BalanceBSVO[] processBusiUnit(BalanceBSVO[] vos) {
		if (vos == null || vos.length == 0)
			return vos;
		HashSet<String> pk_units = new HashSet<String>();
		try {
			for (int i = 0; i < vos.length; i++) {
				if (null != vos[i].getValue(BalanceBSKey.K_PKUNIT)) {
					String pk_unit = vos[i].getValue(BalanceBSKey.K_PKUNIT)
							.toString();
					pk_units.add(pk_unit);
				}
			}
			HashMap<String, String> versionMap = GlOrgUtils
					.getNewVIDSByOrgIDSAndDate(pk_units.toArray(new String[] {}), getQueryVO().getEndDate()==null
							?new UFDate(getQueryVO().getSubjVersion()):getQueryVO().getEndDate());

			for (int i = 0; i < vos.length; i++) {
				if (null != vos[i].getValue(BalanceBSKey.K_PKUNIT)) {
					String pk_unit = vos[i].getValue(BalanceBSKey.K_PKUNIT)
							.toString();
					vos[i].setValue(BalanceBSKey.K_PKUNIT_V,
							versionMap.get(pk_unit));
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return vos;
	}
	
	// ����ҵ��Ԫ
	protected BalanceBSVO[] adjustUnit(BalanceBSVO[] vos, GlQueryVO qryVO) {
		if (vos == null || vos.length == 0)
			return vos;
		try {
			HashSet<String> set = new HashSet<String>();
			for (int i = 0; i < vos.length; i++) {
				set.add((String) vos[i].getValue(BalanceBSKey.K_PKUNIT));
			}
			set.remove(null);
			set.remove("");
			String[] pk_orgs = set.toArray(new String[] {});

			if (pk_orgs.length > 0) {
				OrgVO[] orgs = OrgUtil.getOrgs(pk_orgs);
				for (int i = 0; i < vos.length; i++) {
					for (int k = 0; k < pk_orgs.length; k++) {
						if (pk_orgs[k].equals(vos[i]
								.getValue(BalanceBSKey.K_PKUNIT))) {
							vos[i].setValue(BalanceBSKey.K_UNITCODE, orgs[k]
									.getCode() == null ? orgs[k].getPk_org()
									: orgs[k].getCode());
							vos[i].setValue(BalanceBSKey.K_UNITNAME,
									orgs[k].getName());
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return vos;
	}
	
	public HashMap<String, AccountVO> getAccMap() {
		return accMap;
	}
}