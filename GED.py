# -*- coding: utf-8 -*-
"""
Created on Thu Mar 23 16:18:14 2017

@author: hp
"""

import csv
import re
#Open Score Table
csv_reader = csv.reader(open('ScoreTable1.csv'))
ScoreTable = [[0 for col in range(26)] for row in range(26)]

for row in csv_reader:

    for list in ScoreTable:
        for s in range(0,26):
            list[s] = row[s]
        

def Max(a, b, c):
    max=a
    if max<b:
        max=b
    if max<c:
        max=c
    return max
    
def GetIndex(character):
    index = 0
    if character=='a':
        index = 0
    elif character=='b':
        index = 1
    elif character=='c':
        index = 2
    elif character=='d':
        index = 3
    elif character=='e':
        index = 4
    elif character=='f':
        index = 5
    elif character=='g':
        index = 6
    elif character=='h':
        index = 7
    elif character=='i':
        index = 8
    elif character=='j':
        index = 9
    elif character=='k':
        index = 10
    elif character=='l':
        index = 11
    elif character=='m':
        index = 12
    elif character=='n':
        index = 13
    elif character=='o':
        index = 14
    elif character=='p':
        index = 15
    elif character=='q':
        index = 16
    elif character=='r':
        index = 17
    elif character=='s':
        index = 18
    elif character=='t':
        index = 19
    elif character=='u':
        index = 20
    elif character=='v':
        index = 21
    elif character=='w':
        index = 22
    elif character=='x':
        index = 23
    elif character=='y':
        index = 24
    elif character=='z':
        index = 25
    return index

def GetReplaceScore(q, d):
    que_index = int(GetIndex(q))
    dic_index = int(GetIndex(d))
    return ScoreTable[que_index][dic_index]

def GED_Levenshtein(QueString, DicString):
    insert = -1
    delete = -1
    replace = -1
    match = 1
    scoreMatrix = [[0 for col in range(len(QueString)+1)] for row in range(len(DicString)+1)]
    # Initialize scoring matrix
    for DicLetter in range(len(DicString)+1):
        scoreMatrix[DicLetter][0]=-1*DicLetter
    for QueLetter in range(len(QueString)+1):
        scoreMatrix[0][QueLetter]=-1*QueLetter
    
    for d in range(1, len(DicString)+1):
        for q in range(1, len(QueString)+1):
            if DicString[d-1]!=QueString[q-1]:
                insertScore = scoreMatrix[d][q-1]+insert
                deleteScore = scoreMatrix[d-1][q]+delete
                replaceScore = scoreMatrix[d-1][q-1]+replace
                scoreMatrix[d][q] = Max(insertScore, deleteScore, replaceScore)
            elif DicString[d-1]==QueString[q-1]:
                scoreMatrix[d][q] = scoreMatrix[d-1][q-1]+match
                
    return scoreMatrix[len(DicString)][len(QueString)]

            
def GED_Soundex(QueString, DicString):
    insert = -3
    delete = -3
    match = 3
    scoreMatrix = [[0 for col in range(len(QueString)+1)] for row in range(len(DicString)+1)]
    # Initialize scoring matrix
    for DicLetter in range(len(DicString)+1):
        scoreMatrix[DicLetter][0]=-1*DicLetter
    for QueLetter in range(len(QueString)+1):
        scoreMatrix[0][QueLetter]=-1*QueLetter
    
    for d in range(1, len(DicString)+1):
        for q in range(1, len(QueString)+1):
            if DicString[d-1]!=QueString[q-1]:
                insertScore = scoreMatrix[d][q-1]+insert
                deleteScore = scoreMatrix[d-1][q]+delete
                replaceScore = scoreMatrix[d-1][q-1]+int(GetReplaceScore(QueString[q-1],DicString[d-1]))
                scoreMatrix[d][q] = Max(insertScore, deleteScore, replaceScore)
            elif DicString[d-1]==QueString[q-1]:
                scoreMatrix[d][q] = scoreMatrix[d-1][q-1]+match
                
    return scoreMatrix[len(DicString)][len(QueString)]  
    
#try1 = open('try1.txt', 'r')
dictionary = open('names.txt', 'r')
expect = open('expect.txt','w')
#l_expect = open('l_expect.txt', 'w')
#word = try1.readlines()
entries = dictionary.readlines()

train = open('train.txt','r')
correctAnswers = train.readlines()

row_index = 0
answerlist = [0]*len(correctAnswers)
testlist = [0]*len(correctAnswers)

#Get queryword and the answer from train.txt
for rows in correctAnswers:
    eachrow = re.split('\t', rows)
    answerlist[row_index] = eachrow[1]
    testlist[row_index] = eachrow[0]
    row_index = row_index+1
    
"""
#use lev to calculate the rate
count = 0
for queryWord in testlist:
    best = -100
    queryWord = queryWord.lower()
    for entry in entries:
        score = GED_Levenshtein(queryWord, entry)
        if score>best:
            best = score
            result = entry
    l_expect.write(result)
    print ("write "+str(count)+" times.")
    count=count+1;
    
l_expect.close()

num = 0
n=0
expectAnswers = open('l_expect.txt','r')
for expectAnswer in expectAnswers:
    n=n+1  
    if expectAnswer==answerlist[n-1]:
        num = num+1
      
l_expect.close()
"""  
#use soundex to calculate the rate
count = 0
for queryWord in testlist:
    best = -100
    queryWord = queryWord.lower()
    for entry in entries:
        score = GED_Soundex(queryWord, entry)
        if score>best:
            best = score
            result = entry
    expect.write(result)
    print ("write "+str(count)+" times.")
    count=count+1;
    
expect.close()

num = 0
n=0
expectAnswers = open('expect.txt','r')
for expectAnswer in expectAnswers:
    n=n+1
    if expectAnswer==answerlist[n-1]:
        num = num+1
expect.close()

#Close files
train.close()
dictionary.close()


    
rate = num/n
print (num)
print (n)
print ('%.2f%%' % (rate*100))
expectAnswers.close
    


