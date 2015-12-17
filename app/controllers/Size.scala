
//
// This is generated code.
//
package controllers

import java.io.File

import cv.NeckService
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_highgui._
import play.api._
import play.api.libs.iteratee.Error
import models._
import models.JsonOps._
import play.api.mvc._
import play.api.libs.json.Json

import com.google.inject.Inject

import models._
import models.JsonOps._
import size.ZalandoSizeNormaliser

class Size extends Controller {

  def list() = Action(parse.multipartFormData) { implicit request =>
    val file = request.body.file("file").get
    val height: String = request.body.dataParts("height").head
    val mat = imread(file.filename)
    val result = NeckService.findSizeInInches(mat, height.toDouble)
    Ok(Json.toJson(ZalandoSizeNormaliser.getSize(result)))
  }

}
     
