import java.util.Scanner;
public class ServerFarmSimulation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // userinput number of servers
        System.out.print("Enter the number of servers: ");
        int numServers = scanner.nextInt();


        System.out.print("Enter the Number of jobs to process: ");
        int numJobs = scanner.nextInt();


        // userinput preferred dispatcher type
        System.out.print("Enter the preferred dispatcher type (random, round, shortest, least): ");
        String dispatcherType = scanner.next();


        int meanArrivalTime = 3 ; 
        int meanProcessingTime = 100 ; 
        boolean showViz = true ; 
        

        //Initialize the job maker with the mean arrival and processing time
        JobMaker jobMaker = new JobMaker( meanArrivalTime , meanProcessingTime );

        //Create a dispatcher of the appropriate type
        JobDispatcher dispatcher = null ;
        if ( dispatcherType.equals("random") ){
            dispatcher = new RandomDispatcher( numServers , showViz ) ; 
        }
        else if ( dispatcherType.equals( "round") ) { 
            dispatcher = new RoundRobinDispatcher( numServers , showViz ) ; 
        }
        else if ( dispatcherType.equals("shortest" )) {
            dispatcher = new ShortestQueueDispatcher( numServers , showViz ) ; 
        } else if ( dispatcherType.equals("least" )) {
            dispatcher = new LeastWorkDispatcher( numServers , showViz ) ; 
        }
        
        //Have the dispatched handle the specified number of jobs
        for ( int i = 0 ; i < numJobs ; i ++ ) {
            dispatcher.handleJob( jobMaker.getNextJob() ) ;
        }
        dispatcher.finishUp() ; //Finish all of the remaining jobs in Server queues
        
        
        //Print out the mean processing time
        System.out.println( "Dispatcher: " + dispatcherType + ", Avg. Wait time: " + dispatcher.getAverageWaitingTime() ) ;
        scanner.close();

    }


}