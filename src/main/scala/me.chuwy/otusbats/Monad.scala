package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(a => a)

}

object Monad {

  // instances
  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)

    override def point[A](a: A): Option[A] = Some(a)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

  }

  implicit val listMonad: Monad[List] = new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)

    override def point[A](a: A): List[A] = List(a)

    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val functon0Monad: Monad[Function0] = new Monad[Function0] {
    override def flatMap[A, B](fa: () => A)(f: A => () => B): () => B = f(fa())

    override def point[A](a: A): () => A = () => a

    override def map[A, B](fa: () => A)(f: A => B): () => B = () => f(fa())
  }

  // summoner
  implicit def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev

  // syntax
  implicit class MonadOps[F[_], A](fa: F[A]) {
    def map[B](f: A => B)(implicit ev: Monad[F]): F[B] = ev.map(fa)(f)
    def flatMap[B](f: A => F[B])(implicit ev: Monad[F]): F[B] = ev.flatMap(fa)(f)
  }

  implicit class MonadOpsRaw[A](a: A){
    def pure[F[_]](implicit ev: Monad[F]): F[A] = ev.point(a)
  }

  implicit class NestedMonadOps[F[_], A](fa: F[F[A]]) {
    def flatten(implicit ev: Monad[F]): F[A] = ev.flatten(fa)
  }

}
