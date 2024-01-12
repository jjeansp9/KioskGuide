package com.avad.humancare.kiosk;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.fastfood.FastfoodMainActivity;
import com.avad.humancare.kiosk.hospital.HospitalMainMenuActivity;
import com.avad.humancare.kiosk.issue.IssuanceMainActivity;
import com.avad.humancare.kiosk.train.TrainMainActivity;
import com.avad.humancare.kiosk.util.Preference;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();

    private static final int MSG_TTS_START = 1;

    private Context mContext;
    private TextView tvNotice;
    private TextToSpeech mTts;
    private AudioManager mAudioManager;
    private Bundle mTtsBundle = new Bundle();

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.d(TAG, ">>>> Handler : " + msg.what);

            switch (msg.what) {
                case MSG_TTS_START :
                    speech(getString(R.string.main_guide));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        Intent intent = getIntent();

        mContext = this;
		/*
        String category = "";
        Intent intent = getIntent();
        if(intent != null) {
            if(intent.hasExtra("category")) {
                category = intent.getStringExtra("category");
            }
        }
        Log.e("MainActivity", "onCreate() : category="+category);
        if(category.equals("fastfood")) {
            startActivity(new Intent(mContext, FastfoodMainActivity.class));
            finish();
        }
        */
        if(intent != null){
            String name = "";
            if(intent.hasExtra(Preference.PREF_ELDER_NAME)){
                name = intent.getStringExtra(Preference.PREF_ELDER_NAME);
                if(!Preference.getElderName(this).equals(name)){
                    Preference.setElderName(this, name);
                }
            }
            String sex = "";
            if(intent.hasExtra(Preference.PREF_ELDER_SEX)){
                sex = intent.getStringExtra(Preference.PREF_ELDER_SEX);
                if(!Preference.getElderSex(this).equals(sex)){
                    Preference.setElderSex(this, sex);
                }
            }
        }
        // tts
        mTtsBundle.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = mTts.setLanguage(Locale.KOREAN);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(mContext, "TTS Language is not supported", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mContext, "TTS init error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initViews();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mHandler.sendEmptyMessageDelayed(MSG_TTS_START, 1000);
    }
    private void initViews()
    {
        // init view
        findViewById(R.id.card_fastfood).setOnClickListener(this);
        findViewById(R.id.card_hospital).setOnClickListener(this);
        findViewById(R.id.card_train).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        findViewById(R.id.card_issue).setOnClickListener(this);
        tvNotice = findViewById(R.id.tv_notice);
        setNoticeText();
    }
    private void removeListeners(){
        findViewById(R.id.card_fastfood).setOnClickListener(null);
        findViewById(R.id.card_hospital).setOnClickListener(null);
        findViewById(R.id.card_train).setOnClickListener(null);
        findViewById(R.id.btn_finish).setOnClickListener(null);
        findViewById(R.id.card_issue).setOnClickListener(null);
    }
    private void setNoticeText(){
        Log.e(TAG, "setNoticeText");
        if(tvNotice == null) return;
        Log.e(TAG, "setNoticeText not null");
        int index = getString(R.string.main_guide).indexOf("세로");
        Spannable span = (Spannable) tvNotice.getText();
        span.setSpan(new ForegroundColorSpan(Color.RED), index, index + 2, 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        ttsDestory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
            mTts = null;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
//        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.card_fastfood:
                intent = new Intent(mContext, FastfoodMainActivity.class);
                startActivity(intent);
                break;

            case R.id.card_train:
                intent = new Intent(mContext, TrainMainActivity.class);
                startActivity(intent);
                break;

            case R.id.card_hospital:
                intent = new Intent(mContext, HospitalMainMenuActivity.class);
                startActivity(intent);
                break;

            case R.id.card_issue:
                intent = new Intent(mContext, IssuanceMainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_finish:
                finish();
        }
    }

    private void speech(String str) {
        Preference.setMediaVol(mContext, mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        int maxVol = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVol, 0);

        //mTts.setPitch(1.0f);    // 높낮이
        mTts.setSpeechRate(0.9f);

//        mTts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
//        mTts.speak(str, TextToSpeech.QUEUE_ADD, null);
//        mTts.speak(str, TextToSpeech.QUEUE_ADD, mTtsBundle, "tts_unique_id");
        mTts.speak(str, TextToSpeech.QUEUE_ADD, null, null);
    }

    private void ttsDestory() {
        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Preference.getMediaVol(mContext), 0);
        }

        if (mTts != null) {
            mTts.stop();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        removeListeners();
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged");
        setContentView(R.layout.activity_main);
        initViews();

    }
}