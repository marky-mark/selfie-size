import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgproc._
import org.bytedeco.javacpp.opencv_highgui._

object SelfieSizeApp extends App {

  val image: Mat = imread(getClass.getClassLoader.getResource("IMG_0668.JPG").getPath)

  val imageGrey: Mat = new Mat(image)

  cvtColor(image, imageGrey, COLOR_RGB2GRAY)

  imwrite("target/gray_kelly.jpg", imageGrey)

  val eqHist: Mat = new Mat(imageGrey)

  equalizeHist(imageGrey, eqHist)

  imwrite("target/hist_kelly.jpg", eqHist)

  val gaussianBlur: Mat = new Mat(eqHist)

  GaussianBlur(imageGrey, gaussianBlur, new Size(5,5), 0)

  imwrite("target/blur_kelly.jpg", gaussianBlur)

  val canny: Mat = new Mat(gaussianBlur)

  Canny(gaussianBlur, canny, 35, 125)

  imwrite("target/canny_kelly.jpg", canny)



  val mem: CvMemStorage = new CvMemStorage()
  val contours: MatVector = new MatVector()

  findContours(canny, contours, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE)

  val resultImage: Mat = new Mat(image)

  var ptr : CvSeq = new CvSeq(contours)

  for (i <- 0 until contours.sizeof()) {
    val boundbox = boundingRect(contours.get(i))

    rectangle( resultImage , new Point( boundbox.x(), boundbox.y() ),
      new Point( boundbox.x() + boundbox.width(), boundbox.y() + boundbox.height()),
      new Scalar( 0, 255, 0, 0 ), 1, 0, 0 )
  }

  imwrite("target/result_kelly.jpg", resultImage)

}
