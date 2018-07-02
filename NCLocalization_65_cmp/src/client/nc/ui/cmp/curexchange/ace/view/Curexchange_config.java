package nc.ui.cmp.curexchange.ace.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class Curexchange_config extends AbstractJavaBeanDefinition{
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

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getManagedList0(),getManagedList10());  map.put(getManagedList18(),getManagedList28());  map.put(getManagedList36(),getManagedList46());  map.put(getManagedList54(),getManagedList60());  return map;}

private List getManagedList0(){  List list = new ArrayList();  list.add(getManagedList1());  list.add(getManagedList2());  list.add(getManagedList3());  list.add(getManagedList4());  list.add(getManagedList5());  list.add(getManagedList6());  list.add(getManagedList7());  list.add(getManagedList8());  list.add(getManagedList9());  return list;}

private List getManagedList1(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList2(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList3(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList4(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_buycurrtype");  list.add("HEAD");  return list;}

private List getManagedList5(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList6(){  List list = new ArrayList();  list.add("MONEY");  list.add("buyamount");  list.add("HEAD");  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("buyolcrate");  list.add("HEAD");  return list;}

private List getManagedList8(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("buyglcrate");  list.add("HEAD");  return list;}

private List getManagedList9(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("buygllcrate");  list.add("HEAD");  return list;}

private List getManagedList10(){  List list = new ArrayList();  list.add(getManagedList11());  list.add(getManagedList12());  list.add(getManagedList13());  list.add(getManagedList14());  list.add(getManagedList15());  list.add(getManagedList16());  list.add(getManagedList17());  return list;}

private List getManagedList11(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("buyamount");  list.add("HEAD");  return list;}

private List getManagedList12(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("buyolcrate");  list.add("HEAD");  return list;}

private List getManagedList13(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("buyolcamount");  list.add("HEAD");  return list;}

private List getManagedList14(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("buyglcrate");  list.add("HEAD");  return list;}

private List getManagedList15(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("buyglcamount");  list.add("HEAD");  return list;}

private List getManagedList16(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("buygllcrate");  list.add("HEAD");  return list;}

private List getManagedList17(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("buygllcamount");  list.add("HEAD");  return list;}

private List getManagedList18(){  List list = new ArrayList();  list.add(getManagedList19());  list.add(getManagedList20());  list.add(getManagedList21());  list.add(getManagedList22());  list.add(getManagedList23());  list.add(getManagedList24());  list.add(getManagedList25());  list.add(getManagedList26());  list.add(getManagedList27());  return list;}

private List getManagedList19(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList20(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList21(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList22(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_chargecurrtype");  list.add("HEAD");  return list;}

private List getManagedList23(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList24(){  List list = new ArrayList();  list.add("MONEY");  list.add("chargeamount");  list.add("HEAD");  return list;}

private List getManagedList25(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("chargeolcrate");  list.add("HEAD");  return list;}

private List getManagedList26(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("chargeglcrate");  list.add("HEAD");  return list;}

private List getManagedList27(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("chargegllcrate");  list.add("HEAD");  return list;}

private List getManagedList28(){  List list = new ArrayList();  list.add(getManagedList29());  list.add(getManagedList30());  list.add(getManagedList31());  list.add(getManagedList32());  list.add(getManagedList33());  list.add(getManagedList34());  list.add(getManagedList35());  return list;}

private List getManagedList29(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("chargeamount");  list.add("HEAD");  return list;}

private List getManagedList30(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("chargeolcrate");  list.add("HEAD");  return list;}

private List getManagedList31(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("chargeolcamount");  list.add("HEAD");  return list;}

private List getManagedList32(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("chargeglcrate");  list.add("HEAD");  return list;}

private List getManagedList33(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("chargeglcamount");  list.add("HEAD");  return list;}

private List getManagedList34(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("chargegllcrate");  list.add("HEAD");  return list;}

private List getManagedList35(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("chargegllcamount");  list.add("HEAD");  return list;}

private List getManagedList36(){  List list = new ArrayList();  list.add(getManagedList37());  list.add(getManagedList38());  list.add(getManagedList39());  list.add(getManagedList40());  list.add(getManagedList41());  list.add(getManagedList42());  list.add(getManagedList43());  list.add(getManagedList44());  list.add(getManagedList45());  return list;}

private List getManagedList37(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList38(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList39(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList40(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_sellcurrtype");  list.add("HEAD");  return list;}

private List getManagedList41(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList42(){  List list = new ArrayList();  list.add("MONEY");  list.add("sellamount");  list.add("HEAD");  return list;}

private List getManagedList43(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("sellolcrate");  list.add("HEAD");  return list;}

private List getManagedList44(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("sellglcrate");  list.add("HEAD");  return list;}

private List getManagedList45(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("sellgllcrate");  list.add("HEAD");  return list;}

private List getManagedList46(){  List list = new ArrayList();  list.add(getManagedList47());  list.add(getManagedList48());  list.add(getManagedList49());  list.add(getManagedList50());  list.add(getManagedList51());  list.add(getManagedList52());  list.add(getManagedList53());  return list;}

private List getManagedList47(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("sellamount");  list.add("HEAD");  return list;}

private List getManagedList48(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("sellolcrate");  list.add("HEAD");  return list;}

private List getManagedList49(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("sellolcamount");  list.add("HEAD");  return list;}

private List getManagedList50(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("sellglcrate");  list.add("HEAD");  return list;}

private List getManagedList51(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("sellglcamount");  list.add("HEAD");  return list;}

private List getManagedList52(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("sellgllcrate");  list.add("HEAD");  return list;}

private List getManagedList53(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("sellgllcamount");  list.add("HEAD");  return list;}

private List getManagedList54(){  List list = new ArrayList();  list.add(getManagedList55());  list.add(getManagedList56());  list.add(getManagedList57());  list.add(getManagedList58());  list.add(getManagedList59());  return list;}

private List getManagedList55(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList56(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currtype");  list.add("HEAD");  return list;}

private List getManagedList57(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList58(){  List list = new ArrayList();  list.add("MONEY");  list.add("gainorloss");  list.add("HEAD");  return list;}

private List getManagedList59(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("sellolcrate");  list.add("HEAD");  return list;}

private List getManagedList60(){  List list = new ArrayList();  list.add(getManagedList61());  return list;}

private List getManagedList61(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("gainorloss");  list.add("HEAD");  return list;}

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

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList62());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList64());  return map;}

private List getManagedList62(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_32475a());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_32475a(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#32475a")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#32475a");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#32475a",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
  bean.setSelfDefListeners(getManagedList63());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList63(){  List list = new ArrayList();  list.add(getListCurrExDigitHandler_1f0390f());  return list;}

private nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler getListCurrExDigitHandler_1f0390f(){
 if(context.get("nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler#1f0390f")!=null)
 return (nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler)context.get("nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler#1f0390f");
  nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler bean = new nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler();
  context.put("nc.ui.cmp.curexchange.digit.ListCurrExDigitHandler#1f0390f",bean);
  bean.setModel(getManageAppModel());
  bean.setSourceKey("tradeprice");
  bean.setInCurrKey("pk_incurrtype");
  bean.setOutCurrKey("pk_outcurrtype");
  bean.setBusiTypeKey("busitype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList64(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_557551());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_557551(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#557551")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#557551");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#557551",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
  bean.setSelfDefListeners(getManagedList65());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList65(){  List list = new ArrayList();  list.add(getCardCurrExDigitHandler_ea92f2());  return list;}

private nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler getCardCurrExDigitHandler_ea92f2(){
 if(context.get("nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler#ea92f2")!=null)
 return (nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler)context.get("nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler#ea92f2");
  nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler bean = new nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler();
  context.put("nc.ui.cmp.curexchange.digit.CardCurrExDigitHandler#ea92f2",bean);
  bean.setModel(getManageAppModel());
  bean.setSourceKey("tradeprice");
  bean.setInCurrKey("pk_buycurrtype");
  bean.setOutCurrKey("pk_sellcurrtype");
  bean.setBusiTypeKey("busitype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getCardEditEventDigitMediator(){
 if(context.get("cardEditEventDigitMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("cardEditEventDigitMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getBillFormEditor());  context.put("cardEditEventDigitMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_8937c9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_8937c9(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#8937c9")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#8937c9");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#8937c9",bean);
  bean.setMany2one(getManagedMap2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList66(),getCardEditListener());  map.put(getManagedList67(),getCardEditListener());  map.put(getManagedList68(),getCardEditListener());  return map;}

private List getManagedList66(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_buycurrtype");  list.add("buyamount");  list.add("billdate");  list.add("buyolcrate");  list.add("buyglcrate");  list.add("buygllcrate");  return list;}

private List getManagedList67(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_chargecurrtype");  list.add("chargeamount");  list.add("billdate");  list.add("chargeolcrate");  list.add("chargeglcrate");  list.add("chargegllcrate");  return list;}

private List getManagedList68(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_sellcurrtype");  list.add("sellamount");  list.add("billdate");  list.add("sellolcrate");  list.add("sellglcrate");  list.add("sellgllcrate");  return list;}

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

public nc.ui.cmp.curexchange.ace.serviceproxy.AceCurexchangeMaintainProxy getMaintainProxy(){
 if(context.get("maintainProxy")!=null)
 return (nc.ui.cmp.curexchange.ace.serviceproxy.AceCurexchangeMaintainProxy)context.get("maintainProxy");
  nc.ui.cmp.curexchange.ace.serviceproxy.AceCurexchangeMaintainProxy bean = new nc.ui.cmp.curexchange.ace.serviceproxy.AceCurexchangeMaintainProxy();
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
  bean.setBillType("36S5");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.model.CurrexchangeDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.cmp.curexchange.model.CurrexchangeDataManager)context.get("modelDataManager");
  nc.ui.cmp.curexchange.model.CurrexchangeDataManager bean = new nc.ui.cmp.curexchange.model.CurrexchangeDataManager();
  context.put("modelDataManager",bean);
  bean.setModel(getManageAppModel());
  bean.setPaginationModel(getPaginationModel());
  bean.setPaginationDelegator(getBillModelPaginationDelegator());
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

public nc.ui.uif2.components.pagination.PaginationModel getPaginationModel(){
 if(context.get("paginationModel")!=null)
 return (nc.ui.uif2.components.pagination.PaginationModel)context.get("paginationModel");
  nc.ui.uif2.components.pagination.PaginationModel bean = new nc.ui.uif2.components.pagination.PaginationModel();
  context.put("paginationModel",bean);
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.BillManagePaginationDelegator getBillModelPaginationDelegator(){
 if(context.get("billModelPaginationDelegator")!=null)
 return (nc.ui.uif2.components.pagination.BillManagePaginationDelegator)context.get("billModelPaginationDelegator");
  nc.ui.uif2.components.pagination.BillManagePaginationDelegator bean = new nc.ui.uif2.components.pagination.BillManagePaginationDelegator(getManageAppModel(),getPaginationModel());  context.put("billModelPaginationDelegator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList69());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList69(){  List list = new ArrayList();  list.add("36070FCE");  return list;}

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
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("36070FCE");
  bean.setPaginationBar(getPaginationBar());
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setNorth(getQueryInfo());
  bean.setShowTotalLine(true);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.view.CurexchangeBillForm getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.cmp.curexchange.view.CurexchangeBillForm)context.get("billFormEditor");
  nc.ui.cmp.curexchange.view.CurexchangeBillForm bean = new nc.ui.cmp.curexchange.view.CurexchangeBillForm();
  context.put("billFormEditor",bean);
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setShowOrgPanel(true);
  bean.setNodekey("36070FCE");
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
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
  bean.setParams(getManagedList70());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList70(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1c0894c());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1c0894c(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1c0894c")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1c0894c");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1c0894c",bean);
  bean.setMdfullname("cmp.curexchange");
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
  bean.setParams(getManagedList71());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList71(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1761b06());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1761b06(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1761b06")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1761b06");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1761b06",bean);
  bean.setMdfullname("cmp.curexchange");
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
  bean.setParams(getManagedList72());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList72(){  List list = new ArrayList();  list.add(getQueryParam_e84fab());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_e84fab(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#e84fab")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#e84fab");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#e84fab",bean);
  bean.setMdfullname("cmp.curexchange");
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
  bean.setTangramLayoutRoot(getTBNode_718f9c());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_718f9c(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#718f9c")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#718f9c");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#718f9c",bean);
  bean.setTabs(getManagedList73());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList73(){  List list = new ArrayList();  list.add(getHSNode_549fca());  list.add(getVSNode_17f1d9e());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_549fca(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#549fca")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#549fca");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#549fca",bean);
  bean.setLeft(getCNode_1c68e13());
  bean.setRight(getCNode_998a83());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1c68e13(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1c68e13")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1c68e13");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1c68e13",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_998a83(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#998a83")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#998a83");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#998a83",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_17f1d9e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#17f1d9e")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#17f1d9e");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#17f1d9e",bean);
  bean.setUp(getCNode_1c69e89());
  bean.setDown(getCNode_d24ff4());
  bean.setDividerLocation(31f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1c69e89(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1c69e89")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1c69e89");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1c69e89",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_d24ff4(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#d24ff4")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#d24ff4");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#d24ff4",bean);
  bean.setComponent(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getEventMediator(){
 if(context.get("eventMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("eventMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("eventMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setHandlerGroup(getManagedList74());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList74(){  List list = new ArrayList();  list.add(getEventHandlerGroup_10fd7ec());  list.add(getEventHandlerGroup_ba66cb());  list.add(getEventHandlerGroup_a9424b());  list.add(getEventHandlerGroup_1750047());  return list;}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_10fd7ec(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#10fd7ec")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#10fd7ec");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#10fd7ec",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent");
  bean.setHandler(getAceHeadTailBeforeEditHandler_18b7e48());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler getAceHeadTailBeforeEditHandler_18b7e48(){
 if(context.get("nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler#18b7e48")!=null)
 return (nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler)context.get("nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler#18b7e48");
  nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler bean = new nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler();
  context.put("nc.ui.cmp.curexchange.ace.handler.AceHeadTailBeforeEditHandler#18b7e48",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_ba66cb(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#ba66cb")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#ba66cb");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#ba66cb",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent");
  bean.setHandler(getAceHeadTailAfterEditHandler_128d8ef());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler getAceHeadTailAfterEditHandler_128d8ef(){
 if(context.get("nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler#128d8ef")!=null)
 return (nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler)context.get("nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler#128d8ef");
  nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler bean = new nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler();
  context.put("nc.ui.cmp.curexchange.ace.handler.AceHeadTailAfterEditHandler#128d8ef",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_a9424b(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a9424b")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a9424b");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#a9424b",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.billform.AddEvent");
  bean.setHandler(getAceAddHandler_1b2c8fc());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.curexchange.ace.handler.AceAddHandler getAceAddHandler_1b2c8fc(){
 if(context.get("nc.ui.cmp.curexchange.ace.handler.AceAddHandler#1b2c8fc")!=null)
 return (nc.ui.cmp.curexchange.ace.handler.AceAddHandler)context.get("nc.ui.cmp.curexchange.ace.handler.AceAddHandler#1b2c8fc");
  nc.ui.cmp.curexchange.ace.handler.AceAddHandler bean = new nc.ui.cmp.curexchange.ace.handler.AceAddHandler();
  context.put("nc.ui.cmp.curexchange.ace.handler.AceAddHandler#1b2c8fc",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.event.EventHandlerGroup getEventHandlerGroup_1750047(){
 if(context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1750047")!=null)
 return (nc.ui.pubapp.uif2app.event.EventHandlerGroup)context.get("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1750047");
  nc.ui.pubapp.uif2app.event.EventHandlerGroup bean = new nc.ui.pubapp.uif2app.event.EventHandlerGroup();
  context.put("nc.ui.pubapp.uif2app.event.EventHandlerGroup#1750047",bean);
  bean.setEvent("nc.ui.pubapp.uif2app.event.OrgChangedEvent");
  bean.setHandler(getOrgChangedHandler_3f62e2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.curexchange.handler.OrgChangedHandler getOrgChangedHandler_3f62e2(){
 if(context.get("nc.ui.cmp.curexchange.handler.OrgChangedHandler#3f62e2")!=null)
 return (nc.ui.cmp.curexchange.handler.OrgChangedHandler)context.get("nc.ui.cmp.curexchange.handler.OrgChangedHandler#3f62e2");
  nc.ui.cmp.curexchange.handler.OrgChangedHandler bean = new nc.ui.cmp.curexchange.handler.OrgChangedHandler(getBillFormEditor());  context.put("nc.ui.cmp.curexchange.handler.OrgChangedHandler#3f62e2",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList75());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList75(){  List list = new ArrayList();  list.add(getActionsOfList());  list.add(getActionsOfCard());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getActionsOfList(){
 if(context.get("actionsOfList")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("actionsOfList");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("actionsOfList",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList76());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList76(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitMenuAction());  list.add(getAuditMenuAction());  list.add(getSeparatorAction());  list.add(getTransferMenuAction());  list.add(getSettleMenuAction());  list.add(getMakeBillAction());  list.add(getAssistMenuAction());  list.add(getSeparatorAction());  list.add(getLinkMenuAction());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintMenuAction());  list.add(getSeparatorAction());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getActionsOfCard(){
 if(context.get("actionsOfCard")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("actionsOfCard");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getBillFormEditor());  context.put("actionsOfCard",bean);
  bean.setModel(getManageAppModel());
  bean.setActions(getManagedList77());
  bean.setEditActions(getManagedList78());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList77(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getCardRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitMenuAction());  list.add(getAuditMenuAction());  list.add(getSeparatorAction());  list.add(getTransferMenuAction());  list.add(getSettleMenuAction());  list.add(getMakeBillAction());  list.add(getAssistMenuAction());  list.add(getSeparatorAction());  list.add(getLinkMenuAction());  list.add(getSeparatorAction());  list.add(getPrevVoucherAction());  list.add(getSeparatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintMenuAction());  list.add(getSeparatorAction());  return list;}

private List getManagedList78(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSaveAddAction());  list.add(getSaveSendApproveAction());  list.add(getSeparatorAction());  list.add(getCancelAction());  return list;}

public nc.ui.cmp.curexchange.action.CurexChangeSaveAndCommitAction getSaveSendApproveAction(){
 if(context.get("saveSendApproveAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexChangeSaveAndCommitAction)context.get("saveSendApproveAction");
  nc.ui.cmp.curexchange.action.CurexChangeSaveAndCommitAction bean = new nc.ui.cmp.curexchange.action.CurexChangeSaveAndCommitAction(getSaveAction(),getCommitAction());  context.put("saveSendApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
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

public nc.ui.cmp.curexchange.action.CurexchangeEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeEditAction)context.get("editAction");
  nc.ui.cmp.curexchange.action.CurexchangeEditAction bean = new nc.ui.cmp.curexchange.action.CurexchangeEditAction();
  context.put("editAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setPowercheck(true);
  bean.setBillType("36S5");
  bean.setBillCodeName("vbillno");
  bean.setInterceptor(getShowCardInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeDeleteAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeDeleteAction)context.get("deleteAction");
  nc.ui.cmp.curexchange.action.CurexchangeDeleteAction bean = new nc.ui.cmp.curexchange.action.CurexchangeDeleteAction();
  context.put("deleteAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setActionName("DELETE");
  bean.setPowercheck(true);
  bean.setBillType("36S5");
  bean.setBillCodeName("vbillno");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeSaveAction)context.get("saveAction");
  nc.ui.cmp.curexchange.action.CurexchangeSaveAction bean = new nc.ui.cmp.curexchange.action.CurexchangeSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setActionName("SAVEBASE");
  bean.setBillType("36S5");
  bean.setValidationService(getValidateService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeSaveAddAction getSaveAddAction(){
 if(context.get("saveAddAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeSaveAddAction)context.get("saveAddAction");
  nc.ui.cmp.curexchange.action.CurexchangeSaveAddAction bean = new nc.ui.cmp.curexchange.action.CurexchangeSaveAddAction();
  context.put("saveAddAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setAddAction(getAddAction());
  bean.setSaveAction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.validation.CompositeValidation getValidateService(){
 if(context.get("validateService")!=null)
 return (nc.ui.pubapp.uif2app.validation.CompositeValidation)context.get("validateService");
  nc.ui.pubapp.uif2app.validation.CompositeValidation bean = new nc.ui.pubapp.uif2app.validation.CompositeValidation();
  context.put("validateService",bean);
  bean.setValidators(getManagedList79());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList79(){  List list = new ArrayList();  list.add(getTemplateNotNullValidation_655afe());  list.add(getPowerSaveValidateService_38ea4f());  return list;}

private nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation getTemplateNotNullValidation_655afe(){
 if(context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#655afe")!=null)
 return (nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation)context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#655afe");
  nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation bean = new nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation();
  context.put("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#655afe",bean);
  bean.setBillForm(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerSaveValidateService getPowerSaveValidateService_38ea4f(){
 if(context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#38ea4f")!=null)
 return (nc.ui.pubapp.pub.power.PowerSaveValidateService)context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#38ea4f");
  nc.ui.pubapp.pub.power.PowerSaveValidateService bean = new nc.ui.pubapp.pub.power.PowerSaveValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerSaveValidateService#38ea4f",bean);
  bean.setEditActionCode("edit");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S5");
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

public nc.ui.cmp.curexchange.action.CurexchangeCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeCopyAction)context.get("copyAction");
  nc.ui.cmp.curexchange.action.CurexchangeCopyAction bean = new nc.ui.cmp.curexchange.action.CurexchangeCopyAction();
  context.put("copyAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getShowCardInterceptor());
  bean.setEditor(getBillFormEditor());
  bean.setCopyActionProcessor(getCopyActionProcessor_9f18dc());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.curexchange.action.CopyActionProcessor getCopyActionProcessor_9f18dc(){
 if(context.get("nc.ui.cmp.curexchange.action.CopyActionProcessor#9f18dc")!=null)
 return (nc.ui.cmp.curexchange.action.CopyActionProcessor)context.get("nc.ui.cmp.curexchange.action.CopyActionProcessor#9f18dc");
  nc.ui.cmp.curexchange.action.CopyActionProcessor bean = new nc.ui.cmp.curexchange.action.CopyActionProcessor();
  context.put("nc.ui.cmp.curexchange.action.CopyActionProcessor#9f18dc",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.query.CurexchangeQueryConditionInitializer getQryCondInitializer(){
 if(context.get("qryCondInitializer")!=null)
 return (nc.ui.cmp.curexchange.query.CurexchangeQueryConditionInitializer)context.get("qryCondInitializer");
  nc.ui.cmp.curexchange.query.CurexchangeQueryConditionInitializer bean = new nc.ui.cmp.curexchange.query.CurexchangeQueryConditionInitializer();
  context.put("qryCondInitializer",bean);
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
  bean.setQryCondDLGInitializer(getQryCondInitializer());
  bean.setShowUpComponent(getListView());
  bean.setTemplateContainer(getQueryTemplateContainer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.action.query.DefaultRefreshAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.tmpub.action.query.DefaultRefreshAction)context.get("refreshAction");
  nc.ui.tmpub.action.query.DefaultRefreshAction bean = new nc.ui.tmpub.action.query.DefaultRefreshAction();
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

public nc.ui.cmp.curexchange.action.CurexchangeCommitAction getCommitAction(){
 if(context.get("commitAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeCommitAction)context.get("commitAction");
  nc.ui.cmp.curexchange.action.CurexchangeCommitAction bean = new nc.ui.cmp.curexchange.action.CurexchangeCommitAction();
  context.put("commitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S5");
  bean.setActionName("SAVE");
  bean.setFilledUpInFlow(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeUnCommitAction getUnCommitAction(){
 if(context.get("unCommitAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeUnCommitAction)context.get("unCommitAction");
  nc.ui.cmp.curexchange.action.CurexchangeUnCommitAction bean = new nc.ui.cmp.curexchange.action.CurexchangeUnCommitAction();
  context.put("unCommitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S5");
  bean.setActionName("UNSAVEBILL");
  bean.setFilledUpInFlow(true);
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
  bean.setName(getI18nFB_1194a33());
  bean.setActions(getManagedList80());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1194a33(){
 if(context.get("nc.ui.uif2.I18nFB#1194a33")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1194a33");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1194a33",bean);  bean.setResDir("common");
  bean.setDefaultValue("提交");
  bean.setResId("UC001-0000029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1194a33",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList80(){  List list = new ArrayList();  list.add(getCommitAction());  list.add(getUnCommitAction());  return list;}

public nc.ui.pubapp.pub.power.PowerValidateService getApprovepowervalidservice(){
 if(context.get("approvepowervalidservice")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("approvepowervalidservice");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("approvepowervalidservice",bean);
  bean.setActionCode("approve");
  bean.setBillCodeFiledName("vbillno");
  bean.setPermissionCode("36S5");
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
  bean.setPermissionCode("36S5");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeApproveAction getApproveAction(){
 if(context.get("approveAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeApproveAction)context.get("approveAction");
  nc.ui.cmp.curexchange.action.CurexchangeApproveAction bean = new nc.ui.cmp.curexchange.action.CurexchangeApproveAction();
  context.put("approveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S5");
  bean.setActionName("APPROVE");
  bean.setValidationService(getApprovepowervalidservice());
  bean.setFilledUpInFlow(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurexchangeUnApproveAction getUnApproveAction(){
 if(context.get("unApproveAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeUnApproveAction)context.get("unApproveAction");
  nc.ui.cmp.curexchange.action.CurexchangeUnApproveAction bean = new nc.ui.cmp.curexchange.action.CurexchangeUnApproveAction();
  context.put("unApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setBillType("36S5");
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
  bean.setName(getI18nFB_156954e());
  bean.setActions(getManagedList81());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_156954e(){
 if(context.get("nc.ui.uif2.I18nFB#156954e")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#156954e");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#156954e",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607mng1-0019");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#156954e",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList81(){  List list = new ArrayList();  list.add(getApproveAction());  list.add(getUnApproveAction());  list.add(getQueryAuditFlowAction());  return list;}

public nc.ui.cmp.curexchange.action.CurexchangeTransferAction getTransferAction(){
 if(context.get("transferAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeTransferAction)context.get("transferAction");
  nc.ui.cmp.curexchange.action.CurexchangeTransferAction bean = new nc.ui.cmp.curexchange.action.CurexchangeTransferAction();
  context.put("transferAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S5");
  bean.setCode("TRANSFERFTS");
  bean.setBtnName(getI18nFB_1643491());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1643491(){
 if(context.get("nc.ui.uif2.I18nFB#1643491")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1643491");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1643491",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607mng1-0023");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1643491",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.curexchange.action.CurexchangeUnTransferAction getUnTransferAction(){
 if(context.get("unTransferAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeUnTransferAction)context.get("unTransferAction");
  nc.ui.cmp.curexchange.action.CurexchangeUnTransferAction bean = new nc.ui.cmp.curexchange.action.CurexchangeUnTransferAction();
  context.put("unTransferAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S5");
  bean.setCode("UNTRANSFERFTS");
  bean.setBtnName(getI18nFB_1b74a77());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1b74a77(){
 if(context.get("nc.ui.uif2.I18nFB#1b74a77")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1b74a77");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1b74a77",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("取消委托");
  bean.setResId("03607mng1-0024");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1b74a77",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.GroupAction getTransferMenuAction(){
 if(context.get("transferMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("transferMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("transferMenuAction",bean);
  bean.setCode("transferMenuAction");
  bean.setName(getI18nFB_79d2b0());
  bean.setActions(getManagedList82());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_79d2b0(){
 if(context.get("nc.ui.uif2.I18nFB#79d2b0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#79d2b0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#79d2b0",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607mng1-0023");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#79d2b0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList82(){  List list = new ArrayList();  list.add(getTransferAction());  list.add(getUnTransferAction());  return list;}

public nc.ui.cmp.curexchange.action.CurexchangeSettleAction getSettleAction(){
 if(context.get("settleAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeSettleAction)context.get("settleAction");
  nc.ui.cmp.curexchange.action.CurexchangeSettleAction bean = new nc.ui.cmp.curexchange.action.CurexchangeSettleAction();
  context.put("settleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S5");
  bean.setCode("SETTLE");
  bean.setBtnName(getI18nFB_1f74703());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1f74703(){
 if(context.get("nc.ui.uif2.I18nFB#1f74703")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1f74703");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1f74703",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607mng1-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1f74703",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.curexchange.action.CurexchangeUnSettleAction getUnSettleAction(){
 if(context.get("unSettleAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeUnSettleAction)context.get("unSettleAction");
  nc.ui.cmp.curexchange.action.CurexchangeUnSettleAction bean = new nc.ui.cmp.curexchange.action.CurexchangeUnSettleAction();
  context.put("unSettleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBilltype("36S5");
  bean.setCode("UNSETTLE");
  bean.setBtnName(getI18nFB_15717fd());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_15717fd(){
 if(context.get("nc.ui.uif2.I18nFB#15717fd")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#15717fd");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#15717fd",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("取消结算");
  bean.setResId("03607mng1-0026");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#15717fd",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.GroupAction getSettleMenuAction(){
 if(context.get("settleMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("settleMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("settleMenuAction",bean);
  bean.setCode("settleMenuAction");
  bean.setName(getI18nFB_e813f1());
  bean.setActions(getManagedList83());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_e813f1(){
 if(context.get("nc.ui.uif2.I18nFB#e813f1")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#e813f1");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#e813f1",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607mng1-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#e813f1",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList83(){  List list = new ArrayList();  list.add(getSettleAction());  list.add(getUnSettleAction());  return list;}

public nc.ui.cmp.curexchange.action.CurexchangeMakeBillAction getMakeBillAction(){
 if(context.get("makeBillAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurexchangeMakeBillAction)context.get("makeBillAction");
  nc.ui.cmp.curexchange.action.CurexchangeMakeBillAction bean = new nc.ui.cmp.curexchange.action.CurexchangeMakeBillAction();
  context.put("makeBillAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
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
  bean.setName(getI18nFB_755baf());
  bean.setActions(getManagedList84());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_755baf(){
 if(context.get("nc.ui.uif2.I18nFB#755baf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#755baf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#755baf",bean);  bean.setResDir("common");
  bean.setDefaultValue("打印");
  bean.setResId("UC001-0000007");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#755baf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList84(){  List list = new ArrayList();  list.add(getPrintAction());  list.add(getPreviewAction());  list.add(getOutputAction());  return list;}

public nc.funcnode.ui.action.MenuAction getImportExportActionGroup(){
 if(context.get("importExportActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("importExportActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("importExportActionGroup",bean);
  bean.setCode("ImportExport");
  bean.setName(getI18nFB_d672b6());
  bean.setActions(getManagedList85());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d672b6(){
 if(context.get("nc.ui.uif2.I18nFB#d672b6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d672b6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d672b6",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607mng1-0016");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d672b6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList85(){  List list = new ArrayList();  list.add(getExcelImportAction());  list.add(getSeparatorAction());  list.add(getExcelTemplateExportAction());  return list;}

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

public nc.ui.cmp.curexchange.excel.CurrexImportableEditor getSupplierPriceImportableEditor(){
 if(context.get("supplierPriceImportableEditor")!=null)
 return (nc.ui.cmp.curexchange.excel.CurrexImportableEditor)context.get("supplierPriceImportableEditor");
  nc.ui.cmp.curexchange.excel.CurrexImportableEditor bean = new nc.ui.cmp.curexchange.excel.CurrexImportableEditor();
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

public nc.funcnode.ui.action.MenuAction getLinkMenuAction(){
 if(context.get("linkMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("linkMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("linkMenuAction",bean);
  bean.setCode("linkQuery");
  bean.setName(getI18nFB_1051c98());
  bean.setActions(getManagedList86());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1051c98(){
 if(context.get("nc.ui.uif2.I18nFB#1051c98")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1051c98");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1051c98",bean);  bean.setResDir("common");
  bean.setDefaultValue("联查");
  bean.setResId("UC001-0000146");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1051c98",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList86(){  List list = new ArrayList();  list.add(getLinkBuyAccBalanceAction());  list.add(getLinkSellAccBalanceAction());  list.add(getLinkChargeAccBalanceAction());  list.add(getLinkQueryAction());  list.add(getLinkVoucherQueryAction());  return list;}

public nc.ui.pubapp.uif2app.actions.LinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.LinkQueryAction)context.get("linkQueryAction");
  nc.ui.pubapp.uif2app.actions.LinkQueryAction bean = new nc.ui.pubapp.uif2app.actions.LinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType("36S5");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.action.CurrexLinkBuyAccBalanceAction getLinkBuyAccBalanceAction(){
 if(context.get("linkBuyAccBalanceAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurrexLinkBuyAccBalanceAction)context.get("linkBuyAccBalanceAction");
  nc.ui.cmp.curexchange.action.CurrexLinkBuyAccBalanceAction bean = new nc.ui.cmp.curexchange.action.CurrexLinkBuyAccBalanceAction();
  context.put("linkBuyAccBalanceAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setCode("BuyAccBalance");
  bean.setBtnName(getI18nFB_98563f());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_98563f(){
 if(context.get("nc.ui.uif2.I18nFB#98563f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#98563f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#98563f",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("买入账户余额");
  bean.setResId("03607mng1-0027");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#98563f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.curexchange.action.CurrexLinkSellAccBalanceAction getLinkSellAccBalanceAction(){
 if(context.get("linkSellAccBalanceAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurrexLinkSellAccBalanceAction)context.get("linkSellAccBalanceAction");
  nc.ui.cmp.curexchange.action.CurrexLinkSellAccBalanceAction bean = new nc.ui.cmp.curexchange.action.CurrexLinkSellAccBalanceAction();
  context.put("linkSellAccBalanceAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setCode("SellAccBalance");
  bean.setBtnName(getI18nFB_14657f1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_14657f1(){
 if(context.get("nc.ui.uif2.I18nFB#14657f1")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#14657f1");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#14657f1",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("卖出账户余额");
  bean.setResId("03607mng1-0028");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#14657f1",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.curexchange.action.CurrexLinkChargeAccBalanceAction getLinkChargeAccBalanceAction(){
 if(context.get("linkChargeAccBalanceAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurrexLinkChargeAccBalanceAction)context.get("linkChargeAccBalanceAction");
  nc.ui.cmp.curexchange.action.CurrexLinkChargeAccBalanceAction bean = new nc.ui.cmp.curexchange.action.CurrexLinkChargeAccBalanceAction();
  context.put("linkChargeAccBalanceAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setCode("ChargeAccBalance");
  bean.setBtnName(getI18nFB_137cc64());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_137cc64(){
 if(context.get("nc.ui.uif2.I18nFB#137cc64")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#137cc64");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#137cc64",bean);  bean.setResDir("3607mng1_0");
  bean.setDefaultValue("手续费账户余额");
  bean.setResId("03607mng1-0029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#137cc64",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.curexchange.action.CurrexLinkVoucherQueryAction getLinkVoucherQueryAction(){
 if(context.get("linkVoucherQueryAction")!=null)
 return (nc.ui.cmp.curexchange.action.CurrexLinkVoucherQueryAction)context.get("linkVoucherQueryAction");
  nc.ui.cmp.curexchange.action.CurrexLinkVoucherQueryAction bean = new nc.ui.cmp.curexchange.action.CurrexLinkVoucherQueryAction();
  context.put("linkVoucherQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
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
  bean.setBtnName(getI18nFB_4bcf66());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_4bcf66(){
 if(context.get("nc.ui.uif2.I18nFB#4bcf66")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#4bcf66");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#4bcf66",bean);  bean.setResDir("3607set1_0");
  bean.setDefaultValue("查看审批意见");
  bean.setResId("03607set1-0066");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#4bcf66",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.funcnode.ui.action.MenuAction getAssistMenuAction(){
 if(context.get("assistMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("assistMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("assistMenuAction",bean);
  bean.setCode("assistMenuAction");
  bean.setName(getI18nFB_11d60e0());
  bean.setActions(getManagedList87());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_11d60e0(){
 if(context.get("nc.ui.uif2.I18nFB#11d60e0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#11d60e0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#11d60e0",bean);  bean.setResDir("common");
  bean.setDefaultValue("辅助功能");
  bean.setResId("UC001-0000137");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#11d60e0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList87(){  List list = new ArrayList();  list.add(getFileAction());  return list;}

public nc.ui.pubapp.uif2app.actions.FileDocManageAction getFileAction(){
 if(context.get("fileAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.FileDocManageAction)context.get("fileAction");
  nc.ui.pubapp.uif2app.actions.FileDocManageAction bean = new nc.ui.pubapp.uif2app.actions.FileDocManageAction();
  context.put("fileAction",bean);
  bean.setModel(getManageAppModel());
  bean.setExceptionHandler(getExceptionHandler());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.DefaultExceptionHanler getExceptionHandler(){
 if(context.get("exceptionHandler")!=null)
 return (nc.ui.uif2.DefaultExceptionHanler)context.get("exceptionHandler");
  nc.ui.uif2.DefaultExceptionHanler bean = new nc.ui.uif2.DefaultExceptionHanler();
  context.put("exceptionHandler",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.curexchange.listener.CurrexFuncNodeInitDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.cmp.curexchange.listener.CurrexFuncNodeInitDataListener)context.get("InitDataListener");
  nc.ui.cmp.curexchange.listener.CurrexFuncNodeInitDataListener bean = new nc.ui.cmp.curexchange.listener.CurrexFuncNodeInitDataListener();
  context.put("InitDataListener",bean);
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
  bean.setRemoteCallers(getManagedList88());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList88(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

}
