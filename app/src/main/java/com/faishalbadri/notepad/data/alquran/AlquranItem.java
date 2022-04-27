package com.faishalbadri.notepad.data.alquran;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.linkedin.android.spyglass.mentions.Mentionable;

import org.jetbrains.annotations.NotNull;

public class AlquranItem implements Mentionable {

    @SerializedName("ayat_indo")
    private String ayatIndo;

    @SerializedName("id")
    private String id;

    @SerializedName("verse")
    private String verse;

    @SerializedName("ayat_latin")
    private String ayatLatin;

    @SerializedName("surah_name")
    private String surahName;

    @SerializedName("ayat_arabic")
    private String ayatArabic;

    public void setAyatIndo(String ayatIndo) {
        this.ayatIndo = ayatIndo;
    }

    public String getAyatIndo() {
        return ayatIndo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getVerse() {
        return verse;
    }

    public void setAyatLatin(String ayatLatin) {
        this.ayatLatin = ayatLatin;
    }

    public String getAyatLatin() {
        return ayatLatin;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setAyatArabic(String ayatArabic) {
        this.ayatArabic = ayatArabic;
    }

    public String getAyatArabic() {
        return ayatArabic;
    }

    @Override
    public String toString() {
        return
                "AlquranItem{" +
                        "ayat_indo = '" + ayatIndo + '\'' +
                        ",id = '" + id + '\'' +
                        ",verse = '" + verse + '\'' +
                        ",ayat_latin = '" + ayatLatin + '\'' +
                        ",surah_name = '" + surahName + '\'' +
                        ",ayat_arabic = '" + ayatArabic + '\'' +
                        "}";
    }

    @NonNull
    @NotNull
    @Override
    public String getTextForDisplayMode(@NonNull @NotNull MentionDisplayMode mode) {
        switch (mode) {
            case FULL:
                String t = getAyatIndo() + " (" + getSurahName() + ": " + getVerse() + ")";
                return t;
            case PARTIAL:
                String[] words = getAyatIndo().split(" ");
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
        return getAyatIndo().hashCode();
    }

    @NonNull
    @NotNull
    @Override
    public String getSuggestiblePrimaryText() {
        return getAyatIndo();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ayatIndo);
        parcel.writeString(id);
        parcel.writeString(verse);
        parcel.writeString(ayatLatin);
        parcel.writeString(surahName);
        parcel.writeString(ayatArabic);
    }

    public AlquranItem(Parcel in) {
        ayatIndo = in.readString();
        id = in.readString();
        verse = in.readString();
        ayatLatin = in.readString();
        surahName = in.readString();
        ayatArabic = in.readString();
    }

    public static final Parcelable.Creator<AlquranItem> CREATOR
            = new Parcelable.Creator<AlquranItem>() {
        public AlquranItem createFromParcel(Parcel in) {
            return new AlquranItem(in);
        }

        public AlquranItem[] newArray(int size) {
            return new AlquranItem[size];
        }
    };
}