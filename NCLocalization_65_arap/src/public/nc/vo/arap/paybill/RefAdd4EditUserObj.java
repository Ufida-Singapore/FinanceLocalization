package nc.vo.arap.paybill;

import java.util.List;
import java.util.Set;

public class RefAdd4EditUserObj {
	private String pk_org;
	private String pk_supplier;
	private String pk_customer;
	private List<String> pk_items;
	private String whereStr;

	public String getPk_org() {
		return pk_org;
	}
	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}
	public String getPk_supplier() {
		return pk_supplier;
	}
	public void setPk_supplier(String pk_supplier) {
		this.pk_supplier = pk_supplier;
	}
	public String getWhereStr() {
		return whereStr;
	}
	public void setWhereStr(String whereStr) {
		this.whereStr = whereStr;
	}
	public List<String> getPk_items() {
		return pk_items;
	}
	public void setPk_items(List<String> pk_items) {
		this.pk_items = pk_items;
	}
	public String getPk_customer() {
		return pk_customer;
	}
	public void setPk_customer(String pk_customer) {
		this.pk_customer = pk_customer;
	}

}
