package lesson3;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("hello", "world");
        datedMap.put("test", "world");
        System.out.println(datedMap.get("hello"));
        System.out.println(datedMap.containsKey("hello"));
        System.out.println(datedMap.getKeyLastInsertionDate("hello"));
        System.out.println(datedMap.keySet());
        datedMap.remove("hello");
        System.out.println(datedMap.keySet());
        System.out.println(datedMap.getKeyLastInsertionDate("hello"));
    }
}