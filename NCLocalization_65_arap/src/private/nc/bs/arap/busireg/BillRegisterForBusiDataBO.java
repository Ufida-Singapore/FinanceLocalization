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

	private static String SPECIAL_KEY = "def1"; // Ӧ��Ӧ���ڲ��������������մ��ֶμ�¼�����ε��ݵ����������������������;

	Map<String, ArrayList<AggverifyVO>> aggMap = new HashMap<String, ArrayList<AggverifyVO>>();

	Map<String, List<TermVO>> termVOMap = new HashMap<String, List<TermVO>>();
	
	//add chenth 20161212 �տ������Ƿ�������
	//add by weiningc 20171012 633������65 end
	Map<String,RecpaytypeVO> recpaytypeMap =new HashMap<String,RecpaytypeVO>();
	//add end
	//add by weiningc 20171012 633������65 end

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
	 * ͳһ���������ӿ�
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
	 * ҵ�񵥾ݵ��˽ӿ�
	 */
	public void doAction(IBusinessEvent event) throws BusinessException {
		this.doBusiAction(event);
	}

	/**
	 * ��չ����������
	 * 
	 * @param vos
	 * @param ruleVOMap
	 * @param com
	 * @throws BusinessException
	 * @throws MetaDataException
	 */
	private void billRBVerifyBySrcBillForExtend(AggregatedValueObject[] vos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException, MetaDataException {
		// ��ѯ��չ�������򣬽��д���
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
					// ���ֱ��� �Է�
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
						// ���²�ѯ���ݱ������Ϣ
						this.reQueryBillIteminfo(vo);

						aggVOList.clear();
					}
					this.aggMap.clear();

					// ����rule���Ա����ʹ��
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

			//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
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
//					// ���ֱ��� �Է�
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

			// ����src_itemid���з���
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
						// ���ֱ��� �Է�
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
				// ���²�ѯ���ݱ������Ϣ
				this.reQueryBillIteminfo(vo);
			}
			this.aggMap.clear();
		}
	}

	private void doBusiAction(IBusinessEvent event) throws BusinessException, MetaDataException, DAOException {
		// ��Ч��
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
						// ������ڳ����ݣ�������˼�����ֱ�ӷ���
						return;
					}
				}
				Object[] transform = this.transform(vos);
				List<ArapBusiDataVO> insertdatas = (List<ArapBusiDataVO>) transform[0];
				List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
				if (insertdatas != null && insertdatas.size() > 0) {
					this.setAreaPKForBusiData(insertdatas);// ����PK��ֵ
				}

				this.doInsertRegister(insertdatas);

				if (updatedatas != null && updatedatas.size() > 0) {
					this.setAreaPKForBusiData(updatedatas); // ����PK��ֵ
				}

				this.doInsertRegister(updatedatas);

				for (AggregatedValueObject obj : vos) {
					String billclass = (String) obj.getParentVO().getAttributeValue("billclass");
					if (billclass != null && (billclass.equals(ArapConstant.ARAP_ZS_BILLCLASS) || billclass.equals(ArapConstant.ARAP_ZF_BILLCLASS))) {
						return;
					}
				}

				// ��ʽ���ɵ��Զ�����
				List<String> pks = new ArrayList<String>();
				//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
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

				// ��Դ�����۵�Ӧ�յ���Ч������ڲ������Գ�
				// �����ڰ�����Դ�����к���(����,������������Դ+��֧��Ŀ����)
				this.billRBVerifyBySrcBillForExtend(vos, ruleVOMap, com);
				//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//				this.billRBVerifyBySrcItem(vos, ruleVOMap, com);
