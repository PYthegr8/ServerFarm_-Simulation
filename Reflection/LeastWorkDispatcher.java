/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class extends the abstract JobDispatcher class to implement a job dispatcher that selects 
 *              the server with the least amount of remaining work in its queue to handle incoming jobs. 
 * 
*/

public class LeastWorkDispatcher extends JobDispatcher {
    
    public LeastWorkDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }


    public PreemptiveServer pickServer(Job j) {
        if (server_collection.isEmpty()) {
            throw new IllegalStateException("No servers available to dispatch jobs.");
        }
        PreemptiveServer leastWorkServer = server_collection.get(0);
        double minRemainingWork = leastWorkServer.remainingWorkInQueue();

        for (int i = 1; i < server_collection.size(); i++) {
            PreemptiveServer currentServer = server_collection.get(i);
            double currentRemainingWork = currentServer.remainingWorkInQueue();
            if (currentRemainingWork < minRemainingWork) {
                leastWorkServer = currentServer;
                minRemainingWork = currentRemainingWork;
            }
        }
        return leastWorkServer;
    }
}
