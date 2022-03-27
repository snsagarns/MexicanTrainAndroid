/*
 ************************************************************
 * Name:  Sagar Neupane                                     *
 * Project:  Project 3 Mexican Train Java/Android		    *
 * Class:  CMPS 366 OPL				                        *
 * Date:  12/8/2021				                            *
 ************************************************************
 */
package ramapo.edu.neupanemexicantrain.controller;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.NewGame;
import ramapo.edu.neupanemexicantrain.model.Round;
import ramapo.edu.neupanemexicantrain.model.Tiles;
/**
 * GameRoundView Class
 * A class that builds mexican train and game train.
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 11/27/2016
 */
public class GameRoundView {
    // variables
    AlertDialog.Builder builder;
    AlertDialog alertDialog;


    public static  BoardView trainBoard, mexicanBoard;
    public static HandView handView;
    Round round;
    TextView messageTextView;
    Vector<Integer> trainChoice;
    Button humanTrain,computerTrain,mexicanTrain, helpChooseTrain;

    PlayGame activity;
    /**
     * PUBLIC CONSTRUCTOR
     * */
    public GameRoundView(PlayGame activity,Round round) {
        this.activity = activity;


        this.round = round;

        trainBoard= new BoardView(activity,round);

        handView = new HandView(activity,round);
    }

    // unused remove
    public void startGame(){
        if(round.getTurn()==1){
            round.startHumanTurn();
        }
        updateGameRoundView();
    }

    // Ask User for train choice on which selected tile will be played
    public void askToChooseTrain(Vector<Integer> trainChoice){
        System.out.println("ASK TRAIN CCHOICE");
        System.out.println("choice: " + trainChoice.size());
        this.trainChoice = trainChoice;

        ArrayAdapter<String> trainListAdapter = new ArrayAdapter<String>(activity,R.layout.activity_list_view, R.id.textView);

        LinearLayout trainChoiceLayout = activity.findViewById(R.id.trainChoicLayout);
        trainChoiceLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(activity);
        textView.setText("Train Choices for User: ");
        textView.setLayoutParams(layoutParams);

        trainChoiceLayout.addView(textView);

        for(Integer trainNo: trainChoice){
            switch (trainNo){
                case 1:
                    humanTrain = new Button(activity);
                    humanTrain.setText("Human Train");
                    humanTrain.setEnabled(false);
                    humanTrain.setLayoutParams(layoutParams);
                    // Execute human Turn with selected tile

                    humanTrain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            round.processTrainInput(1);
                            round.setProceedNextTurn(false);
                            trainChoiceLayout.removeAllViews();

                            round.executeHumanTurn();

                        }
                    });
                    trainChoiceLayout.addView(humanTrain);
                    trainListAdapter.add("Human Train");
                    break;

                case 2:
                    trainListAdapter.add("Computer Train");

                    computerTrain = new Button(activity);
                    computerTrain.setLayoutParams(layoutParams);
                    computerTrain.setEnabled(false);
                    computerTrain.setText("Computer Train");
                    // Execute Computer Turn with selected tile
                    computerTrain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            round.processTrainInput(2);
                            round.setProceedNextTurn(false);
                            trainChoiceLayout.removeAllViews();

