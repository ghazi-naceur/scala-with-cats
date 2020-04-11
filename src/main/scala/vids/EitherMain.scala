package vids

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

import scala.util.Try

object EitherMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    type Parse[A] = Either[Throwable, A]
    def parse1(str: String): Parse[Int] = Try(str.toInt).toEither
    def parse2(str: String): Parse[Int] = Either.catchNonFatal(str.toInt)

    def parseAndAdd1(str1: String, str2: String): Parse[Int] = for {
      int1 <- parse1(str1)
      int2 <- parse1(str2)
    } yield int1 + int2

    def parseAndAdd2(str1: String, str2: String): Parse[Int] = parseAndAdd1(str1, str2).flatMap(res =>
    Either.cond(res.isInstanceOf[Int], res, new Throwable()))

    def parseAndAdd3(str1: String, str2: String): String = parseAndAdd2(str1, str2)
      .leftMap(thr => thr.getMessage)
        .fold(thr => s"$thr cannot be parsed to int", i => s"$i is an int")

    IO {
      println("Either.cond :")
      println(Either.cond("two".isInstanceOf[Int], true, new Throwable("Not an int !")))
      println(Either.cond(2.isInstanceOf[Int], true, new Throwable("Not an int !")))

      println("Either.fromOption :")
      println(Either.fromOption(None, new Throwable("It is a None")))
      println(Either.fromOption(Some(1), new Throwable("It is a None")))

      println("Either.catchNonFatal :")
      println(parse1("1"))
      println(parse1("1s"))
      println(parse2("1s"))
      println(parseAndAdd1("1", "2"))
      println(parseAndAdd1("1", "2s"))

      println("Either.flatMap :")
      println(parseAndAdd2("2", "3"))
      println(parseAndAdd2("2", "3s"))

      println("Either.leftMap().fold() :")
      println(parseAndAdd3("2", "3"))
      println(parseAndAdd3("2", "3s"))

    }.as(ExitCode.Success)
  }
}
