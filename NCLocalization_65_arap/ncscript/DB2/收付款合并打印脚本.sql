delete pub_sysinittemp where initcode in ('AP202','AP201','AP201_V','AP202_V','AR101','AR101_V','AR102','AR102_V');

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'nc.ui.arap.pub.para.PayBillCombinDlg', '2008', null, '', 'AP201', '付款管理单据合并', 'AP201', '付款管理单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ10000000014DPF', '', 'Y', 0, 'Y', 40, '2015-04-27 15:07:06', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'invoiceno,pk_currtype,supplier', '2008', null, '', 'AP201', '付款管理单据合并', 'AP201_V', '付款管理单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ10000000014EHR', '', 'N', 0, 'Y', 41, '2015-06-10 11:52:12', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'nc.ui.arap.pub.para.PayEntryCombinDlg', '2008', null, '', 'AP202', '付款录入单据合并', 'AP202', '付款录入单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ10000000014YK5', '', 'Y', 0, 'Y', 42, '2015-04-27 16:22:02', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'invoiceno,pk_currtype,supplier', '2008', null, '', 'AP202', '付款录入单据合并', 'AP202_V', '付款录入单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ10000000014YKQ', '', 'N', 0, 'Y', 43, '2015-06-10 11:52:26', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'nc.ui.arap.pub.para.GatheringCombinDlg', '2006', null, '', 'AR101', '收款管理单据合并', 'AR101', '收款管理单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ1000000001494H', '', 'Y', 0, 'Y', 0, '2015-04-28 17:53:02', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'invoiceno,pk_currtype,customer', '2006', null, '', 'AR101', '收款管理单据合并', 'AR101_V', '收款管理单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ10000000014C6H', '', 'N', 0, 'Y', 0, '2015-04-28 17:53:12', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'nc.ui.arap.pub.para.GatherEntryCombinDlg', '2006', null, '', 'AR102', '收款录入单据合并', 'AR102', '收款录入单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ1000000001647E', '', 'Y', 0, 'Y', 0, '2015-05-06 18:04:20', '', 2);

insert into pub_sysinittemp (AFTERCLASS, CHECKCLASS, DATACLASS, DATAORIGINFLAG, DEFAULTVALUE, DOMAINFLAG, DR, EDITCOMPONENTCTRLCLASS, GROUPCODE, GROUPNAME, INITCODE, INITNAME, MAINFLAG, MUTEXFLAG, ORGTYPECONVERTMODE, PARATYPE, PK_ORGTYPE, PK_REFINFO, PK_SYSINITTEMP, REMARK, SHOWFLAG, STATEFLAG, SYSFLAG, SYSINDEX, TS, VALUELIST, VALUETYPE)
values ('', '', '', 0, 'invoiceno,pk_currtype,customer', '2006', null, '', 'AR102', '收款录入单据合并', 'AR102_V', '收款录入单据合并规则', 'N', 0, 'FINANCEORGTYPE000000', 'business', 'FINANCEORGTYPE000000', '~', 'TTS1ZZ1000000001647F', '', 'N', 0, 'Y', 0, '2015-05-06 18:05:19', '', 2);


delete sm_paramregister where parentid in (select cfunid from sm_funcregister where funcode in ('20060GBR','20060GBM','20080EBR','20080EBM')) and paramvalue in('nc\ui\arap\config\payablebill_Ext.xml','nc\ui\arap\config\payentrybill_Ext.xml','nc\ui\arap\config\gatherentrybill_Ext.xml','nc\ui\arap\config\gatheringbill_Ext.xml');
insert into sm_paramregister (DR, PARAMNAME, PARAMVALUE, PARENTID, PK_PARAM, TS)
values (0, 'PluginBeanConfigFilePath', 'nc\ui\arap\config\payablebill_Ext.xml', '1001Z310000000009NWI', 'TTS1ZZ10000000014YJF', '2015-04-27 15:56:25');

insert into sm_paramregister (DR, PARAMNAME, PARAMVALUE, PARENTID, PK_PARAM, TS)
values (0, 'PluginBeanConfigFilePath', 'nc\ui\arap\config\payentrybill_Ext.xml', '1001Z310000000002WXV', 'TTS1ZZ10000000014YLD', '2015-04-27 16:33:15');

insert into sm_paramregister (DR, PARAMNAME, PARAMVALUE, PARENTID, PK_PARAM, TS)
values (0, 'PluginBeanConfigFilePath', 'nc\ui\arap\config\gatherentrybill_Ext.xml', '1001Z310000000001EXF', 'TTS1ZZ10000000014TX2', '2015-04-27 11:54:05');

insert into sm_paramregister (DR, PARAMNAME, PARAMVALUE, PARENTID, PK_PARAM, TS)
values (0, 'PluginBeanConfigFilePath', 'nc\ui\arap\config\gatheringbill_Ext.xml', '1001Z310000000016ZMM', 'TTS1ZZ10000000014IC2', '2015-04-23 12:12:22');

