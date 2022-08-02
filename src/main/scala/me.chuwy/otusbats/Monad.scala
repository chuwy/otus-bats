package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(a => a)
}

object Monad {
  
  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev

  implicit val optMonad: Monad[Option] = new Monad[Option] {
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
    override def point[A](a: A): Option[A] = Option(a)
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit val listMonad: Monad[List] = new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
    override def point[A](a: A): List[A] = List(a)
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val setMonad: Monad[Set] = new Monad[Set] {
    override def flatMap[A, B](fa: Set[A])(f: A => Set[B]): Set[B] = fa.flatMap(f)
    override def point[A](a: A): Set[A] = Set(a)
    override def map[A, B](fa: Set[A])(f: A => B): Set[B] = fa.map(f)
  }

  implicit class MonadOpsFA[F[_], A](fa: F[A]) {
    def flatMap[B](f: A => F[B])(implicit ev: Monad[F]): F[B] = ev.flatMap(fa)(f)
    def map[B](f: A => B)(implicit ev: Monad[F]): F[B] = ev.map(fa)(f)
  }

  implicit class MonadOpsA[A](a: A) {
    def point[F[_]](implicit ev: Monad[F]): F[A] = ev.point(a)
  }

  implicit class MonadOpsFFA[F[_], A](ffa: F[F[A]]) {
    def flatten(implicit ev: Monad[F]): F[A] = ev.flatten(ffa)
  }

}
