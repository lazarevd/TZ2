package ru.laz.tz.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
	
	
	private static class DateComparator implements Comparator<String> {

		@Override
		public int compare(String a, String b) {
			LocalDateTime aT = LocalDateTime.parse(a);
			LocalDateTime bT = LocalDateTime.parse(b);
			
			
			return 0;
		}
		
	}
	
	
    private static List<String[]> readDataFromFile(String inputFileName) {

        List<String[]> retList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))){
            String line = br.readLine();
            while (line != null) {
                retList.add(line.split("\t"));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File "+inputFileName+" not found");
        } catch (IOException e) {
            System.out.println("File "+inputFileName+" reading error");
        }
        return retList;
    }
    
    
    
    public static Map<String, Ruble> groupByDate(List<String[]> inputData, int dateIdx, int valueIdx) {
    	
    	Map<String, Ruble> dateMap = new TreeMap<String, Ruble>();
    	
    	for (String[] input : inputData) {
    		String date = input[dateIdx];
    		Ruble value = Ruble.toRuble(input[valueIdx]);
    		
    		if (dateMap.containsKey(date)) {
    			Ruble rub = dateMap.get(date);
    			rub = rub.addRuble(value);
    			dateMap.put(date, rub);
    		} else {
    			dateMap.put(date, value);
    		}
    	}
    	
    	return dateMap;
    }
    

	public static void main(String[] args) {
		
		List<String[]> readData = readDataFromFile(System.getProperty("user.dir") +"/etc/operations.txt");
		
		
		Map<String, Ruble> map = groupByDate(readData, 0, 3);
		

	}

}
