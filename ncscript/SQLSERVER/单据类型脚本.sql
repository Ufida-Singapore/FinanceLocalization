delete from BD_FWDBILLTYPE where PK_FWDBILLTYPE = '1001ZZ1000000001VAUG';

delete from BD_FWDBILLTYPE where PK_FWDBILLTYPE = '1001ZZ1000000001VAXP';


INSERT INTO BD_FWDBILLTYPE (DR, ISBIZFLOWBILL, ISCROSSGRP, PK_BACKBILLTYPE, PK_BILLTYPE, PK_BILLTYPEID, PK_FWDBILLTYPE, TS) VALUES (0, 'Y', 'N', 'F2', 'F1', '0000Z3000000000000F2', '1001ZZ1000000001VAUG', '2016-06-30 09:57:16');


INSERT INTO BD_FWDBILLTYPE (DR, ISBIZFLOWBILL, ISCROSSGRP, PK_BACKBILLTYPE, PK_BILLTYPE, PK_BILLTYPEID, PK_FWDBILLTYPE, TS) VALUES (0, 'Y', 'N', 'F3', 'F0', '0000Z3000000000000F3', '1001ZZ1000000001VAXP', '2016-06-30 10:29:35');



DELETE FROM bd_billtype where pk_billtypecode in ('F2','F3');
INSERT INTO BD_BILLTYPE (ACCOUNTCLASS, BILLCODERULE, BILLSTYLE, BILLTYPENAME, BILLTYPENAME2, BILLTYPENAME3, BILLTYPENAME4, BILLTYPENAME5, BILLTYPENAME6, CANEXTENDTRANSACTION, CHECKCLASSNAME, CLASSNAME, COMP, COMPONENT, DATAFINDERCLZ, DEF1, DEF2, DEF3, DR, EMENDENUMCLASS, FORWARDBILLTYPE, ISACCOUNT, ISAPPROVEBILL, ISBIZFLOWBILL, ISEDITABLEPROPERTY, ISENABLEBUTTON, ISENABLETRANSTYPEBCR, ISLOCK, ISROOT, ISTRANSACTION, NCBRCODE, NODECODE, PARENTBILLTYPE, PK_BILLTYPECODE, PK_BILLTYPEID, PK_GROUP, PK_ORG, REFERCLASSNAME, SYSTEMCODE, TRANSTYPE_CLASS, TS, WEBNODECODE, WHERESTRING) VALUES (null, '~', 104, 'Pmt Doc', N'付款单', null, null, null, null, 'Y', 'nc.bs.arap.global.ArapCheckStatusCallBackImpl', '<X>20080EBM', null, 'arap_paybill', null, null, null, null, null, null, '36J1,36J3,F1,F2,F0', 'Y', 'Y', 'Y', 'N', 'N', null, null, 'Y', 'N', '~', '20080EBR', '~', 'F3', '0000Z3000000000000F3', '~', 'GLOBLE00000000000000', 'nc.vo.arap.pf.ArapPrintService', 'AP', null, '2017-08-22 19:28:30', '~', null);
INSERT INTO BD_BILLTYPE (ACCOUNTCLASS, BILLCODERULE, BILLSTYLE, BILLTYPENAME, BILLTYPENAME2, BILLTYPENAME3, BILLTYPENAME4, BILLTYPENAME5, BILLTYPENAME6, CANEXTENDTRANSACTION, CHECKCLASSNAME, CLASSNAME, COMP, COMPONENT, DATAFINDERCLZ, DEF1, DEF2, DEF3, DR, EMENDENUMCLASS, FORWARDBILLTYPE, ISACCOUNT, ISAPPROVEBILL, ISBIZFLOWBILL, ISEDITABLEPROPERTY, ISENABLEBUTTON, ISENABLETRANSTYPEBCR, ISLOCK, ISROOT, ISTRANSACTION, NCBRCODE, NODECODE, PARENTBILLTYPE, PK_BILLTYPECODE, PK_BILLTYPEID, PK_GROUP, PK_ORG, REFERCLASSNAME, SYSTEMCODE, TRANSTYPE_CLASS, TS, WEBNODECODE, WHERESTRING) VALUES (null, null, 103, 'Collection Doc', N'收款单', null, null, null, null, 'Y', 'nc.bs.arap.global.ArapCheckStatusCallBackImpl', '<X>20060GBM', null, 'gatheringbill', null, null, null, null, null, null, '36J2,F0,36J3,36J1,F2,F3,F1', 'Y', 'Y', 'Y', null, null, null, null, 'Y', 'N', null, '20060GBR', '~', 'F2', '0000Z3000000000000F2', '~', 'GLOBLE00000000000000', 'nc.vo.arap.pf.ArapPrintService', 'AR', null, '2017-08-22 19:28:30', '~', null);
