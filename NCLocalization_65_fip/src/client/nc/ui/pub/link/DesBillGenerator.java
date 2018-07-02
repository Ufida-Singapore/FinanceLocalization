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
 * ʵ������Դ���ݽڵ�ֱ���Ƶ���ͨ�����ƽֱ̨����ʾ����Ŀ�굥�ݵĽ���Ĺ���
 * 
 * ע�⣺�����ɵĵ��ݱ������Ѿ����͹����ƽ̨�����ݣ�û�з��͹����������ȷ����ٵ��ø÷���
 * </p>
 * 
 * �޸ļ�¼��<br>
 * <li>�޸��ˣ��޸����ڣ��޸����ݣ�</li> <br>
 * <br>
 * 
 * @see
 * @author gbh
 * @version V6.0
 * @since V6.0 ����ʱ�䣺2010-12-28 ����02:17:50
 */
public class DesBillGenerator {
	/**
	 * ����Դ���ݽڵ�ֱ���Ƶ���ֱ���л���Ŀ�굥�ݽ���
	 * 
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param entranceui
	 *            ��Դ���ݵĽ��棬ToftPanel������ֱ��ʹ��ToftPanel.this��UI����2���ȡ����ToftPanelAdaptor������LoginContext.getEntranceUI();
	 * @param billids
	 *            ��Դ���������Լ������ţ�һ��������Ķ�ά���ݣ���������Լ����1����һά�Ȱ������������֡�2���ڶ�ά�ȵĵ�һ��Ԫ��Ϊ�������͡�3�������ű�����ڶ�Ӧ����������
	 * 
	 *            ����String[][] ids = new String[][]{{"D0",relationID1,relationID2}{"D1",relationID3}{"D9",relationID4,relationID5,relationID6,relationID7}}
	 * @param param
	 *            ������Ҫ�õ��Ĳ���������Ϊ�գ�Ϊ�յĻ���Ĭ��ֵΪ׼
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
	 * ����Դ���ݽڵ�Ԥ�����ɵ�Ŀ�굥�ݣ�ֱ���л���Ŀ�굥�ݽ���
	 * 
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param entranceui
	 *            ��Դ���ݵĽ��棬ToftPanel������ֱ��ʹ��ToftPanel.this��UI����2���ȡ����ToftPanelAdaptor������LoginContext.getEntranceUI();
	 * @param vos
	 *            ��Դ���ݣ�һ��������Ķ�ά���ݣ���������Լ����1����һά��Ϊ�ֵ����ݣ�ÿ��Ԫ�ػ�ϲ�����һ��Ŀ�굥�ݡ�2���ڶ�ά��Ϊ�ϲ����ݣ���ά����ÿ��Ԫ�ض�Ӧ�ĵ��ݽ��ϲ�����.3��
	 * @param desbilltype
	 *            ҪԤ����Ŀ�굥�����ͣ�Ŀǰֻ֧��Ԥ��ͬһ����������
	 * @param param
	 *            ������Ҫ�õ��Ĳ���������Ϊ�գ�Ϊ�յĻ���Ĭ��ֵΪ׼
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
			throw new BusinessException("���汾�ݲ�֧����һ��������ͬʱԤ����ͬ���͵�Ŀ�굥�ݡ�");
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
			// û��Ŀ�굥��
			if (isshowmsg) {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0118")/* @res "û�п������ɵ�Ŀ�굥�ݣ�Ŀ�굥����������ʽ���ݻ���ɾ����" */);
			}
		} else {
			//add by weiningc 633������65  ���ƽ̨֧��ƾ֤Ԥ��   20171016 ���ƽ̨֧��ƾ֤Ԥ��  start
			lstvo = NCLocator.getInstance().lookup(IFipConvertService.class).mergerDetails(lstvo);
			//add by weiningc 633������65  ���ƽ̨֧��ƾ֤Ԥ��   20171016 ���ƽ̨֧��ƾ֤Ԥ��  end
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
				// ֻ��һ��Ŀ�굥�����ͣ�ֱ���л���Ŀ�굥��
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
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0121")/* @res "��ѡ�������ɵ�Ŀ�굥�ݲ�����ͬһ�������ͣ��޷�ֱ���л����뵽�������ɽڵ㰴Ŀ�굥�����ͷֱ����ɡ�" */);
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
			// ��Ҫ���������Ͳ�ֵġ�
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
			// �����������Ͳ�ֵģ��ϲ���һ��Ŀ�굥�ݣ���ͬһ��Ŀ�����Ͷ��ԣ�
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
			// û��Ŀ�굥��
			if (isshowmsg) {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0118")/* @res "û�п������ɵ�Ŀ�굥�ݣ�Ŀ�굥����������ʽ���ݻ���ɾ����" */);
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
				// ֻ��һ��Ŀ�굥�����ͣ�ֱ���л���Ŀ�굥��
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
							throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0119")/* @res "�������ͻ�������" */+ billtype + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0120")/* @res "û�ж�Ӧ�Ľڵ㣬�����Դ򿪽ڵ�ķ�ʽ�������顣" */);
					}
				}
				BillLinkOneNodeCenter.linkAdd_Dialog(entranceui, billtype, null, volist.toArray(), true, null);
			} else {
				showErrorMessage(entranceui, "", nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("1017clt_0", "01017clt-0121")/* @res "��ѡ�������ɵ�Ŀ�굥�ݲ�����ͬһ�������ͣ��޷�ֱ���л����뵽�������ɽڵ㰴Ŀ�굥�����ͷֱ����ɡ�" */);
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
