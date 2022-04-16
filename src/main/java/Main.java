import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Hive stock = new Hive(Config.BEE_COUNT);

    public static void main(String[] args) {
        Winnie winnie = new Winnie(Config.WINNIE_HONEY_CONSUMING, Config.WINNIE_SPEED, stock);
        IntStream.range(0, Config.BEE_COUNT).forEach(i -> executorService.submit(new Bee(Config.BEE_HARVEST, winnie, stock)));
        executorService.submit(winnie);
        executorService.shutdown();
    }
}
