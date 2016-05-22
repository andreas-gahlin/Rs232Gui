package RsLogic;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * 
 * @version 1.0 28 Sep 2015
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */

public class RS232Connect {
    /**
     * FUNCTION NAME : connect
     * DESCRIPTION   : Tries to connect with the chosen COMx port by the user.
     * NOTE          : An error might occur where user can't see if the the COMx 
     *                 port is already in use
     */
    public SerialPort connect(SavedSettings savedSettings) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(savedSettings._port);

        // if the port is in use the program can't bind to the COMx
        if (portIdentifier.isCurrentlyOwned()) {  
            System.out.println("Port in use!");
        } 
        else { 
        // points who owns the port and connection timeout  
            try {
                SerialPort serialPort = (SerialPort) portIdentifier.open(this.getClass().getName(), 5000);  //2000 = 2 seconds for timeout

                // setup connection parameters  
                serialPort.setSerialPortParams(Integer.parseInt(savedSettings._baudRate), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                // setting up flow control
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);

                // set RTS (ready to send) flag to true to get notify when H/W is ready to recive new data
                serialPort.setRTS(true);

                // setup serial port writer  
                CommPortSender.setWriterStream(serialPort.getOutputStream());
                // setup serial port reader      
                //new CommPortReceive(serialPort.getInputStream(), serialPort, savedSettings).start();
                return serialPort;
            }
            catch (UnsupportedCommOperationException ex) {
                    System.out.println("Error in connect. try n catch");
            }
        }
        return null;
    }

}
