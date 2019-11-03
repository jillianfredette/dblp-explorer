# dblp-explorer
Assignment 5 for Software Design

## Application
The application is in the DBLP_Explorer folder. The application takes 
the classpath of the json file, the keyword, and the integer n as command 
line inputs. The program first output the Keyword articles (articles whose 
title contains the keyword, not case sensitive), then it outputs each tier in order. 
The information output for each article is the ID number of the article, followed 
by its title, followed by the year it was published. The program also outputs
the benchmarking information including time and memory consumption.

## Documentation
In the documentation folder you will find the design.md and benchmark.md files.

## Ranking
For each tier I chose to rank the articles based on the year they were published 
since more current articles tend to be more relevant for research purposes.

## Compiling and Running
Since this is a Maven project it can be compiled by navigating to the project folder 
then running the command 

*mvn clean package*

And then it can be run by navigating to the newly created *target* folder within 
the project folder and running the command 

*java -jar "DBLP_Explorer-0.0.1-SNAPSHOT.jar" ".../file_name.json" "keyword" n*

Where there *".../file_name.json"* part should be replaced by the classpath of the 
input file, the *"keyword"* part should be replaced by the keyword being used, and 
*n* should be replaced by the integer value of the maximum number of tiers to be output.