/*
 ************************************************************
 * Name:  Sagar Neupane                                     *
 * Project:  Project 3 Mexican Train Java/Android		    *
 * Class:  CMPS 366 OPL				                        *
 * Date:  12/8/2021				                            *
 ************************************************************
 */

package ramapo.edu.neupanemexicantrain.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.SavedState;
import ramapo.edu.neupanemexicantrain.model.Serializer;

/**
 * Menu Class
 * This activity serves as a controller for the initial menu screen for game
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class Menu extends AppCompatActivity {
    // variables and instantiate
    Button buttonPlay,buttonLoad, buttonQuit;
    Serializer serializer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate game serializer for loading game
        serializer = new Serializer(this);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonLoad = findViewById(R.id.buttonLoad);
        buttonQuit = findViewById(R.id.buttonQuit);

        // Starts a new game when clicked
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, PlayGame.class);
                startActivity(i);
            }
        });

        // Quits game when clicked
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
        // Displays list of save files that can be loaded when clicked
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);

                final String check = Environment.getDataDirectory().getAbsolutePath();

                final String relative= "/data/" + getPackageName() +"/files";

                final File[] all_files = new File(check+relative).listFiles();
                System.out.println("files:"+all_files.length);
                List<String> text_files = new ArrayList<String>();

                // Add save files stored in internal storage
                for(File onefile: all_files){
                    String file_name = onefile.getName();
                    System.out.println("Files name:" +file_name);
                    if (file_name.endsWith(".txt")){
                        text_files.add(file_name);
                    }
                }

                text_files.add("case1.txt");
                text_files.add("case2.txt");
                text_files.add("case3.txt");

                int size = text_files.size();

                String[] options = new String[size];
                options = text_files.toArray(options);

                // Set saved
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView options = ((AlertDialog)dialog).getListView();
                        String selected_file = (String) options.getAdapter().getItem(which);

                        SavedState savedState = serializer.loadGame(selected_file);
                        if(savedState.valid){
                            Intent i = new Intent(Menu.this, PlayGame.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("save", savedState);
                            i.putExtras(bundle);

                            Toast toast =  Toast.makeText(getApplicationContext(),"Save file loaded.", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(i);
                        }else {
                            Toast toast =  Toast.makeText(getApplicationContext(),"Invalid File Name.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
                builder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();

            }
        });
    }
}