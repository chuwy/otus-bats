package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

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

  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] =new Show[List[A]] {
    override def show(a: List[A]): String = a.map(ev.show(_)).mkString(",")
  }
  implicit def optionShow[A](implicit ev: Show[A]): Show[Option[A]] =new Show[Option[A]] {
    override def show(a: Option[A]): String = a.map(ev.show(_)).mkString(",")
  }

  implicit def eitherShow[A:Show,B:Show]: Show[Either[A,B]] =new Show[Either[A,B]] {
    private val el: Show[A] =Show[A]
    private val er: Show[B]=Show[B]

    override def show(a: Either[A,B]): String = a match {
      case Left(l)=> el.show(l)
      case Right(r)=> er.show(r)
    }
  }

  type ShowNothing =Nothing
//  type LeftE[+A] = Either[A, ShowNothing]
//  type RightE[+B] = Either[ShowNothing, B]
  type LeftE[+A] = Left[A, ShowNothing]
  type RightE[+B] = Right[ShowNothing, B]
  implicit def eitherLShow[A:Show]: Show[LeftE[A]] =new Show[LeftE[A]] {
    private val el: Show[A] =Show[A]

    override def show(a: LeftE[A]): String = a match {
      case Left(l)=> el.show(l)
    }
  }
  implicit def eitherRShow[B:Show]: Show[RightE[B]] =new Show[RightE[B]] {
    private val er: Show[B] =Show[B]

    override def show(a: RightE[B]): String = a match {
      case Right(l)=> er.show(l)
    }
  }


  // 2. Summoner (apply)
  def apply[A](implicit ev:Show[A]):Show[A] =ev

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =ev.show(a)

    def mkString_[B](begin: String, end: String, separator: String)(implicit S: Show[B], ev: A <:< List[B]): String = {
      // with `<:<` evidence `isInstanceOf` is safe!
      if(!a.isInstanceOf[List[_]])
        throw new RuntimeException(s"${a} is not List")

      val casted: List[B] = a.asInstanceOf[List[B]]
      Show.mkString_(casted, begin, end, separator)
    }

  }

  /** Transform list of `A` into `String` with custom separator, beginning and ending.
   *  For example: "[a, b, c]" from `List("a", "b", "c")`
   *
   *  @param separator. ',' in above example
   *  @param begin. '[' in above example
   *  @param end. ']' in above example
   */
  def mkString_[A: Show](list: List[A], begin: String, end: String, separator: String): String = {
    val ev:Show[A] =Show[A]
    s"$begin${list.map(ev.show(_)).mkString(separator)}$end"
  }


  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = ???
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = ???

}
