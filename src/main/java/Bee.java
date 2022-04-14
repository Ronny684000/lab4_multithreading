import java.util.Date;
import java.util.Random;

final class Bee implements Runnable {

    private int honeyHarvest;
    private Winnie master;
    private HoneyPot stock;

    public Bee(int honeyHarvest, Winnie master, HoneyPot stock) {
        this.honeyHarvest = honeyHarvest;
        this.master = master;
        this.stock = stock;
    }

    public void run() {
        while (master.isAlive()) {
            harvestHoney();
            stock.getRule().lock();
            try {
                stock.increaseHoneySupply(honeyHarvest);
                System.out.println("Пчелка - " + Thread.currentThread().getName() + " принесла мед: " +
                        honeyHarvest + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
            } finally {
                stock.getRule().unlock();
            }
        }
    }

    public void harvestHoney() {
        long delay;
        Random rnd = new Random();
        delay = 1000 + rnd.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
