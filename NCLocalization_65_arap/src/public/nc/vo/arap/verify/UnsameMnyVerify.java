package nc.vo.arap.verify;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import nc.bs.arap.util.ArapVOUtils;
import nc.bs.logging.Log;
import nc.itf.fi.pub.Currency;
import nc.pubitf.uapbd.DefaultCurrtypeQryUtil;
import nc.vo.arap.agiotage.ArapBusiDataVO;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.bd.currinfo.CurrinfoVO;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.verifynew.pub.DefaultVerifyRuleVO;
import nc.vo.verifynew.pub.IVerifyMethod;
import nc.vo.verifynew.pub.VerifyCom;

/**
 * @author xuhb
 */
public class UnsameMnyVerify implements IVerifyMethod, IVerifyBusiness {

	private static final long serialVersionUID = -8633643216427564606L;
	private VerifyCom verifycom;
	private DefaultVerifyRuleVO rulevo;
	private int pph = 200;// 从 VerifyCom核销上下文中初始化
	private final ArrayList<VerifyDetailVO> retArr = new ArrayList<VerifyDetailVO>();

	private void initPph() {
		if (pph == 200) {
			if (verifycom != null) {
				Integer inte_pph = (Integer) verifycom.getM_context().get("PPH");
				if (inte_pph == null) {
					pph = Integer.valueOf(0);
				} else {
					try {
						pph = inte_pph.intValue() + 1;
					} catch (Exception e) {
					}
				}
			}
		}

	}

	public int getPph() {
		return pph;
	}

	public void setPph(int pph) {
		this.pph = pph;
	}

	public DefaultVerifyRuleVO getRuleVO() {
		return rulevo;
	}

	public void setRuleVO(DefaultVerifyRuleVO rulevo) {
		this.rulevo = rulevo;
	}

	public VerifyCom getVerifycom() {
		return verifycom;
	}

	public void setVerifycom(VerifyCom verifycom) {
		this.verifycom = verifycom;
	}

	/**
	 *
	 */
	public UnsameMnyVerify() {
		super();
	}

	public UnsameMnyVerify(VerifyCom verifycom) {
		super();
		this.verifycom = verifycom;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * nc.ui.verify.pub.IVerifyMethod#onVerify(nc.vo.verify.pub.IVerifyVO[],
	 * nc.vo.verify.pub.IVerifyVO, nc.vo.verify.pub.DefaultVerifyRuleVO)
	 */
	public AggverifyVO onVerify(ArapBusiDataVO[] jf_vos,
			ArapBusiDataVO[] df_vos, DefaultVerifyRuleVO rule) {

		setRuleVO(rule);
		retArr.clear();

		// 检查数据是否合法
		check(jf_vos, df_vos, rulevo);

		int flag = getHxSuanFa(rulevo);
		
		if(!getVerifycom().isAutoVerify()){
			VerifyTool.sortVector(jf_vos, flag);
			VerifyTool.sortVector(df_vos, flag);
		}
		
		String[] jf_keys = rulevo.getM_debtObjKeys();
		if (jf_keys == null) {
			jf_keys = new String[0];
		}
		Vector<String> temp = new Vector<String>();
		for (int i = 0; i < jf_keys.length; i++) {
			temp.add(jf_keys[i]);
		}
//		temp.add(ArapBusiDataVO.CUSTOMER);
//		temp.add(ArapBusiDataVO.SUPPLIER);
//		temp.add(ArapBusiDataVO.PK_DEPTID);
//		temp.add(ArapBusiDataVO.PK_PSNDOC);
		temp.add(ArapBusiDataVO.WLDXPK);
		String[] keys = new String[temp.size()];
		temp.copyInto(keys);

		contract(jf_vos, df_vos, rulevo, keys);
		verify0(jf_vos, df_vos);

		ArrayList<VerifyDetailVO> detailVOList = getLogs();
		VerifyDetailVO[] detailVOArr = new VerifyDetailVO[detailVOList.size()];
		detailVOList.toArray(detailVOArr);
		AggverifyVO aggverifyVO = new AggverifyVO();
		aggverifyVO.setChildrenVO(detailVOArr);
		return aggverifyVO;

	}

