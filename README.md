# Impressions
Impression has/will have three modules

1. Words: This module will count word occurrence neutral for purposes of a sentiment analysis
   in selected sites, manually these words will be categorized as relevant
2. Sentiment: This module will use the words form the Words module to build sentiment analysis training data
3. Problems: Selected Scala warm-up problems from the internet
   

# Words Module

```
src/
├── main/
│   ├── resources/
│   │   └── log4j2.xml
│   └── scala/
│       └── org/
│           └── mwatt/
│               └── app/
│                   └── WordsRunner.scala: Used to run the application
│                   └── ReveFileLoader.scala: Used to filter and load the review files
│                   └── ReviewSpec.scala: Details of a review file
└── test/
    └── scala/
        └── org/
            └── mwatt/
                └── app/
                    └── ReviewFileLoaderTest.scala
```

# Problems Module

* [AreaAndVolumeUnderACurve.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FAreaAndVolumeUnderACurve.scala)
* [CountWithoutCount.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FCountWithoutCount.scala)
* [eToX.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FeToX.scala)
* [FilterImpl.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FFilterImpl.scala)
* [HelloWorldNApp.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FHelloWorldNApp.scala)
* [Lambdas.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FLambdas.scala)
* [OddIndices.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FOddIndices.scala)
* [ReadTypes.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FReadTypes.scala)
* [ReplaceWithAbs.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FReplaceWithAbs.scala)
* [ReplicateNums.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FReplicateNums.scala)
* [ReverseWithoutReverse.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FReverseWithoutReverse.scala)
* [Sum2Lines.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FSum2Lines.scala)
* [SumOfOddElements.scala](problems%2Fsrc%2Fmain%2Fscala%2Fproblems%2FSumOfOddElements.scala)