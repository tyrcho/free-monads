package reader


trait AddressRepo {
  def get(userID: Int): Address
}

object AddressRepo {

  import Repositories.addressRepo

  def getAddress(userID: Int) =
    addressRepo.map(_.get(userID))
}

case class Address(email:String)
