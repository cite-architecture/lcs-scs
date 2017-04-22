package edu.holycross.shot.seqcomp
import scala.scalajs.js
import js.annotation.JSExport
import scala.collection.mutable.ArrayBuffer


/** Functions comparing pairs of vectors.
*
* @param v1 First Vector to compare.
* @param v2 Seond Vector to compare.
*/
@JSExport class SequenceComp[T] (val v1: Vector[T], val v2: Vector[T])  {



  /** Compute Longest Common Subsequence for two Vectors of objects.
  * First compares each pair of elements in the two Vectors
  * and saves the resulting counts of the lenght of common Vectors in a memoizing array,
  * then walks back through the memoizing array to recover the common values in
  * each of the two Vectors.
  *
  * Compare the textbook discussion at http://introcs.cs.princeton.edu/java/23recursion/
  * with accompanying java impementation at http://introcs.cs.princeton.edu/java/23recursion/LongestCommonSubsequence.java.html
  */
  def  lcs : Vector[T] = {
    val memo = Array.ofDim[Int](v1.size + 1, v2.size + 1)
    for {
        i <- v1.size - 1 to 0 by -1
        j <- v2.size - 1 to 0 by -1
      } {
       if (v1(i) == v2(j)) {
        memo(i)(j)= memo(i+1)(j+1) + 1
       } else {
         memo(i)(j) = math.max(memo(i+1)(j), memo(i)(j+1))
       }
    }

    var common = ArrayBuffer[T]()
    var i1 = 0
    var i2 = 0
    while ((i1 < v1.size) && (i2 < v2.size)) {
      if (v1(i1) == v2(i2)) {
        common  += v1(i1)
        i1 = i1 + 1
        i2 = i2 + 1
      } else {
        if(memo(i1 + 1)(i2) >= memo(i1)(i2 + 1)) {
          i1 = i1 + 1
        } else {
          i2 = i2 + 1
        }
      }
    }
    common.toVector
  }
}

/** Factory for making [[SequenceComp]] objects directly from
* pairs of Vectors.
*/
object SequenceComp {

  /** Create a [[SequenceComp]] from two Vectors.
  *
  * @param v1 First Vector.
  * @param v2 Second Vector.
  */
  def apply[T] (v1: Vector[T],  v2: Vector[T] ) = {
    new SequenceComp(v1, v2)
  }

}
