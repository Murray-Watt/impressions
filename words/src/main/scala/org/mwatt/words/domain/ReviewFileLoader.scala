package org.mwatt.words.domain

import java.time.{Instant, ZoneId}
import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

class ReviewFileLoader(baseDirectory: Path) {

  def createReviewSpec(path: Path): ReviewSpec = {
    val fileName = path.getFileName.toString
    val reviewSource = path.getParent.getParent.getParent.getFileName.toString
    val productType = path.getParent.getParent.getFileName.toString
    val modelName = path.getParent.getFileName.toString

    val fileCreationTime = Files.getLastModifiedTime(path)

    val dateTime = Instant.ofEpochMilli(fileCreationTime.toInstant.toEpochMilli)
      .atZone(ZoneId.of("UTC"))

    ReviewSpec(reviewSource = reviewSource, productType = productType, modelName = modelName, fileName = fileName, dateCreated = Some(dateTime))
  }



  def getReviewFiles(reviewFilePatterns: Seq[String]): Stream[ReviewSpec] = {
    val javaStream = Files.walk(baseDirectory)
      .filter(path => !Files.isDirectory(path))
      .filter(path => reviewFilePatterns.exists(pattern => path.toString.endsWith(pattern)))

    val scalaIterator = javaStream.iterator().asScala
    val reviewSpecIterator = scalaIterator.map(createReviewSpec)

    def createStream(iterator: Iterator[ReviewSpec]): Stream[ReviewSpec] = {
      if (!iterator.hasNext) {
        Stream.empty
      } else {
        Stream.cons(iterator.next(), createStream(iterator))
      }
    }

    createStream(reviewSpecIterator)
  }

}