package nc.ui.arap.pub.para;

import nc.ui.trade.component.ListToListPanel;

public class GatheringCombinDefPanel extends ListToListPanel {

  private static final long serialVersionUID = 1L;

  public GatheringCombinDefPanel(GatheringCombinDlg.ParaListItemInfo[] leftfields,
		  GatheringCombinDlg.ParaListItemInfo[] selectedfields) {
	  GatheringCombinDlg.ParaListItemInfo[] rights = selectedfields;
    if (null == selectedfields) {
      rights = new GatheringCombinDlg.ParaListItemInfo[0];
    }

    this.initData(leftfields, rights);
  }

  public void initData(GatheringCombinDlg.ParaListItemInfo[] leftfields,
		  GatheringCombinDlg.ParaListItemInfo[] selectedfields) {
    this.setLeftAndRightData(leftfields, selectedfields);
  }

}
