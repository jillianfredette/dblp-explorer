# Benchmark
In order to better understand the efficiency of my program I took reading on the time consumption and memory 
consumption of my program while it ran.
For testing purposes, I took a sample of the original v11 file that was approximately 155MB. When running my program I have it take measurements on and outputting the 
Time to read the data in from the file, the time to find te articles in all tiers, the total runtime, and the total
memory used. To go a better estimation I ran the program 5 times with a max tier number of 10. Below is a table with the 
time and memory consumption from each run.

Run | Read Time (ms) | Tiering Time (ms) | Total Time | Memory Used (MB)
----|----------------|-------------------|------------|----------------
1 | 14443 | 347 | 14889 | 1275
2 | 14383 | 502 | 14730 | 1275
3 | 14931 | 350 | 15433 | 1275
4 | 14404 | 408 | 14754 | 1275
5 | 14692 | 410 | 15100 | 1275

**Averages:**  
Read Time: **14570 ms**  
Tiering Time: **410 ms**  
Total Time: **14981**
Memory Consumption: **1275MB**

From these tests it is obvious that the majority of time is spent reading in the large file, and that
the time needed to actually find the keyword and tier articles is negligible. 