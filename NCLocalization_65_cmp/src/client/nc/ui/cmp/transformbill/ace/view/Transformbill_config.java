package nc.ui.cmp.transformbill.ace.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class Transformbill_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.tmpub.digit.vo.SrcDestItemCollection getCardSrcDestCollection(){
 if(context.get("cardSrcDestCollection")!=null)
 return (nc.ui.tmpub.digit.vo.SrcDestItemCollection)context.get("cardSrcDestCollection");
  nc.ui.tmpub.digit.vo.SrcDestItemCollection bean = new nc.ui.tmpub.digit.vo.SrcDestItemCollection();
  context.put("cardSrcDestCollection",bean);
  bean.setSrcDestOrigMap(getManagedMap0());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getManagedList0(),getManagedList10());  return map;}

private List getManagedList0(){  List list = new ArrayList();  list.add(getManagedList1());  list.add(getManagedList2());  list.add(getManagedList3());  list.add(getManagedList4());  list.add(getManagedList5());  list.add(getManagedList6());  list.add(getManagedList7());  list.add(getManagedList8());  list.add(getManagedList9());  return list;}

private List getManagedList1(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("HEAD");  return list;}

private List getManagedList2(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList3(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList4(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList5(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList6(){  List list = new ArrayList();  list.add("MONEY");  list.add("amount");  list.add("HEAD");  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("olcrate");  list.add("HEAD");  return list;}

private List getManagedList8(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("glcrate");  list.add("HEAD");  return list;}

private List getManagedList9(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("gllcrate");  list.add("HEAD");  return list;}

private List getManagedList10(){  List list = new ArrayList();  list.add(getManagedList11());  list.add(getManagedList12());  list.add(getManagedList13());  list.add(getManagedList14());  list.add(getManagedList15());  list.add(getManagedList16());  list.add(getManagedList17());  return list;}

private List getManagedList11(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("amount");  list.add("HEAD");  return list;}

private List getManagedList12(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("olcrate");  list.add("HEAD");  return list;}

private List getManagedList13(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("olcamount");  list.add("HEAD");  return list;}

private List getManagedList14(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("glcrate");  list.add("HEAD");  return list;}

private List getManagedList15(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("glcamount");  list.add("HEAD");  return list;}

private List getManagedList16(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("gllcrate");  list.add("HEAD");  return list;}

private List getManagedList17(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("gllcamount");  list.add("HEAD");  return list;}

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

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList18());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList19());  return map;}

private List getManagedList18(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_8ff791());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_8ff791(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#8ff791")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#8ff791");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#8ff791",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList19(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_1c65b22());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_1c65b22(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1c65b22")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1c65b22");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#1c65b22",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getCardEditEventDigitMediator(){
 if(context.get("cardEditEventDigitMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("cardEditEventDigitMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getBillFormEditor());  context.put("cardEditEventDigitMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_189529c());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_189529c(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#189529c")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#189529c");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#189529c",bean);
  bean.setMany2one(getManagedMap2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList20(),getCardEditListener());  return map;}

private List getManagedList20(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_currtype");  list.add("amount");  list.add("billdate");  list.add("olcrate");  list.add("glcrate");  list.add("gllcrate");  return list;}

public nc.ui.tmpub.digit.listener.card.CardPanelEditExListener getCardEditListener(){
 if(context.get("cardEditListener")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelEditExListener)context.get("cardEditListener");
  nc.ui.tmpub.digit.listener.card.CardPanelEditExListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelEditExListener();
  context.put("cardEditListener",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.uif2.LoginContext getContext(){
 if(context.get("context")!=null)
 return (nc.vo.uif2.LoginContext)context.get("context");
  nc.vo.uif2.LoginContext bean = new nc.vo.uif2.LoginContext();
  context.put("context",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.ace.serviceproxy.AceTransformbillMaintainProxy getMaintainProxy(){
 if(context.get("maintainProxy")!=null)
 return (nc.ui.cmp.transformbill.ace.serviceproxy.AceTransformbillMaintainProxy)context.get("maintainProxy");
  nc.ui.cmp.transformbill.ace.serviceproxy.AceTransformbillMaintainProxy bean = new nc.ui.cmp.transformbill.ace.serviceproxy.AceTransformbillMaintainProxy();
  context.put("maintainProxy",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.value.AggVOMetaBDObjectAdapterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.ui.pubapp.uif2app.view.value.AggVOMetaBDObjectAdapterFactory)context.get("boadatorfactory");
  nc.ui.pubapp.uif2app.view.value.AggVOMetaBDObjectAdapterFactory bean = new nc.ui.pubapp.uif2app.view.value.AggVOMetaBDObjectAdapterFactory();
  context.put("boadatorfactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.BillManageModel getManageAppModel(){
 if(context.get("manageAppModel")!=null)
 return (nc.ui.pubapp.uif2app.model.BillManageModel)context.get("manageAppModel");
  nc.ui.pubapp.uif2app.model.BillManageModel bean = new nc.ui.pubapp.uif2app.model.BillManageModel();
  context.put("manageAppModel",bean);
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setContext(getContext());
  bean.setBillType("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.pagination.PaginationModelDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.pubapp.uif2app.model.pagination.PaginationModelDataManager)context.get("modelDataManager");
  nc.ui.pubapp.uif2app.model.pagination.PaginationModelDataManager bean = new nc.ui.pubapp.uif2app.model.pagination.PaginationModelDataManager();
  context.put("modelDataManager",bean);
  bean.setModel(getManageAppModel());
  bean.setPaginationModel(getPaginationModel());
  bean.setPageQueryService(getPageQueryService());
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

public nc.ui.pubapp.uif2app.model.pagination.UIPageQueryService getPageQueryService(){
 if(context.get("pageQueryService")!=null)
 return (nc.ui.pubapp.uif2app.model.pagination.UIPageQueryService)context.get("pageQueryService");
  nc.ui.pubapp.uif2app.model.pagination.UIPageQueryService bean = new nc.ui.pubapp.uif2app.model.pagination.UIPageQueryService();
  context.put("pageQueryService",bean);
  bean.setAllPagePkQueryServiceMethod("nc.itf.cmp.transformbill.ITransformBillQueryService.queryAllPksByQueryScheme");
  bean.setDataOfPksQueryServiceMethod("nc.itf.cmp.transformbill.ITransformBillQueryService.queryBillsByPKS");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationBar getPaginationBar(){
 if(context.get("paginationBar")!=null)
 return (nc.ui.uif2.components.pagination.PaginationBar)context.get("paginationBar");
  nc.ui.uif2.components.pagination.PaginationBar bean = new nc.ui.uif2.components.pagination.PaginationBar();
  context.put("paginationBar",bean);
  bean.setPaginationModel(getPaginationModel());
  bean.setContext(getContext());
  bean.registeCallbak();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.pubapp.uif2app.view.TemplateContainer)context.get("templateContainer");
  nc.ui.pubapp.uif2app.view.TemplateContainer bean = new nc.ui.pubapp.uif2app.view.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
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

public nc.ui.pubapp.uif2app.view.ShowUpableBillListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.pubapp.uif2app.view.ShowUpableBillListView)context.get("listView");
  nc.ui.pubapp.uif2app.view.ShowUpableBillListView bean = new nc.ui.pubapp.uif2app.view.ShowUpableBillListView();
  context.put("listView",bean);
  bean.setPaginationBar(getPaginationBar());
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.initUI();
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
  bean.setParams(getManagedList21());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList21(){  List list = new ArrayList();  list.add(getUserdefQueryParam_8ec12e());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_8ec12e(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#8ec12e")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#8ec12e");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#8ec12e",bean);
  bean.setMdfullname("cmp.cmp_transformbill");
  bean.setPos(0);
  bean.setPrefix("vdef");
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
  bean.setParams(getManagedList22());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList22(){  List list = new ArrayList();  list.add(getUserdefQueryParam_2cbb80());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_2cbb80(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#2cbb80")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#2cbb80");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#2cbb80",bean);
  bean.setMdfullname("cmp.cmp_transformbill");
  bean.setPos(0);
  bean.setPrefix("vdef");
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
  bean.setParams(getManagedList23());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList23(){  List list = new ArrayList();  list.add(getQueryParam_c9d6be());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_c9d6be(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#c9d6be")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#c9d6be");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#c9d6be",bean);
  bean.setMdfullname("cmp.cmp_transformbill");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.ace.view.TransformBillFormEditor getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.cmp.transformbill.ace.view.TransformBillFormEditor)context.get("billFormEditor");
  nc.ui.cmp.transformbill.ace.view.TransformBillFormEditor bean = new nc.ui.cmp.transformbill.ace.view.TransformBillFormEditor();
  context.put("billFormEditor",bean);
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setShowOrgPanel(true);
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
  bean.initUI();
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

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getQueryInfo(){
 if(context.get("queryInfo")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("queryInfo");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("queryInfo",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel getCardInfoPnl(){
 if(context.get("cardInfoPnl")!=null)
 return (nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel)context.get("cardInfoPnl");
  nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel bean = new nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel();
  context.put("cardInfoPnl",bean);
  bean.setTitleAction(getReturnaction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.UEReturnAction getReturnaction(){
 if(context.get("returnaction")!=null)
 return (nc.ui.pubapp.uif2app.actions.UEReturnAction)context.get("returnaction");
  nc.ui.pubapp.uif2app.actions.UEReturnAction bean = new nc.ui.pubapp.uif2app.actions.UEReturnAction();
  context.put("returnaction",bean);
  bean.setGoComponent(getListView());
  bean.setSaveAction(getSaveAction());
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
  bean.setModel(getManageAppModel());
  bean.setTangramLayoutRoot(getTBNode_11f2340());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_11f2340(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#11f2340")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#11f2340");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#11f2340",bean);
  bean.setShowMode("CardLayout");
  bean.setTabs(getManagedList24());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList24(){  List list = new ArrayList();  list.add(getHSNode_1d1d41f());  list.add(getVSNode_18e99fa());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_1d1d41f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#1d1d41f")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#1d1d41f");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#1d1d41f",bean);
  bean.setLeft(getCNode_1317b54());
  bean.setRight(getVSNode_47ebec());
  bean.setDividerLocation(0.22f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1317b54(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1317b54")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1317b54");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1317b54",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_47ebec(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#47ebec")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#47ebec");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#47ebec",bean);
  bean.setUp(getCNode_197f484());
  bean.setDown(getCNode_bc3530());
  bean.setDividerLocation(25f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_197f484(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#197f484")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#197f484");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#197f484",bean);
  bean.setComponent(getQueryInfo());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_bc3530(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#bc3530")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#bc3530");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#bc3530",bean);
  bean.setName(getI18nFB_136405f());
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_136405f(){
 if(context.get("nc.ui.uif2.I18nFB#136405f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#136405f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#136405f",bean);  bean.setResDir("common");
  bean.setDefaultValue("列表");
  bean.setResId("UC001-0000107");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#136405f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_18e99fa(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#18e99fa")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#18e99fa");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#18e99fa",bean);
  bean.setUp(getCNode_bd5ff4());
  bean.setDown(getCNode_1c3958a());
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_bd5ff4(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#bd5ff4")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#bd5ff4");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#bd5ff4",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1c3958a(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1c3958a")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1c3958a");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1c3958a",bean);
  bean.setName(getI18nFB_e6c1e4());
  bean.setComponent(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_e6c1e4(){
 if(context.get("nc.ui.uif2.I18nFB#e6c1e4")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#e6c1e4");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#e6c1e4",bean);  bean.setResDir("common");
  bean.setDefaultValue("卡片");
  bean.setResId("UC001-0000106");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#e6c1e4",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getEventMediator(){
 if(context.get("eventMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("eventMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("eventMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setHandlerGroup(getManagedList25());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList25(){  List list = new ArrayList();  list.add(getEventHandlerGroup_4fc784());  list.add(getEventHandlerGroup_a36f8b());  list.add(getEventHandlerGroup_abf1c1());  list.add(getEventHandlerGroup_1f55a51());  return list;}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_4fc784(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#4fc784")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#4fc784");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#4fc784",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent");
  bean.setHandler(getAceHeadTailBeforeEditHandler_1436f89());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler getAceHeadTailBeforeEditHandler_1436f89(){
 if(context.get("nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler#1436f89")!=null)
 return (nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler)context.get("nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler#1436f89");
  nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler bean = new nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler();
  context.put("nc.ui.cmp.transformbill.ace.handler.AceHeadTailBeforeEditHandler#1436f89",bean);
  bean.setBillFormEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_a36f8b(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a36f8b")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a36f8b");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a36f8b",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent");
  bean.setHandler(getAceHeadTailAfterEditHandler_1b3baa8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler getAceHeadTailAfterEditHandler_1b3baa8(){
 if(context.get("nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler#1b3baa8")!=null)
 return (nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler)context.get("nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler#1b3baa8");
  nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler bean = new nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler();
  context.put("nc.ui.cmp.transformbill.ace.handler.AceHeadTailAfterEditHandler#1b3baa8",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_abf1c1(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#abf1c1")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#abf1c1");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#abf1c1",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.billform.AddEvent");
  bean.setHandler(getAceAddHandler_1f02385());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.ace.handler.AceAddHandler getAceAddHandler_1f02385(){
 if(context.get("nc.ui.cmp.transformbill.ace.handler.AceAddHandler#1f02385")!=null)
 return (nc.ui.cmp.transformbill.ace.handler.AceAddHandler)context.get("nc.ui.cmp.transformbill.ace.handler.AceAddHandler#1f02385");
  nc.ui.cmp.transformbill.ace.handler.AceAddHandler bean = new nc.ui.cmp.transformbill.ace.handler.AceAddHandler(getBillFormEditor());  context.put("nc.ui.cmp.transformbill.ace.handler.AceAddHandler#1f02385",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_1f55a51(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1f55a51")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1f55a51");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1f55a51",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.OrgChangedEvent");
  bean.setHandler(getAceOrgChangedHandler_8cd505());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler getAceOrgChangedHandler_8cd505(){
 if(context.get("nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler#8cd505")!=null)
 return (nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler)context.get("nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler#8cd505");
  nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler bean = new nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler(getBillFormEditor());  context.put("nc.ui.cmp.transformbill.ace.handler.AceOrgChangedHandler#8cd505",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList26());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList26(){  List list = new ArrayList();  list.add(getActionsOfList());  list.add(getActionsOfCard());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getActionsOfList(){
 if(context.get("actionsOfList")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("actionsOfList");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("actionsOfList",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList27());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList27(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitMenuAction());  list.add(getAuditMenuAction());  list.add(getSeparatorAction());  list.add(getTransferMenuAction());  list.add(getSettleMenuAction());  list.add(getPayMenuAction());  list.add(getEupayMenuAction());  list.add(getMakeBillAction());  list.add(getRedHandleAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getLinkMenuAction());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintMenuAction());  list.add(getSeparatorAction());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getActionsOfCard(){
 if(context.get("actionsOfCard")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("actionsOfCard");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getBillFormEditor());  context.put("actionsOfCard",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList28());
  bean.setEditActions(getManagedList29());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList28(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getCardRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitMenuAction());  list.add(getAuditMenuAction());  list.add(getSeparatorAction());  list.add(getTransferMenuAction());  list.add(getSettleMenuAction());  list.add(getPayMenuAction());  list.add(getEupayMenuAction());  list.add(getMakeBillAction());  list.add(getRedHandleAction());  list.add(getSeparatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeparatorAction());  list.add(getBillAssistantActionGroup());  list.add(getSeparatorAction());  list.add(getLinkMenuAction());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintMenuAction());  return list;}

private List getManagedList29(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSaveAddAction());  list.add(getSaveSendApproveAction());  list.add(getSeparatorAction());  list.add(getCancelAction());  return list;}

public nc.funcnode.ui.action.SeparatorAction getSeparatorAction(){
 if(context.get("separatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("separatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("separatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowListInterceptor(){
 if(context.get("showListInterceptor")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("showListInterceptor");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("showListInterceptor",bean);
  bean.setShowUpComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowCardInterceptor(){
 if(context.get("showCardInterceptor")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("showCardInterceptor");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("showCardInterceptor",bean);
  bean.setShowUpComponent(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.AddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.AddAction)context.get("addAction");
  nc.ui.pubapp.uif2app.actions.AddAction bean = new nc.ui.pubapp.uif2app.actions.AddAction();
  context.put("addAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getShowCardInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillEditAction)context.get("editAction");
  nc.ui.cmp.transformbill.action.TransformbillEditAction bean = new nc.ui.cmp.transformbill.action.TransformbillEditAction();
  context.put("editAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPowercheck(true);
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S4");
  bean.setBillCodeName("vbillno");
  bean.setInterceptor(getShowCardInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillDeleteAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillDeleteAction)context.get("deleteAction");
  nc.ui.cmp.transformbill.action.TransformbillDeleteAction bean = new nc.ui.cmp.transformbill.action.TransformbillDeleteAction();
  context.put("deleteAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setActionName("DELETE");
  bean.setPowercheck(true);
  bean.setBillType("36S4");
  bean.setBillCodeName("vbillno");
  bean.setValidationService(getTransformBillDeleteValidateService_fc9dab());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService getTransformBillDeleteValidateService_fc9dab(){
 if(context.get("nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService#fc9dab")!=null)
 return (nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService)context.get("nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService#fc9dab");
  nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService bean = new nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService();
  context.put("nc.ui.cmp.transformbill.validate.TransformBillDeleteValidateService#fc9dab",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillSaveAction)context.get("saveAction");
  nc.ui.cmp.transformbill.action.TransformbillSaveAction bean = new nc.ui.cmp.transformbill.action.TransformbillSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setActionName("SAVEBASE");
  bean.setBillType("36S4");
  bean.setValidationService(getValidateService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillSaveAdd getSaveAddAction(){
 if(context.get("saveAddAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillSaveAdd)context.get("saveAddAction");
  nc.ui.cmp.transformbill.action.TransformbillSaveAdd bean = new nc.ui.cmp.transformbill.action.TransformbillSaveAdd();
  context.put("saveAddAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setActionName("SAVEBASE");
  bean.setBillType("36S4");
  bean.setValidationService(getValidateService());
  bean.setAddAction(getAddAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillSaveAndCommitAction getSaveSendApproveAction(){
 if(context.get("saveSendApproveAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillSaveAndCommitAction)context.get("saveSendApproveAction");
  nc.ui.cmp.transformbill.action.TransformbillSaveAndCommitAction bean = new nc.ui.cmp.transformbill.action.TransformbillSaveAndCommitAction(getSaveAction(),getCommitAction());  context.put("saveSendApproveAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.validation.CompositeValidation getValidateService(){
 if(context.get("validateService")!=null)
 return (nc.ui.pubapp.uif2app.validation.CompositeValidation)context.get("validateService");
  nc.ui.pubapp.uif2app.validation.CompositeValidation bean = new nc.ui.pubapp.uif2app.validation.CompositeValidation();
  context.put("validateService",bean);
  bean.setValidators(getManagedList30());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList30(){  List list = new ArrayList();  list.add(getTemplateNotNullValidation_d3701d());  list.add(getPowerSaveValidateService_165a43e());  return list;}

private nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation getTemplateNotNullValidation_d3701d(){
 if(context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d3701d")!=null)
 return (nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation)context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d3701d");
  nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation bean = new nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation();
  context.put("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d3701d",bean);
  bean.setBillForm(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerSaveValidateService getPowerSaveValidateService_165a43e(){
 if(context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#165a43e")!=null)
 return (nc.ui.pubapp.pub.power.PowerSaveValidateService)context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#165a43e");
  nc.ui.pubapp.pub.power.PowerSaveValidateService bean = new nc.ui.pubapp.pub.power.PowerSaveValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerSaveValidateService#165a43e",bean);
  bean.setInsertActionCode("save");
  bean.setEditActionCode("edit");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.CancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.CancelAction)context.get("cancelAction");
  nc.ui.pubapp.uif2app.actions.CancelAction bean = new nc.ui.pubapp.uif2app.actions.CancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillCopyAction)context.get("copyAction");
  nc.ui.cmp.transformbill.action.TransformbillCopyAction bean = new nc.ui.cmp.transformbill.action.TransformbillCopyAction();
  context.put("copyAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getShowCardInterceptor());
  bean.setEditor(getBillFormEditor());
  bean.setCopyActionProcessor(getCopyActionProcessor_2156d());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.transformbill.action.CopyActionProcessor getCopyActionProcessor_2156d(){
 if(context.get("nc.ui.cmp.transformbill.action.CopyActionProcessor#2156d")!=null)
 return (nc.ui.cmp.transformbill.action.CopyActionProcessor)context.get("nc.ui.cmp.transformbill.action.CopyActionProcessor#2156d");
  nc.ui.cmp.transformbill.action.CopyActionProcessor bean = new nc.ui.cmp.transformbill.action.CopyActionProcessor();
  context.put("nc.ui.cmp.transformbill.action.CopyActionProcessor#2156d",bean);
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
  bean.setName(getI18nFB_ccba99());
  bean.setActions(getManagedList31());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_ccba99(){
 if(context.get("nc.ui.uif2.I18nFB#ccba99")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#ccba99");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#ccba99",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("影像");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#ccba99",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList31(){  List list = new ArrayList();  list.add(getImageShowAction());  list.add(getImageScanAction());  return list;}

public nc.ui.cmp.bill.actions.CmpBillImageShowAction getImageShowAction(){
 if(context.get("imageShowAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillImageShowAction)context.get("imageShowAction");
  nc.ui.cmp.bill.actions.CmpBillImageShowAction bean = new nc.ui.cmp.bill.actions.CmpBillImageShowAction();
  context.put("imageShowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPk_billtype("0001Z61000000000Z9AN");
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
  bean.setPk_billtype("0001Z61000000000Z9AN");
  bean.setCheckscanway("nc.imag.scan.service.CheckScanWay");
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
  bean.setName(getI18nFB_eebd46());
  bean.setActions(getManagedList32());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_eebd46(){
 if(context.get("nc.ui.uif2.I18nFB#eebd46")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#eebd46");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#eebd46",bean);  bean.setResDir("common");
  bean.setDefaultValue("辅助功能");
  bean.setResId("UC001-0000137");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#eebd46",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList32(){  List list = new ArrayList();  list.add(getFileDocManageAction());  return list;}

public nc.ui.pubapp.uif2app.actions.FileDocManageAction getFileDocManageAction(){
 if(context.get("fileDocManageAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.FileDocManageAction)context.get("fileDocManageAction");
  nc.ui.pubapp.uif2app.actions.FileDocManageAction bean = new nc.ui.pubapp.uif2app.actions.FileDocManageAction();
  context.put("fileDocManageAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction)context.get("queryAction");
  nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction bean = new nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction();
  context.put("queryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setShowUpComponent(getListView());
  bean.setTemplateContainer(getQueryTemplateContainer());
  bean.setQryCondDLGInitializer(getQueryCondDLGInitializer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.query.TransformbillQueryConditionInitializer getQueryCondDLGInitializer(){
 if(context.get("queryCondDLGInitializer")!=null)
 return (nc.ui.cmp.transformbill.query.TransformbillQueryConditionInitializer)context.get("queryCondDLGInitializer");
  nc.ui.cmp.transformbill.query.TransformbillQueryConditionInitializer bean = new nc.ui.cmp.transformbill.query.TransformbillQueryConditionInitializer();
  context.put("queryCondDLGInitializer",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction)context.get("refreshAction");
  nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction bean = new nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction();
  context.put("refreshAction",bean);
  bean.setDataManager(getModelDataManager());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.RefreshSingleAction getCardRefreshAction(){
 if(context.get("cardRefreshAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.RefreshSingleAction)context.get("cardRefreshAction");
  nc.ui.pubapp.uif2app.actions.RefreshSingleAction bean = new nc.ui.pubapp.uif2app.actions.RefreshSingleAction();
  context.put("cardRefreshAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillCommitAction getCommitAction(){
 if(context.get("commitAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillCommitAction)context.get("commitAction");
  nc.ui.cmp.transformbill.action.TransformbillCommitAction bean = new nc.ui.cmp.transformbill.action.TransformbillCommitAction();
  context.put("commitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S4");
  bean.setActionName("SAVE");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_1cc7fc4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_1cc7fc4(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#1cc7fc4")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#1cc7fc4");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#1cc7fc4",bean);
  bean.setActionCode("commit");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillUnComitAction getUnCommitAction(){
 if(context.get("unCommitAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillUnComitAction)context.get("unCommitAction");
  nc.ui.cmp.transformbill.action.TransformbillUnComitAction bean = new nc.ui.cmp.transformbill.action.TransformbillUnComitAction();
  context.put("unCommitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S4");
  bean.setActionName("UNSAVEBILL");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_d85426());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_d85426(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#d85426")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#d85426");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#d85426",bean);
  bean.setActionCode("uncommit");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getCommitMenuAction(){
 if(context.get("commitMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("commitMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("commitMenuAction",bean);
  bean.setCode("commitMenuAction");
  bean.setName(getI18nFB_7cab05());
  bean.setActions(getManagedList33());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_7cab05(){
 if(context.get("nc.ui.uif2.I18nFB#7cab05")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#7cab05");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#7cab05",bean);  bean.setResDir("common");
  bean.setDefaultValue("提交");
  bean.setResId("UC001-0000029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#7cab05",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList33(){  List list = new ArrayList();  list.add(getCommitAction());  list.add(getUnCommitAction());  return list;}

public nc.ui.pubapp.pub.power.PowerValidateService getApprovepowervalidservice(){
 if(context.get("approvepowervalidservice")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("approvepowervalidservice");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("approvepowervalidservice",bean);
  bean.setActionCode("approve");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.pub.power.PowerValidateService getUnapprovepowervalidservice(){
 if(context.get("unapprovepowervalidservice")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("unapprovepowervalidservice");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("unapprovepowervalidservice",bean);
  bean.setActionCode("unapprove");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillApproveAction getApproveAction(){
 if(context.get("approveAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillApproveAction)context.get("approveAction");
  nc.ui.cmp.transformbill.action.TransformbillApproveAction bean = new nc.ui.cmp.transformbill.action.TransformbillApproveAction();
  context.put("approveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S4");
  bean.setActionName("APPROVE");
  bean.setValidationService(getApprovepowervalidservice());
  bean.setFilledUpInFlow(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillUnApproveAction getUnApproveAction(){
 if(context.get("unApproveAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillUnApproveAction)context.get("unApproveAction");
  nc.ui.cmp.transformbill.action.TransformbillUnApproveAction bean = new nc.ui.cmp.transformbill.action.TransformbillUnApproveAction();
  context.put("unApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S4");
  bean.setActionName("UNAPPROVE");
  bean.setValidationService(getUnapprovepowervalidservice());
  bean.setFilledUpInFlow(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getAuditMenuAction(){
 if(context.get("auditMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("auditMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("auditMenuAction",bean);
  bean.setCode("auditMenuAction");
  bean.setName(getI18nFB_c00ce7());
  bean.setActions(getManagedList34());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c00ce7(){
 if(context.get("nc.ui.uif2.I18nFB#c00ce7")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c00ce7");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c00ce7",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607mng1-0019");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c00ce7",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList34(){  List list = new ArrayList();  list.add(getApproveAction());  list.add(getUnApproveAction());  list.add(getQueryAuditFlowAction());  return list;}

public nc.ui.cmp.transformbill.action.TransformbillTransferAction getTransferAction(){
 if(context.get("transferAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillTransferAction)context.get("transferAction");
  nc.ui.cmp.transformbill.action.TransformbillTransferAction bean = new nc.ui.cmp.transformbill.action.TransformbillTransferAction();
  context.put("transferAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("TRANSFERFTS");
  bean.setCaSigner(getClientSigner());
  bean.setBtnName(getI18nFB_8f4978());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_8f4978(){
 if(context.get("nc.ui.uif2.I18nFB#8f4978")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#8f4978");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#8f4978",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607mng1-0023");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#8f4978",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillUnTransferAction getUnTransferAction(){
 if(context.get("unTransferAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillUnTransferAction)context.get("unTransferAction");
  nc.ui.cmp.transformbill.action.TransformbillUnTransferAction bean = new nc.ui.cmp.transformbill.action.TransformbillUnTransferAction();
  context.put("unTransferAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("UNTRANSFERFTS");
  bean.setBtnName(getI18nFB_120803d());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_120803d(){
 if(context.get("nc.ui.uif2.I18nFB#120803d")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#120803d");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#120803d",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("取消委托");
  bean.setResId("03607mng1-0024");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#120803d",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.GroupAction getTransferMenuAction(){
 if(context.get("transferMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("transferMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("transferMenuAction",bean);
  bean.setCode("transferMenuAction");
  bean.setName(getI18nFB_c33ae9());
  bean.setActions(getManagedList35());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c33ae9(){
 if(context.get("nc.ui.uif2.I18nFB#c33ae9")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c33ae9");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c33ae9",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607mng1-0023");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c33ae9",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList35(){  List list = new ArrayList();  list.add(getTransferAction());  list.add(getUnTransferAction());  return list;}

public nc.ui.cmp.transformbill.action.TransformbillSettleAction getSettleAction(){
 if(context.get("settleAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillSettleAction)context.get("settleAction");
  nc.ui.cmp.transformbill.action.TransformbillSettleAction bean = new nc.ui.cmp.transformbill.action.TransformbillSettleAction();
  context.put("settleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("SETTLE");
  bean.setBtnName(getI18nFB_150c9a6());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_150c9a6(){
 if(context.get("nc.ui.uif2.I18nFB#150c9a6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#150c9a6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#150c9a6",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607mng1-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#150c9a6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillUnSettleAction getUnSettleAction(){
 if(context.get("unSettleAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillUnSettleAction)context.get("unSettleAction");
  nc.ui.cmp.transformbill.action.TransformbillUnSettleAction bean = new nc.ui.cmp.transformbill.action.TransformbillUnSettleAction();
  context.put("unSettleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("UNSETTLE");
  bean.setBtnName(getI18nFB_e6f297());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_e6f297(){
 if(context.get("nc.ui.uif2.I18nFB#e6f297")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#e6f297");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#e6f297",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("取消结算");
  bean.setResId("03607mng1-0026");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#e6f297",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.GroupAction getSettleMenuAction(){
 if(context.get("settleMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("settleMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("settleMenuAction",bean);
  bean.setCode("settleMenuAction");
  bean.setName(getI18nFB_40a02c());
  bean.setActions(getManagedList36());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_40a02c(){
 if(context.get("nc.ui.uif2.I18nFB#40a02c")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#40a02c");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#40a02c",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607mng1-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#40a02c",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList36(){  List list = new ArrayList();  list.add(getSettleAction());  list.add(getUnSettleAction());  return list;}

public nc.ui.cmp.transformbill.action.TransformbillSettleSuccessAction getSettleSuccessAction(){
 if(context.get("settleSuccessAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillSettleSuccessAction)context.get("settleSuccessAction");
  nc.ui.cmp.transformbill.action.TransformbillSettleSuccessAction bean = new nc.ui.cmp.transformbill.action.TransformbillSettleSuccessAction();
  context.put("settleSuccessAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("SETTLESUCCESS");
  bean.setBtnName(getI18nFB_d3be5f());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d3be5f(){
 if(context.get("nc.ui.uif2.I18nFB#d3be5f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d3be5f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d3be5f",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("结算成功");
  bean.setResId("03607mng1-0045");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d3be5f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillUnSettleSuccessAction getUnSettleSuccessAction(){
 if(context.get("unSettleSuccessAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillUnSettleSuccessAction)context.get("unSettleSuccessAction");
  nc.ui.cmp.transformbill.action.TransformbillUnSettleSuccessAction bean = new nc.ui.cmp.transformbill.action.TransformbillUnSettleSuccessAction();
  context.put("unSettleSuccessAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("UNSETTLESUCCESS");
  bean.setBtnName(getI18nFB_1778cf2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1778cf2(){
 if(context.get("nc.ui.uif2.I18nFB#1778cf2")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1778cf2");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1778cf2",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("取消结算成功");
  bean.setResId("03607mng1-0046");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1778cf2",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.view.TransformbillPayConfirmUI getOutPayConfirmUI(){
 if(context.get("outPayConfirmUI")!=null)
 return (nc.ui.cmp.transformbill.view.TransformbillPayConfirmUI)context.get("outPayConfirmUI");
  nc.ui.cmp.transformbill.view.TransformbillPayConfirmUI bean = new nc.ui.cmp.transformbill.view.TransformbillPayConfirmUI();
  context.put("outPayConfirmUI",bean);
  bean.setBillForm(getBillFormEditor());
  bean.setBillModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.security.DefCommonClientSign getClientSigner(){
 if(context.get("clientSigner")!=null)
 return (nc.ui.tmpub.security.DefCommonClientSign)context.get("clientSigner");
  nc.ui.tmpub.security.DefCommonClientSign bean = new nc.ui.tmpub.security.DefCommonClientSign(getContext());  context.put("clientSigner",bean);
  bean.setConstructClassName("nc.vo.sf.delivery.DeliveryEncryptVO");
  bean.setSignAttributeNameVO(getSignAttributeNameVO());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.tmpub.security.SignAttributeNameVO getSignAttributeNameVO(){
 if(context.get("signAttributeNameVO")!=null)
 return (nc.vo.tmpub.security.SignAttributeNameVO)context.get("signAttributeNameVO");
  nc.vo.tmpub.security.SignAttributeNameVO bean = new nc.vo.tmpub.security.SignAttributeNameVO();
  context.put("signAttributeNameVO",bean);
  bean.setParentAttributeName("encryptkey");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillPayAction getPayAction(){
 if(context.get("payAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillPayAction)context.get("payAction");
  nc.ui.cmp.transformbill.action.TransformbillPayAction bean = new nc.ui.cmp.transformbill.action.TransformbillPayAction();
  context.put("payAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setPayConfirmUI(getOutPayConfirmUI());
  bean.setCode("PAY");
  bean.setCaSigner(getClientSigner());
  bean.setBtnName(getI18nFB_1c66ee0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1c66ee0(){
 if(context.get("nc.ui.uif2.I18nFB#1c66ee0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1c66ee0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1c66ee0",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("网上付款");
  bean.setResId("03607mng1-0047");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1c66ee0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillNetBankEditAction getNetBankEditAction(){
 if(context.get("netBankEditAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillNetBankEditAction)context.get("netBankEditAction");
  nc.ui.cmp.transformbill.action.TransformbillNetBankEditAction bean = new nc.ui.cmp.transformbill.action.TransformbillNetBankEditAction();
  context.put("netBankEditAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setPayConfirmUI(getOutPayConfirmUI());
  bean.setCode("NETBANKEDIT");
  bean.setBtnName(getI18nFB_996332());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_996332(){
 if(context.get("nc.ui.uif2.I18nFB#996332")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#996332");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#996332",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("补录网银信息");
  bean.setResId("03607mng1-0048");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#996332",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillNetBankUpdateAction getNetBankUpdateAction(){
 if(context.get("netBankUpdateAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillNetBankUpdateAction)context.get("netBankUpdateAction");
  nc.ui.cmp.transformbill.action.TransformbillNetBankUpdateAction bean = new nc.ui.cmp.transformbill.action.TransformbillNetBankUpdateAction();
  context.put("netBankUpdateAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setPayConfirmUI(getOutPayConfirmUI());
  bean.setCode("UPDATENETBANK");
  bean.setBtnName(getI18nFB_3f88c0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_3f88c0(){
 if(context.get("nc.ui.uif2.I18nFB#3f88c0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#3f88c0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#3f88c0",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("更新网银支付状态");
  bean.setResId("03607mng1-0049");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#3f88c0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.MenuAction getPayMenuAction(){
 if(context.get("payMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("payMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("payMenuAction",bean);
  bean.setCode("payMenuAction");
  bean.setName(getI18nFB_12b00a9());
  bean.setActions(getManagedList37());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_12b00a9(){
 if(context.get("nc.ui.uif2.I18nFB#12b00a9")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#12b00a9");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#12b00a9",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("支付");
  bean.setResId("03607mng1-0050");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#12b00a9",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList37(){  List list = new ArrayList();  list.add(getPayAction());  list.add(getNetBankEditAction());  list.add(getNetBankUpdateAction());  return list;}

public nc.funcnode.ui.action.MenuAction getEupayMenuAction(){
 if(context.get("eupayMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("eupayMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("eupayMenuAction",bean);
  bean.setCode("eupayMenuAction");
  bean.setName(getI18nFB_386a4f());
  bean.setActions(getManagedList38());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_386a4f(){
 if(context.get("nc.ui.uif2.I18nFB#386a4f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#386a4f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#386a4f",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("欧盟支付");
  bean.setResId("03607mng1-0257");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#386a4f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList38(){  List list = new ArrayList();  list.add(getSettleSuccessAction());  list.add(getUnSettleSuccessAction());  list.add(getExportEuropeAction());  return list;}

public nc.ui.cmp.transformbill.action.ExportEuropeAction getExportEuropeAction(){
 if(context.get("exportEuropeAction")!=null)
 return (nc.ui.cmp.transformbill.action.ExportEuropeAction)context.get("exportEuropeAction");
  nc.ui.cmp.transformbill.action.ExportEuropeAction bean = new nc.ui.cmp.transformbill.action.ExportEuropeAction();
  context.put("exportEuropeAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillMakeBillAction getMakeBillAction(){
 if(context.get("makeBillAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillMakeBillAction)context.get("makeBillAction");
  nc.ui.cmp.transformbill.action.TransformbillMakeBillAction bean = new nc.ui.cmp.transformbill.action.TransformbillMakeBillAction();
  context.put("makeBillAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformBillRedHandleAction getRedHandleAction(){
 if(context.get("redHandleAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformBillRedHandleAction)context.get("redHandleAction");
  nc.ui.cmp.transformbill.action.TransformBillRedHandleAction bean = new nc.ui.cmp.transformbill.action.TransformBillRedHandleAction();
  context.put("redHandleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S4");
  bean.setCode("REDHANDLE");
  bean.setCaSigner(getClientSigner());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction getPrintAction(){
 if(context.get("printAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction)context.get("printAction");
  nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction bean = new nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction();
  context.put("printAction",bean);
  bean.setPreview(false);
  bean.setModel(getManageAppModel());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpDefaultPrintProcessor getPrintProcessor(){
 if(context.get("printProcessor")!=null)
 return (nc.ui.cmp.base.actions.CmpDefaultPrintProcessor)context.get("printProcessor");
  nc.ui.cmp.base.actions.CmpDefaultPrintProcessor bean = new nc.ui.cmp.base.actions.CmpDefaultPrintProcessor();
  context.put("printProcessor",bean);
  bean.setModel(getManageAppModel());
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction getPreviewAction(){
 if(context.get("previewAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction)context.get("previewAction");
  nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction bean = new nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction();
  context.put("previewAction",bean);
  bean.setPreview(true);
  bean.setModel(getManageAppModel());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.OutputAction getOutputAction(){
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

public nc.funcnode.ui.action.GroupAction getPrintMenuAction(){
 if(context.get("printMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printMenuAction",bean);
  bean.setCode("printMenuAction");
  bean.setName(getI18nFB_1cb7e30());
  bean.setActions(getManagedList39());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1cb7e30(){
 if(context.get("nc.ui.uif2.I18nFB#1cb7e30")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1cb7e30");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1cb7e30",bean);  bean.setResDir("common");
  bean.setDefaultValue("打印");
  bean.setResId("UC001-0000007");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1cb7e30",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList39(){  List list = new ArrayList();  list.add(getPrintAction());  list.add(getPreviewAction());  list.add(getOutputAction());  return list;}

public nc.funcnode.ui.action.MenuAction getImportExportActionGroup(){
 if(context.get("importExportActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("importExportActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("importExportActionGroup",bean);
  bean.setCode("ImportExport");
  bean.setName(getI18nFB_c9b16b());
  bean.setActions(getManagedList40());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c9b16b(){
 if(context.get("nc.ui.uif2.I18nFB#c9b16b")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c9b16b");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c9b16b",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607mng1-0016");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c9b16b",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList40(){  List list = new ArrayList();  list.add(getExcelImportAction());  list.add(getSeparatorAction());  list.add(getExcelTemplateExportAction());  return list;}

public nc.ui.uif2.excelimport.ImportAction getExcelImportAction(){
 if(context.get("excelImportAction")!=null)
 return (nc.ui.uif2.excelimport.ImportAction)context.get("excelImportAction");
  nc.ui.uif2.excelimport.ImportAction bean = new nc.ui.uif2.excelimport.ImportAction();
  context.put("excelImportAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getSupplierPriceImportableEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.excelimport.ExportExcelTemplateAction getExcelTemplateExportAction(){
 if(context.get("excelTemplateExportAction")!=null)
 return (nc.ui.uif2.excelimport.ExportExcelTemplateAction)context.get("excelTemplateExportAction");
  nc.ui.uif2.excelimport.ExportExcelTemplateAction bean = new nc.ui.uif2.excelimport.ExportExcelTemplateAction();
  context.put("excelTemplateExportAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getSupplierPriceImportableEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.excel.TfbImportableEditor getSupplierPriceImportableEditor(){
 if(context.get("supplierPriceImportableEditor")!=null)
 return (nc.ui.cmp.transformbill.excel.TfbImportableEditor)context.get("supplierPriceImportableEditor");
  nc.ui.cmp.transformbill.excel.TfbImportableEditor bean = new nc.ui.cmp.transformbill.excel.TfbImportableEditor();
  context.put("supplierPriceImportableEditor",bean);
  bean.setAppModel(getManageAppModel());
  bean.setBillcardPanelEditor(getBillFormEditor());
  bean.setAddAction(getAddAction());
  bean.setSaveAction(getSaveAction());
  bean.setCancelAction(getCancelAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.LinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.LinkQueryAction)context.get("linkQueryAction");
  nc.ui.pubapp.uif2app.actions.LinkQueryAction bean = new nc.ui.pubapp.uif2app.actions.LinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType("36S4");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.pf.PFApproveInfoAction getQueryAuditFlowAction(){
 if(context.get("queryAuditFlowAction")!=null)
 return (nc.ui.uif2.actions.pf.PFApproveInfoAction)context.get("queryAuditFlowAction");
  nc.ui.uif2.actions.pf.PFApproveInfoAction bean = new nc.ui.uif2.actions.pf.PFApproveInfoAction();
  context.put("queryAuditFlowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBtnName(getI18nFB_19f165d());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_19f165d(){
 if(context.get("nc.ui.uif2.I18nFB#19f165d")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#19f165d");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#19f165d",bean);  bean.setResDir("3607set1_0");
  bean.setDefaultValue("查看审批意见");
  bean.setResId("03607set1-0066");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#19f165d",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.transformbill.action.TransformbillVoucherQueryAction getLinkVoucherAction(){
 if(context.get("linkVoucherAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillVoucherQueryAction)context.get("linkVoucherAction");
  nc.ui.cmp.transformbill.action.TransformbillVoucherQueryAction bean = new nc.ui.cmp.transformbill.action.TransformbillVoucherQueryAction();
  context.put("linkVoucherAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillNetBankQueryAction getLinkNetBankAction(){
 if(context.get("linkNetBankAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillNetBankQueryAction)context.get("linkNetBankAction");
  nc.ui.cmp.transformbill.action.TransformbillNetBankQueryAction bean = new nc.ui.cmp.transformbill.action.TransformbillNetBankQueryAction();
  context.put("linkNetBankAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setPayConfirmUI(getOutPayConfirmUI());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillPayConfirmQueryAction getLinkPayConfirmAction(){
 if(context.get("linkPayConfirmAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillPayConfirmQueryAction)context.get("linkPayConfirmAction");
  nc.ui.cmp.transformbill.action.TransformbillPayConfirmQueryAction bean = new nc.ui.cmp.transformbill.action.TransformbillPayConfirmQueryAction();
  context.put("linkPayConfirmAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillAccBalOutQueryAction getLinkAccBalOutAction(){
 if(context.get("linkAccBalOutAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillAccBalOutQueryAction)context.get("linkAccBalOutAction");
  nc.ui.cmp.transformbill.action.TransformbillAccBalOutQueryAction bean = new nc.ui.cmp.transformbill.action.TransformbillAccBalOutQueryAction();
  context.put("linkAccBalOutAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.transformbill.action.TransformbillAccBalInQueryAction getLinkAccBalInAction(){
 if(context.get("linkAccBalInAction")!=null)
 return (nc.ui.cmp.transformbill.action.TransformbillAccBalInQueryAction)context.get("linkAccBalInAction");
  nc.ui.cmp.transformbill.action.TransformbillAccBalInQueryAction bean = new nc.ui.cmp.transformbill.action.TransformbillAccBalInQueryAction();
  context.put("linkAccBalInAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getLinkMenuAction(){
 if(context.get("linkMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("linkMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("linkMenuAction",bean);
  bean.setCode("linkQuery");
  bean.setName(getI18nFB_1756ceb());
  bean.setActions(getManagedList41());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1756ceb(){
 if(context.get("nc.ui.uif2.I18nFB#1756ceb")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1756ceb");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1756ceb",bean);  bean.setResDir("common");
  bean.setDefaultValue("联查");
  bean.setResId("UC001-0000146");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1756ceb",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList41(){  List list = new ArrayList();  list.add(getLinkVoucherAction());  list.add(getLinkAccBalInAction());  list.add(getLinkAccBalOutAction());  list.add(getLinkQueryAction());  list.add(getLinkNetBankAction());  list.add(getLinkPayConfirmAction());  return list;}

public nc.ui.cmp.transformbill.listener.TransformbillFuncNodeInitDataListener getInitDataListener(){
 if(context.get("initDataListener")!=null)
 return (nc.ui.cmp.transformbill.listener.TransformbillFuncNodeInitDataListener)context.get("initDataListener");
  nc.ui.cmp.transformbill.listener.TransformbillFuncNodeInitDataListener bean = new nc.ui.cmp.transformbill.listener.TransformbillFuncNodeInitDataListener();
  context.put("initDataListener",bean);
  bean.setBillFormEditor(getBillFormEditor());
  bean.setListview(getListView());
  bean.setQueryAction(getQueryAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.common.validateservice.ClosingCheck getClosingListener(){
 if(context.get("closingListener")!=null)
 return (nc.ui.pubapp.common.validateservice.ClosingCheck)context.get("closingListener");
  nc.ui.pubapp.common.validateservice.ClosingCheck bean = new nc.ui.pubapp.common.validateservice.ClosingCheck();
  context.put("closingListener",bean);
  bean.setModel(getManageAppModel());
  bean.setSaveAction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.FractionFixMediator getFractionFixMediator(){
 if(context.get("fractionFixMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.FractionFixMediator)context.get("fractionFixMediator");
  nc.ui.pubapp.uif2app.view.FractionFixMediator bean = new nc.ui.pubapp.uif2app.view.FractionFixMediator(getBillFormEditor());  context.put("fractionFixMediator",bean);
  bean.initUI();
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
  bean.setHyperLinkColumn("vbillno");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller getRemoteCallCombinatorCaller(){
 if(context.get("remoteCallCombinatorCaller")!=null)
 return (nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller)context.get("remoteCallCombinatorCaller");
  nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller bean = new nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller();
  context.put("remoteCallCombinatorCaller",bean);
  bean.setRemoteCallers(getManagedList42());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList42(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

}
