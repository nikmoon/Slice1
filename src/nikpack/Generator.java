package nikpack;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sa on 12.06.17.
 */
public class Generator implements Runnable {

    public static volatile boolean stopThread = false;

    private final int UNIQUE_LIMIT;
    private Set<Integer> uniques;
    private List<BlockingQueue<Integer>> queues;
    private Random rand;
    private int maxRand;


    public Generator(int uniqueLimit, int maxRand) {
        UNIQUE_LIMIT = uniqueLimit;
        rand = new Random(System.currentTimeMillis());
        uniques = new HashSet<>();
        queues = new CopyOnWriteArrayList<>();
        this.maxRand = maxRand;
    }

    public Generator() {
        this(100, 100);
    }

    public void linkConsumer(BlockingQueue<Integer> queue) {
        queues.add(queue);
    }

    @Override
    public void run() {
        int uniqueCount;
        try {
            while (!stopThread) {
                Thread.sleep(1000);
                uniques.add(rand.nextInt(maxRand));
                uniqueCount = uniques.size();
                sendValue(uniqueCount);
                if (uniqueCount >= UNIQUE_LIMIT) {
                    sendValue(UNIQUE_LIMIT);
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Поток генератора прерван");
            sendValue(UNIQUE_LIMIT);
        }
    }

    private void sendValue(int value) {
        for(BlockingQueue<Integer> queue: queues) {
            queue.offer(value);
        }
    }

}
