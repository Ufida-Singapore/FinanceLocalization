package nc.ui.gl.transrate;

/**
 * 汇兑损益生成凭证。
 * 创建日期：(2001-11-27 15:23:36)
 * @author：王琛
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import nc.bs.framework.common.NCLocator;
import nc.bs.glcom.ass.assitem.cache.AccAssItemCache;
import nc.bs.logging.Logger;
import nc.gl.glconst.systemtype.SystemtypeConst;
import nc.itf.gl.accountingbook.IAccountingbookService;
import nc.itf.glcom.para.GLParaAccessor;
import nc.pubitf.accperiod.AccountCalendar;
import nc.ui.gl.datacache.AccountCache;
import nc.ui.gl.gateway.glworkbench.GlWorkBench;
import nc.vo.bd.account.AccAssVO;
import nc.vo.bd.account.AccountVO;
import nc.vo.fipub.freevalue.GlAssVO;
import nc.vo.fipub.freevalue.Module;
import nc.vo.fipub.freevalue.util.BigAssMD5Util;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.CalendarUtilGL;
import nc.vo.gateway60.itfs.Currency;
import nc.vo.gl.glreport.publictool.PrepareAssParse;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.gl.pubvoucher.VoucherKey;
import nc.vo.gl.transrate.TransrateHeaderVO;
import nc.vo.gl.transrate.TransrateTableVO;
import nc.vo.glcom.account.Balanorient;
import nc.vo.glcom.ass.AssVO;
import nc.vo.glcom.glperiod.GlPeriodVO;
import nc.vo.glcom.tools.GLPubProxy;
import nc.vo.pub.lang.UFDouble;

public class GenerateVoucher {
	private TransrateTableVO[] m_TransrateTableVO = null;
	private nc.vo.gl.transrate.TransrateVO transrateVO;
	private TransrateTableVO[] m_OutTableVO=null;
	private TransrateTableVO[] m_InTableVO=null;
	private TransrateVO_Wrapper m_TransrateVOWrapper=null;
	private Hashtable<String, Vector<?>> subjAsstab = new Hashtable<String, Vector<?>>();
	private Hashtable<String,AssVO[]> assVotab = new Hashtable<String,AssVO[]>();
	//added NCV31
	private String pk_orgbook;
	private CurrInfoTool currinfotool=null;

	class AssSortTool implements nc.vo.glcom.sorttool.ISortToolProvider {
		public nc.vo.glcom.sorttool.ISortTool getSortTool(Object objCompared) {
			try {
				//nc.vo.glcom.ass.AssVO[] tempVos = (nc.vo.glcom.ass.AssVO[]) objCompared;
				return new nc.ui.glcom.balance.CAssSortTool();

			}
			catch (Exception e) {
				Logger.error(e.getMessage(), e);
				return null;
			}
		}
	}
/**
 * GenerateVoucher 构造子注解。
 */
