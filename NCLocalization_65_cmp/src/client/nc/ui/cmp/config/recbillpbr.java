package nc.ui.cmp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class recbillpbr extends AbstractJavaBeanDefinition{
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
  bean.setName(getI18nFB_a6fbbd());
  bean.setActions(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_a6fbbd(){
 if(context.get("nc.ui.uif2.I18nFB#a6fbbd")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#a6fbbd");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#a6fbbd",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("关联功能");
  bean.setResId("03607mng-0098");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#a6fbbd",product);
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
  bean.setName(getI18nFB_8cadc());
  bean.setActions(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_8cadc(){
 if(context.get("nc.ui.uif2.I18nFB#8cadc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#8cadc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#8cadc",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607mng-0099");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#8cadc",product);
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
  bean.setName(getI18nFB_13e5c9e());
  bean.setActions(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_13e5c9e(){
 if(context.get("nc.ui.uif2.I18nFB#13e5c9e")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#13e5c9e");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#13e5c9e",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("常用单据");
  bean.setResId("03607mng-0100");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#13e5c9e",product);
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

private List getManagedList59(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_12c404c());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_12c404c(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#12c404c")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#12c404c");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#12c404c",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList60(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_314141());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_314141(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#314141")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#314141");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#314141",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList61(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_1b348de());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_1b348de(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1b348de")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1b348de");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1b348de",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList62(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_1750b98());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_1750b98(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1750b98")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1750b98");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#1750b98",bean);
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
  bean.setDispatcher(getMany2ManyDispatcher_cd45f9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_cd45f9(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#cd45f9")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#cd45f9");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#cd45f9",bean);
  bean.setMany2one(getManagedMap2());
  bean.setOne2many(getManagedMap3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList63(),getDigitListener());  return map;}

private List getManagedList63(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_currtype");  list.add("pk_group");  list.add("bill_date");  list.add("local_rate");  list.add("group_rate");  list.add("global_rate");  list.add("rec_primal");  list.add("price");  return list;}

private Map getManagedMap3(){  Map map = new HashMap();  map.put("cinventoryid",getManagedList64());  return map;}

private List getManagedList64(){  List list = new ArrayList();  list.add(getCountEditHandler_14c4edb());  return list;}

private nc.ui.cmp.viewhandler.list.CountEditHandler getCountEditHandler_14c4edb(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#14c4edb")!=null)
 return (nc.ui.cmp.viewhandler.list.CountEditHandler)context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#14c4edb");
  nc.ui.cmp.viewhandler.list.CountEditHandler bean = new nc.ui.cmp.viewhandler.list.CountEditHandler();
  context.put("nc.ui.cmp.viewhandler.list.CountEditHandler#14c4edb",bean);
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
  bean.setNodekey(getNodeKeyFactoryBean());
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

public nc.ui.cmp.bill.views.CmpRecBillCard getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.cmp.bill.views.CmpRecBillCard)context.get("billFormEditor");
  nc.ui.cmp.bill.views.CmpRecBillCard bean = new nc.ui.cmp.bill.views.CmpRecBillCard();
  context.put("billFormEditor",bean);
  bean.setOppui(getOppui());
  bean.setContainer(getContainer());
  bean.setModel(getManageAppModel());
  bean.setNodekeyQry(getD4NodekeyImpl());
  bean.setNodekey(getNodeKeyFactoryBean());
  bean.setClosingListener(getClosingListener());
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

private List getManagedList66(){  List list = new ArrayList();  list.add(getCmpRecBillBodyAddLineAction());  list.add(getCmpRecBillBodyInsertLineAction_ad03c6());  list.add(getBodyDelLineAction_d2cadf());  list.add(getBodyCopyLineAction_1050855());  list.add(getBodyPasteLineAction_16623bf());  return list;}

private nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction getCmpRecBillBodyInsertLineAction_ad03c6(){
 if(context.get("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#ad03c6")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction)context.get("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#ad03c6");
  nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction bean = new nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction();
  context.put("nc.ui.cmp.bill.actions.CmpRecBillBodyInsertLineAction#ad03c6",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyDelLineAction getBodyDelLineAction_d2cadf(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#d2cadf")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyDelLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#d2cadf");
  nc.ui.pubapp.uif2app.actions.BodyDelLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyDelLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#d2cadf",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyCopyLineAction getBodyCopyLineAction_1050855(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1050855")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyCopyLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1050855");
  nc.ui.pubapp.uif2app.actions.BodyCopyLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyCopyLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#1050855",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyPasteLineAction getBodyPasteLineAction_16623bf(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#16623bf")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyPasteLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#16623bf");
  nc.ui.pubapp.uif2app.actions.BodyPasteLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyPasteLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#16623bf",bean);
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
  bean.setUiAccessor(getSettleUIAccessor_b4db92());
  bean.setFilterList(getManagedList68());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.settlement.filter.SettleUIAccessor getSettleUIAccessor_b4db92(){
 if(context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#b4db92")!=null)
 return (nc.ui.cmp.settlement.filter.SettleUIAccessor)context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#b4db92");
  nc.ui.cmp.settlement.filter.SettleUIAccessor bean = new nc.ui.cmp.settlement.filter.SettleUIAccessor();
  context.put("nc.ui.cmp.settlement.filter.SettleUIAccessor#b4db92",bean);
  bean.setBillCardPanelEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList68(){  List list = new ArrayList();  list.add(getFbmbillnoheadfilter());  list.add(getFbmbilltypeheadfilter());  list.add(getFbmbillnobodyfilter());  return list;}

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

private nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter getFbmbilltypeheadfilter(){
 if(context.get("fbmbilltypeheadfilter")!=null)
 return (nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter)context.get("fbmbilltypeheadfilter");
  nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter bean = new nc.ui.cmp.bill.filter.CmpGatherBillnoRefModelFilter();
  context.put("fbmbilltypeheadfilter",bean);
  bean.setSrcKey("note_type");
  bean.setSrcPos(0);
  bean.setSrcTabCode("cmp_recbill");
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

private List getManagedList71(){  List list = new ArrayList();  list.add(getBodyCardCustomerOrSupplierAfterEditHandler());  list.add(getBodyCardObjectTypeAfterEditHandler());  list.add(getBodyCheckNoAfterEditHandler());  list.add(getBodyCardPsndocAfterEditHandler());  list.add(getBodyTSPrimalAfterEditHandler());  list.add(getBodyCardBalatypeAfterEditHandler());  list.add(getBodyCardCountOrPriceAfterEditHandler());  list.add(getBodyCardPrimalAfterEditHandler());  list.add(getBodyBankAccAfterEditHandler());  return list;}

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

private nc.ui.cmp.bill.viewhandler.BodyCardPsndocAfterEditHandler getBodyCardPsndocAfterEditHandler(){
 if(context.get("BodyCardPsndocAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.BodyCardPsndocAfterEditHandler)context.get("BodyCardPsndocAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.BodyCardPsndocAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.BodyCardPsndocAfterEditHandler();
  context.put("BodyCardPsndocAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
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

private List getManagedList75(){  List list = new ArrayList();  list.add(getLoadBillListTemplate_14cf0a3());  return list;}

private nc.ui.cmp.viewhandler.list.LoadBillListTemplate getLoadBillListTemplate_14cf0a3(){
 if(context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#14cf0a3")!=null)
 return (nc.ui.cmp.viewhandler.list.LoadBillListTemplate)context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#14cf0a3");
  nc.ui.cmp.viewhandler.list.LoadBillListTemplate bean = new nc.ui.cmp.viewhandler.list.LoadBillListTemplate();
  context.put("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#14cf0a3",bean);
  bean.setListener(getManagedList76());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList76(){  List list = new ArrayList();  list.add(getCountDecimalListener_1bb81e5());  return list;}

private nc.ui.cmp.viewhandler.list.CountDecimalListener getCountDecimalListener_1bb81e5(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#1bb81e5")!=null)
 return (nc.ui.cmp.viewhandler.list.CountDecimalListener)context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#1bb81e5");
  nc.ui.cmp.viewhandler.list.CountDecimalListener bean = new nc.ui.cmp.viewhandler.list.CountDecimalListener();
  context.put("nc.ui.cmp.viewhandler.list.CountDecimalListener#1bb81e5",bean);
  bean.setTargetkey("rec_count");
  bean.setSource("ts");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList77(){  List list = new ArrayList();  list.add(getListHeadRowChangeListener_4a5dff());  list.add(getCardPanelRowChangedListener_6142c3());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener getListHeadRowChangeListener_4a5dff(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#4a5dff")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#4a5dff");
  nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener bean = new nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#4a5dff",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener getCardPanelRowChangedListener_6142c3(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#6142c3")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#6142c3");
  nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#6142c3",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList78(){  List list = new ArrayList();  list.add(getListHeadModelListener_1661066());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadModelListener getListHeadModelListener_1661066(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1661066")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadModelListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1661066");
  nc.ui.cmp.viewhandler.list.ListHeadModelListener bean = new nc.ui.cmp.viewhandler.list.ListHeadModelListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1661066",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList79(){  List list = new ArrayList();  list.add(getCardBodyAfterRowEditHandler_1bed2b3());  return list;}

private nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler getCardBodyAfterRowEditHandler_1bed2b3(){
 if(context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#1bed2b3")!=null)
 return (nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler)context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#1bed2b3");
  nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler bean = new nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler();
  context.put("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#1bed2b3",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getTSEditHandleMediator(){
 if(context.get("TSEditHandleMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("TSEditHandleMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getBillFormEditor());  context.put("TSEditHandleMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_fb18ee());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_fb18ee(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fb18ee")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fb18ee");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fb18ee",bean);
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
  bean.setNodeType("writeNode");
  bean.setPageLineActions(getManagedList83());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList83(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

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

private List getManagedList85(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getCommonuseBillActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getRelatingActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getExcelImportAndExportActiongroup());  list.add(getPrintActiongroup());  return list;}

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

private List getManagedList86(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getCardRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getCommonuseBillActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getRelatingActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getPrintActiongroup());  return list;}

private List getManagedList87(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getTempSaveAction());  list.add(getSeparatorAction());  list.add(getCancelAction());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getCommonuseBillActionGroup());  return list;}

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

public nc.ui.cmp.bill.actions.F4AddFlowMenuAction getAddActionGroup(){
 if(context.get("addActionGroup")!=null)
 return (nc.ui.cmp.bill.actions.F4AddFlowMenuAction)context.get("addActionGroup");
  nc.ui.cmp.bill.actions.F4AddFlowMenuAction bean = new nc.ui.cmp.bill.actions.F4AddFlowMenuAction();
  context.put("addActionGroup",bean);
  bean.setTransferBillViewProcessor(getTransferProcessor());
  bean.setBillform(getBillFormEditor());
  bean.setListview(getListView());
  bean.setCode("自制");
  bean.setName(getI18nFB_121b3d0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_121b3d0(){
 if(context.get("nc.ui.uif2.I18nFB#121b3d0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#121b3d0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#121b3d0",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("新增");
  bean.setResId("03607mng-0398");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#121b3d0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.pubapp.billref.dest.TransferBillViewProcessor getTransferProcessor(){
 if(context.get("transferProcessor")!=null)
 return (nc.ui.pubapp.billref.dest.TransferBillViewProcessor)context.get("transferProcessor");
  nc.ui.pubapp.billref.dest.TransferBillViewProcessor bean = new nc.ui.pubapp.billref.dest.TransferBillViewProcessor();
  context.put("transferProcessor",bean);
  bean.setList(getListView());
  bean.setTransferLogic(getTransferLogic());
  bean.setActionContainer(getListActions());
  bean.setBillForm(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.billref.dest.DefaultBillDataLogic getTransferLogic(){
 if(context.get("transferLogic")!=null)
 return (nc.ui.pubapp.billref.dest.DefaultBillDataLogic)context.get("transferLogic");
  nc.ui.pubapp.billref.dest.DefaultBillDataLogic bean = new nc.ui.pubapp.billref.dest.DefaultBillDataLogic();
  context.put("transferLogic",bean);
  bean.setBillForm(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getOppActions(){
 if(context.get("oppActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("oppActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getOppui());  context.put("oppActions",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_240345());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_240345(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#240345")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#240345");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#240345",bean);
  bean.setTabs(getManagedList88());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList88(){  List list = new ArrayList();  list.add(getHSNode_930959());  list.add(getVSNode_138775b());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_930959(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#930959")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#930959");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#930959",bean);
  bean.setName(getI18nFB_1a41d44());
  bean.setDividerLocation(0.2f);
  bean.setLeft(getCNode_17d8228());
  bean.setRight(getVSNode_1157ac());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1a41d44(){
 if(context.get("nc.ui.uif2.I18nFB#1a41d44")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1a41d44");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1a41d44",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("列表");
  bean.setResId("03607mng-0101");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1a41d44",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_17d8228(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#17d8228")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#17d8228");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#17d8228",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_1157ac(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#1157ac")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#1157ac");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#1157ac",bean);
  bean.setUp(getCNode_ec11e2());
  bean.setDown(getCNode_8a1f5e());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_ec11e2(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#ec11e2")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#ec11e2");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#ec11e2",bean);
  bean.setComponent(getListInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_8a1f5e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#8a1f5e")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#8a1f5e");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#8a1f5e",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_138775b(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#138775b")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#138775b");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#138775b",bean);
  bean.setName(getI18nFB_14ab882());
  bean.setUp(getCNode_976392());
  bean.setDown(getTBNode_5db19f());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_14ab882(){
 if(context.get("nc.ui.uif2.I18nFB#14ab882")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#14ab882");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#14ab882",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("卡片");
  bean.setResId("03607mng-0399");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#14ab882",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_976392(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#976392")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#976392");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#976392",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_5db19f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#5db19f")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#5db19f");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#5db19f",bean);
  bean.setName(getI18nFB_10a0aa6());
  bean.setTabs(getManagedList89());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_10a0aa6(){
 if(context.get("nc.ui.uif2.I18nFB#10a0aa6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#10a0aa6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#10a0aa6",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("卡片");
  bean.setResId("03607mng-0399");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#10a0aa6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList89(){  List list = new ArrayList();  list.add(getCNode_1155d79());  list.add(getCNode_93b14e());  return list;}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1155d79(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1155d79")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1155d79");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1155d79",bean);
  bean.setComponent(getBillFormEditor());
  bean.setName(getI18nFB_411df3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_411df3(){
 if(context.get("nc.ui.uif2.I18nFB#411df3")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#411df3");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#411df3",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("业务信息");
  bean.setResId("03607mng-0102");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#411df3",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_93b14e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#93b14e")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#93b14e");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#93b14e",bean);
  bean.setComponent(getOppui());
  bean.setName(getI18nFB_1abedf5());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1abedf5(){
 if(context.get("nc.ui.uif2.I18nFB#1abedf5")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1abedf5");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1abedf5",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("结算信息");
  bean.setResId("03607mng-0400");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1abedf5",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

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

public nc.ui.cmp.bill.actions.CmpRecBillTempSaveAction getTempSaveAction(){
 if(context.get("tempSaveAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpRecBillTempSaveAction)context.get("tempSaveAction");
  nc.ui.cmp.bill.actions.CmpRecBillTempSaveAction bean = new nc.ui.cmp.bill.actions.CmpRecBillTempSaveAction();
  context.put("tempSaveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillCommisionRecSaveUIAction getCommisionPayAction(){
 if(context.get("commisionPayAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillCommisionRecSaveUIAction)context.get("commisionPayAction");
  nc.ui.cmp.bill.actions.CmpBillCommisionRecSaveUIAction bean = new nc.ui.cmp.bill.actions.CmpBillCommisionRecSaveUIAction();
  context.put("commisionPayAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setContext(getContext());
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

public nc.ui.cmp.bill.actions.CmpBillDeleteAction getDelAction(){
 if(context.get("delAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillDeleteAction)context.get("delAction");
  nc.ui.cmp.bill.actions.CmpBillDeleteAction bean = new nc.ui.cmp.bill.actions.CmpBillDeleteAction();
  context.put("delAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillListDeleteAction getListDelAction(){
 if(context.get("listDelAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillListDeleteAction)context.get("listDelAction");
  nc.ui.cmp.bill.actions.CmpBillListDeleteAction bean = new nc.ui.cmp.bill.actions.CmpBillListDeleteAction();
  context.put("listDelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
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

public nc.funcnode.ui.action.GroupAction getAssociateActionGroup(){
 if(context.get("associateActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("associateActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("associateActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_52e6af());
  bean.setActions(getManagedList90());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_52e6af(){
 if(context.get("nc.ui.uif2.I18nFB#52e6af")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#52e6af");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#52e6af",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("关联结算信息");
  bean.setResId("03607mng-0106");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#52e6af",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList90(){  List list = new ArrayList();  list.add(getAssociateAction());  list.add(getUnAssociateAction());  return list;}

public nc.funcnode.ui.action.MenuAction getImageFuncActionGroup(){
 if(context.get("imageFuncActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("imageFuncActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("imageFuncActionGroup",bean);
  bean.setCode("imageFunc");
  bean.setName(getI18nFB_d94711());
  bean.setActions(getManagedList91());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d94711(){
 if(context.get("nc.ui.uif2.I18nFB#d94711")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d94711");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d94711",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("影像");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d94711",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList91(){  List list = new ArrayList();  list.add(getImageShowAction());  list.add(getImageScanAction());  return list;}

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
  bean.setName(getI18nFB_c8ad86());
  bean.setActions(getManagedList92());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c8ad86(){
 if(context.get("nc.ui.uif2.I18nFB#c8ad86")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c8ad86");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c8ad86",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("辅助功能");
  bean.setResId("03607mng-0401");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c8ad86",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList92(){  List list = new ArrayList();  list.add(getFileDocManageAction());  list.add(getRecBillReversalAction());  list.add(getCmpBillCancelReversalAction());  return list;}

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

public nc.funcnode.ui.action.MenuAction getRelatedQueryActionGroup(){
 if(context.get("relatedQueryActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("relatedQueryActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("relatedQueryActionGroup",bean);
  bean.setCode("relatedQuery");
  bean.setName(getI18nFB_f85589());
  bean.setActions(getManagedList93());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_f85589(){
 if(context.get("nc.ui.uif2.I18nFB#f85589")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#f85589");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#f85589",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("联查");
  bean.setResId("03607mng-0105");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#f85589",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList93(){  List list = new ArrayList();  list.add(getLinkQueryAction());  list.add(getFlowStateQueryAction());  list.add(getBillLQueryVoucherAction());  list.add(getBillLQueryNoteAction());  list.add(getBillLinkQueryBudgetAction());  list.add(getLinkBConferQueryAction());  return list;}

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

public nc.ui.cmp.bill.actions.BillFlowStateQueryAction getFlowStateQueryAction(){
 if(context.get("flowStateQueryAction")!=null)
 return (nc.ui.cmp.bill.actions.BillFlowStateQueryAction)context.get("flowStateQueryAction");
  nc.ui.cmp.bill.actions.BillFlowStateQueryAction bean = new nc.ui.cmp.bill.actions.BillFlowStateQueryAction();
  context.put("flowStateQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContainer(getContainer());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
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
  bean.setActions(getManagedList94());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList94(){  List list = new ArrayList();  list.add(getPrintaction());  list.add(getPrintpreviewaction());  list.add(getOutputAction());  return list;}

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

public nc.ui.uif2.userdefitem.UserDefItemContainer getUserdefitemContainer(){
 if(context.get("userdefitemContainer")!=null)
 return (nc.ui.uif2.userdefitem.UserDefItemContainer)context.get("userdefitemContainer");
  nc.ui.uif2.userdefitem.UserDefItemContainer bean = new nc.ui.uif2.userdefitem.UserDefItemContainer();
  context.put("userdefitemContainer",bean);
  bean.setContext(getContext());
  bean.setParams(getManagedList95());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList95(){  List list = new ArrayList();  list.add(getQueryParam_1b7d67e());  list.add(getQueryParam_631c50());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1b7d67e(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1b7d67e")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1b7d67e");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1b7d67e",bean);
  bean.setMdfullname("cmp.cmp_recbill");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_631c50(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#631c50")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#631c50");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#631c50",bean);
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
  bean.setParams(getManagedList96());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList96(){  List list = new ArrayList();  list.add(getUserdefQueryParam_d7ffc7());  list.add(getUserdefQueryParam_d99276());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_d7ffc7(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#d7ffc7")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#d7ffc7");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#d7ffc7",bean);
  bean.setMdfullname("cmp.cmp_recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_d99276(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#d99276")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#d99276");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#d99276",bean);
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
  bean.setParams(getManagedList97());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList97(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1e9d1c());  list.add(getUserdefQueryParam_1dc9c67());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1e9d1c(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1e9d1c")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1e9d1c");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1e9d1c",bean);
  bean.setMdfullname("cmp.cmp_recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1dc9c67(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1dc9c67")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1dc9c67");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1dc9c67",bean);
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
  bean.setRemoteCallers(getManagedList98());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList98(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

public java.lang.String getNodeKeyFactoryBean(){
 if(context.get("nodeKeyFactoryBean")!=null)
 return (java.lang.String)context.get("nodeKeyFactoryBean");
  nc.ui.cmp.bill.impl.NodeKeyFactoryBean bean = new nc.ui.cmp.bill.impl.NodeKeyFactoryBean();
    context.put("&nodeKeyFactoryBean",bean);  bean.setContext(getContext());
  bean.setDefaultNodeKey("D4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nodeKeyFactoryBean",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public java.util.List getNodeKeysFactoryBean(){
 if(context.get("nodeKeysFactoryBean")!=null)
 return (java.util.List)context.get("nodeKeysFactoryBean");
  nc.ui.cmp.bill.impl.NodeKeysFactoryBean bean = new nc.ui.cmp.bill.impl.NodeKeysFactoryBean();
    context.put("&nodeKeysFactoryBean",bean);  bean.setContext(getContext());
  bean.setDefaultNodeKey("D4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nodeKeysFactoryBean",product);
     return (java.util.List)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getNodeKeysFactoryBean());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

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
