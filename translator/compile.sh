#! /bin/sh
# building Translator without rebuiliding the Parser
cd src
echo "### Compiling TAILOR "
javac -classpath translator/essencePrimeParser/java-cup-11a.jar:. -d ../ translator/essencePrimeParser/*.java translator/expression/*.java translator/normaliser/*.java translator/tailor/*.java  translator/gui/*.java translator/solver/*.java translator/tailor/minion/*.java translator/tailor/gecode/*.java translator/xcsp2ep/parser/*.java translator/xcsp2ep/mapper/functionalsParser/*.java translator/xcsp2ep/mapper/*.java translator/xcsp2ep/*.java  translator/*.java || exit 1
cd ..
