package com.faishalbadri.notepad.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.di.NotesRepositoryInject;
import com.faishalbadri.notepad.presenter.notes.NotesContract;
import com.faishalbadri.notepad.presenter.notes.NotesPresenter;
import com.faishalbadri.notepad.ui.dialogfragment.MoreNotesDialogFragment;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity implements NotesContract.notesView {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_more)
    ImageView btnMore;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.edt_title)
    EditText edtTitle;

    private int id;
    private int pinned = 0;

    private NotesPresenter notesPresenter;
    private SimpleDateFormat formatter;

    private long delay = 300;
    private long last_text_edit = 0;
    private Handler handler = new Handler();

    private MoreNotesDialogFragment moreNotesDialogFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);

        setView();
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                notesPresenter.updateNotes(id, edtTitle.getText().toString(), edtDesc.getText().toString());
            }
        }
    };

    private void setView() {
        formatter = new SimpleDateFormat("E dd/MM/yyyy HH:mm");
        moreNotesDialogFragment = new MoreNotesDialogFragment();
        fragmentManager = getSupportFragmentManager();
        notesPresenter =
                new NotesPresenter(
                        NotesRepositoryInject.provideTo(RoomClient.getInstance(this)));
        notesPresenter.onAttachView(this);
        notesPresenter.getNotes(id);

        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saving, 0, 0, 0);
                txtStatus.setTextColor(getResources().getColor(R.color.gray_aaa));
                txtStatus.setText("Saving...");
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                last_text_edit = System.currentTimeMillis();
                handler.postDelayed(input_finish_checker, delay);
            }
        });

        edtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
                txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saving, 0, 0, 0);
                txtStatus.setTextColor(getResources().getColor(R.color.gray_aaa));
                txtStatus.setText("Saving...");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                last_text_edit = System.currentTimeMillis();
                handler.postDelayed(input_finish_checker, delay);
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_more)
    public void onClickMore() {
        moreNotesDialogFragment.show(fragmentManager, "");
    }

    @Override
    public void onSuccessGetNotes(List<DataNotes> dataNotes) {
        DataNotes data = dataNotes.get(0);
        edtTitle.setText(data.getNotes_title());
        edtTitle.clearFocus();
        edtDesc.setText(data.getNotes_desc());
        edtDesc.clearFocus();
        txtDate.setText(formatter.format(data.getDates()));
        pinned = data.getPinned();
    }

    @Override
    public void onSuccessDeleteNotes() {
        super.onBackPressed();
    }

    @Override
    public void onSuccessUpdateNotes() {
        txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved, 0, 0, 0);
        txtStatus.setTextColor(getResources().getColor(R.color.primary_dark));
        txtStatus.setText("Saved");
    }

    @Override
    public void onSuccessPinNotes() {
        setPinned(1);
    }

    @Override
    public void onSuccessUnpinNotes() {
        setPinned(0);
    }

    public void pinNotes() {
        notesPresenter.pinNotes(id);
    }

    public void unpinNotes() {
        notesPresenter.unpinNotes(id);
    }

    public void deleteNotes() {
        notesPresenter.deleteNotes(id);
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    @Override
    public void onBackPressed() {
        if (txtStatus.getText().equals("Saved")) {
            if (edtTitle.getText().toString().isEmpty() && edtDesc.getText().toString().isEmpty() || edtDesc.getText().toString().isEmpty()) {
                notesPresenter.deleteNotes(id);
            } else {
                super.onBackPressed();
            }
        } else {
            Toast.makeText(this, "Wait until note is saved", Toast.LENGTH_SHORT).show();
        }
    }
}