package lesson3.datedMap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("hello", "world"); // добавление новых значений
        datedMap.put("test", "world");

        System.out.println("get(\"hello\") = " + datedMap.get("hello")); // получение значения с ключом "hello"
        System.out.println("containsKey(\"hello\") = " + datedMap.containsKey("hello")); // проверка, что ключ "hello" существует
        System.out.println("getKeyLastInsertionDate(\"hello\") = " + datedMap.getKeyLastInsertionDate("hello")); // последнее время редактирования

        System.out.println("keySet() = " + datedMap.keySet());
        datedMap.remove("hello"); // удаление пары с ключом "hello"
        System.out.println("keySet() = " + datedMap.keySet()); // удаление успешно: нет ключа "hello"
        System.out.println("getKeyLastInsertionDate(\"hello\") = " + datedMap.getKeyLastInsertionDate("hello")); // удаление успешно: нет ключа "hello"
    }
}