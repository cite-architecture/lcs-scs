package edu.holycross.shot.seqcomp
import scala.scalajs.js
import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

import scala.annotation.tailrec

/** Two-dimensional matrix of features, organized by row.
*
* @param features Matrix of related features, as
* Scala Options.
*/
@JSExportTopLevel("FeatureMatrix")
case class FeatureMatrix[T] (features: Vector[Vector[Option[T]]]) extends LogSupport {

  val columnCounts: Vector[Int] = features.map(_.size)
  require(columnCounts.distinct.size == 1, "Unbalanced matrix: rows have different numbers of columns.")

  /** Number of columns in the [[FeatureMatrix]].*/
  def columns: Int = columnCounts(0)

  /** Number of rows in the [[FeatureMatrix]].*/
  def rows: Int = features.size

  /** Create a new [[FeatureMatrix]] by transposing rows and columns. */
  def transpose: FeatureMatrix[T] = {
     val transposedFeatures = features.head.indices.map( idx => features.map(_(idx))).toVector
     FeatureMatrix(transposedFeatures)
  }


  /** Generate String view of matrix.
  *
  * @param emptyString String to use for None values.
  * @param featureSeparator String to use to separate columns.
  * @param rowLabels List of labels for rows.
  * @param columnLabels List of labels for columns.
  */
  def prettyPrint(
    emptyValue: String = "-",
    featureSeparator: String = " ",
    rowLabels: Vector[String] = Vector.empty[String],
    columnLabels: Vector[String] = Vector.empty[String]
  ) : String = {
    Logger.setDefaultLogLevel(LogLevel.DEBUG)

    val withRowLabels = if (rowLabels.nonEmpty) {
      require(rowLabels.size == rows, "Number of labels must equal number of rows.")
      val labelled = features.zipWithIndex.map {
        case (row, i ) => rowLabels(i) +: row.map(_.getOrElse(emptyValue).toString) }

    } else { features }
    debug("Rows prepended: " + withRowLabels)
    val withAllLabels = features //withRowLabels

    val stringVects = withAllLabels.map(row => row.map(col => col.getOrElse(emptyValue).toString))
    debug("stringVects: " + stringVects)
    val formatted = stringVects.map(_.mkString(featureSeparator)).mkString("\n")
    debug("Returning formatted value " + formatted)
    formatted
  }

/*
    // prepend labels for rows if any:
    val withRowLabels = if (rowLabels.nonEmpty) {
      require(rowLabels.size == rows, "Number of labels must equal number of rows.")
      val labelled = features.zipWithIndex.map {
        case (row, i ) => rowLabels(i) +: row.map(_.getOrElse(emptyValue).toString) }

    } else { features }

    // prepend labels for columns if any:
    val withAllLabels = if (columnLabels.nonEmpty) {
      withRowLabels
    } else { withRowLabels }
  */



}
