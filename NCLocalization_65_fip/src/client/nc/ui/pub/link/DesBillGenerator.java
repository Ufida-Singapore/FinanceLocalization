package nc.ui.pub.link;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pf.pub.BillTypeCacheKey;
import nc.bs.pf.pub.PfDataCache;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.funcnode.ui.AbstractFunclet;
import nc.funcnode.ui.FuncletContext;
import nc.itf.fip.generate.IGenerateService;
import nc.pubitf.fip.external.IBillLinkQuery;
import nc.pubitf.fip.service.IFipConvertService;
import nc.ui.fip.pub.FipUITools;
import nc.ui.pub.beans.MessageDialog;
import nc.vo.fip.external.FipBillSumRSVO;
import nc.vo.fip.service.FipBaseMessageVO;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fip.trans.FipTransVO;
import nc.vo.fip.trans.FipTranslateResultVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.link.DefaultLinkData;
import nc.vo.pub.link.GenerateParameter;

/**
 * <p>
 * 实现在来源单据节点直接制单，通过会计平台直接显示生成目标单据的界面的功能
 * 
 * 注意：想生成的单据必须是已经发送过会计平台的数据，没有发送过的数据请先发送再调用该服务
 * </p>
 * 
 * 修改记录：<br>
 * <li>修改人：修改日期：修改内容：</li> <br>
 * <br>
 * 
 * @see
 * @author gbh
 * @version V6.0
 * @since V6.0 创建时间：2010-12-28 下午02:17:50
 */
public class DesBillGenerator {
	/**
	 * 在来源单据节点直接制单，直接切换到目标单据界面
	 * 
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param entranceui
	 *            来源单据的界面，ToftPanel可以用直接使用ToftPanel.this，UI工厂2如果取不到ToftPanelAdaptor可以用LoginContext.getEntranceUI();
	 * @param billids
	 *            来源单据类型以及线索号，一个不规则的二维数据，遵守下面约定：1、第一维度按单据类型区分。2、第二维度的第一个元素为单据类型。3、关联号必须放在对应单据类型下
	 * 
	 *            例：String[][] ids = new String[][]{{"D0",relationID1,relationID2}{"D1",relationID3}{"D9",relationID4,relationID5,relationID6,relationID7}}
	 * @param param
	 *            部分需要用到的参数，可以为空，为空的话以默认值为准
	 * @throws Exception
	 * @see
	 * @since V6.0
	 */
	public static void generateDesBill(Component entranceui, String[][] billids, GenerateParameter param) throws Exception {
		generateDesBill(entranceui, billids, param, true);
	}

	public static void generateDesBillNoMsg(Component entranceui, String[][] billids, GenerateParameter param) throws Exception {
		generateDesBill(entranceui, billids, param, false);
	}

	public static void generateDesBill(AbstractFunclet entranceui, String[][] billids, GenerateParameter param) throws Exception {
		generateDesBill(entranceui, billids, param, true);
	}

	public static void generateDesBillNoMsg(AbstractFunclet entranceui, String[][] billids, GenerateParameter param) throws Exception {
		generateDesBill(entranceui, billids, param, false);
	}

	/**
	 * 在来源单据节点预览生成的目标单据，直接切换到目标单据界面
	 * 
	 * <p>
	 * 修改记录：
	 * </p>
	 * 
	 * @param entranceui
	 *            来源单据的界面，ToftPanel可以用直接使用ToftPanel.this，UI工厂2如果取不到ToftPanelAdaptor可以用LoginContext.getEntranceUI();
	 * @param vos
	 *            来源单据，一个不规则的二维数据，遵守下面约定：1、第一维度为分单依据，每个元素会合并生成一张目标单据。2、第二维度为合并依据，该维度下每组元素对应的单据将合并生成.3、
	 * @param desbilltype
	 *            要预览的目标单据类型，目前只支持预览同一个单据类型
	 * @param param
	 *            部分需要用到的参数，可以为空，为空的话以默认值为准
	 * @throws Exception
	 * @see
	 * @since V6.0
	 */
	public static void previewDesBill(Component entranceui, FipMessageVO[][] vos, GenerateParameter param, String[] desbilltype) throws Exception {
		previewDesBill(entranceui, vos, param, desbilltype, true);
	}

