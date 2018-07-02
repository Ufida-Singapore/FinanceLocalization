package nc.ui.arap.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.framework.common.NCLocator;
import nc.pubitf.arap.gathering.IArapGatheringBillPubQueryService;
import nc.vo.arap.gathering.AggGatheringBillVO;
import nc.vo.arap.gathering.CombinCacheVO;
import nc.vo.arap.gathering.CombinContext;
import nc.vo.arap.gathering.CombinResultVO;
import nc.vo.arap.gathering.GatherSysParaInitUtil;
import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.arap.gathering.GatheringBillVO;
import nc.vo.arap.pub.res.ParameterList;
import nc.vo.arap.util.ParameterVOMarger;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.MapList;

/**
 * 收款单发票合并显示和合并编辑处理类 add by bl 2015-04-23
 */
public class GatheringCombin {

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
	public CombinResultVO combinGathering(AggGatheringBillVO[] detailvos,
			CombinCacheVO cachevo, String parcode) {

		code = parcode;

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
	private void combinDetails(AggGatheringBillVO[] detailvos,
			CombinResultVO combinpara) {
		ParameterVOMarger mergertool = new ParameterVOMarger();
		Map<String, String> mparacombins = this.getCombinParas(detailvos);
		combinpara.setMapGroupKeys(mparacombins);
		String pk_org = null;

		MapList<String, GatheringBillItemVO> cominrela = combinpara
				.getCombinRela();
		if (null == cominrela) {
			cominrela = new MapList<String, GatheringBillItemVO>();
		}
		AggGatheringBillVO[] combinvo = new AggGatheringBillVO[detailvos.length];
		int i = 0;
		for (AggGatheringBillVO vo : detailvos) {
			pk_org = ((GatheringBillVO) vo.getParentVO()).getPk_org();
			String combinparas = mparacombins.get(pk_org);
			String[] groupkeys = this.getGroupKeys(combinparas);
			if (groupkeys.length == 0) {
				combinvo[i] = new AggGatheringBillVO();
				combinvo[i].setParentVO(vo.getParentVO());
				combinvo[i].setChildrenVO(vo.getChildrenVO());
				i++;
				continue;
			}
			List<GatheringBillItemVO> megervolist = new ArrayList<GatheringBillItemVO>();
			mergertool.setSummingAttr(this.getSumKeys(combinparas));
			String[] avergeattrs = this.getAvgKeys(combinparas);
			if (null != avergeattrs && avergeattrs.length > 0) {
				mergertool.setAveragingAttr(this.getAvgKeys(combinparas));
			}
			mergertool.setGroupingAttr(groupkeys);
			GatheringBillItemVO[] bodys = (GatheringBillItemVO[]) vo
					.getChildrenVO();
			if(bodys != null){
				for (GatheringBillItemVO bvo : bodys) {
					megervolist.add(bvo);
				}
			}
			GatheringBillItemVO[] mergebvos = null;
			try {
				mergebvos = (GatheringBillItemVO[]) mergertool
						.mergeByGroup(megervolist
								.toArray(new GatheringBillItemVO[0]));
			} catch (BusinessException e) {
				ExceptionUtils.wrappException(e);
			}
			if (null == mergebvos) {
				continue;
			}

			Map<CircularlyAccessibleValueObject, CircularlyAccessibleValueObject[]> mg = mergertool.m_hashMergeRelations;
			for (GatheringBillItemVO mergebvo : mergebvos) {
				GatheringBillItemVO[] detailbvos = (GatheringBillItemVO[]) mg
						.get(mergebvo);

				String bid = detailbvos[0].getPrimaryKey();
				if (null == bid || "isnull".equals(bid)) {
					continue;
				}
				mergebvo.setPrimaryKey(bid);
				for (GatheringBillItemVO bvo : detailbvos) {
					GatheringBillItemVO cachebvo = (GatheringBillItemVO) bvo
							.clone();
					cominrela.put(bid, cachebvo);
				}

			}

			combinvo[i] = new AggGatheringBillVO();
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
			return CombinContext.COMBIN_GATHERING;
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
			for (String key : CombinContext.COMBIN_GATHERING) {
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
		return CombinContext.COMBIN_GATHERING;
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
	private Map<String, String> getCombinParas(AggGatheringBillVO[] detailvos) {
		Set<String> setOrgs = new java.util.HashSet<String>();
		for (AggGatheringBillVO vo : detailvos) {
			setOrgs.add(((GatheringBillVO) vo.getParentVO()).getPk_org());
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
	public AggGatheringBillVO[] splitNoEditGathering(
			AggGatheringBillVO[] combinvos,
			MapList<String, GatheringBillItemVO> combinrela) {
		List<AggGatheringBillVO> detailvos = new ArrayList<AggGatheringBillVO>();
		for (AggGatheringBillVO combinvo : combinvos) {
			List<GatheringBillItemVO> listdetail = new ArrayList<GatheringBillItemVO>();
			if(combinvo == null){
				continue;
			}
			
			for (GatheringBillItemVO combvo : (GatheringBillItemVO[]) combinvo
					.getChildrenVO()) {
				String key = combvo.getPrimaryKey();
				if (null == key || "isnull".equals(key) || combinrela == null) {
					continue;
				}

				List<GatheringBillItemVO> cachebvo = combinrela.get(key);
				if (null == cachebvo) {
					listdetail.add(combvo);
				} else {
					listdetail.addAll(cachebvo);
				}

			}
			AggGatheringBillVO detailvo = new AggGatheringBillVO();
			GatheringBillVO headvo = (GatheringBillVO) combinvo.getParentVO()
					.clone();
			detailvo.setParentVO(headvo);

			GatheringBillItemVO[] bodyvos = new GatheringBillItemVO[listdetail
					.size()];
			detailvo.setChildrenVO(listdetail.toArray(bodyvos));
			detailvos.add(detailvo);
		}
		return detailvos.toArray(new AggGatheringBillVO[detailvos.size()]);
	}
	
	/***
	 * 查询全部表体数据
	 * @param alldata
	 * @throws BusinessException
	 */
	public void updateChildVO(List<AggGatheringBillVO> alldata) throws BusinessException {
		ArrayList<String> noChildPks = new ArrayList<String>();
		for(int i=0;i<alldata.size();i++){
			GatheringBillItemVO[] bvos = (GatheringBillItemVO[]) alldata.get(i).getChildrenVO();
			if(bvos == null || bvos.length <= 0){
				String pk_gatherbill = alldata.get(i).getParentVO().getPrimaryKey();
				noChildPks.add(pk_gatherbill);
			}
		}
		if(noChildPks !=null && noChildPks.size()>0){
			IArapGatheringBillPubQueryService query = NCLocator.getInstance().lookup(IArapGatheringBillPubQueryService.class);
			AggGatheringBillVO[] queryBillVos = query.findBillByPrimaryKey(noChildPks.toArray(new String[0]));
			Map<String,AggGatheringBillVO> maps = new HashMap<String, AggGatheringBillVO>();
			if(queryBillVos !=null && queryBillVos.length>0){
				for(AggGatheringBillVO vo : queryBillVos){
					// Modified by Ethan Wu on 2018-05-17
					vo.getParent().setAttributeValue("customer", vo.getChildrenVO()[0].getAttributeValue("customer"));
					vo.getParent().setAttributeValue("pk_psndoc", vo.getChildrenVO()[0].getAttributeValue("pk_psndoc"));
					maps.put(vo.getParentVO().getPrimaryKey(), vo);
				}
			
				for(int i=0;i<alldata.size();i++){
					AggGatheringBillVO vo = alldata.get(i);
					GatheringBillItemVO[] bvos = (GatheringBillItemVO[]) vo.getChildrenVO();
					if(bvos == null || bvos.length <= 0){
						String pk_gatherbill = vo.getParentVO().getPrimaryKey();
						alldata.set(i, maps.get(pk_gatherbill));
					}
				}
			}
		}
	}
}
