package nc.vo.arap.pub.enumeration;

import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * ����ѡ���ܹ�����д�ѡ��
 * Add by bl 2015-04-22
 */
public enum PayBillCombinRule {
	
	INVOICENO(PayBillItemVO.INVOICENO, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000220")/* ��Ʊ�� */),		
	SCOMMENT(PayBillItemVO.SCOMMENT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000426")/* ժҪ */),	
    OBJTYPE(PayBillItemVO.OBJTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000344")/* ��������*/),	
	SUPPLIER(PayBillItemVO.SUPPLIER, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000089")/* ��Ӧ��*/),
	CUSTOMER(PayBillItemVO.CUSTOMER, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000283")/* �ͻ� */),
	PK_DEPTID(PayBillItemVO.PK_DEPTID, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000972")/* ����*/),
	PK_PSNDOC(PayBillItemVO.PK_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000013")/* ҵ��Ա*/),	
	PK_TRADETYPEID(PayBillItemVO.PK_TRADETYPEID, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("paybill", "2paybill-000126")/* ����ҵ������*/),
	PREPAY(PayBillItemVO.PREPAY, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("paybill", "2paybill-000011")/* ��������*/),
	PK_CURRTYPE(PayBillItemVO.PK_CURRTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000315")/* ���� */),
	CHECKNO(PayBillItemVO.CHECKNO, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000658")/* Ʊ�ݺ�*/),	
	CHECKTYPE(PayBillItemVO.CHECKTYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000660")/* Ʊ������*/),
	CASHACCOUNT(PayBillItemVO.CASHACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000616")/* �ֽ��˻�*/),	
	RECACCOUNT(PayBillItemVO.RECACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000446")/* �տ������˻�*/),	
	PAYACCOUNT(PayBillItemVO.PAYACCOUNT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000063")/* ���������˻�*/),
	PRODUCTLINE(PayBillItemVO.PRODUCTLINE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000044")/* ��Ʒ��*/),
	PAUSETRANSACT(PayBillItemVO.PAUSETRANSACT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("gatherbill", "2gather-000047")/* �����־*/),
	PK_SUBJCODE(PayBillItemVO.PK_SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000442")/* ��֧��Ŀ*/),
	SUBJCODE(PayBillItemVO.SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000662")/* ��Ŀ*/),
	PK_BALATYPE(PayBillItemVO.PK_BALATYPE, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-000712")/* ���㷽ʽ*/),
	PROJECT(PayBillItemVO.PROJECT, NCLangRes4VoTransl.getNCLangRes()
		.getStrByID("receivablebill", "2UC000-001021")/* ��Ŀ*/),	
	PK_BILLTYPE(PayBillItemVO.PK_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000197")/* �������ͱ��� */),
	BILLCLASS(PayBillItemVO.BILLCLASS, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000187")/* ���ݴ��� */),
	PK_TRADETYPE(PayBillItemVO.PK_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000050")/* ��������code */),
	PK_PAYITEM(PayBillItemVO.PK_PAYITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000047")/* ����б�ʶ */),
	BUSIDATE(PayBillItemVO.BUSIDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000946")/* �������� */),
	BILLNO(PayBillItemVO.BILLNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000186")/* ���ݺ� */),
	ROWTYPE(PayBillItemVO.ROWTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000814")/* ������ */),
	DIRECTION(PayBillItemVO.DIRECTION, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000462")/* ���� */),
	PK_SSITEM(PayBillItemVO.PK_SSITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000033")/* ���������� */),
	RATE(PayBillItemVO.RATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000699")/* ��֯���һ��� */),
	MONEY_DE(PayBillItemVO.MONEY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000105")/* �跽ԭ�ҽ�� */),
	LOCAL_MONEY_DE(PayBillItemVO.LOCAL_MONEY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000013")/* ��֯���ҽ��(�跽) */),
	QUANTITY_DE(PayBillItemVO.QUANTITY_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000106")/* �跽���� */),
	QUANTITY_CR(PayBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000928")/* �������� */),
	MONEY_BAL(PayBillItemVO.MONEY_BAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000207")/* ԭ����� */),
	PU_ORG(PayBillItemVO.PU_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000980")/* �ɹ���֯ */),
	PU_ORG_V(PayBillItemVO.PU_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000100")/* �ɹ���֯�汾���� */),
	SETT_ORG_V(PayBillItemVO.SETT_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000104")/* ���������֯�汾���� */),
	PK_PCORG_V(PayBillItemVO.PK_PCORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000102")/* �������İ汾���� */),
	PK_ORG_V(PayBillItemVO.PK_ORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000041")/* ���������֯�汾���� */),
	PK_FIORG_V(PayBillItemVO.PK_FIORG_V, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000911")/* ������֯�汾���� */),
	PU_PSNDOC(PayBillItemVO.PU_PSNDOC, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000976")/* �ɹ�ҵ��Ա */),
	PU_DEPTID(PayBillItemVO.PU_DEPTID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000984")/* �ɹ����� */),
	MATERIAL(PayBillItemVO.MATERIAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000594")/* ���� */),
	POSTUNIT(PayBillItemVO.POSTUNIT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000416")/* ���ۼ�����λ */),
	POSTPRICENOTAX(PayBillItemVO.POSTPRICENOTAX, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000413")/* ���۵�λ��˰���� */),
	POSTQUANTITY(PayBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000412")/* ���۵�λ���� */),
	POSTPRICE(PayBillItemVO.POSTPRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000410")/* ���۵�λ��˰���� */),
	CHECKDIRECTION(PayBillItemVO.CHECKDIRECTION, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000659")/* Ʊ�ݷ��� */),
	COORDFLAG(PayBillItemVO.COORDFLAG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000185")/* ����Эͬ״̬ */),
	EQUIPMENTCODE(PayBillItemVO.EQUIPMENTCODE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000892")/* �豸���� */),
	CASHITEM(PayBillItemVO.CASHITEM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000615")/* �ֽ�������Ŀ */),
	PAYFLAG(PayBillItemVO.PAYFLAG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000437")/* ֧��״̬ */),
	BANKROLLPROJET(PayBillItemVO.BANKROLLPROJET, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000941")/* �ʽ�ƻ���Ŀ */),
	BILLDATE(PayBillItemVO.BILLDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000190")/* �������� */),
	PAYMAN(PayBillItemVO.PAYMAN, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000433")/* ֧���� */),
	PAYDATE(PayBillItemVO.PAYDATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000435")/* ֧������ */),
	PK_GROUP(PayBillItemVO.PK_GROUP, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000367")/* �������� */),
	PK_PCORG(PayBillItemVO.PK_PCORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000161")/* �������� */),
	SETT_ORG(PayBillItemVO.SETT_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000715")/* ���������֯ */),
	PK_ORG(PayBillItemVO.PK_ORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000028")/* ���������֯ */),
	PK_FIORG(PayBillItemVO.PK_FIORG, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000908")/* ������֯ */),
	DEF17(PayBillItemVO.DEF17, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000757")/* �Զ�����17 */),
	DEF16(PayBillItemVO.DEF16, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000756")/* �Զ�����16 */),
	DEF15(PayBillItemVO.DEF15, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000755")/* �Զ�����15 */),
	DEF14(PayBillItemVO.DEF14, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000754")/* �Զ�����14 */),
	DEF13(PayBillItemVO.DEF13, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000753")/* �Զ�����13 */),
	DEF12(PayBillItemVO.DEF12, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000752")/* �Զ�����12 */),
	DEF11(PayBillItemVO.DEF11, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000751")/* �Զ�����11 */),
	DEF10(PayBillItemVO.DEF10, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000750")/* �Զ�����10 */),
	DEF9(PayBillItemVO.DEF9, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000788")/* �Զ�����9 */),
	DEF8(PayBillItemVO.DEF8, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000787")/* �Զ�����8 */),
	DEF7(PayBillItemVO.DEF7, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000786")/* �Զ�����7 */),
	DEF4(PayBillItemVO.DEF4, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000782")/* �Զ�����4 */),
	DEF3(PayBillItemVO.DEF3, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000771")/* �Զ�����3 */),
	DEF2(PayBillItemVO.DEF2, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000760")/* �Զ�����2 */),
	DEF1(PayBillItemVO.DEF1, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000749")/* �Զ�����1 */),
	CHECKELEMENT(PayBillItemVO.CHECKELEMENT, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000912")/* ���κ���Ҫ�� */),
	GROUPRATE(PayBillItemVO.GROUPRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001016")/* ���ű��һ��� */),
	GLOBALRATE(PayBillItemVO.GLOBALRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000119")/* ȫ�ֱ��һ��� */),
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
	DEF6(PayBillItemVO.DEF6, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000785")/* �Զ�����6 */),
	DEF5(PayBillItemVO.DEF5, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000784")/* �Զ�����5 */),
	ISREFUSED(PayBillItemVO.ISREFUSED, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000012")/* �Ƿ񱻾ܸ� */),
	COSTCENTER(PayBillItemVO.COSTCENTER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000106")/* �ɱ����� */),
	PAYREASON(PayBillItemVO.PAYREASON, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000113")/* ֧��ԭ�� */),
	PK_PAYBILL(PayBillItemVO.PK_PAYBILL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000122")/* �����ʶ */),
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
	PRICE(PayBillItemVO.PRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000175")/* ���� */),
	TAXPRICE(PayBillItemVO.TAXPRICE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000002")/* ��˰���� */),
	TAXRATE(PayBillItemVO.TAXRATE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000670")/* ˰�� */),
	TAXNUM(PayBillItemVO.TAXNUM, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000669")/* ˰�� */),
//	TOP_BILLTYPE(PayBillItemVO.TOP_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000004")/* �ϲ㵥������ */),
//	TOP_TRADETYPE(PayBillItemVO.TOP_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000002")/* �ϲ㽻������ */),
//		SRC_TRADETYPE(PayBillItemVO.SRC_TRADETYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000572")/* Դͷ�������� */),
//		SRC_BILLTYPE(PayBillItemVO.SRC_BILLTYPE, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000581")/* Դͷ�������� */),
//		SRC_BILLID(PayBillItemVO.SRC_BILLID, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000575")/* Դͷ�������� */),
//		SRC_ITEMID(PayBillItemVO.SRC_ITEMID, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000582")/* Դͷ���������� */),
//		PK_PAYTERM(PayBillItemVO.PK_PAYTERM, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("receivablebill", "2UC000-000055")/* ����Э�� */),
//	TOP_TERMCH(PayBillItemVO.TOP_TERMCH, NCLangRes4VoTransl.getNCLangRes().
//		getStrByID("paybill", "2paybill-000014")/* ��Դ����Э���� */),
	ORDERCUBASDOC(PayBillItemVO.ORDERCUBASDOC, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000019")/* ������Ӧ�� */),
	INNERORDERNO(PayBillItemVO.INNERORDERNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000904")/* ���������� */),
	BANKRELATED_CODE(PayBillItemVO.BANKRELATED_CODE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000299")/* ���˱�ʶ�� */),
	PROJECT_TASK(PayBillItemVO.PROJECT_TASK, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-001023")/* ��Ŀ���� */),
	COMMPAYTYPE(PayBillItemVO.COMMPAYTYPE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000006")/* �и����� */),
	COMMPAYSTATUS(PayBillItemVO.COMMPAYSTATUS, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000021")/* �и�״̬ */),
	REFUSE_REASON(PayBillItemVO.REFUSE_REASON, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000043")/* �ܸ����� */),
	ASSETPACTNO(PayBillItemVO.ASSETPACTNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000929")/* �ʲ���ͬ�� */),
	CONTRACTNO(PayBillItemVO.CONTRACTNO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000234")/* ��ͬ�� */),
	FREECUST(PayBillItemVO.FREECUST, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000454")/* ɢ�� */),
	FACARD(PayBillItemVO.FACARD, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000025")/* �̶��ʲ���Ƭ�� */),
	PURCHASEORDER(PayBillItemVO.PURCHASEORDER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000877")/* ������ */),
	AGENTRECEIVEPRIMAL(PayBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000010")/* ����ԭ�ҽ�� */),
	AGENTRECEIVELOCAL(PayBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000001")/* ������֯���� */),
	GROUPAGENTRECEIVELOCAL(PayBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000009")/* ���ռ��ű��� */),
	GLOBALAGENTRECEIVELOCAL(PayBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000048")/* ����ȫ�ֱ��� */),
	COMMPAYER(PayBillItemVO.COMMPAYER, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000049")/* �и��� */),
	OUTSTORENO(PayBillItemVO.OUTSTORENO, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000149")/* ���ⵥ�� */),
	DEF30(PayBillItemVO.DEF30, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000772")/* �Զ�����30 */),
	DEF29(PayBillItemVO.DEF29, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000770")/* �Զ�����29 */),
	DEF28(PayBillItemVO.DEF28, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000769")/* �Զ�����28 */),
	DEF27(PayBillItemVO.DEF27, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000768")/* �Զ�����27 */),
	DEF26(PayBillItemVO.DEF26, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000767")/* �Զ�����26 */),
	DEF25(PayBillItemVO.DEF25, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000766")/* �Զ�����25 */),
	DEF24(PayBillItemVO.DEF24, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000765")/* �Զ�����24 */),
	DEF23(PayBillItemVO.DEF23, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000764")/* �Զ�����23 */),
	DEF22(PayBillItemVO.DEF22, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000763")/* �Զ�����22 */),
	DEF21(PayBillItemVO.DEF21, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000762")/* �Զ�����21 */),
	DEF20(PayBillItemVO.DEF20, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000761")/* �Զ�����20 */),
	DEF19(PayBillItemVO.DEF19, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000759")/* �Զ�����19 */),
	DEF18(PayBillItemVO.DEF18, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000758")/* �Զ�����18 */),
	TOP_BILLID(PayBillItemVO.TOP_BILLID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000003")/* �ϲ㵥������ */),
	TOP_ITEMID(PayBillItemVO.TOP_ITEMID, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("receivablebill", "2UC000-000005")/* �ϲ㵥�������� */),
	GLOBALNOTAX_DE(PayBillItemVO.GLOBALNOTAX_DE, NCLangRes4VoTransl.getNCLangRes().
		getStrByID("paybill", "2paybill-000029")/* ȫ�ֱ�����˰���(�跽) */);
			
	// ��������
	private String key;

	// ��������
	private String name;

	private PayBillCombinRule(String key, String name) {
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