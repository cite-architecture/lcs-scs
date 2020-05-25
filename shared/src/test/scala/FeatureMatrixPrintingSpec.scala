
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixPrintingSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val labels = Vector("SCS", "long", "short", "spotty")
  val matrix =  SequenceComp.matrix( many)

  "A FeatureMatrix" should  "print formatted strings" in {
    val expected = "a b c d e f g\na b c d - f g\n- b c d e - -\na - c - e f g"
    //assert(SequenceComp.matrixString(m) == expected)
    println("Vector by row (default):")
    println(matrix.prettyPrint(rowLabels = labels))
  }
}
