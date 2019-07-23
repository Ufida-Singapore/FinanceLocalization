package nc.bs.arap.busireg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.arap.bill.ArapBillCalUtil;
import nc.bs.arap.billact.LogTime;
import nc.bs.arap.cache.ArapBusiPluginCenter;
import nc.bs.arap.util.ArapMaterialUtils;
import nc.bs.arap.verify.FaDMO;
import nc.bs.businessevent.BdUpdateEvent;
import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IEventType;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.core.util.ObjectCreator;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.uap.lock.PKLock;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.arap.prv.IArapVerifyLogPrivate;
import nc.itf.fi.pub.Currency;
import nc.itf.fi.pub.SysInit;
import nc.itf.pcm.marketcontract.pub.IMarContrService4Payable;
import nc.itf.uap.busibean.SysinitAccessor;
import nc.md.data.access.NCObject;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.arap.pub.IArap4VerifyQryBill;
import nc.pubitf.arap.receivable.IArapReceivableBillPubQueryService;
import nc.pubitf.arap.termitem.IArapTermItemPubQueryService;
import nc.pubitf.bd.accessor.GeneralAccessorFactory;
import nc.pubitf.bd.accessor.IGeneralAccessor;
import nc.pubitf.uapbd.CurrencyRateUtilHelper;
import nc.pubitf.uapbd.ICustomerPubService;
import nc.pubitf.uapbd.ISupplierPubService;
import nc.util.fi.pub.SqlUtils;
import nc.vo.arap.agiotage.AgiotageChildVO;
import nc.vo.arap.agiotage.ArapBusiDataVO;
import nc.vo.arap.agiotage.ArapBusiDataVOList;
import nc.vo.arap.baddebts.BaddebtsOcchVO;
import nc.vo.arap.baddebts.BaddebtsOccuVO;
import nc.vo.arap.baddebts.BaddebtsReceVO;
import nc.vo.arap.baddebts.BaddebtsRechVO;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.basebill.IArapItemFieldVO;
import nc.vo.arap.debttransfer.DebtTransferVO;
import nc.vo.arap.event.IArapBSEventType;
import nc.vo.arap.gathering.GatheringBillItemVO;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.pay.PayBillItemVO;
import nc.vo.arap.payable.PayableBillItemVO;
import nc.vo.arap.pfflow.ArapBillMapVO;
import nc.vo.arap.pfflow.ArapBillMapVOTool;
import nc.vo.arap.pub.ArapBillTypeInfo;
import nc.vo.arap.pub.ArapConstant;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.arap.pub.BillEnumCollection.FromSystem;
import nc.vo.arap.receivable.ReceivableBillItemVO;
import nc.vo.arap.sysinit.SysinitConst;
import nc.vo.arap.termitem.TermVO;
import nc.vo.arap.utils.ArrayUtil;
import nc.vo.arap.utils.CmpQueryModulesUtil;
import nc.vo.arap.utils.StringUtil;
import nc.vo.arap.verify.AggverifyVO;
import nc.vo.arap.verify.RBVerify;
import nc.vo.arap.verify.SameMnyVerify;
import nc.vo.arap.verify.UnsameMnyVerify;
import nc.vo.arap.verify.VerifyDetailVO;
import nc.vo.arap.verify.VerifyTool;
import nc.vo.arap.verify.VerifyfaVO;
import nc.vo.arap.verifynew.ProxyVerify;
import nc.vo.arap.verifynew.QueryAndRuleFaVO;
import nc.vo.bd.accessor.IBDData;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.fibd.RecpaytypeVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.pub.MapList;
import nc.vo.pubapp.pattern.pub.PubAppTool;
import nc.vo.verifynew.pub.ConditionVO;
import nc.vo.verifynew.pub.DefaultVerifyRuleVO;
import nc.vo.verifynew.pub.VerifyCom;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
public class BillRegisterForBusiDataBO {

	private static String SPECIAL_KEY = "def1"; // 应收应付内部拉单核销，按照此字段记录上下游单据的主键，用于批量处理核销;

	Map<String, ArrayList<AggverifyVO>> aggMap = new HashMap<String, ArrayList<AggverifyVO>>();

	Map<String, List<TermVO>> termVOMap = new HashMap<String, List<TermVO>>();
	
	//add chenth 20161212 收款类型是否手续费
	//add by weiningc 20171012 633适配至65 end
	Map<String,RecpaytypeVO> recpaytypeMap =new HashMap<String,RecpaytypeVO>();
	//add end
	//add by weiningc 20171012 633适配至65 end

	AggregatedValueObject[] aggvos = null;

	public AggregatedValueObject[] getAggvos() {
		return this.aggvos;
	}

	public void setAggvos(AggregatedValueObject[] aggvos) {
		this.aggvos = aggvos;
	}

	String pkorg = "";

	private String pk_user;

	private Integer syscode;

	public Integer getSyscode() {
		return this.syscode;
	}

	public void setSyscode(Integer syscode) {
		this.syscode = syscode;
	}

	public String getPkorg() {
		return this.pkorg;
	}

	public void setPkorg(String pkorg) {
		this.pkorg = pkorg;
	}

	public String getPk_user() {
		return this.pk_user;
	}

	public void setPk_user(String pkUser) {
		this.pk_user = pkUser;
	}

