package nc.bs.cmp.accountagiotage;

import java.util.Hashtable;
import java.util.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pf.pub.PfDataCache;
import nc.cmp.utils.DataUtil;
import nc.itf.bd.cashaccount.ICashAccountQuerySerivce;
import nc.itf.cmp.pub.CmpPublicServiceProxy;
import nc.itf.org.IOrgUnitQryService;
import nc.pubitf.cmp.bankaccbook.IBankAccService;
import nc.pubitf.uapbd.IBankaccPubQueryService;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.bd.bankaccount.BankAccSubVO;
import nc.vo.bd.cashaccount.CashAccountVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageAggVO;
import nc.vo.cmp.accountagiotage.AccountAgiotageVO;
import nc.vo.cmp.agiotage.CMPAgiotageBzVO;
import nc.vo.cmp.agiotage.CMPAgiotageVO;
import nc.vo.cmp.bankaccbook.BankAccDetailVO;
import nc.vo.cmp.bankaccbook.constant.DirectionEnum;
import nc.vo.cmp.bankaccbook.constant.FundFormCodeEnum;
import nc.vo.cmp.bankaccbook.constant.OperateTypeEnum;
import nc.vo.cmp.bankaccbook.constant.UseFlagEnum;
import nc.vo.cmp.pub.CmpPublicUtil;
import nc.vo.cmp.pub.constant.CmpBusConstant;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.org.OrgVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.pubapp.AppContext;
import nc.vo.tmpub.util.VOUtil;

public class AccountAgiotageUtil {
	private static AccountAgiotageUtil aUtil = null;
	//������浥������
	private String billtype = "0001Z610000000007E7I";
	private AccountAgiotageUtil(){

	}

