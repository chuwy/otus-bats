package me.chuwy.otusbats

import Show._
import Monad._

import scala.collection.mutable;

object Example {

  def example[A: Show, B: Show](a: A, b: B) =
    s"A is ${a.show}, B is ${b.show}"

  val list: List[Int] = Nil
  list.show
  val set: Set[Int] = Set()
  set.show

  def zip[A[_]](a: A[_], b: A[_])(implicit ctx: Monad[A]): A[(_,_)] =
    ctx.flatMap(a)(a => ctx.map(b)((a, _)))

  def example[A, B](implicit eva: Show[A], evb: Show[B]) = ???

}
