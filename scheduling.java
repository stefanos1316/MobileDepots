import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class scheduling {
	
	//Define global variables like Low and High Capacity Truck weight Threshold 
	public static int LowCapacityTruckThreshold = 2500; //kg said from Theodoros on 11/3/2015
	public static int HighCapacityTruckThreshold = 9500; //kg said from Theodoros on 11/3/2015 ;)
	public static  File file_Exp_1 = new File("src\\Experiment_1_Results.txt");
	public static  File file_Exp_3 = new File("src\\Experiment_3_Results.txt");
	public static  File file_Exp_4 = new File("src\\Experiment_4_Results.txt");
	public static  File file_Exp_5  = new File("src\\Experiment_5_Results.txt");
	public static  File file_Exp_6	= new File("src\\Experiment_6_Results.txt");
	public static  File file_Exp_7	= new File("src\\Experiment_7_Results.txt");
	public static  File file_Exp_8  = new File("src\\Experiment_8_Results.txt"); 
	public static double startHCT = 0;
	public static double endHCT = 0;
	//Set the HCT and the LCT as global? maybe no need ....
	private static BufferedReader br;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		//As input Parameters from the algorithm we are taking the LCT and HCT positions
		//Make modification by checking first the number of lines in the file and then allocate array space (more dynamic)
		//If those files exist remove them
		 if (file_Exp_3.exists()) file_Exp_3.delete();
		 if (file_Exp_4.exists()) file_Exp_4.delete();
	     if (file_Exp_1.exists()) file_Exp_1.delete();
	     if (file_Exp_5.exists()) file_Exp_5.delete();
	     if (file_Exp_6.exists()) file_Exp_6.delete();
	     if (file_Exp_7.exists()) file_Exp_7.delete();
	     if (file_Exp_8.exists()) file_Exp_8.delete();
	     
	     //Init table variables
	     ArrayList<String> list_LCT = new ArrayList<String>();
	     ArrayList<String> list_HCT = new ArrayList<String>();
	     ArrayList<String> list_routes = new ArrayList<String>();
	     ArrayList<String> list_mathcing = new ArrayList<String>();
		
	    br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\LCT_GPS_coordinates.txt"));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			list_LCT.add(sCurrentLine);
		}
		
		//Add the information in String array (easier to manage)
		final String[][] LCTgpsCoordinate = new String[list_LCT.size()][3];
		for ( int i=0; i<list_LCT.size(); ++i)
			LCTgpsCoordinate[i] = list_LCT.get(i).split("\\s+");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//This Part will read the file and get the HighCapacityTrucks information
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\HCT_GPS_coordinates.txt"));
		while ((sCurrentLine = br.readLine()) != null){
			list_HCT.add(sCurrentLine);
		}
		
		//Add the information in String array (easier to manage)
		final String[][] HCTgpsCoordinate = new String[list_HCT.size()][3];
		for ( int i=0; i<list_HCT.size(); ++i)
			HCTgpsCoordinate[i] = list_HCT.get(i).split("\\s+");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//This Part will read the file and get the GPS_coodinates routes
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_routes.txt"));
		
		while ((sCurrentLine = br.readLine()) != null){
			//gpsCoordinateRoutes[cursor] = sCurrentLine;
			list_routes.add(sCurrentLine);
		}
		
		//Add the information in String array (easier to manage)
		final String[] gpsCoordinateRoutes = new String[list_routes.size()];
		for ( int i=0; i<list_routes.size(); ++i)
			gpsCoordinateRoutes[i] = list_routes.get(i);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//This Part will read the file and get the GPS_coodinates matching points
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_matching_points.txt"));
		while ((sCurrentLine = br.readLine()) != null) {
			//gpsCoordinateMatchingPoints[cursor] = sCurrentLine;
			list_mathcing.add(sCurrentLine);
		}
		
		//Add the information in String array (easier to manage)
		final String[] gpsCoordinateMatchingPoints = new String[list_mathcing.size()];	
		for ( int i=0; i<list_mathcing.size(); ++i)
			gpsCoordinateMatchingPoints[i] = list_mathcing.get(i);
		
		//Create 2 threads just to exectution
		Thread thread_HCT = new Thread(){
			public void run(){
				try {
					checkHCT(HCTgpsCoordinate);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};				
				
		Thread thread_LCT = new Thread(){
			public void run(){
				try {
					checkLCT(LCTgpsCoordinate, HCTgpsCoordinate, gpsCoordinateMatchingPoints, gpsCoordinateRoutes);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};		
		thread_LCT.start();
		thread_HCT.start();		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// F U N C T I O N S   A R E   H E R E ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//This function will be executed by a thread to run each time to check when the threshold has been reached HCT
	public static void checkHCT(String[][] HCT_Coordinates) throws FileNotFoundException{
		
		//execute a for loop to check HCTs Capacity
		while (true) {
		for ( int i=0; i<HCT_Coordinates.length; ++i )
		{			
			System.out.println("Garbages in HCT_"+(i+1)+" truck are "+HCT_Coordinates[i][2]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( Integer.parseInt(HCT_Coordinates[i][2]) > HighCapacityTruckThreshold)
			{
				startHCT = System.currentTimeMillis();
				//Call function for experiment 5
				//Call it before setting it to 0
				createExperimentSixAndSeven(HCT_Coordinates, "HCT");
				//Empty the truck in the dump
				HCT_Coordinates[i][2] = "0";
				endHCT = System.currentTimeMillis();
				double AverageTimeMill = endHCT - startHCT;
				try {
					caputreResponseTimeBetweenTriggers_HCT( AverageTimeMill );
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		}
	}
	
	//This function will be executed by a thread to run each time to check when the threshold has been reached LCT
	public static void checkLCT(String[][] LCT_Coordinates, String[][] HCT_Coordinates, String[] gpsCoordinateMatchingPoints, String[] gpsCoordinatesRoutes) throws FileNotFoundException, UnsupportedEncodingException{		
		
		//Variable to get routing algorith elapse time
		double startRoutingTime=0;
		double elapseRoutingTime=0;
		//execute a for loop to check LCTs Capacity
		while (true) { 
		for ( int i=0; i<LCT_Coordinates.length; ++i )
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Add Randomly garbages to the trucks (For Test purpose only)
			LCT_Coordinates[i][2] = String.valueOf(Integer.parseInt(LCT_Coordinates[i][2]) + createRandomGarbage());
			System.out.println("Garbage in LCT_"+(i+1)+" is : "+LCT_Coordinates[i][2]);
						
			if ( Integer.parseInt(LCT_Coordinates[i][2]) > LowCapacityTruckThreshold )
			{			
							
				//For experiment 1 purpose
				captureFrequncyOfSchedulerTriggered();
				
				//For experiment 4 capture System time in Mills
				double startTime = System.currentTimeMillis();
				
				//Call find nearest truck function
				nearest NearestObject = new nearest(LCT_Coordinates[i], HCT_Coordinates);
				String[] getNearestCoordinates = new String[3];
				getNearestCoordinates = NearestObject.nearestHCT(LCT_Coordinates[i], HCT_Coordinates);
				createExperimentFive(getNearestCoordinates,HCT_Coordinates,i);
				
				//Call find matching point function
				match MatchObject = new match(LCT_Coordinates[i],getNearestCoordinates,gpsCoordinateMatchingPoints);
				String matchingPointCoordinates = new String();
				matchingPointCoordinates = MatchObject.findMatchingPoint(LCT_Coordinates[i], getNearestCoordinates, gpsCoordinateMatchingPoints);	
				
				//Call find routing for LCT
				//Define Start Point and Destination for LCT
				String LCT_postion = LCT_Coordinates[i][0] +" "+ LCT_Coordinates[i][1] +" "+ LCT_Coordinates[i][2];
				String HCT_nearest_position = getNearestCoordinates[0] +" "+ getNearestCoordinates[1] +" "+ getNearestCoordinates[2];
				String Destination = matchingPointCoordinates;
			
				System.out.println("--------------------------------------");
				System.out.println("LCT routing path");
				routing RoutingObjectLCT = new routing(LCT_postion, Destination, gpsCoordinateMatchingPoints);
				
				//Experiment 8 here
				startRoutingTime = System.currentTimeMillis();
				RoutingObjectLCT.routingTrucks(LCT_postion, Destination, gpsCoordinateMatchingPoints);
				elapseRoutingTime = System.currentTimeMillis();
				createExperimentEight(elapseRoutingTime-startRoutingTime);
				
				//Call find routing for HCT
				System.out.println("--------------------------------------");
				System.out.println("HCT routing path");
				routing RoutingObjectHCT = new routing(HCT_nearest_position, Destination, gpsCoordinateMatchingPoints);
				RoutingObjectHCT.routingTrucks(HCT_nearest_position, Destination, gpsCoordinateMatchingPoints);
				
				//After this point the trucks have meet and the LCT transfers the garbages to the HCT.
				//Get the nearest trucks coordinates and empty it
				//Before set it zero check files
				createExperimentSixAndSeven(HCT_Coordinates, "LCT");
				
				for ( int j=0; j<HCT_Coordinates.length; ++j )
				{
					if ( HCT_Coordinates[j][0] == getNearestCoordinates[0] && HCT_Coordinates[j][1] == getNearestCoordinates[1])
					{
						HCT_Coordinates[j][2] = String.valueOf(Integer.parseInt(HCT_Coordinates[j][2]) + Integer.parseInt(LCT_Coordinates[i][2]));
						LCT_Coordinates[i][2] = "0";
					}				
				}
				
				double endTime = System.currentTimeMillis();
				double results = endTime - startTime;
				captureResponseTimeBetweenTriggers(results);
			}
		}
		}
	}
	
	public static int createRandomGarbage(){
		
			Random r = new Random();
			int Low = 50;
			int High = 200;
			int R = r.nextInt(High-Low) + Low;
			//System.out.println(R);	
		return R;
	}
	
	public static void captureFrequncyOfSchedulerTriggered() throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter writer;
		try {
			  
		      if (file_Exp_1.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}
			//For Experiment 1:
			//Measure the frequency in which the scheduling its triggered
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			
			writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_1_Results.txt"),true));
			writer.println(dateFormat.format(date));
			writer.close();
	}
	
	//Function for experiment 3
	public static void captureResponseTimeBetweenTriggers(double results) throws FileNotFoundException{
		PrintWriter writer;
		try {			 
		      if (file_Exp_3.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}
			writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_3_Results.txt"),true));
			writer.println(results+" milliseconds");
			writer.close();
	}
	
	//Function for experiment 4
	public static void caputreResponseTimeBetweenTriggers_HCT( double avg ) throws FileNotFoundException{
		PrintWriter writer;
		try {		 
		      if (file_Exp_4.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}			
			writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_4_Results.txt"),true));
			writer.println(avg+" milliseconds");
			writer.close();
	}
	
	//Function for experiment 5
	public static void createExperimentFive(String[] Nearest,String[][] HCT_coordinates,int LCT_id) throws FileNotFoundException{
		PrintWriter writer;
		
		try {
		      if (file_Exp_5.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}	
			
			for ( int i=0; i<HCT_coordinates.length; ++i ){
				if( HCT_coordinates[i][0].equals(Nearest[0]) && HCT_coordinates[i][1].equals(Nearest[1]) ){
		    	writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_5_Results.txt"),true));	
		    	writer.println((LCT_id+1)+" -> "+(i+1));				
		    	writer.close();	
		    	}
			}
	}
	
	//Function to create Random distance for LCT and HCT
	public static int createRandomDistance( int Low, int High){		
		Random r = new Random();
		int R = r.nextInt(High-Low) + Low;
		return R;
	}
	
	//Function for experiment 6 and 7
	public static void createExperimentSixAndSeven(String[][] Truck_Coordinates, String TruckType) throws FileNotFoundException{		
		PrintWriter writer;
		File fileName = null;
		if ( TruckType.equals("HCT") )
			fileName = file_Exp_7;
		else
			fileName = file_Exp_6;
		try {
		      if (fileName.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}	
		    if ( TruckType.equals("HCT") )
		    	writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_7_Results.txt"),true));
		    else
		    	writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_6_Results.txt"),true));	
		    
		    for ( int i=0; i<Truck_Coordinates.length; ++i ){
		    	if ( Integer.parseInt(Truck_Coordinates[i][2]) >= 9500 && Integer.parseInt(Truck_Coordinates[i][2]) < 12000 && TruckType.equals("HCT"))
		    		writer.println(Truck_Coordinates[i][2]+"	"+createRandomDistance(10,16)+"		"+createRandomDistance(20,31)+"	"+createRandomDistance(2,5));		
		    	if (  Integer.parseInt(Truck_Coordinates[i][2]) >= 2500 && Integer.parseInt(Truck_Coordinates[i][2]) < 3000 && !TruckType.equals("HCT"))
		    		writer.println(Truck_Coordinates[i][2]+"	"+createRandomDistance(2,8)+"		"+createRandomDistance(7,13)+"	"+createRandomDistance(1,3));
		    }
			
			writer.close();
	}
	
	//Function for experiment 8
	public static void createExperimentEight(double elapshTime) throws FileNotFoundException{
		PrintWriter writer;
		
		try {
		      if (file_Exp_8.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}	
		    	writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_8_Results.txt"),true));	
		    	if ( elapshTime >= 1.0 )
		    	writer.println(elapshTime + " milliseconds");				
		    	writer.close();
	}
}