	public synchronized static AccountAgiotageUtil getInstance(){
		if(null == aUtil){
			aUtil = new AccountAgiotageUtil();
		}
		return aUtil;
	}
	/**
	 * ������浥���ͻ�ɾ�����ƽ̨
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void sendFIPMessge(AccountAgiotageAggVO aggvo,int flag) throws BusinessException {
		if(null!=aggvo){
			UFDate effectdate = AppContext.getInstance().getBusiDate();
			AccountAgiotageVO vo = aggvo.getParentVO();
			// �жϻ��ƽ̨�Ƿ�����
			if (CmpPublicUtil.isFIPEnable(vo.getPk_group())) {

				String pk_tradetype = vo.getBilltypecode();
				FipRelationInfoVO reVO = new FipRelationInfoVO();
				reVO.setPk_group(vo.getPk_group());
				reVO.setPk_org(vo.getPk_org());
				reVO.setRelationID(vo.getPk_accountagiotage());
				reVO.setPk_system(PfDataCache.getBillType(pk_tradetype).getSystemcode());
				reVO.setBusidate(effectdate == null ? new UFDate() : effectdate);
				reVO.setPk_billtype(pk_tradetype);
				reVO.setPk_operator(InvocationInfoProxy.getInstance().getUserId());
				reVO.setFreedef1(vo.getVbillno());
				reVO.setFreedef3("0");
				reVO.setFreedef4("vuserdef4");
				reVO.setFreedef5("vuserdef5");

				FipMessageVO messageVO = new FipMessageVO();
				messageVO.setBillVO(vo);
				messageVO.setMessagetype(flag);
				messageVO.setMessageinfo(reVO);
				CmpPublicServiceProxy.getFipMessageService().sendMessage(messageVO);
			} else {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0013")/*@res "���ƽ̨û�����ã��޷������Ƶ���ز���"*/);
			}
		}
	}
	/**
	 * ������浥ɾ���˲���
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void cancelWriteAccInfo(AccountAgiotageAggVO aggvo) throws BusinessException {
		BankAccDetailVO detailVO = getBankAccDetailVOInfo(aggvo);
		if(null != detailVO){
			// ����ɾ�˷���
			NCLocator.getInstance().lookup(IBankAccService.class).deleteBankAccBySerialno(new String[]{aggvo.getParentVO().getPk_accountagiotage()},OperateTypeEnum.APPLY.getOperateTypeValue());
		}
	}
	/**
	 * ������浥���˲���
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void writeAccInfo(AccountAgiotageAggVO aggvo) throws BusinessException {
		BankAccDetailVO detailVO = getBankAccDetailVOInfo(aggvo);
		if(null != detailVO){
			// ����д�˷���
			NCLocator.getInstance().lookup(IBankAccService.class).writeBankAcc(new BankAccDetailVO[]{detailVO},OperateTypeEnum.APPLYANDUSE.getOperateTypeValue());
		}
	}

	private BankAccDetailVO getBankAccDetailVOInfo(AccountAgiotageAggVO aggvo) {
		BankAccDetailVO detailVO = null;
		if(null!=aggvo){
			
			
			
			AccountAgiotageVO vo = aggvo.getParentVO();
			detailVO = new BankAccDetailVO();
			detailVO.setPk_org(vo.getPk_org());
			detailVO.setPk_org_v(vo.getPk_org_v());
			detailVO.setPk_group(vo.getPk_group());

			detailVO.setPk_bill(vo.getPk_accountagiotage());
			detailVO.setPk_bill_b(vo.getPk_accountagiotage());
			detailVO.setPk_billtype(vo.getBilltypecode());
			detailVO.setBilltypecode(vo.getBilltypecode());
			detailVO.setIsreverse(UFBoolean.TRUE);// ������־--����

			detailVO.setSystemcode(CmpBusConstant.CMP_CODE);
			detailVO.setPk_transtype(vo.getPk_billtype());// ��������--����
			detailVO.setVbillno(vo.getVbillno());// ���ݱ��--����
			detailVO.setPk_curr(vo.getPk_curr());
			detailVO.setOnlyclueno(vo.getPk_accountagiotage());// Ψһ������

			detailVO.setTallyperson(InvocationInfoProxy.getInstance().getUserId());
			detailVO.setTallytime(DataUtil.getCurrentTime().getDate());// ����ʱ��--�������˵�ʱ�䣨ȡϵͳʱ�䣩--����
			detailVO.setTallydate(new UFLiteralDate(vo.getDbilldate().toString()));//ȡ��������
			//��ע
			detailVO.setNote(vo.getMemo());
			// ��;��־������
			detailVO.setUseflag(UseFlagEnum.USE.getOperateTypeValue());
			//�Է�����
			detailVO.setOpptradetype(7);
			// ������Ϣ
			detailVO.setOlcrate(vo.getLocalrate());
			detailVO.setGlcrate(vo.getLocalrate());
			detailVO.setGllcrate(vo.getLocalrate());
			if(null!=vo.getPk_bankaccount() && !"".equals(vo.getPk_bankaccount())){
				// �˺���Ϣ
				detailVO.setPk_account(vo.getPk_bankaccount());
				// ������
				detailVO.setFundformcode(FundFormCodeEnum.BANK.getOperateTypeValue());
			}else if(null!=vo.getPk_cashaccount() && !"".equals(vo.getPk_cashaccount())){
				// �˺���Ϣ
				detailVO.setPk_account(vo.getPk_cashaccount());
				// �ֽ���
				detailVO.setFundformcode(FundFormCodeEnum.CASH.getOperateTypeValue());
			}
			// �����Ϣ
			if(null!=vo.getPay_localmoney()&&!UFDouble.ZERO_DBL.equals(vo.getPay_localmoney())){
				// �ո�����
				detailVO.setDirection(DirectionEnum.PAY.getOperateTypeValue());
				detailVO.setPaymoney(new UFDouble(0));
				detailVO.setOlcpaymoney(vo.getPay_localmoney());
			}else if(null!=vo.getReceipt_localmoney()&&!UFDouble.ZERO_DBL.equals(vo.getReceipt_localmoney())){
				// �ո�����
				detailVO.setDirection(DirectionEnum.REC.getOperateTypeValue());
				detailVO.setRecmoney(new UFDouble(0));
				detailVO.setOlcrecmoney(vo.getReceipt_localmoney());
			}
		}
		
		
		if((detailVO.getOlcpaymoney() == null || UFDouble.ZERO_DBL.equals(detailVO.getOlcpaymoney())) && (detailVO.getOlcrecmoney()== null || UFDouble.ZERO_DBL.equals(detailVO.getOlcrecmoney()))){
			return null;
		}
		return detailVO;
	}
	/**
	 * �ɻ������VOת��ΪAccountAgiotageAggVO
	 *
	 * @param CMPAgiotageVO
	 * @return
	 */
	public AccountAgiotageAggVO changeVO(CMPAgiotageVO agioVO) {
		AccountAgiotageAggVO aggvo = null;
		AccountAgiotageVO vo = null;
		try {
			vo = new AccountAgiotageVO();
			aggvo = new AccountAgiotageAggVO();
			
			//��������
			if(CMPAgiotageVO.AGIOTAGE_WB == agioVO.getCalculateMode()){
				vo.setDbilldate(AppContext.getInstance().getBusiDate());
			}else{
				
				vo.setDbilldate(new UFDate(agioVO.getDateEnd()));
			}
			//��Ϊ�س�ʱ,��������ȡbeginDate
			if(agioVO.getMode() == 3) {
				vo.setDbilldate(new UFDate(agioVO.getDateBeg()));
			}
			
			vo.setPk_group(agioVO.getPk_group());
			vo.setPk_org(agioVO.getDwbm());
			vo.setBilltypecode("36S7");
			vo.setPk_billtype(billtype);
			/*1-��ʾȷ����ʵ�ֻ������ 2-��ʾȷ����ʵ�ֻ������ 3-�����Զ��س�����������, 0��ʾδ���ò���,����ϵͳ������ժҪ*/
			if(agioVO.getMode() == 0) {
				vo.setMemo(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0014")/*@res "�˻�����������"*/);
			} else {
				vo.setMemo(agioVO.getMode() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0014")/*@res "�˻�����������"*/);
			}
			//�Ƶ��ˡ������ˡ�ʱ���
			vo.setBillmaker(agioVO.getUser());
			vo.setCreator(agioVO.getUser());
			vo.setCreationtime(new UFDateTime(agioVO.getDateEnd()));
			vo.setDoperatetime(new UFDateTime(AppContext.getInstance().getBusiDate(), null));
			vo.setDoperatedate(AppContext.getInstance().getBusiDate());
			
			// ����Ϊ�������Ϊ�տ�
			if (ArapCommonTool.isLessZero(agioVO.getCalCe().getBb())) {
				// ����-����
				vo.setReceipt_localmoney(new UFDouble(0));
				vo.setPay_localmoney(agioVO.getCalCe().getBb().abs());
			} else {
				// ����-�տ�
				vo.setReceipt_localmoney(agioVO.getCalCe().getBb().abs());
				vo.setPay_localmoney(new UFDouble(0));
			}
//			//���Ϊ�س�,����෴  modified by weiningc
//			if(ArapCommonTool.isLessZero(agioVO.getCalCe().getBb()) && 3 == agioVO.getMode()) {
//				//��Ϊ����
//				if(vo.getPay_localmoney().doubleValue() != 0) {
//					vo.setReceipt_localmoney(vo.getPay_localmoney());
//					vo.setPay_localmoney(new UFDouble(0));
//				} else {
//					vo.setPay_localmoney(vo.getReceipt_localmoney());
//					vo.setReceipt_localmoney(new UFDouble(0));
//				}
//			}
			
			// ��֯�汾
			OrgVO orgVO = NCLocator.getInstance().lookup(IOrgUnitQryService.class).getOrg(vo.getPk_org());
			vo.setPk_org_v(orgVO.getPk_vid());
			//�����˻����ֽ��˻����˻�����
			Hashtable<String, String> pkaccids = agioVO.getPkAccids();
			Hashtable<String, String> pkcashids = agioVO.getPkManageAccids();

			//�����
			for(CMPAgiotageBzVO bzVO: agioVO.getSelBzbm()){
				if(agioVO.getBzbm().equals(bzVO.getBzbm())){
					//����
					vo.setPk_curr(bzVO.getBzbm());
					//���һ���
					vo.setLocalrate(bzVO.getBbhl());
					break;
				}
			}
			//�����˻����ֽ��˻����˻�����
			if(null!=pkaccids&&pkaccids.size()>0){
				String pk_bankacc = pkaccids.get(agioVO.getBzbm());
				//�����˻�
				vo.setPk_bankaccount(pk_bankacc);
				//�˻�����
				// ��ѯ�����˻��ӻ�
				Map<String, BankAccSubVO> map = NCLocator.getInstance().lookup(IBankaccPubQueryService.class)
						.queryBankAccSubByPk(new String[] { "code", "name" }, new String[] { pk_bankacc });
				vo.setAccountname(map.get(pk_bankacc).getName());
				//���vo
				aggvo.setParentVO(vo);
			}
			if(null!=pkcashids&&pkcashids.size()>0){
				//�ֽ��˻�
				vo.setPk_cashaccount(pkcashids.get(agioVO.getBzbm()));
				// ��ѯ�ֽ��˻�
				String condition = " pk_cashaccount ='" + pkcashids.get(agioVO.getBzbm()) + "'";
				CashAccountVO[] cashAccountVOs = NCLocator.getInstance().lookup(ICashAccountQuerySerivce.class)
						.queryCashAccountVOsByCondition(condition);
				//�˻�����
				vo.setAccountname(VOUtil.getMultiLangText(cashAccountVOs[0],CashAccountVO.NAME));
				aggvo.setParentVO(vo);
			}
		} catch (BusinessException e) {
			
			ExceptionHandler.consume(e);
		}

		return aggvo;
	}
}