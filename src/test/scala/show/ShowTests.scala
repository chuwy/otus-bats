package show

import me.chuwy.otusbats.Show._
import org.scalatest.flatspec.AnyFlatSpec

class ShowTests extends AnyFlatSpec {

  "Check int show" should "return proper string" in {
    assert(
      5.show === "5"
    )
  }

  "Check list of ints show" should "return proper string" in {
    assert(
      List(1, 2, 3).show === "1, 2, 3"
    )
  }

  "Check list of ints mkString" should "return proper string" in {
    assert(
      List(1, 2, 3).mkString_[Int]("{ ", " }", ", ") === "{ 1, 2, 3 }"
    )
  }

  "Check from function" should "return proper string" in {
    val fromFunvcList = fromFunction[List[Int]]((r) => r.toString)
    val fromFunvcInt = fromFunction[Int]((r) => r.toString)
      assert(
        fromFunvcList.show(List(1, 2, 3)) === "List(1, 2, 3)"
      )
      assert(
        fromFunvcInt.show(134) === "134"
      )
    }
}
