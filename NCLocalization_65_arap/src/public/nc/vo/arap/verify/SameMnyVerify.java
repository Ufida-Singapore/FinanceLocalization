/*
 * �������� 2005-8-12
 */
package nc.vo.arap.verify;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import nc.bs.arap.util.ArapVOUtils;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.itf.fi.pub.Currency;
import nc.pubitf.uapbd.DefaultCurrtypeQryUtil;
import nc.vo.arap.agiotage.ArapBusiDataVO;
import nc.vo.arap.global.ArapBillDealVOConsts;
import nc.vo.arap.global.ArapCommonTool;
import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.verifynew.pub.DefaultVerifyRuleVO;
import nc.vo.verifynew.pub.IVerifyMethod;
import nc.vo.verifynew.pub.VerifyCom;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author xuhb
 */
public class SameMnyVerify implements IVerifyMethod, IVerifyBusiness {

	/**
	 *
	 */
	private static final long serialVersionUID = 6560472319130057992L;
	private VerifyCom verifycom;
	private DefaultVerifyRuleVO rulevo;
	private int pph = 100;// �� VerifyCom�����������г�ʼ��
	private static long count = 0;

	private final ArrayList<VerifyDetailVO> retArr = new ArrayList<VerifyDetailVO>();

	/**
	 *
	 */
	public SameMnyVerify() {
		super();
	}

	public SameMnyVerify(VerifyCom verifycom) {
		super();

		this.verifycom = verifycom;
		initPph();
	}

	private void initPph() {
		if (pph == 100) {
			if (verifycom != null) {
				Integer inte_pph = (Integer) verifycom.getM_context()
						.get("PPH");
				if(inte_pph==null){
					pph=Integer.valueOf(0);
				}else{
					try {
						pph = inte_pph.intValue() + 1;
					} catch (Exception e) {
					}
				}
			}
		}

	}

	private void setRuleVO(DefaultVerifyRuleVO vo) {
		rulevo = vo;
	}

	private DefaultVerifyRuleVO getRuleVO() {
		return rulevo;
	}

	/*
	 * ���� Javadoc��
	 *
	 * @see
	 * nc.ui.verify.pub.IVerifyMethod#onVerify(nc.vo.verify.pub.IVerifyVO[],
	 * nc.vo.verify.pub.IVerifyVO, nc.vo.verify.pub.DefaultVerifyRuleVO)
	 */
	public AggverifyVO onVerify(ArapBusiDataVO[] jf_vos,
			ArapBusiDataVO[] df_vos, DefaultVerifyRuleVO rulevo) {
		long start = System.currentTimeMillis();

		getLogs().clear();
		setRuleVO(rulevo);
		if (jf_vos == null || df_vos == null) {
			return null;
		}
		if(!ArrayUtils.isEmpty(jf_vos)){
			for(ArapBusiDataVO vo:jf_vos){
				if(vo.getMoney_bal().compareTo(vo.getAttributeValue(ArapBusiDataVO.SETT_MONEY))==0){
					vo.setIsshlclear(true);
				}else{
					vo.setIsshlclear(false);
				}
			}
		}
		if(!ArrayUtils.isEmpty(df_vos)){
			for(ArapBusiDataVO vo:df_vos){
				if(vo.getMoney_bal().compareTo(vo.getAttributeValue(ArapBusiDataVO.SETT_MONEY))==0){
					vo.setIsshlclear(true);
				}else{
					vo.setIsshlclear(false);
				}
			}
		}

		// ���ؼ����������,�������ݷ���
		int flag = getHxSuanFa(rulevo);
		
		if(!getVerifycom().isAutoVerify()){
			VerifyTool.sortVector(jf_vos, flag);
			VerifyTool.sortVector(df_vos, flag);
		}
		
		Logger.debug("ͬ���ֺ���ǰ�������=" + (System.currentTimeMillis() - start) + "ms");
		long start1 = System.currentTimeMillis();
		String[] jf_keys = rulevo.getM_debtObjKeys();
		if (jf_keys == null) {
			jf_keys = new String[0];
		}
		Vector<String> temp = new Vector<String>();
		for (int i = 0; i < jf_keys.length; i++) {
			temp.add(jf_keys[i]);
		}

		String[] keys = new String[temp.size()];
		temp.copyInto(keys);
		temp.add(ArapBusiDataVO.WLDXPK);
		
		String[] keys2 = new String[temp.size()];
		temp.copyInto(keys2);

		this.contract(jf_vos, df_vos, rulevo, keys, keys2);

		ArrayList<VerifyDetailVO> detailVOList = getLogs();
		VerifyDetailVO[] detailVOArr = new VerifyDetailVO[detailVOList.size()];
		detailVOList.toArray(detailVOArr);
		AggverifyVO aggverifyVO = new AggverifyVO();
		aggverifyVO.setChildrenVO(detailVOArr);
		Logger.debug("ͬ���ֺ�������=" + (System.currentTimeMillis() - start1) + "ms");
		Logger.debug("ͬ���ֺ�������=" + count );
		return aggverifyVO;
	}

