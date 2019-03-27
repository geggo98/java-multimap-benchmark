#! /bin/bash

# Enable strict mode (see http://redsymbol.net/articles/unofficial-bash-strict-mode/)
set -euo pipefail
IFS=$'\n\t'

source ~/.jabba/jabba.sh || { echo "Please install the Jabba JVM version manager: https://github.com/shyiko/jabba" ; exit 1; }

for i in "zulu@1.12.0" "zulu@1.11.0" "openjdk@1.11.0" "adopt-openj9@1.11.0-2" "adopt@1.11.0-2" "zulu@1.8.202" "graalvm@1.0.0-14"
do
  jabba install $i
  jabba use $i
  #java -version 2>&1 | tee $i.txt
  ./sbt clean compile "jmh:run -i 3 -wi 3 -f1 -t1 -rff ${i}.csv"
done

