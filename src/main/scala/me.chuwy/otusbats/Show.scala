package me.chuwy.otusbats

trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val intShow: Show[Int] = fromJvm

  implicit val stringShow: Show[String] = fromJvm

  implicit val booleanShow: Show[Boolean] = fromJvm

  // 1.2 Instances with conditional implicit

  implicit def setShow[A](implicit ev: Show[A]): Show[Set[A]] =
    new Show[Set[A]] {
      def show(a: Set[A]): String = mkString_(a, ", ", "", "")
    }

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] =
    new Show[List[A]] {
      def show(a: List[A]): String = mkString_(a, ", ", "", "")
    }

  // 2. Summoner (apply)

  def apply[A](implicit ev: Show[A]): Show[A] = ev

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String = ev.show(a)

    def mkString_[B](begin: String, end: String, separator: String)(implicit s: Show[B], ev: A <:< List[B]): String = {
      // with `<:<` evidence `isInstanceOf` is safe!
      val casted: List[B] = a.asInstanceOf[List[B]]
      Show.mkString_(casted, separator, begin, end)
    }
  }

  /** Transform list of `A` into `String` with custom separator, beginning and
    * ending. For example: "[a, b, c]" from `List("a", "b", "c")`
    *
    * @param separator. ',' in above example
    * @param begin. '[' in above example
    * @param end. ']' in above example
    */
  def mkString_[B: Show]( list: Iterable[B], separator: String, begin: String, end: String): String = {
    list.zipWithIndex
      .collect {
        case (str, 0) => str.show
        case (str, _) => separator + str.show
      }
      .foldLeft(begin)((elem, accum) => elem + accum) + end
  }

  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = new Show[A] {
    def show(a: A): String = a.toString
  }

  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = new Show[A] {
    def show(a: A): String = f(a)
  }
}
