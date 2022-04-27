package com.faishalbadri.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.data.alquran.AlquranItem;
import com.faishalbadri.notepad.ui.NotesActivity;
import com.linkedin.android.spyglass.suggestions.interfaces.Suggestible;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {

    private Context context;
    private List<? extends Suggestible> suggestions;

    public QuranAdapter(Context context, List<? extends Suggestible> suggestions) {
        this.context = context;
        this.suggestions = suggestions;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_quran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Suggestible suggestion = suggestions.get(position);
        if (!(suggestion instanceof AlquranItem)) {
            return;
        }
        final AlquranItem quranItem = (AlquranItem) suggestion;
        holder.txtSuratAyat.setText(quranItem.getSurahName() + ": " + quranItem.getVerse());
        holder.txtIsiAyat.setText(quranItem.getAyatIndo());
        
        holder.layout.setOnClickListener(view -> {
            ((NotesActivity) context).onClickItemAutoComplete(quranItem);
        });
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_surat_ayat)
        TextView txtSuratAyat;
        @BindView(R.id.txt_isi_ayat)
        TextView txtIsiAyat;
        @BindView(R.id.layout)
        LinearLayout layout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
