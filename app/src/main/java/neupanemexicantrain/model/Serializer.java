/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      3. Mexican Train Java/Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         12/08/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import androidx.core.app.ActivityCompat;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import ramapo.edu.neupanemexicantrain.R;

/**
 * Serializer class
 * Implemented for loading and saving the game from and to a serialized file
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */

public class Serializer {
    private String filename;
    private File sdCard;
    Activity activity;

    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    public Serializer(Activity activity){
        this.activity = activity;
        filename="save";
    }
    /**
     * Setup: saveGame, save a game to a file provided by a user.
     * @param filename
     * @param  game, NewGame object
     * @return
     * @date 12/08/2021
     */

    public boolean saveGame(String filename, NewGame game){
        this.filename = filename;
        Round round = game.getRound();

        try {
            FileOutputStream outputStream = activity.openFileOutput(filename+".txt", Context.MODE_PRIVATE);
            String savedDetails = "";
            savedDetails +="Round: " + round.getRoundNumber()+"\n";
            savedDetails +="Computer:\n";
            savedDetails +="Score: " + round.getComputerScore() + "\n";
            savedDetails +="Hand: ";

            Computer computerPlayer = round.getPlayerComputer();

            for(Tiles tile:computerPlayer.getHand().getHandTiles()){
                if(tile.checkReverse())
                        savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                    else
                        savedDetails +=tile.getFirstSide()+"-"+tile.getFirstSide()+" ";
            }
            savedDetails +="\n";
            savedDetails +="Train: ";
            if(round.getHasLeftMarker()){
                savedDetails +="M ";
            }

            int second_loop=0;
            Setup setup = round.getSetup();
            int i = 0;
            for(Tiles tile: setup.getTrainTiles()){
                if(tile.getFirstSide() == round.getEngineNumber() && tile.getSecondSide() == round.getEngineNumber()){
                    second_loop = i;
                    break;
                }else{
                    if(tile.checkReverse())
                        savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                    else
                        savedDetails +=tile.getFirstSide()+"-"+tile.getSecondSide()+" ";

                }
                i++;
            }

            savedDetails +=round.getEngineNumber()+"-"+round.getEngineNumber();
            savedDetails +="\n";
            savedDetails +="Human:\n";
            savedDetails +="Score: "+round.getHumanScore()+"\n";
            savedDetails +="Hand: ";

            Human humanPlayer = round.getPlayerHuman();
            for(Tiles tile: humanPlayer.getHand().getHandTiles()){
                if(tile.checkReverse())
                        savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                    else
                        savedDetails +=tile.getFirstSide()+"-"+tile.getSecondSide()+" ";

            }

            savedDetails +="\n";
            savedDetails +="Train: ";

            for(int j = second_loop; j<setup.getTrainTiles().size(); j++){
                Tiles tile = setup.getTrainTiles().get(j);

                if(tile.checkReverse())
                    savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                else
                    savedDetails +=tile.getFirstSide()+"-"+tile.getSecondSide()+" ";

            }
            if(round.getHasRightMarker()){
                savedDetails +="M\n";
            }

            savedDetails +="\nMexican Train: ";

            for(Tiles tile: setup.getMexicanTrain()){
                                    if(tile.checkReverse())
                        savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                    else
                        savedDetails +=tile.getFirstSide()+"-"+tile.getSecondSide()+" ";

            }
            savedDetails +="\n";
            savedDetails +="Boneyard: ";

            for(Tiles tile: round.getDeck().getDeck()){
                                    if(tile.checkReverse())
                        savedDetails +=tile.getSecondSide()+"-"+tile.getFirstSide()+" ";
                    else
                        savedDetails +=tile.getFirstSide()+"-"+tile.getSecondSide()+" ";

            }
            savedDetails +="\n";


            if(round.getTurn()==2){
                savedDetails +="Next Player: Computer\n";
            }else {
                savedDetails +="Next Player: Human\n";
            }

            savedDetails +="Game Human Score: "+game.getHumanScore()+"\n";
            savedDetails +="Game Computer Score: "+game.getComputerScore()+"\n";

            savedDetails +="Human Orphan Double: " + round.getOrphanDouble_H() + "\n";
            savedDetails +="Computer Orphan Double: " + round.getOrphanDouble_C() + "\n";
            savedDetails +="Mexican Orphan Double: " + round.getOrphanDouble_M() + "\n";

            outputStream.write(savedDetails.getBytes());

            outputStream.close();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ERROR");
            System.out.println("ERROR: "+e.getMessage());
        }
        return false;
    }