	private void contract(ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos,
			DefaultVerifyRuleVO rulevo, String[] keys) {
		// 同对象不同单
		Hashtable<String, Vector<Vector<ArapBusiDataVO>>> hash2 = new Hashtable<String, Vector<Vector<ArapBusiDataVO>>>();
		for (int i = 0; i < jf_vos.length; i++) {
			String allkeys = VerifyTool.getAllkeys(jf_vos[i], keys);
			if (hash2.containsKey(allkeys)) {
				hash2.get(allkeys).get(0).add(jf_vos[i]);
			} else {
				Vector<Vector<ArapBusiDataVO>> vec = new Vector<Vector<ArapBusiDataVO>>();
				vec.add(new Vector<ArapBusiDataVO>());
				vec.add(new Vector<ArapBusiDataVO>());
				hash2.put(allkeys, vec);
				hash2.get(allkeys).get(0).add(jf_vos[i]);
			}
		}
		for (int i = 0; i < df_vos.length; i++) {
			String allkeys = VerifyTool.getAllkeys(df_vos[i], keys);
			if (hash2.containsKey(allkeys)) {
				hash2.get(allkeys).get(1).add(df_vos[i]);
			} else {
				Vector<Vector<ArapBusiDataVO>> vec = new Vector<Vector<ArapBusiDataVO>>();
				vec.add(new Vector<ArapBusiDataVO>());
				vec.add(new Vector<ArapBusiDataVO>());
				hash2.put(allkeys, vec);
				hash2.get(allkeys).get(1).add(df_vos[i]);
			}
		}
		Iterator it = hash2.keySet().iterator();
		while (it.hasNext()) {
			Vector<Vector<ArapBusiDataVO>> vec = hash2.get(it.next());
			Vector<ArapBusiDataVO> jf = vec.get(0);
			Vector<ArapBusiDataVO> df = vec.get(1);
			int k = 0;
			for (int i = 0; i < jf.size(); i++) {
				ArapBusiDataVO vo = jf.elementAt(i);
				//update chenth 20161212 支持手续费按折扣核销
				//update by weiningc 20171012 V633适配至V65 start
				if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
					vo.setAttributeValue(ArapBusiDataVO.DISCTION, vo.getMoney_de());//设置折扣金额
					vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, UFDouble.ZERO_DBL);//设置结算金额为零
				}else if (ArapCommonTool.isZero((UFDouble) vo
						.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
					continue;
				}
				
				for (int j = k; j < df.size(); j++) {
					ArapBusiDataVO vo2 = df.elementAt(j);
					//add chenth 20161212 支持手续费按折扣核销
					//update by weiningc 20171012 V633适配至V65 start
					if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
						vo2.setAttributeValue(ArapBusiDataVO.DISCTION, vo.getMoney_de());//设置折扣金额
						vo2.setAttributeValue(ArapBusiDataVO.SETT_MONEY, UFDouble.ZERO_DBL);//设置结算金额为零
					}else if (ArapCommonTool.isZero((UFDouble) vo2
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
						continue;
					}
					doBusiness(vo, vo2);
					if (ArapCommonTool.isZero((UFDouble) vo
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
						k = j;
						break;
					}
				}
			}
		}

		if (!rulevo.isStrictMatch()) {
			// 不同对象不同单
			int k = 0;
			for (int i = 0; i < jf_vos.length; i++) {
				ArapBusiDataVO vo = jf_vos[i];
				if (ArapCommonTool.isZero((UFDouble) vo
						.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
					continue;
				}
				for (int j = k; j < df_vos.length; j++) {
					ArapBusiDataVO vo2 = df_vos[j];
					if (ArapCommonTool.isZero((UFDouble) vo2
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
						continue;
					}
					doBusiness(vo, vo2);
					if (ArapCommonTool.isZero((UFDouble) vo
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY))) {
						k = j;
						break;
					}

				}
			}
		}
	}

	private ArrayList<VerifyDetailVO> getLogs() {
		return retArr;
	}

	/**
	 * @param rulevo
	 * @return 0,最早余额法， 1，最近余额法
	 */
	private int getHxSuanFa(DefaultVerifyRuleVO rulevo) {
		if (rulevo == null) {
			return 0;
		}
		Integer inte = rulevo.getM_verifySeq();
		int flag = 0;// 默认为最早余额法
		try {
			flag = inte.intValue();
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
			// Logger.info("####ERROR:最早余额法：verifySeq=0， 最近余额法：verifySeq，现在verifySeq="+inte);
		}
		return flag;
	}

	/**/
	private boolean check(ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos,
			DefaultVerifyRuleVO rulevo) {
		if (jf_vos == null || jf_vos.length == 0 || df_vos == null
				|| df_vos.length == 0 || rulevo == null) {
			return false;
		}
		boolean isHand = rulevo.isHand();// 是否手工核销
		if (isHand) {
			// /*
			// * 测试借贷方金额总和是否相等（都折算成中间币种金额，总和在规则VO提供的误差范围之内），如果不相等直接返回
			// */

			UFDouble ufd_jf = sumJsje(jf_vos).abs();
			UFDouble ufd_df = sumJsje(df_vos).abs();
			UFDouble wuchaFanwei = getRuleVO().getM_maxError().abs();

			UFDouble cha = ufd_jf.sub(ufd_df).abs();

			if (UFDoubleTool.isAbsDayu(cha, wuchaFanwei)) {
				return false;
			}
		}

		// 检查借贷方数据是否同时为正或负，如果不同时为正或负，就直接返回
		boolean jf_tonghao = checkTongHao(jf_vos);
		if (!jf_tonghao) {
			return false;
		}
		boolean df_tonghao = checkTongHao(df_vos);
		if (!df_tonghao) {
			return false;
		}
		return true;
	}

	// 判断是否原币余额同号
	private boolean checkTongHao(ArapBusiDataVO[] vos) {
		if (vos == null || vos.length == 0) {
			return false;
		}
		ArapBusiDataVO notZero = null;
		for (int i = 0; i < vos.length; i++) {
			if (vos[i] == null) {
				continue;
			}
			UFDouble ybye = (UFDouble) vos[i]
					.getAttributeValue(ArapBusiDataVO.MONEY_BAL);
			if (notZero == null && !UFDoubleTool.isZero(ybye)) {
				notZero = vos[i];
				continue;
			} else if (notZero == null) {
				continue;
			}

			UFDouble ybye2 = (UFDouble) notZero
					.getAttributeValue(ArapBusiDataVO.MONEY_BAL);
			boolean b = UFDoubleTool.isTonghao(ybye, ybye2);
			if (!b) {
				return false;
			}
		}
		return true;
	}

	// 中间币种结算金额求和
	private UFDouble sumJsje(ArapBusiDataVO[] vos) {
		if (vos == null || vos.length == 0) {
			return null;
		}
		UFDouble sum = new UFDouble(0);
		for (int i = 0; i < vos.length; i++) {
			if (vos[i] == null) {
				continue;
			}

			UFDouble zjbz_jsybje = getJsybje_zjbz(vos[i]);
			sum = sum.add(zjbz_jsybje);
		}
		return sum;
	}

	private UFDouble getJsybje_zjbz(ArapBusiDataVO vo) {
		// 中间币种
		String pk_zjbz = getRuleVO().getM_verifyCurr();

		// 对中间币种汇率
		UFDouble dzjbzHL = null;
		UFDate date = getDate();
		UFDouble zero = new UFDouble(0);

		if (isJieFang(vo)) {
			dzjbzHL = getRuleVO().getM_jfbz2zjbzHL();
		} else {
			dzjbzHL = getRuleVO().getM_dfbz2zjbzHL();
		}
		UFDouble jsybje = (UFDouble) vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY);
		String pk_currtype = vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
				.toString();
		UFDouble zjbz_jsybje = zero;
		try {
			zjbz_jsybje = Currency.getAmountByOpp(this.getPk_corp(),
					pk_currtype, pk_zjbz, jsybje, dzjbzHL, date);
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
		}
		return zjbz_jsybje;
	}

	// 判断该vo在红冲过程中是在借方还是在贷方
	private boolean isJieFang(ArapBusiDataVO vo) {
		if (vo == null) {
			return false;
		}
		try {
			return Integer.valueOf(vo.getAttributeValue(ArapBusiDataVO.DIRECTION)
					.toString()).intValue() == 1;
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
		}
		return false;
	}

	/*
	 * @see
	 * nc.vo.arap.verifynew.IVerifyBusiness#doBusiness(nc.vo.verifynew.pub.VerifyVO
	 * , nc.vo.verifynew.pub.VerifyVO)
	 */
	public void doBusiness(ArapBusiDataVO vo, ArapBusiDataVO vo2) {
		if (vo == null || vo2 == null) {
			return;
		}

		UFDouble jsybje = (UFDouble) vo
				.getAttributeValue(ArapBusiDataVO.MID_SETT);
		UFDouble jsybje2 = (UFDouble) vo2
				.getAttributeValue(ArapBusiDataVO.MID_SETT);

		if (UFDoubleTool.isZero(jsybje) || UFDoubleTool.isZero(jsybje2)) {
			return;
		}
		if (!UFDoubleTool.isTonghao(jsybje, jsybje2)) {
			return;
		}
		if (!yiJie_yiDai(vo, vo2)) {
			return;
		}

		verify(vo, vo2);

	}

	private boolean yiJie_yiDai(ArapBusiDataVO jf_vo, ArapBusiDataVO df_vo) {
		if (jf_vo == null || df_vo == null) {
			return false;
		}
		Integer jffx = Integer.valueOf(jf_vo.getAttributeValue(
				ArapBusiDataVO.DIRECTION).toString());
		Integer dffx = Integer.valueOf(df_vo.getAttributeValue(
				ArapBusiDataVO.DIRECTION).toString());

		try {
			return (jffx.intValue() + dffx.intValue()) == 0;
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
			// Logger.info("jffx:"+jffx+"\tdffx:"+dffx);
			return false;
		}

	}

