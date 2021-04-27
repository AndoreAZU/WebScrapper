package com.andreas.scrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andreas.scrapper.utils.TokopediaHandphone;
import com.opencsv.CSVWriter;

public class App 
{

    public static void main( String[] args ) throws IOException{
        System.out.println( "Hello World!" );
        String url = "https://www.tokopedia.com/search?navsource=home&ob=9&page=1&q=handphone&st=product";
        List<TokopediaHandphone> listHp = new ArrayList<TokopediaHandphone>();
        
        long start = System.currentTimeMillis();
        
        System.out.println("===Start Scrapping===");
        
        File file = new File("TokopediaHandphone.csv");
        if(file.exists()) {
        	file.delete();
        }
        
        CSVWriter csv = new CSVWriter(new FileWriter(file));
        String[] header = {"No","Product Name","Price","Rating","Store Name", "URL Product", "URL Image", "Description"};
        csv.writeNext(header);
        
        Boolean reach_100 = false;
        try {
        	int count = 1;
        	while(count<=100) {
        		url = "https://www.tokopedia.com/search?navsource=home&ob=5&page=" + count + "&q=handphone&st=product";
        		Document doc = Jsoup.connect(url)
            			.maxBodySize(0)
            			.timeout(10000)
            			.get();
        		
        		Elements elements = doc.select("div[data-testid=master-product-card]");
        		
        		//Per page get 10 data
        		for(int i=0;i<10;i++) {
        			if(elements.get(i).select("div[data-testid=spnSRPProdName]").text().equals(""))continue;
        			TokopediaHandphone temp = new TokopediaHandphone();
        			temp.setId(count+"");
        			temp.setProductName(elements.get(i).select("div[data-testid=spnSRPProdName]").text());
        			temp.setPrice(elements.get(i).select("div[data-testid=spnSRPProdPrice]").text());
        			temp.setImgUrl(elements.get(i).select("div[data-testid=imgSRPProdMain]").select("img").attr("src"));
        			temp.setRating(elements.get(i).select("span[class=css-etd83i]").text().equals("") ? "doesnt have rating":elements.get(i).select("span[class=css-etd83i]").text());
        			temp.setUrlProduct(elements.get(i).select("div[class=css-1ehqh5q]").select("a").attr("href"));
        			temp.setStore(elements.get(i).select("div[class=css-1pznt2j]").select("span[data-testid=spnSRPProdTabShopLoc]").get(1).text());
        			
        			listHp.add(temp);
        			
        			//print data
        			System.out.println(elements.get(i).select("div[data-testid=spnSRPProdName]").text());
            		System.out.println(elements.get(i).select("div[data-testid=spnSRPProdPrice]").text());
            		System.out.println(elements.get(i).select("div[data-testid=imgSRPProdMain]").select("img").attr("src"));
            		System.out.println(elements.get(i).select("div[class=css-1ehqh5q]").select("a").attr("href"));
            		System.out.println(elements.get(i).select("span[class=css-etd83i]").text().equals("") ? "doesnt have rating":elements.get(i).select("span[class=css-etd83i]").text());
            		System.out.println(elements.get(i).select("div[class=css-1pznt2j]").select("span[data-testid=spnSRPProdTabShopLoc]").get(1).text());
            		
            		System.out.println();
            		count++;
            		if(count>100) {
            			reach_100=true;
            			break;
            		}
        		}
        		
        		if(reach_100)break;
        	}
        	
        	for (int i=0;i<listHp.size();i++) {
        		String newUrl = new URI(listHp.get(i).getUrlProduct().substring(listHp.get(i).getUrlProduct().indexOf("www.tokopedia.com"))).getPath();
        		listHp.get(i).setUrlProduct(newUrl);
        		
        		System.out.println("new url : "+ newUrl);
        		Document tempDoc = Jsoup.connect("https://"+newUrl)
        				.maxBodySize(0)
        				.followRedirects(true)
        				.timeout(0)
        				.get();
        		
        		System.out.println(tempDoc.select("div[data-testid=lblPDPDescriptionProduk]").text());
        		
        		listHp.get(i).setDesc(tempDoc.select("div[data-testid=lblPDPDescriptionProduk]").text());
        		
        		csv.writeNext(listHp.get(i).getArrayObject());
        	}
        	
        	csv.flush();
        	
        	System.out.println("===Finish after "+ (System.currentTimeMillis() - start) +" millis ===");
        	
        }catch (Exception e) {
			e.printStackTrace();
		}
        
    }
}
