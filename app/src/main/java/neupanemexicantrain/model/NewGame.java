/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      1. Mexican Train Java Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

import ramapo.edu.neupanemexicantrain.controller.PlayGame;

/**
 * NewGame Class
 * Instantiate game details, updates them and make necessary changes, sets winners, provides option for new game and so on
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class NewGame {

    // human score for the round
    private int human_score;

    // computer score for the round
    private  int computer_score;

    // round number
    private int round_number;

    // winner
    private  String winner;

    // round
    private Round round;

    //activity
    private PlayGame activity;

    /**
     * NewGame:: constructor
     * @param activity, updates the PlayGame view
     * @return none
     * @date 12/08/2021
     */
    public NewGame(PlayGame activity){
        round_number = 1;
        human_score = 0;
        computer_score = 0;
        winner = "";

        this.round = new Round(activity, round_number);
        this.activity = activity;
    }

    /**
     * NewGame:: addHumanScore, adds human score
     * @param score, for the round
     * @return none
     * @date 12/08/2021
     */
    public void addHumanScore(int score){
        this.human_score += score;
    }

    /**
     * NewGame:: addComputerScore, adds computer score
     * @param score, for the round
     * @return none
     * @date 12/08/2021
     */
    public void addComputerScore(int score){
        this.computer_score += score;
    }
    /**
     * NewGame:: setWinner, sets the winner
     * @param winner, string
     * @return none
     * @date 12/08/2021
     */
    public void setWinner(String winner){
        this.winner = winner;
    }
    //getter for the winner
    public String getWinner(){
        return winner;
    }

    // getter for computer score
    public int getComputerScore() {
        return computer_score;
    }

    // getter for human score
    public int getHumanScore() {
        return human_score;
    }

    // getter for round number
    public int getRoundNumber() {
        return round_number;
    }

    // getter for the round object
    public Round getRound() {
        return round;
    }

    /**
     * NewGame:: startNextRound, exception handler for whether or not start the next round
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void startNextRound(){
        round_number++;
        try {
            round.startNewRound(round_number);

        }catch (Exception e){
            activity.finish();
        }
    }
    /**
     * NewGame:: start, start new game
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void start(){
        round.startGame();
    }

    /**
     * NewGame:: checksGameOver: checks the condition for ending the game is met or not
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void checkGameOver() {
        // Human's hand empty means game ended
        if (round.isHumanHandEmpty())
        {
            human_score += round.getHumanScore();
            computer_score += round.getComputerScore();
            winner = "Human";
            round.setTurn(1);
            round.setProceedNextTurn(true);
        }
        // Computer's hand empty means game ended
        if (round.isComputerHandEmpty())
        {
            human_score += round.getHumanScore();
            computer_score += round.getComputerScore();
            winner = "Computer";
            round.setTurn(2);
            round.setProceedNextTurn(true);
        }
        // Boneyard empty means game ended
        if (round.isBoneyardEmpty())
        {
            int human_point = round.getHumanScore();
            int computer_point = round.getComputerScore();

            if (human_point < computer_point)
            {
                human_score += round.getHumanScore();
                computer_score += round.getComputerScore();
                winner = "Human";
                round.setTurn(1);
                round.setProceedNextTurn(true);
            }
            else if (human_point > computer_point)
            {
                human_score += round.getHumanScore();
                computer_score += round.getComputerScore();
                winner = "Computer";
                round.setTurn(2);
                round.setProceedNextTurn(true);
            }
            else
            {
                human_score += human_point;
                computer_score += computer_point;
            }
        }
    }
    /**
     * NewGame:: loadGame: load the game from a serialization file and updates the necessary information for the game
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void loadGame(SavedState savedState){
        round.getDeck().setDeck(savedState.boneyard);
        round.getPlayerHuman().getHand().setHandTiles(savedState.humanHand);
        round.getPlayerComputer().getHand().setHandTiles(savedState.computerHand);
        round.getSetup().setPlayersTrain(savedState.train);
        round.getSetup().setMexicanTrain(savedState.mexicanTrain);

        round.setTurn(savedState.turn);
        round.setEngineNumber(savedState.engineNumber);
        round.setRoundNumber(savedState.roundNumber);
        round_number = savedState.roundNumber;

        human_score = savedState.humanScore;
        computer_score = savedState.computerScore;

        round.setLeftMarker(savedState.leftMarker);
        round.setRightMarker(savedState.rightMarker);

        round.setOrphanDouble();

        if(round.getTurn()==1){
            round.startHumanTurn();
        }
        round.gameRoundView.updateGameRoundView();

    }
}
