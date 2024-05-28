package org.mwatt.words.domain

import java.time.ZonedDateTime


case class ReviewSpec(reviewSource: String,
                      productType: String,
                      modelName: String,
                      fileName: String,
                      dateCreated: Option[ZonedDateTime])

