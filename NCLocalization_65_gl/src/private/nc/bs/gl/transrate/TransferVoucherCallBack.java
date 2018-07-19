package nc.bs.gl.transrate;

import java.util.HashSet;
import java.util.Set;

import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.framework.common.NCLocator;
import nc.bs.gl.init.InitBO;
import nc.bs.gl.pubinterface.IVoucherDelete;
import nc.bs.gl.pubinterface.IVoucherSave;
import nc.bs.gl.transfer.TransferHistoryDMO;
import nc.bs.gl.voucher.VoucherBO;
import nc.bs.gl.vouchertally.VoucherTallyBO;
import nc.bs.logging.Logger;
import nc.gl.glconst.systemtype.SystemtypeConst;
import nc.gl.utils.GLMultiLangUtil;
import nc.itf.gl.transrate.ITransrateQryService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.CalendarUtilGL;
import nc.vo.gateway60.pub.VoucherTypeDataCache;
import nc.vo.gl.pubinterface.VoucherOperateInterfaceVO;
import nc.vo.gl.pubinterface.VoucherSaveInterfaceVO;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.gl.pubvoucher.OperationResultVO;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.transfer.TransferHistoryVO;
import nc.vo.gl.transrate.TransrateVoucherVO;
import nc.vo.org.AccountingBookVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

/**
 * 此处插入类型说明。 创建日期：(2001-11-28 15:49:26)
 * 
 * @author：王琛
 */
public class TransferVoucherCallBack implements IVoucherSave, IVoucherDelete {

	/**
	 * TransferVoucherCallBack 构造子注解。
	 */
	public TransferVoucherCallBack() {
		super();
	}

	/**
	 * afterDelete 方法注解。
	 */
	public OperationResultVO[] afterDelete(VoucherOperateInterfaceVO vo)
			throws BusinessException {
		
		try{
			TransferHistoryDMO aHistoryDMO = new TransferHistoryDMO();
			//删除转账历史
			aHistoryDMO.deleteByVoucherPK(vo.pk_vouchers[0]);
		}catch(Exception e){
			Logger.error(e.getMessage(),e);
			OperationResultVO result = new OperationResultVO();
			result.m_intSuccess = OperationResultVO.ERROR;
			result.m_strDescription = e.getMessage();
			return new OperationResultVO[]{result};
		}
		return null;
	}

