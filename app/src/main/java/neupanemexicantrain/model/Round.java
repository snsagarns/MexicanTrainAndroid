/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      1. Mexican Train Java Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

import java.util.Vector;

import ramapo.edu.neupanemexicantrain.controller.GameRoundView;
import ramapo.edu.neupanemexicantrain.controller.PlayGame;

/**
 * Round class, heart of the game
 * Most of the features like checking side needed extracting them and placing them dealing with markers and tiles and orphan doubles
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class Round {

    // whether or not to proceed to a next turn
    private boolean proceedNextTurn;

    // checker for orphan conditon met or not
    private boolean checkOrphan;

    // PlayGame object
    private PlayGame activity;

    // message information
    private String message;

    // engine for the round
    private int engine;

    // engine tile
    private Tiles engine_tile;

    // number of round
    private  int num_round;

    // turn of the player
    private   int turn;

    // boolean variable to store the status of computer train's marker
    private  boolean has_left_marker;

    // boolean variable to store the status of human train's marker
    private  boolean has_right_marker;

    // boolean to store whether human train, mexican train or computer train is orphan double or not
    private  boolean ht_odt, mt_odt, ct_odt;

    // instance of a round
    private static Round instance;

    // gameRoundView object
    public GameRoundView gameRoundView;

    // log file
    private String log;

    // train choosen by a user
    private int userChosenTrain;

    // Human player
    private  Human playerHuman;

    // Computer player
    private   Computer playerComputer;

    // Deck
    private   Deck deck;

    // Train Setup
    private  Setup setup;

    /**
     * Round:: Default Constructor, intstantiates required variables
     * @param activity, PlayGame object
     * @param round_count , round number
     * @return none
     * @date 12/08/2021
     */
    public Round(PlayGame activity, int round_count)
    {
        this.activity = activity;
        checkOrphan = true;
        proceedNextTurn = true;

        turn = 1;
        ht_odt = false;
        mt_odt = false;
        ct_odt = false;

        has_left_marker = false;
        has_right_marker = false;

        deck = new Deck();
        playerHuman = new Human();
        playerComputer = new Computer();
        setup = new Setup();

        num_round = round_count;
        engine_tile = getEngine(num_round);
        engine = engine_tile.getFirstSide();

        message = "";
        gameRoundView = new GameRoundView(activity, this);
        log = "------------------ Round "+ num_round+ " -------------------\n";

    }
    // checker for a computer marker
    public boolean getHasLeftMarker(){
        return has_left_marker;
    }
    // checker for a human marker
    public boolean getHasRightMarker(){
        return has_right_marker;
    }
    // getter for log file details
    public String getLog() {
        return log;
    }

    /**
     * Round:: addlog, updating the log file string concatenation
     * @param turnMessage message for given turn
     * @param turn player's turn
     * @param message message to display
     * @return none
     * @date 12/08/2021
     */
    public void addLog(Boolean turnMessage,int turn,String message){
        if(turnMessage) {
            System.out.println("TurnL: "+turnMessage + turn );
            if (turn == 1) {
                log += "\n--------  Human 's Turn   ------------\n";
            } else {
                log += "\n--------  Computer's Turn ------------\n";
            }
        }
        log += message+"\n";
        gameRoundView.updateBoardInfo();
    }
    // getter for a message to send
    public String getMessage() {
        return message;
    }

    // checker for whether or not proceeding to a next turn
    public boolean getProceedNextTurn(){
        return proceedNextTurn;
    }

    // setter for the round number
    public void setRoundNumber(int roundNumber){
        this.num_round = roundNumber;
        log = "------------------ Round "+ num_round+ " -------------------\n";

    }

    // setter for proceeding a turn
    public void setProceedNextTurn(boolean proceedNextTurn){
        this.proceedNextTurn = proceedNextTurn;
    }

    // getter for computer player
    public Computer getPlayerComputer() {
        return playerComputer;
    }

    // getter for a human player
    public Human getPlayerHuman() {
        return playerHuman;
    }

    // getter for a turn
    public int getTurn() {
        return turn;
    }

    // getter for a deck
    public Deck getDeck() {
        return deck;
    }

    // getter setup
    public Setup getSetup(){
        return setup;
    }

    // sets the turn of the player
    public  void setTurn(int a)
    {
        turn = a;
    }

    // sets Human player's hand
    public  void setHumanHand(Vector<Tiles> v)
    {
        this.playerHuman.setHand(new Hand((v)));
    }

    // sets Computer player's hand
    public   void setComputerHand(Vector<Tiles> v)
    {
        this.playerComputer.setHand(new Hand(v));
    }

    // sets the Computer-Human Train setup
    public   void setSetup(Vector<Tiles> v)
    {
        this.setup.setPlayersTrain(v);
    }

    // sets the Mexican Train setup
    public  void setMexicanTrain(Vector<Tiles> v)
    {
        this.setup.setMexicanTrain(v);
    }

    // sets the boneyard deck of remaining tiles s
    public  void setDeckBoneyard(Vector<Tiles> v)
    {
        this.deck.setDeck(v);
    }

    // sets the deck
    public void setDeck(Deck d)
    {
        this.deck = d;
    }

    // sets the left marker Computer Train marker
    public void setLeftMarker(boolean a)
    {
        this.has_left_marker = a;
    }

    // sets the right marker Human Train marker
    public  void setRightMarker(boolean a)
    {
        this.has_right_marker = a;
    }

    // sets the Mexican Train as Orphan Double
    public  void setOrphanDouble_M(boolean a)
    {
        this.mt_odt = a;
    }

    // sets the Computer Train as Orphan Double
    public  void setOrphanDouble_C(boolean a)
    {
        this.ct_odt = a;
    }

    // sets the Human Train as Orphan Double
    public void setOrphanDouble_H(boolean a)
    {
        this.ht_odt = a;
    }

    // sets the Mexican Train as Orphan Double
    public  boolean getOrphanDouble_M()
    {
        return this.mt_odt;
    }

    // sets the Computer Train as Orphan Double
    public  boolean getOrphanDouble_C()
    {
        return this.ct_odt;
    }

    // sets the Human Train as Orphan Double
    public boolean getOrphanDouble_H()
    {
        return this.ht_odt;
    }

    // checks if the human's hand is exhausted, i.e, if it's empty
    public boolean isHumanHandEmpty()
    {
        return this.playerHuman.isHandEmpty();
    }

    // checks if the computer's hand is exhausted, i.e, if it's empty
    public boolean isComputerHandEmpty()
    {
        return this.playerComputer.isHandEmpty();
    }

    // getter for a round number
    public int getRoundNumber(){
        return num_round;
    }

    /**
     * Round::startGame, start the game and setup and update board
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void startGame(){
        setupGame();
        gameRoundView.updateGameRoundView();
    }

    /**
     * Round::setupGame, set the HandTiles and turn of players
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void setupGame(){
        setHandTiles();
        setPlayerTurn();
    }

    /**
     * Round::setCoinFlip, utility function to flip the coin and instantiate or determine turn
     * @param input
     * @return none
     * @date 12/08/2021
     */
    public void setCoinFlip(int input){
        int head_tail = (int) Math.random() % 2+ 1;

        if(input == head_tail)
        {
            setTurn(1);
            message = "Human won the toss, so Human playing first....";
            gameRoundView.updateGameRoundView();
            addLog(false, 0, message);
        }
        else
        {
            setTurn(2);
            message = "Human lost the toss, so Computer playing first....";
            gameRoundView.updateGameRoundView();
            addLog(false, 0, message);
        }
        setProceedNextTurn(true);
        if(turn==1){
            startHumanTurn();
        }
    }

    /**
     * Round:: setPlayersTurn, utility function to determine a turn ask for a coin flip if necessary and update the gameboard
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void setPlayerTurn() {
        if(num_round == 1){
            gameRoundView.askCoinFlip();
        }
        else {
            if (getHumanScore() < getComputerScore()) {
                setTurn(1);
                setProceedNextTurn(true);
                message = "Human score is less than computer. So human start first....";
                addLog(false, 0,message);
                gameRoundView.updateGameRoundView();
                if (turn == 1) {
                    startHumanTurn();
                }
            }
            else if (getHumanScore() > getComputerScore()) {
                setTurn(2);
                setProceedNextTurn(true);
                message = "Human score is more than computer. So computer start first....";
                addLog(false, 0,message);
                gameRoundView.updateGameRoundView();
            }
            else {
                gameRoundView.askCoinFlip();
            }
        }
    }

    /**
     * Round:: startNewRound, start the new round and update game board call all necessary functions
     * set hand for players,  setup deck and other features
     * @param round_count, round number
     * @return none
     * @date 12/08/2021
     */
    public void startNewRound(int round_count){
        proceedNextTurn = true;
        turn = 1;
        ht_odt = false;
        mt_odt = false;
        ct_odt = false;

        has_left_marker = false;
        has_right_marker = false;

        deck = new Deck();
        playerHuman = new Human();
        playerComputer = new Computer();
        setup = new Setup();

        num_round = round_count;
        engine_tile = getEngine(num_round);
        engine = engine_tile.getFirstSide();

        message = "\nEngine for the round:  "+engine;
        addLog(false, 0,message);
        setupGame();
    }
    /**
     * Round:: isGameOver, to check if the condition for ending the round has been met
     * @param
     * @return none
     * @date 12/08/2021
     */
    public boolean isGameOver()
    {
        if (isBoneyardEmpty() == true)
            return true;
        if (playerHuman.isHandEmpty() == true)
            return true;
        if (playerComputer.isHandEmpty() == true)
            return true;

        return false;
    }

    /**
     * Round:: setHandTiles,  To shuffle the deck and distribute tiles to the player's hand and place an engine for the round
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void setHandTiles()
    {
        // shuffiling the deck after the extraction of engine
        deck.shuffleDeck();

        // distributing tiles to the players
        playerHuman.setHand(new Hand(deck.distributeToPlayer()));
        playerComputer.setHand(new Hand(deck.distributeToPlayer()));

        // placing engine for the round
        setup.placeEngine(engine_tile);
    }

    /**
     * Round::getEngine, To extract the engine from the deck
     * @param a round number
     * @return engine tiles
     * @date 12/08/2021
     */
    public Tiles getEngine(int a)
    {
        return deck.getEngineFromDeck(a);
    }

    // getter for engine
    public int getEngineNumber(){
        return engine;
    }
    // setter for engine
    public void setEngineNumber(int engineNumber){
        this.engine = engineNumber;
    }

    /**
     * Round::startHumanTurn, determine the start of the Human Turn and update the board
     * @param
     * @return void none
     * @date 12/08/2021
     */
    public void startHumanTurn(){
        if(isGameOver()){
            gameRoundView.updateGameRoundView();
            return;
        }
        proceedNextTurn = false;
        int i = getUserChosenTrain();

        // -1 means no tiles available to play
        if(i == -1){
            message = "No Valid moves Available . Draw tile";
            gameRoundView.updateGameRoundView();
            proceedNextTurn = true;
            addLog(true, 1,message);
        }
        playerHuman.setSelectedTileIndex(-1);
        gameRoundView.updateGameRoundView();
    }

    /**
     * Round::executeHumanTurn, executes Human Turn and checks for condition, if double not played, changes turn
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void executeHumanTurn(){
        if(isGameOver()){
            gameRoundView.updateGameRoundView();
            return;
        }
        playHumanTurn(playerHuman, userChosenTrain);

        if (proceedNextTurn) {
            turn = 2;
        }

        // Set Orphan Double if necessary
        if(checkOrphan){
            setOrphanDouble();
        }
        else{
            checkOrphan = true;
        }
    }

    /**
     * Round:startComputerTurn, executes the computer turn changes turn if necessary determines if orphan double played by computer
     * @param
     * @return none
     * @date 12/08/2021
     */
    public void startComputerTurn(){
        if(isGameOver()){
            gameRoundView.updateGameRoundView();
            return;
        }

        playerHuman.setDrawn(false);
        if (playerComputer.isHandEmpty()) {
            return;
        }

        if(playComputerTurn(playerComputer)) {
            turn = 1;
        }
        gameRoundView.updateGameRoundView();

        // Set Orphan Double if necessary
        if(checkOrphan){
            setOrphanDouble();
        }
        else{
            checkOrphan = true;
        }
        startHumanTurn();
    }
    /**
     * Round:ownSideNeeded, to find out which pip value is required in own train based on turn
     * @param
     * @return returns the integer value of the side needed in own train
     * @date 12/08/2021
     */
    public int ownSideNeeded()
    {
        int side_needed;
        if (turn == 1)
        {
            if (!setup.getRight().checkReverse())
            {
                side_needed = setup.getRight().getSecondSide();
            }
            else
            {
                side_needed = setup.getRight().getFirstSide();
            }
            return side_needed;
        }
        else
        {
            if (!setup.getLeft().checkReverse())
            {
                side_needed = setup.getLeft().getFirstSide();
            }
            else
            {
                side_needed = setup.getLeft().getSecondSide();
            }
            return side_needed;
        }
    }
    /**
     * Round: opponentSideNeeded, To find out which pip value is required in opponent train in case it has a marker in future
     * @param
     * @return returns the integer value of the side needed in opponent train
     * @date 12/08/2021
     */
    public int opponentSideNeeded()
    {
        int side_needed;
        if (turn == 1)
        {
            if (!setup.getLeft().checkReverse())
            {
                side_needed = setup.getLeft().getFirstSide();
            }
            else
            {
                side_needed = setup.getLeft().getSecondSide();
            }
            return side_needed;
        }
        else
        {
            if (!setup.getRight().checkReverse())
            {
                side_needed = setup.getRight().getSecondSide();
            }
            else
            {
                side_needed = setup.getRight().getFirstSide();
            }
            return side_needed;
        }
    }
    /**
     * Round: mexicanSideNeeded, To find out which pip value is required in Mexican Tain
     * @param
     * @return returns the integer value of the side needed in Mexican Train
     * @date 12/08/2021
     */
    public int mexicanSideNeeded()
    {
        int side_needed;
        if (!setup.getFromMexicanTrain().checkReverse())
        {
            side_needed = setup.getFromMexicanTrain().getSecondSide();
        }
        else
        {
            side_needed = setup.getFromMexicanTrain().getFirstSide();
        }
        return side_needed;
    }

    /**
     * Round:  chooseTrain, To help human player to choose the good train and good tile to play and find the best train to play for the computer trai
     * @param
     * @return Train Number 1 for Human Train
     *             Train Number 2 for Computer Train
     *             Train Number 3 for Mexican Train
     *             -1 to Prompt the player to draw from a Boneyard
     * @date 12/08/2021
     */
    public int chooseTrain()
    {
        int side1 = ownSideNeeded();
        int side2 = opponentSideNeeded();
        int side3 = mexicanSideNeeded();

        // stores the indices of eligible tile
        int pos1 =-1, pos2=-1, pos3 = -1;

        // if human train is orphan double, it's only eligible train to play
        if (ht_odt)
        {
            message ="Human train is now orphan double and only eligible train to play" ;
            gameRoundView.updateGameRoundView();
            if (turn == 2)
            {
                if (playerComputer.findGoodTile(opponentSideNeeded()) >= 0)
                {
                    return 1;
                }
            }
            if (turn == 1)
            {
                pos1 = playerHuman.findGoodTile(ownSideNeeded());
                if (pos1 >= 0)
                {
                    message +="\nComputer Suggests Human to Play" + playerHuman.getDemandedTile(pos1).printTiles()+ " as it's highest pip tile matching with Orphan Human Train" ;
                    gameRoundView.updateGameRoundView();
                    return 1;
                }
                message +="\nComputer Suggests you to draw a tile as you don't matching tiles with that of Orphan Train" ;
                gameRoundView.updateGameRoundView();
            }
            return -1;
        }

        // if computer train is orphan double, it's only eligible train to play
        if (ct_odt)
        {
            message="Computer train is now orphan double and only eligible train to play";
            gameRoundView.updateGameRoundView();
            if (turn == 2)
            {
                if (playerComputer.findGoodTile(ownSideNeeded()) >= 0)
                {
                    return 2;
                }
            }
            if (turn == 1)
            {
                pos2 = playerHuman.findGoodTile(opponentSideNeeded());
                if (pos2 >= 0)
                {
                    message+="\nComputer Suggests Human to Play tile " + playerHuman.getDemandedTile(pos2).printTiles() + " as it's highest pip tile matching with Orphan Computer Train ";
                    gameRoundView.updateGameRoundView();
                    return 2;
                }
                message+="\nComputer Suggests you to draw a tile as you don't matching tiles with that of Orphan Train  ";
                gameRoundView.updateGameRoundView();
            }
            return -1;
        }

        // If Mexican Train is orphan double, it's the only eligible train to play
        if (mt_odt)
        {
            message="Mexican train is now orphan double and only eligible train to play \n";
            gameRoundView.updateGameRoundView();
            if (turn == 2)
            {
                if (playerComputer.findGoodTile(mexicanSideNeeded()) >= 0)
                {
                    return 3;
                }
            }
            if (turn == 1)
            {
                pos3 = playerHuman.findGoodTile(mexicanSideNeeded());
                if (pos3 >= 0)
                {
                    message+=" \nComputer Suggests Human to Play " +  playerHuman.getDemandedTile(pos3).printTiles() +  " as it's highest pip tile matching with Orphan Mexican Train\n";
                    gameRoundView.updateGameRoundView();
                    return 3;
                }
                message+="Computer Suggests you to draw a tile as you don't matching tiles with that of Orphan Train ";
                gameRoundView.updateGameRoundView();
            }
            return -1;
        }

        // Human's turn
        if (turn == 1)
        {
            // index position for tiles at hand
            pos1 = playerHuman.findGoodTile(side1);
            pos3 = playerHuman.findGoodTile(side3);
            pos2 = playerHuman.findGoodTile(side2);

            // checks if computer train has marker
            if (has_left_marker &&( pos2 > 0))
            {
                if (pos2 >= 0)
                {
                    if (playerHuman.getDemandedTile(pos2).checkDouble())
                    {
                        message="\nComputer Suggests Human to Play " +playerHuman.getDemandedTile(pos2).printTiles()+  " in Computer Train because it has a marker and you have a double tile";
                        gameRoundView.updateGameRoundView();
                        return 2;
                    }
                }
            }
            // if both human train and mexican train are eligible to play
            if ((pos1 >= 0) && (pos3 >= 0))
            {
                // check if double can be placed on any train
                if (playerHuman.getDemandedTile(pos1).checkDouble())
                {
                    message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() + " in Human Train because playing double is helpful to create orphan double....";
                    gameRoundView.updateGameRoundView();
                    return 1;
                }
                if (playerHuman.getDemandedTile(pos3).checkDouble())
                {
                    message="\nComputer Suggests Human to Play  " + playerHuman.getDemandedTile(pos3).printTiles() + " in Mexican Train because playing double is helpful to create orphan double....";
                    gameRoundView.updateGameRoundView();
                    return 3;
                }
            }
            // if own train has marker and eligible tile to play exists in hand
            if (has_right_marker && pos1 >= 0)
            {
                message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() + " in Human Train because it has a marker and it's better to resolve the marker earlier.";
                gameRoundView.updateGameRoundView();
                return 1;
            }
            // if computer train has marker and all train are possible to play, check where highest sum pip can be played
            if (has_left_marker)
            {
                if ((pos1 >= 0) && (pos2 >= 0) && (pos3 >= 0))
                {
                    if ((side1 > side2) && (side1 > side3))
                    {
                        message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() +" in Human Train because it is has high sum of pips.";
                        gameRoundView.updateGameRoundView();
                        return 1;
                    }
                    if ((side2 > side1) && (side2 > side3))
                    {
                        message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos2).printTiles() +  " in Computer Train because it has marker and human has matching tile with high sum of pips.";
                        gameRoundView.updateGameRoundView();
                        return 2;
                    }
                    message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos3).printTiles() + " in Mexican Train because human has a matching tile with high sum of pips.";
                    gameRoundView.updateGameRoundView();
                    return 3;
                }
            }
            if (has_left_marker)
            {
                if ((pos1 >= 0) && (pos2 >= 0))
                {
                    if (side1 > side2)
                    {
                        message="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() +" in Human Train because it is has high sum of pips.";
                        gameRoundView.updateGameRoundView();
                        return 1;
                    }
                    message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() + " in Computer Train because it has marker and human has matching tile with high sum of pips.";
                    gameRoundView.updateGameRoundView();
                    return 2;
                }
            }
            if ((pos1 >= 0) && (pos3 >= 0))
            {
                if (side1 > side3)
                {
                    message+="\nComputer Suggests Human to Play "+ playerHuman.getDemandedTile(pos1).printTiles() +  " in Human Train because it is has high sum of pips.";
                    gameRoundView.updateGameRoundView();
                    return 1;
                }
                message+="\nComputer Suggests Human to Play a tile " + playerHuman.getDemandedTile(pos3).printTiles() +" in Mexican Train because human has a matching tile with high sum of pips.";
                gameRoundView.updateGameRoundView();
                return 3;
            }
            if (has_left_marker)
            {
                if ((pos3 >= 0) && (pos2 >= 0))
                {
                    if (side2 > side3)
                    {
                        message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos2).printTiles() +  " in Computer Train because it has marker and human has matching tile with high sum of pips.";
                        gameRoundView.updateGameRoundView();
                        return 2;
                    }
                    message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos3).printTiles() +  " in Mexican Train because human has matching tile with high sum of pips.";
                    gameRoundView.updateGameRoundView();
                    return 3;
                }
            }
            if (pos1 >= 0)
            {
                message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos1).printTiles() + " in Human Train because it is has high sum of pips.";
                gameRoundView.updateGameRoundView();
                return 1;
            }
            if (has_left_marker)
            {
                if (pos2 >= 0)
                {
                    message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos2).printTiles() + " in Computer Train because it has marker and human has matching tile with high sum of pips.";
                    gameRoundView.updateGameRoundView();
                    return 2;
                }
            }
            if (pos3 >= 0)
            {
                message+="\nComputer Suggests Human to Play " + playerHuman.getDemandedTile(pos3).printTiles() +   " in Mexican Train because human has matching tile with high sum of pips.";
                gameRoundView.updateGameRoundView();
                return 3;
            }
            message+="You don't have any eligible tile to play in any train. Draw one from the boneyard";
            gameRoundView.updateGameRoundView();
            return -1;
        }

        // Computer Turn
        if (turn == 2)
        {
            pos2 = playerComputer.findGoodTile(side1);
            pos3 = playerComputer.findGoodTile(side3);
            pos1 = playerComputer.findGoodTile(side2);
            if (has_left_marker && pos2 >= 0)
            {
                return 2;
            }
            if (has_right_marker && pos1 >= 0)
            {
                pos1 = playerComputer.findGoodTile(side2);
                if (playerComputer.getDemandedTile(pos1).checkDouble())
                {
                    return 1;
                }
            }

            if ((pos2 >= 0) && (pos3 >= 0))
            {
                if (playerComputer.getDemandedTile(pos2).checkDouble())
                {
                    return 2;
                }
                if (playerComputer.getDemandedTile(pos3).checkDouble())
                {
                    return 3;
                }
            }
            if(has_right_marker) {
                if ((pos1 >= 0) && (pos2 >= 0) && (pos3 >= 0)) {
                    if ((side1 > side2) && (side1 > side3)) {
                        return 2;
                    }
                    if (side2 > side3) {
                        return 1;
                    }
                    return 3;
                }
                if ((pos1 >= 0) && (pos2 >= 0)) {
                    if (side1 > side2) {
                        return 2;
                    }
                    return 1;
                }
            }
            if ((pos2 >= 0) && (pos3 >= 0))
            {
                if (side1 > side3)
                {
                    return 2;
                }
                return 3;
            }
            if(has_right_marker) {
                if ((pos1 >= 0) && (pos3 >= 0)) {
                    if (side2 > side3) {
                        return 2;
                    }
                    return 3;
                }
            }
            if (pos3 >= 0)
            {
                return 3;
            }
            if(has_right_marker) {
                if (pos1 >= 0) {
                    return 1;
                }
            }
            if (pos2 >= 0)
            {
                return 2;
            }
            return -1;
        }
        return -1;
    }
    /**
     * Round:  userChoosenTrain,  To provide human player an option to choose a train and get help and all
     * @param
     * @return Train Number 1 for Human Train
     *             Train Number 2 for Computer Train
     *             Train Number 3 for Mexican Train
     * @date 12/08/2021
     */
    public int getUserChosenTrain()
    {
        // Tile object
        Tiles train_tile;

        // tile indexs
        int tile_index = -1;

        int own_tile_index = playerHuman.findGoodTile(ownSideNeeded());
        int mt_tile_index = playerHuman.findGoodTile(mexicanSideNeeded());
        int op_tile_index = playerHuman.findGoodTile(opponentSideNeeded());

        int input;

        // int vector to store all the possible train and code for help and exit
        Vector<Integer> valid_input = new Vector<Integer>();

        // Mexican Orphan Double Train
        if (mt_odt)
        {
            proceedNextTurn = false;
            System.out.println( " (3) ORPHAN DOUBLE MEXICAN TRAIN " );
            if (mt_tile_index >= 0)
            {
                valid_input.add(3);
            }
            else
            {
                userChosenTrain = -1;
                return -1;
            }
        }
        // Human Orphan Double Train
        if (ht_odt)
        {
            proceedNextTurn = false;
            if (own_tile_index >= 0)
            {
                valid_input.add(1);
            }
            else
            {
                userChosenTrain = -1;
                return -1;
            }
        }
        // Computer Orphan Double train
        if (ct_odt)
        {
            proceedNextTurn = false;
            if (op_tile_index >= 0)
            {
                valid_input.add(2);
            }
            else
            {
                userChosenTrain = -1;
                return -1;
            }
        }
        // If orphan double does not exist
        if ((!mt_odt && !ct_odt && !ht_odt))
        {
            if (own_tile_index >= 0)
            {
                valid_input.add(1);
            }
            if (mt_tile_index >= 0)
            {
                valid_input.add(3);
            }
            // push train code for opponent train too if it has a marker
            if (has_left_marker)
            {
                if (op_tile_index >= 0)
                {
                    valid_input.add(2);
                }
            }
            if ((!has_left_marker) && (own_tile_index < 0) && (mt_tile_index < 0))
            {
                userChosenTrain = -1;
                return -1;
            }
            if ((op_tile_index < 0) && (mt_tile_index < 0) && (own_tile_index < 0))
            {
                userChosenTrain = -1;
                return -1;
            }
        }
        valid_input.add(4);
        gameRoundView.askToChooseTrain(valid_input);

        return -2;
    }

    // getter for user choosen train
    public  int  getTrainChoice(){
        return userChosenTrain;
    }

    /**
     * Round:  processTrainInuput,
     * @param input, train number
     * @return Train Number 1 for Human Train
     *             Train Number 2 for Computer Train
     *             Train Number 3 for Mexican Train, none -1
     * @date 12/08/2021
     */
    public int processTrainInput(int input){
        if (input == 1)
        {
            userChosenTrain = 1;
            return 1;
        }
        else if (input == 2)
        {
            userChosenTrain = 2;
            return 2;
        }
        else if (input == 3)
        {
            userChosenTrain = 3 ;
            return 3;
        }
        else if (input == 4)
        {
            userChosenTrain = chooseTrain();
            return userChosenTrain;
        }
        userChosenTrain = -1;
        return -1;

    }
    /**
     * Round:  setOrphanDouble, To set the orphan double train checking the condition
     * @param
     * @return
     * @date 12/08/2021
     */
    public void setOrphanDouble()
    {
        if (setup.getLeft().getFirstSide() != engine)
        {
            if (setup.getLeft().checkDouble())
            {
                ct_odt = true;
            }
            else
            {
                ct_odt = false;
            }
        }

        if ((setup.getRight().getFirstSide() != engine) && (setup.getRight().getFirstSide() != engine))
        {
            if (setup.getRight().checkDouble())
            {
                ht_odt = true;
            }
            else
            {
                ht_odt = false;
            }
        }

        if (setup.getFromMexicanTrain().getFirstSide() != engine)
        {
            if (setup.getFromMexicanTrain().checkDouble())
            {
                mt_odt = true;
            }
            else
            {
                mt_odt = false;
            }
        }
    }
    /**
     * Round:  playTurn, determine turn, play and orphan double
     * @param player, Player object
     * @return
     * @date 12/08/2021
     */
    public int playTurn(Player player)
    {
        // Human turn
        if (turn == 1) {
            if(proceedNextTurn){
                proceedNextTurn = false;
                int i = getUserChosenTrain();
                if(i == -1){
                    message = "No Valid moves for Orphan double. Draw tile";
                    gameRoundView.updateGameRoundView();
                    proceedNextTurn = true;
                }
            }else
                {
                proceedNextTurn = true;
            }
        }
        // Computer Turn
        else
        {

        }
        // Set Orphan Double if necessary
        setOrphanDouble();
        return 0;
    }

    /**
     * Round: playComputerTurn, play the computer turn and return true after played
     * @param player, Player object
     * @return if turn ended true else false
     * @date 12/08/2021
     */
    public  boolean playComputerTurn(Player player){

        // tile object
        Tiles train_tile;

        // tile index
        int tile_index;

        // check if any train is eligible to play
        int n = chooseTrain();

        // draw a tile if none of the tiles can be played in any eligible train
        if (n == -1)
        {
            message = "Computer does not have any matching tile for any eligible train to play, so draw for a computer \n";
            Tiles t = deck.getFromBoneyard();

            // add to hand
            player.addTilesToHand(t);

            message+= "Computer drew a tile " + t.printTiles() + " from the boneyard\n";
            addLog(true, 2,message);
            gameRoundView.updateGameRoundView();

            //check if the drawn tile can be played
            int j = chooseTrain();
            if (j >= 0)
            {
                message += "\nMatching tile " + t.printTiles() + " drawn from boneyard. Computer can now play its turn.";
                addLog(true, 2,message);
                gameRoundView.updateGameRoundView();
                return false;
            }
            // set marker
            else
            {
                // change turn
                turn = 1;
                has_left_marker = true;
                message += "\nComputer drawn tile " +  t.printTiles() + " could not be played so computer passed it's turn";
                addLog(true, 2,message);
                gameRoundView.updateGameRoundView();
            }
        }
        else
        {
            // Human Train
            if (n==1){
                tile_index = player.findGoodTile(opponentSideNeeded());
                train_tile = player.getDemandedTile(tile_index);
                if (train_tile.getSecondSide() == opponentSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertRight(train_tile);
                player.deleteTilesFromHand(tile_index);
                //printDetails();
                if (train_tile.checkDouble())
                {
                    message = "Computer played double " + train_tile.printTiles()+ " tile on Human side because it has a marker and computer has matching double tile";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                    checkOrphan = false;
                    return false;
                }
                else
                {
                    turn = 1;

                    message += "Computer played tile " + train_tile.printTiles() + "  on Human side because it has marker and computer has matching tile with high pip sum";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                }
            }
            // Computer Train
            else if (n == 2)
            {
                tile_index = player.findGoodTile(ownSideNeeded());
                train_tile = player.getDemandedTile(tile_index);

                if (train_tile.getFirstSide() == ownSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertLeft(train_tile);
                has_left_marker = false;
                player.deleteTilesFromHand(tile_index);
                if (train_tile.checkDouble())
                {
                    message = "Computer played double tile " +  train_tile.printTiles() + " on its own side because it has a matching double tile";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                    checkOrphan = false;
                    return false;
                }
                else
                {
                    turn = 1;
                    message = "Computer played  tile " +  train_tile.printTiles() + " on  it's own train because based on the high sum of pip";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                }
            }
            // Mexican Train
            else if (n == 3)
            {
                tile_index = player.findGoodTile(mexicanSideNeeded());
                train_tile = player.getDemandedTile(tile_index);
                if (train_tile.getSecondSide() == mexicanSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertInMexicanTrain(train_tile);
                player.deleteTilesFromHand(tile_index);
                if (train_tile.checkDouble())
                {
                    message = "Computer played double tile " +  train_tile.printTiles() + " on Mexican side because computer has matching double tile with that of Mexican train";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                    checkOrphan = false;
                    return false;
                }
                else
                {
                    turn = 1;
                    message = "Computer played tile " +  train_tile.printTiles() + " on Mexican train because it has got matching tile with high sum of pips";
                    addLog(true, 2,message);
                    gameRoundView.updateGameRoundView();
                }
            }
        }
        return true;
    }
    /**
     * Round: drawTileFromBoneyard, if an available tile can't be played and tile has to be drawn draw from boneyard, update hand and boneyard
     * @param
     * @return
     * @date 12/08/2021
     */
    public void drawTileFromBoneyard(){
        message = "Human does not have any matching tile for any eligible train to play. Draw from a boneyard\n";
        addLog(true, 1,message);
        gameRoundView.updateGameRoundView();

        // tompost tile from the boneyard
        Tiles t = deck.getFromBoneyard();
        getPlayerHuman().setDrawn(true);

        // add to player's hand
        playerHuman.addTilesToHand(t);

        // see if the drawn tile can be played
        int j = chooseTrain();

        // play if it can be played
        if (j >= 0)
        {
            message += "\nHuman drew a matching tile " +  t.printTiles() +  " from boneyard.\n";
            addLog(true, 1,message);
            gameRoundView.updateGameRoundView();
            turn = 1;

            startHumanTurn();
        }
        // if it can't be played, set the marker and change the turn
        else
        {
            message += "\nHuman drew a tile " + t.printTiles() + " from boneyard.\n" + "\nHuman drawn tile cannot be played so passing recommended";
            addLog(true, 1,message);
            gameRoundView.updateGameRoundView();
            has_right_marker = true;
            startHumanTurn();
        }
    }
    /**
     * Round: passTurn, pass Human turn and change turn
     * @param
     * @return
     * @date 12/08/2021
     */
    public void passTurn(){
        message = "Passing Human Turn ";
        turn = 2;
        gameRoundView.updateGameRoundView();
        proceedNextTurn = true;
    }

    /**
     * Round: playHumanTurn
     * @param player Player object, and train choosen by user
     * @return
     * @date 12/08/2021
     */
    public void playHumanTurn(Player player, int userChosenTrain){

        // tile object
        Tiles train_tile;
        // tile index
        int tile_index;

        // if none of the train was eligible, prompt the human to draw a tile
        if (userChosenTrain == -1)
        {
//                message = "No Valid move available. Draw tile";
//                gameRoundView.updateGameRoundView();
        }
        else
        {
            // Human train
            if (userChosenTrain == 1)
            {
                tile_index = player.selectTiles(ownSideNeeded());
                train_tile = player.getDemandedTile(tile_index);
                if (train_tile.getSecondSide() == ownSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertRight(train_tile);
                has_right_marker = false;
                player.deleteTilesFromHand(tile_index);
                if (train_tile.checkDouble())
                {
                    message = "Human played double tile" + train_tile.printTiles()+ "on Human side of train so human can play again.";
                    addLog(true, 1,message);
                    gameRoundView.updateGameRoundView();
                    checkOrphan = false;
                    startHumanTurn();
                }
                else
                {
                    turn = 2;
                    message = "Human played tile " + train_tile.printTiles()+ " on Human side of train";
                    addLog(true, 1,message);
                    gameRoundView.updateGameRoundView();
                }
            }
            // Computer train
            if (userChosenTrain == 2)
            {
                tile_index = player.selectTiles(opponentSideNeeded());
                train_tile = player.getDemandedTile(tile_index);
                if (train_tile.getFirstSide() == opponentSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertLeft(train_tile);
                player.deleteTilesFromHand(tile_index);
                if (train_tile.checkDouble())
                {
                    message = "Human played double tile " + train_tile.printTiles() + " on Computer Side so Human can play again";
                    addLog(true, 1,message);
                    checkOrphan = false;
                    gameRoundView.updateGameRoundView();
                    startHumanTurn();
                }

                else
                {
                    turn = 2;
                    message = "Human just played a tile " +  train_tile.printTiles() + "  on Computer Side";
                    addLog(true, 1,message);
                    gameRoundView.updateGameRoundView();
                }
            }
            // Mexican Train
            if (userChosenTrain == 3)
            {
                tile_index = player.selectTiles(mexicanSideNeeded());
                train_tile = player.getDemandedTile(tile_index);
                if (train_tile.getSecondSide() == mexicanSideNeeded())
                {
                    train_tile.reverseTile();
                }
                setup.insertInMexicanTrain(train_tile);
                player.deleteTilesFromHand(tile_index);
                if (train_tile.checkDouble())
                {
                    message = "Human played double tile " +  train_tile.printTiles() + " on Mexican train so human can play again";
                    addLog(true, 1,message);
                    gameRoundView.updateGameRoundView();
                    checkOrphan = false;
                    startHumanTurn();

                }
                else
                {
                    turn = 2;
                    message = "Human played tile " + train_tile.printTiles() + " on Mexican train";
                    addLog(true, 1,message);
                    gameRoundView.updateGameRoundView();

                }
            }
        }

    }
    /**
     * Round:getTileFromBoneyard
     * @param
     * @return topmost tile from boneyard
     * @date 12/08/2021
     */
    public Tiles getTileFromBoneyard()
    {
        return this.deck.getFromBoneyard();
    }

    /**
     * Round:  getHumanScore
     * @param
     * @return human score
     * @date 12/08/2021
     */
    public int getHumanScore()
    {
        return this.playerHuman.calculateScore();
    }
    /**
     * Round:  getComputerScore
     * @param
     * @return computer score
     * @date 12/08/2021
     */
    public int getComputerScore()
    {
        return this.playerComputer.calculateScore();
    }

    /**
     * Round: isBoneyardEmpty
     * @param
     * @return boolean to check if the boneyard is empty
     * @date 12/08/2021
     */
    public  boolean isBoneyardEmpty()
    {
        return (this.deck.getDeck()).isEmpty();
    }

}
