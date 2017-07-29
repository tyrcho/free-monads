package reader

trait Repositories {
  def userRepo: UserRepo

  def addressRepo: AddressRepo
}

object Repositories {
  val repositories = Reader[Repositories, Repositories](identity)

  val userRepo: Reader[Repositories, UserRepo] = repositories.map(_.userRepo)
  val addressRepo: Reader[Repositories, AddressRepo] = repositories.map(_.addressRepo)
}
