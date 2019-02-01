#Priority queue web service

This project is developed  with Jersey & Maven in Eclipse.
Name of the project given by me is Randon-Project.

Tools/plugin used
_____________________________
Eclipse neon

JDK 1.8

Jersey 1.9

JUnit 4.12

Maven Plugin

Tomcat 8.5 Server


Steps to import the project into your workspace.
1) Download the zip from https://github.com/rajkrpatel/finalproject and extract it.
2) Go to eclipse --> file  --> import --> existing maven project --> select Randon-Project folder from the extracted location
3)

Steps to run the Priority queue web service in your environment, it can be done in 2 ways
1) Placing war file in the tomcat, below are the steps
    a) Place Randon-Project.war file in tomcat webapp folder and restart tomcat 
    b) Try accessing the Endpoints url from the browser

2) Runnig your project in eclipse using run on server option, below are the steps.
    a) Right click on Randon-Project folder in eclipse --> run as --> run server
    b) Eclipse browser will open, there we have to enter the endpoint urls.
    
Endpoints url
________________________________________________________________________
#TIME FORMAT IS YYYY-MM-DDThh:mm:ss (1970-01-01T00:00:00)

(1) An endpoint for adding a ID to queue (enqueue). This endpoint should accept two parameters, the ID to enqueue and the time at which the ID
    was added to the queue.
    
    example : http://localhost:8080/Randon-Project/works/newwork?id=124&time=2019-02-01T11:06:15
    
(2) An endpoint for getting the top ID from the queue and removing it (de-queue). This endpoint should return the highest ranked ID and the time
    it was entered into the queue.
    
    example : http://localhost:8080/Randon-Project/works/gettop
    
(3) An endpoint for getting the list of IDs in the queue. This endpoint should return a list of IDs sorted from highest ranked to lowest.
    
    example : http://localhost:8080/Randon-Project/works/getall

(4) An endpoint for removing a specific ID from the queue. This endpoint should accept a single parameter, the ID to remove. 
    
    http://localhost:8080/Randon-Project/works/remove?id=

(5) An endpoint to get the position of a specific ID in the queue. This endpoint should accept one parameter, the ID to get the position of. It should return
    the position of the ID in the queue indexed from 0.
    
     example : http://localhost:8080/Randon-Project/works/getindex?id=123
    
(6) An endpoint to get the average wait time. This endpoint should accept a single parameter, the current time, and should return the average (mean)
    number of seconds that each ID has been waiting in the queue.
    
    example : http://localhost:8080/Randon-Project/works/getAverageWaitTime?time=2019-02-01T11:06:15
    
    
Funcationality of each class present in the project
__________________________________________________________
1) src - com.aspect.main.PriorityQueueImpl.java
   This class provides the core implementation of priorityqueue logic.

2) src - com.aspect.main.Work.java
   This is the pojo class, which holds workditem objects

3) src - com.aspect.webservices.EndPointServices.java
   This class provides the implementation of restful services for the endpoints url.

4) test - com.aspect.main.PriorityQueueImplTest.java
   This is the JUnit class for testing the functionality of PriorityQueueImpl.java class
   


