package battleship.game.battleship.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListOperation {

    public static <T> List<T> flatLists(List<List<T>> lists) {
        return lists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
