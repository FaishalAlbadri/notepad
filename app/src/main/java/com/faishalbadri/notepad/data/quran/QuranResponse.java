package com.faishalbadri.notepad.data.quran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuranResponse{

	@SerializedName("quran")
	private List<QuranItem> quran;

	@SerializedName("status")
	private String status;

	public void setQuran(List<QuranItem> quran){
		this.quran = quran;
	}

	public List<QuranItem> getQuran(){
		return quran;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"QuranResponse{" + 
			"quran = '" + quran + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}