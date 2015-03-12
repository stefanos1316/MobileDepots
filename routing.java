import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class routing {
	
	public static String startPoint, endPoint; 
	public static String[] gpsCoordinate;
	
	public routing(String startPoint, String endPoint, String[] gpsCoordinate){
		
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.gpsCoordinate = gpsCoordinate;
	}

	/*public static void main(String[] args) {
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
			System.out.println("--------------------------------------");

			for( int i=0; i<gpsCoordinate.length; i++)
				System.out.println(gpsCoordinate[i]);
			System.out.println("--------------------------------------");
			//Call routing function
			routing(gpsCoordinate[0], gpsCoordinate[gpsCoordinate.length-1], gpsCoordinate);
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
						
	}*/	
	
	//function which will implement the routing algorithm
	//will check which is the closest coordinate to the start point and keep searching the next min unitl
	//to reaching the destination
	public static String[] routingTrucks(String startPoint, String endPoint, String[] gpsCoordinate){
		
			ArrayList<String> list = new ArrayList<String>();
			//Add the first value inside
			list.add(gpsCoordinate[0]);
			String currentPos = gpsCoordinate[0];
			double currentMin = 0;
			double minimun = 0;
			String nextMin = new String();
			String finalMin = new String();
			String[] minimumCoordinates =new String[2];
			
			for ( int i=0; i<gpsCoordinate.length; ++i )
			{
				for ( int j=1; j<gpsCoordinate.length; ++j )
				{
					
					if ( !list.contains(gpsCoordinate[j]) ) //then ignore it go to the next one
					{						
						String[] currentPosition = currentPos.split("\\s+");
						String[] nextPosition = gpsCoordinate[j].split("\\s+");
						nextMin = gpsCoordinate[j];
						//If its the fist value
						if ( currentMin == 0 )
						{
							minimun = ( Math.abs(Double.parseDouble(currentPosition[0]) - (Double.parseDouble(nextPosition[0]))) + Math.abs(Double.parseDouble(currentPosition[1]) - (Double.parseDouble(nextPosition[1]))) );
						    minimumCoordinates = nextPosition;
						    finalMin = nextMin;
						}
						else
						{
							//Else compare values
							currentMin = ( Math.abs(Double.parseDouble(currentPosition[0]) - (Double.parseDouble(nextPosition[0]))) + Math.abs(Double.parseDouble(currentPosition[1]) - (Double.parseDouble(nextPosition[1]))) );
							if ( currentMin > minimun )
							{
								//Get new values
								minimun = currentMin;
								minimumCoordinates = nextPosition;
								finalMin=nextMin;								
							}							
						}
					}						
				}
				
				list.add(finalMin);
			}
			
			String[] route = new String[list.size()-1];
			
			for (int i=0; i<list.size()-1; ++i)
				route[i] = list.get(i);

			
			for (int i=0; i<route.length; ++i)
			{
				//route[i] = list.get(i);
				System.out.println(route[i]);
			}		
			return route;
	}

}
