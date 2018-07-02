package nc.ui.arap.actions.interceptor;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import nc.ui.arap.actions.PayEntryShowChgAction;
import nc.ui.uif2.actions.ActionInterceptor;

/***
 * 付款单录入修改按钮拦截
 * @author Administrator
 *
 */
public class PayEntryEditActionInterceptor  implements ActionInterceptor {
	
	private PayEntryShowChgAction payable;

	@Override
	public boolean beforeDoAction(Action action, ActionEvent e) {
		//动作执行前
		boolean flag = false;
		if(payable.isBcombinflag){
			try {
				payable.doAction(e);
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

	public PayEntryShowChgAction getPayable() {
		return payable;
	}

	public void setPayable(PayEntryShowChgAction payable) {
		this.payable = payable;
	}

}