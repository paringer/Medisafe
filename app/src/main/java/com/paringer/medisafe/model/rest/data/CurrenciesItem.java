package com.paringer.medisafe.model.rest.data;

import com.google.gson.annotations.SerializedName;

public class CurrenciesItem{

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	public String getSymbol(){
		return symbol;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"CurrenciesItem{" + 
			"symbol = '" + symbol + '\'' + 
			",code = '" + code + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}