package nc.vo.arap.util;

import java.util.List;
import java.util.Vector;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.util.DefaultVOMerger;

public class ParameterVOMarger extends DefaultVOMerger {

	@Override
	protected CircularlyAccessibleValueObject merge(
			CircularlyAccessibleValueObject[] vos, boolean isOtherAttr) {

		// ��������Ϸ����ж�
		if (vos == null || vos.length == 0) {
			return null;
		}
		CircularlyAccessibleValueObject vo = null;
		try {
			vo = vos[0].getClass().newInstance();
		} catch (Exception e) {
			ExceptionUtils.wrappException(e);
			return null;
		}
		// ���
		String[] sumAttr = this.getSummingAttr();
		for (int i = 0; sumAttr != null && i < sumAttr.length; i++) {
			int iValue = 0;
			double dValue = 0;
			// 0:��ʾiValue��dValue��û�б��ӹ� 1:��ʾiValue�����ӹ�
			// 2:��ʾdValue�����ӹ�
			int flag = 0;

			for (CircularlyAccessibleValueObject vo2 : vos) {
				Object ob = vo2.getAttributeValue(sumAttr[i]);
				if (ob != null && ob.getClass().equals(Integer.class)) {
					iValue += ((Integer) ob).intValue();
					flag = 1;
				}
				if (ob != null && ob.getClass().equals(UFDouble.class)) {
					dValue += ((UFDouble) ob).doubleValue();
					flag = 2;
				}
			}
			if (flag == 0) {
				vo.setAttributeValue(sumAttr[i], null);
			}
			if (flag == 1) {
				vo.setAttributeValue(sumAttr[i], Integer.valueOf(iValue));
			}
			if (flag == 2) {
				vo.setAttributeValue(sumAttr[i], new UFDouble(dValue));
			}
		}

		// ��ƽ��
		String[] averageAttr = this.getAveragingAttr();
		for (int i = 0; averageAttr != null && i < averageAttr.length; i++) {
			int iValue = 0;
			double dValue = 0;
			// 0:��ʾiValue��dValue��û�б��ӹ� 1:��ʾiValue�����ӹ�
			// 2:��ʾdValue�����ӹ�
			int flag = 0;

			for (CircularlyAccessibleValueObject vo2 : vos) {
				Object ob = vo2.getAttributeValue(averageAttr[i]);
				if (ob != null && ob.getClass().equals(Integer.class)) {
					iValue += ((Integer) ob).intValue();
					flag = 1;
				}
				if (ob != null && ob.getClass().equals(UFDouble.class)) {
					dValue += ((UFDouble) ob).doubleValue();
					flag = 2;
				}
			}
			if (flag == 0) {
				vo.setAttributeValue(averageAttr[i], null);
			}
			if (flag == 1) {
				vo.setAttributeValue(averageAttr[i],
						Integer.valueOf(iValue / vos.length));
			}
			if (flag == 2) {
				vo.setAttributeValue(averageAttr[i], new UFDouble(dValue
						/ vos.length));
			}
		}

		String[] otherAttr = this.getGroupingAttr();
		if (isOtherAttr) {
			// ������������
			otherAttr = this.getOtherAttr(vos[0]);
		}

		for (int i = 0; otherAttr != null && i < otherAttr.length; i++) {
			boolean bSame = true;
			Object first = vos[0].getAttributeValue(otherAttr[i]);

			for (int j = 1; j < vos.length; j++) {
				Object next = vos[j].getAttributeValue(otherAttr[i]);

				if (first == null) {
					if (next != null) {
						bSame = false;
					}
				} else {
					if (next == null) {
						bSame = false;
					} else {
						if (!first.equals(next)) {
							first = next;
							bSame = true;
						}
					}
				}
			}

			if (bSame) {
				vo.setAttributeValue(otherAttr[i], first);
			} else {
				vo.setAttributeValue(otherAttr[i], null);
			}
		}

		return vo;

	}

	private String[] getOtherAttr(CircularlyAccessibleValueObject vo) {
		String[] atts = vo.getAttributeNames();
		List<String> v = new Vector<String>();
		String[] sumAtt = this.getSummingAttr();
		String[] avgAtt = this.getAveragingAttr();
		String[] proavgAtt = this.getProavgingAttr();

		for (int i = 0; atts != null && i < atts.length; i++) {

			boolean exist = false;
			for (int j = 0; sumAtt != null && j < sumAtt.length; j++) {
				if (atts[i].equals(sumAtt[j])) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				for (int j = 0; avgAtt != null && j < avgAtt.length; j++) {
					if (atts[i].equals(avgAtt[j])) {
						exist = true;
						break;
					}
				}
			}
			if (!exist) {
				for (int j = 0; proavgAtt != null && j < proavgAtt.length; j++) {
					if (atts[i].equals(proavgAtt[j])) {
						exist = true;
						break;
					}
				}
			}

			if (!exist) {
				v.add(atts[i]);
			}

		}
		if (this.getDynamicOtherKeys() != null) {
			for (String dynamicKey : this.getDynamicOtherKeys()) {
				v.add(dynamicKey);
			}
		}

		String[] others = null;
		if (v.size() > 0) {
			others = new String[v.size()];
			others = v.toArray(others);
		}
		return others;
	}
}
