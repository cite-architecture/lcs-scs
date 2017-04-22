
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompSpec extends FlatSpec {

  "A SequenceComp" should "compute longest common sequence (LCS) for a Vector of Strings" in {
    val sc = new SequenceComp(Vector("a","b","c"), Vector("b","c","d"))
    assert(sc.lcs.mkString == "bc")
  }

  it should ", in fact, compute LCS for a Vector any type of objects, such as Integers" in {
    val sc = new SequenceComp(Vector(1,2,3), Vector(2,3,4))
    assert(sc.lcs == Vector(2,3))
  }



}
