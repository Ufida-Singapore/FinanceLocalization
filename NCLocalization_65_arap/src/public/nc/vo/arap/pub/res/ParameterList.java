package nc.vo.arap.pub.res;

import nc.vo.ml.NCLangRes4VoTransl;

/***
 * �������룬����
 * Add by bl 2015-04-22
 */
public enum ParameterList {

	  AP202("AP202", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0005")/* ����¼�뵥�ݺϲ�����*/), 
	  AP201("AP201", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0007")/* ��������ݺϲ�����*/),
	  AR102("AR102", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0006")/* �տ�¼�뵥�ݺϲ�����*/),
	  AR101("AR101", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0008")/* �տ�����ݺϲ�����*/); 
	  
	  public static final String BIGSPLITKEY = "#";
	  public static final String SPLITKEY = ",";
	  public static final String SUFFIX = "_V";
	  public static final String DOLLER = "$";

	  // ��������
	  private String code;

	  // ��������
	  private String name;

	  private ParameterList(String code, String name) {
	    this.code = code;
	    this.name = name;
	  }

	  public String getCode() {
	    return this.code;
	  }

	  public String getName() {
	    return this.name;
	  }
}
