package org.mwatt.domain

import org.joda.time.{DateTimeZone, LocalDateTime}
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import java.nio.file._
import java.io.IOException
import java.util.{Comparator, UUID}

class ReviewFileLoaderTest extends FunSuite with BeforeAndAfterEach {

  protected var testDirectory: Path = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    testDirectory = Files.createTempDirectory("reviewTest-" + UUID.randomUUID().toString)
  }

  override protected def afterEach(): Unit = {
    try {
      Files.walk(testDirectory)
        .sorted(Comparator.reverseOrder())
        .forEach((path: Path) => Files.deleteIfExists(path))
      Files.deleteIfExists(testDirectory)
    } catch {
      case e: IOException =>
        fail("An error occurred while cleaning up the test directory", e)
    }
    super.afterEach()
  }

  class Fixture(testDirectory: Path) {
    val subject: ReviewFileLoader = new ReviewFileLoader(testDirectory)
    val extensions: Seq[String] = List(".html", ".txt", ".md")

    def createReviewHtmlDocument(reviewText: String): String = {
      val htmlDocument =
        """
          |<!DOCTYPE html>
          |<html>
          |<head>
          |  <title>Reviews</title>
          |</head>
          |<body>
          |  <div>$reviewsGoHere</div>
          |</body>
          |</html>
    """.stripMargin
      htmlDocument.replace("$reviewsGoHere", reviewText)
    }

    def createReviewFile(testDirectory: Path, reviewSpec: ReviewSpec, reviews: Seq[String]): ReviewSpec = {
      val reviewDirectory = testDirectory.resolve(reviewSpec.reviewSource).resolve(reviewSpec.productType).resolve(reviewSpec.modelName)
      Files.createDirectories(reviewDirectory)

      val reviewFilePath = reviewDirectory.resolve(reviewSpec.fileName)
      val reviewText = reviews.mkString("\n")
      Files.writeString(reviewFilePath, reviewText)
      val fileCreationTime = Files.getLastModifiedTime(reviewFilePath)

      val dateTime = new LocalDateTime(fileCreationTime.toInstant.toEpochMilli).toDateTime(DateTimeZone.UTC)
      reviewSpec.copy(dateCreated = Some(dateTime))
    }
  }

  test("returns an empty stream given an empty base directory") {
    new Fixture(testDirectory) {
      subject.getReviewFiles(extensions) shouldBe empty
    }
  }

  test("returns a stream that contains a single review spec when a single review file is present") {
    new Fixture(testDirectory) {
      val reviewSpec: ReviewSpec = ReviewSpec(reviewSource = "amazon", productType = "headphones", modelName = "A10", fileName = "reviews.html", dateCreated = None)
      val reviews: Seq[Nothing] = Seq.empty
      val expected: ReviewSpec = createReviewFile(testDirectory, reviewSpec, reviews)

      subject.getReviewFiles(extensions).toList should contain only expected
    }
  }

  test("returns a stream that contains review specs for multiple extensions and ignores unrelated files") {
    new Fixture(testDirectory) {
      val relatedExtensions: Seq[String] = List(".htm", ".html", ".XML")

      val reviewSpec1: ReviewSpec = ReviewSpec(reviewSource = "amazon", productType = "headphones", modelName = "A10", fileName = "reviews.html", dateCreated = None)
      val reviewSpec2: ReviewSpec = ReviewSpec(reviewSource = "amazon", productType = "keyboards", modelName = "K12", fileName = "keyboard.htm", dateCreated = None)
      val reviewSpec3: ReviewSpec = ReviewSpec(reviewSource = "bestbuy", productType = "monitors", modelName = "M24", fileName = "monitor.XML", dateCreated = None)
      val unrelatedFileSpec: ReviewSpec = ReviewSpec(reviewSource = "amazon", productType = "headphones", modelName = "A10", fileName = "unrelated.txt", dateCreated = None)

      val reviews: Seq[Nothing] = Seq.empty

      val expectedReviewSpecs: Seq[ReviewSpec] = List(
        createReviewFile(testDirectory = testDirectory, reviewSpec = reviewSpec1, reviews = reviews),
        createReviewFile(testDirectory = testDirectory, reviewSpec = reviewSpec2, reviews = reviews),
        createReviewFile(testDirectory = testDirectory, reviewSpec = reviewSpec3, reviews = reviews)
      )

      val resultReviewSpecs: Seq[ReviewSpec] = subject.getReviewFiles(reviewFileSuffix = relatedExtensions).toList
      resultReviewSpecs should contain theSameElementsAs  expectedReviewSpecs

      val resultFileNames: Seq[String] = resultReviewSpecs.map(_.fileName)
      resultFileNames shouldNot contain (unrelatedFileSpec.fileName)
    }
  }
}