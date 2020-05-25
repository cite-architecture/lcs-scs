package edu.holycross.shot.seqcomp
import scala.scalajs.js
import js.annotation.JSExport

import scala.annotation.tailrec

/** Two-dimensional matrix of features.
*
* @param features Matrix of related features, as
* Scala Options.
*/
@JSExport class FeatureMatrix[T] (features: Vector[Vector[Option[T]]])  {
}
