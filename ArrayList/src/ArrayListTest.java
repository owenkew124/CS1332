import org.junit.Assert;

public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList();
        for (int i = 9; i >= 0; i--) {
            String input = String.format("b0%d", i);
            list.addToFront(input);
        }
        System.out.println(list.toString());
        list.addToFront("Filler1");
        System.out.println(list.toString());
        list.addToBack("Filler2");
        System.out.println(list.toString());
        list.addToBack("Filler3");
        System.out.println(list.toString());
        list.addToFront("Filler0");
        System.out.println(list.toString());
        list.addAtIndex(0, "Filler#");
        System.out.println(list.toString());
        list.addAtIndex(2, "Filler!");
        System.out.println(list.toString());
        list.addAtIndex(6, "Filler$");
        System.out.println(list.toString());
        System.out.println(list.size());
        list.removeFromFront();
        list.removeFromBack();
        System.out.println(list.toString());
        list.removeAtIndex(4);
        System.out.println(list.toString());
        list.removeAtIndex(0);
        System.out.println(list.toString());
        list.removeAtIndex(2);
        System.out.println(list.toString());
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Object[] sA = list.getBackingArray();
        System.out.println(list.size());
        for (int i = list.size(); i < actualCapacity; i++) {
            String s = (String) sA[i];
            System.out.println(i);
            System.out.println(s);
        }

    }

}
