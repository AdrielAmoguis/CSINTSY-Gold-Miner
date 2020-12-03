package mco1.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainMenuController implements EventHandler<Event>, ChangeListener<String>
{
    // JavaFX Instances
    @FXML
    Button createMapButton;

    @FXML
    TextField sizeTextBox;

    // JavaFX Initialization
    @FXML
    public void initialize()
    {
        // Create Map Button should initially be false
        createMapButton.setDisable(true);

        // Add Button's OnAction Event
        createMapButton.setOnAction(this::handle);

        // Add ChangeListener to TextBox
        sizeTextBox.textProperty().addListener(this::changed);
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
        if(((Button)ev.getSource()).getId().equals(createMapButton.getId()))
        {
            // Open the Main Window
            Stage primaryStage = ((Stage) ((Button) ev.getSource()).getScene().getWindow());
            Parent root = null;

            // Custom Controller
            MainMapController controller = new MainMapController(Integer.parseInt(sizeTextBox.getText()));

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mco1/View/MainMap.fxml"));
                loader.setController(controller);
                root = loader.load();
            }

            catch(Exception e)
            {
                e.printStackTrace();
                return;
            }

            primaryStage.setTitle("Gold Miner - Main Window");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    // ChangeListener
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
    {
        // Check if empty
        if(sizeTextBox.getText().isEmpty())
        {
            createMapButton.setDisable(true);
            return;
        }
        else createMapButton.setDisable(false);

        String inputText = sizeTextBox.getText();

        // Check number of digits
        if(inputText.length() > 2)
        {
            sizeTextBox.setText(s);
            return;
        }

        if(!inputText.isEmpty())
        {
            // Check if input are numbers
            if(!t1.matches("^[0-9]+$"))
            {
                sizeTextBox.setText(s);
                return;
            }

            // Check if input is within range 8-64 inclusive
            int n = Integer.parseInt(sizeTextBox.getText());

            if(n < 8 || n > 64)
                createMapButton.setDisable(true);
            else createMapButton.setDisable(false);
        }
    }
}
