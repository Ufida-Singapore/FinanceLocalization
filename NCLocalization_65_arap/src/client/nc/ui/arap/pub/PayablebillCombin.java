package nc.ui.arap.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.framework.common.NCLocator;
import nc.itf.arap.pay.IArapPayBillQueryService;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.pubitf.arap.pay.IArapPayBillPubQueryService;
import nc.vo.arap.gathering.CombinContext;
import nc.vo.arap.gathering.GatherSysParaInitUtil;
import nc.vo.arap.pay.AggPayBillVO;
import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.arap.pay.PayBillVO;
import nc.vo.arap.pay.PayBillViewVO;
import nc.vo.arap.payable.CombinCacheVO;
import nc.vo.arap.payable.CombinResultVO;
import nc.vo.arap.pub.res.ParameterList;
import nc.vo.arap.util.ParameterVOMarger;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.MapList;

public class PayablebillCombin {

	String code = "";

	String summary = NCLangRes4VoTransl.getNCLangRes().getStrByID("summaryext",
			"summaryext-0003")/* 合并 */;

	/**
	 * 集团发票显示方式：汇总 时，对销售发票进行汇总
	 * 
	 * @param detailvos
	 *            明细vos
	 * @param cachevo
	 *            缓存vo
	 * @return 合并数据
	 * 
	 */
	public CombinResultVO combinGathering(AggPayBillVO[] detailvos,
			CombinCacheVO cachevo, String Pcode) {

		code = Pcode;

		CombinResultVO combinpara = new CombinResultVO(false);
		combinpara.setCachevo(cachevo);
		if (null != detailvos) {
			this.combinDetails(detailvos, combinpara);
		}
		return combinpara;
	}

	/**
	 * 表体行合并
	 * 
	 * AggGatheringBillVO GatheringBillVO GatheringBillItemVO
	 * 
	 * @param detailvos
	 * @param combinpara
	 */
	private void combinDetails(AggPayBillVO[] detailvos,
			CombinResultVO combinpara) {
		ParameterVOMarger mergertool = new ParameterVOMarger();
		Map<String, String> mparacombins = this.getCombinParas(detailvos);
		combinpara.setMapGroupKeys(mparacombins);
		String pk_org = null;

		MapList<String, PayBillItemVO> cominrela = combinpara
				.getCombinRela();
		if (null == cominrela) {
			cominrela = new MapList<String, PayBillItemVO>();
		}
		AggPayBillVO[] combinvo = new AggPayBillVO[detailvos.length];
		int i = 0;
		for (AggPayBillVO vo : detailvos) {
			pk_org = ((PayBillVO) vo.getParentVO()).getPk_org();
			String combinparas = mparacombins.get(pk_org);
			String[] groupkeys = this.getGroupKeys(combinparas);
			if (groupkeys.length == 0) {
				combinvo[i] = new AggPayBillVO();
				combinvo[i].setParentVO(vo.getParentVO());
				combinvo[i].setChildrenVO(vo.getChildrenVO());
				i++;
				continue;
			}
			List<PayBillItemVO> megervolist = new ArrayList<PayBillItemVO>();
			mergertool.setSummingAttr(this.getSumKeys(combinparas));
			String[] avergeattrs = this.getAvgKeys(combinparas);
			if (null != avergeattrs && avergeattrs.length > 0) {
				mergertool.setAveragingAttr(this.getAvgKeys(combinparas));
			}
			mergertool.setGroupingAttr(groupkeys);
			PayBillItemVO[] bodys = (PayBillItemVO[]) vo
					.getChildrenVO();
			if (bodys != null) {
				for (PayBillItemVO bvo : bodys) {
					megervolist.add(bvo);
				}
			}
			PayBillItemVO[] mergebvos = null;
			try {
				mergebvos = (PayBillItemVO[]) mergertool
						.mergeByGroup(megervolist
								.toArray(new PayBillItemVO[0]));
			} catch (BusinessException e) {
				ExceptionUtils.wrappException(e);
			}
			if (null == mergebvos) {
				continue;
			}

			Map<CircularlyAccessibleValueObject, CircularlyAccessibleValueObject[]> mg = mergertool.m_hashMergeRelations;
			for (PayBillItemVO mergebvo : mergebvos) {
				PayBillItemVO[] detailbvos = (PayBillItemVO[]) mg
						.get(mergebvo);

				String bid = detailbvos[0].getPrimaryKey();
				if (null == bid || "isnull".equals(bid)) {
					continue;
				}
				mergebvo.setPrimaryKey(bid);
				for (PayBillItemVO bvo : detailbvos) {
					PayBillItemVO cachebvo = (PayBillItemVO) bvo
							.clone();
					cominrela.put(bid, cachebvo);
				}

			}

			combinvo[i] = new AggPayBillVO();
			combinvo[i].setParentVO(vo.getParentVO());
			combinvo[i].setChildrenVO(mergebvos);
			i++;
		}
		combinpara.setCombinvos(combinvo);
		combinpara.setCombinrela(cominrela);
	}

	/**
	 * 平均值字段数据
	 * 
	 * @param paravalue
	 * @return
	 */
	private String[] getAvgKeys(String paravalue) {

		String[] values = paravalue.split(ParameterList.BIGSPLITKEY);
		if (values.length <= 2) {
			return null;
		}
		String keystring = values[2];
		if (keystring.length() == 0) {
			return null;
		}
		return keystring.split(ParameterList.SPLITKEY);
	}

