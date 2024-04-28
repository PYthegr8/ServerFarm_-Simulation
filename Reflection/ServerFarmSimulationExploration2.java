/* 
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class conducts experiments on the ShortestQueueDispatcher with varying servers from 30 to 40 
                to analyze the average waiting time for a large number of jobs (10,000,000 jobs)
 */

public class ServerFarmSimulationExploration2{

    public static void main(String[] args) {
        // Experiment configurations
        int numJobs = 10000000; // Number of jobs to process
        int meanArrivalTime = 3; // How often a new job arrives at the server farm, on average
        int meanProcessingTime = 100; // How long a job takes to process, on average
        boolean showViz = false; // Set to true to see the visualization, and false to run your experiments

        System.out.println();
        System.out.println("Displaying the Average Waiting time for shortestDispatcher with varying number of servers and 10000000 jobs");
        System.out.println();

        for (int numServers = 30; numServers <= 40; numServers++) {
            // Create job maker with the mean arrival and processing time
            JobMaker jobMaker = new JobMaker(meanArrivalTime, meanProcessingTime);

            // Create dispatcher
            JobDispatcher shortestDispatcher = new ShortestQueueDispatcher(numServers, showViz);
   

            // Run experiment
            runExperiment(shortestDispatcher, numJobs, jobMaker);
   
            // Print out the results
            printResults(shortestDispatcher, "Shortest Queue", numServers);

        }
    }

    // Method to run experiment for a dispatcher
    private static void runExperiment(JobDispatcher dispatcher, int numJobs, JobMaker jobMaker) {
        for (int i = 0; i < numJobs; i++) {
            dispatcher.handleJob(jobMaker.getNextJob());
        }
        dispatcher.finishUp();
    }

    // Method to print out results for a dispatcher
    private static void printResults (JobDispatcher dispatcher, String dispatcherType, int numServers) {
        System.out.println(dispatcherType + " Dispatcher with " + numServers + " Servers" + " has an Average Wait time: " + dispatcher.getAverageWaitingTime());
    }
}