	private void contract(ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos,
			DefaultVerifyRuleVO rulevo, String[] keys, String[] keys2) {

		if(getVerifycom().isExactVerify()){
			contractSame(jf_vos, df_vos, keys);// ��ͬ��������ͬ����������ͬ��
		}else{
			contractSame(jf_vos, df_vos, keys2);// ͬ��������ͬ����������ͬ��
			contractSame(jf_vos, df_vos, keys);// ��ͬ��������ͬ����������ͬ��
		}

	}

	private void contractSame(ArapBusiDataVO[] jf_vos, ArapBusiDataVO[] df_vos,
			String[] keys) {

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

			Vector vec = hash2.get(it.next());
			Vector jf = (Vector) vec.get(0);
			Vector df = (Vector) vec.get(1);
			int k = 0;
			for (int i = 0; i < jf.size(); i++) {
				ArapBusiDataVO vo = (ArapBusiDataVO) jf.elementAt(i);
				//update chenth 20161212 ֧�������Ѱ��ۿۺ���
				//update by weiningc 20171012  V633������V65 start
				if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
					vo.setAttributeValue(ArapBusiDataVO.DISCTION, vo.getMoney_de());//�����ۿ۽��
					vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, UFDouble.ZERO_DBL);//���ý�����Ϊ��
				}//update by weiningc 20171012  V633������V65 start
				else if (ArapCommonTool.isZero(new UFDouble(vo.getAttributeValue(
						ArapBusiDataVO.SETT_MONEY).toString()))) {
					continue;
				}
				for (int j = k; j < df.size(); j++) {
					ArapBusiDataVO vo2 = (ArapBusiDataVO) df.elementAt(j);
					//add chenth 20161212 ֧�������Ѱ��ۿۺ���
					//update by weiningc 20171012  V633������V65 start
					if(UFBoolean.TRUE.equals(vo.getIsbankcharges())){
						vo2.setAttributeValue(ArapBusiDataVO.DISCTION, vo.getMoney_de());//�����ۿ۽��
						vo2.setAttributeValue(ArapBusiDataVO.SETT_MONEY, UFDouble.ZERO_DBL);//���ý�����Ϊ��
					} //update by weiningc 20171012  V633������V65 end
					else if (ArapCommonTool.isZero(new UFDouble(vo2
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY)
							.toString()))) {
						continue;
					}
					doBusiness(vo, vo2);
					if (ArapCommonTool.isZero(new UFDouble(vo
							.getAttributeValue(ArapBusiDataVO.SETT_MONEY)
							.toString()))) {
						k = j;
						break;
					}

				}
			}
		}
	}

	/**
	 * @param rulevo
	 * @return 0,�������� 1�������
	 */
	private int getHxSuanFa(DefaultVerifyRuleVO rulevo) {
		if (rulevo == null) {
			return 0;
		}
		Integer inte = rulevo.getM_verifySeq();
		int flag = 0;// Ĭ��Ϊ������
		try {
			flag = inte.intValue();
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
			// Logger.info("####ERROR:��������verifySeq=0�� �������verifySeq������verifySeq="+inte);
		}
		return flag;
	}

	public VerifyCom getVerifycom() {
		return verifycom;
	}

	public void setVerifycom(VerifyCom verifycom) {
		this.verifycom = verifycom;
	}

	public void doBusiness(ArapBusiDataVO vo, ArapBusiDataVO vo2) {
		if (vo == null || vo2 == null) {
			return;
		}

		UFDouble jsybje = new UFDouble(vo.getAttributeValue(
				ArapBusiDataVO.SETT_MONEY).toString());
		UFDouble jsybje2 = new UFDouble(vo2.getAttributeValue(
				ArapBusiDataVO.SETT_MONEY).toString());

		if (!UFDoubleTool.isTonghao(jsybje, jsybje2)) {
			return;
		}

		ArapBusiDataVO jf_vo = null;
		ArapBusiDataVO df_vo = null;

		if (isJiefang(vo)) {
			jf_vo = vo;// ����Ϊ�跽
			df_vo = vo2;// �Է�Ϊ����
		} else {
			jf_vo = vo2;
			df_vo = vo;
		}

		count++;
		verify(jf_vo, df_vo);

	}
	/**
	 * @param jf_vo
	 * @param df_vo
	 */
	private void verify(ArapBusiDataVO jf_vo, ArapBusiDataVO df_vo) {
		if (jf_vo == null || df_vo == null) {
			return;
		}
		// ��VerifyCom�еĺ������������ʼ��ƥ���
		initPph();
		UFDouble zero = new UFDouble(0);
        Boolean isOccuption = getRuleVO().getIsOccuption();
        
        //add chenth 20161212 ֧�������Ѱ��ۿۺ���
        //update by weiningc 20171012  V633������V65 start
        if(UFBoolean.TRUE.equals(jf_vo.getIsbankcharges())){
        	Integer inte_pph = Integer.valueOf(pph++);
        	UFDouble[] verifyRate = VerifyTool.getVerifyRate(jf_vo, df_vo);
        	createZKLogvo(jf_vo,df_vo,verifyRate, ++inte_pph);
        	return;
        }
        //add end
        //update by weiningc 20171012  V633������V65 end


		UFDouble jf_jsybje = (UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY);
		UFDouble jf_zk = (UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);// �ۿ�
		if (jf_zk == null) {
			jf_zk = zero;
		}

		UFDouble df_jsybje = (UFDouble) df_vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY);
		UFDouble df_zk = (UFDouble) df_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);
		if (df_zk == null) {
			df_zk = zero;
		}

		boolean isJfClear = false;
		boolean isDfClear = false;

		// ����ԭ�ҽ��
		UFDouble jf_clybje = null;
		UFDouble df_clybje = null;

		if (UFDoubleTool.isXiangdeng(jf_jsybje, df_jsybje)) {
			jf_clybje = jf_jsybje;
			df_clybje = df_jsybje;

			isDfClear = true;
			isJfClear = true;

		}
		if (UFDoubleTool.isAbsDayu(jf_jsybje, df_jsybje)) {
			jf_clybje = df_jsybje;
			df_clybje = df_jsybje;

			isDfClear = true;
		} else {
			jf_clybje = jf_jsybje;
			df_clybje = jf_jsybje;

			isJfClear = true;
		}
		jf_vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, ((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY)).sub(jf_clybje));
		df_vo.setAttributeValue(ArapBusiDataVO.SETT_MONEY, ((UFDouble) df_vo
				.getAttributeValue(ArapBusiDataVO.SETT_MONEY)).sub(df_clybje));

		// ����VerifyLogVO
		// ���ɺ�����ϸ��
		VerifyDetailVO detaildeVO = new VerifyDetailVO();
		detaildeVO.setIsreserve(getVerifycom().getReserveFlag());
		deepclone(detaildeVO, jf_vo);
		detaildeVO.setSrc_org(df_vo.getPk_org());
		detaildeVO.setStatus(VOStatus.NEW); // �������ݱ�������ΪNew
		detaildeVO.setMoney_de(jf_clybje);
		//��Ԥռ�� ����
		if(isOccuption!=null&&isOccuption.booleanValue()){
			detaildeVO.setOccupationmny(jf_clybje);
		}
		detaildeVO.setJd_flag(1);

		UFDouble[] verifyRate = VerifyTool.getVerifyRate(jf_vo, df_vo);

		// �����ҽ��ʹ����ҽ��,�跽�ʹ�����ͬ�ĸ��һ��ʺ���ͬ�ı��һ���
		// ��ʱע�͵�
