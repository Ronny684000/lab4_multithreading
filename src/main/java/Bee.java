import java.util.Date;
import java.util.Random;

final class Bee implements Runnable {

    private int honeyHarvest;
    private Winnie master;
    private Hive stock;

    public Bee(int honeyHarvest, Winnie master, Hive stock) {
        this.honeyHarvest = honeyHarvest;
        this.master = master;
        this.stock = stock;
    }

    public void run() {
        while (master.winnieIsAlive()) {
            getOutOfHive();
            harvestHoney();
            getIntoHive();
        }
    }

    public void harvestHoney() {
        long delay;
        Random rnd = new Random();
        delay = Config.BEE_MIN_HONEY_HARVEST_TIME + rnd.nextInt(2000);
        try {
            Thread.sleep(delay);
            stock.getRule().lock();
            stock.increaseHoneySupply(honeyHarvest);
            System.out.println("Пчела - " + Thread.currentThread().getName() + " принесла мед: " +
                    honeyHarvest + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
            stock.getRule().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getIntoHive() {
        System.out.println("Пчела " + Thread.currentThread().getName() + " залезла в улей");
        stock.addBee();
        System.out.println(stock.getBeeCount() == 0 ? "В улье нет пчел, можно есть" : "Улей кишит пчелами");
    }

    public void getOutOfHive() {
        System.out.println("Пчела " + Thread.currentThread().getName() + " вылетела из улья");
        stock.removeBee();
        System.out.println(stock.getBeeCount() == 0 ? "В улье нет пчел, можно есть" : "Улей кишит пчелами");
    }
}
