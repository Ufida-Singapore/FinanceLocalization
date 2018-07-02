package nc.vo.arap.gathering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.itf.scmpub.reference.uap.para.SysParaInitQuery;
import nc.vo.arap.pub.res.ParameterList;
import nc.vo.pub.BusinessException;
import nc.vo.pub.para.SysInitVO;

/**
 * 收款单复杂参数的获取 Add by bl 2015-04-24
 */
public class GatherSysParaInitUtil {

	/**
	 * 收款管理单据合并规则
	 * 
	 * @param pk_org
	 * @return
	 * @throws BusinessException
	 */
	public static String[] getAR101(String pk_org) {
		SysInitVO initvo = SysParaInitQuery.queryByParaCode(pk_org,
				ParameterList.AR101.getCode());
		if (null != initvo) {
			String value = initvo.getValue();
			if (null != value && value.length() > 0) {
				return value.split(ParameterList.SPLITKEY);
			}
		}
		return null;
	}
	
	/**
	 * 收款录入单据合并规则
	 * 
	 * @param pk_org
	 * @return
	 * @throws BusinessException
	 */
	public static String[] getAR102(String pk_org) {
		SysInitVO initvo = SysParaInitQuery.queryByParaCode(pk_org,
				ParameterList.AR102.getCode());
		if (null != initvo) {
			String value = initvo.getValue();
			if (null != value && value.length() > 0) {
				return value.split(ParameterList.SPLITKEY);
			}
		}
		return null;
	}

	/**
	 * 付款管理单据合并规则
	 * 
	 * @param pk_org
	 * @return
	 * @throws BusinessException
	 */
	public static String[] getAP201(String pk_org) {
		SysInitVO initvo = SysParaInitQuery.queryByParaCode(pk_org,
				ParameterList.AP201.getCode());
		if (null != initvo) {
			String value = initvo.getValue();
			if (null != value && value.length() > 0) {
				return value.split(ParameterList.SPLITKEY);
			}
		}
		return null;
	}
	
	/**
	 * 付款录入单据合并规则
	 * 
	 * @param pk_org
	 * @return
	 * @throws BusinessException
	 */
	public static String[] getAP202(String pk_org) {
		SysInitVO initvo = SysParaInitQuery.queryByParaCode(pk_org,
				ParameterList.AP202.getCode());
		if (null != initvo) {
			String value = initvo.getValue();
			if (null != value && value.length() > 0) {
				return value.split(ParameterList.SPLITKEY);
			}
		}
		return null;
	}

	/**
	 * 获取参数表里面存的原始字符串
	 * 
	 * @param pk_orgs
	 * @param initCode
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, String> queryBatchParaStringValues(
			String[] pk_orgs, String initCode) {
		if (pk_orgs.length == 0) {
			return null;
		}
		Map<String, String> paraMap = new HashMap<String, String>();
		for (String pk_org : pk_orgs) {
			String code = SysParaInitQuery.getParaString(pk_org, initCode);
			paraMap.put(pk_org, code);
		}
		return paraMap;
	}

	/**
	 * 固定销售组织 批量获取，批量参数值的获取
	 * 
	 * @param pk_org
	 * @param initCodes
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, List<String>> queryBatchParaValues(String pk_org,
			String[] initCodes) {
		if (initCodes.length == 0) {
			return null;
		}
		Map<String, List<String>> paraMap = new HashMap<String, List<String>>();
		Map<String, String> mapCode = SysParaInitQuery.queryBatchParaValues(
				pk_org, initCodes);
		for (Map.Entry<String, String> entry : mapCode.entrySet()) {
			List<String> paraList = new ArrayList<String>();
			String code = entry.getValue();
			if (null != code && !"".equals(code)) {
				String[] paras = code.split(ParameterList.SPLITKEY);
				for (String para : paras) {
					paraList.add(para);
				}
			}
			if (paraList.size() > 0) {
				paraMap.put(entry.getKey(), paraList);
			}
		}
		return paraMap;
	}

	/**
	 * 批量组织 获取相同参数在不同组织下的参数值
	 * 
	 * @param pk_org
	 * @param initCode
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, List<String>> queryBatchParaValues(
			String[] pk_orgs, String initCode) {
		if (pk_orgs.length == 0) {
			return null;
		}
		Map<String, List<String>> paraMap = new HashMap<String, List<String>>();
		for (String pk_org : pk_orgs) {
			List<String> paraList = new ArrayList<String>();
			String code = SysParaInitQuery.getParaString(pk_org, initCode);
			if (null != code && !"".equals(code)) {
				String[] paras = code.split(ParameterList.SPLITKEY);
				for (String para : paras) {
					paraList.add(para);
				}
			}
			if (paraList.size() > 0) {
				paraMap.put(pk_org, paraList);
			}
		}
		return paraMap;
	}

	/**
	 * 批量组织 批量参数 必须一一对应
	 * 
	 * @param pk_orgs
	 * @param initCodes
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, List<String>> queryBatchParaValues(
			String[] pk_orgs, String[] initCodes) {
		if (pk_orgs.length == 0 || initCodes.length == 0) {
			return null;
		}
		Map<String, List<String>> paraMap = new HashMap<String, List<String>>();
		for (int i = 0; i < pk_orgs.length; i++) {
			List<String> paraList = new ArrayList<String>();
			String code = SysParaInitQuery.getParaString(pk_orgs[i],
					initCodes[i]);
			if (null != code && !"".equals(code)) {
				String[] paras = code.split(ParameterList.SPLITKEY);
				for (String para : paras) {
					paraList.add(para);
				}
			}
			if (paraList.size() > 0) {
				paraMap.put(initCodes[i], paraList);
			}
		}
		return paraMap;
	}
}
