package part.i.typeclass

final case class Person(name: String, email: String)

object JsonWriterInstances {
  //  implicit val stringWriter: JsonWriter[String] =
  //    new JsonWriter[String] {
  //      override def write(value: String): Json =
  //        JsonString(value)
  //    }
  implicit val stringWriter: JsonWriter[String] =
  (value: String) => JsonString(value)

  // Defining instances in scala by creating concrete implementations of the type class and tagging them with the `implicit`
  // keyword
  implicit val personWriter: JsonWriter[Person] =
    (person: Person) => JsonObject(Map("name" -> JsonString(person.name), "email" -> JsonString(person.email)))
}
