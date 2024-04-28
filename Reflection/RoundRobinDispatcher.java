/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class extends the abstract JobDispatcher class to implement a job dispatcher that
                cyclically  to servers( in a sequential order)

*/

public class RoundRobinDispatcher extends JobDispatcher {
    private int currentIndex;

    public RoundRobinDispatcher(int k, boolean showViz) {
        super(k, showViz);
        this.currentIndex = 0;
    }

    public PreemptiveServer pickServer(Job j) {
        if (server_collection.isEmpty()) {
            throw new IllegalStateException("No servers available to dispatch jobs.");
        }
        PreemptiveServer chosenServer = server_collection.get(currentIndex);
        currentIndex = (currentIndex + 1) % server_collection.size();
        return chosenServer;
    }
    
}