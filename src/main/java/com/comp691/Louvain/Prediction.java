package com.comp691.Louvain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.comp691.Utils.Config;


public class Prediction {
	public static Map<Integer, Integer> userCommMap = new HashMap<Integer, Integer>();
	public static Map<Integer, ArrayList<Integer>> communitiesUserListMap = new HashMap<Integer, ArrayList<Integer>>();
	public static Map<Integer, ArrayList<Integer>> userItemListMap = new HashMap<Integer, ArrayList<Integer>>();
	public static Map<Integer, HashMap<Integer, Integer>> communitiesItemSetMap = new HashMap<Integer, HashMap<Integer, Integer>>();
	public static Map<Integer, LinkedHashMap<Integer, Integer>> communitiesItemSetMapNew = new HashMap<Integer, LinkedHashMap<Integer, Integer>>();
	
	public static Map<Integer, HashSet<Integer>> userPredictedItemSetMap = new HashMap<Integer, HashSet<Integer>>();
	
	public static void getPrediction() {
		initializeCommunitiesUserListMap();
		initializeUserItemListMap();
		generateCommunitiesItemSetMap();
		
		generateCommunitiesItemSetMapNew();
		
		generatedUserPredictedItemSetMap();
		//System.out.println(userPredictedItemSetMap);
	}
	
	private static void initializeCommunitiesUserListMap() {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			String inputFileName = Config.LOUVAIN_OUTPUT;
			br = new BufferedReader(new FileReader(inputFileName));
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(" ");
				int user = Integer.parseInt(parts[0]);
				int comm = Integer.parseInt(parts[1]);
				
				userCommMap.put(user, comm);
				
				if(!communitiesUserListMap.containsKey(comm)) {
					ArrayList<Integer> userList = new ArrayList<>();
					userList.add(user);
					communitiesUserListMap.put(comm, userList);
				} 
				else
				{
					ArrayList<Integer> userList = communitiesUserListMap.get(comm);
					userList.add(user);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initializeUserItemListMap() {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			String inputFileName = Config.DATASET;
			br = new BufferedReader(new FileReader(inputFileName));
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(",");
				int user = Integer.parseInt(parts[0]);
				int item = Integer.parseInt(parts[1]);
				
				if(!userItemListMap.containsKey(user)) {
					ArrayList<Integer> itemList = new ArrayList<>();
					itemList.add(item);
					userItemListMap.put(user, itemList);
				} 
				else
				{
					ArrayList<Integer> itemList = userItemListMap.get(user);
					itemList.add(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateCommunitiesItemSetMap() {
		for(Entry<Integer, ArrayList<Integer>> commUserListEntry : communitiesUserListMap.entrySet()) 
		{
			int comm = commUserListEntry.getKey();
			for(int user: commUserListEntry.getValue()) 
			{
				if(!communitiesItemSetMap.containsKey(comm)) {
					HashMap<Integer, Integer> itemMap = new HashMap<>();
					for(int item : userItemListMap.get(user)) 
					{
						if(!itemMap.containsKey(item)) {
							itemMap.put(item, 1);
						} 
						else
						{
							itemMap.put(item, itemMap.get(item) + 1);
						}
					}
					
					communitiesItemSetMap.put(comm, itemMap);
				} 
				else
				{
					HashMap<Integer, Integer> itemMap = communitiesItemSetMap.get(comm);
					for(int item : userItemListMap.get(user)) 
					{
						if(!itemMap.containsKey(item)) {
							itemMap.put(item, 1);
						} 
						else
						{
							itemMap.put(item, itemMap.get(item) + 1);
						}
					}
				}
			}
		}
	}
	
	private static void generateCommunitiesItemSetMapNew() {
		Comparator<Entry<Integer, Integer>> comparator = new Comparator<Map.Entry<Integer,Integer>>() {
			
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue().compareTo(o1.getValue());
			}
		};
		for(int comm : communitiesItemSetMap.keySet()) 
		{
			List<Entry<Integer, Integer>> itemStatList = new ArrayList<Entry<Integer,Integer>>(communitiesItemSetMap.get(comm).entrySet());
			Collections.sort(itemStatList, comparator);
			
			LinkedHashMap<Integer, Integer> itemStatMap = new LinkedHashMap<Integer, Integer>();
			for (Entry<Integer, Integer> entry : itemStatList) {
				itemStatMap.put(entry.getKey(), entry.getValue());
			}
			
			communitiesItemSetMapNew.put(comm, itemStatMap);
		}
		communitiesItemSetMap = null;
	}
	
	public static void generatedUserPredictedItemSetMap() {
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(Config.LOUVAIN_PREDICTION));
			for(Entry<Integer, ArrayList<Integer>> userItemListEntry : userItemListMap.entrySet()) 
			{
				int user = userItemListEntry.getKey();
				int comm = userCommMap.get(user);
				HashSet<Integer> predictedItemSet = new HashSet<Integer>(communitiesItemSetMapNew.get(comm).keySet());
				predictedItemSet.removeAll(userItemListEntry.getValue());
				
				bw.write(user + " {");
				int i = 0;
				for (Integer integer : predictedItemSet) {
					bw.write(integer + ",");
					if(++i > Config.NO_TOP_PREDICTIONS)
						break;
				}				
				bw.write("}" + System.lineSeparator());
				
				//userPredictedItemSetMap.put(user, predictedItemSet);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try
			{
				bw.flush();
				bw.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
