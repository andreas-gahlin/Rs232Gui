class CommPortReceive {
    private val buffer: ByteArray? = null
    val `in`: InputStream
    private val serialPortIn: SerialPort? = null

    private inner class SerialEventHandler : SerialPortEventListener
}