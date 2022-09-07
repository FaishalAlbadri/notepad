package com.faishalbadri.notepad.ui.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chinalwb.are.AREditText;
import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.ui.NotesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MoreNotesDialogFragment extends DialogFragment {

    @BindView(R.id.btn_pin)
    TextView btnPin;
    @BindView(R.id.btn_unpin)
    TextView btnUnpin;
    @BindView(R.id.btn_delete)
    TextView btnDelete;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.btn_search)
    TextView btnSearch;

    private AREditText edtDesc;

    private int pin;

    public MoreNotesDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_notes_dialog, container, false);
        ButterKnife.bind(this, view);
        pin = ((NotesActivity) getActivity()).getPinned();
        edtDesc = ((NotesActivity) getActivity()).getEdtDesc();

        Log.i("selectionstart", edtDesc.getSelectionStart() + ", " + edtDesc.getSelectionEnd());

        if (edtDesc.getSelectionStart() > 0) {
            btnSearch.setVisibility(View.VISIBLE);
        } else {
            edtDesc.setVisibility(View.GONE);
        }

        if (pin == 0) {
            btnPin.setVisibility(View.VISIBLE);
        } else {
            btnUnpin.setVisibility(View.VISIBLE);
        }
        return view;
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

    @OnClick(R.id.btn_delete)
    public void onClickDelete() {
        ((NotesActivity) getActivity()).deleteNotes();
        dismiss();
    }

    @OnClick(R.id.btn_unpin)
    public void onClickUnpin() {
        ((NotesActivity) getActivity()).unpinNotes();
        dismiss();
    }

    @OnClick(R.id.btn_pin)
    public void onClickPin() {
        ((NotesActivity) getActivity()).pinNotes();
        dismiss();
    }

    @OnClick(R.id.btn_save)
    public void onClickBtnSave() {
        ((NotesActivity) getActivity()).saveAsPDFNotes();
        dismiss();
    }

    @OnClick(R.id.btn_search)
    public void onClickBtnSearch() {
        ((NotesActivity) getActivity()).searchAlquran();
    }
}