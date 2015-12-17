//
// This is generated code. Do not edit this file.
//

package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonOps {
  

  implicit val readsRangeSize: Reads[RangeSize] = (
    (__ \ "small").readNullable[Double] and
    (__ \ "large").readNullable[Double]
  )(RangeSize)

  implicit val writesRangeSize: Writes[RangeSize] = (
    (__ \ "small").writeNullable[Double] and
    (__ \ "large").writeNullable[Double]
  )(unlift(RangeSize.unapply))
         

       


  implicit val readsSize: Reads[Size] = (
    (__ \ "internationalSize").readNullable[String] and
    (__ \ "waist").readNullable[Double] and
    (__ \ "chestInch").readNullable[RangeSize] and
    (__ \ "neckSizeInch").readNullable[RangeSize] and
    (__ \ "manufacturer").readNullable[Int]
  )(Size)

  implicit val writesSize: Writes[Size] = (
    (__ \ "internationalSize").writeNullable[String] and
    (__ \ "waist").writeNullable[Double] and
    (__ \ "chestInch").writeNullable[RangeSize] and
    (__ \ "neckSizeInch").writeNullable[RangeSize] and
    (__ \ "manufacturer").writeNullable[Int]
  )(unlift(Size.unapply))
         

       


  implicit val readsSizes: Reads[Sizes] =
    (__ \ "values").readNullable[Seq[Size]].map(Sizes(_))

  implicit val writesSizes: Writes[Sizes] =
    (__ \ "values").writeNullable[Seq[Size]].contramap(_.values)
         

     
}
       
