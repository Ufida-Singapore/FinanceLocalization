package nc.itf.arap.bill;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.fip.service.FipMessageVO;
import nc.vo.fipub.report.AggReportInitializeVO;
import nc.vo.fipub.report.QryObj;
import nc.vo.gl.busirecon.query.BusiReconQryVO;
import nc.vo.gl.busirecon.query.BusiRuleVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.rs.MemoryResultSet;

/**
 * @since 2010-09-21
 * @author zhaoruic
 *
 */

public interface IArapBillService {
	
	/**
	 *���Ĵ�ӡ��Ϣ
	 * @param pks
	 * @param printer
	 * @param printtime
	 * @return
	 * @throws BusinessException
	 */
	public int updatePrintStatus(String table, String pkname, String pk, String printer, UFDate printtime) throws BusinessException;

	/**
	 * ɾ����嵥�ݻ�дԭ���ݺ���־
	 * @param table
	 * @param pkfield
	 * @param primarykey
	 * @throws BusinessException
	 */
	public void updateIsredFlag(String table,String pkfield,String primarykey) throws BusinessException;
	
	/**
	 * ���µ���������Ϣ
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public int updateParentVO(BaseBillVO vo) throws BusinessException;

	/**
	 * ����Эͬ����ȡ��ȷ�ϵ���Ϣ����Ϊ��Ĺ��÷������Ὣ״̬�����-1��������̬�������Լ�дsqlʵ��
	 */
	public String updateCancelConfirmInfo(String table, String pkfield, String primarykey) throws BusinessException;
	
	/**
	 * ����Эͬ����ȡ��ȷ�ϵ���Ϣ����Ϊ��Ĺ��÷������Ὣ״̬�����-1��������̬�������Լ�дsqlʵ��
	 */
	public void updateConfirmInfo(String table, String pkfield, String primarykey) throws BusinessException;
	
	/**
	 * ����Ԫ����id��ѯ�����ж�Ӧ�Ĳ�ѯ�������ԡ���������ɷ�Ϊ�ͻ�����Ͳ��������ñ��Ӧ��Ԫ���ݲ�ͬ���ʿ���ͨ��Ԫ���ݵ�id�������֡�
	 * ���⣬�ͻ������������ֲ��漰������ͨ��count(*) =1���˵�
	 * @param bd_mdid ��Ԫ����id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public List<QryObj> getReportQryObj(String bd_mdid);
	
	/**
	 * ����pkȡ�������ʼ���������ľۺ�VO
	 * @param pk
	 * @return
	 * @throws DAOException
	 */
	public AggReportInitializeVO getAggReportInitializeVO(String pk) throws DAOException;
	
	/**
	 * ����Ԫ����id��ѯ��Ӧ�����е�nodecode
	 * @param bd_mdid ��Ԫ����id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public String getNodeCodeByMdid(String bd_mdid);
	
	/**
	 * ����Ԫ����id��ѯ��Ӧ�����еĳ�ʼ��������ReportInitializeVO
	 * @param bd_mdid ��Ԫ����id
	 * @return
	 * @author zhaoruic
	 * @throws BusinessException 
	 * @since V6.0 2010-12-28
	 */
	public AggReportInitializeVO getReportInitializeVOByMdid(String bd_mdid) throws BusinessException;
	
	/**
	 * ����nodecodeȡ��ע��ڵ�id
	 * @param nodeCode
	 * @return
	 * @throws BusinessException
	 */
	public String getFuncIdByNodeCode(String nodeCode) throws BusinessException;
	
	/**
	 * ����������ѯ��������
	 * @param where
	 * @return
	 * @throws BusinessException
	 */
	public BilltypeVO[] queryBillTypeByWhereStr(String where) throws BusinessException;
	
	/**
	 * ���ݽ������Ͳ�ѯ���ϼ���������
	 * @param tradeType
	 * @param pk_group
	 * @return
	 */
	public String getParentBillTypeByTradeType(String tradeType, String pk_group);
	
	/**
	 * �ж��ڳ������ǲ�������ת�Ʋ��ˡ����������ˡ�����Ȳ�����������˲����޸ĺ�ɾ��������false�� ���û�����򷵻�true
	 * @param pk_bill
	 * @return
	 */
	public boolean isInitBillCanEditOrDelete( String[] pk_bills );
	
	/**
	 * ��ѯIUFO��ʽ�в�ѯ�����������Ӧ�Ĳ�ѯ����
	 * @param bd_mdid ��ѯ����Ԫ����ID
	 * @return
	 */
	public List<QryObj> getReportQryObjForIUFO(String bd_mdid) ;
	
	
	/**
	 * �������˶��˹��򣬲�ѯ������ϸ
	 * @param queryVO
	 * @param busiRuleVO
	 * @return MemoryResultSet
	 */
	public MemoryResultSet[] queryBrBillForGL(BusiReconQryVO queryVO, BusiRuleVO busiRuleVO) throws SQLException, BusinessException;
	
	
	
	public  Object[] save(AggregatedValueObject[] bill,String pk_billtype) throws BusinessException ;
	
	public Collection<FipMessageVO[]> previewFillMessage(BaseAggVO[] bills) throws BusinessException;

}
