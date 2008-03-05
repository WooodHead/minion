#!/bin/bash

echo \# Autogenerated by make_depend.sh > Makefile.dep.temp

for file in `cd minion && find .`
do
  # Remove the initial ./ from the filename
  file=${file##*./}
  # Get the extension
  ext=${file##*.}
  if [[ "$ext" == "cpp" ]]; then
    # This nasty line just changes the extension...
    BINFILE=${file%.cpp}.o
  #  BINFILE=${BINFILE#./}
    g++ -MM -MT \$\(OBJDIR\)/${BINFILE} minion/$file &> /dev/null
    if [ "$?" -eq "0" ]; then
      # Have to run this twice, as storing the result in a variable
      # Loses line endings.
      g++ -MM -MT \$\(OBJDIR\)/${BINFILE} minion/$file >> Makefile.dep.temp
    fi
  fi 
done

if [ -f Makefile.dep ]
   then
   if  diff Makefile.dep Makefile.dep.temp  > /dev/null;
     then
       rm Makefile.dep.temp
     else
       mv Makefile.dep.temp Makefile.dep
   fi
else
   mv Makefile.dep.temp Makefile.dep
fi