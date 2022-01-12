package me.chuwy.otusbats

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  // 1. Instances
  implicit val optionFunctor: Functor[Option] =
    new Functor[Option] {
      def map[A, B](fa: Option[A])(f: A => B): Option[B] =
        fa match {
          case Some(value) => Some(f(value))
          case None => None
        }
    }

  implicit val listFunctor: Functor[List] =
    new Functor[List] {
      def map[A, B](fa: List[A])(f: A => B): List[B] =
        fa.map(f)
    }

  val e: Either[String, Int] = Right(3)

  // val list = List(Some(1), None, Some(3))
  // list.map { option => option.map(f) }
  // nested(list)(i => i.toString)

  def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev

  // 2. Combinators

  def nested[F[_]: Functor, G[_]: Functor, A, B](fga: F[G[A]])(f: A => B): F[G[B]] =
    Functor[F].map(fga) { ga =>
      Functor[G].map(ga)(f)
    }

  case class NeFunctor[A](a: A, counter: Int) {
    def map[B](f: A => B): NeFunctor[B] =
      NeFunctor(f(a), counter + 1)
  }



}
