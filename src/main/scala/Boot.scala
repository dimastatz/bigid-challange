import scala.collection.parallel.ParSeq

// Engine
object Boot {
  // refactor it with IOC
  val inputProvider = new SimpleInputProvider()

  def main(args: Array[String]): Unit = {
    val input = inputProvider.loadInput()

    // driver
    val chars = io.Source
      .fromFile(input.inputFile)
      .getLines()
      .grouped(input.chunkSize)
      .zipWithIndex
      .toParArray
      .flatMap(i => Matcher.matchAll(i._1, input.dictionary, i._2 * input.chunkSize))
      .groupBy(i => i.pattern)
      .map(i => (i._1, i._2.map(j => (j.lineOffset, j.charOffset))))

    println(chars.mkString(System.lineSeparator()))
  }
}
