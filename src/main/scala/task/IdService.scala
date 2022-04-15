package task

abstract class IdService {
  def generateId(): Int
}

class UUIDIdService extends IdService {
  def generateId(): Int =
    (scala.util.Random.nextFloat() * 10000).toString().split('.').apply(0).toInt
}

class StubIdService extends IdService {
  var currentIndex = 0
  def generateId(): Int = {
    val id = currentIndex
    currentIndex += 1
    id
  }
}
