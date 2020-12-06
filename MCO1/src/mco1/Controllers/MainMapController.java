package mco1.Controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mco1.Model.Board;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import mco1.Model.Locations.*;

import java.beans.beancontext.BeanContext;
import java.io.File;
import java.util.ArrayList;

public class MainMapController implements EventHandler<Event>
{
    // Size N
    private int dimension;

    // Board Instance
    private Board mainBoard;

    // Algorithm
    private int algorithm;

    // JavaFX Instances
    @FXML
    AnchorPane mainPane;

    @FXML
    ScrollPane mainScrollPane;

    @FXML Button nextStepButton;
    @FXML CheckBox checkGold;
    @FXML CheckBox checkBeacon;
    @FXML CheckBox checkPit;
    @FXML CheckBox checkSlowMode;
    @FXML Button startSimulationButton;
    @FXML ComboBox comboAlgo;
    @FXML Label counterDisplay;
    @FXML Label directionLabel;
    @FXML Label displayRotates;
    @FXML Label displayScans;
    @FXML Label displayMoves;
    @FXML Label displayCurrentMode;
    @FXML Button commandRotate;
    @FXML Button commandScan;
    @FXML Button commandMove;

    Pane minerPane;
    ArrayList<Pane> pitPanes;
    ArrayList<Pane> beaconPanes;
    Pane goldPane;

    GridPane mainGridPane;

    private int initState;

    private Pane[][] panes;

    public MainMapController(int n)
    {
        this.mainBoard = new Board(n);
        this.initState = 0;
        this.dimension = n;
        this.algorithm = 0;
        this.pitPanes = new ArrayList<Pane>();
        this.beaconPanes = new ArrayList<Pane>();
        this.goldPane = null;
    }

    @FXML
    public void initialize()
    {
        // Create the panes
        panes = new Pane[this.dimension][this.dimension];

        // Create the GridPane
        this.mainGridPane = new GridPane();
        mainScrollPane.setContent(mainGridPane);

        // Set Size Constraints
        for(int i = 0; i < this.dimension; i++)
        {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(64);
            mainGridPane.getRowConstraints().add(rowConstraints);
        }

        for(int i = 0; i < this.dimension; i++)
        {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(64);
            mainGridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < this.dimension; i++)
            for(int j = 0; j < this.dimension; j++)
            {
                panes[i][j] = new Pane();
                panes[i][j].setOnMouseClicked(this::handle);
                panes[i][j].setStyle("-fx-border-color: black; -fx-border-width: 1;");
            }

        // Bind the Panes
        for(int i = 0; i < this.dimension; i++)
        {
            mainGridPane.addRow(i, panes[i]);
        }

        // Setup the Algorithm Picker
        comboAlgo.getItems().add("Random");
        comboAlgo.getItems().add("Breadth First Search");
        comboAlgo.setOnAction(this::handle);

        // Disable the simulation button
        startSimulationButton.setDisable(true);
        commandMove.setDisable(true);
        commandRotate.setDisable(true);
        commandScan.setDisable(true);

        // Initialize the View
        this.minerPane = this.panes[0][0];
        updateView();
    }


