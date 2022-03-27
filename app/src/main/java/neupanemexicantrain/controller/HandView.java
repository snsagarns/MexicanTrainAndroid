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
import android.widget.LinearLayout;

import java.util.Vector;

import ramapo.edu.neupanemexicantrain.model.Round;
import ramapo.edu.neupanemexicantrain.model.Tiles;

/**
 * BoardView Class
 * A class that builds hand info such as humand, computer and boneyard.
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class HandView {
    // variables
    Vector<Tiles> hand;
    Round round;
    Vector<Integer> trainChoice;

    Activity activity;
    /**
     * PUBLIC CONSTRUCTOR
     */
    public HandView(Activity activity,Round round) {
        this.activity = activity;
        this.round = round;

    }

    /**
     * Setting hanfd to display to view
     * @param hand The board that contains game tiles
     * @return void
     */
    public void setHand(Vector<Tiles> hand){
        this.hand = hand;
    }
    public void setHand(Vector<Tiles> hand,Vector<Integer> trainChoice){
        this.hand = hand;
        this.trainChoice = trainChoice;
    }

    /**
     * Updating view to display board info
     * @param viewId The Id of the view to be updated
     * @param enableSelection true if tiles can be selected
     * @return void
     */
    public void updateHandView(int viewId, boolean enableSelection){
        LinearLayout layout = activity.findViewById(viewId);
        layout.removeAllViews();
        int index=0;
        for(Tiles t: hand){
            boolean enable = enableSelection;
            if(enableSelection && trainChoice!=null) {
                enable = false;
                for (Integer choice : trainChoice) {
                    switch (choice) {
                        case 1:
                            if ((t.getFirstSide() == round.ownSideNeeded()) || (t.getSecondSide() == round.ownSideNeeded())) {
                                enable = true;
                            }
                            break;
                        case 2:
                            if ((t.getFirstSide() == round.opponentSideNeeded()) || (t.getSecondSide() == round.opponentSideNeeded())) {
                                enable = true;
                            }
                            break;
                        case 3:
                            if ((t.getFirstSide() == round.mexicanSideNeeded()) || (t.getSecondSide() == round.mexicanSideNeeded())) {
                                enable = true;
                            }
                            break;
                    }
                }
            }
            TileView tileView = new TileView(activity,round,t,index,false,enable);

            layout.addView(tileView.getView());
            index++;
        }
    }
}