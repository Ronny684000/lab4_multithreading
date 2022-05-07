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
            System.out.println("Винни проводит разведку");
            Thread.sleep(eatingSpeed);
            stock.spendHoneySupply(honeyConsuming);
            System.out.println("Винни - " + Thread.currentThread().getName() + " съел мед: " +
                    honeyConsuming + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stock.getRule().unlock();
        }
    }

    public void runOut() {
        try {
            System.out.println("Винни проводит разведку");
            System.out.println("Винни испугался пчел и убежал");
            Thread.sleep(Config.FEAR_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(Config.WINNIE_SLEEP_TIME_BEFORE_AWAKING*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        sleep();
        while (true) {
            if (stock.getHoneyLeft() < honeyConsuming) {
                die();
                break;
            } else {
                eat();
            }
        }
    }
}
