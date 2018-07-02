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
	//汇兑损益单据类型
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
	 * 汇兑损益单发送或删除会计平台
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void sendFIPMessge(AccountAgiotageAggVO aggvo,int flag) throws BusinessException {
		if(null!=aggvo){
			UFDate effectdate = AppContext.getInstance().getBusiDate();
			AccountAgiotageVO vo = aggvo.getParentVO();
			// 判断会计平台是否启用
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
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0013")/*@res "会计平台没有启用，无法进行制单相关操作"*/);
			}
		}
	}
	/**
	 * 汇兑损益单删除账操作
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void cancelWriteAccInfo(AccountAgiotageAggVO aggvo) throws BusinessException {
		BankAccDetailVO detailVO = getBankAccDetailVOInfo(aggvo);
		if(null != detailVO){
			// 调用删账服务
			NCLocator.getInstance().lookup(IBankAccService.class).deleteBankAccBySerialno(new String[]{aggvo.getParentVO().getPk_accountagiotage()},OperateTypeEnum.APPLY.getOperateTypeValue());
		}
	}
	/**
	 * 汇兑损益单登账操作
	 * @param aggvos
	 * @throws BusinessException
	 */
	public void writeAccInfo(AccountAgiotageAggVO aggvo) throws BusinessException {
		BankAccDetailVO detailVO = getBankAccDetailVOInfo(aggvo);
		if(null != detailVO){
			// 调用写账服务
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
			detailVO.setIsreverse(UFBoolean.TRUE);// 核销标志--必填

			detailVO.setSystemcode(CmpBusConstant.CMP_CODE);
			detailVO.setPk_transtype(vo.getPk_billtype());// 交易类型--必填
			detailVO.setVbillno(vo.getVbillno());// 单据编号--必填
			detailVO.setPk_curr(vo.getPk_curr());
			detailVO.setOnlyclueno(vo.getPk_accountagiotage());// 唯一线索号

			detailVO.setTallyperson(InvocationInfoProxy.getInstance().getUserId());
			detailVO.setTallytime(DataUtil.getCurrentTime().getDate());// 记账时间--触发登账的时间（取系统时间）--必填
			detailVO.setTallydate(new UFLiteralDate(vo.getDbilldate().toString()));//取单据日期
			//备注
			detailVO.setNote(vo.getMemo());
			// 在途标志：可用
			detailVO.setUseflag(UseFlagEnum.USE.getOperateTypeValue());
			//对方类型
			detailVO.setOpptradetype(7);
			// 汇率信息
			detailVO.setOlcrate(vo.getLocalrate());
			detailVO.setGlcrate(vo.getLocalrate());
			detailVO.setGllcrate(vo.getLocalrate());
			if(null!=vo.getPk_bankaccount() && !"".equals(vo.getPk_bankaccount())){
				// 账号信息
				detailVO.setPk_account(vo.getPk_bankaccount());
				// 银行账
				detailVO.setFundformcode(FundFormCodeEnum.BANK.getOperateTypeValue());
			}else if(null!=vo.getPk_cashaccount() && !"".equals(vo.getPk_cashaccount())){
				// 账号信息
				detailVO.setPk_account(vo.getPk_cashaccount());
				// 现金账
				detailVO.setFundformcode(FundFormCodeEnum.CASH.getOperateTypeValue());
			}
			// 金额信息
			if(null!=vo.getPay_localmoney()&&!UFDouble.ZERO_DBL.equals(vo.getPay_localmoney())){
				// 收付方向
				detailVO.setDirection(DirectionEnum.PAY.getOperateTypeValue());
				detailVO.setPaymoney(new UFDouble(0));
				detailVO.setOlcpaymoney(vo.getPay_localmoney());
			}else if(null!=vo.getReceipt_localmoney()&&!UFDouble.ZERO_DBL.equals(vo.getReceipt_localmoney())){
				// 收付方向
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
	 * 由汇兑损益VO转换为AccountAgiotageAggVO
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
			
			//单据日期
			if(CMPAgiotageVO.AGIOTAGE_WB == agioVO.getCalculateMode()){
				vo.setDbilldate(AppContext.getInstance().getBusiDate());
			}else{
				
				vo.setDbilldate(new UFDate(agioVO.getDateEnd()));
			}
			//若为回冲时,单据日期取beginDate
			if(agioVO.getMode() == 3) {
				vo.setDbilldate(new UFDate(agioVO.getDateBeg()));
			}
			
			vo.setPk_group(agioVO.getPk_group());
			vo.setPk_org(agioVO.getDwbm());
			vo.setBilltypecode("36S7");
			vo.setPk_billtype(billtype);
			/*1-表示确认已实现汇兑损益 2-表示确认以实现汇兑损益 3-下月自动回冲参数汇兑损益, 0表示未启用参数,保留系统本来的摘要*/
			if(agioVO.getMode() == 0) {
				vo.setMemo(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0014")/*@res "账户汇兑损益计算"*/);
			} else {
				vo.setMemo(agioVO.getMode() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607agi1_0","03607agi1-0014")/*@res "账户汇兑损益计算"*/);
			}
			//制单人、创建人、时间等
			vo.setBillmaker(agioVO.getUser());
			vo.setCreator(agioVO.getUser());
			vo.setCreationtime(new UFDateTime(agioVO.getDateEnd()));
			vo.setDoperatetime(new UFDateTime(AppContext.getInstance().getBusiDate(), null));
			vo.setDoperatedate(AppContext.getInstance().getBusiDate());
			
			// 负数为付款，正数为收款
			if (ArapCommonTool.isLessZero(agioVO.getCalCe().getBb())) {
				// 负数-付款
				vo.setReceipt_localmoney(new UFDouble(0));
				vo.setPay_localmoney(agioVO.getCalCe().getBb().abs());
			} else {
				// 正数-收款
				vo.setReceipt_localmoney(agioVO.getCalCe().getBb().abs());
				vo.setPay_localmoney(new UFDouble(0));
			}
//			//如果为回冲,金额相反  modified by weiningc
//			if(ArapCommonTool.isLessZero(agioVO.getCalCe().getBb()) && 3 == agioVO.getMode()) {
//				//若为付款
//				if(vo.getPay_localmoney().doubleValue() != 0) {
//					vo.setReceipt_localmoney(vo.getPay_localmoney());
//					vo.setPay_localmoney(new UFDouble(0));
//				} else {
//					vo.setPay_localmoney(vo.getReceipt_localmoney());
//					vo.setReceipt_localmoney(new UFDouble(0));
//				}
//			}
			
			// 组织版本
			OrgVO orgVO = NCLocator.getInstance().lookup(IOrgUnitQryService.class).getOrg(vo.getPk_org());
			vo.setPk_org_v(orgVO.getPk_vid());
			//银行账户、现金账户、账户名称
			Hashtable<String, String> pkaccids = agioVO.getPkAccids();
			Hashtable<String, String> pkcashids = agioVO.getPkManageAccids();

			//多币种
			for(CMPAgiotageBzVO bzVO: agioVO.getSelBzbm()){
				if(agioVO.getBzbm().equals(bzVO.getBzbm())){
					//币种
					vo.setPk_curr(bzVO.getBzbm());
					//本币汇率
					vo.setLocalrate(bzVO.getBbhl());
					break;
				}
			}
			//银行账户、现金账户、账户名称
			if(null!=pkaccids&&pkaccids.size()>0){
				String pk_bankacc = pkaccids.get(agioVO.getBzbm());
				//银行账户
				vo.setPk_bankaccount(pk_bankacc);
				//账户名称
				// 查询银行账户子户
				Map<String, BankAccSubVO> map = NCLocator.getInstance().lookup(IBankaccPubQueryService.class)
						.queryBankAccSubByPk(new String[] { "code", "name" }, new String[] { pk_bankacc });
				vo.setAccountname(map.get(pk_bankacc).getName());
				//添加vo
				aggvo.setParentVO(vo);
			}
			if(null!=pkcashids&&pkcashids.size()>0){
				//现金账户
				vo.setPk_cashaccount(pkcashids.get(agioVO.getBzbm()));
				// 查询现金账户
				String condition = " pk_cashaccount ='" + pkcashids.get(agioVO.getBzbm()) + "'";
				CashAccountVO[] cashAccountVOs = NCLocator.getInstance().lookup(ICashAccountQuerySerivce.class)
						.queryCashAccountVOsByCondition(condition);
				//账户名称
				vo.setAccountname(VOUtil.getMultiLangText(cashAccountVOs[0],CashAccountVO.NAME));
				aggvo.setParentVO(vo);
			}
		} catch (BusinessException e) {
			
			ExceptionHandler.consume(e);
		}

		return aggvo;
	}
}