import org.bytedeco.javacpp.opencv_core.{Point, Rect, Mat}

object NeckService {

  def findSizeInInches(image : Mat, heightInches : Double) : Double = {
    val faceRect : Rect = FaceIdentifier.find(image)
    val neckPoints : (Point, Point) = NeckIdentifier.find(image, faceRect)
    NeckSizeEstimation.findNeckSizeInInches(heightInches, faceRect, neckPoints)
  }
}
