package nc.ui.arap.recpaystatement.action;

import java.awt.event.ActionEvent;

import nc.bs.uif2.IActionCode;
import nc.ui.arap.bill.ArapBsPrDataSource;
import nc.ui.arap.print.ArapStatementPrintDataSource;
import nc.ui.pub.print.PrintEntry;
import nc.ui.pubapp.uif2app.view.BillListView;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.actions.ActionInitializer;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.arap.pub.ArapConstant;
import nc.vo.uif2.LoginContext;

public class ReportTemplatePrintAction extends NCAction {

	private static final long serialVersionUID = 1L;
	private static String RECSTATEMENT_NODECODE = "20061013";
	private static String PAYSTATEMENT_NODECODE = "20081013";
	
	private BillListView listView = null;
	private String outputFlag = ArapConstant.PRINT_PREVIEW;

	protected AbstractAppModel model = null;

	public ReportTemplatePrintAction() {
		super();
		ActionInitializer.initializeAction(this, IActionCode.TEMPPRINT);
		this.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000007")/*@res "打印"*/);
	}

	public void doAction(ActionEvent e) throws Exception {
		setOutputFlag(ArapConstant.PRINT_PRINT);
		doPrint();
	}

	protected boolean isActionEnable() {
		if (getListView().getModel().getSelectedData() == null) {
			return false;
		}

		int sltBodyRow = getListView().getBillListPanel().getHeadTable().getSelectedRow();

		if (sltBodyRow == -1) {
			return false;
		}

		return true;
	}

	public ArapBsPrDataSource getDataSource() {
		return new ArapStatementPrintDataSource(model, false);
	}
	
	public ArapBsPrDataSource getDataSource(int index) {
		return new ArapStatementPrintDataSource(model, false, index);
	}

	public AbstractAppModel getModel() {
		return model;
	}

	public void setModel(AbstractAppModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

	public BillListView getListView() {
		return listView;
	}

	public void setListView(BillListView listView) {
		this.listView = listView;
	}

	public String getOutputFlag() {
		return outputFlag;
	}

	public void setOutputFlag(String outputFlag) {
		this.outputFlag = outputFlag;
	}

	@SuppressWarnings("static-access")
	protected void doPrint() {
		LoginContext ctx = getModel().getContext();
		String nodeCode = ctx.getNodeCode();
		PrintEntry printEntry = null;
		Integer[] selectedOperaRows = getListView().getModel().getSelectedOperaRows();
		if (this.RECSTATEMENT_NODECODE.equals(nodeCode) || this.PAYSTATEMENT_NODECODE.equals(nodeCode)) {
			if(selectedOperaRows.length==1){
				int index = ((nc.ui.pubapp.uif2app.model.BillManageModel) getModel()).getSelectedRow();
				printEntry = new PrintEntry(getListView(), getDataSource(index));
			}else{
				printEntry = new PrintEntry(getListView());
				for (Integer i : selectedOperaRows) {
					printEntry.setDataSource(getDataSource(i));
				}
						
			}
		} else {
			printEntry = new PrintEntry(getListView(), getDataSource());
		}
		
		printEntry.setTemplateID(ctx.getPk_group(), ctx.getNodeCode(), ctx.getPk_loginUser(), null, getListView().getNodekey());
		
		if (printEntry.selectTemplate() != 1) {
			return;
		}

		if (ArapConstant.PRINT_PREVIEW.equals(getOutputFlag())) {
			printEntry.preview();
		} else if (ArapConstant.PRINT_PRINT.equals(getOutputFlag())) {
			printEntry.print();
		} else if (ArapConstant.PRINT_OUTPUT.equals(getOutputFlag())) {
			printEntry.output();
		}

		ShowStatusBarMsgUtil.showStatusBarMsg(getBtnName() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0","02006v61008-0100")/*@res "操作成功"*/, getModel().getContext());
	}

}

// /:~
