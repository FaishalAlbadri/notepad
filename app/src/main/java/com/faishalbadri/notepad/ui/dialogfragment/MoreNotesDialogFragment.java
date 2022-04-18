package com.faishalbadri.notepad.ui.dialogfragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.ui.NotesActivity;
import com.faishalbadri.notepad.util.knife.KnifeText;

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
    @BindView(R.id.btn_bold)
    TextView btnBold;
    @BindView(R.id.btn_italic)
    TextView btnItalic;
    @BindView(R.id.btn_undeline)
    TextView btnUndeline;

    private int pin;
    private KnifeText edtDesc;

    public MoreNotesDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_notes_dialog, container, false);
        ButterKnife.bind(this, view);
        pin = ((NotesActivity) getActivity()).getPinned();
        edtDesc = ((NotesActivity) getActivity()).getEdtDesc();

        if (pin == 0) {
            btnPin.setVisibility(View.VISIBLE);
        } else {
            btnUnpin.setVisibility(View.VISIBLE);
        }

        if (edtDesc.getSelectionStart() != edtDesc.getSelectionEnd()) {
            btnBold.setVisibility(View.VISIBLE);
            btnItalic.setVisibility(View.VISIBLE);
            btnUndeline.setVisibility(View.VISIBLE);
        } else {
            btnBold.setVisibility(View.GONE);
            btnItalic.setVisibility(View.GONE);
            btnUndeline.setVisibility(View.GONE);
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

    @OnClick(R.id.btn_undeline)
    public void onClickUnderline() {
        edtDesc.underline(!edtDesc.contains(KnifeText.FORMAT_UNDERLINED));
        ((NotesActivity) getActivity()).runSaveText();
        dismiss();
    }

    @OnClick(R.id.btn_italic)
    public void onClickItalic() {
        edtDesc.italic(!edtDesc.contains(KnifeText.FORMAT_ITALIC));
        ((NotesActivity) getActivity()).runSaveText();
        dismiss();
    }

    @OnClick(R.id.btn_bold)
    public void onClickBold() {
        edtDesc.bold(!edtDesc.contains(KnifeText.FORMAT_BOLD));
        ((NotesActivity) getActivity()).runSaveText();
        dismiss();
    }
}