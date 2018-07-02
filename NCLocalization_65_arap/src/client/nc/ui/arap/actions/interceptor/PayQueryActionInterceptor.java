package nc.ui.arap.actions.interceptor;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.arap.actions.PayableShowChgAction;
import nc.ui.pubapp.uif2app.query2.action.DefaultQueryAction;
import nc.ui.uif2.actions.ActionInterceptor;

/***
 * 付款单管理查询按钮拦截
 * @author Administrator
 *
 */
public class PayQueryActionInterceptor  implements ActionInterceptor {
	
	private PayableShowChgAction payable;
	private DefaultQueryAction queryAction;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		//动作执行前
		return true;
	}

	@Override
	public void afterDoActionSuccessed(Action action, ActionEvent e) {
		//动作执行成功后
		if(payable.isBcombinflag){
			payable.initSummary();
		}
	}

	@Override
	public boolean afterDoActionFailed(Action action, ActionEvent e,
			Throwable ex) {
		//动作执行失败后
		return false;
	}

	public PayableShowChgAction getPayable() {
		return payable;
	}

	public void setPayable(PayableShowChgAction payable) {
		this.payable = payable;
	}
	
	public DefaultQueryAction getQueryAction() {
		return queryAction;
	}

	public void setQueryAction(DefaultQueryAction queryAction) {
		this.queryAction = queryAction;
		this.queryAction.setShowProgress(false);
	}


}
