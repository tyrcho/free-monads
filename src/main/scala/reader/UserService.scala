package reader

object UserService {

  def findAddress(email: String) =
    for {
      user <- UserRepo.findUser(email)
      address <- AddressRepo.getAddress(user.id)
    } yield address

  def getSupervisor(userID: Int) =
    for {
      user <- UserRepo.getUser(userID)
      supervisor <- UserRepo.getUser(user.supervisorID)
    } yield supervisor
}