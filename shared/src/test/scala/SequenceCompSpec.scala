
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompSpec extends FlatSpec {

  "A SequenceComp" should "compute longest common sequence (LCS) for a Vector of overlapping Strings" in {
    val sc = SequenceComp(Vector("a","b","c"), Vector("b","c","d"))
    assert(sc.lcs.mkString == "bc")
  }

  it should "compute LCS for overlapping Vectors of any type of objects, such as Integers" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(2,3,4))
    assert(sc.lcs == Vector(2,3))
  }

  it should "treat Strings as Vectors of Chars" in pending

  it should "compute SCS for overlapping vectors of strings" in {
    val sc = SequenceComp(Vector("a","b","c"), Vector("b","c","d"))
    assert(sc.scs.mkString == "abcd")
  }

  it should "compute SCS for overlapping vectors of Integers" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(3,5))
    assert(sc.scs.mkString == "1235")
  }

  it should "place isolated elements of vector 1 before elements of vector 2 in computing SCS" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(1,4,5))
    assert(sc.scs.mkString == "12345")
  }

  it should "start SCS with isolated elements of v1 when they precede first overlapping element" in {
    val sc = SequenceComp(Vector(0,1,2,3), Vector(2,4,5))
    assert(sc.scs.mkString == "012345")

  }

  it should "compute SCS equivalent to vector addition when there is no overlap" in {
    val sc = SequenceComp(Vector(1,2,3), Vector(4,5))
    assert(sc.scs.mkString == "12345")
  }

  it should "append trailing elements to SCS" in {
    val v1 = Vector(1,2,3,5)
    val v2 = Vector(3,4,5,6)
    val sc = SequenceComp( v1,v2)
    assert(sc.scs.mkString == "123456")
  }

  it should "embed elements from each vector in proper location" in {
    val v1 = Vector(1,2,3,7,8,9,10)
    val v2 = Vector(3,4,5,6,7,9,11,12)
    val sc = SequenceComp( v1,v2)
    assert(sc.scs.mkString(",") == "1,2,3,4,5,6,7,8,9,10,11,12")
  }

}
