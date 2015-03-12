import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class match {
	
	public static String[] LCT_position, HCT_position, mathcingPoints;
	
	public match(String[] LCT_position, String[] HCT_position, String[] mathcingPoints){
		
		this.LCT_position = LCT_position;
		this.HCT_position = HCT_position;
		this.mathcingPoints = mathcingPoints;
	}

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Make modification by checking first the number of lines in the file and then allocate array space (more dynamic)
		
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_matching_points.txt"));
			String sCurrentLine;
			int cursor=0;
			String[] gpsCoordinateMatchingPoints = new String[10];
			while ((sCurrentLine = br.readLine()) != null) {
				gpsCoordinateMatchingPoints[cursor] = sCurrentLine;
				cursor++;
			}
			
			System.out.println("--------------------------------------");
			System.out.println("MATCHING POINT");
			for( int i=0; i<gpsCoordinateMatchingPoints.length; i++)
				System.out.println(gpsCoordinateMatchingPoints[i]);
			System.out.println("--------------------------------------");			
 
			br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\LCT_GPS_coordinates.txt"));
			String[] gpsCoordinatesLCT = new String[2];
			while ((sCurrentLine = br.readLine()) != null) {
				gpsCoordinatesLCT = sCurrentLine.split("\\s+");
			}
			
			System.out.println("LOW CAPACITY TRUCK COORD");
			System.out.println(gpsCoordinatesLCT[0]+"	"+gpsCoordinatesLCT[1]);
			System.out.println("--------------------------------------");
		
			br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\NEAREST_HCT_GPS_coordinates.txt"));
			String[] gpsCoordinatesHCT = new String[2];
			while ((sCurrentLine = br.readLine()) != null) {
				gpsCoordinatesHCT = sCurrentLine.split("\\s+");
			}
			
			System.out.println("HIGH CAPACITY TRUCK COORD");
			System.out.println(gpsCoordinatesHCT[0]+"	"+gpsCoordinatesHCT[1]);
			System.out.println("--------------------------------------");
			
			//Call find Matching Point Functino
			findMatchingPoint(gpsCoordinatesLCT, gpsCoordinatesHCT, gpsCoordinateMatchingPoints);
	}*/
	
	public static String findMatchingPoint(String[] LCT_position, String[] HCT_position, String[] mathcingPoints){
		
		
		double minDistance = 0;
		double minX=0, minY=0;
		double lowCapacityTruck_to_MatchingPoint = 0, highCapacityTruck_to_MatchingPoint = 0, totalDistanceBetweenTrucks = 0;
		String minCoordinates = new String();
		System.out.println("--------------------------------------");
		for ( int i=0; i<10; ++i )
		{
			
			//if the test is first time get the first values from matchingPoints
			if ( minDistance ==0 )
			{
				String[] mathcingPoint = mathcingPoints[0].split("\\s+");
				lowCapacityTruck_to_MatchingPoint = (  (Math.abs( Double.parseDouble(LCT_position[0]) - Double.parseDouble(mathcingPoint[0]))) + (Math.abs( Double.parseDouble(LCT_position[1]) - Double.parseDouble(mathcingPoint[1]))) );
				highCapacityTruck_to_MatchingPoint = (  (Math.abs( Double.parseDouble(HCT_position[0]) - Double.parseDouble(mathcingPoint[0]))) + (Math.abs( Double.parseDouble(HCT_position[1]) - Double.parseDouble(mathcingPoint[1]))) );
				totalDistanceBetweenTrucks = lowCapacityTruck_to_MatchingPoint + highCapacityTruck_to_MatchingPoint ;
				minDistance = totalDistanceBetweenTrucks;
				minCoordinates = mathcingPoints[0];
			}
			else
			{
				String[] mathcingPoint = mathcingPoints[i].split("\\s+");
				lowCapacityTruck_to_MatchingPoint = (  (Math.abs( Double.parseDouble(LCT_position[0]) - Double.parseDouble(mathcingPoint[0]))) + (Math.abs( Double.parseDouble(LCT_position[1]) - Double.parseDouble(mathcingPoint[1]))) );
				highCapacityTruck_to_MatchingPoint = (  (Math.abs( Double.parseDouble(HCT_position[0]) - Double.parseDouble(mathcingPoint[0]))) + (Math.abs( Double.parseDouble(HCT_position[1]) - Double.parseDouble(mathcingPoint[1]))) );
				totalDistanceBetweenTrucks = lowCapacityTruck_to_MatchingPoint + highCapacityTruck_to_MatchingPoint ;
				System.out.println("Current smallest matching point is "+mathcingPoints[i]+" with dinstance "+minCoordinates);
				//Check which one is the smallest distance
				if ( totalDistanceBetweenTrucks < minDistance )
				{
					minDistance = totalDistanceBetweenTrucks;
					minCoordinates = mathcingPoints[i];
				}
			}
		}
		System.out.println("--------------------------------------");
		System.out.println("Final Distance for Matching Point is  "+minCoordinates);
		
		return minCoordinates;
	}

}
