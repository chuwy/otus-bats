package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = flatten(map(fa)(f))

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A]
}

object Monad {

  // Implicit instances
  implicit val monadOption: Monad[Option] = new Monad[Option] {
    override def point[A](a: A): Option[A] = Some(a)
    override def flatten[A](fa: Option[Option[A]]): Option[A] = fa.flatten
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit val monadList: Monad[List] = new Monad[List] {
    override def point[A](a: A): List[A] = List(a)
    override def flatten[A](fa: List[List[A]]): List[A] = fa.flatten
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val monadSet: Monad[Set] = new Monad[Set] {
    override def point[A](a: A): Set[A] = Set(a)
    override def flatten[A](fa: Set[Set[A]]): Set[A] = fa.flatten
    override def map[A, B](fa: Set[A])(f: A => B): Set[B] = fa.map(f)
  }

  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev

  implicit class MonadOps[F[_], A, B](fa: F[A]) {
    def flatMap(f: A => F[B])(implicit ev: Monad[F]): F[B] = ev.flatMap(fa)(f)

    def map(f: A => B)(implicit ev: Monad[F]): F[B] = ev.map(fa)(f)
  }

  implicit class NestedMonadOps[F[_], A](ffa: F[F[A]]) {
    def flatten(implicit ev: Monad[F]): F[A] = ev.flatten(ffa)
  }

  implicit class ToMonadOps[F[_], A](a: A) {
    def point(implicit ev: Monad[F]): F[A] = ev.point(a)
  }
}