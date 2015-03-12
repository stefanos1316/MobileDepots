import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class scheduling {
	
	//Define global variables like Low and High Capacity Truck weight Threshold 
	public static int LowCapacityTruckThreshold = 2500; //kg said from Theodoros on 11/3/2015
	public static int HighCapacityTruckThreshold = 11000; //kg said from Theodoros on 11/3/2015 ;)
	
	//Set the HCT and the LCT as global? maybe no need ....

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//As input Parameters from the algorithm we are taking the LCT and HCT positions
		//Make modification by checking first the number of lines in the file and then allocate array space (more dynamic)
		
		
		//This Part will read the file and get the LowCapacityTruck's information
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\LCT_GPS_coordinates.txt"));
		String sCurrentLine;
		String[] LCTgpsCoordinate = new String[3];
		while ((sCurrentLine = br.readLine()) != null) 
			LCTgpsCoordinate = sCurrentLine.split("\\s+");
		
		//This Part will read the file and get the HighCapacityTrucks information
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\HCT_GPS_coordinates.txt"));
		String[][] HCTgpsCoordinate = new String[3][3];
		int cursor=0;
		while ((sCurrentLine = br.readLine()) != null){
			HCTgpsCoordinate[cursor] = sCurrentLine.split("\\s+");
			cursor++;
		}
		
		//This Part will read the file and get the GPS_coodinates routes
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_routes.txt"));
		String[] gpsCoordinateRoutes = new String[10];
		cursor=0;
		while ((sCurrentLine = br.readLine()) != null){
			gpsCoordinateRoutes[cursor] = sCurrentLine;
			cursor++;
		}
		
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_matching_points.txt"));
		cursor=0;
		String[] gpsCoordinateMatchingPoints = new String[10];
		while ((sCurrentLine = br.readLine()) != null) {
			gpsCoordinateMatchingPoints[cursor] = sCurrentLine;
			cursor++;
		}
		
		
		/*for ( int i=0; i<gpsCoordinate.length; ++i )
		System.out.println(gpsCoordinate[i]);*/
		
		//Here create 2 threads which each one will execute a for loop 1)HCTs and 2)LCTs
		while (true)
		{
			//check the threshhold values from each truck
			for ( int i=0; i<(LCTgpsCoordinate.length); ++i ){
				
				checkLCT(LCTgpsCoordinate,HCTgpsCoordinate,gpsCoordinateMatchingPoints,gpsCoordinateRoutes);
				System.out.println("the value is "+i);
			}
			break;
			
			
		}
	}
	
	public static void checkHCT(String[][] HCT_Coordinates){
		
		//execute a for loop to check HCTs Capacity
		for ( int i=0; i<HCT_Coordinates.length; ++i )
		{
			if ( Integer.parseInt(HCT_Coordinates[i][2]) > HighCapacityTruckThreshold)
			{
				//Empty the truck in the dump
				HCT_Coordinates[i][2] = "0";
			}
		}
	}
	
	public static void checkLCT(String[] LCT_Coordinates, String[][] HCT_Coordinates, String[] gpsCoordinateMatchingPoints, String[] gpsCoordinatesRoutes) throws FileNotFoundException, UnsupportedEncodingException{		
		
		//execute a for loop to check LCTs Capacity
		for ( int i=0; i<LCT_Coordinates.length; ++i )
		{
			if ( Integer.parseInt(LCT_Coordinates[2]) > LowCapacityTruckThreshold )
			{
				
				//Call find nearest truck function
				nearest NearestObject = new nearest(LCT_Coordinates, HCT_Coordinates);
				String[] getNearestCoordinates = new String[3];
				getNearestCoordinates = NearestObject.nearestHCT(LCT_Coordinates, HCT_Coordinates);
				
				//Call find matching point function
				match MatchObject = new match(LCT_Coordinates,getNearestCoordinates,gpsCoordinateMatchingPoints);
				String matchingPointCoordinates = new String();
				matchingPointCoordinates = MatchObject.findMatchingPoint(LCT_Coordinates, getNearestCoordinates, gpsCoordinateMatchingPoints);	
				
				//Call find routing for LCT
				//Define Start Point and Destination for LCT
				String LCT_postion = LCT_Coordinates[0] +" "+ LCT_Coordinates[1] +" "+ LCT_Coordinates[2];
				String HCT_nearest_position = getNearestCoordinates[0] +" "+ getNearestCoordinates[1] +" "+ getNearestCoordinates[2];
				String Destination = matchingPointCoordinates;
				String[] LCT_route = new String[10];
				System.out.println("--------------------------------------");
				System.out.println("LCT routing path");
				routing RoutingObjectLCT = new routing(LCT_postion, Destination, gpsCoordinateMatchingPoints);
				LCT_route = RoutingObjectLCT.routingTrucks(LCT_postion, Destination, gpsCoordinateMatchingPoints);
				
				//Call find routing for HCT
				System.out.println("--------------------------------------");
				System.out.println("HCT routing path");
				routing RoutingObjectHCT = new routing(HCT_nearest_position, Destination, gpsCoordinateMatchingPoints);
				LCT_route = RoutingObjectHCT.routingTrucks(HCT_nearest_position, Destination, gpsCoordinateMatchingPoints);
				System.exit(1);
			}
		}
	}
	

}
