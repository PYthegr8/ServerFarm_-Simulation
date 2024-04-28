/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class extends the abstract JobDispatcher class to implement a job dispatcher that
                assign incoming jobs to the server with the shortest queue length. 

*/


public class ShortestQueueDispatcher extends JobDispatcher {


    public ShortestQueueDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

  
    public PreemptiveServer pickServer(Job j) {
        
        if (server_collection.isEmpty()) {
            throw new IllegalStateException("No servers available to dispatch jobs.");
        }

        PreemptiveServer shortestQueueServer = server_collection.get(0);
        int minQueueSize = shortestQueueServer.size();

        for (int i = 1; i < server_collection.size(); i++) {
            PreemptiveServer currentServer = server_collection.get(i);
            int currentQueueSize = currentServer.size();

            if (currentQueueSize < minQueueSize) {
                shortestQueueServer = currentServer;
                minQueueSize = currentQueueSize;
            }
        }
        return shortestQueueServer;
    }
    
}