	/**
	 * 统一调用批量接口
	 * 
	 * @param obj
	 * @return
	 */
	protected AggregatedValueObject[] dealUserObj(Object obj) {
		if (obj instanceof BusinessEvent.BusinessUserObj) {
			obj = ((BusinessEvent.BusinessUserObj) obj).getUserObj();
		}
		AggregatedValueObject[] retvos = null;
		if (obj.getClass().isArray()) {
			retvos = (AggregatedValueObject[]) obj;
		} else {
			retvos = new AggregatedValueObject[] { (AggregatedValueObject) obj };
		}
		this.pk_user = (String) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.APPROVER);
		this.syscode = (Integer) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.SYSCODE);
		this.pkorg = (String) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.PK_ORG);
		return retvos;
	}

	/**
	 * 业务单据登账接口
	 */
	public void doAction(IBusinessEvent event) throws BusinessException {
		this.doBusiAction(event);
	}

	/**
	 * 扩展核销规则处理
	 * 
	 * @param vos
	 * @param ruleVOMap
	 * @param com
	 * @throws BusinessException
	 * @throws MetaDataException
	 */
	private void billRBVerifyBySrcBillForExtend(AggregatedValueObject[] vos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException, MetaDataException {
		// 查询扩展核销规则，进行处理
		List<BillVerifyRule> rules = ArapBusiPluginCenter.getAllVerifyrulePlugins(getSyscode());

		ArrayList<AggverifyVO> aggVOList = new ArrayList<AggverifyVO>();

		for (AggregatedValueObject vo : vos) {
			for (BillVerifyRule vrule : rules) {
				if (vrule.isMatch((BaseAggVO) vo)) {
					for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
						rule.setM_creditObjKeys(vrule.getMatchFields());
						rule.setM_debtObjKeys(vrule.getMatchFields());
					}

					String queryOpBillSql = vrule.getQueryOpBillSql((BaseAggVO) vo);
					NCObject[] objectByCond = null;
					if (PubAppTool.isNull(queryOpBillSql)) {
						objectByCond = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, " 1=2 ", false);
					} else {
						objectByCond = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class,
								queryOpBillSql + " and billclass in ('yf','ys','fk','sk') ", false);
					}

					List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
					List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
					// 区分本方 对方
					if (objectByCond != null) {
						for (NCObject data : objectByCond) {
							ArapBusiDataVO busiDataVO = (ArapBusiDataVO) data.getContainmentObject();
							busiDataVO.setAttributeValue(ArapBusiDataVO.SETT_MONEY, busiDataVO.getOccupationmny());

							if (busiDataVO.getDirection().intValue() == 1) {
								jfList.add(busiDataVO);
							} else {
								dfList.add(busiDataVO);
							}
						}
					}
					this.aggMap.clear();
					this.aggMap = this.onVerify(com, ruleVOMap, jfList.toArray(new ArapBusiDataVO[0]), dfList.toArray(new ArapBusiDataVO[0]));
					aggVOList.addAll(this.getAggVerifyVO(this.aggMap));
					if (aggVOList != null && aggVOList.size() > 0) {
						NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
						// 重新查询单据表体的信息
						this.reQueryBillIteminfo(vo);

						aggVOList.clear();
					}
					this.aggMap.clear();

					// 重置rule，以便后续使用
					for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
						rule.setM_creditObjKeys(null);
						rule.setM_debtObjKeys(null);
					}
				}
			}
		}
	}

	private void billRBVerifyBySrcItem(AggregatedValueObject[] vos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException, MetaDataException {
		for (AggregatedValueObject vo : vos) {
			BaseBillVO parentVO = (BaseBillVO) vo.getParentVO();
			String pkBilltype = parentVO.getPk_billtype();
			BaseItemVO[] itemVOs = (BaseItemVO[]) vo.getChildrenVO();
			ArrayList<AggverifyVO> aggVOList = new ArrayList<AggverifyVO>();

			//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//			for (BaseItemVO itemVO : itemVOs) {
//				String srcBilltype = itemVO.getSrc_billtype();;
//				String srcItemid = itemVO.getSrc_itemid();
//				if (pkBilltype.equals(IBillFieldGet.F0) && srcBilltype != null && !StringUtil.isEmpty(srcItemid) && srcBilltype.equals(ArapBillDealVOConsts.BILLTYPE_30)) {
//					String whereCondStr = "";
//					whereCondStr = " src_itemid ='" + srcItemid + "' and pk_billtype='F0' ";
//
//					NCObject[] objectByCond = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, whereCondStr, false);
//
//					List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
//					List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
//					// 区分本方 对方
//					for (NCObject data : objectByCond) {
//						ArapBusiDataVO busiDataVO = (ArapBusiDataVO) data.getContainmentObject();
//						busiDataVO.setAttributeValue(ArapBusiDataVO.SETT_MONEY, busiDataVO.getOccupationmny());
//
//						if (busiDataVO.getDirection().intValue() == 1) {
//							jfList.add(busiDataVO);
//						} else {
//							dfList.add(busiDataVO);
//						}
//					}
//					this.aggMap.clear();
//					this.aggMap = this.onVerify(com, ruleVOMap, jfList.toArray(new ArapBusiDataVO[0]), dfList.toArray(new ArapBusiDataVO[0]));
//					aggVOList.addAll(this.getAggVerifyVO(this.aggMap));
//				}
//			}
			List<String> srcItemids = new ArrayList<String>();
			for (BaseItemVO itemVO : itemVOs) {
				String srcBilltype = itemVO.getSrc_billtype();
				String srcItemid = itemVO.getSrc_itemid();
				if (pkBilltype.equals(IBillFieldGet.F0) && srcBilltype != null && !StringUtil.isEmpty(srcItemid) && srcBilltype.equals(ArapBillDealVOConsts.BILLTYPE_30)) {
					srcItemids.add(srcItemid);
				}
			}

			String whereCondStr = "";
			whereCondStr = nc.vo.fi.pub.SqlUtils.getInStr("src_itemid", srcItemids.toArray(new String[0]), true) + " and pk_billtype='F0' and pk_org = '" + parentVO.getPk_org() + "'";
			NCObject[] objectByCond = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, whereCondStr, false);
			Map<String, List<ArapBusiDataVO>> busidataKeyMap = new HashMap<String, List<ArapBusiDataVO>>();

			// 根据src_itemid进行分组
			if (objectByCond != null && objectByCond.length > 0) {
				for (NCObject data : objectByCond) {
					ArapBusiDataVO busiDataVO = (ArapBusiDataVO) data.getContainmentObject();
					busiDataVO.setAttributeValue(ArapBusiDataVO.SETT_MONEY, busiDataVO.getOccupationmny());
					if (busidataKeyMap.get(busiDataVO.getSrc_itemid()) != null)
						busidataKeyMap.get(busiDataVO.getSrc_itemid()).add(busiDataVO);
					else {
						List<ArapBusiDataVO> busidatalist = new ArrayList<ArapBusiDataVO>();
						busidatalist.add(busiDataVO);
						busidataKeyMap.put(busiDataVO.getSrc_itemid(), busidatalist);
					}

				}

				for (BaseItemVO itemVO : itemVOs) {
					String srcBilltype = itemVO.getSrc_billtype();
					String srcItemid = itemVO.getSrc_itemid();
					if (pkBilltype.equals(IBillFieldGet.F0) && srcBilltype != null && !StringUtil.isEmpty(srcItemid) && srcBilltype.equals(ArapBillDealVOConsts.BILLTYPE_30)) {
						List<ArapBusiDataVO> busidatalist = busidataKeyMap.get(srcItemid);
						List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
						List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
						// 区分本方 对方
						if (busidatalist != null) {
							for (ArapBusiDataVO busiDataVO : busidatalist) {

								if (busiDataVO.getDirection().intValue() == 1) {
									jfList.add(busiDataVO);
								} else {
									dfList.add(busiDataVO);
								}
							}
							aggMap.clear();
							aggMap = onVerify(com, ruleVOMap, jfList.toArray(new ArapBusiDataVO[0]), dfList.toArray(new ArapBusiDataVO[0]));
							aggVOList.addAll(getAggVerifyVO(aggMap));
						}
					}
				}
			}
			//update end

			if (aggVOList != null && aggVOList.size() > 0) {
				NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
				// 重新查询单据表体的信息
				this.reQueryBillIteminfo(vo);
			}
			this.aggMap.clear();
		}
	}

	private void doBusiAction(IBusinessEvent event) throws BusinessException, MetaDataException, DAOException {
		// 生效后
		long t1 = System.currentTimeMillis();
		if (IArapBSEventType.TYPE_EFFECTION_AFTER.equals(event.getEventType())) {
			BusinessEvent e = (BusinessEvent) event;
			if (null != e.getUserObject()) {
				AggregatedValueObject[] vos = this.dealUserObj(e.getUserObject());
				this.setAggvos(vos);
				for (AggregatedValueObject obj : vos) {
					CircularlyAccessibleValueObject parentVO = obj.getParentVO();
					UFBoolean isinit = (UFBoolean) parentVO.getAttributeValue("isinit");
					if (isinit != null && isinit.booleanValue()) {
						// 如果是期初单据，不用审核监听，直接返回
						return;
					}
				}
				Object[] transform = this.transform(vos);
				List<ArapBusiDataVO> insertdatas = (List<ArapBusiDataVO>) transform[0];
				List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
				if (insertdatas != null && insertdatas.size() > 0) {
					this.setAreaPKForBusiData(insertdatas);// 地区PK赋值
				}

				this.doInsertRegister(insertdatas);

				if (updatedatas != null && updatedatas.size() > 0) {
					this.setAreaPKForBusiData(updatedatas); // 地区PK赋值
				}

				this.doInsertRegister(updatedatas);

				for (AggregatedValueObject obj : vos) {
					String billclass = (String) obj.getParentVO().getAttributeValue("billclass");
					if (billclass != null && (billclass.equals(ArapConstant.ARAP_ZS_BILLCLASS) || billclass.equals(ArapConstant.ARAP_ZF_BILLCLASS))) {
						return;
					}
				}

				// 拉式生成单自动核销
				List<String> pks = new ArrayList<String>();
				//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				List<ArapBillMapVO> allList = new ArrayList<ArapBillMapVO>();
				//del chenth
				List<ArapBillMapVO> scmList = new ArrayList<ArapBillMapVO>();
				List<ArapBillMapVO> defF2List = new ArrayList<ArapBillMapVO>();
				List<ArapBillMapVO> psList = new ArrayList<ArapBillMapVO>();
				List<ArapBillMapVO> pcmListNoMatch = new ArrayList<ArapBillMapVO>();
				MapList<String, ArapBillMapVO> pcmListBatchNoMatch = new MapList<String, ArapBillMapVO>();
				List<ArapBillMapVO> pcmListMatch = new ArrayList<ArapBillMapVO>();
				Hashtable<String, DefaultVerifyRuleVO> ruleVOMap = this.creatVerifyRuleVO();
				VerifyCom com = new VerifyCom();

				// 来源于销售的应收单生效后进行内部红蓝对冲
				// 进出口按照来源单据行核销(进口,按照整单的来源+收支项目核销)
				this.billRBVerifyBySrcBillForExtend(vos, ruleVOMap, com);
				//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				this.billRBVerifyBySrcItem(vos, ruleVOMap, com);
//				this.billRBVerify(vos, ruleVOMap, com);

				// 自动核销方案条件
				String pk_billtype = "";
				Map<String, VerifyfaVO> tradetypes = new HashMap<String, VerifyfaVO>();
				MapList<VerifyfaVO, String> tradeTypeBills = new MapList<VerifyfaVO, String>();
				
				BaseDAO dao = new BaseDAO();
				for (AggregatedValueObject vo : vos) {
					VerifyfaVO faVO = null;
					// 单据来源系统
					Integer src_syscode = (Integer) vo.getParentVO().getAttributeValue(IBillFieldGet.SRC_SYSCODE);
					BaseItemVO[] childrenVO = (BaseItemVO[]) vo.getChildrenVO();

					pk_billtype = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE);
					if (IBillFieldGet.F0.equals(pk_billtype) || IBillFieldGet.F2.equals(pk_billtype)) {
						faVO = new VerifyfaVO();
						faVO.setNode_code("20060VPS");
					} else if (IBillFieldGet.F1.equals(pk_billtype) || IBillFieldGet.F3.equals(pk_billtype)) {
						faVO = new VerifyfaVO();
						faVO.setNode_code("20080VPS");
					}
					if (faVO != null) {
						String pk_org = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.PK_ORG);
						String pk_tradetype = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.PK_TRADETYPEID);
						String key = pk_org + pk_tradetype;
						faVO.setPk_org(pk_org);
						faVO.setBf_transtype(pk_tradetype);
						if (!tradetypes.containsKey(key)) {
							tradetypes.put(key, faVO);
							tradeTypeBills.put(faVO, vo.getParentVO().getPrimaryKey());
						} else {
							tradeTypeBills.put(tradetypes.get(key), vo.getParentVO().getPrimaryKey());
						}
					}
					//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//                    if(true){
//                    	for (BaseItemVO itemVO : childrenVO) {
//	                    	ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//							allList.add(mapVO);
//                    	}
//                    }
					if (ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype())
							|| ArapBillDealVOConsts.BILLTYPE_36D7.equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//							if (itemVO.getTop_termch() == null) {
//								ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//								mapVO.setS_itemid(itemVO.getPurchaseorder());// 付款单
//								scmList.add(mapVO);
//							} else if ((ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype()) && ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getSrc_billtype()))) {
//								// 采购订单-付款申请-付款单，情况直接按照供应链的自动核销控制逻辑走
//								ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//								mapVO.setS_itemid(itemVO.getPurchaseorder());// 付款单
//								scmList.add(mapVO);
							if((ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0]
			                        .getTop_billtype())&&ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getSrc_billtype()))){
			            		//1、采购订单-付款申请-付款单，Top_termch：付款计划.付款协议账期主键
			            		//2、采购订单-采购发票-应付单-付款申请-付款单，Top_termch：应付单.收付款协议主键
			            		//3、付款申请-付款单 扩展流程：付款申请-计划编制-计划执行-付款单。Top_termch：应付单.收付款协议主键
			            		// 如果打了多次付款补丁，两个流程都有，需要通过Top_termch上游判断流程。单只有流程2 时，应先走与应付单的核销。
			            		String top_termch = itemVO.getTop_termch();
			            		if(!StringUtil.isEmpty(top_termch)){
			            			TermVO termVO = (TermVO) dao.retrieveByPK(TermVO.class, top_termch);
			            			if(termVO!=null){
			            				ArapBillMapVO mapVO = new ArapBillMapVO();
			                            mapVO.setT_itemid(itemVO.getPrimaryKey());
			                            mapVO.setS_termid(itemVO.getTop_termch());
			            				mapVO.setS_itemid(itemVO.getPrimaryKey());
			                            mapVO.setS_system(src_syscode);
			                            mapVO.setS_billtype(itemVO.getTop_billtype());
			                            mapVO.setYbje(itemVO.getMoney_bal());
			                            psList.add(mapVO);
			            			}else{
			                			// 采购订单-付款申请-付款单，情况直接按照供应链的自动核销控制逻辑走
			                    		 ArapBillMapVO mapVO =
			                    				 ArapBillMapVOTool.changeVotoBillMapNew(
			                    						 (BaseBillVO) vo.getParentVO(), itemVO);
			                    		 mapVO.setS_itemid(itemVO.getPurchaseorder());// 付款单
			                    		 //如果不将单据类型改为21，在classname_pu  和 classname_ps 匹配的时候会匹配到 classname_ps，
			                    		 //从而取 com.setReserveFlag(VerifyDetailVO.reserve_pc)，导致预占用金额不回写。
			                    		 mapVO.setS_billid(itemVO.getSrc_billid());
			                    		 mapVO.setS_billtype(ArapBillDealVOConsts.BILLTYPE_21);
			                    		 scmList.add(mapVO);
			            			}
			            			
			            		}
			              		 
			              	 } else if (itemVO.getTop_termch() == null ) {
			            	  ArapBillMapVO mapVO =
			            			  ArapBillMapVOTool.changeVotoBillMapNew(
			            					  (BaseBillVO) vo.getParentVO(), itemVO);
			            	  mapVO.setS_itemid(itemVO.getPurchaseorder());// 付款单
			            	  scmList.add(mapVO);
			            	  //update chenth end
							} else {

								ArapBillMapVO mapVO = new ArapBillMapVO();
								mapVO.setT_itemid(itemVO.getPrimaryKey());
								mapVO.setS_termid(itemVO.getTop_termch());
								mapVO.setS_itemid(itemVO.getPrimaryKey());
								mapVO.setS_system(src_syscode);
								mapVO.setS_billtype(itemVO.getTop_billtype());
								mapVO.setYbje(itemVO.getMoney_bal());
								psList.add(mapVO);
							}
						}
					} else if (ArapBillDealVOConsts.BILLTYPE_25.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_45.equals(childrenVO[0].getTop_billtype())
							|| ArapBillDealVOConsts.BILLTYPE_Z2.equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
							scmList.add(mapVO);
						}
					} else if (ArapBillDealVOConsts.BILLTYPE_30.equals(childrenVO[0].getSrc_billtype()) || ArapBillDealVOConsts.BILLTYPE_Z3.equals(childrenVO[0].getTop_billtype())
							|| ArapBillDealVOConsts.BILLTYPE_32.equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNewForSo((BaseBillVO) vo.getParentVO(), itemVO);
							scmList.add(mapVO);
						}
					} else if (ArapBillDealVOConsts.BILLTYPE_4D48.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_4D50.equals(childrenVO[0].getTop_billtype())
							|| ArapBillDealVOConsts.BILLTYPE_4D46.equals(childrenVO[0].getTop_billtype()) || "4D52".equals(childrenVO[0].getTop_billtype())
							|| "4D39".equals(childrenVO[0].getTop_billtype()) || "4D83".equals(childrenVO[0].getTop_billtype())
							//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
							|| "4D53".equals(childrenVO[0].getTop_billtype())
							//add end
							) {
						for (BaseItemVO itemVO : childrenVO) {
							String srcBillid = itemVO.getSrc_billid();
							if (StringUtils.isNotEmpty(srcBillid)) {
								if (ArapBillDealVOConsts.BILLTYPE_4D46.equals(childrenVO[0].getTop_billtype())) {
									Collection<PayableBillItemVO> itemCollections = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
											ArapBillTypeInfo.getInstance(IBillFieldGet.F1).getItemvoClass(),
											SqlUtils.getInStr(IArapItemFieldVO.SRC_BILLID, new String[] { srcBillid }, true) + " and dr = 0", false);
									if (itemCollections != null) {
										for (PayableBillItemVO item : itemCollections) {
											ArapBillMapVO mapVO = new ArapBillMapVO();
											mapVO.setT_itemid(itemVO.getPrimaryKey());
											mapVO.setS_itemid(item.getPrimaryKey());
											mapVO.setS_system(1);// 应付

											if (itemVO.getSrc_itemid() != null) {
												if (!itemVO.getSrc_itemid().equals(item.getSrc_itemid())) {
													continue;
												}
												pcmListMatch.add(mapVO);
											} else {
												pcmListBatchNoMatch.put(item.getParentPK(), mapVO);
											}
										}
									}
								} else {
									Collection<PayBillItemVO> itemCollections = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
											ArapBillTypeInfo.getInstance(IBillFieldGet.F3).getItemvoClass(),
											SqlUtils.getInStr(IArapItemFieldVO.SRC_BILLID, new String[] { srcBillid }, true) + " and dr = 0", false);
									if (itemCollections != null) {
										for (PayBillItemVO item : itemCollections) {
											ArapBillMapVO mapVO = new ArapBillMapVO();
											mapVO.setT_itemid(itemVO.getPrimaryKey());
											mapVO.setS_itemid(item.getPrimaryKey());
											mapVO.setS_system(1);// 应付

											if (item.getSrc_itemid() != null) {
												if (!itemVO.getSrc_itemid().equals(item.getSrc_itemid())) {
													continue;
												}
												pcmListMatch.add(mapVO);
											} else {
												pcmListBatchNoMatch.put(item.getParentPK(), mapVO);
											}
										}
									}
								}
							}
						}
					} else if ("4D60".equals(childrenVO[0].getTop_billtype()) || "4D62".equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							if ("4D60".equals(childrenVO[0].getTop_billtype())) { // 应收单审批
								String classname_pcm = "nc.impl.pcm.marketcontract.pub.MarContrService4PayableImpl";
								IMarContrService4Payable qryPcmClass = (IMarContrService4Payable) ObjectCreator.newInstance("pcm", classname_pcm);
								MapList<String, String> topitems = qryPcmClass.queryPlanByMContr(itemVO.getSrc_billid());
								List<String> top_itemid;
								// 如果该行还未进行收款，不需要核销
								if (topitems == null || topitems.get(itemVO.getTop_itemid()) == null) {
									continue;
								}
								top_itemid = topitems.get(itemVO.getTop_itemid());
								Collection<GatheringBillItemVO> itemCollections = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
										ArapBillTypeInfo.getInstance(IBillFieldGet.F2).getItemvoClass(),
										SqlUtils.getInStr(IArapItemFieldVO.TOP_ITEMID, top_itemid.toArray(new String[0]), true) + " and dr = 0", false);
								if (itemCollections != null && itemCollections.size() != 0) {
									for (GatheringBillItemVO item : itemCollections) {
										ArapBillMapVO mapVO = new ArapBillMapVO();
										mapVO.setT_itemid(itemVO.getPrimaryKey());
										mapVO.setS_itemid(item.getPrimaryKey());
										pcmListNoMatch.add(mapVO);
									}
								}
							} else { // 收款单审批
								String topBillID = itemVO.getTop_billid();
								// 查询收款单top_itemId
								String classname_pcm = "nc.impl.pcm.marketcontract.pub.MarContrService4PayableImpl";
								IMarContrService4Payable qryPcmClass = (IMarContrService4Payable) ObjectCreator.newInstance("pcm", classname_pcm);
								Map<String, String> topitems = qryPcmClass.queryMContrByPlan(topBillID);
								// 如果该行还未生成应收，不需要核销
								if (topitems == null || topitems.get(itemVO.getTop_itemid()) == null) {
									continue;
								}
								String top_itemid = topitems.get(itemVO.getTop_itemid());
								Collection<ReceivableBillItemVO> itemCollections = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
										ArapBillTypeInfo.getInstance(IBillFieldGet.F0).getItemvoClass(),
										SqlUtils.getInStr(IArapItemFieldVO.TOP_ITEMID, new String[] { top_itemid }, true) + " and dr = 0", false);
								if (itemCollections != null && itemCollections.size() != 0) {
									for (ReceivableBillItemVO item : itemCollections) {
										ArapBillMapVO mapVO = new ArapBillMapVO();
										mapVO.setT_itemid(itemVO.getPrimaryKey());
										mapVO.setS_itemid(item.getPrimaryKey());
										pcmListNoMatch.add(mapVO);
									}
								}
							}
						}

					}
					//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
					//如果来源是收款申请的收款单，与源头是销售合同的应收单核销
			          if("4D62".equals(childrenVO[0].getTop_billtype())&&"F2".equals(pk_billtype)){
			        	  for (BaseItemVO itemVO : childrenVO) {
			        		  String sql=IArapItemFieldVO.SRC_BILLID+"='"+childrenVO[0].getSrc_billid()+"'"
			        		  		+ " and dr=0";
			        		  Collection<ReceivableBillItemVO> recbills = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
			        				  ArapBillTypeInfo.getInstance(IBillFieldGet.F0).getItemvoClass(),sql,false);
			        		  if (recbills != null && recbills.size() != 0) {
			        			  for (ReceivableBillItemVO item : recbills) {
			        				  ArapBillMapVO mapVO = new ArapBillMapVO();
			        				  mapVO.setT_itemid(itemVO.getPrimaryKey());
			        				  mapVO.setS_itemid(item.getPrimaryKey());
			        				  pcmListNoMatch.add(mapVO);
			        			  }
			        		  }
			        	  }
			          }else if("4D65".equals(childrenVO[0].getTop_billtype())&&"F0".equals(pk_billtype)){
			        	  for (BaseItemVO itemVO : childrenVO) {
			        		  String sql=IArapItemFieldVO.SRC_BILLID+"='"+childrenVO[0].getSrc_billid()+"'"
			        		  		+ " and dr=0";
			        		  Collection<GatheringBillItemVO> recbills = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
			        				  ArapBillTypeInfo.getInstance(IBillFieldGet.F2).getItemvoClass(),sql,false);
			        		  if (recbills != null && recbills.size() != 0) {
			        			  for (GatheringBillItemVO item : recbills) {
			        				  ArapBillMapVO mapVO = new ArapBillMapVO();
			        				  mapVO.setT_itemid(itemVO.getPrimaryKey());
			        				  mapVO.setS_itemid(item.getPrimaryKey());
			        				  pcmListNoMatch.add(mapVO);
			        			  }
			        		  }
			        	  }
			          }
			          //add end

					if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							String srcBillid = itemVO.getSrc_billid();
							if (StringUtils.isNotEmpty(srcBillid)) {
								Collection<PayableBillItemVO> itemCollections = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByCond(
										ArapBillTypeInfo.getInstance(IBillFieldGet.F1).getItemvoClass(),
										SqlUtils.getInStr(IArapItemFieldVO.SRC_BILLID, new String[] { srcBillid }, true) + " and dr = 0", false);
								if (itemCollections != null) {
									for (PayableBillItemVO item : itemCollections) {
										ArapBillMapVO mapVO = new ArapBillMapVO();
										mapVO.setT_itemid(itemVO.getPrimaryKey());
										mapVO.setS_itemid(item.getPrimaryKey());
										mapVO.setS_system(1);// 应付

										if (itemVO.getSrc_itemid() != null) {
											if (!itemVO.getSrc_itemid().equals(item.getSrc_itemid())) {
												continue;
											}
											pcmListMatch.add(mapVO);
										} else {
											pcmListBatchNoMatch.put(item.getParentPK(), mapVO);
										}
									}
								}
							}
						}
					}
					// 应收应付内部的单据进行自动核销
					if (src_syscode.intValue() == FromSystem.AR.VALUE.intValue() || src_syscode.intValue() == FromSystem.AP.VALUE.intValue()) {
						pks.add(vo.getParentVO().getPrimaryKey());
					}

					// 应收应付自制的或者是其他系统传递过来的收款单都可能建立订单核销关系
					if (vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(IBillFieldGet.F2)) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNewForSo((BaseBillVO) vo.getParentVO(), itemVO);
							defF2List.add(mapVO);
						}
					}
				}
				try {
					if (scmList != null && !scmList.isEmpty()) {
						String classname_so = "";
						String classname_pu = "";
						String classname_ps = "";
						// 供应链核销 ，查询返回核销关系数据
						if (scmList.get(0).getS_billtype() == null) {
							//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
							// 采购订单-》应付， 采购订单-》合同-》付款 ，自动核销
							this.VerifyByContractNo(scmList, com, ruleVOMap);
							return;
						} else if (scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_21)) {
							classname_pu = "nc.pubimpl.pu.m25.arap.f3.InvoiceQueryForVerifyImpl";
						} else if (scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D7) || scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D1)) {
							if (CmpQueryModulesUtil.isPUEnable(InvocationInfoProxy.getInstance().getGroupId())) {
								classname_ps = "nc.pubimpl.pu.m25.arap.f3.InvoiceQueryForVerifyImpl";
							}
						} else if (scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_25) || scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_45)) {
							classname_pu = "nc.pubimpl.pu.m21.arap.mf1.OrderQueryForVerifyImpl";
						} else if (scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_30) || scmList.get(0).getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_32)) {
							classname_so = "nc.pubimpl.so.sobalance.arap.verify.SoBalance4VerifyQryBillImpl";
						}
						IArap4VerifyQryBill qryClass = null;
						//
						List<ArapBillMapVO> newList = new ArrayList<ArapBillMapVO>();
						newList.addAll(scmList);
						List<ArapBillMapVO> puList1 = new ArrayList<ArapBillMapVO>();
						for (ArapBillMapVO vo : scmList) {
							if (StringUtils.isEmpty(vo.getS_itemid())) {
							} else {
								puList1.add(vo);
							}
						}
						if (!classname_pu.equals("")) {
							qryClass = (IArap4VerifyQryBill) ObjectCreator.newInstance("pu", classname_pu);
							com.setReserveFlag(VerifyDetailVO.reserve_pu);
						} else if (!classname_so.equals("")) {
							qryClass = (IArap4VerifyQryBill) ObjectCreator.newInstance("so", classname_so);
							com.setReserveFlag(VerifyDetailVO.reserve_so);
						} else if (!classname_ps.equals("")) {
							try {
								qryClass = (IArap4VerifyQryBill) ObjectCreator.newInstance("pu", classname_ps);
								com.setReserveFlag(VerifyDetailVO.reserve_pc);
							} catch (Throwable ex) {
								ExceptionHandler.consume(ex);
							}
						}

						Map<ArapBillMapVO, Collection<ArapBillMapVO>> arapBillMap = null;

						if (puList1 != null && puList1.size() > 0 && qryClass != null) {
							arapBillMap = qryClass.queryArapBillmap(scmList.toArray(new ArapBillMapVO[0]));
						}

						this.printMapForDebug(arapBillMap);

						Collection<Collection<ArapBillMapVO>> mapCollections = null;
						if (arapBillMap != null && arapBillMap.size() > 0) {
							arapBillMap = ArapBillMapVOTool.combineSameSourceBillMap(arapBillMap);

							// 组织返回的mapvo
							for (ArapBillMapVO vo : arapBillMap.keySet()) {
								Collection<ArapBillMapVO> collection = arapBillMap.get(vo);
								for (ArapBillMapVO vo1 : collection) {
									vo1.setT_billid(vo.getT_billid());
									vo1.setT_billtype(vo.getT_billtype());
									vo1.setT_itemid(vo.getT_itemid());
									vo1.setPk_currtype(vo.getPk_currtype());
								}
							}
							// 核销 --单据来自供应链的
							mapCollections = arapBillMap.values();
							for (Collection<ArapBillMapVO> collection : mapCollections) {
								this.doVerifyWithMap(collection, ruleVOMap, com, null);
							}
						}
						// 采购订单-》应付， 采购订单-》合同-》付款 ，自动核销
						//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
						this.VerifyByContractNo(newList, com, ruleVOMap);
						//add chenth end
					}
					//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//					VerifyByContractNo(allList, com, ruleVOMap);
					//del chenth
					
					// 自制收款单已经和销售订单建立预核销
					if (defF2List.size() > 0) {
						IArap4VerifyQryBill qryClass = null;
						String classname_so = "nc.pubimpl.so.sobalance.arap.verify.SoBalance4VerifyQryBillImpl";

						// 选中的单据生效后进行红蓝对冲
						// billRBVerify(vos, ruleVOMap, com);

						Map<ArapBillMapVO, Collection<ArapBillMapVO>> arapBillMap = null;

						if (CmpQueryModulesUtil.isSOEnable(InvocationInfoProxy.getInstance().getGroupId())) {
							try {
								qryClass = (IArap4VerifyQryBill) ObjectCreator.newInstance("so", classname_so);
								com.setReserveFlag(VerifyDetailVO.reserve_so);
							} catch (Exception e1) {
								Logger.error(e1.getMessage(), e1);
							}
							arapBillMap = qryClass.queryArapBillmap(defF2List.toArray(new ArapBillMapVO[0]));
						}

						Collection<Collection<ArapBillMapVO>> mapCollections = null;
						if (arapBillMap != null && arapBillMap.size() > 0) {
							arapBillMap = ArapBillMapVOTool.combineSameSourceBillMap(arapBillMap);

							// 组织返回的mapvo
							for (ArapBillMapVO vo : arapBillMap.keySet()) {
								Collection<ArapBillMapVO> collection = arapBillMap.get(vo);
								for (ArapBillMapVO vo1 : collection) {
									vo1.setT_billid(vo.getT_billid());
									vo1.setT_billtype(vo.getT_billtype());
									vo1.setT_itemid(vo.getT_itemid());
									vo1.setPk_currtype(vo.getPk_currtype());
								}
							}
							// 核销 --单据来自供应链的
							mapCollections = arapBillMap.values();
							for (Collection<ArapBillMapVO> collection : mapCollections) {
								this.doVerifyWithMap(collection, ruleVOMap, com, null);
							}
						}
					}
					if (pks != null && pks.size() > 0) {
						com.setReserveFlag(VerifyDetailVO.reserve_arap);
						com.setExactVerify(true);
						//restore chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
						for(DefaultVerifyRuleVO rule:ruleVOMap.values()){
							rule.setM_creditObjKeys(new String[]{SPECIAL_KEY});
							rule.setM_debtObjKeys(new String[]{SPECIAL_KEY});
						}
						// arap内部的单据审核时自动核销
						Collection<ArapBillMapVO> billMapData = new BaseDAO().retrieveByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_billid", pks.toArray(new String[] {}), true));
						this.doArapVerifyWithMap(billMapData, ruleVOMap, com);
						this.updateBillMapYbye(billMapData, true);
					}
					//add chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
					 // 来自资金的付款排程单据
			          if (psList != null && !psList.isEmpty()) {
			        	  for (String key : ruleVOMap.keySet()) {
			        		  DefaultVerifyRuleVO ruleVO = ruleVOMap.get(key);
			        		  ruleVO.setIsFkpc(new Boolean(true));
			        	  }
			        	  com.setReserveFlag(VerifyDetailVO.reserve_pc);
			        	  
			        	  for(DefaultVerifyRuleVO rule:ruleVOMap.values()){
			        		  rule.setM_creditObjKeys(new String[]{SPECIAL_KEY});
			        		  rule.setM_debtObjKeys(new String[]{SPECIAL_KEY});
			        	  }
			        	  
			        	  this.doVerifyWithMap(psList, ruleVOMap, com, null);
			          }
					//add end
					// 来自项目预付和进度款的核销
					if (pcmListNoMatch.size() > 0 || pcmListMatch.size() > 0 || pcmListBatchNoMatch.size() > 0) {
						com.setReserveFlag(VerifyDetailVO.reserve_pcm);
						String billclass = (String) vos[0].getParentVO().getAttributeValue(IBillFieldGet.BILLCLASS);
						if (billclass.equals("ys") || billclass.equals("sk")) {
							for (ArapBillMapVO vo : pcmListNoMatch) {
								List<ArapBillMapVO> voss = new ArrayList<ArapBillMapVO>();
								voss.add(vo);
								this.doVerifyWithMap(voss, ruleVOMap, com);
							}
						} else {
							for (ArapBillMapVO vo : pcmListMatch) {
								List<ArapBillMapVO> voss = new ArrayList<ArapBillMapVO>();
								voss.add(vo);
								this.doVerifyWithMap(voss, ruleVOMap, com);
							}

							for (String key : pcmListBatchNoMatch.keySet()) {
								List<ArapBillMapVO> list = pcmListBatchNoMatch.get(key);
								this.doVerifyWithMap(list, ruleVOMap, com);
							}

							if (pcmListNoMatch.size() > 0) {
								this.doVerifyWithMap(pcmListNoMatch, ruleVOMap, com);
							}
						}
					}
					//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//					// 来自资金的付款排程单据
