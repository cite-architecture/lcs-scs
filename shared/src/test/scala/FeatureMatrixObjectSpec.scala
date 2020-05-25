
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixObjectSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)



  "The FeatureMatrix object" should "construct a FeatureMatrix from multiple lists of data values" in {
    // Underlying type in this example is String
    val matrix =  FeatureMatrix( many)
    matrix match {
      case m: FeatureMatrix[String] => assert(true)
      case _ => fail("Wrong underlying type")
    }
  }

  it should "support organizing the resulting FeatureMatrix by either rows or columns first" in {
    val byRows =  FeatureMatrix( many)
    val byColumns =  FeatureMatrix( many, vectorsByRow = false)

    assert(byRows.rows == byColumns.columns)
    assert(byRows.columns == byColumns.rows)
  }
}
