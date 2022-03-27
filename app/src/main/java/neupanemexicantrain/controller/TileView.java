package ramapo.edu.neupanemexicantrain.controller;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.service.quicksettings.Tile;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import ramapo.edu.neupanemexicantrain.R;
import ramapo.edu.neupanemexicantrain.model.Round;
import ramapo.edu.neupanemexicantrain.model.Tiles;

/**
 * TileView Class
 * A class that sets the basic tile
 * Author: Sagar Neupane
 * Project: Mexican Train in Java Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class TileView {
    // Declaring instances of objects and class variables
    Tiles tile;
    Activity activity;
    CardView TileCardView;
    Round round;
    int tileIndex;
    /**
     * Getter for TileCardView
     * @param , View CardView that contains tile details
     */
    public View getView() {
        return TileCardView;
    }
    /**
     * Setter for TileCardView
     * @param, void
     */
    public void setTile(Tiles tile){
        this.tile = tile;
    }

    /**
     * PUBLIC CONSTRUCTOR
     */
    public TileView(Activity activity, Tiles tile, boolean isVerticalDouble) {
        this.activity = activity;
        this.tile = tile;

        updateTileView(isVerticalDouble, false);
    }


    /**
     * PUBLIC CONSTRUCTOR
     *
     */
    public TileView(Activity activity, Round round, Tiles tile, int tileIndex,boolean isVerticalDouble, boolean enableSelection) {
        this.activity = activity;
        this.tile = tile;
        this.round = round;
        this.tileIndex = tileIndex;

        updateTileView(isVerticalDouble, enableSelection);
    }


    /**
     * Updating Tile View with updates
     * @param, pip The pip number to be dispalyed
     * @param, view View on which pip is to be displayed
     * @return void
     */
    public void updateTileView(boolean isVerticalDouble,boolean enableSelection ) {
        TileCardView = new CardView(activity);

        TileCardView.setCardBackgroundColor(Color.WHITE);

        TileCardView.setRadius(20);
        if (enableSelection) {


        }
        // Darken Selected Cardview
        if(enableSelection && tileIndex==round.getPlayerHuman().getSelectedTileIndex()){
            TileCardView.setCardBackgroundColor(Color.GRAY);
        }
        // Select Tile when clicked
        TileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(round!=null && enableSelection)
                {
                    round.getPlayerHuman().setSelectedTileIndex(tileIndex);
                    round.gameRoundView.updateGameRoundView();
                }
            }
        });

        // Set Layout Params to apply to cardview
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(32,32,32,32 );
        layoutParams.gravity = Gravity.CENTER;
        TileCardView.setLayoutParams(layoutParams);

        // Set Layout Params to apply to Cardviews children
        LinearLayout layout = new LinearLayout(activity);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(8,8,8,8);

        TextView textViewFirst = new TextView(activity);
        textViewFirst.setTextColor(activity.getResources().getColor(R.color.purple_500));

        textViewFirst.setLayoutParams(layoutParams);

        TextView textViewSecond = new TextView(activity);
        textViewSecond.setTextColor(activity.getResources().getColor(R.color.purple_500));
        textViewSecond.setLayoutParams(layoutParams);

//        layoutParams.height = 200;
//        layoutParams.width = 200;

        ImageView imageViewFirst = new ImageView(activity);
//        textViewFirst.setTextColor(Color.BLACK);
        imageViewFirst.setLayoutParams(layoutParams);

        ImageView imageViewSecond = new ImageView(activity);
//        textViewSecond.setTextColor(Color.BLACK);
        imageViewSecond.setLayoutParams(layoutParams);


        // Divider for seperation of pips
        View viewDivider = new View(activity);
        int dividerHeight = (int) activity.getResources().getDisplayMetrics().density * 4; // 1dp to pixels
        viewDivider.setBackgroundColor(Color.parseColor("#000000"));


        View viewDivider2 = new View(activity);
        viewDivider2.setBackgroundColor(Color.parseColor("#000000"));

        if(layout != null) {
            int first = tile.getFirstSide();
            int second = tile.getSecondSide();


            // If tile is double and can be displayed as vertical double, set orientation accordingly
            if(isVerticalDouble && tile.checkDouble()){
                layout.setOrientation(LinearLayout.VERTICAL);
                viewDivider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4,dividerHeight));
                viewDivider2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4,dividerHeight));

            }else {
                layout.setOrientation(LinearLayout.HORIZONTAL);
                viewDivider.setLayoutParams(new LinearLayout.LayoutParams(4, ViewGroup.LayoutParams.MATCH_PARENT,dividerHeight));
                viewDivider2.setLayoutParams(new LinearLayout.LayoutParams(4, ViewGroup.LayoutParams.MATCH_PARENT,dividerHeight));

            }

            // set first and second pips in correct order
            if(tile.checkReverse()) {
                first = second;
                second=tile.getFirstSide();
            }
            textViewFirst.setText(Integer.toString(first));
            textViewSecond.setText(Integer.toString(second));
//            textViewFirst.setBackgroundTintMode(activity.getResources().getColorStateList(R.color.b));
            updatePipView(first,imageViewFirst);
            updatePipView(second,imageViewSecond);

            // Adding views as children of cardview
            layout.addView(imageViewFirst);
            layout.addView(viewDivider);
            layout.addView(imageViewSecond);
            layout.addView(textViewFirst);
            layout.addView(viewDivider2);
            layout.addView(textViewSecond);
        }
        if(TileCardView != null){
            TileCardView.addView(layout);
        }

    }

    /**
     * Updating view of Pip as background in view
     * @param pip The pip number to be dispalyed
     * @param view View on which pip is to be displayed
     * @return void
     */
    public void updatePipView(int pip, ImageView view){
        switch(pip){
            case 0:
                view.setBackgroundResource(R.drawable.pip_zero);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.pip_one);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.pip_two);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.pip_three);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.pip_four);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.pip_five);
                break;
            case 6:
                view.setBackgroundResource(R.drawable.pip_six);
                break;
            case 7:
                view.setBackgroundResource(R.drawable.pip_seven);
                break;
            case 8:
                view.setBackgroundResource(R.drawable.pip_eight);
                break;
            case 9:
                view.setBackgroundResource(R.drawable.pip_nine);
                break;
            default:
                view.setBackgroundResource(R.drawable.pip_zero);
                break;
        }
    }

}