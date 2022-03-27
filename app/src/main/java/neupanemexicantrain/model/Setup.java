/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      3. Mexican Train Java/Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         12/08/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

import java.util.Vector;

/**
 * Setup class
 * Implemented for the setup of game board viewing whether it’s Mexican train view or Computer-Human train view, and extracting tiles from setup
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */

public class Setup {
    // train tiles human computer
    private Vector<Tiles> trainTiles;

    // mexican Train tiles
    private Vector<Tiles> mexicanTrain;

    // check if the engine has been inserted
    private boolean insert_engine;

    // returns the Computer-Human train setup
    public Vector<Tiles> getTrainTiles() {
        return trainTiles;
    }

    // returns the Mexican Train Setup
    public Vector<Tiles> getMexicanTrain() {
        return mexicanTrain;
    }

    // sets the Computer-Human Train
    public void setPlayersTrain(Vector<Tiles> train) {
        trainTiles = train;
    }

    // sets the Mexican Train
    public void setMexicanTrain(Vector<Tiles> train) {
        mexicanTrain = train;
    }

    /**
     * Setup: Default constructor initialize the insert_engine to false
     *
     * @param
     * @return
     * @date 12/08/2021
     */
    public Setup() {
        this.insert_engine = false;
        this.trainTiles = new Vector<Tiles>();
        this.mexicanTrain = new Vector<Tiles>();
    }

    /**
     * Setup:: placeEngine,  To insert the engine tile to both Mexican and Computer-Human Train
     *
     * @param e, Tile object e
     * @return
     * @date 12/08/2021
     */
    public void placeEngine(Tiles e) {
        trainTiles.add(e);
        mexicanTrain.add(e);
    }

    /**
     * Setup:: insertLeft, To insert the tile to the Computer Train
     *
     * @param t, Tile object t
     * @return
     * @date 12/08/2021
     */
    public void insertLeft(Tiles t) {
        this.trainTiles.add(0, t);
    }

    /**
     * Setup::  insertRight, To insert the tile to the Human Train
     *
     * @param t, Tile object t
     * @return
     * @date 12/08/2021
     */
    public void insertRight(Tiles t) {
        this.trainTiles.add(t);
    }

    /**
     * Setup:: getLeft  To get the leftmost tile from the Computer train
     *
     * @return leftmost tile
     * @param,
     * @date 12/08/2021
     */
    public Tiles getLeft() {
        return this.trainTiles.get(0);
    }

    /**
     * Setup:: getRight To get the righttmost tile from the Human train
     *
     * @return rightmost tile from human train
     * @param,
     * @date 12/08/2021
     */
    public Tiles getRight() {
        return this.trainTiles.get(trainTiles.size() - 1);
    }

    /**
     * Setup:: getFromMexicanTrain   To get the righttmost tile from the Mexican Train
     *
     * @return rightmost tile from mexican train
     * @param,
     * @date 12/08/2021
     */
    public Tiles getFromMexicanTrain() {
        return this.mexicanTrain.get(mexicanTrain.size() - 1);
    }

    /**
     * Setup:: insertInMexicanTrain To insert provided tile to the right of the Mexican Train
     *
     * @param t, Tile object t
     * @return
     * @date 12/08/2021
     */
    public void insertInMexicanTrain(Tiles t) {
        this.mexicanTrain.add(t);
    }
}