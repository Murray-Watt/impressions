# Impressions
Impression has/will have four  modules

1. Base: Not working yet -- common utils including macro for reflection on sealed objects but there are a few problems
2. Problems: Selected Scala warm-up problems from the internet
3. Reviews: This module will use the words form the Words module to build sentiment analysis training data
4. Words: This module will count word occurrence neutral for purposes of a sentiment analysis
   in selected sites, manually these words will be categorized as relevant

# Base Module   
This module is not working yet, it is intended to provide common utilities for the other modules.

log4s: Is a scala wrapper for log4j2 -- working
EnumHelper: Is a macro that generates a list of values for a sealed object -- working but not with serialization
JsonUtils: Is a utility to convert case classes to json and back -- not working


# Problems Module

Selected Scala warm-up problems from the internet

# Reviews Module

TBA

# Words Module

Goals

This module will eventual check sites for new reviews

It will maintain of list of reviews that have been processed

It will maintain words encountered in reviews that will include a word count and weight for sentiment analysis
The weights will be manually assigned and will be used to train a sentiment analysis model. By default the weight of a word will be 0

It will also create training data for sentiment analysis. 

A review will represented number of sentences, total word count, a total sentiment weight based on the words and trained to rating of the review













