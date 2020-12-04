package mco1.Controllers;

<<<<<<< HEAD
import com.sun.prism.paint.Color;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import javax.security.auth.login.AccountNotFoundException;
=======
import mco1.Model.Board;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c

public class MainMapController implements EventHandler<Event>
{
    // Size N
    private int dimension;

<<<<<<< HEAD
    // JavaFX Instances
    @FXML
    GridPane mainGridPane;

=======
    // Board Instance
    private Board mainBoard;

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


    GridPane mainGridPane;

    private int initState;

>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
    private Pane[][] panes;

    public MainMapController(int n)
    {
<<<<<<< HEAD
=======
        this.mainBoard = new Board(n);
        this.initState = 0;
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
        this.dimension = n;
    }

    @FXML
    public void initialize()
    {
        // Create the panes
        panes = new Pane[this.dimension][this.dimension];

<<<<<<< HEAD
        // Clear the GridPane

        for(int i = 0; i < this.dimension; i++)
            for(int j = 0; j < this.dimension; j++)
            {
                panes[i][j] = new Pane();
                panes[i][j].setStyle("-fx-background-color: #32a852; -fx-border-color: black; -fx-border-width: 1;");
            }

        // Bind the Panes
        for(int i = 0; i < this.dimension; i++)
        {
            mainGridPane.addRow(i, panes[i]);
        }

        // Resize Everything
        for(int i = 0; i < this.dimension; i++)
        {
            double scale = 100 / this.dimension;
=======
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
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(scale);
            mainGridPane.getRowConstraints().add(rowConstraints);
        }

        for(int i = 0; i < this.dimension; i++)
        {
<<<<<<< HEAD
            double scale = 100 / this.dimension;
=======
            double scale = mainGridPane.getPrefWidth() / this.dimension;
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(scale);
            mainGridPane.getColumnConstraints().add(columnConstraints);
        }
<<<<<<< HEAD
=======

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
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
    }


    // Event Handler
    public void handle(Event ev)
    {
        if(ev instanceof ActionEvent)
            handle((ActionEvent) ev);
<<<<<<< HEAD
=======

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
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
    }

    // ActionEvent Handler
    private void handle(ActionEvent ev)
    {
<<<<<<< HEAD

=======
        // Check for Button
        if(ev.getSource() instanceof Button)
        {
            // Button Handler
            if(((Button) ev.getSource()).getId().equals(setGoldButton.getId()))
            {
                this.initState = 1;
            }

        }
>>>>>>> b53ed18076dea8eca416dd44bda85401bc68af1c
    }
}
