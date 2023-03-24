package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {
  
  private DataSource dataSource;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    return false;
  }

  @Override
  public List<String> getKeys() throws SQLException {
    return null;
  }

  @Override
  public String get(String key) throws SQLException {
    return null;
  }

  @Override
  public void remove(String key) throws SQLException {

  }

  @Override
  public void put(String key, String value) throws SQLException {

  }

  @Override
  public void clear() throws SQLException {

  }
}
