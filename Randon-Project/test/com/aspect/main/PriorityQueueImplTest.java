package com.aspect.main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriorityQueueImplTest {

	PriorityQueueImpl queue = null;
	
	@Before 
    public void initializeTestData() {
		queue = PriorityQueueImpl.getInstance();
		queue.addNewWorkItem("45", "2019-01-31T08:56:10");
		queue.addNewWorkItem("49", "2019-01-31T08:56:12");
		queue.addNewWorkItem("1", "2019-01-31T08:56:21");
		queue.addNewWorkItem("15", "2019-01-31T08:57:10");
		queue.addNewWorkItem("121", "2019-01-31T08:59:46");
    }
	
	@Test
	public void test1AddNewWorkItem() {
		String actualMessage = queue.addNewWorkItem("45", "2019-01-31T08:56:10");
		String expectedMessage = "Id : 45 already presents in the queue.";
		assertEquals(expectedMessage, actualMessage);
		
		String actualMessage1 = queue.addNewWorkItem("46", "2019-01-31T09:15:19");
		String expectedMessage1 = "Work Item added with id : 46";
		assertEquals(expectedMessage1, actualMessage1);		
	}
	
	@Test
	public void test2GetAllId() {
		List<Work> list = queue.getAllId();
		ArrayList<Long> actualArrayList = new ArrayList<Long>();
		ArrayList<Long> expectedArrayList = new ArrayList<Long>();
		
		for(Work w : list){
			actualArrayList.add(w.getId());
		}		
		expectedArrayList.add((long) 45);
		expectedArrayList.add((long) 15);
		expectedArrayList.add((long) 49);
		expectedArrayList.add((long) 1);
		expectedArrayList.add((long) 121);
		expectedArrayList.add((long) 46);
		
		assertArrayEquals(expectedArrayList.toArray(), actualArrayList.toArray());
	}
	
		
	@Test
	public void test3GetIndex() {
		long actualIndex = queue.getIndex(15);
		long expectedIndex = 1;
		assertEquals(expectedIndex, actualIndex);		
	}

	@Test
	public void test4GetAverageWaitTIme() {
		long actualAverageWaitTime = queue.getAverageWaitTIme("2019-01-31T09:10:16");
		long expectedAverageWaitTime = 606;		
		assertEquals(expectedAverageWaitTime, actualAverageWaitTime);
	}

	@Test
	public void test5Remove() {		
		String actualMessage = queue.remove(47);
		String expectedMessage = "Id : 47 Does not Exist in Queue";
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void test6GetTopID() {
		Work work = queue.getTopID();
		assertEquals(45, work.getId());		
	}
	
	@Test
	@Ignore
	public void testParseTimeInSeconds() {
		
	}

}
