package Controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 *
 * @version 1.0 28 Sep 2015
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */
public class DBHandler {

    /**
     * FUNCTION NAME : httpRequest
     * DESCRIPTION   : Makes a http request to a database server to retrieve information from the database
     * INPUT         : String name, String Password
     * OUTPUT        : Send the parameters to database and return the server readback
     * NOTE          : -
     */
    public String httpRequestPost(String Name, String Password, String webUrl){
        try{
            URL url = new URL(webUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data_output =
                    URLEncoder.encode("UsersName", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8") + "&" +
                    URLEncoder.encode("UsersPassword", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");
            bufferedWriter.write(data_output);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            //String line = null;
            //while(scanner.hasNext())
                //System.out.println(scanner.nextLine());
            String line = scanner.nextLine();
            inputStream.close();
            httpURLConnection.disconnect();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
