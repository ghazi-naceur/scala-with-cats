package typelevel.typeclass

class Combine {
//  def sumInts(ints: List[Int]) = ints.foldRight(0)(_ + _)
  def sumInts(ints: List[Int]): Int = ints.sum
  def concatenateStrings(strings: List[String]): String = strings.foldRight("")(_ ++ _)
  def unionLists(listOfLists: List[List[String]]): List[String] = listOfLists.foldRight(List.empty[String])(_ concat _)
  def unionSets(listOfSets: List[Set[String]]): Set[String] = listOfSets.foldRight(Set.empty[String])(_ union _)
}

trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}

object App {

  def combineAll[A](list: List[A], A: Monoid[A]): A = list.foldRight(A.empty)(A.combine)

  def main(args: Array[String]): Unit = {

    // 1- Int :
    val intMonoid: Monoid[Int] = new Monoid[Int] {
      override def empty: Int = 0
      override def combine(x: Int, y: Int): Int = x + y
    }

    val intCombine = intMonoid.combine(5, 4)
    println(intCombine)

    val intCombineAll = combineAll(List(1, 2, 3, 4, 5, 6), intMonoid)
    println(intCombineAll)

    // 2- String
    val stringMonoid: Monoid[String] = new Monoid[String] {
      override def empty: String = ""
      override def combine(x: String, y: String): String = x ++ y
    }

    val stringCombine = stringMonoid.combine("hello ", "world !")
    println(stringCombine)

    val stringCombineAll = combineAll(List("a", "b", "c", "d", "e"), stringMonoid)
    println(stringCombineAll)

    // 3- List[String]
    val listMonoid: Monoid[List[String]] = new Monoid[List[String]] {
      override def empty: List[String] = List.empty[String]
      override def combine(x: List[String], y: List[String]): List[String] = x concat y
    }

    val listCombine = listMonoid.combine(List("a", "b", "c"), List("e", "f", "g"))
    println(listCombine)

    val listCombineAll = combineAll(List(List("a", "b", "c"), List("e", "f", "g")), listMonoid)
    println(listCombineAll)

    // 4- Set
    val setMonoid: Monoid[Set[String]] = new Monoid[Set[String]] {
      override def empty: Set[String] = Set.empty[String]
      override def combine(x: Set[String], y: Set[String]): Set[String] = x union y
    }

    val setCombine = setMonoid.combine(Set("a", "b", "c"), Set("e", "f", "g"))
    println(setCombine)

    val setCombineAll = combineAll(List(Set("a", "b", "c"), Set("e", "f", "g")), setMonoid)
    println(setCombineAll)
  }
}

