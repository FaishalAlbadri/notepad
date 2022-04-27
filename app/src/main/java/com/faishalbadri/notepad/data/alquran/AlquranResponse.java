package com.faishalbadri.notepad.data.alquran;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.linkedin.android.spyglass.mentions.Mentionable;

import org.jetbrains.annotations.NotNull;

public class AlquranResponse {

	@SerializedName("msg")
	private String msg;

	@SerializedName("alquran")
	private List<AlquranItem> alquran;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

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
			"msg = '" + msg + '\'' + 
			",alquran = '" + alquran + '\'' + 
			"}";
		}
}