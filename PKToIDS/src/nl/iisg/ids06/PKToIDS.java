package nl.iisg.ids06;

/*
* Naam:    Main
* Version: 0.1
* Author:  Cor Munnik
* Copyright
*/



import java.io.*;
import java.util.Date;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JTextArea;

import nl.iisg.idscontext.Contxt;


/**
 * 
 * This is the top class of the application   
 * It is the main module
 *
 */
public class PKToIDS implements Runnable{

    static JTextArea textArea = null;
    static DataOutputStream out = null;

    public PKToIDS(DataOutputStream out) {
        setTextArea(textArea);
        setOut(out);
    }


    public void run() {

        main(new String[0]);
    }



	
  public static void main( String args[]) {

	ArrayList<ArrayList> all = new ArrayList<ArrayList>();
	
    print("\nPersonal Cards - to IDS      started\n");

	  
	for(int i = 0; i < 10; i++){ // because there are 10 table types: {"B2_ST", "B313_ST", "B32_ST", "B33_ST", "B34_ST", "B35_ST", "B36_ST", "B37_ST", "B4_ST", "B6_ST"}; 
		all.add(new ArrayList());
	}

    if(false)
    {
    	File file  = new File("h:/sysout.log"); 
    	try{
    		PrintStream printStream = new PrintStream(new FileOutputStream(file));
    		System.out.println("Output in h:/sysout.log");  
    		System.setOut(printStream); 

    	}
    	catch(FileNotFoundException e){}  
    }
     
    Runtime runtime = Runtime.getRuntime();
    
    System.out.println("Free Memory:"  + runtime.freeMemory() / (1024 * 1024)); 
    System.out.println("Total Memory:" + runtime.totalMemory() / (1024 * 1024));
    System.out.println("Max Memory:" + runtime.maxMemory() / (1024 * 1024));
    
    String inputDirectory = null;
    
    Date startTime = new Date();
    System.out.println("Start Time : " + startTime);
    
    // Initialization
    
    Initialiser i = new Initialiser();
    
    // Create IDS tables 
    
    print("Creating or resetting IDS tables...");
    i.createIDSTables();
    
    // Initialize Context System

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hsn_perscd_ids");				
	EntityManager em = factory.createEntityManager();	

    System.out.println("Start initializing Context System");
	print("Initializing Context system...");
    Contxt.initializeContext(); 
    System.out.println("Finished initializing Context System");
    
    // Load B*_ST tables 
    
    print("Reading input tables...");
    i.loadTables(all);
    
    
    // Link the objects

    List<OP> ops = i.linkObjects(all);
    
    // test 
    
	try{
		print("Converting...");
		
		em.getTransaction().begin();

		int idCount = 0;
	    for(OP op: ops){
	    	for(B4_ST r: op.getRegistrationsStandardizedOfOP()){
	    		for(B2_ST p: r.getPersons()){
	    			p.convert(em);
	    		}
	    	}
	    	if(++idCount % 100 == 0)
	    		print("Processed " + idCount + " IDNRs");
	    }
	    System.out.print("Processed " + idCount + " IDNRs");
	    
	    System.out.println("Start saving");
	    
	    //Contxt.printTopList();
		print("Saving Context System...");

	    
	    Contxt.save();

		print("Saving IDS Tables...");

		em.getTransaction().commit();
		em.clear();

	    System.out.println("Finished saving");

		
		System.out.println("Finished converting");

	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}


    Date endTime = new Date();
    System.out.println("End Time : " + endTime);
    
    
    System.out.println("Free Memory:"  + runtime.freeMemory() / (1024 * 1024)); 
    System.out.println("Total Memory:" + runtime.totalMemory() / (1024 * 1024));
    System.out.println("Max Memory:" + runtime.maxMemory() / (1024 * 1024));
    
    long runTime = (endTime.getTime() - startTime.getTime())/1000;

    System.out.println("Run Time : " + runTime + " seconds");

    System.out.println("Done");

    print("\nPersonal Cards - to IDS      finished \n");

    }

public static JTextArea getTextArea() {
	return textArea;
}

public static void setTextArea(JTextArea textArea) {
	PKToIDS.textArea = textArea;
}

public static DataOutputStream getOut() {
	return out;
}

public static void setOut(DataOutputStream out) {
	PKToIDS.out = out;
}  
  
public static void print(String line) {
    if (out != null) {
        try {
            out.writeUTF(line);
        } catch (IOException e) {
           // e.printStackTrace();
        	System.out.println("Client Message: " + line);
        }
    } else {
        System.out.println(line);
    }
}



  
}


