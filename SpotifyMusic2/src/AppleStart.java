import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AppleStart extends JFrame{

	   private static final int FRAME_WIDTH = 650;
	   private static final int FRAME_HEIGHT = 300;
	   
	   private JLabel tLabel;
	   private JTextField tField;
	   
	   private JLabel emailLabel;
	   private JLabel passwordLabel;
	   
	   private JTextField emailField;
	   private JTextField passwordField;
	   
	   private JButton button;
	   private String url = "";
	   private String email = "";
	   private String password = "";
	   private JFrame frame;
	   private JPanel panel;
	   //test
	   //gdfgdfgfd
	   public AppleStart()
	   {  
		  frame = new JFrame();
		  frame.setTitle("Apple -> Spotify");
	      createTextFields();
	      createLabels();
	      createButton();
	      createPanel();
	      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
	   }

	   class AddInterestListener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent event)
	      {
	    	 
	    		  url = tField.getText();
	    		  email = emailField.getText();
	    		  password = passwordField.getText();
	    		  try {	   	
	    		  String stringUrl = url.toString();
	    		  Document doc = Jsoup.connect(stringUrl).get(); //might need to use stringUrl instead of URL
	    		  boolean isFound = stringUrl.contains("music.apple.com");
	    		  if(isFound) // apple - > Spotify
	    		  {
	    			  Desktop.getDesktop().browse(URI.create(url));    
	    			
	    			  ArrayList<String> songName = new ArrayList<String>();
	    			  ArrayList<String> artistNames = new ArrayList<String>();
	    			
	    			 // Elements song = doc.select("div.product-page-header__metadata.album-header-metadata");
	    			 //Elements title = doc.select("h1.product-name.typography-large-title-semibold.clamp-4");
	    			 // System.out.println(doc.body().text());
	    			  stringUrl = URLDecoder.decode(new String(stringUrl.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"); //fix for songs with accent marks
	    			  String regexUrl = Pattern.quote("https://music.apple.com/us/album/") + "(.*?)" + Pattern.quote("/");
	    			  Pattern pattern = Pattern.compile(regexUrl);
	    			  Matcher matcher = pattern.matcher(stringUrl);
	    			  
	    			  if (matcher.find()) {
	    			  	songName.add(matcher.group(1).trim().toString().replaceAll("-", " "));
	    			  	System.out.println(songName.get(0));
	    			  }	  
	    			  Elements artistList = doc.select("div.product-creator.typography-large-title");
	    			 // String test = doc.body().text();
	    			 // System.out.println(test);
	    		
	    			  Pattern pattern2;
	    			  Matcher matcher2;
	    			  //use iterator?
	    			  
	    			  String stringArtistList = URLDecoder.decode(new String(artistList.toString().getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"); //fix for artists with accent marks
	    			  
	    				 String reg = Pattern.quote("artist/") + "(.*?)" + Pattern.quote("/");
		    			  pattern2 = Pattern.compile(reg);
		    			  matcher2 = pattern2.matcher(stringArtistList);	
		    			//  System.out.println(artistList);
		    			  //regex (?<=\[)(.*?)(?=\]) - get between 2 brackets
		    			  // "(?<=\>)(.*?)(?=\<)"
	    			
		    			  int count =0;		   
		    			  while (matcher2.find()) {
		    			  	artistNames.add(matcher2.group(1).trim().toString().replaceAll("-", " "));
		    			  	System.out.println(artistNames.get(count));
		    			  	count++;
		    			  
		    			  }
		    			  TimeUnit.SECONDS.sleep(1);
		    			String [] keys = {"VK_CONTROL", "VK_W"};
		    	        sendKeysCombo(keys);

		    			  Desktop.getDesktop().browse(URI.create("https://accounts.spotify.com/en/login?continue=https%3A%2F%2Fopen.spotify.com%2Fsearch"));  
		    			  TimeUnit.SECONDS.sleep(3);
		    			  
		    			  pressKeyTab();
		    			  
		    			  
		    		  
		    			/*  int x = 0;
		    			  int y = 0;
		    			  
		    			    Robot bot = new Robot();
		    			    bot.mouseMove(x, y);    
		    			    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    			    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);*/
		    			  
		    		  
	    		  }
	    		  
	    		  else //Spotify - > apple
	    		  {
	    			  Desktop.getDesktop().browse(URI.create(url));
	    		  }

	    	    } catch (IOException e) {
	    	        System.out.println(e.getMessage());
	    	    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}/* catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
	      }
	   }
	    		  /*
	    		  catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }            
	   } 
	   
	   private static void sendMouseClicks(String click[]) throws AWTException {
		   Robot mouseBot = new Robot();
	   }
	    		  */
	   private static void sendKeysCombo(String keys[]) {
	        try {

	            Robot robot = new Robot();

	            Class<?> cl = KeyEvent.class;

	            int [] intKeys = new int [keys.length];

	            for (int i = 0; i < keys.length; i++) {
	                Field field = cl.getDeclaredField(keys[i]);
	                intKeys[i] = field.getInt(field);
	                robot.keyPress(intKeys[i]);
	            }

	            for (int i = keys.length - 1; i >= 0; i--)
	                robot.keyRelease(intKeys[i]);
	        }
	        catch (Throwable e) {
	            System.err.println(e);
	        }
	    }
	   
	   private void pressKeyTab()
	   {
		   try
		   {
			   Robot robot = new Robot();
			   
			   robot.keyPress(9); // 9 = keycode for TAB Button
			   robot.keyRelease(9);
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   
			   sendKeys(robot,email);
			   
			   
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   
			   sendKeys(robot,password);
			   
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   robot.keyPress(9);
			   robot.keyRelease(9);
			   String [] k= {"VK_ENTER"};
	    	   sendKeysCombo(k);
		   }
		   catch (Throwable e) {
	            System.err.println(e);
	        }
	   }
	   
	   void sendKeys(Robot robot, String keys) {
		    for (char c : keys.toCharArray()) {
		    	 if (c == '@') {
		    		 String [] k= {"VK_SHIFT", "VK_2"};
		    		 sendKeysCombo(k);
			         continue;        	
			        } 
		    	 if (c == ')') {
		    		 String [] k= {"VK_SHIFT", "VK_0"};
		    		 sendKeysCombo(k);
			         continue;  	
			        }
		    	 if (c == '(') {
		    		 String [] k= {"VK_SHIFT", "VK_9"};
		    		 sendKeysCombo(k);
			         continue; 	
			        }
		    	 
		    	 if (c == '%') {
		    		 String [] k= {"VK_SHIFT", "VK_5"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		    	 
		    	 if (c == '!') {
		    		 String [] k= {"VK_SHIFT", "VK_1"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		    	 if (c == '#') {
		    		 String [] k= {"VK_SHIFT", "VK_3"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		    	 if (c == '$') {
		    		 String [] k= {"VK_SHIFT", "VK_4"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		    	 if (c == '^') {
		    		 String [] k= {"VK_SHIFT", "VK_6"};
		    		 sendKeysCombo(k);
			         continue;	
			        }	 
		    	 if (c == '&') {
		    		 String [] k= {"VK_SHIFT", "VK_7"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		    	 if (c == '*') {
		    		 String [] k= {"VK_SHIFT", "VK_8"};
		    		 sendKeysCombo(k);
			         continue;	
			        }
		        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
		        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
		            throw new RuntimeException(
		                "Key code not found for character '" + c + "'");
		        }
		        
		        boolean upperCase = Character.isUpperCase(c);
		        if(upperCase){
		        	 robot.keyPress(20);
		        	 robot.keyPress(keyCode);
				     robot.delay(50);
				     robot.keyRelease(20);
				     robot.keyRelease(keyCode);
				     robot.keyPress(20);
				     robot.keyRelease(20);
				     robot.delay(200);
		        }
		        else
		        {     
		        	robot.keyPress(keyCode);
		        	robot.delay(50);
		        	robot.keyRelease(keyCode);
		        	robot.delay(50);
		        }
		        
		    }
		}
	   
	   private void createTextFields()
	   {		  
	      final int FIELD_WIDTH = 30;
	      tField = new JTextField(FIELD_WIDTH);
	      tField.setBounds(200,10,150,20);	      
	      
	      emailField = new JTextField(20);
	      emailField.setBounds(150,40,100,20);
	      passwordField = new JTextField(20);
	      passwordField.setBounds(150,70,100,20);
	   }
	   
	   private void createLabels()
	   {
		  tLabel = new JLabel("Enter Apple URL(include https://): ");
		  tLabel.setBounds(10,10,200,20);
		  tLabel.setForeground(Color.white);   
		  
		  emailLabel = new JLabel("Enter Spotify Email");
		  emailLabel.setBounds(10,40,150,20);
		  passwordLabel = new JLabel("Enter Spotify Password");
		  passwordLabel.setBounds(10,70,150,20);
		  
		  emailLabel.setForeground(Color.white);
		  passwordLabel.setForeground(Color.white);
	   }
	   
	   // create a button
	   private void createButton()
	   {
	      button = new JButton("Enter");
	      button.setBounds(360,10,75,20);
	      ActionListener listener = new AddInterestListener();
	      button.addActionListener(listener);
	   }

	   //create a panel
	   private void createPanel()
	   {
	      panel = new JPanel();
	      panel.setLayout(null);
	      panel.add(tLabel);
	      panel.add(tField);
	      panel.add(button);
	      panel.add(passwordField);
	      panel.add(passwordLabel);
	      panel.add(emailField);
	      panel.add(emailLabel);
	      panel.setBackground(new Color (32, 42, 68)); 
	      // adds to current object
	      frame.add(panel);
	   } 
	   
}