//					if (psList != null && !psList.isEmpty()) {
//						for (String key : ruleVOMap.keySet()) {
//							DefaultVerifyRuleVO ruleVO = ruleVOMap.get(key);
//							ruleVO.setIsFkpc(new Boolean(true));
//						}
//						com.setReserveFlag(VerifyDetailVO.reserve_pc);
//
//						for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
//							rule.setM_creditObjKeys(new String[] { SPECIAL_KEY });
//							rule.setM_debtObjKeys(new String[] { SPECIAL_KEY });
//						}
//
//						this.doVerifyWithMap(psList, ruleVOMap, com, null);
//					}
					//del end
					
					// 根据自动核销方案核销
					for (VerifyfaVO faVO : tradeTypeBills.keySet()) {
						if (faVO != null) {
							FaDMO dmo = new FaDMO();
							String sql = "  pk_org='" + faVO.getPk_org() + "' and node_code='" + faVO.getNode_code() + "' and dr=0 and isinit='1'  and ";
							if (IBillFieldGet.F0.equals(pk_billtype) || IBillFieldGet.F3.equals(pk_billtype)) {
								sql += "  (COALESCE(bf_transtype,'~') ='~' or bf_transtype ='' or bf_transtype ='" + faVO.getBf_transtype() + "') order by bf_transtype";
							} else {
								sql += "   (COALESCE(df_transtype,'~') ='~' or df_transtype ='' or df_transtype ='" + faVO.getBf_transtype() + "') order by df_transtype";
							}
							List<VerifyfaVO> verifyfaVOs = dmo.queryFa(sql);
							if (verifyfaVOs != null && verifyfaVOs.size() > 0) {
								for (VerifyfaVO favo : verifyfaVOs) {
									int szxt = 0;
									if (favo.getNode_code().equals("20060VPS")) {// 应收
										szxt = 0;
									} else if (favo.getNode_code().equals("20080VPS")) {// 应付
										szxt = 1;
									}
									QueryAndRuleFaVO convertVo = new QueryAndRuleFaVO(favo, favo.getPk_org(), favo.getPk_user().trim(), Integer.valueOf(szxt), null, new UFDate(InvocationInfoProxy
											.getInstance().getBizDateTime()));

									ConditionVO condition = convertVo.getFilterConditionVO();
									if (IBillFieldGet.F0.equals(pk_billtype) || IBillFieldGet.F3.equals(pk_billtype)) {
										condition.setAttributeValue("jfFa", " and " + SqlUtils.getInStr("pk_bill", tradeTypeBills.get(faVO).toArray(new String[] {}), true));
									} else {
										condition.setAttributeValue("dfFa", " and " + SqlUtils.getInStr("pk_bill", tradeTypeBills.get(faVO).toArray(new String[] {}), true));
									}
									DefaultVerifyRuleVO[] rules = convertVo.getRuleVO();
									if (rules == null || rules.length == 0) {
									} else {
										ProxyVerify.getIArapVerifyLogPrivate().autoVerify(condition, rules);
									}
								}
							}
						}
					}
				} catch (Exception e1) {
					throw ExceptionHandler.handleException(e1);
				}
			}

		}
		// 取消生效前处理
		else if (IArapBSEventType.TYPE_UNEFFECTION_BEFORE.equals(event.getEventType())) {
			this.uneffectBill(event);
		}

		// 新增后-期初单
		else if (IEventType.TYPE_INSERT_AFTER.equals(event.getEventType())) {
			BusinessEvent e = (BusinessEvent) event;
			if (null != e.getUserObject()) {
				AggregatedValueObject[] vos = this.dealUserObj(e.getUserObject());
				// 期初单据新增后保存
				UFBoolean isinit = (UFBoolean) vos[0].getParentVO().getAttributeValue("isinit"); // 期初标志
				if (isinit != null && isinit.booleanValue()) {
					Object[] transform = this.transform(vos);
					List<ArapBusiDataVO> insertdatas = (List<ArapBusiDataVO>) transform[0];
					List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
					if (insertdatas != null && insertdatas.size() > 0) {
						// 地区PK赋值
						this.setAreaPKForBusiData(insertdatas);
					}
					this.doInsertRegister(insertdatas);
					if (updatedatas != null && updatedatas.size() > 0) {
						// 地区PK赋值
						this.setAreaPKForBusiData(updatedatas);
					}
					this.doInsertRegister(updatedatas);
				}
			}
		}

		// 删除后-期初单
		else if (IEventType.TYPE_DELETE_BEFORE.equals(event.getEventType())) {
			BusinessEvent e = (BusinessEvent) event;
			if (null != e.getUserObject()) {
				AggregatedValueObject[] vos = this.dealUserObj(e.getUserObject());
				List<String> deldata = new ArrayList<String>();
				for (AggregatedValueObject vo : vos) {
					deldata.add(vo.getParentVO().getPrimaryKey());
				}
				this.doCheckBillDeal(deldata);
				this.doDeleteRegisterByBillid(deldata.toArray(new String[] {}));
			}
		}

		if (IEventType.TYPE_UPDATE_AFTER.equals(event.getEventType())) {
			BdUpdateEvent e = (BdUpdateEvent) event;
			if (null != e.getNewObject()) {
				AggregatedValueObject[] vos = this.dealUserObjForInit(e.getNewObject());
				// 期初单据新增后保存
				UFBoolean isinit = (UFBoolean) vos[0].getParentVO().getAttributeValue("isinit"); // 期初标志
				String pk_bill = vos[0].getParentVO().getPrimaryKey(); // 期初标志
				if (isinit != null && isinit.booleanValue()) {
					// 先删除再插入
					new BaseDAO().deleteByClause(ArapBusiDataVO.class, " pk_bill ='" + pk_bill + "'");
					Object[] transform = this.transform(vos);
					List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
					// 地区PK赋值
					this.setAreaPKForBusiData(updatedatas);
					if (updatedatas != null) {
						this.doInsertRegister(updatedatas);
					}
				}

			}
		}
		LogTime.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0258")/*
																										 * @
																										 * res
																										 * "BillRegisterForBusiData(业务单据登账接口)"
																										 */, t1);
	}

	private void printMapForDebug(Map<ArapBillMapVO, Collection<ArapBillMapVO>> arapBillMap) {
		try {
			if (arapBillMap != null) {
				for (ArapBillMapVO vo : arapBillMap.keySet()) {
					Log.getInstance(LogTime.class).debug("ARAPBILLMAP");/*
																		 * -=
																		 * notranslate
																		 * =-
																		 */
					Log.getInstance(LogTime.class).debug(vo.getYbje());/*
																		 * -=
																		 * notranslate
																		 * =-
																		 */
					Log.getInstance(LogTime.class).debug(vo.getYbye());/*
																		 * -=
																		 * notranslate
																		 * =-
																		 */
					Log.getInstance(LogTime.class).debug(vo.getS_itemid());/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */
					Log.getInstance(LogTime.class).debug(vo.getT_itemid());/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */
					for (ArapBillMapVO svo : arapBillMap.get(vo)) {
						Log.getInstance(LogTime.class).debug(svo.getYbje());/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */
						Log.getInstance(LogTime.class).debug(svo.getYbye());/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */
						Log.getInstance(LogTime.class).debug(svo.getS_itemid());/*
																				 * -=
																				 * notranslate
																				 * =
																				 * -
																				 */
						Log.getInstance(LogTime.class).debug(svo.getT_itemid());/*
																				 * -=
																				 * notranslate
																				 * =
																				 * -
																				 */
					}
					Log.getInstance(LogTime.class).debug("ENDDDDD");/*
																	 * -=notranslate
																	 * =-
																	 */
				}
			}
		} catch (Exception e) {
		}
	}

	private void uneffectBill(IBusinessEvent event) throws BusinessException {
		BusinessEvent e = (BusinessEvent) event;
		if (null != e.getUserObject()) {
			AggregatedValueObject[] vos = this.dealUserObj(e.getUserObject());
			List<String> deldata = new ArrayList<String>();
			List<String> pks = new ArrayList<String>();
			//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//			List<ArapBillMapVO> allList = new ArrayList<ArapBillMapVO>(); // 采购
			//del
			List<ArapBillMapVO> puList = new ArrayList<ArapBillMapVO>(); // 采购
			List<ArapBillMapVO> soList = new ArrayList<ArapBillMapVO>(); // 销售订单-收款单
			List<ArapBillMapVO> htList = new ArrayList<ArapBillMapVO>(); // 销售合同-收单
			List<ArapBillMapVO> psList = new ArrayList<ArapBillMapVO>(); // 付款排程
			List<ArapBillMapVO> pcmList = new ArrayList<ArapBillMapVO>(); // 项目合同
			List<String> bodypks = new ArrayList<String>();
			List<ArapBillMapVO> F2ForSoList = new ArrayList<ArapBillMapVO>(); // 订单收款,//
																				// 手工挂上一个收款单
			String billclass = ((BaseAggVO) vos[0]).getHeadVO().getBillclass();
			boolean ifF1Uneffect = billclass != null && billclass.equals(IBillFieldGet.YF);// 是否应付单反审核，是的话则反核销不需要通知付款申请接口
			for (AggregatedValueObject vo : vos) {
				Integer src_syscode = (Integer) vo.getParentVO().getAttributeValue(IBillFieldGet.SRC_SYSCODE);
				BaseItemVO[] childrenVO = (BaseItemVO[]) vo.getChildrenVO();

				//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				for (BaseItemVO itemVO : childrenVO) {
//					ArapBillMapVO mapVO = new ArapBillMapVO();
//					mapVO.setT_itemid(itemVO.getPrimaryKey());
//					allList.add(mapVO);
//				}
				//del end
				
				if (src_syscode.intValue() == FromSystem.AR.VALUE.intValue() || src_syscode.intValue() == FromSystem.AP.VALUE.intValue()) {
					pks.add(vo.getParentVO().getPrimaryKey());
					if (src_syscode.intValue() == FromSystem.AR.VALUE.intValue() && vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(IBillFieldGet.F2)) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = new ArapBillMapVO();
							mapVO.setT_itemid(itemVO.getPrimaryKey());
							if (IBillFieldGet.F2.equals(itemVO.getAttributeValue(IBillFieldGet.TOP_BILLTYPE))) {
								bodypks.add(itemVO.getPrimaryKey());
							} else {
								F2ForSoList.add(mapVO);
							}
						}
					}
					if (src_syscode.intValue() == FromSystem.AP.VALUE.intValue() && vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(IBillFieldGet.F3)) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = new ArapBillMapVO();
							mapVO.setT_itemid(itemVO.getPrimaryKey());
							if (IBillFieldGet.F3.equals(itemVO.getAttributeValue(IBillFieldGet.TOP_BILLTYPE))) {
								bodypks.add(itemVO.getPrimaryKey());
							}
						}
					}
				} else {
					String pk_billtype = (String) vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE);
					if (pk_billtype.equals(IBillFieldGet.F0) || pk_billtype.equals(IBillFieldGet.F2)) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = new ArapBillMapVO();
							mapVO.setT_itemid(itemVO.getPrimaryKey());
							// 销售合同-收款单
							if (ArapBillDealVOConsts.BILLTYPE_Z3.equals(itemVO.getTop_billtype())) {
								htList.add(mapVO);
							} else {
								soList.add(mapVO);
							}
						}
					} else if (pk_billtype.equals(IBillFieldGet.F1) || pk_billtype.equals(IBillFieldGet.F3)) {
						for (BaseItemVO itemVO : childrenVO) {
							ArapBillMapVO mapVO = new ArapBillMapVO();
							mapVO.setT_itemid(itemVO.getPrimaryKey());
							// 应付单 -付款申请||执行计划 -付款单
							if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_36D7.equals(childrenVO[0].getTop_billtype())) {
								psList.add(mapVO);
							} else if (itemVO.getTop_billtype() != null
									&& (ArapBillDealVOConsts.BILLTYPE_4D46.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_4D50.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_4D48
											.equals(childrenVO[0].getTop_billtype()))) {
								pcmList.add(mapVO);
							} else {
								puList.add(mapVO);
							}
						}
					}
				}
				deldata.add(vo.getParentVO().getPrimaryKey());
			}
			this.doCheckBillDeal(deldata);
			// 拉式生成单审核后反核销
			try {
				boolean isfkpc = false;// 是否应付单反审核，是的话则反核销不需要通知付款申请接口
				if (ifF1Uneffect) {
					isfkpc = true;
				}
				if (pks != null && pks.size() > 0) {
					Collection<ArapBillMapVO> billMapData = new BaseDAO().retrieveByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_billid", pks.toArray(new String[] {}), true));
					this.doUnVerifyWithMap(billMapData, isfkpc);
					this.updateBillMapYbye(billMapData, false);
					// 订单收款 手工挂上收款单
					if (billMapData == null || billMapData.size() == 0) {
						if (F2ForSoList != null && !F2ForSoList.isEmpty()) {
							// 订单收款 手工挂上一个 收款单 ，收款单不更新预占用余额，应收单更新
							this.doUnVerifyWithMap(F2ForSoList, isfkpc);
						}
					}
				}
				// 项目合同单据
				if (pcmList != null && !pcmList.isEmpty()) {
					this.doUnVerifyWithMap(pcmList, isfkpc);
					;
				}
				// 来自资金的付款排程单据
				if (psList != null && !psList.isEmpty()) {
					this.doUnVerifyWithMap(psList, Boolean.TRUE);
				}
				if (soList != null && !soList.isEmpty()) {
					// 订单-收款单不更新预占用余额，应收单更新
					this.doUnVerifyWithMap(soList, isfkpc);
				}
				//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				if (allList != null && !allList.isEmpty()) {
//					// 合同-收款单更新预占用余额
//					this.doUnVerifyWithMap(allList, isfkpc);
//				}
				if (htList != null && !htList.isEmpty()) {
					// 合同-收款单更新预占用余额
					this.doUnVerifyWithMap(htList, isfkpc);
				}
				//update end
				
				if (puList != null && !puList.isEmpty()) {
					this.doUnVerifyWithMap(puList, isfkpc);
				}

				if (bodypks.size() > 0) {
					Collection<ArapBillMapVO> billMapData = new HashSet<ArapBillMapVO>();
					for (String pk : bodypks) {
						ArapBillMapVO vo = new ArapBillMapVO();
						vo.setT_itemid(pk);
						billMapData.add(vo);
					}
					this.doUnVerifyWithMap(billMapData, isfkpc);
				}

				this.doDeleteRegisterByBillid(deldata.toArray(new String[] {}));
				//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip  不知道为什么有这段代码，先注释掉
//				doCheckBillDealAll(deldata);
				//del end
			} catch (Exception e1) {
				throw ExceptionHandler.handleException(e1);
			}
		}
	}
	public void doCheckBillDealAll(List<String> pkbillList) throws BusinessException {
		String[] pkbillArr = pkbillList.toArray(new String[] {});
		String insql = "";
		BaseDAO dao = new BaseDAO();
		insql = SqlUtils.getInStr("pk_bill", pkbillArr);
		String[] fields = new String[] { VerifyDetailVO.PK_BILL, VerifyDetailVO.BILLNO };
		// 查询核销数据
		Collection<VerifyDetailVO> verifydata = dao.retrieveByClause(VerifyDetailVO.class, insql, fields);
		StringBuffer billStr = new StringBuffer();
		Set<String> billSet = new java.util.HashSet<String>();
		if (verifydata != null && verifydata.size() > 0) {
			for (VerifyDetailVO vo : verifydata) {
				billSet.add(vo.getBillno());
			}
			for (String billno : billSet) {
				billStr.append(billno);
				billStr.append(",");
			}
			int lastIndexOf = billStr.lastIndexOf(",");
			String substring = billStr.substring(0, lastIndexOf);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0251")/*
																															 * @res
																															 * "单据"
																															 */+ substring + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0259")/*
																																																								 * @res
																																																								 * "已经进行核销，不能取消审核"
																																																								 */);

		}
		// 是否做过汇兑损益处理
		Collection<AgiotageChildVO> agiotagedata = dao.retrieveByClause(AgiotageChildVO.class, insql+"and not exists (select 1 from ARAP_VERIFYDETAIL where "+insql+" )", fields);
		if (agiotagedata != null && agiotagedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0260")/*
																															 * @res
																															 * "已经做过汇兑损益，不能取消操作"
																															 */);
		}
		// 是否做过并账处理
		Collection<DebtTransferVO> debtdata = dao.retrieveByClause(DebtTransferVO.class, insql, fields);
		if (debtdata != null && debtdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0261")/*
																															 * @res
																															 * "已经做过并账，不能取消操作"
																															 */);
		}
		// 是否做过坏账发生处理
		Collection<BaddebtsOcchVO> badLosedata = dao.retrieveByClause(BaddebtsOccuVO.class, insql, fields);
		if (badLosedata != null && badLosedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0262")/*
																															 * @res
																															 * "已经做过坏账发生，不能取消操作"
																															 */);
		}
		// 是否做过坏账收回处理
		Collection<BaddebtsRechVO> badBackdata = dao.retrieveByClause(BaddebtsReceVO.class, insql, fields);
		if (badBackdata != null && badBackdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0263")/*
																															 * @res
																															 * "已经做过坏账收回，不能取消操作"
																															 */);
		}
	}

	// 选中单据进行红蓝对冲   增加销售订单生成单据外部的红蓝对冲，按照src_itemid核销
	private void billRBVerify(AggregatedValueObject[] vos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException, MetaDataException {
		for (AggregatedValueObject vo : vos) {
			BaseBillVO parentVO = (BaseBillVO) vo.getParentVO();
			String primaryKey = parentVO.getPrimaryKey();
			// BaseItemVO[] itemVOs = (BaseItemVO[]) vo.getChildrenVO();
			// 销售订单外部红蓝对冲
			// List<String> srcItemList = new ArrayList<String>();
			// for(BaseItemVO itemVO:itemVOs){
			// if(!StringUtil.isEmpty(itemVO.getSrc_itemid())){
			// if(!srcItemList.contains(itemVO.getSrc_itemid())){
			// srcItemList.add(itemVO.getSrc_itemid());
			// }
			// }
			// }
			this.aggMap.clear();

			String topBilltype = ((BaseItemVO) vo.getChildrenVO()[0]).getTop_billtype();

			if (topBilltype != null
					&& (topBilltype.equals(ArapBillDealVOConsts.BILLTYPE_32) && parentVO.getPk_billtype().equals(IBillFieldGet.F0) || topBilltype.equals(ArapBillDealVOConsts.BILLTYPE_25)
							&& parentVO.getPk_billtype().equals(IBillFieldGet.F1) || topBilltype.equals(ArapBillDealVOConsts.BILLTYPE_30) && parentVO.getPk_billtype().equals(IBillFieldGet.F2))) {
				String whereCondStr = "";
				// if(srcItemList.size()>0){
				// whereCondStr =SqlUtils.getInStr(IBillFieldGet.SRC_ITEMID,
				// srcItemList.toArray(new String[0]));
				// }else{
				whereCondStr = " pk_bill ='" + primaryKey + "'";
				// }
				NCObject[] objectByCond = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, whereCondStr, false);

				List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
				List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
				// 区分本方 对方
				for (NCObject data : objectByCond) {
					ArapBusiDataVO busiDataVO = (ArapBusiDataVO) data.getContainmentObject();
					// 销售订单收款预占用
					if (busiDataVO.getTop_billtype() != null && busiDataVO.getPk_billtype().equals(IBillFieldGet.F2) && ArapBillDealVOConsts.BILLTYPE_30.equals(busiDataVO.getTop_billtype().trim())) {
						busiDataVO.setAttributeValue(ArapBusiDataVO.SETT_MONEY, busiDataVO.getMoney_bal().sub(busiDataVO.getOccupationmny()));
					} else {
						busiDataVO.setAttributeValue(ArapBusiDataVO.SETT_MONEY, busiDataVO.getOccupationmny());
					}
					if (busiDataVO.getDirection().intValue() == 1) {
						jfList.add(busiDataVO);
					} else {
						dfList.add(busiDataVO);
					}
				}
				this.aggMap = this.onVerify(com, ruleVOMap, jfList.toArray(new ArapBusiDataVO[0]), dfList.toArray(new ArapBusiDataVO[0]));
				ArrayList<AggverifyVO> aggVOList = this.getAggVerifyVO(this.aggMap);
				// 核销结果保存
				if (aggVOList != null && aggVOList.size() > 0) {
					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);

					// 重新查询单据表体的信息
					this.reQueryBillIteminfo(vo);
				}

				this.aggMap.clear();
			}

		}
	}

	private void reQueryBillIteminfo(AggregatedValueObject vo) throws BusinessException {
		CircularlyAccessibleValueObject[] childrenVO = vo.getChildrenVO();
		List<String> pks = new ArrayList<String>();
		for (CircularlyAccessibleValueObject s : childrenVO) {
			pks.add(s.getPrimaryKey());
		}

		if (vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(IBillFieldGet.F0)) {
			IArapReceivableBillPubQueryService service = NCLocator.getInstance().lookup(IArapReceivableBillPubQueryService.class);
			try {
				ReceivableBillItemVO[] items = service.queryRecieableBillItem(pks.toArray(new String[pks.size()]));
				vo.setChildrenVO(items);
			} catch (BusinessException e) {
				ExceptionHandler.handleException(e);
			}
		} else if (vo.getParentVO().getAttributeValue(IBillFieldGet.PK_BILLTYPE).equals(IBillFieldGet.F1)) {
			try {
				Collection<PayableBillItemVO> bills = MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(PayableBillItemVO.class, pks.toArray(new String[pks.size()]), false);
				PayableBillItemVO[] items = bills.toArray(new PayableBillItemVO[] {});
				vo.setChildrenVO(items);
			} catch (BusinessException e) {
				ExceptionHandler.handleException(e);
			}
		}
	}

	protected AggregatedValueObject[] dealUserObjForInit(Object obj) {

		AggregatedValueObject[] retvos = null;
		if (obj.getClass().isArray()) {
			retvos = (AggregatedValueObject[]) obj;
		} else {
			retvos = new AggregatedValueObject[] { (AggregatedValueObject) obj };
		}
		this.pk_user = (String) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.APPROVER);
		this.syscode = (Integer) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.SYSCODE);
		this.pkorg = (String) retvos[0].getParentVO().getAttributeValue(ArapBusiDataVO.PK_ORG);
		return retvos;
	}

	@SuppressWarnings("unused")
	private void VerifyByContractNo(AggregatedValueObject[] vos, VerifyCom com, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap) throws BusinessException {
		//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//		List<ArapBusiDataVO> dataList = new ArrayList<ArapBusiDataVO>();
//		List<ArapBusiDataVO> jfdataList = new ArrayList<ArapBusiDataVO>();
//		List<ArapBusiDataVO> dfdataList = new ArrayList<ArapBusiDataVO>();
//		ArrayList<AggverifyVO> aggVOList = new ArrayList<AggverifyVO>();
//		for (AggregatedValueObject aggvo : vos) {
//			BaseItemVO[] childrenVO = (BaseItemVO[]) aggvo.getChildrenVO();
//			// 流程中包含 销售合同或者采购合同，才需要核销
//			for (BaseItemVO childvo : childrenVO) {
//				this.aggMap.clear();
//				String pk_item = childvo.getPrimaryKey();
//				Object contractno = childvo.getAttributeValue(IBillFieldGet.CONTRACTNO);
//				String currtype = (String) childvo.getAttributeValue(IBillFieldGet.PK_CURRTYPE);
//				String pk_billtype = (String) childvo.getAttributeValue(IBillFieldGet.PK_BILLTYPE);
//				String pk_org = (String) childvo.getAttributeValue(IBillFieldGet.PK_ORG);
//				// 合同号为null 继续
//				if (contractno == null) {
//					continue;
//				}
//
//				// 查询要核销的数据
//				String qrySql = " pk_item ='" + pk_item + "' or (pk_org ='" + pk_org + "'" + " and pausetransact ='N' " + " and pk_currtype ='" + currtype + "'" + "and pk_billtype !='" + pk_billtype
//						+ "' and contractno ='" + contractno + "' and pk_item <> '" + pk_item + "' and money_bal<> 0)";
//				NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, qrySql, false);
//				if (!ArrayUtils.isEmpty(ncObjects)) {
//					for (NCObject obj : ncObjects) {
//						ArapBusiDataVO dataVO = (ArapBusiDataVO) obj.getContainmentObject();
//						dataList.add(dataVO);
//					}
//					// 借贷方分组
//					for (ArapBusiDataVO datavo : dataList) {
//						if (datavo.getPk_billtype().equals(IBillFieldGet.F2) && ArapBillDealVOConsts.BILLTYPE_30.equals(datavo.getTop_billtype())) {
//							datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, datavo.getMoney_bal().sub(datavo.getOccupationmny()));
//						} else {
//							datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, datavo.getOccupationmny());
//						}
//						if (datavo.getDirection().intValue() == 1) {
//							jfdataList.add(datavo);
//						} else {
//							dfdataList.add(datavo);
//						}
//					}
//					Map<String, ArrayList<AggverifyVO>> aggMap = this.onVerify(com, ruleVOMap, jfdataList.toArray(new ArapBusiDataVO[0]), dfdataList.toArray(new ArapBusiDataVO[0]));
//					// 核销结果保存
//					aggVOList = this.getAggVerifyVO(aggMap);
//					if (aggVOList != null && aggVOList.size() > 0) {
//						NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
//					}
//				}
//			}
//		}
		//del end
	}

	private void VerifyByContractNo(List<ArapBillMapVO> MapVOList, VerifyCom com, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap) throws BusinessException {
		//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip 
//		com.setReserveFlag(VerifyDetailVO.reserve_ht);
//		for(DefaultVerifyRuleVO rule:ruleVOMap.values()){
//			rule.setM_creditObjKeys(new String[]{ArapBusiDataVO.SUPPLIER});
//			rule.setM_debtObjKeys(new String[]{ArapBusiDataVO.SUPPLIER});
//		}
//		for (ArapBillMapVO vo : MapVOList) {
//			Map<String, String> busiDataTsMap = new HashMap<String, String>();
//			List<ArapBusiDataVO> dataList = new ArrayList<ArapBusiDataVO>();
//			List<ArapBusiDataVO> jfdataList = new ArrayList<ArapBusiDataVO>();
//			List<ArapBusiDataVO> dfdataList = new ArrayList<ArapBusiDataVO>();
//			this.aggMap.clear();
//			String currtype = vo.getPk_currtype();
//			String pk_item = vo.getT_itemid();
//			String contractno = vo.getContractno();
//			String pk_org = vo.getPk_org();
//			if (contractno == null) {
//				continue;
//			}
//			String billType ="";
//			if(IBillFieldGet.F0.equals(vo.getT_billtype())){
//				billType = "('"+IBillFieldGet.F2+"','"+IBillFieldGet.F1+"')";
//			}else if(IBillFieldGet.F1.equals(vo.getT_billtype())){
//				billType = "('"+IBillFieldGet.F3+"','"+IBillFieldGet.F1+"','"+IBillFieldGet.F0+"')";
//			}else if(IBillFieldGet.F2.equals(vo.getT_billtype())){
//				billType = "('"+IBillFieldGet.F0+"')";
//			} else if(IBillFieldGet.F3.equals(vo.getT_billtype())){
//				billType = "('"+IBillFieldGet.F1+"')";
//			}  
//			// 查询要核销的数据
//			String qrySql = " (money_bal <> 0 and pk_item ='" + pk_item + "') or (pk_org ='" + pk_org + "'" + " and pausetransact ='N' " + " and pk_currtype ='" + currtype + "'" 
//			+ "and pk_billtype in " + billType + " and contractno ='"
//					+ contractno + "' and pk_item <> '" + pk_item + "' and money_bal<> 0)";
//			NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, qrySql, false);
//			if (!ArrayUtils.isEmpty(ncObjects)) {
//				for (NCObject obj : ncObjects) {
//					ArapBusiDataVO dataVO = (ArapBusiDataVO) obj.getContainmentObject();
//					dataList.add(dataVO);
//					// 比较ts 使用
//					busiDataTsMap.put(dataVO.getPk_busidata(), dataVO.getTs().toString());
//					com.setBusiDataTsMap(busiDataTsMap);
//				}
//				// 借贷方分组
//				for (ArapBusiDataVO datavo : dataList) {
//					datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, datavo.getOccupationmny());
//					
//					if(datavo.getCustomer()!=null && datavo.getSupplier()==null){
//						datavo.setSupplier(datavo.getCustomer());
//					}
//					if (datavo.getDirection().intValue() == 1) {
//						jfdataList.add(datavo);
//					} else {
//						dfdataList.add(datavo);
//					}
//				}
//				Map<String, ArrayList<AggverifyVO>> aggMap = this.onVerify(com, ruleVOMap, jfdataList.toArray(new ArapBusiDataVO[0]), dfdataList.toArray(new ArapBusiDataVO[0]));
//				ArrayList<AggverifyVO> aggVOList = this.getAggVerifyVO(aggMap);
//				// 核销结果保存
//				if (aggVOList != null && aggVOList.size() > 0) {
//					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
//				}
//			}
//		}
		//del end

	}

	// 核销后 ，billmap表 ybye字段赋值为0
	private void updateBillMapYbye(Collection<ArapBillMapVO> billMapData, boolean setZero) throws DAOException {
		String[] fild = new String[] { "ybye" };
		if (billMapData != null && billMapData.size() > 0) {
			if (setZero) {
				for (ArapBillMapVO vo : billMapData) {
					vo.setYbye(UFDouble.ZERO_DBL);
				}
			} else {
				for (ArapBillMapVO vo : billMapData) {
					vo.setYbye(vo.getYbje());
				}

			}
			billMapData.toArray(new SuperVO[0]);
			new BaseDAO().updateVOArray(billMapData.toArray(new SuperVO[0]), fild);
		}

	}

	public void doCheckBillDeal(List<String> pkbillList) throws BusinessException {
		String[] pkbillArr = pkbillList.toArray(new String[] {});
		String insql = "";
		BaseDAO dao = new BaseDAO();
		insql = SqlUtils.getInStr("pk_bill", pkbillArr, true);
		String[] fields = new String[] { VerifyDetailVO.PK_BILL, VerifyDetailVO.BILLNO };
		// 查询核销数据
		Collection<VerifyDetailVO> verifydata = dao.retrieveByClause(VerifyDetailVO.class, insql + " and isauto='N'", fields);
		StringBuffer billStr = new StringBuffer();
		Set<String> billSet = new java.util.HashSet<String>();
		if (verifydata != null && verifydata.size() > 0) {
			for (VerifyDetailVO vo : verifydata) {
				billSet.add(vo.getBillno());
			}
			for (String billno : billSet) {
				billStr.append(billno);
				billStr.append(",");
			}
			int lastIndexOf = billStr.lastIndexOf(",");
			String substring = billStr.substring(0, lastIndexOf);
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0251")/*
																															 * @
																															 * res
																															 * "单据"
																															 */
					+ substring + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0259")/*
																													 * @
																													 * res
																													 * "已经进行核销，不能取消审核"
																													 */);

		}
		// 是否做过汇兑损益处理
		Collection<AgiotageChildVO> agiotagedata = dao.retrieveByClause(AgiotageChildVO.class, insql + "and not exists (select 1 from ARAP_VERIFYDETAIL where " + insql + " )", fields);
		if (agiotagedata != null && agiotagedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0260")/*
																															 * @
																															 * res
																															 * "已经做过汇兑损益，不能取消操作"
																															 */);
		}
		// 是否做过并账处理
		Collection<DebtTransferVO> debtdata = dao.retrieveByClause(DebtTransferVO.class, insql, fields);
		if (debtdata != null && debtdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0261")/*
																															 * @
																															 * res
																															 * "已经做过并账，不能取消操作"
																															 */);
		}
		// 是否做过坏账发生处理
		Collection<BaddebtsOcchVO> badLosedata = dao.retrieveByClause(BaddebtsOccuVO.class, insql, fields);
		if (badLosedata != null && badLosedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0262")/*
																															 * @
																															 * res
																															 * "已经做过坏账发生，不能取消操作"
																															 */);
		}
		// 是否做过坏账收回处理
		Collection<BaddebtsRechVO> badBackdata = dao.retrieveByClause(BaddebtsReceVO.class, insql, fields);
		if (badBackdata != null && badBackdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0263")/*
																															 * @
																															 * res
																															 * "已经做过坏账收回，不能取消操作"
																															 */);
		}

	}

	protected Object[] transform(AggregatedValueObject[] vos) throws BusinessException {
		List<SuperVO> insertvos = new ArrayList<SuperVO>();
		List<SuperVO> updatevos = new ArrayList<SuperVO>();
		List<String> delvos = new ArrayList<String>();
		List<String> billPKList = new ArrayList<String>();

		Object[] ret = new Object[] { insertvos, updatevos, delvos };
		// 根据单据主键查询协议表
		for (AggregatedValueObject aggvo : vos) {
			billPKList.add(aggvo.getParentVO().getPrimaryKey());
		}
		TermVO[] termVOs = NCLocator.getInstance().lookup(IArapTermItemPubQueryService.class).queryTermVOByBillParentPK(billPKList.toArray(new String[0]));
		this.termVOMap = this.getTermVOByItemPK(termVOs);
		
		//add chenth 20161212 支持手续费核销
		//add by weiningc 20171012 633适配至65 end
		List<String> recpaytypePks =new ArrayList<String>();
		for(AggregatedValueObject vo: vos){
			for(CircularlyAccessibleValueObject cvo:vo.getChildrenVO()){
				recpaytypePks.add((String)cvo.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE));
			}
		}
		if(recpaytypePks.size() > 0){
			Collection<RecpaytypeVO> typevos = new BaseDAO().retrieveByClause(RecpaytypeVO.class, SqlUtils.getInStr("pk_recpaytype", recpaytypePks.toArray(new String[]{})), new String[]{RecpaytypeVO.PK_RECPAYTYPE, RecpaytypeVO.ISBANKCHARGES});
			for(RecpaytypeVO typevo : typevos){
				recpaytypeMap.put(typevo.getPk_recpaytype(), typevo);
			}
		}
		//add end
		//add by weiningc 20171012 633适配至65 end
		for (AggregatedValueObject vo : vos) {
			SuperVO[] items = (SuperVO[]) vo.getChildrenVO();
			for (SuperVO item : items) {
				switch (item.getStatus()) {
				case VOStatus.NEW:
					List<ArapBusiDataVO> transform = this.transform(item, (SuperVO) vo.getParentVO());
					insertvos.addAll(transform);
					break;
				case VOStatus.DELETED:
					delvos.add(item.getPrimaryKey());
				default:
					List<ArapBusiDataVO> busidataList = this.transform(item, (SuperVO) vo.getParentVO());
					updatevos.addAll(busidataList);
					break;
				}
			}
		}

		ArapMaterialUtils.translateMaterial2NonVersion(insertvos.toArray(new ArapBusiDataVO[] {}), ArapBusiDataVO.MATERIAL, IBillFieldGet.MATERIAL_V);
		ArapMaterialUtils.translateMaterial2NonVersion(updatevos.toArray(new ArapBusiDataVO[] {}), ArapBusiDataVO.MATERIAL, IBillFieldGet.MATERIAL_V);

		return ret;
	}

	private Map<String, List<TermVO>> getTermVOByItemPK(TermVO[] termVOs) {
		Map<String, List<TermVO>> termMap = new HashMap<String, List<TermVO>>();
		for (TermVO vo : termVOs) {
			if (termMap.containsKey(vo.getPk_item())) {
				termMap.get(vo.getPk_item()).add(vo);
			} else {
				List<TermVO> temp = new ArrayList<TermVO>();
				temp.add(vo);
				termMap.put(vo.getPk_item(), temp);
			}
		}
		return termMap;
	}

	// 设置审批时，表头不会同步到表体的字段，按照表体来取值
	static String[] itemKeys = { IBillFieldGet.CUSTOMER, IBillFieldGet.SUPPLIER, IBillFieldGet.PK_DEPTID, IBillFieldGet.PK_DEPTID_V, IBillFieldGet.PK_PSNDOC };

	protected List<ArapBusiDataVO> transform(SuperVO item, SuperVO head) throws BusinessException {
		ArapBusiDataVO retvo = new ArapBusiDataVO();
		
		//add chenth 20161212 支持手续费核销
		//add by weiningc 20171012 633适配至65 end
		RecpaytypeVO recPayTypeVO = recpaytypeMap.get(item.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE));
		if(recPayTypeVO != null ){
			retvo.setIsbankcharges(recPayTypeVO.getIsbankcharges());
		}
		//add end
		//add by weiningc 20171012 633适配至65 end
		
		String[] attrs = retvo.getAttributeNames();
		for (String attr : attrs) {
			Object attributeValue = item.getAttributeValue(attr);
			if (attributeValue == null && !Arrays.asList(BillRegisterForBusiDataBO.itemKeys).contains(attr)) {
				attributeValue = head.getAttributeValue(attr);
			}
			retvo.setAttributeValue(attr, attributeValue);
			// 收支项目
			if (attr.equals(ArapBusiDataVO.PK_COSTSUBJ)) {
				retvo.setAttributeValue(ArapBusiDataVO.PK_COSTSUBJ, item.getAttributeValue(IBillFieldGet.PK_SUBJCODE));
			}
			// 含税单价赋值
			if (attr.equals(ArapBusiDataVO.PRICE)) {
				retvo.setAttributeValue(ArapBusiDataVO.PRICE, item.getAttributeValue(IBillFieldGet.TAXPRICE));
			}
			// 利润中心
			if (attr.equals(ArapBusiDataVO.PK_PCORG)) {
				retvo.setAttributeValue(ArapBusiDataVO.PK_PCORG, item.getAttributeValue(IBillFieldGet.PK_PCORG));
			}
			// 金额字段为空赋默认值0
			if (attributeValue == null) {
				if (attr.equals(ArapBusiDataVO.GLOBAL_MONEY_BAL) || attr.equals(ArapBusiDataVO.GROUP_MONEY_BAL) || attr.equals(ArapBusiDataVO.GLOBAL_MONEY_DE)
						|| attr.equals(ArapBusiDataVO.GLOBAL_MONEY_CR) || attr.equals(ArapBusiDataVO.GROUP_MONEY_DE) || attr.equals(ArapBusiDataVO.GROUP_MONEY_CR)) {
					retvo.setAttributeValue(attr, UFDouble.ZERO_DBL);
				}
			}
		}
		retvo.setPk_bill(head.getPrimaryKey());
		retvo.setPk_item(item.getPrimaryKey());
		// 单据表头原币金额
		retvo.setSum_headmoney((UFDouble) head.getAttributeValue(IBillFieldGet.MONEY));
		// 单据表体原币金额
		String billcalss = (String) item.getAttributeValue(IBillFieldGet.BILLCLASS);
		if (billcalss.equals(IBillFieldGet.YS) || billcalss.equals(IBillFieldGet.FK)) {
			retvo.setSum_itemmoney((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE));
		} else {
			retvo.setSum_itemmoney((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR));
		}
		// 单据状态
		retvo.setBillstatus(Integer.valueOf(1)); // 默认已生效 1，挂起 订单挂起6
		// 表头科目
		retvo.setHeadsubjcode((String) head.getAttributeValue("subjcode"));
		// 业务流程
		retvo.setPk_busitype((String) head.getAttributeValue(ArapBusiDataVO.PK_BUSITYPE));
		retvo.setOperator((String) head.getAttributeValue(IBillFieldGet.BILLMAKER));
		// 利润中心 取表体的
		// retvo.setPk_pcorg((String)head.getAttributeValue(ArapBusiDataVO.PK_PCORG));
		// 单据编号
		retvo.setBillno((String) head.getAttributeValue(ArapBusiDataVO.BILLNO));
		// 暂估标志
		retvo.setEstflag(head.getAttributeValue(ArapBusiDataVO.ESTFLAG) == null ? Integer.valueOf(0) : (Integer) head.getAttributeValue(ArapBusiDataVO.ESTFLAG));
		// 查询收付款协议表数据
		List<ArapBusiDataVO> busiDataList = this.queryTermVOByBillParentPK(retvo);
		// setAreaPKForBusiData(busiDataList);
		return busiDataList;
	}

	private void setAreaPKForBusiData(List<ArapBusiDataVO> busiDataList) {
		if (busiDataList.get(0).getObjtype().intValue() == 0) {
			String[] custPKS = this.getCustOrSuppPKS(busiDataList, true);
			try {
				CustomerVO[] customerVOs = NCLocator.getInstance().lookup(ICustomerPubService.class).getCustomerVO(custPKS, new String[] { "pk_areacl", "pk_customer" });
				Map<String, CustomerVO> custMap = new HashMap<String, CustomerVO>();
				if (!ArrayUtils.isEmpty(customerVOs)) {
					{
						for (CustomerVO cust : customerVOs) {
							if (cust != null) {
								custMap.put(cust.getPk_customer(), cust);
							}
						}
					}
				}
				// 客户地区pk 赋值
				for (ArapBusiDataVO data : busiDataList) {
					if (custMap.get(data.getCustomer()) != null) {
						data.setPk_areacl(custMap.get(data.getCustomer()).getPk_areacl());
					}
				}

			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}
		} else if (busiDataList.get(0).getObjtype().intValue() == 1) {
			String[] suppPKS = this.getCustOrSuppPKS(busiDataList, false);
			try {
				SupplierVO[] supplierVOs = NCLocator.getInstance().lookup(ISupplierPubService.class).getSupplierVO(suppPKS, new String[] { "pk_areacl", "pk_supplier" });
				Map<String, SupplierVO> suppMap = new HashMap<String, SupplierVO>();
				if (!ArrayUtils.isEmpty(supplierVOs)) {
					for (SupplierVO cust : supplierVOs) {
						if (cust != null) {
							suppMap.put(cust.getPk_supplier(), cust);
						}
					}
				}
				// 供应商地区pk 赋值
				for (ArapBusiDataVO data : busiDataList) {
					if (suppMap.get(data.getSupplier()) != null) {
						data.setPk_areacl(suppMap.get(data.getSupplier()).getPk_areacl());
					}
				}

			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}
		}
	}

	private String[] getCustOrSuppPKS(List<ArapBusiDataVO> vos, boolean iscust) {
		Set<String> pkS = new HashSet<String>();
		for (ArapBusiDataVO vo : vos) {
			if (iscust) {
				pkS.add(vo.getCustomer());
			} else {
				pkS.add(vo.getSupplier());
			}
		}
		String[] pkArr = pkS.toArray(new String[0]);
		return pkArr;
	}

	private List<ArapBusiDataVO> queryTermVOByBillParentPK(ArapBusiDataVO busidataVO) throws BusinessException {
		// Logger.debug("单据子表pk==" + busidataVO.getPk_item());
		// TermVO[] termVOs =
		// NCLocator.getInstance().lookup(IArapTermItemPubQueryService.class).queryTermVOByBillItemPK(new
		// String[]{busidataVO.getPk_item()});
		// Logger.debug("查询单据收付款协议表数量=" + (ArrayUtils.isEmpty(termVOs) ? 0 :
		// termVOs.length));
		// TermVO[] termVOs = termlist.toArray(new TermVO[0]);
		List<TermVO> termlist = this.termVOMap.get(busidataVO.getPk_item());
		List<ArapBusiDataVO> busiDataList = new ArrayList<ArapBusiDataVO>();
		if (termlist != null && termlist.size() > 0) {
			for (TermVO termVO : termlist) {
				// 如果协议表有多条记录，则在ArapBusiDataVO与其数量相等
				ArapBusiDataVO copyVO = (ArapBusiDataVO) busidataVO.clone();
				copyVO.setExpiredate(termVO.getExpiredate());
				// 内控到期日
				copyVO.setInnerctldeferdays(termVO.getInnerctldeferdays());
				copyVO.setInsurance(termVO.getInsurance());
				copyVO.setPk_termitem(termVO.getPk_termitem());
				// 借方原币金额
				copyVO.setMoney_de(termVO.getMoney_de() == null ? UFDouble.ZERO_DBL : termVO.getMoney_de());
				// 借方本币金额
				copyVO.setLocal_money_de(termVO.getLocal_money_de() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_de());
				// 贷方原币金额
				copyVO.setMoney_cr(termVO.getMoney_cr() == null ? UFDouble.ZERO_DBL : termVO.getMoney_cr());
				// 贷方本币金额
				copyVO.setLocal_money_cr(termVO.getLocal_money_cr() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_cr());
				// 原币余额
				copyVO.setMoney_bal(termVO.getMoney_bal() == null ? UFDouble.ZERO_DBL : termVO.getMoney_bal());
				// 本币余额
				copyVO.setLocal_money_bal(termVO.getLocal_money_bal() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_bal());
				// 集团借方本币金额
				copyVO.setGroup_money_de(termVO.getGroupdebit() == null ? UFDouble.ZERO_DBL : termVO.getGroupdebit());
				// 集团贷方本币金额
				copyVO.setGroup_money_cr(termVO.getGroupcredit() == null ? UFDouble.ZERO_DBL : termVO.getGroupcredit());
				// 集团本币余额
				copyVO.setGroup_money_bal(termVO.getGroupbalance() == null ? UFDouble.ZERO_DBL : termVO.getGroupbalance());
				// 全局借方本币金额
				copyVO.setGlobal_money_de(termVO.getGlobaldebit() == null ? UFDouble.ZERO_DBL : termVO.getGlobaldebit());
				// 全局贷方本币金额
				copyVO.setGlobal_money_cr(termVO.getGlobalcredit() == null ? UFDouble.ZERO_DBL : termVO.getGlobalcredit());
				// 全局本币余额
				copyVO.setGlobal_money_bal(termVO.getGlobalbalance() == null ? UFDouble.ZERO_DBL : termVO.getGlobalbalance());
				// 借方数量
				copyVO.setQuantity_de(termVO.getQuantity_de() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_de());
				// 贷方数量
				copyVO.setQuantity_cr(termVO.getQuantity_cr() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_cr());
				// 数量余额
				copyVO.setQuantity_bal(termVO.getQuantity_bal() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_bal());
				// 预占余额
				copyVO.setOccupationmny(termVO.getOccupationmny());//
				copyVO.setIstransin(UFBoolean.valueOf(false));// 默认非转入，转出N，转入Y
				copyVO.setPrepay(termVO.getPrepay() != null && termVO.getPrepay().booleanValue() ? 1 : 0);
				busiDataList.add(copyVO);
			}
		}
		return busiDataList;
	}

	protected void doInsertRegister(List<ArapBusiDataVO> insertdatas) throws BusinessException {
		Logger.debug("插入arap_busidata 表  数量 =" + insertdatas.size());
		new BaseDAO().insertVOList(insertdatas);
	}

	protected void doUpdateRegister(List<ArapBusiDataVO> updatedatas) throws BusinessException {
		new BaseDAO().updateVOList(updatedatas);
	}

	protected void doDeleteRegisterByBillid(String[] billids) throws BusinessException {
		// 业务数据加锁
		if (!PKLock.getInstance().addBatchDynamicLock(billids)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0257")/*
																															 * @
																															 * res
																															 * "并发错误"
																															 */);
		}
		new BaseDAO().deleteByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_bill", billids, true));
	}

	protected void doDeleteRegisterByItemid(String[] itemids) throws BusinessException {
		new BaseDAO().deleteByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", itemids, true));
	}

	// 反核销
	private void doUnVerifyWithMap(Collection<ArapBillMapVO> mapvos, Boolean isFkpc) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}
		Map<String, String> businoMap = new HashMap<String, String>();

		List<String> pks = new ArrayList<String>();
		for (ArapBillMapVO vo : mapvos) {
			pks.add(vo.getT_itemid());
		}
		// 核销子表
		Collection<VerifyDetailVO> detailColet = new BaseDAO().retrieveByClause(VerifyDetailVO.class, " isauto='Y' and " + SqlUtils.getInStr("pk_item", pks.toArray(new String[] {}), true));
		for (VerifyDetailVO detailvo : detailColet) {
			if (businoMap.containsKey(detailvo.getBusino())) {
				businoMap.put(detailvo.getBusino(), businoMap.get(detailvo.getBusino()));
			} else {
				businoMap.put(detailvo.getBusino(), detailvo.getBusino());
			}
		}
		if (businoMap.size() == 0) {
			return;
		}
		// 组织核销聚合VO
		Set<String> keyset = businoMap.keySet();
		keyset.toArray(new String[keyset.size()]);
		NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).onCancelVerify(keyset.toArray(new String[keyset.size()]), isFkpc);
	}

	// 核销

	private void doVerifyWithMap(Collection<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com, String nouse) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}

		// 根据参数确定是否要按照预收付标志核销
		if (VerifyDetailVO.reserve_so.equals(com.getReserveFlag())) {
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AR35);
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
		} else if (VerifyDetailVO.reserve_pu.equals(com.getReserveFlag())) {
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
		} else if (VerifyDetailVO.reserve_pcm.equals(com.getReserveFlag())) {
			//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//			if (mapvos.iterator().next().getS_system() == 1) {// 应付
//				UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);// 应付
			if (mapvos.iterator().next().getS_system() == 0) {// 应收
				isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AR35);
			}
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
//			}
		}

		this.aggMap.clear();
		List<String> pks = new ArrayList<String>();
		List<String> termPkList = new ArrayList<String>();
		List<String> itemPkList = new ArrayList<String>();
		Collection<ArapBusiDataVO> busiList = null;
		ArapBillMapVO[] mapVOs2 = mapvos.toArray(new ArapBillMapVO[0]);
		// 调试是 注释 再打开
		Map<String, String> item_top_termMap = new HashMap<String, String>();
		for (ArapBillMapVO vo : mapvos) {
			// 根据系统来源判断 是否来自付款申请
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype()) || ArapBillDealVOConsts.BILLTYPE_36D7.equals(vo.getS_billtype())) {
				// 判断 只有资金的付款排成 才可以按协议行核销
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
				item_top_termMap.put(vo.getS_itemid(), vo.getS_termid());
			} else {
				if (vo.getS_itemid() == null) { // 源头id 为空 ，没有数据不用核销
				} else {
					pks.add(vo.getS_itemid());
					pks.add(vo.getT_itemid());
				}
			}
		}
		if (termPkList != null && termPkList.size() > 0) {
			String sql = SqlUtils.getInStr("pk_termitem", termPkList.toArray(new String[] {}), true) + " and pausetransact ='N' " + " or "
					+ SqlUtils.getInStr("pk_item", itemPkList.toArray(new String[] {}), true) + " and pausetransact ='N' ";
			busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, sql);
			if (busiList != null) {
				for (ArapBusiDataVO b : busiList) {
					String pk_item = b.getPk_item();
					if (item_top_termMap.containsKey(pk_item)) {
						b.setDef1(item_top_termMap.get(pk_item));
					} else {
						b.setDef1(b.getPk_termitem());
					}

				}
			}
		} else {
			if (pks != null && pks.size() > 0) {
				busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", pks.toArray(new String[] {}), true) + " and pk_org='"
						+ ruleVOMap.values().iterator().next().getPk_org() + "' and pausetransact ='N' " + " and money_bal <> 0  " + " and pk_currtype ='" + mapVOs2[0].getPk_currtype()

						+ "'");
			}
		}
		if (busiList == null || busiList.size() == 0) {
			return;
		}
		// 比较ts 使用
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);
		List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
		// 区分本方 对方
		for (ArapBusiDataVO data : busiList) {
			boolean isjf = false;
			for (ArapBillMapVO mpvo : mapVOs2) {
				if (data.getPk_item().equals(mpvo.getT_itemid())) {
					isjf = true;
				}
			}
			if (isjf) {
				jfList.add(data);
			} else {
				dfList.add(data);
			}
		}
		// 排序
		Integer flag = ruleVOMap.get("SAME_VERIFY").getM_verifySeq();
		ArapBusiDataVO[] jfVos = jfList.toArray(new ArapBusiDataVO[0]);
		ArapBusiDataVO[] dfVos = dfList.toArray(new ArapBusiDataVO[0]);
		VerifyTool.sortVector(jfVos, flag.intValue());
		VerifyTool.sortVector(dfVos, flag.intValue());

		Map<String, List<ArapBusiDataVO>> dfBusidataMap = getBusidataMap(dfVos);

		UFDouble clje = UFDouble.ZERO_DBL;
		UFDouble jf_clje = UFDouble.ZERO_DBL;
		for (ArapBillMapVO vo : mapVOs2) {
			clje = clje.add(vo.getYbje());
		}
		jf_clje = clje;
		// 分配金额
		if (mapVOs2[0].getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D1) || mapVOs2[0].getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D7)) {
			for (ArapBillMapVO vo : mapvos) {
				if (vo.getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D1) || vo.getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D7)) {
					// 付款申请，s_item =null,用s_termitem
					// UFDouble clje1 = vo.getYbje();
					if (dfList != null && dfList.size() > 0) {
						for (ArapBusiDataVO datavo : dfVos) {
							UFDouble occupationmny = datavo.getOccupationmny();
							UFDouble money_bal = datavo.getMoney_bal();
							occupationmny = money_bal.sub(occupationmny);
							// if (clje1.compareTo(occupationmny) > 0) {
							// datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY,
							// occupationmny);
							// clje1 = clje.sub(occupationmny);
							// } else {
							// clje1 = UFDouble.ZERO_DBL;
							// }
							datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, occupationmny);
						}
					}
				}
			}
		} else {
			// 排序后 分配金额
			if (dfList != null && dfList.size() > 0) {
				for (String pk_item : dfBusidataMap.keySet()) {
					List<ArapBusiDataVO> list = dfBusidataMap.get(pk_item);
					UFDouble verifyMoney = UFDouble.ZERO_DBL;
					for (ArapBillMapVO mapvo : mapvos) {
						if (pk_item.equals(mapvo.getS_itemid())) {
							verifyMoney = mapvo.getYbje();
							break;
						}
					}
					for (ArapBusiDataVO datavo : list) {
						UFDouble occupationmny = datavo.getOccupationmny();
						UFDouble money_bal = datavo.getMoney_bal();
						if (datavo.getPk_billtype().equals(IBillFieldGet.F2)) {
							// 有销售订单收款预核销 占用
							if (verifyMoney.compareTo(money_bal.sub(occupationmny)) > 0) {
								datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, money_bal.sub(occupationmny));
								verifyMoney = verifyMoney.sub(money_bal.sub(occupationmny));
							} else {
								datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, verifyMoney);
								verifyMoney = UFDouble.ZERO_DBL;
							}
						} else {
							if (verifyMoney.compareTo(occupationmny) > 0) {
								datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, occupationmny);
								verifyMoney = verifyMoney.sub(occupationmny);
							} else {
								datavo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, verifyMoney);
								verifyMoney = UFDouble.ZERO_DBL;
							}
						}
					}
				}
			}
		}
		for (ArapBusiDataVO jfvo : jfVos) {
			UFDouble occupationmny = jfvo.getOccupationmny();
			UFDouble money_bal = jfvo.getMoney_bal();
			if (jfvo.getPk_billtype().equals(IBillFieldGet.F2)) {
				// 有销售订单收款预核销 占用
				if (jf_clje.compareTo(money_bal.sub(occupationmny)) > 0) {
					jfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, money_bal.sub(occupationmny));
					jf_clje = jf_clje.sub(money_bal.sub(occupationmny));
				} else {
					jfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, jf_clje);
					jf_clje = UFDouble.ZERO_DBL;
				}
			} else {
				if (jf_clje.compareTo(occupationmny) > 0 || jf_clje.compareTo(occupationmny) < 0) {
					jfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, occupationmny);
					jf_clje = jf_clje.sub(occupationmny);
				} else {
					jfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, jf_clje);
					jf_clje = UFDouble.ZERO_DBL;
				}
			}
		}
		// for (ArapBusiDataVO jfvo : jfVos) {
		// UFDouble occupationmny = jfvo.getOccupationmny();
		// jfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, occupationmny);
		// }
		// for (ArapBusiDataVO dfvo : dfVos) {
		// UFDouble occupationmny = dfvo.getOccupationmny();
		// dfvo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, occupationmny);
		// }
		List<ArapBusiDataVO> jf = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> df = new ArrayList<ArapBusiDataVO>();

		for (ArapBusiDataVO jfvo : jfVos) {
			if (jfvo.getDirection().intValue() == BillEnumCollection.Direction.DEBIT.VALUE.intValue()) {
				jf.add(jfvo);
			} else {
				df.add(jfvo);
			}
		}
		for (ArapBusiDataVO jfvo : dfVos) {
			if (jfvo.getDirection().intValue() == BillEnumCollection.Direction.DEBIT.VALUE.intValue()) {
				jf.add(jfvo);
			} else {
				df.add(jfvo);
			}
		}

		aggMap = onVerify(com, ruleVOMap, jf.toArray(new ArapBusiDataVO[] {}), df.toArray(new ArapBusiDataVO[] {}));
		ArrayList<AggverifyVO> aggVOList = getAggVerifyVO(aggMap);
		// 核销结果保存
		if (aggVOList != null && aggVOList.size() > 0) {
			NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
		}

	}

	private Map<String, List<ArapBusiDataVO>> getBusidataMap(ArapBusiDataVO[] dfVos) {
		Map<String, List<ArapBusiDataVO>> dataMap = new HashMap<String, List<ArapBusiDataVO>>();
		for (ArapBusiDataVO vo : dfVos) {
			if (dataMap.containsKey(vo.getPk_item())) {
				dataMap.get(vo.getPk_item()).add(vo);
			} else {
				List<ArapBusiDataVO> tempList = new ArrayList<ArapBusiDataVO>();
				tempList.add(vo);
				dataMap.put(vo.getPk_item(), tempList);
			}
		}

		return dataMap;
	}

	public static String SAMVERIFYFLAG = "SAMVERIFYFLAG"; // 同币种核销

	public static String UNVERIFYFLAG = "UNVERIFYFLAG"; // 异币种核销

	public static String HCSAMVERIFYFLAG = "HCSAMVERIFYFLAG"; // 同币种红冲核销

	public static String HCUNVERIFYFLAG = "HCUNVERIFYFLAG"; // 异币种红冲核销

	/**
	 * 收付内部拉单核销
	 * 
	 * @param mapvos
	 * @param ruleVOMap
	 * @param com
	 * @throws BusinessException
	 */
	private void doArapVerifyWithMap(Collection<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException {
		// 效率问题，改为批量处理
		if (mapvos != null && mapvos.size() > 0) {
			List<ArapBillMapVO> billMapDataArap = new ArrayList<ArapBillMapVO>();
			List<ArapBillMapVO> billMapDataUnRb = new ArrayList<ArapBillMapVO>();
			Map<String, List<ArapBillMapVO>> billMapDataUnSam = new HashMap<String, List<ArapBillMapVO>>();
			for (ArapBillMapVO vo : mapvos) {
				if (StringUtil.isEmpty(vo.getSettlecurr()) || vo.getSettlemoney() == null) {
					billMapDataArap.add(vo);
				} else {
					if (IBillFieldGet.F0.equals(vo.getS_billtype()) && IBillFieldGet.F3.equals(vo.getT_billtype())) { // 红字应收拉付款
						billMapDataArap.add(vo);
					} else if (vo.getS_billtype().equals(vo.getT_billtype())) {// 红冲
						billMapDataUnRb.add(vo);
					} else {
						if (vo.getPk_currtype().equals(vo.getSettlecurr())) {// 同币种
							billMapDataArap.add(vo);
						} else { // 异币种按照上游下游币种汇总
							String key = vo.getPk_currtype() + "*" + vo.getSettlecurr();
							if (billMapDataUnSam.get(key) != null) {
								billMapDataUnSam.get(key).add(vo);
							} else {
								List<ArapBillMapVO> map = new ArrayList<ArapBillMapVO>();
								map.add(vo);
								billMapDataUnSam.put(key, map);
							}
						}

					}
				}
			}
			if (billMapDataArap != null && billMapDataArap.size() > 0) {
				this.doVerifyWithMap(billMapDataArap, ruleVOMap, com);
			}
			if (billMapDataUnRb != null && billMapDataUnRb.size() > 0) {
				this.doArapVerifyWithMap(billMapDataUnRb, this.createRBVerifyRuleVO(), BillRegisterForBusiDataBO.HCUNVERIFYFLAG);
			}
			for (String key : billMapDataUnSam.keySet()) {
				this.doArapVerifyWithMap(billMapDataUnSam.get(key), null, BillRegisterForBusiDataBO.UNVERIFYFLAG);
			}
		}
	}

	/**
	 * @param mapvos
	 *            核销map
	 * @param ruleVOMap
	 *            核销规则
	 * @param isSamVerify
	 *            是否同币种
	 * @param isRed
	 *            是否红冲
	 * @throws BusinessException
	 */
	private void doArapVerifyWithMap(List<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, String verifyFlag) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}
		this.aggMap.clear();
		VerifyCom com = new VerifyCom();
		com.setReserveFlag(VerifyDetailVO.reserve_arap);
		com.setExactVerify(true);
		Map<String, List<ArapBusiDataVO>> busiMap = new HashMap<String, List<ArapBusiDataVO>>();
		List<String> pks = new ArrayList<String>();
		List<String> termPkList = new ArrayList<String>();
		List<String> itemPkList = new ArrayList<String>();
		Collection<ArapBusiDataVO> busiList = null;
		Map<String, String> item_TermMap = new HashMap<String, String>();
		// 根据系统来源判断 是否来自付款申请
		for (ArapBillMapVO vo : mapvos) {
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				// 判断 只有资金的付款排成 才可以按协议行核销
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
			} else {
				if (vo.getS_itemid() == null) { // 源头id 为空 ，没有数据不用核销
				} else {
					pks.add(vo.getS_itemid());
					pks.add(vo.getT_itemid());
				}
			}
		}

		if (termPkList != null && termPkList.size() > 0) {
			String sql = SqlUtils.getInStr("pk_termitem", termPkList.toArray(new String[] {}), true) + " and pausetransact ='N' " + " or "
					+ SqlUtils.getInStr("pk_item", itemPkList.toArray(new String[] {}), true);
			busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, sql);
		} else {
			if (pks != null && pks.size() > 0) {
				busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", pks.toArray(new String[] {}), true) + " and money_bal <> 0 " + " and pausetransact ='N' ");
			} else {
			}
		}
		if (busiList == null || busiList.size() == 0) {
			return;
		}

		// 比较ts 使用
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);

		// 以表体行分组
		for (ArapBusiDataVO bvo : busiList) {
			item_TermMap.put(bvo.getPk_termitem(), bvo.getPk_item());
			if (busiMap.containsKey(bvo.getPk_item())) {
				List<ArapBusiDataVO> list2 = busiMap.get(bvo.getPk_item());
				list2.add(bvo);
				busiMap.put(bvo.getPk_item(), list2);
			} else {
				List<ArapBusiDataVO> slist = new ArrayList<ArapBusiDataVO>();
				slist.add(bvo);
				busiMap.put(bvo.getPk_item(), slist);
			}
		}
		Map<String, ArrayList<AggverifyVO>> aggMap = null;

		List<ArapBusiDataVO> jf_vos_list = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> df_vos_list = new ArrayList<ArapBusiDataVO>();

		// 取下游单据日期
		UFDate busiDate = null;
		if (BillRegisterForBusiDataBO.UNVERIFYFLAG.equals(verifyFlag)) {
			busiDate = busiMap.get(mapvos.get(0).getT_itemid()).get(0).getBilldate();
			ruleVOMap = this.createUnVerifyRuleVO(mapvos.get(0), busiDate);
		}
		for (ArapBillMapVO vo : mapvos) {
			ArapBusiDataVO[] jf_vos = null;
			ArapBusiDataVO[] df_vos = null;

			ArapBusiDataVO[] jf_vostemp = null;
			ArapBusiDataVO[] df_vostemp = null;
			String sItemid = vo.getS_itemid();
			String tItemid = vo.getT_itemid();
			List<ArapBusiDataVO> slist = null;
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
			} else {
				if (sItemid == null) {
					return;
				}
			}
			List<ArapBusiDataVO> tlist = null;
			// 付款排程
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				String stermid = vo.getS_termid();
				String titemid = vo.getT_itemid();
				// 根据源协议表id，获得源子表id
				String sitemid = item_TermMap.get(stermid);
				slist = busiMap.get(sitemid);
				tlist = busiMap.get(titemid);
			} else {
				slist = busiMap.get(sItemid);
				tlist = busiMap.get(tItemid);
			}

			if (com.isArapVerify()) {
				if (slist != null) {
					for (ArapBusiDataVO svo : slist) {
						svo.setAttributeValue(BillRegisterForBusiDataBO.SPECIAL_KEY, svo.getPk_item());
					}
				}
				if (tlist != null) {
					for (ArapBusiDataVO tvo : tlist) {
						tvo.setAttributeValue(BillRegisterForBusiDataBO.SPECIAL_KEY, tvo.getTop_itemid());
					}
				}
			}
			ArapBusiDataVOList svolist = new ArapBusiDataVOList(slist);
			ArapBusiDataVOList tvolist = new ArapBusiDataVOList(tlist);
			if (tvolist.getVolist() == null || tvolist.getVolist().size() == 0 || svolist.getVolist() == null || svolist.getVolist().size() == 0) {
				//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//				continue;
				return;
			} else {
				if (BillRegisterForBusiDataBO.SAMVERIFYFLAG.equals(verifyFlag)) {
					// 退货核销，红字应收单（负金额），付款单
					if (svolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F0) && tvolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F3)) {
						svolist.setDealMoney(vo.getYbje().multiply(-1), com.isSoVerify(), aggMap);
						tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap);
					} else {
						svolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap);
						tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap);
					}
				} else if (BillRegisterForBusiDataBO.UNVERIFYFLAG.equals(verifyFlag)) {
					svolist.setUnDealMoney(vo, true, aggMap, busiDate);
					tvolist.setUnDealMoney(vo, false, aggMap, busiDate);
				} else if (BillRegisterForBusiDataBO.HCSAMVERIFYFLAG.equals(verifyFlag)) {
					svolist.setHcDealMoney(vo, true, aggMap);
					tvolist.setHcDealMoney(vo, false, aggMap);
				} else if (BillRegisterForBusiDataBO.HCUNVERIFYFLAG.equals(verifyFlag)) {
					svolist.setHcDealMoney(vo, true, aggMap);
					tvolist.setHcDealMoney(vo, false, aggMap);
				}
			}
			// 取财务组织，核销顺序参数时使用
			this.setPkorg(vo.getPk_org());
			// 判断借贷方
			if (this.isJie(svolist.getVolist())) {
				jf_vostemp = new ArapBusiDataVO[svolist.getVolist().size()];
				svolist.getVolist().toArray(jf_vostemp);

				df_vostemp = new ArapBusiDataVO[tvolist.getVolist().size()];
				tvolist.getVolist().toArray(df_vostemp);
			} else {
				df_vostemp = new ArapBusiDataVO[svolist.getVolist().size()];
				svolist.getVolist().toArray(df_vostemp);

				jf_vostemp = new ArapBusiDataVO[tvolist.getVolist().size()];
				tvolist.getVolist().toArray(jf_vostemp);
			}
			// 退货核销，特殊处理
			if (jf_vostemp[0].getPk_billtype().equals(IBillFieldGet.F0) && df_vostemp[0].getPk_billtype().equals(IBillFieldGet.F3)) {
				jf_vos = ArrayUtil.union(jf_vos, jf_vostemp);
				jf_vos = ArrayUtil.union(jf_vos, df_vostemp);
				com.setRedContrast(true);
			} else {

				jf_vos = ArrayUtil.union(jf_vos, jf_vostemp);
				df_vos = ArrayUtil.union(df_vos, df_vostemp);
			}

			if (jf_vos != null) {
				for (ArapBusiDataVO jfvo : jf_vos) {
					jf_vos_list.add(jfvo);
				}
			}
			if (df_vos != null) {
				for (ArapBusiDataVO dfvo : df_vos) {
					df_vos_list.add(dfvo);
				}
			}
			if (BillRegisterForBusiDataBO.HCSAMVERIFYFLAG.equals(verifyFlag) || BillRegisterForBusiDataBO.HCUNVERIFYFLAG.equals(verifyFlag)) {
				if (this.isJie(svolist.getVolist())) {
					jf_vos_list.addAll(df_vos_list);
					df_vos_list.clear();
				} else {
					df_vos_list.addAll(jf_vos_list);
					jf_vos_list.clear();
				}
			}
		}

		if (com.isArapVerify()) {// 合并为批量处理
			//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//			for(DefaultVerifyRuleVO rule:ruleVOMap.values()){
//				rule.setM_creditObjKeys(new String[]{SPECIAL_KEY});
//				rule.setM_debtObjKeys(new String[]{SPECIAL_KEY});
//			}
			//del end
			aggMap = this.onVerify(com, ruleVOMap, jf_vos_list.toArray(new ArapBusiDataVO[] {}), df_vos_list.toArray(new ArapBusiDataVO[] {}));
		}

		ArrayList<AggverifyVO> aggVOList = this.getAggVerifyVO(aggMap);

		if (aggVOList != null && aggVOList.size() > 0) {
			for (AggverifyVO v : aggVOList) {
				VerifyCom.validate(v);
			}
		}

		this.dealOrgScomment(aggVOList);
		// 核销结果保存
		if (aggVOList != null && aggVOList.size() > 0) {
			NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
		}
	}

	private void dealOrgScomment(ArrayList<AggverifyVO> aggVOList) {
		for (AggverifyVO aggVO : aggVOList) {
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggVO.getChildrenVO();
			for (VerifyDetailVO verifyvo : childrenVO) {
				// 代收代付摘要
				if (!StringUtil.isEmpty(verifyvo.getSrc_org()) && !verifyvo.getPk_org().equals(verifyvo.getSrc_org())) {
					IGeneralAccessor accessor = GeneralAccessorFactory.getAccessor("2cfe13c5-9757-4ae8-9327-f5c2d34bcb46");
					IBDData docByPk = accessor.getDocByPk(verifyvo.getSrc_org());
					String orgName = "";
					if (docByPk != null) {
						orgName = docByPk.getName().toString();
					}
					if (IBillFieldGet.F0.equals(verifyvo.getPk_billtype()) || IBillFieldGet.F2.equals(verifyvo.getPk_billtype())) {
						verifyvo.setScomment(verifyvo.getScomment() + " " + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub0322_0", "02006pub0322-0033", null, new String[] { orgName })/*
																																																		 * @
																																																		 * res
																																																		 * "代收："
																																																		 */);
					} else {
						verifyvo.setScomment(verifyvo.getScomment() + " " + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub0322_0", "02006pub0322-0034", null, new String[] { orgName })/*
																																																		 * @
																																																		 * res
																																																		 * "代付："
																																																		 */);
					}
				}
			}
		}
	}

	private void doVerifyWithMap(Collection<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}
		this.aggMap.clear();
		Map<String, List<ArapBusiDataVO>> busiMap = new HashMap<String, List<ArapBusiDataVO>>();
		List<String> pks = new ArrayList<String>();
		List<String> termPkList = new ArrayList<String>();
		List<String> itemPkList = new ArrayList<String>();
		Collection<ArapBusiDataVO> busiList = null;
		Map<String, String> item_TermMap = new HashMap<String, String>();
		// 调试是 注释 再打开
		for (ArapBillMapVO vo : mapvos) {
			// 根据系统来源判断 是否来自付款申请
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				// 判断 只有资金的付款排成 才可以按协议行核销
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
			} else {
				if (vo.getS_itemid() == null) { // 源头id 为空 ，没有数据不用核销
				} else {
					pks.add(vo.getS_itemid());
					pks.add(vo.getT_itemid());
				}
			}
		}

		// 根据参数确定是否要按照预收付标志核销
		if (VerifyDetailVO.reserve_so.equals(com.getReserveFlag())) {
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AR35);
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
		} else if (VerifyDetailVO.reserve_pu.equals(com.getReserveFlag())) {
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
		} else if (VerifyDetailVO.reserve_pcm.equals(com.getReserveFlag())) {
			//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip
//			if (mapvos.iterator().next().getS_system() == 1) {// 应付
//				UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);// 应付
			if (mapvos.iterator().next().getS_system() == 0) {// 应收
				isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AR35);
			}
			if (isNeed != null && isNeed.booleanValue()) {
				for (DefaultVerifyRuleVO rule : ruleVOMap.values()) {
					rule.setM_creditObjKeys(new String[] { IArapItemFieldVO.PREPAY });
					rule.setM_debtObjKeys(new String[] { IArapItemFieldVO.PREPAY });
				}
			}
