package stream;

import Model.Student;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamBasicExamples {
    @Test
    public void allMatch() {
        Predicate<Student> p1 = s -> s.getStuName().startsWith("A");
        Predicate<Student> p2 = s -> s.getStuAge() < 40;
        Predicate<Student> p3 = s -> s.getStuAge() < 40 && s.getStuName().startsWith("P");
        List<Student> list = new ArrayList<>();
        list.add(new Student(11, 28, "Lucy"));
        list.add(new Student(28, 27, "Tim"));
        list.add(new Student(32, 30, "Daniel"));
        list.add(new Student(49, 27, "Steve"));

        assertEquals(false, list.stream().allMatch(p1));
        assertEquals(true, list.stream().allMatch(p2));
        assertEquals(false, list.stream().allMatch(p3));
    }

    @Test
    public void noneMatch() {
        Predicate<Student> p1 = s -> s.getStuName().startsWith("L");
        Predicate<Student> p2 = s -> s.getStuAge() < 28 && s.getStuName().startsWith("P");
        List<Student> list = new ArrayList<>();
        list.add(new Student(11, 28, "Lucy"));
        list.add(new Student(28, 27, "Kiku"));
        list.add(new Student(32, 30, "Dani"));
        list.add(new Student(49, 27, "Steve"));
        assertEquals(false, list.stream().noneMatch(p1));
        assertEquals(true, list.stream().noneMatch(p2));
    }

    @Test
    public void findFirst() {
        List<String> list = Arrays.asList("A", "B", "C", "D");

        Optional<String> result = list.stream().findFirst();
        assertTrue(result.isPresent());
        assertEquals("A", result.get());
        assertEquals(null, Collections.emptyList().stream().findFirst().orElse(null));
    }

    @Test
    public void findAny() {
        List<String> list = Arrays.asList("A", "B", "C", "D");
        Optional<String> result = list.stream().findAny();
        assertTrue(result.isPresent());
    }

    @Test
    public void builder() {
        Stream.Builder<String> builder = Stream.builder();
        builder.add("Production");
        builder.add("Marketing");
        builder.add("Finance");
        builder.add("Sales");
        builder.add("Operations");
        Stream<String> stream = builder.build();
        Optional<String> result = stream.findFirst();
        assertEquals("Production", result.get());
    }

    @Test
    public void iterate() {
        List<Integer> result = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .collect(Collectors.toList());
        assertEquals(10, result.size());
        List<Integer> expected = Arrays.asList(1, 2, 4, 8, 16);
        result = Stream.iterate(1, n -> n < 20 , n -> n * 2)
                .collect(Collectors.toList());
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void collect() {
        List<String> listOfString = Arrays.asList("Java", "C", "C++", "Go", "JavaScript", "Python", "Scala");
        List<String> listOfStringStartsWithJ = listOfString.stream() .filter(s -> s.startsWith("J")) .collect(Collectors.toList());
        assertArrayEquals(Arrays.asList("Java", "JavaScript").toArray(), listOfStringStartsWithJ.toArray());

        Set<String> setOfStringStartsWithC = listOfString.stream() .filter(s -> s.startsWith("C")) .collect(Collectors.toSet());
        assertEquals(2, setOfStringStartsWithC.size());
        // {Java=4, C++=3, C=1, Scala=5, JavaScript=10, Go=2, Python=6}
        Map<String, Integer> stringToLength = listOfString.stream() .collect(Collectors.toMap(Function.identity(), String::length));
        assertEquals(7, stringToLength.size());

    }

    @Test
    public void concat() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);

        Stream<Integer> resultingStream = Stream.concat(stream1, stream2);

        assertEquals(
                Arrays.asList(1, 3, 5, 2, 4, 6),
                resultingStream.collect(Collectors.toList()));
    }

    @Test
    public void distinct() {
        List<Integer> stream1 = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result = stream1.stream().distinct().collect(Collectors.toList());
        assertArrayEquals(expected.toArray(), result.toArray());

    }

    @Test
    public void flatMap() {
        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(4,5,6);
        List<Integer> list3 = Arrays.asList(7,8,9);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<List<Integer>> listOfLists = Arrays.asList(list1, list2, list3);

        List<Integer> listOfAllIntegers = listOfLists.stream()
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
        assertArrayEquals(expected.toArray(), listOfAllIntegers.toArray());

        String[][] dataArray = new String[][]{{"a", "b"},
                {"c", "d"}, {"e", "f"}, {"g", "h"}};
        List<String> expectedStr = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
        List<String> listOfAllChars = Arrays.stream(dataArray)
                .flatMap(x -> Arrays.stream(x))
                .collect(Collectors.toList());
        assertArrayEquals(expectedStr.toArray(), listOfAllChars.toArray());
    }

    @Test
    public void generate() {
        List<Integer> result = Stream.generate(new Random()::nextInt)
                .limit(10).collect(Collectors.toList());
        assertEquals(10, result.size());
    }

    @Test
    public void map() {
        List<String> list = Arrays.asList("hello", "world", "welcome",
                "to", "the", "world");
        List<String> expected = Arrays.asList("HELLO", "WORLD", "WELCOME",
                "TO", "THE", "WORLD");
        List<String> result = list.stream().map(String::toUpperCase).collect(Collectors.toList());

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void max_min() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());

        LocalDate maxDate = dates.stream()
                .max( Comparator.comparing( LocalDate::toEpochDay ) )
                .get();

        LocalDate minDate = dates.stream()
                .min( Comparator.comparing( LocalDate::toEpochDay ) )
                .get();
        assertEquals(start.toEpochDay(), minDate.toEpochDay());
        assertEquals(end.toEpochDay(), maxDate.plusDays(1).toEpochDay());


        // given
        List<Integer> listOfIntegers = Arrays.asList(1, 2, 3, 4, 56, 7, 89, 10);
        Integer expectedResult = 89;

        // then
        Integer max = listOfIntegers
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        assertEquals(expectedResult, max);
    }

    @Test
    public void peek() {
        List<String> expected = Arrays.asList("THREE", "FOUR");
        //This method exists mainly to support debugging, where you want to see the elements as they flow past a certain point in a pipeline“
        List<String> result = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void reduce() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int sum = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);    // 55
        int sum2 = Arrays.stream(numbers).reduce(0, Integer::sum);      // 55

        int sum3 = Arrays.stream(numbers).reduce(0, (a, b) -> a - b);   // -55
        int sum4 = Arrays.stream(numbers).reduce(0, (a, b) -> a * b);   // 0, initial is 0, 0 * whatever = 0
        int sum5 = Arrays.stream(numbers).reduce(0, (a, b) -> a / b);   // 0

        assertEquals(55, sum);
        assertEquals(55, sum2);
        assertEquals(-55, sum3);
        assertEquals(0, sum4);
        assertEquals(0, sum5);
    }



}
