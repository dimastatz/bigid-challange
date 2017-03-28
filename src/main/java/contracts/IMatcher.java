package contracts;

import java.util.List;
import java.util.Set;

/**
 * Created by dima on 3/28/2017.
 */
public interface IMatcher {
    List<Position>  match(List<String> lines, Set<String> dictionary, int chunkOffset);
}
