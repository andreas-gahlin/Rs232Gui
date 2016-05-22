class RS232Controller : Initializable {
    private val savedSettings: SavedSettings? = null
    private val userListPortsInput: String? = null
    private val isThreadRec: Boolean = false
    private val receiveThread: Thread? = null
    private val serialPort: SerialPort? = null
    private val commPortReceiver: CommPortReceive? = null
    @FXML internal var baudRate: ComboBox<String>
    @FXML internal var stopBits: ComboBox<String>
    @FXML internal var parity: ComboBox<String>
    @FXML internal var dataBits: ComboBox<String>
    @FXML internal var listPorts: TextField
    @FXML internal var inputRS: TextField
    @FXML internal var settingsPanel: AnchorPane
    @FXML internal var centerPane: AnchorPane
    @FXML internal var regionOnMouse: Region
    @FXML internal var mainBorderPane: BorderPane
    @FXML internal var flowCtrOn: RadioButton
    @FXML internal var btnExit: Button
    @FXML internal var btnConnect: Button
    @FXML internal var btnReset: Button
    @FXML internal var outputRS: TextArea
}