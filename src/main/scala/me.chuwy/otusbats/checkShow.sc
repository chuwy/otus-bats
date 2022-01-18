import me.chuwy.otusbats.Show
import me.chuwy.otusbats.Show._

println("Should be '1'")
val showInt = Show[Int].show(1)

val list123 = List(1,2,3)
println("should be '123'")
val showList = list123.show

println("should be '[1,2,3]'")
val customMkString = Show.mkString_(list123,",", "[", "]")

def reverseShow[A] = Show.fromFunction[A](a => a.toString.reverse)
println("Should be 'cba'")
reverseShow.show("abc")

println("should be '321'")
123.show(reverseShow)