package RsLogic;

/**
 *
 * @version 1.0 18 Feb 2016
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */

public class SavedSettings {
    public String _baudRate, _dataBits, _parity, _stopBits, _port ;
    public boolean _flowCtr;
    public SavedSettings(String baudRate, String dataBits, String parity, String stopBits, boolean flowCtr, String port){
        _baudRate = baudRate;
        _dataBits = dataBits;
        _parity = parity;
        _stopBits = stopBits;
        _flowCtr = flowCtr;
        _port = port;
    }
}
