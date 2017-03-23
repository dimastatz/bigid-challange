val pattern = "ma"
val text = "dima dima di"

case class Position(lineOffset: Int, charOffset: Int)


def matchAll(text: String, pattern: String, lineOffset: Int): List[Position] = {
  val index = text.indexOf(pattern)
  if(index == -1) List()
  else Position(lineOffset, index) :: matchAll(text.substring(index + pattern.length), pattern, lineOffset)
}

val data = matchAll(text, pattern, 0)