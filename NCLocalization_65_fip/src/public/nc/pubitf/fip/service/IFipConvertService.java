package nc.pubitf.fip.service;

import java.util.Collection;
import java.util.List;

import nc.vo.fip.service.FipBaseMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fip.trans.FipTransVO;
import nc.vo.fip.trans.FipTranslateResultVO;
import nc.vo.pub.BusinessException;

/**
 * <p>
 * TODO 接口/类功能说明，使用说明（接口是否为服务组件，服务使用者，类是否线程安全等）。
 * </p>
 * 
 * 修改记录：<br>
 * <li>修改人：修改日期：修改内容：</li> <br>
 * <br>
 * 
 * @see
 * @author Administrator
 * @version V6.0
 * @since V6.0 创建时间：2010-1-20 下午03:38:46
 */
public interface IFipConvertService {

	public void convertAndSave(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException;

	public List<FipTranslateResultVO> convertOnly(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException;
	
	//add by weiningc 633适配至65  会计平台支持凭证预览   20171016 会计平台支持凭证预览  start
	public List<FipTransVO> mergerDetails(List<FipTransVO> fipTransVOs) throws BusinessException;
	//add by weiningc 633适配至65  会计平台支持凭证预览   20171016 会计平台支持凭证预览  end

}
