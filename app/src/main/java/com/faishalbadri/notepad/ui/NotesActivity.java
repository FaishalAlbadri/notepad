package com.faishalbadri.notepad.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.adapter.QuranAdapter;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.data.alquran.AlquranItem;
import com.faishalbadri.notepad.di.AlquranRepositoryInject;
import com.faishalbadri.notepad.di.NotesRepositoryInject;
import com.faishalbadri.notepad.presenter.alquran.AlquranContract;
import com.faishalbadri.notepad.presenter.alquran.AlquranPresenter;
import com.faishalbadri.notepad.presenter.notes.NotesContract;
import com.faishalbadri.notepad.presenter.notes.NotesPresenter;
import com.faishalbadri.notepad.ui.dialogfragment.MoreNotesDialogFragment;
import com.faishalbadri.notepad.util.knife.KnifeText;
import com.linkedin.android.spyglass.suggestions.SuggestionsResult;
import com.linkedin.android.spyglass.suggestions.interfaces.Suggestible;
import com.linkedin.android.spyglass.suggestions.interfaces.SuggestionsResultListener;
import com.linkedin.android.spyglass.suggestions.interfaces.SuggestionsVisibilityManager;
import com.linkedin.android.spyglass.tokenization.QueryToken;
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizer;
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizerConfig;
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity implements NotesContract.notesView, AlquranContract.alquranView, QueryTokenReceiver, SuggestionsResultListener, SuggestionsVisibilityManager {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_more)
    ImageView btnMore;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_desc)
    KnifeText edtDesc;
    @BindView(R.id.rv_autocomplete)
    RecyclerView rvAutocomplete;

    private int id;
    private static final String BUCKET = "autocomplete-network";
    private int pinned = 0;

    private NotesPresenter notesPresenter;
    private AlquranPresenter alquranPresenter;

    private QuranAdapter alquranAdapter;
    private ArrayList<AlquranItem> listdata;

    private SimpleDateFormat formatter;

    private long delay = 300;
    private long last_text_edit = 0;
    private Handler handler = new Handler();

    private long delay_autocomplete = 1000;
    private long last_text_edit_autocomplete = 0;
    private Handler handler_autocomplete = new Handler();

    private QueryToken key_auotcomplete;

    private MoreNotesDialogFragment moreNotesDialogFragment;
    private FragmentManager fragmentManager;

    private static final WordTokenizerConfig tokenizerConfig = new WordTokenizerConfig
            .Builder()
            .setExplicitChars("~")
            .setMaxNumKeywords(3)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);

        setView();
    }

    private Runnable input_finish_checker_autocomplete = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit_autocomplete + delay_autocomplete - 500)) {
                if (!key_auotcomplete.getKeywords().isEmpty()) {
                    alquranPresenter.getAlquranAutoComplete(key_auotcomplete.getKeywords());
                }
            }
        }
    };

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                notesPresenter.updateNotes(id, edtTitle.getText().toString(), edtDesc.toHtml());
            }
        }
    };

    private void setView() {
        formatter = new SimpleDateFormat("E dd/MM/yyyy HH:mm");
        edtDesc.setTokenizer(new WordTokenizer(tokenizerConfig));
        edtDesc.setQueryTokenReceiver(this);
        edtDesc.setSuggestionsVisibilityManager(this);
        moreNotesDialogFragment = new MoreNotesDialogFragment();
        fragmentManager = getSupportFragmentManager();
        notesPresenter =
                new NotesPresenter(
                        NotesRepositoryInject.provideTo(RoomClient.getInstance(this)));
        notesPresenter.onAttachView(this);

        alquranPresenter = new AlquranPresenter(AlquranRepositoryInject.provideTo(this));
        alquranPresenter.onAttachView(this);

        listdata = new ArrayList<>();
        alquranAdapter = new QuranAdapter(this, listdata);

        rvAutocomplete.setLayoutManager(new LinearLayoutManager(NotesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvAutocomplete.setAdapter(alquranAdapter);

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
                handler_autocomplete.removeCallbacks(input_finish_checker_autocomplete);
                txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saving, 0, 0, 0);
                txtStatus.setTextColor(getResources().getColor(R.color.gray_aaa));
                txtStatus.setText("Saving...");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                runSaveText();
            }
        });
    }

    public void runSaveText() {
        last_text_edit = System.currentTimeMillis();
        handler.postDelayed(input_finish_checker, delay);
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
        edtDesc.fromHtml(data.getNotes_desc());
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

    public KnifeText getEdtDesc() {
        return edtDesc;
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

    @Override
    public void onSuccessAlquranAutoComplete(List<AlquranItem> alquranItems) {
        SuggestionsResult result = new SuggestionsResult(key_auotcomplete, (List<? extends Suggestible>) alquranItems);
        onReceiveSuggestionsResult(result, BUCKET);
    }

    @Override
    public void onErrorAlquranAutoComplete(String msg) {
        displaySuggestions(false);
    }

    @NonNull
    @NotNull
    @Override
    public List<String> onQueryReceived(@NonNull @NotNull QueryToken queryToken) {
        List<String> buckets = Collections.singletonList(BUCKET);
        key_auotcomplete = queryToken;
        last_text_edit_autocomplete = System.currentTimeMillis();
        handler_autocomplete.postDelayed(input_finish_checker_autocomplete, delay_autocomplete);
        return buckets;
    }

    @Override
    public void onReceiveSuggestionsResult(@NonNull @NotNull SuggestionsResult result, @NonNull @NotNull String bucket) {
        List<? extends Suggestible> suggestions = result.getSuggestions();
        alquranAdapter = new QuranAdapter(this, result.getSuggestions());
        rvAutocomplete.swapAdapter(alquranAdapter, true);
        boolean display = suggestions != null && suggestions.size() > 0;
        displaySuggestions(display);
    }

    @Override
    public void displaySuggestions(boolean display) {
        if (display) {
            rvAutocomplete.setVisibility(RecyclerView.VISIBLE);
        } else {
            rvAutocomplete.setVisibility(RecyclerView.GONE);
        }
    }

    @Override
    public boolean isDisplayingSuggestions() {
        return rvAutocomplete.getVisibility() == RecyclerView.VISIBLE;
    }

    public void onClickItemAutoComplete(AlquranItem quranItem) {
        edtDesc.insertMention(quranItem);
        rvAutocomplete.swapAdapter(new QuranAdapter(this, new ArrayList<AlquranItem>()), true);
        displaySuggestions(false);
        edtDesc.requestFocus();
        notesPresenter.updateNotes(id, edtTitle.getText().toString(), edtDesc.toHtml());
        Handler handleeRefresh = new Handler();
        handleeRefresh.postDelayed(() -> notesPresenter.getNotes(id), 200);
    }
}