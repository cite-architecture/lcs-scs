
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompSpec extends FlatSpec {

  "A SequenceComp" should "compute lsc" in {
    val sc = SequenceComp("abc", "bcd")
    assert (sc.s1 == "abc")
    assert(sc.s2 == "bcd")
    assert(sc.lcs == "bc")
  }



}
