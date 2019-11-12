package application;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.HyperlinkEvent;

import org.apache.commons.codec.binary.Hex;
import org.codefx.libfx.control.webview.WebViewHyperlinkListener;
import org.codefx.libfx.control.webview.WebViews;
import org.json.JSONException;
import org.json.JSONObject;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.schlegel11.jfxanimation.JFXAnimationTemplate;
import de.schlegel11.jfxanimation.JFXAnimationTemplateAction;
import de.schlegel11.jfxanimation.JFXAnimationTemplates;
import de.schlegel11.jfxanimation.JFXTemplateBuilder;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import uk.co.rx14.jmclaunchlib.LaunchSpec;
import uk.co.rx14.jmclaunchlib.LaunchTask;
import uk.co.rx14.jmclaunchlib.LaunchTaskBuilder;
import uk.co.rx14.jmclaunchlib.util.OS;

public class Main extends Application {
	public static String ac;
	public static String nickname;
	public static String rank;
	public static String uuid;
	public static String sw;
	public static String swlink;
	public static Double rame = (double) 4;
	public static Double rammc;
	public String isplay = "0";
	public static final String EVENT_TYPE_CLICK = "click";
    public static final String EVENT_TYPE_MOUSEOVER = "mouseover";
    public static final String EVENT_TYPE_MOUSEOUT = "mouseclick";
	private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
	@FXML
	private Pane userico;
	@FXML
	private Label rami;
	@FXML
	private Slider ramp;
	@FXML
	private Text nick;
	@FXML
	private Label downinfo;
	@FXML
	private Label downloadinfo;
	@FXML
	private ProgressBar down;
	@FXML
	private Pane download;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private WebView wbv;
	@FXML ProgressIndicator web;
	@FXML
	private VBox settingsPane;
	@FXML
	private AnchorPane appPane;
	@FXML
	private Button play;
	@FXML
	private Button closeSettings;
	@FXML
	private MaterialDesignIconView settingsICON;
	@FXML
	private Label playText;
	@FXML
	private Label loginText;
	@FXML
	private Label closeSettingsText;
	@FXML
	private Text rankTxt;
	
	public List<modsListClass> modsList = new ArrayList<modsListClass>();
	public List<modsListClass> modsListInDir = new ArrayList<modsListClass>();
	List<String> addonsList = new ArrayList<String>();
	int jsonSize;
	int mods = 0;
	String modsPath = settings.workingDir+"//"+settings.getPack()+"//Minecraft//mods";
	String urlModsPath = "https://api.technicworld.pl/api/modpack-"+settings.getPack();
	String urlAddonsList = "https://api.technicworld.pl/api/addons_"+settings.getPack()+".json";
	String urlModsList = "https://api.technicworld.pl/api/mods-"+settings.getPack()+".json";
	String urlPackVersion = "0.0.0";
	
	
	
