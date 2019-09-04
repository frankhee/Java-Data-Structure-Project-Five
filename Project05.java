/*
 * @author Frank He
 * @version 20180405
 */
package osu.cse2123;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Project05 {

	public static void main(String[] args) {
		
		//Print out paths
		Scanner in = new Scanner(System.in);
		System.out.print("Enter file name with paths: ");
		String file = in.nextLine();
		Map<String, List<Path>> i = readPaths(file);
		displayAdjacencyList(i);
		System.out.println();
		
		//Prompt user to enter a city 
		System.out.print("Enter a start city (empty line to quit): ");
		String city = in.nextLine();
		while(!city.isEmpty()) {
			Map<String, Double> x =  findDistances(city,i );
			displayShortest(city, x);
			System.out.println();
			System.out.println("Enter a start city (empty line to quit): ");
			city = in.nextLine();
		}
		System.out.println("Goodbye!");
	}

	public static Map<String, List<Path>> readPaths(String fname) {

		Map<String, List<Path>> returnMap = new HashMap<>();
		try {
		// Create new file to read from 
		File inputFile = new File(fname);
		Scanner input = new Scanner(inputFile);
		while (input.hasNextLine()) {
			String line = input.nextLine();
			List<Path> start = new ArrayList<>();
			List<Path> end = new ArrayList<>();
			
			//Assign values to start and end city 
			String[] summary = line.split(",");
			String startCity = summary[0];
			String endCity = summary[1];
			double cost = Double.parseDouble(summary[2]);
			Path startPoint = new Path(endCity, cost);
			Path endPoint = new Path(startCity, cost);

			//Use if statement to add element to the map
			if (!returnMap.containsKey(startCity)) {
				start.add(startPoint);
				returnMap.put(startCity, start);
			} else {
				List<Path> extra = returnMap.get(startCity);
				extra.add(startPoint);
				returnMap.put(startCity, extra);
			}
			if (!returnMap.containsKey(endCity)) {
				end.add(endPoint);
				returnMap.put(endCity, end);
			} else {
				List<Path> extra = returnMap.get(endCity);
				extra.add(endPoint);
				returnMap.put(endCity, extra);
			}
		}
		return returnMap;
		}
		
		//Catch the exception and print out error message
		catch(Exception e) {
			System.out.println("ERROR");
			System.exit(0);
			return null;
		}
	}
	

	public static void displayAdjacencyList(Map<String, List<Path>> map) {
		
		//Create set for key set 
		Set<String> keySet = map.keySet();
		Iterator<String> keys = keySet.iterator();
		
		//Use while loop to print out summary
		while (keys.hasNext()) {
			String location = keys.next();
			List<Path> outPut = map.get(location);
			String x = "(" + outPut.get(0).getEndpoint() + ": " + outPut.get(0).getCost() + ")";
			for (int a = 1; a < outPut.size(); a++) {
				Path i = outPut.get(a);
				String endPoint = i.getEndpoint();
				double cost = i.getCost();
				x = x + ", (" + endPoint + ": " + cost + ")";
			}
			System.out.println(location + ": " + x);
		}
	}

	public static Map<String, Double> findDistances(String start, Map<String,List<Path>> map){
		
		Map<String, Double> returnMap = new HashMap<>();
		
		//Use while loop to add element to the map
		PriorityQueue <Path> paths = new PriorityQueue<>();
		paths.add(new Path(start,0.0));
		while(!paths.isEmpty()) {
			Path current = paths.remove();
			String dest = current.getEndpoint();
			if(!returnMap.containsKey(dest)) {
				double cost = current.getCost();
				returnMap.put(dest, cost);
				List<Path> currentPath = map.get(dest);
				for(int i = 0;i<currentPath.size();i++) {
					Path newly = new Path(currentPath.get(i).getEndpoint(),(cost+currentPath.get(i).getCost()));
					paths.add(newly);
				}	
			}
		}
		return returnMap;
		
	}

	public static void displayShortest(String start, Map<String, Double> map) {
		
		//Print out summary
		System.out.println("Distance from " +start+ " to each city: ");
		System.out.println("Dest. City     Distance ");
		System.out.println("-------------- --------");
		
		Set<String> keySet = map.keySet();
		Iterator <String> keys = keySet.iterator();
		
		//Use while loop to keep the program going until the user enters an empty line
		while(keys.hasNext()) {
			String key = keys.next();
			System.out.printf("%-15s%8.2f%n", key,map.get(key));
		}
	}


}







