package mco1.Controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mco1.Model.Board;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import mco1.Model.Locations.Miner;

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

    @FXML Button setGoldButton;
    @FXML Button setPitButton;
    @FXML Button setBeaconButton;
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
        this.minerPane = null;
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
                panes[i][j].setOnMousePressed(this::handle);
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

        // Initialize the View
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
            if(this.initState == 1)
            {
                // Set the Gold Pot
            }
            else if(this.initState == 2)
            {
                // Set the Pits
            }
            else if(this.initState == 3)
            {
                // Set Beacons
            }
        }
    }

    // ActionEvent Handler
    private void handle(ActionEvent ev)
    {
        // Check for Button
        if(ev.getSource() instanceof Button)
        {
            // Button Handler
            if(((Button) ev.getSource()).getId().equals(setGoldButton.getId()))
            {
                this.initState = 1;
            }
            else if(((Button) ev.getSource()).getId().equals(setPitButton.getId()))
            {
                this.initState = 2;
            }
            else if(((Button) ev.getSource()).getId().equals(setBeaconButton.getId()))
            {
                this.initState = 3;
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
                    case "Breadth First Search": this.algorithm = 2; break;
                }
                updateSimulationButtonState();
            }
        }
    }

    // Update Start Simulation Button State
    private void updateSimulationButtonState()
    {
        boolean doEnable = true;
        if(this.algorithm == 0)
            doEnable = false;

        if(doEnable)
            startSimulationButton.setDisable(false);
        else
            startSimulationButton.setDisable(true);
    }

    // Update Miner Direction
    private void updateView()
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
        if(this.minerPane != null)
            this.minerPane.getChildren().clear();
        minerImage.fitWidthProperty().bind(panes[minerRow][minerCol].widthProperty());
        minerImage.fitHeightProperty().bind(panes[minerRow][minerCol].heightProperty());
        this.panes[minerRow][minerCol].getChildren().add(minerImage);
        this.minerPane = this.panes[minerRow][minerRow];
    }
}
