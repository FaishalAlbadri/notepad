package com.faishalbadri.notepad.data.alquran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuranResponse{

	@SerializedName("result")
	private List<QuranItem> result;

	public void setResult(List<QuranItem> result){
		this.result = result;
	}

	public List<QuranItem> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"QuranResponse{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}