package equipment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;




public class solution {

	public static void main(String[] args) {
		 
		String outputString  = "";
		//meters p second,speed of the plane
        double mps = 800000/3600;
 
		double totalTime = 0;
		//reading and adding all city in arrayList
		ArrayList<City> arrayList = readfileMakeArray();
		
		// starting traveling from city from first line file 
		City currentCity  =  arrayList.get(0); 
		//This was used to get from last city back to original
		City startCity  =  arrayList.get(0);
		City lastCity  =  arrayList.get(162);
		//starting point
		outputString = outputString+0;
		
		// removing cities from list 
		//arrayList.remove(currentCity); I commented this out as I needed visit some cities twice.
		
		
		while(arrayList.size() > 0){
			
			// Getting smallest distance city from current city 
			double distance = Double.MAX_VALUE;
			
			City forholdingcityObject = null;
			
			//code for all remaining city 
			for (int i = 0; i < arrayList.size(); i++) {
				
				//calculating distance for between two city 
				double distancebetweencity = distance(Double.parseDouble(currentCity.getXaxis()),Double.parseDouble(arrayList.get(i).getXaxis()), Double.parseDouble(currentCity.getYaxis()), Double.parseDouble(arrayList.get(i).getYaxis()));

				//100km restriction and adding distance
				if(distancebetweencity >= 174.7 && distancebetweencity <= distance){
					forholdingcityObject = arrayList.get(i);
					distance = distancebetweencity;
				}
				
			}

			
			if( forholdingcityObject != null ){
		/* I used this to show exactly where I was going to an from and the distance
		 * System.out.println("moving from "+currentCity.getName() + " to "+forholdingcityObject.getName() + " as distance between them is "+ distance);
		 */   
		         currentCity = forholdingcityObject;
		         arrayList.remove(forholdingcityObject);
		         totalTime =  totalTime+distance/mps/3.6+0.5; 
		         outputString = outputString + ","+ forholdingcityObject.getName() ;
	             /*
	              *I used these to find the cities I removed and remainders 
	              * System.out.println("removed city"+ forholdingcityObject.getName());
		            System.out.println("remaining city"+arrayList.size());
		         */
			} else {
				//If I had an output but missed some cities 
				System.out.println("city remaining is  "+ arrayList.size());
				break ;
			}
			
			
		}
		//This was a seperate distance formula to get back to original city
		double dd = distance(Double.parseDouble(lastCity.getXaxis()),Double.parseDouble(startCity.getXaxis()), Double.parseDouble(lastCity.getYaxis()), Double.parseDouble(startCity.getYaxis()));
		//To get time of last city back to original
		double totalTime2= 0;
		//Added zero as distance and time were calculated
		outputString = outputString+ ","+ 0;
		//Calculating time of last city to origin
		totalTime2 =  totalTime2 +dd/mps/3.6+0.5; 
		
		//converted to integer, just incase it was to be rounded off
		double timeInt = totalTime+totalTime2;
		double timeTotal = totalTime+totalTime2;

		//round off
		System.out.println((int)timeInt+"  rounded off as int");
		//output String
		System.out.println(outputString);
		//pure time
		System.out.println("Total Time: "+ timeTotal + " hours,");
	}

	
	//reading data from file 
	private static ArrayList<City> readfileMakeArray() {
		try {
			//creating array object 
			ArrayList<City> arrayList = new ArrayList<City>();
			File file=new File("EquipmentGPSCo-ordinates.csv");    //created new file instance  
			
			FileReader fr=new FileReader(file); // reading file 
			
			BufferedReader br=new BufferedReader(fr); //creating buffer character for input  
	
			String line;
			int i = 0;
			
			//filling objects 
			while((line=br.readLine())!=null)  
			{  
				City city= new City();
				String[] temp = line.split(",");
				city.setXaxis(temp[0]);
				city.setYaxis(temp[1]);
				city.setName(i+"");
				arrayList.add(city);
				i++;
			}  
			fr.close();
			
			//return to main
			return arrayList;
		} catch (Exception e) {
			return null;
		}
	}


	public static double distance(double lat1,double lat2, double lon1 , double lon2) 
	{ 

		// convert to radians
		lon1 = Math.toRadians(lon1); 
		lon2 = Math.toRadians(lon2); 
		lat1 = Math.toRadians(lat1); 
		lat2 = Math.toRadians(lat2); 

		//  The Haversine formula  
		double dlon = lon2 - lon1;  
		double dlat = lat2 - lat1; 
		double a = Math.pow(Math.sin(dlat / 2), 2) 
				+ Math.cos(lat1) * Math.cos(lat2) 
				* Math.pow(Math.sin(dlon / 2),2); 

		double c = 2 * Math.asin(Math.sqrt(a)); 

		// Radius of earth in kilometers
		double r = 6371; 

		//result 
		return(c * r); 
	}  
}

//city class
class City{
	String name = "";
	String xaxis, yaxis;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXaxis() {
		return xaxis;
	}
	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}
	public String getYaxis() {
		return yaxis;
	}
	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;  
	}
} 


