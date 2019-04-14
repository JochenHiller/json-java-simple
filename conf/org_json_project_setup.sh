#!/bin/bash

# Will create a project org.json with latest versions to be used in an IDE
# run with: ./conf/org_json_project_setup.sh


DEST_DIR=../org.json

echo "Setup project org.json in $DEST_DIR"

(
rm -rf $DEST_DIR
mkdir -p $DEST_DIR
mkdir -p $DEST_DIR/git
cd $DEST_DIR/git
git clone https://github.com/stleary/JSON-java
git clone https://github.com/stleary/JSON-Java-unit-test
rm -rf JSON-java/.git
rm -rf JSON-Java-unit-test/.git

mv JSON-Java-unit-test/* ..
mv ../LICENSE.txt ../LICENSE-tests.txt
mv ../README.md ../README-tests.md
mkdir -p ../src/main/java/org/json
mv ./JSON-java/*.java ../src/main/java/org/json
mv ./JSON-java/LICENSE ..
mv ./JSON-java/README.md ..
cd ..
rm -rf ./git
cd ..
)

cp -r ./conf/org.json/.[cps]* $DEST_DIR
mv $DEST_DIR/build.gradle $DEST_DIR/build.gradle.ORI
cp ./conf/org.json/build.gradle $DEST_DIR

(
cd $DEST_DIR
gradle clean build
)
echo "Setup project org.json in $DEST_DIR ==> DONE"
