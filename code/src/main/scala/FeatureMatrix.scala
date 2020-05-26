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

  /** Retrieve value of matrix identified by
  * row, column indices.
  *
  * @param r Row index.
  * @param c Column index.
  */
  def cell(r: Int, c: Int): Option[T] = {
    features(r)(c)
  }

  /** Retrieve value of matrix identified by
  * CellIndex object.
  *
  * @param idx Type-parameterized [[CellIndex]].
  */
  def cell(idx: CellIndex[T]): Option[T] = {
    features(idx.r)(idx.c)
  }

  /** Compose text table with rows labelled.
  *
  * @param labels List of labels to use.
  * @param emptyValue String to represent None.
  */
  def labelRows(labels: Vector[String],emptyValue: String = "-") : Vector[Vector[String]] = {
    require(labels.size == rows, "Number of labels must equal number of rows.")
    val stringTable = FeatureMatrix.stringTable(features, emptyValue)
    FeatureMatrix.labelRows(stringTable, labels)
  }

  /** Compose text table with columns labelled.
  *
  * @param labels List of labels to use.
  * @param emptyValue String to represent None.
  */
  def labelColumns(labels: Vector[String],
  emptyValue: String = "-"): Vector[Vector[String]] = {
      require(labels.size == columns, "Number of labels must equal number of columns.")
      val stringTable = FeatureMatrix.stringTable(features, emptyValue)
      FeatureMatrix.labelColumns(stringTable, labels)
  }

// REWORK THIS
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
        withRowLabels
        //columnLabels +: withRowLabels
      } else {
        withRowLabels
      }

      withAllLabels.map(_.mkString(featureSeparator)).mkString("\n")
    }
}


object FeatureMatrix extends LogSupport {

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
      vectorsByRow: Boolean = true,
      includeScs: Boolean = false
    ): FeatureMatrix[T] = {
      val superseq =  SequenceComp.scs(features)
      matrix(superseq, features, vectorsByRow, includeScs, Vector(superseq.map(Some(_))))
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
      includeScs: Boolean = false,
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

        matrix(supersequence,  features.tail, vectorsByRow, includeScs, newComposite)
      }
    }


  /** Compose a table of text values for a matrix of features.
  *
  * @param featureMatrix
  * @param emptyValue
  */
  def stringTable(featureMatrix: Vector[Vector[Any]], emptyValue: String = "-") : Vector[Vector[String]] = {
    featureMatrix.map (row => row.map( cell => {
        cell match {
          case s: String => s
          case None => emptyValue
          case Some(other) => other.toString
        }
      }
    ))
  }

  /** Add column labels to a table of text strings.
  *
  * @param featureStrings Table of text strings.
  * @param labels List of labels to apply.
  */
  def labelColumns(featureStrings: Vector[Vector[String]], labels: Vector[String]): Vector[Vector[String] ] = {
    //featureStrings.zipWithIndex.map { case (row, i ) => labels(i) +: row }
    labels +: featureStrings
  }

  /** Add row labels to a table of text strings.
  *
  * @param featureStrings Table of text strings.
  * @param labels List of labels to apply.
  */
  def labelRows(featureStrings: Vector[Vector[String]], labels: Vector[String]): Vector[Vector[String] ] = {
      //labels +: featureStrings
    //val rows = stringTable(featureMatrix, emptyValue)


    debug("SRC\n" + featureStrings.mkString("\n") + "\n")

    val labelled = for ((row,i) <- featureStrings.zipWithIndex) yield {
      val labelRow = labels(i) +: row
      debug("LABEL ROW: " + labelRow)
      labelRow
    }
    //{ case (row, i ) =>  }
    labelled
    //featureStrings.map(rows => rows.map(_.mkString(separator)).mkString("\n")

  }

  /** Compose a delimited-text representation of a table of data options.
  *
  * @param featureMatrix Table of data options.
  * @param emptyValue String to use for None values.
  * @param separator String to use in separating columns of the
  * delimited-text output.
  */
  def delimited(featureMatrix: Vector[Vector[Any]], emptyValue: String = "-", separator: String = "|"): String = {
    //Logger.setDefaultLogLevel(LogLevel.DEBUG)
    val rows = stringTable(featureMatrix, emptyValue)
    rows.map(_.mkString(separator)).mkString("\n")
  }
}
