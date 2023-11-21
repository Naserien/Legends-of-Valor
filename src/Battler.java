/*Battle interface*/

public interface Battler {

    boolean succeedInDodging();

    int getDamage();

    boolean receiveHarmAndAlive(int damage);
}
