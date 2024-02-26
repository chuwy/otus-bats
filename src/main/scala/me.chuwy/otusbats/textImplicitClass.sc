
object Shower{
  trait Show[A] {
    def show(a: A): String
  }

  def apply[A](implicit ev:Show[A]):Show[A] =ev

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val ShowInt =new Show[Int] {
    override def show(a: Int): String = a.toString
  }
  implicit val ShowBoolean =new Show[Boolean] {
    override def show(a: Boolean): String = a.toString
  }
  implicit val ShowString =new Show[String] {
    override def show(a: String): String = a
  }

  implicit def optionShow[A](implicit ev: Show[A]): Show[Option[A]] =new Show[Option[A]] {
    override def show(a: Option[A]): String = a.map(ev.show(_)).mkString(",")
  }

  implicit def eitherLShow[A:Show,B:Show]: Show[Either[A,B]] =new Show[Either[A,B]] {
    private val el: Show[A] =Shower[A]
    private val er: Show[B]=Shower[B]

    override def show(a: Either[A,B]): String = a match {
      case Left(l)=> el.show(l)
      case Right(r)=> er.show(r)
    }
  }

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =ev.show(a)
  }


  val monSome2 = Option.apply("33").show
  //val monSome2 = Some("33").show //could not find implicit value for parameter ev: Show[Some[String]]
  println(monSome2)

  val monEitherL3 = Left("44").asInstanceOf[Either[String,String]].show
  //val monEitherL3 = Left("44").show//could not find implicit value for parameter ev: Show[Left[String, Nothing]]
  val monEitherR3 = Right(44).asInstanceOf[Either[String,Int]].show
  //val monEitherR3 = Right(44).show//could not find implicit value for parameter ev:Right[Nothing, Int]

}