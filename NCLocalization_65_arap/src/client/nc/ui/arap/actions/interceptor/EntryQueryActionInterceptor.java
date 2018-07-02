package nc.ui.arap.actions.interceptor;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.arap.actions.GatherEntryShowChgAction;
import nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction;
import nc.ui.uif2.actions.ActionInterceptor;

/***
 * 收款单录入查询按钮拦截
 * @author Administrator
 *
 */
public class EntryQueryActionInterceptor  implements ActionInterceptor {
	
	private GatherEntryShowChgAction gathering;
	private DefaultQueryAction queryAction;

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

	public GatherEntryShowChgAction getGathering() {
		return gathering;
	}

	public void setGathering(GatherEntryShowChgAction gathering) {
		this.gathering = gathering;
	}
	
	public DefaultQueryAction getQueryAction() {
		return queryAction;
	}

	public void setQueryAction(DefaultQueryAction queryAction) {
		this.queryAction = queryAction;
		this.queryAction.setShowProgress(false);
	}

}