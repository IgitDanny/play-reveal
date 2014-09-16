package console

import java.io.PrintWriter

import play.api.Play
import play.api.Play.current

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.Results._
import scala.tools.nsc.interpreter.Results.Result
import scala.util.Try

object REPL {

  def interpret(code:String) : (String, Result) = {

    val out = new java.io.StringWriter()
    val flusher = new PrintWriter(out)

    try {

      val settings = buildSettings()

      val repl = new IMain(settings, flusher)

      // we want to interpret line by line
      val split = code.split("\n\r|\n|\r")

      val results = split.map(repl.interpret)
      flusher.flush()

      val successFull = results.forall {
        case Success => true
        case _ => false
      }
      if (successFull)
        (out.toString, Success)
      else
        (out.toString, Error)

    } finally {
      Try(flusher.close())
      Try(out.close())
    }
  }

  def buildSettings(): Settings = {
    val settings = new Settings

    // get the correct classpath...
    val cl = settings.getClass.getClassLoader // or getClassLoader.getParent, or one more getParent...

    val urls = cl match {
      case cl: java.net.URLClassLoader => cl.getURLs.toList
      case a => sys.error("oops: I was expecting an URLClassLoader, foud a " + a.getClass)
    }
    val classpath = urls map {_.toString}

    settings.classpath.value = classpath.distinct.mkString(java.io.File.pathSeparator)
    settings.embeddedDefaults(cl) // or getClass.getClassLoader

    settings
  }
}