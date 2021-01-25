package com.example.daab;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.Random;

public class DeadlockTesting extends AppCompatActivity {
    TextView allocationMatrix, maxMatrixGenerated, availableMemoryGenerated;
    String temporary_Matrix;
    int tpro, tres, available_memory[], allocation_Matrix[][], need_Matrix[][];
    int[][] process;
    boolean finish[], created;
    TextToSpeech textToSpeech;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadlock_testing);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        allocation_Matrix = new int[10][10];
        available_memory = new int[10];
        need_Matrix = new int[10][10];
        temporary_Matrix = "";

        allocationMatrix = (TextView)findViewById(R.id.allocation_matrix);
        process = new int[10][10];
        tpro = getIntent().getExtras().getInt("TPRO");
        tres = getIntent().getExtras().getInt("TRES");
        for (int i = 0; i <= tpro; i++) {
            process[i] = getIntent().getExtras().getIntArray("Process" + i);
            for (int j = 0; j <= tres; j++) {
                temporary_Matrix = temporary_Matrix + " " + process[i][j];
            }
            temporary_Matrix += "\n";
        }
        allocationMatrix.setText(temporary_Matrix);
    }

    public void automaticGenerate(View v)
    {
        allocation_Matrix = generateAllocationMatrix();
        available_memory = generateAvailableMemory();
        created=true;
    }

    public int[][] generateAllocationMatrix() {
        maxMatrixGenerated = (TextView) findViewById(R.id.max_matrix_generated);
        int max[][] = new int[10][10];
        temporary_Matrix = "";
        int maximum = 5;

        for (int i = 0; i <= tpro; i++) {
            for (int j = 0; j <= tres; j++) {
                Random rand = new Random();
                max[i][j] = rand.nextInt(maximum) + process[i][j];
                String m = String.valueOf(max[i][j]);
                temporary_Matrix = temporary_Matrix + " " + m;
            }
            temporary_Matrix += "\n";
        }
        maxMatrixGenerated.setText(temporary_Matrix);
        return max;
    }

    public int[] generateAvailableMemory() {
        availableMemoryGenerated = (TextView) findViewById(R.id.available_memory_generated);
        int avail[] = new int[10];
        temporary_Matrix = "";
        int maximum = 6;

        for (int i = 0; i <= tres; i++) {
            Random rand = new Random();
            avail[i] = rand.nextInt(maximum);
            String m = String.valueOf(avail[i]);
            temporary_Matrix = temporary_Matrix + " " + m;
        }
        availableMemoryGenerated.setText(temporary_Matrix);
        return avail;
    }

    public void isSafe(View v) {
        if(created){
            String sequence = "";

            for (int i = 0; i <=tpro; i++) {
                for (int j = 0; j <= tres; j++) {
                    need_Matrix[i][j] = allocation_Matrix[i][j] - process[i][j];
                }
            }
            finish= new boolean[tpro+1];

            for (int i = 0; i <= tpro; i++) {
                finish[i] = false;
            }

            boolean check = true;
            while (check) {
                check = false;
                for (int i = 0; i <=tpro; i++) {
                    if (!finish[i]) {
                        int j;
                        for (j = 0; j <= tres; j++) {
                            if (need_Matrix[i][j] > available_memory[j]) {
                                break;
                            }
                        }

                        if (j == tres+1) {
                            for (j = 0; j <=tres; j++) {
                                available_memory[j] = available_memory[j] + process[i][j];

                            }
                            sequence += "P"+ i + " ";
                            finish[i] = true;
                            check = true;
                        }
                    }
                }
            }

            int i;
            for (i = 0; i <= tpro; i++) {
                if (!finish[i])
                    break;
            }

            if (i == tpro+1) {
                textToSpeech.speak("Deadlock can be avoided if we follow "+sequence, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(this, sequence, Toast.LENGTH_SHORT).show();
            } else {
                textToSpeech.speak("I'm afraid it's a DEADLOCK and can't be avoided", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(this, "DEAD LOCK", Toast.LENGTH_SHORT).show();
            }
        }
        else
            textToSpeech.speak("Kindly generate the matrices first", TextToSpeech.QUEUE_FLUSH, null);
    }
}
