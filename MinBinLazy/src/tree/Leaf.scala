package tree

class Leaf(val value: Int) extends Tree {
  
  override def toString(): String = s"$value"
}