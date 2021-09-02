package me.chuwy.otusbats

trait Monoid[A] extends Semigroup[A] {
  def zero: A
}

object Monoid {
}
