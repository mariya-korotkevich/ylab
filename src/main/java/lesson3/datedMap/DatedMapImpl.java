package lesson3.datedMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    private final Map<String, DatedValue> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, new DatedValue(value, new Date()));
    }

    @Override
    public String get(String key) {
        return map.containsKey(key) ? map.get(key).value : null;
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return map.containsKey(key) ? map.get(key).date : null;
    }

    private class DatedValue{
        private String value;
        private Date date;

        public DatedValue(String value, Date date) {
            this.value = value;
            this.date = date;
        }
    }
}