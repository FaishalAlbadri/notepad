package com.faishalbadri.notepad.data.alquran.lokal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AlquranItem{

	@SerializedName("ayat")
	private List<AyatItem> ayat;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setAyat(List<AyatItem> ayat){
		this.ayat = ayat;
	}

	public List<AyatItem> getAyat(){
		return ayat;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"AlquranItem{" + 
			"ayat = '" + ayat + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}