package nc.ui.arap.recpaystatement.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class recstatement_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.vo.uif2.LoginContext getContext(){
 if(context.get("context")!=null)
 return (nc.vo.uif2.LoginContext)context.get("context");
  nc.vo.uif2.LoginContext bean = new nc.vo.uif2.LoginContext();
  context.put("context",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.bd.meta.GeneralBDObjectAdapterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.vo.bd.meta.GeneralBDObjectAdapterFactory)context.get("boadatorfactory");
  nc.vo.bd.meta.GeneralBDObjectAdapterFactory bean = new nc.vo.bd.meta.GeneralBDObjectAdapterFactory();
  context.put("boadatorfactory",bean);
  bean.setMode("MD");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.BillManageModel getBillManageModel(){
 if(context.get("billManageModel")!=null)
 return (nc.ui.pubapp.uif2app.model.BillManageModel)context.get("billManageModel");
  nc.ui.pubapp.uif2app.model.BillManageModel bean = new nc.ui.pubapp.uif2app.model.BillManageModel();
  context.put("billManageModel",bean);
  bean.setContext(getContext());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.model.RecPayStatementModelDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.arap.recpaystatement.model.RecPayStatementModelDataManager)context.get("modelDataManager");
  nc.ui.arap.recpaystatement.model.RecPayStatementModelDataManager bean = new nc.ui.arap.recpaystatement.model.RecPayStatementModelDataManager();
  context.put("modelDataManager",bean);
  bean.setContext(getContext());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ArapStatementBillLinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.arap.recpaystatement.action.ArapStatementBillLinkQueryAction)context.get("linkQueryAction");
  nc.ui.arap.recpaystatement.action.ArapStatementBillLinkQueryAction bean = new nc.ui.arap.recpaystatement.action.ArapStatementBillLinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setListView(getListView());
  bean.setModel(getBillManageModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ArapStatementRefreshAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.arap.recpaystatement.action.ArapStatementRefreshAction)context.get("refreshAction");
  nc.ui.arap.recpaystatement.action.ArapStatementRefreshAction bean = new nc.ui.arap.recpaystatement.action.ArapStatementRefreshAction();
  context.put("refreshAction",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ReportTemplatePrintPreviewAction getPrintPreview(){
 if(context.get("printPreview")!=null)
 return (nc.ui.arap.recpaystatement.action.ReportTemplatePrintPreviewAction)context.get("printPreview");
  nc.ui.arap.recpaystatement.action.ReportTemplatePrintPreviewAction bean = new nc.ui.arap.recpaystatement.action.ReportTemplatePrintPreviewAction();
  context.put("printPreview",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ReportTemplatePrintAction getPrint(){
 if(context.get("print")!=null)
 return (nc.ui.arap.recpaystatement.action.ReportTemplatePrintAction)context.get("print");
  nc.ui.arap.recpaystatement.action.ReportTemplatePrintAction bean = new nc.ui.arap.recpaystatement.action.ReportTemplatePrintAction();
  context.put("print",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ReportTemplateBatchPrintAction getBatchPrint(){
 if(context.get("batchPrint")!=null)
 return (nc.ui.arap.recpaystatement.action.ReportTemplateBatchPrintAction)context.get("batchPrint");
  nc.ui.arap.recpaystatement.action.ReportTemplateBatchPrintAction bean = new nc.ui.arap.recpaystatement.action.ReportTemplateBatchPrintAction();
  context.put("batchPrint",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ReportOutputAction getOutput(){
 if(context.get("output")!=null)
 return (nc.ui.arap.recpaystatement.action.ReportOutputAction)context.get("output");
  nc.ui.arap.recpaystatement.action.ReportOutputAction bean = new nc.ui.arap.recpaystatement.action.ReportOutputAction();
  context.put("output",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getOutputMenuAction(){
 if(context.get("outputMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("outputMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("outputMenuAction",bean);
  bean.setCode("printMenuAction");
  bean.setName(getI18nFB_1811e22());
  bean.setActions(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1811e22(){
 if(context.get("nc.ui.uif2.I18nFB#1811e22")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1811e22");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1811e22",bean);  bean.setResDir("common");
  bean.setResId("UC001-0000007");
  bean.setDefaultValue("¥Ú”°");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1811e22",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList0(){  List list = new ArrayList();  list.add(getPrintPreview());  list.add(getPrint());  list.add(getOutput());  return list;}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getAppEventHandlerMediator(){
 if(context.get("appEventHandlerMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("appEventHandlerMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("appEventHandlerMediator",bean);
  bean.setModel(getBillManageModel());
  bean.setHandlerMap(getManagedMap0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListBodyRowChangedEvent",getManagedList1());  return map;}

private List getManagedList1(){  List list = new ArrayList();  list.add(getBodyRowChangeHandler());  return list;}

private nc.ui.arap.recpaystatement.action.BillListViewBodyRowChangeHandler getBodyRowChangeHandler(){
 if(context.get("bodyRowChangeHandler")!=null)
 return (nc.ui.arap.recpaystatement.action.BillListViewBodyRowChangeHandler)context.get("bodyRowChangeHandler");
  nc.ui.arap.recpaystatement.action.BillListViewBodyRowChangeHandler bean = new nc.ui.arap.recpaystatement.action.BillListViewBodyRowChangeHandler();
  context.put("bodyRowChangeHandler",bean);
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getCNode_1e170e8());
  bean.setModel(getBillManageModel());
  bean.setActions(getManagedList2());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1e170e8(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1e170e8")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1e170e8");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1e170e8",bean);
  bean.setName("");
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getLinkQueryAction());  list.add(getOutputMenuAction());  return list;}

public nc.ui.arap.recpaystatement.view.ListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.arap.recpaystatement.view.ListView)context.get("listView");
  nc.ui.arap.recpaystatement.view.ListView bean = new nc.ui.arap.recpaystatement.view.ListView();
  context.put("listView",bean);
  bean.setModel(getBillManageModel());
  bean.setMultiSelectionEnable(true);
  bean.setNodekey("RS");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.recpaystatement.action.ArapRecStatementQueryAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.arap.recpaystatement.action.ArapRecStatementQueryAction)context.get("queryAction");
  nc.ui.arap.recpaystatement.action.ArapRecStatementQueryAction bean = new nc.ui.arap.recpaystatement.action.ArapRecStatementQueryAction();
  context.put("queryAction",bean);
  bean.setModel(getBillManageModel());
  bean.setListView(getListView());
  bean.setDataManager(getModelDataManager());
  bean.setNodeKey("");
  bean.setTpaProgressUtil(getTPAProgressUtil_1bb9469());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.components.progress.TPAProgressUtil getTPAProgressUtil_1bb9469(){
 if(context.get("nc.ui.uif2.components.progress.TPAProgressUtil#1bb9469")!=null)
 return (nc.ui.uif2.components.progress.TPAProgressUtil)context.get("nc.ui.uif2.components.progress.TPAProgressUtil#1bb9469");
  nc.ui.uif2.components.progress.TPAProgressUtil bean = new nc.ui.uif2.components.progress.TPAProgressUtil();
  context.put("nc.ui.uif2.components.progress.TPAProgressUtil#1bb9469",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
