/* 
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class conducts experiments on four types of job dispatchers (Random,Round Robin,Shortest Queue,Least Work)
                with 34 servers and 10,000,000 jobs to analyze their average waiting times. 

*/

public class ServerFarmSimulationExploration1 {

    public static void main(String[] args) {
        // Experiment configurations
        int numServers = 34; // Numbers of servers in the farm
        int numJobs = 10000000; // Number of jobs to process
        int meanArrivalTime = 3; // How often a new job arrives at the server farm, on average
        int meanProcessingTime = 100; // How long a job takes to process, on average
        boolean showViz = false; // Set to true to see the visualization, and false to run your experiments

        // Create job maker with the mean arrival and processing time
        JobMaker jobMaker = new JobMaker(meanArrivalTime, meanProcessingTime);

        // Create dispatchers of all four types
        JobDispatcher randomDispatcher = new RandomDispatcher(numServers, showViz);
        JobDispatcher roundDispatcher = new RoundRobinDispatcher(numServers, showViz);
        JobDispatcher shortestDispatcher = new ShortestQueueDispatcher(numServers, showViz);
        JobDispatcher leastDispatcher = new LeastWorkDispatcher(numServers, showViz);

        // Run experiments with all dispatchers
        runExperiment(randomDispatcher, numJobs, jobMaker);
        runExperiment(roundDispatcher, numJobs, jobMaker);
        runExperiment(shortestDispatcher, numJobs, jobMaker);
        runExperiment(leastDispatcher, numJobs, jobMaker);

        System.out.println();
        System.out.println("Displaying the Average Waiting time for the 4 job dispatchers with 34 servers and 10000000 jobs");
        System.out.println();

        // Print out the results
        printResults(randomDispatcher, "Random");
        printResults(roundDispatcher, "Round Robin");
        printResults(shortestDispatcher, "Shortest Queue");
        printResults(leastDispatcher, "Least Work");
    }

    // Method to run experiment for a dispatcher
    private static void runExperiment(JobDispatcher dispatcher, int numJobs, JobMaker jobMaker) {
        for (int i = 0; i < numJobs; i++) {
            dispatcher.handleJob(jobMaker.getNextJob());
        }
        dispatcher.finishUp();
    }

    // Method to print out results for a dispatcher
    private static void printResults (JobDispatcher dispatcher, String dispatcherType) {
        System.out.println(dispatcherType + " Dispatcher has an Average Wait time: " + dispatcher.getAverageWaitingTime());
    }
}
