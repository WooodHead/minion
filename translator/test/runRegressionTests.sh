#!/bin/bash
# 
# Run regression tests for Tailor
# Andrea Rendl

TEST_DIR=test/regressionTests

# Start with the .cm files
cd ..
for X in `ls ${TEST_DIR}/*.cm`;
  do
  java -jar tailor.jar  -tf -silent $X 
  Difference=`diff "${X}.minion" "${X}.minion.expected"`
  
  if [ "$Difference" != "" ]; then
      echo "ERROR in translating ${X}"
  else 
      echo "OK: translating ${X}"
      rm ${X}.minion
  fi
done



# then move on to the .eprime files
for X in `ls ${TEST_DIR}/*.eprime`
  do
# if there is a parameter file
#echo "`ls ${X}*.param`"
parameters=`ls ${X}*.param`
if [ "$parameters" != "" ]; then 
    for Y in `ls ${X}*.param` 
      do
      java -jar tailor.jar  -tf -silent $X $Y 
      Difference=`diff "${Y}.minion" "${Y}.minion.expected"`
      if [ "$Difference" != "" ]; then
	  echo "ERROR in translating ${Y}"
      else 
	  echo "OK: translating ${Y}"
	  rm ${Y}.minion
      fi
    done
    
# otherwise just run the .eprime file
    
else
    java -jar tailor.jar -tf -silent $X 
    
    Difference=`diff "${X}.minion" "${X}.minion.expected"` 
    
    if [ "$Difference" != "" ]; then
	echo "ERROR in translating ${X}"
    else 
	echo "OK: translating ${X}"
        rm ${X}.minion
    fi
    
fi
    
done
# go back to the initial directory
cd test
