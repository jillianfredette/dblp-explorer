# Design 
The program is divided into two main sections, the first is the section which handles reading in the file
using Java Stream, and the second section first finds all articles with the keyword in their title, 
and then finds all tiers of articles being cited up to the amount specified via input using Java Stream. 

I decided to optimize my solution for time efficiency if finding the keyword and tier articles 
over reading in the file or memory efficiency since the usefulness of this
application is dependent on if it can deliver results in a timely manner.

## Efficiency
### Section 1
This section reads in the articles from the file as JSON objects. I used Java Stream as specified to 
read in the file, and stored the JSON Objects in a list to be used as a stream later for finding each tier of
articles. Since the input file is expected to be rather large there wasn't much I could do in speeding up the process
of reading it in, since it is entirely dependent on the size of the file to be input. The list that I used to store 
the JSON Objects must be able to hold all of the articles, so there are memory limitations.

### Section 2
This section takes the list of JSON objects, which represent the articles, and first finds all articles which have the 
keyword in their title, stores the ids of the articles they reference, and then for each tier up to n the program 
repeats the process of find the articles that have been cited by the previous tier, and their citations. The program 
uses the list of articles as a stream each time the next tier of articles need to be found, and to aid in this effort 
I created a HashMap which stores the id numbers of the referenced articles so they can quickly be cross-referenced (O(1) 
time complexity to find an id using the HashMap). I have a helper function in this section which is responsible for 
printing the articles and finding their referenced articles. In this function I am able to use parallelStream() option 
to aid in efficiency.