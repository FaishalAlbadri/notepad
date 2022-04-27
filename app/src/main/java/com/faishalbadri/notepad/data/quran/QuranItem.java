package com.faishalbadri.notepad.data.quran;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class QuranItem {

	@SerializedName("ayat")
	private String ayat;

	@SerializedName("surat")
	private String surat;

	@SerializedName("id")
	private String id;

	@SerializedName("isi")
	private String isi;

	public void setAyat(String ayat){
		this.ayat = ayat;
	}

	public String getAyat(){
		return ayat;
	}

	public void setSurat(String surat){
		this.surat = surat;
	}

	public String getSurat(){
		return surat;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	@Override
 	public String toString(){
		return 
			"QuranItem{" + 
			"ayat = '" + ayat + '\'' + 
			",surat = '" + surat + '\'' + 
			",id = '" + id + '\'' + 
			",isi = '" + isi + '\'' + 
			"}";
		}
}