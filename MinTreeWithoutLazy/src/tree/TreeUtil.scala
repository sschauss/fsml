package tree

object TreeUtil {

  def main(args: Array[String]) {
    val tree = new Bin(new Bin(new Leaf(1), new Bin(new Leaf(-10), new Leaf(12))), new Bin(new Leaf(-15), new Leaf(10)))
    println(tree)
    println(repMin(tree))
  }

  def repMinTrav(t: Tree): (Int, Int => Tree) = t match {
    case l: Leaf => (l.v, v => new Leaf(v))
    case t: Bin => {
      val (ml, tl) = repMinTrav(t.l)
      val (mr, tr) = repMinTrav(t.r)
      (math.min(ml, mr), v => new Bin(tl(v), tr(v)))
    }
  }

  def repMin(t: Tree) = {
    val (m, f) = repMinTrav(t)
    f(m)
  }
}