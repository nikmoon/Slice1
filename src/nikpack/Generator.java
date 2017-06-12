package nikpack;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by sa on 12.06.17.
 */
public class Generator implements Runnable {

    static volatile boolean stopThread = false;
    static Set<Integer> set;
    Random rand = new Random(System.currentTimeMillis());

    public Generator() {
        set = new HashSet<>();
    }

    @Override
    public void run() {
        try {
            while (!stopThread) {
                putValue(rand.nextInt(100));
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Поток генератора прерван");
        }
    }


    public synchronized void putValue(int value) {
        set.add(value);
    }

    public synchronized int getSetSize() {
        return set.size();
    }
}