	private void verify0(ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos) {
		if (jf_vos == null || jf_vos.length == 0 || df_vos == null
				|| df_vos.length == 0) {
			return;
		}
		boolean is_jf_finish = isFinishVerify(jf_vos);
		boolean is_df_finish = isFinishVerify(df_vos);
		if (is_jf_finish && is_df_finish) {
			return;
		}

		if (is_jf_finish && !is_df_finish) {
			ArapBusiDataVO jf_vo = jf_vos[jf_vos.length - 1];
			verify0(df_vos, jf_vo);
		} else if (!is_jf_finish && is_df_finish) {
			ArapBusiDataVO df_vo = df_vos[df_vos.length - 1];
			verify0(jf_vos, df_vo);
		}
	}

	/**
	 * @param df_vos
	 * @param jf_vo
	 */
	private void verify0(ArapBusiDataVO[] vos, ArapBusiDataVO jf_vo) {
		if (vos == null || vos.length == 0) {
			return;
		}
		for (int i = 0; i < vos.length; i++) {
			if (vos[i] == null
					|| vos[i].getAttributeValue(ArapBusiDataVO.MID_SETT) == null
					|| UFDoubleTool.isZero((UFDouble) vos[i]
							.getAttributeValue(ArapBusiDataVO.MID_SETT))) {
				continue;
			}
			verify0(jf_vo, vos[i]);
		}
	}

	private boolean isFinishVerify(ArapBusiDataVO[] jf_vos) {
		if (jf_vos == null || jf_vos.length == 0) {
			return false;
		}
		for (int i = 0; i < jf_vos.length; i++) {
			if (jf_vos[i] == null) {
				continue;
			}
			UFDouble zbjs = (UFDouble) jf_vos[i]
					.getAttributeValue(ArapBusiDataVO.MID_SETT);
			if (zbjs != null && !UFDoubleTool.isZero(zbjs)) {
				return false;
			}
		}
		return true;

	}

	// 和0核销
	private void verify0(ArapBusiDataVO vo, ArapBusiDataVO vo2) {
		if (vo == null || vo2 == null) {
			return;
		}
		UFDouble jsybje = (UFDouble) vo
				.getAttributeValue(ArapBusiDataVO.MID_SETT);
		UFDouble jsybje2 = (UFDouble) vo2
				.getAttributeValue(ArapBusiDataVO.MID_SETT);
		if ((jsybje == null || UFDoubleTool.isZero(jsybje))
				&& (jsybje2 == null || UFDoubleTool.isZero(jsybje2))) {
			return;
		}
		verifywith0(vo, vo2);
	}

	private void verifywith0(ArapBusiDataVO vo, ArapBusiDataVO vo2) {

		if (vo == null || vo2 == null) {
			return;
		}
		ArapBusiDataVO jf_vo = null;
		ArapBusiDataVO df_vo = null;

		if (isJieFang(vo)) {
			jf_vo = vo;// 本方为借方
			df_vo = vo2;// 对方为贷方
		} else {
			jf_vo = vo2;
			df_vo = vo;
		}
		// 从VerifyCom中的核销上下文里初始化匹配号
		initPph();
		UFDouble zero = new UFDouble(0);
		
		//add chenth 20161220 支持手续费按折扣核销 只需要生成借方折扣核销，不需要生成贷方折扣核销
		//update by weiningc 20171012 V633适配至V65 start
        if(UFBoolean.TRUE.equals(jf_vo.getIsbankcharges())){
        	Integer inte_pph = Integer.valueOf(pph++);
        	createZKLogvo(jf_vo,df_vo,++inte_pph);
        	return;
        }
        //add end

		UFDouble jf_zk = (UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);// 折扣
		if (jf_zk == null) {
			jf_zk = zero;
		}
		UFDouble df_zk = (UFDouble) df_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);
		if (df_zk == null) {
			df_zk = zero;
		}

		// 生成核销明细表VO
		VerifyDetailVO detaildeVO = new VerifyDetailVO();
		detaildeVO.setIsreserve(getVerifycom().getReserveFlag());
		detaildeVO.setStatus(VOStatus.NEW); // 插入数据必须设置为New
		deepclone(detaildeVO, jf_vo);
		detaildeVO.setSrc_org(df_vo.getPk_org());
		detaildeVO.setJd_flag(1);
		detaildeVO.setMoney_de((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY));
		detaildeVO.setMoney_cr(UFDouble.ZERO_DBL);
		// 计算辅币本币
		VerifyTool.bb_fb_jisuan_new(jf_vo, getVerifyRate(jf_vo), detaildeVO,
				getDate(), getPk_corp());
		subJsybje(jf_vo, true, null, null);
		UFDouble jf_clybje = detaildeVO.getMoney_de();

		// 数量
		UFDouble jf_shl = null;
		UFDouble df_shl = null;
		UFDouble jf_dj = null;
		UFDouble df_dj = null;

//		detaildeVO.setM_jfverifyVO(jf_vo);
//		detaildeVO.setM_dfverifyVO(df_vo);

		jf_dj = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.PRICE);
		df_dj = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.PRICE);


		if (jf_dj != null && !UFDoubleTool.isZero(jf_dj)) {

			if (ArapCommonTool.isZero(jf_clybje)) {
				jf_shl = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.QUANTITY_BAL);
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						new UFDouble(0));
			} else {
				jf_shl = jf_clybje.div(jf_dj, ArapVOUtils.getDecimalFromSource(jf_vo.getMaterial()));
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						((UFDouble) jf_vo
								.getAttributeValue(ArapBusiDataVO.QUANTITY_BAL))
								.sub(jf_shl));
			}
		}
		detaildeVO.setQuantity_de(jf_shl);


		Integer inte_pph = Integer.valueOf(pph++);
		detaildeVO.setRowno(inte_pph);

		// 匹配号放到核销上下文中
		verifycom.getM_context().put("PPH", inte_pph);
		setDetailVO(detaildeVO, jf_vo, df_vo);
		UFDate busidate = detaildeVO.getBusidate();
		try {
			if(detaildeVO.getMoney_de() ==null ||detaildeVO.getMoney_de().compareTo(UFDouble.ZERO_DBL)==0){
				detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
			}else{
				detaildeVO.setLocal_money_de(Currency.getAmountByOpp(jf_vo.getPk_org(),
						jf_vo.getPk_currtype(),
						DefaultCurrtypeQryUtil.getInstance()
								.getDefaultCurrtypeByOrgID(jf_vo.getPk_org())
								.getPk_currtype(), detaildeVO.getMoney_de(),(UFDouble)getVerifyRateBz(jf_vo)[1],
						busidate));
			}
			detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);

