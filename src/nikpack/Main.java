package nikpack;

public class Main {

    public static void main(String[] args) {
        Generator gen = new Generator();
        Thread genThread = new Thread(gen);
        Thread reporter = new Thread(new Reporter(gen));

        genThread.start();
        reporter.start();

        try {
            reporter.join();
            genThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
