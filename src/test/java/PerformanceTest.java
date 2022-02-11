
import org.junit.Test;
import ru.victor.domain.CSVTask;
import ru.victor.domain.CSVTaskParallel;

import java.io.IOException;

public class PerformanceTest {


    @Test
    public void PerformanceTestParallel() throws IOException {
        long time2 = System.nanoTime();
        CSVTaskParallel createCSVTaskParallel = new CSVTaskParallel(1000000);
        createCSVTaskParallel.writeCSV();
        time2 = System.nanoTime() - time2;
        System.out.printf("Elapsed parallel %,9.3f ms\n", time2 / 1_000_000.0);

        long time1 = System.nanoTime();
        CSVTask createCSVTask = new CSVTask(1000000);
        createCSVTask.writeCSV();
        time1 = System.nanoTime() - time1;
        System.out.printf("Elapsed not parallel %,9.3f ms\n", time1 / 1_000_000.0);


    }
}
