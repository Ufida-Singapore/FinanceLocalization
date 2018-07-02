package nc.ui.cmp.cash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class cashdeposit_org extends AbstractJavaBeanDefinition{
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
  bean.setInterceptor(getCompositeActionInterceptor_1b2762c());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor getCompositeActionInterceptor_1b2762c(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#1b2762c")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#1b2762c");
  nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor#1b2762c",bean);
  bean.setInterceptors(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getShowUpComponentInterceptor_aca1cc());  return list;}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_aca1cc(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#aca1cc")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#aca1cc");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#aca1cc",bean);
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
  bean.setBillType("36S1");
  bean.setResourceCode("36S1");
  bean.setOperateCode("edit");
  bean.setBillCodeName("billno");
  bean.setInterceptor(getShowUpComponentInterceptor_16c50e8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_16c50e8(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16c50e8")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16c50e8");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#16c50e8",bean);
  bean.setShowUpComponent(getCardEditor());
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
  bean.setBillType("36S1");
  bean.setBillCodeName("billno");
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
  bean.setName(getI18nFB_33e150());
  bean.setActions(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_33e150(){
 if(context.get("nc.ui.uif2.I18nFB#33e150")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#33e150");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#33e150",bean);  bean.setResDir("3607mng_0");
  bean.setDefaultValue("影像");
  bean.setResId("03607mng-0446");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#33e150",product);
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
  bean.setPk_billtype("1001Z610000000000IIV");
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
  bean.setPk_billtype("1001Z610000000000IIV");
  bean.setCheckscanway("nc.imag.scan.service.CheckScanWay");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.actions.CmpBaseCopyAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCopyAction)context.get("copyAction");
  nc.ui.cmp.base.actions.CmpBaseCopyAction bean = new nc.ui.cmp.base.actions.CmpBaseCopyAction();
  context.put("copyAction",bean);
  bean.setCopyActionProcessor(getCmpBaseCopyActionProcessor_d5278());
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
  bean.setInterceptor(getShowUpComponentInterceptor_a17dc0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor getCmpBaseCopyActionProcessor_d5278(){
 if(context.get("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d5278")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor)context.get("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d5278");
  nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor bean = new nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor();
  context.put("nc.ui.cmp.base.actions.CmpBaseCopyActionProcessor#d5278",bean);
  bean.setEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_a17dc0(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#a17dc0")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#a17dc0");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#a17dc0",bean);
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
  bean.setBillType("36S1");
  bean.setBillVOName("nc.vo.cmp.cash.AggCashDepositVO");
  bean.setHeadVOName("nc.vo.cmp.cash.CashDepositVO");
  bean.setCheckedDate("inputdate");
  bean.setValidationService(getValidateService());
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

private List getManagedList2(){  List list = new ArrayList();  list.add(getTemplateNotNullValidation_470726());  list.add(getPowerSaveValidateService_1264fee());  return list;}

private nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation getTemplateNotNullValidation_470726(){
 if(context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#470726")!=null)
 return (nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation)context.get("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#470726");
  nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation bean = new nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation();
  context.put("nc.ui.pubapp.uif2app.validation.TemplateNotNullValidation#470726",bean);
  bean.setBillForm(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerSaveValidateService getPowerSaveValidateService_1264fee(){
 if(context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#1264fee")!=null)
 return (nc.ui.pubapp.pub.power.PowerSaveValidateService)context.get("nc.ui.pubapp.pub.power.PowerSaveValidateService#1264fee");
  nc.ui.pubapp.pub.power.PowerSaveValidateService bean = new nc.ui.pubapp.pub.power.PowerSaveValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerSaveValidateService#1264fee",bean);
  bean.setInsertActionCode("save");
  bean.setEditActionCode("edit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S1");
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
  bean.setInterceptor(getShowUpComponentInterceptor_49ae8b());
  bean.setQryCondDLGInitializer(getSalequoQryCondDLGInitializer());
  bean.setTemplateContainer(getQueryTemplateContainer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor getShowUpComponentInterceptor_49ae8b(){
 if(context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#49ae8b")!=null)
 return (nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor)context.get("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#49ae8b");
  nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor bean = new nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor();
  context.put("nc.ui.pubapp.uif2app.actions.interceptor.ShowUpComponentInterceptor#49ae8b",bean);
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

public nc.ui.cmp.cash.query.CashDepositQryCondInitializer getSalequoQryCondDLGInitializer(){
 if(context.get("salequoQryCondDLGInitializer")!=null)
 return (nc.ui.cmp.cash.query.CashDepositQryCondInitializer)context.get("salequoQryCondDLGInitializer");
  nc.ui.cmp.cash.query.CashDepositQryCondInitializer bean = new nc.ui.cmp.cash.query.CashDepositQryCondInitializer();
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
  bean.setQueryArea(getQueryAction_created_162427e());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.queryarea.QueryArea getQueryAction_created_162427e(){
 if(context.get("queryAction.created#162427e")!=null)
 return (nc.ui.queryarea.QueryArea)context.get("queryAction.created#162427e");
  nc.ui.queryarea.QueryArea bean = getQueryAction().createQueryArea();
  context.put("queryAction.created#162427e",bean);
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
  bean.setName(getI18nFB_1fb87f0());
  bean.setActions(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1fb87f0(){
 if(context.get("nc.ui.uif2.I18nFB#1fb87f0")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1fb87f0");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1fb87f0",bean);  bean.setResDir("common");
  bean.setDefaultValue("提交");
  bean.setResId("UC001-0000029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1fb87f0",product);
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
  bean.setBillType("36S1");
  bean.setActionName("SAVE");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_99a2ae());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_99a2ae(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#99a2ae")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#99a2ae");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#99a2ae",bean);
  bean.setActionCode("commit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S1");
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
  bean.setBillType("36S1");
  bean.setActionName("UNSAVEBILL");
  bean.setFilledUpInFlow(true);
  bean.setValidationService(getPowerValidateService_877f0e());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.pub.power.PowerValidateService getPowerValidateService_877f0e(){
 if(context.get("nc.ui.pubapp.pub.power.PowerValidateService#877f0e")!=null)
 return (nc.ui.pubapp.pub.power.PowerValidateService)context.get("nc.ui.pubapp.pub.power.PowerValidateService#877f0e");
  nc.ui.pubapp.pub.power.PowerValidateService bean = new nc.ui.pubapp.pub.power.PowerValidateService();
  context.put("nc.ui.pubapp.pub.power.PowerValidateService#877f0e",bean);
  bean.setActionCode("uncommit");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S1");
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
  bean.setName(getI18nFB_1a6737b());
  bean.setActions(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1a6737b(){
 if(context.get("nc.ui.uif2.I18nFB#1a6737b")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1a6737b");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1a6737b",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("审批");
  bean.setResId("03607cash-0071");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1a6737b",product);
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

private List getManagedList5(){  List list = new ArrayList();  list.add(getCashDepositApproveValidator_e7a599());  return list;}

private nc.ui.cmp.cash.validator.CashDepositApproveValidator getCashDepositApproveValidator_e7a599(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositApproveValidator#e7a599")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositApproveValidator)context.get("nc.ui.cmp.cash.validator.CashDepositApproveValidator#e7a599");
  nc.ui.cmp.cash.validator.CashDepositApproveValidator bean = new nc.ui.cmp.cash.validator.CashDepositApproveValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositApproveValidator#e7a599",bean);
  bean.setActionCode("approve");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S1");
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

private List getManagedList6(){  List list = new ArrayList();  list.add(getCashDepositUnApproveValidator_2918b1());  return list;}

private nc.ui.cmp.cash.validator.CashDepositUnApproveValidator getCashDepositUnApproveValidator_2918b1(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositUnApproveValidator#2918b1")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositUnApproveValidator)context.get("nc.ui.cmp.cash.validator.CashDepositUnApproveValidator#2918b1");
  nc.ui.cmp.cash.validator.CashDepositUnApproveValidator bean = new nc.ui.cmp.cash.validator.CashDepositUnApproveValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositUnApproveValidator#2918b1",bean);
  bean.setActionCode("unapprove");
  bean.setBillCodeFiledName("billno");
  bean.setPermissionCode("36S1");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.tm.approve.model.DefaultApproveServie getApproveService(){
 if(context.get("approveService")!=null)
 return (nc.ui.tm.approve.model.DefaultApproveServie)context.get("approveService");
  nc.ui.tm.approve.model.DefaultApproveServie bean = new nc.ui.tm.approve.model.DefaultApproveServie();
  context.put("approveService",bean);
  bean.setBilltype("36S1");
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
  bean.setName(getI18nFB_142216());
  bean.setActions(getManagedList7());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_142216(){
 if(context.get("nc.ui.uif2.I18nFB#142216")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#142216");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#142216",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("委托办理");
  bean.setResId("03607cash-0025");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#142216",product);
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

private List getManagedList8(){  List list = new ArrayList();  list.add(getCashDepositSubmitValidator_1ca0bd6());  return list;}

private nc.ui.cmp.cash.validator.CashDepositSubmitValidator getCashDepositSubmitValidator_1ca0bd6(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositSubmitValidator#1ca0bd6")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositSubmitValidator)context.get("nc.ui.cmp.cash.validator.CashDepositSubmitValidator#1ca0bd6");
  nc.ui.cmp.cash.validator.CashDepositSubmitValidator bean = new nc.ui.cmp.cash.validator.CashDepositSubmitValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositSubmitValidator#1ca0bd6",bean);
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

private List getManagedList9(){  List list = new ArrayList();  list.add(getCashDepositUnSubmitValidator_1cf2c3d());  return list;}

private nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator getCashDepositUnSubmitValidator_1cf2c3d(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator#1cf2c3d")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator)context.get("nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator#1cf2c3d");
  nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator bean = new nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositUnSubmitValidator#1cf2c3d",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.modelservice.CmpAppModelService getSubmitService(){
 if(context.get("submitService")!=null)
 return (nc.ui.cmp.base.modelservice.CmpAppModelService)context.get("submitService");
  nc.ui.cmp.base.modelservice.CmpAppModelService bean = new nc.ui.cmp.base.modelservice.CmpAppModelService();
  context.put("submitService",bean);
  bean.setBilltype("36S1");
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
  bean.setName(getI18nFB_619a58());
  bean.setActions(getManagedList10());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_619a58(){
 if(context.get("nc.ui.uif2.I18nFB#619a58")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#619a58");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#619a58",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("结算");
  bean.setResId("03607cash-0022");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#619a58",product);
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

private List getManagedList11(){  List list = new ArrayList();  list.add(getCashDepositSettleValidator_1dc462d());  return list;}

private nc.ui.cmp.cash.validator.CashDepositSettleValidator getCashDepositSettleValidator_1dc462d(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositSettleValidator#1dc462d")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositSettleValidator)context.get("nc.ui.cmp.cash.validator.CashDepositSettleValidator#1dc462d");
  nc.ui.cmp.cash.validator.CashDepositSettleValidator bean = new nc.ui.cmp.cash.validator.CashDepositSettleValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositSettleValidator#1dc462d",bean);
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

private List getManagedList12(){  List list = new ArrayList();  list.add(getCashDepositUnSettleValidator_8343ab());  return list;}

private nc.ui.cmp.cash.validator.CashDepositUnSettleValidator getCashDepositUnSettleValidator_8343ab(){
 if(context.get("nc.ui.cmp.cash.validator.CashDepositUnSettleValidator#8343ab")!=null)
 return (nc.ui.cmp.cash.validator.CashDepositUnSettleValidator)context.get("nc.ui.cmp.cash.validator.CashDepositUnSettleValidator#8343ab");
  nc.ui.cmp.cash.validator.CashDepositUnSettleValidator bean = new nc.ui.cmp.cash.validator.CashDepositUnSettleValidator();
  context.put("nc.ui.cmp.cash.validator.CashDepositUnSettleValidator#8343ab",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.base.modelservice.CmpAppModelService getSettleService(){
 if(context.get("settleService")!=null)
 return (nc.ui.cmp.base.modelservice.CmpAppModelService)context.get("settleService");
  nc.ui.cmp.base.modelservice.CmpAppModelService bean = new nc.ui.cmp.base.modelservice.CmpAppModelService();
  context.put("settleService",bean);
  bean.setBilltype("36S1");
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
  bean.setBilltype("36S1");
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
  bean.setName(getI18nFB_1cfc528());
  bean.setActions(getManagedList13());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1cfc528(){
 if(context.get("nc.ui.uif2.I18nFB#1cfc528")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1cfc528");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1cfc528",bean);  bean.setResDir("common");
  bean.setDefaultValue("辅助功能");
  bean.setResId("UC001-0000137");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1cfc528",product);
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
  bean.setName(getI18nFB_198c605());
  bean.setActions(getManagedList14());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_198c605(){
 if(context.get("nc.ui.uif2.I18nFB#198c605")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#198c605");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#198c605",bean);  bean.setResDir("common");
  bean.setDefaultValue("联查");
  bean.setResId("UC001-0000146");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#198c605",product);
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
  bean.setBillType("36S1");
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
  bean.setBtnName(getI18nFB_f0ab80());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_f0ab80(){
 if(context.get("nc.ui.uif2.I18nFB#f0ab80")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#f0ab80");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#f0ab80",bean);  bean.setResDir("3607set1_0");
  bean.setDefaultValue("查看审批意见");
  bean.setResId("03607set1-0066");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#f0ab80",product);
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

public nc.ui.cmp.base.actions.CmpBaseCashAccBalanceAction getCashAccountBalanceAction(){
 if(context.get("cashAccountBalanceAction")!=null)
 return (nc.ui.cmp.base.actions.CmpBaseCashAccBalanceAction)context.get("cashAccountBalanceAction");
  nc.ui.cmp.base.actions.CmpBaseCashAccBalanceAction bean = new nc.ui.cmp.base.actions.CmpBaseCashAccBalanceAction();
  context.put("cashAccountBalanceAction",bean);
  bean.setModel(getManageAppModel());
  bean.setEditor(getCardEditor());
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
  bean.setName(getI18nFB_8b6c44());
  bean.setActions(getManagedList15());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_8b6c44(){
 if(context.get("nc.ui.uif2.I18nFB#8b6c44")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#8b6c44");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#8b6c44",bean);  bean.setResDir("3607cash_0");
  bean.setDefaultValue("导入导出");
  bean.setResId("03607cash-0072");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#8b6c44",product);
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

public nc.ui.cmp.cash.importable.CashDepositImportableEditor getSupplierPriceImportableEditor(){
 if(context.get("supplierPriceImportableEditor")!=null)
 return (nc.ui.cmp.cash.importable.CashDepositImportableEditor)context.get("supplierPriceImportableEditor");
  nc.ui.cmp.cash.importable.CashDepositImportableEditor bean = new nc.ui.cmp.cash.importable.CashDepositImportableEditor();
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

public nc.ui.cmp.cash.actions.CashDepositPrintProcessor getPrintProcessor(){
 if(context.get("printProcessor")!=null)
 return (nc.ui.cmp.cash.actions.CashDepositPrintProcessor)context.get("printProcessor");
  nc.ui.cmp.cash.actions.CashDepositPrintProcessor bean = new nc.ui.cmp.cash.actions.CashDepositPrintProcessor();
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

private List getManagedList20(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSaveAddAction());  list.add(getSaveSendApproveAction());  list.add(getSaveTempAction());  list.add(getSeperatorAction());  list.add(getCancelAction());  return list;}

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

private List getManagedList39(){  List list = new ArrayList();  list.add(getListPanelLoadDigitListener_82ba12());  return list;}

private nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener getListPanelLoadDigitListener_82ba12(){
 if(context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#82ba12")!=null)
 return (nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#82ba12");
  nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.list.ListPanelLoadDigitListener#82ba12",bean);
  bean.setSrcDestItemCollection(getCardSrcDestCollection());
  bean.setModel(getManageAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList40(){  List list = new ArrayList();  list.add(getCardPanelLoadDigitListener_86025d());  return list;}

private nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener getCardPanelLoadDigitListener_86025d(){
 if(context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#86025d")!=null)
 return (nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener)context.get("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#86025d");
  nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener bean = new nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener();
  context.put("nc.ui.tmpub.digit.listener.card.CardPanelLoadDigitListener#86025d",bean);
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
  bean.setDispatcher(getMany2ManyDispatcher_54de4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher getMany2ManyDispatcher_54de4(){
 if(context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#54de4")!=null)
 return (nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher)context.get("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#54de4");
  nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher bean = new nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher();
  context.put("nc.ui.pubapp.uif2app.view.eventdispatch.Many2ManyDispatcher#54de4",bean);
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
  bean.setUiAccessor(getDefaultUIAccessor_d4006e());
  bean.setFilterList(getManagedList42());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.tmpub.filter.DefaultUIAccessor getDefaultUIAccessor_d4006e(){
 if(context.get("nc.ui.tmpub.filter.DefaultUIAccessor#d4006e")!=null)
 return (nc.ui.tmpub.filter.DefaultUIAccessor)context.get("nc.ui.tmpub.filter.DefaultUIAccessor#d4006e");
  nc.ui.tmpub.filter.DefaultUIAccessor bean = new nc.ui.tmpub.filter.DefaultUIAccessor();
  context.put("nc.ui.tmpub.filter.DefaultUIAccessor#d4006e",bean);
  bean.setBillCardPanelEditor(getCardEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList42(){  List list = new ArrayList();  list.add(getCashAccountRefModelFilter_6850da());  list.add(getBankAccountRefModelFilter_943e60());  list.add(getFundOrgBankAccRefModelFilter_8b13ff());  list.add(getBalanceTypeRefModelFilter_ce030b());  return list;}

private nc.ui.cmp.cash.filter.CashAccountRefModelFilter getCashAccountRefModelFilter_6850da(){
 if(context.get("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#6850da")!=null)
 return (nc.ui.cmp.cash.filter.CashAccountRefModelFilter)context.get("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#6850da");
  nc.ui.cmp.cash.filter.CashAccountRefModelFilter bean = new nc.ui.cmp.cash.filter.CashAccountRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.CashAccountRefModelFilter#6850da",bean);
  bean.setSrcKey("pk_currency");
  bean.setDestKey("pk_cashaccount");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.BankAccountRefModelFilter getBankAccountRefModelFilter_943e60(){
 if(context.get("nc.ui.cmp.cash.filter.BankAccountRefModelFilter#943e60")!=null)
 return (nc.ui.cmp.cash.filter.BankAccountRefModelFilter)context.get("nc.ui.cmp.cash.filter.BankAccountRefModelFilter#943e60");
  nc.ui.cmp.cash.filter.BankAccountRefModelFilter bean = new nc.ui.cmp.cash.filter.BankAccountRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.BankAccountRefModelFilter#943e60",bean);
  bean.setSrcKey("pk_currency");
  bean.setDestKey("pk_bankaccount");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter getFundOrgBankAccRefModelFilter_8b13ff(){
 if(context.get("nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter#8b13ff")!=null)
 return (nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter)context.get("nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter#8b13ff");
  nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter bean = new nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.FundOrgBankAccRefModelFilter#8b13ff",bean);
  bean.setSrcKey("pk_bankaccount");
  bean.setDestKey("pk_fundbankaccount");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter getBalanceTypeRefModelFilter_ce030b(){
 if(context.get("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#ce030b")!=null)
 return (nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter)context.get("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#ce030b");
  nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter bean = new nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter();
  context.put("nc.ui.cmp.cash.filter.BalanceTypeRefModelFilter#ce030b",bean);
  bean.setSrcKey("pk_balatype");
  bean.setDestKey("pk_balatype");
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

private List getManagedList45(){  List list = new ArrayList();  list.add("36070DC");  return list;}

public nc.ui.cmp.cash.model.CashDepositModel getManageAppModel(){
 if(context.get("manageAppModel")!=null)
 return (nc.ui.cmp.cash.model.CashDepositModel)context.get("manageAppModel");
  nc.ui.cmp.cash.model.CashDepositModel bean = new nc.ui.cmp.cash.model.CashDepositModel();
  context.put("manageAppModel",bean);
  bean.setContext(getContext());
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setBillType("36S1");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.model.CashDepositDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.cmp.cash.model.CashDepositDataManager)context.get("modelDataManager");
  nc.ui.cmp.cash.model.CashDepositDataManager bean = new nc.ui.cmp.cash.model.CashDepositDataManager();
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

public nc.ui.cmp.cash.model.CashDepositModelService getManageModelService(){
 if(context.get("manageModelService")!=null)
 return (nc.ui.cmp.cash.model.CashDepositModelService)context.get("manageModelService");
  nc.ui.cmp.cash.model.CashDepositModelService bean = new nc.ui.cmp.cash.model.CashDepositModelService();
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
  bean.setTangramLayoutRoot(getTBNode_4927fc());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_4927fc(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#4927fc")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#4927fc");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#4927fc",bean);
  bean.setTabs(getManagedList46());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList46(){  List list = new ArrayList();  list.add(getHSNode_1d3f233());  list.add(getVSNode_f56b14());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_1d3f233(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#1d3f233")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#1d3f233");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#1d3f233",bean);
  bean.setLeft(getCNode_1d80ce4());
  bean.setRight(getCNode_1368305());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1d80ce4(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1d80ce4")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1d80ce4");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1d80ce4",bean);
  bean.setComponent(getQueryArea());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1368305(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1368305")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1368305");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1368305",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_f56b14(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#f56b14")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#f56b14");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#f56b14",bean);
  bean.setUp(getCNode_1c208bf());
  bean.setDown(getCNode_1cc68d0());
  bean.setDividerLocation(31f);
  bean.setShowMode("NoDivider");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1c208bf(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1c208bf")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1c208bf");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1c208bf",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1cc68d0(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1cc68d0")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1cc68d0");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1cc68d0",bean);
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

public nc.ui.cmp.cash.view.CashDepositListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.cmp.cash.view.CashDepositListView)context.get("listView");
  nc.ui.cmp.cash.view.CashDepositListView bean = new nc.ui.cmp.cash.view.CashDepositListView();
  context.put("listView",bean);
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("36070DC");
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

private List getManagedList48(){  List list = new ArrayList();  list.add("cmp_cashdeposit");  return list;}

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

private List getManagedList49(){  List list = new ArrayList();  list.add(getQueryParam_e01189());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_e01189(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#e01189")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#e01189");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#e01189",bean);
  bean.setMdfullname("cmp.cmp_cashdeposit");
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

private List getManagedList50(){  List list = new ArrayList();  list.add(getUserdefQueryParam_9f5bac());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_9f5bac(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#9f5bac")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#9f5bac");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#9f5bac",bean);
  bean.setMdfullname("cmp.cmp_cashdeposit");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.cmp.cash.listener.CashDepositOrgListener getFinanceOrgChangedMediator(){
 if(context.get("financeOrgChangedMediator")!=null)
 return (nc.ui.cmp.cash.listener.CashDepositOrgListener)context.get("financeOrgChangedMediator");
  nc.ui.cmp.cash.listener.CashDepositOrgListener bean = new nc.ui.cmp.cash.listener.CashDepositOrgListener();
  context.put("financeOrgChangedMediator",bean);
  bean.setBillform(getCardEditor());
  bean.setModel(getManageAppModel());
  bean.setOrgChangedImpl(getOrgChanged_3b7b92());
  bean.setCheckedDate("inputdate");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged getOrgChanged_3b7b92(){
 if(context.get("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#3b7b92")!=null)
 return (nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged)context.get("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#3b7b92");
  nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged bean = new nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged();
  context.put("nc.ui.pubapp.uif2app.mediator.modelevent.OrgChanged#3b7b92",bean);
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

private List getManagedList51(){  List list = new ArrayList();  list.add(getUserdefQueryParam_12decef());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_12decef(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#12decef")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#12decef");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#12decef",bean);
  bean.setMdfullname("cmp.cmp_cashdeposit");
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

public nc.ui.cmp.cash.view.CashDepositCardEditor getCardEditor(){
 if(context.get("cardEditor")!=null)
 return (nc.ui.cmp.cash.view.CashDepositCardEditor)context.get("cardEditor");
  nc.ui.cmp.cash.view.CashDepositCardEditor bean = new nc.ui.cmp.cash.view.CashDepositCardEditor();
  context.put("cardEditor",bean);
  bean.setModel(getManageAppModel());
  bean.setNodekey("36070DC");
  bean.setTemplateContainer(getTemplateContainer());
  bean.setTemplateNotNullValidate(true);
  bean.setClosingListener(getClosingListener());
  bean.setUserdefitemPreparator(getUserdefitemPreparator());
  bean.setDefaultRefWherePartHandler(getDefaultRefWherePartHandler());
  bean.setComponentValueManager(getBillCardPanelMetaDataFullValueAdapter_1e6ba0());
  bean.setAutoAddLine(true);
  bean.setBlankChildrenFilter(getSingleFieldBlankChildrenFilter_12bd98a());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter getBillCardPanelMetaDataFullValueAdapter_1e6ba0(){
 if(context.get("nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter#1e6ba0")!=null)
 return (nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter)context.get("nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter#1e6ba0");
  nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter bean = new nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter();
  context.put("nc.ui.pubapp.uif2app.view.value.BillCardPanelMetaDataFullValueAdapter#1e6ba0",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter getSingleFieldBlankChildrenFilter_12bd98a(){
 if(context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#12bd98a")!=null)
 return (nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter)context.get("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#12bd98a");
  nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter bean = new nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter();
  context.put("nc.ui.pubapp.uif2app.view.value.SingleFieldBlankChildrenFilter#12bd98a",bean);
  bean.setFieldName("pk_cashdeposit");
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

public nc.ui.cmp.base.listener.CashDepositFuncNodeInitDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.cmp.base.listener.CashDepositFuncNodeInitDataListener)context.get("InitDataListener");
  nc.ui.cmp.base.listener.CashDepositFuncNodeInitDataListener bean = new nc.ui.cmp.base.listener.CashDepositFuncNodeInitDataListener();
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
