package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)


  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] =
    ???


  // 2. Summoner (apply)

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =
      ???

    /** Transform list of `A` into `String` with custom separator, beginning and ending.
     *  For example: "[a, b, c]" from `List("a", "b", "c")`
     *
     *  @param separator. ',' in above example
     *  @param begin. '[' in above example
     *  @param end. ']' in above example
     */
    def mkString_(list: List[A], separator: String, begin: String, end: String)(implicit ev: Show[A]): String =
      ???

  }

  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = ???
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = ???

}
