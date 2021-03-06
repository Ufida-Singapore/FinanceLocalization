package nc.ui.cmp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class pbm extends AbstractJavaBeanDefinition{
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
  bean.setName(getI18nFB_1a97318());
  bean.setActions(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1a97318(){
 if(context.get("nc.ui.uif2.I18nFB#1a97318")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1a97318");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1a97318",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("关联功能");
  bean.setResId("03607mng-0098");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1a97318",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList0(){  List list = new ArrayList();  list.add(getCmpNoteRegisterAction());  return list;}

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
  bean.setName(getI18nFB_1c33ad8());
  bean.setActions(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1c33ad8(){
 if(context.get("nc.ui.uif2.I18nFB#1c33ad8")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1c33ad8");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1c33ad8",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607mng-0099");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1c33ad8",product);
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
  bean.setName(getI18nFB_4c4404());
  bean.setActions(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_4c4404(){
 if(context.get("nc.ui.uif2.I18nFB#4c4404")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#4c4404");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#4c4404",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("常用单据");
  bean.setResId("03607mng-0100");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#4c4404",product);
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

public nc.ui.cmp.bill.model.CmpBillDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.cmp.bill.model.CmpBillDataManager)context.get("modelDataManager");
  nc.ui.cmp.bill.model.CmpBillDataManager bean = new nc.ui.cmp.bill.model.CmpBillDataManager();
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

public nc.ui.uif2.actions.QueryAreaShell getQueryArea(){
 if(context.get("queryArea")!=null)
 return (nc.ui.uif2.actions.QueryAreaShell)context.get("queryArea");
  nc.ui.uif2.actions.QueryAreaShell bean = new nc.ui.uif2.actions.QueryAreaShell();
  context.put("queryArea",bean);
  bean.setQueryArea(getQueryAction_created_109d4b3());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.queryarea.QueryArea getQueryAction_created_109d4b3(){
 if(context.get("queryAction.created#109d4b3")!=null)
 return (nc.ui.queryarea.QueryArea)context.get("queryAction.created#109d4b3");
  nc.ui.queryarea.QueryArea bean = getQueryAction().createQueryArea();
  context.put("queryAction.created#109d4b3",bean);
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

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getManagedList4(),getManagedList10());  map.put(getManagedList18(),getManagedList29());  map.put(getManagedList38(),getManagedList49());  return map;}

private List getManagedList4(){  List list = new ArrayList();  list.add(getManagedList5());  list.add(getManagedList6());  list.add(getManagedList7());  list.add(getManagedList8());  list.add(getManagedList9());  return list;}

private List getManagedList5(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("HEAD");  return list;}

private List getManagedList6(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList8(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList9(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("bill_date");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList10(){  List list = new ArrayList();  list.add(getManagedList11());  list.add(getManagedList12());  list.add(getManagedList13());  list.add(getManagedList14());  list.add(getManagedList15());  list.add(getManagedList16());  list.add(getManagedList17());  return list;}

private List getManagedList11(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("primal_money");  list.add("HEAD");  return list;}

private List getManagedList12(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("local_money");  list.add("HEAD");  return list;}

private List getManagedList13(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local");  list.add("HEAD");  return list;}

private List getManagedList14(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local");  list.add("HEAD");  return list;}

private List getManagedList15(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("HEAD");  return list;}

private List getManagedList16(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("HEAD");  return list;}

private List getManagedList17(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("HEAD");  return list;}

private List getManagedList18(){  List list = new ArrayList();  list.add(getManagedList19());  list.add(getManagedList20());  list.add(getManagedList21());  list.add(getManagedList22());  list.add(getManagedList23());  list.add(getManagedList24());  list.add(getManagedList25());  list.add(getManagedList26());  list.add(getManagedList27());  list.add(getManagedList28());  return list;}

private List getManagedList19(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList20(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList21(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList22(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList23(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("bill_date");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList24(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList25(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList26(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList27(){  List list = new ArrayList();  list.add("MONEY");  list.add("pay_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList28(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList29(){  List list = new ArrayList();  list.add(getManagedList30());  list.add(getManagedList31());  list.add(getManagedList32());  list.add(getManagedList33());  list.add(getManagedList34());  list.add(getManagedList35());  list.add(getManagedList36());  list.add(getManagedList37());  return list;}

private List getManagedList30(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("pay_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList31(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList32(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList33(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList34(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("pay_local");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList35(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local_pay");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList36(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local_pay");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList37(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList38(){  List list = new ArrayList();  list.add(getManagedList39());  list.add(getManagedList40());  list.add(getManagedList41());  list.add(getManagedList42());  list.add(getManagedList43());  list.add(getManagedList44());  list.add(getManagedList45());  list.add(getManagedList46());  list.add(getManagedList47());  list.add(getManagedList48());  return list;}

private List getManagedList39(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList40(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList41(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList42(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList43(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("bill_date");  list.add("HEAD");  return list;}

private List getManagedList44(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList45(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList46(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList47(){  List list = new ArrayList();  list.add("MONEY");  list.add("ts_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList48(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList49(){  List list = new ArrayList();  list.add(getManagedList50());  list.add(getManagedList51());  list.add(getManagedList52());  list.add(getManagedList53());  list.add(getManagedList54());  list.add(getManagedList55());  list.add(getManagedList56());  list.add(getManagedList57());  return list;}

private List getManagedList50(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("ts_primal");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList51(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("local_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList52(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("group_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList53(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("global_rate");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList54(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("ts_local");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList55(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("group_local_ts");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList56(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("global_local_ts");  list.add("BODY");  list.add("items");  return list;}

private List getManagedList57(){  List list = new ArrayList();  list.add("UNIT_PRICE");  list.add("price");  list.add("BODY");  list.add("items");  return list;}

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

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList58());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList59());  map.put("nc.ui.pubapp.uif2app.event.PubAppEvent",getManagedList60());  map.put("nc.ui.pubapp.uif2app.event.list.ListBillItemHyperlinkEvent",getManagedList61());  return map;}

private List getManagedList58(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_cee83());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_cee83(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#cee83")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#cee83");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#cee83",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList59(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_fef941());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_fef941(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#fef941")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#fef941");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#fef941",bean);
  bean.setSrcDestItemCollection(getSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList60(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_19e5669());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_19e5669(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#19e5669")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#19e5669");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#19e5669",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList61(){  List list = new ArrayList();  list.add(getCardPanelSelectionChangedListener_3b0f81());  return list;}

private nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener getCardPanelSelectionChangedListener_3b0f81(){
 if(context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#3b0f81")!=null)
 return (nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener)context.get("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#3b0f81");
  nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener bean = new nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.cmp.digit.listener.card.CardPanelSelectionChangedListener#3b0f81",bean);
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
  bean.setDispatcher(getMany2ManyDispatcher_21f546());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_21f546(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#21f546")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#21f546");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#21f546",bean);
  bean.setMany2one(getManagedMap2());
  bean.setOne2many(getManagedMap3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList62(),getDigitListener());  return map;}

private List getManagedList62(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_currtype");  list.add("bill_date");  list.add("local_rate");  list.add("group_rate");  list.add("global_rate");  list.add("pay_primal");  list.add("price");  return list;}

private Map getManagedMap3(){  Map map = new HashMap();  map.put("cinventoryid",getManagedList63());  return map;}

private List getManagedList63(){  List list = new ArrayList();  list.add(getCountEditHandler_177118f());  return list;}

private nc.ui.cmp.viewhandler.list.CountEditHandler getCountEditHandler_177118f(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#177118f")!=null)
 return (nc.ui.cmp.viewhandler.list.CountEditHandler)context.get("nc.ui.cmp.viewhandler.list.CountEditHandler#177118f");
  nc.ui.cmp.viewhandler.list.CountEditHandler bean = new nc.ui.cmp.viewhandler.list.CountEditHandler();
  context.put("nc.ui.cmp.viewhandler.list.CountEditHandler#177118f",bean);
  bean.setTargetkey("pay_count");
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

public nc.ui.cmp.bill.paybill.serviceproxy.PaySettleAccountsMaintainProxy getMaintainProxy(){
 if(context.get("maintainProxy")!=null)
 return (nc.ui.cmp.bill.paybill.serviceproxy.PaySettleAccountsMaintainProxy)context.get("maintainProxy");
  nc.ui.cmp.bill.paybill.serviceproxy.PaySettleAccountsMaintainProxy bean = new nc.ui.cmp.bill.paybill.serviceproxy.PaySettleAccountsMaintainProxy();
  context.put("maintainProxy",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.model.BillVOObjectAdapterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.ui.cmp.bill.model.BillVOObjectAdapterFactory)context.get("boadatorfactory");
  nc.ui.cmp.bill.model.BillVOObjectAdapterFactory bean = new nc.ui.cmp.bill.model.BillVOObjectAdapterFactory();
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
  bean.setBillType("F5");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.impl.D5NodekeyImpl getD5NodekeyImpl(){
 if(context.get("D5NodekeyImpl")!=null)
 return (nc.ui.cmp.bill.impl.D5NodekeyImpl)context.get("D5NodekeyImpl");
  nc.ui.cmp.bill.impl.D5NodekeyImpl bean = new nc.ui.cmp.bill.impl.D5NodekeyImpl();
  context.put("D5NodekeyImpl",bean);
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
  bean.setNodekey("D5");
  bean.setPaginationDelegate(getPaginationDelegator());
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setShowTotalLine(true);
  bean.setShowTotalLineTabcodes(getManagedList64());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList64(){  List list = new ArrayList();  list.add("items");  return list;}

public nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator getPaginationDelegator(){
 if(context.get("paginationDelegator")!=null)
 return (nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator)context.get("paginationDelegator");
  nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator bean = new nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator(getManageAppModel());  context.put("paginationDelegator",bean);
  bean.setPaginationQuery(getPaginationQueryService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.model.CmpPayBillQuery getPaginationQueryService(){
 if(context.get("paginationQueryService")!=null)
 return (nc.ui.cmp.bill.model.CmpPayBillQuery)context.get("paginationQueryService");
  nc.ui.cmp.bill.model.CmpPayBillQuery bean = new nc.ui.cmp.bill.model.CmpPayBillQuery();
  context.put("paginationQueryService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.views.CmpBillDefValue getBillDefVauleItf(){
 if(context.get("billDefVauleItf")!=null)
 return (nc.ui.cmp.bill.views.CmpBillDefValue)context.get("billDefVauleItf");
  nc.ui.cmp.bill.views.CmpBillDefValue bean = new nc.ui.cmp.bill.views.CmpBillDefValue();
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

public nc.ui.cmp.bill.views.CmpBillCard getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.cmp.bill.views.CmpBillCard)context.get("billFormEditor");
  nc.ui.cmp.bill.views.CmpBillCard bean = new nc.ui.cmp.bill.views.CmpBillCard();
  context.put("billFormEditor",bean);
  bean.setOppui(getOppui());
  bean.setContainer(getContainer());
  bean.setModel(getManageAppModel());
  bean.setContext(getContext());
  bean.setNodekeyQry(getD5NodekeyImpl());
  bean.setClosingListener(getClosingListener());
  bean.setNodekey("D5");
  bean.setBillDataManager(getModelDataManager());
  bean.setComponentValueManager(getComponentValueManager());
  bean.setAutoAddLine(false);
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
  bean.setDefValueItf(getBillDefVauleItf());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setBodyLineActions(getManagedList65());
  bean.setDefaultRefWherePartHandler(getDefaultRefWherePartHandler());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList65(){  List list = new ArrayList();  list.add(getCmpBillBodyAddLineAction());  list.add(getCmpBillBodyInsertLineAction_16defc3());  list.add(getBodyDelLineAction_981f3b());  list.add(getBodyCopyLineAction_12a650d());  list.add(getBodyPasteLineAction_504042());  return list;}

private nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction getCmpBillBodyInsertLineAction_16defc3(){
 if(context.get("nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction#16defc3")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction)context.get("nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction#16defc3");
  nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction bean = new nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction();
  context.put("nc.ui.cmp.bill.actions.CmpBillBodyInsertLineAction#16defc3",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyDelLineAction getBodyDelLineAction_981f3b(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#981f3b")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyDelLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#981f3b");
  nc.ui.pubapp.uif2app.actions.BodyDelLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyDelLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyDelLineAction#981f3b",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyCopyLineAction getBodyCopyLineAction_12a650d(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#12a650d")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyCopyLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#12a650d");
  nc.ui.pubapp.uif2app.actions.BodyCopyLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyCopyLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyCopyLineAction#12a650d",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.BodyPasteLineAction getBodyPasteLineAction_504042(){
 if(context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#504042")!=null)
 return (nc.ui.pubapp.uif2app.actions.BodyPasteLineAction)context.get("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#504042");
  nc.ui.pubapp.uif2app.actions.BodyPasteLineAction bean = new nc.ui.pubapp.uif2app.actions.BodyPasteLineAction();
  context.put("nc.ui.pubapp.uif2app.actions.BodyPasteLineAction#504042",bean);
  bean.setClearItems(getManagedList66());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList66(){  List list = new ArrayList();  list.add("pk_paybill_detail");  list.add("bankrelated_code");  list.add("ts");  return list;}

public nc.ui.cmp.bill.actions.CmpBillBodyAddLineAction getCmpBillBodyAddLineAction(){
 if(context.get("CmpBillBodyAddLineAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillBodyAddLineAction)context.get("CmpBillBodyAddLineAction");
  nc.ui.cmp.bill.actions.CmpBillBodyAddLineAction bean = new nc.ui.cmp.bill.actions.CmpBillBodyAddLineAction();
  context.put("CmpBillBodyAddLineAction",bean);
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
  bean.setUiAccessor(getSettleUIAccessor_aac1bb());
  bean.setFilterList(getManagedList67());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.settlement.filter.SettleUIAccessor getSettleUIAccessor_aac1bb(){
 if(context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#aac1bb")!=null)
 return (nc.ui.cmp.settlement.filter.SettleUIAccessor)context.get("nc.ui.cmp.settlement.filter.SettleUIAccessor#aac1bb");
  nc.ui.cmp.settlement.filter.SettleUIAccessor bean = new nc.ui.cmp.settlement.filter.SettleUIAccessor();
  context.put("nc.ui.cmp.settlement.filter.SettleUIAccessor#aac1bb",bean);
  bean.setBillCardPanelEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList67(){  List list = new ArrayList();  list.add(getFbmbillnoheadfilter());  list.add(getFbmbillnobodyfilter());  return list;}

private nc.ui.cmp.bill.filter.CmpBillFbmbillnoRefModelFilter getFbmbillnoheadfilter(){
 if(context.get("fbmbillnoheadfilter")!=null)
 return (nc.ui.cmp.bill.filter.CmpBillFbmbillnoRefModelFilter)context.get("fbmbillnoheadfilter");
  nc.ui.cmp.bill.filter.CmpBillFbmbillnoRefModelFilter bean = new nc.ui.cmp.bill.filter.CmpBillFbmbillnoRefModelFilter();
  context.put("fbmbillnoheadfilter",bean);
  bean.setSrcKey("note_no");
  bean.setDestKey("note_no");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.bill.filter.CmpBillBodyFbmbillnoRefModelFilter getFbmbillnobodyfilter(){
 if(context.get("fbmbillnobodyfilter")!=null)
 return (nc.ui.cmp.bill.filter.CmpBillBodyFbmbillnoRefModelFilter)context.get("fbmbillnobodyfilter");
  nc.ui.cmp.bill.filter.CmpBillBodyFbmbillnoRefModelFilter bean = new nc.ui.cmp.bill.filter.CmpBillBodyFbmbillnoRefModelFilter();
  context.put("fbmbillnobodyfilter",bean);
  bean.setSrcKey("note_no");
  bean.setSrcPos(1);
  bean.setSrcTabCode("cmp_paybill");
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

public nc.ui.cmp.bill.cal.CmpPayBillCardCalculator getCmpPayBillCardCalculator(){
 if(context.get("CmpPayBillCardCalculator")!=null)
 return (nc.ui.cmp.bill.cal.CmpPayBillCardCalculator)context.get("CmpPayBillCardCalculator");
  nc.ui.cmp.bill.cal.CmpPayBillCardCalculator bean = new nc.ui.cmp.bill.cal.CmpPayBillCardCalculator();
  context.put("CmpPayBillCardCalculator",bean);
  bean.setCalItem(getRelationItemForCal());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.cal.CmpPayBillRelationItemForCal getRelationItemForCal(){
 if(context.get("relationItemForCal")!=null)
 return (nc.ui.cmp.bill.cal.CmpPayBillRelationItemForCal)context.get("relationItemForCal");
  nc.ui.cmp.bill.cal.CmpPayBillRelationItemForCal bean = new nc.ui.cmp.bill.cal.CmpPayBillRelationItemForCal();
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

private Map getManagedMap4(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.mediator.mutiltrans.NodekeyEvent",getManagedList68());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent",getManagedList69());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent",getManagedList70());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent",getManagedList71());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent",getManagedList72());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList73());  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList74());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadRowChangedEvent",getManagedList76());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadDataChangedEvent",getManagedList77());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent",getManagedList78());  return map;}

private List getManagedList68(){  List list = new ArrayList();  list.add(getCmpBillNodeKeyChangeListener());  list.add(getAddActionGroup());  return list;}

private List getManagedList69(){  List list = new ArrayList();  list.add(getBodyBillItemBeforeEditHandler());  list.add(getBodyBankAccBeforeEditHandler());  list.add(getBodyCheckNoBeforeEditHandler());  list.add(getBodyFreeCustBeforeEditHandler());  list.add(getBodyCashAccountBeforeEditHandler());  list.add(getBodyCfTypeBeforeEditHandler());  list.add(getBodyObjectTypeBeforeEditHandler());  list.add(getBodySanhuBillItemBeforeEditHandler());  list.add(getBodyPksubjctBeforeEditHandler());  return list;}

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

private nc.ui.cmp.bill.viewhandler.cardbefore.BodyCfTypeBeforeEditHandler getBodyCfTypeBeforeEditHandler(){
 if(context.get("BodyCfTypeBeforeEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardbefore.BodyCfTypeBeforeEditHandler)context.get("BodyCfTypeBeforeEditHandler");
  nc.ui.cmp.bill.viewhandler.cardbefore.BodyCfTypeBeforeEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardbefore.BodyCfTypeBeforeEditHandler();
  context.put("BodyCfTypeBeforeEditHandler",bean);
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

private List getManagedList70(){  List list = new ArrayList();  list.add(getBodyPsnDocAfterEditHandler());  list.add(getBodyCardCustomerOrSupplierAfterEditHandler());  list.add(getBodyCardObjectTypeAfterEditHandler());  list.add(getCmpBankAccAfterEditHandler());  list.add(getBodyCheckNoAfterEditHandler());  list.add(getBodyCardBalatypeAfterEditHandler());  list.add(getBodyCardCountOrPriceAfterEditHandler());  list.add(getBodyCardPrimalAfterEditHandler());  list.add(getBodyCardCfTypeAfterEditHandler());  list.add(getBodyBankAccAfterEditHandler());  return list;}

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

private nc.ui.cmp.bill.viewhandler.CmpBankAccAfterEditHandler getCmpBankAccAfterEditHandler(){
 if(context.get("CmpBankAccAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.CmpBankAccAfterEditHandler)context.get("CmpBankAccAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.CmpBankAccAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.CmpBankAccAfterEditHandler();
  context.put("CmpBankAccAfterEditHandler",bean);
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

private nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCfTypeAfterEditHandler getBodyCardCfTypeAfterEditHandler(){
 if(context.get("BodyCardCfTypeAfterEditHandler")!=null)
 return (nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCfTypeAfterEditHandler)context.get("BodyCardCfTypeAfterEditHandler");
  nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCfTypeAfterEditHandler bean = new nc.ui.cmp.bill.viewhandler.cardafter.BodyCardCfTypeAfterEditHandler();
  context.put("BodyCardCfTypeAfterEditHandler",bean);
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

private List getManagedList71(){  List list = new ArrayList();  list.add(getHeadCashAccountBeforeEditHandler());  list.add(getHeadPksubjctBeforeEditHandler());  list.add(getHeadBankAccBeforeEditHandler());  list.add(getHeadResissueBeforeEditHandler());  list.add(getHeadCheckNoBeforeEditHandler());  list.add(getHeadObjectTypeBeforeEditHandler());  return list;}

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

private List getManagedList72(){  List list = new ArrayList();  list.add(getHBRelationAfterEditHandler());  list.add(getHeadCurrTypeAfterEditHandler());  list.add(getHeadObjtypeAfterEditHandler());  list.add(getHeadCusdocDocAfterEditHandler());  list.add(getHeadSupdocDocAfterEditHandler());  list.add(getHeadObjectTypeAfterEditHandler());  list.add(getHeadCustomerOrSupplierAfterEditHandler());  list.add(getHeadPsnDocAfterEditHandler());  list.add(getHeadBalatypeAfterEditHandler());  list.add(getHeadCheckNoAfterEditHandler());  return list;}

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

private nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler getHeadCheckNoAfterEditHandler(){
 if(context.get("HeadCheckNoAfterEditHandler")!=null)
 return (nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler)context.get("HeadCheckNoAfterEditHandler");
  nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler bean = new nc.ui.cmp.viewhandler.cardafter.HeadCheckNoAfterEditHandler();
  context.put("HeadCheckNoAfterEditHandler",bean);
  bean.setFieldGet(getCmpBillFieldGet());
  bean.setAddLineAction(getCmpBillBodyAddLineAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList73(){  List list = new ArrayList();  list.add(getLoadBillCardTemplate());  return list;}

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

private List getManagedList74(){  List list = new ArrayList();  list.add(getLoadBillListTemplate_d6d94f());  return list;}

private nc.ui.cmp.viewhandler.list.LoadBillListTemplate getLoadBillListTemplate_d6d94f(){
 if(context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#d6d94f")!=null)
 return (nc.ui.cmp.viewhandler.list.LoadBillListTemplate)context.get("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#d6d94f");
  nc.ui.cmp.viewhandler.list.LoadBillListTemplate bean = new nc.ui.cmp.viewhandler.list.LoadBillListTemplate();
  context.put("nc.ui.cmp.viewhandler.list.LoadBillListTemplate#d6d94f",bean);
  bean.setListener(getManagedList75());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList75(){  List list = new ArrayList();  list.add(getCountDecimalListener_94f2e5());  return list;}

private nc.ui.cmp.viewhandler.list.CountDecimalListener getCountDecimalListener_94f2e5(){
 if(context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#94f2e5")!=null)
 return (nc.ui.cmp.viewhandler.list.CountDecimalListener)context.get("nc.ui.cmp.viewhandler.list.CountDecimalListener#94f2e5");
  nc.ui.cmp.viewhandler.list.CountDecimalListener bean = new nc.ui.cmp.viewhandler.list.CountDecimalListener();
  context.put("nc.ui.cmp.viewhandler.list.CountDecimalListener#94f2e5",bean);
  bean.setTargetkey("pay_count");
  bean.setSource("ts");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList76(){  List list = new ArrayList();  list.add(getListHeadRowChangeListener_1550541());  list.add(getCardPanelRowChangedListener_cc1a2b());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener getListHeadRowChangeListener_1550541(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1550541")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1550541");
  nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener bean = new nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadRowChangeListener#1550541",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener getCardPanelRowChangedListener_cc1a2b(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#cc1a2b")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#cc1a2b");
  nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener(getBillFormEditor(),getSrcDestCollection());  context.put("nc.ui.tmpub.digit.listener.card.CardPanelRowChangedListener#cc1a2b",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList77(){  List list = new ArrayList();  list.add(getListHeadModelListener_1ca1e49());  return list;}

private nc.ui.cmp.viewhandler.list.ListHeadModelListener getListHeadModelListener_1ca1e49(){
 if(context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1ca1e49")!=null)
 return (nc.ui.cmp.viewhandler.list.ListHeadModelListener)context.get("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1ca1e49");
  nc.ui.cmp.viewhandler.list.ListHeadModelListener bean = new nc.ui.cmp.viewhandler.list.ListHeadModelListener();
  context.put("nc.ui.cmp.viewhandler.list.ListHeadModelListener#1ca1e49",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList78(){  List list = new ArrayList();  list.add(getCardBodyAfterRowEditHandler_8a56d2());  return list;}

private nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler getCardBodyAfterRowEditHandler_8a56d2(){
 if(context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#8a56d2")!=null)
 return (nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler)context.get("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#8a56d2");
  nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler bean = new nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler();
  context.put("nc.ui.cmp.bill.viewhandler.CardBodyAfterRowEditHandler#8a56d2",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.F5AddFlowMenuAction getAddActionGroup(){
 if(context.get("addActionGroup")!=null)
 return (nc.ui.cmp.bill.actions.F5AddFlowMenuAction)context.get("addActionGroup");
  nc.ui.cmp.bill.actions.F5AddFlowMenuAction bean = new nc.ui.cmp.bill.actions.F5AddFlowMenuAction();
  context.put("addActionGroup",bean);
  bean.setBillform(getBillFormEditor());
  bean.setListview(getListView());
  bean.setCode("add");
  bean.setName(getI18nFB_4a2161());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_4a2161(){
 if(context.get("nc.ui.uif2.I18nFB#4a2161")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#4a2161");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#4a2161",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("新增");
  bean.setResId("03607mng-0398");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#4a2161",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

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
  bean.setPageLineActions(getManagedList79());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList79(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

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
  bean.setContributors(getManagedList80());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList80(){  List list = new ArrayList();  list.add(getListActions());  list.add(getCardActions());  list.add(getOppActions());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getListActions(){
 if(context.get("listActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("listActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("listActions",bean);
  bean.setActions(getManagedList81());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList81(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getAuditActionGroup());  list.add(getListRedHandleAction());  list.add(getMadeBillAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getListPrintMenuAction());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getCardActions(){
 if(context.get("cardActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("cardActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getBillFormEditor());  context.put("cardActions",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList82());
  bean.setEditActions(getManagedList83());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList82(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDelScheduleAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getCmpBillCardRefreshAction());  list.add(getSeparatorAction());  list.add(getTranstypeAction());  list.add(getAssociateActionGroup());  list.add(getAuditCardActionGroup());  list.add(getListRedHandleAction());  list.add(getMadeBillAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getRelatedQueryActionGroup());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getPrintActiongroup());  return list;}

private List getManagedList83(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSeparatorAction());  list.add(getCancelAction());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  return list;}

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
  bean.setTangramLayoutRoot(getTBNode_1065a9e());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_1065a9e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#1065a9e")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#1065a9e");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#1065a9e",bean);
  bean.setTabs(getManagedList84());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList84(){  List list = new ArrayList();  list.add(getHSNode_10dfb3c());  list.add(getVSNode_19f0513());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_10dfb3c(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#10dfb3c")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#10dfb3c");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#10dfb3c",bean);
  bean.setName(getI18nFB_d571af());
  bean.setDividerLocation(0.2f);
  bean.setLeft(getCNode_11a9fc4());
  bean.setRight(getVSNode_1e18d74());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d571af(){
 if(context.get("nc.ui.uif2.I18nFB#d571af")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d571af");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d571af",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("列表");
  bean.setResId("03607mng-0101");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d571af",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_11a9fc4(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#11a9fc4")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#11a9fc4");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#11a9fc4",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_1e18d74(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#1e18d74")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#1e18d74");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#1e18d74",bean);
  bean.setUp(getCNode_ef2da3());
  bean.setDown(getCNode_9d4fca());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_ef2da3(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#ef2da3")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#ef2da3");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#ef2da3",bean);
  bean.setComponent(getListInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_9d4fca(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#9d4fca")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#9d4fca");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#9d4fca",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_19f0513(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#19f0513")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#19f0513");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#19f0513",bean);
  bean.setUp(getCNode_e952f2());
  bean.setDown(getTBNode_1fc377d());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_e952f2(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#e952f2")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#e952f2");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#e952f2",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_1fc377d(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#1fc377d")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#1fc377d");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#1fc377d",bean);
  bean.setName(getI18nFB_44a3e0());
  bean.setTabs(getManagedList85());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_44a3e0(){
 if(context.get("nc.ui.uif2.I18nFB#44a3e0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#44a3e0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#44a3e0",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("卡片");
  bean.setResId("03607mng-0399");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#44a3e0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList85(){  List list = new ArrayList();  list.add(getCNode_ec78de());  list.add(getCNode_1f5bbcb());  return list;}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_ec78de(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#ec78de")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#ec78de");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#ec78de",bean);
  bean.setComponent(getBillFormEditor());
  bean.setName(getI18nFB_62e609());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_62e609(){
 if(context.get("nc.ui.uif2.I18nFB#62e609")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#62e609");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#62e609",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("业务信息");
  bean.setResId("03607mng-0102");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#62e609",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1f5bbcb(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1f5bbcb")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1f5bbcb");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1f5bbcb",bean);
  bean.setComponent(getOppui());
  bean.setName(getI18nFB_f35f4d());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_f35f4d(){
 if(context.get("nc.ui.uif2.I18nFB#f35f4d")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#f35f4d");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#f35f4d",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("结算信息");
  bean.setResId("03607mng-0400");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#f35f4d",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.bill.actions.CmpBillSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillSaveAction)context.get("saveAction");
  nc.ui.cmp.bill.actions.CmpBillSaveAction bean = new nc.ui.cmp.bill.actions.CmpBillSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillCancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillCancelAction)context.get("cancelAction");
  nc.ui.cmp.bill.actions.CmpBillCancelAction bean = new nc.ui.cmp.bill.actions.CmpBillCancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setSaveAction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.CmpBillAddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillAddAction)context.get("addAction");
  nc.ui.cmp.bill.actions.CmpBillAddAction bean = new nc.ui.cmp.bill.actions.CmpBillAddAction();
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

public nc.ui.cmp.bill.actions.CmpBillCardRefreshAction getCmpBillCardRefreshAction(){
 if(context.get("cmpBillCardRefreshAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillCardRefreshAction)context.get("cmpBillCardRefreshAction");
  nc.ui.cmp.bill.actions.CmpBillCardRefreshAction bean = new nc.ui.cmp.bill.actions.CmpBillCardRefreshAction();
  context.put("cmpBillCardRefreshAction",bean);
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
  bean.setName(getI18nFB_276ddc());
  bean.setActions(getManagedList86());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_276ddc(){
 if(context.get("nc.ui.uif2.I18nFB#276ddc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#276ddc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#276ddc",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607mng-0103");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#276ddc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList86(){  List list = new ArrayList();  list.add(getAuditListAction());  list.add(getReverseAuditListAction());  list.add(getFlowStateQueryAction());  return list;}

public nc.funcnode.ui.action.GroupAction getAuditCardActionGroup(){
 if(context.get("auditCardActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("auditCardActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("auditCardActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_9da24a());
  bean.setActions(getManagedList87());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_9da24a(){
 if(context.get("nc.ui.uif2.I18nFB#9da24a")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#9da24a");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#9da24a",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607mng-0103");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#9da24a",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList87(){  List list = new ArrayList();  list.add(getAuditAction());  list.add(getReverseAuditAction());  list.add(getFlowStateQueryAction());  return list;}

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

public nc.ui.cmp.bill.actions.CmpBillListRedHandleAction getListRedHandleAction(){
 if(context.get("listRedHandleAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillListRedHandleAction)context.get("listRedHandleAction");
  nc.ui.cmp.bill.actions.CmpBillListRedHandleAction bean = new nc.ui.cmp.bill.actions.CmpBillListRedHandleAction();
  context.put("listRedHandleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setBillType("F5");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.PayTransTypeAction getTranstypeAction(){
 if(context.get("transtypeAction")!=null)
 return (nc.ui.cmp.bill.actions.PayTransTypeAction)context.get("transtypeAction");
  nc.ui.cmp.bill.actions.PayTransTypeAction bean = new nc.ui.cmp.bill.actions.PayTransTypeAction();
  context.put("transtypeAction",bean);
  bean.setModel(getManageAppModel());
  bean.setTransTypeRefModel(getTransTypeRefModel());
  bean.setWherepart(" parentbilltype = 'F5'");
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

public nc.ui.cmp.bill.actions.CmpBillCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillCopyAction)context.get("copyAction");
  nc.ui.cmp.bill.actions.CmpBillCopyAction bean = new nc.ui.cmp.bill.actions.CmpBillCopyAction();
  context.put("copyAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setFactory(getPayFactory());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.PayBillCopyFactory getPayFactory(){
 if(context.get("payFactory")!=null)
 return (nc.ui.cmp.bill.PayBillCopyFactory)context.get("payFactory");
  nc.ui.cmp.bill.PayBillCopyFactory bean = new nc.ui.cmp.bill.PayBillCopyFactory();
  context.put("payFactory",bean);
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
  bean.setNodeKey("D5");
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

public nc.ui.cmp.bill.CmpPayRelationQueryDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.cmp.bill.CmpPayRelationQueryDataListener)context.get("InitDataListener");
  nc.ui.cmp.bill.CmpPayRelationQueryDataListener bean = new nc.ui.cmp.bill.CmpPayRelationQueryDataListener();
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
  bean.setName(getI18nFB_6b3761());
  bean.setActions(getManagedList88());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_6b3761(){
 if(context.get("nc.ui.uif2.I18nFB#6b3761")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#6b3761");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#6b3761",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("影像");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#6b3761",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList88(){  List list = new ArrayList();  list.add(getImageShowAction());  list.add(getImageScanAction());  return list;}

public nc.ui.cmp.bill.actions.CmpBillImageShowAction getImageShowAction(){
 if(context.get("imageShowAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillImageShowAction)context.get("imageShowAction");
  nc.ui.cmp.bill.actions.CmpBillImageShowAction bean = new nc.ui.cmp.bill.actions.CmpBillImageShowAction();
  context.put("imageShowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPk_billtype("0000Z6000000000000F5");
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
  bean.setPk_billtype("0000Z6000000000000F5");
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
  bean.setName(getI18nFB_100ccc6());
  bean.setActions(getManagedList89());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_100ccc6(){
 if(context.get("nc.ui.uif2.I18nFB#100ccc6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#100ccc6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#100ccc6",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("辅助功能");
  bean.setResId("03607mng-0401");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#100ccc6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList89(){  List list = new ArrayList();  list.add(getFileDocManageAction());  list.add(getMakeAlterBill());  return list;}

public nc.funcnode.ui.action.GroupAction getAssociateActionGroup(){
 if(context.get("associateActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("associateActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("associateActionGroup",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_18408d0());
  bean.setActions(getManagedList90());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_18408d0(){
 if(context.get("nc.ui.uif2.I18nFB#18408d0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#18408d0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#18408d0",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("关联结算信息");
  bean.setResId("03607mng-0106");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#18408d0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList90(){  List list = new ArrayList();  list.add(getAssociateAction());  list.add(getUnAssociateAction());  return list;}

public nc.ui.cmp.bill.actions.AssociateUIAction getAssociateAction(){
 if(context.get("associateAction")!=null)
 return (nc.ui.cmp.bill.actions.AssociateUIAction)context.get("associateAction");
  nc.ui.cmp.bill.actions.AssociateUIAction bean = new nc.ui.cmp.bill.actions.AssociateUIAction();
  context.put("associateAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setContext(getContext());
  bean.setBillform(getBillFormEditor());
  bean.setBillType("F5");
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
  bean.setBillType("F5");
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.bill.actions.MakeAlterBillAction getMakeAlterBill(){
 if(context.get("makeAlterBill")!=null)
 return (nc.ui.cmp.bill.actions.MakeAlterBillAction)context.get("makeAlterBill");
  nc.ui.cmp.bill.actions.MakeAlterBillAction bean = new nc.ui.cmp.bill.actions.MakeAlterBillAction();
  context.put("makeAlterBill",bean);
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
  bean.setName(getI18nFB_15eb251());
  bean.setActions(getManagedList91());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_15eb251(){
 if(context.get("nc.ui.uif2.I18nFB#15eb251")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#15eb251");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#15eb251",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("联查");
  bean.setResId("03607mng-0105");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#15eb251",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList91(){  List list = new ArrayList();  list.add(getLinkQueryAction());  list.add(getBillLQueryVoucherAction());  list.add(getBillLQueryNoteAction());  list.add(getBillLinkQueryBudgetAction());  list.add(getLinkBConferQueryAction());  return list;}

public nc.ui.pubapp.uif2app.actions.LinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.LinkQueryAction)context.get("linkQueryAction");
  nc.ui.pubapp.uif2app.actions.LinkQueryAction bean = new nc.ui.pubapp.uif2app.actions.LinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType("F5");
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

public nc.funcnode.ui.action.GroupAction getPrintActiongroup(){
 if(context.get("printActiongroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printActiongroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printActiongroup",bean);
  bean.setActions(getManagedList92());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList92(){  List list = new ArrayList();  list.add(getPrintaction());  list.add(getPrintpreviewaction());  list.add(getOutputAction());  return list;}

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

public nc.ui.pubapp.uif2app.model.pagination.PubPaginationModel getPaginationModel(){
 if(context.get("paginationModel")!=null)
 return (nc.ui.pubapp.uif2app.model.pagination.PubPaginationModel)context.get("paginationModel");
  nc.ui.pubapp.uif2app.model.pagination.PubPaginationModel bean = new nc.ui.pubapp.uif2app.model.pagination.PubPaginationModel();
  context.put("paginationModel",bean);
  bean.setPaginationQueryService(getMaintainProxy());
  bean.init();
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
  bean.setName(getI18nFB_cecbf());
  bean.setActions(getManagedList93());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_cecbf(){
 if(context.get("nc.ui.uif2.I18nFB#cecbf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#cecbf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#cecbf",bean);  bean.setResDir("common");
  bean.setResId("UC001-0000007");
  bean.setDefaultValue("打印");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#cecbf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList93(){  List list = new ArrayList();  list.add(getPrintaction());  list.add(getPrintpreviewaction());  list.add(getOutputAction());  list.add(getPrintPreviewBillListAction());  return list;}

public nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction getPrintPreviewBillListAction(){
 if(context.get("printPreviewBillListAction")!=null)
 return (nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction)context.get("printPreviewBillListAction");
  nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction bean = new nc.ui.tmpub.action.listprint.TemplatePaginationPreviewAction();
  context.put("printPreviewBillListAction",bean);
  bean.setPrintAction(getPrintBillListAction());
  bean.setBtnName(getI18nFB_8b20dc());
  bean.setCode("billprint");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_8b20dc(){
 if(context.get("nc.ui.uif2.I18nFB#8b20dc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#8b20dc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#8b20dc",bean);  bean.setResDir("3601tmpub_0");
  bean.setDefaultValue("打印清单");
  bean.setResId("03601tmpub-1021");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#8b20dc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction getPrintBillListAction(){
 if(context.get("printBillListAction")!=null)
 return (nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction)context.get("printBillListAction");
  nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction bean = new nc.ui.tmpub.action.listprint.TemplatePaginationPrintAction();
  context.put("printBillListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setNodeKey("paybilllist");
  bean.setPrintFactory(getPrintFactory());
  bean.setPaginationModel(getPaginationModel());
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
  bean.setParams(getManagedList94());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList94(){  List list = new ArrayList();  list.add(getQueryParam_1a853d());  list.add(getQueryParam_12187a5());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1a853d(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1a853d")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1a853d");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1a853d",bean);
  bean.setMdfullname("cmp.cmp_paybill");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_12187a5(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#12187a5")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#12187a5");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#12187a5",bean);
  bean.setMdfullname("cmp.cmp_paybilldetail");
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
  bean.setParams(getManagedList95());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList95(){  List list = new ArrayList();  list.add(getUserdefQueryParam_508204());  list.add(getUserdefQueryParam_187ee6a());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_508204(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#508204")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#508204");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#508204",bean);
  bean.setMdfullname("cmp.cmp_paybill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_187ee6a(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#187ee6a")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#187ee6a");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#187ee6a",bean);
  bean.setMdfullname("cmp.cmp_paybilldetail");
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
  bean.setParams(getManagedList96());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList96(){  List list = new ArrayList();  list.add(getUserdefQueryParam_13593c9());  list.add(getUserdefQueryParam_7979f2());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_13593c9(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#13593c9")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#13593c9");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#13593c9",bean);
  bean.setMdfullname("cmp.cmp_paybill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_7979f2(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#7979f2")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#7979f2");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#7979f2",bean);
  bean.setMdfullname("cmp.cmp_paybilldetail");
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
  bean.setRemoteCallers(getManagedList97());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList97(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList98());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList98(){  List list = new ArrayList();  list.add("D5");  return list;}

public nc.ui.cmp.bill.excelimport.PayBillExcelImportProcessor getExcelImportProcessor(){
 if(context.get("excelImportProcessor")!=null)
 return (nc.ui.cmp.bill.excelimport.PayBillExcelImportProcessor)context.get("excelImportProcessor");
  nc.ui.cmp.bill.excelimport.PayBillExcelImportProcessor bean = new nc.ui.cmp.bill.excelimport.PayBillExcelImportProcessor();
  context.put("excelImportProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
