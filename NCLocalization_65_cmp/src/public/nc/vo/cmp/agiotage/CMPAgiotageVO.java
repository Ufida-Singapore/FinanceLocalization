package nc.vo.cmp.agiotage;

/**
 * �������VO��
 * ��Ҫ�е�����������ѡ��������ʾ����������Ĺ�������
 * �������ڣ�(2001-8-7 20:18:50)
 * ����޸����ڣ�(2001-8-7 20:18:50)
 * @author��wyan
 */
import java.util.Hashtable;
import java.util.Vector;

import nc.cmp.pub.exception.ExceptionHandler;
import nc.itf.cmp.pub.Currency;
import nc.itf.fi.pub.SysInit;
import nc.pubitf.bbd.CurrtypeQuery;
import nc.vo.bd.currtype.CurrtypeVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.tmpub.util.VOUtil;

/**
 * �������VO
 *
 * @author jiaweib
 * @version 1.0 2011-2-24
 * @since NC6.0
 */
public class CMPAgiotageVO extends nc.vo.pub.ValueObject {

	public static final int AGIOTAGE_SX = 2; // ��ʵ��ʱ����
	public static final int AGIOTAGE_END = 1; // ��ĩ����
	public static final int AGIOTAGE_WB = 0; // ��ҽ���ʱ����

