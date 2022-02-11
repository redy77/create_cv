import org.junit.Test;
import ru.victor.domain.CSVTask;
import ru.victor.domain.CSVTaskParallel;

import java.io.IOException;

public class PerformanceTest {

    @Test
    public void PerformanceTestParallel() throws IOException {
        long time = System.nanoTime();
        CSVTaskParallel createCSVTaskParallel = new CSVTaskParallel(1000000);
        createCSVTaskParallel.writeCSV();
        time = System.nanoTime() - time;
        System.out.printf("Elapsed parallel %,9.3f ms\n", time / 1_000_000.0);
    }

    @Test
    public void PerformanceTest() throws IOException {
        long time = System.nanoTime();
        CSVTask createCSVTask = new CSVTask(1500);
        createCSVTask.writeCSV();
        time = System.nanoTime() - time;
        System.out.printf("Elapsed not parallel %,9.3f ms\n", time / 1_000_000.0);
    }
}
