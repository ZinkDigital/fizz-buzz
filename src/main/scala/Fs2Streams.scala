

import fs2.*
import fs2.Stream._

object Fs2Streams extends App {

    val fizz : Stream[Pure,String] = Stream("","","fizz").repeat
    val buzz : Stream[Pure,String] = Stream("","","","","buzz").repeat
    val fizzBuzz  = fizz zip buzz map { (f,b) => f + b }

    val limit = 16
    val result = Stream.emits(1 to limit).zip(fizzBuzz).map
          {  (n , fb ) => if (fb.isEmpty) n else fb }
  
    println(result.compile.toList)

}
