package com.faishalbadri.notepad.data.alquran;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.linkedin.android.spyglass.mentions.Mentionable;

import org.jetbrains.annotations.NotNull;

public class QuranItem implements Mentionable {

	@SerializedName("no_surah")
	private String noSurah;

	@SerializedName("no_ayat")
	private String noAyat;

	@SerializedName("text")
	private String text;

	@SerializedName("nama_surah")
	private String namaSurah;

	public void setNoSurah(String noSurah){
		this.noSurah = noSurah;
	}

	public String getNoSurah(){
		return noSurah;
	}

	public void setNoAyat(String noAyat){
		this.noAyat = noAyat;
	}

	public String getNoAyat(){
		return noAyat;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setNamaSurah(String namaSurah){
		this.namaSurah = namaSurah;
	}

	public String getNamaSurah(){
		return namaSurah;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"no_surah = '" + noSurah + '\'' + 
			",no_ayat = '" + noAyat + '\'' + 
			",text = '" + text + '\'' + 
			",nama_surah = '" + namaSurah + '\'' + 
			"}";
		}
	@NonNull
	@NotNull
	@Override
	public String getTextForDisplayMode(@NonNull @NotNull MentionDisplayMode mode) {
		switch (mode) {
			case FULL:
				String t = "\"" + getText() + "\"" + " (" + getNamaSurah() + ": " + getNoAyat() + ")";
				return t;
			case PARTIAL:
				String[] words = getText().split(" ");
				return (words.length > 1) ? words[0] : "";
			case NONE:
			default:
				return "";
		}
	}

	@NonNull
	@NotNull
	@Override
	public MentionDeleteStyle getDeleteStyle() {
		return MentionDeleteStyle.PARTIAL_NAME_DELETE;
	}

	@Override
	public int getSuggestibleId() {
		return getText().hashCode();
	}

	@NonNull
	@NotNull
	@Override
	public String getSuggestiblePrimaryText() {
		return getText();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(text);
		parcel.writeString(noAyat);
		parcel.writeString(namaSurah);
		parcel.writeString(noSurah);
	}

	public QuranItem(Parcel in) {
		text = in.readString();
		noAyat = in.readString();
		namaSurah = in.readString();
		noSurah = in.readString();
	}

	public static final Parcelable.Creator<QuranItem> CREATOR
			= new Parcelable.Creator<QuranItem>() {
		public QuranItem createFromParcel(Parcel in) {
			return new QuranItem(in);
		}

		public QuranItem[] newArray(int size) {
			return new QuranItem[size];
		}
	};
}