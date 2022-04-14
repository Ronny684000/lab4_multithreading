import java.util.concurrent.locks.ReentrantLock;

public class HoneyPot {

    private int honeySupply = 0;
    private ReentrantLock rule = new ReentrantLock();

    public ReentrantLock getRule() {
        return rule;
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
}






