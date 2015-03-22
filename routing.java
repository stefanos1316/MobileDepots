import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class routing {
	
	public static String startPoint, endPoint; 
	public static String[] gpsCoordinate;
	
	public routing(String startPoint, String endPoint, String[] gpsCoordinate){
		
		routing.startPoint = startPoint;
		routing.endPoint = endPoint;
		routing.gpsCoordinate = gpsCoordinate;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Make modification by checking first the number of lines in the file and then allocate array space (more dynamic)
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\GPS_coordinates_routes.txt")))
		{ 
			String sCurrentLine;
			int cursor=0;
			String[] gpsCoordinate = new String[10];
			while ((sCurrentLine = br.readLine()) != null) {
				gpsCoordinate[cursor] = sCurrentLine;
				cursor++;
			}		
			//define array size
			System.out.println("Start point is 59.571023325559 30.799322932779 and the end point is 59.660079356461 30.273135800359");
			System.out.println("--------------------------------------");

			for( int i=0; i<gpsCoordinate.length; i++)
				System.out.println(gpsCoordinate[i]);
			System.out.println("--------------------------------------");
			//Call routing function
			routingTrucks("59.571023325559 30.799322932779", "59.660079356461 30.273135800359", gpsCoordinate);
 
		} catch (IOException e) {
			e.printStackTrace();
		} 						
	}
	
	//function which will implement the routing algorithm
	//will check which is the closest coordinate to the start point and keep searching the next min unitl
	//to reaching the destination
	public static void routingTrucks(String startPoint, String endPoint, String[] gpsCoordinate){
		
			ArrayList<String> list = new ArrayList<String>();
			
			//When you receive the start and end point put the at the begin of array (startPoint) and at the end of it (endPoint)
			String[] new_gpsCoordinates = new String[gpsCoordinate.length+2];
			for( int i=0; i<gpsCoordinate.length; ++i )
			{
				new_gpsCoordinates[i+1] = gpsCoordinate[i];
			}
			
			new_gpsCoordinates[0] = startPoint;
			new_gpsCoordinates[new_gpsCoordinates.length-1] = endPoint;
			
			/*for ( int i=0; i<new_gpsCoordinates.length; ++i )
				System.out.println(new_gpsCoordinates[i]);*/
			//System.exit(1);
			//Add the first value inside
			list.add(new_gpsCoordinates[0]);
			double currentMin = 0;
			double minimun = 0;
			String nextMin = new String();
			String finalMin = new String();
			for ( int i=0; i<new_gpsCoordinates.length-1; ++i )
			{
				for ( int j=1; j<new_gpsCoordinates.length-1; ++j )
				{					
					
					if ( !list.contains(new_gpsCoordinates[j]))
					{
						
						String[] currentPosition = new_gpsCoordinates[i].split("\\s+");
						String[] nextPosition = new_gpsCoordinates[j].split("\\s+");
						nextMin = new_gpsCoordinates[j];
						
						if (minimun == 0)
						{
							currentMin = ( Math.abs(Double.parseDouble(currentPosition[0]) - (Double.parseDouble(nextPosition[0]))) + Math.abs(Double.parseDouble(currentPosition[1]) - (Double.parseDouble(nextPosition[1]))) );
							minimun = currentMin;
							finalMin=nextMin;
							//System.out.println("Minimum is "+minimun);
						}
						else
						{
							currentMin = ( Math.abs(Double.parseDouble(currentPosition[0]) - (Double.parseDouble(nextPosition[0]))) + Math.abs(Double.parseDouble(currentPosition[1]) - (Double.parseDouble(nextPosition[1]))) );
							if ( currentMin < minimun )
							{
								minimun = currentMin;
								finalMin=nextMin;
							}
						}
					}
				}
				
				//System.out.println("Final minimum if this number is "+finalMin);
				if ( !list.contains(finalMin) )
				list.add(finalMin);
			}
			
			//Add the final value			
			list.add(list.size()-1, endPoint);
			list.remove(list.size()-1);			
			String[] route = new String[list.size()];
			
			for (int i=0; i<list.size(); ++i)
				route[i] = list.get(i);
			
			for (int i=0; i<route.length; ++i)
				System.out.println(i+1+" "+route[i]);	
			
			System.out.println("--------------------------------------");
			System.out.println("End is '"+endPoint);
			
	}

}
