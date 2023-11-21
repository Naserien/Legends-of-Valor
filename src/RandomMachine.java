/*
* random */


import java.util.*;

public class RandomMachine{

    private final static Random random = new Random();

    public static double get0to1() {
        return random.nextDouble();
    }

    public static <T> T selectAnElement(ArrayList<T> list) {
        return selectRandomElements(list, 1).get(0);
    }

    public static <T> ArrayList<T> selectRandomElements(ArrayList<T> list, int n) {
        if (n <= 0 || list.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument for number of elements or empty list");
        }

        if (n > list.size()) {
            Utils.stateWarning();
            System.out.printf("The number n=%d is larger than the size of choices %d.\n", n, list.size());
            return new ArrayList<>(list); // Return a new list containing all elements
        }

        Set<Integer> pickedIndexes = new HashSet<>();
        ArrayList<T> randomElements = new ArrayList<>();

        while (randomElements.size() < n) {
            int randomIndex = random.nextInt(list.size());
            // Add the element to the result if it hasn't been added already
            if (pickedIndexes.add(randomIndex)) {
                randomElements.add(list.get(randomIndex));
            }
        }

        return randomElements;
    }

    public static int chooseInt(int size) {
        return random.nextInt(size);
    }

    public static <T> T RouletteSelect(HashMap<T, Double> prob) {
        double rand = RandomMachine.get0to1();
        T selection = null;

        double cumulativeProbability = 0.0;
        for (Map.Entry<T, Double> entry : prob.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (cumulativeProbability == 0)
                continue;
            selection = entry.getKey();
            if (rand < cumulativeProbability) {
                return selection;
            }
        }

        return selection;
    }

}
