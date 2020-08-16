---
layout: page
title: The Pairing class
parent: "The lcs-scs library"
nav_order: 2
---

**Version @VERSION@**

# The `Pairing` class

Alignment produces a Vector of `Pairing`s.



```scala mdoc:silent
import edu.holycross.shot.seqcomp._

val v1 = Vector(1,5,7,9,11,13,15,17,19)
val v2 = Vector(1,3,6,9,12,15,18)

val seqcomp = SequenceComp(v1,v2)
```

```scala mdoc
seqcomp.align
```
