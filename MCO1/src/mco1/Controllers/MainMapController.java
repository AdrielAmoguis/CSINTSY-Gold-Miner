package mco1.Controllers;

import mco1.Model.Board;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;

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


    GridPane mainGridPane;

    private int initState;

    private Pane[][] panes;

    public MainMapController(int n)
    {
        this.mainBoard = new Board(n);
        this.initState = 0;
        this.dimension = n;
        this.algorithm = 0;
    }

    @FXML
    public void initialize()
    {
        // Create the panes
        panes = new Pane[this.dimension][this.dimension];

        // Create the GridPane
        this.mainGridPane = new GridPane();
        mainGridPane.setPrefHeight(773);
        mainGridPane.setPrefWidth(1302);
        mainGridPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.getChildren().add(mainGridPane);

        // Set Size Constraints
        for(int i = 0; i < this.dimension; i++)
        {
            double scale = mainGridPane.getPrefHeight() / this.dimension;
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(scale);
            mainGridPane.getRowConstraints().add(rowConstraints);
        }

        for(int i = 0; i < this.dimension; i++)
        {
            double scale = mainGridPane.getPrefWidth() / this.dimension;
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(scale);
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

    // Update Direction


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
    private void updateMinerDirection()
    {
        // Get from model
        int direction = mainBoard.getMinerAgent().getFront();

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

        // Update the label
        directionLabel.setText(facing);
    }
}