	/**
	 * afterSave 方法注解。
	 */
	public OperationResultVO[] afterSave(VoucherSaveInterfaceVO vo)
			throws BusinessException {
		Object objUserData = vo.userdata;
		try {
			if (objUserData == null)
				return null;
			
			boolean isGenWriteoff = false;
			TransferHistoryVO hVO = (TransferHistoryVO)objUserData;
			if(vo.voucher.getFree10() != null && vo.voucher.getFree10().equals("VOUCHERNEWADD")){
				
				if(SystemtypeConst.RECLASSIFY.equals(vo.voucher.getPk_system())){
					
					if("00".equals(hVO.getPeriod()) || !isLastOfYear(vo.voucher.getPk_accountingbook(), hVO.getYear(), hVO.getPeriod()))
						isGenWriteoff = true;
				}else if(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES.equals(vo.voucher.getPk_system())){
					String pk_transfer = hVO.getPk_transfer();
					TransrateVoucherVO[] defvo = NCLocator.getInstance().lookup(ITransrateQryService.class).queryTransrate(TransrateVoucherVO.PK_TRANSRATE + "='" + pk_transfer + "'");
					if(defvo != null && defvo.length>0){
						if(defvo[0].getAutowriteoff() != null && defvo[0].getAutowriteoff().booleanValue())
							isGenWriteoff = true;
					}
				}
				
				if(SystemtypeConst.RECLASSIFY.equals(vo.voucher.getPk_system()) && "00".equals(hVO.getPeriod())){
					//期初重分类凭证把保存之后要记账
					new InitBO().Tally(new VoucherVO[]{vo.voucher}, "00", true, false);
				}
			}
			
			if (isGenWriteoff){
				
				VoucherVO reversedVO = getReversedVoucherVO(vo.voucher);
				setPrepareddate(reversedVO, hVO.getYear(), hVO.getPeriod());
				OperationResultVO[] results = new VoucherBO().save(reversedVO, true);
				String strWorningMessage = "";
				if(results!=null&&results.length>0){
					for(OperationResultVO result : results){
						if(result.m_intSuccess==2)
							strWorningMessage=strWorningMessage+result.m_strDescription;
					}
				}
				if(strWorningMessage!=null && !strWorningMessage.replace("null", "").equals("")){
					throw new BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000739")/*
					 * @
					 * res
					 * "保存冲回凭证失败！"
					 */
							+ strWorningMessage.replace("null", ""));
				}
				
				//更新历史表
				TransferHistoryDMO aHistoryDMO = new TransferHistoryDMO();
				TransferHistoryVO aHistoryVO = (TransferHistoryVO)objUserData;
				aHistoryVO.setPk_voucher(vo.voucher.getPrimaryKey());
				aHistoryVO.setPk_voucher_reversed(reversedVO.getPk_voucher());
				aHistoryDMO.insert(aHistoryVO);
			}else{
				TransferHistoryVO aHistoryVO = new TransferHistoryVO();
				TransferHistoryDMO aHistoryDMO = new TransferHistoryDMO();
				aHistoryVO.setPk_voucher(vo.voucher.getPrimaryKey());
				aHistoryVO.setPk_voucher_reversed(vo.voucher.getPrimaryKey());
				
				TransferHistoryVO[] historyVOs = aHistoryDMO.queryByVO(aHistoryVO,new Boolean(false));
				if (historyVOs != null && historyVOs.length > 0)
					return null;
				
				aHistoryVO = (TransferHistoryVO) objUserData;
				aHistoryVO.setPk_voucher(vo.voucher.getPrimaryKey());
				if(SystemtypeConst.RECLASSIFY.equals(vo.voucher.getPk_system())){
					aHistoryVO.setPk_voucher_reversed(SystemtypeConst.RECLASSIFY);
				}
				aHistoryDMO.insert(aHistoryVO);
			}
			
		} catch (Exception ex) {
			Logger.error("error");
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021505", "UPP20021505-000351")/*
																				 * @
																				 * res
																				 * "转账回调类异常:"
																				 */
					+ ex.getMessage());
		}
		return null;
	}

	/**
	 * beforeDelete 方法注解。
	 */
	public OperationResultVO[] beforeDelete(VoucherOperateInterfaceVO vo)
			throws BusinessException {
		try{
			TransferHistoryVO aHistoryVO = new TransferHistoryVO();
			TransferHistoryDMO aHistoryDMO = new TransferHistoryDMO();
			Set<String> pk_vouchers = new HashSet<String>();
			if(vo != null && vo.pk_vouchers != null){
				for (String pk_voucher : vo.pk_vouchers) {
					aHistoryVO.setPk_voucher(pk_voucher);
					aHistoryVO.setPk_voucher_reversed(pk_voucher);
					TransferHistoryVO[] historyVOs = aHistoryDMO.queryByVO(aHistoryVO,new Boolean(false));
					if(historyVOs != null && historyVOs.length ==1){
						TransferHistoryVO historyVO = historyVOs[0];
						if(pk_voucher.equals(historyVO.getPk_voucher()) && historyVO.getPk_voucher_reversed() != null){
							pk_vouchers.add(historyVO.getPk_voucher_reversed());
						}else if(pk_voucher.equals(historyVO.getPk_voucher_reversed()) && historyVO.getPk_voucher() !=null){
							pk_vouchers.add(historyVO.getPk_voucher());
						}
					}
				}
			}
			
			VoucherBO bo = new VoucherBO();
			VoucherVO[] vouchers = bo.queryByPks(pk_vouchers.toArray(new String[0]));
			if(vouchers != null && vouchers.length>0){
				for (int i = 0; i < vouchers.length; i++) {
					if(SystemtypeConst.RECLASSIFY.equals(vouchers[i].getPk_system()) && "00".equals(vouchers[i].getPeriod())){
						new VoucherTallyBO().tallyinitVoucherByPks(new String[]{vouchers[i].getPk_voucher()}, vouchers[i].getPk_prepared(), null , false);
					}
					new VoucherBO().deleteReverseVoucherByPk(vouchers[i].getPk_voucher());
				}
			}
			
		}catch(Exception e){
			Logger.error(e.getMessage(),e);
			throw new BusinessException(e.getMessage());
		}
		
		return null;

	}

	/**
	 * beforeSave 方法注解。
	 */
	public OperationResultVO[] beforeSave(VoucherSaveInterfaceVO vo)
			throws BusinessException {
		VoucherVO voucher = vo.voucher;
		if(voucher!= null){
			if(SystemtypeConst.RECLASSIFY.equals(voucher.getPk_system())){
				voucher.setModifyflag("NNN");
				voucher.setDetailmodflag(UFBoolean.FALSE);
				for(Object detail:voucher.getDetail()){
					((DetailVO)detail).setModifyflag("NNNNNNNNNNNNNNNNNNNNNNN");
				}
			}else if(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES.equals(voucher.getPk_system())){
				voucher.setModifyflag("NNN");
				voucher.setDetailmodflag(UFBoolean.FALSE);
				for(Object detail:voucher.getDetail()){
					((DetailVO)detail).setModifyflag("NYNNNNNNNNNNNNNNNNNNNNN");
				}
			}
		}
		return null;
	}
	
	private VoucherVO getReversedVoucherVO(VoucherVO vo){
		
		//add by weiningc  摘要修改  20180601 start
		StringBuilder newExplanationBySingapore = new StringBuilder();
		newExplanationBySingapore.append("(")
			.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("gl_exchange_local","gl2002exchangeloca0002"/*@res回冲*/))
			.append(" ");
		
		StringBuilder newExplanation = new StringBuilder();
		newExplanation.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000741")/** @res* "冲回"*/);
		newExplanation.append(" ");
		newExplanation.append(vo.getPrepareddate().toStdString());
		newExplanation.append(" ");
		newExplanation.append(GLMultiLangUtil.getMultiName(VoucherTypeDataCache.getInstance().getVoucherTypeByPk(vo.getPk_vouchertype())));
		newExplanation.append("-");
		newExplanation.append(vo.getNo());
		newExplanation.append(" ");
		if(SystemtypeConst.RECLASSIFY.equals(vo.getPk_system())){
			newExplanation.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000751")/** @res* "重分类凭证"*/);
			newExplanationBySingapore.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000751")/** @res* "重分类凭证"*/)
				.append(")");
		}else if(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES.equals(vo.getPk_system())){
			newExplanation.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000752")/** @res* "汇兑凭证"*/);
			newExplanationBySingapore.append(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000752")/** @res* "汇兑凭证"*/)
				.append(")");
		}		
		
		VoucherVO newVO = (VoucherVO)vo.clone();
		for (int i = 0; i < newVO.getNumDetails(); i++) {
			if (newVO.getDetail(i).getDetailindex() == null)
				continue;
			
			//修改摘要  modified by weiningc 20180601 start
			newVO.getDetail(i).setExplanation(newVO.getDetail(i).getExplanation() + newExplanationBySingapore.toString());
			//end
//			newVO.getDetail(i).setExplanation(newExplanation.toString());
			
			newVO.getDetail(i).setPk_detail(null);
			newVO.getDetail(i).setPk_voucher(null);
			newVO.setNo(Integer.valueOf(0));
			newVO.getDetail(i).setDebitquantity(new UFDouble(0).sub(newVO.getDetail(i).getDebitquantity()));
			newVO.getDetail(i).setDebitamount(new UFDouble(0).sub(newVO.getDetail(i).getDebitamount()));
			newVO.getDetail(i).setLocaldebitamount(new UFDouble(0).sub(newVO.getDetail(i).getLocaldebitamount()));
			
			newVO.getDetail(i).setCreditquantity(new UFDouble(0).sub(newVO.getDetail(i).getCreditquantity()));
			newVO.getDetail(i).setCreditamount(new UFDouble(0).sub(newVO.getDetail(i).getCreditamount()));
			newVO.getDetail(i).setLocalcreditamount(new UFDouble(0).sub(newVO.getDetail(i).getLocalcreditamount()));
			
			newVO.getDetail(i).setGroupdebitamount(new UFDouble(0).sub(newVO.getDetail(i).getGroupdebitamount()));
			newVO.getDetail(i).setGroupcreditamount(new UFDouble(0).sub(newVO.getDetail(i).getGroupcreditamount()));
			newVO.getDetail(i).setGlobaldebitamount(new UFDouble(0).sub(newVO.getDetail(i).getGlobaldebitamount()));
			newVO.getDetail(i).setGlobalcreditamount(new UFDouble(0).sub(newVO.getDetail(i).getGlobalcreditamount()));
		}
		
		newVO.setExplanation(newVO.getDetail(0).getExplanation());
//		newVO.setExplanation(newExplanation.toString());
		newVO.setPk_voucher(null);
		newVO.setAddclass(null);
		return newVO;
	}
	
	/**
	 * 判断是否年初启用（会计年的第一个期间启用）
	 * @param calendar 设置日期为核算账簿启用期间的calendar
	 * @return
	 */
	private boolean isStartOfYear(AccountCalendar calendar){
		return calendar.getMonthVO().getPk_accperiodmonth().equals(calendar.getFirstMonthOfCurrentYear().getPk_accperiodmonth()); 
	}
	
	/**
	 * 判断是否是最后一个期间
	 * @param pk_accountingbook
	 * @param year 执行年
	 * @param month 执行月
	 * @return
	 */
	private boolean isLastOfYear(String pk_accountingbook, String year, String month){
		AccountCalendar cal = CalendarUtilGL.getAccountCalendarByAccountBook(pk_accountingbook);
		String lastPeriod;
		try {
			cal.set(year);
			lastPeriod = cal.getLastMonthOfCurrentYear().getAccperiodmth();
		} catch (InvalidAccperiodExcetion e) {
			Logger.error(e.getMessage(),e);
			lastPeriod = "12";
		}
		return month.equals(lastPeriod); 
	}
	
	/**
	 * 判断是否是启用的第一年
	 * @param calendar	设置日期为核算账簿启用期间的calendar
	 * @param year 执行年
	 * @return
	 */
	private boolean isFirstYear(AccountCalendar calendar, String year){
		return year.equals(calendar.getYearVO().getPeriodyear());
	}
	
	private void setPrepareddate(VoucherVO vo, String year, String month)throws BusinessException {
		try {
			AccountingBookVO accountingBookVO = AccountBookUtil.getAccountingBookVOByPrimaryKey(vo.getPk_accountingbook());
			AccountCalendar calendar;
			if("00".equals(month)){
				calendar = AccountCalendar.getInstanceByAccperiodMonth(accountingBookVO.getPk_accountperiod());
				if(isStartOfYear(calendar)){
					calendar.set(year);
				}else{
					if(isFirstYear(calendar,year)){
						//do nothing
					}else{
						calendar.set(year);
					}
				}
			}else{
				calendar = CalendarUtilGL.getAccountCalendarByAccountBook(vo.getPk_accountingbook());
				calendar.setDate(vo.getPrepareddate());
				calendar.roll(AccountCalendar.MONTH, 1);
			}
			vo.setPrepareddate(calendar.getMonthVO().getBegindate());
			vo.setYear(calendar.getYearVO().getPeriodyear());
			vo.setPeriod(calendar.getMonthVO().getAccperiodmth());
		} catch (InvalidAccperiodExcetion e) {
			throw new BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021505", "UPP20021505-000740")/** @* res* "获取冲回凭证制单日期失败！"*/, e);
		}
	}
}