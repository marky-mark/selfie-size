package size

import org.scalatest.FunSuite
import size.ZalandoSizeNormaliser.Size

class ZalandoSizeNormaliserTest extends FunSuite {

  test("lowest") {
    val size: List[Size] = ZalandoSizeNormaliser.getSize(14.5)
    assert(size.size === 1)
    assert(size.head.internationalSize === "XS")
  }

  test("middle") {
    val size: List[Size] = ZalandoSizeNormaliser.getSize(15.4)
    assert(size.size === 2)
    assert(size.head.internationalSize === "M")
    assert(size(1).internationalSize === "M/L")
  }

  test("high") {
    val size: List[Size] = ZalandoSizeNormaliser.getSize(19)
    assert(size.size === 1)
    assert(size.head.internationalSize === "3XL")
  }

  test("highest") {
    val size: List[Size] = ZalandoSizeNormaliser.getSize(21)
    assert(size.size === 1)
    assert(size.head.internationalSize === "4XL")
  }

}
