package nc.ui.glcom.displayformattool;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.bs.logging.Logger;
import nc.gl.utils.GLMultiLangUtil;
import nc.itf.glcom.para.GLParaAccessor;
import nc.itf.glcom.para.GLParaValueConst;
import nc.itf.uap.busibean.SysinitAccessor;
import nc.pubitf.bd.accessor.GeneralAccessorFactory;
import nc.pubitf.bd.accessor.IGeneralAccessor;
import nc.ui.gl.datacache.AccountCache;
import nc.ui.gl.datacache.BDInfoDataCache;
import nc.ui.gl.datacache.GLParaDataCacheUseUap;
import nc.ui.gl.datacache.GroupAccountCache;
import nc.vo.bd.accessor.IBDData;
import nc.vo.bd.account.AccAssVO;
import nc.vo.bd.account.AccountVO;
import nc.vo.fipub.utils.AccountCalendarUtils;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.AccountUtilGL;
import nc.vo.gateway60.para.ParaMacro;
import nc.vo.gateway60.pub.GlBdinfoVO;
import nc.vo.gateway60.pub.GlBusinessException;
import nc.vo.glcom.ass.AssVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * 类型说明：根据显示参数得到科目和辅助项显示内容 创建日期：(2003-1-13 14:57:05)
 *
 * @author：魏小炯
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ShowContentCenter {

	private static java.util.Hashtable assStyleCache = new java.util.Hashtable();

	private static java.util.Hashtable subjStyleCache = new java.util.Hashtable();

	private static java.util.Hashtable nullAssStyleCache = new java.util.Hashtable();
	
	private static String oppName = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("2002gl55","UC000-0001640")/*@res "对方科目"*/;
	/**
	 * AccsubjShowCenter 构造子注解。
	 */
	public ShowContentCenter() {
		super();
	}

	/**
	 * 创建日期：(2003-1-13 14:58:05)
	 *
	 * @return java.lang.String
	 * @param pk_accsubj
	 *            java.lang.String
	 */
	public static String getShowAccsubj(String pk_glorgbook, String pk_accsubj) {
		nc.vo.bd.account.AccountVO vo = null;
		String showName = null;
		if (pk_glorgbook == null || pk_accsubj == null)
			return "";
		vo = AccountCache.getInstance().getAccountVOByPK(pk_glorgbook, pk_accsubj);
		showName = getName(vo);
		return showName;
	}
	
	public static String getShowAccsubjGroup(String pk_setofbook, String pk_accsubj) {
		nc.vo.bd.account.AccountVO vo = null;
		String showName = null;
		if (pk_setofbook == null || pk_accsubj == null)
			return "";
		vo = GroupAccountCache.getAccount(pk_setofbook, pk_accsubj);
		showName = getName(vo);
		return showName;
	}
	
	/**
	 * 加载传入核算账簿的科目表头显示。 主要是为了平衡连接数，切换核算账簿就缓存上。 看情况使用。
	 * @param pk_accountingbook
	 * @author zhaoshya
	 * 
	 */

	public static void loadSubjStyleCache(String pk_accountingbook){
		if (pk_accountingbook == null)
			return;
		try {
			Integer style = (Integer) subjStyleCache.get(pk_accountingbook);
			if (style == null) {
				String strTemp = SysinitAccessor.getInstance().getParaString(pk_accountingbook, "GL011");
				if (strTemp.equals(GLParaValueConst.GL011_SELF_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CURRENTNAME);
				else if (strTemp.equals(GLParaValueConst.GL011_FIRST_SELF_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_FIRSTCURRENTNAME);
				else if (strTemp.equals(GLParaValueConst.GL011_LEVEL_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_ALLNAME);
				else if (strTemp.equals(GLParaValueConst.GL011_CODE_LEVEL_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODEALLNAME);
				else if (strTemp.equals(GLParaValueConst.GL011_CODE_SELF_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODECURRENTNAME);
				else if (strTemp.equals(GLParaValueConst.GL011_CODE_FIRST_SELF_NAME))
					style = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODEFIRSTCURENTNAME);
				subjStyleCache.put(pk_accountingbook, style);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			nc.bs.logging.Logger.debug("没有取到表头科目显示模式");
		}
	}
	
	public static Integer getAccBookSubjDispNameStyle(String strPk_orgbook) throws java.rmi.RemoteException {
		try {
			Integer intTemp = null;
			String strTemp = SysinitAccessor.getInstance().getParaString( strPk_orgbook,"GL011");
			if (strTemp.equals(GLParaValueConst.GL011_SELF_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CURRENTNAME);
			else if (strTemp.equals(GLParaValueConst.GL011_FIRST_SELF_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_FIRSTCURRENTNAME);
			else if (strTemp.equals(GLParaValueConst.GL011_LEVEL_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_ALLNAME);
			else if (strTemp.equals(GLParaValueConst.GL011_CODE_LEVEL_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODEALLNAME);
			else if (strTemp.equals(GLParaValueConst.GL011_CODE_SELF_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODECURRENTNAME);
			else if (strTemp.equals(GLParaValueConst.GL011_CODE_FIRST_SELF_NAME))
				intTemp = Integer.valueOf(ParaMacro.ACCBOOK_SUBJDISPNAME_CODEFIRSTCURENTNAME);
			return intTemp;
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * 创建日期：(2003-1-13 14:58:05)
	 *
	 * @return java.lang.String
	 * @param pk_accsubj
	 *            java.lang.String
	 */
	public static String getShowAccsubj(String pk_glorgbook, String pk_accsubj, String stddate) {
		if (stddate == null || stddate.equals(""))
			return getShowAccsubj(pk_glorgbook, pk_accsubj);
		nc.vo.bd.account.AccountVO vo = null;
		Integer style = null;
		String showName = null;
		if (pk_glorgbook == null || pk_accsubj == null)
			return "";
		try {
			vo = AccountCache.getInstance().getAccountVOByPK(pk_glorgbook, pk_accsubj, stddate);
			style = (Integer) subjStyleCache.get(pk_glorgbook);
			if (style == null) {
				style = getAccBookSubjDispNameStyle(pk_glorgbook);
				subjStyleCache.put(pk_glorgbook, style);
			} else if (style.intValue() == -1) {
				nc.bs.logging.Logger.debug("没有取到表头科目显示模式");
				return "";
			}

		} catch (Exception e) {
			subjStyleCache.put(pk_glorgbook, Integer.valueOf(-1));
			style = (Integer) subjStyleCache.get(pk_glorgbook);
			Logger.error(e.getMessage(), e);
			nc.bs.logging.Logger.debug("没有取到表头科目显示模式");
		}

		try {
			if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_CURRENTNAME) {
				/** 本级名称 */
				showName = getName(vo);
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_FIRSTCURRENTNAME) {
				/** 一级本级名称 */
				int[] scheme = AccountCache.getInstance().getAccountLevelScheme(pk_glorgbook, stddate);
				String subjCode = vo.getCode();

				if (subjCode.length() > scheme[0]) {
					String firstLevelSubjcode = subjCode.substring(0, scheme[0]);
					nc.vo.bd.account.AccountVO[] subjVOs = AccountUtilGL.queryAccountVosByCodes(pk_glorgbook, new String[] { firstLevelSubjcode }, stddate);
					showName = getName(subjVOs[0]) + "/" + getName(vo);
				} else {
					showName = getName(vo);
				}
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_ALLNAME) {
				/** 逐级名称 */
				int[] scheme = AccountCache.getInstance().getAccountLevelScheme(pk_glorgbook, stddate);
				String subjCode = vo.getCode();
				String subjName = getName(vo);
				/** 若为一级科目，则不需要查询上级科目信息 */
				if (subjCode.length() > scheme[0]) {
					java.util.Vector vecSubjCode = new java.util.Vector();
					int subjLen = 0;
					for (int kk = 0; kk < scheme.length; kk++) {
						subjLen += scheme[kk];
						if (subjCode.length() < subjLen)
							break;
						vecSubjCode.addElement(subjCode.substring(0, subjLen));

					}
					String[] subjCodes = new String[vecSubjCode.size()];
					vecSubjCode.copyInto(subjCodes);

					nc.vo.bd.account.AccountVO[] subjVOs = AccountUtilGL.queryAccountVosByCodes(pk_glorgbook, subjCodes, stddate);
					subjName = "";
					for (int k = 0; k < subjVOs.length; k++) {
						subjName += getName(subjVOs[k]) + "/";
					}
					subjName = subjName.substring(0, subjName.length() - 1);
				}
				showName = subjName;
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_CODEALLNAME) {
				/** 编码+逐级名称 */
				int[] scheme = AccountCache.getInstance().getAccountLevelScheme(pk_glorgbook, stddate);
				String subjCode = vo.getCode();
				String subjName = getName(vo);
				/** 若为一级科目，则不需要查询上级科目信息 */
				if (subjCode.length() > scheme[0]) {
					java.util.Vector vecSubjCode = new java.util.Vector();
					int subjLen = 0;
					for (int kk = 0; kk < scheme.length; kk++) {
						subjLen += scheme[kk];
						if (subjCode.length() < subjLen)
							break;
						vecSubjCode.addElement(subjCode.substring(0, subjLen));

					}
					String[] subjCodes = new String[vecSubjCode.size()];
					vecSubjCode.copyInto(subjCodes);

					nc.vo.bd.account.AccountVO[] subjVOs = AccountUtilGL.queryAccountVosByCodes(pk_glorgbook, subjCodes, stddate);
					subjName = "";
					for (int k = 0; k < subjVOs.length; k++) {
						subjName += getName(subjVOs[k]) + "/";
					}
					subjName = subjName.substring(0, subjName.length() - 1);
				}
				subjName = subjCode + "/" + subjName;
				showName = subjName;
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_CODECURRENTNAME) {
				/** 编码+本级名称 */
				showName = vo.getCode() + "/" + getName(vo);
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ACCBOOK_SUBJDISPNAME_CODEFIRSTCURENTNAME) {
				/** 编码+一级本级名称 */
				int[] scheme = AccountCache.getInstance().getAccountLevelScheme(pk_glorgbook, stddate);
				String subjCode = vo.getCode();

				showName = subjCode + "/" + getName(vo);
				if (subjCode.length() > scheme[0]) {
					String firstLevelSubjcode = subjCode.substring(0, scheme[0]);
					nc.vo.bd.account.AccountVO[] subjVOs = AccountUtilGL.queryAccountVosByCodes(pk_glorgbook, new String[] { firstLevelSubjcode }, stddate);
					showName = vo.getCode() + "/" + getName(subjVOs[0]) + "/" + getName(vo);
				}
			}
		} catch (Exception e) {
		}

		return showName;

	}

	/**
	 * 创建日期：(2003-1-13 14:58:05)
	 *
	 * @return java.lang.String
	 * @param pk_accsubj
	 *            java.lang.String
	 */
	public static String getShowAccsubjGL000(String pk_glorgbook, String pk_accsubj) {
		String showName = "";
		if (pk_glorgbook == null || pk_accsubj == null)
			return ""; 
		try {
			AccountVO accountVO = AccountCache.getInstance().getAccountVOByPK(pk_glorgbook,pk_accsubj);
			if(accountVO!=null){
				showName = accountVO.getDispname();
			}
//			showName = AccountCache.getInstance().getAccountVOByPK(pk_glorgbook,pk_accsubj).getName();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(e.getMessage());
		}

		return showName;
	}
	
	
	public static  nc.vo.glcom.ass.AssVO[] getHashSetAssVos(nc.vo.glcom.ass.AssVO[] assvos){
		nc.vo.glcom.ass.AssVO[] result=null;
		ArrayList<AssVO> list=new ArrayList<AssVO>();
		HashSet<String> typeSet=new HashSet<String>();
		if(null!=assvos && assvos.length>0){
			for(AssVO vo:assvos){
				if(typeSet.contains(vo.getPk_Checktype().trim())){
				}else{
					typeSet.add(vo.getPk_Checktype().trim());
					list.add(vo);
				}
			}
		}
		if(list.size()>0){
			result=new AssVO[list.size()];
			list.toArray(result);
		}
		
		
		return result;
	}
	
	
	
	public static String getAssShowName(nc.vo.glcom.ass.AssVO[] assvos){
		
		StringBuilder content=new StringBuilder();
		if(null==assvos || assvos.length==0){
			return "";
		}else{
			assvos= getHashSetAssVos(assvos);
			for(nc.vo.glcom.ass.AssVO vo:assvos){
				if(vo.getChecktypename()!=null){
					content.append("[").append(vo.getChecktypename()).append(":").append(vo.getCheckvaluename()==null?"~":vo.getCheckvaluename()).append("]");/*-=notranslate=-*/
				}
			}
		}
		return content.toString();
		
	}

	/**
	 * 创建日期：(2003-1-16 14:59:51)
	 *
	 * @return java.lang.String
	 * @param pk_glorgbook
	 *            String,vos nc.vo.glcom.ass.AssVO[]
	 */
	public static String getShowAss(String pk_glorgbook, nc.vo.glcom.ass.AssVO[] vos) {
		Integer style = null;
		UFBoolean showNullAss = null;
		String showName = "";
//		GlMulitTool glm = new GlMulitTool();
		if (pk_glorgbook == null || vos == null)
			return "";
		// 得到辅助核算显示模式
		try {
			style = (Integer) assStyleCache.get(pk_glorgbook);
			if (style == null) {
				style = GLParaDataCacheUseUap.getAssDispNameStyle(pk_glorgbook)/*GLPubProxy.getRemoteGlPara().getAssDispNameStyle(pk_glorgbook)*/;
				assStyleCache.put(pk_glorgbook, style);
			} else if (style.intValue() == -1) {
				nc.bs.logging.Logger.error("没有取到辅助核算显示模式");
				assStyleCache.put(pk_glorgbook, 0);
//				return "";
			}
		} catch (Exception e) {
			assStyleCache.put(pk_glorgbook, Integer.valueOf(-1));
			Logger.error(e.getMessage(), e);
			nc.bs.logging.Logger.debug("没有取到辅助核算显示模式");

		}

		// 得到辅助核算为空时显示模式
		try {
			showNullAss = (UFBoolean) nullAssStyleCache.get(pk_glorgbook);
			if (showNullAss == null) {
				showNullAss = GLParaAccessor.isDisplayNullFree(pk_glorgbook);
				nullAssStyleCache.put(pk_glorgbook, showNullAss);
			}
		} catch (Exception e) {
			nullAssStyleCache.put(pk_glorgbook, nc.vo.pub.lang.UFBoolean.TRUE);
			showNullAss = (UFBoolean) nullAssStyleCache.get(pk_glorgbook);
			Logger.error(e.getMessage(), e);
			nc.bs.logging.Logger.debug("没有取到辅助核算为空时显示模式");

		}
		// 根据显示模式生成显示字符串
		//*************************************************
//		style = ParaMacro.ASSDISPNAME_ASSNAME;//TODO 设置默认值，调试时使用
		//*************************************************
		try {
			if (style.intValue() == ParaMacro.ASSDISPNAME_ASSNAME) {
				/** 辅助项名称 */
				for (int i = 0; vos != null && i < vos.length; i++) {
					if (showNullAss.booleanValue())
						showName = showName + "[" + (vos[i].getCheckvaluename() == null ? StrTools.NULL : vos[i].getCheckvaluename()) + "]";/*-=notranslate=-*/
					else
						showName = showName + ((vos[i].getPk_Checkvalue() == null || vos[i].getPk_Checkvalue().trim().equals("") || StrTools.NULL.equals(vos[i].getPk_Checkvalue())) ? "" : ("["+ (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]"));/*-=notranslate=-*/
				}
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ASSDISPNAME_ASSTYPEANDNAME) {
				/** 辅助核算类型＋辅助项名称 */

				for (int i = 0; vos != null && i < vos.length; i++) {
					String checkTypeName ="";
					if(vos[i].getChecktypename() != null && vos[i].getChecktypename().equals(oppName)){
					    checkTypeName = oppName;
					}else{
						// hurh 连接数修改，暂时认为下面代码没有必要
						checkTypeName = vos[i].getChecktypename();
					}

					if (checkTypeName == null)
						checkTypeName = getNoMltLanCheckTypeName(pk_glorgbook,vos[i].getPk_Checktype());
					if (showNullAss.booleanValue())
						showName = showName + "[" + checkTypeName + "：" + (vos[i].getCheckvaluename() == null ? StrTools.NULL : vos[i].getCheckvaluename()) + "]";/*-=notranslate=-*/
					else
						showName = showName
								+ ((vos[i].getPk_Checkvalue() == null || vos[i].getPk_Checkvalue().trim().equals("") || StrTools.NULL.equals(vos[i].getPk_Checkvalue())) ? "" : "["/*-=notranslate=-*/
										+ checkTypeName
										+ "：" + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename())+ "]");/*-=notranslate=-*/
				}
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ASSDISPNAME_ASSCODEANDNAME) {
				/** 辅助项编码＋辅助项名称 */
				for (int i = 0; vos != null && i < vos.length; i++) {
					if (showNullAss.booleanValue())
						showName = showName + "[" + (vos[i].getCheckvaluecode() == null ? StrTools.NULL : vos[i].getCheckvaluecode()) + "/" + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]";/*-=notranslate=-*/
					else
						showName = showName + ((vos[i].getPk_Checkvalue() == null || vos[i].getPk_Checkvalue().trim().equals("") || StrTools.NULL.equals(vos[i].getPk_Checkvalue())) ? "" : ("["+ (vos[i].getCheckvaluecode() == null ? "" : vos[i].getCheckvaluecode()) + "/" + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]" ));/*-=notranslate=-*/
				}
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ASSDISPNAME_ASSTYPEPLUSCODEANDNAME) {
				/** 辅助核算类型＋辅助项编码＋辅助项名称 */
				for (int i = 0; vos != null && i < vos.length; i++) {
//					String checkTypeName = glm.getBdinfoName(vos[i].getPk_Checktype(), vos[i].getChecktypename());
					String checkTypeName = vos[i].getChecktypename();
					if (checkTypeName == null)
						checkTypeName = getNoMltLanCheckTypeName(pk_glorgbook,vos[i].getPk_Checktype());
					if (showNullAss.booleanValue())
						showName = showName + "[" + checkTypeName + "：" + (vos[i].getCheckvaluecode() == null ? StrTools.NULL : vos[i].getCheckvaluecode()) + "/" + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]";/*-=notranslate=-*/
					else
						showName = showName + ((vos[i].getPk_Checkvalue() == null || vos[i].getPk_Checkvalue().trim().equals("") || StrTools.NULL.equals(vos[i].getPk_Checkvalue())) ? "" : ("["+ checkTypeName + "：" + (vos[i].getCheckvaluecode() == null ? "" : vos[i].getCheckvaluecode()) + "/" + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]"));/*-=notranslate=-*/
				}
			} else if (style.intValue() == nc.vo.glcom.para.ParaMacro.ASSDISPNAME_ASSALLNAME) {
				IGeneralAccessor assaccessor = null;
				
				/** 辅助项逐级名称 */
				for (int i = 0; vos != null && i < vos.length; i++) {
					//AssCodeConstructor tool = new AssCodeConstructor();
					String pk_corp = AccountBookUtil.getPk_org(pk_glorgbook);
					//BddataVO[] bddatas = tool.getAllparentDoc(pk_corp, vos[i].getPk_Checktype(), vos[i].getPk_Checkvalue());
					
					// hurh 根据辅助核算取上级档案
					assaccessor = GeneralAccessorFactory.getAccessor(vos[i].getM_classid());
					List<IBDData> fatherDocs = null;
					if(!StrTools.isEmptyStr(vos[i].getPk_Checkvalue())&&!vos[i].getPk_Checkvalue().equals("~")){
						fatherDocs = assaccessor.getFatherDocs(pk_corp, vos[i].getPk_Checkvalue(), false);
					}
					
					String parentName = "";
					if (fatherDocs == null || fatherDocs.size() == 0) {
						parentName = null;
					} else {
						for (int j = fatherDocs.size(); j > 0; j--) {
							parentName += fatherDocs.get(j - 1).getName() + "/";
						}
					}
					if (showNullAss.booleanValue())
						showName = showName + "[" + (parentName == null ? "" : parentName) + (vos[i].getCheckvaluename() == null ? StrTools.NULL : vos[i].getCheckvaluename()) + "]" ;/*-=notranslate=-*/
					else
						showName = showName + ((vos[i].getPk_Checkvalue() == null || vos[i].getPk_Checkvalue().trim().equals("") || StrTools.NULL.equals(vos[i].getPk_Checkvalue())) ? "" : ("["+ (parentName == null ? "" : parentName) + (vos[i].getCheckvaluename() == null ? "" : vos[i].getCheckvaluename()) + "]"));/*-=notranslate=-*/
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
		return showName;
	}

	/**
	 * @param pk_bdinfo
	 * @param string 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String getNoMltLanCheckTypeName(String pk_accountingbook, String pk_bdinfo) {
		GlBdinfoVO vo = BDInfoDataCache.getInstance().getBDNameByPK(pk_accountingbook,AccountCalendarUtils.getStdDate(), pk_bdinfo);
		if(vo==null){
			return "";
		}
		return vo.getBdname();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-10-17 15:12:34)
	 *
	 * @return java.lang.String
	 * @param vo
	 *            java.lang.Object
	 * @param language
	 *            java.lang.String
	 */
	private static String getName(nc.vo.bd.account.AccountVO vo) {
		return GLMultiLangUtil.getMultiName(vo);
	}

	/**
	 * 按照科目辅助核算顺序格式化辅助核算信息
	 *
	 * @param pk_glorgbook
	 * @param pk_accsubj
	 * @param year
	 * @param period
	 * @param vos
	 * @return
	 */
	public static String getShowAss(String pk_glorgbook, String pk_accsubj, String stddate, nc.vo.glcom.ass.AssVO[] vos) {
		AssVO[] newVos = vos;
		if (pk_accsubj != null && pk_accsubj.length() != 0) {
			AccountVO subjvo = null;
			subjvo = AccountCache.getInstance().getAccountVOByPK(pk_glorgbook, pk_accsubj, stddate);
			
			AccAssVO[] rstAssVos =null;
			if(subjvo!=null){
				rstAssVos = subjvo.getAccass();
				
			///Vector<AccAssVO> sortAssVector = AccAssGL.getAccAssVOsByAccount(pk_glorgbook, subjvo,stddate);
//			if (sortAssVector != null && sortAssVector.size() > 0) {
//				rstAssVos=new AccAssVO[sortAssVector.size()];
//				sortAssVector.toArray(rstAssVos);
//			}
			}

			HashMap<String, AssVO> map = new HashMap<String, AssVO>();
			List<AssVO> list = new ArrayList<AssVO>();
			if (newVos != null) {
				for (int i = 0; i < newVos.length; i++) {
					map.put(newVos[i].getPk_Checktype(), newVos[i]);
					list.add(newVos[i]);
				}
				Vector<AssVO> rewAssVector = new Vector<AssVO>();
				for (int i = 0; rstAssVos!=null&&i < rstAssVos.length; i++) {
					if (map.get(rstAssVos[i].getPk_entity()) != null) {
						rewAssVector.add(map.get(rstAssVos[i].getPk_entity()));
						list.remove(map.get(rstAssVos[i].getPk_entity()));
						map.remove(rstAssVos[i].getPk_entity());
					}
				}
				// Iterator itrt = map.values().iterator();
				// while (itrt.hasNext()) {
				// rewAssVector.add((AssVO) itrt.next());
				// }
				for (AssVO assVO : list) {
					if (assVO != null) {
						rewAssVector.add(assVO);
					}

				}

				// while (list.listIterator().hasNext()) {
				// AssVO ass = list.listIterator().next();
				// if (ass != null)
				// rewAssVector.add(ass);
				//
				// }
				newVos = new AssVO[rewAssVector.size()];
				newVos = rewAssVector.toArray(newVos);
			}
		}
		return getShowAss(pk_glorgbook, newVos);
	}
	
	/**
	 * 
	 * 辅助核算换行显示
	 * <p>修改记录：</p>
	 * @param str
	 * @return
	 * @see 
	 * @since V6.3
	 * @author hurh
	 */
	public static String changeAssShowWithHtml(String str) {
		/*Pattern ptn1 = Pattern.compile("([[][^]]+[]])+");-=notranslate=-
		Pattern ptn2 = Pattern.compile("[[][^]]+[]]");-=notranslate=-*/
		//modified by weiningc  633适配至65  修改为英文括号  20171017  start
		Pattern ptn1 = Pattern.compile("([\\[][^\\]]+[\\]])+");/*-=notranslate=-*/
		Pattern ptn2 = Pattern.compile("[\\[][^\\]]+[\\]]");/*-=notranslate=-*/
		//modified by weiningc  633适配至65  修改为英文括号  20171017  end
		
		if (str == null)
			return str;
		Matcher m = ptn1.matcher(str);
		StringBuffer stb = new StringBuffer();
		if (m.matches()) {
			String tmp = null;
			stb.append("<html>");
			for (m = ptn2.matcher(str); m.find();){
				tmp = m.group();
				if (tmp.length() <= 50){
					stb.append(tmp).append("<p>");
				}else{
					stb.append(tmp.substring(0, 50)).append("<p>");
					tmp = tmp.substring(50);
					while(tmp.length() > 50){
						stb.append(tmp.substring(0, 50)).append("<p>");
						tmp = tmp.substring(50);
					}
					stb.append(tmp).append("<p>");
				}
			}
			stb.append("</html>");
			return stb.toString();
		}
		if (str.length() <= 50)
			return str;
		stb.append("<html>");
		for (; str.length() > 50; str = str.substring(50))
			stb.append(str.substring(0, 50)).append(" <p>");

		stb.append(str);
		stb.append("</html>");
		return stb.toString();
	}
	
	/**
	 * 
	 * 会计科目换行显示
	 * <p>修改记录：</p>
	 * @param str
	 * @return
	 * @see 
	 * @since V6.36
	 * @author wangsg
	 */
	public static String changeAccountVOWithHtml(String name, String str) {
		String tip  = name + ":";
		if (str.length() <= 50)
			return tip + str;
		StringBuffer stb = new StringBuffer();
		stb.append("<html>");
		stb.append(tip);
		for (; str.length() > 50; str = str.substring(50))
			stb.append(str.substring(0, 50)).append(" <p>");

		stb.append(str);
		stb.append("</html>");
		return stb.toString();
	}
	
}