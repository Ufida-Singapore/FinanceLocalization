package nc.ui.arap.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import nc.funcnode.ui.action.INCAction;
import nc.ui.arap.model.ArapBillManageModel;
import nc.ui.arap.pub.GatheringCombin;
import nc.ui.arap.view.ArapBillCardForm;
import nc.ui.pubapp.uif2app.view.BillListView;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.vo.arap.gathering.AggGatheringBillVO;
import nc.vo.arap.gathering.CombinCacheVO;
import nc.vo.arap.gathering.CombinResultVO;
import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.arap.pub.res.ParameterList;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pubapp.pattern.pub.MapList;

/***
 * 明细显示按钮Action Add by bl 2015-04-23
 */
public class GatheringShowChgAction extends NCAction {

	String details = NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
			"summaryext-0001")/* 明细显示 */;
	String summary = NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
			"summaryext-0002")/* 合并显示 */;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ArapBillManageModel model;

	private ArapBillCardForm editor;

	private BillListView listView = null;

	// 是否汇总
	public Boolean isBcombinflag = false;

	// 用于收款单汇总显示缓存
	private CombinCacheVO combinvo;

	public void setCombinCacheVO(CombinCacheVO cachevo) {
		this.combinvo = cachevo;
	}

	public CombinCacheVO getCombinCacheVO() {
		if (null == this.combinvo) {
			this.combinvo = new CombinCacheVO(false);
		}
		return this.combinvo;
	}

	/**
	 * 构造方法
	 */
	public GatheringShowChgAction() {
		super();
		setBtnName(summary)/* 合并显示 */;
		putValue(INCAction.CODE, IPrintActionCode.MERGEDISPLAY);
	}
	
	/***
	 * 按钮更新为合并显示
	 */
	public void initSummary() {
		this.setBtnName(summary)/* 合并显示 */;
		this.combinvo = new CombinCacheVO(false);
	}

	@Override
	public void doAction(ActionEvent arg0) throws Exception {

		GatheringCombin combinutil = new GatheringCombin();
		CombinCacheVO cachevo = this.getCombinCacheVO();

		@SuppressWarnings("unchecked")
		List<AggGatheringBillVO> alldata = this.getModel().getData();
		int selectedRow = this.getModel().getSelectedRow();
		// 显示汇总->显示明细
		if (cachevo.getBcombinflag()) {
			cachevo.setBcombinflag(false);
			//预览凭证判断是否合并  add by weiningc
			model.setCombineFlag(false);
			cachevo.setMapGroupKeys(null);
			AggGatheringBillVO[] chgedvos = combinutil.splitNoEditGathering(
					alldata.toArray(new AggGatheringBillVO[alldata.size()]),
					cachevo.getCombinRela());

			this.initModel(chgedvos);
			this.setBtnName(summary)/* 合并显示 */;

			cachevo.setCombinrela(new MapList<String, GatheringBillItemVO>());
			
			isBcombinflag = false;
		}
		// 显示明细->显示汇总
		else {
			combinutil.updateChildVO(alldata);

			AggGatheringBillVO[] allvos = new AggGatheringBillVO[alldata.size()];
			alldata.toArray(allvos);
			cachevo.setBcombinflag(true);
			cachevo.setCombinrela(null);
			setCombinCacheVO(cachevo);
			this.initModel(allvos);

			this.setBtnName(details)/* 明细显示 */;
			
			isBcombinflag = true;
			//预览凭证判断是否合并  add by weiningc
			model.setCombineFlag(true);
		}

		this.getModel().setSelectedRow(selectedRow);
	}

	public void initModel(Object data) {
		if (null == data) {
			this.getModel().initModel(data);
			return;
		}
		AggGatheringBillVO[] vos = null;
		if (data.getClass().isArray()) {
			vos = (AggGatheringBillVO[]) data;
		} else {
			vos = new AggGatheringBillVO[] { (AggGatheringBillVO) data };
		}

		CombinResultVO combinres = null;
		CombinCacheVO cache = this.getCombinCacheVO();

		if (cache.getBcombinflag() && null != cache.getCombinRela()
				&& cache.getCombinRela().size() > 0) {
			// 正常在汇总状态下列表界面的刷新
			cache.setCombinrela(new MapList<String, GatheringBillItemVO>());
			GatheringCombin combinutil = new GatheringCombin();
			combinres = combinutil.combinGathering(vos, cache,
					ParameterList.AR101.getCode());
			this.getModel().initModel(combinres.getCombinvos());
		} else if (cache.getBcombinflag()
				&& (null == cache.getCombinRela() || cache.getCombinRela()
						.size() == 0)) {
			// 处理从消息中心打开节点问题
			cache.setCombinrela(new MapList<String, GatheringBillItemVO>());
			GatheringCombin combinutil = new GatheringCombin();
			combinres = combinutil.combinGathering(vos, cache,
					ParameterList.AR101.getCode());
			this.getModel().initModel(combinres.getCombinvos());
		} else {
			this.getModel().initModel(vos);
		}
		this.setCombinCacheVO(cache);
	}

	@Override
	protected boolean isActionEnable() {

		return super.isActionEnable()
				&& getModel().getUiState() != UIState.EDIT
				&& getModel().getSelectedData() != null;
	}

	public ArapBillManageModel getModel() {
		return model;
	}

	public void setModel(ArapBillManageModel model) {
		this.model = model;
		model.addAppEventListener(this);
	}

	public ArapBillCardForm getEditor() {
		return editor;
	}

	public void setEditor(ArapBillCardForm editor) {
		this.editor = editor;
	}

	public BillListView getListView() {
		return listView;
	}

	public void setListView(BillListView listView) {
		this.listView = listView;
	}
	
}
