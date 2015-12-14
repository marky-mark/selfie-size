import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_highgui._
import org.bytedeco.javacpp.opencv_objdetect._

object BodyDetectionApp extends App {
  //   Loader.load(classOf[org.bytedeco.javacpp.opencv_objdetect])

  val cascadeClassifier: CascadeClassifier = new CascadeClassifier(getClass.getClassLoader.getResource("haarcascade_mcs_upperbody.xml").getPath)

  val image = imread(getClass.getClassLoader.getResource("rsz_img_0587.jpg").getPath)

  val rect : Rect = new Rect()

  cascadeClassifier.detectMultiScale(image, rect)

  println(rect.x, rect.y)

  rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255))

  imwrite("target/foo.jpg", image)

}