	public static void token(String act, String nm, String serwer, String rnk) {
		ac=act;
		nickname=nm;
		rank=rnk;
		sw=System.getProperty("user.home")+"//TechnicWorld//"+serwer;
		swlink=serwer;
	}
	public void start(Stage stage) {
		LOGGER.log(Level.INFO, "Try to render gui...");
		try {
			Font.loadFont(getClass().getResource("font.ttf").toExternalForm(), 10);
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		        Scene scene = new Scene(root, 790, 440);
		        stage.getIcons().add(new Image("file:icon.png"));
		        stage.setResizable(true);
		        stage.setTitle("TechnicWorld launcher | "+swlink);
		        stage.setMinHeight(440);
		        stage.setMinWidth(790);
		        stage.setScene(scene);
		        stage.show();
		        LOGGER.log(Level.INFO, "Render gui success!");
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent t) {
					      Platform.exit();
					      System.exit(0);
					   }
					});
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Render gui error! try to load swing GUI");
			swingload("Proszê zainstalowaæ javaFX!", "jTextAreaOutputStreamavafxerrormode");
		}
		
	}
	

	
    @SuppressWarnings({ "restriction", "resource" })
	String mcver = "1.12.2";
	String forgever = "1.12.2-14.23.5.2838";
	@FXML
    private void initialize() throws IOException
    {
	// TODO Auto-generated constructor stub
		if (sw.contains("skyblock")) {
			 mcver = "1.12.2";
        	 forgever = "1.12.2-14.23.5.2838";
		} else if(sw.contains("survival")) {
			mcver = "1.7.10";
       	 	forgever = "1.7.10-10.13.4.1614-1.7.10";
			 //mcver = "1.12.2";
        	 //forgever = "1.12.2-14.23.2.2654";
		}
        browserload("https://api.technicworld.pl/?site=lglowna&accesstoken="+ac);
        InputStream fontStream = Main.class.getResourceAsStream("font.ttf");
        if (fontStream != null) {
            Font bgFont = Font.loadFont(fontStream, 27);
            fontStream.close();

            nick.setFont(bgFont);
        } else {
            throw new IOException("Could not create font: " + "font.ttf");
        }
        nick.setText(nickname);
        rankTxt.setText("#"+rank);
        userico.setStyle("-fx-background-image: url('https://minotar.net/helm/"+nickname+"'); -fx-background-size: cover; -fx-shape: \"M0 13 C0 5 5 0 13 0 L86 0 C94 0 99 5 99 13 L99 86 C99 94 94 99 86 99 L13 99 C5 99 0 94 0 86Z\";\r\n" + 
        		"");
        Runnable updatethread = new Runnable() {
            public void run() {
           try {

			modpack();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        
    };
    new Thread(updatethread).

    start();

        
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize()/1024/1024;
        Double memsize = (double) memorySize;
        System.out.println("RAM Size="+memsize+" MB");
        	  //String nickuser = bufferedReader.readLine();
        	  //System.out.println();
        	  //bufferedReader.close();
        ramp.setMax(Double.parseDouble(String.format("%.0f", (memsize/1024))));

       
        if (((memsize)/1024) < 1) {
        	 System.out.println(Double.parseDouble(String.format("%.0f", (memsize)))+" MB");
        	 rami.setText(Double.parseDouble(String.format("%.0f", (memsize)))+" MB");
        } else {
        	 System.out.println(Double.parseDouble(String.format("%.0f", (memsize/1024)))+" GB");
        	 rami.setText(Double.parseDouble(String.format("%.0f", (memsize/1024)))+" GB");
        }
        rammc = Double.parseDouble(String.format("%.0f", ramp.getValue()*1024));
        System.out.println(rammc);
        
        ramp.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	
                    
                    rami.setText(Double.parseDouble(String.format("%.0f", ramp.getValue()))+" GB");
                  
                    rammc = Double.parseDouble(String.format("%.0f", ramp.getValue()*1024));
                    System.out.println(rammc);
                    
            }
        });
  	  rame = Double.parseDouble(settings.getRam());
  	  ramp.setValue(rame);
    }



	public void brcheck() {
		web.setVisible(true);
		wbv.setVisible(false);
		WebEngine engine = wbv.getEngine();
        web.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                  @Override public void changed(ObservableValue ov, State oldState, State newState) {

                      if (newState == Worker.State.SUCCEEDED) {
                    	  web.setVisible(false);
                    	  wbv.setVisible(true);
                    }
                      
                    }
                });
	}
	public void browserload(String URL) {
  	  	web.setVisible(true);
		wbv.setVisible(false);
        WebEngine engine = wbv.getEngine();
        engine.getLoadWorker().cancel();
        engine.setJavaScriptEnabled(true);
        brcheck();
        WebViewHyperlinkListener eventPrintingListener = event -> {
        	if (event.getURL().toExternalForm().contains("launcher") == true) {
        		brcheck();
        	} else {
        		browserload(event.getURL().toExternalForm()+"&accesstoken="+ac);
        		System.out.println("Clicked: "+event.getURL().toExternalForm()+"&accesstoken="+ac);
        	}
        	return false;
        };
        WebViews.addHyperlinkListener(wbv, eventPrintingListener, HyperlinkEvent.EventType.ACTIVATED);
        engine.load(URL+"&accesstoken="+ac);
		
	}


	public static void swingload(String string, String title) {
		System.out.println("Warning! swing gui isn't recomended! please use javafx gui!");
			JFrame f=new JFrame();//creating instance of JFrame  
	        
			JLabel label1 = new JLabel(string, JLabel.CENTER);
			label1.setBounds(0,0,200, 40);//x axis, y axis, width, height
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setTitle(title); 
			f.add(label1);//adding button in JFrame  
			f.setResizable(false);
			f.setSize(310,75);//400 width and 500 height  
			f.setLayout(null);//using no layout managers  
			f.setVisible(true);//making the frame visible 
			centreWindow(f);
		}
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	//przyciski
	//login
	@FXML
	private Button login;
	@FXML
	private Button modpacks;
	@FXML
	private void login(ActionEvent event) throws JSONException, IOException {
		LOGGER.log(Level.INFO, "btn_click(login): Clicked");
		if (isplay.equals("1")) {
	 			JSONObject json = readJsonFromUrl("https://api.technicworld.pl/index.php?api=logout&token="+ac);
	 			
				Stage primaryStage = new Stage();
				loginpanel guimain = new loginpanel();
				guimain.start(primaryStage);
				Stage stage = (Stage) login.getScene().getWindow();
			    // do what you have to do
			    stage.close();
		} else {
			LOGGER.log(Level.INFO, "btn_click(login): notClicked");
		}
			}


	 @FXML
		private void sklep(ActionEvent event) {
				LOGGER.log(Level.INFO, "btn_click(login): Clicked");
				browserload("https://api.technicworld.pl?api=sklep&serwer="+swlink);
				}
	 @FXML
		private void modpacks(ActionEvent event) throws IOException {
		 if (isplay.equals("1")) {
				LOGGER.log(Level.INFO, "btn_click(login): Clicked");
				Stage primaryStage = new Stage();
				pack_select guimain = new pack_select();
				guimain.start(primaryStage);
				Stage stage = (Stage) modpacks.getScene().getWindow();
			    // do what you have to do
			    stage.close();
				} else {
					LOGGER.log(Level.INFO, "btn_click(login): notClicked");
				}
	 }
	 @FXML
		private void addons(ActionEvent event) throws IOException {
		 if (isplay.equals("1")) {
			 addons.serwer(swlink.replaceAll("2", ""));
			 Stage primaryStage = new Stage();
			 addons addonsd = new addons();
			 addonsd.start(primaryStage);
		 }
	 }
	 @FXML
		private void friends(ActionEvent event) {
				LOGGER.log(Level.INFO, "btn_click(login): Clicked");
				browserload("https://api.technicworld.pl/?api=friends");
				}
	 @FXML
		 private void news(ActionEvent event) {
				LOGGER.log(Level.INFO, "btn_click(login): Clicked");
				browserload("https://api.technicworld.pl/?site=lglowna&accesstoken="+ac);
				}
	 @FXML
	 private void settingsBTN(ActionEvent event) {
			LOGGER.log(Level.INFO, "btn_click(login): Clicked");
			settingsPane.setVisible(true);
			BoxBlur blur = new BoxBlur(5, 5, 5);
	        appPane.setEffect(blur);
			}
	 @FXML
	 private void closeSettings(ActionEvent event) {
			LOGGER.log(Level.INFO, "btn_click(login): Clicked");
			settingsPane.setVisible(false);
	        appPane.setEffect(null);
			}
	 @FXML
	  private void skyb(ActionEvent event) throws IOException {
		 settings.setPack("skyblock");
		 settings.saveSettings();

	  }
	 @FXML
	  private void survi(ActionEvent event) throws IOException {
		 settings.setPack("survival");
		 settings.saveSettings();

	  }
	//login
	@FXML
	private void play(ActionEvent event) throws IOException {
		LOGGER.log(Level.INFO, "btn_click(play): Clicked");
		if (isplay.equals("1")) {
        	downinfo.setVisible(true);
        	download.setVisible(true);
        	down.setVisible(true);
      		down.setProgress(0);
            downinfo.setText("Uruchamiam minecraft!");
			LOGGER.log(Level.INFO, "btn_click(play): ISPLAYABLE");
			int sett = (int) ramp.getValue();
		  	String settt = Integer.toString(sett);
		  	System.out.println(sett);
		  	settings.setRam(settt);
		  	settings.saveSettings();
			try {
				
		        Runnable updatethread = new Runnable() {
		            public void run() {


		        

						 LaunchTask task = new LaunchTaskBuilder()
									
									.setCachesDir(sw+"/Minecraft/mc-files") //Directory to cache stuff in, copies caches from .minecraft (and verifies)
									
									//.setMinecraftVersion("1.7.10") //Set vanilla version
									//OR
									.setForgeVersion(mcver, forgever) //Minecraftforge version
									
									.setInstanceDir(sw+"/Minecraft") //Minecraft directory
									
									.setUsername(nickname) //Username for offline
									.setOffline() //Offline mode
									
									.build(); //Build LaunchTask
							        Runnable updatethread = new Runnable() {
							            public void run() {
												Double a = (double) 0;
												while (a<100) {
													a = task.getCompletedPercentage();
							                          Platform.runLater(new Runnable() {;
							                          
						                              @Override
						                              public void run() {
						                            	  //System.out.println(task.getCompletedPercentage()/100);
						                            	downinfo.setVisible(true);
						                            	download.setVisible(true);
						                            	down.setVisible(true);
						                          		down.setProgress(task.getCompletedPercentage()/100);
						                                downinfo.setText("Uruchamiam minecraft!");
						                                
						                              }
						                          });
												}
							            }};
									    new Thread(updatethread).
		
									    start();
									LaunchSpec spec = task.getSpec();
									spec.getJvmArgs().add("-Xmx"+rammc.toString().replace(".0", "")+"M");
									spec.getJvmArgs().add("-XX:InitiatingHeapOccupancyPercent=10");
									spec.getJvmArgs().add("-XX:AllocatePrefetchStyle=1");
									spec.getJvmArgs().add("-XX:+UseSuperWord");
									spec.getJvmArgs().add("-XX:+OptimizeFill");
									spec.getJvmArgs().add("-XX:LoopUnrollMin=4");
									spec.getJvmArgs().add("-XX:LoopMaxUnroll=16");
									spec.getJvmArgs().add("-XX:+UseLoopPredicate");
									spec.getJvmArgs().add("-XX:+RangeCheckElimination");
									spec.getJvmArgs().add("-XX:+CMSCleanOnEnter");
									spec.getJvmArgs().add("-XX:+EliminateLocks");
									spec.getJvmArgs().add("-XX:+DoEscapeAnalysis");
									spec.getJvmArgs().add("-XX:+TieredCompilation");
									spec.getJvmArgs().add("-XX:+UseCodeCacheFlushing");
									spec.getJvmArgs().add("-XX:+UseFastJNIAccessors");
									spec.getJvmArgs().add("-XX:+CMSScavengeBeforeRemark");
									spec.getJvmArgs().add("-XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses");
									spec.getJvmArgs().add("-XX:+ScavengeBeforeFullGC");
									spec.getJvmArgs().add("-XX:+AlwaysPreTouch");
									spec.getJvmArgs().add("-XX:+UseFastAccessorMethods");
									spec.getJvmArgs().add("-XX:+UnlockExperimentalVMOptions");
									spec.getJvmArgs().add("-XX:G1HeapWastePercent=10");
									spec.getJvmArgs().add("-XX:G1MaxNewSizePercent=10");
									spec.getJvmArgs().add("-XX:G1HeapRegionSize=32M");
									spec.getJvmArgs().add("-XX:G1NewSizePercent=10");
									spec.getJvmArgs().add("-XX:MaxGCPauseMillis=100");
									spec.getJvmArgs().add("-XX:+OptimizeStringConcat");
									spec.getJvmArgs().add("-XX:+UseParNewGC");
									spec.getJvmArgs().add("-XX:+UseNUMA");
									spec.getJvmArgs().add("-XX:+AlwaysTenure");
									spec.getJvmArgs().add("-XX:+UseCompressedOops");
									spec.getJvmArgs().add("-XX:G1NewSizePercent=10");
									spec.getJvmArgs().add("-XX:G1ReservePercent=10");
									spec.getJvmArgs().add("-XX:+UseConcMarkSweepGC");
									spec.getJvmArgs().add("-XX:+CMSClassUnloadingEnabled");
									spec.getJvmArgs().add("-XX:SurvivorRatio=2");
									spec.getJvmArgs().add("-XX:+DisableExplicitGC");
									spec.getJvmArgs().add("-Dfml.readTimeout=120");
									System.out.println("-Xmx"+rammc.toString().replace(".0", "")+"M");
									if (sw.contains("skyblock")) {
										spec.getLaunchArgs().add("--server api.technicworld.pl --port 25565");
									} else if(sw.contains("survival")) {
										spec.getLaunchArgs().add("--server api.technicworld.pl --port 25566");
									}
									System.out.println(spec.getLaunchArgs());
									System.out.println(spec.getJvmArgs());

									//System.out.println(Paths.get(System.getProperty("java.home"), "bin", OS.getCURRENT() == OS.WINDOWS ? "java.exe" : "java"));
									if (task.getCompletedPercentage() == 100) {

										spec.run(Paths.get(System.getProperty("java.home"), "bin", OS.getCURRENT() == OS.WINDOWS ? "java.exe" : "java"));
										//;
										
										System.exit(0);
									}
		            }};
			    new Thread(updatethread).

			    start();
				
			} catch(Exception e) {
				e.printStackTrace();
				
			}
			
		} else {
			LOGGER.log(Level.INFO, "btn_click(play): NOTPLAYABLE");
		}
	}
	
	@FXML
	
	public void Procent() {
		web.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        web.setVisible(true);
		wbv.setVisible(false);
	}

	
	@SuppressWarnings("finally")
	public boolean modpack() throws JSONException, IOException, NoSuchAlgorithmException {
		try {
			getModsJson();
			if(startCheckingAndDownloadMods(urlModsPath, modsPath) == true) {
				try {
					if(!checkMods(urlModsPath, modsPath)) {
						System.out.println("Wyst¹pi³ nieznany problem!");
					}
					downloadConfigs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Wyst¹pi³ nieznany problem!");
			}
		
		} finally {
			isplay = "1";
	        // update progress bar
	        Platform.runLater(new Runnable() {;

	            @Override
	            public void run() {
	          	downinfo.setVisible(false);
	          	download.setVisible(false);
	          	down.setVisible(false);
	        	down.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	        	downinfo.setText("Sprawdzam mody...");
	             
	            }
	            
	        });
	        return true;
		}
	}
	
	private boolean getModsJson() throws JSONException, IOException {
		JSONObject json = readJsonFromUrl(urlModsList);
		jsonSize = json.getJSONArray("mods").length();
		urlPackVersion = json.getString("version");
	    for (int i = 0; i < jsonSize; i++) {
	        JSONObject jsonObject = json.getJSONArray("mods").getJSONObject(i);
	        modsListClass arrayMods = new modsListClass();
	        arrayMods.setId(i);
	        arrayMods.setName(jsonObject.get("name").toString());
	        arrayMods.setAddon(jsonObject.get("addon").toString());
	        arrayMods.setHash(jsonObject.get("md5").toString().toLowerCase());
	        modsList.add(arrayMods);
	        mods+=1;
	    }
		return true;
	}
	
	private String getModHash(String mod) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		File folder = new File(mod);
        if (!folder.exists()) {
        	return "false";
        }
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    String digest = getDigest(new FileInputStream(mod), md, 2048);
	    return digest.toLowerCase();
	     
	}
	@SuppressWarnings("finally")
	private boolean checkMods(String modsUrl, String modspath) throws IOException {
        isplay = "0";
        // update progress bar
        Platform.runLater(new Runnable() {;

            @Override
            public void run() {
          	downinfo.setVisible(true);
          	download.setVisible(true);
          	down.setVisible(true);
        	down.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        	downinfo.setText("Sprawdzam mody...");
              
            }
        });
		try {
			File folder = new File(modspath);
		    File[] listOfFiles = folder.listFiles();
		    String modsStr = "";
			for (modsListClass mods : modsList) {
				modsStr+=mods.getName();
				if (mods.isAddon()) {
					modsStr+=mods.getName()+".disable";
				}
				if(mods.isAddon()) {
					String modStr = modspath+"/"+mods.getName().replace("|", "//");
					File mod = new File(modStr);
					String modStrAddon = modspath+"/"+mods.getName().replace("|", "//")+".disable";
					File modAddon = new File(modStrAddon);
					if (!modAddon.exists() && !mod.exists()) {
						downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
					} else {
						try {
							if (!getModHash(modStrAddon).contains(mods.getHash().toLowerCase()) && !getModHash(modStr).contains(mods.getHash().toLowerCase())) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					String modStr = modspath+"/"+mods.getName().replace("|", "//");
					File mod = new File(modStr);
					if (mod.exists()) {
						try {
							if (!getModHash(modStr).contains(mods.getHash().toLowerCase())) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
					}
				}
			}
		    for (int i = 0; i < listOfFiles.length; i++) {
		    	 if (listOfFiles[i].isFile()) {
					 if(!modsStr.contains(listOfFiles[i].getName()) == true) {
						 System.out.println(listOfFiles[i].getName()+", usuwanie...");
						 Path pat = Paths.get(modspath+"//"+listOfFiles[i].getName());
		            	 Files.delete(pat);
		            	 System.out.println("Usuniêto!");
		    		 }
		         }
		    }
		} finally {
			return true;
		}
	}
	
	@SuppressWarnings("unused")
	private void downloadConfigs() throws IOException {
		if (!settings.getPackVersion().contains(urlPackVersion)) {
	            isplay = "0";
	            // update progress bar
	            Platform.runLater(new Runnable() {;
	
	                @Override
	                public void run() {
	              	downinfo.setVisible(true);
	              	download.setVisible(true);
	              	down.setVisible(true);
	            	down.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	            	downinfo.setText("Pobieram: konfiguracja (config)");
	                  
	                }
	            });
				System.out.println(settings.getPackVersion()+"::"+urlPackVersion);
  	            URL url = new URL("https://api.technicworld.pl/api/config-"+settings.getPack()+".zip");
  	            File p1 = new File(settings.workingDir+"//"+settings.getPack()+"//Minecraft");
		  	    unpackArchive(url, p1);
				settings.setPackVersion(urlPackVersion);
				settings.saveSettings();
		}
			
	}
	
    public static File unpackArchive(URL url, File targetDir) throws IOException {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        InputStream in = new BufferedInputStream(urlConnection.getInputStream(), 1024);
        // make sure we get the actual file
        File zip = File.createTempFile("arc", ".zip", targetDir);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
        copyInputStream(in, out);
        out.close();
        return unpackArchive(zip, targetDir);
    }
    @SuppressWarnings("resource")
	public static File unpackArchive(File theFile, File targetDir) throws IOException {
        if (!theFile.exists()) {
            throw new IOException(theFile.getAbsolutePath() + " nie istn");
        }
        if (!buildDirectory(targetDir)) {
            throw new IOException("Could not create directory: " + targetDir);
        }
        ZipFile zipFile = new ZipFile(theFile);
        for (Enumeration<?> entries = zipFile.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            File file = new File(targetDir, File.separator + entry.getName());
            if (!buildDirectory(file.getParentFile())) {
                throw new IOException("Could not create directory: " + file.getParentFile());
            }
            if (!entry.isDirectory()) {
                copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
            } else {
                if (!buildDirectory(file)) {
                    throw new IOException("Could not create directory: " + file);
                }
            }
        }
        zipFile.close();
        return theFile;
    }

    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len >= 0) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }

    public static boolean buildDirectory(File file) {
        return file.exists() || file.mkdirs();
    }
	
	private void downloadMod(URL url, String path, String modname, File modsPath, File modsPath2, Boolean addon) {
		if (addon) {
			path +=".disable";
		}
		 try  {
			 if (!modsPath2.exists()) {
		         System.out.println("Tworzê folder: " + modsPath2.getName());
		         boolean result = false;
		
		         try{
		        	 modsPath2.mkdirs();
		             result = true;
		         } 
		         catch(SecurityException se){
		         }        
		         if(result) {    
		             System.out.println("stworzono!");  
		         }
		     }
			 if (!modsPath.exists()) {
		         System.out.println("Tworzê folder: " + modsPath.getName());
		         boolean result = false;
		
		         try{
		        	 modsPath.mkdirs();
		             result = true;
		         } 
		         catch(SecurityException se){
		         }        
		         if(result) {    
		             System.out.println("Stworzono!");  
		         }
		     }
			 System.out.println("Pobieram mod: "+modname+" ...");
			 
             HttpURLConnection httpsConnection = (HttpURLConnection) (url.openConnection());
             httpsConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
             long completeFileSize = httpsConnection.getContentLength();
             java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpsConnection.getInputStream());
             java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
             java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
             byte[] data = new byte[1024];
             long downloadedFileSize = 0;
             int x = 0;
             while ((x = in.read(data, 0, 1024)) >= 0) {
                 downloadedFileSize += x;
                 // calculate progress
                 final double currentProgress = ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100d);
                 isplay = "0";
                 // update progress bar
                 Platform.runLater(new Runnable() {;

                     @Override
                     public void run() {
                   	downinfo.setVisible(true);
                   	download.setVisible(true);
                   	down.setVisible(true);
                 	down.setProgress(currentProgress/100);
                    downinfo.setText("Pobieram: "+url.toString().replace("https://api.technicworld.pl/api/modpack-"+swlink+"/", "").replaceAll("https://api.technicworld.pl/api/", ""));
                       
                     }
                 });
                 bout.write(data, 0, x);
             }
             bout.close();
             in.close();
             System.out.println("Pobrano!");
         } catch (FileNotFoundException e) {
       	  	 System.out.println(e);
         } catch (IOException e) {
        	 System.out.println(e);
         }
	}
	
	@SuppressWarnings("finally")
	private boolean startCheckingAndDownloadMods(String modsUrl, String modspath) throws MalformedURLException {
		try {
			for (modsListClass mods : modsList) {
				if(mods.isAddon()) {
					String modStr = modspath+"/"+mods.getName().replace("|", "//");
					File mod = new File(modStr);
					String modStrAddon = modspath+"/"+mods.getName().replace("|", "//")+".disable";
					File modAddon = new File(modStrAddon);
					if (!modAddon.exists() && !mod.exists()) {
						downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
					} else {
						try {
							if (!getModHash(modStrAddon).contains(mods.getHash().toLowerCase()) && !getModHash(modStr).contains(mods.getHash().toLowerCase())) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					String modStr = modspath+"/"+mods.getName().replace("|", "//");
					File mod = new File(modStr);
					if (mod.exists()) {
						try {
							if (!getModHash(modStr).contains(mods.getHash().toLowerCase())) {
								downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						downloadMod(new URL(modsUrl+"/"+mods.getName().replace("|", "/").replaceAll(" ", "%20")), modspath+"//"+mods.getName().replace("|", "//"), mods.getName(), new File(modspath+"//1.7.10"), new File(modspath), mods.isAddon());
					}
				}
			}
		} finally {
			return true;
		}
	}
	
	private String readAll(Reader rd) throws IOException {
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
	
	public String getDigest(InputStream is, MessageDigest md, int byteArraySize)
		throws NoSuchAlgorithmException, IOException {
			md.reset();
			byte[] bytes = new byte[byteArraySize];
			int numBytes;
			while ((numBytes = is.read(bytes)) != -1) {
				md.update(bytes, 0, numBytes);
			}
			byte[] digest = md.digest();
			String result = new String(Hex.encodeHex(digest)).toUpperCase();
			return result;
	}
	
//ANIMATIONS :C
     private static JFXTemplateBuilder<Node> fadeInBTN() {
    	 	ColorAdjust color = new ColorAdjust(0, 0, 0, 0); 
    	    return JFXAnimationTemplate.create()
    	        .from()
    	        // One execution for init behaviour.
    	        .action(
    	            b -> b.executions(1).onFinish((node, actionEvent) -> node.setEffect(color)))
    	        .percent(1)
    	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
    	        .percent(99)
    	        .action(b -> b.target(color.hueProperty()).endValue(0.60))
    	        .to()
    	        .action(b -> b.target(color.hueProperty()).endValue(0.60))
    	        .config(
    	            b -> b.duration(Duration.seconds(0.3)).interpolator(Interpolator.EASE_BOTH));
    	  }
     private static JFXTemplateBuilder<Node> fadeOutBTN() {
 	 	ColorAdjust color = new ColorAdjust(0, 0, 0, 0); 
 	    return JFXAnimationTemplate.create()
 	        .from()
 	        // One execution for init behaviour.
 	        .action(
 	            b -> b.executions(1).onFinish((node, actionEvent) -> node.setEffect(color)))
 	        .percent(1)
 	        .action(b -> b.target(color.hueProperty()).endValue(0.60))
 	        .percent(99)
 	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
 	        .to()
 	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
 	        .config(
 	            b -> b.duration(Duration.seconds(0.3)).interpolator(Interpolator.EASE_BOTH));
 	  }
     private static JFXTemplateBuilder<Node> fadeInBTNlogin() {
 	 	ColorAdjust color = new ColorAdjust(0, 0, 0, 0); 
 	    return JFXAnimationTemplate.create()
 	        .from()
 	        // One execution for init behaviour.
 	        .action(
 	            b -> b.executions(1).onFinish((node, actionEvent) -> node.setEffect(color)))
 	        .percent(1)
 	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
 	        .percent(99)
 	        .action(b -> b.target(color.hueProperty()).endValue(-0.40))
 	        .to()
 	        .action(b -> b.target(color.hueProperty()).endValue(-0.40))
 	        .config(
 	            b -> b.duration(Duration.seconds(0.3)).interpolator(Interpolator.EASE_BOTH));
 	  }
  private static JFXTemplateBuilder<Node> fadeOutBTNlogin() {
	 	ColorAdjust color = new ColorAdjust(0, 0, 0, 0); 
	    return JFXAnimationTemplate.create()
	        .from()
	        // One execution for init behaviour.
	        .action(
	            b -> b.executions(1).onFinish((node, actionEvent) -> node.setEffect(color)))
	        .percent(1)
	        .action(b -> b.target(color.hueProperty()).endValue(-0.40))
	        .percent(99)
	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
	        .to()
	        .action(b -> b.target(color.hueProperty()).endValue(0.00))
	        .config(
	            b -> b.duration(Duration.seconds(0.3)).interpolator(Interpolator.EASE_BOTH));
	  }
	  @FXML
	  private void playAnim(MouseEvent event) {
		  Timeline timeline = fadeInBTN().build(JFXAnimationTemplates::buildTimeline, playText);
		  timeline.play();
	  }
	  @FXML
	  private void playExit(MouseEvent event) {
		  Timeline timeline = fadeOutBTN().build(JFXAnimationTemplates::buildTimeline, playText);
		  timeline.play();
	  }
	  @FXML
	  private void settingsAnim(MouseEvent event) {
		  Timeline timeline = fadeInBTN().build(JFXAnimationTemplates::buildTimeline, settingsICON);
		  timeline.play();
	  }
	  @FXML
	  private void settingsExit(MouseEvent event) {
		  Timeline timeline = fadeOutBTN().build(JFXAnimationTemplates::buildTimeline, settingsICON);
		  timeline.play();
	  }
	  @FXML
	  private void closeSettingsAnim(MouseEvent event) {
		  Timeline timeline = fadeInBTN().build(JFXAnimationTemplates::buildTimeline, closeSettingsText);
		  timeline.play();
	  }
	  @FXML
	  private void closeSettingsExit(MouseEvent event) {
		  Timeline timeline = fadeOutBTN().build(JFXAnimationTemplates::buildTimeline, closeSettingsText);
		  timeline.play();
	  }
	  @FXML
	  private void loginAnim(MouseEvent event) {
		  Timeline timeline = fadeInBTNlogin().build(JFXAnimationTemplates::buildTimeline, loginText);
		  timeline.play();
	  }
	  @FXML
	  private void loginExit(MouseEvent event) {
		  Timeline timeline = fadeOutBTNlogin().build(JFXAnimationTemplates::buildTimeline, loginText);
		  timeline.play();
	  }
}
