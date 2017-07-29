package reader

object ReaderDemo extends App {
  val findAddress = UserService.findAddress("alice@gmail.com")

  val repositories = new Repositories {
    def userRepo = new UserRepo {
      override def find(email: String): User = User(1, "alice@gmail.com", 0)

      override def get(userID: Int): User = User(1, "alice@gmail.com", 0)
    }

    def addressRepo = (_: Int) => Address("alice@gmail.com")
  }

  val address: Address = findAddress(repositories)

  println(address)
}
