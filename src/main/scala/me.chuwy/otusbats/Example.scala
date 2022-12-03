package me.chuwy.otusbats

import cats.Show
import cats.implicits._

object Example extends App {

  def example[A: Show, B: Show, C: Show](a: A, b: B, c: C) =
    show"A is $a, B is $b, C is $c"

  print(example(10, "30", false))
  //def example[A, B](implicit eva: Show[A], evb: Show[B]) = show"A is $eva, B is $evb"
}