//			UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//					detaildeVO.getMoney_de(), detaildeVO.getLocal_money_de()==null?UFDouble.ZERO_DBL:detaildeVO.getLocal_money_de(),
//					jf_vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
//							.toString(), busidate, jf_vo.getAttributeValue(
//							ArapBusiDataVO.PK_ORG).toString(),null,null);
//			// 借方全局本位币
//			detaildeVO.setGlobal_money_de(group_global[1]);
//			// 贷方全局本位币
//			detaildeVO.setGlobal_money_cr(UFDouble.ZERO_DBL);
//			// 借方集团本位币
//			detaildeVO.setGroup_money_de(group_global[0]);
//			// 贷方集团本位币
//			detaildeVO.setGroup_money_cr(UFDouble.ZERO_DBL);
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}

		// 匹配号放到核销上下文中
		verifycom.getM_context().put("PPH", inte_pph);
		setDetailVO(detaildeVO, jf_vo, df_vo);
		if (detaildeVO.getMoney_de() == null || detaildeVO.getMoney_de().compareTo(UFDouble.ZERO_DBL) == 0) {
		} else {
			getLogs().add(detaildeVO);
		}

		// 贷
		VerifyDetailVO detailcrVO = (VerifyDetailVO) detaildeVO.clone();
		detailcrVO.setStatus(VOStatus.NEW); // 插入数据必须设置为New
		deepclone(detailcrVO, df_vo);
		detailcrVO.setJd_flag(-1);
		// 处理编号
		detailcrVO.setRowno(inte_pph);
		detailcrVO.setMoney_cr((UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.SETT_MONEY));
		detailcrVO.setMoney_de(UFDouble.ZERO_DBL);
		// 计算辅币本币
		VerifyTool.bb_fb_jisuan_new(df_vo, getVerifyRate(df_vo), detailcrVO, getDate(), getPk_corp());
		subJsybje(df_vo, true, null, null);
		UFDouble df_clybje = detailcrVO.getMoney_cr();

		detailcrVO.setM_jfverifyVO(df_vo);
		detailcrVO.setM_dfverifyVO(jf_vo);

		try {
			if (detailcrVO.getMoney_cr() == null
					|| detailcrVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL) == 0) {
				detailcrVO.setLocal_money_cr(UFDouble.ZERO_DBL);
			} else {
				detailcrVO.setLocal_money_cr(Currency.getAmountByOpp(df_vo.getPk_org(), df_vo
						.getPk_currtype(), DefaultCurrtypeQryUtil.getInstance()
						.getDefaultCurrtypeByOrgID(df_vo.getPk_org()).getPk_currtype(), detailcrVO
						.getMoney_cr(), getVerifyRate(df_vo)[1], busidate));
			}
			detailcrVO.setLocal_money_de(UFDouble.ZERO_DBL);

