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
            getIntoHive();
        }
    }

    public void getIntoHive() {
        try {
            stock.getRule().lock();
            stock.increaseHoneySupply(honeyHarvest);
            System.out.println("Пчела - " + Thread.currentThread().getName() + " залезла в улей и принесла мед: " +
                    honeyHarvest + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
            stock.addBee();
            System.out.println("Улей кишит пчелами");
            if (stock.getBeeCount() == 1 && master.winnieIsAlive())
                master.runOut();
        } finally {
            stock.getRule().unlock();
        }
    }

    public void getOutOfHive() {
        System.out.println("Пчела " + Thread.currentThread().getName() + " вылетела из улья");
        stock.removeBee();
        long delay;
        Random rnd = new Random();
        delay = Config.BEE_MIN_HONEY_HARVEST_TIME + rnd.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
