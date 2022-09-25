package me.chuwy.otusbats

import Show._
import Monad._

object Example extends App {

  def example[A: Show, B: Show](a: A, b: B) =
    s"A is ${a.show}, B is ${b.show}"

  println(example(25, 14L :: 34L :: 16L :: Nil))
  val list: List[Int] = Nil
  println(list.show)
  val set: Set[Int] = Set()
  println(set.show)

  def zip[A[_]](a: A[_], b: A[_])(implicit ctx: Monad[A]): A[(_,_)] =
    ctx.flatMap(a)(a => ctx.map(b)((a, _)))

  println(zip(Option(5), Option("Hello")))
  println(zip(Option(5), Option.empty))

  def example[A, B](implicit eva: Show[A], evb: Show[B]) = ???

}
