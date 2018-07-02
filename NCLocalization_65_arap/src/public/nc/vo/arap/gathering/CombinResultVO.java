package nc.vo.arap.gathering;

import java.util.Map;

import nc.vo.pubapp.pattern.pub.MapList;

/***
 * ���ܱ���VO
 * Add by bl 2015-04-23
 */
public class CombinResultVO {

	  // ������ϸ����
	  private CombinCacheVO cachevo;

	  // ���ܺ�ۺ�VO
	  private AggGatheringBillVO[] combinvos;

	  public CombinResultVO(boolean combinflag) {
	    this.cachevo = new CombinCacheVO(combinflag);
	  }

	  public boolean getBcombinflag() {
	    return this.cachevo.getBcombinflag();
	  }

	  public CombinCacheVO getCacheVO() {
	    return this.cachevo;
	  }

	  public MapList<String, GatheringBillItemVO> getCombinRela() {
	    return this.cachevo.getCombinRela();
	  }

	  public AggGatheringBillVO[] getCombinvos() {
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

	  public void setCombinrela(MapList<String, GatheringBillItemVO> combinrela) {
	    this.cachevo.setCombinrela(combinrela);
	  }

	  public void setCombinvos(AggGatheringBillVO[] combinvos) {
	    this.combinvos = combinvos;
	  }

	  public void setMapGroupKeys(Map<String, String> groupkeys) {
	    this.cachevo.setMapGroupKeys(groupkeys);
	  }

}
