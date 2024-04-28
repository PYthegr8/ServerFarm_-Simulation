/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class represents a server that manages a queue of jobs. 
                The server keeps track of system time, total waiting time, remaining processing time,
                and the number of jobs processed.
                It has a constructor, size(), draw() and addJob method to Add a job to the server's queue
                processTo() advances the system time and processes jobs in the queue until reaching the specified time.
                remainingWorkInQueue(): Returns the total remaining processing time in the server's queue.

*/

import java.awt.Graphics;
import java.awt.Color; 
import java.awt.Toolkit; 
import java.awt.Font;

public class Server {
    
    private Queue<Job> jobQueue;
    private double Time;
    private double remainingTime;
    double totalWaitingTime;
    int numJobsFinished;



    /**
     *  this constructor initializes whatever fields the Server will need
     * @return nothing
    */
    public Server() {
        this.jobQueue = new LinkedList<>();
        this.Time = 0;
        this.totalWaitingTime = 0;
        this.remainingTime= 0;
        this.numJobsFinished = 0;


    }



    /**
     *  adds the specified Job job into the queue
     * @return nothing
    */
    
    public void addJob(Job job){
    
        jobQueue.offer(job);
        remainingTime += job.getProcessingTimeNeeded();
        
    }


    /**
     *   advances the system time of this queue to the specified time time
     * @return nothing
    */
    public void processTo(double time) {
        double timeLeft =  time - this.Time ;

        while (timeLeft > 0 && size() > 0) {
            Job currentJob = jobQueue.peek(); 
            
            double timeToProcess = Math.min(timeLeft, currentJob.getProcessingTimeRemaining()); 
            
            // Process the current job
            currentJob.process(timeToProcess, this.Time);

            // if the job has been finished: 
            if (currentJob.isFinished()) {
                jobQueue.poll();
            
                // Increment totalWaitingTime by this job's waiting time
                totalWaitingTime +=  currentJob.timeInQueue();
                
                // Increment numJobsFinished by 1
                numJobsFinished++;
            }
        
            // Decrement timeLeft, remainingTime by the time I spent processing
            timeLeft -= timeToProcess;
            remainingTime -= timeToProcess;
            
            // Increase time by time I spent processing
            this.Time += timeToProcess;
        }

    // just in case I ran out of jobs before getting to the desired time
    this.Time = time;

    }
    
    

    /**
     *  returns the total remaining processing time in this Server's queue
     * @return time
    */
    public double remainingWorkInQueue(){

        return remainingTime;
    }



    /**
     *  returns the number of Jobs in this Server's queue.
     * @return int-number of Jobs 
    */
    public int size(){
        return jobQueue.size();
    }


    /**
     *  This is necessary for the GUI we've provided
     * @return nothing
    */
    public void draw(Graphics g, Color c, double loc, int numberOfServers){
        double sep = (ServerFarmViz.HEIGHT - 20) / (numberOfServers + 2.0);
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (72.0 * (sep * .5) / Toolkit.getDefaultToolkit().getScreenResolution())));
        g.drawString("Work: " + (remainingWorkInQueue() < 1000 ? remainingWorkInQueue() : ">1000"), 2, (int) (loc + .2 * sep));
        g.drawString("Jobs: " + (size() < 1000 ? size() : ">1000"), 5 , (int) (loc + .55 * sep));
        g.setColor(c); 
        g.fillRect((int) (3 * sep), (int) loc, (int) (.8 * remainingWorkInQueue()), (int) sep);
        g.drawOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
        if (remainingWorkInQueue() == 0) g.setColor(Color.GREEN.darker());
        else g.setColor(Color.RED.darker());
        g.fillOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
    }

}

