import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Race {
    private ArrayList<Stage> stages;
    private AtomicBoolean isWinnerExists=new AtomicBoolean(false);

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public AtomicBoolean getIsWinnerExists() {
        return isWinnerExists;
    }
}
