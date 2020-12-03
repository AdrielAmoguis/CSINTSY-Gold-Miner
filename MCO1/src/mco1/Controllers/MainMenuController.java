package mco1.Controllers;

import javafx.fxml.FXML;
import mco1.Model.*;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class MainMenuController implements EventHandler<Event>
{
    // JavaFX Instances


    // Constructor
    public MainMenuController()
    {

    }

    // JavaFX Initialization
    @FXML
    public void initialize()
    {

    }

    // Event Listener
    public void handle(Event ev)
    {
        // Forward to ActionEvent Handler if ActionEvent
        if(ev instanceof ActionEvent)
            handle((ActionEvent) ev);
    }

    // ActionEvent Listener
    private void handle(ActionEvent ev)
    {

    }

}
