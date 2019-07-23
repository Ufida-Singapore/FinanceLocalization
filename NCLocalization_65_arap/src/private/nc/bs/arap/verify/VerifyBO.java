/*
 * 创建日期 2005-9-12
 *
 */
package nc.bs.arap.verify;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import nc.bs.arap.agiotage.AgiotageBO;
import nc.bs.arap.agiotage.IBusiDataService;
import nc.bs.arap.util.ArapBillType2TableMapping;
import nc.bs.arap.util.ArapBusiLogUtils;
import nc.bs.arap.util.SqlUtils;
import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.EventDispatcher;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.impl.arap.agiotage.BusiDataServiceImp;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.arap.prv.IAgiotagePrivate;
import nc.itf.arap.tally.ITallySourceData;
import nc.itf.fi.pub.Currency;
import nc.itf.uap.pf.IPFConfig;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.md.MDBaseQueryFacade;
import nc.md.data.access.NCObject;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.arap.tally.ITallyService;
import nc.ui.arap.pub.AgiotageVOConsts;
import nc.vo.arap.agiotage.AggAgiotageVO;
import nc.vo.arap.agiotage.AgiotageChildVO;
import nc.vo.arap.agiotage.AgiotageMainVO;
import nc.vo.arap.agiotage.ArapBusiDataVO;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.tally.BaseTallySourceVO;
import nc.vo.arap.tally.BusiTypeEnum;
import nc.vo.arap.verify.AggverifyVO;
import nc.vo.arap.verify.VerifyDetailVO;
import nc.vo.arap.verify.VerifyMainVO;
import nc.vo.arap.verify.VerifyTool;
import nc.vo.arap.verifynew.MethodVO;
import nc.vo.arap.verifynew.Saver;
import nc.vo.arap.verifynew.VerifyFilter;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pfflow01.BillbusinessVO;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.pubapp.pattern.pub.MapList;
import nc.vo.verifynew.pub.ConditionVO;
import nc.vo.verifynew.pub.DefaultVerifyRuleVO;
import nc.vo.verifynew.pub.VerifyCom;
import nc.vo.verifynew.pub.VerifySqlTool;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhongyue
 */
@SuppressWarnings("unchecked")
public class VerifyBO {

	VerifyDetailVO[] detailVOS;
	List<VerifyDetailVO> updateList;

	List<String> selectPK;

	public List<String> getSelectPK() {
		return selectPK;
	}

	public void setSelectPK(List<String> selectPK) {
		this.selectPK = selectPK;
	}

