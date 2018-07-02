/*
 * @(#)CmpAgiotageBusiUtils.java 2011-2-25
 * Copyright 2010 UFIDA Software CO.LTD. All rights reserved.
 */
package nc.cmp.agiotage.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bd.accperiod.AccperiodParamAccessor;
import nc.bd.accperiod.AccperiodmonthAccessor;
import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.cmp.pub.exception.ExceptionHandler;
import nc.pubitf.accperiod.AccountCalendar;
import nc.vo.bd.period.AccperiodVO;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.pub.lang.UFDate;

/**
 * ������
 * 
 * @author jiaweib
 * @version 1.0 2011-2-25
 * @since NC6.0
 */
public class CmpAgiotageBusiUtils {

	/**
	 * map to string
	 * 
	 * @param map
	 * @return
	 * @author jiaweib
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getValueListByStringTOStringArrayMap(Map<String, String[]> map) {
		List<String> list = new ArrayList<String>();
		Collection<String[]> values = map.values();
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (obj == null) {
				continue;
			}
			String[] strings = (String[]) obj;
			List<String> tempList = Arrays.asList(strings);
			list.addAll(tempList);
		}
		return list;
	}

	public static Map<String, String> getPk_AccountTOBzbmMapByBzbmToPk_accountHashTable(
			Map<String, String[]> bzbmToPk_accountHashTable) {
		Set<String> bzbmKey = bzbmToPk_accountHashTable.keySet();
		Map<String, String> pk_AccountTOBzbmMap = new HashMap<String, String>();
		for (Iterator iterator = bzbmKey.iterator(); iterator.hasNext();) {
			String bzbm = (String) iterator.next();
			String[] pk_accounts = bzbmToPk_accountHashTable.get(bzbm);
			for (String pk_account : pk_accounts) {
				pk_AccountTOBzbmMap.put(pk_account, bzbm);
			}
		}
		return pk_AccountTOBzbmMap;
	}

	/**
	 * ���������пգ��ر��ǿɱ��βε����
	 * */
	public static boolean isArrayIsNull(Object... objs) {
		if (objs == null || objs.length == 0 || (objs.length == 1 && objs[0] == null)) {
			return true;
		}
		return false;
	}

	/**
	 * ����������ת��Ϊ����ڼ�
	 * */
	public static String converDateToKJQJ(UFDate djrq, String pk_org) {
		if (djrq == null) {
			return null;
		}

		String kjqj = "";

		String AccperiodschemePk = AccperiodParamAccessor.getInstance().getAccperiodschemePkByPk_org(pk_org);

		if (AccperiodschemePk != null) {
			AccperiodmonthVO vo = AccperiodmonthAccessor.getInstance().queryAccperiodmonthVOByDate(AccperiodschemePk,
					djrq);

			if (vo != null) {
				kjqj = vo.getYearmth();
			}
		}

		return kjqj;
	}
	
	/**
	 * �жϵ��������ж��Ƿ�Ϊ�س��˻�������浥
	 * @param djrq
	 * @param pk_org
	 * @return
	 */
	public static Boolean isBackRedBill(UFDate djrq, String pk_org) {
		if (djrq == null) {
			return null;
		}

		String kjqj = "";

		String AccperiodschemePk = AccperiodParamAccessor.getInstance().getAccperiodschemePkByPk_org(pk_org);

		if (AccperiodschemePk != null) {
			AccperiodmonthVO vo = AccperiodmonthAccessor.getInstance().queryAccperiodmonthVOByDate(AccperiodschemePk,
					djrq);

			if (vo != null) {
				return vo.getBegindate().equals(djrq);
			}
		}

		return false;
	}
	
	/**
	 * ����δʵ�ֻ��������Ƶ�ʱ��õ��¸��ڼ�Ļس��˻�������浥���Ƶ�ʱ��
	 * @param djrq
	 * @param pk_org
	 * @return
	 * @throws InvalidAccperiodExcetion 
	 */
	public static String queryRedBackByDate(UFDate unachieveDate, String pk_org) throws InvalidAccperiodExcetion {
		if (unachieveDate == null) {
			return null;
		}
		AccountCalendar acc = null;
		acc = AccountCalendar.getInstanceByPk_org(pk_org);
		acc.setDate(unachieveDate);
		acc.roll(AccountCalendar.MONTH, 1);
		//�õ����� yy-mm-01 00:00:00 	�³���ʱ��
		String redbackDate = acc.getMonthVO().getBegindate().toString();
		return redbackDate;
		
	}
}