//			UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//					detailcrVO.getMoney_cr(), detailcrVO.getLocal_money_cr()==null?UFDouble.ZERO_DBL:detailcrVO.getLocal_money_cr(),
//					df_vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
//							.toString(), busidate, df_vo.getAttributeValue(
//							ArapBusiDataVO.PK_ORG).toString(),null,null);
//			// 借方全局本位币
//			detailcrVO.setGlobal_money_cr(group_global[1]);
//			// 贷方全局本位币
//			detailcrVO.setGlobal_money_de(UFDouble.ZERO_DBL);
//			// 借方集团本位币
//			detailcrVO.setGroup_money_cr(group_global[0]);
//			// 贷方集团本位币
//			detailcrVO.setGroup_money_de(UFDouble.ZERO_DBL);
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		detailcrVO.setM_jfverifyVO(df_vo);
		detailcrVO.setM_dfverifyVO(jf_vo);

		// 数量
		if (df_dj != null && UFDoubleTool.isZero(df_dj)) {

			if (ArapCommonTool.isZero(df_clybje)) {
				df_shl = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.QUANTITY_BAL);
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL, new UFDouble(0));
			} else {
				if (df_dj.compareTo(UFDouble.ZERO_DBL) == 0) {
					df_shl = UFDouble.ZERO_DBL;
				} else {
					df_shl = df_clybje.div(df_dj,ArapVOUtils.getDecimalFromSource(df_vo.getMaterial()));
				}
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL, ((UFDouble) df_vo
						.getAttributeValue(ArapBusiDataVO.QUANTITY_BAL)).sub(df_shl));
			}

			detailcrVO.setQuantity_cr(df_shl);
		}
		setDetailVO(detailcrVO, df_vo, jf_vo);
		detailcrVO.setSrc_org(jf_vo.getPk_org());
		if (detailcrVO.getMoney_cr() == null || detailcrVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL) == 0) {
		} else {
			getLogs().add(detailcrVO);
		}

		// 折扣项
		createZKLogvo(jf_vo, df_vo, inte_pph);
		createZKLogvo(df_vo, jf_vo, inte_pph);

	}

	private void verify(ArapBusiDataVO vo, ArapBusiDataVO vo2) {
		if (vo == null || vo2 == null) {
			return;
		}
		ArapBusiDataVO jf_vo = null;
		ArapBusiDataVO df_vo = null;
		boolean isJfClear = false;
		boolean isDfClear = false;

		if (isJieFang(vo)) {
			jf_vo = vo;// 本方为借方
			df_vo = vo2;// 对方为贷方
		} else {
			jf_vo = vo2;
			df_vo = vo;
		}
		// 从VerifyCom中的核销上下文里初始化匹配号
		initPph();
		UFDouble zero = new UFDouble(0);

		//add chenth 20161212 支持手续费按折扣核销 只需要生成借方折扣核销，不需要生成贷方折扣核销
		//update by weiningc 20171012 V633适配至V65 start
        if(UFBoolean.TRUE.equals(jf_vo.getIsbankcharges())){
        	Integer inte_pph = Integer.valueOf(pph++);
        	createZKLogvo(jf_vo,df_vo,++inte_pph);
        	return;
        }
        //add end
		
		UFDouble jf_zk = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.DISCTION);// 折扣
		if (jf_zk == null) {
			jf_zk = zero;
		}
		UFDouble df_zk = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.DISCTION);
		if (df_zk == null) {
			df_zk = zero;
		}

		UFDouble clzbjs = null;

		UFDouble jf_zbjs = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.MID_SETT);
		UFDouble df_zbjs = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.MID_SETT);

		if (UFDoubleTool.isAbsDayu(jf_zbjs, df_zbjs)) {
			clzbjs = df_zbjs;
			isDfClear = true;
		} else if (UFDoubleTool.isXiangdeng(jf_zbjs, df_zbjs)) {
			isDfClear = true;
			isJfClear = true;
		} else {
			clzbjs = jf_zbjs;
			isJfClear = true;
		}

		// 生成VerifyLogVO
		VerifyDetailVO detaildeVO = new VerifyDetailVO();
		detaildeVO.setIsreserve(getVerifycom().getReserveFlag());
		detaildeVO.setJd_flag(1);
		deepclone(detaildeVO, jf_vo);
		detaildeVO.setSrc_org(df_vo.getPk_org());
		detaildeVO.setStatus(VOStatus.NEW); // 插入数据必须设置为New
		detaildeVO.setMoney_de((UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.SETT_MONEY));
		detaildeVO.setMoney_cr(new UFDouble(0));

		// 处理核销原币辅币本币
		dealYbFbBb(jf_vo, isJfClear, clzbjs, detaildeVO);

		UFDouble jf_clybje = detaildeVO.getMoney_de();

		// 处理标志（clbz）：-2—折扣、-1、异币种核销、0、同币种核销，1、汇兑损益、
		// 2—红票对冲、票据贴现; 3、坏账发生; 4、坏账收回; 5、并帐转出；6、并帐转入

		// 数量
		UFDouble jf_shl = null;
		UFDouble df_shl = null;
		UFDouble jf_dj = null;
		UFDouble df_dj = null;

		// detaildeVO.setM_jfverifyVO(jf_vo);
		// detaildeVO.setM_dfverifyVO(df_vo);

		jf_dj = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.PRICE);
		df_dj = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.PRICE);

		if (isJfClear) {
			detaildeVO.setQuantity_de(jf_vo.getQuantity());
		} else if (jf_dj != null && !UFDoubleTool.isZero(jf_dj)) {

			if (ArapCommonTool.isZero(jf_clybje)) {
				jf_shl = jf_vo.getQuantity();
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY, new UFDouble(0));
			} else {
				if (jf_dj.compareTo(UFDouble.ZERO_DBL) == 0) {
					jf_shl = UFDouble.ZERO_DBL;
				} else {
					jf_shl = jf_clybje.div(jf_dj);
				}
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY, jf_vo.getQuantity().sub(jf_shl));
			}
			detaildeVO.setQuantity_de(jf_shl);
		} else {
			detaildeVO.setQuantity_de(UFDouble.ZERO_DBL);
		}

		Integer inte_pph = Integer.valueOf(pph++);

		// 匹配号放到核销上下文中
		verifycom.getM_context().put("PPH", inte_pph);
		// 处理序号
		detaildeVO.setRowno(inte_pph);
		setDetailVO(detaildeVO, jf_vo, df_vo);
		UFDate busidate = detaildeVO.getBusidate();
		try {
			if (detaildeVO.getMoney_de() == null || detaildeVO.getMoney_de().compareTo(UFDouble.ZERO_DBL) == 0) {
				detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
			} else {
				detaildeVO.setLocal_money_de(Currency.getAmountByOpp(jf_vo.getPk_org(), jf_vo
						.getPk_currtype(), DefaultCurrtypeQryUtil.getInstance()
						.getDefaultCurrtypeByOrgID(jf_vo.getPk_org()).getPk_currtype(), detaildeVO
						.getMoney_de(), (UFDouble) getVerifyRateBz(jf_vo)[1], busidate));
			}
			detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);

		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		getLogs().add(detaildeVO);

		// 贷-------------------------------------------
		VerifyDetailVO detailcrVO = new VerifyDetailVO();
		detailcrVO.setIsreserve(getVerifycom().getReserveFlag());
		deepclone(detailcrVO, df_vo);
		detailcrVO.setSrc_org(jf_vo.getPk_org());
		detailcrVO.setStatus(VOStatus.NEW); // 插入数据必须设置为New
		detailcrVO.setMoney_de(UFDouble.ZERO_DBL);
		detailcrVO.setJd_flag(-1);
		setDetailVO(detailcrVO, df_vo, jf_vo);
		// 处理序号
		detailcrVO.setRowno(inte_pph);
		// 处理核销原币辅币本币
		dealYbFbBb(df_vo, isDfClear, clzbjs, detailcrVO);

		UFDouble df_clybje = detailcrVO.getMoney_cr();

		try {
			if (detailcrVO.getMoney_cr() == null || detailcrVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL) == 0) {
				detailcrVO.setLocal_money_cr(UFDouble.ZERO_DBL);
			} else {
				detailcrVO.setLocal_money_cr(Currency.getAmountByOpp(df_vo.getPk_org(), df_vo
						.getPk_currtype(), DefaultCurrtypeQryUtil.getInstance()
						.getDefaultCurrtypeByOrgID(df_vo.getPk_org()).getPk_currtype(), detailcrVO
						.getMoney_cr(), (UFDouble) getVerifyRateBz(df_vo)[1], busidate));
			}
			detailcrVO.setLocal_money_de(UFDouble.ZERO_DBL);

		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
		detailcrVO.setM_jfverifyVO(df_vo);
		detailcrVO.setM_dfverifyVO(jf_vo);

		// 数量
		if (isDfClear) {
			detailcrVO.setQuantity_cr(df_vo.getQuantity());
		} else if (df_dj != null && UFDoubleTool.isZero(df_dj)) {
			if (ArapCommonTool.isZero(df_clybje)) {
				df_shl = df_vo.getQuantity();
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY, new UFDouble(0));
			} else {
				if (df_dj.compareTo(UFDouble.ZERO_DBL) == 0) {
					df_shl = UFDouble.ZERO_DBL;
				} else {
					df_shl = df_clybje.div(df_dj);
				}
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY, df_vo.getQuantity().sub(df_shl));
			}

			detailcrVO.setQuantity_cr(df_shl);
		}

		getLogs().add(detailcrVO);

		// 折扣项
		createZKLogvo(jf_vo, df_vo, inte_pph);
		createZKLogvo(df_vo, jf_vo, inte_pph);
	}

	/**
	 * @param verifyVO
	 * @param isClear
	 * @param clzbjs
	 * @param logvo
	 *            处理核销原币辅币本币
	 */
	private void dealYbFbBb(ArapBusiDataVO verifyVO, boolean isClear,
			UFDouble clzbjs, VerifyDetailVO detailVO) {
		// 取核销汇率和币种
		Object[] verifyRateBz_df = getVerifyRateBz(verifyVO);
		// 计算核销结算原币金额
		UFDouble jsybje_df = getJsybje(verifyVO, clzbjs, isClear,
				(UFDouble) verifyRateBz_df[2], (String) verifyRateBz_df[3]);
		// 设置核销处理原币金额

		if ((Integer) verifyVO.getAttributeValue(ArapBusiDataVO.DIRECTION) == 1) {
			detailVO.setMoney_de(jsybje_df);
		} else if ((Integer) verifyVO
				.getAttributeValue(ArapBusiDataVO.DIRECTION) == -1) {
			detailVO.setMoney_cr(jsybje_df);
		}
		// 处理辅币金额和本币金额
		VerifyTool.bb_fb_jisuan_new(verifyVO, new UFDouble[] {
				(UFDouble) verifyRateBz_df[0], (UFDouble) verifyRateBz_df[1] },
				detailVO, getDate(), getPk_corp());
		// 扣除核销结算原币和中币
		subJsybje(verifyVO, isClear, clzbjs, jsybje_df);
	}

	private void subJsybje(ArapBusiDataVO vo, boolean isClear, UFDouble clzbjs,
			UFDouble jsybje) {
		if (isClear) {
			vo.setAttributeValue(ArapBusiDataVO.MID_SETT, new UFDouble(0));
			vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, new UFDouble(0));
		} else {
			vo.setAttributeValue(ArapBusiDataVO.MID_SETT, ((UFDouble) vo
					.getAttributeValue(ArapBusiDataVO.MID_SETT)).sub(clzbjs));
			vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, ((UFDouble) vo
					.getAttributeValue(ArapBusiDataVO.SETT_MONEY)).sub(jsybje));
		}
	}

	/**
	 * @param vo
	 * @param logvo
	 * @param clzbjs
	 * @param clear
	 *
	 *            进行原币，辅币和本币余额的计算
	 *
	 * @deprecated
	 */
