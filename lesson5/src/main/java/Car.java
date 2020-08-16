import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
        CyclicBarrier raceStartBarrier;

    }

    private Race race;
    private int speed;
    private String name;

    private CyclicBarrier cb;
    private CountDownLatch cdl;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier raceStartBarrier, CountDownLatch finishRaceLatch) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = raceStartBarrier;
        this.cdl = finishRaceLatch;

    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!race.getIsWinnerExists().getAndSet(true)) {
            System.out.println(this.name + " - победитель!!!");
        }
        cdl.countDown();
    }
}