import me.chuwy.otusbats.Monad
import me.chuwy.otusbats.Monad._

val o1 = Monad[Option].point(1)
println("Should be Some(1)")
o1

val o2 = 2.pure[Option]
println("Should be Some(2)")
o2

val f1 = () => 1
val mf4 = f1.map(_+3)
println("Should be 4:")
mf4() //4

val f2 = () => 2
val mf5 = f2.flatMap(i => () => i + 3)
println("Should be 5:")
mf5() //5

val f3 = () => () => 3
var ff3 = f3.flatten
println("Should be 3:")
ff3() //3



