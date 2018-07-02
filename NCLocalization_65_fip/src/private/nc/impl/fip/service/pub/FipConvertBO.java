package nc.impl.fip.service.pub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.core.util.ObjectCreator;
import nc.impl.fip.operatinglog.FipOperatingLogBO;
import nc.itf.fip.pub.FipInterfaceCenter;
import nc.pub.fip.pubtools.FipBSCacheCleaner;
import nc.pubitf.fip.external.IBillDataFiller;
import nc.pubitf.fip.external.IDesBillSumService;
import nc.vo.fip.external.FipBillSumRSVO;
import nc.vo.fip.service.FipBaseMessageVO;
import nc.vo.fip.service.FipBasicRelationVO;
import nc.vo.fip.service.FipExtRelationVO;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fip.trans.FipTransVO;
import nc.vo.fip.trans.FipTranslateResultVO;
import nc.vo.pub.BusinessException;

public class FipConvertBO {
	public void convertAndSave(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException {
		if (desInfo == null || srcbill == null || srcbill.isEmpty())
			return;
		FipBSCacheCleaner.clearAllCache();
		FipBasicRelationVO relation = new FipBasicRelationVO();
		relation.setSrcRelation(srcbill.iterator().next().getMessageinfo());
		relation.setDesRelation(desInfo);
		new FipOperatingLogBO().drawSrcBillByBill(relation, srcbill);
		// String pk_group = null;
		// for (FipBaseMessageVO fipBaseMessageVO : srcbill) {
		// if (pk_group == null)
		// pk_group = fipBaseMessageVO.getMessageinfo().getPk_group();
		// }
	}

	public List<FipTranslateResultVO> convertOnly(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException {
		if (desInfo == null || srcbill == null || srcbill.isEmpty())
			return null;
		List<FipTranslateResultVO> rs = null;
		FipBSCacheCleaner.clearAllCache();
		if (desInfo.getPk_org() == null) {
			// 走入账设置
			List<FipMessageVO> fipmessagelist = new ArrayList<FipMessageVO>();
			for (FipBaseMessageVO fipMessageVO : srcbill) {
				if (fipMessageVO instanceof FipMessageVO)
					fipmessagelist.add((FipMessageVO) fipMessageVO);
				else {
					FipMessageVO msgvo = new FipMessageVO();
					msgvo.setBillVO(fipMessageVO.getBillVO());
					msgvo.setMessageinfo(fipMessageVO.getMessageinfo());
					fipmessagelist.add(msgvo);
				}
			}
			List<List<FipExtRelationVO>> processConfigAndOrgRule = new FipMessageBO().processConfigAndOrgRule(fipmessagelist, new FipRelationInfoVO[] { desInfo });
			if (processConfigAndOrgRule != null && !processConfigAndOrgRule.isEmpty()) {
				rs = new ArrayList<FipTranslateResultVO>();
				for (List<FipExtRelationVO> list : processConfigAndOrgRule) {
					if (list == null || list.isEmpty())
						continue;
					for (FipExtRelationVO fipExtRelationVO : list) {
						if (fipExtRelationVO == null)
							continue;
						List<FipTranslateResultVO> convertBill = new FipOperatingLogBO().convertBill(fipExtRelationVO, srcbill);
						if (convertBill != null && !convertBill.isEmpty()) {
							for (FipTranslateResultVO fipTranslateResultVO : convertBill) {
								if (fipTranslateResultVO.getErrorMsg() != null) {
									throw new BusinessException(fipTranslateResultVO.getErrorMsg());
								}
							}
							rs.addAll(convertBill);
						}
					}
				}
			}
		} else {
			FipBasicRelationVO relation = new FipBasicRelationVO();
			relation.setSrcRelation(srcbill.iterator().next().getMessageinfo());
			relation.setDesRelation(desInfo);
			rs = new FipOperatingLogBO().convertBill(relation, srcbill);
		}
		if (rs != null && !rs.isEmpty()) {
			for (FipTranslateResultVO fipTranslateResultVO : rs) {
				if (fipTranslateResultVO == null)
					continue;
				List<FipTransVO> desBills = fipTranslateResultVO.getDesBills();
				if (desBills == null || desBills.isEmpty())
					continue;
				for (FipTransVO fipTransVO2 : desBills) {
					filldata(fipTransVO2);
				}
			}
		}
		return rs;
		// String pk_group = null;
		// for (FipBaseMessageVO fipBaseMessageVO : srcbill) {
		// if (pk_group == null)
		// pk_group = fipBaseMessageVO.getMessageinfo().getPk_group();
		// }
	}

	private static void filldata(FipTransVO fipTransVO) throws BusinessException {
		FipRelationInfoVO desRelation = fipTransVO.getMessagevo().getDesRelation();
		IBillDataFiller idf = getDataFiller(desRelation.getPk_group() == null ? InvocationInfoProxy.getInstance().getGroupId() : desRelation.getPk_group(), desRelation.getPk_billtype());
		if (idf != null) {
			fipTransVO.setDatavo(idf.fill(fipTransVO.getMessagevo(), fipTransVO.getDatavo()));
		}
	}

	private static IBillDataFiller getDataFiller(String string, String pk_billtype) {
		IBillDataFiller newInstance = null;
		if ("C0".equals(pk_billtype)) {
			newInstance = (IBillDataFiller) ObjectCreator.newInstance("nc.impl.fip.pub.gl.MDVoucher2VoucherDataFiller");
		}
		return newInstance;
	}

	public List<FipTransVO> mergerDetails(List<FipTransVO> fipTransVOs) throws BusinessException {
		String pk_group = null;
		String pk_billtype = null;
		for (FipTransVO fipTransVO : fipTransVOs) {
			FipBasicRelationVO messagevo = fipTransVO.getMessagevo();
			FipRelationInfoVO desRelation = messagevo.getDesRelation();
			if (desRelation != null) {
				pk_group = desRelation.getPk_group();
				pk_billtype = desRelation.getPk_billtype();
				if (pk_group != null && pk_billtype != null)
					break;
			}
		}
		// 正式单据 直接生成正式单据的，如果目标单据有合并服务，则在保存前需要做一次合并
		IDesBillSumService desBillSumService = FipInterfaceCenter.getDesBillSumService(pk_group, pk_billtype);
		if (desBillSumService != null) {
			for (FipTransVO vo : fipTransVOs) {
				if (vo.getDatavo() == null) {
					continue;
				}
				FipBillSumRSVO svo = new FipBillSumRSVO();
				svo.setBillVO(vo.getDatavo());
				svo.setMessageinfo(vo.getMessagevo().getDesRelation());
				ArrayList<FipBillSumRSVO> volist = new ArrayList<FipBillSumRSVO>();
				volist.add(svo);
				Collection<FipBillSumRSVO> sumvolist = desBillSumService.querySumBill(volist, null);
				if (sumvolist != null && !sumvolist.isEmpty()) {
					FipBillSumRSVO ssvo = sumvolist.iterator().next();
					if (ssvo != null) {
						vo.setDatavo(ssvo.getBillVO());
						vo.getMessagevo().setDesRelation(ssvo.getMessageinfo());
					} else {
						vo.setDatavo(null);
					}
				} else {
					vo.setDatavo(null);
				}
			}
		}
		return fipTransVOs;
	}

}
