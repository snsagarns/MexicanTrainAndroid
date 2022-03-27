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
import android.graphics.Color;
import android.service.quicksettings.Tile;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.Round;
import ramapo.edu.neupanemexicantrain.model.Tiles;

import java.util.Vector;
/**
 * BoardView Class
 * A class that builds mexican train and game train.
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class BoardView {
    //variables declaration
    Vector<Tiles> board;
    Round round;
    Activity activity;
    /**
     * PUBLIC CONSTRUCTOR
     */
    public BoardView(Activity activity,Round round) {
        this.activity = activity;
        this.round = round;
    }

    /**
     * Setting board to display to view
     * @param board The board that contains game tiles
     * @return void
     */
    public void setBoard(Vector<Tiles> board){
        this.board = board;
    }
    /**
     * Updating view to display board info
     * @param viewId The Id of the view to be updated
     * @param isMarkable true if marker can be placed on the train(For engine train where marker can be placed)
     * @return void
     */
    public void updateBoardView(int viewId, boolean isMarkable){

        LinearLayout layout = activity.findViewById(viewId);
        layout.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(!isMarkable){
            if(round.getHasLeftMarker()){
                TextView textViewLeftMarker = new TextView(activity);
                // textViewLeftMarker.setText("Marker");
                textViewLeftMarker.setTextColor(Color.WHITE);
                textViewLeftMarker.setLayoutParams(layoutParams);
                textViewLeftMarker.setBackgroundResource(R.drawable.marker);

                layout.addView(textViewLeftMarker);
            }
        }
        for(Tiles t: board){
            TileView tileView = new TileView(activity,t,true);
            layout.addView(tileView.getView());
            // boardViews.addElement(tileView);
        }
        if(!isMarkable){
            if(round.getHasRightMarker()){
                TextView textViewRightMarker = new TextView(activity);
                // textViewRightMarker.setText("Marker");
                textViewRightMarker.setTextColor(Color.WHITE);
                textViewRightMarker.setLayoutParams(layoutParams);
                textViewRightMarker.setBackgroundResource(R.drawable.marker);

                layout.addView(textViewRightMarker );
            }
        }
    }
}