package Controllers;

import RsLogic.*;
import gnu.io.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @version 1.0 18 Feb 2016
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */

public class RS232Controller implements Initializable{
    private SavedSettings savedSettings = null;
    private  String userListPortsInput = null;
    private boolean isThreadRec;
    private Thread receiveThread;
    private SerialPort serialPort;
    private CommPortReceive commPortReceiver;
    @FXML ComboBox<String> baudRate, stopBits, parity, dataBits;
    @FXML TextField listPorts, inputRS;
    @FXML AnchorPane settingsPanel, centerPane;
    @FXML Region regionOnMouse;
    @FXML BorderPane mainBorderPane;
    @FXML RadioButton flowCtrOn;
    @FXML Button btnExit, btnConnect, btnReset;
    @FXML TextArea outputRS;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputRS.setEditable(false);
        inputRS.setOnKeyTyped(event -> {
            if(inputRS.isEditable()) {
                CommPortSender.send(event.getCharacter().getBytes());
            }
        });

        receiveThread = new Thread() {
            @Override
            public void run() {
                commPortReceiver.setSerial();
                while (isThreadRec) {
                    if (!isThreadRec)
                        stopThread(receiveThread);

                    commPortReceiver.readSerial();

                    try{
                        sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ListAvailablePorts listAvailablePorts = new ListAvailablePorts();
        if(listAvailablePorts.CalcLen() > 0){
            String[] allPorts = listAvailablePorts.listPorts();
            listPorts.setText(allPorts[0]);
        }
        baudRate.getItems().addAll(
                "75", "110", "300", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200");
        stopBits.getItems().addAll(
                "1", "2"
        );
        parity.getItems().addAll(
                "none (N)", "odd (O)", "even (E)", "mark (M)", "space (S)"
        );
        dataBits.getItems().addAll(
                "1", "5", "6", "7", "8"
        );
        dataBits.setValue("8");
        parity.setValue("none (N)");
        baudRate.setValue("9600");
        stopBits.setValue("1");
    }

    private synchronized void stopThread(Thread thread) {
        if (thread != null)
            receiveThread = null;
    }

    /**
     * FUNCTION NAME : send
     * DESCRIPTION   : After written text you shall reset the TextWindows
     * NOTE          : -
     */
    @FXML protected void submitSettings(ActionEvent event) {
        String userBaudRateInput = String.valueOf(baudRate.getSelectionModel().getSelectedItem());
        String userStopBitsInput = String.valueOf(stopBits.getSelectionModel().getSelectedItem());
        String userParityInput = String.valueOf(parity.getSelectionModel().getSelectedItem());
        String userDataBitsInput = String.valueOf(dataBits.getSelectionModel().getSelectedItem());
        userListPortsInput = listPorts.getText();

        if(!userListPortsInput.equals("null")){
            if(flowCtrOn.isSelected())
                savedSettings = new SavedSettings(userBaudRateInput, userDataBitsInput, userParityInput, userStopBitsInput, flowCtrOn.isSelected(), userListPortsInput);
            else
                savedSettings = new SavedSettings(userBaudRateInput, userDataBitsInput, userParityInput, userStopBitsInput, flowCtrOn.isSelected(), userListPortsInput);
        }
    }


    /**
     * FUNCTION NAME : send
     * DESCRIPTION   : After written text you shall reset the TextWindows
     * NOTE          : -
     */
    @FXML protected void handleSubmitButtonReset(ActionEvent event) {
        inputRS.setText("");
        outputRS.setText("");
    }

    /**
     * FUNCTION NAME : handleSubmitButtonConnect
     * DESCRIPTION   : Sending the data who was typed by the user in the TextField
     * NOTE          : -
     */
    @FXML protected void handleSubmitButtonConnect(ActionEvent event) {

        if (btnConnect.getText().equals("Connect") && !userListPortsInput.equals("")) {
            isThreadRec = true;
            if (savedSettings != null ) {
                try {
                    serialPort = new RS232Connect().connect(savedSettings);
                    commPortReceiver = new CommPortReceive(serialPort.getInputStream(), serialPort, outputRS);
                    receiveThread.start();
                    btnConnect.setText("Disconnect");
                    inputRS.setText("");
                    inputRS.setEditable(true);
                } catch (Exception e) {
                    inputRS.setText("Port could't be found");
                }
                savedSettings = null;
            }
        }
        else if( btnConnect.getText().equals("Disconnect")){
            disconnect();
            inputRS.setEditable(false);
            isThreadRec = false;
            btnConnect.setText("Connect");
        }
    }

    /**
     * FUNCTION NAME : handleSubmitButtonExit
     * DESCRIPTION   : Will exit program when user pushes this button
     * NOTE          : -
     */
    @FXML protected void handleSubmitButtonExit(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML protected void openSideMenu(MouseEvent event){
        if(event.getSource() == regionOnMouse){
            mainBorderPane.setLeft(settingsPanel);
            settingsPanel.setVisible(true);
        }
    }

    @FXML protected void closeSideMenu(MouseEvent event){
        if(event.getSource() == centerPane){
            mainBorderPane.setLeft(null);
            settingsPanel.setVisible(false);
        }
    }

    public void disconnect() {
        if (serialPort != null) {
            try {
                // close the i/o streams.
                serialPort.removeEventListener();
                serialPort.getOutputStream().close();
                commPortReceiver.in.close();
                serialPort.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Close the port.
            serialPort.close();
        }
    }
}