package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances

  implicit val stringShow: Show[String] =
    new Show[String] {
      def show(a: String): String = a
    }

  implicit val intShow: Show[Int] =
    new Show[Int] {
      def show(a: Int): String = a.toString
    }


  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] =
    new Show[List[A]] {
      def show(as: List[A]): String = "[" ++ as.map(a => ev.show(a)).mkString(",") ++ "]"
    }


  // 2. Summoner

  def apply[A](implicit ev: Show[A]): Show[A] = ev

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =
      ev.show(a)
  }

  // 4. Helper constructors

}