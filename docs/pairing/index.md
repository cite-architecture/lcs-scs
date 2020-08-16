---
layout: page
title: The Pairing class
parent: "The lcs-scs library"
nav_order: 2
---

**Version 2.2.1**

# The `Pairing` class

Alignment produces a Vector of `Pairing`s.



```scala
import edu.holycross.shot.seqcomp._

val v1 = Vector(1,5,7,9,11,13,15,17,19)
val v2 = Vector(1,3,6,9,12,15,18)

val seqcomp = SequenceComp(v1,v2)
```

```scala
seqcomp.align
// res0: Vector[Pairing[Int]] = Vector(
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
