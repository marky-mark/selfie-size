import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_highgui._

object SelfieSizeApp extends App {

  val image: Mat = imread(getClass.getClassLoader.getResource("IMG_3769.JPG").getPath)
  imwrite("target/edge_result.jpg", NeckIdentifier.prepareImage(image))

  val heightInches = 72
  val headsInBody = 7.5
  val hackyPadding = 30 //face recognition only takes up a proportion of the head, add 30 at top and bottom to make up for it

  val resultImage: Mat = new Mat()
  image.copyTo(resultImage)

  val faceRect = FaceIdentifier.findHead(image)
  val neckPoints = NeckIdentifier.findNeckAverage(image, faceRect)

  val facePadding = new Rect(new Point(faceRect.tl.x, faceRect.tl.y -hackyPadding), new Point(faceRect.br.x, faceRect.br.y +hackyPadding) )

  rectangle(resultImage, new Point(facePadding.x, facePadding.y), new Point(facePadding.x + facePadding.width, facePadding.y + facePadding.height), new Scalar(0, 255))

  circle(resultImage, neckPoints._1, 5, new Scalar(0, 255, 0, 0))
  circle(resultImage, neckPoints._2, 5, new Scalar(0, 255, 0, 0))

  line(resultImage, neckPoints._1, neckPoints._2, new Scalar(255, 0, 0, 0))

  println("neck point a: " + neckPoints._1.x + "," + neckPoints._1.y)
  println("neck point b: " + neckPoints._2.x + "," + neckPoints._2.y)
  val neckWidthPx: Int = neckPoints._2.x - neckPoints._1.x
  println("neck length in pixels: " + neckWidthPx)

  println("face " + faceRect.tl().x() + "," + faceRect.tl().y())
  println("face " + faceRect.br().x() + "," + faceRect.br().y())
  println("face height in pixels " + (faceRect.br.y - faceRect.tl.y))

  println("head " + facePadding.tl().x() + "," + facePadding.tl().y())
  println("head " + facePadding.br().x() + "," + facePadding.br().y())
  val headHeightPx: Int = facePadding.br.y - facePadding.tl.y
  println("head height in pixels " + headHeightPx)

  println("neck size " + (heightInches/(headsInBody*headHeightPx))*neckWidthPx*CV_PI + " Inches")

  imwrite("target/found_result* test .jpg", resultImage)

}
