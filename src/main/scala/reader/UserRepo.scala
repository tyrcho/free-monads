package reader

case class User(id: Int, email: String, supervisorID: Int)


trait UserRepo {
  def get(userID: Int): User

  def find(email: String): User
}

object UserRepo {

  import Repositories.userRepo

  def getUser(userID: Int) =
    userRepo.map(_.get(userID))

  def findUser(email: String) =
    userRepo.map(_.find(email))
}