package shopping;

import java.util.HashSet;
import java.util.Set;

public class Filter {

	public static boolean isFit(Product product) {
		int low_price = 300;
		int high_price = 600;
		int low_sold = 100;
		double min_discount = 0.1;
		Set<String> madeIn = new HashSet<String>();
		Set<String> notMadeIn = new HashSet<String>();

		boolean isFitMadeIn = true;

		if (madeIn.size() > 0 || notMadeIn.size() > 0) {
			isFitMadeIn = madeIn.contains(product.getMadeIn())
					&& !notMadeIn.contains(product.getMadeIn());
		}

		return product.getPrice() >= low_price
				&& product.getPrice() <= high_price
				&& product.getSold() >= low_sold
				&& product.getDiscount() >= min_discount && isFitMadeIn;
	}
}
