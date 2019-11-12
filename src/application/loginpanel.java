package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
public class loginpanel extends Application {
	private String successs;
	private String usernic;
	private String ranks;
	//private String uuid;
	@Override
	public void start(Stage stage) {
		try {
			settings.loadSettings();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		        
		        Scene scene = new Scene(root, 265, 353);
		        stage.getIcons().add(new Image("file:icon.png"));
		        stage.setResizable(false);
		        stage.setTitle("TechnicWorld | Logowanie");
		        stage.setScene(scene);
		        stage.show();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent t) {
					      Platform.exit();
					      System.exit(0);
					   }
					});

		} catch(Exception e) {
			e.printStackTrace();
			Main.swingload("ERROR RENDERING LOGIN GUI!", "jTextAreaOutputStreamavafxerrormode");
		}		
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		launch(args);
		
		
	}
	 @FXML
	 private JFXTextField usernamelogin;
	 @FXML
	 private JFXPasswordField passwordlogin;
	 @FXML
	 private Pane logpanel;
	 @FXML
	 private Button lgin2;
	 @FXML
	 private Label lab;
	 @FXML
	 private JFXSpinner progressbar;
	 @FXML
	 private Hyperlink register2;
	 
	 @FXML
	    private void initialize() throws IOException
	    {
	    Runnable updatethread = new Runnable() {
	        public void run() {
		 File theDir = new File(System.getProperty("user.home")+"//TechnicWorld");

	     // if the directory does not exist, create it
	     if (!theDir.exists()) {
	         System.out.println("creating directory: " + theDir.getName());
	         boolean result = false;
	
	         try{
	             theDir.mkdirs();
	             result = true;
	         } 
	         catch(SecurityException se){
	             //handle it
	         }        
	         if(result) {    
	             System.out.println("DIR created");  
	         }
	     }
		 System.out.println(System.getProperty("user.home"));
		 progressbar.setVisible(true);
		 progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
     	lab.setVisible(false);
     	lgin2.setVisible(false);
     	usernamelogin.setVisible(false);
     	passwordlogin.setVisible(false);
     	register2.setVisible(false);
		 Platform.runLater(new Runnable() {
			 
	            @Override public void run() {
	                try {

						checkd();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        
		  }
		};
		ExecutorService executor = Executors.newCachedThreadPool();
	    executor.submit(updatethread);
	    }
	 @FXML
	 public void checkd() throws IOException {

		 
		 		  String token = settings.getToken();
				  JSONObject json = readJsonFromUrl("https://api.technicworld.pl/index.php?api=check&token="+token);
				  //uuid = (String) (json.get("uuid"));
				  if(json.get("token").equals("succ")) {
					  		  ranks = (String) (json.get("rank"));
				        	  String pack = settings.getPack();
				        	  System.out.println(pack);
				        	  if (pack.equals("survival") | pack.equals("skyblock")) {
						        	Main.token(successs,usernic, pack, ranks);
									Main.token((String) (token), (String) json.get("username"), pack, ranks);
									Stage primaryStage = new Stage();
									Main guimain = new Main();
									guimain.start(primaryStage);
									Stage stage = (Stage) lgin2.getScene().getWindow();
								    // do what you have to do
								    stage.close();		  
				        	  } else {
									progressbar.setVisible(true);
									lgin2.setVisible(false);
									progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
									Stage primaryStage = new Stage();
									pack_select guimain = new pack_select();
									guimain.start(primaryStage);
									Stage stage = (Stage) lgin2.getScene().getWindow();
								    // do what you have to do
								    stage.close();
				        	  }
				        } else {

							  System.out.println("Wylogowano!");
			                	lab.setVisible(true);
			                	lgin2.setVisible(true);
			                	usernamelogin.setVisible(true);
			                	passwordlogin.setVisible(true);
			                	progressbar.setVisible(false);
			                	register2.setVisible(true);
				}
	}
	
	 @FXML
	  private void lgin2(ActionEvent event) throws JSONException, IOException {
		 	

			boolean success = checklogin(usernamelogin.getText().trim(), passwordlogin.getText().trim());


			if (success == true) {
				File f = new File(System.getProperty("user.home")+"//TechnicWorld//"+"pack.txt");
		        if(f.exists() && !f.isDirectory()) { 
		        	  FileReader fileReader = new FileReader(System.getProperty("user.home")+"//TechnicWorld//"+"pack.txt");
		        	  BufferedReader bufferedReader = new BufferedReader(fileReader);
		        	  String pack = bufferedReader.readLine();
		        	  System.out.println(pack);
		        	  if (pack.equals("survival") | pack.equals("skyblock")) {
				    	   Main.token(successs,usernic, pack, ranks);
		        		  	progressbar.setVisible(true);
							lgin2.setVisible(false);
							progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
							Stage primaryStage = new Stage();
							Main guimain = new Main();
							guimain.start(primaryStage);
							Stage stage = (Stage) lgin2.getScene().getWindow();
						    // do what you have to do
						    stage.close();
		        		  
		        	  } else {
							progressbar.setVisible(true);
							lgin2.setVisible(false);
							progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
							Stage primaryStage = new Stage();
							pack_select guimain = new pack_select();
							guimain.start(primaryStage);
							Stage stage = (Stage) lgin2.getScene().getWindow();
						    // do what you have to do
						    stage.close();
		        	  }
		        } else {
					progressbar.setVisible(true);
					lgin2.setVisible(false);
					progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
					Stage primaryStage = new Stage();
					pack_select guimain = new pack_select();
					guimain.start(primaryStage);
					Stage stage = (Stage) lgin2.getScene().getWindow();
				    // do what you have to do
				    stage.close();
		        }
			} else {
				
			}
	  }
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
		  
		  private static HttpURLConnection con;
		  public static JSONObject readJsonFromUrlPost(String url, String urlParameters) throws IOException, JSONException {

		        byte[] postData = urlParameters.getBytes();

		        try {

		            URL myurl = new URL(url);
		            con = (HttpURLConnection) myurl.openConnection();

		            con.setDoOutput(true);
		            con.setRequestMethod("POST");
		            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
		                wr.write(postData);
		            }

		            StringBuilder content;

		            try (BufferedReader in = new BufferedReader(
		                    new InputStreamReader(con.getInputStream()))) {

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
		            
		            con.disconnect();
		        }
			  }

	 private boolean checklogin(String user, String pass) throws JSONException, IOException {
		 	resetColors();
		 	//System.out.println("https://api.technicworld.pl/index.php?api=login&login="+user+"&pass="+pass);
		 	JSONObject json = readJsonFromUrlPost("https://api.technicworld.pl/index.php?api=login", "login="+user+"&pass="+pass);
		    System.out.println(json.toString());
		    //System.out.println(json.get("info"));
		    if (!json.isNull("info")) {
		    	//System.out.println(json.get("success"));
		    	successs = (String) (json.get("success"));
		    	usernic = (String) (json.get("username"));
		    	ranks = (String) (json.get("rank"));
		    	System.out.println("USERNAME: "+json.get("username"));
		    	  settings.setToken(json.getString("success"));
		    	  settings.saveSettings();
		    	return true;
		    } 
		    else {
		    	System.out.println(json.get("error"));
		    	
		    	if (((String) json.get("function")).equals("login")) {
		    		lab.setText("SprawdŸ pole z loginem!");
		    		usernamelogin.setUnFocusColor(Color.RED);
		    	} else if (((String) json.get("function")).equals("password")) {
		    		lab.setText("SprawdŸ pole z has³em!");
		    		passwordlogin.setUnFocusColor(Color.RED);
		    	} else if (json.get("msg").toString().contains("zablokowane")) {
		    		lab.setText("Konto zosta³o zablokowane!\nSpróbuj za jakiœ czas\n lub skontaktuj siê z administracj¹!");
		    
		    	} else {
		    		lab.setText("Poda³eœ b³êdne has³o/login!");
		    		usernamelogin.setUnFocusColor(Color.RED);
		    		passwordlogin.setUnFocusColor(Color.RED);
		    	}
		    	return false;
		    }
			
	 
		
	 }
	 
	 private void resetColors() {
		 Color normal = new Color(0.77, 0.77, 0.77, 1);
		 usernamelogin.setUnFocusColor(normal);
		 passwordlogin.setUnFocusColor(normal);
	 }
	 
	 @FXML
	 private void register2(ActionEvent event) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://api.technicworld.pl/index.php?site=login"));
	 }
}
