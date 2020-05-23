package edu.holycross.shot.seqcomp
import scala.scalajs.js
import js.annotation.JSExport
import scala.collection.mutable.ArrayBuffer
import scala.annotation.tailrec


// Should ensure that at leats one of left, right is not None
/**
*/
@JSExport case class Pairing[T] (left: Option[T], right: Option[T])  {

  def cex : String = {
    left.getOrElse("") + "#" + right.getOrElse("")
  }
}
