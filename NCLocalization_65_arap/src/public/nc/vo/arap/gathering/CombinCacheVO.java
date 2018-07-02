package nc.vo.arap.gathering;

import java.util.Map;

import nc.vo.pubapp.pattern.pub.MapList;

/**
 * 收款单合并缓存对象
 * add by bl 2015-04-23
 */
public class CombinCacheVO {
  // 是否汇总标志位
  private boolean bcombinflag;

  // 汇总和明细对照关系
  private MapList<String, GatheringBillItemVO> combinrela =
      new MapList<String, GatheringBillItemVO>();

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

  public MapList<String, GatheringBillItemVO> getCombinRela() {
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

  public void setCombinrela(MapList<String, GatheringBillItemVO> combinrela) {
    this.combinrela = combinrela;
  }

  public void setMapGroupKeys(Map<String, String> mapgroupkey) {
    this.mapgroupkeys = mapgroupkey;
  }
}