//	@Deprecated
//	private void yb_fb_bb_jisuan(ArapBusiDataVO vo, VerifyLogVO logvo,
//			UFDouble clzbjs, boolean clear) {
//
//		if (vo == null || logvo == null) {
//			return;
//		}
//		String pk_currtype = vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
//				.toString();
//		UFDate date = getDate();
//		try {
//			UFDouble bbhl = Currency.getRate(this.getPk_corp(), pk_currtype,
//					date);
//			UFDouble zbhl = null;
//
//			String ybpk = null;
//			String fbpk = getRuleVO().getM_fbpk();
//			String bbpk = getRuleVO().getM_bbpk();
//			UFDouble fbhl = UFDouble.ZERO_DBL;
//
//			if (!isJieFang(vo)) {
//				bbhl = getRuleVO().getM_creditoBBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_creditoBBExchange_rate()) ? bbhl
//						: getRuleVO().getM_creditoBBExchange_rate();
//				fbhl = getRuleVO().getM_creditoFBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_creditoFBExchange_rate()) ? fbhl
//						: getRuleVO().getM_creditoFBExchange_rate();
//				zbhl = getRuleVO().getM_dfbz2zjbzHL();
//				ybpk = getRuleVO().getM_creditCurr();
//			} else {
//				bbhl = getRuleVO().getM_debttoBBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_debttoBBExchange_rate()) ? bbhl
//						: getRuleVO().getM_debttoBBExchange_rate();
//				fbhl = getRuleVO().getM_debttoFBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_debttoFBExchange_rate()) ? fbhl
//						: getRuleVO().getM_debttoFBExchange_rate();
//				zbhl = getRuleVO().getM_jfbz2zjbzHL();
//				ybpk = getRuleVO().getM_debitCurr();
//			}
//
//			// 借方结算辅币金额，结算本币金额
//
//			UFDouble jsybje = getJsybje(vo, clzbjs, clear, zbhl, ybpk);
//
//			UFDouble jsfbje = UFDouble.ZERO_DBL;
//			UFDouble jsbbje = UFDouble.ZERO_DBL;
//
//			try {
//
//				if (fbhl == null || fbhl.equals(UFDouble.ZERO_DBL)) {// 辅币汇率为零表示单主币核算或者币种是本币
//					jsbbje = Currency.getAmountByOpp(this.getPk_corp(),
//							pk_currtype, bbpk, jsybje, bbhl, date);
//				} else {
//					jsfbje = Currency.getAmountByOpp(this.getPk_corp(),
//							pk_currtype, fbpk, jsybje, fbhl, date);
//					jsbbje = Currency.getAmountByOpp(this.getPk_corp(), fbpk,
//							bbpk, jsfbje, bbhl, date);
//				}
//
//			} catch (Exception e) {
//				Log.getInstance(this.getClass()).debug(e.getMessage());
//			}
//
//			logvo.setM_clybje(jsybje);
//			logvo.setM_clfbje(jsfbje);
//			logvo.setM_clbbje(jsbbje);
//
//			if (clear) {
//				vo.setAttributeValue(ArapBusiDataVO.MID_SETT, new UFDouble(0));
//				vo
//						.setAttributeValue(ArapBusiDataVO.SETT_MONEY,
//								new UFDouble(0));
//			} else {
//				vo.setAttributeValue(ArapBusiDataVO.MID_SETT, ((UFDouble) vo
//						.getAttributeValue(ArapBusiDataVO.MID_SETT))
//						.sub(clzbjs));
//				vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, ((UFDouble) vo
//						.getAttributeValue(ArapBusiDataVO.SETT_MONEY))
//						.sub(jsybje));
//			}
//
//		} catch (Exception e) {
//			Log.getInstance(this.getClass()).debug(e.getMessage());
//		}
//	}

	@Deprecated
	private UFDouble getJsybje(ArapBusiDataVO vo, UFDouble clzbjs,
			boolean clear, UFDouble zbhl, String ybpk) {

		String zbpk = getRuleVO().getM_verifyCurr();

		UFDouble jsybje = UFDouble.ZERO_DBL;

		if (clear) {
			jsybje = (UFDouble) vo.getAttributeValue(ArapBusiDataVO.SETT_MONEY);
		} else {
			try {
				CurrinfoVO currinfoVO = Currency.getCurrRateInfo(this
						.getPk_corp(), ybpk, zbpk);
				// 暂时注释掉
				if (currinfoVO.getConvmode() == 0) {
					jsybje = clzbjs.div(zbhl, Currency.getCurrDigit(ybpk));
				} else {
					jsybje = clzbjs.multiply(zbhl, Currency.getCurrDigit(ybpk));
				}

			} catch (Exception e) {
				Log.getInstance(this.getClass()).debug(e.getMessage());
			}
		}
		return jsybje;
	}


	public java.lang.String getPk_corp() {
		try {
			return this.rulevo.getPk_org();
		} catch (Exception e) {

		}
		return null;
	}

	private UFDate getDate() {
		return getRuleVO().getM_Clrq();
	}

	/**
	 * @param jf_vo
	 * @param jf_ybye
	 * @param jsybje
	 * @param jf_bbhl
	 * @param jf_fbhl
	 *
	 *            进行辅币和本币余额的计算
	 *
	 * @deprecated
	 */
