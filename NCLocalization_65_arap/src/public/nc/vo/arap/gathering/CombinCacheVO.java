package nc.vo.arap.gathering;

import java.util.Map;

import nc.vo.pubapp.pattern.pub.MapList;

/**
 * �տ�ϲ��������
 * add by bl 2015-04-23
 */
public class CombinCacheVO {
  // �Ƿ���ܱ�־λ
  private boolean bcombinflag;

  // ���ܺ���ϸ���չ�ϵ
  private MapList<String, GatheringBillItemVO> combinrela =
      new MapList<String, GatheringBillItemVO>();

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
