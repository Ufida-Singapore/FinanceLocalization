package nc.ui.arap.pub.para;

import nc.ui.trade.component.ListToListPanel;

public class PayEntryCombinDefPanel extends ListToListPanel {

	private static final long serialVersionUID = 1L;

	public PayEntryCombinDefPanel(
			PayEntryCombinDlg.ParaListItemInfo[] leftfields,
			PayEntryCombinDlg.ParaListItemInfo[] selectedfields) {
		PayEntryCombinDlg.ParaListItemInfo[] rights = selectedfields;
		if (null == selectedfields) {
			rights = new PayEntryCombinDlg.ParaListItemInfo[0];
		}

		this.initData(leftfields, rights);
	}

	public void initData(PayEntryCombinDlg.ParaListItemInfo[] leftfields,
			PayEntryCombinDlg.ParaListItemInfo[] selectedfields) {
		this.setLeftAndRightData(leftfields, selectedfields);
	}

}