	private static void previewDesBill(Component entranceui, FipMessageVO[][] vos, GenerateParameter param, String[] desbilltype, boolean isshowmsg) throws Exception {
		if (entranceui == null || vos == null || vos.length == 0 || desbilltype == null || desbilltype.length == 0)
			return;
		if (desbilltype.length > 1)
			throw new BusinessException("本版本暂不支持在一个界面内同时预览不同类型的目标单据。");
		if (param == null)
			param = new GenerateParameter();
		List<FipTranslateResultVO> rs = new ArrayList<FipTranslateResultVO>();
		for (FipMessageVO[] vogroup : vos) {
			if (vogroup == null || vogroup.length == 0)
				continue;
			Collection<FipBaseMessageVO> svos = new ArrayList<FipBaseMessageVO>();
			for (FipMessageVO fipvo : vogroup) {
				svos.add(fipvo);
			}
			FipRelationInfoVO desinfovo = new FipRelationInfoVO();
			desinfovo.setPk_billtype(desbilltype[0]);
			List<FipTranslateResultVO> trans = convert(desinfovo, svos);
			if (trans != null && !trans.isEmpty())
				rs.addAll(trans);
		}
		List<FipTransVO> lstvo = new ArrayList<FipTransVO>();
		if (!rs.isEmpty()) {
			for (FipTranslateResultVO fipTranslateResultVO : rs) {
				List<FipTransVO> desBills = fipTranslateResultVO.getDesBills();
				if (desBills != null && !desBills.isEmpty())
					lstvo.addAll(desBills);
			}
		}

		if (lstvo.isEmpty()) {
			// 没有目标单据
			if (isshowmsg) {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0118")/* @res "没有可以生成的目标单据，目标单据已生成正式单据或已删除。" */);
			}
		} else {
			//add by weiningc 633适配至65  会计平台支持凭证预览   20171016 会计平台支持凭证预览  start
			lstvo = NCLocator.getInstance().lookup(IFipConvertService.class).mergerDetails(lstvo);
			//add by weiningc 633适配至65  会计平台支持凭证预览   20171016 会计平台支持凭证预览  end
			ArrayList<String> desbilllist = new ArrayList<String>();
			ArrayList<Object> volist = new ArrayList<Object>();
			for (FipTransVO vo : lstvo) {
				String pkBilltype = vo.getMessagevo().getDesRelation().getPk_billtype();
				if (!desbilllist.contains(pkBilltype)) {
					desbilllist.add(pkBilltype);
				}
				volist.add(vo.getDatavo());
			}
			if (desbilllist.size() == 1) {
				// 只有一种目标单据类型，直接切换到目标单据
				String billtype = desbilllist.get(0);
				// if (entranceui instanceof AbstractFunclet) {
				// FuncletContext fc = ((AbstractFunclet) entranceui).getFuncletContext();
				// if (fc != null) {
				// String nodecode = null;
				// try {
				// nodecode = PfDataCache.getBillType(new BillTypeCacheKey().buildPkGroup(WorkbenchEnvironment.getInstance().getGroupVO().getPk_group()).buildBilltype(billtype)).getNodecode();
				// } catch (Exception e) {
				// Logger.error("", e);
				// }
				// if (nodecode != null)
				// fc.removeWindow(nodecode);
				// }
				// }
				IBillLinkQuery billLinkQuery = FipBillLinkQueryCenter.getBillLinkQuery(FipUITools.getLoginPk_group(), billtype);
				if (billLinkQuery == null)
					BillLinkOneNodeCenter.linkQuery_Dialog(entranceui, billtype, new String[] {}, volist.toArray(), false);
				else {
					DefaultLinkData userdata = new DefaultLinkData();
					userdata.setBillType(billtype);
					userdata.setBillIDs(new String[] {});
					userdata.setBillVOs(volist);
					userdata.setUserObject(volist);
					billLinkQuery.showLinkQueryDialog(entranceui, userdata, null, false);
				}
			} else {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0121")/* @res "所选单据生成的目标单据不属于同一单据类型，无法直接切换，请到单据生成节点按目标单据类型分别生成。" */);
			}
		}
	}

	private static List<FipTranslateResultVO> convert(FipRelationInfoVO desinfovo, Collection<FipBaseMessageVO> svos) throws BusinessException {
		return NCLocator.getInstance().lookup(IFipConvertService.class).convertOnly(desinfovo, svos);
	}

