package com.example.logintry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class WelcomActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        mButtonSpeak = findViewById(R.id.play);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) { //enable play button
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.UK);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }

                }
            }
        });
        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });


    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f; //f for fload
        float speed = (float) mSeekBarSpeed.getProgress() / 50; //we set 50 as normal speed
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);


    }

    public void logout(View v) {
        if (mTTS.isSpeaking()) {
            mTTS.speak("let me finnish dummy", TextToSpeech.QUEUE_FLUSH, null);
            mTTS.speak(mEditText.getText().toString(), TextToSpeech.QUEUE_ADD, null);

        } else {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void moveToCamera(View view){
        startActivity(new Intent(this, CameraActivity.class));

    }

    @Override
    protected void onDestroy() {
        if(mTTS !=null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();

    }
}