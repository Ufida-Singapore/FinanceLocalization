package nc.ui.arap.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class payablebill_record extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.funcnode.ui.action.SeparatorAction getSeparatorAction(){
 if(context.get("separatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("separatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("separatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator getOrgChangedMediator(){
 if(context.get("OrgChangedMediator")!=null)
 return (nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator)context.get("OrgChangedMediator");
  nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator bean = new nc.ui.pubapp.uif2app.mediator.modelevent.OrgChangedMediator();
  context.put("OrgChangedMediator",bean);
  bean.setBillform(getBillFormEditor());
  bean.setModel(getManageAppModel());
  bean.setOrgChangedImpl(getOrgchange());
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

public nc.ui.arap.viewhandler.HBRelationAfterEditHandler getRelationEditHandler(){
 if(context.get("RelationEditHandler")!=null)
 return (nc.ui.arap.viewhandler.HBRelationAfterEditHandler)context.get("RelationEditHandler");
  nc.ui.arap.viewhandler.HBRelationAfterEditHandler bean = new nc.ui.arap.viewhandler.HBRelationAfterEditHandler();
  context.put("RelationEditHandler",bean);
  bean.setHtob(getArapH2B());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator getPaginationDelegator(){
 if(context.get("paginationDelegator")!=null)
 return (nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator)context.get("paginationDelegator");
  nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator bean = new nc.ui.pubapp.uif2app.actions.pagination.BillModelPaginationDelegator(getManageAppModel());  context.put("paginationDelegator",bean);
  bean.setPaginationQuery(getPaginationQueryService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.PauseTransactActionInterceptor getPauseTransactActionInterceptor(){
 if(context.get("pauseTransactActionInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.PauseTransactActionInterceptor)context.get("pauseTransactActionInterceptor");
  nc.ui.arap.actions.interceptor.PauseTransactActionInterceptor bean = new nc.ui.arap.actions.interceptor.PauseTransactActionInterceptor();
  context.put("pauseTransactActionInterceptor",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.BillVersionActionInterceptor getBillVersionActionInterceptor(){
 if(context.get("billVersionActionInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.BillVersionActionInterceptor)context.get("billVersionActionInterceptor");
  nc.ui.arap.actions.interceptor.BillVersionActionInterceptor bean = new nc.ui.arap.actions.interceptor.BillVersionActionInterceptor();
  context.put("billVersionActionInterceptor",bean);
  bean.setModel(getManageAppModel());
  bean.setHtob(getArapH2B());
  bean.setBillform(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.OperPowerActionInterceptor getOperPowerInterceptor(){
 if(context.get("operPowerInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.OperPowerActionInterceptor)context.get("operPowerInterceptor");
  nc.ui.arap.actions.interceptor.OperPowerActionInterceptor bean = new nc.ui.arap.actions.interceptor.OperPowerActionInterceptor();
  context.put("operPowerInterceptor",bean);
  bean.setModel(getManageAppModel());
  bean.setBillform(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.CaActionInterceptor getCaActionInterceptor(){
 if(context.get("caActionInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.CaActionInterceptor)context.get("caActionInterceptor");
  nc.ui.arap.actions.interceptor.CaActionInterceptor bean = new nc.ui.arap.actions.interceptor.CaActionInterceptor();
  context.put("caActionInterceptor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.BillVerifyActionInterceptor getBillVerifyActionInterceptor(){
 if(context.get("billVerifyActionInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.BillVerifyActionInterceptor)context.get("billVerifyActionInterceptor");
  nc.ui.arap.actions.interceptor.BillVerifyActionInterceptor bean = new nc.ui.arap.actions.interceptor.BillVerifyActionInterceptor();
  context.put("billVerifyActionInterceptor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.OrgCheckActionInterceptor getOrgCheckActionInterceptor(){
 if(context.get("orgCheckActionInterceptor")!=null)
 return (nc.ui.arap.actions.interceptor.OrgCheckActionInterceptor)context.get("orgCheckActionInterceptor");
  nc.ui.arap.actions.interceptor.OrgCheckActionInterceptor bean = new nc.ui.arap.actions.interceptor.OrgCheckActionInterceptor();
  context.put("orgCheckActionInterceptor",bean);
  bean.setBillform(getBillFormEditor());
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
  bean.setParams(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getQueryParam_1035010());  list.add(getQueryParam_7d6ac1());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1035010(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1035010")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1035010");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1035010",bean);
  bean.setMdfullname("arap.recbill");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_7d6ac1(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#7d6ac1")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#7d6ac1");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#7d6ac1",bean);
  bean.setMdfullname("arap.recitem");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction getRefreshAllAction(){
 if(context.get("refreshAllAction")!=null)
 return (nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction)context.get("refreshAllAction");
  nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction bean = new nc.ui.pubapp.uif2app.query2.action.DefaultRefreshAction();
  context.put("refreshAllAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.arap.bill.util.ArapH2BMapping getArapH2B(){
 if(context.get("ArapH2B")!=null)
 return (nc.vo.arap.bill.util.ArapH2BMapping)context.get("ArapH2B");
  nc.vo.arap.bill.util.ArapH2BMapping bean = new nc.vo.arap.bill.util.ArapH2BMapping();
  context.put("ArapH2B",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.impl.BillCardCalculator getBillCardCalculator(){
 if(context.get("BillCardCalculator")!=null)
 return (nc.ui.arap.impl.BillCardCalculator)context.get("BillCardCalculator");
  nc.ui.arap.impl.BillCardCalculator bean = new nc.ui.arap.impl.BillCardCalculator();
  context.put("BillCardCalculator",bean);
  bean.setCalItem(getRelationItemForCal());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.NodeKeyQry getNodeKeyQry(){
 if(context.get("NodeKeyQry")!=null)
 return (nc.ui.arap.view.NodeKeyQry)context.get("NodeKeyQry");
  nc.ui.arap.view.NodeKeyQry bean = new nc.ui.arap.view.NodeKeyQry();
  context.put("NodeKeyQry",bean);
  bean.setModel(getManageAppModel());
  bean.setDefNodekey(getDefNodeKey());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.ArapBillListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.arap.view.ArapBillListView)context.get("listView");
  nc.ui.arap.view.ArapBillListView bean = new nc.ui.arap.view.ArapBillListView();
  context.put("listView",bean);
  bean.setModel(getManageAppModel());
  bean.setNodeKeyQry(getNodeKeyQry());
  bean.setPageSize(10000);
  bean.setTemplateContainer(getTemplateContainer());
  bean.setMultiSelectionEnable(true);
  bean.setPaginationDelegate(getPaginationDelegator());
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setMouseMediator(getMouseClickShowPanelMediator());
  bean.setLoadBillListTemplate(getLoadBillListTemplate());
  bean.initRealUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemListPreparator(){
 if(context.get("userdefitemListPreparator")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("userdefitemListPreparator");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("userdefitemListPreparator",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add(getListUserdefitemQueryParam());  list.add(getListUserdefitemQueryParam1());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getListUserdefitemQueryParam(){
 if(context.get("listUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("listUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("listUserdefitemQueryParam",bean);
  bean.setMdfullname("arap.recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getListUserdefitemQueryParam1(){
 if(context.get("listUserdefitemQueryParam1")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("listUserdefitemQueryParam1");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("listUserdefitemQueryParam1",bean);
  bean.setMdfullname("arap.recitem");
  bean.setPos(1);
  bean.setTabcode("bodys");
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.beans.ActionsBar.ActionsBarSeparator getActionsBarSeparator(){
 if(context.get("actionsBarSeparator")!=null)
 return (nc.ui.pub.beans.ActionsBar.ActionsBarSeparator)context.get("actionsBarSeparator");
  nc.ui.pub.beans.ActionsBar.ActionsBarSeparator bean = new nc.ui.pub.beans.ActionsBar.ActionsBarSeparator();
  context.put("actionsBarSeparator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator getMouseClickShowPanelMediator(){
 if(context.get("MouseClickShowPanelMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator)context.get("MouseClickShowPanelMediator");
  nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator bean = new nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator();
  context.put("MouseClickShowPanelMediator",bean);
  bean.setShowUpComponent(getBillFormEditor());
  bean.setHyperLinkColumn("billno");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.ArapBillCardTemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.arap.view.ArapBillCardTemplateContainer)context.get("templateContainer");
  nc.ui.arap.view.ArapBillCardTemplateContainer bean = new nc.ui.arap.view.ArapBillCardTemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeyQry(getNodeKeyQry());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.ArapBillCardForm getBillFormEditor(){
 if(context.get("billFormEditor")!=null)
 return (nc.ui.arap.view.ArapBillCardForm)context.get("billFormEditor");
  nc.ui.arap.view.ArapBillCardForm bean = new nc.ui.arap.view.ArapBillCardForm();
  context.put("billFormEditor",bean);
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setComponentValueManager(getComponentValueManager());
  bean.setNodekeyQry(getNodeKeyQry());
  bean.setClosingListener(getClosingListener());
  bean.setAutoAddLine(false);
  bean.setTemplateNotNullValidate(true);
  bean.setBlankChildrenFilter(getSingleFieldBlankChildrenFilter_1ebdfa0());
  bean.setDefValueItf(getBillDefVauleItf());
  bean.setBodyLineActions(getManagedList2());
  bean.setActions(getManagedList5());
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_65279b());
  bean.initRealUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter getSingleFieldBlankChildrenFilter_1ebdfa0(){
 if(context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#1ebdfa0")!=null)
 return (nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter)context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#1ebdfa0");
  nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter bean = new nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter();
  context.put("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#1ebdfa0",bean);
  bean.setFieldName(getDefMoneyField());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getAddLineAction());  list.add(getInsertLineAction_a68570());  list.add(getBodyDelLineAction_828393());  list.add(getBodyCopyLineAction_8c5444());  list.add(getBodyPasteLineAction_f045df());  list.add(getBodyPasteToTailAction_7b51b3());  list.add(getActionsBarSeparator());  list.add(getBodyLineEditAction_30fa83());  list.add(getActionsBarSeparator());  list.add(getBillBodyZoomAction_18ed589());  return list;}

private nc.ui.arap.actions.InsertLineAction getInsertLineAction_a68570(){
 if(context.get("nc.ui.arap.actions.InsertLineAction#a68570")!=null)
 return (nc.ui.arap.actions.InsertLineAction)context.get("nc.ui.arap.actions.InsertLineAction#a68570");
  nc.ui.arap.actions.InsertLineAction bean = new nc.ui.arap.actions.InsertLineAction();
  context.put("nc.ui.arap.actions.InsertLineAction#a68570",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
  bean.setIArapLineDefValUtil(getIArapLineDefValUtil());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.arap.actions.BodyDelLineAction getBodyDelLineAction_828393(){
 if(context.get("nc.ui.arap.actions.BodyDelLineAction#828393")!=null)
 return (nc.ui.arap.actions.BodyDelLineAction)context.get("nc.ui.arap.actions.BodyDelLineAction#828393");
  nc.ui.arap.actions.BodyDelLineAction bean = new nc.ui.arap.actions.BodyDelLineAction();
  context.put("nc.ui.arap.actions.BodyDelLineAction#828393",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.arap.actions.BodyCopyLineAction getBodyCopyLineAction_8c5444(){
 if(context.get("nc.ui.arap.actions.BodyCopyLineAction#8c5444")!=null)
 return (nc.ui.arap.actions.BodyCopyLineAction)context.get("nc.ui.arap.actions.BodyCopyLineAction#8c5444");
  nc.ui.arap.actions.BodyCopyLineAction bean = new nc.ui.arap.actions.BodyCopyLineAction();
  context.put("nc.ui.arap.actions.BodyCopyLineAction#8c5444",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.arap.actions.BodyPasteLineAction getBodyPasteLineAction_f045df(){
 if(context.get("nc.ui.arap.actions.BodyPasteLineAction#f045df")!=null)
 return (nc.ui.arap.actions.BodyPasteLineAction)context.get("nc.ui.arap.actions.BodyPasteLineAction#f045df");
  nc.ui.arap.actions.BodyPasteLineAction bean = new nc.ui.arap.actions.BodyPasteLineAction();
  context.put("nc.ui.arap.actions.BodyPasteLineAction#f045df",bean);
  bean.setClearItems(getManagedList3());
  bean.setBillType(getDefBillType());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add("ts");  return list;}

private nc.ui.arap.actions.BodyPasteToTailAction getBodyPasteToTailAction_7b51b3(){
 if(context.get("nc.ui.arap.actions.BodyPasteToTailAction#7b51b3")!=null)
 return (nc.ui.arap.actions.BodyPasteToTailAction)context.get("nc.ui.arap.actions.BodyPasteToTailAction#7b51b3");
  nc.ui.arap.actions.BodyPasteToTailAction bean = new nc.ui.arap.actions.BodyPasteToTailAction();
  context.put("nc.ui.arap.actions.BodyPasteToTailAction#7b51b3",bean);
  bean.setClearItems(getManagedList4());
  bean.setBillType(getDefBillType());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList4(){  List list = new ArrayList();  list.add("ts");  return list;}

private nc.ui.arap.actions.BodyLineEditAction getBodyLineEditAction_30fa83(){
 if(context.get("nc.ui.arap.actions.BodyLineEditAction#30fa83")!=null)
 return (nc.ui.arap.actions.BodyLineEditAction)context.get("nc.ui.arap.actions.BodyLineEditAction#30fa83");
  nc.ui.arap.actions.BodyLineEditAction bean = new nc.ui.arap.actions.BodyLineEditAction();
  context.put("nc.ui.arap.actions.BodyLineEditAction#30fa83",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.arap.actions.BillBodyZoomAction getBillBodyZoomAction_18ed589(){
 if(context.get("nc.ui.arap.actions.BillBodyZoomAction#18ed589")!=null)
 return (nc.ui.arap.actions.BillBodyZoomAction)context.get("nc.ui.arap.actions.BillBodyZoomAction#18ed589");
  nc.ui.arap.actions.BillBodyZoomAction bean = new nc.ui.arap.actions.BillBodyZoomAction();
  context.put("nc.ui.arap.actions.BillBodyZoomAction#18ed589",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList5(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_65279b(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#65279b")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#65279b");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#65279b",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList6());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList6(){  List list = new ArrayList();  list.add(getCardUserdefitemQueryParam());  list.add(getCardUserdefitemQueryParam1());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getCardUserdefitemQueryParam(){
 if(context.get("cardUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("cardUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("cardUserdefitemQueryParam",bean);
  bean.setMdfullname("arap.recbill");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getCardUserdefitemQueryParam1(){
 if(context.get("cardUserdefitemQueryParam1")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("cardUserdefitemQueryParam1");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("cardUserdefitemQueryParam1",bean);
  bean.setMdfullname("arap.recitem");
  bean.setPos(1);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.AddLineAction getAddLineAction(){
 if(context.get("AddLineAction")!=null)
 return (nc.ui.arap.actions.AddLineAction)context.get("AddLineAction");
  nc.ui.arap.actions.AddLineAction bean = new nc.ui.arap.actions.AddLineAction();
  context.put("AddLineAction",bean);
  bean.setHbrealtion(getRelationEditHandler());
  bean.setEditor(getBillFormEditor());
  bean.setIArapLineDefValUtil(getIArapLineDefValUtil());
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

public nc.vo.bd.meta.BDObjectAdpaterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.vo.bd.meta.BDObjectAdpaterFactory)context.get("boadatorfactory");
  nc.vo.bd.meta.BDObjectAdpaterFactory bean = new nc.vo.bd.meta.BDObjectAdpaterFactory();
  context.put("boadatorfactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.model.ArapBillManageModel getManageAppModel(){
 if(context.get("ManageAppModel")!=null)
 return (nc.ui.arap.model.ArapBillManageModel)context.get("ManageAppModel");
  nc.ui.arap.model.ArapBillManageModel bean = new nc.ui.arap.model.ArapBillManageModel();
  context.put("ManageAppModel",bean);
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setContext(getContext());
  bean.setSupportLazilyLoad(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PrintPreviewAction getPrintPreview(){
 if(context.get("printPreview")!=null)
 return (nc.ui.arap.actions.PrintPreviewAction)context.get("printPreview");
  nc.ui.arap.actions.PrintPreviewAction bean = new nc.ui.arap.actions.PrintPreviewAction();
  context.put("printPreview",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
  bean.setTaxForm(getTaxForm());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PrintOutputAction getPrintOutput(){
 if(context.get("printOutput")!=null)
 return (nc.ui.arap.actions.PrintOutputAction)context.get("printOutput");
  nc.ui.arap.actions.PrintOutputAction bean = new nc.ui.arap.actions.PrintOutputAction();
  context.put("printOutput",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
  bean.setTaxForm(getTaxForm());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.card.LoadBillCardTemplate getLoadBillCardTemplate(){
 if(context.get("LoadBillCardTemplate")!=null)
 return (nc.ui.arap.viewhandler.card.LoadBillCardTemplate)context.get("LoadBillCardTemplate");
  nc.ui.arap.viewhandler.card.LoadBillCardTemplate bean = new nc.ui.arap.viewhandler.card.LoadBillCardTemplate();
  context.put("LoadBillCardTemplate",bean);
  bean.setBspsp(getScaleProcessor());
  bean.setBillform(getBillFormEditor());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.card.CardAfterEditCalculateHandler getCardAfterEditCalculateHandler(){
 if(context.get("CardAfterEditCalculateHandler")!=null)
 return (nc.ui.arap.viewhandler.card.CardAfterEditCalculateHandler)context.get("CardAfterEditCalculateHandler");
  nc.ui.arap.viewhandler.card.CardAfterEditCalculateHandler bean = new nc.ui.arap.viewhandler.card.CardAfterEditCalculateHandler();
  context.put("CardAfterEditCalculateHandler",bean);
  bean.setCalitf(getBillCardCalculator());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyCurrTypeAfterEditHandler getBodyCurrTypeAfterEditHandler(){
 if(context.get("BodyCurrTypeAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyCurrTypeAfterEditHandler)context.get("BodyCurrTypeAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyCurrTypeAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyCurrTypeAfterEditHandler();
  context.put("BodyCurrTypeAfterEditHandler",bean);
  bean.setBcpsp(getScaleProcessor());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.OtherOrgBodyAfterEditHandler getOtherOrgBodyAfterEditHandler(){
 if(context.get("OtherOrgBodyAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.OtherOrgBodyAfterEditHandler)context.get("OtherOrgBodyAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.OtherOrgBodyAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.OtherOrgBodyAfterEditHandler();
  context.put("OtherOrgBodyAfterEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.OtherOrgHeadAfterEdithandler getOtherOrgHeadAfterEdithandler(){
 if(context.get("OtherOrgHeadAfterEdithandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.OtherOrgHeadAfterEdithandler)context.get("OtherOrgHeadAfterEdithandler");
  nc.ui.arap.viewhandler.cardafter.OtherOrgHeadAfterEdithandler bean = new nc.ui.arap.viewhandler.cardafter.OtherOrgHeadAfterEdithandler();
  context.put("OtherOrgHeadAfterEdithandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.OtherOrgBodyBeforeEditHandler getOtherOrgBodyBeforeEditHandler(){
 if(context.get("OtherOrgBodyBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.OtherOrgBodyBeforeEditHandler)context.get("OtherOrgBodyBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.OtherOrgBodyBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.OtherOrgBodyBeforeEditHandler();
  context.put("OtherOrgBodyBeforeEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.OtherOrgHeadBeforeEditHandler getOtherOrgHeadBeforeEditHandler(){
 if(context.get("OtherOrgHeadBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.OtherOrgHeadBeforeEditHandler)context.get("OtherOrgHeadBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.OtherOrgHeadBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.OtherOrgHeadBeforeEditHandler();
  context.put("OtherOrgHeadBeforeEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadBankAccBeforeEditHandler getHeadBankAccBeforeEditHandler(){
 if(context.get("HeadBankAccBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadBankAccBeforeEditHandler)context.get("HeadBankAccBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadBankAccBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadBankAccBeforeEditHandler();
  context.put("HeadBankAccBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyObjTypeBeforeEditHandler getBodyObjTypeBeforeEditHandler(){
 if(context.get("BodyObjTypeBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyObjTypeBeforeEditHandler)context.get("BodyObjTypeBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyObjTypeBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyObjTypeBeforeEditHandler();
  context.put("BodyObjTypeBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadEuroBeforeEditHandler getHeadEuroBeforeEditHandler(){
 if(context.get("HeadEuroBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadEuroBeforeEditHandler)context.get("HeadEuroBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadEuroBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadEuroBeforeEditHandler();
  context.put("HeadEuroBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyEuroBeforeEditHandler getBodyEuroBeforeEditHandler(){
 if(context.get("BodyEuroBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyEuroBeforeEditHandler)context.get("BodyEuroBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyEuroBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyEuroBeforeEditHandler();
  context.put("BodyEuroBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadObjTypeBeforeEditHandler getHeadObjTypeBeforeEditHandler(){
 if(context.get("HeadObjTypeBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadObjTypeBeforeEditHandler)context.get("HeadObjTypeBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadObjTypeBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadObjTypeBeforeEditHandler();
  context.put("HeadObjTypeBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadFundplanAfterEditHandler getHeadFundplanAfterEditHandler(){
 if(context.get("HeadFundplanAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadFundplanAfterEditHandler)context.get("HeadFundplanAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadFundplanAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadFundplanAfterEditHandler();
  context.put("HeadFundplanAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyFundplanAfterEditHandler getBodyFundplanAfterEditHandler(){
 if(context.get("BodyFundplanAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyFundplanAfterEditHandler)context.get("BodyFundplanAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyFundplanAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyFundplanAfterEditHandler();
  context.put("BodyFundplanAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.HBRelationAfterEditHandler getHBRelationAfterEditHandler(){
 if(context.get("HBRelationAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.HBRelationAfterEditHandler)context.get("HBRelationAfterEditHandler");
  nc.ui.arap.viewhandler.HBRelationAfterEditHandler bean = new nc.ui.arap.viewhandler.HBRelationAfterEditHandler();
  context.put("HBRelationAfterEditHandler",bean);
  bean.setHtob(getArapH2B());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadCurrTypeAfterEditHandler getHeadCurrTypeAfterEditHandler(){
 if(context.get("HeadCurrTypeAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadCurrTypeAfterEditHandler)context.get("HeadCurrTypeAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadCurrTypeAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadCurrTypeAfterEditHandler();
  context.put("HeadCurrTypeAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.ObjTypeHeadAfterEdithandler getObjTypeHeadAfterEdithandler(){
 if(context.get("ObjTypeHeadAfterEdithandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.ObjTypeHeadAfterEdithandler)context.get("ObjTypeHeadAfterEdithandler");
  nc.ui.arap.viewhandler.cardafter.ObjTypeHeadAfterEdithandler bean = new nc.ui.arap.viewhandler.cardafter.ObjTypeHeadAfterEdithandler();
  context.put("ObjTypeHeadAfterEdithandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyCheckNoAfterEditHandler getBodyCheckNoAfterEditHandler(){
 if(context.get("BodyCheckNoAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyCheckNoAfterEditHandler)context.get("BodyCheckNoAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyCheckNoAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyCheckNoAfterEditHandler();
  context.put("BodyCheckNoAfterEditHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyMaterialAfterEditHandler getBodyMaterialAfterEditHandler(){
 if(context.get("BodyMaterialAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyMaterialAfterEditHandler)context.get("BodyMaterialAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyMaterialAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyMaterialAfterEditHandler();
  context.put("BodyMaterialAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyCrossCheckBeforeHandler getBodyCrossCheckBeforeHandler(){
 if(context.get("BodyCrossCheckBeforeHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyCrossCheckBeforeHandler)context.get("BodyCrossCheckBeforeHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyCrossCheckBeforeHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyCrossCheckBeforeHandler();
  context.put("BodyCrossCheckBeforeHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadCrossCheckBeforeHandler getHeadCrossCheckBeforeHandler(){
 if(context.get("HeadCrossCheckBeforeHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadCrossCheckBeforeHandler)context.get("HeadCrossCheckBeforeHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadCrossCheckBeforeHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadCrossCheckBeforeHandler();
  context.put("HeadCrossCheckBeforeHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.list.LoadBillListTemplate getLoadBillListTemplate(){
 if(context.get("LoadBillListTemplate")!=null)
 return (nc.ui.arap.viewhandler.list.LoadBillListTemplate)context.get("LoadBillListTemplate");
  nc.ui.arap.viewhandler.list.LoadBillListTemplate bean = new nc.ui.arap.viewhandler.list.LoadBillListTemplate();
  context.put("LoadBillListTemplate",bean);
  bean.setModel(getManageAppModel());
  bean.setBlpsp(getListScaleProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.list.ListHeadRowChangeListener getListHeadRowChangeListener(){
 if(context.get("ListHeadRowChangeListener")!=null)
 return (nc.ui.arap.viewhandler.list.ListHeadRowChangeListener)context.get("ListHeadRowChangeListener");
  nc.ui.arap.viewhandler.list.ListHeadRowChangeListener bean = new nc.ui.arap.viewhandler.list.ListHeadRowChangeListener();
  context.put("ListHeadRowChangeListener",bean);
  bean.setBlpsp(getListScaleProcessor());
  bean.setBillFormEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.list.ListHeadModelListener getListHeadModelListener(){
 if(context.get("ListHeadModelListener")!=null)
 return (nc.ui.arap.viewhandler.list.ListHeadModelListener)context.get("ListHeadModelListener");
  nc.ui.arap.viewhandler.list.ListHeadModelListener bean = new nc.ui.arap.viewhandler.list.ListHeadModelListener();
  context.put("ListHeadModelListener",bean);
  bean.setBlpsp(getListScaleProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyBankAccBeforeEditHandler getBodyBankAccBeforeEditHandler(){
 if(context.get("BodyBankAccBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyBankAccBeforeEditHandler)context.get("BodyBankAccBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyBankAccBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyBankAccBeforeEditHandler();
  context.put("BodyBankAccBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodySoBilltypeBeforeEditHandler getBodySoBilltypeBeforeEditHandler(){
 if(context.get("BodySoBilltypeBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodySoBilltypeBeforeEditHandler)context.get("BodySoBilltypeBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodySoBilltypeBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodySoBilltypeBeforeEditHandler();
  context.put("BodySoBilltypeBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyAccountRefBeforeEditHandler getBodyAccountRefBeforeEditHandler(){
 if(context.get("BodyAccountRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyAccountRefBeforeEditHandler)context.get("BodyAccountRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyAccountRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyAccountRefBeforeEditHandler();
  context.put("BodyAccountRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadAccountRefBeforeEditHandler getHeadAccountRefBeforeEditHandler(){
 if(context.get("HeadAccountRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadAccountRefBeforeEditHandler)context.get("HeadAccountRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadAccountRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadAccountRefBeforeEditHandler();
  context.put("HeadAccountRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyCostCenterRefBeforeEditHandler getBodyCostCenterRefBeforeEditHandler(){
 if(context.get("BodyCostCenterRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyCostCenterRefBeforeEditHandler)context.get("BodyCostCenterRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyCostCenterRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyCostCenterRefBeforeEditHandler();
  context.put("BodyCostCenterRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadCostCenterRefBeforeEditHandler getHeadCostCenterRefBeforeEditHandler(){
 if(context.get("HeadCostCenterRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadCostCenterRefBeforeEditHandler)context.get("HeadCostCenterRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadCostCenterRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadCostCenterRefBeforeEditHandler();
  context.put("HeadCostCenterRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler getBodyFreeCustBeforeEditHandler(){
 if(context.get("BodyFreeCustBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler)context.get("BodyFreeCustBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyFreeCustBeforeEditHandler();
  context.put("BodyFreeCustBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyProjectBeforeEditHandler getBodyProjectBeforeEditHandler(){
 if(context.get("BodyProjectBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyProjectBeforeEditHandler)context.get("BodyProjectBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyProjectBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyProjectBeforeEditHandler();
  context.put("BodyProjectBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadSummaryBeforeEditHandler getHeadSummaryBeforeEditHandler(){
 if(context.get("HeadSummaryBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadSummaryBeforeEditHandler)context.get("HeadSummaryBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadSummaryBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadSummaryBeforeEditHandler();
  context.put("HeadSummaryBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodySummaryBeforeEditHandler getBodySummaryBeforeEditHandler(){
 if(context.get("BodySummaryBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodySummaryBeforeEditHandler)context.get("BodySummaryBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodySummaryBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodySummaryBeforeEditHandler();
  context.put("BodySummaryBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadCostCenterAfterEditHandler getHeadCostCenterAfterEditHandler(){
 if(context.get("HeadCostCenterAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadCostCenterAfterEditHandler)context.get("HeadCostCenterAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadCostCenterAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadCostCenterAfterEditHandler();
  context.put("HeadCostCenterAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyCostCenterAfterEditHandler getBodyCostCenterAfterEditHandler(){
 if(context.get("BodyCostCenterAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyCostCenterAfterEditHandler)context.get("BodyCostCenterAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyCostCenterAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyCostCenterAfterEditHandler();
  context.put("BodyCostCenterAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyTaxcodeBeforeEditHandler getBodyTaxcodeBeforeEditHandler(){
 if(context.get("BodyTaxcodeBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyTaxcodeBeforeEditHandler)context.get("BodyTaxcodeBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyTaxcodeBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyTaxcodeBeforeEditHandler();
  context.put("BodyTaxcodeBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadVersionRefBeforeEditHandler getHeadVersionRefBeforeEditHandler(){
 if(context.get("HeadVersionRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadVersionRefBeforeEditHandler)context.get("HeadVersionRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadVersionRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadVersionRefBeforeEditHandler();
  context.put("HeadVersionRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyVersionRefBeforeEditHandler getBodyVersionRefBeforeEditHandler(){
 if(context.get("BodyVersionRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyVersionRefBeforeEditHandler)context.get("BodyVersionRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyVersionRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyVersionRefBeforeEditHandler();
  context.put("BodyVersionRefBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadVersionRefAfterEditHandler getHeadVersionRefAfterEditHandler(){
 if(context.get("HeadVersionRefAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadVersionRefAfterEditHandler)context.get("HeadVersionRefAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadVersionRefAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadVersionRefAfterEditHandler();
  context.put("HeadVersionRefAfterEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyVersionRefAfterEditHandler getBodyVersionRefAfterEditHandler(){
 if(context.get("BodyVersionRefAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyVersionRefAfterEditHandler)context.get("BodyVersionRefAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyVersionRefAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyVersionRefAfterEditHandler();
  context.put("BodyVersionRefAfterEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.PayTermBodyBeforeEditHandler getPayTermBodyBeforeEditHandler(){
 if(context.get("PayTermBodyBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.PayTermBodyBeforeEditHandler)context.get("PayTermBodyBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.PayTermBodyBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.PayTermBodyBeforeEditHandler();
  context.put("PayTermBodyBeforeEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler getBodyChecNoRefBeforeEditHandler(){
 if(context.get("BodyChecNoRefBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler)context.get("BodyChecNoRefBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler();
  context.put("BodyChecNoRefBeforeEditHandler",bean);
  bean.setIsGatherbill(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler getBodyChecNoRefBeforeEditHandler4Pay(){
 if(context.get("BodyChecNoRefBeforeEditHandler4Pay")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler)context.get("BodyChecNoRefBeforeEditHandler4Pay");
  nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyChecNoRefBeforeEditHandler();
  context.put("BodyChecNoRefBeforeEditHandler4Pay",bean);
  bean.setIsGatherbill(false);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyCuspAfterEditHandler getBodyCuspAfterEditHandler(){
 if(context.get("BodyCuspAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyCuspAfterEditHandler)context.get("BodyCuspAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyCuspAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyCuspAfterEditHandler();
  context.put("BodyCuspAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyDeptAfterEditHandler getBodyDeptAfterEditHandler(){
 if(context.get("BodyDeptAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyDeptAfterEditHandler)context.get("BodyDeptAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyDeptAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyDeptAfterEditHandler();
  context.put("BodyDeptAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyPsnDocAfterEditHandler getBodyPsnDocAfterEditHandler(){
 if(context.get("BodyPsnDocAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyPsnDocAfterEditHandler)context.get("BodyPsnDocAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyPsnDocAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyPsnDocAfterEditHandler();
  context.put("BodyPsnDocAfterEditHandler",bean);
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyLocalMoneyAfterEditHandler getBodyLocalMoneyAfterEditHandler(){
 if(context.get("BodyLocalMoneyAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyLocalMoneyAfterEditHandler)context.get("BodyLocalMoneyAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyLocalMoneyAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyLocalMoneyAfterEditHandler();
  context.put("BodyLocalMoneyAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyRateAfterEditHandler getBodyRateAfterEditHandler(){
 if(context.get("BodyRateAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyRateAfterEditHandler)context.get("BodyRateAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyRateAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyRateAfterEditHandler();
  context.put("BodyRateAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyEuroAfterEditHandler getBodyEuroAfterEditHandler(){
 if(context.get("BodyEuroAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyEuroAfterEditHandler)context.get("BodyEuroAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyEuroAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyEuroAfterEditHandler();
  context.put("BodyEuroAfterEditHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadEuroAfterEditHandler getHeadEuroAfterEditHandler(){
 if(context.get("HeadEuroAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadEuroAfterEditHandler)context.get("HeadEuroAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadEuroAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadEuroAfterEditHandler();
  context.put("HeadEuroAfterEditHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyMoneyBalanceAfterEditHandler getBodyMoneyBalanceAfterEditHandler(){
 if(context.get("BodyMoneyBalanceAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyMoneyBalanceAfterEditHandler)context.get("BodyMoneyBalanceAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyMoneyBalanceAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyMoneyBalanceAfterEditHandler();
  context.put("BodyMoneyBalanceAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.EnableCtrlBodyAfterEditHandler getEnableCtrlBodyAfterEditHandler(){
 if(context.get("EnableCtrlBodyAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.EnableCtrlBodyAfterEditHandler)context.get("EnableCtrlBodyAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.EnableCtrlBodyAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.EnableCtrlBodyAfterEditHandler();
  context.put("EnableCtrlBodyAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadBankAccAfterEditHandler getHeadBankAccAfterEditHandler(){
 if(context.get("HeadBankAccAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadBankAccAfterEditHandler)context.get("HeadBankAccAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadBankAccAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadBankAccAfterEditHandler();
  context.put("HeadBankAccAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.BodyBankAccAfterEditHandler getBodyBankAccAfterEditHandler(){
 if(context.get("BodyBankAccAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.BodyBankAccAfterEditHandler)context.get("BodyBankAccAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.BodyBankAccAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.BodyBankAccAfterEditHandler();
  context.put("BodyBankAccAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadPsnDocAfterEditHandler getHeadPsnDocAfterEditHandler(){
 if(context.get("HeadPsnDocAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadPsnDocAfterEditHandler)context.get("HeadPsnDocAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadPsnDocAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadPsnDocAfterEditHandler();
  context.put("HeadPsnDocAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.card.CardBodyAfterRowEditHandler getCardBodyAfterRowEditHandler(){
 if(context.get("CardBodyAfterRowEditHandler")!=null)
 return (nc.ui.arap.viewhandler.card.CardBodyAfterRowEditHandler)context.get("CardBodyAfterRowEditHandler");
  nc.ui.arap.viewhandler.card.CardBodyAfterRowEditHandler bean = new nc.ui.arap.viewhandler.card.CardBodyAfterRowEditHandler();
  context.put("CardBodyAfterRowEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.card.CardBodyRowChangeHandler getCardBodyRowChangeHandler(){
 if(context.get("CardBodyRowChangeHandler")!=null)
 return (nc.ui.arap.viewhandler.card.CardBodyRowChangeHandler)context.get("CardBodyRowChangeHandler");
  nc.ui.arap.viewhandler.card.CardBodyRowChangeHandler bean = new nc.ui.arap.viewhandler.card.CardBodyRowChangeHandler();
  context.put("CardBodyRowChangeHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.RowNoMediator getRowNoMediator(){
 if(context.get("RowNoMediator")!=null)
 return (nc.ui.arap.view.RowNoMediator)context.get("RowNoMediator");
  nc.ui.arap.view.RowNoMediator bean = new nc.ui.arap.view.RowNoMediator();
  context.put("RowNoMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadCuspAfterEditHandler getHeadCuspAfterEditHandler(){
 if(context.get("HeadCuspAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadCuspAfterEditHandler)context.get("HeadCuspAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadCuspAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadCuspAfterEditHandler();
  context.put("HeadCuspAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadDeptAfterEditHandler getHeadDeptAfterEditHandler(){
 if(context.get("HeadDeptAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadDeptAfterEditHandler)context.get("HeadDeptAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadDeptAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadDeptAfterEditHandler();
  context.put("HeadDeptAfterEditHandler",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyAutoAddLineBeforeEditHandler getBodyAutoAddLineBeforeEditHandler(){
 if(context.get("BodyAutoAddLineBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyAutoAddLineBeforeEditHandler)context.get("BodyAutoAddLineBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyAutoAddLineBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyAutoAddLineBeforeEditHandler();
  context.put("BodyAutoAddLineBeforeEditHandler",bean);
  bean.setAddLineAction(getAddLineAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.TaxForm getTaxForm(){
 if(context.get("taxForm")!=null)
 return (nc.ui.arap.sideforms.TaxForm)context.get("taxForm");
  nc.ui.arap.sideforms.TaxForm bean = new nc.ui.arap.sideforms.TaxForm();
  context.put("taxForm",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.BodyPsnDocBeforeEditHandler getBodyPsnDocBeforeEditHandler(){
 if(context.get("BodyPsnDocBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.BodyPsnDocBeforeEditHandler)context.get("BodyPsnDocBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.BodyPsnDocBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.BodyPsnDocBeforeEditHandler();
  context.put("BodyPsnDocBeforeEditHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardbefore.HeadPsnDocBeforeEditHandler getHeadPsnDocBeforeEditHandler(){
 if(context.get("HeadPsnDocBeforeEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardbefore.HeadPsnDocBeforeEditHandler)context.get("HeadPsnDocBeforeEditHandler");
  nc.ui.arap.viewhandler.cardbefore.HeadPsnDocBeforeEditHandler bean = new nc.ui.arap.viewhandler.cardbefore.HeadPsnDocBeforeEditHandler();
  context.put("HeadPsnDocBeforeEditHandler",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.cardafter.HeadBillDateAfterEditHandler getHeadBillDateAfterEditHandler(){
 if(context.get("HeadBillDateAfterEditHandler")!=null)
 return (nc.ui.arap.viewhandler.cardafter.HeadBillDateAfterEditHandler)context.get("HeadBillDateAfterEditHandler");
  nc.ui.arap.viewhandler.cardafter.HeadBillDateAfterEditHandler bean = new nc.ui.arap.viewhandler.cardafter.HeadBillDateAfterEditHandler();
  context.put("HeadBillDateAfterEditHandler",bean);
  bean.setModel(getManageAppModel());
  bean.setHtob(getArapH2B());
  bean.setBillform(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.widget.BesideWidget getBesidewidget(){
 if(context.get("besidewidget")!=null)
 return (nc.ui.uif2.components.widget.BesideWidget)context.get("besidewidget");
  nc.ui.uif2.components.widget.BesideWidget bean = new nc.ui.uif2.components.widget.BesideWidget();
  context.put("besidewidget",bean);
  bean.setBesideWidgetlets(getManagedList7());
  bean.setContext(getContext());
  bean.setShowAllAction(getShowAllAction());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList7(){  List list = new ArrayList();  list.add(getTermForm());  list.add(getCreditForm());  return list;}

public nc.ui.uif2.components.widget.MultiBesideWidget getMultiBesideWidget(){
 if(context.get("multiBesideWidget")!=null)
 return (nc.ui.uif2.components.widget.MultiBesideWidget)context.get("multiBesideWidget");
  nc.ui.uif2.components.widget.MultiBesideWidget bean = new nc.ui.uif2.components.widget.MultiBesideWidget();
  context.put("multiBesideWidget",bean);
  bean.setBesideWidgetInfoMap(getManagedMap0());
  bean.setContext(getContext());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getI18nFB_11607(),getManagedList8());  map.put(getI18nFB_14d2bb9(),getManagedList9());  return map;}

private java.lang.String getI18nFB_11607(){
 if(context.get("nc.ui.uif2.I18nFB#11607")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#11607");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#11607",bean);  bean.setResDir("uif2");
  bean.setResId("BesideWidget-000001");
  bean.setDefaultValue("辅助信息");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#11607",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList8(){  List list = new ArrayList();  list.add(getTermForm());  list.add(getCreditForm());  list.add(getShowAllActionForm());  return list;}

private java.lang.String getI18nFB_14d2bb9(){
 if(context.get("nc.ui.uif2.I18nFB#14d2bb9")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#14d2bb9");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#14d2bb9",bean);  bean.setResDir("uif2");
  bean.setResId("ActionRegistry-000055");
  bean.setDefaultValue("审批");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#14d2bb9",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList9(){  List list = new ArrayList();  list.add(getBesideApproveForm());  list.add(getBesideHintMessageForm());  list.add(getBesideHistoryForm());  return list;}

public nc.ui.arap.sideforms.ShowAllAction getShowAllAction(){
 if(context.get("showAllAction")!=null)
 return (nc.ui.arap.sideforms.ShowAllAction)context.get("showAllAction");
  nc.ui.arap.sideforms.ShowAllAction bean = new nc.ui.arap.sideforms.ShowAllAction();
  context.put("showAllAction",bean);
  bean.setMediator(getSideFormMediator());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.ArapSideFormMediator getSideFormMediator(){
 if(context.get("sideFormMediator")!=null)
 return (nc.ui.arap.sideforms.ArapSideFormMediator)context.get("sideFormMediator");
  nc.ui.arap.sideforms.ArapSideFormMediator bean = new nc.ui.arap.sideforms.ArapSideFormMediator();
  context.put("sideFormMediator",bean);
  bean.setSideFormList(getManagedList10());
  bean.setCardPanel(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList10(){  List list = new ArrayList();  list.add(getTermForm());  list.add(getCreditForm());  return list;}

public nc.ui.arap.sideforms.TermForm getTermForm(){
 if(context.get("termForm")!=null)
 return (nc.ui.arap.sideforms.TermForm)context.get("termForm");
  nc.ui.arap.sideforms.TermForm bean = new nc.ui.arap.sideforms.TermForm();
  context.put("termForm",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.CreditForm getCreditForm(){
 if(context.get("creditForm")!=null)
 return (nc.ui.arap.sideforms.CreditForm)context.get("creditForm");
  nc.ui.arap.sideforms.CreditForm bean = new nc.ui.arap.sideforms.CreditForm();
  context.put("creditForm",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.ShowAllActionForm getShowAllActionForm(){
 if(context.get("showAllActionForm")!=null)
 return (nc.ui.arap.sideforms.ShowAllActionForm)context.get("showAllActionForm");
  nc.ui.arap.sideforms.ShowAllActionForm bean = new nc.ui.arap.sideforms.ShowAllActionForm();
  context.put("showAllActionForm",bean);
  bean.setShowAllAction(getShowAllAction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.ArapBesideApproveForm getBesideApproveForm(){
 if(context.get("besideApproveForm")!=null)
 return (nc.ui.arap.sideforms.ArapBesideApproveForm)context.get("besideApproveForm");
  nc.ui.arap.sideforms.ArapBesideApproveForm bean = new nc.ui.arap.sideforms.ArapBesideApproveForm();
  context.put("besideApproveForm",bean);
  bean.setModel(getManageAppModel());
  bean.setBesideapproveAction(getBillApproveAction());
  bean.setBesideunapproveaction(getBillUnApproveAction());
  bean.setBillSourceEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.ArapBesideHistoryForm getBesideHistoryForm(){
 if(context.get("besideHistoryForm")!=null)
 return (nc.ui.arap.sideforms.ArapBesideHistoryForm)context.get("besideHistoryForm");
  nc.ui.arap.sideforms.ArapBesideHistoryForm bean = new nc.ui.arap.sideforms.ArapBesideHistoryForm();
  context.put("besideHistoryForm",bean);
  bean.setModel(getManageAppModel());
  bean.setBillSourceEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.sideforms.ArapBesideHintMessageForm getBesideHintMessageForm(){
 if(context.get("besideHintMessageForm")!=null)
 return (nc.ui.arap.sideforms.ArapBesideHintMessageForm)context.get("besideHintMessageForm");
  nc.ui.arap.sideforms.ArapBesideHintMessageForm bean = new nc.ui.arap.sideforms.ArapBesideHintMessageForm();
  context.put("besideHintMessageForm",bean);
  bean.setModel(getManageAppModel());
  bean.setBillSourceEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillApproveAction getBillApproveAction(){
 if(context.get("BillApproveAction")!=null)
 return (nc.ui.arap.actions.BillApproveAction)context.get("BillApproveAction");
  nc.ui.arap.actions.BillApproveAction bean = new nc.ui.arap.actions.BillApproveAction();
  context.put("BillApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillUnApproveAction getBillUnApproveAction(){
 if(context.get("BillUnApproveAction")!=null)
 return (nc.ui.arap.actions.BillUnApproveAction)context.get("BillUnApproveAction");
  nc.ui.arap.actions.BillUnApproveAction bean = new nc.ui.arap.actions.BillUnApproveAction();
  context.put("BillUnApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.pub.remote.RetAddLoader getRetAddLoader(){
 if(context.get("retAddLoader")!=null)
 return (nc.ui.arap.pub.remote.RetAddLoader)context.get("retAddLoader");
  nc.ui.arap.pub.remote.RetAddLoader bean = new nc.ui.arap.pub.remote.RetAddLoader();
  context.put("retAddLoader",bean);
  bean.setBilltype(getDefBillType());
  bean.setTranstype(getNodeKeyQry());
  bean.prepare();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.ArapAddFlowMenuAciton getAddActionGroup(){
 if(context.get("addActionGroup")!=null)
 return (nc.ui.arap.actions.ArapAddFlowMenuAciton)context.get("addActionGroup");
  nc.ui.arap.actions.ArapAddFlowMenuAciton bean = new nc.ui.arap.actions.ArapAddFlowMenuAciton(getNodeKeyQry(),getBillFormEditor(),getInterceptor());  context.put("addActionGroup",bean);
  bean.setList(getListView());
  bean.setLoader(getRetAddLoader());
  bean.setModel(getManageAppModel());
  bean.setTransferBillViewProcessor(getTransferProcessor());
  bean.setOrgChangedImpl(getOrgchange());
  bean.setCode("add");
  bean.setName(getI18nFB_1718caf());
  bean.refreshChildBtns();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1718caf(){
 if(context.get("nc.ui.uif2.I18nFB#1718caf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1718caf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1718caf",bean);  bean.setResDir("pubapp_0");
  bean.setResId("0pubapp-0121");
  bean.setDefaultValue("新增");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1718caf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.arap.view.ArapTransferBillViewProcessor getTransferProcessor(){
 if(context.get("transferProcessor")!=null)
 return (nc.ui.arap.view.ArapTransferBillViewProcessor)context.get("transferProcessor");
  nc.ui.arap.view.ArapTransferBillViewProcessor bean = new nc.ui.arap.view.ArapTransferBillViewProcessor();
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

public nc.ui.arap.importable.interceptor.ExportActionInterceptor getExportActionInterceptor(){
 if(context.get("exportActionInterceptor")!=null)
 return (nc.ui.arap.importable.interceptor.ExportActionInterceptor)context.get("exportActionInterceptor");
  nc.ui.arap.importable.interceptor.ExportActionInterceptor bean = new nc.ui.arap.importable.interceptor.ExportActionInterceptor();
  context.put("exportActionInterceptor",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.interceptor.CompositeActionInterceptor getInterceptor(){
 if(context.get("interceptor")!=null)
 return (nc.ui.arap.actions.interceptor.CompositeActionInterceptor)context.get("interceptor");
  nc.ui.arap.actions.interceptor.CompositeActionInterceptor bean = new nc.ui.arap.actions.interceptor.CompositeActionInterceptor();
  context.put("interceptor",bean);
  bean.setInterceptors(getManagedList11());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getCaActionInterceptor());  list.add(getPauseTransactActionInterceptor());  list.add(getBillVersionActionInterceptor());  list.add(getOperPowerInterceptor());  list.add(getBillVerifyActionInterceptor());  list.add(getOrgCheckActionInterceptor());  return list;}

public nc.ui.arap.model.ArapPageModelDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.arap.model.ArapPageModelDataManager)context.get("modelDataManager");
  nc.ui.arap.model.ArapPageModelDataManager bean = new nc.ui.arap.model.ArapPageModelDataManager();
  context.put("modelDataManager",bean);
  bean.setModel(getManageAppModel());
  bean.setService(getManageModelService());
  bean.setPaginationDelegator(getPaginationDelegator());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList12());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList12(){  List list = new ArrayList();  list.add(getListActions());  list.add(getCardActions());  list.add(getVerifyActions());  list.add(getMakeupActions());  return list;}

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

public nc.ui.arap.scale.ArapBillCardPanelScaleProcessor getScaleProcessor(){
 if(context.get("scaleProcessor")!=null)
 return (nc.ui.arap.scale.ArapBillCardPanelScaleProcessor)context.get("scaleProcessor");
  nc.ui.arap.scale.ArapBillCardPanelScaleProcessor bean = new nc.ui.arap.scale.ArapBillCardPanelScaleProcessor();
  context.put("scaleProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.scale.ArapBillListPanelScaleProcessor getListScaleProcessor(){
 if(context.get("listScaleProcessor")!=null)
 return (nc.ui.arap.scale.ArapBillListPanelScaleProcessor)context.get("listScaleProcessor");
  nc.ui.arap.scale.ArapBillListPanelScaleProcessor bean = new nc.ui.arap.scale.ArapBillListPanelScaleProcessor();
  context.put("listScaleProcessor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.ArapOrgChanged getOrgchange(){
 if(context.get("orgchange")!=null)
 return (nc.ui.arap.viewhandler.ArapOrgChanged)context.get("orgchange");
  nc.ui.arap.viewhandler.ArapOrgChanged bean = new nc.ui.arap.viewhandler.ArapOrgChanged();
  context.put("orgchange",bean);
  bean.setBcpsp(getScaleProcessor());
  bean.setHtob(getArapH2B());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_10dcb23());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_10dcb23(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#10dcb23")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#10dcb23");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#10dcb23",bean);
  bean.setTabs(getManagedList13());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList13(){  List list = new ArrayList();  list.add(getVSNode_1eb0a79());  list.add(getHSNode_70cf05());  list.add(getCNode_1b4fcad());  list.add(getCNode_13465e5());  return list;}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_1eb0a79(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#1eb0a79")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#1eb0a79");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#1eb0a79",bean);
  bean.setUp(getCNode_e3be01());
  bean.setDown(getCNode_72090());
  bean.setShowMode("NoDivider");
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_e3be01(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#e3be01")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#e3be01");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#e3be01",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_72090(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#72090")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#72090");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#72090",bean);
  bean.setName(getI18nFB_1cf51db());
  bean.setComponent(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1cf51db(){
 if(context.get("nc.ui.uif2.I18nFB#1cf51db")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1cf51db");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1cf51db",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0136");
  bean.setDefaultValue("卡片");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1cf51db",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_70cf05(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#70cf05")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#70cf05");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#70cf05",bean);
  bean.setLeft(getCNode_1fcb5e6());
  bean.setRight(getVSNode_83f1b1());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1fcb5e6(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1fcb5e6")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1fcb5e6");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1fcb5e6",bean);
  bean.setComponent(getQueryAreaShell());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_83f1b1(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#83f1b1")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#83f1b1");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#83f1b1",bean);
  bean.setUp(getCNode_a100e());
  bean.setDown(getCNode_571a5d());
  bean.setShowMode("NoDivider");
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_a100e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#a100e")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#a100e");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#a100e",bean);
  bean.setComponent(getQueryInfo());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_571a5d(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#571a5d")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#571a5d");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#571a5d",bean);
  bean.setName(getI18nFB_a443ad());
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_a443ad(){
 if(context.get("nc.ui.uif2.I18nFB#a443ad")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#a443ad");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#a443ad",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0135");
  bean.setDefaultValue("列表");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#a443ad",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1b4fcad(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1b4fcad")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1b4fcad");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1b4fcad",bean);
  bean.setName(getI18nFB_275ab8());
  bean.setComponent(getVerifyui());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_275ab8(){
 if(context.get("nc.ui.uif2.I18nFB#275ab8")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#275ab8");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#275ab8",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0140");
  bean.setDefaultValue("核销界面");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#275ab8",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_13465e5(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#13465e5")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#13465e5");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#13465e5",bean);
  bean.setName(getI18nFB_138e8af());
  bean.setComponent(getMakeupEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_138e8af(){
 if(context.get("nc.ui.uif2.I18nFB#138e8af")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#138e8af");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#138e8af",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0141");
  bean.setDefaultValue("补差面板");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#138e8af",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

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

public nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell getQueryAreaShell(){
 if(context.get("queryAreaShell")!=null)
 return (nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell)context.get("queryAreaShell");
  nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell bean = new nc.ui.pubapp.uif2app.tangramlayout.UEQueryAreaShell();
  context.put("queryAreaShell",bean);
  bean.setQueryAreaCreator(getQueryAction());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel getCardInfoPnl(){
 if(context.get("cardInfoPnl")!=null)
 return (nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel)context.get("cardInfoPnl");
  nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel bean = new nc.ui.pubapp.uif2app.tangramlayout.UECardLayoutToolbarPanel();
  context.put("cardInfoPnl",bean);
  bean.setActions(getManagedList14());
  bean.setTitleAction(getReturnaction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList14(){  List list = new ArrayList();  list.add(getDocumentManage());  list.add(getActionsBarSeparator());  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  list.add(getActionsBarSeparator());  list.add(getBillHeadZoomAction_1a5e5b4());  return list;}

private nc.ui.arap.actions.BillHeadZoomAction getBillHeadZoomAction_1a5e5b4(){
 if(context.get("nc.ui.arap.actions.BillHeadZoomAction#1a5e5b4")!=null)
 return (nc.ui.arap.actions.BillHeadZoomAction)context.get("nc.ui.arap.actions.BillHeadZoomAction#1a5e5b4");
  nc.ui.arap.actions.BillHeadZoomAction bean = new nc.ui.arap.actions.BillHeadZoomAction();
  context.put("nc.ui.arap.actions.BillHeadZoomAction#1a5e5b4",bean);
  bean.setModel(getManageAppModel());
  bean.setBillForm(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.arap.actions.ArapReturnAction getReturnaction(){
 if(context.get("returnaction")!=null)
 return (nc.ui.arap.actions.ArapReturnAction)context.get("returnaction");
  nc.ui.arap.actions.ArapReturnAction bean = new nc.ui.arap.actions.ArapReturnAction();
  context.put("returnaction",bean);
  bean.setGoComponent(getListView());
  bean.setSaveAction(getSaveAction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.commom.OppUIContainer getVerifyui(){
 if(context.get("verifyui")!=null)
 return (nc.ui.arap.commom.OppUIContainer)context.get("verifyui");
  nc.ui.arap.commom.OppUIContainer bean = new nc.ui.arap.commom.OppUIContainer();
  context.put("verifyui",bean);
  bean.setClosingListener(getClosingListener());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.BillManageModel getMakeupAppModel(){
 if(context.get("makeupAppModel")!=null)
 return (nc.ui.pubapp.uif2app.model.BillManageModel)context.get("makeupAppModel");
  nc.ui.pubapp.uif2app.model.BillManageModel bean = new nc.ui.pubapp.uif2app.model.BillManageModel();
  context.put("makeupAppModel",bean);
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy getMakeupValueManager(){
 if(context.get("makeupValueManager")!=null)
 return (nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy)context.get("makeupValueManager");
  nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy bean = new nc.ui.pubapp.uif2app.view.BillCardAllDataValueStrategy();
  context.put("makeupValueManager",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.NodeKeyQry getMakeupNodeKeyQry(){
 if(context.get("makeupNodeKeyQry")!=null)
 return (nc.ui.arap.view.NodeKeyQry)context.get("makeupNodeKeyQry");
  nc.ui.arap.view.NodeKeyQry bean = new nc.ui.arap.view.NodeKeyQry();
  context.put("makeupNodeKeyQry",bean);
  bean.setModel(getMakeupAppModel());
  bean.setDefNodekey(getDefNodeKey());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.ArapBillCardForm getMakeupEditor(){
 if(context.get("makeupEditor")!=null)
 return (nc.ui.arap.view.ArapBillCardForm)context.get("makeupEditor");
  nc.ui.arap.view.ArapBillCardForm bean = new nc.ui.arap.view.ArapBillCardForm();
  context.put("makeupEditor",bean);
  bean.setModel(getMakeupAppModel());
  bean.setComponentValueManager(getMakeupValueManager());
  bean.setNodekeyQry(getMakeupNodeKeyQry());
  bean.setClosingListener(getClosingListener());
  bean.setAutoAddLine(false);
  bean.setTemplateNotNullValidate(true);
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_8bae17());
  bean.initRealUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_8bae17(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#8bae17")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#8bae17");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#8bae17",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList15());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList15(){  List list = new ArrayList();  list.add(getCardUserdefitemQueryParam());  list.add(getCardUserdefitemQueryParam1());  return list;}

public nc.ui.arap.actions.BodyVerifyAction getOnBodyVerify(){
 if(context.get("onBodyVerify")!=null)
 return (nc.ui.arap.actions.BodyVerifyAction)context.get("onBodyVerify");
  nc.ui.arap.actions.BodyVerifyAction bean = new nc.ui.arap.actions.BodyVerifyAction();
  context.put("onBodyVerify",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getManageAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
  bean.setOppui(getVerifyui());
  bean.setListView(getListView());
  bean.setVerifyui(getVerifyui());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.WholeBillVerifyAction getOnWholeBillVerifyAction(){
 if(context.get("onWholeBillVerifyAction")!=null)
 return (nc.ui.arap.actions.WholeBillVerifyAction)context.get("onWholeBillVerifyAction");
  nc.ui.arap.actions.WholeBillVerifyAction bean = new nc.ui.arap.actions.WholeBillVerifyAction();
  context.put("onWholeBillVerifyAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getManageAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
  bean.setListView(getListView());
  bean.setOppui(getVerifyui());
  bean.setVerifyui(getVerifyui());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getVerifyActions(){
 if(context.get("verifyActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("verifyActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getVerifyui());  context.put("verifyActions",bean);
  bean.setActions(getManagedList16());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList16(){  List list = new ArrayList();  list.add(getVerifyMakeupAction());  list.add(getVerifyAllocationAction());  list.add(getVerifyIntimeAction());  list.add(getVerifyFilterAction());  list.add(getVerifyAllSelectedAction());  list.add(getVerifyAllCancelAction());  list.add(getVerifyGoBackAction());  list.add(getVerifyLinkedQueryAction());  return list;}

public nc.ui.arap.actions.VerifyMakeupAction getVerifyMakeupAction(){
 if(context.get("verifyMakeupAction")!=null)
 return (nc.ui.arap.actions.VerifyMakeupAction)context.get("verifyMakeupAction");
  nc.ui.arap.actions.VerifyMakeupAction bean = new nc.ui.arap.actions.VerifyMakeupAction();
  context.put("verifyMakeupAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setMakeupEditor(getMakeupEditor());
  bean.setContext(getContext());
  bean.setModel(getMakeupAppModel());
  bean.setOrgChangedImpl(getOrgchange());
  bean.setCardPanel(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyAllocationAction getVerifyAllocationAction(){
 if(context.get("verifyAllocationAction")!=null)
 return (nc.ui.arap.actions.VerifyAllocationAction)context.get("verifyAllocationAction");
  nc.ui.arap.actions.VerifyAllocationAction bean = new nc.ui.arap.actions.VerifyAllocationAction();
  context.put("verifyAllocationAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyIntimeAction getVerifyIntimeAction(){
 if(context.get("verifyIntimeAction")!=null)
 return (nc.ui.arap.actions.VerifyIntimeAction)context.get("verifyIntimeAction");
  nc.ui.arap.actions.VerifyIntimeAction bean = new nc.ui.arap.actions.VerifyIntimeAction();
  context.put("verifyIntimeAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
  bean.setBillFormEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyFilterAction getVerifyFilterAction(){
 if(context.get("verifyFilterAction")!=null)
 return (nc.ui.arap.actions.VerifyFilterAction)context.get("verifyFilterAction");
  nc.ui.arap.actions.VerifyFilterAction bean = new nc.ui.arap.actions.VerifyFilterAction();
  context.put("verifyFilterAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyAllSelectedAction getVerifyAllSelectedAction(){
 if(context.get("verifyAllSelectedAction")!=null)
 return (nc.ui.arap.actions.VerifyAllSelectedAction)context.get("verifyAllSelectedAction");
  nc.ui.arap.actions.VerifyAllSelectedAction bean = new nc.ui.arap.actions.VerifyAllSelectedAction();
  context.put("verifyAllSelectedAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyAllCancelAction getVerifyAllCancelAction(){
 if(context.get("verifyAllCancelAction")!=null)
 return (nc.ui.arap.actions.VerifyAllCancelAction)context.get("verifyAllCancelAction");
  nc.ui.arap.actions.VerifyAllCancelAction bean = new nc.ui.arap.actions.VerifyAllCancelAction();
  context.put("verifyAllCancelAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyGoBackAction getVerifyGoBackAction(){
 if(context.get("verifyGoBackAction")!=null)
 return (nc.ui.arap.actions.VerifyGoBackAction)context.get("verifyGoBackAction");
  nc.ui.arap.actions.VerifyGoBackAction bean = new nc.ui.arap.actions.VerifyGoBackAction();
  context.put("verifyGoBackAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setBillFormEditor(getBillFormEditor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.VerifyLinkedQueryAction getVerifyLinkedQueryAction(){
 if(context.get("verifyLinkedQueryAction")!=null)
 return (nc.ui.arap.actions.VerifyLinkedQueryAction)context.get("verifyLinkedQueryAction");
  nc.ui.arap.actions.VerifyLinkedQueryAction bean = new nc.ui.arap.actions.VerifyLinkedQueryAction();
  context.put("verifyLinkedQueryAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setModel(getMakeupAppModel());
  bean.setCardPanel(getBillFormEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.MakeupCanelAction getMakeupCanelAction(){
 if(context.get("makeupCanelAction")!=null)
 return (nc.ui.arap.actions.MakeupCanelAction)context.get("makeupCanelAction");
  nc.ui.arap.actions.MakeupCanelAction bean = new nc.ui.arap.actions.MakeupCanelAction();
  context.put("makeupCanelAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setVerifyui(getVerifyui());
  bean.setModel(getMakeupAppModel());
  bean.setMakeupEditor(getMakeupEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.MakeupConfirmAction getMakeupConfirmAction(){
 if(context.get("makeupConfirmAction")!=null)
 return (nc.ui.arap.actions.MakeupConfirmAction)context.get("makeupConfirmAction");
  nc.ui.arap.actions.MakeupConfirmAction bean = new nc.ui.arap.actions.MakeupConfirmAction();
  context.put("makeupConfirmAction",bean);
  bean.setInterceptor(getInterceptor());
  bean.setBillFormEditor(getBillFormEditor());
  bean.setModel(getMakeupAppModel());
  bean.setMakeupEditor(getMakeupEditor());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getMakeupActions(){
 if(context.get("makeupActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("makeupActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getMakeupEditor());  context.put("makeupActions",bean);
  bean.setActions(getManagedList17());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList17(){  List list = new ArrayList();  list.add(getMakeupConfirmAction());  list.add(getMakeupCanelAction());  return list;}

public nc.funcnode.ui.action.SeparatorAction getCurrNullAction(){
 if(context.get("currNullAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("currNullAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("currNullAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.CreditCheckDecoratorAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.arap.actions.CreditCheckDecoratorAction)context.get("saveAction");
  nc.ui.arap.actions.CreditCheckDecoratorAction bean = new nc.ui.arap.actions.CreditCheckDecoratorAction(getSaveActionReal());  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.arap.actions.BillEditAction)context.get("editAction");
  nc.ui.arap.actions.BillEditAction bean = new nc.ui.arap.actions.BillEditAction();
  context.put("editAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillDeleteAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.arap.actions.BillDeleteAction)context.get("deleteAction");
  nc.ui.arap.actions.BillDeleteAction bean = new nc.ui.arap.actions.BillDeleteAction();
  context.put("deleteAction",bean);
  bean.setEditor(getBillFormEditor());
  bean.setModel(getManageAppModel());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillCancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.arap.actions.BillCancelAction)context.get("cancelAction");
  nc.ui.arap.actions.BillCancelAction bean = new nc.ui.arap.actions.BillCancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillConferCancelAction getConferCancelAction(){
 if(context.get("conferCancelAction")!=null)
 return (nc.ui.arap.actions.BillConferCancelAction)context.get("conferCancelAction");
  nc.ui.arap.actions.BillConferCancelAction bean = new nc.ui.arap.actions.BillConferCancelAction();
  context.put("conferCancelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillAddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.arap.actions.BillAddAction)context.get("addAction");
  nc.ui.arap.actions.BillAddAction bean = new nc.ui.arap.actions.BillAddAction();
  context.put("addAction",bean);
  bean.setModel(getManageAppModel());
  bean.setShowUpComponent(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
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
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.query.ArapBillQueryConditionDLGInitializer getQueryDLGInitializer(){
 if(context.get("queryDLGInitializer")!=null)
 return (nc.ui.arap.query.ArapBillQueryConditionDLGInitializer)context.get("queryDLGInitializer");
  nc.ui.arap.query.ArapBillQueryConditionDLGInitializer bean = new nc.ui.arap.query.ArapBillQueryConditionDLGInitializer();
  context.put("queryDLGInitializer",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType(getDefBillType());
  bean.setIsInit("N");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.ArapQueryTemplateContainer getQueryTemplateContainer(){
 if(context.get("queryTemplateContainer")!=null)
 return (nc.ui.arap.view.ArapQueryTemplateContainer)context.get("queryTemplateContainer");
  nc.ui.arap.view.ArapQueryTemplateContainer bean = new nc.ui.arap.view.ArapQueryTemplateContainer();
  context.put("queryTemplateContainer",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller getRemoteCallCombinatorCaller(){
 if(context.get("remoteCallCombinatorCaller")!=null)
 return (nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller)context.get("remoteCallCombinatorCaller");
  nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller bean = new nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller();
  context.put("remoteCallCombinatorCaller",bean);
  bean.setRemoteCallers(getManagedList18());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList18(){  List list = new ArrayList();  list.add(getQueryTemplateContainer());  list.add(getUserdefitemContainer());  list.add(getTemplateContainer());  list.add(getRetAddLoader());  return list;}

public nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getListInterceptor(){
 if(context.get("listInterceptor")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("listInterceptor");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("listInterceptor",bean);
  bean.setShowUpComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction)context.get("queryAction");
  nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction bean = new nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction();
  context.put("queryAction",bean);
  bean.setDataManager(getModelDataManager());
  bean.setQryCondDLGInitializer(getQueryDLGInitializer());
  bean.setModel(getManageAppModel());
  bean.setTemplateContainer(getQueryTemplateContainer());
  bean.setInterceptor(getListInterceptor());
  bean.setShowUpComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillLinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.arap.actions.BillLinkQueryAction)context.get("linkQueryAction");
  nc.ui.arap.actions.BillLinkQueryAction bean = new nc.ui.arap.actions.BillLinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.LinkVoucharQueryAction getLinkVoucharQueryAction(){
 if(context.get("linkVoucharQueryAction")!=null)
 return (nc.ui.arap.actions.LinkVoucharQueryAction)context.get("linkVoucharQueryAction");
  nc.ui.arap.actions.LinkVoucharQueryAction bean = new nc.ui.arap.actions.LinkVoucharQueryAction();
  context.put("linkVoucharQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.LinkBConferQueryAction getBconferQueryAction(){
 if(context.get("bconferQueryAction")!=null)
 return (nc.ui.arap.actions.LinkBConferQueryAction)context.get("bconferQueryAction");
  nc.ui.arap.actions.LinkBConferQueryAction bean = new nc.ui.arap.actions.LinkBConferQueryAction();
  context.put("bconferQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContainer(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillFlowStateQueryAction getFlowStateQueryAction(){
 if(context.get("flowStateQueryAction")!=null)
 return (nc.ui.arap.actions.BillFlowStateQueryAction)context.get("flowStateQueryAction");
  nc.ui.arap.actions.BillFlowStateQueryAction bean = new nc.ui.arap.actions.BillFlowStateQueryAction();
  context.put("flowStateQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContainer(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillBalanceLinkQueryAction getBalanceLinkQueryAction(){
 if(context.get("balanceLinkQueryAction")!=null)
 return (nc.ui.arap.actions.BillBalanceLinkQueryAction)context.get("balanceLinkQueryAction");
  nc.ui.arap.actions.BillBalanceLinkQueryAction bean = new nc.ui.arap.actions.BillBalanceLinkQueryAction();
  context.put("balanceLinkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillTbbLinkAction getTbbLinkAction(){
 if(context.get("tbbLinkAction")!=null)
 return (nc.ui.arap.actions.BillTbbLinkAction)context.get("tbbLinkAction");
  nc.ui.arap.actions.BillTbbLinkAction bean = new nc.ui.arap.actions.BillTbbLinkAction();
  context.put("tbbLinkAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillDealLinkQueryAction getDealLinkQueryAction(){
 if(context.get("dealLinkQueryAction")!=null)
 return (nc.ui.arap.actions.BillDealLinkQueryAction)context.get("dealLinkQueryAction");
  nc.ui.arap.actions.BillDealLinkQueryAction bean = new nc.ui.arap.actions.BillDealLinkQueryAction();
  context.put("dealLinkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setContainer(getContainer());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.LinkTermQueryAction getTermLinkQueryAction(){
 if(context.get("termLinkQueryAction")!=null)
 return (nc.ui.arap.actions.LinkTermQueryAction)context.get("termLinkQueryAction");
  nc.ui.arap.actions.LinkTermQueryAction bean = new nc.ui.arap.actions.LinkTermQueryAction();
  context.put("termLinkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.InitBillCancelCloseAction getInitCancelCloseAction(){
 if(context.get("initCancelCloseAction")!=null)
 return (nc.ui.arap.actions.InitBillCancelCloseAction)context.get("initCancelCloseAction");
  nc.ui.arap.actions.InitBillCancelCloseAction bean = new nc.ui.arap.actions.InitBillCancelCloseAction();
  context.put("initCancelCloseAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillRefreshSingAction getRefreshSingAction(){
 if(context.get("refreshSingAction")!=null)
 return (nc.ui.arap.actions.BillRefreshSingAction)context.get("refreshSingAction");
  nc.ui.arap.actions.BillRefreshSingAction bean = new nc.ui.arap.actions.BillRefreshSingAction();
  context.put("refreshSingAction",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillApproveAction getApproveAction(){
 if(context.get("ApproveAction")!=null)
 return (nc.ui.arap.actions.BillApproveAction)context.get("ApproveAction");
  nc.ui.arap.actions.BillApproveAction bean = new nc.ui.arap.actions.BillApproveAction();
  context.put("ApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillUnApproveAction getUnApproveAction(){
 if(context.get("UnApproveAction")!=null)
 return (nc.ui.arap.actions.BillUnApproveAction)context.get("UnApproveAction");
  nc.ui.arap.actions.BillUnApproveAction bean = new nc.ui.arap.actions.BillUnApproveAction();
  context.put("UnApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillListApproveAction getApproveListBatchAction(){
 if(context.get("ApproveListBatchAction")!=null)
 return (nc.ui.arap.actions.BillListApproveAction)context.get("ApproveListBatchAction");
  nc.ui.arap.actions.BillListApproveAction bean = new nc.ui.arap.actions.BillListApproveAction();
  context.put("ApproveListBatchAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setIsbatch("Y");
  bean.setInterceptor(getInterceptor());
  bean.setTpaProgressUtil(getTPAProgressUtil_52a2c1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.components.progress.TPAProgressUtil getTPAProgressUtil_52a2c1(){
 if(context.get("nc.ui.uif2.components.progress.TPAProgressUtil#52a2c1")!=null)
 return (nc.ui.uif2.components.progress.TPAProgressUtil)context.get("nc.ui.uif2.components.progress.TPAProgressUtil#52a2c1");
  nc.ui.uif2.components.progress.TPAProgressUtil bean = new nc.ui.uif2.components.progress.TPAProgressUtil();
  context.put("nc.ui.uif2.components.progress.TPAProgressUtil#52a2c1",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillListUnApproveAction getUnApproveListBatchAction(){
 if(context.get("UnApproveListBatchAction")!=null)
 return (nc.ui.arap.actions.BillListUnApproveAction)context.get("UnApproveListBatchAction");
  nc.ui.arap.actions.BillListUnApproveAction bean = new nc.ui.arap.actions.BillListUnApproveAction();
  context.put("UnApproveListBatchAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setIsbatch("Y");
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillListApproveAction getApproveListAction(){
 if(context.get("ApproveListAction")!=null)
 return (nc.ui.arap.actions.BillListApproveAction)context.get("ApproveListAction");
  nc.ui.arap.actions.BillListApproveAction bean = new nc.ui.arap.actions.BillListApproveAction();
  context.put("ApproveListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setIsbatch("N");
  bean.setInterceptor(getInterceptor());
  bean.setTpaProgressUtil(getTPAProgressUtil_13b45b0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.components.progress.TPAProgressUtil getTPAProgressUtil_13b45b0(){
 if(context.get("nc.ui.uif2.components.progress.TPAProgressUtil#13b45b0")!=null)
 return (nc.ui.uif2.components.progress.TPAProgressUtil)context.get("nc.ui.uif2.components.progress.TPAProgressUtil#13b45b0");
  nc.ui.uif2.components.progress.TPAProgressUtil bean = new nc.ui.uif2.components.progress.TPAProgressUtil();
  context.put("nc.ui.uif2.components.progress.TPAProgressUtil#13b45b0",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillListUnApproveAction getUnApproveListAction(){
 if(context.get("UnApproveListAction")!=null)
 return (nc.ui.arap.actions.BillListUnApproveAction)context.get("UnApproveListAction");
  nc.ui.arap.actions.BillListUnApproveAction bean = new nc.ui.arap.actions.BillListUnApproveAction();
  context.put("UnApproveListAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getListView());
  bean.setIsbatch("N");
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillTempSaveAction getTempSaveAction(){
 if(context.get("tempSaveAction")!=null)
 return (nc.ui.arap.actions.BillTempSaveAction)context.get("tempSaveAction");
  nc.ui.arap.actions.BillTempSaveAction bean = new nc.ui.arap.actions.BillTempSaveAction();
  context.put("tempSaveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.arap.actions.BillCopyAction)context.get("copyAction");
  nc.ui.arap.actions.BillCopyAction bean = new nc.ui.arap.actions.BillCopyAction();
  context.put("copyAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillWriteBackAction getWriteBack(){
 if(context.get("writeBack")!=null)
 return (nc.ui.arap.actions.BillWriteBackAction)context.get("writeBack");
  nc.ui.arap.actions.BillWriteBackAction bean = new nc.ui.arap.actions.BillWriteBackAction();
  context.put("writeBack",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.DocumentManageAction getDocumentManage(){
 if(context.get("documentManage")!=null)
 return (nc.ui.arap.actions.DocumentManageAction)context.get("documentManage");
  nc.ui.arap.actions.DocumentManageAction bean = new nc.ui.arap.actions.DocumentManageAction();
  context.put("documentManage",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.RentAffiliatedAction getRentAffiliated(){
 if(context.get("rentAffiliated")!=null)
 return (nc.ui.arap.actions.RentAffiliatedAction)context.get("rentAffiliated");
  nc.ui.arap.actions.RentAffiliatedAction bean = new nc.ui.arap.actions.RentAffiliatedAction();
  context.put("rentAffiliated",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.NoteRegisterAction getNoteRegister(){
 if(context.get("noteRegister")!=null)
 return (nc.ui.arap.actions.NoteRegisterAction)context.get("noteRegister");
  nc.ui.arap.actions.NoteRegisterAction bean = new nc.ui.arap.actions.NoteRegisterAction();
  context.put("noteRegister",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.MakeAlterBillAction getMakeAlterBill(){
 if(context.get("makeAlterBill")!=null)
 return (nc.ui.arap.actions.MakeAlterBillAction)context.get("makeAlterBill");
  nc.ui.arap.actions.MakeAlterBillAction bean = new nc.ui.arap.actions.MakeAlterBillAction();
  context.put("makeAlterBill",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PauseTransactAction getPauseTransact(){
 if(context.get("pauseTransact")!=null)
 return (nc.ui.arap.actions.PauseTransactAction)context.get("pauseTransact");
  nc.ui.arap.actions.PauseTransactAction bean = new nc.ui.arap.actions.PauseTransactAction();
  context.put("pauseTransact",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.CancelPauseTransactAction getCancelPauseTransact(){
 if(context.get("cancelPauseTransact")!=null)
 return (nc.ui.arap.actions.CancelPauseTransactAction)context.get("cancelPauseTransact");
  nc.ui.arap.actions.CancelPauseTransactAction bean = new nc.ui.arap.actions.CancelPauseTransactAction();
  context.put("cancelPauseTransact",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillPrintAction getPrintBill(){
 if(context.get("printBill")!=null)
 return (nc.ui.arap.actions.BillPrintAction)context.get("printBill");
  nc.ui.arap.actions.BillPrintAction bean = new nc.ui.arap.actions.BillPrintAction();
  context.put("printBill",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
  bean.setTaxForm(getTaxForm());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.OfficialPrintAction getOfficialPrint(){
 if(context.get("officialPrint")!=null)
 return (nc.ui.arap.actions.OfficialPrintAction)context.get("officialPrint");
  nc.ui.arap.actions.OfficialPrintAction bean = new nc.ui.arap.actions.OfficialPrintAction();
  context.put("officialPrint",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
  bean.setTaxForm(getTaxForm());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.CancelPrintAction getCancelPrint(){
 if(context.get("cancelPrint")!=null)
 return (nc.ui.arap.actions.CancelPrintAction)context.get("cancelPrint");
  nc.ui.arap.actions.CancelPrintAction bean = new nc.ui.arap.actions.CancelPrintAction();
  context.put("cancelPrint",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PrintListAction getPrintList(){
 if(context.get("printList")!=null)
 return (nc.ui.arap.actions.PrintListAction)context.get("printList");
  nc.ui.arap.actions.PrintListAction bean = new nc.ui.arap.actions.PrintListAction();
  context.put("printList",bean);
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PreviewVoucherAction getPrevVoucherAction(){
 if(context.get("prevVoucherAction")!=null)
 return (nc.ui.arap.actions.PreviewVoucherAction)context.get("prevVoucherAction");
  nc.ui.arap.actions.PreviewVoucherAction bean = new nc.ui.arap.actions.PreviewVoucherAction();
  context.put("prevVoucherAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.MadeBillAction getMadeBillAction(){
 if(context.get("madeBillAction")!=null)
 return (nc.ui.arap.actions.MadeBillAction)context.get("madeBillAction");
  nc.ui.arap.actions.MadeBillAction bean = new nc.ui.arap.actions.MadeBillAction();
  context.put("madeBillAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillPrepayAction getPrepayAction(){
 if(context.get("prepayAction")!=null)
 return (nc.ui.arap.actions.BillPrepayAction)context.get("prepayAction");
  nc.ui.arap.actions.BillPrepayAction bean = new nc.ui.arap.actions.BillPrepayAction();
  context.put("prepayAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getBillOperateActionGroup(){
 if(context.get("billOperateActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billOperateActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billOperateActionGroup",bean);
  bean.setCode("billOperate");
  bean.setName(getI18nFB_5c2671());
  bean.setActions(getManagedList19());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_5c2671(){
 if(context.get("nc.ui.uif2.I18nFB#5c2671")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#5c2671");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#5c2671",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0137");
  bean.setDefaultValue("单据操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#5c2671",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList19(){  List list = new ArrayList();  list.add(getDeleteAction());  list.add(getEditAction());  list.add(getTempSaveAction());  list.add(getWriteBack());  list.add(getDocumentManage());  return list;}

public nc.funcnode.ui.action.MenuAction getBillOperateActionGroup_Record(){
 if(context.get("billOperateActionGroup_Record")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billOperateActionGroup_Record");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billOperateActionGroup_Record",bean);
  bean.setCode("billOperate");
  bean.setName(getI18nFB_1dff30f());
  bean.setActions(getManagedList20());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1dff30f(){
 if(context.get("nc.ui.uif2.I18nFB#1dff30f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1dff30f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1dff30f",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0137");
  bean.setDefaultValue("单据操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1dff30f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList20(){  List list = new ArrayList();  list.add(getDeleteAction());  list.add(getEditAction());  list.add(getTempSaveAction());  list.add(getDocumentManage());  return list;}

public nc.funcnode.ui.action.MenuAction getBillOperateActionGroup_Init(){
 if(context.get("billOperateActionGroup_Init")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billOperateActionGroup_Init");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billOperateActionGroup_Init",bean);
  bean.setCode("billOperate");
  bean.setName(getI18nFB_1ce8adb());
  bean.setActions(getManagedList21());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1ce8adb(){
 if(context.get("nc.ui.uif2.I18nFB#1ce8adb")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1ce8adb");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1ce8adb",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0137");
  bean.setDefaultValue("单据操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1ce8adb",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList21(){  List list = new ArrayList();  list.add(getDeleteAction());  list.add(getEditAction());  list.add(getDocumentManage());  return list;}

public nc.funcnode.ui.action.MenuAction getBillOperateActionGroup_List(){
 if(context.get("billOperateActionGroup_List")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billOperateActionGroup_List");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billOperateActionGroup_List",bean);
  bean.setCode("billOperate_List");
  bean.setName(getI18nFB_fe4862());
  bean.setActions(getManagedList22());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_fe4862(){
 if(context.get("nc.ui.uif2.I18nFB#fe4862")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#fe4862");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#fe4862",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0137");
  bean.setDefaultValue("单据操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#fe4862",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList22(){  List list = new ArrayList();  list.add(getDeleteAction());  list.add(getDocumentManage());  return list;}

public nc.funcnode.ui.action.MenuAction getCloseOperateAction(){
 if(context.get("closeOperateAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("closeOperateAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("closeOperateAction",bean);
  bean.setCode("billClose");
  bean.setName(getI18nFB_422ada());
  bean.setActions(getManagedList23());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_422ada(){
 if(context.get("nc.ui.uif2.I18nFB#422ada")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#422ada");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#422ada",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0138");
  bean.setDefaultValue("关闭操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#422ada",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList23(){  List list = new ArrayList();  return list;}

public nc.funcnode.ui.action.GroupAction getBillApproveActionGroup(){
 if(context.get("billApproveActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("billApproveActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("billApproveActionGroup",bean);
  bean.setCode("billApproveActionGroup");
  bean.setName(getI18nFB_b59a0());
  bean.setActions(getManagedList24());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_b59a0(){
 if(context.get("nc.ui.uif2.I18nFB#b59a0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#b59a0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#b59a0",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0142");
  bean.setDefaultValue("审批");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#b59a0",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList24(){  List list = new ArrayList();  list.add(getApproveAction());  list.add(getUnApproveAction());  list.add(getFlowStateQueryAction());  return list;}

public nc.funcnode.ui.action.GroupAction getBillApproveActionGroup_List(){
 if(context.get("billApproveActionGroup_List")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("billApproveActionGroup_List");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("billApproveActionGroup_List",bean);
  bean.setCode("billApproveActionGroup_List");
  bean.setName(getI18nFB_1294174());
  bean.setActions(getManagedList25());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1294174(){
 if(context.get("nc.ui.uif2.I18nFB#1294174")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1294174");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1294174",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0142");
  bean.setDefaultValue("审批");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1294174",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList25(){  List list = new ArrayList();  list.add(getApproveListAction());  list.add(getUnApproveListAction());  list.add(getApproveListBatchAction());  list.add(getUnApproveListBatchAction());  list.add(getFlowStateQueryAction());  return list;}

public nc.funcnode.ui.action.GroupAction getBillVerifyActionGroup(){
 if(context.get("billVerifyActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("billVerifyActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("billVerifyActionGroup",bean);
  bean.setCode("billVerify");
  bean.setName(getI18nFB_1885b95());
  bean.setActions(getManagedList26());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1885b95(){
 if(context.get("nc.ui.uif2.I18nFB#1885b95")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1885b95");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1885b95",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0143");
  bean.setDefaultValue("即时核销");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1885b95",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList26(){  List list = new ArrayList();  list.add(getOnBodyVerify());  list.add(getOnWholeBillVerifyAction());  return list;}

public nc.funcnode.ui.action.GroupAction getPauseTransactActionGroup(){
 if(context.get("pauseTransactActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("pauseTransactActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("pauseTransactActionGroup",bean);
  bean.setCode("pauseTransact");
  bean.setName(getI18nFB_134b6dc());
  bean.setActions(getManagedList27());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_134b6dc(){
 if(context.get("nc.ui.uif2.I18nFB#134b6dc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#134b6dc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#134b6dc",bean);  bean.setResDir("2006pub_0");
  bean.setResId("02006pub-0644");
  bean.setDefaultValue("挂起操作");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#134b6dc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList27(){  List list = new ArrayList();  list.add(getPauseTransact());  list.add(getCancelPauseTransact());  return list;}

public nc.funcnode.ui.action.GroupAction getPrintOperateActionGroup(){
 if(context.get("printOperateActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printOperateActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printOperateActionGroup",bean);
  bean.setCode("printOperateActionGroup");
  bean.setName(getI18nFB_1d164b5());
  bean.setActions(getManagedList28());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1d164b5(){
 if(context.get("nc.ui.uif2.I18nFB#1d164b5")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1d164b5");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1d164b5",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0139");
  bean.setDefaultValue("打印");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1d164b5",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList28(){  List list = new ArrayList();  list.add(getPrintBill());  list.add(getPrintPreview());  list.add(getPrintOutput());  list.add(getSeparatorAction());  list.add(getOfficialPrint());  list.add(getCancelPrint());  return list;}

public nc.funcnode.ui.action.GroupAction getPrintOperateActionGroup_List(){
 if(context.get("printOperateActionGroup_List")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printOperateActionGroup_List");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printOperateActionGroup_List",bean);
  bean.setCode("printOperateActionGroup_List");
  bean.setName(getI18nFB_2ac8d5());
  bean.setActions(getManagedList29());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_2ac8d5(){
 if(context.get("nc.ui.uif2.I18nFB#2ac8d5")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#2ac8d5");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#2ac8d5",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0139");
  bean.setDefaultValue("打印");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#2ac8d5",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList29(){  List list = new ArrayList();  list.add(getPrintBill());  list.add(getPrintPreview());  list.add(getPrintOutput());  list.add(getSeparatorAction());  list.add(getOfficialPrint());  list.add(getCancelPrint());  list.add(getSeparatorAction());  list.add(getPrintList());  return list;}

public nc.funcnode.ui.action.MenuAction getRelatedQueryActionGroup(){
 if(context.get("relatedQueryActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("relatedQueryActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("relatedQueryActionGroup",bean);
  bean.setCode("relatedQuery");
  bean.setName(getI18nFB_12b0b11());
  bean.setActions(getManagedList30());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_12b0b11(){
 if(context.get("nc.ui.uif2.I18nFB#12b0b11")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#12b0b11");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#12b0b11",bean);  bean.setResDir("pubapp_0");
  bean.setResId("0pubapp-0025");
  bean.setDefaultValue("联查");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#12b0b11",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList30(){  List list = new ArrayList();  list.add(getLinkQueryAction());  list.add(getFlowStateQueryAction());  list.add(getBalanceLinkQueryAction());  list.add(getDealLinkQueryAction());  list.add(getLinkVoucharQueryAction());  list.add(getBconferQueryAction());  list.add(getTermLinkQueryAction());  list.add(getTbbLinkAction());  return list;}

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

public nc.ui.arap.ref.FiBillTypeRefModel getTransTypeRefModel(){
 if(context.get("transTypeRefModel")!=null)
 return (nc.ui.arap.ref.FiBillTypeRefModel)context.get("transTypeRefModel");
  nc.ui.arap.ref.FiBillTypeRefModel bean = new nc.ui.arap.ref.FiBillTypeRefModel();
  context.put("transTypeRefModel",bean);
  bean.setNodeKeyQry(getNodeKeyQry());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.ReceiptCheckAction getReceiptCheckAction(){
 if(context.get("receiptCheckAction")!=null)
 return (nc.ui.arap.actions.ReceiptCheckAction)context.get("receiptCheckAction");
  nc.ui.arap.actions.ReceiptCheckAction bean = new nc.ui.arap.actions.ReceiptCheckAction();
  context.put("receiptCheckAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.ReceiptScanAction getReceiptScanAction(){
 if(context.get("receiptScanAction")!=null)
 return (nc.ui.arap.actions.ReceiptScanAction)context.get("receiptScanAction");
  nc.ui.arap.actions.ReceiptScanAction bean = new nc.ui.arap.actions.ReceiptScanAction();
  context.put("receiptScanAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getReceiptMenuAction(){
 if(context.get("receiptMenuAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("receiptMenuAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("receiptMenuAction",bean);
  bean.setCode("Image");
  bean.setName(getI18nFB_1467734());
  bean.setActions(getManagedList31());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1467734(){
 if(context.get("nc.ui.uif2.I18nFB#1467734")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1467734");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1467734",bean);  bean.setResDir("common");
  bean.setResId("arapcommonv6-0180");
  bean.setDefaultValue("影像");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1467734",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList31(){  List list = new ArrayList();  list.add(getReceiptCheckAction());  list.add(getReceiptScanAction());  return list;}

public java.lang.String getDefMoneyField(){
 if(context.get("defMoneyField")!=null)
 return (java.lang.String)context.get("defMoneyField");
  java.lang.String bean = new java.lang.String("money_cr");  context.put("defMoneyField",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getDefBillType(){
 if(context.get("defBillType")!=null)
 return (java.lang.String)context.get("defBillType");
  java.lang.String bean = new java.lang.String("F1");  context.put("defBillType",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getDefNodeKey(){
 if(context.get("defNodeKey")!=null)
 return (java.lang.String)context.get("defNodeKey");
  java.lang.String bean = new java.lang.String("D1");  context.put("defNodeKey",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getMdFullnameHead(){
 if(context.get("mdFullnameHead")!=null)
 return (java.lang.String)context.get("mdFullnameHead");
  java.lang.String bean = new java.lang.String("arap.payablebill");  context.put("mdFullnameHead",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getMdFullnameBody(){
 if(context.get("mdFullnameBody")!=null)
 return (java.lang.String)context.get("mdFullnameBody");
  java.lang.String bean = new java.lang.String("arap.payableitem");  context.put("mdFullnameBody",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.PayableLineDefValUtil getIArapLineDefValUtil(){
 if(context.get("iArapLineDefValUtil")!=null)
 return (nc.ui.arap.actions.PayableLineDefValUtil)context.get("iArapLineDefValUtil");
  nc.ui.arap.actions.PayableLineDefValUtil bean = new nc.ui.arap.actions.PayableLineDefValUtil();
  context.put("iArapLineDefValUtil",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.model.PayableBillAppModelService getManageModelService(){
 if(context.get("ManageModelService")!=null)
 return (nc.ui.arap.model.PayableBillAppModelService)context.get("ManageModelService");
  nc.ui.arap.model.PayableBillAppModelService bean = new nc.ui.arap.model.PayableBillAppModelService();
  context.put("ManageModelService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.model.PayableBillPaginationQueryService getPaginationQueryService(){
 if(context.get("paginationQueryService")!=null)
 return (nc.ui.arap.model.PayableBillPaginationQueryService)context.get("paginationQueryService");
  nc.ui.arap.model.PayableBillPaginationQueryService bean = new nc.ui.arap.model.PayableBillPaginationQueryService();
  context.put("paginationQueryService",bean);
  bean.setPageSize(10);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.view.PayableBillDefValue getBillDefVauleItf(){
 if(context.get("billDefVauleItf")!=null)
 return (nc.ui.arap.view.PayableBillDefValue)context.get("billDefVauleItf");
  nc.ui.arap.view.PayableBillDefValue bean = new nc.ui.arap.view.PayableBillDefValue();
  context.put("billDefVauleItf",bean);
  bean.setEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.viewhandler.CreditBillCalculatorFields getCalFldsItf(){
 if(context.get("calFldsItf")!=null)
 return (nc.ui.arap.viewhandler.CreditBillCalculatorFields)context.get("calFldsItf");
  nc.ui.arap.viewhandler.CreditBillCalculatorFields bean = new nc.ui.arap.viewhandler.CreditBillCalculatorFields();
  context.put("calFldsItf",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.TransTypeAction getTranstype(){
 if(context.get("transtype")!=null)
 return (nc.ui.arap.actions.TransTypeAction)context.get("transtype");
  nc.ui.arap.actions.TransTypeAction bean = new nc.ui.arap.actions.TransTypeAction();
  context.put("transtype",bean);
  bean.setEditor(getBillFormEditor());
  bean.setModel(getManageAppModel());
  bean.setTransTypeRefModel(getTransTypeRefModel());
  bean.setWherepart(" bd_billtype.parentbilltype ='F1' and pk_group != 'global00000000000000'");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.actions.BillSaveAction getSaveActionReal(){
 if(context.get("saveActionReal")!=null)
 return (nc.ui.arap.actions.BillSaveAction)context.get("saveActionReal");
  nc.ui.arap.actions.BillSaveAction bean = new nc.ui.arap.actions.BillSaveAction();
  context.put("saveActionReal",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getBillFormEditor());
  bean.setInterceptor(getInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.model.AppEventHandlerMediator getAppEventHandlerMediator(){
 if(context.get("AppEventHandlerMediator")!=null)
 return (nc.ui.pubapp.uif2app.model.AppEventHandlerMediator)context.get("AppEventHandlerMediator");
  nc.ui.pubapp.uif2app.model.AppEventHandlerMediator bean = new nc.ui.pubapp.uif2app.model.AppEventHandlerMediator();
  context.put("AppEventHandlerMediator",bean);
  bean.setModel(getManageAppModel());
  bean.setHandlerMap(getManagedMap1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent",getManagedList32());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent",getManagedList33());  map.put("nc.ui.arap.viewhandler.CardBodyAfterEditEvent",getManagedList34());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent",getManagedList35());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent",getManagedList36());  map.put("nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent",getManagedList37());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList38());  map.put("nc.ui.pubapp.uif2app.event.card.CardBodyRowChangedEvent",getManagedList39());  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList40());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadRowChangedEvent",getManagedList41());  map.put("nc.ui.pubapp.uif2app.event.list.ListHeadDataChangedEvent",getManagedList42());  map.put("nc.ui.pubapp.uif2app.mediator.mutiltrans.NodekeyEvent",getManagedList43());  return map;}

private List getManagedList32(){  List list = new ArrayList();  list.add(getBodyBankAccBeforeEditHandler());  list.add(getBodyAccountRefBeforeEditHandler());  list.add(getBodyFreeCustBeforeEditHandler());  list.add(getBodyProjectBeforeEditHandler());  list.add(getBodyCrossCheckBeforeHandler());  list.add(getBodySummaryBeforeEditHandler());  list.add(getBodyVersionRefBeforeEditHandler());  list.add(getOtherOrgBodyBeforeEditHandler());  list.add(getBodyCostCenterRefBeforeEditHandler());  list.add(getBodyTaxcodeBeforeEditHandler());  list.add(getBodyPsnDocBeforeEditHandler());  return list;}

private List getManagedList33(){  List list = new ArrayList();  list.add(getBodyAutoAddLineBeforeEditHandler());  list.add(getBodyMaterialAfterEditHandler());  list.add(getCardAfterEditCalculateHandler());  list.add(getBodyPsnDocAfterEditHandler());  list.add(getBodyCurrTypeAfterEditHandler());  list.add(getBodyLocalMoneyAfterEditHandler());  list.add(getBodyRateAfterEditHandler());  list.add(getBodyEuroAfterEditHandler());  list.add(getOtherOrgBodyAfterEditHandler());  list.add(getBodyMoneyBalanceAfterEditHandler());  list.add(getEnableCtrlBodyAfterEditHandler());  list.add(getBodyFundplanAfterEditHandler());  list.add(getBodyCuspAfterEditHandler());  list.add(getBodyBankAccAfterEditHandler());  list.add(getBodyDeptAfterEditHandler());  list.add(getBodyVersionRefAfterEditHandler());  list.add(getBodyCostCenterAfterEditHandler());  return list;}

private List getManagedList34(){  List list = new ArrayList();  list.add(getBodyEuroAfterEditHandler());  list.add(getBodyCuspAfterEditHandler());  return list;}

private List getManagedList35(){  List list = new ArrayList();  list.add(getHeadBankAccBeforeEditHandler());  list.add(getOtherOrgHeadBeforeEditHandler());  list.add(getHeadCrossCheckBeforeHandler());  list.add(getHeadSummaryBeforeEditHandler());  list.add(getHeadVersionRefBeforeEditHandler());  list.add(getHeadCostCenterRefBeforeEditHandler());  list.add(getHeadAccountRefBeforeEditHandler());  list.add(getHeadPsnDocBeforeEditHandler());  return list;}

private List getManagedList36(){  List list = new ArrayList();  list.add(getCardBodyAfterRowEditHandler());  return list;}

private List getManagedList37(){  List list = new ArrayList();  list.add(getHBRelationAfterEditHandler());  list.add(getHeadCurrTypeAfterEditHandler());  list.add(getHeadPsnDocAfterEditHandler());  list.add(getOtherOrgHeadAfterEdithandler());  list.add(getHeadFundplanAfterEditHandler());  list.add(getObjTypeHeadAfterEdithandler());  list.add(getHeadCuspAfterEditHandler());  list.add(getHeadDeptAfterEditHandler());  list.add(getHeadVersionRefAfterEditHandler());  list.add(getHeadEuroAfterEditHandler());  list.add(getHeadCostCenterAfterEditHandler());  list.add(getHeadBillDateAfterEditHandler());  return list;}

private List getManagedList38(){  List list = new ArrayList();  list.add(getLoadBillCardTemplate());  return list;}

private List getManagedList39(){  List list = new ArrayList();  list.add(getCardBodyRowChangeHandler());  list.add(getSideFormMediator());  return list;}

private List getManagedList40(){  List list = new ArrayList();  list.add(getLoadBillListTemplate());  return list;}

private List getManagedList41(){  List list = new ArrayList();  list.add(getListHeadRowChangeListener());  list.add(getSideFormMediator());  return list;}

private List getManagedList42(){  List list = new ArrayList();  list.add(getListHeadModelListener());  return list;}

private List getManagedList43(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getTranstype());  return list;}

public nc.vo.arappub.calculator.data.RelationItemForCal_Credit getRelationItemForCal(){
 if(context.get("relationItemForCal")!=null)
 return (nc.vo.arappub.calculator.data.RelationItemForCal_Credit)context.get("relationItemForCal");
  nc.vo.arappub.calculator.data.RelationItemForCal_Credit bean = new nc.vo.arappub.calculator.data.RelationItemForCal_Credit();
  context.put("relationItemForCal",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.listener.PayableBillRelationQueryDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.arap.listener.PayableBillRelationQueryDataListener)context.get("InitDataListener");
  nc.ui.arap.listener.PayableBillRelationQueryDataListener bean = new nc.ui.arap.listener.PayableBillRelationQueryDataListener();
  context.put("InitDataListener",bean);
  bean.setBillFormEditor(getBillFormEditor());
  bean.setListview(getListView());
  bean.setContext(getContext());
  bean.setModel(getManageAppModel());
  bean.setVoClassName("nc.vo.arap.payable.AggPayableBillVO");
  bean.setAutoShowUpComponent(getBillFormEditor());
  bean.setQueryAction(getQueryAction());
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
  bean.setName(getI18nFB_13bfbad());
  bean.setActions(getManagedList44());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_13bfbad(){
 if(context.get("nc.ui.uif2.I18nFB#13bfbad")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#13bfbad");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#13bfbad",bean);  bean.setResDir("2006pub_0");
  bean.setResId("02006pub-0643");
  bean.setDefaultValue("辅助功能");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#13bfbad",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList44(){  List list = new ArrayList();  list.add(getMakeAlterBill());  list.add(getDocumentManage());  return list;}

public nc.funcnode.ui.action.MenuAction getBillAssistantActionGroup_List(){
 if(context.get("billAssistantActionGroup_List")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("billAssistantActionGroup_List");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("billAssistantActionGroup_List",bean);
  bean.setCode("billAssistant");
  bean.setName(getI18nFB_10854ef());
  bean.setActions(getManagedList45());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_10854ef(){
 if(context.get("nc.ui.uif2.I18nFB#10854ef")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#10854ef");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#10854ef",bean);  bean.setResDir("2006pub_0");
  bean.setResId("02006pub-0643");
  bean.setDefaultValue("辅助功能");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#10854ef",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList45(){  List list = new ArrayList();  list.add(getDocumentManage());  return list;}

public nc.ui.arap.importable.PayableRecordImportablePanel getImportableEditor(){
 if(context.get("importableEditor")!=null)
 return (nc.ui.arap.importable.PayableRecordImportablePanel)context.get("importableEditor");
  nc.ui.arap.importable.PayableRecordImportablePanel bean = new nc.ui.arap.importable.PayableRecordImportablePanel();
  context.put("importableEditor",bean);
  bean.setUiEditor(getBillFormEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.importable.action.ArapImportAction getImportAction(){
 if(context.get("importAction")!=null)
 return (nc.ui.arap.importable.action.ArapImportAction)context.get("importAction");
  nc.ui.arap.importable.action.ArapImportAction bean = new nc.ui.arap.importable.action.ArapImportAction();
  context.put("importAction",bean);
  bean.setModel(getManageAppModel());
  bean.setImportableEditor(getImportableEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.arap.importable.ArapExportAction getExportAction(){
 if(context.get("exportAction")!=null)
 return (nc.ui.arap.importable.ArapExportAction)context.get("exportAction");
  nc.ui.arap.importable.ArapExportAction bean = new nc.ui.arap.importable.ArapExportAction();
  context.put("exportAction",bean);
  bean.setImportableEditor(getImportableEditor());
  bean.setModel(getManageAppModel());
  bean.setInterceptor(getExportActionInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getImportexportActionGroup(){
 if(context.get("importexportActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("importexportActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("importexportActionGroup",bean);
  bean.setCode("ImportExportMenu");
  bean.setName(getI18nFB_23bf());
  bean.setActions(getManagedList46());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_23bf(){
 if(context.get("nc.ui.uif2.I18nFB#23bf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#23bf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#23bf",bean);  bean.setResDir("2006pub_0");
  bean.setResId("02006pub-0654");
  bean.setDefaultValue("导入导出");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#23bf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList46(){  List list = new ArrayList();  list.add(getImportAction());  list.add(getCurrNullAction());  list.add(getExportAction());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getListActions(){
 if(context.get("listActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("listActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("listActions",bean);
  bean.setActions(getManagedList47());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList47(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getCurrNullAction());  list.add(getQueryAction());  list.add(getRefreshAllAction());  list.add(getCurrNullAction());  list.add(getTranstype());  list.add(getBillAssistantActionGroup());  list.add(getCurrNullAction());  list.add(getReceiptMenuAction());  list.add(getCurrNullAction());  list.add(getRelatedQueryActionGroup());  list.add(getCurrNullAction());  list.add(getPrevVoucherAction());  list.add(getCurrNullAction());  list.add(getImportexportActionGroup());  list.add(getPrintOperateActionGroup_List());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getCardActions(){
 if(context.get("cardActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("cardActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getBillFormEditor());  context.put("cardActions",bean);
  bean.setActions(getManagedList48());
  bean.setEditActions(getManagedList49());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList48(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getCurrNullAction());  list.add(getQueryAction());  list.add(getRefreshSingAction());  list.add(getCurrNullAction());  list.add(getTranstype());  list.add(getBillAssistantActionGroup());  list.add(getCurrNullAction());  list.add(getReceiptMenuAction());  list.add(getCurrNullAction());  list.add(getRelatedQueryActionGroup());  list.add(getCurrNullAction());  list.add(getPrevVoucherAction());  list.add(getCurrNullAction());  list.add(getImportexportActionGroup());  list.add(getPrintOperateActionGroup());  return list;}

private List getManagedList49(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getTempSaveAction());  list.add(getCurrNullAction());  list.add(getCancelAction());  list.add(getCurrNullAction());  list.add(getDocumentManage());  list.add(getReceiptMenuAction());  return list;}

}
