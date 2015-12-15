import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgproc._
import org.bytedeco.javacpp.opencv_highgui._
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier
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

  val addedLeft : Point = leftSide.reduce((p1, p2) => new Point(p1.x + p2.x, p1.y + p2.y))

  val averageLeft = new Point(addedLeft.x/leftSide.size, addedLeft.y/leftSide.size)

  circle(resultImage, averageLeft, 5, new Scalar(0, 255, 0, 0))

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

  val addedRight : Point = rightSide.reduce((p1, p2) => new Point(p1.x + p2.x, p1.y + p2.y))
  val averageRight = new Point(addedRight.x/rightSide.size, addedRight.y/rightSide.size)

  circle(resultImage, averageRight, 5, new Scalar(0, 255, 0, 0))

  line(resultImage, averageLeft, averageRight, new Scalar(255, 0, 0, 0))

  println("distance in pixels: " + (averageRight.x - averageLeft.x))


  /////

  val cascadeClassifier: CascadeClassifier = new CascadeClassifier(getClass.getClassLoader.getResource("haarcascade_frontalface_alt2.xml").getPath)

  val rect : Rect = new Rect()

  cascadeClassifier.detectMultiScale(image, rect)

  println(rect.x, rect.y)

  rectangle(resultImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255))

  ////

  imwrite("target/result_kelly.jpg", resultImage)

}
