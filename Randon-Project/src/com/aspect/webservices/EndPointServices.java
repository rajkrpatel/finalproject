package com.aspect.webservices;
 
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.aspect.main.PriorityQueueImpl;
import com.aspect.main.Work;
 
@Path("/works")
public class EndPointServices {
	
	PriorityQueueImpl priorityQueue = PriorityQueueImpl.getInstance();
	
	@GET
	@Path("/gettop")
	@Produces(MediaType.APPLICATION_XML)
	public Work getTopItem() { 
		Work work = priorityQueue.getTopID();		
		return work; 
	}
	
	@GET
	@Path("/newwork")
	@Produces(MediaType.TEXT_PLAIN)
	public String insert(@QueryParam("id") String id,
            @QueryParam("time") String timeStamp) {
		String message = priorityQueue.addNewWorkItem(id, timeStamp);
		return message; 
	} 
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_XML)
	public List<Work> getAll() {
		List<Work> list = priorityQueue.getAllId();
		return list; 
	} 
	
	@GET
	@Path("/remove")
	@Produces(MediaType.TEXT_PLAIN)
	public String remove(@QueryParam("id") long id) {
		String message = priorityQueue.remove(id);		
		return message; 
	} 
	
	@GET
	@Path("/getindex")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIndex(@QueryParam("id") long id) {
		long index = priorityQueue.getIndex(id);		
		String message = "";
		if(index == -1){
			message = "Id : " + id + " does not exists in queue" ;
		}
		else
			message = "Id : " + id + " is present at index : " + index;
		
		return   message;
	} 
	
	@GET
	@Path("/getAverageWaitTime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAverageWaitTIme(@QueryParam("time") String currentTimeStamp) {
		long averageWaitTime = priorityQueue.getAverageWaitTIme(currentTimeStamp);	
		
		if(averageWaitTime == -1){
			return "Please enter the Current time stamp in correct format which is YYYY-MM-DDThh:mm:ss (1970-01-01T00:00:00)";
		}
		else
			return "Average wait time for each ids : " + averageWaitTime + " seconds";		
	} 
	
}