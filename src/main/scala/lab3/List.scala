package lab3

@SerialVersionUID(1)
trait List[+T] extends Serializable {
  def head: T
  def tail: List[T]
  def isEmpty: Boolean
  def including[R >: T](e: R): List[R] = new Cons(e, this)

  def foldLeft[R](z: R)(f: (R, T) => R): R = {

    def loop (xs: List[T], acc: R):R = {
      if (xs.isEmpty)
        acc
      else
        loop (xs.tail, f(acc, xs.head))
    }

    loop(this, z)
  }

  def size: Int = foldLeft(0)((acc, _) => acc + 1)

  def filter(p: T => Boolean): List[T] =
    foldLeft(List[T]())((acc, x) => if (p(x)) acc.including(x) else acc).reverse

  def map[R](f: T => R): List[R] = foldLeft(List[R]())((acc, x) => acc.including(f(x))).reverse

  // zips this list with that list
  // both lists must have the same size
  def zip[R](that: List[R]): List[(T,R)] = ???

  // tip: use StringBuilder
  def mkString(prefix: String, sep: String, suffix: String): String = {
   // val sb: StringBuilder = new StringBuilder(prefix)

    def loop(acc: StringBuilder, xs:List[T]): StringBuilder = {
      if (xs.isEmpty)
        acc
      else
        loop(acc.append(xs.head).append(sep), xs.tail)
    }

    loop(new StringBuilder(prefix), this).append(suffix).toString()
  }

  def reverse: List[T] = foldLeft(List[T]())((acc, x) => acc.including(x))

  // returns a list with first n elements
  def take(n: Int): List[T] = {
    def loop (acc: List[T], rn: Int, xs: List[T]): List[T] =
      if (rn == 0) acc else loop(acc.including(xs.head), rn - 1, xs.tail)

    if (n > this.size) throw new IllegalArgumentException
    else loop(List[T](), n, this)
  }

  // returns the same list except the first n elements
  def drop(n: Int): List[T] = ???

  def split(n: Int): (List[T], List[T]) = (this take n, this drop n)

  // sorts this list using the ordering given function f
  // for all x,y: f(x, y) < 0 iff x < y
  // tip: which sorting works particularly well for linked list?
  def sortBy(f: (T, T) => Int): List[T] = ???

  override def toString: String = mkString("[", ",", "]")
}

object List {
  def apply[T](): List[T] = Nil
}

@SerialVersionUID(1)
object Nil extends List[Nothing] {
  override def head: Nothing = throw new NoSuchElementException("Nil.empty")
  override def tail: List[Nothing] = throw new NoSuchElementException("Nil.empty")
  override def isEmpty: Boolean = true
}

@SerialVersionUID(1)
class Cons[+T](val head: T, val tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
}
