package cv

import cv.NeckIdentifier.CannotFindNeckException
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_highgui._
import org.scalatest.FunSuite

class NeckServiceTest extends FunSuite {

  test("Should get Marks neck size") {
    val image: Mat = imread(getClass.getClassLoader.getResource("images/mark.JPG").getPath)
    val neckSizeInches = NeckService.findSizeInInches(image, 71)
    println("Neck Size inches " + neckSizeInches)
    assert(neckSizeInches > 15)
    assert(neckSizeInches < 16)
  }

  test("Should get Johns neck size 1") {
    val image: Mat = imread(getClass.getClassLoader.getResource("images/john1.JPG").getPath)
    val neckSizeInches = NeckService.findSizeInInches(image, 72)
    println("Neck Size inches " + neckSizeInches)
    assert(neckSizeInches > 17)
    assert(neckSizeInches < 18)
  }

  test("Should get Johns neck size 2") {
    val image: Mat = imread(getClass.getClassLoader.getResource("images/john2.JPG").getPath)
    val neckSizeInches = NeckService.findSizeInInches(image, 72)
    println("Neck Size inches " + neckSizeInches)
    assert(neckSizeInches > 17)
    assert(neckSizeInches < 18)
  }

  test("Should get Darrells neck size") {
    val image: Mat = imread(getClass.getClassLoader.getResource("images/darrell.JPG").getPath)
    val neckSizeInches = NeckService.findSizeInInches(image, 68.5)
    println("Neck Size inches " + neckSizeInches)
    assert(neckSizeInches > 15)
    assert(neckSizeInches < 16)
  }

  test("Should break") {
    val image: Mat = imread(getClass.getClassLoader.getResource("images/break.JPG").getPath)

    intercept[CannotFindNeckException] {
      val neckSizeInches = NeckService.findSizeInInches(image, 68.5)
    }
  }

}
