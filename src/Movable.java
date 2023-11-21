/*movable object interface*/


public interface Movable {

    public boolean At(int r, int c);

//    public int[] Move(ArrayList<Character> legalDirections);

    public boolean updatePos(String input);
}
