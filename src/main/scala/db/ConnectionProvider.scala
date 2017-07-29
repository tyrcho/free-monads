package db

import java.sql.DriverManager

abstract class ConnectionProvider {
  def apply[A](f: DB[A]): A
}

object ConnectionProvider {
  def mkProvider(driver: String, url: String) =
    new ConnectionProvider {
      def apply[A](f: DB[A]): A = {
        Class.forName(driver)
        val connection = DriverManager.getConnection(url)
        try {
          f(connection)
        } finally {
          connection.close()
        }
      }
    }

  lazy val sqliteTestDB: ConnectionProvider = mkProvider("org.sqlite.JDBC", "jdbc:sqlite::memory:")
  lazy val mysqlProdDB: ConnectionProvider = mkProvider("org.gjt.mm.mysql.Driver", "jdbc:mysql://prod:3306/?user=one&password=two")
}