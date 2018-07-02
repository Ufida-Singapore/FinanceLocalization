package nc.ui.arap.cal;

import nc.vo.fipub.exception.ExceptionHandler;
import nc.vo.pub.lang.UFDouble;

public class ScaleUtils extends nc.vo.pubapp.scale.ScaleUtils {

	public ScaleUtils(String pkGroup) {
		super(pkGroup);
	}

	  /*
	   * 处理销售采购 单价精度 UFDouble value 待处理的数据
	   */
	  public UFDouble adjustSoPuPriceScale(UFDouble value, String pk_curr) {
		try {
			  return super.adjustSoPuPriceScale(value, pk_curr);
		} catch (Exception e) {
			ExceptionHandler.consume(e);
		}
	    return value;
	  }
}
