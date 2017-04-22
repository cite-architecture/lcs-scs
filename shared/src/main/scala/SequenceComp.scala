package edu.holycross.shot.seqcomp
import scala.scalajs.js
import js.annotation.JSExport
import scala.collection.mutable.ArrayBuffer


@JSExport class SequenceComp[T] (val v1: Vector[T], val v2: Vector[T])  {


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
