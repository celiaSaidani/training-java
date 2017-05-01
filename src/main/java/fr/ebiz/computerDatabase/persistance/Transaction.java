package fr.ebiz.computerDatabase.persistance;

import java.sql.Connection;

public class Transaction {
  public static ThreadLocal<Connection> TH = new ThreadLocal<>();

  public static Connection getConnetion() {
    return TH.get();
  }

  public static void set(Connection con) {
    TH.set(con);
  }

  public static void remove() {
    TH.remove();
  }

}