public GenerateVoucher()
{
	super();
}
//帐簿主辅币信息
public   CurrInfoTool getCurrinfotool() {
	if(currinfotool==null)
	{
		currinfotool=new CurrInfoTool(getPk_orgbook());
	}
	return currinfotool;
}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private nc.vo.gl.pubvoucher.DetailVO[] computerDetail(nc.vo.gl.pubvoucher.DetailVO[] details) throws java.lang.Exception {

		TransrateHeaderVO aHeaderVO = getTransrateHeader();
		Vector<DetailVO> vTableVO = new Vector<DetailVO>();
 
		for (int i = 0; i < details.length; i++) {
			if (details[i].getAssid() == null || details[i].getAssid().trim().equals(""))
				details[i].setAssid("null");
			vTableVO.addElement(details[i]);
		}

		nc.vo.glcom.intelvo.CIntelVO tt = new nc.vo.glcom.intelvo.CIntelVO();

		//在此不指定分组信息,则不分组合计

		int intSortIndex[] = null;

		//指定合计的最小列号组合
		int intSumLimit = 2;
		//指定用于分组计算的列号
		if (aHeaderVO.getCurrproperty().intValue() != 2) {
			if(GLParaAccessor.isSecondBUStart(getPk_orgbook()).booleanValue())
					intSortIndex = new int[] { VoucherKey.D_PK_ACCSUBJ, VoucherKey.D_PK_CURRTYPE,VoucherKey.D_ASSID,VoucherKey.D_PK_BUSIUNIT};
			else
				intSortIndex = new int[] { VoucherKey.D_PK_ACCSUBJ, VoucherKey.D_PK_CURRTYPE,VoucherKey.D_ASSID};
			 //intSumLimit = 3;
		} else {
			if(GLParaAccessor.isSecondBUStart(getPk_orgbook()).booleanValue())
				intSortIndex = new int[] { VoucherKey.D_PK_ACCSUBJ, VoucherKey.D_PK_CURRTYPE, VoucherKey.D_ASSID,VoucherKey.D_PK_BUSIUNIT };
			else
				intSortIndex = new int[] { VoucherKey.D_PK_ACCSUBJ, VoucherKey.D_PK_CURRTYPE, VoucherKey.D_ASSID};
			//intSumLimit = 3;
		}
		intSumLimit = intSortIndex.length-1;
	   // nc.ui.glcom.example.DetailKey
		nc.vo.glcom.inteltool.CGenTool genTool = new nc.vo.glcom.inteltool.CGenTool();

		genTool.setLimitSumGen(intSumLimit);
		genTool.setSortIndex(intSortIndex);
		//	genTool.setGenRatio(100);
		//	genTool.setGetSortTool(new AssSortTool());
		//	genTool.setSpecialToolKey(new int[]{TransrateKey.K_Ass});

		nc.vo.glcom.inteltool.CSumTool sumTool = new nc.vo.glcom.inteltool.CSumTool();
		int sumIndex[] =
			{
				VoucherKey.D_DEBITAMOUNT,         //add by zhaoshya  原币。
				VoucherKey.D_CREDITAMOUNT,
				VoucherKey.D_LOCALCREDITAMOUNT,
				VoucherKey.D_LOCALDEBITAMOUNT,
				VoucherKey.D_GROUPDEBIT,
				VoucherKey.D_GROUPCREDIT,
				VoucherKey.D_GLOBALDEBIT,
				VoucherKey.D_GLOBALCREDIT};
		//要进行合计的列
		sumTool.setSumIndex(sumIndex);

		nc.vo.glcom.inteltool.COutputTool outputTool = new nc.vo.glcom.inteltool.COutputTool();
		outputTool.setGenTool(genTool);
		outputTool.setRequireOutputDetail(false);

		nc.vo.glcom.inteltool.CDataSource datasource = new nc.vo.glcom.inteltool.CDataSource();
		datasource.setSumVector(nc.vo.glcom.inteltool.CDataSource.sortVector(vTableVO, genTool, false));

		try {
			tt.setSumTool(sumTool);
			//		tt.setBalanceTool(balanceTool);
			tt.setGenTool(genTool);
			tt.setDatasource(datasource);
			tt.setOutputTool(outputTool);
		} catch (Throwable e) {
			Logger.error(e.getMessage(), e);
		}
		
		Vector recVector = tt.getResultVector();
		
//		String sLocCurrPK = getCurrinfotool().getPk_LocalCurr();
//		String sGroupCurrPK = getCurrinfotool().getPk_groupCurr();
//		String sGlobalCurrPK = getCurrinfotool().getPk_globalCurr();
//		int iLocalNumber = Currency.getCurrDigit(sLocCurrPK);
//		int iGroupNumber = Currency.getCurrDigit(sGroupCurrPK);
//		int iGlobalNumber = Currency.getCurrDigit(sGlobalCurrPK);
		
		nc.vo.gl.pubvoucher.DetailVO[] voRet = null;
		Vector v=new Vector();
		for (int i = 0; i < recVector.size(); i++){
			UFDouble groupdeff = new UFDouble(0);
			UFDouble globaldeff = new UFDouble(0);
			nc.vo.gl.pubvoucher.DetailVO vo=(nc.vo.gl.pubvoucher.DetailVO)recVector.elementAt(i);
			UFDouble deffer=vo.getLocaldebitamount().sub(vo.getLocalcreditamount()).abs();
			if(Currency.isStartGroupCurr(GlWorkBench.getLoginGroup()))
				groupdeff=vo.getGroupdebitamount().sub(vo.getGroupcreditamount()).abs();
			if(Currency.isStartGlobalCurr())
				globaldeff=vo.getGlobaldebitamount().sub(vo.getGlobalcreditamount()).abs();
			
			if(deffer.doubleValue()>0.0001||groupdeff.doubleValue()>0.0001||globaldeff.doubleValue()>0.0001)
				v.addElement(recVector.elementAt(i));
		}
		recVector=v;
		if (recVector != null && recVector.size() > 0) {
			voRet = new nc.vo.gl.pubvoucher.DetailVO[recVector.size()];
			recVector.copyInto(voRet);
			for (int i = 0; i < voRet.length; i++){
				nc.vo.pub.lang.UFDouble debit=voRet[i].getDebitamount()==null?new UFDouble("0"):voRet[i].getDebitamount();
				nc.vo.pub.lang.UFDouble credit=voRet[i].getCreditamount()==null?new UFDouble("0"):voRet[i].getCreditamount();
				
				nc.vo.pub.lang.UFDouble ldebit=voRet[i].getLocaldebitamount()==null?new UFDouble("0"):voRet[i].getLocaldebitamount();
				nc.vo.pub.lang.UFDouble lcredit=voRet[i].getLocalcreditamount()==null?new UFDouble("0"):voRet[i].getLocalcreditamount();
				
				nc.vo.pub.lang.UFDouble gdebit=voRet[i].getGroupdebitamount()==null?new UFDouble("0"):voRet[i].getGroupdebitamount();
				nc.vo.pub.lang.UFDouble gcredit=voRet[i].getGroupcreditamount()==null?new UFDouble("0"):voRet[i].getGroupcreditamount();
				
				nc.vo.pub.lang.UFDouble gldebit=voRet[i].getGlobaldebitamount()==null?new UFDouble("0"):voRet[i].getGlobaldebitamount();
				nc.vo.pub.lang.UFDouble glcredit=voRet[i].getGlobalcreditamount()==null?new UFDouble("0"):voRet[i].getGlobalcreditamount();

				// add By shaoguo.wang 判断转入项目 at 2007-07-19
				String PAcc, LAcc,pAss,lAss;
				boolean inAccount = false;
				PAcc = getTransrateHeader().getPk_accsubjPL();
			     pAss=getTransrateHeader().getAssprofit();
			     lAss=getTransrateHeader().getAssloss();
				if (PAcc == null || PAcc.equals("")) {
					PAcc = getTransrateHeader().getPk_accsubjProfit();
					LAcc = getTransrateHeader().getPk_accsubjLoss();
					pAss=getTransrateHeader().getAssprofit();
			        lAss=getTransrateHeader().getAssloss();
				} else {
					LAcc = PAcc;
				}
				if (voRet[i].getPk_accasoa() != null && (voRet[i].getPk_accasoa().equals(PAcc) || voRet[i].getPk_accasoa().equals(LAcc))) {
					if (voRet[i].getPk_accasoa().equals(PAcc) && ((voRet[i].getAssid().equals("null") && pAss == null) || voRet[i].getAssid().equals(pAss) )) {
						inAccount = true;
					} else if (voRet[i].getPk_accasoa().equals(LAcc) && ((voRet[i].getAssid().equals("null") && lAss == null) || voRet[i].getAssid().equals(lAss) )) {
						inAccount = true;
					}
				}
				if (PAcc != null && !PAcc.equals("") && (LAcc == null || LAcc.equals("")) && inAccount) {
					voRet[i].setLocalcreditamount(lcredit.sub(ldebit));
					voRet[i].setCreditamount(credit.sub(debit));
					voRet[i].setGroupcreditamount(gcredit.sub(gdebit));
					voRet[i].setGlobalcreditamount(glcredit.sub(gldebit));
					
					voRet[i].setLocaldebitamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setDebitamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setGroupdebitamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setGlobaldebitamount(new nc.vo.pub.lang.UFDouble("0"));

				} else if (LAcc != null && !LAcc.equals("") && (PAcc == null || PAcc.equals("")) && inAccount) {
					voRet[i].setLocaldebitamount(ldebit.sub(lcredit).multiply(-1));
					voRet[i].setDebitamount(debit.sub(credit).multiply(-1));
					voRet[i].setGroupdebitamount(gdebit.sub(gcredit).multiply(-1));
					voRet[i].setGlobaldebitamount(gldebit.sub(glcredit).multiply(-1));
					
					voRet[i].setLocalcreditamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setCreditamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setGroupcreditamount(new nc.vo.pub.lang.UFDouble("0"));
					voRet[i].setGlobalcreditamount(new nc.vo.pub.lang.UFDouble("0"));
				} else {
					
					if(voRet[i].getLocaldebitamount().doubleValue() >= voRet[i].getLocalcreditamount().doubleValue())
					{
						voRet[i].setLocaldebitamount(ldebit.sub(lcredit));	
						voRet[i].setLocalcreditamount(new nc.vo.pub.lang.UFDouble("0"));
						
						voRet[i].setDebitamount(debit.sub(credit));
						voRet[i].setCreditamount(new nc.vo.pub.lang.UFDouble("0"));
						voRet[i].setGroupdebitamount(gdebit.sub(gcredit));
						voRet[i].setGroupcreditamount(new nc.vo.pub.lang.UFDouble("0"));
						voRet[i].setGlobaldebitamount(gldebit.sub(glcredit));
						voRet[i].setGlobalcreditamount(new nc.vo.pub.lang.UFDouble("0"));
						
//						if(gdebit.sub(gcredit).getDouble()>=0){
//							voRet[i].setGroupdebitamount(gdebit.sub(gcredit));
//							voRet[i].setGroupcreditamount(new nc.vo.pub.lang.UFDouble("0"));
//						}else{
//							voRet[i].setGroupcreditamount(gdebit.sub(gcredit).multiply(-1));
//							voRet[i].setGroupdebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}
//						if(gldebit.sub(glcredit).getDouble()>=0){
//							voRet[i].setGlobaldebitamount(gldebit.sub(glcredit));
//							voRet[i].setGlobalcreditamount(new nc.vo.pub.lang.UFDouble("0"));
//						}else{
//							voRet[i].setGlobalcreditamount(gldebit.sub(glcredit).multiply(-1));
//							voRet[i].setGlobaldebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}
					} else {
						voRet[i].setLocalcreditamount(lcredit.sub(ldebit));
						voRet[i].setLocaldebitamount(new nc.vo.pub.lang.UFDouble("0"));
						voRet[i].setCreditamount(credit.sub(debit));
						voRet[i].setDebitamount(new nc.vo.pub.lang.UFDouble("0"));
						
						voRet[i].setGroupcreditamount(gcredit.sub(gdebit));
						voRet[i].setGroupdebitamount(new nc.vo.pub.lang.UFDouble("0"));
						voRet[i].setGlobalcreditamount(glcredit.sub(gldebit));
						voRet[i].setGlobaldebitamount(new nc.vo.pub.lang.UFDouble("0"));

//						if(gcredit.sub(gdebit).getDouble()>=0){
//							voRet[i].setGroupcreditamount(gcredit.sub(gdebit));
//							voRet[i].setGroupdebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}else{
//							voRet[i].setGroupcreditamount(gcredit.sub(gdebit).multiply(-1));
//							voRet[i].setGroupdebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}
//						if(glcredit.sub(gldebit).getDouble()>=0){
//							voRet[i].setGlobalcreditamount(glcredit.sub(gldebit));
//							voRet[i].setGlobaldebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}else{
//							voRet[i].setGlobalcreditamount(glcredit.sub(gldebit).multiply(-1));
//							voRet[i].setGlobaldebitamount(new nc.vo.pub.lang.UFDouble("0"));
//						}
					}
					
					//全局、集团跟组织本币一致计算， 最后 如果出现负数就 借贷互换。 zhaoshya
					if(voRet[i].getGroupdebitamount().doubleValue()<0){
						voRet[i].setGroupcreditamount(voRet[i].getGroupdebitamount().multiply(-1));
						voRet[i].setGroupdebitamount(new UFDouble("0"));
					}else if(voRet[i].getGroupcreditamount().doubleValue()<0){
						voRet[i].setGroupdebitamount(voRet[i].getGroupcreditamount().multiply(-1));
						voRet[i].setGroupcreditamount(new UFDouble("0"));
					}
					
					if(voRet[i].getGlobaldebitamount().doubleValue()<0){
						voRet[i].setGlobalcreditamount(voRet[i].getGlobaldebitamount().multiply(-1));
						voRet[i].setGlobaldebitamount(new UFDouble("0"));
					}else if(voRet[i].getGlobalcreditamount().doubleValue()<0){
						voRet[i].setGlobaldebitamount(voRet[i].getGlobalcreditamount().multiply(-1));
						voRet[i].setGlobalcreditamount(new UFDouble("0"));
					}

				}

			if(voRet[i].getAssid().equals("null"))
			{
				voRet[i].setAssid(null);
			}
		}
	}

	return voRet;
}

