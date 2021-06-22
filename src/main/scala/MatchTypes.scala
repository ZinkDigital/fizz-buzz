
// Do fizz-buzz in the type system using MatchTypes.

object MatchTypes {

  type FizzBuzzResult = (1, 2, "Fizz", 4, "Buzz", "Fizz", 7, 8, "Fizz",
                          "Buzz", 11, "Fizz", 13, 14, "FizzBuzz", 16)

  import compiletime._
  import compiletime.ops._
  import compiletime.ops.int._


  type Limit = 16

  type Concat[Xs <: Tuple, +Ys <: Tuple] <: Tuple = Xs match
    case EmptyTuple => Ys
    case head *: tail  => head *: Concat[tail, Ys]

  type Reverse[T <: Tuple] <: Tuple = T match
    case EmptyTuple => EmptyTuple
    case head *: tail => Concat[ Reverse[tail], Tuple1[head] ]

  type Expand[T <: Tuple, L <: Int] <: Tuple = Reverse[T] match
    case EmptyTuple => EmptyTuple
    case L *: _ => T
    case head *: _ => Expand[Concat[ T, Tuple1[head + 1] ], L ]

  // old version
  //  type FizzBuzz[T <: Tuple] <: Tuple = T match
  //    case EmptyTuple => EmptyTuple
  //    case head *: tail => head % 15 match
  //      case 0 => Concat[ Tuple1["FizzBuzz"], FizzBuzz[tail] ]
  //      case _ => head % 3 match
  //        case 0 => Concat[ Tuple1["Fizz"], FizzBuzz[tail] ]
  //        case _ => head % 5 match
  //          case 0 => Concat[ Tuple1["Buzz"], FizzBuzz[tail] ]
  //          case _ => Concat[ Tuple1[ head ], FizzBuzz[tail] ]


  type FizzBuzz[T <: Tuple] <: Tuple = T match
    case EmptyTuple => EmptyTuple
    case head *: tail => head % 15 match
      case 0              => Concat[ Tuple1["FizzBuzz"], FizzBuzz[tail] ]
      case 5 | 10         => Concat[ Tuple1["Buzz"], FizzBuzz[tail] ]
      case 3 | 6 | 9 | 12 => Concat[ Tuple1["Fizz"], FizzBuzz[tail] ]
      case _              => Concat[ Tuple1[ head ], FizzBuzz[tail] ]


  val cat2 : Concat[Tuple1[1], Tuple1[2]] = (1,2)
  val cat3 : Concat[(1,2), Tuple1[3]] = (1,2,3)
  val cat3Rev : Concat[Tuple1[1],(2,3)] = (1,2,3)
  val cat4 : Concat[(1,2), (3,4)] = (1,2,3,4)

  val rev0 : Reverse[EmptyTuple] = EmptyTuple
  val rev1 : Reverse[Tuple1[1]] = Tuple1(1)
  val rev4 : Reverse[(1,2,3,4)] = (4,3,2,1)

  val exp0 : Expand[EmptyTuple, 0] = EmptyTuple
  val exp2 : Expand[(1,2), 2] = (1,2)
  val exp3 : Expand[(1,2), 3] = (1,2,3)
  val exp16 : Expand[(1,2),Limit] = (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16)

  val fb2 : FizzBuzz[(1,2)] = (1,2)
  val fb3 : FizzBuzz[(1,2,3)] = (1,2,"Fizz")
  val fb6 : FizzBuzz[(1,2,3,4,5,6)] = (1, 2, "Fizz", 4, "Buzz", "Fizz")

  val fb4 : FizzBuzz[(1,2,3,4)] = (1,2,"Fizz",4)
  val fbexp4 : FizzBuzz[Expand[(1,2), 4]] = (1, 2, "Fizz", 4)

  val fbexp16 : FizzBuzz[Expand[(1,2), Limit]] =
    (1, 2, "Fizz", 4, "Buzz", "Fizz", 7, 8, "Fizz", "Buzz", 11, "Fizz", 13, 14, "FizzBuzz", 16)

  type proof = FizzBuzz[Expand[(1,2), Limit]] =:= FizzBuzzResult


}
