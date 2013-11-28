package tree

class Bin(val left: Tree, val right: Tree) extends Tree{
	
  override def toString(): String = s"Bin($left, $right)"
}