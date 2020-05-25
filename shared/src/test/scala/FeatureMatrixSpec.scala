
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val labels = Vector("SCS", "long", "short", "spotty")
  val matrix =  SequenceComp.matrix( many)

  "A FeatureMatrix" should "do things" in {
    println(matrix.features.mkString("\n"))
    //println(matrix.features.size)
  }


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

  it should "create a new matrix by transposition" in pending

  it should "ensure that the matrix is fully populated by Options" in pending


  it should "print formatted strings" in {


    val expected = "a b c d e f g\na b c d - f g\n- b c d e - -\na - c - e f g"
    //assert(SequenceComp.matrixString(m) == expected)
    println("Vector by row (default):")
    println(matrix.prettyPrint(rowLabels = labels))
  }

  it should "be able to transpose a matrix to organize by column" in pending /* {
    val m = SequenceComp.matrix( many, vectorsByRow = false)
    println("Vector by column:")
    println(SequenceComp.matrixString(m))
  }*/

  it should "support adding labels to string output" in pending /*{
    val m = SequenceComp.matrix( many)
    println("Labelled:")
    println(SequenceComp.matrixString(m, labels = labels, featureSeparator = " | "))

    val rotated =  SequenceComp.matrix( many, vectorsByRow = false)
    println("Rotated:")
    println(rotated)
    //println(SequenceComp.matrixString(rotated, labels = labels, featureSeparator = " | "))

  }*/


}
