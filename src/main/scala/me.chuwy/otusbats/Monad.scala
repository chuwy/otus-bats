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
    def flatten[A](fa: Option[Option[A]]): Option[A] = fa.flatMap(a => a)
  }

  implicit def monadList: Monad[List] = new Monad[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.flatMap(a => point(f(a)))
    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
    def point[A](a: A): List[A] = List(a)
    def flatten[A](fa: List[List[A]]): List[A] = fa.flatMap(a => a)
  }

  implicit def monadEither[A] = new Monad[Either[A, *]]  {
    override def map[B, BB](fa: Either[A, B])(f: B => BB): Either[A, BB] = fa.map(f)
    def flatMap[B, BB](fa: Either[A, B])(f: B => Either[A, BB]): Either[A, BB] = fa.flatMap(f)    
    def point[B](a: B): Either[A, B] = Right(a)    
    def flatten[B](fa: Either[A, Either[A, B]]): Either[A, B] = fa.flatMap(a => a)    
  }  
}
