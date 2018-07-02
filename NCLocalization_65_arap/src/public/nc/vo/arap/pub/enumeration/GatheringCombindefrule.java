package nc.vo.arap.pub.enumeration;

import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * ��͡���ƽ���ֶ�
 * Add by bl 2015-04-22
 */
public enum GatheringCombindefrule {

	LOCAL_MONEY_CR(GatheringBillItemVO.LOCAL_MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000026")/* ��֯���ҽ��(����)*/),		
	MONEY_CR(GatheringBillItemVO.MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000927")/* ����ԭ�ҽ��*/),
	SETTLEMONEY(GatheringBillItemVO.SETTLEMONEY, NCLangRes4VoTransl.getNCLangRes()
		    .getStrByID("gatherbill", "2gather-000119")/* Ӧ�ս�� */), 
	MONEY_BAL(GatheringBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000207")/* ԭ����� */),
	LOCAL_MONEY_BAL(GatheringBillItemVO.LOCAL_MONEY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000698")/* ��֯������� */),
	QUANTITY_BAL(GatheringBillItemVO.QUANTITY_BAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000460")/* ������� */),
	LOCAL_TAX_CR(GatheringBillItemVO.LOCAL_TAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000010")/* ˰�� */),
	NOTAX_CR(GatheringBillItemVO.NOTAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000043")/* ����ԭ����˰��� */),
	LOCAL_NOTAX_CR(GatheringBillItemVO.LOCAL_NOTAX_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000032")/* ��֯������˰���(����) */),
	GROUPCREBIT(GatheringBillItemVO.GROUPCREBIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000021")/* ���ű��ҽ��(����) */),
	GLOBALCREBIT(GatheringBillItemVO.GLOBALCREBIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000039")/* ȫ�ֱ��ҽ��(����) */),
	GROUPBALANCE(GatheringBillItemVO.GROUPBALANCE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001014")/* ���ű������ */),
	GLOBALBALANCE(GatheringBillItemVO.GLOBALBALANCE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000117")/* ȫ�ֱ������ */),
	GROUPNOTAX_CRE(GatheringBillItemVO.GROUPNOTAX_CRE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000019")/* ���ű�����˰���(����) */),
	GLOBALNOTAX_CRE(GatheringBillItemVO.GLOBALNOTAX_CRE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000055")/* ȫ�ֱ�����˰���(����) */),
	OCCUPATIONMNY(GatheringBillItemVO.OCCUPATIONMNY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000017")/* Ԥռ��ԭ����� */),
	AGENTRECEIVEPRIMAL(GatheringBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000004")/* ����ԭ�ҽ�� */),
	AGENTRECEIVELOCAL(GatheringBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000002")/* ������֯���� */),
	GROUPAGENTRECEIVELOCAL(GatheringBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000003")/* ���ռ��ű��� */),
    GLOBALAGENTRECEIVELOCAL(GatheringBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000051")/* ����ȫ�ֱ��� */),
	POSTQUANTITY(GatheringBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000412")/* ���۵�λ���� */), 
	QUANTITY_CR(GatheringBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000928")/* �������� */);
					
			
			
			
  // ��������
  private String key;

  // ��������
  private String name;

  private GatheringCombindefrule(String key, String name) {
    this.key = key;
    this.name = name;
  }

  public String getKey() {
    return this.key;
  }

  public String getName() {
    return this.name;
  }

}
