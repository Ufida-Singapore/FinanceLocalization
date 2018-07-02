package nc.ui.arap.pub.para;

import nc.ui.trade.component.ListToListPanel;

public class PayBillCombinDefPanel extends ListToListPanel {

	private static final long serialVersionUID = 1L;

	public PayBillCombinDefPanel(
			PayBillCombinDlg.ParaListItemInfo[] leftfields,
			PayBillCombinDlg.ParaListItemInfo[] selectedfields) {
		PayBillCombinDlg.ParaListItemInfo[] rights = selectedfields;
		if (null == selectedfields) {
			rights = new PayBillCombinDlg.ParaListItemInfo[0];
		}

		this.initData(leftfields, rights);
	}

	public void initData(PayBillCombinDlg.ParaListItemInfo[] leftfields,
			PayBillCombinDlg.ParaListItemInfo[] selectedfields) {
		this.setLeftAndRightData(leftfields, selectedfields);
	}

}