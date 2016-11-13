import java.io._
import java.util.{StringTokenizer}

object PointsAndSegmentsImp {

  def countSegments(starts: List[Int], ends: List[Int], points: List[Int]): String = {

    def count(l: List[Int], point: Int, fn: (Int, Int) => Boolean, index: Int = 0): Int = l match {
      case Nil => index
      case x :: xs if fn(x, point) => count(xs, point, fn, index + 1)
      case _ => index
    }

    def countSegmentsIter(cnt: String, s: List[Int], e: List[Int], p: List[Int]): String = p match {
      case Nil => cnt
      case x :: xs => {
        val cntStarts = count(s, x, (x, point) => x <= point)
        val cntEnds = count(e, x, (x, point) => x < point)
        countSegmentsIter(cnt + (cntStarts - cntEnds) + " ", s, e, xs)
      }
    }

    countSegmentsIter("", starts.sorted, ends.sorted, points).trim()
  }

  def main(args: Array[String]): Unit = {

    new Thread(null, new Runnable() {
      def run() {
        try
          pointsSegments()

        catch {
          case e: IOException => {
          }
        }
      }
    }, "1", 1 << 26).start()
  }

  def pointsSegments() = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt
    val m: Int = scanner.nextInt

    def buildStartsEnds(n: Int, l: (List[Int], List[Int])): (List[Int], List[Int]) =
      if (n == 0) l
      else buildStartsEnds(n - 1, (scanner.nextInt :: l._1, scanner.nextInt :: l._2))

    def buildPoints(m: Int, l: List[Int]): List[Int] =
      if (m == 0) l
      else scanner.nextInt :: buildPoints(m - 1, l)

    val (starts, ends) = buildStartsEnds(n, (Nil, Nil))
    val points = buildPoints(m, Nil)

    println(countSegments(starts, ends, points))
  }

  class FastScanner(val stream: InputStream) {

    var br: BufferedReader = null
    var st: StringTokenizer = null

    try
      br = new BufferedReader(new InputStreamReader(stream))

    catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }

    def next: String = {
      while (st == null || !st.hasMoreTokens)
        try
          st = new StringTokenizer(br.readLine)

        catch {
          case e: IOException => {
            e.printStackTrace()
          }
        }
      st.nextToken
    }

    def nextInt: Int = next.toInt
  }
}
