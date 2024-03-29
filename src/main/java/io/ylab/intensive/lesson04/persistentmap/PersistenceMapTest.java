package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        persistentMap.init("testMap");
        persistentMap.put("key1", "value1");
        persistentMap.put("key2", "value2");
        persistentMap.put("key2", "value3");

        System.out.println("getKeys() = " + persistentMap.getKeys());

        System.out.println("get(\"key2\") = " + persistentMap.get("key2"));

        persistentMap.remove("key2");
        System.out.println("getKeys() = " + persistentMap.getKeys());

        persistentMap.clear();
        System.out.println("getKeys() = " + persistentMap.getKeys());
        System.out.println("get(\"key2\") = " + persistentMap.get("key2"));
        System.out.println("containsKey(\"key2\") = " + persistentMap.containsKey("key2"));
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
