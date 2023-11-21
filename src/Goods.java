/*Goods extends Entities*/


public abstract class Goods extends GameEntities{

    protected int cost;

    public int getCost() {
        return cost;
    }

    @Override
    public void check_null() {
        if (type == null) {
            Utils.stateError();
            throw new NullPointerException("Goods should have a type.");
        }

        if (name == null) {
            Utils.stateError();
            throw new NullPointerException("Goods should have a name.");
        }
    }
}
