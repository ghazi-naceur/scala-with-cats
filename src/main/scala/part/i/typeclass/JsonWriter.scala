package part.i.typeclass

sealed trait Json

final case class JsonObject(get: Map[String, Json]) extends Json
final case class JsonString(get: String) extends Json
final case class JsonNumber(get: Double) extends Json
case object JsonNull extends Json

// In cats, a type class is represented by a trait with at least one type parameter
// JsonWriter is a type class with Json as a type parameter ans its subtypes
trait JsonWriter[A] {
  def write(value: A): Json
}

object Json {
  // A type class interface is any functionality we expose to users.
  // Interfaces are generic methods that accept instances of the type class as implicit parameters.
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

// Json sealed trait and Json object must be in the same file

object Main {
  def main(args: Array[String]): Unit = {
    import JsonWriterInstances._
    val personJsonfied = Json.toJson(Person("isaac netero", "isaac.netero@gmail.com"))
    // The compiler will search for the appropriate implicit that handles Person class : personWriter
    // val personJsonfied = Json.toJson(Person("isaac netero", "isaac.netero@gmail.com"))(personWriter)
    println(personJsonfied)
  }
}