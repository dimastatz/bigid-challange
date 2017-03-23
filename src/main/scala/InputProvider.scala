import scala.collection.immutable.HashSet

case class InputData(chunkSize: Int, inputFile: String, dictionary: HashSet[String])

trait InputProvider {
  def loadInput(): InputData
}

class SimpleInputProvider extends InputProvider {
  override def loadInput(): InputData = {
    val names = "James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel,Paul,Mark,Donald,George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose,Larry,Jeffrey,Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold,Douglas,Henry,Carl,Arthur,Ryan,Roger "
    InputData(1000, "C:/temp/bigid.txt", HashSet() ++ names.split(",").map(i => i.trim))
  }
}