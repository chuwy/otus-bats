package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(v => v)
}

object Monad {
  // Implicit Instances
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

  type RHSEither[A] = Either[_, A];
  implicit val rhsEitherMonad: Monad[RHSEither] = new Monad[RHSEither] {
    override def flatMap[A, B](fa: RHSEither[A])(f: A => RHSEither[B]): RHSEither[B] =fa match {
      case Right(value) => f(value)
      case Left(value) => Left(value)
    }

    override def point[A](a: A): RHSEither[A] = Right(a)

    override def map[A, B](fa: RHSEither[A])(f: A => B): RHSEither[B] = fa match {
      case Right(value) => point(f(value))
      case Left(value) => Left(value)
    }
  }

  type LHSEither[A] = Either[A, _];
  implicit val lhsEitherMonad: Monad[LHSEither] = new Monad[LHSEither] {
    override def flatMap[A, B](fa: LHSEither[A])(f: A => LHSEither[B]): LHSEither[B] = fa match {
      case Left(value) => f(value)
      case Right(value) => Right(value)
    }

    override def point[A](a: A): LHSEither[A] = Left(a)

    override def map[A, B](fa: LHSEither[A])(f: A => B): LHSEither[B] = fa match {
      case Left(value) => point(f(value))
      case Right(value) => Right(value)
    }
  }

  // Summoner
  def apply[F[_]](implicit v: Monad[F]): Monad[F] = v
}