//	private double roundDouble(double d, int scale) {
//		double dblNew = Math.abs(d);
//
//		long lngTemp = (long) dblNew;
//		double dblTemp = dblNew - lngTemp;
//
//		double convertFactor;
//		convertFactor = 0.5;
//
//		dblNew = lngTemp
//				+ (double) (((long) (dblTemp * Math.pow(10, scale) + convertFactor)) / Math
//						.pow(10, scale));
//
//		if (d < 0)
//			d = dblNew * (-1);
//		else
//			d = dblNew;
//
//		return d;
//	}

//	private nc.vo.pub.lang.UFDouble roundDouble(
//			nc.vo.pub.lang.UFDouble dblTemp, int intDigit) {
//		return new nc.vo.pub.lang.UFDouble(roundDouble(dblTemp.doubleValue(),
//				intDigit));
//	}
	
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-19 14:12:34)
 * @return nc.vo.gl.transrate.TransrateTableVO[]
 * @param param nc.vo.gl.transrate.TransrateTableVO[]
 * @exception java.lang.Exception 异常说明。
 */
private TransrateTableVO[] dealInItem(TransrateTableVO[] param) throws java.lang.Exception {
	String PAcc, LAcc;

	if(param==null || param.length==0)
		return null;

	PAcc = getTransrateHeader().getPk_accsubjPL();

	if (PAcc == null || PAcc.equals("")) {

		PAcc = getTransrateHeader().getPk_accsubjProfit();
		LAcc = getTransrateHeader().getPk_accsubjLoss();
	}
	else {
		LAcc = PAcc;
	}

	String pk_curr=null;
	if(getTransrateHeader().getCurrproperty().intValue()==0)
		pk_curr=getCurrinfotool().getPk_LocalCurr();//本币

	//根据转出的金额正负决定采用的转入科目和方向
	for (int i = 0; i < param.length; i++) {

		nc.vo.pub.lang.UFDouble localDiff,groupDiff,globalDiff;
		nc.vo.pub.lang.UFDouble debitLocalDiff,creditLocalDiff,debitGroupDiff,creditGroupDiff,debitGlobalDiff,creditGlobalDiff;

		debitLocalDiff=param[i].getDebitLocalDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getDebitLocalDiff();
		creditLocalDiff=param[i].getCreditLocalDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getCreditLocalDiff();
		debitGroupDiff =param[i].getM_DebitGroupDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getM_DebitGroupDiff();
		creditGroupDiff =param[i].getM_CreditGroupDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getM_CreditGroupDiff();
		debitGlobalDiff =param[i].getM_DebitGlobalDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getM_DebitGlobalDiff();
		creditGlobalDiff =param[i].getM_CreditGlobalDiff()==null?new nc.vo.pub.lang.UFDouble(0d):param[i].getM_CreditGlobalDiff();
		
		localDiff = debitLocalDiff.sub(creditLocalDiff);
		groupDiff=debitGroupDiff.sub(creditGroupDiff);
		globalDiff=debitGlobalDiff.sub(creditGlobalDiff);
		if(pk_curr!=null)
			param[i].setPKCurrType(pk_curr);
		// 将汇兑结转定义的"汇兑损失"和“汇兑收益”考虑
		if (PAcc != null && !PAcc.equals("") && (LAcc == null || LAcc.equals(""))) {
			param[i].setPKAccount(PAcc);
			param[i].setOrientation(Integer.valueOf(Balanorient.CREDIT));
			param[i].setCreditLocalDiff(localDiff);
			param[i].setM_CreditGroupDiff(groupDiff);
			param[i].setM_CreditGlobalDiff(globalDiff);
		} else if (LAcc != null && !LAcc.equals("") && (PAcc == null || PAcc.equals(""))) {
			param[i].setPKAccount(LAcc);
			param[i].setOrientation(Integer.valueOf(Balanorient.DEBIT));
			param[i].setDebitLocalDiff(localDiff);
			param[i].setM_DebitGroupDiff(groupDiff);
			param[i].setM_DebitGlobalDiff(globalDiff);
		} else {
			if (localDiff.getDouble() > 0) {
				param[i].setPKAccount(PAcc);
				param[i].setOrientation(Integer.valueOf(Balanorient.CREDIT));
				param[i].setCreditLocalDiff(localDiff);
				param[i].setM_CreditGroupDiff(groupDiff);
				param[i].setM_CreditGlobalDiff(globalDiff);
			}
			else {
				param[i].setPKAccount(LAcc);
				param[i].setOrientation(Integer.valueOf(Balanorient.DEBIT));
				param[i].setDebitLocalDiff(localDiff.multiply(-1));
				param[i].setM_DebitGroupDiff(groupDiff.multiply(-1));
				param[i].setM_DebitGlobalDiff(globalDiff.multiply(-1));
			}
		}
		//param[i].setAssID(null);
		
	}

	return param;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-19 14:12:34)
 * @return nc.vo.gl.transrate.TransrateTableVO[]
 * @param param nc.vo.gl.transrate.TransrateTableVO[]
 * @exception java.lang.Exception 异常说明。
 */
