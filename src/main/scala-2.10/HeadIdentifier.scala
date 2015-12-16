import org.bytedeco.javacpp.opencv_core.{Mat, Rect}
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier

object HeadIdentifier {

  def findHead(image: Mat): Rect = {
    val cascadeClassifier: CascadeClassifier = new CascadeClassifier(getClass.getClassLoader.getResource("haarcascade_frontalface_alt2.xml").getPath)
    val rect : Rect = new Rect()
    cascadeClassifier.detectMultiScale(image, rect)
    rect
  }

}
