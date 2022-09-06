package com.faishalbadri.notepad.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.ui.HomeActivity;
import com.faishalbadri.notepad.ui.NotesActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<DataNotes> listData;
    private Context context;
    private SimpleDateFormat formatter;
    private int pinned;

    public NotesAdapter(List<DataNotes> listData, Context context) {
        this.listData = listData;
        this.context = context;
        formatter = new SimpleDateFormat("E dd/MM/yyyy HH:mm");
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DataNotes dataNotes = listData.get(position);
        pinned = dataNotes.getPinned();
        if (dataNotes.getNotes_title().isEmpty()){
            holder.txtTitle.setText("Belum Ada Judul");
        } else {
            holder.txtTitle.setText(dataNotes.getNotes_title());
        }
        holder.txtDesc.setText(Html.fromHtml(dataNotes.getNotes_desc()));
        holder.txtDate.setText(formatter.format(dataNotes.getDates()));
        if (pinned == 1) {
            holder.txtPinned.setVisibility(View.VISIBLE);
        } else {
            holder.txtPinned.setVisibility(View.GONE);
        }
        holder.btnMore.setOnClickListener(view -> {
            ((HomeActivity) context).setPinnedByItem(dataNotes.getPinned(), dataNotes.getId_notes(), listData.get(holder.getAdapterPosition()));
        });
        holder.layout.setOnClickListener(view -> {
            context.startActivity(new Intent(context, NotesActivity.class).putExtra("id", dataNotes.getId_notes()));
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_pinned)
        TextView txtPinned;
        @BindView(R.id.btn_more)
        ImageView btnMore;
        @BindView(R.id.layout)
        ConstraintLayout layout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
