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

}