    public void viewSavedState(){
        this.filename = filename;

        SavedState savedState = new SavedState();

        try {
            InputStream inputStream;
            if(filename.contains("case")){
                Resources res = activity.getResources();

                switch (filename){
                    case "case1.txt":
                        inputStream = res.openRawResource(R.raw.case1);
                        break;
                    case "case2.txt":
                        inputStream = res.openRawResource(R.raw.case2);
                        break;
                    case "case3.txt":
                        inputStream = res.openRawResource(R.raw.case3);
                        break;
                    default:
                        inputStream = res.openRawResource(R.raw.case1);
                        break;

                }
            }else{
                inputStream = activity.openFileInput(filename);
            }

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                System.out.println("Read File");

                String receiveString = "";
                while ((receiveString = bufferedReader.readLine())!= null){
                    System.out.println("String : "+ receiveString);
//                    stringBuilder.append("\n").append(receiveString);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Setup: loadGame, load a game and setup the tiles and train from a resource file
     * @param filename
     * @return
     * @date 12/08/2021
     */

    public SavedState loadGame(String filename){
        this.filename = filename;
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CODE);

        SavedState savedState = new SavedState();
        try {
            InputStream inputStream;
            if(filename.contains("case")){
                Resources res = activity.getResources();

                switch (filename){
                    case "case1.txt":
                        inputStream = res.openRawResource(R.raw.case1);
                        break;
                    case "case2.txt":
                        inputStream = res.openRawResource(R.raw.case2);
                        break;
                    case "case3.txt":
                        inputStream = res.openRawResource(R.raw.case3);
                        break;
                    default:
                        inputStream = res.openRawResource(R.raw.case1);
                        break;

                }
            }else{
                inputStream = activity.openFileInput(filename);
            }

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveString ="";
                String[] splitString;

                viewSavedState();
            //Round
                receiveString = bufferedReader.readLine();
                splitString = receiveString.split(" ");
                savedState.roundNumber = Integer.parseInt(splitString[1]);

                if (savedState.roundNumber < 11)
                {
                    savedState.engineNumber = 10 - savedState.roundNumber;
                }
                else
                {
                    savedState.engineNumber = 10 - savedState.roundNumber % 10;
                }
            //Computer
                receiveString = bufferedReader.readLine();
            //Score
                receiveString = bufferedReader.readLine();
                System.out.println("String 2:"+ receiveString);
                splitString = receiveString.split(" ");
                System.out.println("String 11sCORE:"+Integer.parseInt(splitString[1]));
                savedState.computerScore = Integer.parseInt(splitString[1]);

             //Hand
                receiveString = bufferedReader.readLine();

                for (String string: receiveString.split(" ")){
                    if(string.contains("Hand")){
                        continue;
                    }
                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);

                    savedState.computerHand.add(new Tiles(firstSide,secondSide));
                }
            //Train
                receiveString = bufferedReader.readLine();
                for (String string: receiveString.split(" ")){
                    if(string.contains("Train")){
                        continue;
                    }

                    if(string.contains("M")){
                        savedState.leftMarker = true;
                        continue;
                    }

                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);
                    if(firstSide==savedState.engineNumber && secondSide==savedState.engineNumber) {
                        continue;
                    }

                    savedState.train.add(new Tiles(firstSide,secondSide));

                }
            //Human
                receiveString = bufferedReader.readLine();
            //Score
                receiveString = bufferedReader.readLine();
                System.out.println("String 2:"+ receiveString);
                splitString = receiveString.split(" ");
                System.out.println("String 11sCORE:"+Integer.parseInt(splitString[1]));
                savedState.humanScore = Integer.parseInt(splitString[1]);
            //Hand
                receiveString = bufferedReader.readLine();
                for (String string: receiveString.split(" ")){
                    if(string.contains("Hand")){
                        continue;
                    }
                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);

                    savedState.humanHand.add(new Tiles(firstSide,secondSide));
                }
                receiveString = bufferedReader.readLine();
                for (String string: receiveString.split(" ")){
                    if(string.contains("Train")){
                        continue;
                    }

                    if(string.contains("M")){
                        savedState.rightMarker = true;
                        continue;
                    }
                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);
                    savedState.train.add(new Tiles(firstSide,secondSide));
                }

            //Mexican Train
                receiveString = bufferedReader.readLine();
                for (String string: receiveString.split(" ")){
                    if(string.contains("Mexican") || string.contains("Train")){
                        continue;
                    }
                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);

                    savedState.mexicanTrain.add(new Tiles(firstSide,secondSide));
                }

            //Boneyard
                receiveString = bufferedReader.readLine();
                for (String string: receiveString.split(" ")){
                    if(string.contains("Boneyard")){
                        continue;
                    }
                    String[] sides = string.split("-");
                    int firstSide = Integer.parseInt(sides[0]);
                    int secondSide = Integer.parseInt(sides[1]);

                    savedState.boneyard.add(new Tiles(firstSide,secondSide));
                }
            //Next Player
                receiveString = bufferedReader.readLine();
                System.out.println("String Check:"+ receiveString);
                splitString = receiveString.split(" ");
                if(splitString[2].contains("Computer")){
                    savedState.turn = 2;
                }else{
                    savedState.turn =1;
                }

                for(Tiles t:savedState.train){
                    t.printTiles();
                }
                System.out.println("Mexican Train");
                for(Tiles t:savedState.mexicanTrain){
                    t.printTiles();
                }
                System.out.println("Human hand");
                for(Tiles t:savedState.humanHand){
                    t.printTiles();
                }
                System.out.println("Computer Hand");
                for(Tiles t:savedState.computerHand){
                    t.printTiles();
                }

                System.out.println("Boneyard");
                for(Tiles t:savedState.boneyard){
                    t.printTiles();
                }

                inputStream.close();
                savedState.valid = true;
            }else {
                savedState.valid= false;
                System.out.println("Unable to read file");
            }

        }catch (Exception e){
            savedState.valid = false;
            e.printStackTrace();
        }
        return savedState;
    }
}
