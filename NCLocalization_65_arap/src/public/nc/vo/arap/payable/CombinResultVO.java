package nc.vo.arap.payable;

import java.util.Map;

import nc.vo.arap.pay.AggPayBillVO;
import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.pubapp.pattern.pub.MapList;

/***
 * 汇总表体VO
 * Add by bl 2015-04-23
 */
public class CombinResultVO {

	  // 缓存明细数据
	  private CombinCacheVO cachevo;

	  // 汇总后聚合VO
	  private AggPayBillVO[] combinvos;

	  public CombinResultVO(boolean combinflag) {
	    this.cachevo = new CombinCacheVO(combinflag);
	  }

	  public boolean getBcombinflag() {
	    return this.cachevo.getBcombinflag();
	  }

	  public CombinCacheVO getCacheVO() {
	    return this.cachevo;
	  }

	  public MapList<String, PayBillItemVO> getCombinRela() {
	    return this.cachevo.getCombinRela();
	  }

	  public AggPayBillVO[] getCombinvos() {
	    return this.combinvos;
	  }

	  public Map<String, String> getGroupKeys() {
	    return this.cachevo.getMapGroupKeys();
	  }

	  public void setBcombinflag(boolean combinflag) {
	    this.cachevo.setBcombinflag(combinflag);
	  }

	  public void setCachevo(CombinCacheVO cachevo) {
	    this.cachevo = cachevo;
	  }

	  public void setCombinrela(MapList<String, PayBillItemVO> combinrela) {
	    this.cachevo.setCombinrela(combinrela);
	  }

	  public void setCombinvos(AggPayBillVO[] combinvos) {
	    this.combinvos = combinvos;
	  }

	  public void setMapGroupKeys(Map<String, String> groupkeys) {
	    this.cachevo.setMapGroupKeys(groupkeys);
	  }

}