//	@Deprecated
//	private void bb_fb_jisuan(ArapBusiDataVO vo, VerifyLogVO logvo) {
//		if (vo == null || logvo == null) {
//			return;
//		}
//
//		UFDouble jsybje = logvo.getM_clybje();
//		String pk_currtype = vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
//				.toString();
//		UFDate date = getDate();
//		try {
//			UFDouble bbhl = Currency.getRate(this.getPk_corp(), pk_currtype,
//					date);
//			UFDouble fbhl = UFDouble.ZERO_DBL;
//
//			if (!isJieFang(vo)) {
//				bbhl = getRuleVO().getM_creditoBBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_creditoBBExchange_rate()) ? bbhl
//						: getRuleVO().getM_creditoBBExchange_rate();
//			} else {
//				bbhl = getRuleVO().getM_debttoBBExchange_rate() == null
//						|| ArapCommonTool.isZero(getRuleVO()
//								.getM_debttoBBExchange_rate()) ? bbhl
//						: getRuleVO().getM_debttoBBExchange_rate();
//			}
//
//			String fbpk = getRuleVO().getM_fbpk();
//			String bbpk = getRuleVO().getM_bbpk();
//
//			// 借方结算辅币金额，结算本币金额
//			UFDouble jsfbje = UFDouble.ZERO_DBL;
//			UFDouble jsbbje = UFDouble.ZERO_DBL;
//
//			try {
//				if (fbhl == null || fbhl.equals(UFDouble.ZERO_DBL)) {// 辅币汇率为零表示单主币核算或者币种是本币
//					jsbbje = Currency.getAmountByOpp(this.getPk_corp(),
//							pk_currtype, bbpk, jsybje, bbhl, date);
//				} else {
//					jsfbje = Currency.getAmountByOpp(this.getPk_corp(),
//							pk_currtype, fbpk, jsybje, fbhl, date);
//					jsbbje = Currency.getAmountByOpp(this.getPk_corp(), fbpk,
//							bbpk, jsfbje, bbhl, date);
//				}
//
//			} catch (Exception e) {
//				Log.getInstance(this.getClass()).debug(e.getMessage());
//			}
//
//			logvo.setM_clfbje(jsfbje);
//			logvo.setM_clbbje(jsbbje);
//
//			// vo.setM_jsfbje(jsfbje);
//			// vo.setM_jsbbje(jsbbje);
//		} catch (Exception e) {
//			Log.getInstance(this.getClass()).debug(e.getMessage());
//		}
//
//	}

	@Deprecated
	private void createZKLogvo(ArapBusiDataVO jf_vo,ArapBusiDataVO df_vo, Integer inte_pph) {
		if (jf_vo == null) {
			return;
		}
		UFDouble jf_zk = (UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);// 折扣
		// 折扣
		if (jf_zk != null && !UFDoubleTool.isZero(jf_zk)) {
			VerifyDetailVO detaildeVO = new VerifyDetailVO();
			detaildeVO.setIsreserve(getVerifycom().getReserveFlag());
			deepclone(detaildeVO, jf_vo);
			//折扣核销设置
			detaildeVO.setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0","02006ver-0322")/*@res "折扣核销"*/);
			detaildeVO.setStatus(VOStatus.NEW); // 插入数据必须设置为New
			//核销标志
			detaildeVO.setBusiflag(ArapBillDealVOConsts.DISCTIONVERIFY_FLAG);
			setDetailVO(detaildeVO, jf_vo, df_vo);
			// 核销汇率
			detaildeVO.setRate(getVerifyRate(jf_vo)[1]);
			// 处理序号
			detaildeVO.setRowno(inte_pph);

			UFDate busidate = detaildeVO.getBusidate();
			if(jf_vo.getDirection()==1){
				detaildeVO.setSrc_org(df_vo.getPk_org());
				detaildeVO.setMoney_de(jf_zk);
				detaildeVO.setMoney_cr(UFDouble.ZERO_DBL);
				try {
					if(detaildeVO.getMoney_de() ==null ||detaildeVO.getMoney_de().compareTo(UFDouble.ZERO_DBL)==0){
						detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
					}else{
						detaildeVO.setLocal_money_de(Currency.getAmountByOpp(jf_vo.getPk_org(),
								jf_vo.getPk_currtype(),
								DefaultCurrtypeQryUtil.getInstance()
										.getDefaultCurrtypeByOrgID(jf_vo.getPk_org())
										.getPk_currtype(), detaildeVO.getMoney_de(),getVerifyRate(jf_vo)[1],
								busidate));

					}
					detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);
//					UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//							detaildeVO.getMoney_de(), detaildeVO.getLocal_money_de()==null?UFDouble.ZERO_DBL:detaildeVO.getLocal_money_de(),
//							jf_vo.getPk_currtype(), busidate, jf_vo.getPk_org()
//									.toString(),null,null);
//					// 借方全局本位币
//					detaildeVO.setGlobal_money_de(group_global[1]);
//					// 贷方全局本位币
//					detaildeVO.setGlobal_money_cr(UFDouble.ZERO_DBL);
//					// 借方集团本位币
//					detaildeVO.setGroup_money_de(group_global[0]);
//					// 贷方集团本位币
//					detaildeVO.setGroup_money_cr(UFDouble.ZERO_DBL);
				} catch (Exception e) {
					ExceptionHandler.handleRuntimeException(e);
				}
			}else if(jf_vo.getDirection()==-1) {
				detaildeVO.setSrc_org(jf_vo.getPk_org());
				detaildeVO.setMoney_cr(jf_zk);
				detaildeVO.setMoney_de(UFDouble.ZERO_DBL);
				try {
					if(detaildeVO.getMoney_cr() ==null ||detaildeVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL)==0){
						detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);
					}else{
						detaildeVO.setLocal_money_cr(Currency.getAmountByOpp(jf_vo.getPk_org(),
								jf_vo.getPk_currtype(),
								DefaultCurrtypeQryUtil.getInstance()
										.getDefaultCurrtypeByOrgID(jf_vo.getPk_org())
										.getPk_currtype(), detaildeVO.getMoney_cr(),getVerifyRate(jf_vo)[1],
								busidate));

					}
					detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
//					UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//							detaildeVO.getMoney_cr(), detaildeVO.getLocal_money_cr()==null?UFDouble.ZERO_DBL:detaildeVO.getLocal_money_cr(),
//							jf_vo.getPk_currtype(), busidate, jf_vo.getPk_org()
//									.toString(),null,null);
//					// 借方全局本位币
//					detaildeVO.setGlobal_money_cr(group_global[1]);
//					// 贷方全局本位币
//					detaildeVO.setGlobal_money_de(UFDouble.ZERO_DBL);
//					// 借方集团本位币
//					detaildeVO.setGroup_money_cr(group_global[0]);
//					// 贷方集团本位币
//					detaildeVO.setGroup_money_de(UFDouble.ZERO_DBL);
				} catch (Exception e) {
					ExceptionHandler.handleRuntimeException(e);
				}
			}
			
			//add chenth 20161212 支持手续费按折扣核销
			//update by weiningc 20171012 V633适配至V65 start
			if(UFBoolean.TRUE.equals(jf_vo.getIsbankcharges())){
				detaildeVO.setOccupationmny(jf_zk);
			}
			//add end

//			detaildeVO.setM_jfverifyVO(jf_vo);
//			detaildeVO.setM_dfverifyVO(df_vo);
			getLogs().add(detaildeVO);
			jf_vo.setAttributeValue(ArapBusiDataVO.DISCTION, null);
		}

