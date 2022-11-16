package com.faishalbadri.notepad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.adapter.NotesAdapter;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.di.HomeRepositoryInject;
import com.faishalbadri.notepad.presenter.home.HomeContract;
import com.faishalbadri.notepad.presenter.home.HomePresenter;
import com.faishalbadri.notepad.ui.dialogfragment.MoreHomeDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeContract.homeView {

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.rv_notes)
    RecyclerView rvNotes;
    @BindView(R.id.btn_new_note)
    FloatingActionButton btnNewNote;
    @BindView(R.id.btn_ex_fab)
    FloatingActionButton btnExFab;
    @BindView(R.id.btn_text_summarizer)
    FloatingActionButton btnTextSummarizer;
    @BindView(R.id.cv_new_note)
    CardView cvNewNote;
    @BindView(R.id.cv_text_summarizer)
    CardView cvTextSummarizer;
    @BindView(R.id.layout_blank)
    ConstraintLayout layoutBlank;

    private HomePresenter homePresenter;
    private LinearLayoutManager linearLayoutManager;

    private long delay = 300;
    private long last_text_edit = 0;
    private Handler handler = new Handler();

    private MoreHomeDialogFragment moreHomeDialogFragment;
    private FragmentManager fragmentManager;

    private int pinnedByItem = 3;
    private int idByItem;
    private DataNotes dataNotesByItem;

    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setView();
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                if (edtSearch.getText().toString().isEmpty()) {
                    homePresenter.getNotes();
                } else {
                    homePresenter.searchNotes(edtSearch.getText().toString());
                }
            }
        }
    };

    private void setView() {
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        moreHomeDialogFragment = new MoreHomeDialogFragment();
        fragmentManager = getSupportFragmentManager();

        homePresenter =
                new HomePresenter(
                        HomeRepositoryInject.provideTo(RoomClient.getInstance(this)));
        homePresenter.onAttachView(this);

        linearLayoutManager = new LinearLayoutManager(this);
        rvNotes.setLayoutManager(linearLayoutManager);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                last_text_edit = System.currentTimeMillis();
                handler.postDelayed(input_finish_checker, delay);
            }
        });
    }

    @OnClick(R.id.btn_text_summarizer)
    public void onClickTextSummarizer() {
        Toast.makeText(this, "Text Summarizer", Toast.LENGTH_SHORT).show();
        hideFAB();
    }

    @OnClick(R.id.btn_new_note)
    public void onClickNewNote() {
        homePresenter.addNotes();
        hideFAB();
    }

    @OnClick(R.id.cv_text_summarizer)
    public void onClickCVTextSummarizer() {
        Toast.makeText(this, "Text Summarizer", Toast.LENGTH_SHORT).show();
        hideFAB();
    }

    @OnClick(R.id.cv_new_note)
    public void onClickCVNewNote() {
        homePresenter.addNotes();
        hideFAB();
    }

    @OnClick(R.id.btn_ex_fab)
    public void onClickExFab() {
        if (isOpen) {
            hideFAB();
        }  else {
            showFAB();
        }
    }

    public void hideFAB() {
        cvNewNote.setVisibility(View.GONE);
        cvTextSummarizer.setVisibility(View.GONE);
        btnNewNote.setVisibility(View.GONE);
        btnTextSummarizer.setVisibility(View.GONE);
        cvNewNote.startAnimation(fab_close);
        cvTextSummarizer.startAnimation(fab_close);
        btnNewNote.startAnimation(fab_close);
        btnTextSummarizer.startAnimation(fab_close);
        btnExFab.startAnimation(fab_anticlock);
        isOpen = false;
    }

    public void showFAB() {
        cvNewNote.setVisibility(View.VISIBLE);
        cvTextSummarizer.setVisibility(View.VISIBLE);
        btnNewNote.setVisibility(View.VISIBLE);
        btnTextSummarizer.setVisibility(View.VISIBLE);
        cvNewNote.startAnimation(fab_open);
        cvTextSummarizer.startAnimation(fab_open);
        btnNewNote.startAnimation(fab_open);
        btnTextSummarizer.startAnimation(fab_open);
        btnExFab.startAnimation(fab_clock);
        isOpen = true;
    }

    @Override
    public void onSuccessGetNotes(List<DataNotes> dataNote) {
        if (dataNote.size() > 0) {
            NotesAdapter notesAdapter = new NotesAdapter(dataNote, this);
            rvNotes.setAdapter(notesAdapter);
            layoutBlank.setVisibility(View.GONE);
            rvNotes.setVisibility(View.VISIBLE);
        } else {
            layoutBlank.setVisibility(View.VISIBLE);
            rvNotes.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessDeleteNotes() {
        homePresenter.getNotes();
    }

    @Override
    public void onSuccessPinNotes() {
        homePresenter.getNotes();
    }

    @Override
    public void onSuccessUnpinNotes() {
        homePresenter.getNotes();
    }

    @Override
    public void onSuccessAddNotes(int id) {
        startActivity(new Intent(getApplicationContext(), NotesActivity.class).putExtra("id", id));
    }

    public void pinNotes() {
        homePresenter.pinNotes(idByItem);
    }

    public void unpinNotes() {
        homePresenter.unpinNotes(idByItem);
    }

    public void deleteNotes() {
        homePresenter.deleteNotes(dataNotesByItem);
    }

    public void setPinnedByItem(int pinnedByItem, int idByItem, DataNotes dataNotes) {
        this.pinnedByItem = pinnedByItem;
        this.idByItem = idByItem;
        dataNotesByItem = dataNotes;
        moreHomeDialogFragment.show(fragmentManager, "");
    }

    public int getPinnedByItem() {
        return pinnedByItem;
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtSearch.setText("");
        edtSearch.clearFocus();
        homePresenter.getNotes();
    }
}