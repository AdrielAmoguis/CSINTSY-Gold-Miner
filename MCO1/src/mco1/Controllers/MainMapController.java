package mco1.Controllers;

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

public class MainMapController implements EventHandler<Event>
{
    // Size N
    private int dimension;

    // JavaFX Instances
    @FXML
    GridPane mainGridPane;

    private Pane[][] panes;

    public MainMapController(int n)
    {
        this.dimension = n;
    }

    @FXML
    public void initialize()
    {
        // Create the panes
        panes = new Pane[this.dimension][this.dimension];

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
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(scale);
            mainGridPane.getRowConstraints().add(rowConstraints);
        }

        for(int i = 0; i < this.dimension; i++)
        {
            double scale = 100 / this.dimension;
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(scale);
            mainGridPane.getColumnConstraints().add(columnConstraints);
        }
    }


    // Event Handler
    public void handle(Event ev)
    {
        if(ev instanceof ActionEvent)
            handle((ActionEvent) ev);
    }

    // ActionEvent Handler
    private void handle(ActionEvent ev)
    {

    }
}
