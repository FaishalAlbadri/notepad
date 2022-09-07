package com.faishalbadri.notepad.ui.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;
import com.faishalbadri.notepad.di.AlquranLocalRepositoryInject;
import com.faishalbadri.notepad.presenter.alquran.local.AlquranLocalContract;
import com.faishalbadri.notepad.presenter.alquran.local.AlquranLocalPresenter;
import com.faishalbadri.notepad.ui.NotesActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreNotesSearchAlquranDialogFragment extends DialogFragment implements AlquranLocalContract.alquranLocalView {

    @BindView(R.id.edt_surah)
    AppCompatAutoCompleteTextView edtSurah;
    @BindView(R.id.edt_ayat)
    EditText edtAyat;
    @BindView(R.id.txt_preview_value)
    TextView txtPreviewValue;
    @BindView(R.id.btn_add)
    TextView btnAdd;
    @BindView(R.id.btn_close)
    ImageView btnClose;

    private String ayat = "";
    private String surat = "";
    private String verse = "";

    private long delay = 200;
    private long last_text_edit = 0;
    private Handler handler = new Handler();
    private AlquranLocalPresenter alquranLocalPresenter;


    public MoreNotesSearchAlquranDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_note_search_alquran_dialog, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        alquranLocalPresenter = new AlquranLocalPresenter(AlquranLocalRepositoryInject.provideTo(RoomClient.getInstance(getActivity())));
        alquranLocalPresenter.onAttachView(this);

        alquranLocalPresenter.getSurat();
        onChangedText();

        return view;
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                alquranLocalPresenter.getAyat(edtSurah.getText().toString(), Integer.parseInt(edtAyat.getText().toString()));
            }
        }
    };

    private void onChangedText() {
        edtSurah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSurah.getText().toString().isEmpty() && !edtAyat.getText().toString().isEmpty()) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        });

        edtAyat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSurah.getText().toString().isEmpty() && !edtAyat.getText().toString().isEmpty()) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @OnClick(R.id.btn_add)
    public void onClickBtnAdd() {
        if (!ayat.isEmpty() || !ayat.equals("")) {
            ((NotesActivity) getActivity()).addAlquran("\"" + ayat + "\"" + " (" + surat + ": " + verse + ")");
            dismiss();
        } else {
            Toast.makeText(getActivity(), "Ayat Masih Kosong", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_close)
    public void onClickClose() {
        dismiss();
    }

    @Override
    public void oSuccessAlquranSurat(List<DataAlquranSurat> dataAlquranSurats) {
        ArrayList<String> listsurat = new ArrayList<>();
        for (int i = 0; i < dataAlquranSurats.size(); i++) {
            String name = dataAlquranSurats.get(i).getName();
            listsurat.add(name);
        }
        edtSurah.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, listsurat));
    }

    @Override
    public void oSuccessAlquranAyat(List<DataAlquranAyat> dataAlquranAyats) {
        if (dataAlquranAyats.size() > 0) {
            ayat = dataAlquranAyats.get(0).getAyat_indo();
            surat = edtSurah.getText().toString();
            verse = dataAlquranAyats.get(0).getVerse();
            txtPreviewValue.setText(ayat);
        } else {
            ayat = "";
            txtPreviewValue.setText(ayat);
        }
    }
}