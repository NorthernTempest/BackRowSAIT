package bean;

import java.util.Comparator;

public class PaymentDateComparator implements Comparator<PaymentBean> {

	@Override
	public int compare(PaymentBean arg0, PaymentBean arg1) {
		if (arg0.getDate().after(arg1.getDate())) {
			return 1;
		} else if (arg0.getDate().before(arg1.getDate())) {
			return -1;
		} else {
			return 0;
		}
	}
}
