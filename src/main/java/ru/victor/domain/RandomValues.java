package ru.victor.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomValues {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final long startDate = LocalDate.of(2021, 1, 1).toEpochDay();
    private final long endDate = LocalDate.of(2021, 12, 31).toEpochDay();
    private static final BigDecimal max = new BigDecimal(999);

    public List<String> ListRandomName() {
        char[] string = new char[8];
        return IntStream.rangeClosed(0, 1000)
//                .parallel()
                .mapToObj(s -> {
                    for (int i = 0; i < 8; i++) {
                        int random = ThreadLocalRandom
                                .current()
                                .nextInt(10, 34);
                        string[i] = Character.forDigit(random, Character.MAX_RADIX);
                    }
                    return new String(string);
                })
                .collect(Collectors.toList());
    }

    public String randomName(int count, List<String> listNames){
            if(count > listNames.size() - 1) count = count % (count / listNames.size());
            return listNames.get(count);
    }
    public List<String> randomBigDecimal(int amount) {
        List<String> strings = new ArrayList<>(amount);
        for (int i = 0; i <= amount; i++) {
            BigDecimal random = BigDecimal.valueOf(ThreadLocalRandom
                    .current()
                    .nextDouble(1, 999));
            strings.add(random
                    .setScale(2, RoundingMode.DOWN).toPlainString());
        }
        return strings;
    }

    public List<String> randomDate(int amount) {
        List<String> strings = new ArrayList<>(amount);
        for (int i = 0; i <= amount; i++) {
            long random = ThreadLocalRandom
                    .current()
                    .nextLong(startDate, endDate);
            String string = LocalDate.ofEpochDay(random).format(dateTimeFormatter);
            strings.add(string);
        }
        return strings;
    }
}