//			}
		}

		if (termPkList != null && termPkList.size() > 0) {
			String sql = SqlUtils.getInStr("pk_termitem", termPkList.toArray(new String[] {}), true) + " and pausetransact ='N' " + " or "
					+ SqlUtils.getInStr("pk_item", itemPkList.toArray(new String[] {}), true);
			busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, sql);
		} else {
			if (pks != null && pks.size() > 0) {
				busiList = new BaseDAO().retrieveByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", pks.toArray(new String[] {}), true) + " and money_bal <> 0 " + " and pausetransact ='N' ");
			} else {
			}
		}

		if (busiList == null || busiList.size() == 0) {
			return;
		}

		// 比较ts 使用
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);

		// 以表体行分组
		for (ArapBusiDataVO bvo : busiList) {
			item_TermMap.put(bvo.getPk_termitem(), bvo.getPk_item());
			if (busiMap.containsKey(bvo.getPk_item())) {
				List<ArapBusiDataVO> list2 = busiMap.get(bvo.getPk_item());
				list2.add(bvo);
				busiMap.put(bvo.getPk_item(), list2);
			} else {
				List<ArapBusiDataVO> slist = new ArrayList<ArapBusiDataVO>();
				slist.add(bvo);
				busiMap.put(bvo.getPk_item(), slist);
			}
		}
		Map<String, ArrayList<AggverifyVO>> aggMap = null;

		List<ArapBusiDataVO> jf_vos_list = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> df_vos_list = new ArrayList<ArapBusiDataVO>();

		for (ArapBillMapVO vo : mapvos) {
			ArapBusiDataVO[] jf_vos = null;
			ArapBusiDataVO[] df_vos = null;

			ArapBusiDataVO[] jf_vostemp = null;
			ArapBusiDataVO[] df_vostemp = null;
			String sItemid = vo.getS_itemid();
			String tItemid = vo.getT_itemid();
			List<ArapBusiDataVO> slist = null;
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
			} else {
				if (sItemid == null) {
					continue;
				}
			}
			List<ArapBusiDataVO> tlist = null;
			// 付款排程
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				String stermid = vo.getS_termid();
				String titemid = vo.getT_itemid();
				// 根据源协议表id，获得源子表id
				String sitemid = item_TermMap.get(stermid);
				slist = busiMap.get(sitemid);
				tlist = busiMap.get(titemid);
			} else {
				slist = busiMap.get(sItemid);
				tlist = busiMap.get(tItemid);
			}

			if (com.isArapVerify()) {
				if (slist != null) {
					for (ArapBusiDataVO svo : slist) {
						svo.setAttributeValue(BillRegisterForBusiDataBO.SPECIAL_KEY, svo.getPk_item());
					}
				}
				if (tlist != null) {
					for (ArapBusiDataVO tvo : tlist) {
						tvo.setAttributeValue(BillRegisterForBusiDataBO.SPECIAL_KEY, tvo.getTop_itemid());
					}
				}
			}

			ArapBusiDataVOList svolist = new ArapBusiDataVOList(slist);
			ArapBusiDataVOList tvolist = new ArapBusiDataVOList(tlist);
			if (tvolist.getVolist() == null || tvolist.getVolist().size() == 0 || svolist.getVolist() == null || svolist.getVolist().size() == 0) {
				continue;
			} else {
				// 退货核销，红字应收单（负金额），付款单
				if (svolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F0) && tvolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F3)) {
					svolist.setDealMoney(vo.getYbje().multiply(-1), com.isSoVerify(), aggMap);
					tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap);
				} else {
					//update chenth 20161212  如果是手续费特殊处理
					//add by weiningc 20171012 633适配至65 start
					if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
						svolist.setDealMoney(UFDouble.ZERO_DBL, com.isSoVerify(),aggMap, com.getReserveFlag());
					}else{
						svolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap, com.getReserveFlag());
					}
					tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap, com.getReserveFlag());
				}
			}
			// 取财务组织，核销顺序参数时使用
			this.setPkorg(vo.getPk_org());
			// 判断借贷方
			if (this.isJie(svolist.getVolist())) {
				jf_vostemp = new ArapBusiDataVO[svolist.getVolist().size()];
				svolist.getVolist().toArray(jf_vostemp);

				df_vostemp = new ArapBusiDataVO[tvolist.getVolist().size()];
				tvolist.getVolist().toArray(df_vostemp);
			} else {
				df_vostemp = new ArapBusiDataVO[svolist.getVolist().size()];
				svolist.getVolist().toArray(df_vostemp);

				jf_vostemp = new ArapBusiDataVO[tvolist.getVolist().size()];
				tvolist.getVolist().toArray(jf_vostemp);
			}
			// 退货核销，特殊处理
			if (jf_vostemp[0].getPk_billtype().equals(IBillFieldGet.F0) && df_vostemp[0].getPk_billtype().equals(IBillFieldGet.F3)) {
				jf_vos = ArrayUtil.union(jf_vos, jf_vostemp);
				jf_vos = ArrayUtil.union(jf_vos, df_vostemp);
				com.setRedContrast(true);
			} else {

				jf_vos = ArrayUtil.union(jf_vos, jf_vostemp);
				df_vos = ArrayUtil.union(df_vos, df_vostemp);
			}

			if (!com.isArapVerify() && com.getReserveFlag().intValue() != VerifyDetailVO.reserve_pcm) {
				aggMap = this.onVerify(com, ruleVOMap, jf_vos, df_vos);
				//del chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip  不知道为什么会有这段代码，手续费折扣核销时没有这段代码，标准产品也没有
//				ArrayList<AggverifyVO> aggVOList = getAggVerifyVO(aggMap);
//				dealOrgScomment(aggVOList);
//				// 核销结果保存
//				if (aggVOList != null && aggVOList.size() > 0) {
//					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
//				}
//				
//				// 比较ts 使用
//				ArrayList<String> apks=new ArrayList<String>();
//				for(ArrayList<AggverifyVO>  a:aggMap.values()){
//					if(a!=null){
//						for(AggverifyVO b:a){
//							if(b!=null){
//								VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) b.getChildrenVO();
//								for(VerifyDetailVO c:childrenVO){
//									apks.add(c.getPk_item());
//								}
//							}
//						}
//					}
//				}
//				Collection<ArapBusiDataVO> busisssList =
//						new BaseDAO().retrieveByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", 
//								apks.toArray(new String[] {})));
//				for (ArapBusiDataVO svo : busisssList) {
//					com.getBusiDataTsMap().put(svo.getPk_busidata(), svo.getTs().toString());
//				}
//				aggMap.clear();
				//del end
				
				// for(ArapBusiDataVO v:jf_vos){
				// v.setAttributeValue("SETT_MONEY", null);
				// }
				// for(ArapBusiDataVO v:df_vos){
				// v.setAttributeValue("SETT_MONEY", null);
				// }
			} else {
				if (jf_vos != null) {
					for (ArapBusiDataVO jfvo : jf_vos) {
						jf_vos_list.add(jfvo);
					}
				}
				if (df_vos != null) {
					for (ArapBusiDataVO dfvo : df_vos) {
						df_vos_list.add(dfvo);
					}
				}
			}
		}

		if (com.isArapVerify() || com.getReserveFlag().intValue() == VerifyDetailVO.reserve_pcm) {// 合并为批量处理
			aggMap = this.onVerify(com, ruleVOMap, jf_vos_list.toArray(new ArapBusiDataVO[] {}), df_vos_list.toArray(new ArapBusiDataVO[] {}));
		}

		ArrayList<AggverifyVO> aggVOList = this.getAggVerifyVO(aggMap);
		this.dealOrgScomment(aggVOList);
		// 核销结果保存
		if (aggVOList != null && aggVOList.size() > 0) {
			NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
		}
	}

	private Map<String, ArrayList<AggverifyVO>> onVerify(VerifyCom com, Map<String, DefaultVerifyRuleVO> ruleVOMap, ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos) {

		// 自动核销 只有红蓝对冲，同币种核销

		com.setM_creditSelected(df_vos);
		com.setM_debitSelected(jf_vos);
		AggverifyVO aggvo = null;
		if (!ArrayUtils.isEmpty(jf_vos) || !ArrayUtils.isEmpty(df_vos)) {
			for (String key : ruleVOMap.keySet()) {
				if (ArrayUtils.isEmpty(jf_vos) || ArrayUtils.isEmpty(df_vos)) {
					if (key.equals("SAME_VERIFY")) {
						continue;
					}
				} else {
					String billclass = jf_vos[0].getBillclass();
					String billclass2 = df_vos[0].getBillclass();

					if (billclass.equals(IBillFieldGet.YS) && billclass2.equals(IBillFieldGet.FK) || billclass.equals(IBillFieldGet.FK) && billclass2.equals(IBillFieldGet.YS)) {
						if (key.equals("SAME_VERIFY") || key.equals("UNSAME_VERIFY")) {
							continue;
						}
					}
					if (billclass.equals(IBillFieldGet.YS) && billclass2.equals(IBillFieldGet.SK) || billclass.equals(IBillFieldGet.SK) && billclass2.equals(IBillFieldGet.YS)
							|| billclass.equals(IBillFieldGet.YF) && billclass2.equals(IBillFieldGet.FK) || billclass.equals(IBillFieldGet.FK) && billclass2.equals(IBillFieldGet.YF)) {
						if (key.equals("RB_VERIFY")) {
							continue;
						}
					}
				}

				DefaultVerifyRuleVO rulevo = ruleVOMap.get(key);
				if (key.equals("SAME_VERIFY")) {

					SameMnyVerify smverify = new SameMnyVerify(com);
					aggvo = smverify.onVerify(jf_vos, df_vos, rulevo);
					if (aggvo == null) {
						continue;
					}
					if (!ArrayUtils.isEmpty(aggvo.getChildrenVO())) {
						if (this.aggMap.containsKey("SAME_VERIFY")) {
							this.aggMap.get(key).add(aggvo);
						} else {
							ArrayList<AggverifyVO> temp = new ArrayList<AggverifyVO>();
							temp.add(aggvo);
							this.aggMap.put("SAME_VERIFY", temp);
						}
					}
				} else if (key.equals("RB_VERIFY")) {
					RBVerify rbverify = new RBVerify(com);
					aggvo = rbverify.onVerify(jf_vos, df_vos, rulevo);
					if (!ArrayUtils.isEmpty(aggvo.getChildrenVO())) {
						if (this.aggMap.containsKey("RB_VERIFY")) {
							this.aggMap.get(key).add(aggvo);
						} else {
							ArrayList<AggverifyVO> temp = new ArrayList<AggverifyVO>();
							temp.add(aggvo);
							this.aggMap.put("RB_VERIFY", temp);
						}
					}
				} else if (key.equals("UNSAME_VERIFY")) {
					UnsameMnyVerify unsameMnyVerify = new UnsameMnyVerify(com);
					aggvo = unsameMnyVerify.onVerify(jf_vos, df_vos, rulevo);
					if (!ArrayUtils.isEmpty(aggvo.getChildrenVO())) {
						if (this.aggMap.containsKey("UNSAME_VERIFY")) {
							this.aggMap.get(key).add(aggvo);
						} else {
							ArrayList<AggverifyVO> temp = new ArrayList<AggverifyVO>();
							temp.add(aggvo);
							this.aggMap.put("UNSAME_VERIFY", temp);
						}

					}
				}

			}
		}

		return this.aggMap;

	}

	@SuppressWarnings("rawtypes")
	private ArrayList<AggverifyVO> getAggVerifyVO(Map<String, ArrayList<AggverifyVO>> aggMap) throws BusinessException {
		//update chenth 20190722 适配通版补丁: NCM_65_ARAP_通版综合20190704.zip 这个定义之前放在for循环中，不确定什么原因，手续费代码和标准代码都没有放在for中
		List<VerifyDetailVO> detailVOList = new ArrayList<VerifyDetailVO>();
		//update end
		ArrayList resultList = new ArrayList();
		if (aggMap == null) {
			return resultList;
		}
		for (String key : aggMap.keySet()) {
			ArrayList<AggverifyVO> arrayList = aggMap.get(key);
			if (arrayList != null) {
				for (AggverifyVO aggVO : arrayList) {
					VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggVO.getChildrenVO();
					// 审核后自动核销，设置标志位 核销记录查询不出来
					for (VerifyDetailVO verifyvo : childrenVO) {
						verifyvo.setIsauto(UFBoolean.TRUE);
						detailVOList.add(verifyvo);
					}
				}
				// 组织聚合vo
				AggverifyVO aggVO = new AggverifyVO();
				aggVO.setChildrenVO(detailVOList.toArray(new VerifyDetailVO[0]));
				// 计算全局 集团本位币
				VerifyCom.calGroupAndGloablMny(aggVO);
				ArrayList<AggverifyVO> aggvolist = (ArrayList) VerifyCom.verifySumData(aggVO);

				resultList.addAll(aggvolist);
			}
		}
		return resultList;
	}

	/**
	 * 异币种核销规则
	 * 
	 * @param vo
	 * @param busiDate
	 *            取下游单据日期所对应的汇率。
	 * @return
	 * @throws BusinessException
	 */
	private Hashtable<String, DefaultVerifyRuleVO> createUnVerifyRuleVO(ArapBillMapVO vo, UFDate busiDate) throws BusinessException {
		Hashtable<String, DefaultVerifyRuleVO> ruleMap = new Hashtable<String, DefaultVerifyRuleVO>();
		DefaultVerifyRuleVO rulevo = this.getVerifyRuleVO();
		rulevo.setM_verifyName("UNSAME_VERIFY");
		// 是否跨组织 跨组织中币取集团本币，否则取组织本币
		String pk_group = InvocationInfoProxy.getInstance().getGroupId();
		if (vo.getPk_org().equals(vo.getPk_org1())) {
			pk_group = vo.getPk_org();
		}
		String verifyCurr = CurrencyRateUtilHelper.getInstance().getLocalCurrtypeByOrgID(pk_group);
		rulevo.setM_verifyCurr(verifyCurr);
		// UFDate busiDate =new
		// UFDate(InvocationInfoProxy.getInstance().getBizDateTime());
		// 判断来源单据是否借方
		if (IBillFieldGet.F2.equals(vo.getS_billtype()) || IBillFieldGet.F1.equals(vo.getS_billtype())) {
			// 借方币种、汇率
			int rateType = ArapBillCalUtil.getRateType(vo.getT_billtype(), pk_group);
			rulevo.setM_debitCurr(vo.getPk_currtype());
			rulevo.setM_jfbz2zjbzHL(Currency.getRate(pk_group, vo.getPk_currtype(), verifyCurr, busiDate, rateType));
			rulevo.setM_debttoBBExchange_rate(Currency.getRate(vo.getPk_org(), vo.getPk_currtype(), busiDate, rateType));
			// 贷方币种。汇率
			int rateType1 = ArapBillCalUtil.getRateType(vo.getS_billtype(), pk_group);
			rulevo.setM_creditCurr(vo.getSettlecurr());
			rulevo.setM_dfbz2zjbzHL(Currency.getRate(pk_group, vo.getSettlecurr(), verifyCurr, busiDate, rateType1));
			rulevo.setM_creditoBBExchange_rate(Currency.getRate(vo.getPk_org1(), vo.getSettlecurr(), busiDate, rateType1));
		} else {
			// 借方币种、汇率
			int rateType = ArapBillCalUtil.getRateType(vo.getS_billtype(), pk_group);
			rulevo.setM_debitCurr(vo.getSettlecurr());
			rulevo.setM_jfbz2zjbzHL(Currency.getRate(pk_group, vo.getSettlecurr(), verifyCurr, busiDate, rateType));
			rulevo.setM_debttoBBExchange_rate(Currency.getRate(vo.getPk_org1(), vo.getSettlecurr(), busiDate, rateType));

			// 贷方币种。汇率
			int rateType1 = ArapBillCalUtil.getRateType(vo.getT_billtype(), pk_group);
			rulevo.setM_creditCurr(vo.getPk_currtype());
			rulevo.setM_dfbz2zjbzHL(Currency.getRate(pk_group, vo.getPk_currtype(), verifyCurr, busiDate, rateType1));
			rulevo.setM_creditoBBExchange_rate(Currency.getRate(vo.getPk_org(), vo.getPk_currtype(), busiDate, rateType1));
		}
		ruleMap.put("UNSAME_VERIFY", rulevo);
		return ruleMap;
	}

	private Hashtable<String, DefaultVerifyRuleVO> createRBVerifyRuleVO() {
		Hashtable<String, DefaultVerifyRuleVO> ruleMap = new Hashtable<String, DefaultVerifyRuleVO>();
		DefaultVerifyRuleVO rulevo = this.getVerifyRuleVO();
		rulevo.setM_verifyName("RB_VERIFY");
		ruleMap.put("RB_VERIFY", rulevo);
		return ruleMap;
	}

	// private Hashtable<String, DefaultVerifyRuleVO> createVerifyRuleVO() {
	// Hashtable<String, DefaultVerifyRuleVO> ruleMap =
	// new Hashtable<String, DefaultVerifyRuleVO>();
	// DefaultVerifyRuleVO rulevo = this.getVerifyRuleVO();
	// rulevo.setM_verifyName("SAME_VERIFY");
	// ruleMap.put("SAME_VERIFY", rulevo);
	// return ruleMap;
	// }

	private DefaultVerifyRuleVO getVerifyRuleVO() {
		DefaultVerifyRuleVO rulevo = new DefaultVerifyRuleVO();
		Integer m_hxSeq = Integer.valueOf(0);
		// UFDateTime busidate = null;
		try {
			// busidate =
			// NCLocator.getInstance().lookup(IServerEnvironmentService.class)
			// .getServerTime();
			String hxSeq = "";
			if (this.getSyscode() == 0) {
				hxSeq = SysinitAccessor.getInstance().getParaString(this.getPkorg(), "AR1");
			} else {
				hxSeq = SysinitAccessor.getInstance().getParaString(this.getPkorg(), "AP1");
			}
			if (hxSeq.equals(SysinitConst.VERIFY_ZAO)) {
				m_hxSeq = Integer.valueOf(0);
			} else {
				m_hxSeq = Integer.valueOf(1);
			}

		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		rulevo.setM_verifySeq(m_hxSeq);
		UFDate clrq = new UFDate(InvocationInfoProxy.getInstance().getBizDateTime());
		rulevo.setM_Clrq(clrq);
		AccountCalendar ac = AccountCalendar.getInstanceByPk_org(this.getPkorg());
		try {
			ac.setDate(clrq);
		} catch (InvalidAccperiodExcetion e) {
			ExceptionHandler.consume(e);
		}
		rulevo.setSystem(this.getSyscode());
		rulevo.setPk_org(this.getPkorg());
		rulevo.setM_Clnd(ac.getYearVO().getPeriodyear());
		rulevo.setM_Clqj(ac.getMonthVO().getAccperiodmth());
		rulevo.setIsOccuption(true);
		rulevo.setM_clr(this.getPk_user());
		return rulevo;
	}

	private Hashtable<String, DefaultVerifyRuleVO> creatVerifyRuleVO() {

		Hashtable<String, DefaultVerifyRuleVO> ruleMap = new Hashtable<String, DefaultVerifyRuleVO>();
		DefaultVerifyRuleVO rulevo = this.getVerifyRuleVO();
		rulevo.setM_verifyName("SAME_VERIFY");
		DefaultVerifyRuleVO rbrulevo = (DefaultVerifyRuleVO) rulevo.clone();
		rbrulevo.setM_verifyName("RB_VERIFY");
		ruleMap.put("SAME_VERIFY", rulevo);
		ruleMap.put("RB_VERIFY", rbrulevo);

		return ruleMap;
	}

	private boolean isJie(List<ArapBusiDataVO> listvo) {
		for (ArapBusiDataVO vo : listvo) {
			if (vo == null) {
				return false;
			}
			Integer int_fx = (Integer) vo.getAttributeValue(ArapBusiDataVO.DIRECTION);
			if (int_fx == null) {
				return false;
			}

			int fx = int_fx.intValue();

			return fx == 1;

		}
		return false;
	}
}
