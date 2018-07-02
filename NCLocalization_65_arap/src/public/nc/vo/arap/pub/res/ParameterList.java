package nc.vo.arap.pub.res;

import nc.vo.ml.NCLangRes4VoTransl;

/***
 * 参数编码，名称
 * Add by bl 2015-04-22
 */
public enum ParameterList {

	  AP202("AP202", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0005")/* 付款录入单据合并规则*/), 
	  AP201("AP201", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0007")/* 付款管理单据合并规则*/),
	  AR102("AR102", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0006")/* 收款录入单据合并规则*/),
	  AR101("AR101", NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext", "summaryext-0008")/* 收款管理单据合并规则*/); 
	  
	  public static final String BIGSPLITKEY = "#";
	  public static final String SPLITKEY = ",";
	  public static final String SUFFIX = "_V";
	  public static final String DOLLER = "$";

	  // 参数编码
	  private String code;

	  // 参数名称
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
