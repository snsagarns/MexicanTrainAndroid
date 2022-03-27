/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      3. Mexican Train Java/Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         12/08/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

import java.io.Serializable;
import java.util.Vector;

public class SavedState implements Serializable {
    public boolean valid;
    public int roundNumber ;
    public int humanScore ;
    public int computerScore ;
    public   int engineNumber ;
    public   int turn ;
    public   int gameComputerScore ;
    public   int gameHumanScore ;
    public boolean leftMarker;
    public boolean rightMarker;
    public boolean ht_odt;
    public boolean ct_odt;
    public boolean mt_odt;
    public   Vector<Tiles> train ;
    public   Vector<Tiles> mexicanTrain ;
    public   Vector<Tiles> boneyard ;
    public   Vector<Tiles> humanHand ;
    public   Vector<Tiles> computerHand ;

    SavedState(){
         roundNumber = 0;
         humanScore = 0;
         computerScore = 0;
         engineNumber = 0;
         turn = 1;
         gameComputerScore = 0;
         gameHumanScore = 0;
         train = new Vector<Tiles>();
         mexicanTrain = new Vector<Tiles>();
         boneyard = new Vector<Tiles>();
         humanHand = new Vector<Tiles>();
         computerHand = new Vector<Tiles>();
         valid=false;
    }
}
