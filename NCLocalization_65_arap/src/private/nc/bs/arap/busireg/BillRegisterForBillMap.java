package nc.bs.arap.busireg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.arap.util.SqlUtils;
import nc.bs.businessevent.BdUpdateEvent;
import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.businessevent.IEventType;
import nc.bs.dao.BaseDAO;
import nc.bs.logging.Log;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.itf.cmp.pub.Currency;
import nc.md.persist.framework.MDPersistenceService;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.gathering.AggGatheringBillVO;
import nc.vo.arap.pay.AggPayBillVO;
import nc.vo.arap.payable.AggPayableBillVO;
import nc.vo.arap.pfflow.ArapBillMapVO;
import nc.vo.arap.pfflow.ArapBillMapVOTool;
import nc.vo.arap.receivable.AggReceivableBillVO;
import nc.vo.arap.utils.StringUtil;
import nc.vo.bd.currinfo.CurrinfoVO;
import nc.vo.fibd.RecpaytypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDouble;

public class BillRegisterForBillMap implements IBusinessListener{

	/**
	 * 统一调用批量接口
	 * @param obj
	 * @return
	 */
	protected AggregatedValueObject[] dealUserObj(Object obj){
		if(obj instanceof BusinessEvent.BusinessUserObj){
			obj=((BusinessEvent.BusinessUserObj)obj).getUserObj();
		}
		AggregatedValueObject[] retvos=null;
		if(obj.getClass().isArray()){
			retvos=(AggregatedValueObject[])obj;
		}else{
			retvos=new AggregatedValueObject[]{(AggregatedValueObject)obj};
		}
		return retvos;
	}
	
	/**
	 * 业务单据登账接口
	 */
	
	@SuppressWarnings("unchecked")
	public void doAction(IBusinessEvent event) throws BusinessException {
		//新增
		if(IEventType.TYPE_INSERT_AFTER.equals(event.getEventType())){
			if(event instanceof BusinessEvent){
				BusinessEvent e=(BusinessEvent)event;
				if(null != e.getUserObject()){
					AggregatedValueObject[] vos=dealUserObj(e.getUserObject());
					Object[] obj=transform(vos);
					List<ArapBillMapVO> insertdatas=(List<ArapBillMapVO>)obj[0];
					List<ArapBillMapVO> updatedatas=(List<ArapBillMapVO>)obj[1];
					doInsertRegister(insertdatas);
					doInsertRegister(updatedatas);
				}
			}
		} 
		//更新
		else if(IEventType.TYPE_UPDATE_AFTER.equals(event.getEventType())){
			BdUpdateEvent e=(BdUpdateEvent)event;
			if(null != e.getNewObject()){
				AggregatedValueObject[] vos=dealUserObj(e.getNewObject());
				Object[] obj=transform(vos);
				//先删除，再插入
				List<ArapBillMapVO> insertdatas=(List<ArapBillMapVO>)obj[0];
				delArapBillMapVOsByItemPK(insertdatas);
				doInsertRegister(insertdatas);
				
				List<ArapBillMapVO> updatedatas=(List<ArapBillMapVO>)obj[1];
				delArapBillMapVOsByItemPK(updatedatas);
				doUpdateRegister(updatedatas);
//				List<String> delvos=(List<String>)obj[2];
				
//				doDeleteRegisterByItemid(delvos.toArray(new String[]{}));
			}
		}
		else if(IEventType.TYPE_APPROV_AFTER.equals(event.getEventType())||
				IEventType.TYPE_UNAPPROV_AFTER.equals(event.getEventType())){
			BusinessEvent e=(BusinessEvent)event;
			if(null != e.getUserObject()){
				AggregatedValueObject[] vos=dealUserObj(e.getUserObject());
				List<ArapBillMapVO> updatedatas=(List<ArapBillMapVO>)transform(vos)[1];
				doUpdateRegister(updatedatas);
			}

		}
		//删除
		else if(IEventType.TYPE_DELETE_AFTER.equals(event.getEventType())){
			BusinessEvent e=(BusinessEvent)event;
			if(null != e.getUserObject()){
				AggregatedValueObject[] vos=dealUserObj(e.getUserObject());
				List<String> deldata=new ArrayList<String>();
				for(AggregatedValueObject vo:vos){
					deldata.add(vo.getParentVO().getPrimaryKey());
				}
				doDeleteRegisterByBillid(deldata.toArray(new String[]{}));
			}
		}
	}
	@SuppressWarnings("unchecked")
	protected Object[] transform(AggregatedValueObject[] vos) throws BusinessException{
		List<SuperVO> insertvos=new ArrayList<SuperVO>();
		List<SuperVO> updatevos=new ArrayList<SuperVO>();
		List<String> delvos=new ArrayList<String>();
		Object[] ret=new Object[ ]{insertvos,updatevos,delvos};
		List<String> pks =new ArrayList<String>();
		
		//add chenth 20161210 
		List<String> recpaytypePks =new ArrayList<String>();
		Map<String,RecpaytypeVO> recpaytypeMap =new HashMap<String,RecpaytypeVO>();
		//add end 
		
		//增加上游单据的pk_org信息和拉单核销对应的组织本币
		for(AggregatedValueObject vo:vos){
			for(CircularlyAccessibleValueObject cvo:vo.getChildrenVO()){
				String topBillid = (String)cvo.getAttributeValue(IBillFieldGet.TOP_BILLID);
				if(!StringUtil.isEmpty(topBillid)){
					if(!pks.contains(topBillid)){
						pks.add(topBillid);
					}
				}
				
				//add chenth 20161210 
				recpaytypePks.add((String)cvo.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE));
				//add end 
			}
		}
		
