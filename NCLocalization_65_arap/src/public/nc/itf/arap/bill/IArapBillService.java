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
	 *更改打印信息
	 * @param pks
	 * @param printer
	 * @param printtime
	 * @return
	 * @throws BusinessException
	 */
	public int updatePrintStatus(String table, String pkname, String pk, String printer, UFDate printtime) throws BusinessException;

	/**
	 * 删除红冲单据回写原单据红冲标志
	 * @param table
	 * @param pkfield
	 * @param primarykey
	 * @throws BusinessException
	 */
	public void updateIsredFlag(String table,String pkfield,String primarykey) throws BusinessException;
	
	/**
	 * 更新单据主表信息
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public int updateParentVO(BaseBillVO vo) throws BusinessException;

	/**
	 * 更新协同单据取消确认的信息。因为别的公用方法都会将状态保存成-1，即保存态，所以自己写sql实现
	 */
	public String updateCancelConfirmInfo(String table, String pkfield, String primarykey) throws BusinessException;
	
	/**
	 * 更新协同单据取消确认的信息。因为别的公用方法都会将状态保存成-1，即保存态，所以自己写sql实现
	 */
	public void updateConfirmInfo(String table, String pkfield, String primarykey) throws BusinessException;
	
	/**
	 * 根据元数据id查询余额表中对应的查询对象属性。由于余额表可分为客户余额表和部门余额表，该表对应的元数据不同，故可以通过元数据的id进行区分。
	 * 另外，客户部门余额表本部分不涉及，所以通过count(*) =1过滤掉
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public List<QryObj> getReportQryObj(String bd_mdid);
	
	/**
	 * 根据pk取得余额表初始环境变量的聚合VO
	 * @param pk
	 * @return
	 * @throws DAOException
	 */
	public AggReportInitializeVO getAggReportInitializeVO(String pk) throws DAOException;
	
	/**
	 * 根据元数据id查询对应余额表中的nodecode
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @since V6.0 2010-12-28
	 */
	public String getNodeCodeByMdid(String bd_mdid);
	
	/**
	 * 根据元数据id查询对应余额表中的初始环境参数ReportInitializeVO
	 * @param bd_mdid ：元数据id
	 * @return
	 * @author zhaoruic
	 * @throws BusinessException 
	 * @since V6.0 2010-12-28
	 */
	public AggReportInitializeVO getReportInitializeVOByMdid(String bd_mdid) throws BusinessException;
	
	/**
	 * 根据nodecode取得注册节点id
	 * @param nodeCode
	 * @return
	 * @throws BusinessException
	 */
	public String getFuncIdByNodeCode(String nodeCode) throws BusinessException;
	
	/**
	 * 根据条件查询单据类型
	 * @param where
	 * @return
	 * @throws BusinessException
	 */
	public BilltypeVO[] queryBillTypeByWhereStr(String where) throws BusinessException;
	
	/**
	 * 根据交易类型查询其上级单据类型
	 * @param tradeType
	 * @param pk_group
	 * @return
	 */
	public String getParentBillTypeByTradeType(String tradeType, String pk_group);
	
	/**
	 * 判断期初单据是不是做了转移并账、核销、坏账、损益等操作，如果做了不能修改和删除，返回false， 如果没做否则返回true
	 * @param pk_bill
	 * @return
	 */
	public boolean isInitBillCanEditOrDelete( String[] pk_bills );
	
	/**
	 * 查询IUFO公式中查询对象参数所对应的查询对象
	 * @param bd_mdid 查询对象元数据ID
	 * @return
	 */
	public List<QryObj> getReportQryObjForIUFO(String bd_mdid) ;
	
	
	/**
	 * 根据总账对账规则，查询单据明细
	 * @param queryVO
	 * @param busiRuleVO
	 * @return MemoryResultSet
	 */
	public MemoryResultSet[] queryBrBillForGL(BusiReconQryVO queryVO, BusiRuleVO busiRuleVO) throws SQLException, BusinessException;
	
	
	
	public  Object[] save(AggregatedValueObject[] bill,String pk_billtype) throws BusinessException ;
	
	public Collection<FipMessageVO[]> previewFillMessage(BaseAggVO[] bills) throws BusinessException;

}
