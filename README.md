# DomainClassification

Suppose we've classified some website domains into various categories: sports, games, politics, dating, news, ...( you can assume there aren't more than 100 or so categories )
These classifications can be used in ad targeting.

A small sample domain->category list is provided in domain_classifications.txt.  Each domain can belong to multiple categories.
A "real" list would be much larger, perhaps millions of rows.

Your task is to create a DomainManager class that loads the classifications from file and enables fast lookup of categories given a domain.

Implement DomainManager.
    - During initialization, DomainManager needs to load all of the classifications from the file into an in-memory data structure.  
      NOTE: Since there can be millions of classifications, you should make efficient use of memory space in your data structures.
    - Implement a categories lookup function.  lookupCategories( String domain ) returns all the categories that the domain belongs to.
      e.g. based on data in the sample file: lookupCategories( espn.com ) would return [sports,news]