private TransrateTableVO[] dealOutItem(TransrateTableVO[] param) throws java.lang.Exception {
	//根据转出的金额正负调整方向

	for(int i=0;i<param.length;i++)
	{
		nc.vo.pub.lang.UFDouble localDiff=null;
		nc.vo.pub.lang.UFDouble groupDiff=null;
		nc.vo.pub.lang.UFDouble globalDiff=null;
		int orientation=param[i].getOrientation().intValue();
		if(orientation==Balanorient.CREDIT)
		{
			localDiff=param[i].getCreditLocalDiff();
			groupDiff = param[i].getM_CreditGroupDiff();
			globalDiff = param[i].getM_CreditGlobalDiff();
		}
		else
		{
			localDiff=param[i].getDebitLocalDiff();
			groupDiff = param[i].getM_DebitGroupDiff();
			globalDiff = param[i].getM_DebitGlobalDiff();
		}

		if(localDiff.getDouble()<0)
		{
			param[i].setOrientation(orientation==Balanorient.DEBIT?Integer.valueOf(Balanorient.CREDIT):Integer.valueOf(Balanorient.DEBIT));
			if(orientation==Balanorient.DEBIT)
			{
				if(groupDiff!=null)
					param[i].setM_CreditGroupDiff(groupDiff.multiply(-1));
				if(globalDiff!=null)
					param[i].setM_CreditGlobalDiff(globalDiff.multiply(-1));
				param[i].setCreditLocalDiff(localDiff.multiply(-1));
			}
			else
			{
				if(groupDiff!=null)
					param[i].setM_DebitGroupDiff(groupDiff.multiply(-1));
				if(globalDiff!=null)
					param[i].setM_DebitGlobalDiff(globalDiff.multiply(-1));
				param[i].setDebitLocalDiff(localDiff.multiply(-1));
			}
		}
		if(param[i].getOrientation().intValue()==Balanorient.DEBIT)
		{
			param[i].setCreditLocalDiff(new nc.vo.pub.lang.UFDouble(0));
			param[i].setM_CreditGroupDiff(new nc.vo.pub.lang.UFDouble(0));
			param[i].setM_CreditGlobalDiff(new nc.vo.pub.lang.UFDouble(0));

		}
		else
		{
			param[i].setDebitLocalDiff(new nc.vo.pub.lang.UFDouble(0));
			param[i].setM_DebitGroupDiff(new nc.vo.pub.lang.UFDouble(0));
			param[i].setM_DebitGlobalDiff(new nc.vo.pub.lang.UFDouble(0));
		}
		//出现负数的话， 借贷互换  使金额变成正数
//		if(param[i].getM_CreditGroupDiff().getDouble()<0){
//			param[i].setM_DebitGroupDiff(param[i].getM_CreditGroupDiff().multiply(-1));
//			param[i].setM_CreditGroupDiff(new UFDouble("0"));
//		}else if(param[i].getM_DebitGroupDiff().getDouble()<0){
//			param[i].setM_CreditGroupDiff(param[i].getM_DebitGroupDiff().multiply(-1));
//			param[i].setM_DebitGroupDiff(new UFDouble("0"));
//		}
//		
//		if(param[i].getM_CreditGlobalDiff().getDouble()<0){
//			param[i].setM_DebitGlobalDiff(param[i].getM_CreditGlobalDiff().multiply(-1));
//			param[i].setM_CreditGlobalDiff(new UFDouble("0"));
//		}else if(param[i].getM_DebitGlobalDiff().getDouble()<0){
//			param[i].setM_CreditGlobalDiff(param[i].getM_DebitGlobalDiff().multiply(-1));
//			param[i].setM_DebitGlobalDiff(new UFDouble("0"));
//		}

	}

	return param;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-17 14:15:38)
 * @return nc.vo.gl.transrate.TransrateTableVO[]
 * @param param nc.vo.gl.transrate.TransrateTableVO[]
 * @exception java.lang.Exception 异常说明。
 */
