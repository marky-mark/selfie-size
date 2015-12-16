import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_highgui._

object SelfieSizeApp extends App {

  val image: Mat = imread(getClass.getClassLoader.getResource("mark1.JPG").getPath)
  imwrite("target/edge_result.jpg", NeckIdentifier.prepareImage(image))

  val heightInches = 71

  val resultImage: Mat = new Mat()
  image.copyTo(resultImage)

  val faceRect : Rect = FaceIdentifier.find(image)
  val neckPoints : (Point, Point) = NeckIdentifier.find(image, faceRect)

  rectangle(resultImage, new Point(faceRect.x, faceRect.y), new Point(faceRect.x + faceRect.width, faceRect.y + faceRect.height), new Scalar(0, 255))

  circle(resultImage, neckPoints._1, 5, new Scalar(0, 255, 0, 0))
  circle(resultImage, neckPoints._2, 5, new Scalar(0, 255, 0, 0))

  line(resultImage, neckPoints._1, neckPoints._2, new Scalar(255, 0, 0, 0))

  println("neck size " + NeckSizeEstimation.findNeckSizeInInches(heightInches, faceRect, neckPoints) + " Inches")

  imwrite("target/found_result.jpg", resultImage)

}
