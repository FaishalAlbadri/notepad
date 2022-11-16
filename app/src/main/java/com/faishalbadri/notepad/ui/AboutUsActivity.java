package com.faishalbadri.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.faishalbadri.notepad.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.img_faishal)
    ImageView imgFaishal;

    @BindView(R.id.img_rizan)
    ImageView imgRizan;

    @BindView(R.id.img_mayla)
    ImageView imgMayla;

    @BindView(R.id.btn_back)
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(getImage("faishal"))
                .circleCrop()
                .into(imgFaishal);

        Glide.with(this)
                .load(getImage("mayla"))
                .circleCrop()
                .into(imgMayla);

        Glide.with(this)
                .load(getImage("rizan"))
                .circleCrop()
                .into(imgRizan);

    }

    @OnClick(R.id.btn_back)
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
    }
}