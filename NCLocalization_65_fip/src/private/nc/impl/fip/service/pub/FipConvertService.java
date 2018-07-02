package nc.impl.fip.service.pub;

import java.util.Collection;
import java.util.List;

import nc.pubitf.fip.service.IFipConvertService;
import nc.vo.fip.service.FipBaseMessageVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fip.trans.FipTransVO;
import nc.vo.fip.trans.FipTranslateResultVO;
import nc.vo.pub.BusinessException;

public class FipConvertService implements IFipConvertService {

	public void convertAndSave(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException {
		new FipConvertBO().convertAndSave(desInfo, srcbill);
	}

	public List<FipTranslateResultVO> convertOnly(FipRelationInfoVO desInfo, Collection<FipBaseMessageVO> srcbill) throws BusinessException {
		// TODO Auto-generated method stub
		return new FipConvertBO().convertOnly(desInfo, srcbill);
	}

	@Override
	public List<FipTransVO> mergerDetails(List<FipTransVO> fipTransVOs) throws BusinessException {
		return new FipConvertBO().mergerDetails(fipTransVOs);
	}

}
