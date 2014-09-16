package controllers

import console.REPL
import play.api.Logger
import play.api.mvc._

import scala.tools.nsc.interpreter.Results.Success
import scala.tools.nsc.interpreter._

object Application extends Controller {


  def index = Action {
    Ok(views.html.index())
  }

  def interpret = Action(parse.text) { req =>
    val (output, result) = REPL.interpret(req.body)

    result match {
      case Success => Ok(output)
      case _ => BadRequest(output)
    }
  }
}