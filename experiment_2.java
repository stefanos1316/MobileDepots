import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class experiment_2 {

	public static  File file_Exp_2 = new File("src\\Experiment_2_Results.txt");
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 if (file_Exp_2.exists()) file_Exp_2.delete();
		
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stefanos\\workspace_new\\MobileDepots\\src\\Experiment_1_Results.txt"));
		String sCurrentLine;
		ArrayList<String> list = new ArrayList<String>();
		int AverageTimeTriggered = 0;
		
		while ((sCurrentLine = br.readLine()) != null) 
			list.add(sCurrentLine);
		
		for ( int i=0; i<list.size()-1; ++i)
		{
			String[] separator = list.get(i).split(":");
			String[] separator_next = list.get(i+1).split(":");
			
			//if the min are the same then
			if ( separator[1].equals(separator_next[1])  )
				AverageTimeTriggered = AverageTimeTriggered + ( Integer.parseInt(separator_next[2]) - Integer.parseInt(separator[2]) );
			else
				AverageTimeTriggered = AverageTimeTriggered + ( (Integer.parseInt(separator_next[2]) + 60) - Integer.parseInt(separator[2]) );
			
		}
		
		PrintWriter writer;
		
		try {
		      if (file_Exp_2.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		        System.out.println("File already exists.");
		      }
			} catch (IOException e) {
		      e.printStackTrace();
			}	
		    	writer = new PrintWriter(new FileOutputStream(new File("src\\Experiment_2_Results.txt"),true));	
		    	writer.println(AverageTimeTriggered/list.size()+" seconds");				
		    	writer.close();
	
		
		System.out.println(AverageTimeTriggered/list.size()+" seconds");
		
	}
}
