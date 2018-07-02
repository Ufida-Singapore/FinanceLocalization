package nc.ui.cmp.cash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class cashdraw_org extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.funcnode.ui.action.SeparatorAction getSeperatorAction(){
 if(context.get("seperatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("seperatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("seperatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseAddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseAddAction)context.get("addAction");
  nc.ui.cmp.base.actions.CmpBaseAddAction bean = new nc.ui.cmp.base.actions.CmpBaseAddAction();
  context.put("addAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setInterceptor(getCompositeActionInterceptor_10af67());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor getCompositeActionInterceptor_10af67(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#10af67")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#10af67");
  nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#10af67",bean);
  bean.setInterceptors(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getShowUpComponentInterceptor_5c1796());  return list;}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_5c1796(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#5c1796")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#5c1796");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#5c1796",bean);
  bean.setShowUpComponent(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseEditAction)context.get("editAction");
  nc.ui.cmp.base.actions.CmpBaseEditAction bean = new nc.ui.cmp.base.actions.CmpBaseEditAction();
  context.put("editAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setPowercheck(true);
  bean.setBillType("36S2");
  bean.setResourceCode("36S2");
  bean.setOperateCode("edit");
  bean.setBillCodeName("billno");
  bean.setInterceptor(getShowUpComponentInterceptor_16bb61a());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_16bb61a(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16bb61a")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16bb61a");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16bb61a",bean);
  bean.setShowUpComponent(getCardEditor());
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
  bean.setName(getI18nFB_807720());
  bean.setActions(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_807720(){
 if(context.get("nc.ui.uif2.I18nFB#807720")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#807720");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#807720",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("影像");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#807720",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList1(){  List list = new ArrayList();  list.add(getImageShowAction());  list.add(getImageScanAction());  return list;}

public nc.ui.cmp.bill.actions.CmpBillImageShowAction getImageShowAction(){
 if(context.get("imageShowAction")!=null)
 return (nc.ui.cmp.bill.actions.CmpBillImageShowAction)context.get("imageShowAction");
  nc.ui.cmp.bill.actions.CmpBillImageShowAction bean = new nc.ui.cmp.bill.actions.CmpBillImageShowAction();
  context.put("imageShowAction",bean);
  bean.setModel(getManageAppModel());
  bean.setPk_billtype("1001Z610000000000IIW");
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
  bean.setPk_billtype("1001Z610000000000IIW");
  bean.setCheckscanway("nc.imag.scan.service.CheckScanWay");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseDeleteAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseDeleteAction)context.get("deleteAction");
  nc.ui.cmp.base.actions.CmpBaseDeleteAction bean = new nc.ui.cmp.base.actions.CmpBaseDeleteAction();
  context.put("deleteAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setPowercheck(true);
  bean.setActionName("DELETE");
  bean.setBillType("36S2");
  bean.setBillCodeName("billno");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCopyAction)context.get("copyAction");
  nc.ui.cmp.base.actions.CmpBaseCopyAction bean = new nc.ui.cmp.base.actions.CmpBaseCopyAction();
  context.put("copyAction",bean);
  bean.setCopyActionProcessor(getCmpBaseCopyActionProcessor_d11a1());
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setInterceptor(getShowUpComponentInterceptor_7070());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor getCmpBaseCopyActionProcessor_d11a1(){
 if(context.get("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d11a1")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor)context.get("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d11a1");
  nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor bean = new nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor();
  context.put("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d11a1",bean);
  bean.setEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_7070(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#7070")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#7070");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#7070",bean);
  bean.setShowUpComponent(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseSaveAction)context.get("saveAction");
  nc.ui.cmp.base.actions.CmpBaseSaveAction bean = new nc.ui.cmp.base.actions.CmpBaseSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setActionName("SAVEBASE");
  bean.setBillType("36S2");
  bean.setBillVOName("nc.vo.cmp.cash.AggCashDrawVO");
  bean.setHeadVOName("nc.vo.cmp.cash.CashDrawVO");
  bean.setCheckedDate("inputdate");
  bean.setValidationService(getValidateService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.validation.CompositeValidation getValidateService(){
 if(context.get("validateService")!=null)
 return (nc.ui.pubapp.uif2app.validation.CompositeValidation)context.get("validateService");
  nc.ui.pubapp.uif2app.validation.CompositeValidation bean = new nc.ui.pubapp.uif2app.validation.CompositeValidation();
  context.put("validateService",bean);
  bean.setValidators(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getTemplateNotNullValidation_d60075());  list.add(getPowerSaveValidateService_1b809f5());  return list;}

private nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation getTemplateNotNullValidation_d60075(){
 if(context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d60075")!=null)
 return (nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation)context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d60075");
  nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation bean = new nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation();
  context.put("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#d60075",bean);
  bean.setBillForm(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerSaveValidateService getPowerSaveValidateService_1b809f5(){
 if(context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#1b809f5")!=null)
 return (nc.ui.pubapp.pub.power.PowerSaveValidateService)context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#1b809f5");
  nc.ui.pubapp.pub.power.PowerSaveValidateService bean = new nc.ui.pubapp.pub.power.PowerSaveValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerSaveValidateService#1b809f5",bean);
  bean.setInsertActionCode("save");
  bean.setEditActionCode("edit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseSaveAddAction getSaveAddAction(){
 if(context.get("saveAddAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseSaveAddAction)context.get("saveAddAction");
  nc.ui.cmp.base.actions.CmpBaseSaveAddAction bean = new nc.ui.cmp.base.actions.CmpBaseSaveAddAction();
  context.put("saveAddAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setAddAction(getAddAction());
  bean.setSaveAction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpCashSaveAndCommitAction getSaveSendApproveAction(){
 if(context.get("saveSendApproveAction")!=null)
 return (nc.ui.cmp.base.actions.CmpCashSaveAndCommitAction)context.get("saveSendApproveAction");
  nc.ui.cmp.base.actions.CmpCashSaveAndCommitAction bean = new nc.ui.cmp.base.actions.CmpCashSaveAndCommitAction();
  context.put("saveSendApproveAction",bean);
  bean.setSaveAction(getSaveAction());
  bean.setCommitAction(getCommitAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseSaveTempAction getSaveTempAction(){
 if(context.get("saveTempAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseSaveTempAction)context.get("saveTempAction");
  nc.ui.cmp.base.actions.CmpBaseSaveTempAction bean = new nc.ui.cmp.base.actions.CmpBaseSaveTempAction();
  context.put("saveTempAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseCancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCancelAction)context.get("cancelAction");
  nc.ui.cmp.base.actions.CmpBaseCancelAction bean = new nc.ui.cmp.base.actions.CmpBaseCancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tmpub.action.query.DefaultQueryAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.tmpub.action.query.DefaultQueryAction)context.get("queryAction");
  nc.ui.tmpub.action.query.DefaultQueryAction bean = new nc.ui.tmpub.action.query.DefaultQueryAction();
  context.put("queryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setInterceptor(getShowUpComponentInterceptor_195ddbd());
  bean.setQryCondDLGInitializer(getSalequoQryCondDLGInitializer());
  bean.setTemplateContainer(getQueryTemplateContainer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_195ddbd(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#195ddbd")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#195ddbd");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#195ddbd",bean);
  bean.setShowUpComponent(getListView());
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

public nc.ui.cmp.cash.query.CashDrawQryCondInitializer getSalequoQryCondDLGInitializer(){
 if(context.get("salequoQryCondDLGInitializer")!=null)
 return (nc.ui.cmp.cash.query.CashDrawQryCondInitializer)context.get("salequoQryCondDLGInitializer");
  nc.ui.cmp.cash.query.CashDrawQryCondInitializer bean = new nc.ui.cmp.cash.query.CashDrawQryCondInitializer();
  context.put("salequoQryCondDLGInitializer",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.QueryAreaShell getQueryArea(){
 if(context.get("queryArea")!=null)
 return (nc.ui.uif2.actions.QueryAreaShell)context.get("queryArea");
  nc.ui.uif2.actions.QueryAreaShell bean = new nc.ui.uif2.actions.QueryAreaShell();
  context.put("queryArea",bean);
  bean.setQueryArea(getQueryAction_created_10a835());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.queryarea.QueryArea getQueryAction_created_10a835(){
 if(context.get("queryAction.created#10a835")!=null)
 return (nc.ui.queryarea.QueryArea)context.get("queryAction.created#10a835");
  nc.ui.queryarea.QueryArea bean = getQueryAction().createQueryArea();
  context.put("queryAction.created#10a835",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseRefreshMutiAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseRefreshMutiAction)context.get("refreshAction");
  nc.ui.cmp.base.actions.CmpBaseRefreshMutiAction bean = new nc.ui.cmp.base.actions.CmpBaseRefreshMutiAction();
  context.put("refreshAction",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
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

public nc.funcnode.ui.action.GroupAction getCommitMenuAction(){
 if(context.get("commitMenuAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("commitMenuAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("commitMenuAction",bean);
  bean.setCode("commitMenuAction");
  bean.setName(getI18nFB_193579d());
  bean.setActions(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_193579d(){
 if(context.get("nc.ui.uif2.I18nFB#193579d")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#193579d");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#193579d",bean);  bean.setResDir("common");
  bean.setDefaultValue("提交");
  bean.setResId("UC001-0000029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#193579d",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList3(){  List list = new ArrayList();  list.add(getCommitAction());  list.add(getUnCommitAction());  return list;}

public nc.ui.cmp.base.actions.CmpBaseCommitAction getCommitAction(){
 if(context.get("commitAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCommitAction)context.get("commitAction");
  nc.ui.cmp.base.actions.CmpBaseCommitAction bean = new nc.ui.cmp.base.actions.CmpBaseCommitAction();
  context.put("commitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setBillType("36S2");
  bean.setActionName("SAVE");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_5d18b7());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_5d18b7(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#5d18b7")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#5d18b7");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#5d18b7",bean);
  bean.setActionCode("commit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseUnCommitAction getUnCommitAction(){
 if(context.get("unCommitAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseUnCommitAction)context.get("unCommitAction");
  nc.ui.cmp.base.actions.CmpBaseUnCommitAction bean = new nc.ui.cmp.base.actions.CmpBaseUnCommitAction();
  context.put("unCommitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setBillType("36S2");
  bean.setActionName("UNSAVEBILL");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_1b450());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_1b450(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#1b450")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#1b450");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#1b450",bean);
  bean.setActionCode("uncommit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getApproveActionGroup(){
 if(context.get("approveActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("approveActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("approveActionGroup",bean);
  bean.setCode("Approve");
  bean.setName(getI18nFB_1e50be3());
  bean.setActions(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1e50be3(){
 if(context.get("nc.ui.uif2.I18nFB#1e50be3")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1e50be3");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1e50be3",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607cash-0071");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1e50be3",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList4(){  List list = new ArrayList();  list.add(getApproveAction());  list.add(getUnApproveAction());  list.add(getQueryAuditFlowAction());  return list;}

public nc.ui.cmp.base.actions.CmpBaseApproveAction getApproveAction(){
 if(context.get("approveAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseApproveAction)context.get("approveAction");
  nc.ui.cmp.base.actions.CmpBaseApproveAction bean = new nc.ui.cmp.base.actions.CmpBaseApproveAction();
  context.put("approveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setApproveService(getApproveService());
  bean.setValidaotrList(getManagedList5());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList5(){  List list = new ArrayList();  list.add(getCashDrawApproveValidator_6f7991());  return list;}

private nc.ui.cmp.cash.validator.CashDrawApproveValidator getCashDrawApproveValidator_6f7991(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawApproveValidator#6f7991")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawApproveValidator)context.get("nc.ui.cmp.cash.validator.CashDrawApproveValidator#6f7991");
  nc.ui.cmp.cash.validator.CashDrawApproveValidator bean = new nc.ui.cmp.cash.validator.CashDrawApproveValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawApproveValidator#6f7991",bean);
  bean.setActionCode("approve");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseUnApproveAction getUnApproveAction(){
 if(context.get("unApproveAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseUnApproveAction)context.get("unApproveAction");
  nc.ui.cmp.base.actions.CmpBaseUnApproveAction bean = new nc.ui.cmp.base.actions.CmpBaseUnApproveAction();
  context.put("unApproveAction",bean);
  bean.setModel(getManageAppModel());
  bean.setUnApproveService(getApproveService());
  bean.setValidaotrList(getManagedList6());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList6(){  List list = new ArrayList();  list.add(getCashDrawUnApproveValidator_965e69());  return list;}

private nc.ui.cmp.cash.validator.CashDrawUnApproveValidator getCashDrawUnApproveValidator_965e69(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawUnApproveValidator#965e69")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawUnApproveValidator)context.get("nc.ui.cmp.cash.validator.CashDrawUnApproveValidator#965e69");
  nc.ui.cmp.cash.validator.CashDrawUnApproveValidator bean = new nc.ui.cmp.cash.validator.CashDrawUnApproveValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawUnApproveValidator#965e69",bean);
  bean.setActionCode("unapprove");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tm.approve.model.DefaultApproveServie getApproveService(){
 if(context.get("approveService")!=null)
 return (nc.ui.tm.approve.model.DefaultApproveServie)context.get("approveService");
  nc.ui.tm.approve.model.DefaultApproveServie bean = new nc.ui.tm.approve.model.DefaultApproveServie();
  context.put("approveService",bean);
  bean.setBilltype("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSubmitActionGroup(){
 if(context.get("submitActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("submitActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("submitActionGroup",bean);
  bean.setCode("Submit");
  bean.setName(getI18nFB_107e5f());
  bean.setActions(getManagedList7());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_107e5f(){
 if(context.get("nc.ui.uif2.I18nFB#107e5f")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#107e5f");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#107e5f",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607cash-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#107e5f",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList7(){  List list = new ArrayList();  list.add(getSubmitAction());  list.add(getUnSubmitAction());  return list;}

public nc.ui.cmp.base.actions.CmpBaseSubmitAction getSubmitAction(){
 if(context.get("submitAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseSubmitAction)context.get("submitAction");
  nc.ui.cmp.base.actions.CmpBaseSubmitAction bean = new nc.ui.cmp.base.actions.CmpBaseSubmitAction();
  context.put("submitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setSubmitService(getSubmitService());
  bean.setValidaotrList(getManagedList8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList8(){  List list = new ArrayList();  list.add(getCashDrawSubmitValidator_d8a27f());  return list;}

private nc.ui.cmp.cash.validator.CashDrawSubmitValidator getCashDrawSubmitValidator_d8a27f(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawSubmitValidator#d8a27f")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawSubmitValidator)context.get("nc.ui.cmp.cash.validator.CashDrawSubmitValidator#d8a27f");
  nc.ui.cmp.cash.validator.CashDrawSubmitValidator bean = new nc.ui.cmp.cash.validator.CashDrawSubmitValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawSubmitValidator#d8a27f",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseUnSubmitAction getUnSubmitAction(){
 if(context.get("unSubmitAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseUnSubmitAction)context.get("unSubmitAction");
  nc.ui.cmp.base.actions.CmpBaseUnSubmitAction bean = new nc.ui.cmp.base.actions.CmpBaseUnSubmitAction();
  context.put("unSubmitAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setUnSubmitService(getSubmitService());
  bean.setValidaotrList(getManagedList9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList9(){  List list = new ArrayList();  list.add(getCashDrawUnSubmitValidator_944255());  return list;}

private nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator getCashDrawUnSubmitValidator_944255(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator#944255")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator)context.get("nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator#944255");
  nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator bean = new nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawUnSubmitValidator#944255",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.modelservice.CmpAppModelService getSubmitService(){
 if(context.get("submitService")!=null)
 return (nc.ui.cmp.base.modelservice.CmpAppModelService)context.get("submitService");
  nc.ui.cmp.base.modelservice.CmpAppModelService bean = new nc.ui.cmp.base.modelservice.CmpAppModelService();
  context.put("submitService",bean);
  bean.setBilltype("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSettleActionGroup(){
 if(context.get("settleActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("settleActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("settleActionGroup",bean);
  bean.setCode("Settle");
  bean.setName(getI18nFB_18ef049());
  bean.setActions(getManagedList10());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_18ef049(){
 if(context.get("nc.ui.uif2.I18nFB#18ef049")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#18ef049");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#18ef049",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607cash-0022");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#18ef049",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList10(){  List list = new ArrayList();  list.add(getSettleAction());  list.add(getUnSettleAction());  return list;}

public nc.ui.cmp.base.actions.CmpBaseSettleAction getSettleAction(){
 if(context.get("settleAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseSettleAction)context.get("settleAction");
  nc.ui.cmp.base.actions.CmpBaseSettleAction bean = new nc.ui.cmp.base.actions.CmpBaseSettleAction();
  context.put("settleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setSettleService(getSettleService());
  bean.setValidaotrList(getManagedList11());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getCashDrawSettleValidator_1417fc9());  return list;}

private nc.ui.cmp.cash.validator.CashDrawSettleValidator getCashDrawSettleValidator_1417fc9(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawSettleValidator#1417fc9")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawSettleValidator)context.get("nc.ui.cmp.cash.validator.CashDrawSettleValidator#1417fc9");
  nc.ui.cmp.cash.validator.CashDrawSettleValidator bean = new nc.ui.cmp.cash.validator.CashDrawSettleValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawSettleValidator#1417fc9",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseUnSettleAction getUnSettleAction(){
 if(context.get("unSettleAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseUnSettleAction)context.get("unSettleAction");
  nc.ui.cmp.base.actions.CmpBaseUnSettleAction bean = new nc.ui.cmp.base.actions.CmpBaseUnSettleAction();
  context.put("unSettleAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setUnSettleService(getSettleService());
  bean.setValidaotrList(getManagedList12());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList12(){  List list = new ArrayList();  list.add(getCashDrawUnSettleValidator_12ca143());  return list;}

private nc.ui.cmp.cash.validator.CashDrawUnSettleValidator getCashDrawUnSettleValidator_12ca143(){
 if(context.get("nc.ui.cmp.cash.validator.CashDrawUnSettleValidator#12ca143")!=null)
 return (nc.ui.cmp.cash.validator.CashDrawUnSettleValidator)context.get("nc.ui.cmp.cash.validator.CashDrawUnSettleValidator#12ca143");
  nc.ui.cmp.cash.validator.CashDrawUnSettleValidator bean = new nc.ui.cmp.cash.validator.CashDrawUnSettleValidator();
  context.put("nc.ui.cmp.cash.validator.CashDrawUnSettleValidator#12ca143",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.modelservice.CmpAppModelService getSettleService(){
 if(context.get("settleService")!=null)
 return (nc.ui.cmp.base.modelservice.CmpAppModelService)context.get("settleService");
  nc.ui.cmp.base.modelservice.CmpAppModelService bean = new nc.ui.cmp.base.modelservice.CmpAppModelService();
  context.put("settleService",bean);
  bean.setBilltype("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseMakeBillAction getMakeBillAction(){
 if(context.get("makeBillAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseMakeBillAction)context.get("makeBillAction");
  nc.ui.cmp.base.actions.CmpBaseMakeBillAction bean = new nc.ui.cmp.base.actions.CmpBaseMakeBillAction();
  context.put("makeBillAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
  bean.setMakeBillService(getMakeBillService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.modelservice.CmpAppModelService getMakeBillService(){
 if(context.get("makeBillService")!=null)
 return (nc.ui.cmp.base.modelservice.CmpAppModelService)context.get("makeBillService");
  nc.ui.cmp.base.modelservice.CmpAppModelService bean = new nc.ui.cmp.base.modelservice.CmpAppModelService();
  context.put("makeBillService",bean);
  bean.setBilltype("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getAuxiliaryActionGroup(){
 if(context.get("auxiliaryActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("auxiliaryActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("auxiliaryActionGroup",bean);
  bean.setCode("Auxiliary");
  bean.setName(getI18nFB_10f45b2());
  bean.setActions(getManagedList13());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_10f45b2(){
 if(context.get("nc.ui.uif2.I18nFB#10f45b2")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#10f45b2");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#10f45b2",bean);  bean.setResDir("common");
  bean.setDefaultValue("辅助功能");
  bean.setResId("UC001-0000137");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#10f45b2",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList13(){  List list = new ArrayList();  list.add(getFileAction());  return list;}

public nc.ui.cmp.base.actions.CmpBaseFileDocManageAction getFileAction(){
 if(context.get("fileAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseFileDocManageAction)context.get("fileAction");
  nc.ui.cmp.base.actions.CmpBaseFileDocManageAction bean = new nc.ui.cmp.base.actions.CmpBaseFileDocManageAction();
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

public nc.funcnode.ui.action.MenuAction getLinkActionGroup(){
 if(context.get("linkActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("linkActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("linkActionGroup",bean);
  bean.setCode("linkQuery");
  bean.setName(getI18nFB_1a8bf33());
  bean.setActions(getManagedList14());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1a8bf33(){
 if(context.get("nc.ui.uif2.I18nFB#1a8bf33")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1a8bf33");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1a8bf33",bean);  bean.setResDir("common");
  bean.setDefaultValue("联查");
  bean.setResId("UC001-0000146");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1a8bf33",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList14(){  List list = new ArrayList();  list.add(getLinkVoucherAction());  list.add(getCashAccountBalanceAction());  list.add(getPrevVoucherAction());  list.add(getLinkQueryAction());  return list;}

public nc.ui.pubapp.uif2app.actions.LinkQueryAction getLinkQueryAction(){
 if(context.get("linkQueryAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.LinkQueryAction)context.get("linkQueryAction");
  nc.ui.pubapp.uif2app.actions.LinkQueryAction bean = new nc.ui.pubapp.uif2app.actions.LinkQueryAction();
  context.put("linkQueryAction",bean);
  bean.setModel(getManageAppModel());
  bean.setBillType("36S2");
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
  bean.setBtnName(getI18nFB_1a0eaa4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1a0eaa4(){
 if(context.get("nc.ui.uif2.I18nFB#1a0eaa4")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1a0eaa4");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1a0eaa4",bean);  bean.setResDir("3607set1_0");
  bean.setDefaultValue("查看审批意见");
  bean.setResId("03607set1-0066");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1a0eaa4",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.cmp.base.actions.CmpBaseVoucherQueryAction getLinkVoucherAction(){
 if(context.get("linkVoucherAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseVoucherQueryAction)context.get("linkVoucherAction");
  nc.ui.cmp.base.actions.CmpBaseVoucherQueryAction bean = new nc.ui.cmp.base.actions.CmpBaseVoucherQueryAction();
  context.put("linkVoucherAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setListView(getListView());
  bean.setLoginContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseBankAccBalanceQueryAction getCashAccountBalanceAction(){
 if(context.get("cashAccountBalanceAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseBankAccBalanceQueryAction)context.get("cashAccountBalanceAction");
  nc.ui.cmp.base.actions.CmpBaseBankAccBalanceQueryAction bean = new nc.ui.cmp.base.actions.CmpBaseBankAccBalanceQueryAction();
  context.put("cashAccountBalanceAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setListView(getListView());
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
  bean.setEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getImportExportActionGroup(){
 if(context.get("importExportActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("importExportActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("importExportActionGroup",bean);
  bean.setCode("ImportExport");
  bean.setName(getI18nFB_18cea22());
  bean.setActions(getManagedList15());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_18cea22(){
 if(context.get("nc.ui.uif2.I18nFB#18cea22")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#18cea22");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#18cea22",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607cash-0072");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#18cea22",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList15(){  List list = new ArrayList();  list.add(getExcelImportAction());  list.add(getSeperatorAction());  list.add(getExcelTemplateExportAction());  return list;}

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

public nc.ui.cmp.cash.importable.CashDrawImportableEditor getSupplierPriceImportableEditor(){
 if(context.get("supplierPriceImportableEditor")!=null)
 return (nc.ui.cmp.cash.importable.CashDrawImportableEditor)context.get("supplierPriceImportableEditor");
  nc.ui.cmp.cash.importable.CashDrawImportableEditor bean = new nc.ui.cmp.cash.importable.CashDrawImportableEditor();
  context.put("supplierPriceImportableEditor",bean);
  bean.setAppModel(getManageAppModel());
  bean.setBillcardPanelEditor(getCardEditor());
  bean.setAddAction(getAddAction());
  bean.setSaveAction(getSaveAction());
  bean.setCancelAction(getCancelAction());
  bean.setMany2ManyDispatcher(getMany2ManyDispatcher());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getPrintActionGroup(){
 if(context.get("printActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printActionGroup",bean);
  bean.setActions(getManagedList16());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList16(){  List list = new ArrayList();  list.add(getPrintAction());  list.add(getPrintPreviewAction());  list.add(getOutputAction());  return list;}

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

public nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction getPrintPreviewAction(){
 if(context.get("printPreviewAction")!=null)
 return (nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction)context.get("printPreviewAction");
  nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction bean = new nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction();
  context.put("printPreviewAction",bean);
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
  bean.setParent(getCardEditor());
  bean.setBeforePrintDataProcess(getPrintProcessor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.actions.CashDrawPrintProcessor getPrintProcessor(){
 if(context.get("printProcessor")!=null)
 return (nc.ui.cmp.cash.actions.CashDrawPrintProcessor)context.get("printProcessor");
  nc.ui.cmp.cash.actions.CashDrawPrintProcessor bean = new nc.ui.cmp.cash.actions.CashDrawPrintProcessor();
  context.put("printProcessor",bean);
  bean.setModel(getManageAppModel());
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList17());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList17(){  List list = new ArrayList();  list.add(getListViewActions());  list.add(getCardEditorActions());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getListViewActions(){
 if(context.get("listViewActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("listViewActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getListView());  context.put("listViewActions",bean);
  bean.setActions(getManagedList18());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList18(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeperatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeperatorAction());  list.add(getCommitMenuAction());  list.add(getApproveActionGroup());  list.add(getSeperatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeperatorAction());  list.add(getSubmitActionGroup());  list.add(getSettleActionGroup());  list.add(getMakeBillAction());  list.add(getAuxiliaryActionGroup());  list.add(getSeperatorAction());  list.add(getLinkActionGroup());  list.add(getSeperatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintActionGroup());  return list;}

public nc.ui.uif2.actions.StandAloneToftPanelActionContainer getCardEditorActions(){
 if(context.get("cardEditorActions")!=null)
 return (nc.ui.uif2.actions.StandAloneToftPanelActionContainer)context.get("cardEditorActions");
  nc.ui.uif2.actions.StandAloneToftPanelActionContainer bean = new nc.ui.uif2.actions.StandAloneToftPanelActionContainer(getCardEditor());  context.put("cardEditorActions",bean);
  bean.setActions(getManagedList19());
  bean.setEditActions(getManagedList20());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList19(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeperatorAction());  list.add(getQueryAction());  list.add(getCardRefreshAction());  list.add(getSeperatorAction());  list.add(getCommitMenuAction());  list.add(getApproveActionGroup());  list.add(getSeperatorAction());  list.add(getImageFuncActionGroup());  list.add(getSeperatorAction());  list.add(getSubmitActionGroup());  list.add(getSettleActionGroup());  list.add(getMakeBillAction());  list.add(getAuxiliaryActionGroup());  list.add(getSeperatorAction());  list.add(getLinkActionGroup());  list.add(getSeperatorAction());  list.add(getImportExportActionGroup());  list.add(getPrintActionGroup());  return list;}

private List getManagedList20(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSaveAddAction());  list.add(getSaveSendApproveAction());  list.add(getSaveTempAction());  list.add(getSeperatorAction());  list.add(getSeperatorAction());  list.add(getCancelAction());  return list;}

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

private Map getManagedMap0(){  Map map = new HashMap();  map.put(getManagedList21(),getManagedList31());  return map;}

private List getManagedList21(){  List list = new ArrayList();  list.add(getManagedList22());  list.add(getManagedList23());  list.add(getManagedList24());  list.add(getManagedList25());  list.add(getManagedList26());  list.add(getManagedList27());  list.add(getManagedList28());  list.add(getManagedList29());  list.add(getManagedList30());  return list;}

private List getManagedList22(){  List list = new ArrayList();  list.add("ORG");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList23(){  List list = new ArrayList();  list.add("GROUP");  list.add("pk_group");  list.add("HEAD");  return list;}

private List getManagedList24(){  List list = new ArrayList();  list.add("GLOBAL");  list.add("pk_org");  list.add("HEAD");  return list;}

private List getManagedList25(){  List list = new ArrayList();  list.add("CURR");  list.add("pk_currency");  list.add("HEAD");  return list;}

private List getManagedList26(){  List list = new ArrayList();  list.add("EXCHANGEDATE");  list.add("billdate");  list.add("HEAD");  return list;}

private List getManagedList27(){  List list = new ArrayList();  list.add("MONEY");  list.add("money");  list.add("HEAD");  return list;}

private List getManagedList28(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("olcrate");  list.add("HEAD");  return list;}

private List getManagedList29(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("gllcrate");  list.add("HEAD");  return list;}

private List getManagedList30(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("glcrate");  list.add("HEAD");  return list;}

private List getManagedList31(){  List list = new ArrayList();  list.add(getManagedList32());  list.add(getManagedList33());  list.add(getManagedList34());  list.add(getManagedList35());  list.add(getManagedList36());  list.add(getManagedList37());  list.add(getManagedList38());  return list;}

private List getManagedList32(){  List list = new ArrayList();  list.add("CURR_MONEY");  list.add("money");  list.add("HEAD");  return list;}

private List getManagedList33(){  List list = new ArrayList();  list.add("ORG_RATE");  list.add("olcrate");  list.add("HEAD");  return list;}

private List getManagedList34(){  List list = new ArrayList();  list.add("ORG_MONEY");  list.add("olcmoney");  list.add("HEAD");  return list;}

private List getManagedList35(){  List list = new ArrayList();  list.add("GROUP_RATE");  list.add("glcrate");  list.add("HEAD");  return list;}

private List getManagedList36(){  List list = new ArrayList();  list.add("GROUP_MONEY");  list.add("glcmoney");  list.add("HEAD");  return list;}

private List getManagedList37(){  List list = new ArrayList();  list.add("GLOBAL_RATE");  list.add("gllcrate");  list.add("HEAD");  return list;}

private List getManagedList38(){  List list = new ArrayList();  list.add("GLOBAL_MONEY");  list.add("gllcmoney");  list.add("HEAD");  return list;}

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

private Map getManagedMap1(){  Map map = new HashMap();  map.put("nc.ui.pubapp.uif2app.event.list.ListPanelLoadEvent",getManagedList39());  map.put("nc.ui.pubapp.uif2app.event.card.CardPanelLoadEvent",getManagedList40());  return map;}

private List getManagedList39(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_f61e59());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_f61e59(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#f61e59")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#f61e59");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#f61e59",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList40(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_428453());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_428453(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#428453")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#428453");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#428453",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getCardEditEventDigitMediator(){
 if(context.get("cardEditEventDigitMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("cardEditEventDigitMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getCardEditor());  context.put("cardEditEventDigitMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher_fd8721());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_fd8721(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fd8721")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fd8721");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#fd8721",bean);
  bean.setMany2one(getManagedMap2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap2(){  Map map = new HashMap();  map.put(getManagedList41(),getCardEditListener());  return map;}

private List getManagedList41(){  List list = new ArrayList();  list.add("pk_org");  list.add("pk_group");  list.add("pk_currency");  list.add("money");  list.add("billdate");  list.add("olcrate");  list.add("glcrate");  list.add("gllcrate");  return list;}

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

public nc.ui.tmpub.filter.DefaultRefWherePartHandler getDefaultRefWherePartHandler(){
 if(context.get("defaultRefWherePartHandler")!=null)
 return (nc.ui.tmpub.filter.DefaultRefWherePartHandler)context.get("defaultRefWherePartHandler");
  nc.ui.tmpub.filter.DefaultRefWherePartHandler bean = new nc.ui.tmpub.filter.DefaultRefWherePartHandler();
  context.put("defaultRefWherePartHandler",bean);
  bean.setUiAccessor(getDefaultUIAccessor_1e0766f());
  bean.setFilterList(getManagedList42());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.tmpub.filter.DefaultUIAccessor getDefaultUIAccessor_1e0766f(){
 if(context.get("nc.ui.tmpub.filter.DefaultUIAccessor#1e0766f")!=null)
 return (nc.ui.tmpub.filter.DefaultUIAccessor)context.get("nc.ui.tmpub.filter.DefaultUIAccessor#1e0766f");
  nc.ui.tmpub.filter.DefaultUIAccessor bean = new nc.ui.tmpub.filter.DefaultUIAccessor();
  context.put("nc.ui.tmpub.filter.DefaultUIAccessor#1e0766f",bean);
  bean.setBillCardPanelEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList42(){  List list = new ArrayList();  list.add(getCashAccountRefModelFilter_17f725f());  list.add(getCashDrawBankAccFilter_2dd770());  list.add(getBalanceTypeRefModelFilter_19b025c());  list.add(getNoteTypeRefModelFilter_1b7ff75());  return list;}

private nc.ui.cmp.cash.filter.CashAccountRefModelFilter getCashAccountRefModelFilter_17f725f(){
 if(context.get("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#17f725f")!=null)
 return (nc.ui.cmp.cash.filter.CashAccountRefModelFilter)context.get("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#17f725f");
  nc.ui.cmp.cash.filter.CashAccountRefModelFilter bean = new nc.ui.cmp.cash.filter.CashAccountRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#17f725f",bean);
  bean.setSrcKey("pk_currency");
  bean.setDestKey("pk_cashaccount");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.CashDrawBankAccFilter getCashDrawBankAccFilter_2dd770(){
 if(context.get("nc.ui.cmp.cash.filter.CashDrawBankAccFilter#2dd770")!=null)
 return (nc.ui.cmp.cash.filter.CashDrawBankAccFilter)context.get("nc.ui.cmp.cash.filter.CashDrawBankAccFilter#2dd770");
  nc.ui.cmp.cash.filter.CashDrawBankAccFilter bean = new nc.ui.cmp.cash.filter.CashDrawBankAccFilter();
  context.put("nc.ui.cmp.cash.filter.CashDrawBankAccFilter#2dd770",bean);
  bean.setSrcKey("pk_currency");
  bean.setDestKey("pk_bankaccount");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter getBalanceTypeRefModelFilter_19b025c(){
 if(context.get("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#19b025c")!=null)
 return (nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter)context.get("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#19b025c");
  nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter bean = new nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#19b025c",bean);
  bean.setSrcKey("pk_balatype");
  bean.setDestKey("pk_balatype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.NoteTypeRefModelFilter getNoteTypeRefModelFilter_1b7ff75(){
 if(context.get("nc.ui.cmp.cash.filter.NoteTypeRefModelFilter#1b7ff75")!=null)
 return (nc.ui.cmp.cash.filter.NoteTypeRefModelFilter)context.get("nc.ui.cmp.cash.filter.NoteTypeRefModelFilter#1b7ff75");
  nc.ui.cmp.cash.filter.NoteTypeRefModelFilter bean = new nc.ui.cmp.cash.filter.NoteTypeRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.NoteTypeRefModelFilter#1b7ff75",bean);
  bean.setSrcKey("pk_notetype");
  bean.setDestKey("pk_notetype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.EditHandleMediator getEditHandleMediator(){
 if(context.get("editHandleMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.EditHandleMediator)context.get("editHandleMediator");
  nc.ui.pubapp.uif2app.view.EditHandleMediator bean = new nc.ui.pubapp.uif2app.view.EditHandleMediator(getCardEditor());  context.put("editHandleMediator",bean);
  bean.setDispatcher(getMany2ManyDispatcher());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher(){
 if(context.get("many2ManyDispatcher")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("many2ManyDispatcher");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("many2ManyDispatcher",bean);
  bean.setMany2one(getManagedMap3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap3(){  Map map = new HashMap();  map.put(getManagedList43(),getCurrencyListener());  map.put(getManagedList44(),getBankAccListener());  return map;}

private List getManagedList43(){  List list = new ArrayList();  list.add("pk_currency");  return list;}

private nc.ui.cmp.cash.listener.CmpCurrencyListener getCurrencyListener(){
 if(context.get("currencyListener")!=null)
 return (nc.ui.cmp.cash.listener.CmpCurrencyListener)context.get("currencyListener");
  nc.ui.cmp.cash.listener.CmpCurrencyListener bean = new nc.ui.cmp.cash.listener.CmpCurrencyListener();
  context.put("currencyListener",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList44(){  List list = new ArrayList();  list.add("pk_bankaccount");  return list;}

private nc.ui.cmp.cash.listener.CashDepositBankAccListener getBankAccListener(){
 if(context.get("bankAccListener")!=null)
 return (nc.ui.cmp.cash.listener.CashDepositBankAccListener)context.get("bankAccListener");
  nc.ui.cmp.cash.listener.CashDepositBankAccListener bean = new nc.ui.cmp.cash.listener.CashDepositBankAccListener();
  context.put("bankAccListener",bean);
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

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList45());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList45(){  List list = new ArrayList();  list.add("36070WC");  return list;}

public nc.ui.cmp.cash.model.CashDrawModel getManageAppModel(){
 if(context.get("manageAppModel")!=null)
 return (nc.ui.cmp.cash.model.CashDrawModel)context.get("manageAppModel");
  nc.ui.cmp.cash.model.CashDrawModel bean = new nc.ui.cmp.cash.model.CashDrawModel();
  context.put("manageAppModel",bean);
  bean.setContext(getContext());
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setBillType("36S2");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.model.CashDrawDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.cmp.cash.model.CashDrawDataManager)context.get("modelDataManager");
  nc.ui.cmp.cash.model.CashDrawDataManager bean = new nc.ui.cmp.cash.model.CashDrawDataManager();
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

public nc.vo.bd.meta.BDObjectAdpaterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.vo.bd.meta.BDObjectAdpaterFactory)context.get("boadatorfactory");
  nc.vo.bd.meta.BDObjectAdpaterFactory bean = new nc.vo.bd.meta.BDObjectAdpaterFactory();
  context.put("boadatorfactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.model.CashDrawModelService getManageModelService(){
 if(context.get("manageModelService")!=null)
 return (nc.ui.cmp.cash.model.CashDrawModelService)context.get("manageModelService");
  nc.ui.cmp.cash.model.CashDrawModelService bean = new nc.ui.cmp.cash.model.CashDrawModelService();
  context.put("manageModelService",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_172166e());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_172166e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#172166e")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#172166e");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#172166e",bean);
  bean.setTabs(getManagedList46());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList46(){  List list = new ArrayList();  list.add(getHSNode_1596b4f());  list.add(getVSNode_141bf3c());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_1596b4f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#1596b4f")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#1596b4f");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#1596b4f",bean);
  bean.setLeft(getCNode_f6d223());
  bean.setRight(getCNode_5e1f4e());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_f6d223(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#f6d223")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#f6d223");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#f6d223",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_5e1f4e(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#5e1f4e")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#5e1f4e");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#5e1f4e",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_141bf3c(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#141bf3c")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#141bf3c");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#141bf3c",bean);
  bean.setUp(getCNode_1943214());
  bean.setDown(getCNode_63d14b());
  bean.setDividerLocation(30f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1943214(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1943214")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1943214");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1943214",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_63d14b(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#63d14b")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#63d14b");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#63d14b",bean);
  bean.setComponent(getCardEditor());
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

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getCardInfoPnl(){
 if(context.get("cardInfoPnl")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("cardInfoPnl");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("cardInfoPnl",bean);
  bean.setActions(getManagedList47());
  bean.setTitleAction(getReturnaction());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList47(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

private nc.ui.uif2.actions.FirstLineAction getFirstLineAction(){
 if(context.get("firstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("firstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("firstLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.PreLineAction getPreLineAction(){
 if(context.get("preLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("preLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("preLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.NextLineAction getNextLineAction(){
 if(context.get("nextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("nextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("nextLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.LastLineAction getLastLineAction(){
 if(context.get("lastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("lastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("lastLineAction",bean);
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

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

public nc.ui.cmp.cash.view.CashDrawListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.cmp.cash.view.CashDrawListView)context.get("listView");
  nc.ui.cmp.cash.view.CashDrawListView bean = new nc.ui.cmp.cash.view.CashDrawListView();
  context.put("listView",bean);
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("36070WC");
  bean.setModel(getManageAppModel());
  bean.setMultiSelectionEnable(true);
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setPaginationBar(getPaginationBar());
  bean.setNorth(getListInfoPnl());
  bean.setShowTotalLine(true);
  bean.setShowTotalLineTabcodes(getManagedList48());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList48(){  List list = new ArrayList();  list.add("cmp_cashdraw");  return list;}

public nc.ui.uif2.userdefitem.UserDefItemContainer getUserdefitemContainer(){
 if(context.get("userdefitemContainer")!=null)
 return (nc.ui.uif2.userdefitem.UserDefItemContainer)context.get("userdefitemContainer");
  nc.ui.uif2.userdefitem.UserDefItemContainer bean = new nc.ui.uif2.userdefitem.UserDefItemContainer();
  context.put("userdefitemContainer",bean);
  bean.setContext(getContext());
  bean.setParams(getManagedList49());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList49(){  List list = new ArrayList();  list.add(getQueryParam_11ea0e9());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_11ea0e9(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#11ea0e9")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#11ea0e9");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#11ea0e9",bean);
  bean.setMdfullname("cmp.cmp_cashdraw");
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
  bean.setParams(getManagedList50());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList50(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1d6467f());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1d6467f(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1d6467f")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1d6467f");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1d6467f",bean);
  bean.setMdfullname("cmp.cmp_cashdraw");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.listener.CashDrawOrgListener getFinanceOrgChangedMediator(){
 if(context.get("financeOrgChangedMediator")!=null)
 return (nc.ui.cmp.cash.listener.CashDrawOrgListener)context.get("financeOrgChangedMediator");
  nc.ui.cmp.cash.listener.CashDrawOrgListener bean = new nc.ui.cmp.cash.listener.CashDrawOrgListener();
  context.put("financeOrgChangedMediator",bean);
  bean.setBillform(getCardEditor());
  bean.setModel(getManageAppModel());
  bean.setOrgChangedImpl(getOrgChanged_ae7a56());
  bean.setCheckedDate("inputdate");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged getOrgChanged_ae7a56(){
 if(context.get("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#ae7a56")!=null)
 return (nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged)context.get("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#ae7a56");
  nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged bean = new nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged();
  context.put("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#ae7a56",bean);
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
  bean.setParams(getManagedList51());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList51(){  List list = new ArrayList();  list.add(getUserdefQueryParam_e1d6b7());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_e1d6b7(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#e1d6b7")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#e1d6b7");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#e1d6b7",bean);
  bean.setMdfullname("cmp.cmp_cashdraw");
  bean.setPos(0);
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
  bean.setRemoteCallers(getManagedList52());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList52(){  List list = new ArrayList();  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  return list;}

public nc.ui.cmp.cash.view.CashDrawCardEditor getCardEditor(){
 if(context.get("cardEditor")!=null)
 return (nc.ui.cmp.cash.view.CashDrawCardEditor)context.get("cardEditor");
  nc.ui.cmp.cash.view.CashDrawCardEditor bean = new nc.ui.cmp.cash.view.CashDrawCardEditor();
  context.put("cardEditor",bean);
  bean.setModel(getManageAppModel());
  bean.setNodekey("36070WC");
  bean.setTemplateContainer(getTemplateContainer());
  bean.setTemplateNotNullValidate(true);
  bean.setClosingListener(getClosingListener());
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
  bean.setDefaultRefWherePartHandler(getDefaultRefWherePartHandler());
  bean.setComponentValueManager(getComponentValueManager());
  bean.setAutoAddLine(true);
  bean.setBlankChildrenFilter(getSingleFieldBlankChildrenFilter_d79f66());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter getSingleFieldBlankChildrenFilter_d79f66(){
 if(context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#d79f66")!=null)
 return (nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter)context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#d79f66");
  nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter bean = new nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter();
  context.put("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#d79f66",bean);
  bean.setFieldName("pk_cashdraw");
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

public nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator getOrgetterForAllRefMediator(){
 if(context.get("orgetterForAllRefMediator")!=null)
 return (nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator)context.get("orgetterForAllRefMediator");
  nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator bean = new nc.ui.pubapp.uif2app.view.MouseClickShowPanelMediator();
  context.put("orgetterForAllRefMediator",bean);
  bean.setListView(getListView());
  bean.setShowUpComponent(getCardEditor());
  bean.setHyperLinkColumn("billno");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator getCardPanelOrgSetterForAllRefMediator_0(){
 if(context.get("nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator#0")!=null)
 return (nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator)context.get("nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator#0");
  nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator bean = new nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator(getCardEditor());  context.put("nc.ui.pubapp.uif2app.view.CardPanelOrgSetterForAllRefMediator#0",bean);
  bean.setModel(getManageAppModel());
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

public nc.ui.cmp.base.listener.CashDrawFuncNodeInitDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.cmp.base.listener.CashDrawFuncNodeInitDataListener)context.get("InitDataListener");
  nc.ui.cmp.base.listener.CashDrawFuncNodeInitDataListener bean = new nc.ui.cmp.base.listener.CashDrawFuncNodeInitDataListener();
  context.put("InitDataListener",bean);
  bean.setBillFormEditor(getCardEditor());
  bean.setListview(getListView());
  bean.setQueryAction(getQueryAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pubapp.uif2app.view.OrgPanel getOrgPanel(){
 if(context.get("orgPanel")!=null)
 return (nc.ui.pubapp.uif2app.view.OrgPanel)context.get("orgPanel");
  nc.ui.pubapp.uif2app.view.OrgPanel bean = new nc.ui.pubapp.uif2app.view.OrgPanel();
  context.put("orgPanel",bean);
  bean.setModel(getManageAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setType("集团");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
