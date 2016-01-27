package shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Collector {
	private static String EBAY = "http://www.ebay.com/sch/i.html?_nkw=";
	private static String PAGE = "_pgn=";
	private static String CONNECTOR = "&";

	public List<Product> gather(String keyword) throws Exception {
		List<Product> products = new ArrayList<Product>();
		for (int page = 1; page <= 20; page++) {
			Thread.sleep(100);
			Elements es = Jsoup
					.connect(EBAY + keyword + CONNECTOR + PAGE + page).get()
					.select("li[r]");
			products.addAll(parseProducts(es));
		}
		for (Product p : products) {
			System.out.println(p.getPrice());
			System.out.println(p.getDiscount());
			System.out.println(p.getLink());
			System.out.println(p.getMadeIn());
			System.out.println(p.getSold());
			System.out.println(p.getWatching());
			System.out.println("==================================");
		}
		System.out.println(products.size());
		return products;
	}

	private List<Product> parseProducts(Elements es) {
		List<Product> products = new ArrayList<Product>();
		for (Element e : es) {
			Product product = parseProduct(e);
			if(Filter.isFit(product)) {
				products.add(product);
			}
		}
		return products;
	}

	private Product parseProduct(Element e) {
		try {
			Product product = new Product();
			product.setLink(e.getElementsByAttribute("href").attr("href"));
			product.setPrice(Double.parseDouble(e.getElementsByClass("lvprice")
					.text().split(" ")[1].replace(",", "")));
			setExtras(product, e);
			product.setMadeIn(e.getElementsByClass("lvdetails").text()
					.replace("From", "").replace(
					"Customs services and international tracking provided","").trim());
			return product;
		} catch (Exception ex) {
			System.err.println(e);
			System.err.println(ex.getMessage());
			System.err.println("============================");
			return new Product();
		}
	}

	private void setExtras(Product product, Element e) {
		String extras = e.getElementsByClass("lvextras").text();
		String watching = "(.*?)(\\d+)(\\+* watching)(.*)";
		String sold = "(.*?)(\\d+)(\\+* sold)(.*)";
		String discount = "(.*?)(\\d+)(%* off)(.*)";

		Pattern pWatching = Pattern.compile(watching);
		Pattern pSold = Pattern.compile(sold);
		Pattern pDiscount = Pattern.compile(discount);

		Matcher mWatching = pWatching.matcher(extras);
		Matcher mSold = pSold.matcher(extras);
		Matcher mDiscount = pDiscount.matcher(extras);
		if (mWatching.find()) {
			product.setWatching(Integer.parseInt(mWatching.group(2)));
		} else {
			product.setWatching(0);
		}

		if (mSold.find()) {
			product.setSold(Integer.parseInt(mSold.group(2)));
		} else {
			product.setSold(0);
		}

		if (mDiscount.find()) {
			product.setDiscount(Integer.parseInt(mDiscount.group(2)));
		} else {
			product.setDiscount(0);
		}
	}
}
