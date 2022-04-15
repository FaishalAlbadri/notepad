package com.faishalbadri.notepad.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faishalbadri.notepad.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_more)
    ImageView btnMore;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.edt_desc)
    EditText edtDesc;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
    }

    @OnClick(R.id.btn_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_more)
    public void onClickMore() {
    }
}