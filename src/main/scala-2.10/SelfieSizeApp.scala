import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_highgui._

object SelfieSizeApp extends App {

  val image: Mat = imread(getClass.getClassLoader.getResource("IMG_0668.JPG").getPath)
  val resultImage: Mat = new Mat()
  image.copyTo(resultImage)

  val headRect = HeadIdentifier.findHead(image)

  val averageNeck = NeckIdentifier.findNeckAverage(image, headRect)

  println(headRect.tl().x() + " : " + headRect.tl().y())
  println(headRect.br().x() + " : " + headRect.br().y())

  rectangle(resultImage, new Point(headRect.x, headRect.y), new Point(headRect.x + headRect.width, headRect.y + headRect.height), new Scalar(0, 255))

  circle(resultImage, averageNeck._1, 5, new Scalar(0, 255, 0, 0))
  circle(resultImage, averageNeck._2, 5, new Scalar(0, 255, 0, 0))

  line(resultImage, averageNeck._1, averageNeck._2, new Scalar(255, 0, 0, 0))

  println("point a: " + averageNeck._1.x + "," + averageNeck._1.y)
  println("point b: " + averageNeck._2.x + "," + averageNeck._2.y)
  println("distance in pixels: " + (averageNeck._2.x - averageNeck._1.x))

  imwrite("target/result_kelly.jpg", resultImage)

}
