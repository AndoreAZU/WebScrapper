package com.andreas.scrapper.utils;

public class TokopediaHandphone {
	String id;
	String price;
	String productName;
	String rating;
	String imgUrl;
	String desc;
	String store;
	String urlProduct;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrlProduct() {
		return urlProduct;
	}
	public void setUrlProduct(String urlProduct) {
		this.urlProduct = urlProduct;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	
	public String[] getArrayObject() {
		String[] temp = new String[8];
		temp[0] = id;
		temp[1] = productName;
		temp[2] = price;
		temp[3] = rating;
		temp[4] = store;
		temp[5] = urlProduct;
		temp[6] = imgUrl;
		temp[7] = desc;
		return temp;
	}
}
