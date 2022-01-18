package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val intShow: Show[Int] = fromJvm
  implicit val stringShow: Show[String] = s => s
  implicit val booleanShow: Show[Boolean] = v => if (v) "true" else "false"

  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] = (a: List[A]) => a.map(ev.show).mkString

  implicit def setShow[A](implicit ev: Show[A]): Show[Set[A]] = (a: Set[A]) => a.map(ev.show).mkString


  // 2. Summoner (apply)
  def apply[A](implicit ev: Show[A]) = ev


  /** Transform list of `A` into `String` with custom separator, beginning and ending.
   *  For example: "[a, b, c]" from `List("a", "b", "c")`
   *
   *  @param separator. ',' in above example
   *  @param begin. '[' in above example
   *  @param end. ']' in above example
   */
  def mkString_[A](list: List[A], separator: String, begin: String, end: String)(implicit ev: Show[A]): String =
    list.map(ev.show).mkString(begin, separator, end)

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =
      ev.show(a)
  }

  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = (a: A) => a.toString
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = (a: A) => f(a)

}
