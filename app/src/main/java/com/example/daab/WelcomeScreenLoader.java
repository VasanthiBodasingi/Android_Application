package com.example.daab;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class WelcomeScreenLoader extends AppCompatActivity {
    Intent intent;
    EditText numberOfProcessesInput;
    TextToSpeech textToSpeech;
    Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen_loader);
        numberOfProcessesInput = (EditText)findViewById(R.id.number_of_process_edittext);
        proceedButton = (Button)findViewById(R.id.proceed_button);
        if(!numberOfProcessesInput.getText().toString().isEmpty())
        {
            proceedButton.setEnabled(true);
        }
    }

    public void onClickProceed(View v)
    {
        numberOfProcessesInput = (EditText)findViewById(R.id.number_of_process_edittext);
        intent = new Intent(this,ResourceSelection.class);
        intent.putExtra("Number",numberOfProcessesInput.getText().toString());
        startActivity(intent);
        textToSpeech.speak("Please insert the Resources and its instances for Allocation Matrix. Let us start with P0", TextToSpeech.QUEUE_FLUSH, null);

    }
}
