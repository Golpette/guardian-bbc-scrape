##############################################
#  Reading Guardian articles on Weinstein 
#  for rape vs non-consensual use.
##############################################
#
#---Example output for start=https://www.theguardian.com/film/harvey-weinstein
#     -> max=400, 16/6/18
#-------------RESULTS----------------------------
#Total articles found =  223
#
#Articles containing rape =  110
#Articles containing non-consensual =  90
#
#Articles with non-consensual but without rape =  25
#Articles with rape but not non-consensual =  45
#
#Total count of rape all articles =  267
#Total count of non-consensual 99
#-----------------------------------------------


from bs4 import BeautifulSoup
import urllib2
import urllib    
import re 



def get_related_links( page, to_visit, visited ):
    ''' Retrieve "More on this story" links from current page
        and update list of visited and to_visit pages
     '''

    resp = urllib2.urlopen( page )
    soup = BeautifulSoup(resp, from_encoding=resp.info().getparam('charset'))

    for link in soup.find_all('a', href=True):
        if 'weinstein' in link['href'].lower() and '//www.theguardian' in link['href'].lower():
            if link['href'] not in to_visit and link['href'] not in visited:
                to_visit.append( link['href'] )

    return to_visit




def getArticle( link ):
    """ Get single String of article body from URL """

    ol = urllib.urlopen( link )
    article = ""

    article_started = False
    article_finished = False

    for line in ol:
        if 'articleBody' in line:
            article_started = True
        if 'class="after-article' in line:
            article_finished = True
        
        if (article_started and not article_finished):
            article = article + " " + line

    article = tidy_article( article )
    
    return article




def get_date_published( link ):
    """ Get article's publish date """

    ol = urllib.urlopen( link )
    date = ""

    next_line = False
    for line in ol:
        if next_line:
            next_line = False
            date = tidy_article(line)
        if 'datePublished' in line:
            next_line = True
    
    return date




def tidy_article( text ):
    """ Tidy up body of article
        Remove html tages and extra spaces
    """        
    text = re.sub( '<[^>]+>', ' ', text) 
    text = re.sub('\s+', ' ' , text)        
    return text.strip()







def main():

    # Max number Weinstein stories to read
    MAX_PAGES = 400
    # Count actual articles
    c_articles = 0
    # Articles using each
    count_with_rape = 0
    count_with_noncon = 0
    # Total usage
    c_rape= 0
    c_noncon= 0
    # Nonconsensual without rape
    c_noncon_no_rape = 0
    c_rape_no_noncon = 0


    start_page = "https://www.theguardian.com/film/harvey-weinstein"

    to_visit = []
    visited = []

    visited.append( start_page )
    to_visit = get_related_links( start_page, to_visit, visited )


    if len(to_visit)==0:
        print "No links found"
        exit(2)


    while len(visited)<MAX_PAGES+1:

        #print "visited", visited
        #print "to_visit", to_visit

        if len(to_visit)==0 or len(visited)==MAX_PAGES:
            print "-------------RESULTS----------------------------"
            print "Total articles found = ", c_articles
            print ""
            print "Articles containing rape = ", count_with_rape
            print "Articles containing non-consensual = ", count_with_noncon 
            print ""
            print "Articles with non-consensual but without rape = ", c_noncon_no_rape
            print "Articles with rape but not non-consensual = ", c_rape_no_noncon
            print ""
            print "Total count of rape all articles = ", c_rape
            print "Total count of non-consensual", c_noncon
            print "-----------------------------------------------"
            exit(7)


        url = to_visit.pop(0)

        visited.append( url )

        to_visit = get_related_links( url, to_visit, visited )
        
        # May not necessarily be an article
        article = getArticle( url ).lower()
        if len(article) > 20:
            c_articles += 1
    
        date = get_date_published( url )


        #Save article
        filename = "article_"+str( len(visited) )+".txt"
        file = open( filename, 'w' )
        file.write( url + "\n" )
        file.write( date + "\n")
        file.write( article )
        file.close()


        
        # ------------- Counting --------------------------------
        c_rape += article.count( ' rape' ) #Will miss it if rape first word in article. Unlikely.
        c_noncon += article.count( 'non-consensual sex' )
        c_noncon += article.count( 'nonconsensual sex' )
        c_noncon += article.count( 'non consensual sex' )
        #c_noncon += article.count( 'unwanted sex' )

        if 'rape' in article:
            count_with_rape += 1
            if ('non-consensual sex' not in article and 'nonconsensual sex' not in article and 'non consensual sex' not in article):
                c_rape_no_noncon += 1

        if ('non-consensual sex' in article or 'nonconsensual sex'  in article or 'non consensual sex' in article):
            count_with_noncon += 1
            if 'rape' not in article:
                c_noncon_no_rape += 1
        # -------------------------------------------------------



    





main()


