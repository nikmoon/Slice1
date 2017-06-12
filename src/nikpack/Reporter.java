package nikpack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sa on 12.06.17.
 */
public class Reporter implements Runnable {

    public static volatile boolean stopThread = false;

    private BlockingQueue<Integer> queue;
    private long millis;
    private int seconds;

    public static List<Thread> createReporters(Generator gen, int ... intervals) {
        List<Thread> reporters = new ArrayList<>();
        for(int interval: intervals) {
            reporters.add(new Thread(new Reporter(gen, interval)));
        }
        return reporters;
    }

    public Reporter(Generator gen, int interval) {
        this.seconds = interval;
        this.millis = interval * 1000;
        queue = new LinkedBlockingQueue<>();
        gen.linkConsumer(queue);
    }

    @Override
    public void run() {
        Integer uniqueCount;
        long beginMillis = System.currentTimeMillis();
        try {
            while (!stopThread) {
                uniqueCount = queue.take();
                if ((System.currentTimeMillis() - beginMillis) >= millis) {
                    System.out.println("Поток " + seconds + ", количество уникальных значений: " + uniqueCount);
                    beginMillis = System.currentTimeMillis();
                }
                if (uniqueCount >= 100) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Поток отчета прерван");
        }
    }

}
