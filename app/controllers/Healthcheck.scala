
package controllers

import play.api.mvc.{Action, Controller}

class HealthCheck extends Controller {

  def ping = Action {
    Ok
  }

}
     
