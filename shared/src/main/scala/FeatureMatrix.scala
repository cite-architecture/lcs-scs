package edu.holycross.shot.seqcomp
import scala.scalajs.js

import scala.scalajs.js.annotation._

import scala.annotation.tailrec

/** Two-dimensional matrix of features.
*
* @param features Matrix of related features, as
* Scala Options.
*/
@JSExportTopLevel("FeatureMatrix")
case class FeatureMatrix[T] (features: Vector[Vector[Option[T]]])  {
}
