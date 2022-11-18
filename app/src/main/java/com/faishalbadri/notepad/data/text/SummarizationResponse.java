package com.faishalbadri.notepad.data.text;

import java.util.List;

public class SummarizationResponse{
	private List<String> result;

	public void setResult(List<String> result){
		this.result = result;
	}

	public List<String> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"SummarizationResponse{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}