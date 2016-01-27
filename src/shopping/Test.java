package shopping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) throws Exception {
		Collector c = new Collector();
		c.gather("stroller");
//		String sold = "(.*?)(\\d+)(%* sold)(.*)";
//		String s = "20% sold";
//		Pattern p = Pattern.compile(sold);
//		Matcher m = p.matcher(s);
//		if(m.find()) {
//			System.out.println(m.group(2));
//		}
	}

}
