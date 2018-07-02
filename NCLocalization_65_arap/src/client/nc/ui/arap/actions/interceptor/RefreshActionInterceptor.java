package nc.ui.arap.actions.interceptor;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.arap.actions.GatheringShowChgAction;
import nc.ui.uif2.actions.ActionInterceptor;

/***
 * 收款单管理刷新按钮拦截
 * @author Administrator
 *
 */
public class RefreshActionInterceptor  implements ActionInterceptor {
	
	private GatheringShowChgAction gathering;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		return true;
	}

	@Override
	public void afterDoActionSuccessed(Action action, ActionEvent e) {
		//动作执行成功后
		if(gathering.isBcombinflag){
			gathering.initSummary();
		}
	}

	@Override
	public boolean afterDoActionFailed(Action action, ActionEvent e,
			Throwable ex) {
		//动作执行失败后
		return false;
	}

	public GatheringShowChgAction getGathering() {
		return gathering;
	}

	public void setGathering(GatheringShowChgAction gathering) {
		this.gathering = gathering;
	}

}
