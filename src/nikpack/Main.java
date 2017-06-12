package nikpack;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Generator generator = new Generator();
        Thread generatorThread = new Thread(generator);
        List<Thread> reporters = Reporter.createReporters(generator, new int[] {1, 5});


        generatorThread.start();
        for(Thread reporter: reporters)
            reporter.start();

        try {
            generatorThread.join();
            for(Thread reporter: reporters) {
                reporter.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
