package edu.holycross.shot
import scala.scalajs.js
import js.annotation.JSExport
import scala.collection.mutable.ArrayBuffer
package seqcomp {



  @JSExport  case class SequenceComp (val s1: String, val s2: String)  {


    def  lcs : String = {
      val memo = Array.ofDim[Int](s1.size + 1  ,s2.size + 1)
      for {
          i <- s1.size - 1 to 0 by -1
          j <- s2.size - 1 to 0 by -1
        } {
         if (s1(i) == s2(j)) {
          memo(i)(j)= memo(i+1)(j+1) + 1
         } else {
           memo(i)(j) = math.max(memo(i+1)(j), memo(i)(j+1))
         }
      }

      var common = ArrayBuffer[Char]()
      var i1 = 0
      var i2 = 0
      while ((i1 < s1.size) && (i2 < s2.size)) {
        if (s1(i1) == s2(i2)) {
          common  += s1(i1)
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
      common.mkString
    }
  }



}
