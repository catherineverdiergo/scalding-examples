import com.twitter.scalding.{Job, Args, TextLine, Tsv}
import scala.util.matching.Regex


class QuestionsEcrites(args : Args) extends Job(args) {
  val pattern = new Regex("\\s+\"(.*)\": (\"(.*)\"|null).*")
  def parseLine(s : String) = {
    ( pattern findFirstIn s get , // 1st tuple element
      s.split(",").toList.get(1)) // 2nd tuple element
  }
  var uid = ""
  var txt_suffix = 0
  val outputSchema = List('uid, 'rubrique, 'teteAnalyse, 'acteurRef, 'mandatRef, 'organeRef)
      
  TextLine( args("input") )
  .filter('line) {line : String => 
      (line.indexOf("\"uid\": ") != -1) ||
      (line.indexOf("\"rubrique\": ") != -1) ||
      (line.indexOf("\"teteAnalyse\": ") != -1) ||
      (line.indexOf("\"acteurRef\": ") != -1) ||
      (line.indexOf("\"mandatRef\": ") != -1) ||
      (line.indexOf("\"texte\": ") != -1) ||
      (line.indexOf("\"organeRef\": ") != -1)
    }
  .mapTo('line -> ('uid, 'field, 'value)) { line : String =>
    val split = pattern.findFirstMatchIn(line).get.subgroups
    if (line.indexOf("\"uid\":") != -1) {
      uid = split(1).replaceAll("^\"|\"$", "")
      txt_suffix = 0
    }
    var f = split(0)
    var v = split(1)
    if (line.indexOf("\"texte\":") == -1)
      v = v.replaceAll("^\"|\"$", "")
    else {
      f = f + txt_suffix.toString()
      txt_suffix += 1
    }
    (uid, f, v)
  }
  .groupBy('uid) { group => group.pivot(('field, 'value) -> ('rubrique, 'teteAnalyse, 'acteurRef, 'mandatRef, 'organeRef, 'texte0, 'texte1)) }
  .write( Tsv(args("output"), writeHeader = true) )
}