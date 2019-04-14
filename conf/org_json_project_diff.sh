#!/bin/bash

# Will create a diff with project org.json to see about changes done
# will only check tests at the moment, the impl needs more work
# to extract the inner classes before doing a diff
# run with: ./conf/org_json_project_diff.sh

DEST_DIR=../org.json

echo "Diff against project org.json in $DEST_DIR"

(
echo "Diff tests..."
diff -r ./src/test/java/org/json $DEST_DIR/src/test/java/org/json
) 2>&1 | tee ./build/diff.txt
