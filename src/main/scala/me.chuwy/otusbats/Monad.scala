package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = flatten(map(fa)(a => f(a)))

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(a => a)
}

object Monad {
  implicit def monadOption[T]: Monad[Option] = new Monad[Option] { thisMonad =>

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

    override def point[A](a: A): Option[A] = Option(a)

    override def flatten[A](fa: Option[Option[A]]): Option[A] = fa.flatten
  }
}
