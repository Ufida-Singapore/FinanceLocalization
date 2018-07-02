package nc.vo.arap.pub.enumeration;

import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.ml.NCLangRes4VoTransl;

/***
 * ����ѡ���ܹ�����д�ѡ��
 * Add by bl 2015-04-22
 */
public enum GatheringCombinRule {

    INVOICENO(GatheringBillItemVO.INVOICENO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000220")/* ��Ʊ�� */),
	SCOMMENT(GatheringBillItemVO.SCOMMENT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000426")/* ժҪ*/),
	OBJTYPE(GatheringBillItemVO.OBJTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000344")/* ��������*/),
	PK_CURRTYPE(GatheringBillItemVO.PK_CURRTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000315")/* ���� */),
	CUSTOMER(GatheringBillItemVO.CUSTOMER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000283")/* �ͻ� */),
	PK_DEPTID(GatheringBillItemVO.PK_DEPTID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000972")/* ����*/),
	PK_PSNDOC(GatheringBillItemVO.PK_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000013")/* ҵ��Ա*/),
	PREPAY(GatheringBillItemVO.PREPAY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000037")/* �տ�����*/),	
	PK_RECPAYTYPE("pk_recpaytype", NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000127")/* �տ�ҵ������*/),
	PRODUCTLINE(GatheringBillItemVO.PRODUCTLINE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000044")/* ��Ʒ��*/),
	PK_SUBJCODE(GatheringBillItemVO.PK_SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000442")/* ��֧��Ŀ*/),
	PAUSETRANSACT(GatheringBillItemVO.PAUSETRANSACT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000047")/* �����־*/),
	SUBJCODE(GatheringBillItemVO.SUBJCODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000662")/* ��Ŀ*/),
	PK_BALATYPE(GatheringBillItemVO.PK_BALATYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000712")/* ���㷽ʽ*/),
	CHECKNO(GatheringBillItemVO.CHECKNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000658")/* Ʊ�ݺ�*/),	
	CHECKTYPE(GatheringBillItemVO.CHECKTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000660")/* Ʊ������*/),
	CASHACCOUNT(GatheringBillItemVO.CASHACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000616")/* �ֽ��˻�*/),		
	RECACCOUNT(GatheringBillItemVO.RECACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000446")/* �տ������˻�*/),	
	PAYACCOUNT(GatheringBillItemVO.PAYACCOUNT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000063")/* ���������˻�*/),
    PROJECT(GatheringBillItemVO.PROJECT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001021")/* ��Ŀ */), 
	ISREFUSED(GatheringBillItemVO.ISREFUSED, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000005")/* �Ƿ񱻾ܸ� */), 
	COSTCENTER(GatheringBillItemVO.COSTCENTER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000105")/* �ɱ����� */), 
    PAYREASON(GatheringBillItemVO.PAYREASON, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000113")/* ֧��ԭ�� */), 
	PK_GATHERBILL(GatheringBillItemVO.PK_GATHERBILL, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("gatherbill","2gather-000123")/* �ͻ��տ��ʶ */), 
	SUPPLIER(GatheringBillItemVO.SUPPLIER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000089")/* ��Ӧ�� */), 
	SETTLEMONEY(GatheringBillItemVO.SETTLEMONEY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000119")/* Ӧ�ս�� */), 
	SETTLECURR(GatheringBillItemVO.SETTLECURR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000120")/* Ӧ�ձ��� */), 
	OUTSTORENO(GatheringBillItemVO.OUTSTORENO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000149")/* ���ⵥ�� */), 
	POSTUNIT(GatheringBillItemVO.POSTUNIT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000416")/* ���ۼ�����λ */), 
	POSTPRICENOTAX(GatheringBillItemVO.POSTPRICENOTAX, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000413")/* ���۵�λ��˰���� */), 
	POSTQUANTITY(GatheringBillItemVO.POSTQUANTITY, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000412")/* ���۵�λ���� */), 
	POSTPRICE(GatheringBillItemVO.POSTPRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000410")/* ���۵�λ��˰���� */), 
	CHECKDIRECTION(GatheringBillItemVO.CHECKDIRECTION, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000659")/* Ʊ�ݷ��� */), 
	COORDFLAG(GatheringBillItemVO.COORDFLAG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000185")/* ����Эͬ״̬ */), 
	EQUIPMENTCODE(GatheringBillItemVO.EQUIPMENTCODE, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000892")/* �豸���� */), 
	CASHITEM(GatheringBillItemVO.CASHITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000615")/* �ֽ�������Ŀ */), 
	PAYFLAG(GatheringBillItemVO.PAYFLAG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000437")/* ֧��״̬ */), 
	BANKROLLPROJET(GatheringBillItemVO.BANKROLLPROJET, NCLangRes4VoTransl
			.getNCLangRes().getStrByID("receivablebill","2UC000-000941")/* �ʽ�ƻ���Ŀ */), 
	BILLDATE(GatheringBillItemVO.BILLDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000190")/* �������� */), 
	PAYMAN(GatheringBillItemVO.PAYMAN, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000433")/* ֧���� */), 
	PAYDATE(GatheringBillItemVO.PAYDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000435")/* ֧������ */),
	PK_GROUP(GatheringBillItemVO.PK_GROUP, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000367")/* �������� */),
	PK_BILLTYPE(GatheringBillItemVO.PK_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000197")/* �������ͱ��� */),
	BILLCLASS(GatheringBillItemVO.BILLCLASS, NCLangRes4VoTransl.getNCLangRes()
	        .getStrByID("receivablebill", "2UC000-000187")/* ���ݴ��� */),
	PK_TRADETYPE(GatheringBillItemVO.PK_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000015")/* �տ�����code */),
	PK_TRADETYPEID(GatheringBillItemVO.PK_TRADETYPEID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000041")/* �տ����� */),
	PK_GATHERITEM(GatheringBillItemVO.PK_GATHERITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000024")/* �տ�б�ʶ */),
	QUANTITY_CR(GatheringBillItemVO.QUANTITY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000928")/* �������� */),
	LOCAL_MONEY_CR(GatheringBillItemVO.LOCAL_MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000026")/* ��֯���ҽ��(����) */),
	MONEY_CR(GatheringBillItemVO.MONEY_CR, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000927")/* ����ԭ�ҽ�� */),
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
	PRICE(GatheringBillItemVO.PRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000175")/* ���� */),
	TAXPRICE(GatheringBillItemVO.TAXPRICE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000246")/* ��˰���� */),
	TAXRATE(GatheringBillItemVO.TAXRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000670")/* ˰�� */),
	TAXNUM(GatheringBillItemVO.TAXNUM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000669")/* ˰�� */),
//	TOP_BILLID(GatheringBillItemVO.TOP_BILLID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000003")/* �ϲ㵥������ */),
//	TOP_ITEMID(GatheringBillItemVO.TOP_ITEMID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000005")/* �ϲ㵥�������� */),
//	TOP_BILLTYPE(GatheringBillItemVO.TOP_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000004")/* �ϲ㵥������ */),
//	TOP_TRADETYPE(GatheringBillItemVO.TOP_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000002")/* �ϲ㽻������ */),
//	SRC_TRADETYPE(GatheringBillItemVO.SRC_TRADETYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000572")/* Դͷ�������� */),
//	SRC_BILLTYPE(GatheringBillItemVO.SRC_BILLTYPE, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000581")/* Դͷ�������� */),
//	SRC_BILLID(GatheringBillItemVO.SRC_BILLID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000575")/* Դͷ�������� */),
//	SRC_ITEMID(GatheringBillItemVO.SRC_ITEMID, NCLangRes4VoTransl.getNCLangRes()
//			.getStrByID("receivablebill", "2UC000-000582")/* Դͷ���������� */),
	BUSIDATE(GatheringBillItemVO.BUSIDATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000946")/* �������� */),
	BILLNO(GatheringBillItemVO.BILLNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000186")/* ���ݺ� */),
	ROWNO(GatheringBillItemVO.ROWNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000183")/* ���ݷ�¼�� */),
	PK_PAYTERM(GatheringBillItemVO.PK_PAYTERM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000443")/* �տ�Э�� */),
	PK_ORG(GatheringBillItemVO.PK_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000031")/* �տ������֯ */),
	PK_FIORG(GatheringBillItemVO.PK_FIORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000908")/* ������֯ */),
	PK_PCORG(GatheringBillItemVO.PK_PCORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000161")/* �������� */),
	SETT_ORG(GatheringBillItemVO.SETT_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000011")/* ���������֯ */),
	SETT_ORG_V(GatheringBillItemVO.SETT_ORG_V, NCLangRes4VoTransl.getNCLangRes()
		    .getStrByID("gatherbill", "2gather-000025")/* ���������֯�汾 */),
	PK_PCORG_V(GatheringBillItemVO.PK_PCORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000102")/* �������İ汾 */),
	PK_ORG_V(GatheringBillItemVO.PK_ORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000000")/* �տ������֯�汾 */),
	PK_FIORG_V(GatheringBillItemVO.PK_FIORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000910")/* ������֯�汾 */),
	SO_ORG(GatheringBillItemVO.SO_ORG, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000996")/* ������֯ */),
	SO_ORG_V(GatheringBillItemVO.SO_ORG_V, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000103")/* ������֯�汾 */),
	SO_TRANSTYPE(GatheringBillItemVO.SO_TRANSTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000995")/* ������������ */),
	SO_ORDERTYPE(GatheringBillItemVO.SO_ORDERTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001001")/* ���۶������� */),
	SO_PSNDOC(GatheringBillItemVO.SO_PSNDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000994")/* ����ҵ��Ա */),
	SO_DEPTID(GatheringBillItemVO.SO_DEPTID, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001002")/* ���۲��� */),
	ROWTYPE(GatheringBillItemVO.ROWTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000814")/* ������ */),
	DIRECTION(GatheringBillItemVO.DIRECTION, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000462")/* ���� */),
	PK_SSITEM(GatheringBillItemVO.PK_SSITEM, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000033")/* ���������� */),
	RATE(GatheringBillItemVO.RATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000699")/* ��֯���һ��� */),
	ORDERCUBASDOC(GatheringBillItemVO.ORDERCUBASDOC, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000878")/* �����ͻ� */),
	INNERORDERNO(GatheringBillItemVO.INNERORDERNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000904")/* ���������� */),
	ASSETPACTNO(GatheringBillItemVO.ASSETPACTNO, NCLangRes4VoTransl.getNCLangRes()
	        .getStrByID("receivablebill", "2UC000-000929")/* �ʲ���ͬ�� */),
	CONTRACTNO(GatheringBillItemVO.CONTRACTNO, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000234")/* ��ͬ�� */),
	FREECUST(GatheringBillItemVO.FREECUST, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000454")/* ɢ�� */),
	FACARD(GatheringBillItemVO.FACARD, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000023")/* �̶��ʲ���Ƭ�� */),
	PURCHASEORDER(GatheringBillItemVO.PURCHASEORDER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000877")/* ������ */),
	GROUPRATE(GatheringBillItemVO.GROUPRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001016")/* ���ű��һ��� */),
	GLOBALRATE(GatheringBillItemVO.GLOBALRATE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000119")/* ȫ�ֱ��һ��� */),
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
	PROJECT_TASK(GatheringBillItemVO.PROJECT_TASK, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-001023")/* ��Ŀ���� */),
	COMMPAYSTATUS(GatheringBillItemVO.COMMPAYSTATUS, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000401")/* �и�״̬ */),
	COMMPAYTYPE(GatheringBillItemVO.COMMPAYTYPE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000402")/* �и���̬ */),
	REFUSE_REASON(GatheringBillItemVO.REFUSE_REASON, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000045")/* �ܸ����� */),
	AGENTRECEIVEPRIMAL(GatheringBillItemVO.AGENTRECEIVEPRIMAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000004")/* ����ԭ�ҽ�� */),
	AGENTRECEIVELOCAL(GatheringBillItemVO.AGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000002")/* ������֯���� */),
	GROUPAGENTRECEIVELOCAL(GatheringBillItemVO.GROUPAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000003")/* ���ռ��ű��� */),
    GLOBALAGENTRECEIVELOCAL(GatheringBillItemVO.GLOBALAGENTRECEIVELOCAL, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000051")/* ����ȫ�ֱ��� */),
	COMMPAYER(GatheringBillItemVO.COMMPAYER, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("gatherbill", "2gather-000053")/* �и��� */),
	BANKRELATED_CODE(GatheringBillItemVO.BANKRELATED_CODE, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000299")/* ���˱�ʶ�� */),
	DEF30(GatheringBillItemVO.DEF30, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000772")/* �Զ�����30 */),
	DEF29(GatheringBillItemVO.DEF29, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000770")/* �Զ�����29 */),
	DEF28(GatheringBillItemVO.DEF28, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000769")/* �Զ�����28 */),
	DEF27(GatheringBillItemVO.DEF27, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000768")/* �Զ�����27 */),
	DEF26(GatheringBillItemVO.DEF26, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000767")/* �Զ�����26 */),
	DEF25(GatheringBillItemVO.DEF25, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000766")/* �Զ�����25 */),
	DEF24(GatheringBillItemVO.DEF24, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000765")/* �Զ�����24 */),
	DEF23(GatheringBillItemVO.DEF23, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000764")/* �Զ�����23 */),
	DEF22(GatheringBillItemVO.DEF22, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000763")/* �Զ�����22 */),
	DEF21(GatheringBillItemVO.DEF21, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000762")/* �Զ�����21 */),
	DEF20(GatheringBillItemVO.DEF20, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000761")/* �Զ�����20 */),
	DEF19(GatheringBillItemVO.DEF19, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000759")/* �Զ�����19 */),
	DEF18(GatheringBillItemVO.DEF18, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000758")/* �Զ�����18 */),
	DEF17(GatheringBillItemVO.DEF17, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000757")/* �Զ�����17 */),
	DEF16(GatheringBillItemVO.DEF16, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000756")/* �Զ�����16 */),
	DEF15(GatheringBillItemVO.DEF15, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000755")/* �Զ�����15 */),
	DEF14(GatheringBillItemVO.DEF14, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000754")/* �Զ�����14 */),
	DEF13(GatheringBillItemVO.DEF13, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000753")/* �Զ�����13 */),
    DEF12(GatheringBillItemVO.DEF12, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000752")/* �Զ�����12 */),
	DEF11(GatheringBillItemVO.DEF11, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000751")/* �Զ�����11 */),
	DEF10(GatheringBillItemVO.DEF10, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000750")/* �Զ�����10 */),
	DEF9(GatheringBillItemVO.DEF9, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000788")/* �Զ�����9 */),
	DEF8(GatheringBillItemVO.DEF8, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000787")/* �Զ�����8 */),
	DEF7(GatheringBillItemVO.DEF7, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000786")/* �Զ�����7 */),
    DEF6(GatheringBillItemVO.DEF6, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000785")/* �Զ�����6 */),
	DEF5(GatheringBillItemVO.DEF5, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000784")/* �Զ�����5 */),
	DEF4(GatheringBillItemVO.DEF4, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000782")/* �Զ�����4 */),
	DEF3(GatheringBillItemVO.DEF3, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000771")/* �Զ�����3 */),
	DEF2(GatheringBillItemVO.DEF2, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000760")/* �Զ�����2 */),
	DEF1(GatheringBillItemVO.DEF1, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000749")/* �Զ�����1 */),
	CHECKELEMENT(GatheringBillItemVO.CHECKELEMENT, NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("receivablebill", "2UC000-000912")/* ���κ���Ҫ�� */);
			

	
	
	// ��������
	private String key;

	// ��������
	private String name;

	private GatheringCombinRule(String key, String name) {
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
