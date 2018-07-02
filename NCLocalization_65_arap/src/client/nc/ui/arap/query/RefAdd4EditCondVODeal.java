package nc.ui.arap.query;

import java.util.HashMap;
import java.util.Map;

import nc.ui.pubapp.uif2app.query2.totalvo.IQueryConditionVODealer;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.QueryConditionVO;

/**
 * 
 * @author chenth 20170219
 * @see nc.ui.pubapp.uif2app.query2.totalvo.DefaultValueDealer
 *
 */
public class RefAdd4EditCondVODeal implements IQueryConditionVODealer {

	  public static class DefaultValue {
	    String pk;

	    // String text;

	    public DefaultValue(String pk) {
	      this.pk = pk;
	    }
	  }

	  private Map<String, RefAdd4EditCondVODeal.DefaultValue> valueMap =
	      new HashMap<String, RefAdd4EditCondVODeal.DefaultValue>();

	  @Override
	  public QueryConditionVO[] deal(QueryConditionVO[] conditions) {
	    for (QueryConditionVO qcvo : conditions) {
	      // if (StringUtils.isNotEmpty(qcvo.getValue())) {
	      // // 如果模板上设置了默认值，则不处理
	      // continue;
	      // }

	      String key = this.getKey(qcvo.getTableCode(), qcvo.getFieldCode());
	      DefaultValue value = this.valueMap.get(key);
	      if (null != value && value.pk != null) {
	        qcvo.setValue(value.pk);
	        // if (!StringUtil.isEmpty(value.text)) {
	        // qcvo.setDispValue(value.text);
	        // }
	        qcvo.setIfImmobility(UFBoolean.TRUE);
	      }
	    }
	    return conditions;
	  }

	  public void setDefaultValue(String tableCode, String fieldCode, String pk) {
	    this.valueMap.put(this.getKey(tableCode, fieldCode), new DefaultValue(pk));
	  }

	  private String getKey(String ptableCode, String fieldCode) {
	    String tableCode = ptableCode;
	    if (null == tableCode) {
	      tableCode = "";
	    }
	    return tableCode + "%$%" + fieldCode;
	  }

	}
