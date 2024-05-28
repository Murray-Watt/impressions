package org.mwatt.words.util

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row


object SentenceGenerator {
  case class Sentence(listOfWords: List[String])
  case class ReviewData(reviewId: Long, sentences: List[Sentence])
  def generateSentence(n: Int): String = {
    val words = (1 to n).map(x => "word" + x).toList
    words.toList.mkString(" ")
  }

  def generateSentences(numSentences: Int, numReviews: Int, outputDir: String): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Sentence Generator")
      .getOrCreate()

    import spark.implicits._

    // Generate unique reviewIds
    val reviewIds = spark.sparkContext.parallelize(1 to numReviews).map(_.toLong)

    // Generate sentences for each reviewId
    val reviewDataRDD = reviewIds.map(reviewId => {
      val sentencesForReview = (1 to numSentences).map(generateSentence).map(sentence => Sentence(sentence.split(" ").toList)).toList
      ReviewData(reviewId, sentencesForReview)
    })

    // Create DataFrame from RDD[ReviewData]
    val reviewDataDF = reviewDataRDD.toDF()

    reviewDataDF.write
      .option("header", true)
      .csv(outputDir)
  }
}