	public List<VerifyDetailVO> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<VerifyDetailVO> updateList) {
		this.updateList = updateList;
	}

	boolean allUnVerify = false;

	public VerifyDetailVO[] getDetailVOS() {
		return detailVOS;
	}

	public void setDetailVOS(VerifyDetailVO[] detailVOS) {
		this.detailVOS = detailVOS;
	}

	public int systemcode;

	public int getSystemcode() {
		return systemcode;
	}

	public void setSystemcode(int systemcode) {
		this.systemcode = systemcode;
	}

	public VerifyMainVO[] autoVerify(ConditionVO condition, DefaultVerifyRuleVO[] rules) throws BusinessException {
		List<VerifyMainVO> retnMainVOS = new ArrayList<VerifyMainVO>();
		VerifyMainVO verifyMainVO = null;
		VerifyCom com = this.getCom();
		com.setM_rule(rules);

		String clr = null;
		for (int i = 0; i < rules.length; i++) {
			if (rules[i] != null) {
				clr = rules[i].getM_clr();
				break;
			}
		}

		autoVerifyPrepare(condition, rules, com, clr);
		if (com.getM_logs() == null || com.getM_logs().size() == 0) {
			return null;
		}
		// 将ts 校验使用
		Map<String, String> busiDataTsMap = getbusiDataTSMap(com.getM_creditSelected(), com.getM_debitSelected());
		com.setBusiDataTsMap(busiDataTsMap);
		com.save();

		ArrayList<AggverifyVO> m_logs = com.getM_logs();
		for (int i = 0; i < m_logs.size(); i++) {
			verifyMainVO = (VerifyMainVO) m_logs.get(i).getParentVO();
			retnMainVOS.add(verifyMainVO);
		}

		com.destroyAll();
		com = null;

		return retnMainVOS.toArray(new VerifyMainVO[] {});

	}

	public VerifyCom autoVerifySim(ConditionVO condition, DefaultVerifyRuleVO[] rules) throws BusinessException {
		VerifyCom com = this.getCom();
		com.setM_rule(rules);
		String clr = null;
		for (int i = 0; i < rules.length; i++) {
			if (rules[i] != null) {
				clr = rules[i].getM_clr();
				break;
			}
		}

		autoVerifyPrepare(condition, rules, com, clr);

		// 不需要返回
		com.setM_creditdata(null);
		com.setM_debitdata(null);

		// 不需要全部返回，只用pk，ts 即可
		ArapBusiDataVO[] creditSelected = com.getM_creditSelected();
		ArapBusiDataVO[] debitSelected = com.getM_debitSelected();
		Map<String, String> busiDataTsMap = getbusiDataTSMap(creditSelected, debitSelected);
		com.setBusiDataTsMap(busiDataTsMap);
		com.setM_creditSelected(null);
		com.setM_debitSelected(null);

		// 模拟核销避免数据量过大
		int sum = 0;
		ArrayList<AggverifyVO> mLogs = com.getM_logs();
		for (AggverifyVO vos : mLogs) {
			sum += vos.getChildrenVO().length;
		}
		if (sum > 50000) {
			com.destroyAll();
			com = null;
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006arappub0523_0", "02006arappub0523-0052")/* "核销明细数据超过100000行，无法使用模拟核销查看结果，请重新查询自动核销!" */);
		}

		return com;
	}

	private Map<String, String> getbusiDataTSMap(ArapBusiDataVO[] creditSelected, ArapBusiDataVO[] debitSelected) {
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		if (creditSelected != null && creditSelected.length > 0) {
			for (int i = 0; i < creditSelected.length; i++) {
				busiDataTsMap.put(creditSelected[i].getPk_busidata(), creditSelected[i].getTs().toString());
			}

		}
		if (debitSelected != null && debitSelected.length > 0) {
			for (int i = 0; i < debitSelected.length; i++) {
				busiDataTsMap.put(debitSelected[i].getPk_busidata(), debitSelected[i].getTs().toString());
			}
		}
		return busiDataTsMap;
	}

	private VerifyCom autoVerifyPrepare(ConditionVO condition, DefaultVerifyRuleVO[] rules, VerifyCom com, String clr) throws BusinessException {

		long start = System.currentTimeMillis();
		ArapBusiDataVO[] credit = null;
		ArapBusiDataVO[] debit = null;
		MethodVO[] methods = null;
		methods = VerifyTool.getMethods();
		Vector<String> names = new Vector<String>();

		for (int i = 0; i < methods.length; i++) {
			for (int j = 0; j < rules.length; j++) {
				if (rules[j] == null)
					continue;
				else {
					if (methods[i].getFa().equals(rules[j].getM_verifyName())) {
						names.add(rules[j].getM_verifyName());
						break;
					}
				}
			}
		}
		if (names.size() == 0)
			return null;
		String[] name = new String[names.size()];
		names.copyInto(name);
		com.setM_verifySequence(name);

		condition.setAttributeValue(VerifySqlTool.IS_AUTO, UFBoolean.TRUE);

		if (rules[0] != null) { // 有红蓝对冲
			condition.setAttributeValue(VerifySqlTool.HAS_REDBLUE, UFBoolean.TRUE);
		} else {
			condition.setAttributeValue(VerifySqlTool.HAS_REDBLUE, UFBoolean.FALSE);
		}

		com.filter(condition);
		Logger.debug("*单据查询 结束=" + (System.currentTimeMillis() - start) + "ms");
		long start2 = System.currentTimeMillis();
		credit = com.getM_creditdata();
		debit = com.getM_debitdata();
		com.setM_rule(rules);
		if (rules[0] != null) // 有红蓝对冲
		{
			if ((credit == null || credit.length == 0) && (debit == null || debit.length == 0)) {
				return null;
			}
		} else { // 没有红蓝对冲
			if ((credit == null || credit.length == 0) || (debit == null || debit.length == 0)) {
				return null;
			}
		}

		List<ArapBusiDataVO> debitList = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> creditList = new ArrayList<ArapBusiDataVO>();
		int maxSize = 20000;
		if (debit != null) {
			int i = 1;
			for (ArapBusiDataVO bvo : debit) {
				if (i++ > maxSize) {
					bvo = null;
					continue;
				}
				bvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, bvo.getOccupationmny());
				debitList.add(bvo);
			}
		}
		if (credit != null) {
			int i = 1;
			for (ArapBusiDataVO bvo : credit) {
				if (i++ > maxSize) {
					bvo = null;
					continue;
				}
				bvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, bvo.getOccupationmny());
				creditList.add(bvo);
			}
		}
		// 开始核销
		com.setM_debitSelected(debitList.toArray(new ArapBusiDataVO[] {}));
		com.setM_creditSelected(creditList.toArray(new ArapBusiDataVO[] {}));
		com.verify(debit, credit);
		Logger.debug("*单据核销 结束=" + (System.currentTimeMillis() - start2) + "ms");
		return com;
	}

	// 新增方法-核销保存
	public ArrayList<String> save(ArrayList<AggverifyVO> agglist, Hashtable<String, DefaultVerifyRuleVO> rules, VerifyCom com) throws BusinessException {
		Collection<DefaultVerifyRuleVO> ruleVOs = rules.values();
		int system = 0; // 系统编码
		if (ruleVOs.size() != 0) {
			DefaultVerifyRuleVO ruleVO = ruleVOs.iterator().next();
			if (ruleVO.getSystem() != null)
				system = ruleVO.getSystem();
			setSystemcode(system);
		}

		// 获得核销的借贷方 数据，比较ts时使用
		for (AggverifyVO volist : agglist) {
			VerifyDetailVO[] vos = (VerifyDetailVO[]) volist.getChildrenVO();
			if (vos[0].getBusidata() == null) {
				getBusidDataForVerifyVO(agglist, com.getBusiDataTsMap());
			}
		}

		// 核销前事件
		if (agglist == null || agglist.size() == 0) {
			return null;
		}

		String id = MDBaseQueryFacade.getInstance().getBeanByFullClassName(agglist.get(0).getClass().getName()).getID();
		EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_VERIFY_BEFORE, agglist));

		// 插入核销数据
		ArrayList<String> insertVerifyData = insertVerifyData(agglist);

		// 回写单据辅表余额
		IBusiDataService busidata = NCLocator.getInstance().lookup(IBusiDataService.class);

		busidata.updateVerifyData(agglist, com.getBusiDataTsMap(), com.getReserveFlag());
		// 是否计算已实现汇兑损益
		VerifyMainVO parentVO = (VerifyMainVO) agglist.get(0).getParentVO();
		boolean isYsxAgiotage = isYsxAgiotage(rules, parentVO.getPk_org());
		if (isYsxAgiotage) {
			// 核销尾差处理到已实现汇兑损益
			List<AgiotageChildVO> agdetailList = onCalAchieveAgiotageList(agglist);
			if (agdetailList != null && agdetailList.size() > 0) {
				busidata.updateAgiotageData(agdetailList);
			}
			Map<String, String> pks = this.onCalAchieveAgiotagePks(agglist);
			Collection<ArapBusiDataVO> busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_busidata", pks.keySet().toArray(new String[] {}))
					+ " and money_bal = 0 " + " and (local_money_bal <> 0 or GROUP_MONEY_BAL <> 0 or GLOBAL_MONEY_BAL <> 0) ");
			if (busiList != null && busiList.size() > 0) {
				List<AgiotageChildVO> agdetailList_ce = onCalAchieveAgiotageCE(busiList, pks);
				if (agdetailList_ce.size() > 0) {
					busidata.updateAgiotageData(agdetailList_ce);
					agdetailList.addAll(agdetailList_ce);
				}
			}

			List<AggAgiotageVO> aggAgiotageList = onCalAchieveAgiotage(agdetailList);
			List<ITallySourceData> tallylist = new ArrayList<ITallySourceData>();
			if (aggAgiotageList != null && aggAgiotageList.size() > 0) {
				NCLocator.getInstance().lookup(IAgiotagePrivate.class).save(aggAgiotageList);

				// 回写-获得汇兑损益明细VOList
				for (AggAgiotageVO aggAgiotageVO : aggAgiotageList) {
					AgiotageMainVO agioHeadVO = (AgiotageMainVO) aggAgiotageVO.getParentVO();
					AgiotageChildVO[] agdetaivos = (AgiotageChildVO[]) aggAgiotageVO.getChildrenVO();
					tallylist.add(new BaseTallySourceVO(agioHeadVO, agdetaivos));
					// for (AgiotageChildVO agiotageChildVO : agdetaivos) {
					// agdetailList.add(agiotageChildVO);
					// }
				}
				// busidata.updateAgiotageData(agdetailList);

				// 写帐
				NCLocator.getInstance().lookup(ITallyService.class).agiotageTally(BusiTypeEnum.AGIO, tallylist);

				// 汇兑损益传会计平台
				new AgiotageBO().sendMessageToFip(aggAgiotageList, FipMessageVO.MESSAGETYPE_ADD);
			}
		}

		try {
			// 写帐
			Map<Integer, List<ITallySourceData>> aggmap = new HashMap<Integer, List<ITallySourceData>>();
			for (AggverifyVO aggvo : agglist) {
				if (aggmap.containsKey(aggvo.getParentVO().getAttributeValue(VerifyMainVO.BUSIFLAG))) {
					aggmap.get(aggvo.getParentVO().getAttributeValue(VerifyMainVO.BUSIFLAG)).add(
							new BaseTallySourceVO((SuperVO) aggvo.getParentVO(), (SuperVO[]) aggvo.getChildrenVO()));
				} else {
					List<ITallySourceData> temp = new ArrayList<ITallySourceData>();
					temp.add(new BaseTallySourceVO((SuperVO) aggvo.getParentVO(), (SuperVO[]) aggvo.getChildrenVO()));
					aggmap.put((Integer) aggvo.getParentVO().getAttributeValue(VerifyMainVO.BUSIFLAG), temp);
				}
			}

			for (Integer busiflag : aggmap.keySet()) {
				BusiTypeEnum busiType = null;
				List<ITallySourceData> list = aggmap.get(busiflag);
				if (busiflag.intValue() == 0) { // 同币种
					busiType = BusiTypeEnum.INDIFF_VERIFY;
				} else if (busiflag.intValue() == -2) { // 折扣
					busiType = BusiTypeEnum.DISCOUNT;
				} else if (busiflag.intValue() == -1) { // 异币种
					busiType = BusiTypeEnum.DIFF_VERIFY;
				} else if (busiflag.intValue() == 2 || busiflag.intValue() == 14) { // 红蓝对冲
					// 退回核销
					busiType = BusiTypeEnum.RED_BLUE_VERIFY;
				}
				NCLocator.getInstance().lookup(ITallyService.class).verifyTally(busiType, list);
			}
		} catch (Exception e) {
			throw ExceptionHandler.handleException(e);
		}

		// 传会计平台
		sendMessageToFip(agglist, FipMessageVO.MESSAGETYPE_ADD);
		// 核销后事件
		EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_VERIFY_AFTER, agglist));
		//
		return insertVerifyData;
	}

	private void getBusidDataForVerifyVO(ArrayList<AggverifyVO> agglist, Map<String, String> tsMap) throws BusinessException {
		// 查询busidatavo
		List<String> pkList = new ArrayList<String>();
		Map<String, ArapBusiDataVO> datavoMap = new HashMap<String, ArapBusiDataVO>();
		for (AggverifyVO agg : agglist) {
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) agg.getChildrenVO();
			for (VerifyDetailVO vo : childrenVO) {
				pkList.add(vo.getPk_busidata());
			}
		}
		String[] busiPKArr = pkList.toArray(new String[0]);
		Collection<ArapBusiDataVO> busiDataCollection = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(ArapBusiDataVO.class, busiPKArr, false);
		// 校验单据是否被删除，或反审核
		if (busiDataCollection == null || busiDataCollection.size() == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0", "02006v61008-0351")/* @res "单据已经反审核或删除" */);
		} else {
			for (ArapBusiDataVO vo : busiDataCollection) {
				String pk_busidata = vo.getPk_busidata();
				if (tsMap == null) {
				} else {
					if (!tsMap.containsKey(pk_busidata)) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006v61008_0", "02006v61008-0352")/* @res "单据已经反审核或删除,单据号：" */
								+ vo.getBillno());

					}
				}
			}

		}
		for (ArapBusiDataVO vo : busiDataCollection) {
			datavoMap.put(vo.getPk_busidata(), vo);
		}
		for (AggverifyVO agg : agglist) {
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) agg.getChildrenVO();
			// 明细vo 赋值 busidatavo
			for (VerifyDetailVO vo : childrenVO) {
				vo.setBusidata(datavoMap.get(vo.getPk_busidata()));
			}
		}

	}

	private Map<String, UFBoolean> getBillTypeByBusiType(List<AggverifyVO> agglist) throws BusinessException {
		Map<String, UFBoolean> busiMap = new HashMap<String, UFBoolean>();
		Set<String> busiTypeSet = new HashSet<String>();
		for (AggverifyVO aggvo : agglist) {
			VerifyDetailVO[] detailVOs = (VerifyDetailVO[]) aggvo.getChildrenVO();
			for (VerifyDetailVO child : detailVOs) {
				if (child.getPk_billtype().equals(IBillFieldGet.F1)) {
					busiTypeSet.add(child.getBusidata().getPk_busitype());
				}
			}
		}

		for (String busitype : busiTypeSet) {
			BillbusinessVO[] busiVOs = NCLocator.getInstance().lookup(IPFConfig.class).findBillbusinessVOs(busitype,
					agglist.get(0).getParentVO().getAttributeValue(VerifyMainVO.PK_GROUP).toString());
			if (ArrayUtils.isEmpty(busiVOs)) {
				busiMap.put(busitype, UFBoolean.valueOf(false));
			} else {
				UFBoolean nohas = UFBoolean.valueOf(false);
				for (BillbusinessVO vo : busiVOs) {
					if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getPk_billtype())) {
						nohas = UFBoolean.valueOf(true);
						busiMap.put(busitype, nohas);
						break;
					}
				}
			}
		}
		return busiMap;
	}

	private void sendMessageToFip(List<AggverifyVO> agglist, int message) throws BusinessException {
		for (AggverifyVO aggvo : agglist) {
			VerifyMainVO mainvo = (VerifyMainVO) aggvo.getParentVO();
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggvo.getChildrenVO();
			if (ArrayUtils.isEmpty(childrenVO)) {
				continue;
			}

			// 异币种核销，增加一行明细记录，记录借贷方本币差额（奥德亮）
			AggverifyVO aggvonew = dealUnsameSpecial(aggvo);

			String pk_currtype = childrenVO[0].getPk_currtype();
			FipRelationInfoVO reVO = new FipRelationInfoVO();
			reVO.setPk_group(mainvo.getPk_group());
			reVO.setPk_org(mainvo.getPk_org());
			reVO.setRelationID(mainvo.getPk_verify());
			if (childrenVO[0].getBillclass().equals(IBillFieldGet.YS) || childrenVO[0].getBillclass().equals(IBillFieldGet.SK)) {
				reVO.setPk_system("AR");
				reVO.setBusidate(mainvo.getBusidate() == null ? new UFDate() : mainvo.getBusidate());
				reVO.setPk_billtype(ArapBillDealVOConsts.VERIFY_D9);

			} else if (childrenVO[0].getBillclass().equals(IBillFieldGet.YF) || childrenVO[0].getBillclass().equals(IBillFieldGet.FK)) {

				reVO.setBusidate(mainvo.getBusidate() == null ? new UFDate() : mainvo.getBusidate());
				reVO.setPk_billtype(ArapBillDealVOConsts.VERIFY_D9AP);
				reVO.setPk_system("AP");
			}
			reVO.setPk_operator(mainvo.getCreator());
			reVO.setFreedef1(mainvo.getBusino());
			reVO.setFreedef2(mainvo.getScomment());
			UFDouble money = Currency.getFormaUfValue(pk_currtype, mainvo.getLocal_money_de());
			reVO.setFreedef3(money.toString());
			reVO.setFreedef4("bxdef4");
			reVO.setFreedef5("bxdef5");

			FipMessageVO messageVO = new FipMessageVO();
			messageVO.setBillVO(aggvonew);
			messageVO.setMessagetype(message);
			messageVO.setMessageinfo(reVO);

			new FipCallFacade().sendMessage(messageVO);
		}

	}

	/**
	 * @param aggvo
	 * @param mainvo
	 * @param childrenVO
	 *        异币种核销，增加一行明细记录，记录借贷方本币差额（奥德亮）
	 * @return
	 */
	public static AggverifyVO dealUnsameSpecial(AggverifyVO aggvo) {
		VerifyMainVO mainvo = (VerifyMainVO) aggvo.getParentVO();
		VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggvo.getChildrenVO();
		if (mainvo.getBusiflag().equals(ArapBillDealVOConsts.UNSAMEVERIFY_FLAG)) {
			AggverifyVO aggvonew = new AggverifyVO();
			aggvonew.setParentVO(aggvo.getParentVO());
			UFDouble crMoney = new UFDouble(0);
			UFDouble deMoney = new UFDouble(0);
			UFDouble crMoneyGrp = new UFDouble(0);
			UFDouble deMoneyGrp = new UFDouble(0);
			UFDouble crMoneyGlb = new UFDouble(0);
			UFDouble deMoneyGlb = new UFDouble(0);
			for (VerifyDetailVO de : childrenVO) {
				crMoney = crMoney.add(de.getLocal_money_cr());
				deMoney = deMoney.add(de.getLocal_money_de());
				crMoneyGrp = crMoneyGrp.add(de.getGroup_money_cr());
				deMoneyGrp = deMoneyGrp.add(de.getGroup_money_de());
				crMoneyGlb = crMoneyGlb.add(de.getGlobal_money_cr());
				deMoneyGlb = deMoneyGlb.add(de.getGlobal_money_de());
			}
			VerifyDetailVO denew = (VerifyDetailVO) childrenVO[0].clone();
			denew.setRowno(-1);
			denew.setMoney_cr(UFDouble.ZERO_DBL);
			denew.setMoney_de(UFDouble.ZERO_DBL);
			denew.setQuantity_cr(UFDouble.ZERO_DBL);
			denew.setQuantity_de(UFDouble.ZERO_DBL);
			denew.setLocal_money_cr(crMoney);
			denew.setLocal_money_de(deMoney);
			denew.setGroup_money_cr(crMoneyGrp);
			denew.setGroup_money_de(deMoneyGrp);
			denew.setGlobal_money_cr(crMoneyGlb);
			denew.setGlobal_money_de(deMoneyGlb);
			List<VerifyDetailVO> detailListNew = new ArrayList<VerifyDetailVO>();
			detailListNew.addAll(Arrays.asList(childrenVO));
			detailListNew.add(denew);
			aggvonew.setChildrenVO(detailListNew.toArray(new VerifyDetailVO[] {}));

			return aggvonew;
		}
		return aggvo;
	}

	private VerifyCom getCom() {
		VerifyCom verifyCom = new VerifyCom(new VerifyFilter(), new Saver(), null);
		verifyCom.setAutoVerify(true);
		return verifyCom;
	}

	public ArrayList<String> insertVerifyData(ArrayList<AggverifyVO> aggList) throws BusinessException {
		if (aggList == null || aggList.size() == 0) {
			return null;
		}

		ArrayList<String> pkList = new ArrayList();
		for (int i = 0; i < aggList.size(); i++) {
			pkList.add(MDPersistenceService.lookupPersistenceService().saveBill(aggList.get(i)));
		}

		return pkList;
	}

	public ArrayList onFilterLog(ConditionVO condition, boolean isSum) throws BusinessException {
		String[] sqlcondtion = this.getCondtionSql(condition, isSum);
		String busiflag = (String) condition.getAttributeValue("hxfs");
		String busiflagSql = "";
		String[] split = busiflag.split(",");
		for (String st : split) {
			if (st.equals(ArapBillDealVOConsts.RBVERIFY_FLAG.toString())) {
				busiflagSql = " busiflag != 0 and ";
				break;
			} else if (st.equals(ArapBillDealVOConsts.SAMEVERIFY_FLAG.toString())) {
				busiflagSql = " busiflag != 2 and ";
				break;
			}
		}

		ArrayList displayvoList = null;
		String sumSql = "select main.* from arap_verify main where " + busiflagSql + " main.busino in "
				+ "(select ver.busino from arap_verifydetail ver ,arap_busidata busi where ver.pk_busidata = busi.pk_busidata  " + sqlcondtion[0] + ")" + sqlcondtion[1];

		String dtlSelectSql = "";
		String dtlWhereSql = busiflagSql + " ver.redflag =0  " + sqlcondtion[0];
		if (sqlcondtion[0].indexOf("busi.") != -1) {
			dtlSelectSql = " select ver.* from arap_verifydetail ver ,arap_busidata busi where ver.pk_busidata = busi.pk_busidata and ";
		} else {
			dtlSelectSql = " select ver.* from arap_verifydetail ver where ";
		}

		String dtlSql = dtlSelectSql + dtlWhereSql;

		if (isSum) // 按汇总查询
		{
			displayvoList = (ArrayList) new BaseDAO().executeQuery(sumSql, new BeanListProcessor(VerifyMainVO.class));
		} else {
			displayvoList = (ArrayList) new BaseDAO().executeQuery(dtlSql, new BeanListProcessor(VerifyDetailVO.class));
			List<String> strList =new ArrayList<String>();
			for(Object vo:displayvoList){
				VerifyDetailVO dvo = (VerifyDetailVO)vo;
				if(!strList.contains(dvo.getPk_verify())){
					strList.add(dvo.getPk_verify());
				}
			}
			if(strList.size()>0){
				String insql =" select agverify.BILLCLASS BILLCLASS, '' BILLCLASS2, BILLNO,'' BILLNO2, BUSIDATE, null BUSIFLAG, agverify.busino BUSINO, BUSIPERIOD, BUSIYEAR, agverify.CREATOR CREATOR, CUSTOMER, DR, EXPIREDATE, GLOBAL_MONEY_CR, GLOBAL_MONEY_DE, GLOBALRATE, GROUP_MONEY_CR, GROUP_MONEY_DE, GROUPRATE, INNERCTLDEFERDAYS, agverify.ISAUTO ISAUTO, null ISRESERVE, LOCAL_MONEY_CR, LOCAL_MONEY_DE, '' MATERIAL, null MONEY_CR, null MONEY_DE, ORDERCUBASDOC, PK_BILL, '' PK_BILL2, PK_BILLTYPE, '' PK_BILLTYPE2, agid.PK_BUSIDATA, agverify.PK_BUSIDATA2, '' PK_COSTSUBJ, agid.CURRTYPE PK_CURRTYPE, '' PK_DEPTID, PK_GROUP, PK_ITEM, '' PK_ITEM2, PK_ORG, '' PK_PSNDOC, PK_TERMITEM, '' PK_TERMITEM2, PK_TRADETYPE, '' PK_TRADETYPE2, agverify.PK_VERIFY, '' PK_VERIFYDETAIL,null  QUANTITY_CR, null QUANTITY_DE, RATE, REDFLAG, agverify.ROWNO, SCOMMENT, '' SRC_ORG, SUPPLIER, TS from ARAP_AGIOTAGE_D  agid ";
				insql+="inner join (select verify.ISAUTO,verify.BILLCLASS,agiotage.PK_AGIOTAGE,verify.PK_VERIFY,verify.BUSINO,verify.PK_BUSIDATA2,verify.ROWNO,verify.PK_BUSIDATA,verify.CREATOR from ARAP_VERIFYDETAIL verify left join  ARAP_AGIOTAGE agiotage ";
				insql+=" on agiotage.DEALNO = verify.BUSINO  where verify."+SqlUtils.getInStr(VerifyDetailVO.PK_VERIFY, strList.toArray(new String[]{}))+" and agiotage.DEALFLAG ='12') agverify ";
				insql+=" on agid.PK_AGIOTAGE = agverify.pk_agiotage  and agverify.PK_BUSIDATA =agid.PK_BUSIDATA    where agverify."+SqlUtils.getInStr(VerifyDetailVO.PK_VERIFY, strList.toArray(new String[]{}));
				ArrayList agiovoList = (ArrayList) new BaseDAO().executeQuery(insql, new BeanListProcessor(VerifyDetailVO.class));
				if(agiovoList!=null&&agiovoList.size()>0){
					displayvoList.addAll(agiovoList);
				}	
			}
		}

		return displayvoList;
	}

	public String[] getSeltField(boolean isSum) {
		String[] selectSql = new String[] {};
		if (isSum) {
			selectSql = new String[] {
					"pk_group",
					"pk_org",
					"scomment",
					"busiflag",
					"busidate",
					"busino",
					"billmaker",
					"money_de",
					"local_money_de",
					"money_cr",
					"local_money_cr",
					"quantity_de",
					"quantity_cr",
					"pk_verify" };
		} else {
			selectSql = new String[] {
					"pk_verifydetail",
					"pk_group",
					"pk_org",
					"scomment",
					"busiflag",
					"busidate",
					"busino",
					"billmaker",
					"busiyear",
					"busiperiod",
					"rate",
					"pk_bill",
					"pk_item",
					"pk_termitem",
					"pk_billtype",
					"pk_verify",
					"billno",
					"billno2",
					"billclass",
					"billclass2",
					"pk_termitem2",
					"pk_billtype2",
					"pk_item2",
					"pk_bill2",
					"money_de",
					"money_cr",
					"local_money_de",
					"local_money_cr",
					"quantity_de",
					"quantity_cr",
					"rowno" };
		}

		return selectSql;
	}

	// 根据CondtionVO 生成查询sql
	public String[] getCondtionSql(ConditionVO condition, boolean isSum) {
		String[] sqlCond = new String[2];
		String whereSql = "";
		// 核销条件
		if (condition.getAttributeValue(ArapBusiDataVO.PK_ORG) != null) {
			whereSql = whereSql + " and ver.pk_org='" + condition.getAttributeValue(ArapBusiDataVO.PK_ORG) + "' ";
		}
		if (condition.getAttributeValue("hxfs") != null) {
			whereSql = whereSql + " and ver.busiflag in(" + condition.getAttributeValue("hxfs") + ") ";
		}
		if (condition.getAttributeValue("hxr") != null) {
			whereSql = whereSql + " and ver.creator='" + condition.getAttributeValue("hxr") + "' ";
		}
		if (condition.getAttributeValue("startrq") != null && condition.getAttributeValue("endrq") != null) {
			whereSql = whereSql + " and ver.busidate>='" + condition.getAttributeValue("startrq") + "' and ver.busidate<='" + condition.getAttributeValue("endrq") + "' ";
		} else if (condition.getAttributeValue("startrq") != null) {
			whereSql = whereSql + " and ver.busidate>='" + condition.getAttributeValue("startrq") + "' ";
		} else if (condition.getAttributeValue("endrq") != null) {
			whereSql = whereSql + " and ver.busidate<='" + condition.getAttributeValue("endrq") + "' ";
		}
		// 核销批次号
		if (condition.getAttributeValue("starthxpch") != null && condition.getAttributeValue("endhxpch") != null) {
			whereSql = whereSql + " and ver.busino>='" + condition.getAttributeValue("starthxpch") + "' and ver.busino<='" + condition.getAttributeValue("endhxpch") + "' ";
		} else if (condition.getAttributeValue("starthxpch") != null) {
			whereSql = whereSql + " and ver.busino>='" + condition.getAttributeValue("starthxpch") + "' ";
		} else if (condition.getAttributeValue("endhxpch") != null) {
			whereSql = whereSql + " and ver.busino<='" + condition.getAttributeValue("endhxpch") + "' ";
		}
		// 单据条件*********
		// 核销币种
		if (condition.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE) != null) {
			whereSql = whereSql + " and busi.pk_currtype='" + condition.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE) + "' ";
		}
		// 部门
		if (condition.getAttributeValue(ArapBusiDataVO.PK_DEPTID) != null) {
			whereSql = whereSql + " and busi.pk_deptid='" + condition.getAttributeValue(ArapBusiDataVO.PK_DEPTID) + "' ";
		}
		// 业务员
		if (condition.getAttributeValue(ArapBusiDataVO.PK_PSNDOC) != null) {
			whereSql = whereSql + " and busi.pk_psndoc='" + condition.getAttributeValue(ArapBusiDataVO.PK_PSNDOC) + "' ";
		}
		// 项目
		if (condition.getAttributeValue("zx") != null) {
			whereSql = whereSql + " and busi.project='" + condition.getAttributeValue("zx") + "' ";
		}
		// 收支项目
		if (condition.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ) != null) {
			whereSql = whereSql + " and busi.pk_costsubj='" + condition.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ) + "' ";
		}
		// 表头科目
		if (condition.getAttributeValue(ArapBusiDataVO.HEADSUBJCODE) != null) {
			whereSql = whereSql + " and busi.headsubjcode='" + condition.getAttributeValue(ArapBusiDataVO.HEADSUBJCODE) + "' ";
		}
		// 表体科目
		if (condition.getAttributeValue(ArapBusiDataVO.SUBJCODE) != null) {
			whereSql = whereSql + " and busi.subjcode='" + condition.getAttributeValue(ArapBusiDataVO.SUBJCODE) + "' ";
		}
		// 发票号
		if (condition.getAttributeValue(ArapBusiDataVO.INVOICENO) != null) {
			whereSql = whereSql + " and busi.invoiceno='" + condition.getAttributeValue(ArapBusiDataVO.INVOICENO) + "' ";
		}
		// 利润中心
		if (condition.getAttributeValue(ArapBusiDataVO.PK_PCORG) != null) {
			whereSql = whereSql + " and busi.pk_pcorg='" + condition.getAttributeValue(ArapBusiDataVO.PK_PCORG) + "' ";
		}
		// 往来对象
		if (!StringUtils.isEmpty((String) condition.getAttributeValue(ArapBusiDataVO.OBJTYPE))) {
			whereSql = whereSql + " and busi.objtype=" + condition.getAttributeValue(ArapBusiDataVO.OBJTYPE);
		}
		// 对象名称
		if (condition.getAttributeValue("objname") != null) {
			String objtype = (String) condition.getAttributeValue(ArapBusiDataVO.OBJTYPE);
			if (objtype.equals("0")) {
				whereSql = whereSql + " and busi.customer='" + condition.getAttributeValue("objname") + "' ";

			} else if (objtype.equals("1")) {
				whereSql = whereSql + " and busi.supplier='" + condition.getAttributeValue("objname") + "' ";
			} else if (objtype.equals("2")) {
				whereSql = whereSql + " and busi.pk_deptid='" + condition.getAttributeValue("objname") + "' ";
			} else if (objtype.equals("3")) {
				whereSql = whereSql + " and busi.pk_psndoc='" + condition.getAttributeValue("objname") + "' ";
			}
		}
		if (isSum) {
			if (condition.getAttributeValue(ArapBusiDataVO.MYDEFINESQL) != null) {
				sqlCond[1] = " and " + condition.getAttributeValue(ArapBusiDataVO.MYDEFINESQL).toString();
			} else {
				sqlCond[1] = "";
			}
		} else {
			if (condition.getAttributeValue(ArapBusiDataVO.MYDEFINESQL) != null) {
				// 两个表关联，相同字段，加前缀区分
				String defsql = condition.getAttributeValue(ArapBusiDataVO.MYDEFINESQL).toString();
				defsql = defsql.replace(VerifyMainVO.MONEY_DE, "ver.money_de");
				defsql = defsql.replace(VerifyMainVO.MONEY_CR, "ver.money_cr");
				defsql = defsql.replace(VerifyMainVO.LOCAL_MONEY_DE, "ver.local_money_de");
				defsql = defsql.replace(VerifyMainVO.LOCAL_MONEY_CR, "ver.local_money_cr");
				defsql = defsql.replace(VerifyMainVO.QUANTITY_DE, "ver.quantity_de");
				defsql = defsql.replace(VerifyMainVO.QUANTITY_CR, "ver.quantity_cr");
				whereSql = whereSql + " and " + defsql;
			}
		}
		sqlCond[0] = whereSql;

		return sqlCond;
	}

	// 过滤明细反核销生成的vo，不传帐表
	private List<AggverifyVO> getAggverifyVOsNotPart(List<AggverifyVO> agglist, List<String> selectpks) {
		List<AggverifyVO> aggverifyVOs = new ArrayList<AggverifyVO>();
		for (AggverifyVO agg : agglist) {
			List<VerifyDetailVO> chiDetailVOs = new ArrayList<VerifyDetailVO>();
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) agg.getChildrenVO();
			VerifyMainVO parentVO = (VerifyMainVO) agg.getParentVO();
			for (VerifyDetailVO childVo : childrenVO) {
				Integer redflag = childVo.getRedflag();
				if (selectpks != null && selectpks.size() > 0) {
					String pkVerifydetail = childVo.getPk_verifydetail();
					if (selectpks.contains(pkVerifydetail)) {
						chiDetailVOs.add(childVo);
					}
				} else {
					if (redflag.intValue() == 0) {
						chiDetailVOs.add(childVo);
					}
				}
			}
			//
			UFDouble money_de = UFDouble.ZERO_DBL;
			UFDouble money_cr = UFDouble.ZERO_DBL;
			UFDouble local_money_de = UFDouble.ZERO_DBL;
			UFDouble local_money_cr = UFDouble.ZERO_DBL;
			UFDouble group_money_de = UFDouble.ZERO_DBL;
			;
			UFDouble group_money_cr = UFDouble.ZERO_DBL;
			;
			UFDouble global_money_de = UFDouble.ZERO_DBL;
			;
			UFDouble global_money_cr = UFDouble.ZERO_DBL;
			;
			UFDouble quantity_de = UFDouble.ZERO_DBL;
			;
			UFDouble quantity_cr = UFDouble.ZERO_DBL;
			;
			for (VerifyDetailVO vo : chiDetailVOs) {
				money_de = money_de.add(vo.getMoney_de());
				money_cr = money_cr.add(vo.getMoney_cr());
				local_money_de = local_money_de.add(vo.getLocal_money_de());
				local_money_cr = local_money_cr.add(vo.getLocal_money_cr());
				group_money_de = group_money_de.add(vo.getGroup_money_de());
				group_money_cr = group_money_cr.add(vo.getGroup_money_cr());
				global_money_de = global_money_de.add(vo.getGlobal_money_de());
				global_money_cr = global_money_cr.add(vo.getGlobal_money_cr());
				quantity_de = quantity_de.add(vo.getQuantity_de());
				quantity_cr = quantity_cr.add(vo.getQuantity_cr());
			}
			parentVO.setMoney_de(money_de);
			parentVO.setMoney_cr(money_cr);
			parentVO.setLocal_money_de(local_money_de);
			parentVO.setLocal_money_cr(local_money_cr);
			parentVO.setGroup_money_de(group_money_de);
			parentVO.setGroup_money_cr(group_money_cr);
			parentVO.setGlobal_money_de(global_money_de);
			parentVO.setGlobal_money_cr(global_money_cr);
			parentVO.setQuantity_de(quantity_de);
			parentVO.setQuantity_cr(quantity_cr);
			AggverifyVO temp = new AggverifyVO();
			temp.setParentVO(parentVO);
			temp.setChildrenVO(chiDetailVOs.toArray(new VerifyDetailVO[0]));
			aggverifyVOs.add(temp);
		}

		return aggverifyVOs;
	}

	/*
	 * 是否更新与占用余额 isUpdateOccp 是否是销售订单有预核销关系 isupdateForSo，是 不用更新 预占用余额
	 */
	public void onCancelVerify(String[] businos, Boolean isfkpc) throws BusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			String insql = SqlUtils.getInStr(VerifyMainVO.BUSINO, businos, true);

			// 反核销前事件
			List<String> selectPKS = null;
			if (getSelectPK() != null && getSelectPK().size() > 0) {
				selectPKS = getSelectPK();
			}
			List<AggverifyVO> agglist = getVerifyAggVO(insql);
			List<AggverifyVO> notpartaggList = getAggverifyVOsNotPart(agglist, selectPKS);
			if (notpartaggList == null || notpartaggList.size() == 0) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0270")/* @res "此记录已经被反核销" */);
			}
			IBean bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(agglist.get(0).getClass().getName());
			String id = bean.getID();
			EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_UNVERIFY_BEFORE, notpartaggList));

			// 更新业务表余额
			updateVerifyData(businos);

			// 核销主表加锁
			AggverifyVO[] aggArr = new AggverifyVO[notpartaggList.size()];
			agglist.toArray(aggArr);
			BusiDataServiceImp.setDealDataLock(aggArr);
			// 核销主表检查ts
			BusiDataServiceImp.checkBillsTs(aggArr);

			// 删除核销子表
			dao.deleteByClause(VerifyDetailVO.class, insql);
			// 删除核销主表
			dao.deleteByClause(VerifyMainVO.class, insql);

			// 已实现汇兑损益处理
			agiotageDealForYSX(businos);

			// 传帐表
			List<ITallySourceData> tallyList = new ArrayList<ITallySourceData>();
			for (AggverifyVO aggvo : notpartaggList) {
				tallyList.add(new BaseTallySourceVO((SuperVO) aggvo.getParentVO(), (SuperVO[]) aggvo.getChildrenVO()));
			}

			NCLocator.getInstance().lookup(ITallyService.class).verifyTally(BusiTypeEnum.UN_VERIFY, tallyList);
			// 传会计平台
			sendMessageToFip(agglist, FipMessageVO.MESSAGETYPE_DEL);
			// 反核销后事件
			EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_UNVERIFY_AFTER, notpartaggList));
			String dateId = getYSData(id, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0417")/* @res "反核销" */);
			ArapBusiLogUtils.insertSmartBusiLogs(id, dateId, businos);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	private String getYSData(String hxzbId, String displayname) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		String id = null;
		String sql = "select id from MD_BUSIOP where ownertype = '" + hxzbId + "' and  displayname = '" + displayname.trim() + "'";
		id = (String) dao.executeQuery(sql, new ResultSetProcessor() {

			private static final long serialVersionUID = 1L;

			public Object handleResultSet(ResultSet rs) throws SQLException {
				String result = null;
				while (rs.next()) {
					result = rs.getString(1);
				}
				return result;
			}
		});

		return id;

	}

	// 部分反核销
	public void onCancelVerifyDtl(Map<String, List<String>> busiNoMap) throws BusinessException {
//		try {
			List<String> pk_verifyList = new ArrayList<String>();
			List<String> allUnVerifyList = new ArrayList<String>();
			List<String> partUnVerifyList = new ArrayList<String>();
			List<VerifyDetailVO> insertVOList = new ArrayList<VerifyDetailVO>();
			List<VerifyDetailVO> updateVOList = new ArrayList<VerifyDetailVO>();
			// Map<String, List<String>> busiNoMap 批次号-核销明细表pk列表
			for (String busino : busiNoMap.keySet()) {
				pk_verifyList.addAll(busiNoMap.get(busino));
			}
			// 根据明细表主键查询部分反核销数据
			String inStr = "";
			try {
				inStr = SqlUtils.getInStr(VerifyDetailVO.PK_VERIFYDETAIL, pk_verifyList.toArray(new String[0]), true);
			} catch (SQLException e) {
				ExceptionHandler.handleException(e);
			}
			NCObject[] detailResult = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(VerifyDetailVO.class, inStr + " and redflag = 0 ", false);

			// 没有查询到数据，已经被反核销，但是前台界面没有更新，后台控制
			if (detailResult == null || detailResult.length == 0) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0270")/* @res "此记录已经被反核销" */);
			}
			List<String> verifyPkList = new ArrayList<String>();
			for (NCObject ncvo : detailResult) {
				VerifyDetailVO vo = (VerifyDetailVO) ncvo.getContainmentObject();
				vo.setRedflag(Integer.valueOf(1)); // 1 已经部分反核销，0 没有部分反核销,默认0
				vo.setStatus(VOStatus.UPDATED);
				updateVOList.add(vo);
				setUpdateList(updateVOList);
				// 核销主表pk
				verifyPkList.add(vo.getPk_verify());
				VerifyDetailVO partUnVerifyVO = (VerifyDetailVO) vo.clone();
				partUnVerifyVO.setMoney_de(partUnVerifyVO.getMoney_de().multiply(-1));
				partUnVerifyVO.setMoney_cr(partUnVerifyVO.getMoney_cr().multiply(-1));
				partUnVerifyVO.setLocal_money_de(partUnVerifyVO.getLocal_money_de().multiply(-1));
				partUnVerifyVO.setLocal_money_cr(partUnVerifyVO.getLocal_money_cr().multiply(-1));
				partUnVerifyVO.setGroup_money_de(partUnVerifyVO.getGroup_money_de().multiply(-1));
				partUnVerifyVO.setGroup_money_cr(partUnVerifyVO.getGroup_money_cr().multiply(-1));
				partUnVerifyVO.setGlobal_money_de(partUnVerifyVO.getGlobal_money_de().multiply(-1));
				partUnVerifyVO.setGlobal_money_cr(partUnVerifyVO.getGlobal_money_cr().multiply(-1));
				partUnVerifyVO.setQuantity_de(partUnVerifyVO.getQuantity_de().multiply(-1));
				partUnVerifyVO.setQuantity_cr(partUnVerifyVO.getQuantity_cr().multiply(-1));
				partUnVerifyVO.setPk_verifydetail(null);
				partUnVerifyVO.setStatus(VOStatus.NEW);
				partUnVerifyVO.setRedflag(Integer.valueOf(2)); // 2 生成的部分反核销明细记录（红字）
				partUnVerifyVO.setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0271")/* @res "明细反核销" */);
				insertVOList.add(partUnVerifyVO);
			}
			// 核销住表加锁
			String qrysql = SqlUtils.getInStr(VerifyDetailVO.PK_VERIFY, verifyPkList.toArray(new String[0]));
			NCObject[] aggVerifyObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(AggverifyVO.class, qrysql, false);
			List<AggverifyVO> aggVerifyList = new ArrayList<AggverifyVO>();
			if (!ArrayUtils.isEmpty(aggVerifyObjects)) {
				for (NCObject ob : aggVerifyObjects) {
					AggverifyVO aggVo = (AggverifyVO) ob.getContainmentObject();
					aggVerifyList.add(aggVo);
				}
			}

			// 核销主表加锁
			AggverifyVO[] aggArr = new AggverifyVO[aggVerifyList.size()];
			aggVerifyList.toArray(aggArr);
			BusiDataServiceImp.setDealDataLock(aggArr);
			// 检查ts
			BusiDataServiceImp.checkBillsTs(aggArr);

			// 插入与部分反核销数据金额相反的数据
			MDPersistenceService.lookupPersistenceService().saveBill(insertVOList.toArray(new VerifyDetailVO[0]));
			// 更新部分反核销标志
			MDPersistenceService.lookupPersistenceService().saveBill(updateVOList.toArray(new VerifyDetailVO[0]));
			// 查询与部分反核销同批次的是否都部分反核销过
			Set<String> busiSet = busiNoMap.keySet();
			for (String busino : busiSet) {
				NCObject[] existResult = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(VerifyDetailVO.class,
						" redflag =0 and busino ='" + busino + "'", new String[] { VerifyDetailVO.PK_VERIFYDETAIL }, false);
				// 部分反核销
				if (existResult != null && existResult.length > 0) {
					partUnVerifyList.add(busino);
				} else {
					// 全部被反核销
					allUnVerifyList.add(busino);
				}
			}
			if (partUnVerifyList.size() > 0) {
				Map<String, String> busiDataTsMap = new HashMap<String, String>();
				List<String> busidatapkList = new ArrayList<String>();
				// 修改核销汇总表金额 重新计算
				try {
					updateSumVerifyData(partUnVerifyList);
				} catch (DbException | SQLException e) {
					ExceptionHandler.handleException(e);
				}
				// 组织部分反核销聚合vo，是否同批次号+处理标志相同的 分组？，
				//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				List<AggverifyVO> partUnVerifyAggVOList = getPartUnVerifyAggVO(insertVOList, busiNoMap);
				List<AggverifyVO> partUnVerifyAggVOList = getPartUnVerifyAggVO(insertVOList, partUnVerifyList);
				// 核销前事件
				IBean bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(partUnVerifyAggVOList.get(0).getClass().getName());
				String id = bean.getID();
				EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_VERIFY_BEFORE, partUnVerifyAggVOList));
				// 比较ts 使用
				for (VerifyDetailVO vo : insertVOList) {
					busidatapkList.add(vo.getPk_busidata());
				}
				Collection<ArapBusiDataVO> results = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(ArapBusiDataVO.class,
						busidatapkList.toArray(new String[0]), false);
				for (ArapBusiDataVO vo : results) {
					busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
				}
				// 写帐，部分分核销vo ，放置busidatavo ，帐表使用
				List<String> busidataPKList = new ArrayList<String>();
				List<ITallySourceData> tallylist = new ArrayList<ITallySourceData>();
				for (VerifyDetailVO unpartVO : insertVOList) {
					busidataPKList.add(unpartVO.getPk_busidata());
				}
				// 根据明细表的业务数据pk查询业务表数据，放置到detailvo中
				Collection<ArapBusiDataVO> busiDataResult = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(ArapBusiDataVO.class,
						busidataPKList.toArray(new String[0]), false);
				for (VerifyDetailVO unpartVO : insertVOList) {
					String pkBusidata = unpartVO.getPk_busidata();
					for (ArapBusiDataVO busivo : busiDataResult) {
						if (pkBusidata.equals(busivo.getPk_busidata())) {
							unpartVO.setBusidata(busivo);
						}
					}
				}
				// 更新业务表数据
				NCLocator.getInstance().lookup(IBusiDataService.class).updateVerifyData(partUnVerifyAggVOList, busiDataTsMap, VerifyDetailVO.reserve_default);

				// 取消部分已实现汇兑损益处理，需要部分取消汇兑损益处理，根据批次号，pk_busidata
				try {
					partCanclAgiotage(partUnVerifyList, insertVOList);
				} catch (SQLException e) {
					ExceptionHandler.handleException(e);
				}

				VerifyDetailVO[] dtlListDetailVOs = new VerifyDetailVO[updateVOList.size()];
				updateVOList.toArray(dtlListDetailVOs);
				tallylist.add(new BaseTallySourceVO((SuperVO) partUnVerifyAggVOList.get(0).getParentVO(), dtlListDetailVOs));
				NCLocator.getInstance().lookup(ITallyService.class).verifyTally(BusiTypeEnum.UN_VERIFY, tallylist);

				// 传会计平台
				sendMessageToFip(partUnVerifyAggVOList, FipMessageVO.MESSAGETYPE_ADD);

				// 反核销后事件
				EventDispatcher.fireEvent(new BusinessEvent(id, ArapBillDealVOConsts.TYPE_VERIFY_AFTER, partUnVerifyAggVOList));
				// 部分反核销业务日志记录
				String dateId = getYSData(id, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub_0", "02006pub-0417")/* @res "反核销" */);
				ArapBusiLogUtils.insertSmartBusiLogs(id, dateId, partUnVerifyList.toArray(new String[0]));
			}
			if (allUnVerifyList.size() > 0) {
				allUnVerify = true;
				setSelectPK(pk_verifyList);
				onCancelVerify(allUnVerifyList.toArray(new String[0]), new Boolean(false));
			}
