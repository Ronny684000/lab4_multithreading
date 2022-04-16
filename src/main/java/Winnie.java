import java.util.Date;

final class Winnie implements Runnable {

    private int honeyConsuming;
    private int eatingSpeed;
    private Hive stock;
    private boolean isAlive = true;

    public Winnie(int honeyConsuming, int eatingSpeed, Hive stock) {
        this.honeyConsuming = honeyConsuming;
        this.eatingSpeed = eatingSpeed;
        this.stock = stock;
    }

    public boolean winnieIsAlive() {
        return isAlive;
    }

    private void die() {
        isAlive = false;
        System.out.println("Винни - " + Thread.currentThread().getName() + " не хватило меда. RIP ");
    }

    private void eat() {
        try {
            stock.getRule().lock();
            Thread.sleep(eatingSpeed);
            stock.spendHoneySupply(honeyConsuming);
            System.out.println("Винни - " + Thread.currentThread().getName() + " съел мед: " +
                    honeyConsuming + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
            stock.getRule().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitOut() {
        try {
            System.out.println("Винни испугался пчел и убежал");
            Thread.sleep(Config.FEAR_TIME);
            System.out.println("Винни вернулся");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            System.out.println("Винни спит");
            Thread.sleep(Config.WINNIE_SLEEP_TIME_BEFORE_AWAKING);
            System.out.println("Винни проснулся и решил поесть");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        sleep();
        while (true) {
            if (!stock.getRule().isLocked()) {
                if (stock.getHoneyLeft() < honeyConsuming) {
                    die();
                    break;
                } else {
                    eat();
                }
            } else {
                waitOut();
            }
        }
    }
}
