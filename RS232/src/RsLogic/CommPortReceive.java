package RsLogic;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;
/**
* 
* @version 1.0 18 Feb 2016
* @author Andreas Gåhlin
* @email andreas.gahlin@gmail.com
*/


public class CommPortReceive {
    private final byte[] buffer = new byte[1024];
    public final InputStream in;
    private final SerialPort serialPortIn;
    private TextArea outputRS;


    public CommPortReceive(InputStream in, SerialPort serialPort, TextArea _outputRS) {
        this.in = in;
        this.serialPortIn = serialPort;
        outputRS = _outputRS;
    }

    /**
     * FUNCTION NAME : run
     * DESCRIPTION   : This function is the start point of the thread and will always keep reading the buffer witch
     *                 is send from the H/W who is connected with the serial cable
     * NOTE          : -
     */
    public void setSerial() {
        //setup of the eventHandler who will
        setSerialEventHandler(this.serialPortIn);

    }

    /**
     * FUNCTION NAME : readSerial
     * DESCRIPTION   : Shall always read the buffer and try to receive data from the H/W because we will never know when
     *                 data is going to be available
     * INPUT         : Data from the serial cable who was sent from the H/W
     * OUTPUT        : Print out the data from the H/W to the view(JFrame)
     * NOTE          : -
     */
    public void readSerial () {
        try {
            int availableBytes = this.in.available();
            if ( availableBytes > 0 ) {
            	// Read the serial port
                this.in.read ( buffer, 0, availableBytes );
                // Print out answer
                outputRS.appendText(getMessage(buffer, availableBytes));
            }
        } 
        catch (IOException e) {
            // N/A
        }
    }

    /**
     * FUNCTION NAME : getMessage
     * DESCRIPTION   : Decode the data who is sent as an byte array to string.
     * NOTE          : Makes the data readable for a user
     */
    private String getMessage (byte[] buffer, int len ) {
    	//returns a string from the byte array
        return new String ( buffer, 0, len );  
    }

    /**
     * CLASS NAME 	 : SerialEventHandler
     * DESCRIPTION   : Makes an EventHanlder who will check when data is available on the buffer
     * NOTE          : -
     */
    private class SerialEventHandler implements SerialPortEventListener {
    	/**
         * FUNCTION NAME : serialEvent
         * DESCRIPTION   : Setting up the EventListener of the COMx port User have the serial cable on
         * NOTE          : -
         */
        public void serialEvent(SerialPortEvent event) {
            switch (event.getEventType()) {
            	//when data is available run function who will read serial port
                case SerialPortEvent.DATA_AVAILABLE:
                    readSerial();
                    break;
            }
        }
    }
    /**
     * FUNCTION NAME : setSerialEventHandler
     * DESCRIPTION   : Runs the functions in the class to change the attributes in the class
     * NOTE          : -
     */
    private void setSerialEventHandler(SerialPort serialPort) {
        try {
            // Add the serial port event listener
            serialPort.addEventListener(new SerialEventHandler());
            //when buffer changes we will get the notify from the flag who keeps track of the buffer
            serialPort.notifyOnDataAvailable(true);
        } 
        catch (TooManyListenersException ex) {
            // Error message
            System.err.println(ex.getMessage());
        }
    }
}