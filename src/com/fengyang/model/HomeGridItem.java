package com.fengyang.model;

public class HomeGridItem {

	String text;
	int image_resouce;
	
	public HomeGridItem(String text, int image_resouce) {
		super();
		this.text = text;
		this.image_resouce = image_resouce;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImage_resouce() {
		return image_resouce;
	}

	public void setImage_resouce(int image_resouce) {
		this.image_resouce = image_resouce;
	}

}
