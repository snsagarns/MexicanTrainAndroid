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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.NewGame;
import ramapo.edu.neupanemexicantrain.model.SavedState;

/**
 * PlayGame Class
 * This activity serves as a controller of the main game loop
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 11/28/2016
 */
public class PlayGame extends AppCompatActivity {
    // variables and class instantiation
    NewGame game;
    AlertDialog.Builder builder;
    GameRoundView gView;

    // winner
    private  String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load saved state if available
        SavedState savedState = (SavedState) getIntent().getSerializableExtra("save");

        // Initiate New Game
        game = new NewGame(this);

        // If PlayGame activity started from load state load game based on savedState, else start a new game
        if(savedState !=null){
            System.out.println("Saved State:"+savedState.turn + " "+ savedState.computerScore);
            game.loadGame(savedState);
        }else {
            game.start();
        }
    }
}