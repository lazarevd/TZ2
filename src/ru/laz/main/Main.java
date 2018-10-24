package ru.laz.main;

import ru.laz.tz.util.Process;
import ru.laz.tz.util.Ruble;

import java.io.*;
import java.util.*;

public class Main {

	
	
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
    
    
    
    private static Map<String, Ruble> groupByString(List<String[]> inputData, int keyIdx, int valueIdx, Process proc) {
    	
    	Map<String, Ruble> map = new TreeMap<>();
    	
    	for (String[] input : inputData) {
    	    try {


                String key = proc.process(input[keyIdx]);
                Ruble value = Ruble.toRuble(input[valueIdx]);

                if (map.containsKey(key)) {
                    Ruble rub = map.get(key);
                    rub = rub.addRuble(value);
                    map.put(key, rub);
                } else {
                    map.put(key, value);
                }
            } catch (IllegalArgumentException e) {
    	        System.out.println("WARN: "+ input + " has wrong ruble string");
            }
    	}
    	
    	return map;
    }


    public static List<Map.Entry<String, Ruble>> getSortedList(Map<String, Ruble> map) {
        ArrayList<Map.Entry<String, Ruble>> al = new ArrayList(map.entrySet());

	    al.sort((r1,r2) -> {
                return r1.getValue().compareTo(r2.getValue());
            }
        );

	    return al;
    }






	public static void main(String[] args) {

        String operationsFile = args[0];
        String sumsByDaysFile = args[1];
        String sumsByOfficesFile = args[2];

		List<String[]> readData = readDataFromFile(operationsFile);

        Process proc = (inputStr) -> inputStr.substring(0,10);
		Map<String, Ruble> dateMap = groupByString(readData, 0, 3, proc);

        try (PrintStream ps = new PrintStream(new FileOutputStream(sumsByDaysFile))) {

            for (Map.Entry entry : dateMap.entrySet()) {
                ps.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can not write file " + operationsFile);
        }

        Process proc2 = (inputStr) -> inputStr;
        Map<String, Ruble> officeMap = groupByString(readData, 1, 3, proc2);

        List<Map.Entry<String,Ruble>> sorted = getSortedList(officeMap);

        try (PrintStream ps = new PrintStream(new FileOutputStream(sumsByOfficesFile))) {

            for (Map.Entry entry : sorted) {
                ps.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can not write file " + operationsFile);
        }
	}

}
