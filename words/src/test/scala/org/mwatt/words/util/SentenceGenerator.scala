package org.mwatt.words.util

import org.apache.spark.sql.{Row, SparkSession}

object SentenceGenerator {

  val words = List(
    "cat", "dog", "apple", "house", "sleep", "eat", "run", "happy", "sky", "blue"
  )

  def generateSentences(numSentences: Int, outputDir: String): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Sentence Generator")
      .getOrCreate()

    val sentences = spark.sparkContext.parallelize(1 to numSentences).map(generateSentence)

    val sentenceDF = sentences.toDF("sentence")
    sentenceDF.write
      .option("header", true)
      .csv(outputDir)
  }

  def generateSentence(index: Int): String = {
    val sentenceLength = 5 + scala.util.Random.nextInt(15)
    val sentence = (1 to sentenceLength).map(i => words(scala.util.Random.nextInt(words.length))).mkString(" ")
    sentence
  }
}

