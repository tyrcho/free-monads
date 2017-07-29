import java.sql.Connection

case class DB[A](g: Connection => A) {
  def apply(c: Connection): A = g(c)

  def map[B](f: A => B): DB[B] =
    DB(c => f(g(c)))

  def flatMap[B](f: A => DB[B]): DB[B] =
    DB(c => f(g(c))(c))
}

object DB {

  def pureDB[A](a: A): DB[A] = DB(_ => a)

  implicit def db[A](f: Connection => A): DB[A] = DB(f)
}