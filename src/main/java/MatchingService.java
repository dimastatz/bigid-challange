import contracts.*;
import contracts.Position;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public class MatchingService {
    private Settings setting;
    private IMatcher matcher;
    private IDataReader dataReader;
    private ThreadPoolExecutor executor;

    public MatchingService(IMatcher matcher,
                           IDataReader dataReader,
                           ISettingsProvider settingProvider) {
        this.matcher = matcher;
        this.dataReader = dataReader;
        this.setting = settingProvider.readSettings();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(setting.degreeOfParallelism);
    }


    public Map<String, List<Position>> execute() throws InterruptedException {
        List<Future<List<contracts.Position>>> positions = new ArrayList<>();

        while (true) {
            if (canSubmitTask()) continue;
            Chunk chunk = dataReader.readNextChunk();
            if (chunk.isEndOfFile) break;
            executor.submit(createMatchingTask(chunk));
        }

        return positions
                .stream()
                .map(this::getPosition)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Position::getWord, Collectors.mapping(p -> p, toList())));
    }

    private List<contracts.Position> getPosition(Future<List<contracts.Position>> future) {
        try {
            return future.get();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private boolean canSubmitTask() throws InterruptedException {
        if(executor.getActiveCount() >= setting.degreeOfParallelism){
            Thread.sleep(1000);
            return true;
        }
        else {
            return false;
        }
    }

    private Callable<List<contracts.Position>> createMatchingTask(Chunk chunk){
        Callable<List<contracts.Position>> task = () -> {
            return matcher.match(chunk.data, setting.dictionary, chunk.index);
        };
        return task;
    }
}


