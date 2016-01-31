### Reading BBC news page ###

# TODO:  stripArticle():  more contractions, remove webiste links
#        getLinks():      change method to get x-number of news links


import urllib    
import re 
import matplotlib.pyplot as pyplot



def getLinks( homepage ):
    ''' Create list of desired links from homepage '''

    links = []
    home = urllib.urlopen( homepage )
    
    for line in home:
        # Get ItemList stories (usually 12 of them)
        if '"url": "' in line and '/news/"' not in line:
            s = line
            s = re.sub( '"url": "', '', s)
            s = re.sub( '"', '', s)
            links.append( s.strip() )

    return links




def getArticle( link ):
    """ Get single String of article text from URL """
    
    ol = urllib.urlopen( link )
    article = ""

    for line in ol:
        if 'story-body__introduction' in line or 'story-body__crosshead' in line \
           or 'story-body__line' in line or 'story-body__list-item' in line:
            s = line
            s = re.sub( '<[^>]+>', ' ', s).strip()
            article = article + " " + s

    article = re.sub('\s+', ' ' , article)
    
    return article




def stripArticle( article ):
    """ Remove punctuation and contractions from article """

    article = article.lower()

    # Remove contractions
    article = re.sub('\'s', '', article)
    article = re.sub('n\'t', ' not', article)
    article = re.sub('s\'', 's', article)
    article = re.sub('I\'m', 'I am', article)
    ##(  she'd ->  she would, OR she had;
 
    # Remove punctuation but leave hyphenated words 
    article = re.sub(' - ', ' ', article)
    article = re.sub('- ', ' ', article)
    article = re.sub(r'[?|$|.|!|)|\]|\[|(|"|,|:|\']', r'', article)   

    # html stuff
    article = re.sub('&amp;', '&', article)

    return article




def hasNumbers( inputString ):
    """ Check string for numbers, return true if found"""
    return any(char.isdigit() for char in inputString)




def addWords( text, wordlist ):
    """ Count and store occurrences of each word in a dictionary"""
    
    words = text.split()
    for word in words:
        # Remove numbers, hashtags and emails
        if not hasNumbers( word ) and '#' not in word and '@' not in word:
            if word in wordlist:
                wordlist[ word ] += 1
            else:
                wordlist[ word ] = 1

    return wordlist




def plotFreq( wordlist, max ):
    
    x = []
    y = []
    for i in range(1,max+1):
        x.append(i)
        y.append(0)

    for w in wordlist:
        val = wordlist[w]
        #print "val", val
        y[val-1] = y[val-1] + 1        

    pyplot.plot(x, y, 'bo')
    pyplot.yscale('log')
    pyplot.xscale('log')
    pyplot.xlabel('No. of occurrences')
    pyplot.ylabel('No. of words with this occurence')
    pyplot.savefig('tst.png')

    #print x
    #print y


    






def main():


    # Go to home page and get x-number of news links
    links = getLinks( "http://www.bbc.co.uk/news" )
    #links = ["http://www.bbc.co.uk/news/uk-politics-32810887"]

    # Dictionary of words and occurrences
    wordlist = {}

    print "links used:"
    for i in links:
        print i
    print " "


    for link in links:

        # Iterate through pages updating wordlist
        article = getArticle( link )
        stripped_article = stripArticle( article )
        wordlist = addWords( stripped_article, wordlist )

    # Create list of words, most common word first
    srt_list = sorted(wordlist, key=wordlist.get, reverse=True)

    # Max no. of occurrences of any word (normally "the")
    max = wordlist[ srt_list[0] ]

    # Plot frequency of words 
    plotFreq( wordlist, max )
   
    # Print results:
    print "Number distinct words: ", len( wordlist )
    print " "
    # Print sorted wordlist
    for w in srt_list:
        print w, wordlist[w]



    


main()










### 2 options to remove multiple spaces:
#  re.sub( '\s+', ' ', mystring ).strip()
#  ' '.join(mystring.split())

