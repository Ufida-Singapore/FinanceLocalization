package nc.ui.arap.pub.para;

import nc.ui.trade.component.ListToListPanel;

public class GatherEntryCombinDefPanel extends ListToListPanel {

  private static final long serialVersionUID = 1L;

  public GatherEntryCombinDefPanel(GatherEntryCombinDlg.ParaListItemInfo[] leftfields,
		  GatherEntryCombinDlg.ParaListItemInfo[] selectedfields) {
	  GatherEntryCombinDlg.ParaListItemInfo[] rights = selectedfields;
    if (null == selectedfields) {
      rights = new GatherEntryCombinDlg.ParaListItemInfo[0];
    }

    this.initData(leftfields, rights);
  }

  public void initData(GatherEntryCombinDlg.ParaListItemInfo[] leftfields,
		  GatherEntryCombinDlg.ParaListItemInfo[] selectedfields) {
    this.setLeftAndRightData(leftfields, selectedfields);
  }

}
