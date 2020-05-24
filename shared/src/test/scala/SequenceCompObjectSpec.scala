
package edu.holycross.shot.seqcomp


import org.scalatest.FlatSpec

class SequenceCompObjectSpec extends FlatSpec {

  val a = Vector("a","b","c","d","f", "g")
  val b = Vector("b","c","d", "e")
  val c = Vector("a", "c", "e", "f", "g")
  val many = Vector(a,b,c)



  "The SequenceComp object" should "compute shortest common supersequence (SCS) for a Vector of Vectors" in {
    val expected = Vector("a", "b", "c", "d", "e", "f", "g")
    assert(SequenceComp.scs(many) == expected)
  }

  it should "tabulate a matrix of relations among objects for multiple lists of type-parameterized objects" in {
    val totalScs = SequenceComp.scs(many)
    val reslt = SequenceComp.matrix(totalScs, many)
    val expected = Vector(
      "a#a##a",
      "b#b#b#",
      "c#c#c#c",
      "d#d#d#",
      "e##e#e")
    assert (reslt == expected)

  }



}
