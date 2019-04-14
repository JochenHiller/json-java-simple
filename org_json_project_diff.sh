#!/bin/bash

DEST_DIR=../org.json

echo "Diff against project org.json in $DEST_DIR"

(
echo "Diff tests..."
diff -r ./src/test/java/org/json $DEST_DIR/src/test/java/org/json
) 2>&1 | tee ./build/diff.txt