    // Event Handler
    public void handle(Event ev)
    {
        if(ev instanceof ActionEvent)
            handle((ActionEvent) ev);

        // Pane Press
        else if(ev.getSource() instanceof Pane)
        {
            Pane source = (Pane) ev.getSource();
            //System.out.println("Coordinates: " + String.valueOf(GridPane.getRowIndex(source)) + " " + String.valueOf(GridPane.getColumnIndex(source)));

            // Return if miner
            if(source.getChildren().size() > 0)
                if(mainBoard.getMinerAgent().getRow() == GridPane.getRowIndex(source) &&
                        mainBoard.getMinerAgent().getCol() == GridPane.getColumnIndex(source))
                    return;

            if(this.initState == 1)
            {
                // Set the Gold Pot
                // Load the image
                Image image = new Image("./mco1/View/POG.png");
                ImageView goldImage = new ImageView(image);

                // Check if there is already a pot
                if(!GoldenSquare.isSet())
                {
                    // Case 1 - No Gold Pot yet
                    this.goldPane = source;
                    mainBoard.placeGold(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);
                }
                else if(mainBoard.getSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1) instanceof Empty)
                {
                    // Case 2 - Gold Pot is already set
                    // Remove old gold pot
                    this.goldPane.getChildren().clear();
                    mainBoard.resetSquare(GridPane.getRowIndex(this.goldPane) + 1, GridPane.getColumnIndex(this.goldPane) + 1);
                    // Set new gold pot
                    this.goldPane = source;
                    mainBoard.placeGold(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);
                }

                // Load image to source
                goldImage.fitWidthProperty().bind(this.goldPane.widthProperty());
                goldImage.fitHeightProperty().bind(this.goldPane.heightProperty());
                this.goldPane.getChildren().add(goldImage);

            }
            else if(this.initState == 2)
            {
                // Check if source already has pit
                if(mainBoard.getSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1) instanceof Pit)
                {
                    // Already has pit
                    // Remove from ArrayList
                    this.pitPanes.remove(source);
                    source.getChildren().clear();
                    mainBoard.resetSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);
                }
                else if(mainBoard.getSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1) instanceof Empty)
                {
                    // Load the image
                    Image image = new Image("./mco1/View/Pit.png");
                    ImageView pitImage = new ImageView(image);

                    // Update model
                    mainBoard.placePit(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);

                    // Create new pit
                    this.pitPanes.add(source);
                    pitImage.fitWidthProperty().bind(source.widthProperty());
                    pitImage.fitHeightProperty().bind(source.heightProperty());
                    source.getChildren().add(pitImage);
                }
            }
            else if(this.initState == 3)
            {
                // Set Beacons
                // Load the Image
                Image image = new Image("./mco1/View/Beacon.png");
                ImageView beaconImage = new ImageView(image);

                if(mainBoard.getSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1) instanceof Empty)
                {
                    // New Beacon
                    // Check if valid location
                    boolean valid = mainBoard.placeBeacon(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);
                    if(valid)
                    {
                        this.beaconPanes.add(source);
                        beaconImage.fitWidthProperty().bind(source.widthProperty());
                        beaconImage.fitHeightProperty().bind(source.heightProperty());
                        source.getChildren().add(beaconImage);
                    }
                }
                else if(mainBoard.getSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1) instanceof Beacon)
                {
                    // Remove Beacon
                    mainBoard.resetSquare(GridPane.getRowIndex(source) + 1, GridPane.getColumnIndex(source) + 1);
                    this.beaconPanes.remove(source);
                    source.getChildren().clear();
                }
            }
            updateCheckers();
            updateButtonStates();
        }
    }

    // ActionEvent Handler
    private void handle(ActionEvent ev)
    {
        // Check for Button
        if(ev.getSource() instanceof Button)
        {
            Button source = (Button) ev.getSource();

            // Input Next State Button
            if(source.getId().equals(nextStepButton.getId()))
            {
                switch(this.initState)
                {
                    case 1:
                        if(!GoldenSquare.isSet()) return; break;
                    case 2:
                        if(!Pit.isSet()) return; break;
                    case 3:
                        if(!Beacon.isSet()) return; break;
                }

                this.initState++;
                updateCheckers();
            }

            // Movement Buttons
            else if(source.getId().equals(commandRotate.getId()))
            {
                mainBoard.rotateMiner();
                updateView();
            }
            else if(source.getId().equals(commandMove.getId()))
            {
                mainBoard.moveMiner();
                updateView();
            }
            else if(source.getId().equals(commandScan.getId()))
            {
                mainBoard.farScan();
                updateView();
            }
        }
        else if(ev.getSource() instanceof ComboBox)
        {
            // ComboBox Handler
            if(((ComboBox) ev.getSource()).getId().equals((comboAlgo.getId())))
            {
                ComboBox comboBox = (ComboBox) ev.getSource();
                switch(comboBox.getValue().toString())
                {
                    case "Random": this.algorithm = 1; break;
                    case "Smart": this.algorithm = 2; break;
                }
                updateButtonStates();
            }
        }
    }

    // Update Start Simulation Button State
    private void updateButtonStates()
    {
        if(GoldenSquare.isSet() &&
            Pit.isSet() && Beacon.isSet())
        {
            if(this.algorithm != 0)
                startSimulationButton.setDisable(false);
            commandScan.setDisable(false);
            commandRotate.setDisable(false);
            commandMove.setDisable(false);
        }
        else
        {
            if(this.algorithm == 0)
                startSimulationButton.setDisable(true);
            commandScan.setDisable(true);
            commandRotate.setDisable(true);
            commandMove.setDisable(true);
        }
    }

    // Update Miner Direction
    public void updateView()
    {
        // Label Displays
        // Get from model
        int direction = mainBoard.getMinerAgent().getFront();
        int moves = mainBoard.getnMoves();
        int scans = mainBoard.getnScans();
        int rotates = mainBoard.getnRotates();

        String facing = null;
        switch(direction)
        {
            case 0:
                facing = new String("East");
                break;
            case 90:
                facing = new String("North");
                break;
            case 180:
                facing = new String("West");
                break;
            case 270:
                facing = new String("South");
        }

        // Update the labels
        directionLabel.setText(facing);
        displayMoves.setText(String.valueOf(moves));
        displayRotates.setText(String.valueOf(rotates));
        displayScans.setText(String.valueOf(scans));
        counterDisplay.setText(String.valueOf(moves + rotates + scans));

        // Update Graphical View
        // Miner Image
        Miner miner = mainBoard.getMinerAgent();
        int minerRow = miner.getRow();
        int minerCol = miner.getCol();

        // Load image based on direction
        Image image;
        ImageView minerImage = new ImageView();
        switch(direction)
        {
            case 0:
                // Load miner right
                image = new Image("./mco1/View/PickaxeRight.png");
                minerImage.setImage(image);
                break;
            case 90:
                // Load miner top
                image = new Image("./mco1/View/PickaxeTop.png");
                minerImage.setImage(image);
                break;
            case 180:
                // Load miner left
                image = new Image("./mco1/View/PickaxeLeft.png");
                minerImage.setImage(image);
                break;
            case 270:
                image = new Image("./mco1/View/PickaxeBottom.png");
                minerImage.setImage(image);
        }
        if(this.minerPane.getChildren().size() > 1)
            this.minerPane.getChildren().remove(1);
        else
            this.minerPane.getChildren().clear();
        minerImage.fitWidthProperty().bind(panes[minerRow][minerCol].widthProperty());
        minerImage.fitHeightProperty().bind(panes[minerRow][minerCol].heightProperty());
        this.panes[minerRow][minerCol].getChildren().add(minerImage);
        this.minerPane = this.panes[minerRow][minerCol];
    }

    // Updates the checkboxes
    private void updateCheckers()
    {
        // Update the nextStateButton
        switch (this.initState)
        {
            case 1:
                nextStepButton.setText("Input Pits");
                displayCurrentMode.setText("Inputting Gold");
                break;
            case 2:
                nextStepButton.setText("Input Beacons");
                displayCurrentMode.setText("Inputting Pits");
                break;
            case 3:
                displayCurrentMode.setText("Inputting Beacons");
                nextStepButton.setText("DONE");
                nextStepButton.setDisable(true);
        }

        if(GoldenSquare.isSet())
            this.checkGold.setSelected(true);
        else if(!GoldenSquare.isSet())
            this.checkGold.setSelected(false);

        if(Pit.isSet())
            this.checkPit.setSelected(true);
        else if(!Pit.isSet())
            this.checkPit.setSelected(false);

        if(Beacon.isSet())
            this.checkBeacon.setSelected(true);
        else if(!Beacon.isSet())
            this.checkBeacon.setSelected(false);
    }

    // Show Alert to User
    public void endSimulation(boolean success)
    {
        if(success)
            displayCurrentMode.setText("Goal Reached!");
        else
            displayCurrentMode.setText("Failed to reach Goal.");

        mainPane.setDisable(true);
    }
}
