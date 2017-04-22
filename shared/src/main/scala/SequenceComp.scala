package edu.holycross.shot.seqcomp
import scala.scalajs.js
import js.annotation.JSExport
import scala.collection.mutable.ArrayBuffer


/** A class for comparing pairs of vectors.
*
* @param v1 First Vector to compare.
* @param v2 Second Vector to compare.
*/
@JSExport class SequenceComp[T] (val v1: Vector[T], val v2: Vector[T])  {


  /** Compares each pair of elements in the two Vectors
  * and saves the resulting counts of the lenght of common Vectors in a memoizing array.
  */
  def memo = {
    val memoized = Array.ofDim[Int](v1.size + 1, v2.size + 1)
    for {
        i <- v1.size - 1 to 0 by -1
        j <- v2.size - 1 to 0 by -1
      } {
       if (v1(i) == v2(j)) {
        memoized(i)(j)= memoized(i+1)(j+1) + 1
       } else {
         memoized(i)(j) = math.max(memoized(i+1)(j), memoized(i)(j+1))
       }
    }
    memoized
  }

  /** Compute Longest Common Subsequence for two Vectors of objects.
  * Walk back through the memoizing array to recover the common values in
  * each of the two Vectors.
  *
  * Compare the textbook discussion at http://introcs.cs.princeton.edu/java/23recursion/
  * with accompanying java impementation at http://introcs.cs.princeton.edu/java/23recursion/LongestCommonSubsequence.java.html
  */
  def  lcs : Vector[T] = {
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

  // tag for tail recursion
  def  insertT(src1: Vector[T], src2: Vector[T],overlap: Vector[T], mashup: ArrayBuffer[T]) : Vector[T] = {
    println(s"""INSERTING: ${src1}, ${src2}, ${overlap}, ${mashup}""")
    if (overlap.size == 0){
      mashup.toVector  ++ src1 ++ src2
    } else {
      if ((src1(0) == overlap(0)) && (src2(0) == overlap(0))) { // common
        println("COMMON: keep " + overlap(0))
        if (overlap.size == 1) {
          val mashed = mashup += overlap(0)
          mashed.toVector  ++ src1.drop(1) ++ src2.drop(1)
        }  else {
          insertT(src1.drop(1), src2.drop(1),overlap.drop(1), mashup)
        }
      } else if (src1(0)== overlap(0)){
        println("V1 matches: ADD " + src2(0))
        if (overlap.size == 1) {
          mashup.toVector ++ src1.drop(1) ++ src2.drop(1)
        } else {
          insertT(src1.drop(1), src2.drop(1),overlap.drop(1), mashup += src2(0))
        }
      } else { //} if (src2(0) == overlap(0)){
        println("V2 matches: ADD " + src1(0))
        if (overlap.size == 1) {
          mashup.toVector ++ src1.drop(1) ++ src2.drop(1)
        } else {
          insertT(src1.drop(1), src2.drop(1),overlap.drop(1), mashup += src1(0))
        }
      }
      /* else {
        println("NEITHER matches??, aod add: " + src1(0) + " and " + src2(0))

        insertT(src1.drop(1), src2.drop(1),overlap.drop(1), mashup += src1(0) += src2(0))
      }*/
    }
    //mashup.toVector ++ src1 ++ src2
  }

  def  scs: Vector[T] = {
    println(s"""Create scs by inserting from ${v1}, ${v2} into ${lcs}""")

    insertT(v1,v2, lcs,ArrayBuffer[T]())
    //val shortest = lcs
    /*
    var common = ArrayBuffer[T]()
    var i1 = 0
    var i2 = 0
    while ((i1 < v1.size) && (i2 < v2.size)) {
      println(s"""Cf ${v1(i1)} at ${i1}, ${v2(i2)} at ${i2}""" )
      if (v1(i1) == v2(i2)) {
        common  += v1(i1)
        println("Add match to common " + common)
        i1 = i1 + 1
        i2 = i2 + 1
      } else {
        if(memo(i1 + 1)(i2) >= memo(i1)(i2 + 1)) {
          common += v1(i1)
          i1 = i1 + 1

          println("Advance row to common " + common)

        } else {
          common += v2(i2)
          i2 = i2 + 1

          println("Advance col to common " + common)

        }
      }
    }
    common.toVector*/
  }



  def seeRow(i: Integer) = {
    print(v1(i) + "=>")
    for (j <- 0 to v2.size - 1) {
     print (v2(j) + ":" + memo(i)(j) + ", ")
    }

    println(memo(i)(v2.size))
  }
  def seeMemo = {
    for (i <- 0 to v1.size - 1) {
      seeRow(i)
    }
    print(" =>")
    for (j <- 0 to v2.size - 1) { print(" :" + memo(v1.size)(j) + ", ") }
    println(memo(v1.size)(v2.size))
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
