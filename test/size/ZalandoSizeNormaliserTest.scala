package size

import models.Sizes
import org.scalatest.FunSuite

class ZalandoSizeNormaliserTest extends FunSuite {

  test("lowest") {
    val size: Sizes = ZalandoSizeNormaliser.getSize(14.5)
    assert(size.values.length === 1)
    assert(size.values.head.internationalSize === "XS")
  }

  test("middle") {
    val size: Sizes = ZalandoSizeNormaliser.getSize(15.4)
    assert(size.values.length === 2)
    assert(size.values.head.internationalSize === "M")
    assert(size.values(1).internationalSize === "M/L")
  }

  test("high") {
    val size: Sizes = ZalandoSizeNormaliser.getSize(19)
    assert(size.values.length === 1)
    assert(size.values.head.internationalSize === "3XL")
  }

  test("highest") {
    val size: Sizes = ZalandoSizeNormaliser.getSize(21)
    assert(size.values.length === 1)
    assert(size.values.head.internationalSize === "4XL")
  }

}
