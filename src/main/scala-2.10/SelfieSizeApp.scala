import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgproc._
import org.bytedeco.javacpp.opencv_highgui._
import util.control.Breaks._

object SelfieSizeApp extends App {

  val image: Mat = imread(getClass.getClassLoader.getResource("IMG_0668.JPG").getPath)

  val imageGrey: Mat = new Mat(image)

  cvtColor(image, imageGrey, COLOR_RGB2GRAY)

  imwrite("target/gray_kelly.jpg", imageGrey)

  val eqHist: Mat = new Mat(imageGrey)

  equalizeHist(imageGrey, eqHist)

  imwrite("target/hist_kelly.jpg", eqHist)

  val gaussianBlur: Mat = new Mat(eqHist)

  GaussianBlur(imageGrey, gaussianBlur, new Size(5, 5), 0)

  imwrite("target/blur_kelly.jpg", gaussianBlur)

  val canny: Mat = new Mat(gaussianBlur)

  Canny(gaussianBlur, canny, 35, 125)

  imwrite("target/canny_kelly.jpg", canny)

  val resultImage: Mat = new Mat(image)

  val leftSide = scala.collection.mutable.MutableList[Point]()

  //left side
  for (j <- 575 until 680) {
    breakable {
      for (i <- 375 until 450) {
        if (canny.col(i).row(j).data().get() == -1) {
          leftSide += new Point(i, j)
          circle(resultImage, new Point(i, j), 1, new Scalar(0, 0, 255, 0))
          break
        }
      }
    }
  }

  val rightSide = scala.collection.mutable.MutableList[Point]()

  //right side
  for (j <- 575 until 680) {
    breakable {
      for (i <- 650 to 575 by -1) {
        if (canny.col(i).row(j).data().get() == -1) {
          rightSide += new Point(i, j)
          circle(resultImage, new Point(i, j), 1, new Scalar(0, 0, 255, 0))
          break
        }
      }
    }
  }

  imwrite("target/result_kelly.jpg", resultImage)

}
