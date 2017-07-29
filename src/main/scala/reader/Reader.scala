package reader

case class Reader[C, A](g: C => A) {
  def apply(c: C): A = g(c)

  def map[B](f: A => B): Reader[C, B] =
    Reader(c => f(g(c)))

  def flatMap[B](f: A => Reader[C, B]): Reader[C, B] =
    Reader(c => f(g(c))(c))
}


object Reader {
  def pureReader[C, A](a: A): Reader[C, A] = Reader(_ => a)

  implicit def funToReader[C, A](read: C => A): Reader[C, A] = Reader(read)
}