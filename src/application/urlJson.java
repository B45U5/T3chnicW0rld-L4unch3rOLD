package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class urlJson {

	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

	  private static HttpURLConnection con2;
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {



	        try {

	            URL myurl = new URL(url);
	            con2 = (HttpURLConnection) myurl.openConnection();

	            con2.setDoOutput(true);
	            con2.setRequestMethod("POST");
	            con2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	            con2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	            StringBuilder content;

	            try (BufferedReader in = new BufferedReader(
	                    new InputStreamReader(con2.getInputStream()))) {

	                String line;
	                content = new StringBuilder();

	                while ((line = in.readLine()) != null) {
	                    content.append(line);
	                    content.append(System.lineSeparator());
	                }
	            }

			      JSONObject json = new JSONObject(content.toString());
			      return json;

	        } finally {
	            
	            con2.disconnect();
	        }
		  }

}
