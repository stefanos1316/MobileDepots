import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class nearest {
	
	public static String[] LCT_position;
	public static String[][] HCT_position; 
	
	public nearest( String[] LCT_position, String[][] HCT_position ){
		
		this.LCT_position = LCT_position;
		this.HCT_position = HCT_position;
	}

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Make modification by checking first the number of lines in the file and then allocate array space (more dynamic)
		//this implementation is responsible to find the nearest High Capacity Truck (HCT) for a the Low Capacity Truck(LCT)
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\LCT_GPS_coordinates.txt"));
		String sCurrentLine;
		String[] LCT_position = new String[3];
		String[][] gpsCoordinate = new String[4][3];
		while ((sCurrentLine = br.readLine()) != null) {
			LCT_position = sCurrentLine.split("\\s+");
		}
		
		br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\HCT_GPS_coordinates.txt"));
		String[][] HCT_position = new String[3][3];
		int cursor = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			HCT_position[cursor] = sCurrentLine.split("\\s+");
			cursor++;
		}
		
		for ( int i=0; i<HCT_position.length; ++i )
		System.out.println(HCT_position[i][0]+"  "+HCT_position[i][1]);
		
		String[] nearestHCT = nearestHCT(LCT_position, HCT_position);
		
		System.out.println(nearestHCT[0]+"	"+nearestHCT[1]+"	"+nearestHCT[2]);
		
	}*/
	
	public static String[] nearestHCT( String[] LCT_position, String[][] HCT_position ) throws FileNotFoundException, UnsupportedEncodingException
	{
		String[] getClosest = new String[3];
		double minDistance = 0;
		double currentMin = 0 ;
		for ( int i=0; i<HCT_position.length; ++i )
		{
			if ( minDistance ==0 )
			{
				minDistance = ( (Math.abs(Double.parseDouble(LCT_position[0]) - Double.parseDouble(HCT_position[i][0]))) + (Math.abs(Double.parseDouble(LCT_position[1]))-Math.abs(Double.parseDouble(HCT_position[i][1]))) );
				getClosest = HCT_position[i];
			}
			else
			{
				currentMin = ( (Math.abs(Double.parseDouble(LCT_position[0]) - Double.parseDouble(HCT_position[i][0]))) + (Math.abs(Double.parseDouble(LCT_position[1]))-Math.abs(Double.parseDouble(HCT_position[i][1]))) );
				//System.out.println(minDistance+" and current "+currentMin);
				if (currentMin < minDistance )
				{
					minDistance = currentMin;
					getClosest = HCT_position[i];
					
				}
			}
		}
		
		System.out.println("The closest one has coordinates x:"+getClosest[0]+" and y:"+getClosest[1]);
		System.out.println("Min Distance is "+minDistance);
		
		//Write data in File (this file will be used by other executions)
		try {
			 
		      File file = new File("src\\NEAREST_HCT_GPS_coordinates.txt");
	 
		      if (file.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}
		
		PrintWriter writer = new PrintWriter("src\\NEAREST_HCT_GPS_coordinates.txt", "UTF-8");
		writer.println(getClosest[0]+"	"+getClosest[1]);
		writer.close();
		
		return getClosest;
	}

}