//		if (jf_vo == null) {
//			return;
//		}
//		UFDouble jf_zk = (UFDouble) jf_vo
//				.getAttributeValue(ArapBusiDataVO.DISCTION);// 折扣
//		// 折扣
//		if (jf_zk != null && !UFDoubleTool.isZero(jf_zk.abs())) {
//			VerifyLogVO zk_logvo = new VerifyLogVO();
//			setVerifyEnv(zk_logvo);
//			zk_logvo.setM_clybje(jf_zk);
//			zk_logvo.setM_clbz(Integer.valueOf(-2));
//			zk_logvo
//					.setM_jdbz(Integer.valueOf(jf_vo.getAttributeValue(
//							ArapBusiDataVO.DIRECTION).toString()) == 1 ? ArapConstant.INT_ZERO
//							: ArapConstant.INT_ONE);
//			zk_logvo.setM_jfverifyVO(jf_vo);
//			zk_logvo.setM_dfverifyVO(jf_vo);
//			zk_logvo.setM_pph(inte_pph);
//			// 处理辅币金额和处理本币金额,借方和贷方相同的辅币汇率和相同的本币汇率
//			// 暂时注释掉
//			// VerifyTool.bb_fb_jisuan_new(jf_vo, getVerifyRate(jf_vo),
//			// zk_logvo, getDate(), getPk_corp());
//			// getLogs().add(zk_logvo);
//		}
	}

	private UFDouble[] getVerifyRate(ArapBusiDataVO vo) {

		Object[] verifyRate = getVerifyRateBz(vo);

		return new UFDouble[] { (UFDouble) verifyRate[0],
				(UFDouble) verifyRate[1] };
	}

	/**
	 * @param vo
	 * @return 核销辅币汇率 核销本币汇率 核销对中间币种汇率 核销原币币种
	 */
	private Object[] getVerifyRateBz(ArapBusiDataVO vo) {

		String pk_currtype = vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE)
				.toString();
		UFDate date = getDate();

		UFDouble bbhl = null;
		UFDouble fbhl = null;

		try {
			UFDouble rateBoth = Currency.getRate(this.getPk_corp(),
					pk_currtype, date);
			bbhl = rateBoth;
		} catch (BusinessException e) {
			ExceptionHandler.consume(e);
		}

		UFDouble zbhl = null;
		String ybpk = null;

		if (!isJieFang(vo)) {
			bbhl = getRuleVO().getM_creditoBBExchange_rate() == null
					|| ArapCommonTool.isZero(getRuleVO()
							.getM_creditoBBExchange_rate()) ? bbhl
					: getRuleVO().getM_creditoBBExchange_rate();
			fbhl = getRuleVO().getM_creditoFBExchange_rate() == null
					|| ArapCommonTool.isZero(getRuleVO()
							.getM_creditoFBExchange_rate()) ? fbhl
					: getRuleVO().getM_creditoFBExchange_rate();
			zbhl = getRuleVO().getM_dfbz2zjbzHL();
			ybpk = getRuleVO().getM_creditCurr();
		} else {
			bbhl = getRuleVO().getM_debttoBBExchange_rate() == null
					|| ArapCommonTool.isZero(getRuleVO()
							.getM_debttoBBExchange_rate()) ? bbhl : getRuleVO()
					.getM_debttoBBExchange_rate();
			fbhl = getRuleVO().getM_debttoFBExchange_rate() == null
					|| ArapCommonTool.isZero(getRuleVO()
							.getM_debttoFBExchange_rate()) ? fbhl : getRuleVO()
					.getM_debttoFBExchange_rate();
			zbhl = getRuleVO().getM_jfbz2zjbzHL();
			ybpk = getRuleVO().getM_debitCurr();
		}

		return new Object[] { fbhl, bbhl, zbhl, ybpk };
	}

	public void deepclone(VerifyDetailVO detailVO, ArapBusiDataVO verifyvo) {

		// 处理标志
		detailVO.setBusiflag(ArapBillDealVOConsts.UNSAMEVERIFY_FLAG);
		// 红冲标志
		detailVO.setRedflag(Integer.valueOf(0));
		// 处理日期
		detailVO.setBusidate(getRuleVO().getM_Clrq());
		// 处理年度
		detailVO.setBusiyear(getRuleVO().getM_Clnd());
		// 处理期间
		detailVO.setBusiperiod(getRuleVO().getM_Clqj());
		// 处理人
		detailVO.setCreator(getRuleVO().getM_clr());
		// 所属集团
		detailVO.setPk_group(verifyvo
				.getAttributeValue(ArapBusiDataVO.PK_GROUP).toString());
		// 主组织
		detailVO.setPk_org(verifyvo.getAttributeValue(ArapBusiDataVO.PK_ORG)
				.toString());
		// 预占用
		// detailVO.setIsreserve(newIsreserve);
		// 自动处理
		 detailVO.setIsauto(UFBoolean.FALSE);
		 
		 detailVO.setBusidata(verifyvo);

		// 摘要
		detailVO.setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0","02006ver-0158")/*@res "异币种核销"*/);

	}

	private void setDetailVO(VerifyDetailVO detailvo, ArapBusiDataVO jf_vo,
			ArapBusiDataVO df_vo) {

		// 单据编号
		detailvo.setBillno(jf_vo.getAttributeValue(ArapBusiDataVO.BILLNO)
				.toString());
		detailvo.setBillno2(df_vo.getAttributeValue(ArapBusiDataVO.BILLNO)
				.toString());
		// 主表ID
		detailvo.setPk_bill(jf_vo.getPk_bill());
		detailvo.setPk_bill2(df_vo.getPk_bill());
		// 辅表ID
		detailvo.setPk_item(jf_vo.getPk_item());
		detailvo.setPk_item2(df_vo.getPk_item());
		// 协议表ID

		detailvo.setPk_termitem((String)jf_vo.getAttributeValue(
				ArapBusiDataVO.PK_TERMITEM));
		detailvo.setPk_termitem2((String)df_vo.getAttributeValue(
				ArapBusiDataVO.PK_TERMITEM));
		// 业务数据
		detailvo.setPk_busidata(jf_vo.getPk_busidata());
		detailvo.setPk_busidata2(df_vo.getPk_busidata());
//		detailvo.setBusidata(jf_vo);
		//交易类型
		detailvo.setPk_tradetype(jf_vo.getPk_tradetypeid());
		detailvo.setPk_tradetype2(df_vo.getPk_tradetypeid());
		// 单据类型
		detailvo.setPk_billtype(jf_vo.getAttributeValue(
				ArapBusiDataVO.PK_BILLTYPE).toString());
		detailvo.setPk_billtype2(df_vo.getAttributeValue(
				ArapBusiDataVO.PK_BILLTYPE).toString());
		// 单据大类
		detailvo.setBillclass(jf_vo.getAttributeValue(ArapBusiDataVO.BILLCLASS)
				.toString());
		detailvo.setBillclass2(df_vo
				.getAttributeValue(ArapBusiDataVO.BILLCLASS).toString());
		// 集团本币汇率
		detailvo.setGrouprate((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.GROUPRATE));
		// 全局本币汇率
		detailvo.setGlobalrate((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.GLOBALRATE));
		// 物料
		detailvo
				.setMaterial(jf_vo.getAttributeValue(ArapBusiDataVO.MATERIAL) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.MATERIAL)
								.toString());
		// 收支项目
		detailvo.setPk_costsubj(jf_vo
				.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ) == null ? null
				: jf_vo.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ)
						.toString());
		// 客户
		detailvo
				.setCustomer(jf_vo.getAttributeValue(ArapBusiDataVO.CUSTOMER) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.CUSTOMER)
								.toString());
		// 供应商
		detailvo
				.setSupplier(jf_vo.getAttributeValue(ArapBusiDataVO.SUPPLIER) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.SUPPLIER)
								.toString());
		// 订单客户
		detailvo.setOrdercubasdoc(jf_vo
				.getAttributeValue(ArapBusiDataVO.ORDERCUBASDOC) == null ? null
				: jf_vo.getAttributeValue(ArapBusiDataVO.ORDERCUBASDOC)
						.toString());
		// 部门
		detailvo
				.setPk_deptid(jf_vo.getAttributeValue(ArapBusiDataVO.PK_DEPTID) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.PK_DEPTID)
								.toString());
		// 业务员
		detailvo
				.setPk_psndoc(jf_vo.getAttributeValue(ArapBusiDataVO.PK_PSNDOC) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.PK_PSNDOC)
								.toString());
        detailvo.setPk_currtype(jf_vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE).toString());

        //信用到期日
		detailvo.setExpiredate((UFDate)jf_vo.getAttributeValue(ArapBusiDataVO.EXPIREDATE));
		//内控到期日
		detailvo.setInnerctldeferdays((UFDate)jf_vo.getAttributeValue(ArapBusiDataVO.INNERCTLDEFERDAYS));
	}
}