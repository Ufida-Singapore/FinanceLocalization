package nc.bs.cmp.accountagiotage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.cmp.accountagiotage.ace.bp.AceAccountagiotageDeleteBP;
import nc.bs.cmp.accountagiotage.ace.bp.AceAccountagiotageInsertBP;
import nc.bs.cmp.util.SqlUtils;
import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.uap.lock.PKLock;
import nc.cmp.agiotage.utils.CmpAgiotageBusiUtils;
import nc.cmp.bill.util.CmpBillInterfaceProxy;
import nc.itf.bd.cashaccount.ICashAccountQuerySerivce;
import nc.itf.cmp.accountagiotage.IAccountAgiotageQueryService;
import nc.itf.mdm.cube.IntegerResultSetProcessor;
import nc.jdbc.framework.SQLParameter;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.cmp.changebill.ICmpChangeBillPubQueryService;
import nc.pubitf.cmp.changebill.ICmpChangeBillPubService;
import nc.pubitf.uapbd.IBankaccPubQueryService;
import nc.vo.bd.bankaccount.BankAccSubVO;
import nc.vo.bd.cashaccount.CashAccountVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageAggVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageVO;
import nc.vo.cmp.agiotage.CMPAgiotageBzVO;
import nc.vo.cmp.agiotage.CMPAgiotageVO;
import nc.vo.cmp.agiotage.CMPDJCLBVO;
import nc.vo.cmp.agiotage.CMPRemoteTransferVO;
import nc.vo.cmp.bill.ChangeBillAggVO;
import nc.vo.cmp.exception.ExceptionHandler;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.tmpub.util.SqlUtil;

public class AccountAgiotageBO {
	/**
	 * AccountAgiotageBO ������ע�⡣
	 */
	public AccountAgiotageBO() {
		super();
	}

