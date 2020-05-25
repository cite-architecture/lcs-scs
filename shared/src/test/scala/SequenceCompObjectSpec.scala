
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

  it should "tabulate a matrix of relations among objects for multiple lists of type-parameterized objects" in {

    val reslt = SequenceComp.matrix( many)
    reslt match {
      case m : FeatureMatrix[String] => assert(true)
      case _ => {
        println("Something went wrong with this matrix: failed to create FeatureMatrix")
      }
    }
  }

  it should "ensure that the matrix is square" in pending /* {
    val expectedSize = SequenceComp.scs(many).size
    val reslt = SequenceComp.matrix(many)
    assert(reslt.map(_.size).distinct(0) == expectedSize)
  }
*/


  it should "print formatted strings" in pending /*{
    val m = SequenceComp.matrix( many)

    val expected = "a b c d e f g\na b c d - f g\n- b c d e - -\na - c - e f g"
    assert(SequenceComp.matrixString(m) == expected)
    println("Vector by row (default):")
    println(SequenceComp.matrixString(m))
  }
*/
  it should "be able to transpose a matrix to organize by column" in pending /* {
    val m = SequenceComp.matrix( many, vectorsByRow = false)
    println("Vector by column:")
    println(SequenceComp.matrixString(m))
  }*/

  it should "support adding labels to string output" in pending /*{
    val m = SequenceComp.matrix( many)
    println("Labelled:")
    println(SequenceComp.matrixString(m, labels = labels, featureSeparator = " | "))

    val rotated =  SequenceComp.matrix( many, vectorsByRow = false)
    println("Rotated:")
    println(rotated)
    //println(SequenceComp.matrixString(rotated, labels = labels, featureSeparator = " | "))

  }*/


}