//		VerifyTool.bb_fb_jisuan_new(jf_vo, verifyRate, detaildeVO, getDate(),
//				getPk_corp());

		// �����־��clbz����-2���ۿۡ�-1������ֺ�����0��ͬ���ֺ�����1��������桢
		// 2����Ʊ�Գ塢Ʊ������; 3�����˷���; 4�������ջ�; 5������ת����6������ת��

		// ����
		UFDouble jf_shl = null;
		UFDouble df_shl = null;
		UFDouble jf_dj = null;
		UFDouble df_dj = null;

//		detaildeVO.setM_jfverifyVO(jf_vo);
//		detaildeVO.setM_dfverifyVO(df_vo);

		jf_dj = (UFDouble) jf_vo.getAttributeValue(ArapBusiDataVO.PRICE);
		df_dj = (UFDouble) df_vo.getAttributeValue(ArapBusiDataVO.PRICE);

		if (isJfClear && jf_vo.isIsshlclear()) {
			detaildeVO.setQuantity_de(jf_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(jf_vo.getMaterial()), UFDouble.ROUND_HALF_UP));
		} else if (jf_dj != null && !UFDoubleTool.isZero(jf_dj)) {

			if (ArapCommonTool.isZero(jf_clybje) && jf_vo.isIsshlclear()) {
				jf_shl = jf_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(jf_vo.getMaterial()), UFDouble.ROUND_HALF_UP);
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						new UFDouble(0));
			} else {
				jf_shl = jf_clybje.div(jf_dj,ArapVOUtils.getDecimalFromSource(jf_vo.getMaterial()));
				jf_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						jf_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(jf_vo.getMaterial()), UFDouble.ROUND_HALF_UP).sub(jf_shl));
			}
			detaildeVO.setQuantity_de(jf_shl);
		}

		Integer inte_pph = Integer.valueOf(pph++);

		// ƥ��ŷŵ�������������
		verifycom.getM_context().put("PPH", inte_pph);

		// �跽------------------------------------------
		// �跽������
		detaildeVO.setMoney_cr(new UFDouble(0));
		setDetailVO(detaildeVO, jf_vo, df_vo);

		// ��������
		UFDouble localrate_jf = verifyRate.length==6?verifyRate[4]:verifyRate[1];
		detaildeVO.setRate(localrate_jf);
		detaildeVO.setGrouprate(verifyRate[2]);
		detaildeVO.setGlobalrate(verifyRate[3]);
		// �������
		detaildeVO.setRowno(inte_pph);

		UFDate busidate = detaildeVO.getBusidate();

		try {
			if(detaildeVO.getMoney_de() ==null ||detaildeVO.getMoney_de().compareTo(UFDouble.ZERO_DBL)==0){
				detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
			}else{
				detaildeVO.setLocal_money_de(Currency.getAmountByOpp(jf_vo.getPk_org(),
						jf_vo.getPk_currtype(),
						DefaultCurrtypeQryUtil.getInstance()
								.getDefaultCurrtypeByOrgID(jf_vo.getPk_org())
								.getPk_currtype(), detaildeVO.getMoney_de(),localrate_jf,
						busidate));

			}
			detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);
		} catch (Exception e) {
			ExceptionHandler.handleRuntimeException(e);
		}

		getLogs().add(detaildeVO);

		// ��-----------------------------------------------
		VerifyDetailVO detailcrVO = new VerifyDetailVO();
		detailcrVO.setIsreserve(getVerifycom().getReserveFlag());
		detailcrVO.setStatus(VOStatus.NEW); // �������ݱ�������ΪNew
		detailcrVO.setJd_flag(-1);
		deepclone(detailcrVO, df_vo);
		setDetailVO(detailcrVO, df_vo, jf_vo);
		// ����������
		detailcrVO.setMoney_cr(df_clybje);
		//��Ԥռ�� ����
		if(isOccuption!=null&&isOccuption.booleanValue()){
			detailcrVO.setOccupationmny(df_clybje);
		}
		detailcrVO.setSrc_org(jf_vo.getPk_org());
		// �跽������Ϊ0
		detailcrVO.setMoney_de(UFDouble.ZERO_DBL);
		detailcrVO.setLocal_money_de(UFDouble.ZERO_DBL);
		// ��������
		UFDouble localrate_df = verifyRate.length==6?verifyRate[5]:verifyRate[1];
		detailcrVO.setRate(localrate_df);
		
		//add chenth 20170212 �ϲ�����
		//ʢ���ź�������ȡfk����  
		detailcrVO.setGrouprate(verifyRate[2]);
		//add end
		// �������
		detailcrVO.setRowno(inte_pph);
		try {
			if(detailcrVO.getMoney_cr() ==null ||detailcrVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL)==0){
				detailcrVO.setLocal_money_cr(UFDouble.ZERO_DBL);
			}else{
				detailcrVO.setLocal_money_cr(Currency.getAmountByOpp(df_vo.getPk_org(),
						df_vo.getPk_currtype(),
						DefaultCurrtypeQryUtil.getInstance()
								.getDefaultCurrtypeByOrgID(df_vo.getPk_org())
								.getPk_currtype(), detailcrVO.getMoney_cr(),localrate_df,
						busidate));
			}
			detailcrVO.setLocal_money_de(UFDouble.ZERO_DBL);
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}

		// ����
		if (isDfClear && df_vo.isIsshlclear()) {
			detailcrVO.setQuantity_cr(df_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(df_vo.getMaterial()), UFDouble.ROUND_HALF_UP));
		} else if (df_dj != null && !UFDoubleTool.isZero(df_dj)) {

			if (ArapCommonTool.isZero(df_clybje)&&df_vo.isIsshlclear()) {
				df_shl = df_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(df_vo.getMaterial()), UFDouble.ROUND_HALF_UP);
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						new UFDouble(0));
			} else {
				df_shl = df_clybje.div(df_dj,ArapVOUtils.getDecimalFromSource(df_vo.getMaterial()));
				df_vo.setAttributeValue(ArapBusiDataVO.QUANTITY_BAL,
						(df_vo.getQuantity_bal().setScale(ArapVOUtils.getDecimalFromSource(df_vo.getMaterial()), UFDouble.ROUND_HALF_UP)).sub(df_shl));
			}

			detailcrVO.setQuantity_cr(df_shl);
		}

		getLogs().add(detailcrVO);

		// �ۿ���
		 createZKLogvo(jf_vo,df_vo,verifyRate, ++inte_pph);
		 createZKLogvo(df_vo,jf_vo,verifyRate, ++inte_pph);
	}

	/**
	 * @param jf_vo
	 * @param jf_zk
	 * @param inte_pph
	 */
	private void createZKLogvo(ArapBusiDataVO jf_vo,ArapBusiDataVO df_vo, UFDouble[] verifyRate,
			Integer inte_pph) {

		if (jf_vo == null) {
			return;
		}
		UFDouble jf_zk = (UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.DISCTION);// �ۿ�
		// �ۿ�
		if (jf_zk != null && !UFDoubleTool.isZero(jf_zk)) {
			VerifyDetailVO detaildeVO = new VerifyDetailVO();
			detaildeVO.setIsreserve(getVerifycom().getReserveFlag());
			deepclone(detaildeVO, jf_vo);
			//�ۿۺ�������
			detaildeVO.setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0","02006ver-0322")/*@res "�ۿۺ���"*/);
			detaildeVO.setStatus(VOStatus.NEW); // �������ݱ�������ΪNew



			//������־
			detaildeVO.setBusiflag(ArapBillDealVOConsts.DISCTIONVERIFY_FLAG);
			setDetailVO(detaildeVO, jf_vo, df_vo);
			// ��������
			UFDouble localrate_jf = verifyRate.length==6?verifyRate[4]:verifyRate[1];
			detaildeVO.setRate(localrate_jf);
			detaildeVO.setGrouprate(verifyRate[2]);
			detaildeVO.setGlobalrate(verifyRate[3]);
			// �������
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
										.getPk_currtype(), detaildeVO.getMoney_de(),localrate_jf,
								busidate));

					}
					detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);
