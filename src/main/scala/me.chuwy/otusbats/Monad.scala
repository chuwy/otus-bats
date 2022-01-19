package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A]
}

object Monad {

  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev
  
  implicit def monadOption: Monad[Option] = new Monad[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.flatMap(a => point(f(a)))
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
    def point[A](a: A): Option[A] = Option(a)
    def flatten[A](fa: Option[Option[A]]): Option[A] = fa.flatMap(a => a.map(b => b))
  }
  
}