//				this.billRBVerify(vos, ruleVOMap, com);

				// �Զ�������������
				String pk_billtype = "";
				Map<String, VerifyfaVO> tradetypes = new HashMap<String, VerifyfaVO>();
				MapList<VerifyfaVO, String> tradeTypeBills = new MapList<VerifyfaVO, String>();
				
				BaseDAO dao = new BaseDAO();
				for (AggregatedValueObject vo : vos) {
					VerifyfaVO faVO = null;
					// ������Դϵͳ
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
					//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//                    if(true){
//                    	for (BaseItemVO itemVO : childrenVO) {
//	                    	ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//							allList.add(mapVO);
//                    	}
//                    }
					if (ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getTop_billtype()) || ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype())
							|| ArapBillDealVOConsts.BILLTYPE_36D7.equals(childrenVO[0].getTop_billtype())) {
						for (BaseItemVO itemVO : childrenVO) {
							//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//							if (itemVO.getTop_termch() == null) {
//								ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//								mapVO.setS_itemid(itemVO.getPurchaseorder());// ���
//								scmList.add(mapVO);
//							} else if ((ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0].getTop_billtype()) && ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getSrc_billtype()))) {
//								// �ɹ�����-��������-��������ֱ�Ӱ��չ�Ӧ�����Զ����������߼���
//								ArapBillMapVO mapVO = ArapBillMapVOTool.changeVotoBillMapNew((BaseBillVO) vo.getParentVO(), itemVO);
//								mapVO.setS_itemid(itemVO.getPurchaseorder());// ���
//								scmList.add(mapVO);
							if((ArapBillDealVOConsts.BILLTYPE_36D1.equals(childrenVO[0]
			                        .getTop_billtype())&&ArapBillDealVOConsts.BILLTYPE_21.equals(childrenVO[0].getSrc_billtype()))){
			            		//1���ɹ�����-��������-�����Top_termch������ƻ�.����Э����������
			            		//2���ɹ�����-�ɹ���Ʊ-Ӧ����-��������-�����Top_termch��Ӧ����.�ո���Э������
			            		//3����������-��� ��չ���̣���������-�ƻ�����-�ƻ�ִ��-�����Top_termch��Ӧ����.�ո���Э������
			            		// ������˶�θ�������������̶��У���Ҫͨ��Top_termch�����ж����̡���ֻ������2 ʱ��Ӧ������Ӧ�����ĺ�����
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
			                			// �ɹ�����-��������-��������ֱ�Ӱ��չ�Ӧ�����Զ����������߼���
			                    		 ArapBillMapVO mapVO =
			                    				 ArapBillMapVOTool.changeVotoBillMapNew(
			                    						 (BaseBillVO) vo.getParentVO(), itemVO);
			                    		 mapVO.setS_itemid(itemVO.getPurchaseorder());// ���
			                    		 //��������������͸�Ϊ21����classname_pu  �� classname_ps ƥ���ʱ���ƥ�䵽 classname_ps��
			                    		 //�Ӷ�ȡ com.setReserveFlag(VerifyDetailVO.reserve_pc)������Ԥռ�ý���д��
			                    		 mapVO.setS_billid(itemVO.getSrc_billid());
			                    		 mapVO.setS_billtype(ArapBillDealVOConsts.BILLTYPE_21);
			                    		 scmList.add(mapVO);
			            			}
			            			
			            		}
			              		 
			              	 } else if (itemVO.getTop_termch() == null ) {
			            	  ArapBillMapVO mapVO =
			            			  ArapBillMapVOTool.changeVotoBillMapNew(
			            					  (BaseBillVO) vo.getParentVO(), itemVO);
			            	  mapVO.setS_itemid(itemVO.getPurchaseorder());// ���
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
							//add chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
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
											mapVO.setS_system(1);// Ӧ��

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
											mapVO.setS_system(1);// Ӧ��

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
							if ("4D60".equals(childrenVO[0].getTop_billtype())) { // Ӧ�յ�����
								String classname_pcm = "nc.impl.pcm.marketcontract.pub.MarContrService4PayableImpl";
								IMarContrService4Payable qryPcmClass = (IMarContrService4Payable) ObjectCreator.newInstance("pcm", classname_pcm);
								MapList<String, String> topitems = qryPcmClass.queryPlanByMContr(itemVO.getSrc_billid());
								List<String> top_itemid;
								// ������л�δ�����տ����Ҫ����
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
							} else { // �տ����
								String topBillID = itemVO.getTop_billid();
								// ��ѯ�տtop_itemId
								String classname_pcm = "nc.impl.pcm.marketcontract.pub.MarContrService4PayableImpl";
								IMarContrService4Payable qryPcmClass = (IMarContrService4Payable) ObjectCreator.newInstance("pcm", classname_pcm);
								Map<String, String> topitems = qryPcmClass.queryMContrByPlan(topBillID);
								// ������л�δ����Ӧ�գ�����Ҫ����
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
					//add chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
					//�����Դ���տ�������տ����Դͷ�����ۺ�ͬ��Ӧ�յ�����
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
										mapVO.setS_system(1);// Ӧ��

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
					// Ӧ��Ӧ���ڲ��ĵ��ݽ����Զ�����
					if (src_syscode.intValue() == FromSystem.AR.VALUE.intValue() || src_syscode.intValue() == FromSystem.AP.VALUE.intValue()) {
						pks.add(vo.getParentVO().getPrimaryKey());
					}

					// Ӧ��Ӧ�����ƵĻ���������ϵͳ���ݹ������տ�����ܽ�������������ϵ
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
						// ��Ӧ������ ����ѯ���غ�����ϵ����
						if (scmList.get(0).getS_billtype() == null) {
							//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
							// �ɹ�����-��Ӧ���� �ɹ�����-����ͬ-������ ���Զ�����
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

							// ��֯���ص�mapvo
							for (ArapBillMapVO vo : arapBillMap.keySet()) {
								Collection<ArapBillMapVO> collection = arapBillMap.get(vo);
								for (ArapBillMapVO vo1 : collection) {
									vo1.setT_billid(vo.getT_billid());
									vo1.setT_billtype(vo.getT_billtype());
									vo1.setT_itemid(vo.getT_itemid());
									vo1.setPk_currtype(vo.getPk_currtype());
								}
							}
							// ���� --�������Թ�Ӧ����
							mapCollections = arapBillMap.values();
							for (Collection<ArapBillMapVO> collection : mapCollections) {
								this.doVerifyWithMap(collection, ruleVOMap, com, null);
							}
						}
						// �ɹ�����-��Ӧ���� �ɹ�����-����ͬ-������ ���Զ�����
						//add chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
						this.VerifyByContractNo(newList, com, ruleVOMap);
						//add chenth end
					}
					//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//					VerifyByContractNo(allList, com, ruleVOMap);
					//del chenth
					
					// �����տ�Ѿ������۶�������Ԥ����
					if (defF2List.size() > 0) {
						IArap4VerifyQryBill qryClass = null;
						String classname_so = "nc.pubimpl.so.sobalance.arap.verify.SoBalance4VerifyQryBillImpl";

						// ѡ�еĵ�����Ч����к����Գ�
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

							// ��֯���ص�mapvo
							for (ArapBillMapVO vo : arapBillMap.keySet()) {
								Collection<ArapBillMapVO> collection = arapBillMap.get(vo);
								for (ArapBillMapVO vo1 : collection) {
									vo1.setT_billid(vo.getT_billid());
									vo1.setT_billtype(vo.getT_billtype());
									vo1.setT_itemid(vo.getT_itemid());
									vo1.setPk_currtype(vo.getPk_currtype());
								}
							}
							// ���� --�������Թ�Ӧ����
							mapCollections = arapBillMap.values();
							for (Collection<ArapBillMapVO> collection : mapCollections) {
								this.doVerifyWithMap(collection, ruleVOMap, com, null);
							}
						}
					}
					if (pks != null && pks.size() > 0) {
						com.setReserveFlag(VerifyDetailVO.reserve_arap);
						com.setExactVerify(true);
						//restore chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
						for(DefaultVerifyRuleVO rule:ruleVOMap.values()){
							rule.setM_creditObjKeys(new String[]{SPECIAL_KEY});
							rule.setM_debtObjKeys(new String[]{SPECIAL_KEY});
						}
						// arap�ڲ��ĵ������ʱ�Զ�����
						Collection<ArapBillMapVO> billMapData = new BaseDAO().retrieveByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_billid", pks.toArray(new String[] {}), true));
						this.doArapVerifyWithMap(billMapData, ruleVOMap, com);
						this.updateBillMapYbye(billMapData, true);
					}
					//add chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
					 // �����ʽ�ĸ����ų̵���
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
					// ������ĿԤ���ͽ��ȿ�ĺ���
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
					//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//					// �����ʽ�ĸ����ų̵���
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
					
					// �����Զ�������������
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
									if (favo.getNode_code().equals("20060VPS")) {// Ӧ��
										szxt = 0;
									} else if (favo.getNode_code().equals("20080VPS")) {// Ӧ��
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
		// ȡ����Чǰ����
		else if (IArapBSEventType.TYPE_UNEFFECTION_BEFORE.equals(event.getEventType())) {
			this.uneffectBill(event);
		}

		// ������-�ڳ���
		else if (IEventType.TYPE_INSERT_AFTER.equals(event.getEventType())) {
			BusinessEvent e = (BusinessEvent) event;
			if (null != e.getUserObject()) {
				AggregatedValueObject[] vos = this.dealUserObj(e.getUserObject());
				// �ڳ����������󱣴�
				UFBoolean isinit = (UFBoolean) vos[0].getParentVO().getAttributeValue("isinit"); // �ڳ���־
				if (isinit != null && isinit.booleanValue()) {
					Object[] transform = this.transform(vos);
					List<ArapBusiDataVO> insertdatas = (List<ArapBusiDataVO>) transform[0];
					List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
					if (insertdatas != null && insertdatas.size() > 0) {
						// ����PK��ֵ
						this.setAreaPKForBusiData(insertdatas);
					}
					this.doInsertRegister(insertdatas);
					if (updatedatas != null && updatedatas.size() > 0) {
						// ����PK��ֵ
						this.setAreaPKForBusiData(updatedatas);
					}
					this.doInsertRegister(updatedatas);
				}
			}
		}

		// ɾ����-�ڳ���
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
				// �ڳ����������󱣴�
				UFBoolean isinit = (UFBoolean) vos[0].getParentVO().getAttributeValue("isinit"); // �ڳ���־
				String pk_bill = vos[0].getParentVO().getPrimaryKey(); // �ڳ���־
				if (isinit != null && isinit.booleanValue()) {
					// ��ɾ���ٲ���
					new BaseDAO().deleteByClause(ArapBusiDataVO.class, " pk_bill ='" + pk_bill + "'");
					Object[] transform = this.transform(vos);
					List<ArapBusiDataVO> updatedatas = (List<ArapBusiDataVO>) transform[1];
					// ����PK��ֵ
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
																										 * "BillRegisterForBusiData(ҵ�񵥾ݵ��˽ӿ�)"
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
			//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//			List<ArapBillMapVO> allList = new ArrayList<ArapBillMapVO>(); // �ɹ�
			//del
			List<ArapBillMapVO> puList = new ArrayList<ArapBillMapVO>(); // �ɹ�
			List<ArapBillMapVO> soList = new ArrayList<ArapBillMapVO>(); // ���۶���-�տ
			List<ArapBillMapVO> htList = new ArrayList<ArapBillMapVO>(); // ���ۺ�ͬ-�յ�
			List<ArapBillMapVO> psList = new ArrayList<ArapBillMapVO>(); // �����ų�
			List<ArapBillMapVO> pcmList = new ArrayList<ArapBillMapVO>(); // ��Ŀ��ͬ
			List<String> bodypks = new ArrayList<String>();
			List<ArapBillMapVO> F2ForSoList = new ArrayList<ArapBillMapVO>(); // �����տ�,//
																				// �ֹ�����һ���տ
			String billclass = ((BaseAggVO) vos[0]).getHeadVO().getBillclass();
			boolean ifF1Uneffect = billclass != null && billclass.equals(IBillFieldGet.YF);// �Ƿ�Ӧ��������ˣ��ǵĻ��򷴺�������Ҫ֪ͨ��������ӿ�
			for (AggregatedValueObject vo : vos) {
				Integer src_syscode = (Integer) vo.getParentVO().getAttributeValue(IBillFieldGet.SRC_SYSCODE);
				BaseItemVO[] childrenVO = (BaseItemVO[]) vo.getChildrenVO();

				//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
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
							// ���ۺ�ͬ-�տ
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
							// Ӧ���� -��������||ִ�мƻ� -���
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
			// ��ʽ���ɵ���˺󷴺���
			try {
				boolean isfkpc = false;// �Ƿ�Ӧ��������ˣ��ǵĻ��򷴺�������Ҫ֪ͨ��������ӿ�
				if (ifF1Uneffect) {
					isfkpc = true;
				}
				if (pks != null && pks.size() > 0) {
					Collection<ArapBillMapVO> billMapData = new BaseDAO().retrieveByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_billid", pks.toArray(new String[] {}), true));
					this.doUnVerifyWithMap(billMapData, isfkpc);
					this.updateBillMapYbye(billMapData, false);
					// �����տ� �ֹ������տ
					if (billMapData == null || billMapData.size() == 0) {
						if (F2ForSoList != null && !F2ForSoList.isEmpty()) {
							// �����տ� �ֹ�����һ�� �տ ���տ������Ԥռ����Ӧ�յ�����
							this.doUnVerifyWithMap(F2ForSoList, isfkpc);
						}
					}
				}
				// ��Ŀ��ͬ����
				if (pcmList != null && !pcmList.isEmpty()) {
					this.doUnVerifyWithMap(pcmList, isfkpc);
					;
				}
				// �����ʽ�ĸ����ų̵���
				if (psList != null && !psList.isEmpty()) {
					this.doUnVerifyWithMap(psList, Boolean.TRUE);
				}
				if (soList != null && !soList.isEmpty()) {
					// ����-�տ������Ԥռ����Ӧ�յ�����
					this.doUnVerifyWithMap(soList, isfkpc);
				}
				//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//				if (allList != null && !allList.isEmpty()) {
//					// ��ͬ-�տ����Ԥռ�����
//					this.doUnVerifyWithMap(allList, isfkpc);
//				}
				if (htList != null && !htList.isEmpty()) {
					// ��ͬ-�տ����Ԥռ�����
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
				//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip  ��֪��Ϊʲô����δ��룬��ע�͵�
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
		// ��ѯ��������
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
																															 * "����"
																															 */+ substring + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0259")/*
																																																								 * @res
																																																								 * "�Ѿ����к���������ȡ�����"
																																																								 */);

		}
		// �Ƿ�����������洦��
		Collection<AgiotageChildVO> agiotagedata = dao.retrieveByClause(AgiotageChildVO.class, insql+"and not exists (select 1 from ARAP_VERIFYDETAIL where "+insql+" )", fields);
		if (agiotagedata != null && agiotagedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0260")/*
																															 * @res
																															 * "�Ѿ�����������棬����ȡ������"
																															 */);
		}
		// �Ƿ��������˴���
		Collection<DebtTransferVO> debtdata = dao.retrieveByClause(DebtTransferVO.class, insql, fields);
		if (debtdata != null && debtdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0261")/*
																															 * @res
																															 * "�Ѿ��������ˣ�����ȡ������"
																															 */);
		}
		// �Ƿ��������˷�������
		Collection<BaddebtsOcchVO> badLosedata = dao.retrieveByClause(BaddebtsOccuVO.class, insql, fields);
		if (badLosedata != null && badLosedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0262")/*
																															 * @res
																															 * "�Ѿ��������˷���������ȡ������"
																															 */);
		}
		// �Ƿ����������ջش���
		Collection<BaddebtsRechVO> badBackdata = dao.retrieveByClause(BaddebtsReceVO.class, insql, fields);
		if (badBackdata != null && badBackdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0263")/*
																															 * @res
																															 * "�Ѿ����������ջأ�����ȡ������"
																															 */);
		}
	}

	// ѡ�е��ݽ��к����Գ�   �������۶������ɵ����ⲿ�ĺ����Գ壬����src_itemid����
	private void billRBVerify(AggregatedValueObject[] vos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException, MetaDataException {
		for (AggregatedValueObject vo : vos) {
			BaseBillVO parentVO = (BaseBillVO) vo.getParentVO();
			String primaryKey = parentVO.getPrimaryKey();
			// BaseItemVO[] itemVOs = (BaseItemVO[]) vo.getChildrenVO();
			// ���۶����ⲿ�����Գ�
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
				// ���ֱ��� �Է�
				for (NCObject data : objectByCond) {
					ArapBusiDataVO busiDataVO = (ArapBusiDataVO) data.getContainmentObject();
					// ���۶����տ�Ԥռ��
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
				// �����������
				if (aggVOList != null && aggVOList.size() > 0) {
					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);

					// ���²�ѯ���ݱ������Ϣ
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
		//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//		List<ArapBusiDataVO> dataList = new ArrayList<ArapBusiDataVO>();
//		List<ArapBusiDataVO> jfdataList = new ArrayList<ArapBusiDataVO>();
//		List<ArapBusiDataVO> dfdataList = new ArrayList<ArapBusiDataVO>();
//		ArrayList<AggverifyVO> aggVOList = new ArrayList<AggverifyVO>();
//		for (AggregatedValueObject aggvo : vos) {
//			BaseItemVO[] childrenVO = (BaseItemVO[]) aggvo.getChildrenVO();
//			// �����а��� ���ۺ�ͬ���߲ɹ���ͬ������Ҫ����
//			for (BaseItemVO childvo : childrenVO) {
//				this.aggMap.clear();
//				String pk_item = childvo.getPrimaryKey();
//				Object contractno = childvo.getAttributeValue(IBillFieldGet.CONTRACTNO);
//				String currtype = (String) childvo.getAttributeValue(IBillFieldGet.PK_CURRTYPE);
//				String pk_billtype = (String) childvo.getAttributeValue(IBillFieldGet.PK_BILLTYPE);
//				String pk_org = (String) childvo.getAttributeValue(IBillFieldGet.PK_ORG);
//				// ��ͬ��Ϊnull ����
//				if (contractno == null) {
//					continue;
//				}
//
//				// ��ѯҪ����������
//				String qrySql = " pk_item ='" + pk_item + "' or (pk_org ='" + pk_org + "'" + " and pausetransact ='N' " + " and pk_currtype ='" + currtype + "'" + "and pk_billtype !='" + pk_billtype
//						+ "' and contractno ='" + contractno + "' and pk_item <> '" + pk_item + "' and money_bal<> 0)";
//				NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, qrySql, false);
//				if (!ArrayUtils.isEmpty(ncObjects)) {
//					for (NCObject obj : ncObjects) {
//						ArapBusiDataVO dataVO = (ArapBusiDataVO) obj.getContainmentObject();
//						dataList.add(dataVO);
//					}
//					// ���������
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
//					// �����������
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
		//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip 
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
//			// ��ѯҪ����������
//			String qrySql = " (money_bal <> 0 and pk_item ='" + pk_item + "') or (pk_org ='" + pk_org + "'" + " and pausetransact ='N' " + " and pk_currtype ='" + currtype + "'" 
//			+ "and pk_billtype in " + billType + " and contractno ='"
//					+ contractno + "' and pk_item <> '" + pk_item + "' and money_bal<> 0)";
//			NCObject[] ncObjects = MDPersistenceService.lookupPersistenceQueryService().queryBillOfNCObjectByCond(ArapBusiDataVO.class, qrySql, false);
//			if (!ArrayUtils.isEmpty(ncObjects)) {
//				for (NCObject obj : ncObjects) {
//					ArapBusiDataVO dataVO = (ArapBusiDataVO) obj.getContainmentObject();
//					dataList.add(dataVO);
//					// �Ƚ�ts ʹ��
//					busiDataTsMap.put(dataVO.getPk_busidata(), dataVO.getTs().toString());
//					com.setBusiDataTsMap(busiDataTsMap);
//				}
//				// ���������
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
//				// �����������
//				if (aggVOList != null && aggVOList.size() > 0) {
//					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
//				}
//			}
//		}
		//del end

	}

	// ������ ��billmap�� ybye�ֶθ�ֵΪ0
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
		// ��ѯ��������
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
																															 * "����"
																															 */
					+ substring + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0259")/*
																													 * @
																													 * res
																													 * "�Ѿ����к���������ȡ�����"
																													 */);

		}
		// �Ƿ�����������洦��
		Collection<AgiotageChildVO> agiotagedata = dao.retrieveByClause(AgiotageChildVO.class, insql + "and not exists (select 1 from ARAP_VERIFYDETAIL where " + insql + " )", fields);
		if (agiotagedata != null && agiotagedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0260")/*
																															 * @
																															 * res
																															 * "�Ѿ�����������棬����ȡ������"
																															 */);
		}
		// �Ƿ��������˴���
		Collection<DebtTransferVO> debtdata = dao.retrieveByClause(DebtTransferVO.class, insql, fields);
		if (debtdata != null && debtdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0261")/*
																															 * @
																															 * res
																															 * "�Ѿ��������ˣ�����ȡ������"
																															 */);
		}
		// �Ƿ��������˷�������
		Collection<BaddebtsOcchVO> badLosedata = dao.retrieveByClause(BaddebtsOccuVO.class, insql, fields);
		if (badLosedata != null && badLosedata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0262")/*
																															 * @
																															 * res
																															 * "�Ѿ��������˷���������ȡ������"
																															 */);
		}
		// �Ƿ����������ջش���
		Collection<BaddebtsRechVO> badBackdata = dao.retrieveByClause(BaddebtsReceVO.class, insql, fields);
		if (badBackdata != null && badBackdata.size() > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0263")/*
																															 * @
																															 * res
																															 * "�Ѿ����������ջأ�����ȡ������"
																															 */);
		}

	}

	protected Object[] transform(AggregatedValueObject[] vos) throws BusinessException {
		List<SuperVO> insertvos = new ArrayList<SuperVO>();
		List<SuperVO> updatevos = new ArrayList<SuperVO>();
		List<String> delvos = new ArrayList<String>();
		List<String> billPKList = new ArrayList<String>();

		Object[] ret = new Object[] { insertvos, updatevos, delvos };
		// ���ݵ���������ѯЭ���
		for (AggregatedValueObject aggvo : vos) {
			billPKList.add(aggvo.getParentVO().getPrimaryKey());
		}
		TermVO[] termVOs = NCLocator.getInstance().lookup(IArapTermItemPubQueryService.class).queryTermVOByBillParentPK(billPKList.toArray(new String[0]));
		this.termVOMap = this.getTermVOByItemPK(termVOs);
		
		//add chenth 20161212 ֧�������Ѻ���
		//add by weiningc 20171012 633������65 end
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
		//add by weiningc 20171012 633������65 end
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

	// ��������ʱ����ͷ����ͬ����������ֶΣ����ձ�����ȡֵ
	static String[] itemKeys = { IBillFieldGet.CUSTOMER, IBillFieldGet.SUPPLIER, IBillFieldGet.PK_DEPTID, IBillFieldGet.PK_DEPTID_V, IBillFieldGet.PK_PSNDOC };

	protected List<ArapBusiDataVO> transform(SuperVO item, SuperVO head) throws BusinessException {
		ArapBusiDataVO retvo = new ArapBusiDataVO();
		
		//add chenth 20161212 ֧�������Ѻ���
		//add by weiningc 20171012 633������65 end
		RecpaytypeVO recPayTypeVO = recpaytypeMap.get(item.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE));
		if(recPayTypeVO != null ){
			retvo.setIsbankcharges(recPayTypeVO.getIsbankcharges());
		}
		//add end
		//add by weiningc 20171012 633������65 end
		
		String[] attrs = retvo.getAttributeNames();
		for (String attr : attrs) {
			Object attributeValue = item.getAttributeValue(attr);
			if (attributeValue == null && !Arrays.asList(BillRegisterForBusiDataBO.itemKeys).contains(attr)) {
				attributeValue = head.getAttributeValue(attr);
			}
			retvo.setAttributeValue(attr, attributeValue);
			// ��֧��Ŀ
			if (attr.equals(ArapBusiDataVO.PK_COSTSUBJ)) {
				retvo.setAttributeValue(ArapBusiDataVO.PK_COSTSUBJ, item.getAttributeValue(IBillFieldGet.PK_SUBJCODE));
			}
			// ��˰���۸�ֵ
			if (attr.equals(ArapBusiDataVO.PRICE)) {
				retvo.setAttributeValue(ArapBusiDataVO.PRICE, item.getAttributeValue(IBillFieldGet.TAXPRICE));
			}
			// ��������
			if (attr.equals(ArapBusiDataVO.PK_PCORG)) {
				retvo.setAttributeValue(ArapBusiDataVO.PK_PCORG, item.getAttributeValue(IBillFieldGet.PK_PCORG));
			}
			// ����ֶ�Ϊ�ո�Ĭ��ֵ0
			if (attributeValue == null) {
				if (attr.equals(ArapBusiDataVO.GLOBAL_MONEY_BAL) || attr.equals(ArapBusiDataVO.GROUP_MONEY_BAL) || attr.equals(ArapBusiDataVO.GLOBAL_MONEY_DE)
						|| attr.equals(ArapBusiDataVO.GLOBAL_MONEY_CR) || attr.equals(ArapBusiDataVO.GROUP_MONEY_DE) || attr.equals(ArapBusiDataVO.GROUP_MONEY_CR)) {
					retvo.setAttributeValue(attr, UFDouble.ZERO_DBL);
				}
			}
		}
		retvo.setPk_bill(head.getPrimaryKey());
		retvo.setPk_item(item.getPrimaryKey());
		// ���ݱ�ͷԭ�ҽ��
		retvo.setSum_headmoney((UFDouble) head.getAttributeValue(IBillFieldGet.MONEY));
		// ���ݱ���ԭ�ҽ��
		String billcalss = (String) item.getAttributeValue(IBillFieldGet.BILLCLASS);
		if (billcalss.equals(IBillFieldGet.YS) || billcalss.equals(IBillFieldGet.FK)) {
			retvo.setSum_itemmoney((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_DE));
		} else {
			retvo.setSum_itemmoney((UFDouble) item.getAttributeValue(IBillFieldGet.MONEY_CR));
		}
		// ����״̬
		retvo.setBillstatus(Integer.valueOf(1)); // Ĭ������Ч 1������ ��������6
		// ��ͷ��Ŀ
		retvo.setHeadsubjcode((String) head.getAttributeValue("subjcode"));
		// ҵ������
		retvo.setPk_busitype((String) head.getAttributeValue(ArapBusiDataVO.PK_BUSITYPE));
		retvo.setOperator((String) head.getAttributeValue(IBillFieldGet.BILLMAKER));
		// �������� ȡ�����
		// retvo.setPk_pcorg((String)head.getAttributeValue(ArapBusiDataVO.PK_PCORG));
		// ���ݱ��
		retvo.setBillno((String) head.getAttributeValue(ArapBusiDataVO.BILLNO));
		// �ݹ���־
		retvo.setEstflag(head.getAttributeValue(ArapBusiDataVO.ESTFLAG) == null ? Integer.valueOf(0) : (Integer) head.getAttributeValue(ArapBusiDataVO.ESTFLAG));
		// ��ѯ�ո���Э�������
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
				// �ͻ�����pk ��ֵ
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
				// ��Ӧ�̵���pk ��ֵ
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
		// Logger.debug("�����ӱ�pk==" + busidataVO.getPk_item());
		// TermVO[] termVOs =
		// NCLocator.getInstance().lookup(IArapTermItemPubQueryService.class).queryTermVOByBillItemPK(new
		// String[]{busidataVO.getPk_item()});
		// Logger.debug("��ѯ�����ո���Э�������=" + (ArrayUtils.isEmpty(termVOs) ? 0 :
		// termVOs.length));
		// TermVO[] termVOs = termlist.toArray(new TermVO[0]);
		List<TermVO> termlist = this.termVOMap.get(busidataVO.getPk_item());
		List<ArapBusiDataVO> busiDataList = new ArrayList<ArapBusiDataVO>();
		if (termlist != null && termlist.size() > 0) {
			for (TermVO termVO : termlist) {
				// ���Э����ж�����¼������ArapBusiDataVO�����������
				ArapBusiDataVO copyVO = (ArapBusiDataVO) busidataVO.clone();
				copyVO.setExpiredate(termVO.getExpiredate());
				// �ڿص�����
				copyVO.setInnerctldeferdays(termVO.getInnerctldeferdays());
				copyVO.setInsurance(termVO.getInsurance());
				copyVO.setPk_termitem(termVO.getPk_termitem());
				// �跽ԭ�ҽ��
				copyVO.setMoney_de(termVO.getMoney_de() == null ? UFDouble.ZERO_DBL : termVO.getMoney_de());
				// �跽���ҽ��
				copyVO.setLocal_money_de(termVO.getLocal_money_de() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_de());
				// ����ԭ�ҽ��
				copyVO.setMoney_cr(termVO.getMoney_cr() == null ? UFDouble.ZERO_DBL : termVO.getMoney_cr());
				// �������ҽ��
				copyVO.setLocal_money_cr(termVO.getLocal_money_cr() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_cr());
				// ԭ�����
				copyVO.setMoney_bal(termVO.getMoney_bal() == null ? UFDouble.ZERO_DBL : termVO.getMoney_bal());
				// �������
				copyVO.setLocal_money_bal(termVO.getLocal_money_bal() == null ? UFDouble.ZERO_DBL : termVO.getLocal_money_bal());
				// ���Ž跽���ҽ��
				copyVO.setGroup_money_de(termVO.getGroupdebit() == null ? UFDouble.ZERO_DBL : termVO.getGroupdebit());
				// ���Ŵ������ҽ��
				copyVO.setGroup_money_cr(termVO.getGroupcredit() == null ? UFDouble.ZERO_DBL : termVO.getGroupcredit());
				// ���ű������
				copyVO.setGroup_money_bal(termVO.getGroupbalance() == null ? UFDouble.ZERO_DBL : termVO.getGroupbalance());
				// ȫ�ֽ跽���ҽ��
				copyVO.setGlobal_money_de(termVO.getGlobaldebit() == null ? UFDouble.ZERO_DBL : termVO.getGlobaldebit());
				// ȫ�ִ������ҽ��
				copyVO.setGlobal_money_cr(termVO.getGlobalcredit() == null ? UFDouble.ZERO_DBL : termVO.getGlobalcredit());
				// ȫ�ֱ������
				copyVO.setGlobal_money_bal(termVO.getGlobalbalance() == null ? UFDouble.ZERO_DBL : termVO.getGlobalbalance());
				// �跽����
				copyVO.setQuantity_de(termVO.getQuantity_de() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_de());
				// ��������
				copyVO.setQuantity_cr(termVO.getQuantity_cr() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_cr());
				// �������
				copyVO.setQuantity_bal(termVO.getQuantity_bal() == null ? UFDouble.ZERO_DBL : termVO.getQuantity_bal());
				// Ԥռ���
				copyVO.setOccupationmny(termVO.getOccupationmny());//
				copyVO.setIstransin(UFBoolean.valueOf(false));// Ĭ�Ϸ�ת�룬ת��N��ת��Y
				copyVO.setPrepay(termVO.getPrepay() != null && termVO.getPrepay().booleanValue() ? 1 : 0);
				busiDataList.add(copyVO);
			}
		}
		return busiDataList;
	}

	protected void doInsertRegister(List<ArapBusiDataVO> insertdatas) throws BusinessException {
		Logger.debug("����arap_busidata ��  ���� =" + insertdatas.size());
		new BaseDAO().insertVOList(insertdatas);
	}

	protected void doUpdateRegister(List<ArapBusiDataVO> updatedatas) throws BusinessException {
		new BaseDAO().updateVOList(updatedatas);
	}

	protected void doDeleteRegisterByBillid(String[] billids) throws BusinessException {
		// ҵ�����ݼ���
		if (!PKLock.getInstance().addBatchDynamicLock(billids)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0", "02006ver-0257")/*
																															 * @
																															 * res
																															 * "��������"
																															 */);
		}
		new BaseDAO().deleteByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_bill", billids, true));
	}

	protected void doDeleteRegisterByItemid(String[] itemids) throws BusinessException {
		new BaseDAO().deleteByClause(ArapBusiDataVO.class, SqlUtils.getInStr("pk_item", itemids, true));
	}

	// ������
	private void doUnVerifyWithMap(Collection<ArapBillMapVO> mapvos, Boolean isFkpc) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}
		Map<String, String> businoMap = new HashMap<String, String>();

		List<String> pks = new ArrayList<String>();
		for (ArapBillMapVO vo : mapvos) {
			pks.add(vo.getT_itemid());
		}
		// �����ӱ�
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
		// ��֯�����ۺ�VO
		Set<String> keyset = businoMap.keySet();
		keyset.toArray(new String[keyset.size()]);
		NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).onCancelVerify(keyset.toArray(new String[keyset.size()]), isFkpc);
	}

	// ����

	private void doVerifyWithMap(Collection<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com, String nouse) throws BusinessException {
		if (mapvos == null || mapvos.size() == 0) {
			return;
		}

		// ���ݲ���ȷ���Ƿ�Ҫ����Ԥ�ո���־����
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
			//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//			if (mapvos.iterator().next().getS_system() == 1) {// Ӧ��
//				UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);// Ӧ��
			if (mapvos.iterator().next().getS_system() == 0) {// Ӧ��
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
		// ������ ע�� �ٴ�
		Map<String, String> item_top_termMap = new HashMap<String, String>();
		for (ArapBillMapVO vo : mapvos) {
			// ����ϵͳ��Դ�ж� �Ƿ����Ը�������
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype()) || ArapBillDealVOConsts.BILLTYPE_36D7.equals(vo.getS_billtype())) {
				// �ж� ֻ���ʽ�ĸ����ų� �ſ��԰�Э���к���
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
				item_top_termMap.put(vo.getS_itemid(), vo.getS_termid());
			} else {
				if (vo.getS_itemid() == null) { // Դͷid Ϊ�� ��û�����ݲ��ú���
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
		// �Ƚ�ts ʹ��
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);
		List<ArapBusiDataVO> jfList = new ArrayList<ArapBusiDataVO>();
		List<ArapBusiDataVO> dfList = new ArrayList<ArapBusiDataVO>();
		// ���ֱ��� �Է�
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
		// ����
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
		// ������
		if (mapVOs2[0].getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D1) || mapVOs2[0].getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D7)) {
			for (ArapBillMapVO vo : mapvos) {
				if (vo.getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D1) || vo.getS_billtype().equals(ArapBillDealVOConsts.BILLTYPE_36D7)) {
					// �������룬s_item =null,��s_termitem
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
			// ����� ������
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
							// �����۶����տ�Ԥ���� ռ��
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
				// �����۶����տ�Ԥ���� ռ��
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
		// �����������
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

	public static String SAMVERIFYFLAG = "SAMVERIFYFLAG"; // ͬ���ֺ���

	public static String UNVERIFYFLAG = "UNVERIFYFLAG"; // ����ֺ���

	public static String HCSAMVERIFYFLAG = "HCSAMVERIFYFLAG"; // ͬ���ֺ�����

	public static String HCUNVERIFYFLAG = "HCUNVERIFYFLAG"; // ����ֺ�����

	/**
	 * �ո��ڲ���������
	 * 
	 * @param mapvos
	 * @param ruleVOMap
	 * @param com
	 * @throws BusinessException
	 */
	private void doArapVerifyWithMap(Collection<ArapBillMapVO> mapvos, Hashtable<String, DefaultVerifyRuleVO> ruleVOMap, VerifyCom com) throws BusinessException {
		// Ч�����⣬��Ϊ��������
		if (mapvos != null && mapvos.size() > 0) {
			List<ArapBillMapVO> billMapDataArap = new ArrayList<ArapBillMapVO>();
			List<ArapBillMapVO> billMapDataUnRb = new ArrayList<ArapBillMapVO>();
			Map<String, List<ArapBillMapVO>> billMapDataUnSam = new HashMap<String, List<ArapBillMapVO>>();
			for (ArapBillMapVO vo : mapvos) {
				if (StringUtil.isEmpty(vo.getSettlecurr()) || vo.getSettlemoney() == null) {
					billMapDataArap.add(vo);
				} else {
					if (IBillFieldGet.F0.equals(vo.getS_billtype()) && IBillFieldGet.F3.equals(vo.getT_billtype())) { // ����Ӧ��������
						billMapDataArap.add(vo);
					} else if (vo.getS_billtype().equals(vo.getT_billtype())) {// ���
						billMapDataUnRb.add(vo);
					} else {
						if (vo.getPk_currtype().equals(vo.getSettlecurr())) {// ͬ����
							billMapDataArap.add(vo);
						} else { // ����ְ����������α��ֻ���
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
	 *            ����map
	 * @param ruleVOMap
	 *            ��������
	 * @param isSamVerify
	 *            �Ƿ�ͬ����
	 * @param isRed
	 *            �Ƿ���
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
		// ����ϵͳ��Դ�ж� �Ƿ����Ը�������
		for (ArapBillMapVO vo : mapvos) {
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				// �ж� ֻ���ʽ�ĸ����ų� �ſ��԰�Э���к���
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
			} else {
				if (vo.getS_itemid() == null) { // Դͷid Ϊ�� ��û�����ݲ��ú���
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

		// �Ƚ�ts ʹ��
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);

		// �Ա����з���
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

		// ȡ���ε�������
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
			// �����ų�
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				String stermid = vo.getS_termid();
				String titemid = vo.getT_itemid();
				// ����ԴЭ���id�����Դ�ӱ�id
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
				//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//				continue;
				return;
			} else {
				if (BillRegisterForBusiDataBO.SAMVERIFYFLAG.equals(verifyFlag)) {
					// �˻�����������Ӧ�յ������������
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
			// ȡ������֯������˳�����ʱʹ��
			this.setPkorg(vo.getPk_org());
			// �жϽ����
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
			// �˻����������⴦��
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

		if (com.isArapVerify()) {// �ϲ�Ϊ��������
			//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
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
		// �����������
		if (aggVOList != null && aggVOList.size() > 0) {
			NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
		}
	}

	private void dealOrgScomment(ArrayList<AggverifyVO> aggVOList) {
		for (AggverifyVO aggVO : aggVOList) {
			VerifyDetailVO[] childrenVO = (VerifyDetailVO[]) aggVO.getChildrenVO();
			for (VerifyDetailVO verifyvo : childrenVO) {
				// ���մ���ժҪ
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
																																																		 * "���գ�"
																																																		 */);
					} else {
						verifyvo.setScomment(verifyvo.getScomment() + " " + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006pub0322_0", "02006pub0322-0034", null, new String[] { orgName })/*
																																																		 * @
																																																		 * res
																																																		 * "������"
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
		// ������ ע�� �ٴ�
		for (ArapBillMapVO vo : mapvos) {
			// ����ϵͳ��Դ�ж� �Ƿ����Ը�������
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				// �ж� ֻ���ʽ�ĸ����ų� �ſ��԰�Э���к���
				termPkList.add(vo.getS_termid());
				itemPkList.add(vo.getT_itemid());
			} else {
				if (vo.getS_itemid() == null) { // Դͷid Ϊ�� ��û�����ݲ��ú���
				} else {
					pks.add(vo.getS_itemid());
					pks.add(vo.getT_itemid());
				}
			}
		}

		// ���ݲ���ȷ���Ƿ�Ҫ����Ԥ�ո���־����
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
			//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip
//			if (mapvos.iterator().next().getS_system() == 1) {// Ӧ��
//				UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);
			UFBoolean isNeed = SysInit.getParaBoolean(this.getPkorg(), SysinitConst.AP35);// Ӧ��
			if (mapvos.iterator().next().getS_system() == 0) {// Ӧ��
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

		// �Ƚ�ts ʹ��
		Map<String, String> busiDataTsMap = new HashMap<String, String>();
		for (ArapBusiDataVO vo : busiList) {
			busiDataTsMap.put(vo.getPk_busidata(), vo.getTs().toString());
		}
		com.setBusiDataTsMap(busiDataTsMap);

		// �Ա����з���
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
			// �����ų�
			if (ArapBillDealVOConsts.BILLTYPE_36D1.equals(vo.getS_billtype())) {
				String stermid = vo.getS_termid();
				String titemid = vo.getT_itemid();
				// ����ԴЭ���id�����Դ�ӱ�id
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
				// �˻�����������Ӧ�յ������������
				if (svolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F0) && tvolist.getVolist().get(0).getPk_billtype().equals(IBillFieldGet.F3)) {
					svolist.setDealMoney(vo.getYbje().multiply(-1), com.isSoVerify(), aggMap);
					tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap);
				} else {
					//update chenth 20161212  ��������������⴦��
					//add by weiningc 20171012 633������65 start
					if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
						svolist.setDealMoney(UFDouble.ZERO_DBL, com.isSoVerify(),aggMap, com.getReserveFlag());
					}else{
						svolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap, com.getReserveFlag());
					}
					tvolist.setDealMoney(vo.getYbje(), com.isSoVerify(), aggMap, com.getReserveFlag());
				}
			}
			// ȡ������֯������˳�����ʱʹ��
			this.setPkorg(vo.getPk_org());
			// �жϽ����
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
			// �˻����������⴦��
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
				//del chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip  ��֪��Ϊʲô������δ��룬�������ۿۺ���ʱû����δ��룬��׼��ƷҲû��
//				ArrayList<AggverifyVO> aggVOList = getAggVerifyVO(aggMap);
//				dealOrgScomment(aggVOList);
//				// �����������
//				if (aggVOList != null && aggVOList.size() > 0) {
//					NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
//				}
//				
//				// �Ƚ�ts ʹ��
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

		if (com.isArapVerify() || com.getReserveFlag().intValue() == VerifyDetailVO.reserve_pcm) {// �ϲ�Ϊ��������
			aggMap = this.onVerify(com, ruleVOMap, jf_vos_list.toArray(new ArapBusiDataVO[] {}), df_vos_list.toArray(new ArapBusiDataVO[] {}));
		}

		ArrayList<AggverifyVO> aggVOList = this.getAggVerifyVO(aggMap);
		this.dealOrgScomment(aggVOList);
		// �����������
		if (aggVOList != null && aggVOList.size() > 0) {
			NCLocator.getInstance().lookup(IArapVerifyLogPrivate.class).save(aggVOList, ruleVOMap, com);
		}
	}

	private Map<String, ArrayList<AggverifyVO>> onVerify(VerifyCom com, Map<String, DefaultVerifyRuleVO> ruleVOMap, ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos) {

		// �Զ����� ֻ�к����Գ壬ͬ���ֺ���

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
		//update chenth 20190722 ����ͨ�油��: NCM_65_ARAP_ͨ���ۺ�20190704.zip �������֮ǰ����forѭ���У���ȷ��ʲôԭ�������Ѵ���ͱ�׼���붼û�з���for��
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
					// ��˺��Զ����������ñ�־λ ������¼��ѯ������
					for (VerifyDetailVO verifyvo : childrenVO) {
						verifyvo.setIsauto(UFBoolean.TRUE);
						detailVOList.add(verifyvo);
					}
				}
				// ��֯�ۺ�vo
				AggverifyVO aggVO = new AggverifyVO();
				aggVO.setChildrenVO(detailVOList.toArray(new VerifyDetailVO[0]));
				// ����ȫ�� ���ű�λ��
				VerifyCom.calGroupAndGloablMny(aggVO);
				ArrayList<AggverifyVO> aggvolist = (ArrayList) VerifyCom.verifySumData(aggVO);

				resultList.addAll(aggvolist);
			}
		}
		return resultList;
	}

	/**
	 * ����ֺ�������
	 * 
	 * @param vo
	 * @param busiDate
	 *            ȡ���ε�����������Ӧ�Ļ��ʡ�
	 * @return
	 * @throws BusinessException
	 */
	private Hashtable<String, DefaultVerifyRuleVO> createUnVerifyRuleVO(ArapBillMapVO vo, UFDate busiDate) throws BusinessException {
		Hashtable<String, DefaultVerifyRuleVO> ruleMap = new Hashtable<String, DefaultVerifyRuleVO>();
		DefaultVerifyRuleVO rulevo = this.getVerifyRuleVO();
		rulevo.setM_verifyName("UNSAME_VERIFY");
		// �Ƿ����֯ ����֯�б�ȡ���ű��ң�����ȡ��֯����
		String pk_group = InvocationInfoProxy.getInstance().getGroupId();
		if (vo.getPk_org().equals(vo.getPk_org1())) {
			pk_group = vo.getPk_org();
		}
		String verifyCurr = CurrencyRateUtilHelper.getInstance().getLocalCurrtypeByOrgID(pk_group);
		rulevo.setM_verifyCurr(verifyCurr);
		// UFDate busiDate =new
		// UFDate(InvocationInfoProxy.getInstance().getBizDateTime());
		// �ж���Դ�����Ƿ�跽
		if (IBillFieldGet.F2.equals(vo.getS_billtype()) || IBillFieldGet.F1.equals(vo.getS_billtype())) {
			// �跽���֡�����
			int rateType = ArapBillCalUtil.getRateType(vo.getT_billtype(), pk_group);
			rulevo.setM_debitCurr(vo.getPk_currtype());
			rulevo.setM_jfbz2zjbzHL(Currency.getRate(pk_group, vo.getPk_currtype(), verifyCurr, busiDate, rateType));
			rulevo.setM_debttoBBExchange_rate(Currency.getRate(vo.getPk_org(), vo.getPk_currtype(), busiDate, rateType));
			// �������֡�����
			int rateType1 = ArapBillCalUtil.getRateType(vo.getS_billtype(), pk_group);
			rulevo.setM_creditCurr(vo.getSettlecurr());
			rulevo.setM_dfbz2zjbzHL(Currency.getRate(pk_group, vo.getSettlecurr(), verifyCurr, busiDate, rateType1));
			rulevo.setM_creditoBBExchange_rate(Currency.getRate(vo.getPk_org1(), vo.getSettlecurr(), busiDate, rateType1));
		} else {
			// �跽���֡�����
			int rateType = ArapBillCalUtil.getRateType(vo.getS_billtype(), pk_group);
			rulevo.setM_debitCurr(vo.getSettlecurr());
			rulevo.setM_jfbz2zjbzHL(Currency.getRate(pk_group, vo.getSettlecurr(), verifyCurr, busiDate, rateType));
			rulevo.setM_debttoBBExchange_rate(Currency.getRate(vo.getPk_org1(), vo.getSettlecurr(), busiDate, rateType));

			// �������֡�����
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
