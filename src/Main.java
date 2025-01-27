
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import Stock.FoodItem;

public class Main {
    public static void main(String[]args) {
        CommandStorage cs = CommandStorage.getInstance(); //create a command list
        Record record = Record.getInstance();
        CareTaker careTaker = new CareTaker();
        Command com;
        BufferedWriter writer = null;

        CommandOptions co = new CommandOptions(record,careTaker,cs.getMap());
        //return Command Creator by comparing the character
        
        do {
            createMenu(cs.toString()); //create starting menu
            
            try { 
                char c = getRequest();

                //make a save point
                CommandCreator tmp = co.getOpt(c);
                if(tmp != null) { //check any matched result
                    com = tmp.newCommand(); //new the matching command
                    com.execute();
                } else if ('o' == c) {
                	int size = record.record.size();
                	String filename = System.getProperty("user.dir") + "\\Report_" + 
                						LocalDateTime.now().toString().replace(':', '-')
                						+ ".txt";
                	                	
                	if (size > 0) {
                		try {
                			new File(filename).createNewFile();
                			
	                	    writer = new BufferedWriter(new FileWriter(filename));
	                	    
	            	    	writer.write("ID\tName\tQuantity\tOther Info\tValue\n");
	
	                	    for(int i = 0; i < size; i++) {
	                	    	writer.write(((FoodItem) record.record.get(i)).tableize());
	                	    }                	    
	                	    
	                	    System.out.print("Report located in : " + filename + '\n');
                		} catch (IOException ex) {
                			ex.printStackTrace();
	                	} finally {
	                	   try {writer.close();} catch (Exception ex) {ex.printStackTrace();}
	                	   writer = null;
	                	}
                		
	                } else {
	                    System.out.println("Item List have no record.\n");
	                }
                
                } else // if no matched result
                    System.out.println("Command not found.\n");
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void createMenu(String str) {
        System.out.println("Advanced Inventory Management System");
        System.out.println("Please enter command: ["+ str +"]");
        System.out.println("c = create item,  s = show item,  g = receive item,  d = distribute item \n u = undo,  r = redo,  l = list undo/redo,  x = exit system,  o = output as CSV");
    }
	
    public static char getRequest() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          String cmd=br.readLine();
                if(cmd.length()>1) {
                    return (char)0;
                }
                return cmd.charAt(0);
    }

}
