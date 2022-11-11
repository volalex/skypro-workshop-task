package com.skypro.streams.workshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * В этом задании вам нужно переписать предложенные методы с помощью Stream API таким образом,
 * чтобы вывод программы не изменился, но все методы были переписаны с помощью Stream API
 */
public class Main {
    private static final String TASK_SEPARATOR =
            "---------------------------------------------------";

    public static void main(String[] args) throws IOException {
        String[] fruits = {"apple", "lemon", "pineapple", "watermelon", "avocado", "apricot"};
        System.out.println("Задание 1");
        System.out.println("Фруктов начинающихся на а всего " + countAllFruitsWithFirstA(fruits));
        System.out.println(TASK_SEPARATOR);
        System.out.println("Задание 2");
        System.out.println("Распечатываем все фрукты по алфавиту в верхнем регистре");
        printFruits(Arrays.asList(Arrays.copyOf(fruits, fruits.length)));
        System.out.println(TASK_SEPARATOR);
        System.out.println("Задание 3");
        List<FruitWithId> fruit = Arrays.stream(fruits)
                .map(FruitWithId::new)
                .collect(Collectors.toList());
        System.out.println("2 фрукта с длинными именами:" + collectTwoFruitsWithLongNames(fruit));
        System.out.println(TASK_SEPARATOR);
        System.out.println("Задание 4");
        List<List<String>> fruitsFromFiles = Stream.of(
                        Files.readAllLines(Paths.get("fruits1.txt")),
                        Files.readAllLines(Paths.get("fruits2.txt")))
                .collect(Collectors.toList());
        System.out.println("Фрукты в списках: " + getAllFruitsFromFilesInLowerCase(fruitsFromFiles));
    }

    /**
     * Метод подсчитывает количество фруктов в массиве название которых начинается с буквы 'a'
     * PS: Нужно создать стрим из массива с помощью Arrays.stream(), и использовать операции filter и count
     *
     * @param fruits Список фруктов
     * @return количество фруктов в массиве которые начинаются с буквы а
     */
    public static long countAllFruitsWithFirstA(String[] fruits) {
        return Arrays.stream(fruits)
                .filter(s -> s.charAt(0) == 'a')
                .count();
    }

    /**
     * Метод распечатывает в UPPER_CASE названия всех фруктов по алфавиту
     * PS: Нужно сделать stream из list используя встроенный метод,
     * далее помогут операции sorted, map и терминальная операция forEach
     *
     * @param fruits Список фруктов
     */
    public static void printFruits(List<String> fruits) {
        fruits
                .stream()
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Метод принимает пару фрукт и его идентификатор, выбирает 2 фрукта с именами больше 5 символов
     * и собирает их в Map в которой ключом является id фрукта, а значением - сам фрукт
     * PS: тут пригодится filter и skip а также коллектор toMap
     *
     * @param fruits список фруктов с id
     * @return Ассоциативный массив фруктов и их идентификаторов
     */
    public static Map<Integer, FruitWithId> collectTwoFruitsWithLongNames(List<FruitWithId> fruits) {
        return fruits.stream()
                .filter(fr -> fr.getName().length() > 5)
                .limit(2)
                .collect(Collectors.toMap(FruitWithId::getId, Function.identity()));
    }

    /**
     * Метод получает считанные из файлов строки с фруктами,
     * приводит их в одинаковый вид и составляет из них единый список
     * PS: тут пригодится flatMap
     *
     * @param fruitsFromFiles строки с фруктами которые мы считали из файлов
     * @return список названий фруктов в нижнем регистре
     */
    public static List<String> getAllFruitsFromFilesInLowerCase(List<List<String>> fruitsFromFiles) {
        return fruitsFromFiles
                .stream()
                .flatMap(Collection::stream)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
