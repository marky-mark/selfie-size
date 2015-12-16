import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgproc._

import scala.util.control.Breaks._

object NeckIdentifier {

  def find(image: Mat, headRect: Rect): (Point, Point) = {
    val canny: Mat = prepareImage(image)
    val bl = new Point(headRect.tl.x, headRect.br.y)
    val leftSoldierHit = findShoulder(bl, canny)
    val rightSoldierHit = findShoulder(headRect.br, canny)
    (leftSideOfNeck(canny, bl, headRect.br, leftSoldierHit),
      rightSideOfNeck(canny, bl, headRect.br, rightSoldierHit))
  }

  def findShoulder(bl: Point, image: Mat): Point = {

    for (j <- bl.y() until image.size().height()) {
      if (image.col(bl.x).row(j).data().get() == -1) {
        return new Point(bl.x, j)
      }
    }

    new Point(bl.x, image.size().height())
  }

  def rightSideOfNeck(image: Mat, bl: Point, br: Point, rightShoulder: Point): Point = {
    val rightSide = scala.collection.mutable.MutableList[Point]()

    for (j <- br.y until rightShoulder.y) {
      breakable {
        for (i <- br.x to (br.x + bl.x)/2  by -1) {
          if (image.col(i).row(j).data().get() == -1) {
            rightSide += new Point(i, j)
            break
          }
        }
      }
    }

    rightSide.get(rightSide.size/2).get
  }

  def leftSideOfNeck(canny: Mat, bl: Point, br: Point, leftShoulder: Point): Point = {

    val leftSide = scala.collection.mutable.MutableList[Point]()

    for (j <- bl.y until leftShoulder.y) {
      breakable {
        for (i <- bl.x until (br.x + bl.x)/2) {
          if (canny.col(i).row(j).data().get() == -1) {
            leftSide += new Point(i, j)
            break
          }
        }
      }
    }

    leftSide.get(leftSide.size/2).get
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
