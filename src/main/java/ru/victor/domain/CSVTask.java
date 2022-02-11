package ru.victor.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CSVTask {
    private final int amount;
    private static final RandomValues random = new RandomValues();
    private static final String COMMA_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id, product, price, date";

    public CSVTask(int amount) {
        this.amount = amount;
    }

    private void createTask(BufferedWriter bufferedWriter) throws IOException, ExecutionException, InterruptedException {
        List<String> listDate = random.randomDate(amount);
        List<String> listDecimal = random.randomBigDecimal(amount);
        List<String> listNames = random.ListRandomName();
        bufferedWriter.append(FILE_HEADER)
                .append(NEW_LINE_SEPARATOR);
        for (int i = 1; i <= amount; i++) {
            try {
                bufferedWriter
                        .append(String.format("%08d\n", i))
                        .append(COMMA_DELIMITER)
                        .append(random.randomName(i, listNames))
                        .append(COMMA_DELIMITER)
                        .append(listDecimal.get(i))
                        .append(COMMA_DELIMITER)
                        .append(listDate.get(i))
                        .append(NEW_LINE_SEPARATOR);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeCSV() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("historicalPrice.csv"))) {
            createTask(bufferedWriter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
