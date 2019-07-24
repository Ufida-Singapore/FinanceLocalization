package nc.ui.arap.recpaystatement.view;

import nc.ui.pubapp.uif2app.view.BillListView;
import nc.vo.pub.bill.BillTempletVO;

public class ListView extends BillListView {

	@Override
	protected void createListPanel() {
		super.createListPanel();
	}
	
	BillTempletVO templatevo = null;

	@Override
	protected void processTemplateVO(BillTempletVO templatevo) {
		this.templatevo = templatevo;
		super.processTemplateVO(templatevo);
	}

	public BillTempletVO getTemplatevo() {
		return templatevo;
	}

	public void setTemplatevo(BillTempletVO templatevo) {
		this.templatevo = templatevo;
	}
	
	public void setListMultiProp(){
		super.setListMultiProp();
	}
	
	
}
