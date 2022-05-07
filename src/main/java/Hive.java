import java.util.concurrent.locks.ReentrantLock;

public class Hive {

    private int honeySupply = 0;

    private int beeCount;

    private ReentrantLock rule = new ReentrantLock();

    public Hive(int beeCount) {
        this.beeCount = beeCount;
    }

    public int getHoneyLeft() {
        return honeySupply;
    }

    public void increaseHoneySupply(int addition) {
        honeySupply += addition;
    }

    public void spendHoneySupply(int spending) {
        honeySupply -= spending;
    }

    public ReentrantLock getRule() {
        return rule;
    }

    public int getBeeCount() {
        return beeCount;
    }

    public void addBee() {
        beeCount++;
    }

    public void removeBee() {
        beeCount--;
    }

}







