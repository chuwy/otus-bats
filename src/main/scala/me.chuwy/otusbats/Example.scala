package me.chuwy.otusbats


object Example extends App{
  import Show._

//  def example[A: Show, B: Show](a: A, b: B) =
//    show"A is $a, B is $b"
//
//  def example[A, B](implicit eva: Show[A], evb: Show[B]) = ???

  val showInt =20.show
  println(showInt == "20")

  val mon1 =List("1","2","3").mkString_[String]("[","]",",")
  println(mon1)
  assert(mon1 == "[1,2,3]")

  val monSome2 = Option.apply("33").show
  println(monSome2)

  //val monEitherL3 = Left("44").asInstanceOf[Either[String,String]].show
  val monEitherL3 = Left("44").show
  val monEitherR3 = Right(44).show
  println(monEitherL3 == monEitherR3)
}
