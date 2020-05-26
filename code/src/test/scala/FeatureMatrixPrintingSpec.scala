
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class FeatureMatrixPrintingSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val labels = Vector("SCS", "long", "short", "spotty")
  val matrix =  FeatureMatrix( many)

  "A FeatureMatrix" should  "print formatted strings" in {
    val expected = "a b c d e f g\na b c d - f g\n- b c d e - -\na - c - e f g"
    assert(matrix.prettyPrint() == expected)
  }

  it should "allow labelling of rows" in {
    val expected = "SCS a b c d e f g\nlong a b c d - f g\nshort - b c d e - -\nspotty a - c - e f g"
    assert(matrix.prettyPrint(rowLabels = labels) == expected)
  }

  it should "allow labelling of columns" in {
    val rotated = matrix.transpose
    val expected = "SCS long short spotty\na a - a\nb b b -\nc c c c\nd d d -\ne - e e\nf f - f\ng g - g"
    val actual = rotated.prettyPrint(columnLabels = labels)
    assert(actual == expected)
  }
}
