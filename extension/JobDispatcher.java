/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This abstract superclass represents a job dispatcher that manages a collection of servers. 
 *              It contains a constructor to initialize the server collection, advance time, handle jobs, and calculate statistics. 
 *              Additionally, it includes an abstract method for picking a serverand a method for drawing the server farm.

*/

import java.awt.Graphics;
import java.awt.Color; 
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Font;

public abstract class JobDispatcher {

    double system_time;
    int numJobsHandled;
    protected ServerFarmViz viz;
    protected ArrayList<Server> server_collection;
   


    // constructs a JobDispatcher with k total Servers
    public JobDispatcher(int k, boolean showViz){
        this.server_collection = new ArrayList<>();
        this.system_time = 0;
        this.numJobsHandled =0;
        for (int i = 0; i<k; i++){
            server_collection.add(new Server());
        }
        this.viz = new ServerFarmViz(this , showViz );
    }



    // returns the time
    public double getTime(){
        return this.system_time;
    }



    // returns the jobDispatcher's collection of Servers
    public ArrayList<Server>  getServerList(){
        return this.server_collection;
    }



    // this method updates the system, time to the specified time and calls the processTo method for each Server it maintains.
    public void advanceTimeTo(double param_time){
        if (param_time > this.system_time) {
        for (Server server : server_collection) {
            server.processTo(param_time);
        }
        this.system_time = param_time;
    }
    }




    // advances the time to job's arrival time, calls the ServerFarmViz object's repaint() method,...
    public void handleJob(Job job){
        boolean paused = viz.isPaused(); // Store the pause state
        if (!paused) {
            viz.repaint();
            advanceTimeTo(job.getArrivalTime());
            Server chosenServer = pickServer(job);
            chosenServer.addJob(job);
            viz.repaint();
            numJobsHandled++;
        }
        viz.repaint();
      
    }




    // advances the time to the earliest point when all jobs will have completed.
    public void finishUp() {
        
        double maxCompletionTime = 0;
        for (Server server : server_collection) {
            double serverCompletionTime = server.remainingWorkInQueue() + this.system_time;
            if (serverCompletionTime > maxCompletionTime) {
                maxCompletionTime = serverCompletionTime;
            }
        }
        advanceTimeTo(maxCompletionTime);
    }




    //  gets the total number of jobs handled across all Servers.
    public int getNumJobsHandled(){
        return this.numJobsHandled;
    }



    //  gets the total waiting time for each Server, adds them together, and divides it by the number of jobs handled across all Servers.
    public double getAverageWaitingTime(){

        double WaitingTime = 0;

        for (Server server : server_collection) {
            WaitingTime += server.totalWaitingTime;
        }

        double average = WaitingTime / numJobsHandled ;
        return average;
        
    }



    //  as specified above, this method is abstract as we don't know how to implement this method without knowing what particular algorithm we are following for handling our jobs.
    public abstract Server pickServer(Job j);


    // This method is necessary for the GUI we've provided. For this method, you can copy and paste the following method into your JobDispatcher class. Make sure to import the following in order for it to work: java.awt.Graphics; java.awt.Color.

    public void draw(Graphics g){
        double sep = (ServerFarmViz.HEIGHT - 20) / (getServerList().size() + 2.0);
        g.drawString("Time: " + getTime(), (int) sep, ServerFarmViz.HEIGHT - 20);
        g.drawString("Jobs handled: " + getNumJobsHandled(), (int) sep, ServerFarmViz.HEIGHT - 10);
        for(int i = 0; i < getServerList().size(); i++){
            getServerList().get( i ).draw(g, (i % 2 == 0) ? Color.GRAY : Color.DARK_GRAY, (i + 1) * sep, getServerList().size());
        }
    }
    		


}
