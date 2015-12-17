import org.bytedeco.javacpp.opencv_core.{Point, Rect, _}

object NeckSizeEstimation {

  private val headsInBody = 7.5
  private val hackyPadding = 30 //face recognition only takes up a proportion of the head, add 30 at top and bottom to make up for it
  private val percentageOfNeckWidthtoDepth = 0.80

  def findNeckSizeInInches(heightInches: Double, faceRect: Rect, neckPoints: (Point, Point)) : Double = {
    val facePadding = new Rect(new Point(faceRect.tl.x, faceRect.tl.y -hackyPadding), new Point(faceRect.br.x, faceRect.br.y +hackyPadding) )

    val neckWidthPx: Int = neckPoints._2.x - neckPoints._1.x
    val headHeightPx: Int = facePadding.br.y - facePadding.tl.y

    val inchPerPx: Double = heightInches / (headsInBody * headHeightPx)

    val widthInches  = inchPerPx * neckWidthPx
    val neckdepthInches  = widthInches * percentageOfNeckWidthtoDepth

    2 * CV_PI * Math.sqrt(((widthInches / 2) * (widthInches / 2) + (neckdepthInches / 2) * (neckdepthInches / 2)) / 2)
  }

}
