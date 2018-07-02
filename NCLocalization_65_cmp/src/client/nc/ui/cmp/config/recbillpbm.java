package nc.ui.cmp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class recbillpbm extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.cmp.bill.actions.CmpBillDeleteScheduleAction getDelScheduleAction(){
 if(context.get("delScheduleAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillDeleteScheduleAction)context.get("delScheduleAction");
  nc.ui.cmp.bill.actions.CmpBillDeleteScheduleAction bean = new nc.ui.cmp.bill.actions.CmpBillDeleteScheduleAction();
  context.put("delScheduleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setSingleBillView(getBillFormEditor());
  bean.setSingleBillService(getDeleteBillService());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.SeparatorAction getSeparatorAction(){
 if(context.get("separatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("separatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("separatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpQuerySchemeAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpQuerySchemeAction)context.get("queryAction");
  nc.ui.cmp.bill.actions.CmpQuerySchemeAction bean = new nc.ui.cmp.bill.actions.CmpQuerySchemeAction();
  context.put("queryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setShowUpComponent(getListView());
  bean.setTemplateContainer(getQueryTemplateContainer());
  bean.setQryCondDLGInitializer(getCMPQryCondDLGInitializer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.util.CMPQryCondDLGInitializer getCMPQryCondDLGInitializer(){
 if(context.get("CMPQryCondDLGInitializer")!=null)
 return (nc.ui.cmp.util.CMPQryCondDLGInitializer)context.get("CMPQryCondDLGInitializer");
  nc.ui.cmp.util.CMPQryCondDLGInitializer bean = new nc.ui.cmp.util.CMPQryCondDLGInitializer();
  context.put("CMPQryCondDLGInitializer",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.QueryTemplateContainer getQueryTemplateContainer(){
 if(context.get("queryTemplateContainer")!=null)
 return (nc.ui.uif2.editor.QueryTemplateContainer)context.get("queryTemplateContainer");
  nc.ui.uif2.editor.QueryTemplateContainer bean = new nc.ui.uif2.editor.QueryTemplateContainer();
  context.put("queryTemplateContainer",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRefreshSchemeAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRefreshSchemeAction)context.get("refreshAction");
  nc.ui.cmp.bill.actions.CmpRefreshSchemeAction bean = new nc.ui.cmp.bill.actions.CmpRefreshSchemeAction();
  context.put("refreshAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpNoteRegisterAction getCmpNoteRegisterAction(){
 if(context.get("cmpNoteRegisterAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpNoteRegisterAction)context.get("cmpNoteRegisterAction");
  nc.ui.cmp.bill.actions.CmpNoteRegisterAction bean = new nc.ui.cmp.bill.actions.CmpNoteRegisterAction();
  context.put("cmpNoteRegisterAction",bean);
  bean.setContext(getContext());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getRelatingActionGroup(){
 if(context.get("relatingActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("relatingActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("relatingActionGroup",bean);
  bean.setCode("billRelating");
  bean.setName(getI18nFB_1c5982a());
  bean.setActions(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1c5982a(){
 if(context.get("nc.ui.uif2.I18nFB#1c5982a")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1c5982a");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1c5982a",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("��������");
  bean.setResId("03607mng-0098");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1c5982a",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList0(){  List list = new ArrayList();  list.add(getCmpNoteRegisterAction());  list.add(getRentAffiliated());  return list;}

public nc.ui.cmp.bill.actions.PreviewVoucherAction getPrevVoucherAction(){
 if(context.get("prevVoucherAction")!=null)
 return (nc.ui.cmp.bill.actions.PreviewVoucherAction)context.get("prevVoucherAction");
  nc.ui.cmp.bill.actions.PreviewVoucherAction bean = new nc.ui.cmp.bill.actions.PreviewVoucherAction();
  context.put("prevVoucherAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getExcelImportAndExportActiongroup(){
 if(context.get("excelImportAndExportActiongroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("excelImportAndExportActiongroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("excelImportAndExportActiongroup",bean);
  bean.setCode("importActionActiongroup");
  bean.setName(getI18nFB_148b7d4());
  bean.setActions(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_148b7d4(){
 if(context.get("nc.ui.uif2.I18nFB#148b7d4")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#148b7d4");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#148b7d4",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("���뵼��");
  bean.setResId("03607mng-0099");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#148b7d4",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList1(){  List list = new ArrayList();  list.add(getImportAction());  list.add(getQuickImportAction());  list.add(getSeparatorAction());  list.add(getExportTemplateAction());  return list;}

public nc.ui.uif2.excelimport.ImportAction getImportAction(){
 if(context.get("importAction")!=null)
 return (nc.ui.uif2.excelimport.ImportAction)context.get("importAction");
  nc.ui.uif2.excelimport.ImportAction bean = new nc.ui.uif2.excelimport.ImportAction();
  context.put("importAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getCmpBillImportableEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.excelimport.actions.QuickImportAction getQuickImportAction(){
 if(context.get("quickImportAction")!=null)
 return (nc.ui.cmp.bill.excelimport.actions.QuickImportAction)context.get("quickImportAction");
  nc.ui.cmp.bill.excelimport.actions.QuickImportAction bean = new nc.ui.cmp.bill.excelimport.actions.QuickImportAction();
  context.put("quickImportAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getCmpBillImportableEditor());
  bean.setExcelImportProcessor(getExcelImportProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.excelimport.ExportBillTemplateAction getExportTemplateAction(){
 if(context.get("exportTemplateAction")!=null)
 return (nc.ui.cmp.bill.excelimport.ExportBillTemplateAction)context.get("exportTemplateAction");
  nc.ui.cmp.bill.excelimport.ExportBillTemplateAction bean = new nc.ui.cmp.bill.excelimport.ExportBillTemplateAction();
  context.put("exportTemplateAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getCmpBillImportableEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.excel.CmpBillImportableEditor getCmpBillImportableEditor(){
 if(context.get("cmpBillImportableEditor")!=null)
 return (nc.ui.cmp.bill.excel.CmpBillImportableEditor)context.get("cmpBillImportableEditor");
  nc.ui.cmp.bill.excel.CmpBillImportableEditor bean = new nc.ui.cmp.bill.excel.CmpBillImportableEditor();
  context.put("cmpBillImportableEditor",bean);
  bean.setAddAction(getAddAction());
  bean.setCancelAction(getCancelAction());
  bean.setBillcardPanelEditor(getBillFormEditor());
  bean.setSaveAction(getSaveAction());
  bean.setAppModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.service.DeleteBillService getDeleteBillService(){
 if(context.get("deleteBillService")!=null)
 return (nc.ui.cmp.bill.service.DeleteBillService)context.get("deleteBillService");
  nc.ui.cmp.bill.service.DeleteBillService bean = new nc.ui.cmp.bill.service.DeleteBillService();
  context.put("deleteBillService",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.BillLinkQueryNoteAction getBillLQueryNoteAction(){
 if(context.get("billLQueryNoteAction")!=null)
 return (nc.ui.cmp.bill.actions.BillLinkQueryNoteAction)context.get("billLQueryNoteAction");
  nc.ui.cmp.bill.actions.BillLinkQueryNoteAction bean = new nc.ui.cmp.bill.actions.BillLinkQueryNoteAction();
  context.put("billLQueryNoteAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction getBillLQueryBudgetAction(){
 if(context.get("billLQueryBudgetAction")!=null)
 return (nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction)context.get("billLQueryBudgetAction");
  nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction bean = new nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction();
  context.put("billLQueryBudgetAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.LinkBConferQueryAction getLinkBConferQueryAction(){
 if(context.get("linkBConferQueryAction")!=null)
 return (nc.ui.cmp.bill.actions.LinkBConferQueryAction)context.get("linkBConferQueryAction");
  nc.ui.cmp.bill.actions.LinkBConferQueryAction bean = new nc.ui.cmp.bill.actions.LinkBConferQueryAction();
  context.put("linkBConferQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getCommonuseBillActionGroup(){
 if(context.get("commonuseBillActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("commonuseBillActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("commonuseBillActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_11ccc43());
  bean.setActions(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_11ccc43(){
 if(context.get("nc.ui.uif2.I18nFB#11ccc43")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#11ccc43");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#11ccc43",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("���õ���");
  bean.setResId("03607mng-0100");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#11ccc43",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList2(){  List list = new ArrayList();  list.add(getCommonuseBillUseAction());  list.add(getCommonUseBillSaveAction());  return list;}

public nc.ui.cmp.bill.actions.CommonUseBillSaveAction getCommonUseBillSaveAction(){
 if(context.get("commonUseBillSaveAction")!=null)
 return (nc.ui.cmp.bill.actions.CommonUseBillSaveAction)context.get("commonUseBillSaveAction");
  nc.ui.cmp.bill.actions.CommonUseBillSaveAction bean = new nc.ui.cmp.bill.actions.CommonUseBillSaveAction();
  context.put("commonUseBillSaveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CommonuseBillUseAction getCommonuseBillUseAction(){
 if(context.get("commonuseBillUseAction")!=null)
 return (nc.ui.cmp.bill.actions.CommonuseBillUseAction)context.get("commonuseBillUseAction");
  nc.ui.cmp.bill.actions.CommonuseBillUseAction bean = new nc.ui.cmp.bill.actions.CommonuseBillUseAction();
  context.put("commonuseBillUseAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getCardInfoPnl(){
 if(context.get("cardInfoPnl")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("cardInfoPnl");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("cardInfoPnl",bean);
  bean.setActions(getManagedList3());
  bean.setTitleAction(getReturnaction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

private nc.ui.uif2.actions.ShowMeUpAction getReturnaction(){
 if(context.get("returnaction")!=null)
 return (nc.ui.uif2.actions.ShowMeUpAction)context.get("returnaction");
  nc.ui.uif2.actions.ShowMeUpAction bean = new nc.ui.uif2.actions.ShowMeUpAction();
  context.put("returnaction",bean);
  bean.setGoComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.FirstLineAction getFirstLineAction(){
 if(context.get("firstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("firstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("firstLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.PreLineAction getPreLineAction(){
 if(context.get("preLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("preLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("preLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.NextLineAction getNextLineAction(){
 if(context.get("nextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("nextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("nextLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.LastLineAction getLastLineAction(){
 if(context.get("lastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("lastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("lastLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getListInfoPnl(){
 if(context.get("listInfoPnl")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("listInfoPnl");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("listInfoPnl",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.model.CmpRecBillDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.cmp.bill.model.CmpRecBillDataManager)context.get("modelDataManager");
  nc.ui.cmp.bill.model.CmpRecBillDataManager bean = new nc.ui.cmp.bill.model.CmpRecBillDataManager();
  context.put("modelDataManager",bean);
  bean.setModel(getManageAppModel());
  bean.setPaginationDelegate(getPaginationDelegator());
  bean.setListInfoPnl(getListInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.linkquery.bd.BDLinkQueryMediator getBdLinkQueryMediator(){
 if(context.get("bdLinkQueryMediator")!=null)
 return (nc.ui.pubapp.uif2app.linkquery.bd.BDLinkQueryMediator)context.get("bdLinkQueryMediator");
  nc.ui.pubapp.uif2app.linkquery.bd.BDLinkQueryMediator bean = new nc.ui.pubapp.uif2app.linkquery.bd.BDLinkQueryMediator();
  context.put("bdLinkQueryMediator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator getMouseClickShowPanelMediator(){
 if(context.get("mouseClickShowPanelMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator)context.get("mouseClickShowPanelMediator");
  nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator bean = new nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator();
  context.put("mouseClickShowPanelMediator",bean);
  bean.setListView(getListView());
  bean.setShowUpComponent(getBillFormEditor());
  bean.setHyperLinkColumn("bill_no");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.digit.print.DefaultPrintProcessor getPrintProcessor(){
 if(context.get("printProcessor")!=null)
 return (nc.ui.tmpub.digit.print.DefaultPrintProcessor)context.get("printProcessor");
  nc.ui.tmpub.digit.print.DefaultPrintProcessor bean = new nc.ui.tmpub.digit.print.DefaultPrintProcessor();
  context.put("printProcessor",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell getQueryArea(){
 if(context.get("queryArea")!=null)
 return (nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell)context.get("queryArea");
  nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell bean = new nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell();
  context.put("queryArea",bean);
  bean.setQueryAreaCreator(getQueryAction());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.digit.vo.SrcDestItemCollection getSrcDestCollection(){
 if(context.get("srcDestCollection")!=null)
 return (nc.ui.tmpub.digit.vo.SrcDestItemCollection)context.get("srcDestCollection");
  nc.ui.tmpub.digit.vo.SrcDestItemCollection bean = new nc.ui.tmpub.digit.vo.SrcDestItemCollection();
  context.put("srcDestCollection",bean);
  bean.setSrcDestOrigMap(getManagedMap0());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getManagedList4(),getManagedList9());  map.put(getManagedList17(),getManagedList29());  map.put(getManagedList38(),getManagedList50());  return map;}

private List getManagedList4(){  List list = new ArrayList();  list.add(getManagedList5());  list.add(getManagedList6());  list.add(getManagedList7());  list.add(getManagedList8());  return list;}

private List getManagedList5(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("HEAD");  return list;}

private List getManagedList6(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList8(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList9(){  List list = new ArrayList();  list.add(getManagedList10());  list.add(getManagedList11());  list.add(getManagedList12());  list.add(getManagedList13());  list.add(getManagedList14());  list.add(getManagedList15());  list.add(getManagedList16());  return list;}

private List getManagedList10(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("primal_money");  list.add("HEAD");  return list;}

private List getManagedList11(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("local_money");  list.add("HEAD");  return list;}

private List getManagedList12(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local");  list.add("HEAD");  return list;}

private List getManagedList13(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local");  list.add("HEAD");  return list;}

private List getManagedList14(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("HEAD");  return list;}

private List getManagedList15(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("HEAD");  return list;}

private List getManagedList16(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("HEAD");  return list;}

private List getManagedList17(){  List list = new ArrayList();  list.add(getManagedList18());  list.add(getManagedList19());  list.add(getManagedList20());  list.add(getManagedList21());  list.add(getManagedList22());  list.add(getManagedList23());  list.add(getManagedList24());  list.add(getManagedList25());  list.add(getManagedList26());  list.add(getManagedList27());  list.add(getManagedList28());  return list;}

private List getManagedList18(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList19(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList20(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList21(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList22(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("bill_date");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList23(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList24(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList25(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList26(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList27(){  List list = new ArrayList();  list.add("MONEY");  list.add("rec_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList28(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList29(){  List list = new ArrayList();  list.add(getManagedList30());  list.add(getManagedList31());  list.add(getManagedList32());  list.add(getManagedList33());  list.add(getManagedList34());  list.add(getManagedList35());  list.add(getManagedList36());  list.add(getManagedList37());  return list;}

private List getManagedList30(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("rec_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList31(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList32(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList33(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList34(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("rec_local");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList35(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local_rec");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList36(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local_rec");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList37(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList38(){  List list = new ArrayList();  list.add(getManagedList39());  list.add(getManagedList40());  list.add(getManagedList41());  list.add(getManagedList42());  list.add(getManagedList43());  list.add(getManagedList44());  list.add(getManagedList45());  list.add(getManagedList46());  list.add(getManagedList47());  list.add(getManagedList48());  list.add(getManagedList49());  return list;}

private List getManagedList39(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList40(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList41(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList42(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList43(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("bill_date");  list.add("HEAD");  return list;}

private List getManagedList44(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList45(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList46(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList47(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList48(){  List list = new ArrayList();  list.add("MONEY");  list.add("ts_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList49(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList50(){  List list = new ArrayList();  list.add(getManagedList51());  list.add(getManagedList52());  list.add(getManagedList53());  list.add(getManagedList54());  list.add(getManagedList55());  list.add(getManagedList56());  list.add(getManagedList57());  list.add(getManagedList58());  return list;}

private List getManagedList51(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("ts_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList52(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList53(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList54(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList55(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("ts_local");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList56(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local_ts");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList57(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local_ts");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList58(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getDigitMediator(){
 if(context.get("digitMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("digitMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("digitMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setHandlerMap(getManagedMap1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList59());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList60());  map.put("nc.ui.pubapp.uif2app.event.PubAppEvent",getManagedList61());  map.put("nc.ui.pubapp.uif2app.event.list.ListBillItemHyperlinkEvent",getManagedList62());  return map;}

private List getManagedList59(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_1db6876());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_1db6876(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#1db6876")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#1db6876");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#1db6876",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList60(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_1a7348c());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_1a7348c(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1a7348c")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1a7348c");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1a7348c",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList61(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_6af1ec());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_6af1ec(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#6af1ec")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#6af1ec");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#6af1ec",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList62(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_130ed61());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_130ed61(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#130ed61")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#130ed61");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#130ed61",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.digit.listener.card.CardPanelEditExListener getDigitListener(){
 if(context.get("digitListener")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelEditExListener)context.get("digitListener");
  nc.ui.tmpub.digit.listener.card.CardPanelEditExListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelEditExListener();
  context.put("digitListener",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getEditDigitHandleMediator(){
 if(context.get("EditDigitHandleMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("EditDigitHandleMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getBillFormEditor());  context.put("EditDigitHandleMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_3d52e3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_3d52e3(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#3d52e3")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#3d52e3");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#3d52e3",bean);
  bean.setMany2one(getManagedMap2());
  bean.setOne2many(getManagedMap3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList63(),getDigitListener());  return map;}

private List getManagedList63(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_currtype");  list.add("pk_group");  list.add("bill_date");  list.add("local_rate");  list.add("group_rate");  list.add("global_rate");  list.add("rec_primal");  list.add("price");  return list;}

private Map getManagedMap3(){  Map map = new HashMap();  map.put("cinventoryid",getManagedList64());  return map;}

private List getManagedList64(){  List list = new ArrayList();  list.add(getCountEditHandler_1309bb0());  return list;}

private nc.ui.cmp.viewhandler.list.CountEditHandler getCountEditHandler_1309bb0(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#1309bb0")!=null)
 return (nc.ui.cmp.viewhandler.list.CountEditHandler)context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#1309bb0");
  nc.ui.cmp.viewhandler.list.CountEditHandler bean = new nc.ui.cmp.viewhandler.list.CountEditHandler();
  context.put("nc.ui.cmp.viewhandler.list.CountEditHandler#1309bb0",bean);
  bean.setTargetkey("rec_count");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.uif2.LoginContext getContext(){
 if(context.get("context")!=null)
 return (nc.vo.uif2.LoginContext)context.get("context");
  nc.vo.uif2.LoginContext bean = new nc.vo.uif2.LoginContext();
  context.put("context",bean);
  bean.setNodeType(nc.vo.bd.pub.NODE_TYPE.ORG_NODE);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.model.RecBillVOObjectAdapterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.ui.cmp.bill.model.RecBillVOObjectAdapterFactory)context.get("boadatorfactory");
  nc.ui.cmp.bill.model.RecBillVOObjectAdapterFactory bean = new nc.ui.cmp.bill.model.RecBillVOObjectAdapterFactory();
  context.put("boadatorfactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.BillManageModel getManageAppModel(){
 if(context.get("ManageAppModel")!=null)
 return (nc.ui.pubapp.uif2app.model.BillManageModel)context.get("ManageAppModel");
  nc.ui.pubapp.uif2app.model.BillManageModel bean = new nc.ui.pubapp.uif2app.model.BillManageModel();
  context.put("ManageAppModel",bean);
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setContext(getContext());
  bean.setBillType("F4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.impl.D4NodekeyImpl getD4NodekeyImpl(){
 if(context.get("D4NodekeyImpl")!=null)
 return (nc.ui.cmp.bill.impl.D4NodekeyImpl)context.get("D4NodekeyImpl");
  nc.ui.cmp.bill.impl.D4NodekeyImpl bean = new nc.ui.cmp.bill.impl.D4NodekeyImpl();
  context.put("D4NodekeyImpl",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.impl.CmpBillNodeKeyChangeListener getCmpBillNodeKeyChangeListener(){
 if(context.get("CmpBillNodeKeyChangeListener")!=null)
 return (nc.ui.cmp.bill.impl.CmpBillNodeKeyChangeListener)context.get("CmpBillNodeKeyChangeListener");
  nc.ui.cmp.bill.impl.CmpBillNodeKeyChangeListener bean = new nc.ui.cmp.bill.impl.CmpBillNodeKeyChangeListener();
  context.put("CmpBillNodeKeyChangeListener",bean);
  bean.setCard(getBillFormEditor());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.BillOrgPanel getOrgPanel(){
 if(context.get("orgPanel")!=null)
 return (nc.ui.pubapp.uif2app.view.BillOrgPanel)context.get("orgPanel");
  nc.ui.pubapp.uif2app.view.BillOrgPanel bean = new nc.ui.pubapp.uif2app.view.BillOrgPanel();
  context.put("orgPanel",bean);
  bean.setModel(getManageAppModel());
  bean.setOnlyLeafCanSelected(false);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.views.CmpBillList getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.cmp.bill.views.CmpBillList)context.get("listView");
  nc.ui.cmp.bill.views.CmpBillList bean = new nc.ui.cmp.bill.views.CmpBillList();
  context.put("listView",bean);
  bean.setModel(getManageAppModel());
  bean.setNodekey("D4");
  bean.setPaginationDelegate(getPaginationDelegator());
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setShowTotalLine(true);
  bean.setShowTotalLineTabcodes(getManagedList65());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList65(){  List list = new ArrayList();  list.add("items");  return list;}

public nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator getPaginationDelegator(){
 if(context.get("paginationDelegator")!=null)
 return (nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator)context.get("paginationDelegator");
  nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator bean = new nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator(getManageAppModel());  context.put("paginationDelegator",bean);
  bean.setPaginationQuery(getPaginationQueryService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.model.CmpRecBillQuery getPaginationQueryService(){
 if(context.get("paginationQueryService")!=null)
 return (nc.ui.cmp.bill.model.CmpRecBillQuery)context.get("paginationQueryService");
  nc.ui.cmp.bill.model.CmpRecBillQuery bean = new nc.ui.cmp.bill.model.CmpRecBillQuery();
  context.put("paginationQueryService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.views.CmpRecBillDefValue getBillDefVauleItf(){
 if(context.get("billDefVauleItf")!=null)
 return (nc.ui.cmp.bill.views.CmpRecBillDefValue)context.get("billDefVauleItf");
  nc.ui.cmp.bill.views.CmpRecBillDefValue bean = new nc.ui.cmp.bill.views.CmpRecBillDefValue();
  context.put("billDefVauleItf",bean);
  bean.setEditor(getBillFormEditor());
  bean.setCmpBillNodeKeyChangeListener(getCmpBillNodeKeyChangeListener());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.viewhandler.HBRelationAfterEditHandler getRelationEditHandler(){
 if(context.get("RelationEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.HBRelationAfterEditHandler)context.get("RelationEditHandler");
  nc.ui.cmp.viewhandler.HBRelationAfterEditHandler bean = new nc.ui.cmp.viewhandler.HBRelationAfterEditHandler();
  context.put("RelationEditHandler",bean);
  bean.setHtob(getCmpH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.viewhandler.CmpH2BMapping getCmpH2B(){
 if(context.get("CmpH2B")!=null)
 return (nc.ui.cmp.bill.viewhandler.CmpH2BMapping)context.get("CmpH2B");
  nc.ui.cmp.bill.viewhandler.CmpH2BMapping bean = new nc.ui.cmp.bill.viewhandler.CmpH2BMapping();
  context.put("CmpH2B",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.RowNoMediator getRowNoMediator(){
 if(context.get("rowNoMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.RowNoMediator)context.get("rowNoMediator");
  nc.ui.pubapp.uif2app.view.RowNoMediator bean = new nc.ui.pubapp.uif2app.view.RowNoMediator();
  context.put("rowNoMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.views.CmpRecBillCard getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.cmp.bill.views.CmpRecBillCard)context.get("billFormEditor");
  nc.ui.cmp.bill.views.CmpRecBillCard bean = new nc.ui.cmp.bill.views.CmpRecBillCard();
  context.put("billFormEditor",bean);
  bean.setOppui(getOppui());
  bean.setContainer(getContainer());
  bean.setModel(getManageAppModel());
  bean.setClosingListener(getClosingListener());
  bean.setNodekeyQry(getD4NodekeyImpl());
  bean.setNodekey("D4");
  bean.setBillDataManager(getModelDataManager());
  bean.setComponentValueManager(getComponentValueManager());
  bean.setAutoAddLine(false);
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
  bean.setDefValueItf(getBillDefVauleItf());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setBodyLineActions(getManagedList66());
  bean.setDefaultRefWherePartHandler(getDefaultRefWherePartHandler());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList66(){  List list = new ArrayList();  list.add(getCmpRecBillBodyAddLineAction());  list.add(getCmpRecBillBodyInsertLineAction_cc28a());  list.add(getBodyDelLineAction_1b80aab());  list.add(getBodyCopyLineAction_1c46bb0());  list.add(getBodyPasteLineAction_7a2402());  return list;}

private nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction getCmpRecBillBodyInsertLineAction_cc28a(){
 if(context.get("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#cc28a")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction)context.get("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#cc28a");
  nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction bean = new nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction();
  context.put("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#cc28a",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyDelLineAction getBodyDelLineAction_1b80aab(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#1b80aab")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyDelLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#1b80aab");
  nc.ui.pubapp.uif2app.actions.BodyDelLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyDelLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#1b80aab",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyCopyLineAction getBodyCopyLineAction_1c46bb0(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1c46bb0")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyCopyLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1c46bb0");
  nc.ui.pubapp.uif2app.actions.BodyCopyLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyCopyLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1c46bb0",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyPasteLineAction getBodyPasteLineAction_7a2402(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#7a2402")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyPasteLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#7a2402");
  nc.ui.pubapp.uif2app.actions.BodyPasteLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyPasteLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#7a2402",bean);
  bean.setClearItems(getManagedList67());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList67(){  List list = new ArrayList();  list.add("pk_recbill_detail");  list.add("bankrelated_code");  list.add("ts");  return list;}

public nc.ui.cmp.bill.actions.CmpRecBillBodyAddLineAction getCmpRecBillBodyAddLineAction(){
 if(context.get("CmpRecBillBodyAddLineAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillBodyAddLineAction)context.get("CmpRecBillBodyAddLineAction");
  nc.ui.cmp.bill.actions.CmpRecBillBodyAddLineAction bean = new nc.ui.cmp.bill.actions.CmpRecBillBodyAddLineAction();
  context.put("CmpRecBillBodyAddLineAction",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.filter.DefaultRefWherePartHandler getDefaultRefWherePartHandler(){
 if(context.get("defaultRefWherePartHandler")!=null)
 return (nc.ui.tmpub.filter.DefaultRefWherePartHandler)context.get("defaultRefWherePartHandler");
  nc.ui.tmpub.filter.DefaultRefWherePartHandler bean = new nc.ui.tmpub.filter.DefaultRefWherePartHandler();
  context.put("defaultRefWherePartHandler",bean);
  bean.setUiAccessor(getSettleUIAccessor_d22166());
  bean.setFilterList(getManagedList68());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.settlement.filter.SettleUIAccessor getSettleUIAccessor_d22166(){
 if(context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#d22166")!=null)
 return (nc.ui.cmp.settlement.filter.SettleUIAccessor)context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#d22166");
  nc.ui.cmp.settlement.filter.SettleUIAccessor bean = new nc.ui.cmp.settlement.filter.SettleUIAccessor();
  context.put("nc.ui.cmp.settlement.filter.SettleUIAccessor#d22166",bean);
  bean.setBillCardPanelEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList68(){  List list = new ArrayList();  list.add(getFbmbillnoheadfilter());  list.add(getFbmbillnobodyfilter());  return list;}

private nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter getFbmbillnoheadfilter(){
 if(context.get("fbmbillnoheadfilter")!=null)
 return (nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter)context.get("fbmbillnoheadfilter");
  nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter bean = new nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter();
  context.put("fbmbillnoheadfilter",bean);
  bean.setSrcKey("note_no");
  bean.setDestKey("note_no");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.filter.CmpGatherBodyBillnoRefModelFilter getFbmbillnobodyfilter(){
 if(context.get("fbmbillnobodyfilter")!=null)
 return (nc.ui.cmp.bill.filter.CmpGatherBodyBillnoRefModelFilter)context.get("fbmbillnobodyfilter");
  nc.ui.cmp.bill.filter.CmpGatherBodyBillnoRefModelFilter bean = new nc.ui.cmp.bill.filter.CmpGatherBodyBillnoRefModelFilter();
  context.put("fbmbillnobodyfilter",bean);
  bean.setSrcKey("note_no");
  bean.setSrcPos(1);
  bean.setSrcTabCode("cmp_recbill");
  bean.setDestKey("note_no");
  bean.setDestPos(1);
  bean.setDestTabCode("items");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.commom.OppUIContainer getOppui(){
 if(context.get("oppui")!=null)
 return (nc.ui.cmp.bill.commom.OppUIContainer)context.get("oppui");
  nc.ui.cmp.bill.commom.OppUIContainer bean = new nc.ui.cmp.bill.commom.OppUIContainer();
  context.put("oppui",bean);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.FunNodeClosingHandler getClosingListener(){
 if(context.get("ClosingListener")!=null)
 return (nc.ui.uif2.FunNodeClosingHandler)context.get("ClosingListener");
  nc.ui.uif2.FunNodeClosingHandler bean = new nc.ui.uif2.FunNodeClosingHandler();
  context.put("ClosingListener",bean);
  bean.setModel(getManageAppModel());
  bean.setSaveaction(getSaveAction());
  bean.setCancelaction(getCancelAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy getComponentValueManager(){
 if(context.get("componentValueManager")!=null)
 return (nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy)context.get("componentValueManager");
  nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy bean = new nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy();
  context.put("componentValueManager",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.cal.CmpRecBillCardCalculator getCmpBillCardCalculator(){
 if(context.get("CmpBillCardCalculator")!=null)
 return (nc.ui.cmp.bill.cal.CmpRecBillCardCalculator)context.get("CmpBillCardCalculator");
  nc.ui.cmp.bill.cal.CmpRecBillCardCalculator bean = new nc.ui.cmp.bill.cal.CmpRecBillCardCalculator();
  context.put("CmpBillCardCalculator",bean);
  bean.setCalItem(getRelationItemForCal());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.cal.CmpRecBillRelationItemForCal getRelationItemForCal(){
 if(context.get("relationItemForCal")!=null)
 return (nc.ui.cmp.bill.cal.CmpRecBillRelationItemForCal)context.get("relationItemForCal");
  nc.ui.cmp.bill.cal.CmpRecBillRelationItemForCal bean = new nc.ui.cmp.bill.cal.CmpRecBillRelationItemForCal();
  context.put("relationItemForCal",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.cmp.bill.CmpBillFieldGet getCmpBillFieldGet(){
 if(context.get("cmpBillFieldGet")!=null)
 return (nc.vo.cmp.bill.CmpBillFieldGet)context.get("cmpBillFieldGet");
  nc.vo.cmp.bill.CmpBillFieldGet bean = new nc.vo.cmp.bill.CmpBillFieldGet();
  context.put("cmpBillFieldGet",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getAppEventHandlerMediator(){
 if(context.get("appEventHandlerMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("appEventHandlerMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("appEventHandlerMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setHandlerMap(getManagedMap4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap4(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.mediator.mutiltrans.NodekeyEvent",getManagedList69());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent",getManagedList70());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent",getManagedList71());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent",getManagedList72());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent",getManagedList73());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList74());  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList75());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadRowChangedEvent",getManagedList77());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadDataChangedEvent",getManagedList78());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent",getManagedList79());  return map;}

private List getManagedList69(){  List list = new ArrayList();  list.add(getCmpBillNodeKeyChangeListener());  list.add(getAddActionGroup());  return list;}

private List getManagedList70(){  List list = new ArrayList();  list.add(getBodyBillItemBeforeEditHandler());  list.add(getBodyCashAccountBeforeEditHandler());  list.add(getBodyCheckNoBeforeEditHandler());  list.add(getBodyFreeCustBeforeEditHandler());  list.add(getBodyBankAccBeforeEditHandler());  list.add(getBodyObjectTypeBeforeEditHandler());  list.add(getBodySanhuBillItemBeforeEditHandler());  list.add(getBodyPksubjctBeforeEditHandler());  return list;}

private nc.ui.cmp.bill.viewhandler.cardbefore.BodyBillItemBeforeEditHandler getBodyBillItemBeforeEditHandler(){
 if(context.get("BodyBillItemBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodyBillItemBeforeEditHandler)context.get("BodyBillItemBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodyBillItemBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodyBillItemBeforeEditHandler();
  context.put("BodyBillItemBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.BodyCashAccountBeforeEditHandler getBodyCashAccountBeforeEditHandler(){
 if(context.get("BodyCashAccountBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodyCashAccountBeforeEditHandler)context.get("BodyCashAccountBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodyCashAccountBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodyCashAccountBeforeEditHandler();
  context.put("BodyCashAccountBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardbefore.BodyCheckNoBeforeEditHandler getBodyCheckNoBeforeEditHandler(){
 if(context.get("BodyCheckNoBeforeEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardbefore.BodyCheckNoBeforeEditHandler)context.get("BodyCheckNoBeforeEditHandler");
  nc.ui.cmp.viewhandler.cardbefore.BodyCheckNoBeforeEditHandler bean = new nc.ui.cmp.viewhandler.cardbefore.BodyCheckNoBeforeEditHandler();
  context.put("BodyCheckNoBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setCardeditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler getBodyFreeCustBeforeEditHandler(){
 if(context.get("BodyFreeCustBeforeEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler)context.get("BodyFreeCustBeforeEditHandler");
  nc.ui.cmp.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler bean = new nc.ui.cmp.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler();
  context.put("BodyFreeCustBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardbefore.BodyBankAccBeforeEditHandler getBodyBankAccBeforeEditHandler(){
 if(context.get("BodyBankAccBeforeEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardbefore.BodyBankAccBeforeEditHandler)context.get("BodyBankAccBeforeEditHandler");
  nc.ui.cmp.viewhandler.cardbefore.BodyBankAccBeforeEditHandler bean = new nc.ui.cmp.viewhandler.cardbefore.BodyBankAccBeforeEditHandler();
  context.put("BodyBankAccBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.BodyObjectTypeBeforeEditHandler getBodyObjectTypeBeforeEditHandler(){
 if(context.get("BodyObjectTypeBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodyObjectTypeBeforeEditHandler)context.get("BodyObjectTypeBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodyObjectTypeBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodyObjectTypeBeforeEditHandler();
  context.put("BodyObjectTypeBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.BodySanhuBillItemBeforeEditHandler getBodySanhuBillItemBeforeEditHandler(){
 if(context.get("BodySanhuBillItemBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodySanhuBillItemBeforeEditHandler)context.get("BodySanhuBillItemBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodySanhuBillItemBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodySanhuBillItemBeforeEditHandler();
  context.put("BodySanhuBillItemBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.BodyPksubjctBeforeEditHandler getBodyPksubjctBeforeEditHandler(){
 if(context.get("BodyPksubjctBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodyPksubjctBeforeEditHandler)context.get("BodyPksubjctBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodyPksubjctBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodyPksubjctBeforeEditHandler();
  context.put("BodyPksubjctBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList71(){  List list = new ArrayList();  list.add(getBodyCardCustomerOrSupplierAfterEditHandler());  list.add(getBodyCardObjectTypeAfterEditHandler());  list.add(getBodyPsnDocAfterEditHandler());  list.add(getBodyCheckNoAfterEditHandler());  list.add(getBodyTSPrimalAfterEditHandler());  list.add(getBodyCardBalatypeAfterEditHandler());  list.add(getBodyCardCountOrPriceAfterEditHandler());  list.add(getBodyCardPrimalAfterEditHandler());  list.add(getBodyBankAccAfterEditHandler());  return list;}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCustomerOrSupplierAfterEditHandler getBodyCardCustomerOrSupplierAfterEditHandler(){
 if(context.get("BodyCardCustomerOrSupplierAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCustomerOrSupplierAfterEditHandler)context.get("BodyCardCustomerOrSupplierAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCustomerOrSupplierAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCustomerOrSupplierAfterEditHandler();
  context.put("BodyCardCustomerOrSupplierAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardObjectTypeAfterEditHandler getBodyCardObjectTypeAfterEditHandler(){
 if(context.get("BodyCardObjectTypeAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardObjectTypeAfterEditHandler)context.get("BodyCardObjectTypeAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardObjectTypeAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardObjectTypeAfterEditHandler();
  context.put("BodyCardObjectTypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.BodyPsnDocAfterEditHandler getBodyPsnDocAfterEditHandler(){
 if(context.get("BodyPsnDocAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.BodyPsnDocAfterEditHandler)context.get("BodyPsnDocAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.BodyPsnDocAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.BodyPsnDocAfterEditHandler();
  context.put("BodyPsnDocAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.BodyCheckNoAfterEditHandler getBodyCheckNoAfterEditHandler(){
 if(context.get("BodyCheckNoAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.BodyCheckNoAfterEditHandler)context.get("BodyCheckNoAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.BodyCheckNoAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.BodyCheckNoAfterEditHandler();
  context.put("BodyCheckNoAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyTSPrimalAfterEditHandler getBodyTSPrimalAfterEditHandler(){
 if(context.get("BodyTSPrimalAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyTSPrimalAfterEditHandler)context.get("BodyTSPrimalAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyTSPrimalAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyTSPrimalAfterEditHandler();
  context.put("BodyTSPrimalAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardBalatypeAfterEditHandler getBodyCardBalatypeAfterEditHandler(){
 if(context.get("BodyCardBalatypeAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardBalatypeAfterEditHandler)context.get("BodyCardBalatypeAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardBalatypeAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardBalatypeAfterEditHandler();
  context.put("BodyCardBalatypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCountOrPriceAfterEditHandler getBodyCardCountOrPriceAfterEditHandler(){
 if(context.get("BodyCardCountOrPriceAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCountOrPriceAfterEditHandler)context.get("BodyCardCountOrPriceAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCountOrPriceAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCountOrPriceAfterEditHandler();
  context.put("BodyCardCountOrPriceAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardPrimalAfterEditHandler getBodyCardPrimalAfterEditHandler(){
 if(context.get("BodyCardPrimalAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardPrimalAfterEditHandler)context.get("BodyCardPrimalAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardPrimalAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardPrimalAfterEditHandler();
  context.put("BodyCardPrimalAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.BodyBankAccAfterEditHandler getBodyBankAccAfterEditHandler(){
 if(context.get("BodyBankAccAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.BodyBankAccAfterEditHandler)context.get("BodyBankAccAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.BodyBankAccAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.BodyBankAccAfterEditHandler();
  context.put("BodyBankAccAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList72(){  List list = new ArrayList();  list.add(getHeadCashAccountBeforeEditHandler());  list.add(getHeadPksubjctBeforeEditHandler());  list.add(getHeadBankAccBeforeEditHandler());  list.add(getHeadResissueBeforeEditHandler());  list.add(getHeadCheckNoBeforeEditHandler());  list.add(getHeadObjectTypeBeforeEditHandler());  list.add(getHeadConsignagreementBeforeEditHandler());  return list;}

private nc.ui.cmp.bill.viewhandler.cardbefore.HeadCashAccountBeforeEditHandler getHeadCashAccountBeforeEditHandler(){
 if(context.get("HeadCashAccountBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.HeadCashAccountBeforeEditHandler)context.get("HeadCashAccountBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.HeadCashAccountBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.HeadCashAccountBeforeEditHandler();
  context.put("HeadCashAccountBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.HeadPksubjctBeforeEditHandler getHeadPksubjctBeforeEditHandler(){
 if(context.get("HeadPksubjctBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.HeadPksubjctBeforeEditHandler)context.get("HeadPksubjctBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.HeadPksubjctBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.HeadPksubjctBeforeEditHandler();
  context.put("HeadPksubjctBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardbefore.HeadBankAccBeforeEditHandler getHeadBankAccBeforeEditHandler(){
 if(context.get("HeadBankAccBeforeEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardbefore.HeadBankAccBeforeEditHandler)context.get("HeadBankAccBeforeEditHandler");
  nc.ui.cmp.viewhandler.cardbefore.HeadBankAccBeforeEditHandler bean = new nc.ui.cmp.viewhandler.cardbefore.HeadBankAccBeforeEditHandler();
  context.put("HeadBankAccBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.HeadResissueBeforeEditHandler getHeadResissueBeforeEditHandler(){
 if(context.get("HeadResissueBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.HeadResissueBeforeEditHandler)context.get("HeadResissueBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.HeadResissueBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.HeadResissueBeforeEditHandler();
  context.put("HeadResissueBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardbefore.HeadCheckNoBeforeEditHandler getHeadCheckNoBeforeEditHandler(){
 if(context.get("HeadCheckNoBeforeEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardbefore.HeadCheckNoBeforeEditHandler)context.get("HeadCheckNoBeforeEditHandler");
  nc.ui.cmp.viewhandler.cardbefore.HeadCheckNoBeforeEditHandler bean = new nc.ui.cmp.viewhandler.cardbefore.HeadCheckNoBeforeEditHandler();
  context.put("HeadCheckNoBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setCardeditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.HeadObjectTypeBeforeEditHandler getHeadObjectTypeBeforeEditHandler(){
 if(context.get("HeadObjectTypeBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.HeadObjectTypeBeforeEditHandler)context.get("HeadObjectTypeBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.HeadObjectTypeBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.HeadObjectTypeBeforeEditHandler();
  context.put("HeadObjectTypeBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardbefore.HeadConsignagreementBeforeEditHandler getHeadConsignagreementBeforeEditHandler(){
 if(context.get("HeadConsignagreementBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.HeadConsignagreementBeforeEditHandler)context.get("HeadConsignagreementBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.HeadConsignagreementBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.HeadConsignagreementBeforeEditHandler();
  context.put("HeadConsignagreementBeforeEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList73(){  List list = new ArrayList();  list.add(getHBRelationAfterEditHandler());  list.add(getHeadCurrTypeAfterEditHandler());  list.add(getHeadObjtypeAfterEditHandler());  list.add(getHeadCusdocDocAfterEditHandler());  list.add(getHeadSupdocDocAfterEditHandler());  list.add(getHeadObjectTypeAfterEditHandler());  list.add(getHeadCustomerOrSupplierAfterEditHandler());  list.add(getHeadPsnDocAfterEditHandler());  list.add(getHeadBalatypeAfterEditHandler());  list.add(getHeadConsignagreementAfterEditHandler());  list.add(getHeadCheckNoAfterEditHandler());  return list;}

private nc.ui.cmp.viewhandler.HBRelationAfterEditHandler getHBRelationAfterEditHandler(){
 if(context.get("HBRelationAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.HBRelationAfterEditHandler)context.get("HBRelationAfterEditHandler");
  nc.ui.cmp.viewhandler.HBRelationAfterEditHandler bean = new nc.ui.cmp.viewhandler.HBRelationAfterEditHandler();
  context.put("HBRelationAfterEditHandler",bean);
  bean.setHtob(getCmpH2B());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.HeadCurrTypeAfterEditHandler getHeadCurrTypeAfterEditHandler(){
 if(context.get("HeadCurrTypeAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.HeadCurrTypeAfterEditHandler)context.get("HeadCurrTypeAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.HeadCurrTypeAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.HeadCurrTypeAfterEditHandler();
  context.put("HeadCurrTypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.HeadObjtypeAfterEditHandler getHeadObjtypeAfterEditHandler(){
 if(context.get("HeadObjtypeAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.HeadObjtypeAfterEditHandler)context.get("HeadObjtypeAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.HeadObjtypeAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.HeadObjtypeAfterEditHandler();
  context.put("HeadObjtypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.HeadCusdocDocAfterEditHandler getHeadCusdocDocAfterEditHandler(){
 if(context.get("HeadCusdocDocAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.HeadCusdocDocAfterEditHandler)context.get("HeadCusdocDocAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.HeadCusdocDocAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.HeadCusdocDocAfterEditHandler();
  context.put("HeadCusdocDocAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.HeadSupdocDocAfterEditHandler getHeadSupdocDocAfterEditHandler(){
 if(context.get("HeadSupdocDocAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.HeadSupdocDocAfterEditHandler)context.get("HeadSupdocDocAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.HeadSupdocDocAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.HeadSupdocDocAfterEditHandler();
  context.put("HeadSupdocDocAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.HeadObjectTypeAfterEditHandler getHeadObjectTypeAfterEditHandler(){
 if(context.get("HeadObjectTypeAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.HeadObjectTypeAfterEditHandler)context.get("HeadObjectTypeAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.HeadObjectTypeAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.HeadObjectTypeAfterEditHandler();
  context.put("HeadObjectTypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.HeadCustomerOrSupplierAfterEditHandler getHeadCustomerOrSupplierAfterEditHandler(){
 if(context.get("HeadCustomerOrSupplierAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.HeadCustomerOrSupplierAfterEditHandler)context.get("HeadCustomerOrSupplierAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.HeadCustomerOrSupplierAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.HeadCustomerOrSupplierAfterEditHandler();
  context.put("HeadCustomerOrSupplierAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.HeadPsnDocAfterEditHandler getHeadPsnDocAfterEditHandler(){
 if(context.get("HeadPsnDocAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.HeadPsnDocAfterEditHandler)context.get("HeadPsnDocAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.HeadPsnDocAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.HeadPsnDocAfterEditHandler();
  context.put("HeadPsnDocAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.HeadBalatypeAfterEditHandler getHeadBalatypeAfterEditHandler(){
 if(context.get("HeadBalatypeAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.HeadBalatypeAfterEditHandler)context.get("HeadBalatypeAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.HeadBalatypeAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.HeadBalatypeAfterEditHandler();
  context.put("HeadBalatypeAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.viewhandler.cardafter.HeadConsignagreementAfterEditHandler getHeadConsignagreementAfterEditHandler(){
 if(context.get("HeadConsignagreementAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.HeadConsignagreementAfterEditHandler)context.get("HeadConsignagreementAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.HeadConsignagreementAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.HeadConsignagreementAfterEditHandler();
  context.put("HeadConsignagreementAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler getHeadCheckNoAfterEditHandler(){
 if(context.get("HeadCheckNoAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler)context.get("HeadCheckNoAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler();
  context.put("HeadCheckNoAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setAddLineAction(getCmpRecBillBodyAddLineAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList74(){  List list = new ArrayList();  list.add(getLoadBillCardTemplate());  return list;}

private nc.ui.cmp.viewhandler.card.LoadBillCardTemplate getLoadBillCardTemplate(){
 if(context.get("LoadBillCardTemplate")!=null)
 return (nc.ui.cmp.viewhandler.card.LoadBillCardTemplate)context.get("LoadBillCardTemplate");
  nc.ui.cmp.viewhandler.card.LoadBillCardTemplate bean = new nc.ui.cmp.viewhandler.card.LoadBillCardTemplate();
  context.put("LoadBillCardTemplate",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setBillform(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList75(){  List list = new ArrayList();  list.add(getLoadBillListTemplate_162dd18());  return list;}

private nc.ui.cmp.viewhandler.list.LoadBillListTemplate getLoadBillListTemplate_162dd18(){
 if(context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#162dd18")!=null)
 return (nc.ui.cmp.viewhandler.list.LoadBillListTemplate)context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#162dd18");
  nc.ui.cmp.viewhandler.list.LoadBillListTemplate bean = new nc.ui.cmp.viewhandler.list.LoadBillListTemplate();
  context.put("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#162dd18",bean);
  bean.setListener(getManagedList76());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList76(){  List list = new ArrayList();  list.add(getCountDecimalListener_18ee133());  return list;}

private nc.ui.cmp.viewhandler.list.CountDecimalListener getCountDecimalListener_18ee133(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#18ee133")!=null)
 return (nc.ui.cmp.viewhandler.list.CountDecimalListener)context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#18ee133");
  nc.ui.cmp.viewhandler.list.CountDecimalListener bean = new nc.ui.cmp.viewhandler.list.CountDecimalListener();
  context.put("nc.ui.cmp.viewhandler.list.CountDecimalListener#18ee133",bean);
  bean.setTargetkey("rec_count");
  bean.setSource("ts");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList77(){  List list = new ArrayList();  list.add(getListHeadRowChangeListener_1a15fad());  list.add(getCardPanelRowChangedListener_1906c35());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener getListHeadRowChangeListener_1a15fad(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1a15fad")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1a15fad");
  nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener bean = new nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1a15fad",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener getCardPanelRowChangedListener_1906c35(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#1906c35")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#1906c35");
  nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#1906c35",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList78(){  List list = new ArrayList();  list.add(getListHeadModelListener_b01cca());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadModelListener getListHeadModelListener_b01cca(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#b01cca")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadModelListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#b01cca");
  nc.ui.cmp.viewhandler.list.ListHeadModelListener bean = new nc.ui.cmp.viewhandler.list.ListHeadModelListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadModelListener#b01cca",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList79(){  List list = new ArrayList();  list.add(getCardBodyAfterRowEditHandler_321a55());  return list;}

private nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler getCardBodyAfterRowEditHandler_321a55(){
 if(context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#321a55")!=null)
 return (nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler)context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#321a55");
  nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler bean = new nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler();
  context.put("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#321a55",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.F4AddFlowMenuAction getAddActionGroup(){
 if(context.get("addActionGroup")!=null)
 return (nc.ui.cmp.bill.actions.F4AddFlowMenuAction)context.get("addActionGroup");
  nc.ui.cmp.bill.actions.F4AddFlowMenuAction bean = new nc.ui.cmp.bill.actions.F4AddFlowMenuAction();
  context.put("addActionGroup",bean);
  bean.setBillform(getBillFormEditor());
  bean.setListview(getListView());
  bean.setCode("add");
  bean.setName(getI18nFB_15ecfe2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_15ecfe2(){
 if(context.get("nc.ui.uif2.I18nFB#15ecfe2")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#15ecfe2");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#15ecfe2",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("����");
  bean.setResId("03607mng-0398");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#15ecfe2",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getTSEditHandleMediator(){
 if(context.get("TSEditHandleMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("TSEditHandleMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getBillFormEditor());  context.put("TSEditHandleMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_1a22723());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_1a22723(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#1a22723")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#1a22723");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#1a22723",bean);
  bean.setMany2one(getManagedMap5());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap5(){  Map map = new HashMap();  map.put(getManagedList80(),getTSTMCurrtypeConvertorListener());  return map;}

private List getManagedList80(){  List list = new ArrayList();  list.add("pk_currtype");  list.add("ts_primal");  list.add("local_rate");  list.add("ts_local");  return list;}

private nc.ui.tm.listener.TMCurrtypeConvertorListener getTSTMCurrtypeConvertorListener(){
 if(context.get("TSTMCurrtypeConvertorListener")!=null)
 return (nc.ui.tm.listener.TMCurrtypeConvertorListener)context.get("TSTMCurrtypeConvertorListener");
  nc.ui.tm.listener.TMCurrtypeConvertorListener bean = new nc.ui.tm.listener.TMCurrtypeConvertorListener(getManagedList81(),getManagedList82());  context.put("TSTMCurrtypeConvertorListener",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList81(){  List list = new ArrayList();  list.add("pk_currtype");  list.add("ts_primal");  list.add("pk_org");  list.add("local_rate");  list.add("ts_local");  list.add("pk_group");  list.add("group_rate");  list.add("group_local_ts");  list.add("global_rate");  list.add("global_local_ts");  list.add("bill_date");  return list;}

private List getManagedList82(){  List list = new ArrayList();  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  list.add("BODY");  return list;}

public nc.ui.cmp.bill.commom.NewMutilFuncletMediator getMutilFuncletMediator(){
 if(context.get("mutilFuncletMediator")!=null)
 return (nc.ui.cmp.bill.commom.NewMutilFuncletMediator)context.get("mutilFuncletMediator");
  nc.ui.cmp.bill.commom.NewMutilFuncletMediator bean = new nc.ui.cmp.bill.commom.NewMutilFuncletMediator();
  context.put("mutilFuncletMediator",bean);
  bean.setCardPanel(getBillFormEditor());
  bean.setOppui(getOppui());
  bean.setModel(getManageAppModel());
  bean.setListView(getListView());
  bean.setReadMsg(getReadMsg());
  bean.setNodeType("manageNode");
  bean.setPageLineActions(getManagedList83());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList83(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

public nc.ui.cmp.bill.model.CmpBillAppModelService getManageModelService(){
 if(context.get("ManageModelService")!=null)
 return (nc.ui.cmp.bill.model.CmpBillAppModelService)context.get("ManageModelService");
  nc.ui.cmp.bill.model.CmpBillAppModelService bean = new nc.ui.cmp.bill.model.CmpBillAppModelService();
  context.put("ManageModelService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList84());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList84(){  List list = new ArrayList();  list.add(getListActions());  list.add(getCardActions());  list.add(getOppActions());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getListActions(){
 if(context.get("listActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("listActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("listActions",bean);
  bean.setActions(getManagedList85());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList85(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getAuditActionGroup());  list.add(getListRedHandleAction());  list.add(getMadeBillAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getRelatingActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getListPrintMenuAction());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getCardActions(){
 if(context.get("cardActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("cardActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getBillFormEditor());  context.put("cardActions",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList86());
  bean.setEditActions(getManagedList87());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList86(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getCardRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getAuditCardActionGroup());  list.add(getListRedHandleAction());  list.add(getMadeBillAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getPrintActiongroup());  return list;}

private List getManagedList87(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getCancelAction());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getOppActions(){
 if(context.get("oppActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("oppActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getOppui());  context.put("oppActions",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_1e2f499());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_1e2f499(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#1e2f499")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#1e2f499");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#1e2f499",bean);
  bean.setTabs(getManagedList88());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList88(){  List list = new ArrayList();  list.add(getHSNode_74a6b5());  list.add(getVSNode_1fa169e());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_74a6b5(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#74a6b5")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#74a6b5");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#74a6b5",bean);
  bean.setName(getI18nFB_b798ce());
  bean.setDividerLocation(0.2f);
  bean.setLeft(getCNode_1910f37());
  bean.setRight(getVSNode_1c5da24());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_b798ce(){
 if(context.get("nc.ui.uif2.I18nFB#b798ce")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#b798ce");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#b798ce",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("�б�");
  bean.setResId("03607mng-0101");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#b798ce",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1910f37(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1910f37")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1910f37");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1910f37",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_1c5da24(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#1c5da24")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#1c5da24");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#1c5da24",bean);
  bean.setUp(getCNode_7dece3());
  bean.setDown(getCNode_14b514a());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_7dece3(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#7dece3")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#7dece3");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#7dece3",bean);
  bean.setComponent(getListInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_14b514a(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#14b514a")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#14b514a");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#14b514a",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_1fa169e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#1fa169e")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#1fa169e");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#1fa169e",bean);
  bean.setName(getI18nFB_6cedba());
  bean.setUp(getCNode_16b651f());
  bean.setDown(getTBNode_7ec9e9());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_6cedba(){
 if(context.get("nc.ui.uif2.I18nFB#6cedba")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#6cedba");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#6cedba",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("��Ƭ");
  bean.setResId("03607mng-0399");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#6cedba",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_16b651f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#16b651f")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#16b651f");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#16b651f",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_7ec9e9(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#7ec9e9")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#7ec9e9");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#7ec9e9",bean);
  bean.setName(getI18nFB_1bbe863());
  bean.setTabs(getManagedList89());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1bbe863(){
 if(context.get("nc.ui.uif2.I18nFB#1bbe863")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1bbe863");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1bbe863",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("��Ƭ");
  bean.setResId("03607mng-0399");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1bbe863",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList89(){  List list = new ArrayList();  list.add(getCNode_1e5ea7d());  list.add(getCNode_1a0525a());  return list;}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1e5ea7d(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1e5ea7d")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1e5ea7d");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1e5ea7d",bean);
  bean.setComponent(getBillFormEditor());
  bean.setName(getI18nFB_195ecaf());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_195ecaf(){
 if(context.get("nc.ui.uif2.I18nFB#195ecaf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#195ecaf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#195ecaf",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("ҵ����Ϣ");
  bean.setResId("03607mng-0102");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#195ecaf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1a0525a(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1a0525a")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1a0525a");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1a0525a",bean);
  bean.setComponent(getOppui());
  bean.setName(getI18nFB_185ce22());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_185ce22(){
 if(context.get("nc.ui.uif2.I18nFB#185ce22")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#185ce22");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#185ce22",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("������Ϣ");
  bean.setResId("03607mng-0400");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#185ce22",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.bill.actions.MadeBillAction getMadeBillAction(){
 if(context.get("madeBillAction")!=null)
 return (nc.ui.cmp.bill.actions.MadeBillAction)context.get("madeBillAction");
  nc.ui.cmp.bill.actions.MadeBillAction bean = new nc.ui.cmp.bill.actions.MadeBillAction();
  context.put("madeBillAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRecBillSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillSaveAction)context.get("saveAction");
  nc.ui.cmp.bill.actions.CmpRecBillSaveAction bean = new nc.ui.cmp.bill.actions.CmpRecBillSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRecBillCancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillCancelAction)context.get("cancelAction");
  nc.ui.cmp.bill.actions.CmpRecBillCancelAction bean = new nc.ui.cmp.bill.actions.CmpRecBillCancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setSaveAction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRecBillAddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillAddAction)context.get("addAction");
  nc.ui.cmp.bill.actions.CmpRecBillAddAction bean = new nc.ui.cmp.bill.actions.CmpRecBillAddAction();
  context.put("addAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEdit(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.AddLineAction getAddline(){
 if(context.get("addline")!=null)
 return (nc.ui.uif2.actions.AddLineAction)context.get("addline");
  nc.ui.uif2.actions.AddLineAction bean = new nc.ui.uif2.actions.AddLineAction();
  context.put("addline",bean);
  bean.setModel(getManageAppModel());
  bean.setCardpanel(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillAuditAction getAuditAction(){
 if(context.get("auditAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillAuditAction)context.get("auditAction");
  nc.ui.cmp.bill.actions.CmpBillAuditAction bean = new nc.ui.cmp.bill.actions.CmpBillAuditAction();
  context.put("auditAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillReverseAuditAction getReverseAuditAction(){
 if(context.get("reverseAuditAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillReverseAuditAction)context.get("reverseAuditAction");
  nc.ui.cmp.bill.actions.CmpBillReverseAuditAction bean = new nc.ui.cmp.bill.actions.CmpBillReverseAuditAction();
  context.put("reverseAuditAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillListAuditAction getAuditListAction(){
 if(context.get("auditListAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillListAuditAction)context.get("auditListAction");
  nc.ui.cmp.bill.actions.CmpBillListAuditAction bean = new nc.ui.cmp.bill.actions.CmpBillListAuditAction();
  context.put("auditListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setListSelected(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillListReverseAuditAction getReverseAuditListAction(){
 if(context.get("reverseAuditListAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillListReverseAuditAction)context.get("reverseAuditListAction");
  nc.ui.cmp.bill.actions.CmpBillListReverseAuditAction bean = new nc.ui.cmp.bill.actions.CmpBillListReverseAuditAction();
  context.put("reverseAuditListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setListSelected(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRecBillCardRefreshAction getCardRefreshAction(){
 if(context.get("cardRefreshAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillCardRefreshAction)context.get("cardRefreshAction");
  nc.ui.cmp.bill.actions.CmpRecBillCardRefreshAction bean = new nc.ui.cmp.bill.actions.CmpRecBillCardRefreshAction();
  context.put("cardRefreshAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getAuditActionGroup(){
 if(context.get("auditActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("auditActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("auditActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_1289b03());
  bean.setActions(getManagedList90());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1289b03(){
 if(context.get("nc.ui.uif2.I18nFB#1289b03")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1289b03");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1289b03",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("����");
  bean.setResId("03607mng-0103");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1289b03",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList90(){  List list = new ArrayList();  list.add(getAuditListAction());  list.add(getReverseAuditListAction());  list.add(getQueryAuditFlowAction());  return list;}

public nc.funcnode.ui.action.GroupAction getAuditCardActionGroup(){
 if(context.get("auditCardActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("auditCardActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("auditCardActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_d92120());
  bean.setActions(getManagedList91());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d92120(){
 if(context.get("nc.ui.uif2.I18nFB#d92120")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d92120");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d92120",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("����");
  bean.setResId("03607mng-0103");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d92120",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList91(){  List list = new ArrayList();  list.add(getAuditAction());  list.add(getReverseAuditAction());  list.add(getQueryAuditFlowAction());  return list;}

public nc.ui.cmp.bill.actions.BillFlowStateQueryAction getQueryAuditFlowAction(){
 if(context.get("queryAuditFlowAction")!=null)
 return (nc.ui.cmp.bill.actions.BillFlowStateQueryAction)context.get("queryAuditFlowAction");
  nc.ui.cmp.bill.actions.BillFlowStateQueryAction bean = new nc.ui.cmp.bill.actions.BillFlowStateQueryAction();
  context.put("queryAuditFlowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContainer(getContainer());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpRecBillListDeleteAction getListDelAction(){
 if(context.get("listDelAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillListDeleteAction)context.get("listDelAction");
  nc.ui.cmp.bill.actions.CmpRecBillListDeleteAction bean = new nc.ui.cmp.bill.actions.CmpRecBillListDeleteAction();
  context.put("listDelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.RecTransTypeAction getTranstypeAction(){
 if(context.get("transtypeAction")!=null)
 return (nc.ui.cmp.bill.actions.RecTransTypeAction)context.get("transtypeAction");
  nc.ui.cmp.bill.actions.RecTransTypeAction bean = new nc.ui.cmp.bill.actions.RecTransTypeAction();
  context.put("transtypeAction",bean);
  bean.setModel(getManageAppModel());
  bean.setTransTypeRefModel(getTransTypeRefModel());
  bean.setWherepart("parentbilltype = 'F4'");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.ref.FiBillTypeRefModel getTransTypeRefModel(){
 if(context.get("transTypeRefModel")!=null)
 return (nc.ui.cmp.ref.FiBillTypeRefModel)context.get("transTypeRefModel");
  nc.ui.cmp.ref.FiBillTypeRefModel bean = new nc.ui.cmp.ref.FiBillTypeRefModel();
  context.put("transTypeRefModel",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillEditAction)context.get("editAction");
  nc.ui.cmp.bill.actions.CmpBillEditAction bean = new nc.ui.cmp.bill.actions.CmpBillEditAction();
  context.put("editAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.RecBillCopyFactory getRecFactory(){
 if(context.get("recFactory")!=null)
 return (nc.ui.cmp.bill.RecBillCopyFactory)context.get("recFactory");
  nc.ui.cmp.bill.RecBillCopyFactory bean = new nc.ui.cmp.bill.RecBillCopyFactory();
  context.put("recFactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillCopyAction)context.get("copyAction");
  nc.ui.cmp.bill.actions.CmpBillCopyAction bean = new nc.ui.cmp.bill.actions.CmpBillCopyAction();
  context.put("copyAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setFactory(getRecFactory());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillQueryDelege getQueryDelegator(){
 if(context.get("queryDelegator")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillQueryDelege)context.get("queryDelegator");
  nc.ui.cmp.bill.actions.CmpBillQueryDelege bean = new nc.ui.cmp.bill.actions.CmpBillQueryDelege();
  context.put("queryDelegator",bean);
  bean.setContext(getContext());
  bean.setModel(getManageAppModel());
  bean.setNodeKey("D4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getAssociateActionGroup(){
 if(context.get("associateActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("associateActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("associateActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_10dcfcc());
  bean.setActions(getManagedList92());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_10dcfcc(){
 if(context.get("nc.ui.uif2.I18nFB#10dcfcc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#10dcfcc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#10dcfcc",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("����������Ϣ");
  bean.setResId("03607mng-0106");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#10dcfcc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList92(){  List list = new ArrayList();  list.add(getAssociateAction());  list.add(getUnAssociateAction());  return list;}

public nc.ui.cmp.bill.actions.AssociateUIAction getAssociateAction(){
 if(context.get("associateAction")!=null)
 return (nc.ui.cmp.bill.actions.AssociateUIAction)context.get("associateAction");
  nc.ui.cmp.bill.actions.AssociateUIAction bean = new nc.ui.cmp.bill.actions.AssociateUIAction();
  context.put("associateAction",bean);
  bean.setContext(getContext());
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setBillform(getBillFormEditor());
  bean.setBillType("F4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.UnAssociateUIAction getUnAssociateAction(){
 if(context.get("unAssociateAction")!=null)
 return (nc.ui.cmp.bill.actions.UnAssociateUIAction)context.get("unAssociateAction");
  nc.ui.cmp.bill.actions.UnAssociateUIAction bean = new nc.ui.cmp.bill.actions.UnAssociateUIAction();
  context.put("unAssociateAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContext(getContext());
  bean.setBillType("F4");
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator getOrgChangedMediator(){
 if(context.get("orgChangedMediator")!=null)
 return (nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator)context.get("orgChangedMediator");
  nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator bean = new nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator();
  context.put("orgChangedMediator",bean);
  bean.setBillform(getBillFormEditor());
  bean.setModel(getManageAppModel());
  bean.setOrgChangedImpl(getOrgchange());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.mediator.modelevent.OrgChangedEvent getOrgchange(){
 if(context.get("orgchange")!=null)
 return (nc.ui.cmp.mediator.modelevent.OrgChangedEvent)context.get("orgchange");
  nc.ui.cmp.mediator.modelevent.OrgChangedEvent bean = new nc.ui.cmp.mediator.modelevent.OrgChangedEvent();
  context.put("orgchange",bean);
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.scale.CmpBillCardPanelScaleProcessor getScaleProcessor(){
 if(context.get("scaleProcessor")!=null)
 return (nc.ui.cmp.scale.CmpBillCardPanelScaleProcessor)context.get("scaleProcessor");
  nc.ui.cmp.scale.CmpBillCardPanelScaleProcessor bean = new nc.ui.cmp.scale.CmpBillCardPanelScaleProcessor();
  context.put("scaleProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.scale.CmpBillListPanelScaleProcessor getListScaleProcessor(){
 if(context.get("listScaleProcessor")!=null)
 return (nc.ui.cmp.scale.CmpBillListPanelScaleProcessor)context.get("listScaleProcessor");
  nc.ui.cmp.scale.CmpBillListPanelScaleProcessor bean = new nc.ui.cmp.scale.CmpBillListPanelScaleProcessor();
  context.put("listScaleProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.impl.CmpBillReadMsgImpl getReadMsg(){
 if(context.get("readMsg")!=null)
 return (nc.ui.cmp.bill.impl.CmpBillReadMsgImpl)context.get("readMsg");
  nc.ui.cmp.bill.impl.CmpBillReadMsgImpl bean = new nc.ui.cmp.bill.impl.CmpBillReadMsgImpl();
  context.put("readMsg",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.CmpRecRelationQueryDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.cmp.bill.CmpRecRelationQueryDataListener)context.get("InitDataListener");
  nc.ui.cmp.bill.CmpRecRelationQueryDataListener bean = new nc.ui.cmp.bill.CmpRecRelationQueryDataListener();
  context.put("InitDataListener",bean);
  bean.setBillFormEditor(getBillFormEditor());
  bean.setListview(getListView());
  bean.setQueryAction(getQueryAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getImageFuncActionGroup(){
 if(context.get("imageFuncActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("imageFuncActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("imageFuncActionGroup",bean);
  bean.setCode("imageFunc");
  bean.setName(getI18nFB_1892bdb());
  bean.setActions(getManagedList93());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1892bdb(){
 if(context.get("nc.ui.uif2.I18nFB#1892bdb")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1892bdb");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1892bdb",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("Ӱ��");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1892bdb",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList93(){  List list = new ArrayList();  list.add(getImageShowAction());  list.add(getImageScanAction());  return list;}

public nc.ui.cmp.bill.actions.CmpBillImageShowAction getImageShowAction(){
 if(context.get("imageShowAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillImageShowAction)context.get("imageShowAction");
  nc.ui.cmp.bill.actions.CmpBillImageShowAction bean = new nc.ui.cmp.bill.actions.CmpBillImageShowAction();
  context.put("imageShowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPk_billtype("0000Z6000000000000F4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillImageScanAction getImageScanAction(){
 if(context.get("imageScanAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillImageScanAction)context.get("imageScanAction");
  nc.ui.cmp.bill.actions.CmpBillImageScanAction bean = new nc.ui.cmp.bill.actions.CmpBillImageScanAction();
  context.put("imageScanAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPk_billtype("0000Z6000000000000F4");
  bean.setCheckscanway("nc.bs.cmp.imag.service.CmpBillCheckScanWay");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getBillAssistantActionGroup(){
 if(context.get("billAssistantActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billAssistantActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billAssistantActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_9e44fc());
  bean.setActions(getManagedList94());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_9e44fc(){
 if(context.get("nc.ui.uif2.I18nFB#9e44fc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#9e44fc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#9e44fc",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("��������");
  bean.setResId("03607mng-0401");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#9e44fc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList94(){  List list = new ArrayList();  list.add(getFileDocManageAction());  list.add(getRecBillReversalAction());  list.add(getCmpBillCancelReversalAction());  return list;}

public nc.ui.cmp.bill.international.actions.CmpRecBillReversalAction getRecBillReversalAction(){
 if(context.get("recBillReversalAction")!=null)
 return (nc.ui.cmp.bill.international.actions.CmpRecBillReversalAction)context.get("recBillReversalAction");
  nc.ui.cmp.bill.international.actions.CmpRecBillReversalAction bean = new nc.ui.cmp.bill.international.actions.CmpRecBillReversalAction();
  context.put("recBillReversalAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.international.actions.CmpBillCancelReversalAction getCmpBillCancelReversalAction(){
 if(context.get("CmpBillCancelReversalAction")!=null)
 return (nc.ui.cmp.bill.international.actions.CmpBillCancelReversalAction)context.get("CmpBillCancelReversalAction");
  nc.ui.cmp.bill.international.actions.CmpBillCancelReversalAction bean = new nc.ui.cmp.bill.international.actions.CmpBillCancelReversalAction();
  context.put("CmpBillCancelReversalAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.RentAffiliatedAction getRentAffiliated(){
 if(context.get("rentAffiliated")!=null)
 return (nc.ui.cmp.bill.actions.RentAffiliatedAction)context.get("rentAffiliated");
  nc.ui.cmp.bill.actions.RentAffiliatedAction bean = new nc.ui.cmp.bill.actions.RentAffiliatedAction();
  context.put("rentAffiliated",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillListRedHandleAction getListRedHandleAction(){
 if(context.get("listRedHandleAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillListRedHandleAction)context.get("listRedHandleAction");
  nc.ui.cmp.bill.actions.CmpBillListRedHandleAction bean = new nc.ui.cmp.bill.actions.CmpBillListRedHandleAction();
  context.put("listRedHandleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setBillType("F4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getRelatedQueryActionGroup(){
 if(context.get("relatedQueryActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("relatedQueryActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("relatedQueryActionGroup",bean);
  bean.setCode("relatedQuery");
  bean.setName(getI18nFB_c322a9());
  bean.setActions(getManagedList95());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c322a9(){
 if(context.get("nc.ui.uif2.I18nFB#c322a9")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c322a9");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c322a9",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("����");
  bean.setResId("03607mng-0105");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c322a9",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList95(){  List list = new ArrayList();  list.add(getLinkQueryAction());  list.add(getBillLQueryVoucherAction());  list.add(getBillLQueryNoteAction());  list.add(getBillLinkQueryBudgetAction());  list.add(getLinkBConferQueryAction());  return list;}

public nc.ui.pubapp.uif2app.actions.LinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.LinkQueryAction)context.get("linkQueryAction");
  nc.ui.pubapp.uif2app.actions.LinkQueryAction bean = new nc.ui.pubapp.uif2app.actions.LinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType("F4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.BillLQueryVoucherAction getBillLQueryVoucherAction(){
 if(context.get("billLQueryVoucherAction")!=null)
 return (nc.ui.cmp.bill.actions.BillLQueryVoucherAction)context.get("billLQueryVoucherAction");
  nc.ui.cmp.bill.actions.BillLQueryVoucherAction bean = new nc.ui.cmp.bill.actions.BillLQueryVoucherAction();
  context.put("billLQueryVoucherAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction getBillLinkQueryBudgetAction(){
 if(context.get("BillLinkQueryBudgetAction")!=null)
 return (nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction)context.get("BillLinkQueryBudgetAction");
  nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction bean = new nc.ui.cmp.bill.actions.BillLinkQueryBudgetAction();
  context.put("BillLinkQueryBudgetAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpFileDocManageAction getFileDocManageAction(){
 if(context.get("fileDocManageAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpFileDocManageAction)context.get("fileDocManageAction");
  nc.ui.cmp.bill.actions.CmpFileDocManageAction bean = new nc.ui.cmp.bill.actions.CmpFileDocManageAction();
  context.put("fileDocManageAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getPrintActiongroup(){
 if(context.get("printActiongroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printActiongroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printActiongroup",bean);
  bean.setActions(getManagedList96());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList96(){  List list = new ArrayList();  list.add(getPrintaction());  list.add(getPrintpreviewaction());  list.add(getOutputAction());  return list;}

private nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction getPrintaction(){
 if(context.get("printaction")!=null)
 return (nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction)context.get("printaction");
  nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction bean = new nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction();
  context.put("printaction",bean);
  bean.setPreview(false);
  bean.setModel(getManageAppModel());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction getPrintpreviewaction(){
 if(context.get("printpreviewaction")!=null)
 return (nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction)context.get("printpreviewaction");
  nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction bean = new nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction();
  context.put("printpreviewaction",bean);
  bean.setPreview(true);
  bean.setModel(getManageAppModel());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.OutputAction getOutputAction(){
 if(context.get("outputAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.OutputAction)context.get("outputAction");
  nc.ui.pubapp.uif2app.actions.OutputAction bean = new nc.ui.pubapp.uif2app.actions.OutputAction();
  context.put("outputAction",bean);
  bean.setModel(getManageAppModel());
  bean.setParent(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getListPrintMenuAction(){
 if(context.get("listPrintMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("listPrintMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("listPrintMenuAction",bean);
  bean.setCode("printMenuAction");
  bean.setName(getI18nFB_118b4d3());
  bean.setActions(getManagedList97());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_118b4d3(){
 if(context.get("nc.ui.uif2.I18nFB#118b4d3")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#118b4d3");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#118b4d3",bean);  bean.setResDir("common");
  bean.setResId("UC001-0000007");
  bean.setDefaultValue("��ӡ");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#118b4d3",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList97(){  List list = new ArrayList();  list.add(getPrintaction());  list.add(getPrintpreviewaction());  list.add(getOutputAction());  list.add(getPrintPreviewBillListAction());  return list;}

public nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction getPrintPreviewBillListAction(){
 if(context.get("printPreviewBillListAction")!=null)
 return (nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction)context.get("printPreviewBillListAction");
  nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction bean = new nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction();
  context.put("printPreviewBillListAction",bean);
  bean.setPrintAction(getPrintBillListAction());
  bean.setBtnName(getI18nFB_1079063());
  bean.setCode("billprint");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1079063(){
 if(context.get("nc.ui.uif2.I18nFB#1079063")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1079063");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1079063",bean);  bean.setResDir("3601tmpub_0");
  bean.setDefaultValue("��ӡ�嵥");
  bean.setResId("03601tmpub-1021");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1079063",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction getPrintBillListAction(){
 if(context.get("printBillListAction")!=null)
 return (nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction)context.get("printBillListAction");
  nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction bean = new nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction();
  context.put("printBillListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setNodeKey("recbilllist");
  bean.setPrintFactory(getPrintFactory());
  bean.setPrintDlgParentConatiner(getListView());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.action.listprint.DefaultTemplatePagePrintFactory getPrintFactory(){
 if(context.get("printFactory")!=null)
 return (nc.ui.tmpub.action.listprint.DefaultTemplatePagePrintFactory)context.get("printFactory");
  nc.ui.tmpub.action.listprint.DefaultTemplatePagePrintFactory bean = new nc.ui.tmpub.action.listprint.DefaultTemplatePagePrintFactory();
  context.put("printFactory",bean);
  bean.setMdId("4afe1534-2b21-41e6-ae1a-dd3310b6903c");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.userdefitem.UserDefItemContainer getUserdefitemContainer(){
 if(context.get("userdefitemContainer")!=null)
 return (nc.ui.uif2.userdefitem.UserDefItemContainer)context.get("userdefitemContainer");
  nc.ui.uif2.userdefitem.UserDefItemContainer bean = new nc.ui.uif2.userdefitem.UserDefItemContainer();
  context.put("userdefitemContainer",bean);
  bean.setContext(getContext());
  bean.setParams(getManagedList98());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList98(){  List list = new ArrayList();  list.add(getQueryParam_198637f());  list.add(getQueryParam_1ac3c2e());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_198637f(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#198637f")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#198637f");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#198637f",bean);
  bean.setMdfullname("cmp.cmp_recbill");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1ac3c2e(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1ac3c2e")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1ac3c2e");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1ac3c2e",bean);
  bean.setMdfullname("cmp.cmp_recbilldetail");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemListPreparator(){
 if(context.get("userdefitemListPreparator")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("userdefitemListPreparator");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("userdefitemListPreparator",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList99());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList99(){  List list = new ArrayList();  list.add(getUserdefQueryParam_a31aec());  list.add(getUserdefQueryParam_3ca550());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_a31aec(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#a31aec")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#a31aec");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#a31aec",bean);
  bean.setMdfullname("cmp.cmp_recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_3ca550(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#3ca550")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#3ca550");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#3ca550",bean);
  bean.setMdfullname("cmp.cmp_recbilldetail");
  bean.setPos(1);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemPreparator(){
 if(context.get("userdefitemPreparator")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("userdefitemPreparator");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("userdefitemPreparator",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList100());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList100(){  List list = new ArrayList();  list.add(getUserdefQueryParam_189295c());  list.add(getUserdefQueryParam_1fa6c51());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_189295c(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#189295c")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#189295c");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#189295c",bean);
  bean.setMdfullname("cmp.cmp_recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1fa6c51(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1fa6c51")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1fa6c51");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1fa6c51",bean);
  bean.setMdfullname("cmp.cmp_recbilldetail");
  bean.setPos(1);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller getRemoteCallCombinatorCaller(){
 if(context.get("remoteCallCombinatorCaller")!=null)
 return (nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller)context.get("remoteCallCombinatorCaller");
  nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller bean = new nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller();
  context.put("remoteCallCombinatorCaller",bean);
  bean.setRemoteCallers(getManagedList101());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList101(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList102());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList102(){  List list = new ArrayList();  list.add("D4");  return list;}

public nc.ui.cmp.bill.excelimport.RecBillExcelImportProcessor getExcelImportProcessor(){
 if(context.get("excelImportProcessor")!=null)
 return (nc.ui.cmp.bill.excelimport.RecBillExcelImportProcessor)context.get("excelImportProcessor");
  nc.ui.cmp.bill.excelimport.RecBillExcelImportProcessor bean = new nc.ui.cmp.bill.excelimport.RecBillExcelImportProcessor();
  context.put("excelImportProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
