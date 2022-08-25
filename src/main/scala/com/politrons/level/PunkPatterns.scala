package com.politrons.level

import scala.collection.{Seq, immutable}

/**
 * Movement patterns for enemies
 */
object PunkPatterns {

  val punk1MovePattern: Seq[String] = immutable.List(
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"
  )

  val punk2MovePattern: Seq[String] = immutable.List(
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left"
  )

  val punk3MovePattern: Seq[String] = immutable.List(
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
  )

  val punk4MovePattern: Seq[String] = immutable.List(
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left",
    "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left", "left"
  )

}
