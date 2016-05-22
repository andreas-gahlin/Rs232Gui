package RsLogic;

import java.io.IOException;
import java.io.OutputStream;
/**
 * 
 * @version 1.0 28 Sep 2015
 * @author Andreas G�hlin
 * @email andreas.gahlin@gmail.com
 */
public class CommPortSender {
    static OutputStream out;
    /**
     * FUNCTION NAME : setWriterStream
     * DESCRIPTION   : setup function who will make so that we can write data out from the computer trough the COMx port
     * NOTE          : -
     */
    public static void setWriterStream(OutputStream out) {
        CommPortSender.out = out;  
    }
    /**
     * FUNCTION NAME : send
     * DESCRIPTION   : Sending the data who was typed by the user in the TextField 
     * OUTPUT        : Data from the user
     * NOTE          : -
     */
     public static void send(byte[] bytes) {
        try {        
            out.write(bytes); 
            out.flush();
            out.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
