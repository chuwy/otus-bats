package me.chuwy.otusbats

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

object Semigroup {

  implicit val semigroupInt = new Semigroup[Int] {
    override def combine(x: Int, y: Int): Int = ???
  }
}
