package tree

object TreeUtil {

  def main(args: Array[String]) {
    val tree = new Bin(new Leaf(5), new Bin(new Leaf(1), new Leaf(10)))
    println(tree)
    println(repMin(tree))
  }

  def repMinTrav(tree: Tree, m: Int): (Int, Tree) = tree match {
    case l: Leaf => (l.value, new Leaf(m))
    case t: Bin => {
      lazy val (ml, tl: Tree) = repMinTrav(t.left, mNew)
      lazy val (mr, tr: Tree) = repMinTrav(t.right, mNew)
      lazy val mNew: Int = math.min(ml, mr)
      (mNew, new Bin(tl, tr))
    }
  }

  def repMin(tree: Tree) = {
    lazy val (m: Int, t) = repMinTrav(tree, m)
    t
  }
}