import java.util.Date;

final class Winnie implements Runnable {

    private int honeyConsuming;
    private int eatingSpeed;
    private HoneyPot stock;
    private boolean isAlive = true;

    public Winnie(int honeyConsuming, int eatingSpeed, HoneyPot stock) {
        this.honeyConsuming = honeyConsuming;
        this.eatingSpeed = eatingSpeed;
        this.stock = stock;
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void die() {
        isAlive = false;
    }

    private void eat() {
        try {
            Thread.sleep(eatingSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(Config.WINNIE_SLEEP_TIME_BEFORE_AWAKING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        sleep();
        while (true) {
            eat();
            if (stock.getHoneyLeft() < honeyConsuming) {
                die();
                System.out.println("Винни - " + Thread.currentThread().getName() + " не хватило меда. RIP ");
                break;
            } else {
                stock.spendHoneySupply(honeyConsuming);
                System.out.println("Винни - " + Thread.currentThread().getName() + " съел мед: " +
                        honeyConsuming + " в " + new Date() + "\nТекущий запас: " + stock.getHoneyLeft());
            }
        }
    }
}
