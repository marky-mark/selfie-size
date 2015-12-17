package size

import models.{Sizes, RangeSize, Size}

object ZalandoSizeNormaliser {


  private val sizes: List[Size] = List(
    new Size(neckSizeInch = RangeSize(Some(14.6), None), internationalSize = "XS", manufacturer = 36, chestInch = RangeSize(Some(25.4), Some(36.6)), waist = 29.9),
    new Size(neckSizeInch = RangeSize(Some(14.6), Some(15.3)), internationalSize = "S", manufacturer = 38, chestInch = RangeSize(Some(37), Some(38.2)), waist = 33.1),
    new Size(neckSizeInch = RangeSize(Some(15.4), Some(16)),internationalSize =  "M", manufacturer = 40, chestInch = RangeSize(Some(38.6), Some(39.8)), waist = 34.6),
    new Size(neckSizeInch = RangeSize(Some(15.4), Some(16)), internationalSize = "M/L", manufacturer = 41, chestInch = RangeSize(Some(39), Some(40)), waist = 35),
    new Size(neckSizeInch = RangeSize(Some(16.1), Some(16.8)), internationalSize = "L", manufacturer = 42, chestInch = RangeSize(Some(40.2), Some(41.7)), waist = 36.2),
    new Size(neckSizeInch = RangeSize(Some(16.9), Some(17.6)), internationalSize = "XL", manufacturer = 44, chestInch = RangeSize(Some(42.1), Some(42.9)), waist = 38.6),
    new Size(neckSizeInch = RangeSize(Some(17.7), Some(18.4)), internationalSize = "XXL", manufacturer = 46, chestInch = RangeSize(Some(43.3), Some(44.5)), waist = 40.9),
    new Size(neckSizeInch = RangeSize(Some(18.5), Some(19)), internationalSize = "3XL", manufacturer = 48, chestInch = RangeSize(Some(44.9), Some(46.1)), waist = 43.3),
    new Size(neckSizeInch = RangeSize(None, Some(19.1)), internationalSize = "4XL", manufacturer = 50, chestInch = RangeSize(Some(46.5), Some(47.6)), waist = 45.7)
  )

  def getSize(neckSize: Double): Sizes = {

    val foundSizes =
    sizes.filter(
      s =>
        if (s.neckSizeInch.small.isDefined & s.neckSizeInch.large.isDefined) {
          neckSize >= s.neckSizeInch.small.get & neckSize <= s.neckSizeInch.large.get
        } else if (s.neckSizeInch.small.isDefined) {
          neckSize < s.neckSizeInch.small.get
        } else {
          neckSize >= s.neckSizeInch.large.get
        }
    ).collect { case s: Size => s }

    new Sizes(foundSizes)
  }
}