	/**
	 * SessionBean�ӿ��еķ�����
	 * <p>
	 * ���������EJB Container���ã���Զ��Ҫ����ĳ����е�������� ����
	 * <p>
	 * �������ڣ�(2001-2-15 16:34:02)
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	/**
	 * ��Ҫ���ܣ�ȡ�û���������ѡ�����ĳ�ʼ����Ϣ ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-7 20:29:07)
	 * ����޸����ڣ�(2001-8-7 20:29:07)
	 * 
	 * @author��wyan
	 * @return nc.vo.arap.agiotage.AgiotageVO
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public Hashtable getAllBzbmOfHisRecordAccount(CMPAgiotageVO vo) throws BusinessException {

		Hashtable vdata = new Hashtable();
		try {
			AccountAgiotageDMO dmo = new AccountAgiotageDMO();
			vdata = dmo.getAllBzbmOfHisRecord(vo);
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0033")/*
																													 * @res
																													 * "ȡ�û��������ʷ��¼"
																													 */
					+ ex); 
			throw ExceptionHandler.createException(ex.getMessage());
		}
		return vdata;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-10 14:30:51) ����޸����ڣ�(2001-8-10 14:30:51)
	 * 
	 * @author��wyan
	 * @return java.util.Vector
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public CMPRemoteTransferVO getHisRecordAccount(CMPAgiotageVO vo, Hashtable hBzDig) throws BusinessException {

		CMPRemoteTransferVO remoteVO = new CMPRemoteTransferVO();
		try {
			AccountAgiotageDMO dmo = new AccountAgiotageDMO();
			Vector vData = dmo.getHisRecord(vo, hBzDig);
			remoteVO.setTranData1(vData);
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0034")/*
																													 * @res
																													 * "�û���������ѡ�����ĳ�ʼ����Ϣ"
																													 */
					+ ex); 
			throw ExceptionHandler.createException(ex.getMessage());
		}
		return remoteVO;
	}

	/**
	 * ��Ҫ���ܣ�ȡ�û���������ѡ�����ĳ�ʼ����Ϣ ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-7 20:29:07)
	 * ����޸����ڣ�(2001-8-7 20:29:07)
	 * 
	 * @author��wyan
	 * @return nc.vo.arap.agiotage.AgiotageVO
	 * @param vo
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	public CMPRemoteTransferVO getInitDataAccount(CMPAgiotageVO vo) throws BusinessException {

		CMPRemoteTransferVO remoteVO = new CMPRemoteTransferVO();
		try {
			AccountAgiotageDMO dmo = new AccountAgiotageDMO();
			Hashtable vdata = dmo.getAllBzbm(vo);
			Vector<Vector<?>> initData = new Vector<Vector<?>>();
			java.util.Enumeration enumeration = vdata.elements();
			while (enumeration.hasMoreElements()) {
				String bzbm = enumeration.nextElement().toString().trim();
				vo.setBzbm(bzbm);
				Vector bzV = dmo.getInitCurrTypeInfo(vo);
				nc.vo.pub.lang.UFDate computeDate = dmo.getComputeDate(vo);
				String kjqj = CmpAgiotageBusiUtils.converDateToKJQJ(computeDate, vo.getDwbm());
				if (bzV.size() == 0)
					continue;
				bzV.set(2, kjqj);
				initData.addElement(bzV);
			}
			remoteVO.setTranData1(initData);
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0034")/*
																													 * @res
																													 * "�û���������ѡ�����ĳ�ʼ����Ϣ"
																													 */
					+ ex); 
			throw ExceptionHandler.createException(ex.getMessage());
		}
		return remoteVO;
	}

	/**
	 * ��Ҫ���ܣ� ��Ҫ�㷨�� �쳣������ �������ڣ�(2001-8-8 15:04:43) ����޸����ڣ�(2001-8-8 15:04:43)
	 * 
	 * @author��wyan
	 * @return java.util.Vector
	 * @param agioVO
	 *            nc.vo.arap.agiotage.AgiotageVO
	 */
	@SuppressWarnings("unchecked")
	public java.util.ArrayList onCalculateAccount(CMPAgiotageVO agioVO) throws BusinessException {

		ArrayList<CMPAgiotageBzVO> voMsgs = new ArrayList<CMPAgiotageBzVO>();

		List<String> pk_accountList = CmpAgiotageBusiUtils.getValueListByStringTOStringArrayMap(agioVO.getPkAccids());
		List<String> pk_cashaccountList = CmpAgiotageBusiUtils.getValueListByStringTOStringArrayMap(agioVO
				.getPkManageAccids());

		pk_accountList.addAll(pk_cashaccountList);

		boolean islock = false;

		try {
			islock = PKLock.getInstance().acquireBatchLock(pk_accountList.toArray(new String[0]), agioVO.getUser(),
					null);
			/*������,�ſ���*/
//			islock = true;
			if (!islock) {
				ExceptionHandler.cteateandthrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
						"03607agi-0035")/* @res "�˻�����ʧ��" */);
			}

			AccountAgiotageDMO dmo = new AccountAgiotageDMO();
			Object[] oRs = dmo.generateAccountAgiotageVO(agioVO);//dmo.onCalculate(agioVO);
			voMsgs = (ArrayList<CMPAgiotageBzVO>) oRs[1];
			/*Vector<ChangeBillAggVO> vZb = (Vector<ChangeBillAggVO>) oRs[0];
			 �����˻����浥�� 
			ChangeBillAggVO[] voZbs = new ChangeBillAggVO[vZb.size()];
			if (vZb.size() != 0) {
				vZb.copyInto(voZbs);
				voZbs = onGenerateBill(voZbs);
			}*/

			//huangzhen modify for accountagiotage 2012-2-10
			//����vouchid=ChangeBillAggVO��pk
			//ȡ�����ֶ�ӦVector<CMPAgiotageDJVO>
			//Map<String, Vector<CMPAgiotageDJVO>> bzbmTOAgiotageDJVOVecMap = (Map<String, Vector<CMPAgiotageDJVO>>) oRs[2];
			ArrayList<AccountAgiotageAggVO> a = (ArrayList<AccountAgiotageAggVO>) oRs[0];
			AccountAgiotageAggVO[] accAgiVO = a.toArray(new AccountAgiotageAggVO[0]);
			if(accAgiVO.length>0){
				//���ɻ�����浥
				AceAccountagiotageInsertBP accAgiInsertBP = new AceAccountagiotageInsertBP();
				accAgiInsertBP.insert(accAgiVO);
			}
			
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0036")/*
																													 * @res
																													 * "�����ʻ��������"
																													 */
					+ ex);
			ExceptionHandler.handleException(ex);
		} finally {
			if (islock == true && pk_accountList != null && pk_accountList.size() != 0) {
				PKLock.getInstance().releaseBatchLock(pk_accountList.toArray(new String[0]), agioVO.getUser(), null);
			}
		}

		return voMsgs;
	}

	/**
	 * ���õ��ݽӿ������˻�������浥�ݡ� �������ڣ�(2003-9-29 13:57:10)
	 * 
	 * @param voBill
	 *            nc.vo.arap.verify.VaryMoneyVerifyVO
	 */
	private ChangeBillAggVO[] onGenerateBill(ChangeBillAggVO[] voBill) throws BusinessException {
		ChangeBillAggVO[] changeBillAggVOs = null;
		try {
			ICmpChangeBillPubQueryService queryService = CmpBillInterfaceProxy.INSTANCE.getChangeBillPubQueryService();

			StringBuffer account = new StringBuffer("");

			for (ChangeBillAggVO bill : voBill) {
				for (CircularlyAccessibleValueObject childVo : bill.getChildrenVO()) {
					StringBuffer sbUAP = new StringBuffer("");
					sbUAP.append(" pk_org = '").append(bill.getParentVO().getAttributeValue("pk_org")).append("' ");
					sbUAP.append("and trade_type = 'DR' ");
					sbUAP.append("and bill_date = '").append(bill.getChildrenVO()[0].getAttributeValue("bill_date"))
							.append("' ");

					// �����˻�
					String pkBankAccount = (String) childVo.getAttributeValue("pk_account");
					// �ֽ��˻�
					String pkCashAccount = (String) childVo.getAttributeValue("mon_account");

					if (pkBankAccount == null || "".equals(pkBankAccount)) {
						pkBankAccount = (String) bill.getChildrenVO()[0].getAttributeValue("pk_oppaccount");
					}

					sbUAP.append("and (pk_account = '").append(pkBankAccount).append("' ");
					sbUAP.append("or pk_oppaccount = '").append(pkBankAccount).append("' ");
					sbUAP.append("or mon_account = '").append(pkCashAccount).append("')");

					int i = queryService.findBillDetailCountByCondition(sbUAP.toString());

					if (i > 0) {
						String accountCode = "";

						// ��ѯ�����˻��ӻ�
						Map<String, BankAccSubVO> map = NCLocator.getInstance().lookup(IBankaccPubQueryService.class)
								.queryBankAccSubByPk(new String[] { "code", "name" }, new String[] { pkBankAccount });

						if (map != null && map.size() != 0) {
							BankAccSubVO bankAccSubVO = map.get(pkBankAccount);
							accountCode = bankAccSubVO.getCode();
						}

						// ��ѯ�ֽ��˻��ӻ�
						String condition = " pk_cashaccount ='" + pkCashAccount + "'";
						CashAccountVO[] cashAccountVOs = NCLocator.getInstance().lookup(ICashAccountQuerySerivce.class)
								.queryCashAccountVOsByCondition(condition);

						if (cashAccountVOs != null && cashAccountVOs.length != 0) {
							CashAccountVO cashAccountVO = cashAccountVOs[0];
							accountCode = cashAccountVO.getCode();
						}

						if (!"".equals(account.toString())) {
							account.append(",");
						}
						account.append("[").append(accountCode).append("]");
					}
				}
			}

			if (!"".equals(account.toString())) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0037",null,new String[]{account.toString()})/*
																											 * @res
																											 * "�˻�{0}�Ѿ���������棬�����ظ�����"
																											 */);
			}

			ICmpChangeBillPubService proxy = CmpBillInterfaceProxy.INSTANCE.getChangeBillPubService();
			changeBillAggVOs = proxy.saveAndSign(voBill);
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0038")/*
																													 * @res
																													 * "���õ��ݽӿ������˻�������浥��"
																													 */
					+ ex);
			throw ExceptionHandler.createException(ex.getMessage());
		}
		return changeBillAggVOs;
	}

	/**
	 * ȡ���˻������������� �������ڣ�(2003-9-29 15:52:54)
	 * 
	 * @param vData
	 *            java.util.Vector
	 */
	@SuppressWarnings("unchecked")
	public void onCancelOperationAccount(Vector vData) throws BusinessException {
		boolean islock = false;
		ArrayList<String> pk_accountList = new ArrayList<String>();
		String pk_org = null;//���ڲ�ѯ�ڼ�
		try {
			ArrayList<String> idList = new ArrayList<String>();
			String[] m_vouchid = null;
			for (int i = 0; i < vData.size(); i++) {
				CMPDJCLBVO voClb = (CMPDJCLBVO) vData.elementAt(i);
				String vouchid = voClb.getVouchid();
				if(vouchid!=null && !"".equals(vouchid)) {
					String bill_no = voClb.getBill_no();

					if (!idList.contains(vouchid))
						idList.add(vouchid);

					if (!pk_accountList.contains(bill_no))
						pk_accountList.add(bill_no);
				}
			}
			//add by weiningc  20171205 �������ȡ���������δʵ�ֻ������,����Ӧ��ȡ�����¸��ڼ����ɵĻس������浥  start
			m_vouchid = idList.toArray(new String[0]);
			//��ѯ�˻�������浥
			AccountAgiotageAggVO[] aggvos = NCLocator.getInstance().lookup(IAccountAgiotageQueryService.class).queryBillsByPks(m_vouchid);
			ArrayList<UFDate> redBackDate = new ArrayList<UFDate>();
			
			if(aggvos != null && aggvos.length > 0) {
				for(AccountAgiotageAggVO aggvo : aggvos) {
					AccountAgiotageVO parentVO = aggvo.getParentVO();
					pk_org = aggvo.getParentVO().getPk_org();
					if(parentVO.getMemo().startsWith("2")) {//ժҪȷ���Ƿ�Ϊδʵ�ֻ������
						redBackDate.add(parentVO.getDbilldate());
					}
				}
			}
			//��¼��嵥�ݺ�
			ArrayList<String> redbillnos = new ArrayList<String>();
			//��¼���PK
			ArrayList<String> redPKs = new ArrayList<String>();
			AccountAgiotageAggVO[] redbackvos = this.addRedBackVO(redBackDate, pk_org); //��ѯ���������VO
			if(redbackvos != null && redbackvos.length > 0) {
				for(AccountAgiotageAggVO redvo : redbackvos) {
					if(redvo.getParentVO().getMemo().startsWith("3")) {
						redbillnos.add(redvo.getParentVO().getVbillno());
						redPKs.add(redvo.getParentVO().getPk_accountagiotage());
					}
				}
			}
			
			//����ѯ���Ļس嵥�ݵĵ��ݺź�pk������ϵͳ�������߼���,����һ����.
			if(redbillnos.size()>0 && redPKs.size()>0) {
				for(String redPK : redPKs) {
					idList.add(redPK);
				}
				for(String redbillno : redbillnos) {
					pk_accountList.add(redbillno);
				}
			}
			//add by weiningc  20171205 �������ȡ���������δʵ�ֻ������,����Ӧ��ȡ�����¸��ڼ����ɵĻس������浥   end
			
			if(pk_accountList!=null && pk_accountList.size()!=0) {
				islock = PKLock.getInstance().acquireBatchLock(pk_accountList.toArray(new String[0]), null, null);

				if (!islock) {
					ExceptionHandler.cteateandthrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0",
							"03607agi-0035")/* @res "�˻�����ʧ��" */);
				}

				/*ICmpChangeBillPubService proxy = CmpBillInterfaceProxy.INSTANCE.getChangeBillPubService();
				proxy.deleteAndUnSign(m_vouchid);*/
				//huangzhena modify to cancel accountagiotage
				//ɾ��������浥
				aggvos = NCLocator.getInstance().lookup(IAccountAgiotageQueryService.class).queryBillsByPks(idList.toArray(new String[0]));
				if(null!=aggvos&&aggvos.length>0){
					AceAccountagiotageDeleteBP bp = new AceAccountagiotageDeleteBP();
					bp.delete(aggvos);
				}
			}
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0039")/*
																													 * @res
																													 * "ȡ���˻�����������"
																													 */
					+ ex); 

			throw ExceptionHandler.createException(ex.getMessage());
		} finally {
			if (islock == true && pk_accountList != null && pk_accountList.size() != 0) {
				PKLock.getInstance().releaseBatchLock(pk_accountList.toArray(new String[0]), null, null);
			}
		}
	}
	/**
	 * �������ڲ�ѯ�س��˻��������VO
	 * @param pk_org 
	 * @param redBackDate �ò���Ϊδʵ�ֻ��������Ƶ�����
	 * @return
	 * @throws BusinessException 
	 */
	private AccountAgiotageAggVO[] addRedBackVO(ArrayList<UFDate> unachieveDate, String pk_org) throws BusinessException {
		if(unachieveDate == null || unachieveDate.size()<=0) {
			return null;
		}
		StringBuffer wheresql = new StringBuffer();
		ArrayList<String> redBackDates = new ArrayList<String>();
		for(UFDate unachieve : unachieveDate) {
			redBackDates.add(CmpAgiotageBusiUtils.queryRedBackByDate(unachieve, pk_org));
		}
		String redBackConditions = SqlUtils.getInStr("dbilldate", redBackDates.toArray(new String[0]), false);
		wheresql.append(redBackConditions).append(" and pk_org = '").append(pk_org)
		.append("'").append(" and dr=0");
		Collection<AccountAgiotageAggVO> lst = getMDQueryService().queryBillOfVOByCond(AccountAgiotageVO.class, wheresql.toString(),true);
		if (lst == null || lst.size() == 0) {
			return null;
		}
		return lst.toArray(new AccountAgiotageAggVO[]{});
		
	}
	
	/**
	 * ����Ԫ���ݲ�ѯ�������
	 */
	private IMDPersistenceQueryService getMDQueryService() {
		return MDPersistenceService.lookupPersistenceQueryService();
	}
	/**
	 * ������������
	 * 
	 * @param agioVO
	 * @return
	 * @throws BusinessException
	 * @author jiaweib
	 * @since NC6.0
	 */
	public Object[] settleAccountCheckAccountAgiotage(CMPAgiotageVO agioVO) throws BusinessException {
		try {

			if ((agioVO.getPkAccids() == null || agioVO.getPkAccids().size() == 0)
					&& (agioVO.getPkManageAccids() == null || agioVO.getPkManageAccids().size() == 0)) {
				return null;
			}
			List<String> pk_accounts = new ArrayList<String>();
			for (Object value : agioVO.getPkAccids().values()) {
				pk_accounts.addAll(Arrays.asList((String[]) value));
			}

			String pk_accountsSql = SqlUtil.buildSqlForIn("pk_bankaccount", pk_accounts.toArray(new String[] {}));
			String pk_monaccountsSql = SqlUtil.buildSqlForIn("pk_cashaccount", pk_accounts.toArray(new String[] {}));

			String sql = "select count(pk_accountagiotage)  from cmp_accountagiotage "
					+ " where dr= 0 and  pk_org = ?   " + " and dbilldate >= ? and dbilldate <= ? and ("
					+ pk_accountsSql + " or " + pk_monaccountsSql + ")";

			BaseDAO baseDAO = new BaseDAO();
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(agioVO.getDwbm());
			parameter.addParam(agioVO.getDateBeg());
			parameter.addParam(agioVO.getDateEnd());
			Integer count = (Integer) baseDAO.executeQuery(sql, parameter, new IntegerResultSetProcessor());

			
			Vector vector = new Vector();
			Object[] rst = new Object[]{vector};
			if (count < pk_accounts.size()) {
				vector.add(new Object());
			}

			return rst;

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * �ж��Ƿ��������浥
	 * 
	 * @param voBill
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	private boolean checkBillExist(ChangeBillAggVO[] voBill) {
		try {
			ICmpChangeBillPubQueryService queryService = CmpBillInterfaceProxy.INSTANCE.getChangeBillPubQueryService();

			StringBuffer account = new StringBuffer("");

			for (ChangeBillAggVO bill : voBill) {
				for (CircularlyAccessibleValueObject childVo : bill.getChildrenVO()) {
					StringBuffer sbUAP = new StringBuffer("");
					sbUAP.append(" pk_org = '").append(bill.getParentVO().getAttributeValue("pk_org")).append("' ");
					sbUAP.append("and trade_type = 'DR' ");
					sbUAP.append("and bill_date = '").append(bill.getChildrenVO()[0].getAttributeValue("bill_date"))
							.append("' ");

					// �����˻�
					String pkBankAccount = (String) childVo.getAttributeValue("pk_account");
					// �ֽ��˻�
					String pkCashAccount = (String) childVo.getAttributeValue("mon_account");

					if (pkBankAccount == null || "".equals(pkBankAccount)) {
						pkBankAccount = (String) bill.getChildrenVO()[0].getAttributeValue("pk_oppaccount");
					}

					sbUAP.append("and (pk_account = '").append(pkBankAccount).append("' ");
					sbUAP.append("or pk_oppaccount = '").append(pkBankAccount).append("' ");
					sbUAP.append("or mon_account = '").append(pkCashAccount).append("')");

					int i = queryService.findBillDetailCountByCondition(sbUAP.toString());

					if (i > 0) {
						String accountCode = "";

						// ��ѯ�����˻��ӻ�
						Map<String, BankAccSubVO> map = NCLocator.getInstance().lookup(IBankaccPubQueryService.class)
								.queryBankAccSubByPk(new String[] { "code", "name" }, new String[] { pkBankAccount });

						if (map != null && map.size() != 0) {
							BankAccSubVO bankAccSubVO = map.get(pkBankAccount);
							accountCode = bankAccSubVO.getCode();
						}

						// ��ѯ�ֽ��˻��ӻ�
						String condition = " pk_cashaccount ='" + pkCashAccount + "'";
						CashAccountVO[] cashAccountVOs = NCLocator.getInstance().lookup(ICashAccountQuerySerivce.class)
								.queryCashAccountVOsByCondition(condition);

						if (cashAccountVOs != null && cashAccountVOs.length != 0) {
							CashAccountVO cashAccountVO = cashAccountVOs[0];
							accountCode = cashAccountVO.getCode();
						}

						if (!"".equals(account.toString())) {
							account.append(",");
						}
						account.append("[").append(accountCode).append("]");
					}
				}
			}

			if (!"".equals(account.toString())) {
				return true;
			}
		} catch (Exception ex) {
			ExceptionHandler.error(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi_0", "03607agi-0038")/*
																													 * @res
																													 * "���õ��ݽӿ������˻�������浥��"
																													 */
					+ ex);
		}
		return false;
	}
}