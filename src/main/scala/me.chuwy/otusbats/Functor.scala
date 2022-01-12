package me.chuwy.otusbats

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  // 1. Instances
  implicit val optionFunctor: Functor[Option] =
    new Functor[Option] {
      def map[A, B](fa: Option[A])(f: A => B): Option[B] =
        ???
    }

  implicit val listFunctor: Functor[List] =
    new Functor[List] {
      def map[A, B](fa: List[A])(f: A => B): List[B] =
        ???
    }

  def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev

  // 2. Combinators

  def nested[F[_]: Functor, G[_]: Functor, A, B](fga: F[G[A]])(f: A => B): F[G[B]] =
    ???

  /// NeFunctor

}
