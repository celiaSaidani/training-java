package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;

public class Transaction {
  public static ThreadLocal<Connection> connectionPool = new ThreadLocal<>();

  public static Connection getConnetion() {
    return connectionPool.get();
  }

  /**
   * @param con
   *          to set in connectionPool
   */
  public static void set(Connection con) {
    connectionPool.set(con);
  }

  /**
   * Remove conncetion from connectionPool.
   */
  public static void remove() {
    connectionPool.remove();
  }

}
