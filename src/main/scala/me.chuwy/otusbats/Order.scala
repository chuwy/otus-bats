package me.chuwy.otusbats

trait Order[A] {
  def compare(x: A, y: A): Option[Boolean]
}


