/**
  * Fizz Buzz Using Scala Streams
  */
object S3LazyList extends App {

    val fizz : LazyList[String] = "" #:: "" #:: "Fizz" #:: fizz
    val buzz : LazyList[String] = "" #:: "" #:: "" #:: "" #:: "Buzz" #:: buzz
    val fizzBuzz = fizz zip buzz map { (f,b) => f + b }

    val limit = 16
    val res = LazyList.range(1, limit + 1) zip fizzBuzz map {  (n, fb) => if ( fb.isEmpty ) n else fb }

    println(res.toList)

}
