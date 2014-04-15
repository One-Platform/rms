package com.sinosoft.one.rms;

import java.util.List;

import org.springframework.util.Assert;


public class Menu {
	
	private  String url;
	
	private  String id;
	
	private  String name;
	
	private List<Menu> children;
	
	public Menu(){
		
	}
	
	public Menu(final String id,final String url,final String name){
		Assert.hasText(id);
		Assert.hasText(name);
		this.url=url;
		this.id=id;
		this.name=name;
	}

	public void setChildren(final List<Menu> children) {
		if(this.children==null)
			this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setUrl(String url) {
		if(this.url==null){
			this.url = url;
		}
	}

	public void setId(String id) {
		if(this.id ==null){
			this.id = id;
		}
	}

	public void setName(String name) {
		if(this.name ==null){
			this.name = name;
		}
	}
	
	
}