//		} catch (Exception e) {
//			ExceptionHandler.handleException(e);
//		}
	}

	// 取得部分反核销的聚合vo
	//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//	private List<AggverifyVO> getPartUnVerifyAggVO(List<VerifyDetailVO> insertVOList, Map<String, List<String>> busiNoMap) {
	private List<AggverifyVO> getPartUnVerifyAggVO(List<VerifyDetailVO> insertVOList, List<String> partUnVerifyList) {
		List<AggverifyVO> aggVOList = new ArrayList<AggverifyVO>();
		
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		for (String busino : busiNoMap.keySet()) {
		for (String busino : partUnVerifyList) {
			List<VerifyDetailVO> detailVOList = new ArrayList<VerifyDetailVO>();
			UFDouble sum_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_global_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_global_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_quantity_de = UFDouble.ZERO_DBL;
			UFDouble sum_quantity_cr = UFDouble.ZERO_DBL;
			VerifyMainVO mainvo = new VerifyMainVO();
			for (VerifyDetailVO dtlvo : insertVOList) {
				if (dtlvo.getBusino().equals(busino)) {
					detailVOList.add(dtlvo);
					sum_money_de = sum_money_de.add(dtlvo.getMoney_de());
					sum_money_cr = sum_money_cr.add(dtlvo.getMoney_cr());
					sum_local_money_de = sum_local_money_de.add(dtlvo.getLocal_money_de());
					sum_local_money_cr = sum_local_money_cr.add(dtlvo.getLocal_money_cr());
					sum_group_money_de = sum_group_money_de.add(dtlvo.getGroup_money_de());
					sum_group_money_cr = sum_group_money_cr.add(dtlvo.getGroup_money_cr());
					sum_global_money_de = sum_global_money_de.add(dtlvo.getGlobal_money_de());
					sum_global_money_cr = sum_global_money_cr.add(dtlvo.getGlobal_money_cr());
					sum_quantity_de = sum_quantity_de.add(dtlvo.getQuantity_de());
					sum_quantity_cr = sum_quantity_cr.add(dtlvo.getQuantity_cr());
					// 重复赋值了
					mainvo.setPk_group(dtlvo.getPk_group());
					mainvo.setPk_org(dtlvo.getPk_org());
					mainvo.setPk_verify(dtlvo.getPk_verify());
					mainvo.setBusidate(dtlvo.getBusidate());
					mainvo.setBusiflag(dtlvo.getBusiflag());
					mainvo.setBusino(dtlvo.getBusino());
					mainvo.setCreator(dtlvo.getCreator());
				}
			}
			mainvo.setMoney_de(sum_money_de);
			mainvo.setMoney_cr(sum_money_cr);
			mainvo.setLocal_money_de(sum_local_money_de);
			mainvo.setLocal_money_cr(sum_local_money_cr);
			mainvo.setGroup_money_de(sum_group_money_de);
			mainvo.setGroup_money_cr(sum_group_money_cr);
			mainvo.setGlobal_money_de(sum_global_money_de);
			mainvo.setGlobal_money_cr(sum_global_money_cr);
			mainvo.setQuantity_de(sum_quantity_de);
			mainvo.setQuantity_cr(sum_quantity_cr);

			AggverifyVO aggvo = new AggverifyVO();
			aggvo.setParentVO(mainvo);
			aggvo.setChildrenVO(detailVOList.toArray(new VerifyDetailVO[0]));
			aggVOList.add(aggvo);
		}

		return aggVOList;
	}

	// 重新计算核销汇总表数据
	private void updateSumVerifyData(List<String> partUnVerifyList) throws MetaDataException, DbException, SQLException {

		Map<String, List<VerifyMainVO>> mainvoMap = new HashMap<String, List<VerifyMainVO>>();
		Map<String, List<VerifyDetailVO>> detailvoMap = new HashMap<String, List<VerifyDetailVO>>();

		String insql = SqlUtils.getInStr(VerifyMainVO.BUSINO, partUnVerifyList.toArray(new String[0]), true);
		NCObject[] moneyResult = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(VerifyMainVO.class, insql, false);
		// 汇总和明细表以（处理批次号+处理标志）分组，然后分别计算汇总
		for (NCObject ncObject : moneyResult) {
			AggverifyVO aggvo = (AggverifyVO) ncObject.getContainmentObject();
			VerifyMainVO parentVO = (VerifyMainVO) aggvo.getParentVO();
			VerifyDetailVO[] childVO = (VerifyDetailVO[]) aggvo.getChildrenVO();
			if (mainvoMap.containsKey(parentVO.getBusiflag() + parentVO.getBusino())) {
				mainvoMap.get(parentVO.getBusiflag() + parentVO.getBusino()).add(parentVO);
			} else {
				List<VerifyMainVO> temp = new ArrayList<VerifyMainVO>();
				temp.add(parentVO);
				mainvoMap.put(parentVO.getBusiflag() + parentVO.getBusino(), temp);
			}
			for (VerifyDetailVO dtlvo : childVO) {
				if (detailvoMap.containsKey(dtlvo.getBusiflag() + dtlvo.getBusino())) {
					detailvoMap.get(parentVO.getBusiflag() + dtlvo.getBusino()).add(dtlvo);
				} else {
					List<VerifyDetailVO> temp = new ArrayList<VerifyDetailVO>();
					temp.add(dtlvo);
					detailvoMap.put(dtlvo.getBusiflag() + dtlvo.getBusino(), temp);
				}
			}

		}
		for (String key : mainvoMap.keySet()) {
			List<VerifyMainVO> mainList = mainvoMap.get(key);
			UFDouble sum_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_global_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_global_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_quantity_de = UFDouble.ZERO_DBL;
			UFDouble sum_quantity_cr = UFDouble.ZERO_DBL;
			List<VerifyDetailVO> detailList = detailvoMap.get(key);
			for (VerifyDetailVO vo : detailList) {
				sum_money_de = sum_money_de.add(vo.getMoney_de());
				sum_money_cr = sum_money_cr.add(vo.getMoney_cr());
				sum_local_money_de = sum_local_money_de.add(vo.getLocal_money_de());
				sum_local_money_cr = sum_local_money_cr.add(vo.getLocal_money_cr());
				sum_group_money_de = sum_group_money_de.add(vo.getGroup_money_de());
				sum_group_money_cr = sum_group_money_cr.add(vo.getGroup_money_cr());
				sum_global_money_de = sum_global_money_de.add(vo.getGlobal_money_de());
				sum_global_money_cr = sum_global_money_cr.add(vo.getGlobal_money_cr());
				sum_quantity_de = sum_quantity_de.add(vo.getQuantity_de());
				sum_quantity_cr = sum_quantity_cr.add(vo.getQuantity_cr());
			}
			for (VerifyMainVO vo : mainList) {
				vo.setMoney_de(sum_money_de);
				vo.setMoney_cr(sum_money_cr);
				vo.setLocal_money_de(sum_local_money_de);
				vo.setLocal_money_cr(sum_local_money_cr);
				vo.setGroup_money_de(sum_group_money_de);
				vo.setGroup_money_cr(sum_group_money_cr);
				vo.setGlobal_money_de(sum_global_money_de);
				vo.setGlobal_money_cr(sum_global_money_cr);
				vo.setQuantity_de(sum_quantity_de);
				vo.setQuantity_cr(sum_quantity_cr);
				vo.setStatus(VOStatus.UPDATED);
			}
			if (mainList != null && mainList.size() > 0) {
				// 更新核销汇总表 金额字段
				MDPersistenceService.lookupPersistenceService().saveBill(mainList.toArray(new VerifyMainVO[0]));
			}
		}
	}

	// 取核销主表pk
	public String[] getVerifyMainPk(VerifyMainVO[] mainvo) {
		List<String> pkList = new ArrayList<String>();

		for (VerifyMainVO vo : mainvo) {
			String pk = vo.getPk_verify();
			pkList.add(pk);
		}

		String[] pk = new String[pkList.size()];
		pkList.toArray(pk);

		return pk;
	}

	// 获得 删除sql 条件
	public String getInSqlStr(String[] pks) {
		String wheresql = "busino in (";
		StringBuffer pkstr = new StringBuffer();
		for (String pk : pks) {
			pkstr.append("'");
			pkstr.append(pk);
			pkstr.append("',");
		}
		int index = pkstr.lastIndexOf(",");
		String insql = pkstr.substring(0, index).toString();
		wheresql = wheresql + insql + ")";
		return wheresql;
	}

	public void updateVerifyData(String[] businos) throws BusinessException, SQLException {
		List<AggverifyVO> aggList = new ArrayList<AggverifyVO>();
		Map<String, List<VerifyDetailVO>> businoMap = new HashMap<String, List<VerifyDetailVO>>();
		List<String> pkList = new ArrayList<String>();
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		Map<String, ArapBusiDataVO> busiDataMap = new HashMap<String, ArapBusiDataVO>();
		
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		String insql = getInSqlStr(businos);
		String insql = SqlUtils.getInStr(VerifyMainVO.BUSINO, businos, true);
		// 如果在明细界面将记录全部反核销，则会生成一批红字明细核销记录，只查询原核销明细记录 （redflag =1）
		if (allUnVerify) {
			List<String> pk_List = new ArrayList<String>();
			List<VerifyDetailVO> updateVOList = getUpdateList();
			if (updateVOList != null) {
				for (VerifyDetailVO vo : updateVOList) {
					pk_List.add(vo.getPk_verifydetail());
				}
			}
			String detailPKSql = SqlUtils.getInStr(VerifyDetailVO.PK_VERIFYDETAIL, pk_List.toArray(new String[0]), true);
			insql = insql + " and redflag = 1" + " and " + detailPKSql;
			setUpdateList(null);
		} else {
			insql = insql + " and redflag = 0";
		}

		Collection<VerifyDetailVO> result = new BaseDAO().retrieveByClause(VerifyDetailVO.class, insql);

		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			VerifyDetailVO detailvo = (VerifyDetailVO) iterator.next();
			// 赋值
			pkList.add(detailvo.getPk_busidata());
			detailvo.setMoney_de(detailvo.getMoney_de().multiply(-1));
			detailvo.setMoney_cr(detailvo.getMoney_cr().multiply(-1));
			detailvo.setLocal_money_de(detailvo.getLocal_money_de().multiply(-1));
			detailvo.setLocal_money_cr(detailvo.getLocal_money_cr().multiply(-1));
			detailvo.setQuantity_de(detailvo.getQuantity_de().multiply(-1));
			detailvo.setQuantity_cr(detailvo.getQuantity_cr().multiply(-1));
			detailvo.setGlobal_money_de(detailvo.getGlobal_money_de().multiply(-1));
			detailvo.setGlobal_money_cr(detailvo.getGlobal_money_cr().multiply(-1));
			detailvo.setGroup_money_de(detailvo.getGroup_money_de().multiply(-1));
			detailvo.setGroup_money_cr(detailvo.getGroup_money_cr().multiply(-1));
			if (businoMap.containsKey(detailvo.getBusino())) {
				List<VerifyDetailVO> list2 = businoMap.get(detailvo.getBusino());
				list2.add(detailvo);
				businoMap.put(detailvo.getBusino(), list2);
			} else {
				List<VerifyDetailVO> detaillist = new ArrayList<VerifyDetailVO>();
				detaillist.add(detailvo);
				businoMap.put(detailvo.getBusino(), detaillist);
			}
		}

		// 组织核销聚合VO
		Set<String> keyset = businoMap.keySet();
		for (String businostr : keyset) {
			List<VerifyDetailVO> detail = businoMap.get(businostr);
			AggverifyVO aggvo = new AggverifyVO();
			VerifyDetailVO[] temp = new VerifyDetailVO[detail.size()];
			detail.toArray(temp);
			aggvo.setChildrenVO(temp);
			aggList.add(aggvo);
		}
		// 比较ts 使用
		Collection<ArapBusiDataVO> results = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(ArapBusiDataVO.class, pkList.toArray(new String[0]), false);
		for (ArapBusiDataVO vo : results) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
			busiDataMap.put(vo.getPk_busidata(), vo);
		}

		// 业务表数据，并账后核销使用
		for (AggverifyVO aggvo : aggList) {
			VerifyDetailVO[] childDetailVOs = (VerifyDetailVO[]) aggvo.getChildrenVO();
			for (VerifyDetailVO vo : childDetailVOs) {
				vo.setBusidata(busiDataMap.get(vo.getPk_busidata()));
			}
		}

		MapList<Integer, AggverifyVO> mapList = new MapList<Integer, AggverifyVO>();
		for (AggverifyVO aggvo : aggList) {
			VerifyDetailVO[] childDetailVOs = (VerifyDetailVO[]) aggvo.getChildrenVO();
			mapList.put(childDetailVOs[0].getIsreserve(), aggvo);
		}

		// 回写单据辅表余额
		IBusiDataService busidata = NCLocator.getInstance().lookup(IBusiDataService.class);

		int i = 0;
		for (Integer reserve : mapList.keySet()) {

			if (i++ > 0) { // 第二次和以后的调用需要重新查询,比较少的场景需要使用这个分支
				// 比较ts 使用
				results = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(ArapBusiDataVO.class, pkList.toArray(new String[0]), false);
				for (ArapBusiDataVO vo : results) {
					busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
					busiDataMap.put(vo.getPk_busidata(), vo);
				}
			}

			busidata.updateVerifyData(mapList.get(reserve), busiDataTsMap, reserve);
		}
	}

	public List<AggverifyVO> getVerifyAggVO(String insql) throws DAOException, BusinessException {
		// 获得聚合VO
		List<AggverifyVO> agglist = new ArrayList<AggverifyVO>();

		NCObject[] objects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(AggverifyVO.class, insql, false);
		if (!ArrayUtils.isEmpty(objects)) {
			for (NCObject ob : objects) {
				AggverifyVO aggVo = (AggverifyVO) ob.getContainmentObject();
				VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggVo.getChildrenVO();
				Map<String, ArapBusiDataVO> busiDataMap = getBusiDataVOs(childrenVO);
				for (VerifyDetailVO dtlVO : childrenVO) {
					String pkBusidata = dtlVO.getPk_busidata();
					dtlVO.setBusidata(busiDataMap.get(pkBusidata));
				}
				agglist.add(aggVo);
			}
		}

		return agglist;
	}

	private Map<String, ArapBusiDataVO> getBusiDataVOs(VerifyDetailVO[] detailvos) throws DAOException {

		Map<String, ArapBusiDataVO> busiDataMap = new HashMap<String, ArapBusiDataVO>();
		Set<String> pkSet = new HashSet<String>();
		for (VerifyDetailVO vo : detailvos) {
			pkSet.add(vo.getPk_busidata());
		}
		String[] pks = pkSet.toArray(new String[0]);
		String wheresql = "";
		try {
			wheresql = SqlUtils.getInStr("pk_busidata", pks, true);
		} catch (SQLException e) {
			Logger.error(e.getMessage());
		}

		Collection<ArapBusiDataVO> busidataResult = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, wheresql);

		for (ArapBusiDataVO dataVO : busidataResult) {
			busiDataMap.put(dataVO.getPk_busidata(), dataVO);
		}

		return busiDataMap;
	}

	public List<AgiotageChildVO> onCalAchieveAgiotageList(ArrayList<AggverifyVO> agglist) throws BusinessException {
		List<AgiotageChildVO> childList = new ArrayList<AgiotageChildVO>();

		for (AggverifyVO aggvo : agglist) {
			VerifyDetailVO[] detailvos = (VerifyDetailVO[]) aggvo.getChildrenVO();
			for (VerifyDetailVO detailvo : detailvos) {
				if (detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.SAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.UNSAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.RBVERIFY_FLAG.intValue()
						//add by weiningc 20171012 633适配至65 start
						//add chenth 20161220 手续费折扣核销，也要汇兑损益
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.DISCTIONVERIFY_FLAG.intValue()
						//add end
						//add by weiningc 20171012 633适配至65 end
						) {
					
					if (detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YS) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YF)
							|| detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_SK) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_FK)) {
						AgiotageChildVO childvo = createAgiotageChildVO(detailvo);
						if (childvo != null) {
							childList.add(childvo);
						}
					}
				}

			}
		}
		return childList;
	}

	public List<AgiotageChildVO> onCalAchieveAgiotageCE(Collection<ArapBusiDataVO> busiList, Map<String, String> pks) throws BusinessException {
		List<AgiotageChildVO> childList = new ArrayList<AgiotageChildVO>();
		for (ArapBusiDataVO bdtVO : busiList) {
			AgiotageChildVO agdetailVO = new AgiotageChildVO();
			UFDate busiDate = new UFDate(InvocationInfoProxy.getInstance().getBizDateTime());
			int fx = bdtVO.getDirection();
			if (fx == 1) {
				// 贷方处理本币金额
				agdetailVO.setLocal_money_cr(UFDouble.ZERO_DBL);
				agdetailVO.setGlobal_money_cr(UFDouble.ZERO_DBL);
				agdetailVO.setGroup_money_cr(UFDouble.ZERO_DBL);
				// 借方处理本币金额
				agdetailVO.setLocal_money_de(bdtVO.getLocal_money_bal());
				agdetailVO.setGlobal_money_de(bdtVO.getGlobal_money_bal());
				agdetailVO.setGroup_money_de(bdtVO.getGroup_money_bal());
			} else {
				agdetailVO.setLocal_money_cr(bdtVO.getLocal_money_bal());
				agdetailVO.setGlobal_money_cr(bdtVO.getGlobal_money_bal());
				agdetailVO.setGroup_money_cr(bdtVO.getGroup_money_bal());

				agdetailVO.setLocal_money_de(UFDouble.ZERO_DBL);
				agdetailVO.setGlobal_money_de(UFDouble.ZERO_DBL);
				agdetailVO.setGroup_money_de(UFDouble.ZERO_DBL);
			}
			agdetailVO.setMoney(bdtVO.getMoney());
			// 调整后原币余额
			agdetailVO.setMoney_bal(bdtVO.getMoney_bal());
			// 调整后本币余额
			agdetailVO.setLocal_money_bal(UFDouble.ZERO_DBL);
			// 调整后集团本币余额
			agdetailVO.setGroup_money_bal(UFDouble.ZERO_DBL);
			// 调整后全局本币余额
			agdetailVO.setGlobal_money_bal(UFDouble.ZERO_DBL);
			// 单据编号
			agdetailVO.setBillno(bdtVO.getBillno());
			// 处理日期
			agdetailVO.setBusidate(busiDate);
			// 处理期间
			AccountCalendar instance = AccountCalendar.getInstanceByPk_org(bdtVO.getPk_org());
			instance.setDate(busiDate);
			agdetailVO.setBusiperiod(instance.getMonthVO().getAccperiodmth());
			agdetailVO.setBusiyear(instance.getYearVO().getPeriodyear());
			// 暂时赋默认值
			agdetailVO.setScomment(ArapBillDealVOConsts.YSX_AGIOTAGE_COMMNET);
			agdetailVO.setCurrtype(bdtVO.getPk_currtype());
			agdetailVO.setPk_group(bdtVO.getPk_group());
			agdetailVO.setPk_billtype(bdtVO.getPk_billtype());
			agdetailVO.setPk_tradetype(bdtVO.getPk_tradetypeid());
			agdetailVO.setPk_org(bdtVO.getPk_org());
			agdetailVO.setPk_termitem(bdtVO.getPk_termitem());
			agdetailVO.setPk_item(bdtVO.getPk_item());
			agdetailVO.setPk_Busidata(bdtVO.getPk_busidata());
			agdetailVO.setCustomer(bdtVO.getCustomer());
			agdetailVO.setSupplier(bdtVO.getSupplier());
			agdetailVO.setPk_bill(bdtVO.getPk_bill());
			agdetailVO.setGrouprate(bdtVO.getGrouprate());
			agdetailVO.setGlobalrate(bdtVO.getGlobalrate());
			agdetailVO.setRate(bdtVO.getRate());
			agdetailVO.setRedflag(Integer.valueOf(0));
			agdetailVO.setExpiredate(bdtVO.getExpiredate());
			agdetailVO.setInnerctldeferdays(bdtVO.getInnerctldeferdays());
			agdetailVO.setOrdercubasdoc(bdtVO.getOrdercubasdoc());
			agdetailVO.setStatus(VOStatus.NEW); // 保存
			agdetailVO.setBillmaker(bdtVO.getApprover());
			agdetailVO.setDealno(pks.get(bdtVO.getPk_busidata()));
			childList.add(agdetailVO);
		}
		return childList;
	}

	public Map<String, String> onCalAchieveAgiotagePks(ArrayList<AggverifyVO> agglist) throws BusinessException {
		Map<String, String> pks = new HashMap<String, String>();

		for (AggverifyVO aggvo : agglist) {
			VerifyDetailVO[] detailvos = (VerifyDetailVO[]) aggvo.getChildrenVO();
			for (VerifyDetailVO detailvo : detailvos) {
				if (detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.SAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.UNSAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.RBVERIFY_FLAG.intValue()) {
					if (detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YS) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YF)
							|| detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_SK) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_FK)) {
						pks.put(detailvo.getBusidata().getPk_busidata(), detailvo.getBusino());
					}
				}

			}
		}
		return pks;
	}

	public List<AggAgiotageVO> onCalAchieveAgiotage(List<AgiotageChildVO> agdetailList) throws BusinessException {
		Map<String, List<AgiotageChildVO>> childMap = new HashMap<String, List<AgiotageChildVO>>();
		if (agdetailList != null && agdetailList.size() > 0) {
			for (AgiotageChildVO childvo : agdetailList) {
				if (childvo != null) {
					if (childMap.containsKey(childvo.getCurrtype())) {
						childMap.get(childvo.getCurrtype()).add(childvo);
					} else {
						List<AgiotageChildVO> childvoList = new ArrayList<AgiotageChildVO>();
						childvoList.add(childvo);
						childMap.put(childvo.getCurrtype(), childvoList);
					}
				}
			}
		}
		return createAgiotageVO(childMap);
	}

	public List<AggAgiotageVO> onCalAchieveAgiotage(ArrayList<AggverifyVO> agglist) throws BusinessException {
		Map<String, List<AgiotageChildVO>> childMap = new HashMap<String, List<AgiotageChildVO>>();

		for (AggverifyVO aggvo : agglist) {
			VerifyDetailVO[] detailvos = (VerifyDetailVO[]) aggvo.getChildrenVO();
			for (VerifyDetailVO detailvo : detailvos) {
				if (detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.SAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.UNSAMEVERIFY_FLAG.intValue()
						|| detailvo.getBusiflag().intValue() == ArapBillDealVOConsts.RBVERIFY_FLAG.intValue()) {
					if (detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YS) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_YF)
							|| detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_SK) || detailvo.getBillclass().equals(ArapBillDealVOConsts.BILL_CLASS_FK)) {
						AgiotageChildVO childvo = createAgiotageChildVO(detailvo);
						if (childvo != null) {
							if (childMap.containsKey(childvo.getCurrtype())) {
								childMap.get(childvo.getCurrtype()).add(childvo);
							} else {
								List<AgiotageChildVO> childvoList = new ArrayList<AgiotageChildVO>();
								childvoList.add(childvo);
								childMap.put(childvo.getCurrtype(), childvoList);
							}
						}
					}
				}

			}
		}

		return createAgiotageVO(childMap);
	}

	private AgiotageChildVO createAgiotageChildVO(VerifyDetailVO detailvo) throws BusinessException {
		AgiotageChildVO childvo = new AgiotageChildVO();
		ArapBusiDataVO jfverifyvo = detailvo.getBusidata();
		// 本币汇率
		UFDouble bbrate = jfverifyvo.getRate();

		// 核销原币
		UFDouble clybje = detailvo.getMoney_cr().add(detailvo.getMoney_de());
		// 核销本币
		UFDouble bbje = detailvo.getLocal_money_cr().add(detailvo.getLocal_money_de());
		// 核销集团本币
		UFDouble group_bb = detailvo.getGroup_money_cr().add(detailvo.getGroup_money_de());
		// 核销全局本币
		UFDouble global_bb = detailvo.getGlobal_money_cr().add(detailvo.getGlobal_money_de());

		UFDouble bb_ce = UFDouble.ZERO_DBL;
		UFDouble group_ce = UFDouble.ZERO_DBL;
		UFDouble global_ce = UFDouble.ZERO_DBL;
		UFDouble[] group_global = new UFDouble[] { UFDouble.ZERO_DBL, UFDouble.ZERO_DBL, UFDouble.ZERO_DBL, UFDouble.ZERO_DBL };

		try {
			// 取单据的3个汇率，用于计算全局和组织的金额
			UFDouble[] rates = new UFDouble[] { jfverifyvo.getRate(), jfverifyvo.getGrouprate(), jfverifyvo.getGlobalrate() };
			// 计算单据的本币，用于计算全局和组织的金额
			UFDouble[] yfbs = Currency.computeYFB(detailvo.getPk_org(), Currency.Change_YBJE, jfverifyvo.getPk_currtype(), clybje, null, null, UFDouble.ZERO_DBL, bbrate, detailvo
					.getBusidate());
			// 计算单据的全局本币和集团本币金额
			group_global = Currency.computeGroupGlobalAmount(clybje, yfbs[2], detailvo.getPk_currtype(), detailvo.getBusidate(), detailvo.getPk_org(), detailvo.getPk_group(),
					rates[2], rates[1]);

			// 本币差额
			bb_ce = yfbs[2].sub(bbje);
			// 集团本币差额
			group_ce = group_global[0].sub(group_bb);
			// 全局本币差额
			global_ce = group_global[1].sub(global_bb);
			// 如果差额都是零，则没有损益
			if (bb_ce.abs().compareTo(UFDouble.ZERO_DBL) == 0 && group_ce.abs().compareTo(UFDouble.ZERO_DBL) == 0 && global_ce.abs().compareTo(UFDouble.ZERO_DBL) == 0)
				return null;

		} catch (BusinessException e) {
			throw ExceptionHandler.handleException(e);
		}

		if (jfverifyvo.getDirection().intValue() == ArapBillDealVOConsts.DIRECTION_JF.intValue()) {
			// 贷方处理本币金额
			childvo.setLocal_money_cr(UFDouble.ZERO_DBL);
			childvo.setGlobal_money_cr(UFDouble.ZERO_DBL);
			childvo.setGroup_money_cr(UFDouble.ZERO_DBL);
			// 借方处理本币金额
			childvo.setLocal_money_de(bb_ce);
			childvo.setGlobal_money_de(global_ce);
			childvo.setGroup_money_de(group_ce);
		} else {
			childvo.setLocal_money_cr(bb_ce);
			childvo.setGlobal_money_cr(global_ce);
			childvo.setGroup_money_cr(group_ce);
			childvo.setLocal_money_de(UFDouble.ZERO_DBL);
			childvo.setGlobal_money_de(UFDouble.ZERO_DBL);
			childvo.setGroup_money_de(UFDouble.ZERO_DBL);
		}
		// 调整后原币余额
		childvo.setMoney_bal(clybje);
		// 调整后本币余额
		childvo.setLocal_money_bal(bbje);
		// 调整后集团本币余额
		childvo.setGroup_money_bal(group_bb);
		// 调整后全局本币余额
		childvo.setGlobal_money_bal(global_bb);
		// 单据编号
		childvo.setBillno(detailvo.getBillno());
		// 处理日期
		childvo.setBusidate(detailvo.getBusidate());
		// 处理期间
		childvo.setBusiperiod(detailvo.getBusiperiod());
		childvo.setBusiyear(detailvo.getBusiyear());
		childvo.setScomment(ArapBillDealVOConsts.YSX_AGIOTAGE_COMMNET);
		childvo.setCurrtype(jfverifyvo.getPk_currtype());
		childvo.setPk_group(detailvo.getPk_group());
		childvo.setPk_billtype(detailvo.getPk_billtype());
		childvo.setPk_tradetype(detailvo.getPk_tradetype());
		childvo.setPk_org(detailvo.getPk_org());
		childvo.setOrdercubasdoc(detailvo.getOrdercubasdoc());
		childvo.setPk_termitem(detailvo.getPk_termitem());
		childvo.setPk_item(detailvo.getPk_item());
		childvo.setCustomer(detailvo.getCustomer());
		childvo.setSupplier(detailvo.getSupplier());
		childvo.setPk_bill(detailvo.getPk_bill());
		childvo.setExpiredate(detailvo.getExpiredate());// 信用到期日
		childvo.setGrouprate(group_global[2]);
		childvo.setGlobalrate(group_global[3]);
		childvo.setRate(bbrate);
		childvo.setStatus(VOStatus.NEW);// 新增
		childvo.setDealno(detailvo.getBusino());
		childvo.setBillmaker(detailvo.getCreator());
		childvo.setPk_Busidata(detailvo.getPk_busidata());
		childvo.setRedflag(Integer.valueOf(0));
		return childvo;
	}

	private List<AggAgiotageVO> createAgiotageVO(Map<String, List<AgiotageChildVO>> childMap) {
		List<AggAgiotageVO> aggvoList = new ArrayList<AggAgiotageVO>();
		Set<String> keyset = childMap.keySet();

		for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
			UFDouble sum_global_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_global_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_group_money_cr = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_de = UFDouble.ZERO_DBL;
			UFDouble sum_local_money_cr = UFDouble.ZERO_DBL;
			String currtype = iterator.next();
			List<AgiotageChildVO> childList = childMap.get(currtype);
			AgiotageMainVO mainvo = new AgiotageMainVO();
			for (int i = 0; i < childList.size(); i++) {
				AgiotageChildVO vo = childList.get(i);
				if (i == 0) {
					// 取不到值
					mainvo.setBillmaker(vo.getBillmaker());
					if (getSystemcode() == ArapBillDealVOConsts.SYSCODE_AR.intValue()) {
						mainvo.setSystemcode(ArapBillDealVOConsts.SYSCODE_AR);
					} else {
						mainvo.setSystemcode(ArapBillDealVOConsts.SYSCODE_AP);
					}
					mainvo.setBusidate(vo.getBusidate());
					mainvo.setCurrtype(vo.getCurrtype());
					mainvo.setDealflag(ArapBillDealVOConsts.AGIOTAGE_YSX_FLAG);
					mainvo.setDealno(vo.getDealno());
					mainvo.setPk_group(vo.getPk_group());
					mainvo.setPk_org(vo.getPk_org());
					mainvo.setStatus(VOStatus.NEW);
				}
				// 需要汇总的字段
				sum_global_money_de = sum_global_money_de.add(vo.getGlobal_money_de());
				sum_global_money_cr = sum_global_money_cr.add(vo.getGlobal_money_cr());
				sum_group_money_de = sum_group_money_de.add(vo.getGroup_money_de());
				sum_group_money_cr = sum_group_money_cr.add(vo.getGroup_money_cr());
				sum_local_money_de = sum_local_money_de.add(vo.getLocal_money_de());
				sum_local_money_cr = sum_local_money_cr.add(vo.getLocal_money_cr());

				mainvo.setGlobal_money_cr(sum_global_money_cr);
				mainvo.setGlobal_money_de(sum_global_money_de);
				mainvo.setGroup_money_cr(sum_group_money_cr);
				mainvo.setGroup_money_de(sum_group_money_de);
				mainvo.setLocal_money_cr(sum_local_money_cr);
				mainvo.setLocal_money_de(sum_local_money_de);
			}
			AggAgiotageVO aggvo = new AggAgiotageVO();
			aggvo.setParentVO(mainvo);
			aggvo.setChildrenVO(childList.toArray(new AgiotageChildVO[] {}));
			aggvoList.add(aggvo);
		}

		return aggvoList;
	}

	private boolean isYsxAgiotage(Hashtable<String, DefaultVerifyRuleVO> m_rule, String pk_org) {
		Collection<DefaultVerifyRuleVO> ruleVOs = m_rule.values();

		int system = 0; // 系统编码
		String pk_corp = pk_org;

		if (ruleVOs.size() != 0) {
			DefaultVerifyRuleVO ruleVO = ruleVOs.iterator().next();
			if (ruleVO != null) {
				if (ruleVO.getSystem() != null)
					system = ruleVO.getSystem();
				setSystemcode(system);
				if (ruleVO.getPk_org() != null)
					pk_corp = ruleVO.getPk_org();
			}
		}

		int smode = AgiotageVOConsts.getMode(pk_corp, system);

		return smode == AgiotageVOConsts.AGIOTAGE_SX;
	}

	private void partCanclAgiotage(List<String> partBusinoList, List<VerifyDetailVO> insertVOList) throws SQLException, BusinessException {
		List<AgiotageChildVO> insertChildVOList = new ArrayList<AgiotageChildVO>();
		List<AgiotageChildVO> updateChildVOList = new ArrayList<AgiotageChildVO>();
		List<AggAgiotageVO> aggVOList = new ArrayList<AggAgiotageVO>();
		List<String> busipkList = new ArrayList<String>();
		Map<String, List<AgiotageChildVO>> childvoMap = new HashMap<String, List<AgiotageChildVO>>();
		Map<String, List<AgiotageMainVO>> mainvoMap = new HashMap<String, List<AgiotageMainVO>>();
		for (String busino : partBusinoList) {
			for (VerifyDetailVO vo : insertVOList) {
				if (vo.getBusino().equals(busino)) {
					busipkList.add(vo.getPk_busidata());
				}
			}
		}

		// 获得查询汇兑损益明细表sql
		String dealnosql = SqlUtils.getInStr(AgiotageMainVO.DEALNO, partBusinoList.toArray(new String[0]));
		String busipksql = SqlUtils.getInStr(AgiotageChildVO.PK_BUSIDATA, busipkList.toArray(new String[0]), true);
		String sqlwhere = " pk_agiotage in(select pk_agiotage from arap_agiotage where " + dealnosql + ") and " + busipksql;

		// 需要取消汇兑损益的明细记录
		NCObject[] result = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(AgiotageChildVO.class, sqlwhere, false);
		if (ArrayUtils.isEmpty(result)) {
			return; // 没有记录则返回
		}

		// 插入部分取消汇兑损益明细记录（金额方向相反）
		for (NCObject ncvo : result) {
			AgiotageChildVO detailvo = (AgiotageChildVO) ncvo.getContainmentObject();
			detailvo.setRedflag(Integer.valueOf(1));// 已经部分取消汇兑损益
			detailvo.setStatus(VOStatus.UPDATED);
			updateChildVOList.add(detailvo);
			AgiotageChildVO insertAgiotageChildVO = (AgiotageChildVO) detailvo.clone();
			insertAgiotageChildVO.setLocal_money_bal(insertAgiotageChildVO.getLocal_money_bal().multiply(-1));
			insertAgiotageChildVO.setLocal_money_de(insertAgiotageChildVO.getLocal_money_de().multiply(-1));
			insertAgiotageChildVO.setLocal_money_cr(insertAgiotageChildVO.getLocal_money_cr().multiply(-1));
			insertAgiotageChildVO.setGroup_money_bal(insertAgiotageChildVO.getGroup_money_bal().multiply(-1));
			insertAgiotageChildVO.setGroup_money_cr(insertAgiotageChildVO.getGroup_money_cr().multiply(-1));
			insertAgiotageChildVO.setGroup_money_de(insertAgiotageChildVO.getGroup_money_de().multiply(-1));
			insertAgiotageChildVO.setGlobal_money_bal(insertAgiotageChildVO.getGlobal_money_bal().multiply(-1));
			insertAgiotageChildVO.setGlobal_money_de(insertAgiotageChildVO.getGlobal_money_de().multiply(-1));
			insertAgiotageChildVO.setGlobal_money_cr(insertAgiotageChildVO.getGlobal_money_cr().multiply(-1));
			insertAgiotageChildVO.setPk_agiotage_d("");
			insertAgiotageChildVO.setStatus(VOStatus.NEW);
			insertChildVOList.add(insertAgiotageChildVO);
		}

		MDPersistenceService.lookupPersistenceService().saveBill(insertChildVOList.toArray(new AgiotageChildVO[0]));
		MDPersistenceService.lookupPersistenceService().saveBill(updateChildVOList.toArray(new AgiotageChildVO[0]));

		// 重新计算汇兑损益汇总数据 ，根据币种分组汇总
		NCObject[] Aggresult = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(AgiotageMainVO.class, dealnosql, false);
		for (NCObject ncObject : Aggresult) {
			AggAgiotageVO aggvo = (AggAgiotageVO) ncObject.getContainmentObject();
			AgiotageMainVO mainvo = (AgiotageMainVO) aggvo.getParentVO();

			if (mainvoMap.containsKey(mainvo.getCurrtype() + mainvo.getDealno())) {
				mainvoMap.get(mainvo.getCurrtype() + mainvo.getDealno()).add(mainvo);
			} else {
				List<AgiotageMainVO> temp = new ArrayList<AgiotageMainVO>();
				temp.add(mainvo);
				mainvoMap.put(mainvo.getCurrtype() + mainvo.getDealno(), temp);
			}

			AgiotageChildVO[] childvo = (AgiotageChildVO[]) aggvo.getChildrenVO();
			for (AgiotageChildVO vo : childvo) {
				if (childvoMap.containsKey(vo.getCurrtype() + vo.getPk_agiotage())) {
					childvoMap.get(vo.getCurrtype() + vo.getPk_agiotage()).add(vo);
				} else {
					List<AgiotageChildVO> temp = new ArrayList<AgiotageChildVO>();
					temp.add(vo);
					childvoMap.put(vo.getCurrtype() + vo.getPk_agiotage(), temp);
				}
			}
		}

		Set<String> curr_dealSet = mainvoMap.keySet();
		for (String str : curr_dealSet) {
			List<AgiotageMainVO> list = mainvoMap.get(str);
			for (AgiotageMainVO mainvo : list) {
				AggAgiotageVO aggvo = new AggAgiotageVO();

				UFDouble local_money_de_sum = UFDouble.ZERO_DBL;
				UFDouble local_money_cr_sum = UFDouble.ZERO_DBL;
				UFDouble global_money_de_sum = UFDouble.ZERO_DBL;
				UFDouble global_money_cr_sum = UFDouble.ZERO_DBL;
				UFDouble group_money_de_sum = UFDouble.ZERO_DBL;
				UFDouble group_money_cr_sum = UFDouble.ZERO_DBL;
				List<AgiotageChildVO> childList = childvoMap.get(mainvo.getCurrtype() + mainvo.getPk_agiotage());
				for (AgiotageChildVO childvo : childList) {
					local_money_de_sum = local_money_de_sum.add(childvo.getLocal_money_de());
					local_money_cr_sum = local_money_de_sum.add(childvo.getLocal_money_cr());
					group_money_de_sum = group_money_de_sum.add(childvo.getGroup_money_de());
					group_money_cr_sum = group_money_cr_sum.add(childvo.getGroup_money_cr());
					global_money_de_sum = global_money_de_sum.add(childvo.getGlobal_money_de());
					global_money_cr_sum = global_money_cr_sum.add(childvo.getGlobal_money_cr());
				}
				mainvo.setStatus(VOStatus.UPDATED);
				mainvo.setLocal_money_de(local_money_de_sum);
				mainvo.setLocal_money_cr(local_money_cr_sum);
				mainvo.setGroup_money_de(group_money_de_sum);
				mainvo.setGroup_money_cr(group_money_cr_sum);
				mainvo.setGlobal_money_de(global_money_de_sum);
				mainvo.setGlobal_money_cr(global_money_cr_sum);
				MDPersistenceService.lookupPersistenceService().saveBill(mainvo);
				aggvo.setParentVO(mainvo);
				aggvo.setChildrenVO(childList.toArray(new AgiotageChildVO[0]));
				aggVOList.add(aggvo);
			}
		}

		// 回写业务数据
		NCLocator.getInstance().lookup(IBusiDataService.class).updateAgiotageData(insertChildVOList);

		new AgiotageBO().sendMessageToFip(aggVOList, FipMessageVO.MESSAGETYPE_ADD);
	}

	private void agiotageDealForYSX(String[] businos) throws BusinessException, SQLException {
		// 获取已实现汇兑损益数据
		List<ITallySourceData> tallylist = new ArrayList<ITallySourceData>();
		String agiotagesql = SqlUtils.getInStr(AgiotageMainVO.DEALNO, businos, true);
		NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(AggAgiotageVO.class, agiotagesql, false);

		if (ArrayUtils.isEmpty(ncObjects)) {
			return;
		}

		List<AggAgiotageVO> aggvoList = new ArrayList<AggAgiotageVO>();
		for (NCObject ob : ncObjects) {
			AggAgiotageVO aggVo = (AggAgiotageVO) ob.getContainmentObject();
			tallylist.add(new BaseTallySourceVO((SuperVO) aggVo.getParentVO(), (SuperVO[]) aggVo.getChildrenVO()));
			aggvoList.add(aggVo);
		}
		// 写帐
		NCLocator.getInstance().lookup(ITallyService.class).agiotageTally(BusiTypeEnum.CANCEL_AGIO, tallylist);

		// 核销主表加锁
		AggAgiotageVO[] aggArr = new AggAgiotageVO[aggvoList.size()];
		aggvoList.toArray(aggArr);
		BusiDataServiceImp.setDealDataLock(aggArr);
		// 检查ts
		BusiDataServiceImp.checkBillsTs(aggArr);

		BaseDAO dao = new BaseDAO();
		Collection<AgiotageMainVO> agmainCLct = dao.retrieveByClause(AgiotageMainVO.class, agiotagesql);
		if (agmainCLct != null && agmainCLct.size() > 0) {
			List<String> mainpks = new ArrayList<String>();
			for (AgiotageMainVO agiotageMainVO : agmainCLct) {
				mainpks.add(agiotageMainVO.getPk_agiotage());
			}
			String querysql = SqlUtils.getInStr(AgiotageMainVO.PK_AGIOTAGE, mainpks.toArray(new String[] {}));
			Collection<AgiotageChildVO> agdetailLct = dao.retrieveByClause(AgiotageChildVO.class, querysql);
			for (AgiotageChildVO agvo : agdetailLct) {
				agvo.setGlobal_money_bal(agvo.getGlobal_money_bal().multiply(-1));
				agvo.setGroup_money_bal(agvo.getGroup_money_bal().multiply(-1));
				agvo.setGlobal_money_de(agvo.getGlobal_money_de().multiply(-1));
				agvo.setGlobal_money_cr(agvo.getGlobal_money_cr().multiply(-1));
				agvo.setGroup_money_cr(agvo.getGroup_money_cr().multiply(-1));
				agvo.setGroup_money_de(agvo.getGroup_money_de().multiply(-1));
				agvo.setLocal_money_bal(agvo.getLocal_money_bal().multiply(-1));
				agvo.setLocal_money_de(agvo.getLocal_money_de().multiply(-1));
				agvo.setLocal_money_cr(agvo.getLocal_money_cr().multiply(-1));
				agvo.setMoney_bal(agvo.getMoney_bal().multiply(-1));
			}
			List<AgiotageChildVO> childvoList = new ArrayList<AgiotageChildVO>();
			childvoList.addAll(agdetailLct);
			// 回写
			NCLocator.getInstance().lookup(IBusiDataService.class).updateAgiotageData(childvoList);
			// 删除已实现汇兑损益数据
			dao.deleteByClause(AgiotageChildVO.class, querysql);
			dao.deleteByClause(AgiotageMainVO.class, agiotagesql);
			// 汇兑损益传会计平台
			new AgiotageBO().sendMessageToFip(aggvoList, FipMessageVO.MESSAGETYPE_DEL);
		}
	}

	// 根据pk_verify 查询verifydetailVO ，明细表精度设置
	public VerifyDetailVO[] getVerDetailVOByPntPK(String sql) throws BusinessException {
		String[] fileds = new String[] { VerifyDetailVO.PK_VERIFY, VerifyDetailVO.MONEY_DE, VerifyDetailVO.MONEY_CR, VerifyDetailVO.PK_CURRTYPE };

		NCObject[] result = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(VerifyDetailVO.class, sql, fileds, false);

		if (ArrayUtils.isEmpty(result)) {
			return null;
		}

		VerifyDetailVO[] vos = new VerifyDetailVO[result.length];
		for (int i = 0; i < vos.length; i++) {
			vos[i] = (VerifyDetailVO) result[i].getContainmentObject();
		}

		return vos;
	}

	public IBill generateAggVO(SuperVO headVo, SuperVO[] items) {
		String pk_billtype = (String) headVo.getAttributeValue(IBillFieldGet.PK_BILLTYPE);

		if (StringUtils.isEmpty(pk_billtype) || null == headVo || ArrayUtils.isEmpty(items))
			return null;
		IBill newInstance = null;
		try {
			newInstance = ArapBillType2TableMapping.getVOClassByBilltype(pk_billtype).newInstance();
		} catch (InstantiationException e) {
			nc.bs.logging.Log.getInstance(this.getClass()).error(e);
		} catch (IllegalAccessException e) {
			nc.bs.logging.Log.getInstance(this.getClass()).error(e);
		}
		if (newInstance == null)
			return null;
		newInstance.setParent(headVo);
		newInstance.setChildren(items[0].getClass(), items);
		return newInstance;

	}

	// 及时核销
	public BaseAggVO onIntimeVerify(BaseAggVO bcdj, VerifyCom com, String pk_billtype, String djpk) throws BusinessException {

		if (bcdj != null) {
			// 单据保存
			String billtype = bcdj.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).toString();
			NCLocator.getInstance().lookup(nc.pubitf.arap.bill.IArapBillPubService.class).save(bcdj);
			// 单据审核
			NCLocator.getInstance().lookup(nc.pubitf.arap.bill.IArapBillPubService.class).approve(bcdj);
			ConditionVO condition = new ConditionVO();
			ConditionVO jf_vo = null;
			ConditionVO df_vo = null;
			if (billtype.equals(IBillFieldGet.F0) || billtype.equals(IBillFieldGet.F3C) || billtype.equals(IBillFieldGet.F3) || billtype.equals(IBillFieldGet.F0S)) {
				jf_vo = new ConditionVO();
				jf_vo.setAttributeValue("other", " ( pk_bill= '" + bcdj.getParentVO().getPrimaryKey() + "' )");
				jf_vo.setAttributeValue(VerifySqlTool.JF_BILLTYPE, billtype);
				condition.setAttributeValue(VerifySqlTool.jfvo, jf_vo);
			} else if (billtype.equals(IBillFieldGet.F1C) || billtype.equals(IBillFieldGet.F2) || billtype.equals(IBillFieldGet.F2S) || billtype.equals(IBillFieldGet.F1)) {
				df_vo = new ConditionVO();
				df_vo.setAttributeValue("other", " ( pk_bill= '" + bcdj.getParentVO().getPrimaryKey() + "' )");
				df_vo.setAttributeValue(VerifySqlTool.DF_BILLTYPE, billtype);
				condition.setAttributeValue(VerifySqlTool.dfvo, df_vo);
			}

			ArapBusiDataVO[] vos = NCLocator.getInstance().lookup(IBusiDataService.class).getVerifyData(condition);
			ArapBusiDataVO[] debitData = com.getM_filter().getDebitData(vos);
			ArapBusiDataVO[] creditData = com.getM_filter().getCreditData(vos);

			Vector<ArapBusiDataVO> debitDataVec = new Vector<ArapBusiDataVO>();
			Vector<ArapBusiDataVO> creditDataVec = new Vector<ArapBusiDataVO>();

			if (debitData != null) {
				for (ArapBusiDataVO vo : debitData) {
					vo.setAttributeValue(ArapBusiDataVO.XZBZ, UFBoolean.valueOf(true));
					vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, vo.getAttributeValue(ArapBusiDataVO.MONEY_BAL));
					debitDataVec.add(vo);
				}
			}

			if (creditData != null) {
				for (ArapBusiDataVO vo : creditData) {
					vo.setAttributeValue(ArapBusiDataVO.XZBZ, UFBoolean.valueOf(true));
					vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, vo.getAttributeValue(ArapBusiDataVO.MONEY_BAL));
					creditDataVec.add(vo);
				}
			}

			if (com.getM_debitSelected() != null) {
				for (ArapBusiDataVO vo : com.getM_debitSelected()) {
					debitDataVec.add(vo);
				}
			}

			if (com.getM_creditSelected() != null) {
				for (ArapBusiDataVO vo : com.getM_creditSelected()) {
					creditDataVec.add(vo);
				}
			}

			com.setM_debitSelected(debitDataVec.toArray(new ArapBusiDataVO[] {}));
			com.setM_creditSelected(creditDataVec.toArray(new ArapBusiDataVO[] {}));
			com.setBusiDataTsMap(null);
		}

		com.verify(com.getM_debitSelected(), com.getM_creditSelected());

		com.save();

		try {
			return NCLocator.getInstance().lookup(nc.pubitf.arap.bill.IArapBillPubQueryService.class).findBillByPrimaryKey(new String[] { djpk }, new String[] { pk_billtype })[0];
		} catch (Exception e) {
			throw ExceptionHandler.handleException(this.getClass(), e);
		}

	}
}
