package com.andreas.scrapper;

import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;

public class HtmlUnitScrapper {

	public static void main(String[] args) {
		try {
			String url = "https://www.tokopedia.com/search?navsource=home&ob=9&page=2&q=handphone&st=product";
			WebClient web = new WebClient(BrowserVersion.CHROME);
			web.getOptions().setJavaScriptEnabled(false);
			web.getOptions().setCssEnabled(false);
			web.getOptions().setThrowExceptionOnFailingStatusCode(false);
			web.getOptions().setTimeout(10000);
			
			HtmlPage page = web.getPage(url);
			JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
				while (manager.getJobCount() > 0) {
				    Thread.sleep(1000);
				}
			
			FileWriter obj = new FileWriter("web.html");
        	
			String temp = page.asXml();
			
        	obj.write(page.asXml());
        	obj.close();
        	
        	Document doc = Jsoup.parse(temp, url);
        	Elements elements = doc.select("div[data-testid=master-product-card]");
        	for (Element element: elements){
        		System.out.println(element.select("div[data-testid=spnSRPProdName]").text());
        		System.out.println(element.select("div[data-testid=spnSRPProdPrice]").text());
        		System.out.println(element.select("div[data-testid=imgSRPProdMain]").select("img").attr("src"));
        	}
			
			System.out.println(page.getTitleText());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
