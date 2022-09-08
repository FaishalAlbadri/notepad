package com.faishalbadri.notepad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentCenter;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentLeft;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentRight;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Bold;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_FontColor;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_FontSize;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListBullet;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListNumber;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Underline;
import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.adapter.QuranAdapter;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.data.alquran.QuranItem;
import com.faishalbadri.notepad.di.AlquranRepositoryInject;
import com.faishalbadri.notepad.di.NotesRepositoryInject;
import com.faishalbadri.notepad.presenter.alquran.AlquranContract;
import com.faishalbadri.notepad.presenter.alquran.AlquranPresenter;
import com.faishalbadri.notepad.presenter.notes.NotesContract;
import com.faishalbadri.notepad.presenter.notes.NotesPresenter;
import com.faishalbadri.notepad.ui.dialogfragment.MoreNotesDialogFragment;
import android.print.PdfConverter;

import com.faishalbadri.notepad.ui.dialogfragment.MoreNotesSearchAlquranDialogFragment;
import com.linkedin.android.spyglass.suggestions.SuggestionsResult;
import com.linkedin.android.spyglass.suggestions.interfaces.Suggestible;
import com.linkedin.android.spyglass.suggestions.interfaces.SuggestionsResultListener;
import com.linkedin.android.spyglass.suggestions.interfaces.SuggestionsVisibilityManager;
import com.linkedin.android.spyglass.tokenization.QueryToken;
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizer;
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizerConfig;
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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
    AREditText edtDesc;
    @BindView(R.id.rv_autocomplete)
    RecyclerView rvAutocomplete;
    @BindView(R.id.img_loading)
    ImageView imgLoading;
    @BindView(R.id.txt_keyword)
    TextView txtKeyword;
    @BindView(R.id.areToolbar)
    ARE_ToolbarDefault areToolbar;

    private int id;
    private static final String BUCKET = "autocomplete-network";
    private int pinned = 0;

    private NotesPresenter notesPresenter;
    private AlquranPresenter alquranPresenter;

    private QuranAdapter alquranAdapter;
    private ArrayList<QuranItem> listdata;

    private SimpleDateFormat formatter;

    private long delay = 300;
    private long last_text_edit = 0;
    private Handler handler = new Handler();

    private long delay_autocomplete = 1000;
    private long last_text_edit_autocomplete = 0;
    private Handler handler_autocomplete = new Handler();

    private QueryToken key_auotcomplete;

    private MoreNotesSearchAlquranDialogFragment moreNotesSearchAlquranDialogFragment;
    private MoreNotesDialogFragment moreNotesDialogFragment;
    private FragmentManager fragmentManager;

    private static final WordTokenizerConfig tokenizerConfig = new WordTokenizerConfig
            .Builder()
            .setExplicitChars("~")
            .setMaxNumKeywords(5)
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
                    imgLoading.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                notesPresenter.updateNotes(id, edtTitle.getText().toString(), edtDesc.getHtml());
            }
        }
    };

    private void setView() {
        formatter = new SimpleDateFormat("E dd/MM/yyyy HH:mm");
        edtDesc.setTokenizer(new WordTokenizer(tokenizerConfig));
        edtDesc.setQueryTokenReceiver(this);
        edtDesc.setSuggestionsVisibilityManager(this);
        setToolbarEdit();
        moreNotesDialogFragment = new MoreNotesDialogFragment();
        moreNotesSearchAlquranDialogFragment = new MoreNotesSearchAlquranDialogFragment();
        fragmentManager = getSupportFragmentManager();
        notesPresenter =
                new NotesPresenter(
                        NotesRepositoryInject.provideTo(RoomClient.getInstance(this)));
        notesPresenter.onAttachView(this);

        alquranPresenter = new AlquranPresenter(AlquranRepositoryInject.provideTo(this));
        alquranPresenter.onAttachView(this);

        listdata = new ArrayList<>();
        alquranAdapter = new QuranAdapter(this, listdata);

        Glide.with(this)
                .load(R.raw.loading)
                .into(imgLoading);

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

    private void setToolbarEdit() {
        areToolbar.addToolbarItem(new ARE_ToolItem_Bold());
        areToolbar.addToolbarItem(new ARE_ToolItem_Italic());
        areToolbar.addToolbarItem(new ARE_ToolItem_Underline());
        areToolbar.addToolbarItem(new ARE_ToolItem_FontSize());
        areToolbar.addToolbarItem(new ARE_ToolItem_FontColor());
        areToolbar.addToolbarItem(new ARE_ToolItem_ListNumber());
        areToolbar.addToolbarItem(new ARE_ToolItem_ListBullet());
        areToolbar.addToolbarItem(new ARE_ToolItem_AlignmentLeft());
        areToolbar.addToolbarItem(new ARE_ToolItem_AlignmentCenter());
        areToolbar.addToolbarItem(new ARE_ToolItem_AlignmentRight());

        edtDesc.setToolbar(areToolbar);
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
        if (edtDesc.getSelectionStart() == 0) {
            edtDesc.setSelection(1);
        }
        moreNotesDialogFragment.show(fragmentManager, "");
    }

    @Override
    public void onSuccessGetNotes(List<DataNotes> dataNotes) {
        DataNotes data = dataNotes.get(0);
        edtTitle.setText(data.getNotes_title());
        edtTitle.clearFocus();
        edtDesc.setText("");
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

    public void addAlquran(String ayat){
            int start =edtDesc.getSelectionStart();
            edtDesc.getText().insert(start, ayat);
    }

    public void searchAlquran(){
        moreNotesDialogFragment.dismiss();
        moreNotesSearchAlquranDialogFragment.show(fragmentManager, "");
    }

    public void saveAsPDFNotes() {
        String titleText = edtTitle.getText().toString();
        String bodyText = edtDesc.getHtml();
        File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "notepad-islamic");
        if (!root.exists()) {
            root.mkdirs();
        }
        String path = root + "/" +titleText.toLowerCase().replace(" ", "-") + ".pdf";
        PdfConverter converter = PdfConverter.getInstance();
        File file = new File(path);
        converter.convert(getApplicationContext(), "<b>" + titleText + "</b><br>" +bodyText, file);
        Toast.makeText(this, "Save as PDF Successfully", Toast.LENGTH_SHORT).show();
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

    public AREditText getEdtDesc() {
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
    public void onSuccessAlquranAutoComplete(List<QuranItem> alquranItems) {
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
        String text = "~" + key_auotcomplete.getKeywords();
        if (edtDesc.getHtml().contains(text)) {
            txtKeyword.setVisibility(View.VISIBLE);
            txtKeyword.setText(Html.fromHtml(String.format(getResources().getString(R.string.text_cari_ayat), key_auotcomplete.getKeywords())));
            handler_autocomplete.postDelayed(input_finish_checker_autocomplete, delay_autocomplete);
        }
        return buckets;
    }

    @Override
    public void onReceiveSuggestionsResult(@NonNull @NotNull SuggestionsResult result, @NonNull @NotNull String bucket) {
        List<? extends Suggestible> suggestions = result.getSuggestions();
        imgLoading.setVisibility(View.GONE);
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
            txtKeyword.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isDisplayingSuggestions() {
        return rvAutocomplete.getVisibility() == RecyclerView.VISIBLE;
    }

    public void onClickItemAutoComplete(QuranItem quranItem) {
        edtDesc.insertMention(quranItem);
        rvAutocomplete.swapAdapter(new QuranAdapter(this, new ArrayList<QuranItem>()), true);
        displaySuggestions(false);
        edtDesc.requestFocus();
        notesPresenter.updateNotes(id, edtTitle.getText().toString(), edtDesc.getHtml());
        Handler handleeRefresh = new Handler();
        handleeRefresh.postDelayed(() -> notesPresenter.getNotes(id), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        areToolbar.onActivityResult(requestCode, resultCode, data);
    }
}