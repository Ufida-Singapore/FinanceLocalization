package nc.vo.arap.pfflow;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class ArapBillMapVO extends SuperVO{

	private static final long serialVersionUID = 7099021388892488173L;
	private String s_billtype;
	/*��ͬ��*/
	private String contractno ;
	/*Դ��������*/
	private int s_system ;
	/*ϵͳ��Դ*/
	private UFDouble ybye; 
	/*�������*/
	private int maptype; 
	/*ռ�ñ�־*/
	private UFDouble ybje;
	/*������*/
	private String t_termid;
	/*Ŀ�굥��Э����pk*/
	private String t_itemid;
	
	/*Ŀ�굥����pk*/
	private String t_billid;
	/*Ŀ�굥��pk*/
	private String t_billtype;
	/*Ŀ�굥������*/
	private String s_termid;
	/*Դ����Э����pk*/
	private String s_itemid;
	/*Դ������pk*/
	private String s_billid;
	/*Դ����pk*/
	private String pk_billmap;
	/*����*/
	private UFDouble oldje;
	/*��ʷ���*/
	private UFDateTime ts;
	private Integer dr;
	private String pk_org;
	private String pk_currtype;
	
	/*�������α���*/
	private String settlecurr;
	/*�������*/
	private UFDouble settlemoney;
	/*����������֯*/
	private String pk_org1;
	//add chenth 20161212 �¼���ʢ����Ŀ���ӡ��Ƿ������ѡ��ֶ� 
	//add by weiningc 20171012 V633������V65 start
	private nc.vo.pub.lang.UFBoolean isbankcharges;	
	public static final String ISBANKCHARGES = "isbankcharges";	
	public nc.vo.pub.lang.UFBoolean getIsbankcharges() {
		return isbankcharges;
	}
	public void setIsbankcharges(nc.vo.pub.lang.UFBoolean isbankcharges) {
		this.isbankcharges = isbankcharges;
	}
	//add end
	//add by weiningc 20171012 V633������V65 end
	
	
	public String getPk_org1() {
		return pk_org1;
	}

	public void setPk_org1(String pkOrg1) {
		pk_org1 = pkOrg1;
	}

	public String getSettlecurr() {
		return settlecurr;
	}

	public void setSettlecurr(String settlecurr) {
		this.settlecurr = settlecurr;
	}

	public UFDouble getSettlemoney() {
		return settlemoney;
	}

	public void setSettlemoney(UFDouble settlemoney) {
		this.settlemoney = settlemoney;
	}
	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pkOrg) {
		pk_org = pkOrg;
	}

	public String getPk_currtype() {
		return pk_currtype;
	}

	public void setPk_currtype(String pkCurrtype) {
		pk_currtype = pkCurrtype;
	}

	@Override
	public String getPKFieldName() {
		return "pk_billmap";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	

	public String getPk_billmap() {
		return pk_billmap;
	}

	public void setPk_billmap(String pk_billmap) {
		this.pk_billmap = pk_billmap;
	}

	public String getS_billid() {
		return s_billid;
	}

	public void setS_billid(String s_billid) {
		this.s_billid = s_billid;
	}

	public String getS_itemid() {
		return s_itemid;
	}

	public void setS_itemid(String s_itemid) {
		this.s_itemid = s_itemid;
	}

	public int getS_system() {
		return s_system;
	}

	public void setS_system(int s_system) {
		this.s_system = s_system;
	}

	public String getT_billid() {
		return t_billid;
	}

	public void setT_billid(String t_billid) {
		this.t_billid = t_billid;
	}

	public String getT_billtype() {
		return t_billtype;
	}

	public void setT_billtype(String t_billtype) {
		this.t_billtype = t_billtype;
	}

	public String getT_itemid() {
		return t_itemid;
	}

	public void setT_itemid(String t_itemid) {
		this.t_itemid = t_itemid;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public UFDouble getYbje() {
		return ybje;
	}

	public void setYbje(UFDouble ybje) {
		this.ybje = ybje;
	}

	public UFDouble getYbye() {
		return ybye;
	}

	public void setYbye(UFDouble ybye) {
		this.ybye = ybye;
	}

	@Override
	public String getTableName() {
		return "arap_billmap";
	}

	public String getS_billtype() {
		return s_billtype;
	}

	public void setS_billtype(String s_billtype) {
		this.s_billtype = s_billtype;
	}

	public UFDouble getOldje() {
		return oldje;
	}

	public void setOldje(UFDouble oldje) {
		this.oldje = oldje;
	}

	public int getMaptype() {
		return maptype;
	}

	public void setMaptype(int maptype) {
		this.maptype = maptype;
	}

	public String getT_termid() {
		return t_termid;
	}

	public void setT_termid(String tTermid) {
		t_termid = tTermid;
	}

	public String getS_termid() {
		return s_termid;
	}

	public void setS_termid(String sTermid) {
		s_termid = sTermid;
	}

}
