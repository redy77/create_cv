package ru.victor.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class CSVTaskParallel {
    private final int amount;
    private static final RandomValues random = new RandomValues();
    private static final String COMMA_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id, product, price, date";
    List<String> listDates;
    List<String> listDecimal;
    List<String> listNames;

    public CSVTaskParallel(int amount) {
        this.amount = amount;
    }

    private void parallel() throws ExecutionException, InterruptedException {
        Callable<List<String>> randomDecimal = () -> random.randomBigDecimal(amount);
        Future<List<String>> decimal = Executors.newSingleThreadExecutor().submit(randomDecimal);
        Callable<List<String>> randomData = () -> random.randomDate(amount);
        Future<List<String>> date = Executors.newSingleThreadExecutor().submit(randomData);
        Callable<List<String>> randomName = random::ListRandomName;
        Future<List<String>> name = Executors.newCachedThreadPool().submit(randomName);
        listDates = date.get();
        listDecimal = decimal.get();
        listNames = name.get();
//        Runnable runnable = () -> {
//            listDates = random.randomDate(amount);
//            listDecimal = random.randomBigDecimal(amount);
//            listNames = random.ListRandomName();
//        };
//        Executors.newCachedThreadPool().submit(runnable).get();
    }

    private void createTask(BufferedWriter bufferedWriter) throws ExecutionException, InterruptedException {
        parallel();
        try {
            bufferedWriter.append(FILE_HEADER)
                    .append(NEW_LINE_SEPARATOR);

            for (int i = 1; i <= amount; i++) {
                bufferedWriter
                        .append(String.format("%08d\n", i))
                        .append(COMMA_DELIMITER)
                        .append(random.randomName(i - 1, listNames))
                        .append(COMMA_DELIMITER)
                        .append(listDecimal.get(i - 1))
                        .append(COMMA_DELIMITER)
                        .append(listDates.get(i - 1))
                        .append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
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