		//add chenth 20161210
		if(recpaytypePks.size() > 0){
			Collection<RecpaytypeVO> typevos = new BaseDAO().retrieveByClause(RecpaytypeVO.class, SqlUtils.getInStr("pk_recpaytype", recpaytypePks.toArray(new String[]{})), new String[]{RecpaytypeVO.PK_RECPAYTYPE, RecpaytypeVO.ISBANKCHARGES});
			for(RecpaytypeVO typevo : typevos){
				recpaytypeMap.put(typevo.getPk_recpaytype(), typevo);
			}
		}
		//add end 
		
		String topBilltype =(String)vos[0].getChildrenVO()[0].getAttributeValue(IBillFieldGet.TOP_BILLTYPE);
		Map<String,String> map =new HashMap<String,String>();
		if(!StringUtil.isEmpty(topBilltype)&&pks.size()>0){
			Class<?> classvo =AggReceivableBillVO.class;
			if(IBillFieldGet.F1.equals(topBilltype)){
				classvo = AggPayableBillVO.class;
			}else if(IBillFieldGet.F2.equals(topBilltype)){
				classvo = AggGatheringBillVO.class;
			}else if(IBillFieldGet.F3.equals(topBilltype)){
				classvo = AggPayBillVO.class;
			}
			Collection<BaseAggVO> bills = (Collection<BaseAggVO>) MDPersistenceService.lookupPersistenceQueryService().queryBillOfVOByPKs(classvo, pks.toArray(new String[] {}),
					false);
			if(bills!=null){
				for(BaseAggVO baseAggVO:bills){
					map.put(baseAggVO.getPrimaryKey(),(String) baseAggVO.getParentVO().getAttributeValue(IBillFieldGet.PK_ORG));
				}
			}
		}
		for(AggregatedValueObject vo:vos){
			SuperVO[] items=(SuperVO[]) vo.getChildrenVO();
			for(SuperVO item:items){
				ArapBillMapVO transform = transform(item,(SuperVO) vo.getParentVO());
				if(map.size()>0 && map!=null && transform != null){
					String pkOrg1=map.get(transform.getS_billid());
					transform.setPk_org1(pkOrg1);	
				}
				//add chenth 如果是“手续费”
				RecpaytypeVO recPayTypeVO = recpaytypeMap.get(item.getAttributeValue(IBillFieldGet.PK_RECPAYTYPE));
				if(transform != null && recPayTypeVO != null ){
					transform.setIsbankcharges(recPayTypeVO.getIsbankcharges());
				}
				//add chenth 
				
				switch(item.getStatus()){
				case VOStatus.NEW: 
					if(transform!=null)
						insertvos.add(transform);
					break;
				case VOStatus.DELETED:
					delvos.add(item.getPrimaryKey());
					break;
				default:
					if(transform!=null)
						updatevos.add(transform);
					break;
				}
			}
		}

		return ret;
	}
	
	public UFDouble bzHuansuanYB2ZB(UFDouble jsyb, UFDouble zbhl, String ybpk,String zbpk,String pkOrg) {

		UFDouble jszb = new UFDouble(0);
		try {
			CurrinfoVO currinfoVO = Currency.getCurrRateInfo(pkOrg, ybpk, zbpk);
			if (currinfoVO.getConvmode() == 0) {
				jszb = jsyb.multiply(zbhl, Currency.getCurrDigit(zbpk));
			} else {
				jszb = jsyb.div(zbhl, Currency.getCurrDigit(zbpk));
			}
		} catch (Exception e) {
			Log.getInstance(this.getClass()).debug(e.getMessage());
		}
		return jszb;
	}
	
	protected ArapBillMapVO transform(SuperVO item,SuperVO head){
		return ArapBillMapVOTool.changeVotoBillMap((BaseBillVO)head, (BaseItemVO)item);
	}
	
	private void delArapBillMapVOsByItemPK(List<ArapBillMapVO> updatevos) throws BusinessException{
		List<String> t_itempk = new ArrayList<String>();
		List<String> s_itempk = new ArrayList<String>();
		if(updatevos !=null && updatevos.size()>0){
			for(ArapBillMapVO vo:updatevos){
				t_itempk.add(vo.getT_itemid());
				s_itempk.add(vo.getS_itemid());
			}
			String s_sql = SqlUtils.getInStr("s_itemid", s_itempk.toArray(new String[0]));
			String t_sql = SqlUtils.getInStr("t_itemid", t_itempk.toArray(new String[0]));
			new BaseDAO().deleteByClause(ArapBillMapVO.class, s_sql +" and " +t_sql);
		}
	}
	protected void doInsertRegister(List<ArapBillMapVO> insertdatas) throws BusinessException{
		new BaseDAO().insertVOList(insertdatas);
	}
	
	protected void doUpdateRegister(List<ArapBillMapVO> updatedatas) throws BusinessException{
		new BaseDAO().insertVOList(updatedatas);
	}
	
	protected void doDeleteRegisterByBillid(String[] billids) throws BusinessException{
		new BaseDAO().deleteByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_billid", billids));
	}
	
	protected void doDeleteRegisterByItemid(String[] itemids) throws BusinessException{
		new BaseDAO().deleteByClause(ArapBillMapVO.class, SqlUtils.getInStr("t_itemid", itemids));
	}
	
}
