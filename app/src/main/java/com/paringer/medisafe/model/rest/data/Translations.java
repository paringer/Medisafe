package com.paringer.medisafe.model.rest.data;

import com.google.gson.annotations.SerializedName;

public class Translations{

	@SerializedName("br")
	private String br;

	@SerializedName("de")
	private String de;

	@SerializedName("pt")
	private String pt;

	@SerializedName("ja")
	private String ja;

	@SerializedName("hr")
	private String hr;

	@SerializedName("it")
	private String it;

	@SerializedName("fa")
	private String fa;

	@SerializedName("fr")
	private String fr;

	@SerializedName("es")
	private String es;

	@SerializedName("nl")
	private String nl;

	public void setBr(String br){
		this.br = br;
	}

	public String getBr(){
		return br;
	}

	public void setDe(String de){
		this.de = de;
	}

	public String getDe(){
		return de;
	}

	public void setPt(String pt){
		this.pt = pt;
	}

	public String getPt(){
		return pt;
	}

	public void setJa(String ja){
		this.ja = ja;
	}

	public String getJa(){
		return ja;
	}

	public void setHr(String hr){
		this.hr = hr;
	}

	public String getHr(){
		return hr;
	}

	public void setIt(String it){
		this.it = it;
	}

	public String getIt(){
		return it;
	}

	public void setFa(String fa){
		this.fa = fa;
	}

	public String getFa(){
		return fa;
	}

	public void setFr(String fr){
		this.fr = fr;
	}

	public String getFr(){
		return fr;
	}

	public void setEs(String es){
		this.es = es;
	}

	public String getEs(){
		return es;
	}

	public void setNl(String nl){
		this.nl = nl;
	}

	public String getNl(){
		return nl;
	}

	@Override
 	public String toString(){
		return 
			"Translations{" + 
			"br = '" + br + '\'' + 
			",de = '" + de + '\'' + 
			",pt = '" + pt + '\'' + 
			",ja = '" + ja + '\'' + 
			",hr = '" + hr + '\'' + 
			",it = '" + it + '\'' + 
			",fa = '" + fa + '\'' + 
			",fr = '" + fr + '\'' + 
			",es = '" + es + '\'' + 
			",nl = '" + nl + '\'' + 
			"}";
		}
}