	public static int getMode(String dwbm, Integer system) {

		String paraString = system == 0 ? "AR5" : "CMP3";

		String mode = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0","03607agi-0055")/*@res "��ĩ����"*/;
		try {
			mode = SysInit.getParaString(dwbm, paraString);
		} catch (BusinessException e) {
			ExceptionHandler.consume(e);
		}

		int smode = CMPAgiotageVO.AGIOTAGE_WB;
		if (mode.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0","03607agi-0055")/*@res "��ĩ����"*/)) /*-=notranslate=-*/
			smode = CMPAgiotageVO.AGIOTAGE_END;
		else if (mode.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0","03607agi-0056")/*@res "������ʵ�ֻ������"*/)) /*-=notranslate=-*/
			smode = CMPAgiotageVO.AGIOTAGE_SX;

		return smode;
	}
	
	private int calculateMode = -1;

	/**
	 *
	 */
	private static final long serialVersionUID = -3441494149924364782L;
	private Boolean m_bXzbz; /* ѡ���־ */
	private int mode;/*����ģʽ(��ĩ������ҽ���) 0,1,2 */
	private boolean m_HsMode;/* �Ƿ������Һ��� */
	private String m_sDwbm; /* ��λ���� */
	private String m_sBzmc; /* �������� */
	private String m_sBzbm; /* ���ֱ��� */
	private String m_sFrac;/* ���ұ��ֱ��� */
	private String m_sLocal;/* ���ұ��ֱ��� */
	private String m_sClbh;/* ������ */
	private UFDate m_uLastTime; /* �ϴμ���ʱ�� */
	private UFDate m_sCalDate; /* ���μ������� */
	private String m_sCalQj; /* ���μ������������ڼ� */
	private String m_sCalNd;/* ���μ�������������� */
	private String m_sQjBeg; /* ���μ������������ڼ���ʼ�� */
	private String m_sQjEnd; /* ���μ������������ڼ������ */
	private String m_sSfbz; /* ������־ */
	private Vector<CMPAgiotageBzVO> m_sSelBzbm; /* �û�ѡ�еı��ֱ����� */
	private Hashtable m_pkAccids;/* ���ֶ�Ӧ���˻� */
	private Hashtable m_pkManageAccids;/* ���ֶ�Ӧ�Ĺ����˻� */
	private String m_sDjlx;/* �������� */
	private String m_sDjbhBeg;/* ���ݱ�ſ�ʼ */
	private String m_sDjbhEnd;/* ���ݱ�Ž��� */
	private String m_sDateBeg;/* �������ڿ�ʼ */
	private String m_sDateEnd;/* �������ڽ��� */
	private String m_sCurrency;/* ���� */
	private String m_sMinje;/* ��С��� */
	private String m_sMaxje;/* ����� */
	private CMPJe m_CalCe;/* ����������Ĳ�� */
	private String m_sUser;/* ��ǰ�û� */
	private String m_sSign;/* ���ֱ�־ */
	private Hashtable<String, String> allBz; // ����λ��������б�����Ϣ(bzbm-bzmc).

	private int queryType; // ��ѯ���� 0 ���ܣ� 1 ����
	private int agiotageType; // ����������� 0 ȫ�� 1 ���� 2 �س�
	private String queryStr; // ��ѯ�ַ���

	private String pk_group;

	/**
	 * TODO �ֶ�����
	 *
	 * @return the pk_group
	 */
	public String getPk_group() {
		return pk_group;
	}

	/**
	 * TODO �ֶ�����
	 *
	 * @param pkGroup
	 *            the pk_group to set
	 */
	public void setPk_group(String pkGroup) {
		pk_group = pkGroup;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public int getAgiotageType() {
		return agiotageType;
	}

	public void setAgiotageType(int agiotageType) {
		this.agiotageType = agiotageType;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public Hashtable<String, String> getAllBz() throws BusinessException {
		if (allBz == null && m_sDwbm != null) {
			try {
				getAllBzExceptLocal();
			} catch (BusinessException e) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0","03607agi-0057")/*@res "��ѯ������Ϣ����:"*/
						+ e.getMessage(), e);
			}
		}
		return allBz;
	}

	public void setAllBz(Hashtable<String, String> allBz) {
		this.allBz = allBz;
	}

	public void getAllBzExceptLocal() throws BusinessException {

		String bzbm = Currency.getLocalCurrPK(getDwbm());

		Hashtable<String, String> allBz = new Hashtable<String, String>();
		CurrtypeVO[] allCurrtypeVOs = CurrtypeQuery.getInstance().getAllCurrtypeVOs();
		for (int i = 0; i < allCurrtypeVOs.length; i++) {
			if (!bzbm.equals(allCurrtypeVOs[i].getPk_currtype())) {
				// allBz.put(allCurrtypeVOs[i].getPk_currtype(),allCurrtypeVOs[i].getCurrtypename());
				allBz.put(allCurrtypeVOs[i].getPk_currtype(), VOUtil.getMultiLangText(allCurrtypeVOs[i], CurrtypeVO.NAME));
			}
		}

		setAllBz(allBz);
	}

	/**
	 * AgiotageVO ������ע�⡣
	 */
	public CMPAgiotageVO() {
		super();
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:02:51) ����޸����ڣ�(2001-8-8 11:02:51)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getBzbm() {
		return m_sBzbm;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:13:29) ����޸����ڣ�(2001-8-8 11:13:29)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getBzmc() {
		return m_sBzmc;
	}

	/**
	 * �˴����뷽�������� �������ڣ�(2003-9-29 13:28:43)
	 *
	 * @return nc.vo.arap.transaction.Je
	 */
	public CMPJe getCalCe() {
		return m_CalCe;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:26:57) ����޸����ڣ�(2001-8-8 10:26:57)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public UFDate getCalDate() {
		return m_sCalDate;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:03:03) ����޸����ڣ�(2001-8-10 14:03:03)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getCalNd() {
		return m_sCalNd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:03:03) ����޸����ڣ�(2001-8-10 14:03:03)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getCalQj() {
		return m_sCalQj;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 15:33:03) ����޸����ڣ�(2001-8-10 15:33:03)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getClbh() {
		return m_sClbh;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:10:46) ����޸����ڣ�(2001-8-10 14:10:46)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getCurrency() {
		return m_sCurrency;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:31) ����޸����ڣ�(2001-8-10 14:09:31)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDateBeg() {
		return m_sDateBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:10:08) ����޸����ڣ�(2001-8-10 14:10:08)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDateEnd() {
		return m_sDateEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:31) ����޸����ڣ�(2001-8-10 14:09:31)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDjbhBeg() {
		return m_sDjbhBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:10:08) ����޸����ڣ�(2001-8-10 14:10:08)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDjbhEnd() {
		return m_sDjbhEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:05:49) ����޸����ڣ�(2001-8-10 14:05:49)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDjlx() {
		return m_sDjlx;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:01:26) ����޸����ڣ�(2001-8-8 11:01:26)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getDwbm() {
		return m_sDwbm;
	}

	/**
	 * ������ֵ�������ʾ���ơ�
	 *
	 * �������ڣ�(2001-2-15 14:18:08)
	 *
	 * @return java.lang.String ������ֵ�������ʾ���ơ�
	 */
	public String getEntityName() {
		return null;
	}

	/**
	 * a����: ���ߣ�wyan ����ʱ�䣺(2002-5-14 11:26:24) ������<|> ����ֵ�� �㷨��
	 *
	 * @return java.lang.String
	 */
	public String getFrac() {
		return m_sFrac;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-6 16:52:40)
	 *
	 * @return boolean
	 */
	public boolean getHsMode() {
		return m_HsMode;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 16:22:31) ����޸����ڣ�(2001-8-10 16:22:31)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	// public int getIsMonthEnd() {
	// return mode;
	// }
	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:26:11) ����޸����ڣ�(2001-8-8 10:26:11)
	 *
	 * @author��wyan
	 * @return nc.vo.pub.lang.UFDate
	 */
	public UFDate getLastTime() {
		return m_uLastTime;
	}

	/**
	 * a����: ���ߣ�wyan ����ʱ�䣺(2002-5-14 11:28:21) ������<|> ����ֵ�� �㷨��
	 *
	 * @return java.lang.String
	 */
	public String getLocal() {
		return m_sLocal;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:12:22) ����޸����ڣ�(2001-8-10 14:12:22)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getMaxJe() {
		return m_sMaxje;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:12:07) ����޸����ڣ�(2001-8-10 14:12:07)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getMinJe() {
		return m_sMinje;
	}

	/**
	 * �˴����뷽�������� �������ڣ�(2003-9-28 13:49:39)
	 *
	 * @return java.util.Hashtable
	 */
	public Hashtable getPkAccids() {
		return m_pkAccids;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:45:58) ����޸����ڣ�(2001-8-8 11:45:58)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getQjBeg() {
		return m_sQjBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:46:28) ����޸����ڣ�(2001-8-8 11:46:28)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getQjEnd() {
		return m_sQjEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:25:02) ����޸����ڣ�(2001-8-8 10:25:02)
	 *
	 * @author��wyan
	 * @return java.lang.String[]
	 */
	public Vector<CMPAgiotageBzVO> getSelBzbm() {
		return m_sSelBzbm;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 13:55:24) ����޸����ڣ�(2001-8-10 13:55:24)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getSfbz() {
		return m_sSfbz;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-7-3 15:33:19)
	 *
	 * @return java.lang.String
	 */
	public String getSign() {
		return m_sSign;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 18:42:43) ����޸����ڣ�(2001-8-10 18:42:43)
	 *
	 * @author��wyan
	 * @return java.lang.String
	 */
	public String getUser() {
		return m_sUser;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:52:50) ����޸����ڣ�(2001-8-8 11:52:50)
	 *
	 * @author��wyan
	 * @return java.lang.Boolean
	 */
	public Boolean getXzbz() {
		return m_bXzbz;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:02:09) ����޸����ڣ�(2001-8-8 11:02:09)
	 *
	 * @author��wyan
	 * @param bzbm
	 *            java.lang.String
	 */
	public void setBzbm(String bzbm) {
		m_sBzbm = bzbm;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:13:09) ����޸����ڣ�(2001-8-8 11:13:09)
	 *
	 * @author��wyan
	 * @param bzmc
	 *            java.lang.String
	 */
	public void setBzmc(String bzmc) {
		m_sBzmc = bzmc;
	}

	/**
	 * �˴����뷽�������� �������ڣ�(2003-9-29 13:29:07)
	 *
	 * @param ce
	 *            nc.vo.arap.transaction.Je
	 */
	public void setCalCe(CMPJe ce) {
		m_CalCe = ce;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:26:41) ����޸����ڣ�(2001-8-8 10:26:41)
	 *
	 * @author��wyan
	 * @param date
	 *            java.lang.String
	 */
	public void setCalDate(UFDate calDate) {
		m_sCalDate = calDate;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:02:47) ����޸����ڣ�(2001-8-10 14:02:47)
	 *
	 * @author��wyan
	 * @param qj
	 *            java.lang.String
	 */
	public void setCalNd(String calNd) {
		m_sCalNd = calNd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:02:47) ����޸����ڣ�(2001-8-10 14:02:47)
	 *
	 * @author��wyan
	 * @param qj
	 *            java.lang.String
	 */
	public void setCalQj(String calQj) {
		m_sCalQj = calQj;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 15:33:22) ����޸����ڣ�(2001-8-10 15:33:22)
	 *
	 * @author��wyan
	 * @param clbh
	 *            java.lang.String
	 */
	public void setClbh(String clbh) {
		m_sClbh = clbh;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:10:30) ����޸����ڣ�(2001-8-10 14:10:30)
	 *
	 * @author��wyan
	 * @param currency
	 *            java.lang.String
	 */
	public void setCurrency(String currency) {
		m_sCurrency = currency;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:15) ����޸����ڣ�(2001-8-10 14:09:15)
	 *
	 * @author��wyan
	 * @param datebeg
	 *            java.lang.String
	 */
	public void setDateBeg(String dateBeg) {
		m_sDateBeg = dateBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:53) ����޸����ڣ�(2001-8-10 14:09:53)
	 *
	 * @author��wyan
	 * @param dateEnd
	 *            java.lang.String
	 */
	public void setDateEnd(String dateEnd) {
		m_sDateEnd = dateEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:15) ����޸����ڣ�(2001-8-10 14:09:15)
	 *
	 * @author��wyan
	 * @param datebeg
	 *            java.lang.String
	 */
	public void setDjbhBeg(String djbhBeg) {
		m_sDjbhBeg = djbhBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:09:53) ����޸����ڣ�(2001-8-10 14:09:53)
	 *
	 * @author��wyan
	 * @param dateEnd
	 *            java.lang.String
	 */
	public void setDjbhEnd(String djbhEnd) {
		m_sDjbhEnd = djbhEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:05:33) ����޸����ڣ�(2001-8-10 14:05:33)
	 *
	 * @author��wyan
	 * @param custcode
	 *            java.lang.String
	 */
	public void setDjlx(String djlx) {
		m_sDjlx = djlx;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:01:05) ����޸����ڣ�(2001-8-8 11:01:05)
	 *
	 * @author��wyan
	 * @param dwbm
	 *            java.lang.String
	 */
	public void setDwbm(String dwbm) {
		m_sDwbm = dwbm;
	}

	/**
	 * a����: ���ߣ�wyan ����ʱ�䣺(2002-5-14 11:26:04) ������<|> ����ֵ�� �㷨��
	 *
	 * @param fbpk
	 *            java.lang.String
	 */
	public void setFrac(String fbpk) {
		m_sFrac = fbpk;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-6 16:52:01)
	 *
	 * @param mode
	 *            boolean
	 */
	public void setHsMode(boolean mode) {
		m_HsMode = mode;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:25:53) ����޸����ڣ�(2001-8-8 10:25:53)
	 *
	 * @author��wyan
	 * @param date
	 *            nc.vo.pub.lang.UFDate
	 */
	public void setLastTime(UFDate date) {
		m_uLastTime = date;
	}

	/**
	 * a����: ���ߣ�wyan ����ʱ�䣺(2002-5-14 11:26:46) ������<|> ����ֵ�� �㷨��
	 *
	 * @param Bbpk
	 *            java.lang.String
	 */
	public void setLocal(String Bbpk) {
		m_sLocal = Bbpk;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:11:52) ����޸����ڣ�(2001-8-10 14:11:52)
	 *
	 * @author��wyan
	 * @param maxje
	 *            java.lang.String
	 */
	public void setMaxJe(String maxje) {
		m_sMaxje = maxje;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:11:29) ����޸����ڣ�(2001-8-10 14:11:29)
	 *
	 * @author��wyan
	 * @param minje
	 *            java.lang.String
	 */
	public void setMinJe(String minje) {
		m_sMinje = minje;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 16:23:00) ����޸����ڣ�(2001-8-10 16:23:00)
	 *
	 * @author��wyan
	 * @param mode
	 *            java.lang.String
	 */
	 public void setMode(int mode) {
		 this.mode = mode;
	 }
	/**
	 * �˴����뷽�������� �������ڣ�(2003-9-28 13:50:39)
	 *
	 * @param accids
	 *            java.util.Hashtable
	 */
	public void setPkAccids(Hashtable accids) {
		m_pkAccids = accids;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:47:47) ����޸����ڣ�(2001-8-8 11:47:47)
	 *
	 * @author��wyan
	 * @param begDate
	 *            java.lang.String
	 */
	public void setQjBeg(String qjBeg) {
		m_sQjBeg = qjBeg;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:47:22) ����޸����ڣ�(2001-8-8 11:47:22)
	 *
	 * @author��wyan
	 * @param endDate
	 *            java.lang.String
	 */
	public void setQjEnd(String qjEnd) {
		m_sQjEnd = qjEnd;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 10:24:44) ����޸����ڣ�(2001-8-8 10:24:44)
	 *
	 * @author��wyan
	 * @param bzbms
	 *            java.lang.String[]
	 */
	public void setSelBzbm(Vector<CMPAgiotageBzVO> bzbms) {
		m_sSelBzbm = bzbms;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 13:55:06) ����޸����ڣ�(2001-8-10 13:55:06)
	 *
	 * @author��wyan
	 * @param sfbz
	 *            java.lang.String
	 */
	public void setSfbz(String sfbz) {
		m_sSfbz = sfbz;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-7-3 15:32:56)
	 *
	 * @param sign
	 *            java.lang.String
	 */
	public void setSign(String sign) {
		m_sSign = sign;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 18:43:08) ����޸����ڣ�(2001-8-10 18:43:08)
	 *
	 * @author��wyan
	 * @param user
	 *            java.lang.String
	 */
	public void setUser(String user) {
		m_sUser = user;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 11:52:30) ����޸����ڣ�(2001-8-8 11:52:30)
	 *
	 * @author��wyan
	 * @param xzbz
	 *            java.lang.Boolean
	 */
	public void setXzbz(Boolean xzbz) {
		m_bXzbz = xzbz;
	}

	/**
	 * ��֤���������֮��������߼���ȷ�ԡ�
	 *
	 * �������ڣ�(2001-2-15 11:47:35)
	 *
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ�ܣ��׳� ValidationException���Դ�����н��͡�
	 */
	public void validate() throws nc.vo.pub.ValidationException {
	}

	public Hashtable getPkManageAccids() {
		return m_pkManageAccids;
	}

	public void setPkManageAccids(Hashtable manageAccids) {
		m_pkManageAccids = manageAccids;
	}

	public int getCalculateMode() {
		return calculateMode;
	}

	public void setCalculateMode(int calculateMode) {
		this.calculateMode = calculateMode;
	}
	public Integer getMode() {
		return this.mode;
	}

}