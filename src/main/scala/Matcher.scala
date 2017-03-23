case class Position(pattern: String, lineOffset: Int, charOffset: Int)

object Matcher {
  def matchAll(lines: Seq[String], patterns: Set[String], chunkOffset: Int): List[Position] = {
    lines.zipWithIndex.par.flatMap(i => matchAll(i._1, patterns, chunkOffset + i._2)).toList
  }

  def matchAll(text: String, patterns: Set[String], lineOffset: Int): List[Position] = {
    patterns.par.flatMap(i => matchAll(text, i, lineOffset)).toList
  }

  def matchAll(text: String, pattern: String, lineOffset: Int): List[Position] = {
    val index = text.indexOf(pattern)
    if(index == -1) List()
    else
      Position(pattern, lineOffset, index) ::
      matchAll(text.substring(index + pattern.length), pattern, lineOffset)
  }
}
