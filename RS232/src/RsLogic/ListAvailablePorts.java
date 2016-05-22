package RsLogic;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
/**
 * 
 * @version 1.0 10 Feb 2015
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */
public class ListAvailablePorts {
    /**
     * FUNCTION NAME : listPorts
     * DESCRIPTION   : This function will find all available COMx ports on your 
     *                 computer and save them into a String array
     * 	               The String array will then be return to a ComboBox so the 
     *                 user can choose COMx port
     * NOTE          : -
     */
  public String[] listPorts(){
        @SuppressWarnings("rawtypes")
        //get all Ports who is available and has a serial cable pluged in
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        //calculate how many Ports you have available on your computer
        int len = CalcLen();
        //Create a string array of all available ports 
        String[] AviablePorts = new String[len];
        int idx = 0;
        while(ports.hasMoreElements()) {
            //put the Name of the port into the string array
            AviablePorts[idx] = (((CommPortIdentifier) ports.nextElement()).getName());
            idx++;
        }
        //if the list is greater the zero means we have identifyed a COM port on the computer
        if(idx > 0)
                return AviablePorts;
        return null;
    }
    /**
     * FUNCTION NAME : CalcLen
     * DESCRIPTION   : Calculate all the COMx ports who is available on your computer
     * NOTE          : -
     */
    public int CalcLen(){
        @SuppressWarnings("rawtypes")
        //get all Ports who is available and has a serial cable plugged in
        Enumeration listPorts = CommPortIdentifier.getPortIdentifiers();
        //variable to calculate all the ports
        int Stringlen = 0;
        while(listPorts.hasMoreElements()) {
            //To get to next port we need step trough the list
            ((CommPortIdentifier)listPorts.nextElement()).getName();
            //each loop contains one COMx port
            Stringlen++;
        }
        //return the total length
        return Stringlen;
    }
}
