import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExamples {

    @Test
    public void transformShouldFlattenCollection() {
        List<List<String>> collection = Arrays.asList(Arrays.asList("Viktor", "Farcic"), Arrays.asList("John", "Doe", "Third"));
        List<String> expected = Arrays.asList("Viktor", "Farcic", "John", "Doe", "Third");
        List<String> newCollection = collection.stream().flatMap(e -> e.stream()).collect(Collectors.toList());
        Assertions.assertArrayEquals(expected.toArray(), newCollection.toArray());
    }
}