	/**
	 * 求和字段数组
	 * 
	 * @param paravalue
	 * @return
	 */
	private String[] getSumKeys(String paravalue) {
		String[] values = paravalue.split(ParameterList.BIGSPLITKEY);
		if (values.length <= 1) {
			return CombinContext.COMBIN_PAYBILL;
		}
		String keystring = values[1];
		
		String rightvalue2 = values.length > 2 ? values[2]
				: null;

		Set<String> rightvalueSet2 = new HashSet<String>();
		if (rightvalue2 != null && rightvalue2.length() > 0) {
			for (String s : rightvalue2.split(ParameterList.SPLITKEY)) {
				rightvalueSet2.add(s);
			}
		}
		
		if (null != keystring && keystring.length() > 0) {
			List<String> sumkeylist = new ArrayList<String>();
			for (String key : CombinContext.COMBIN_PAYBILL) {
				if(rightvalueSet2!=null && rightvalueSet2.contains(key)){
					continue;
				}
				sumkeylist.add(key);
			}
//			String[] keys = keystring.split(ParameterList.SPLITKEY);
//			for (String key : keys) {
//				sumkeylist.add(key);
//			}
			return sumkeylist.toArray(new String[sumkeylist.size()]);
		}
		return CombinContext.COMBIN_PAYBILL;
	}

	/**
	 * 汇总依据字段
	 * 
	 * @param paravalue
	 * @return
	 */
	private String[] getGroupKeys(String paravalue) {
		String groupstring = paravalue.split(ParameterList.BIGSPLITKEY)[0];

		return groupstring.split(ParameterList.SPLITKEY);
	}

	/**
	 * 根据组织获得分组合并条件
	 * 
	 * @param detailvos
	 * @return
	 */
	private Map<String, String> getCombinParas(AggPayBillVO[] detailvos) {
		Set<String> setOrgs = new java.util.HashSet<String>();
		for (AggPayBillVO vo : detailvos) {
			setOrgs.add(((PayBillVO) vo.getParentVO()).getPk_org());
		}
		String[] orgs = new String[setOrgs.size()];
		orgs = setOrgs.toArray(orgs);
		Map<String, String> mParas = null;

		mParas = GatherSysParaInitUtil.queryBatchParaStringValues(orgs, code
				+ ParameterList.SUFFIX);

		return mParas;
	}

	/**
	 * 非编辑态还原明细状态数据
	 * 
	 * @param combinvos
	 * @param combinrela
	 * @return 发票vo
	 */
	public AggPayBillVO[] splitNoEditGathering(
			AggPayBillVO[] combinvos,
			MapList<String, PayBillItemVO> combinrela) {
		List<AggPayBillVO> detailvos = new ArrayList<AggPayBillVO>();
		for (AggPayBillVO combinvo : combinvos) {
			List<PayBillItemVO> listdetail = new ArrayList<PayBillItemVO>();
			if (combinvo == null) {
				continue;
			}

			for (PayBillItemVO combvo : (PayBillItemVO[]) combinvo
					.getChildrenVO()) {
				String key = combvo.getPrimaryKey();
				if (null == key || "isnull".equals(key) || combinrela == null) {
					continue;
				}

				List<PayBillItemVO> cachebvo = combinrela.get(key);
				if (null == cachebvo) {
					listdetail.add(combvo);
				} else {
					listdetail.addAll(cachebvo);
				}

			}
			AggPayBillVO detailvo = new AggPayBillVO();
			PayBillVO headvo = (PayBillVO) combinvo.getParentVO()
					.clone();
			detailvo.setParentVO(headvo);

			PayBillItemVO[] bodyvos = new PayBillItemVO[listdetail
					.size()];
			detailvo.setChildrenVO(listdetail.toArray(bodyvos));
			detailvos.add(detailvo);
		}
		return detailvos.toArray(new AggPayBillVO[detailvos.size()]);
	}

	/***
	 * 查询全部表体数据
	 * 
	 * @param alldata
	 * @throws BusinessException
	 */
	public void updateChildVO(List<AggPayBillVO> alldata)
			throws BusinessException {
		ArrayList<String> noChildPks = new ArrayList<String>();
		for (int i = 0; i < alldata.size(); i++) {
			PayBillItemVO[] bvos = (PayBillItemVO[]) alldata.get(i)
					.getChildrenVO();
			if (bvos == null || bvos.length <= 0) {
				String pk_gatherbill = alldata.get(i).getParentVO()
						.getPrimaryKey();
				noChildPks.add(pk_gatherbill);
			}
		}
		
		if (noChildPks != null && noChildPks.size() > 0) {
			IArapPayBillQueryService query = NCLocator.getInstance().lookup(IArapPayBillQueryService.class);
			AggPayBillVO[] queryBillVos = (AggPayBillVO[]) query.findBillByPrimaryKey(noChildPks.toArray(new String[0]));
			Map<String, AggPayBillVO> maps = new HashMap<String, AggPayBillVO>();
			if (queryBillVos != null && queryBillVos.length > 0) {
				for (AggPayBillVO vo : queryBillVos) {
					// Modified by Ethan Wu on 2018-05-10
					// Manually set the supplier and operator fields.
					vo.getParent().setAttributeValue("supplier", vo.getChildrenVO()[0].getAttributeValue("supplier"));
					vo.getParent().setAttributeValue("pk_psndoc", vo.getChildrenVO()[0].getAttributeValue("pk_psndoc"));
					maps.put(vo.getParentVO().getPrimaryKey(), vo);
				}

				for (int i = 0; i < alldata.size(); i++) {
					AggPayBillVO vo = alldata.get(i);
					PayBillItemVO[] bvos = (PayBillItemVO[]) vo
							.getChildrenVO();
					if (bvos == null || bvos.length <= 0) {
						String pk_gatherbill = vo.getParentVO().getPrimaryKey();
						alldata.set(i, maps.get(pk_gatherbill));
					}
				}
			}
		}
	}
}