	private static void generateDesBill(Component entranceui, String[][] billids, GenerateParameter param, boolean isshowmsg) throws Exception {
		if (entranceui == null || billids == null || billids.length == 0)
			return;
		if (param == null)
			param = new GenerateParameter();
		IGenerateService ip = NCLocator.getInstance().lookup(IGenerateService.class);
		ArrayList<List<FipRelationInfoVO>> list = new ArrayList<List<FipRelationInfoVO>>();
		if (param.isSplitByBilltype()) {
			// 需要按单据类型拆分的。
			for (int i = 0; i < billids.length; i++) {
				String[] billpks = billids[i];
				if (billpks == null || billpks.length < 2)
					continue;
				String billtype = billpks[0];
				List<FipRelationInfoVO> srclist = new ArrayList<FipRelationInfoVO>();
				for (int j = 1; j < billpks.length; j++) {
					String pk_bill = billpks[j];
					if (pk_bill == null)
						continue;
					FipRelationInfoVO infovo = new FipRelationInfoVO();
					infovo.setPk_billtype(billtype);
					infovo.setRelationID(pk_bill);
					srclist.add(infovo);
				}
				if (!srclist.isEmpty()) {
					list.add(srclist);
				}
			}
		} else {
			// 不按单据类型拆分的，合并成一张目标单据（对同一种目标类型而言）
			ArrayList<FipRelationInfoVO> srclist = new ArrayList<FipRelationInfoVO>();
			for (int i = 0; i < billids.length; i++) {
				String[] billpks = billids[i];
				if (billpks == null || billpks.length < 2)
					continue;
				String billtype = billpks[0];
				for (int j = 1; j < billpks.length; j++) {
					String pk_bill = billpks[j];
					if (pk_bill == null)
						continue;
					FipRelationInfoVO infovo = new FipRelationInfoVO();
					infovo.setPk_billtype(billtype);
					infovo.setRelationID(pk_bill);
					srclist.add(infovo);
				}
			}
			if (!srclist.isEmpty()) {
				list.add(srclist);
			}
		}
		if (list.isEmpty())
			return;
		List<FipBillSumRSVO> generateBySrcInfo = ip.generateBySrcInfo(list);
		if (generateBySrcInfo == null || generateBySrcInfo.isEmpty()) {
			// 没有目标单据
			if (isshowmsg) {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0118")/* @res "没有可以生成的目标单据，目标单据已生成正式单据或已删除。" */);
			}
		} else {
			ArrayList<String> desbilltype = new ArrayList<String>();
			ArrayList<Object> volist = new ArrayList<Object>();
			for (FipBillSumRSVO vo : generateBySrcInfo) {
				String pkBilltype = vo.getMessageinfo().getPk_billtype();
				if (!desbilltype.contains(pkBilltype)) {
					desbilltype.add(pkBilltype);
				}
				volist.add(vo.getBillVO());
			}
			if (desbilltype.size() == 1) {
				// 只有一种目标单据类型，直接切换到目标单据
				String billtype = desbilltype.get(0);
				if (entranceui instanceof AbstractFunclet) {
					FuncletContext fc = ((AbstractFunclet) entranceui).getFuncletContext();
					if (fc != null) {
						String nodecode = null;
						try {
							nodecode = PfDataCache.getBillType(new BillTypeCacheKey().buildPkGroup(WorkbenchEnvironment.getInstance().getGroupVO().getPk_group()).buildBilltype(billtype)).getNodecode();
						} catch (Exception e) {
							Logger.error("", e);
						}
						if (nodecode != null)
							fc.removeWindow(nodecode);
						else
							throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0119")/* @res "单据类型或交易类型" */+ billtype + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0120")/* @res "没有对应的节点，不能以打开节点的方式进行联查。" */);
					}
				}
				BillLinkOneNodeCenter.linkAdd_Dialog(entranceui, billtype, null, volist.toArray(), true, null);
			} else {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0121")/* @res "所选单据生成的目标单据不属于同一单据类型，无法直接切换，请到单据生成节点按目标单据类型分别生成。" */);
			}
		}
	}

	public static void showErrorMessage(Component parentui, String titlemsg, String detailmsg) {
		if (parentui instanceof AbstractFunclet)
			((AbstractFunclet) parentui).showErrorMessage(titlemsg, detailmsg);
		else {
			if (!(parentui instanceof Container)) {
				parentui = parentui.getParent();
			}
			MessageDialog.showErrorDlg((Container) parentui, titlemsg, detailmsg);
		}
	}
}
