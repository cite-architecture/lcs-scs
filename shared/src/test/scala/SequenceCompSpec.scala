
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompSpec extends FlatSpec {

  "A SequenceComp" should "compute longest common sequence (LCS) for a Vector of Strings" in {
    val sc = SequenceComp(Vector("a","b","c"), Vector("b","c","d"))
    assert(sc.lcs.mkString == "bc")
  }

  it should ", in fact, compute LCS for a Vector any type of objects, such as Integers" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(2,3,4))
    assert(sc.lcs == Vector(2,3))
  }

  it should "treat Strings as Vectors of Chars" in pending

  it should "compute SCS for strings" in {
    val sc = SequenceComp(Vector("a","b","c"), Vector("b","c","d"))
    assert(sc.scs.mkString == "abcd")
  }

  it should "compute SCS for Integers" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(3,5))
    assert(sc.scs.mkString == "1235")
  }

  it should "compute SCS when first two elements agree" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(1,4,5))
    assert(sc.scs.mkString == "12345")
  }

  it should "compute put v1 first in SCS when first two elements disagree" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(2,4,5))
    assert(sc.scs.mkString == "12345")
  }

}
