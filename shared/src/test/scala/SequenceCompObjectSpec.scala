
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompObjectSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)
  val labels = Vector("long", "short", "spotty")

  "The SequenceComp object" should "compute shortest common supersequence (SCS) for a Vector of Vectors" in  {
    val expected = Vector("a", "b", "c", "d", "e", "f", "g")
    assert(SequenceComp.scs(many) == expected)
  }

}
