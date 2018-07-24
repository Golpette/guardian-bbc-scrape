** weinstein.py
Scrapes specified number of stories related to Harvey Weinstein from the Guardian website and looks at usage of "non-consensual" vs "rape". If you're interested, ~23% of articles used the euphemism without mentioning rape.



**  bbc.py  
Rewrite of Guardian code below. Essentially does same thing but neater and using python.  
  
  
  

**  GuardianFun.java is main program

Q: What's all this then?  
A: This was some code I had initially written because I wanted to know how to read webpages with java. At the same time I was listening to Michel Thomas' spanish lessons and he claimed that several years ago a study was done that showed that to understand a typical issue of the New York Times, you would only need a vocabulary of ~600 words. (Though he didn't elaborate on what exactly this meant).


Q: What does it do?  
A: The main program, "GuardianFun.java", will go to The Guardian's (British newspaper) homepage and then visit the first 100 links to stories. It will get the article body from each page and keep a tally of what words appear and how many times. It outputs this list to "Guardian_words_xx.dat" and it outputs a data file with #occurrences on x-axis and #words with this #occurences on y-axis into "plotData.dat".
If you plot this you'll see a power law... I used gnuplot to generate the example I uploaded. These commands will let you do the same:

set termoption enhanced;  
set logscale xy;  
f(x) = a*x**b;  
b = -1;  
a = 1000;  
fit f(x) 'plotData' via a,b;  
plot 'plotData.dat' with points title 'Plot title', \  
     f(x) with lines title sprintf('power fit curve f(x) = %.2f·x^{%.2f}', a, b)
     
     

Q: Wow. Is that really a power law?  
A: Yup


Q: What does it mean!?  
A: Power laws are usually associated with critical phenomena, so it's probably something really deep. Also, I ran this program 3 times in the last 2 weeks and the critical exponent went from 1.77 to 1.75 to 1.73. God only knows what'll happen when it hits zero


