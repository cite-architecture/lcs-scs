
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val labels = Vector("SCS", "long", "short", "spotty")
  val matrix =  FeatureMatrix( many)

  "A FeatureMatrix" should "have a two-dimensional Vector of type-parameterized Options" in {
    // Test example uses Strings as underlying type:
    matrix.features match {
      case m: Vector[Vector[Option[String]]] => assert(true)
      case _ => fail("Something went wrong: wrong type for matrix.")
    }
  }

  it should "make inclusion of computed SCS option" in pending

  it should "count rows" in {
    // 3 rows of source data plus automatically built SCS
    val expectedRows = 4
    assert(matrix.rows == expectedRows)
  }

  it should "count columns" in {
    // a - g
    val expectedColumns = 7
    assert(matrix.columns == expectedColumns)
  }

  it should "create a new matrix by transposition" in {
    val rotated = matrix.transpose
    assert(rotated.rows == matrix.columns)
    assert(rotated.columns == matrix.rows)
  }

  it should "support selecting value by r,c reference" in {
    val expected = Some("a")
    assert(matrix.cell(0,0) == expected)
  }

  it should "select values by CellIndex" in {
    val ci: CellIndex[String] = CellIndex(0,0)
    val expected = Some("a")
    assert(matrix.cell(ci) == expected)
  }

  it should "create a string table" in {
    val strTable = matrix.stringTable()
    strTable match {
      case t: Vector[Vector[String]] => assert(true)
      case _ => fail("Did not create correct type of table.")
    }
  }

  it should "create a string table with rows labelled" in {
    val labelledRows = matrix.labelRows(labels)
    // check size by making a new Matrix from it:
    val labelledMatrix = FeatureMatrix.fromDataTable(labelledRows)
    assert(matrix.columns == labelledMatrix.columns - 1)
    assert(matrix.rows == labelledMatrix.rows)
  }


  it should "create a string table with columns labelled" in {
    val labelledCols = matrix.transpose.labelColumns(labels)
    // check size by making a new Matrix from it:
    val labelledMatrix = FeatureMatrix.fromDataTable(labelledCols)
    assert(matrix.transpose.columns == labelledMatrix.columns)
    assert(matrix.transpose.rows == labelledMatrix.rows - 1)
  }

  it should "format a delimited-text version of the table" in {
    val actual = matrix.delimited()
    val expected = "a|b|c|d|e|f|g\na|b|c|d|-|f|g\n-|b|c|d|e|-|-\na|-|c|-|e|f|g"
    assert(actual == expected)
  }

  it should "format a markdown version of the table" in {
    val actual = matrix.markdown(labels)
    val expected = "|  | 0 | 1 | 2 | 3 | 4 | 5 | 6 |\n|  ---  |  ---  |  ---  |  ---  |  ---  |  ---  |  ---  |  ---  |\n| **SCS** | a | b | c | d | e | f | g |\n| **long** | a | b | c | d | - | f | g |\n| **short** | - | b | c | d | e | - | - |\n| **spotty** | a | - | c | - | e | f | g |"
    assert(actual == expected)
  }

  it should "create a list of indices for a given value" in pending

  it should "find diagonally adjacent values" in pending

}
