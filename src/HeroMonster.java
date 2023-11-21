/*abstract class
* can add more same functions in the future*/


public abstract class HeroMonster extends GameEntities implements Observer, Battler {

    public void check_null() {
        if (type == null) {
            Utils.stateError();
            throw new NullPointerException("Monster or Hero should have a type.");
        }

        if (name == null) {
            Utils.stateError();
            throw new NullPointerException("Monster or Hero should have a name.");
        }
    }
}
