package mco1.Controllers;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class MainMapController implements EventHandler<Event>
{
    // JavaFX Instances


    public MainMapController(int n)
    {

    }

    @FXML
    public void initialize()
    {

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
