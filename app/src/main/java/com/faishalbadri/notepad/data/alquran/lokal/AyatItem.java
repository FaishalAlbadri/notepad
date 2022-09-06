package com.faishalbadri.notepad.data.alquran.lokal;

import com.google.gson.annotations.SerializedName;

public class AyatItem{

	@SerializedName("id_surah")
	private String idSurah;

	@SerializedName("ayat_indo")
	private String ayatIndo;

	@SerializedName("id")
	private String id;

	@SerializedName("verse")
	private String verse;

	public void setIdSurah(String idSurah){
		this.idSurah = idSurah;
	}

	public String getIdSurah(){
		return idSurah;
	}

	public void setAyatIndo(String ayatIndo){
		this.ayatIndo = ayatIndo;
	}

	public String getAyatIndo(){
		return ayatIndo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setVerse(String verse){
		this.verse = verse;
	}

	public String getVerse(){
		return verse;
	}

	@Override
 	public String toString(){
		return 
			"AyatItem{" + 
			"id_surah = '" + idSurah + '\'' + 
			",ayat_indo = '" + ayatIndo + '\'' + 
			",id = '" + id + '\'' + 
			",verse = '" + verse + '\'' + 
			"}";
		}
}