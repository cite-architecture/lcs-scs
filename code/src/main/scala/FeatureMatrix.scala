package edu.holycross.shot.seqcomp

//import scala.scalajs.js
import wvlet.log._

// JVM only:
import wvlet.log.LogFormatter.SourceCodeLogFormatter

//import scala.scalajs.js.annotation._
import scala.annotation.tailrec

/** Two-dimensional matrix of features, organized by row.
*
* @param features Matrix of related features, as
* Scala Options.
*/
//@JSExportTopLevel("FeatureMatrix")
case class FeatureMatrix[T] (features: Vector[Vector[Option[T]]]) { //extends LogSupport {

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

      val withRowLabels = if (rowLabels.nonEmpty) {
        require(rowLabels.size == rows, "Number of labels must equal number of rows.")
        val labelled = features.zipWithIndex.map {
          case (row, i ) => rowLabels(i) +: row.map(_.getOrElse(emptyValue).toString)
        }
        labelled

      } else {
        features.map(row => row.map(col => col.getOrElse(emptyValue).toString))
      }


      val withAllLabels = if (columnLabels.nonEmpty) {
        require(columnLabels.size == columns, "Number of labels must equal number of columns.")
        columnLabels +: withRowLabels
      } else {
        withRowLabels
      }

      withAllLabels.map(_.mkString(featureSeparator)).mkString("\n")
    }
}


object FeatureMatrix {

    /** Tabulate a matrix of features from
    * multiple lists of data values.
    *
    * @param features Two-dimensional Vector of data values.
    * Values are ordered, but lists may be of different lengths.
    * @param vectorsByRow True if resulting matrix should be
    * organized rows-first.
    */
    def apply[T](
      features: Vector[Vector[T]],
      vectorsByRow: Boolean = true
    ): FeatureMatrix[T] = {
      val superseq =  SequenceComp.scs(features)
      matrix(superseq, features, vectorsByRow, Vector(superseq.map(Some(_))))
    }


    /** Recursively tabulate a matrix of features from
    * multiple lists of data values.
    *
    * @param supersequence The shortest common supersequence of
    * all data values in features.
    * @param features Two-dimensional Vector of data values.
    * Values are ordered, but lists may be of different lengths.
    * @param vectorsByRow True if resulting matrix should be
    * organized rows-first.
    * @param compiled Cumulative tabulation so far.
    */
    def matrix[T](
      supersequence: Vector[T],
      features: Vector[Vector[T]],
      vectorsByRow: Boolean,
      compiled: Vector[Vector[Option[T]]]
    ) : FeatureMatrix[T] = {
      if (features.isEmpty) {
        if (vectorsByRow) {
          FeatureMatrix(compiled)
        } else {
          FeatureMatrix(compiled).transpose
        }

      } else {
        val pairing  = SequenceComp(supersequence, features.head).align
        val rightValues : Vector[Option[T]] =  pairing.map(_.right)
        val newColumn = if (rightValues.size  < supersequence.size) {
          val diff = supersequence.size - rightValues.size
          val addedNulls = for (i <- 1 to diff) yield { None }
          rightValues ++ addedNulls.toVector

        } else {
          rightValues
        }
        val newComposite: Vector[Vector[Option[T]]] = compiled :+ newColumn

        matrix(supersequence,  features.tail, vectorsByRow, newComposite)
      }
    }

}
