package com.aspect.main;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "work")
public class Work implements Comparable<Work>{
	
	@XmlAttribute(name = "ID")	
	private long id;
	
	@XmlElement(name = "timestamp")
	private String timeStamp;
	
	private long timeInSeconds;
	
	// @XmlElement(name = "rank") 
	// uncomment above line if you want to see the rank of each id
	private long rank;
	
	public Work(){}
	
	public Work(long id, String timeStamp, long timeInSeconds, long rank) {
		this.id = id;
		this.timeStamp = timeStamp;
		this.timeInSeconds = timeInSeconds;
		this.rank = rank;
	}

	public long getId() {
		return id;
	}
					
	public String getTimeStamp() {
		return timeStamp;
	}

	public long getTimeInSeconds() {
		return timeInSeconds;
	}

	public long getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return "ID =" + id + ", Time Entered = " + timeStamp + "<br> <br>";
	}

	public int compareTo(Work work) {
		// TODO Auto-generated method stub
		if(this.getRank() > work.getRank())
			return 1;
		else if(this.getRank() < work.getRank())
			return -1;
		else
			return 0;
	}
}