                            round.executeHumanTurn();
                        }
                    });
                    trainChoiceLayout.addView(computerTrain);
                    break;

                case 3:
                    mexicanTrain = new Button(activity);
                    mexicanTrain.setText("Mexican Train");
                    mexicanTrain.setLayoutParams(layoutParams);
                    mexicanTrain.setEnabled(false);

                    // Execute Mexican Turn with selected tile
                    mexicanTrain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            round.processTrainInput(3);
                            round.setProceedNextTurn(false);
                            trainChoiceLayout.removeAllViews();

                            round.executeHumanTurn();
                        }
                    });
                    trainListAdapter.add("Mexican Train");
                    trainChoiceLayout.addView(mexicanTrain);
                    break;

                case 4:
                    helpChooseTrain = new Button(activity);
                    helpChooseTrain.setText("Help Choose Train");
                    helpChooseTrain.setLayoutParams(layoutParams);
                    // Diplay hint to play next turn
                    helpChooseTrain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            round.processTrainInput(4);
                            round.setProceedNextTurn(true);
                            trainChoiceLayout.removeAllViews();

                            round.startHumanTurn();
                        }
                    });
                    trainChoiceLayout.addView(helpChooseTrain);
                    trainListAdapter.add("Help Choose Train and tiles");
                    break;
            }
        }
    }
    /**
     * Ask the user to choose between head and tail for coin flip
     * @return void
     * */
    public void askCoinFlip(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false)
                .setTitle("Coin Flip")
                .setMessage("Select a side. Winner will start turn first.")
                .setPositiveButton("Head", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        round.setCoinFlip(1);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Tail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        round.setCoinFlip(2);
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
    /**
     * Update Train choice buttons, Check if user tiles can be played inb given trains
     * @return void
     * */
    public void  updateChoiceTrain(){
        if(round.getTurn() == 2){
            LinearLayout trainChoiceLayout = activity.findViewById(R.id.trainChoicLayout);
            trainChoiceLayout.removeAllViews();
        }
        int index = round.getPlayerHuman().getSelectedTileIndex();
        int size = round.getPlayerHuman().getHand().getHandTiles().size();

        // if tile can be played in human train enable button
        if(humanTrain!=null){
            if(index<0 || index >=size ){
                humanTrain.setEnabled(false);
            }else{
                Tiles t = round.getPlayerHuman().getDemandedTile( index);

                if(t.getFirstSide() == round.ownSideNeeded() || t.getSecondSide() ==round.ownSideNeeded()){
                    humanTrain.setEnabled(true);
                }else {
                    humanTrain.setEnabled(false);
                }
            }
        }
        // if tile can be played in computer train enable button
        if(computerTrain!=null){
            if(index<0 || index >=size ){
                computerTrain.setEnabled(false);
            }else{
                Tiles t = round.getPlayerHuman().getDemandedTile( index);

                if(t.getFirstSide() == round.opponentSideNeeded() || t.getSecondSide() ==round.opponentSideNeeded()){
                    computerTrain.setEnabled(true);
                }else {
                    computerTrain.setEnabled(false);
                }
            }
        }
        // if tile can be played in mexican train enable button
        if(mexicanTrain!=null){
            if(index<0 || index >=size ){
                mexicanTrain.setEnabled(false);
            }else{
                Tiles t = round.getPlayerHuman().getDemandedTile( index);

                if(t.getFirstSide() == round.mexicanSideNeeded() || t.getSecondSide() ==round.mexicanSideNeeded()){
                    mexicanTrain.setEnabled(true);
                }else {
                    mexicanTrain.setEnabled(false);
                }
            }
        }
    }
    /**
     * Update the entire game Round view
     * @return void
     * */
    public void updateGameRoundView(){

        ButtonGroupView buttonGroupView = new ButtonGroupView(activity, round);
        buttonGroupView.updateButtonGroupView();

        updateBoardInfo();
        updateBoard();
        updateChoiceTrain();
        checkGameOver();

    }

    /**
     * Updates all the board and hand views
     * @return void
     * */
    public void  updateBoard(){

        trainBoard.setBoard(round.getSetup().getMexicanTrain());
        trainBoard.updateBoardView(R.id.tileBoardMexicanTrain,true);

        trainBoard.setBoard( round.getSetup().getTrainTiles());
        trainBoard.updateBoardView(R.id.tileBoardEngineTrain,false);

        handView.setHand(round.getDeck().getDeck());
        handView.updateHandView(R.id.tileBoneyard,false);

        handView.setHand(round.getPlayerHuman().getHand().getHandTiles(), trainChoice);
        handView.updateHandView(R.id.tileUserHand,true);

        handView.setHand(round.getPlayerComputer().getHand().getHandTiles());
        handView.updateHandView(R.id.tileComputerHand,false);
    }

    /**
     * Update board info and display info such as round, score and messages
     * @return void
     * */
    public void updateBoardInfo(){
        messageTextView = this.activity.findViewById(R.id.message);
        messageTextView.setText(round.getMessage());

        NewGame game = activity.game;

        //Turn
        TextView textTurn = activity.findViewById(R.id.turn);
        String turn;
        switch (round.getTurn()){
            case 1:
                turn = "User";
                break;
            case 2:
                turn = "Computer";
                break;
            default:
                turn = "Human";
        }
        textTurn.setText("Turn: "+turn);
        //Round Number
        TextView textRound = activity.findViewById(R.id.round);
        textRound.setText("Round: "+Integer.toString(game.getRoundNumber()));

        //Human Score
        TextView textHumanScore = activity.findViewById(R.id.humanScore);
        textHumanScore.setText("Human Score: "+Integer.toString(game.getHumanScore()));

        // Computer Score
        TextView textComputerScore = activity.findViewById(R.id.computerScore);
        textComputerScore.setText("Computer Score: " + " "+game.getComputerScore());

        //Log
        TextView logView = activity.findViewById(R.id.roundLog);
        logView.setText(round.getLog());
    }
    /**
     * Checks if the game round is over and display appropriate message
     * @return void
     * */
    public void checkGameOver() {

        if(round.isGameOver()){
            String message = "";

            NewGame game = activity.game;
            message = ""+game.getComputerScore();

            boolean skip =false;

            // Human's hand empty means game ended
            if (round.isHumanHandEmpty() )
            {
                game.setWinner( "Human");

                round.setTurn(1);
                round.setProceedNextTurn(true);

                message = "\nWinner of the Round: " + game.getWinner()  +"🏆🥇🙌🏻\n";
                message+= "Human Score: " + round.getHumanScore()+"\n";
                message+= "Computer Score: " + round.getComputerScore()+"\n";
                skip = true;

            }

            // Computer's hand empty means game ended
            if (round.isComputerHandEmpty() && !skip)
            {
                game.setWinner("Computer");

                round.setTurn(2);
                round.setProceedNextTurn(true);

                message = "\nWinner of the Round: " + game.getWinner()  +"🏆🥇🙌🏻\n";
                message+= "Human Score: " + round.getHumanScore()+"\n";
                message+= "Computer Score: " + round.getComputerScore()+"\n";
                skip = true;

            }

            // Boneyard empty means game ended
            if (round.isBoneyardEmpty() && !skip)
            {
                int human_point = round.getHumanScore();
                int computer_point = round.getComputerScore();

                if (human_point < computer_point)
                {
                    game.setWinner("Human");
                    round.setTurn(1);
                    round.setProceedNextTurn(true);

                    message = "\nWinner of the Round: " + game.getWinner()  +"🏆🥇🙌🏻\n";
                    message+= "Human Score: " + round.getHumanScore()+"\n";
                    message+= "Computer Score: " + round.getComputerScore()+"\n";

                }
                else if (human_point > computer_point)
                {
                    message = "\nWinner of the Round: " + game.getWinner()  +"🏆🥇🙌🏻\n";
                    message+= "Human Score: " + round.getHumanScore()+"\n";
                    message+= "Computer Score: " + round.getComputerScore()+"\n";

                    game.setWinner( "Computer");

                    round.setTurn(2);
                    round.setProceedNextTurn(true);

                }
                else
                {
                    message = "......GAME was a draw.....";
                    message+= "Human Score: " + round.getHumanScore()+"\n";
                    message+= "Computer Score: " + round.getComputerScore()+"\n";
                }
            }

            if(alertDialog!=null){
                alertDialog.cancel();
            }
            builder = new AlertDialog.Builder(activity);
            builder.setTitle("Round Ended!"+game.getRoundNumber());
            builder.setMessage(message);
            builder.setCancelable(false);

            if(game.getRoundNumber()>8){
                builder.setCancelable(false)
                        .setPositiveButton("Finish Game", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String message ;
                                if(game.getHumanScore()<game.getComputerScore()){

                                    game.setWinner("Human");
                                    message = "Winner: "+game.getWinner();
                                }else if(game.getHumanScore()>game.getComputerScore()){
                                    game.setWinner("Computer");
                                    message = "Winner: "+game.getWinner();
                                }else {
                                    message="GAME was a draw";
                                }
                                builder = new AlertDialog.Builder(activity);
                                builder.setCancelable(false);
                                builder.setTitle("Game Ended!");
                                builder.setMessage(message + " \nHuman Point: "+game.getHumanScore()+"\nComputer Point: "+game.getComputerScore());
                                builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        activity.finish();
                                    }
                                });
                                builder.create().show();
                                dialog.cancel();
                            }
                        });
            }else{
                builder.setPositiveButton("Start Next Round", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.addComputerScore(round.getComputerScore());
                        game.addHumanScore(round.getHumanScore());

                        updateBoardInfo();

                        game.startNextRound();
                        dialog.cancel();

                    }
                });
                builder.setNegativeButton("End Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message ;

                        game.addComputerScore(round.getComputerScore());
                        game.addHumanScore(round.getHumanScore());

                        updateBoardInfo();

                        if(game.getHumanScore()<game.getComputerScore()){

                            game.setWinner("Human");
                            message = "Winner: "+game.getWinner();
                        }else if(game.getHumanScore()>game.getComputerScore()){
                            game.setWinner("Computer");
                            message = "Winner: "+game.getWinner();
                        }else {
                            message="GAME was a draw";
                        }
                        builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Game Ended!");
                        builder.setCancelable(false);
                        builder.setMessage(message + " \nHuman Point: "+game.getHumanScore()+"\nComputer Point: "+game.getComputerScore());
                        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                activity.finish();
                            }
                        });
                        builder.create().show();
                        dialog.cancel();
                    }
                });
            }
            alertDialog = builder.create();
            alertDialog.show();
        }

    }
}