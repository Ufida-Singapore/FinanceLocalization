package nc.vo.arap.pub.enumeration;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * ��͡���ƽ���ֶ�
 * Add by bl 2015-04-22
 */
public enum PayBillCombindefrule {

	LOCAL_MONEY_DE(PayBillItemVO.LOCAL_MONEY_DE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("paybill", "2paybill-000013")/* ��֯���ҽ��(�跽)*/),	
	MONEY_DE(PayBillItemVO.MONEY_DE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000105")/* �跽ԭ�ҽ��*/),
	GROUPDEBIT(PayBillItemVO.GROUPDEBIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000007")/* ���ű��ҽ��(�跽) */),
	GLOBALDEBIT(PayBillItemVO.GLOBALDEBIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000005")/* ȫ�ֱ��ҽ��(�跽) */),
	GROUPBALANCE(PayBillItemVO.GROUPBALANCE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001014")/* ���ű������ */),
	GLOBALBALANCE(PayBillItemVO.GLOBALBALANCE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000117")/* ȫ�ֱ������ */),
	GROUPNOTAX_DE(PayBillItemVO.GROUPNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000042")/* ���ű�����˰���(�跽) */),
    OCCUPATIONMNY(PayBillItemVO.OCCUPATIONMNY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000004")/* Ԥռ�ú���ԭ����� */),
	LOCAL_MONEY_BAL(PayBillItemVO.LOCAL_MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000698")/* ��֯������� */),
	QUANTITY_BAL(PayBillItemVO.QUANTITY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000460")/* ������� */),
	LOCAL_TAX_DE(PayBillItemVO.LOCAL_TAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000018")/* ˰�� */),
	NOTAX_DE(PayBillItemVO.NOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000030")/* �跽ԭ����˰��� */),
	LOCAL_NOTAX_DE(PayBillItemVO.LOCAL_NOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000026")/* ��֯������˰���(�跽) */),
	AGENTRECEIVEPRIMAL(PayBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000010")/* ����ԭ�ҽ�� */),
	AGENTRECEIVELOCAL(PayBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000001")/* ������֯���� */),
	GROUPAGENTRECEIVELOCAL(PayBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000009")/* ���ռ��ű��� */),
	GLOBALAGENTRECEIVELOCAL(PayBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000048")/* ����ȫ�ֱ��� */),
	GLOBALNOTAX_DE(PayBillItemVO.GLOBALNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000029")/* ȫ�ֱ�����˰���(�跽) */),
	QUANTITY_DE(PayBillItemVO.QUANTITY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000106")/* �跽���� */),
	QUANTITY_CR(PayBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000928")/* �������� */),
	MONEY_BAL(PayBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000207")/* ԭ����� */),
	POSTQUANTITY(PayBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000412")/* ���۵�λ���� */);

	// ��������
	private String key;

	// ��������
	private String name;

	private PayBillCombindefrule(String key, String name) {
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