---
layout: page
title: The SequenceComp class
---

## The `SequenceComp` class

A `SequenceComp` instance compares two Vectors of ordered values of any type.  The `SequenceComp` can find their longest common subsequence and their shortest common supersequence, and can align them against each other.

```scala mdoc:silent
import edu.holycross.shot.seqcomp._

val v1 = Vector(1,5,7,9,11,13,15,17,19)
val v2 = Vector(1,3,6,9,12,15,18)

val seqcomp = SequenceComp(v1,v2)
```




```scala mdoc
seqcomp.lcs


```


## SCS

Order matters!  Symmetrical only if enough common points. Otherwise, `v1` is ordered first.

```scala mdoc
seqcomp.scs
SequenceComp(v2, v1).scs
```


## Alignment

Alignment produces a Vector of `Pairing`s.


```scala mdoc
seqcomp.align

```
