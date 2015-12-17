package size

object ZalandoSizeNormaliser {

  case class Size(neckSizeInch: (Option[Double], Option[Double]), internationalSize: String, manufacturer: Int, chestInch: (Double, Double), waist: Double)

  private val sizes: List[Size] = List(
    new Size((Some(14.6), None), "XS", 36, (25.4, 36.6), 29.9),
    new Size((Some(14.6), Some(15.3)), "S", 38, (37, 38.2), 33.1),
    new Size((Some(15.4), Some(16)), "M", 40, (38.6, 39.8), 34.6),
    new Size((Some(15.4), Some(16)), "M/L", 41, (39, 40), 35),
    new Size((Some(16.1), Some(16.8)), "L", 42, (40.2, 41.7), 36.2),
    new Size((Some(16.9), Some(17.6)), "XL", 44, (42.1, 42.9), 38.6),
    new Size((Some(17.7), Some(18.4)), "XXL", 46, (43.3, 44.5), 40.9),
    new Size((Some(18.5), Some(19)), "3XL", 48, (44.9, 46.1), 43.3),
    new Size((None, Some(19.1)), "4XL", 50, (46.5, 47.6), 45.7)
  )

  def getSize(neckSize: Double): List[Size] = {
    sizes.filter(
      s =>
        if (s.neckSizeInch._1.isDefined & s.neckSizeInch._2.isDefined) {
          neckSize >= s.neckSizeInch._1.get & neckSize <= s.neckSizeInch._2.get
        } else if (s.neckSizeInch._1.isDefined) {
          neckSize < s.neckSizeInch._1.get
        } else {
          neckSize >= s.neckSizeInch._2.get
        }
    ).collect { case s: Size => s }
  }
}
