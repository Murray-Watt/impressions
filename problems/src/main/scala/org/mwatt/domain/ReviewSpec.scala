package org.mwatt.domain

import org.joda.time.DateTime

case class ReviewSpec(reviewSource: String,
                      productType: String,
                      modelName: String,
                      fileName: String,
                      dateCreated: Option[DateTime])

