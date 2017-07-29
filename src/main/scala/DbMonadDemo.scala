import java.sql.Connection

import ConnectionProvider._
import DB._

import scala.io.StdIn.readLine

object DbMonadDemo {

  def myProgram(userId: String)(provider: ConnectionProvider): Unit = {
    println("Enter old password")
    val oldPwd = readLine()
    println("Enter new password")
    val newPwd = readLine()
    val changed = provider(changePwd(userId, oldPwd, newPwd))
    println(changed)
  }

  def runInTest[A](f: ConnectionProvider => A): A = f(sqliteTestDB)

  def runInProduction[A](f: ConnectionProvider => A): A = f(mysqlProdDB)

  def main(args: Array[String]): Unit = runInTest(myProgram(userId = "toto"))

  def getUserPwd(id: String): Connection => String = c => {
    ""
  }


  def setUserPwd(id: String, pwd: String): Connection => Unit = c => {
    val stmt = c.prepareStatement("update users set pwd = ? where id = ?")
    stmt.setString(1, pwd)
    stmt.setString(2, id)
    stmt.executeUpdate
    stmt.close()
  }


  def changePwd(userid: String, enterPwd: String, newPwd: String): DB[Boolean] =
    for {oldPwd <- getUserPwd(userid)
         eq <- pw(userid, enterPwd, newPwd, oldPwd)
    } yield eq

  private def pw(userid: String, enteredPwd: String, newPwd: String, oldPwd: String): DB[Boolean] =
    if (oldPwd == enteredPwd)
      setUserPwd(userid, newPwd).map(_ => true)
    else
      pureDB(false)
}
