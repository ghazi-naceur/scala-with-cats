package vids

import cats.Functor
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

case class Person(firstName: String, lastName: String, age: Int)

object MonadExample extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    IO {
      println("Option Functor :")
      val per = Person("isaac", "netero", 125)
      val optionOfOption = Option(Option(per))
      val lastName1 = optionOfOption.map(option => option.map(person => person.lastName))
      // To avoid mapping twice to get the last name, we can use Functor instead
      // Composing a Functor : Having an existing Functor for an Option and another Functor for an Option, and you create
      // a new Functor for an Option and an Option
      val lastName2 = Functor[Option].compose[Option].map(optionOfOption)(person => person.lastName)
      println(lastName1)
      println(lastName2)

    }.as(ExitCode.Success)
  }
}
