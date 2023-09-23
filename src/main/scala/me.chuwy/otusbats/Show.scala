package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  implicit def intShow: Show[Int] = fromFunction[Int](_ => toString)
  implicit def stringShow: Show[String] = fromFunction[String](s => s)
  implicit def booleanShow: Show[Boolean] = fromFunction[Boolean](_ => toString)
  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] = fromFunction(a => a.mkString)


  // 2. Summoner (apply)

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String = ev.show(a)

    def mkString_[B](begin: String, end: String, separator: String)(implicit S: Show[B], ev: A <:< List[B]): String = {
      // with `<:<` evidence `isInstanceOf` is safe!
      val casted: List[B] = a.asInstanceOf[List[B]]
      Show.mkString_(casted, separator, begin, end)
    }

  }

  /** Transform list of `A` into `String` with custom separator, beginning and ending.
   *  For example: "[a, b, c]" from `List("a", "b", "c")`
   *
   *  @param separator. ',' in above example
   *  @param begin. '[' in above example
   *  @param end. ']' in above example
   */
  def mkString_[A: Show](list: List[A], begin: String, end: String, separator: String): String = list.mkString(begin, separator, end)


  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = _.toString
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = (a: A) => f(a)

}
