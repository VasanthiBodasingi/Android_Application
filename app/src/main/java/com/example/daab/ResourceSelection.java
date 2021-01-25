package com.example.daab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResourceSelection extends AppCompatActivity {
    String number;
    int numberParsedInt, processNumber = 0, resourceNumber = -1, instancesParsedInt;
    TextView resourceSelectionText;
    static int instanceArray[][];
    Intent intent;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_selction);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);

                }
            }
        });

        number = getIntent().getExtras().getString("Number");
        numberParsedInt = Integer.parseInt(number);
        resourceSelectionText = (TextView) findViewById(R.id.resourse_selection_text);
        resourceSelectionText.setText("Select Resources for "+"P"+processNumber);
        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        MenuItem proceedMenu = menu.add(0,0,0,"Proceed");
        proceedMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        instanceArray = new int[numberParsedInt][6];
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(processNumber<numberParsedInt-1) {
            processNumber++;
            textToSpeech.speak("Enter resources for P"+processNumber, TextToSpeech.QUEUE_FLUSH, null);

            resourceNumber = -1;
            resourceSelectionText.setText("Select Resources for "+"P"+processNumber);
        }
        else
        {
            textToSpeech.speak("Thank you for your time", TextToSpeech.QUEUE_FLUSH, null);

            intent= new Intent(this,DeadlockTesting.class);
            for(int i=0;i<=processNumber;i++)
            {
                int arry[] = instanceArray[i];
                intent.putExtra("Process"+i,arry);
            }
            intent.putExtra("TPRO", processNumber);
            intent.putExtra("TRES",resourceNumber);

            startActivity(intent);
            textToSpeech.speak("Click on Automatic generate to create instancesParsedInt Available and Max Matrix and click Analyse to check", TextToSpeech.QUEUE_FLUSH, null);

        }
        return true;
    }

    public void file(View v)
    {

        switch(v.getId())
        {
            case R.id.file:
                addResourceConfirmation();
                break;
            case R.id.network:
                addResourceConfirmation();
                break;
            case R.id.hard_drive:
                addResourceConfirmation();
                break;
            case R.id.images:
                addResourceConfirmation();
                break;
            case R.id.buffer:
                addResourceConfirmation();
                break;
            case R.id.download:
                addResourceConfirmation();
                break;
        }
    }

    public void addResourceConfirmation()
    {
        AlertDialog.Builder myBox= new AlertDialog.Builder(this);

        myBox.setTitle("Add Resource");
        myBox.setMessage("Are you Sure?");
        myBox.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id)
            {
                instance();
            }});
        myBox.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id)
            {
                dialog.cancel();
            }
        });

        myBox.show();
    }

    public void instance() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Instances");
        alertDialog.setMessage("Enter number of instances");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setNeutralButton("Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String instances = input.getText().toString();
                        instancesParsedInt = Integer.parseInt(instances);
                        resourceNumber++;
                        instanceArray[processNumber][resourceNumber] = instancesParsedInt;

                        Toast.makeText(ResourceSelection.this, "Resources Added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        );
        alertDialog.show();
    }
}
