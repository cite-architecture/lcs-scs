
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixObjectSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val matrix =  FeatureMatrix( many, includeScs = true)

  val labels = Vector("SCS", "long", "short", "spotty")

  "The FeatureMatrix object" should "construct a FeatureMatrix from multiple lists of data values" in {
    // Underlying type in this example is String
    matrix match {
      case m: FeatureMatrix[String] => assert(true)
      case _ => fail("Wrong underlying type")
    }
  }

  it should "support organizing the resulting FeatureMatrix by either rows or columns first" in {
    val byRows =  FeatureMatrix(many)
    val byColumns =  FeatureMatrix( many, vectorsByRow = false)

    assert(byRows.rows == byColumns.columns)
    assert(byRows.columns == byColumns.rows)
  }


    it should "convert two-dimensional Vector of features to two-dimensional table of string values" in {
      FeatureMatrix.stringTable(matrix.features) match {
        case table: Vector[Vector[String]] => assert(true)
        case _ => fail("Did not create correct type of object.")
      }
    }

    it should "convert two-dimensional table of features to delimited-text format" in {
      val expected = "a|b|c|d|e|f|g\na|b|c|d|-|f|g\n-|b|c|d|e|-|-\na|-|c|-|e|f|g"
      val delimited = FeatureMatrix.delimited(matrix.features)
      assert(delimited == expected)
    }

    it should "create a FeatureMatrix instance from a table of data values" in {
      // Convert FeatureMatrix to table of String values:
      val stringTable = FeatureMatrix.stringTable(matrix.features)
      val stringMatrix: FeatureMatrix[String] = FeatureMatrix.fromDataTable(stringTable)
      val expectedRows = 4
      val expectedColumns = 7
      assert(stringMatrix.rows == expectedRows)
      assert(stringMatrix.columns == expectedColumns)
    }

    it should "offer a method to label rows" in  {
      val stringTable = FeatureMatrix.stringTable(matrix.features)
      val stringMatrix = FeatureMatrix.fromDataTable(stringTable)

      val labelledTable = FeatureMatrix.labelRows(stringTable, labels)
      val labelledMatrix = FeatureMatrix.fromDataTable(labelledTable)

      //println("STRINGS: \n" + stringTable.mkString("\n"))
      //println("ROWS LABELLED\n" + labelledTable.mkString("\n"))
      //println("STRINGS MATRIX:\n" + stringMatrix.features.mkString("\n"))
      //println("STRINGS MATR  r/c: " + stringMatrix.rows  + "/" + stringMatrix.columns)

      assert(stringMatrix.columns == labelledMatrix.columns - 1)
      assert(labelledTable.map(_(0)) == labels)
    }

    it should "offer a method to label columns" in {
      val stringTable = FeatureMatrix.stringTable(matrix.transpose.features)
      val stringMatrix = FeatureMatrix.fromDataTable(stringTable)

      val labelledTable = FeatureMatrix.labelColumns(stringTable, labels)
      val labelledMatrix = FeatureMatrix.fromDataTable(labelledTable)

      assert(stringMatrix.columns == labelledMatrix.columns)
      assert(labelledTable(0) == labels)

    }

}
