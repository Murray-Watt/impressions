package org.mwatt.words.domain

case class Sentence(listOfWords: List[String])
case class ReviewData(reviewId: Long, sentences: List[Sentence])
