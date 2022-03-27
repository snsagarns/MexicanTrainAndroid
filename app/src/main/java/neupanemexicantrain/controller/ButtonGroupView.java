/*
 ************************************************************
 * Name:  Sagar Neupane                                     *
 * Project:  Project 3 Mexican Train Java/Android		    *
 * Class:  CMPS 366 OPL				                        *
 * Date:  12/8/2021				                            *
 ************************************************************
 */

package ramapo.edu.neupanemexicantrain.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.Round;
import ramapo.edu.neupanemexicantrain.model.Serializer;
/**
 * ButtonGroupView Class
 * A class that serves the controller of the utility functions for user interface
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class ButtonGroupView {
    Button passButton,saveButton,drawButton,hintButton, playComputerButton, logButton;
    PlayGame activity;
    TextView view;
    Round round;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    /**
     * PUBLIC CONSTRUCTOR
     */
    public ButtonGroupView(PlayGame activity, Round round){
        this.activity = activity;
        this.round = round;
    }

    /**
     * Update the Buttons in the Game View
     * @return void
     */
    public void updateButtonGroupView(){
        // Link views from the activity
        passButton = this.activity.findViewById(R.id.pass_button);
        saveButton = this.activity.findViewById(R.id.save_button);
        drawButton = this.activity.findViewById(R.id.draw_button);
        hintButton = this.activity.findViewById(R.id.hint_button);
        playComputerButton = this.activity.findViewById(R.id.playComputerTurn);
        logButton = this.activity.findViewById(R.id.logButton);
        view = activity.findViewById(R.id.roundLog);


        // Toggles visibility of log
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getVisibility()==View.VISIBLE)
                    view.setVisibility(View.INVISIBLE);
                else if(view.getVisibility()==View.INVISIBLE)
                    view.setVisibility(View.VISIBLE);
            }
        });

        // Disables button if turn=Human
        if(round.getTurn()==1){
            playComputerButton.setEnabled(false);
        }else{
            playComputerButton.setEnabled(true);
        }

        // Enables button if no available train choice and tile is not drawn
        if(round.getTrainChoice() == -1 && !round.getPlayerHuman().getDrawn()){
            drawButton.setEnabled(true);
        }else {
            drawButton.setEnabled(false);
        }

        // Enables button if card is drawn and no suitable moves can take place
        if(round.getPlayerHuman().getDrawn()){
            passButton.setEnabled(true);

        }else {
            passButton.setEnabled(false);
        }

        // Starts computer turn when clicked
        playComputerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                round.startComputerTurn();
            }
        });
        // Passes Human turn when clicked
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.passTurn();
            }
        });

        // Saves the current game state when clicked
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CODE);

                LayoutInflater li = activity.getLayoutInflater();
                View promptView = li.inflate(R.layout.save_prompt, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final EditText userInput = promptView.findViewById(R.id.editTextDialogUserInput);

                builder.setView(promptView);

                builder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Serializer s = new Serializer(activity);
                                String filename = userInput.getText().toString();
                                if(s.saveGame(filename, activity.game)){
                                    System.out.println("Game Saved");
                                    Toast t = Toast.makeText(activity, "Game Saved in "+filename+".txt", Toast.LENGTH_SHORT);
                                    t.show();
                                }else {
                                    System.out.println("Unable to save game");
                                    Toast t = Toast.makeText(activity, "Unable to save game", Toast.LENGTH_SHORT);
                                    t.show();
                                };
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();

            }
        });

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Own side needed: "+round.ownSideNeeded());
                String hint = "Hint: " + round.getPlayerHuman().provideHelp(round.ownSideNeeded());
                Toast hintToast =  Toast.makeText(activity.getApplicationContext(),hint,Toast.LENGTH_LONG);
                hintToast.show();
            }
        });

        // Draws tile from boneyard when pressed
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round.drawTileFromBoneyard();
            }
        });
    }
}
