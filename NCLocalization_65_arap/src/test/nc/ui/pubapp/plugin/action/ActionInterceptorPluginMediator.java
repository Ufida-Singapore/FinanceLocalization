package nc.ui.pubapp.plugin.action;

import java.util.Map;

import nc.ui.pubapp.uif2app.actions.interceptor.CompositeActionInterceptor;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.actions.ActionInterceptor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

public class ActionInterceptorPluginMediator implements BeanFactoryAware {

  @Override
  public void setBeanFactory(BeanFactory beanFactory) {
    ListableBeanFactory factory = (ListableBeanFactory) beanFactory;
    // Map<String, ActionInterceptorInfo> map =
    // factory.getBeansOfType(ActionInterceptorInfo.class);
    Map<String, ActionInterceptorInfo> map =
        BeanFactoryUtils.beansOfTypeIncludingAncestors(factory,
            ActionInterceptorInfo.class);
    for (ActionInterceptorInfo info : map.values()) {
      this.addInterceptor(info.getInterceptor(), info.getTarget());
    }
  }

  private void addInterceptor(ActionInterceptor interceptor, NCAction action) {
    if (null == action.getInterceptor()) {
      action.setInterceptor(interceptor);
    }
    else {
    	//add chenth 20181006 ARAP没事瞎弄什么自己的类
    	if(action.getInterceptor() instanceof nc.ui.arap.actions.interceptor.CompositeActionInterceptor){
    		nc.ui.arap.actions.interceptor.CompositeActionInterceptor cai = (nc.ui.arap.actions.interceptor.CompositeActionInterceptor) action.getInterceptor();
    		cai.addInterceptor(interceptor);
    	}else{
	      CompositeActionInterceptor cai = new CompositeActionInterceptor();
	      cai.addInterceptor(action.getInterceptor());
	      cai.addInterceptor(interceptor);
	      action.setInterceptor(cai);
    	}
    }
  }
}
