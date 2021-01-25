package com.example.daab;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak("Welcome to Deadlock Avoidance Application. Please enter the number of processes!", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(this) {
                    Intent intent = new Intent(MainActivity.this,WelcomeScreenLoader.class);
                    startActivity(intent);
                }
            }
        }).start();

    }}
