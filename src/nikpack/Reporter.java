package nikpack;

/**
 * Created by sa on 12.06.17.
 */
public class Reporter implements Runnable {

    public static volatile boolean stopThread = false;

    private Generator gen;

    public Reporter(Generator gen) {
        this.gen = gen;
    }

    @Override
    public void run() {
        try {
            while (!stopThread) {
                Thread.sleep(5000);
                int distinct = gen.getSetSize();
                System.out.println("Количество уникальных значений: " + distinct);

                if (stopThread || (distinct >= 100)) {
                    Generator.stopThread = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Поток отчета прерван");
        }
    }

}
