package com.faishalbadri.notepad.data.alquran.lokal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AlquranResponse{

	@SerializedName("alquran")
	private List<AlquranItem> alquran;

	public void setAlquran(List<AlquranItem> alquran){
		this.alquran = alquran;
	}

	public List<AlquranItem> getAlquran(){
		return alquran;
	}

	@Override
 	public String toString(){
		return 
			"AlquranResponse{" + 
			"alquran = '" + alquran + '\'' + 
			"}";
		}
}