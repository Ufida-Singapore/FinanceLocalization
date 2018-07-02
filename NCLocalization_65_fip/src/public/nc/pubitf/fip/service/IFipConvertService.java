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
 * TODO �ӿ�/�๦��˵����ʹ��˵�����ӿ��Ƿ�Ϊ�������������ʹ���ߣ����Ƿ��̰߳�ȫ�ȣ���
 * </p>
 * 
 * �޸ļ�¼��<br>
 * <li>�޸��ˣ��޸����ڣ��޸����ݣ�</li> <br>
 * <br>
 * 
 * @see
 * @author Administrator
 * @version V6.0
 * @since V6.0 ����ʱ�䣺2010-1-20 ����03:38:46
 */
public interface IFipConvertService {

	public void convertAndSave(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException;

	public List<FipTranslateResultVO> convertOnly(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException;
	
	//add by weiningc 633������65  ���ƽ̨֧��ƾ֤Ԥ��   20171016 ���ƽ̨֧��ƾ֤Ԥ��  start
	public List<FipTransVO> mergerDetails(List<FipTransVO> fipTransVOs) throws BusinessException;
	//add by weiningc 633������65  ���ƽ̨֧��ƾ֤Ԥ��   20171016 ���ƽ̨֧��ƾ֤Ԥ��  end

}
