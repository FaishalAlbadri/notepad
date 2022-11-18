package com.faishalbadri.notepad.ui.dialogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.ui.HomeActivity;
import com.faishalbadri.notepad.ui.NotesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummarizationDialogFragment extends DialogFragment {

    @BindView(R.id.btn_add)
    TextView btnAdd;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.edt_text)
    EditText edtText;

    public SummarizationDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summarization_dialog, container, false);
        ButterKnife.bind(this, view);

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
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 24,24,24,24);
            getDialog().getWindow().setBackgroundDrawable(inset);        }
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
        String text = edtText.getText().toString();
        String[] strList = text.split(" ");

        if (strList.length > 5) {
            ((HomeActivity) getActivity()).textSummarization(text);
            edtText.setText("");
            edtText.clearFocus();
            dismiss();
        } else {
            Toast.makeText(getActivity(), "Text Kurang Panjang", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_close)
    public void onClickClose() {
        edtText.setText("");
        edtText.clearFocus();
        dismiss();
    }

}