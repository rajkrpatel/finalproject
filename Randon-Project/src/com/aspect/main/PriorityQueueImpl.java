package com.aspect.main;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PriorityQueueImpl {
	
	private static List<Work> managementOverrideIdList = new LinkedList<Work>();
	private static List<Work> OthersIdList = new LinkedList<Work>();
	private static List<Long> listOfIds = new LinkedList<Long>();
	private static volatile PriorityQueueImpl single_instance = null; 
	
    private PriorityQueueImpl(){} 
  
    public static PriorityQueueImpl getInstance() 
    { 
    	synchronized(PriorityQueueImpl.class){
	        if (single_instance == null) {
	            single_instance = new PriorityQueueImpl(); 
	        }
        }  
        return single_instance; 
    } 
    
	/* Add new item to existing queue and rearranged according to priority rule
	 * if id already exists in the queue return message that id already exists.
	 */
	public String addNewWorkItem(String workId, String timeStamp){
		
		long id = 0;
		try{
			id = Long.parseLong(workId);
			if(id < 1 && id > Long.MAX_VALUE)
				return "Please pass a valid id between 1 to " + Long.MAX_VALUE;
		}
		catch (NumberFormatException e) {
			return "Please pass a valid id between 1 to " + Long.MAX_VALUE;
		}
		if(!(listOfIds.contains(id))){
			long rank = 0;
			
			// parse the input timestamp into seconds and if timestamp is not in correct format print error message.
			long timeInSeconds = parseTimeInSeconds(timeStamp);
			
			if(timeInSeconds == -1){
				return "Please enter the time in correct format which is YYYY-MM-DDThh:mm:ss (1970-01-01T00:00:00)";
			}
			boolean isManagementOverrideIDs = false;
			if(id %3 == 0 && id %5 == 0){ // Management Override IDs
				rank = timeInSeconds;
				isManagementOverrideIDs = true;
			}
			else if(id %3 == 0){ // Priority IDs
				rank =  (long) Math.max(3, timeInSeconds * Math.log10(timeInSeconds));
			}
			else if(id % 5 == 0){// VIP IDs
				rank =  (long) Math.max(4, 2 * timeInSeconds * Math.log10(timeInSeconds));
			}
			else{// Normal IDs
				rank = timeInSeconds;
			}
			
			/* adding work item information to List and sorting work item in ascending order of rank
			 * Maintaining to different list for Management Override IDS and all other IDs, As Management Override IDs should always come at top,
			 * it will be easier to sort these to list separately.			 * 
			 */
			
			if(isManagementOverrideIDs){
				managementOverrideIdList.add(new Work(id, timeStamp, timeInSeconds, rank));	
				Collections.sort(managementOverrideIdList);
			}
			else{
				OthersIdList.add(new Work(id, timeStamp, timeInSeconds, rank));
				Collections.sort(OthersIdList);
			}
			
			listOfIds.add(id);
			return "Work Item added with id : " + id;
		}
		else
			return "Id : " + id + " already presents in the queue.";
	}
	
	/*
	 * Return Work item present at the top of Priority Queue with highest ranked and removing it.
	 * if no item is there in the queue return null. 
	 */
	public Work getTopID(){
		
		if(!managementOverrideIdList.isEmpty()){
			Work work = managementOverrideIdList.get(0);
			managementOverrideIdList.remove(work);
			return work;
		}
		else if(!OthersIdList.isEmpty()){
			Work work = OthersIdList.get(0);
			OthersIdList.remove(work);
			return work;
		}
		else
			return null;
	}
	
	/*
	 * Return list of all the work item present in Priority Queue
	 */
	public List<Work> getAllId(){
		List<Work> list = new ArrayList<Work>();
		list.addAll(managementOverrideIdList);
		list.addAll(OthersIdList);
		return list;
	}
	
	/*
	 * Remove the the specific id from the Priority Queue
	 */
	public String remove(long id){
		boolean workItemDeleted = false;
		
		if(listOfIds.contains(id)){
			if(!managementOverrideIdList.isEmpty()){			
				for(Work work : managementOverrideIdList){
					if(work.getId() == id){
						managementOverrideIdList.remove(work);
						workItemDeleted = true;
						break;
					}
				}
			}
			if(!workItemDeleted && !OthersIdList.isEmpty()){			
				for(Work work : OthersIdList){
					if(work.getId() == id){
						OthersIdList.remove(work);
						workItemDeleted = true;
						break;
					}
				}
			}	
			listOfIds.remove(id);
			return "Work Item with id = " + id + " has been removed from the Queue";
		}		
		else
			return "Id : " + id + " Does not Exist in Queue";
	}
	
	public long getIndex(long id){
		if(!listOfIds.contains(id))
			return -1;
		
		long index = 0;		
		boolean idExist = false;				
		
		if(!managementOverrideIdList.isEmpty()){			
			for(Work work : managementOverrideIdList){
				if(work.getId() == id){
					idExist = true;
					break;
				}
				index++;
			}
		}
		if(!idExist && !OthersIdList.isEmpty()){			
			for(Work work : OthersIdList){
				if(work.getId() == id){
					idExist = true;
					break;
				}
				index++;
			}
		}				
		return index;
	}
	
	public long getAverageWaitTIme(String currentTimeStamp){
		long totalCount = 0;
		long totalWaitTime = 0;
		long currentTimeInSecond = parseTimeInSeconds(currentTimeStamp);
		
		if(currentTimeInSecond == -1)
			return -1;
		
		if(!managementOverrideIdList.isEmpty()){			
			for(Work work : managementOverrideIdList){
				totalWaitTime = totalWaitTime + (currentTimeInSecond - work.getTimeInSeconds());
				totalCount++;
			}
		}
		if(!OthersIdList.isEmpty()){			
			for(Work work : OthersIdList){
				totalWaitTime = totalWaitTime + (currentTimeInSecond - work.getTimeInSeconds());
				totalCount++;
			}
		}	
		
		if(totalCount == 0)
			return 0;
		
		long averageWaitTime = (long) Math.floor(totalWaitTime/totalCount);
		return averageWaitTime;		
	}
	
	public long parseTimeInSeconds(String timeStamp){
		long timeInSeconds = -1;
		try{
			LocalDateTime  localDate1 = LocalDateTime.parse(timeStamp);
			//since we are not passing GMTOFFSET so taking gmt offset "+0". Not converting time to locale time
	        ZoneOffset offset = ZoneOffset.of("+0");
	        timeInSeconds = localDate1.toEpochSecond(offset);
		}
		catch(DateTimeParseException e){
			// print error in log file
		}		
		return timeInSeconds;
	}
}
