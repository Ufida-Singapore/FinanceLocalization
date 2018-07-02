package nc.vo.arap.payable;

import java.util.Map;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.pubapp.pattern.pub.MapList;

public class CombinCacheVO {
	  // 是否汇总标志位
	  private boolean bcombinflag;

	  // 汇总和明细对照关系
	  private MapList<String, PayBillItemVO> combinrela =
	      new MapList<String, PayBillItemVO>();

	  // key:开票组织 value:合并字段
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
