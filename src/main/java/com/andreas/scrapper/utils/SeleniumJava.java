package com.andreas.scrapper.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumJava {

	static WebDriver driver;
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
		
		driver = new ChromeDriver();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("http://demo.guru99.com/test/guru99home/scrolling.html");
		
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

}
