package tree

class Bin(val l: Tree, val r: Tree) extends Tree {

  override def toString(): String = s"Bin($l, $r)"
}