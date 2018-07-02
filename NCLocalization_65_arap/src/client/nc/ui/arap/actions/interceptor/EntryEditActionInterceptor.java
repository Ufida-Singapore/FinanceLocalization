package nc.ui.arap.actions.interceptor;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.arap.actions.GatherEntryShowChgAction;
import nc.ui.uif2.actions.ActionInterceptor;

/***
 * 收款单录入修改按钮拦截
 * @author Administrator
 *
 */
public class EntryEditActionInterceptor implements ActionInterceptor {
	
	private GatherEntryShowChgAction gathering;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		//动作执行前
		boolean flag = false;
		if(gathering.isBcombinflag){
			try {
				gathering.doAction(e);
				flag = true;
			} catch (Exception e1) {
				e1.printStackTrace();
				flag = false;
			}
		}else {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void afterDoActionSuccessed(Action action, ActionEvent e) {
		//动作执行成功后
	}

	@Override
	public boolean afterDoActionFailed(Action action, ActionEvent e,
			Throwable ex) {
		//动作执行失败后
		return false;
	}

	public GatherEntryShowChgAction getGathering() {
		return gathering;
	}

	public void setGathering(GatherEntryShowChgAction gathering) {
		this.gathering = gathering;
	}

}