public void dealPLItems() throws java.lang.Exception {
	if(getTransRateTableVO()==null)
		return ;

	TransrateTableVO[] voRet=getTransRateTableVO();
	
	//add by zhaoyangm 20120315 处理精度 start
//	String sLocCurrPK = getCurrinfotool().getPk_LocalCurr();
//	String sGroupCurrPK = getCurrinfotool().getPk_groupCurr();
//	String sGlobalCurrPK = getCurrinfotool().getPk_globalCurr();
//	int iLocalNumber = Currency.getCurrDigit(sLocCurrPK);
//	int iGroupNumber = Currency.getCurrDigit(sGroupCurrPK);
//	int iGlobalNumber = Currency.getCurrDigit(sGlobalCurrPK);
//	for (TransrateTableVO vo : voRet) {
//		
//		if(vo.getDebitLocalDiff() != null){
//			vo.setDebitLocalDiff(roundDouble(vo.getDebitLocalDiff(), iLocalNumber));
//		}
//		if(vo.getCreditLocalDiff() != null){
//			vo.setCreditLocalDiff(roundDouble(vo.getCreditLocalDiff(), iLocalNumber));
//		}
//		if(Currency.isStartGroupCurr(GlWorkBench.getLoginGroup())){
//			if(vo.getM_DebitGroupDiff() != null){
//				vo.setM_DebitGroupDiff(roundDouble(vo.getM_DebitGroupDiff(), iGroupNumber));
//			}
//			if(vo.getM_CreditGroupDiff() != null){
//				vo.setM_CreditGroupDiff(roundDouble(vo.getM_CreditGroupDiff(), iGroupNumber));
//			}
//		}
//		if(Currency.isStartGlobalCurr()){
//			if(vo.getM_DebitGlobalDiff() != null){
//				vo.setM_DebitGlobalDiff(roundDouble(vo.getM_DebitGlobalDiff(), iGlobalNumber));
//			}
//			if(vo.getM_CreditGlobalDiff() != null){
//				vo.setM_CreditGlobalDiff(roundDouble(vo.getM_CreditGlobalDiff(), iGlobalNumber));
//			}
//		}	
//		
//	}
	//add by zhaoyangm 处理精度 end
	
	//处理转出科目
	m_OutTableVO=dealOutItem(voRet);
	//根据转入科目的类型和转入的币种类型计算转入项目
	TransrateTableVO[] aTableVO=new TransrateTableVO[m_OutTableVO.length];
	for(int i=0;i<m_OutTableVO.length;i++)
	{
		aTableVO[i]=(TransrateTableVO)m_OutTableVO[i].clone();
	}
	voRet=aTableVO;
	//voRet=computePLItems(aTableVO);
	//根据转入科目处理转入科目VO
	m_InTableVO=dealInItem(voRet);

	return ;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-27 15:36:21)
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public nc.vo.gl.pubvoucher.VoucherVO fillVoucher(String year, String period) throws Exception{

	if(m_OutTableVO==null || m_InTableVO==null || m_OutTableVO.length==0 || m_InTableVO.length==0)
		return null;

	nc.vo.gl.pubvoucher.VoucherVO aVoucherVO = new nc.vo.gl.pubvoucher.VoucherVO();
	TransrateHeaderVO aHeaderVO = (TransrateHeaderVO) getTransrateVO().getParentVO();
	aVoucherVO.setPk_accountingbook(getPk_orgbook());
	aVoucherVO.setCorpname("");
	aVoucherVO.setPk_vouchertype(getTransrateHeader().getPk_vouchertype());
	aVoucherVO.setAttachment(getTransrateHeader().getBillnum());
	aVoucherVO.setPk_prepared(GlWorkBench.getLoginUser());
	nc.bs.logging.Logger.debug("*****************   汇兑损益生成凭证信息输出 *****************");

	AccountCalendar cal = CalendarUtilGL.getAccountCalendarByAccountBook(getPk_orgbook());
	cal.set(year, period);
	aVoucherVO.setPrepareddate(cal.getMonthVO().getEnddate());
	
	nc.bs.logging.Logger.debug("getClientEnvironment().getDate()   "+ GlWorkBench.getBusiDate());


	aVoucherVO.setNo(Integer.valueOf(0));
	aVoucherVO.setVoucherkind(Integer.valueOf(0));
	aVoucherVO.setDiscardflag(nc.vo.pub.lang.UFBoolean.FALSE);
	aVoucherVO.setModifyflag("YYY");
	aVoucherVO.setDetailmodflag(nc.vo.pub.lang.UFBoolean.TRUE);
	aVoucherVO.setPk_system(SystemtypeConst.EXCHANGE_GAINS_AND_LOSSES);

	nc.vo.gl.transfer.TransferHistoryVO aHistoryVO=new nc.vo.gl.transfer.TransferHistoryVO();
	aHistoryVO.setOperatetime(GlWorkBench.getBusiDate());
	aHistoryVO.setPk_transfer(getTransrateHeader().getPk_transRate());
	aHistoryVO.setTransferType(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002GL502","UPP2002GL502-000117")/*@res "汇兑损益结转"*/);
	aHistoryVO.setYear(year);
	aHistoryVO.setPeriod(period);
	aHistoryVO.setPk_glorgbook(getPk_orgbook());
	aHistoryVO.setPk_group(GlWorkBench.getLoginGroup());
	aVoucherVO.setAddclass("nc.bs.gl.transrate.TransferVoucherCallBack");
	aVoucherVO.setDeleteclass("nc.bs.gl.transrate.TransferVoucherCallBack");
	aVoucherVO.setUserData(aHistoryVO);

	Vector<DetailVO> vDetail = new Vector<DetailVO>();

	String explanation = year + nc.ui.ml.NCLangRes.getInstance().getStrByID("20021505","UPP20021505-000135")/*@res "年"*/ + period + nc.ui.ml.NCLangRes.getInstance().getStrByID("20021505","UPP20021505-000357")/*@res "月汇兑损益结转"*/;
	//add by weiningc  汇兑损益摘要需求  20180601 start
	String newexpanation =  nc.ui.ml.NCLangRes.getInstance().getStrByID("gl_exchange_local","gl2002exchangeloca0001"/*@res未实现汇兑损益*/);

	nc.vo.pub.lang.UFDouble debitTotal=new nc.vo.pub.lang.UFDouble(0);
	nc.vo.pub.lang.UFDouble creditTotal=new nc.vo.pub.lang.UFDouble(0);

	for (int i = 0; i < m_OutTableVO.length; i++) {
		nc.vo.gl.pubvoucher.DetailVO aDetail = new nc.vo.gl.pubvoucher.DetailVO();
		if (m_OutTableVO[i].getOrientation().intValue() == Balanorient.DEBIT) {

			if((m_OutTableVO[i].getDebitLocalDiff()==null || m_OutTableVO[i].getDebitLocalDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_DebitGroupDiff()==null || m_OutTableVO[i].getM_DebitGroupDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_DebitGlobalDiff()==null || m_OutTableVO[i].getM_DebitGlobalDiff().doubleValue()==0)) {
				continue;
			}
		} else {
				if((m_OutTableVO[i].getCreditLocalDiff()==null || m_OutTableVO[i].getCreditLocalDiff().doubleValue()==0)&&
						(m_OutTableVO[i].getM_CreditGroupDiff()==null || m_OutTableVO[i].getM_CreditGroupDiff().doubleValue()==0)&&
						(m_OutTableVO[i].getM_CreditGlobalDiff()==null || m_OutTableVO[i].getM_CreditGlobalDiff().doubleValue()==0)) {
					continue;
				}
		}
		Double s1 = m_OutTableVO[i].getDebitBalance().doubleValue() ==0.00 ? m_OutTableVO[i].getCreditBalance().doubleValue() : m_OutTableVO[i].getDebitBalance().doubleValue();
		Double s2 = m_OutTableVO[i].getLocalAdjustRate().doubleValue();
		Double s3 = m_OutTableVO[i].getDebitLocalBalance().doubleValue() == 0.00 ? m_OutTableVO[i].getCreditLocalBalance().doubleValue() : m_OutTableVO[i].getDebitLocalBalance().doubleValue();
		String newexpanationForSingapore = newexpanation + "(" + s1.toString() + "*" + s2.toString() + "-" + s3.toString() + ")";
		
		aDetail.setExplanation(newexpanationForSingapore);
		// end 20180601
		aDetail.setPk_accasoa(m_OutTableVO[i].getPKAccount());
		aDetail.setPk_currtype(m_OutTableVO[i].getPKCurrType());
		aDetail.setModifyflag("YYYYYYYYYYYYYYYY");
		aDetail.setPk_accountingbook(getPk_orgbook());
		aDetail.setDetailindex(Integer.valueOf(vDetail.size()+1));
		aDetail.setPrice(new nc.vo.pub.lang.UFDouble(0));

		aDetail.setExcrate2(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setDebitquantity(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setDebitamount(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setCreditquantity(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setCreditamount(new nc.vo.pub.lang.UFDouble(0));
		//if(m_OutTableVO[i].getAssVO()==null)
		//处理转出科目的辅助核算项目，如果设定“入账核算项目”则以之为准

		if(m_OutTableVO[i].getAssID()==null) {
			aDetail.setAssid(null);
		} else {
			aDetail.setAssid(m_OutTableVO[i].getAssID());
		}
		if (m_OutTableVO[i].getOrientation().intValue() == Balanorient.DEBIT) {

			if((m_OutTableVO[i].getDebitLocalDiff()==null || m_OutTableVO[i].getDebitLocalDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_DebitGroupDiff()==null || m_OutTableVO[i].getM_DebitGroupDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_DebitGlobalDiff()==null || m_OutTableVO[i].getM_DebitGlobalDiff().doubleValue()==0))
				continue;
			aDetail.setLocaldebitamount(m_OutTableVO[i].getDebitLocalDiff());
			aDetail.setGroupdebitamount(m_OutTableVO[i].getM_DebitGroupDiff());
			aDetail.setGlobaldebitamount(m_OutTableVO[i].getM_DebitGlobalDiff());
			
			aDetail.setLocalcreditamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGroupcreditamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGlobalcreditamount(new nc.vo.pub.lang.UFDouble(0));

			debitTotal=debitTotal.add(aDetail.getLocaldebitamount());
		} else {
			if((m_OutTableVO[i].getCreditLocalDiff()==null || m_OutTableVO[i].getCreditLocalDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_CreditGroupDiff()==null || m_OutTableVO[i].getM_CreditGroupDiff().doubleValue()==0)&&
					(m_OutTableVO[i].getM_CreditGlobalDiff()==null || m_OutTableVO[i].getM_CreditGlobalDiff().doubleValue()==0))
				continue;
			aDetail.setLocalcreditamount(m_OutTableVO[i].getCreditLocalDiff());
			aDetail.setGroupcreditamount(m_OutTableVO[i].getM_CreditGroupDiff());
			aDetail.setGlobalcreditamount(m_OutTableVO[i].getM_CreditGlobalDiff());

			aDetail.setLocaldebitamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGroupdebitamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGlobaldebitamount(new nc.vo.pub.lang.UFDouble(0));
			creditTotal=creditTotal.add(aDetail.getLocalcreditamount());
		}
		aDetail.setPk_unit(m_OutTableVO[i].getM_PKUnit());
		vDetail.addElement(aDetail);

	}
	// 科目数组
	String[] pkAccsubjs = new String[m_InTableVO.length];
	Set<String> assIdSet = new HashSet<String>();
	for (int i = 0; i < m_InTableVO.length; i++) {
		pkAccsubjs[i] = m_InTableVO[i].getPKAccount();
		if (m_InTableVO[i].getAssID()!=null && ! "".equals(m_InTableVO[i].getAssID())) {
			assIdSet.add(m_InTableVO[i].getAssID());
		}
	}
	
	UFDouble inBalance = new UFDouble(0);
	List<String> assidList = new ArrayList<String>();
	for (int j = 0; j < m_InTableVO.length; j++) {
		if(!StrTools.isEmptyStr(m_InTableVO[j].getAssID())){
			assidList.add(m_InTableVO[j].getAssID());
		}
	}
	ConcurrentHashMap<String,AssVO[]> assidMap = new ConcurrentHashMap<String,AssVO[]>();
	if(assidList.size()>0){
		assidMap = GLPubProxy.getRemoteFreevaluePub().queryAssvosByAssids(assidList.toArray(new String[0]), Module.GL);
	}
	
	List<AssVO[]> assvoList = new ArrayList<AssVO[]>();
	for (int j = 0; j < m_InTableVO.length; j++) {
		boolean blProfit= m_InTableVO[j].getOrientation().intValue() != Balanorient.DEBIT;
		AssVO[] assvos = null;
		if(m_InTableVO[j].getAssID()!=null){
			assvos = assidMap.get(m_InTableVO[j].getAssID());
		}
		AssVO[] inassvo = genASSID(m_InTableVO[j].getPKAccount(),blProfit, assvos);
		if(inassvo!=null&&inassvo.length>0){
			assvoList.add(inassvo);
		}
	}
	Map<String,String> md5AssidMap = new HashMap<String,String>();
	if(assvoList.size()>0){
		AssVO[][] assvos = new AssVO[assvoList.size()][];
		for(int i=0;i<assvoList.size();i++){
			assvos[i] = assvoList.get(i);
		}
		md5AssidMap = GLPubProxy.getRemoteFreevaluePub().getAssIDs_RequiresNew(assvos, GlWorkBench.getLoginGroup());
	}
	BigAssMD5Util md5Util = new BigAssMD5Util();
	
	for (int j = 0; j < m_InTableVO.length; j++) {

		nc.vo.gl.pubvoucher.DetailVO aDetail = new nc.vo.gl.pubvoucher.DetailVO();

		aDetail.setExplanation(newexpanation);
		aDetail.setPk_accasoa(m_InTableVO[j].getPKAccount());
		//aDetail.setAss(m_InTableVO[j].getAssVO());
		aDetail.setPk_currtype(m_InTableVO[j].getPKCurrType());
		aDetail.setModifyflag("YYYYYYYYYYYYYYYY");
		aDetail.setPk_accountingbook(getPk_orgbook());

		aDetail.setDetailindex(Integer.valueOf(vDetail.size()+1));
		aDetail.setPrice(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setExcrate2(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setDebitquantity(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setDebitamount(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setCreditquantity(new nc.vo.pub.lang.UFDouble(0));
		aDetail.setCreditamount(new nc.vo.pub.lang.UFDouble(0));
       //判断如果收益和损失科目是相同科目不同辅助核算时候的不同处理,
	   //addBy shaoguo.wang 收益和损失也可以在设定处设定一个
		boolean blProfit=false;
		if(m_InTableVO[j].getOrientation().intValue() != Balanorient.DEBIT){
			blProfit=true;
		}
		AssVO[] assvos = null;
		if(m_InTableVO[j].getAssID()!=null){
			assvos = assidMap.get(m_InTableVO[j].getAssID());
		}
		AssVO[] inassvos = genASSID(aDetail.getPk_accasoa(),blProfit, assvos);
		if(inassvos!=null&&inassvos.length>0){
			aDetail.setAssid(md5AssidMap.get(md5Util.getMD5ByAssvos(inassvos, GlWorkBench.getLoginGroup())));
		}
		aDetail.setAss(null);
		if (m_InTableVO[j].getOrientation().intValue() == Balanorient.DEBIT) {

			if((m_InTableVO[j].getDebitLocalDiff()==null || m_InTableVO[j].getDebitLocalDiff().doubleValue()==0)&&
					(m_InTableVO[j].getM_DebitGroupDiff()==null || m_InTableVO[j].getM_DebitGroupDiff().doubleValue()==0)&&
					(m_InTableVO[j].getM_DebitGlobalDiff()==null || m_InTableVO[j].getM_DebitGlobalDiff().doubleValue()==0))
				continue;
 
			aDetail.setLocaldebitamount(m_InTableVO[j].getDebitLocalDiff());
			aDetail.setGroupdebitamount(m_InTableVO[j].getM_DebitGroupDiff());
			aDetail.setGlobaldebitamount(m_InTableVO[j].getM_DebitGlobalDiff());
			//如果入账币种是本币的话  原币的值=本币的值， 否则原币为空
			if (aHeaderVO.getCurrproperty().intValue() == 0){
				aDetail.setDebitamount(aDetail.getLocaldebitamount());
			}
			inBalance = inBalance.add(m_InTableVO[j].getDebitLocalDiff());
			
			aDetail.setLocalcreditamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGroupcreditamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGlobalcreditamount(new nc.vo.pub.lang.UFDouble(0));

		}
		else {


			if((m_InTableVO[j].getCreditLocalDiff()==null || m_InTableVO[j].getCreditLocalDiff().doubleValue()==0)&&
					(m_InTableVO[j].getM_CreditGroupDiff()==null || m_InTableVO[j].getM_CreditGroupDiff().doubleValue()==0)&&
					(m_InTableVO[j].getM_CreditGlobalDiff()==null || m_InTableVO[j].getM_CreditGlobalDiff().doubleValue()==0))
				continue;
			aDetail.setLocalcreditamount(m_InTableVO[j].getCreditLocalDiff());
			aDetail.setGroupcreditamount(m_InTableVO[j].getM_CreditGroupDiff());
			aDetail.setGlobalcreditamount(m_InTableVO[j].getM_CreditGlobalDiff());
			//如果入账币种是本币的话  原币的值=本币的值， 否则原币为空
			if (aHeaderVO.getCurrproperty().intValue() == 0){
				aDetail.setCreditamount(aDetail.getLocalcreditamount());
			}
			inBalance = inBalance.sub(m_InTableVO[j].getCreditLocalDiff());

			aDetail.setLocaldebitamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGroupdebitamount(new nc.vo.pub.lang.UFDouble(0));
			aDetail.setGlobaldebitamount(new nc.vo.pub.lang.UFDouble(0));
		}
		aDetail.setPk_unit(m_OutTableVO[j].getM_PKUnit());
		vDetail.addElement(aDetail);
	}
	if (vDetail.size() == 0)
		return null;
	nc.vo.gl.pubvoucher.DetailVO[] details = new nc.vo.gl.pubvoucher.DetailVO[vDetail.size()];
	vDetail.copyInto(details);
	details=computerDetail(details);
	if(details!=null && details.length>0)
	{	
		makeDetailsSeries(details);
		if(aVoucherVO!=null)
		{
			if(aVoucherVO.getPrepareddate()==null)
				aVoucherVO.setPrepareddate(GlWorkBench.getBusiDate());
			String pk_org = AccountBookUtil.getPk_orgByAccountBookPk(aVoucherVO.getPk_accountingbook());
			aVoucherVO.setPk_org(pk_org);
			Set<String> pk_orgs = new HashSet<String>();
			for (int i = 0;i < details.length; i++){
				if(details[i].getPk_unit()!=null)
					pk_orgs.add(details[i].getPk_unit());
			}
			pk_orgs.add(pk_org);
			
			for (int i = 0;i < details.length; i++){
			    if(details[i].getAssid()!=null&&details[i].getAss()==null)
			    	assIdSet.add(details[i].getAssid());
			}
			
			IAccountingbookService service = (IAccountingbookService)NCLocator.getInstance().lookup(IAccountingbookService.class.getName());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put(IAccountingbookService.DATE, GlWorkBench.getBusiDate());
			map.put(IAccountingbookService.ASSIDS, assIdSet.toArray(new String[]{}));
			map.put(IAccountingbookService.PK_ACCASOAS, pkAccsubjs);
			map.put(IAccountingbookService.PK_ACCOUNTINGBOOKS, new String[]{getPk_orgbook()});
			map.put(IAccountingbookService.PK_UNIT,pk_orgs);
			HashMap<String,Object> returnObj = service.getObjectOnce(map);
			
			GlPeriodVO vo=(GlPeriodVO)returnObj.get(IAccountingbookService.PEROIDVO);
			aVoucherVO.setYear(vo.getYear());
			aVoucherVO.setPeriod(vo.getMonth());
			

			
			if (assIdSet.size() != 0) {
				GlAssVO[] glAssvos = (GlAssVO[])returnObj.get(IAccountingbookService.GLASSVO);
				for(int j = 0; glAssvos != null && j < glAssvos.length; j++ ) {
					assVotab.put(glAssvos[j].getAssID(), glAssvos[j].getAssVos());
				}
			}
			
			Map<String, List<AccAssVO>> accassMap  = (Map<String, List<AccAssVO>>)returnObj.get(IAccountingbookService.ACCASSMAP);
			for (int i = 0; pkAccsubjs != null && i < pkAccsubjs.length; i++) {
				if(accassMap.get(pkAccsubjs[i]) != null) {
					subjAsstab.put(pkAccsubjs[i], new Vector(accassMap.get(pkAccsubjs[i])));
				}
				
			}
			
			HashMap<String, String> versionMap = (HashMap<String, String>)returnObj.get(IAccountingbookService.PK_UNIT_V);
			
			if(aVoucherVO.getPk_accountingbook() != null){
				if(versionMap != null){
					aVoucherVO.setPk_org_v(versionMap.get(pk_org));
				}
				aVoucherVO.setPk_group(GlWorkBench.getLoginGroup());
			}
			
			List<String> assids = new ArrayList<String>();//add by zhaoshya
			for (int i = 0;i < details.length; i++){
				details[i].setPrepareddate(aVoucherVO.getPrepareddate());
			    if(details[i].getAssid()!=null)
			    {
			    	details[i].setAssid(details[i].getAssid().trim());
				}
			
			    if(details[i].getPk_unit()==null)
			    	details[i].setPk_unit(aVoucherVO.getPk_org());
			    details[i].setPrepareddate(aVoucherVO.getPrepareddate());
			    details[i].setPk_unit_v(aVoucherVO.getPk_org_v());
			    if(versionMap != null){
			    	details[i].setPk_unit_v(versionMap.get(details[i].getPk_unit()));
			    }
			    details[i].setPk_org(aVoucherVO.getPk_org());
			    details[i].setPk_org_v(aVoucherVO.getPk_org_v());
			    details[i].setPk_group(GlWorkBench.getLoginGroup());
			    if(details[i].getAssid()!=null&&details[i].getAss()==null)
			    	assids.add(details[i].getAssid());//add by zhaoshya
			}
			
			//add by zhaoshya 没有辅助核算转完不显示， 容错下， 如果有辅助就不处理
			if(assids.size()>0){
				for (int i = 0;i < details.length; i++){
					if(details[i].getAss()==null&&details[i].getAssid()!=null)
						details[i].setAss((AssVO[])assVotab.get(details[i].getAssid()));
				}
			}
			//add by zhaoshya
		}
		UFDouble credit = new UFDouble(0);
		UFDouble debit = new UFDouble(0);
		UFDouble groupcredit = new UFDouble(0);
		UFDouble groupdebit = new UFDouble(0);
		UFDouble globalcredit = new UFDouble(0);
		UFDouble globaldebit = new UFDouble(0);
		 
		for(DetailVO d : details) {
			if(d.getLocaldebitamount().doubleValue()> 0d) {
				debit = debit.add(d.getLocaldebitamount());
				groupdebit = groupdebit.add(d.getGroupdebitamount());
				globaldebit = globaldebit.add(d.getGlobaldebitamount());
			}else{
				credit = credit.add(d.getLocalcreditamount());
				groupcredit = groupcredit.add(d.getGroupcreditamount());
				globalcredit = globalcredit.add(d.getGlobalcreditamount());
			}
		}
		aVoucherVO.setTotaldebit(debit);
		aVoucherVO.setTotalcredit(credit);
		aVoucherVO.setTotalcreditgroup(groupcredit);
		aVoucherVO.setTotaldebitgroup(groupdebit);
		aVoucherVO.setTotalcreditglobal(globalcredit);
		aVoucherVO.setTotaldebitglobal(globaldebit);
		aVoucherVO.setExplanation(details[0].getExplanation());
		aVoucherVO.setDetails(details);
		return aVoucherVO;
	} else {
		return null;
	}
}

/**
 * 入账科目的辅助项根据入账科目的核算项目类型确定
 * 创建日期：(2002-11-14 14:50:51)
 * @return java.lang.Integer
 * @param pk_corp java.lang.String
 * @param accsubj java.lang.String
 * @param assvos nc.vo.glcom.ass.AssVO[]
 */
private AssVO[] genASSID( String accsubj,boolean blIsProfit, AssVO[] assvos) {

try{
	AccountVO accountVO = AccountCache.getInstance().getAccountVOByPK(getPk_orgbook(), accsubj, GlWorkBench.getBusiDate().toStdString());
	if(accountVO.getAccass() != null){
		Map<String, Integer> typesIndex = new HashMap<String, Integer>();
		Set<String> onlyTypes = new HashSet<String>();
		AssVO[] finalAssvo = new AssVO[accountVO.getAccass().length];
		
		for (int j = 0; j < accountVO.getAccass().length; j++) {
			typesIndex.put(accountVO.getAccass()[j].getPk_entity(), j);
			onlyTypes.add(accountVO.getAccass()[j].getPk_entity());
			AssVO assVO = new AssVO();
			assVO.setPk_Checktype(accountVO.getAccass()[j].getPk_entity());
			finalAssvo[j] = assVO;
		}
		
		String ass=getAssByPkSubj(accsubj,blIsProfit);
		if(ass!=null&&ass.trim().length()>0)
		{
			PrepareAssParse pap = new  PrepareAssParse();
			AssVO[] initassvo=pap.prepareAssitantToAssvos(ass,getPK_Corp());
			
			for (int i = 0; i < initassvo.length; i++) {
				if(typesIndex.containsKey(initassvo[i].getPk_Checktype()) && initassvo[i].getPk_Checkvalue() != null){
					onlyTypes.remove(initassvo[i].getPk_Checktype());
					finalAssvo[typesIndex.get(initassvo[i].getPk_Checktype())] = initassvo[i];
				}
				
			}
		}
		if(onlyTypes.size() > 0 && assvos !=null){
			AssVO[] glAssVo = null;
			List<AssVO> assList = new ArrayList<AssVO>();
			for(String pk_type : onlyTypes){
				for(AssVO assvo : assvos){
					if(pk_type.equals(assvo.getPk_Checktype())){
						assList.add(assvo);
						continue;
					}
				}
			}
			glAssVo = assList.toArray(new AssVO[0]);
			if(glAssVo != null && glAssVo.length == 1){
				for (AssVO assVO : glAssVo) {
					finalAssvo[typesIndex.get(assVO.getPk_Checktype())] = assVO;
				}
			}
		}
		return finalAssvo;
	
	}else{
		return null;
	}
}catch (Exception e)
{
	nc.bs.logging.Logger.debug("取科目的辅助项发生错误");
	Logger.error(e.getMessage(), e);
	return null;
}

}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-27 15:41:43)
 */
public nc.vo.gl.pubvoucher.VoucherVO generate(String year, String period) throws Exception{
	dealPLItems();
	nc.vo.gl.pubvoucher.VoucherVO aVoucherVO= fillVoucher(year, period);
	if(aVoucherVO==null)
		throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("20021505","UPP20021505-000358")/*@res "凭证没有需要生成的分录信息，不需要生成凭证！"*/);
	return aVoucherVO;
}
/**
 * 此处插入方法描述。
 * 创建日期：(2004-4-7 12:00:03)
 * @return java.lang.String
 * @param pk_subj java.lang.String
 */
public String getAssByPkSubj(String pk_subj,boolean blIsProfit) {
	String retAss=null;
	String pk_PL=getTransrateHeader().getPk_accsubjPL();
	if(pk_PL!=null && pk_PL.toString().trim().length()>0)
	{
	     retAss=getTransrateHeader().getAsspl();

		}
	else
	{
		if(getTransrateHeader().getPk_accsubjProfit().equals(pk_subj) && blIsProfit)
		retAss=getTransrateHeader().getAssprofit();
	    else
	    retAss=getTransrateHeader().getAssloss();
		}
	return retAss;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-27 16:12:20)
 * @return nc.ui.pub.ClientEnvironment
 */
//public nc.ui.pub.ClientEnvironment getClientEnvironment() {
//	return m_ClientEnvironment;
//}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-28 11:19:24)
 * @return java.lang.String
 */
private String getPK_Corp() {
//	return getClientEnvironment().getCorporation().getPk_corp();
	return AccountBookUtil.getPk_org(getPk_orgbook());
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-12 10:41:41)
 * @return nc.vo.gl.transrate.TransrateDefVO
 */
public nc.vo.gl.transrate.TransrateVO getTransrateVO() {
	return transrateVO;
}

private TransrateHeaderVO getTransrateHeader() {
	TransrateHeaderVO voRet=null;
	if(getTransrateVO()!=null)
		voRet=(TransrateHeaderVO)getTransrateVO().getParentVO();
	return voRet;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-17 13:52:40)
 * @return nc.vo.gl.transrate.TransrateTableVO[]
 * @exception java.lang.Exception 异常说明。
 */
public TransrateTableVO[] getTransRateTableVO() throws java.lang.Exception {
	return m_TransrateTableVO;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-26 16:36:24)
 * @return nc.ui.gl.transrate.TransrateVO_Wrapper
 */
public TransrateVO_Wrapper getVOWrapper() {
	if(m_TransrateVOWrapper==null)
		m_TransrateVOWrapper=new TransrateVO_Wrapper(getPk_orgbook());
	return m_TransrateVOWrapper;
}
/**
 * 调整分录使编号连续
 * 创建日期：(2001-11-27 15:36:21)
 */
private void makeDetailsSeries (nc.vo.gl.pubvoucher.DetailVO[] details) throws java.lang.Exception {
if(details==null || details.length==0)
return ;
for (int i = 0; i < details.length; i++){
	int counter=i+1;
	details[i].setDetailindex(Integer.valueOf(counter));
}

}
/**
 * 根据转出科目辅助核算设置凭证辅助核算
 * 创建日期：(2004-4-7 13:47:27)
 * @return java.util.Vector
 * @param v java.util.Vector
 */
@SuppressWarnings("rawtypes")
public void setFinalAssByInit(Vector v,String pk_subj,boolean blIsProfit) {
	if(v==null || v.size()==0)
	return ;

	PrepareAssParse pap = new  PrepareAssParse();
	String ass=null;
	AssVO[] initassvo=null;

    ass=getAssByPkSubj(pk_subj,blIsProfit);


	 initassvo=null;
	 if(ass==null || ass.toString().trim().length()==0)
	 return ;
	  try{
	   initassvo=pap.prepareAssitantToAssvos(ass,getPK_Corp());
	  }
	  catch(Exception e)
	  {

		  Logger.error(e.getMessage(), e);
		   return;
		  }
	  if(initassvo==null)
	  return ;

	  for (int i = 0; i < initassvo.length; i++){
	  	for (int j = 0; j < v.size() ; j++){
		  	AssVO vo=(AssVO)v.elementAt(j);
	  		if(initassvo[i].getPk_Checktype().equals(vo.getPk_Checktype()))
	  		{
		  		vo.setPk_Checkvalue(initassvo[i].getPk_Checkvalue());
		  		vo.setCheckvaluecode(null);
		  		vo.setCheckvaluename(null);
		  		}
	  	}
	  }


}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-28 11:22:23)
 * @param newM_TransrateDefVO nc.vo.gl.transrate.TransrateDefVO
 */
public void setTransrateVO(nc.vo.gl.transrate.TransrateVO transrateVO) {
	this.transrateVO = transrateVO;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-28 11:22:23)
 * @param newM_TransrateTableVO nc.vo.gl.transrate.TransrateTableVO[]
 */
public void setTransrateTableVO(nc.vo.gl.transrate.TransrateTableVO[] param) {
	if (param != null && param.length > 0) {
		m_TransrateTableVO = new TransrateTableVO[param.length];
		for(int i=0;i<param.length;i++)
			m_TransrateTableVO[i]=(TransrateTableVO)param[i].clone();
	}

}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-11-27 9:19:10)
 * @param param nc.ui.gl.transrate.TransrateVO_Wrapper
 */
public final void setVOWrapper(TransrateVO_Wrapper param) {
	m_TransrateVOWrapper = param;

}
	/**
	 * @return 返回 pk_orgbook。
	 */
	public String getPk_orgbook() {
		return pk_orgbook;
	}
	/**
	 * @param pk_orgbook 要设置的 pk_orgbook。
	 */
	public void setPk_orgbook(String pk_orgbook) {
		this.pk_orgbook = pk_orgbook;
	}
}