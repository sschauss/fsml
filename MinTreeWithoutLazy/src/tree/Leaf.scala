package tree

class Leaf(val v: Int) extends Tree {

  override def toString(): String = s"$v"
  
}