package me.chuwy.otusbats

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {

  // *
  def a: Int = 3

  // * -> *
  def b(a: Int): Int = ???

  // (* -> *) -> *
  def c(f: Int => Int): Int = ???

  // * -> * -> *
  def d(a: Int, b: Int): Int = ???


  val example: Option[Int] = Some(10)
  example.map(_ + 10)

  case class NeFunctor[A](a: A, counter: Int)

  implicit val neFunctorFunctor: Functor[NeFunctor] = new Functor[NeFunctor] {
    def map[A, B](fa: NeFunctor[A])(f: A => B): NeFunctor[B] =
      NeFunctor(f(fa.a), fa.counter + 1)
  }

  // a.map(f).map(indentity) === a.map(f)

}
