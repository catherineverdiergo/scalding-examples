import com.twitter.scalding.{Job, Args, TextLine, Tsv}
import scala.util.matching.Regex

class TestGroupBy(args : Args) extends Job(args)  {
  val pattern = new Regex("(\\d+),\"(.*)\"")
  def parseLine(s : String) = {
    ( pattern findFirstIn s get , // 1st tuple element
      s.split(",").toList.get(1)) // 2nd tuple element
  }
  TextLine( args("input") )
  .filter('offset) { offset : Int => offset > 0 }
  .mapTo('line -> ('key, 'value)) { line : String =>
    val split = pattern.findFirstMatchIn(line).get.subgroups
    (split(0), split(1))
  }
  .write( Tsv(args("output") ) )
//  line.toLowerCase.split("\\s+") }
//  .groupBy('word) { _.size }
//  .write( Tsv( args("output") ) )  
    
}