//					UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//							detaildeVO.getMoney_de(), detaildeVO.getLocal_money_de()==null?UFDouble.ZERO_DBL:detaildeVO.getLocal_money_de(),
//							jf_vo.getPk_currtype(), busidate, jf_vo.getPk_org()
//									.toString(),null,null);
//					// �跽ȫ�ֱ�λ��
//					detaildeVO.setGlobal_money_de(group_global[1]);
//					// ����ȫ�ֱ�λ��
//					detaildeVO.setGlobal_money_cr(UFDouble.ZERO_DBL);
//					// �跽���ű�λ��
//					detaildeVO.setGroup_money_de(group_global[0]);
//					// �������ű�λ��
//					detaildeVO.setGroup_money_cr(UFDouble.ZERO_DBL);
				} catch (Exception e) {
					ExceptionHandler.handleRuntimeException(e);
				}
			} else if (jf_vo.getDirection() == -1) {
				detaildeVO.setSrc_org(jf_vo.getPk_org());
				detaildeVO.setMoney_cr(jf_zk);
				detaildeVO.setMoney_de(UFDouble.ZERO_DBL);
				
				UFDouble localrate_df = verifyRate.length==6?verifyRate[5]:verifyRate[1];
				
				try {
					if (detaildeVO.getMoney_cr() == null || detaildeVO.getMoney_cr().compareTo(UFDouble.ZERO_DBL) == 0) {
						detaildeVO.setLocal_money_cr(UFDouble.ZERO_DBL);
					} else {
						detaildeVO.setLocal_money_cr(Currency.getAmountByOpp(jf_vo.getPk_org(),
								jf_vo.getPk_currtype(), DefaultCurrtypeQryUtil.getInstance()
										.getDefaultCurrtypeByOrgID(jf_vo.getPk_org())
										.getPk_currtype(), detaildeVO.getMoney_cr(), localrate_df,
								busidate));

					}
					detaildeVO.setLocal_money_de(UFDouble.ZERO_DBL);
//					UFDouble[] group_global = Currency.computeGroupGlobalAmount(
//							detaildeVO.getMoney_cr(), detaildeVO.getLocal_money_cr()==null?UFDouble.ZERO_DBL:detaildeVO.getLocal_money_cr(),
//							jf_vo.getPk_currtype(), busidate, jf_vo.getPk_org()
//									.toString(),null,null);
//					// �跽ȫ�ֱ�λ��
//					detaildeVO.setGlobal_money_cr(group_global[1]);
//					// ����ȫ�ֱ�λ��
//					detaildeVO.setGlobal_money_de(UFDouble.ZERO_DBL);
//					// �跽���ű�λ��
//					detaildeVO.setGroup_money_cr(group_global[0]);
//					// �������ű�λ��
//					detaildeVO.setGroup_money_de(UFDouble.ZERO_DBL);
				} catch (Exception e) {
					ExceptionHandler.handleRuntimeException(e);
				}
			}
			//add chenth 20161212 ֧�������Ѱ��ۿۺ���
			//add by weiningc 20171012  V633������V65 start
			if(UFBoolean.TRUE.equals(jf_vo.getIsbankcharges())){
				detaildeVO.setOccupationmny(jf_zk);
			}
			//add end
			//add by weiningc 20171012  V633������V65 end
			// detaildeVO.setM_jfverifyVO(jf_vo);
			// detaildeVO.setM_dfverifyVO(df_vo);
			getLogs().add(detaildeVO);
			jf_vo.setAttributeValue(ArapBusiDataVO.DISCTION, null);
		}
	}

	private ArrayList<VerifyDetailVO> getLogs() {
		return retArr;
	}

	private boolean isJiefang(ArapBusiDataVO vo) {
		if (vo == null) {
			return false;
		}
		Integer fx = Integer.valueOf(vo.getAttributeValue(ArapBusiDataVO.DIRECTION)
				.toString());
		try {
			int int_fx = fx.intValue();
			return int_fx == 1;
		} catch (Exception e) {

		}
		return false;
	}

	public void deepclone(VerifyDetailVO detailVO, ArapBusiDataVO verifyvo) {

		// ��������
		// detailVO.setRate(verifyRate[1]);
		// �����־
		detailVO.setBusiflag(ArapBillDealVOConsts.SAMEVERIFY_FLAG);
		// ����־
		detailVO.setRedflag(Integer.valueOf(0));
		// ��������
		detailVO.setBusidate(getRuleVO().getM_Clrq());
		// �������
		detailVO.setBusiyear(getRuleVO().getM_Clnd());
		// �����ڼ�
		detailVO.setBusiperiod(getRuleVO().getM_Clqj());
		// ������
		detailVO.setCreator(getRuleVO().getM_clr());
		// ��������
		detailVO.setPk_group(verifyvo
				.getAttributeValue(ArapBusiDataVO.PK_GROUP).toString());
		// ����֯
		detailVO.setPk_org(verifyvo.getAttributeValue(ArapBusiDataVO.PK_ORG)
				.toString());
		//����������֯
		detailVO.setSrc_org(verifyvo.getSrc_org());
		// Ԥռ��
		// detailVO.setIsreserve(newIsreserve);
		// �Զ�����
		 detailVO.setIsauto(UFBoolean.FALSE);
		 
		 detailVO.setBusidata(verifyvo);

		// ժҪ
		detailVO.setScomment(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2006ver_0","02006ver-0157")/*@res "ͬ���ֺ���"*/);


	}

	private void setDetailVO(VerifyDetailVO detailvo, ArapBusiDataVO jf_vo,
			ArapBusiDataVO df_vo) {

		// ���ݱ��
		detailvo.setBillno((String)jf_vo.getAttributeValue(ArapBusiDataVO.BILLNO));
		detailvo.setBillno2((String)df_vo.getAttributeValue(ArapBusiDataVO.BILLNO));
		// ����ID
		detailvo.setPk_bill(jf_vo.getPk_bill());
		detailvo.setPk_bill2(df_vo.getPk_bill());
		// ����ID
		detailvo.setPk_item(jf_vo.getPk_item());
		detailvo.setPk_item2(df_vo.getPk_item());
		// Э���ID
		detailvo.setPk_termitem(jf_vo.getPk_termitem());
		detailvo.setPk_termitem2(df_vo.getPk_termitem());
		// ҵ������
		detailvo.setPk_busidata(jf_vo.getPk_busidata());
		detailvo.setPk_busidata2(df_vo.getPk_busidata());
//		detailvo.setBusidata(jf_vo);
		//��������
		detailvo.setPk_tradetype(jf_vo.getPk_tradetypeid());
		detailvo.setPk_tradetype2(df_vo.getPk_tradetypeid());
		// ��������
		detailvo.setPk_billtype(jf_vo.getAttributeValue(
				ArapBusiDataVO.PK_BILLTYPE).toString());
		detailvo.setPk_billtype2(df_vo.getAttributeValue(
				ArapBusiDataVO.PK_BILLTYPE).toString());
		// ���ݴ���
		detailvo.setBillclass(jf_vo.getBillclass());
		detailvo.setBillclass2(df_vo.getBillclass());
		// ���ű��һ���
		detailvo.setGrouprate((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.GROUPRATE));
		// ȫ�ֱ��һ���
		detailvo.setGlobalrate((UFDouble) jf_vo
				.getAttributeValue(ArapBusiDataVO.GLOBALRATE));
		// ����
		detailvo
				.setMaterial(jf_vo.getAttributeValue(ArapBusiDataVO.MATERIAL) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.MATERIAL)
								.toString());
		// ��֧��Ŀ
		detailvo.setPk_costsubj(jf_vo
				.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ) == null ? null
				: jf_vo.getAttributeValue(ArapBusiDataVO.PK_COSTSUBJ)
						.toString());
		// �ͻ�
		detailvo
				.setCustomer(jf_vo.getAttributeValue(ArapBusiDataVO.CUSTOMER) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.CUSTOMER)
								.toString());
		// ��Ӧ��
		detailvo
				.setSupplier(jf_vo.getAttributeValue(ArapBusiDataVO.SUPPLIER) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.SUPPLIER)
								.toString());
		// �����ͻ�
		detailvo.setOrdercubasdoc(jf_vo
				.getAttributeValue(ArapBusiDataVO.ORDERCUBASDOC) == null ? null
				: jf_vo.getAttributeValue(ArapBusiDataVO.ORDERCUBASDOC)
						.toString());
		// ����
		detailvo
				.setPk_deptid(jf_vo.getAttributeValue(ArapBusiDataVO.PK_DEPTID) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.PK_DEPTID)
								.toString());
		// ҵ��Ա
		detailvo
				.setPk_psndoc(jf_vo.getAttributeValue(ArapBusiDataVO.PK_PSNDOC) == null ? null
						: jf_vo.getAttributeValue(ArapBusiDataVO.PK_PSNDOC)
								.toString());
		//����
		detailvo.setPk_currtype(jf_vo.getAttributeValue(ArapBusiDataVO.PK_CURRTYPE).toString());

		//���õ�����
		detailvo.setExpiredate((UFDate)jf_vo.getAttributeValue(ArapBusiDataVO.EXPIREDATE));
		//�ڿص�����
		detailvo.setInnerctldeferdays((UFDate)jf_vo.getAttributeValue(ArapBusiDataVO.INNERCTLDEFERDAYS));
	}
}