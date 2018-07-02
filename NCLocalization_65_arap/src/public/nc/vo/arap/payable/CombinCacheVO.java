package nc.vo.arap.payable;

import java.util.Map;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.pubapp.pattern.pub.MapList;

public class CombinCacheVO {
	  // �Ƿ���ܱ�־λ
	  private boolean bcombinflag;

	  // ���ܺ���ϸ���չ�ϵ
	  private MapList<String, PayBillItemVO> combinrela =
	      new MapList<String, PayBillItemVO>();

	  // key:��Ʊ��֯ value:�ϲ��ֶ�
	  private Map<String, String> mapgroupkeys;

	  /**
	   * 
	   * 
	   * @param combinflag
	   */
	  public CombinCacheVO(boolean combinflag) {
	    this.bcombinflag = combinflag;
	  }

	  public boolean getBcombinflag() {
	    return this.bcombinflag;
	  }

	  public MapList<String, PayBillItemVO> getCombinRela() {
	    return this.combinrela;
	  }

	  public String getGroupKeys(String pk_org) {
	    return this.mapgroupkeys.get(pk_org);
	  }

	  public Map<String, String> getMapGroupKeys() {
	    return this.mapgroupkeys;
	  }

	  public void setBcombinflag(boolean combinflag) {
	    this.bcombinflag = combinflag;
	  }

	  public void setCombinrela(MapList<String, PayBillItemVO> combinrela) {
	    this.combinrela = combinrela;
	  }

	  public void setMapGroupKeys(Map<String, String> mapgroupkey) {
	    this.mapgroupkeys = mapgroupkey;
	  }
}
