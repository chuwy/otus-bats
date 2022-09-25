package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val byteShow: Show[Byte] = v => s"$v"
  implicit val shortShow: Show[Short] = v => s"$v"
  implicit val intShow: Show[Int] = v => s"$v"
  implicit val longShow: Show[Long] = v => s"$v"
  implicit val booleanShow: Show[Boolean] = v => s"$v"
  implicit val stringShow: Show[String] = v => v

  // 1.2 Instances with conditional implicit

  implicit def listShow[A: Show]: Show[List[A]] = v => v.map(_.show).mkString(", ")
  implicit def setShow[A: Show]: Show[Set[A]] = v => v.map(_.show).mkString(", ")


  // 2. Summoner (apply)
  def apply[A](implicit v: Show[A]): Show[A] = v

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
  def mkString_[A: Show](list: List[A], begin: String, end: String, separator: String): String =
    list.map(_.show).mkString(begin, end, separator)


  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = _.toString
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = v => f(v)

}
