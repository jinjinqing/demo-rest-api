package services.common

trait FinderBy[Id, T] {
  def findById(id: Id): Option[T]
}

trait FinderExist[Id] {
  def exists(id: Id): Boolean
}

trait FinderAll[T] {
  def findAll: List[T]
}

trait Finder[Id, T] extends FinderBy[Id, T] with FinderExist[Id] with FinderAll[T]
