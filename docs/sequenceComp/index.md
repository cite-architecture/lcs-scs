---
layout: page
title: The SequenceComp class
---

## The `SequenceComp` class

A `SequenceComp` instance compares two Vectors of ordered values of any type.  The `SequenceComp` can find their longest common subsequence and their shortest common supersequence, and can align them against each other.

```scala
import edu.holycross.shot.seqcomp._

val v1 = Vector(1,5,7,9,11,13,15,17,19)
val v2 = Vector(1,3,6,9,12,15,18)

val seqcomp = SequenceComp(v1,v2)
```




```scala
seqcomp.lcs
// res0: Vector[Int] = Vector(1, 9, 15)
```


## SCS

Order matters!  Symmetrical only if enough common points. Otherwise, `v1` is ordered first.

```scala
seqcomp.scs
// res1: Vector[Int] = Vector(1, 5, 7, 3, 6, 9, 11, 13, 15, 17, 19, 15, 18)
SequenceComp(v2, v1).scs
// res2: Vector[Int] = Vector(1, 3, 6, 5, 7, 9, 12, 15, 18, 13, 15, 17, 19)
```


## Alignment

Alignment produces a Vector of `Pairing`s.


```scala
seqcomp.align
// res3: Vector[Pairing[Int]] = Vector(
//   Pairing(Some(1), Some(1)),
//   Pairing(Some(5), None),
//   Pairing(Some(7), None),
//   Pairing(None, Some(3)),
//   Pairing(None, Some(6)),
//   Pairing(Some(9), Some(9)),
//   Pairing(Some(11), None),
//   Pairing(Some(13), None),
//   Pairing(Some(15), None),
//   Pairing(Some(17), None),
//   Pairing(Some(19), None),
//   Pairing(None, Some(12)),
//   Pairing(None, Some(15)),
//   Pairing(None, Some(18))
// )
```
