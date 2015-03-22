import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;



//This Class is responsible to create Random coordinates by defining X and Y and number of lines
// 3 parameters are required
/////////////////////////////// INSTRUCTIONS ///////////////////////////////////
// The user can give to this porgram 6 parameters at Run -> Run Condifurations ->  (From the Panel select createRandomCoordinates) -> Arguments
//Arg 0 -> Lowest value of X
//Arg 1 -> Highest value of X
//Arg 2 -> Lowest value of Y
//Arg 3 -> Highest value of Y
//Arg 4 -> Number of wished lines in a file
//Arg 5 -> Name of the File

public class createRandomCoordinates {

	private static PrintWriter writer;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub

			try {
			 
		      File file = new File("src\\"+args[5]);
	 
		      if (file.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}
			
			writer = new PrintWriter("src\\"+args[5], "UTF-8");
		
			Random r = new Random();
			double LowX = Double.parseDouble(args[0]);
			double HighX = Double.parseDouble(args[1]);
			double LowY =  Double.parseDouble(args[2]);
			double HighY =  Double.parseDouble(args[3]);
			
			for ( int i=0; i<Integer.parseInt(args[4]); ++i)
			{
			double R_X = LowX + ( HighX - LowX )*r.nextDouble();
			double R_Y = LowY + ( HighY - LowY )*r.nextDouble();
			
			if ( args.length == 7 )
			{
			System.out.println(String.format("%.12f", R_X)+" "+String.format("%.12f", R_Y)+" 0");
			writer.println(String.format("%.12f", R_X)+" "+String.format("%.12f", R_Y)+" 0");
			}
			else
			{
			System.out.println(String.format("%.12f", R_X)+" "+String.format("%.12f", R_Y));
			writer.println(String.format("%.12f", R_X)+" "+String.format("%.12f", R_Y));
			}
				
			}
			
			writer.close();
		
	}

}
