
## Solution

**System requirements**

- Java 8
- Maven 3.x.x
- [Apache OpenNLP](https://opennlp.apache.org/)

**Solution overview**

1. Train a model using Apache OpenNLP to read company names from the news articles. This has been done using 16 articles from the given data. That file is   available as training.TXT in the project. This step is done only once.

2. Extract data from the CSV and XML files. [OpenCSV](http://opencsv.sourceforge.net/) and JAXB has been used respectively.

3. Put the company names from the CSV in a [PatriciaTrie](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/trie/PatriciaTrie.html).

4. Using the trained model, extract the possible company names from each article and check for their presence in the Trie.

5. On my machine, the application runs in around 500 seconds with the given data.

**Shortcomings and scope for improvement**

1. Currently company name is picked up from the Trie by doing a prefix search. This will not get the former names. Also, it might pick up the wrong company (for exampel, xyz Ltd instead of xyz BV). Instead, a new method should be implemented in the Trie to do a partial String match.

2. The model should be trained with more data to minimize false positives. Currently there is a fair share of false positives picked up by the model.

3. Modify the program to accept file path and file name. Currently, it has been hard coded. 