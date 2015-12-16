import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgproc._

import scala.util.control.Breaks._

object NeckIdentifier {

  def findNeckAverage(image: Mat, headRect: Rect): (Point, Point, Point, Point) = {
    val canny: Mat = prepareImage(image)
    val bl = new Point(headRect.tl.x, headRect.br.y)
    val leftSoldierHit = findShoulder(bl, canny)
    val rightSoldierHit = findShoulder(headRect.br, canny)
    (averageLeftSide(canny), averageRightSide(canny), leftSoldierHit, rightSoldierHit)
  }

  def findShoulder(bl: Point, image: Mat): Point = {

    for (j <- bl.y() until image.size().height()) {
      if (image.col(bl.x).row(j).data().get() == -1) {
        return new Point(bl.x, j)
      }
    }

    new Point(bl.x, image.size().height())
  }

  def averageRightSide(canny: Mat): Point = {
    val rightSide = scala.collection.mutable.MutableList[Point]()

    for (j <- 575 until 680) {
      breakable {
        for (i <- 650 to 575 by -1) {
          if (canny.col(i).row(j).data().get() == -1) {
            rightSide += new Point(i, j)
            break
          }
        }
      }
    }

    val addedRight: Point = rightSide.reduce((p1, p2) => new Point(p1.x + p2.x, p1.y + p2.y))
    new Point(addedRight.x / rightSide.size, addedRight.y / rightSide.size)
  }

  def averageLeftSide(canny: Mat): Point = {

    val leftSide = scala.collection.mutable.MutableList[Point]()

    for (j <- 575 until 680) {
      breakable {
        for (i <- 375 until 450) {
          if (canny.col(i).row(j).data().get() == -1) {
            leftSide += new Point(i, j)
            break
          }
        }
      }
    }

    val addedLeft: Point = leftSide.reduce((p1, p2) => new Point(p1.x + p2.x, p1.y + p2.y))
    new Point(addedLeft.x / leftSide.size, addedLeft.y / leftSide.size)
  }

  def prepareImage(image: Mat): Mat = {
    val imageGrey: Mat = new Mat(image)

    cvtColor(image, imageGrey, COLOR_RGB2GRAY)

    val eqHist: Mat = new Mat(imageGrey)

    equalizeHist(imageGrey, eqHist)

    val gaussianBlur: Mat = new Mat(eqHist)

    GaussianBlur(imageGrey, gaussianBlur, new Size(5, 5), 0)

    val canny: Mat = new Mat(gaussianBlur)

    Canny(gaussianBlur, canny, 35, 125)
    canny
  }
}
