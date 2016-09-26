# Twitter scalding jobs examples (Maven projet)

This Maven project contains 3 examples of MapReduce jobs implemented with the Twitter Scalding Toolbox (https://github.com/twitter/scalding):
* a WordCount job wich can be run with the licence.txt file as input;
* a TestGroupBy job (input is the train.csv file);
* a Json file tranformation to tsv file (input QEcrites_XIV.json)

# Install and run

## Prerequisites:
* eclipse installed;
* jdk7 or jdk8 installed;
* scala-IDE for eclipse installed (http://scala-ide.org/);
* scala 2.10 or 2.11 installed (http://www.scala-lang.org/)

## Instructions:
1. clone the project
2. unzip the $PRJ/examples/data.zip file
3. use the $PRJ/launch-confs files to run WordCountJob and QuestionEcrites job with eclipse

## Additional notes about QuestionEcrites
<p>This job can be used to work with a large file available at: <br>
http://data.assemblee-nationale.fr/static/openData/repository/QUESTIONS/questions_ecrites/Questions_ecrites_XIV.json.zip

<p>Before using the large json file, it is necessary to prettify it with jq (https://stedolan.github.io/jq/) with the command:<br>
**jq . Questions_Ecrites_XIV.json > QEcrites_XIV.json**

<p>Note that this third job works with a local scalding execution: it has not been tested in a distributed cluster.
