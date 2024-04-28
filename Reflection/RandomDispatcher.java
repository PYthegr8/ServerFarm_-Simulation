/*
 * Papa Yaw Owusu Nti
 * March 22nd, 2024
 * CS231 B
 * Project 4
 * 
 * Description: This class extends the abstract JobDispatcher class to implement a job dispatcher that
                randomly assign incoming jobs to servers.

*/

import java.util.Random;
public class RandomDispatcher extends JobDispatcher{

    private Random rand;
    public RandomDispatcher(int k, boolean showViz) {
        super(k, showViz);
        rand = new Random();
        
    }

    @Override
    public PreemptiveServer pickServer(Job j) {
        int index = rand.nextInt(server_collection.size());
        return server_collection.get(index);
    }
}
