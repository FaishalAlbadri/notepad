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
import com.faishalbadri.notepad.data.quran.QuranItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {

    private Context context;
    private List<QuranItem> listdata;

    public QuranAdapter(Context context, List<QuranItem> listdata) {
        this.context = context;
        this.listdata = listdata;
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
        final QuranItem quranItem = listdata.get(position);
        holder.txtSuratAyat.setText(quranItem.getSurat() + ": " + quranItem.getAyat());
        holder.txtIsiAyat.setText(quranItem.getIsi());
        
        holder.layout.setOnClickListener(view -> {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
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
