
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class AlignmentSpec extends FlatSpec {

  "A SequenceComp" should "align pairs of objects" in {
    val sc = SequenceComp( Vector("b","c","d"), Vector("a","b","d","e"))
    val aligned = sc.align
    val expectedCex = Vector(
      "#a",
      "b#b",
      "c#",
      "d#d",
      "#e"
    )
    assert(aligned.map(_.cex) == expectedCex)
  }

}
