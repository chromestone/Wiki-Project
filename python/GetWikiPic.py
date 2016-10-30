import sys
import requests
from xml.dom import minidom
import os

def getDiscription(i) :
    pNode = i.parentNode
    gPNode = pN.parentNode
    for node in gPNode.childNodes:
        if node.nodeType == node.TEXT_NODE and node.localName == 'thumbcaption':
            return str(node.nodeValue)
    return None
def getImg(i) :
    url = 'http:' + str(i.attributes['src'].value)

    if 'upload' in url and not 'svg' in url and not 'logo' in url :
        try :
            connection = requests.get(url, stream=True)
            statusCode2 = connection.status_code
            if statusCode2 == 200 :
                index = url.rfind("/")
                fileName = url[index + 1 : len(url)]

                try :
                    with open(directory + '/' + fileName, 'wb') as f :
                        for chunk in r2.iter_content():
                            f.write(chunk)
                except IOError as e :
                    print 'IOError occured writing image #' + str(imgWritten), '(' + fileName + '):'
                    print '  ', e.args
                    pass
                except :
                    print 'Unexpected error:', sys.exc_info()
                    print '    writing image #' + str(imgWritten), '(' + fileName + ')'
                else :
                    return fileName
                    imgWritten += 1

                if imgWritten == 100 :
                    break
                else :
                    print 'Error getting image #' + str(imgWritten + 1) + ':'
                    print '   Response code of ' + statusCode2 + ' from (' + s + ')'
        except requests.exceptions.RequestException as e :
            print 'Exception while connecting to:'
            print url
            print e.args
            pass
        except :
            print 'Unexpected error:', sys.exc_info()
            print url
            pass

    print imgWritten, 'images written.'

if len(sys.argv) > 1 :
    topic = str(sys.argv[1])

    try :
        r = requests.get('http://en.wikipedia.org/wiki/' + topic)
        statusCode = r.status_code
        if statusCode == 200 :
            doc = minidom.parseString(r.content)

            directory = os.getcwd() + '\\' + topic
            if not os.path.exists(directory) :
                os.makedirs(directory)
            
            imgList = doc.getElementsByTagName('img')
            
            imgWritten = 0
            for i in imgList :
                getImg(i)

            try :
                os.rmdir(topic)
            except OSError:
                pass
            except :
                print 'Unexpected error:', sys.exc_info()
            else :
                print topic, 'directory deleted as no files existed in directory.'

        else :
            print 'Wikipedia gave response code of', statusCode

    except requests.exceptions.RequestException as e :
        print 'Exception while connecting to:'
        print 'http://en.wikipedia.org/wiki/' + topic
        print e.args
    except :
        print 'Unexpected error:', sys.exc_info()
        print 'http://en.wikipedia.org/wiki/' + topic
else :
    print 'No Topic Argument'