#!/bin/bash

./recursiveSearch.sh "the" "and" "in" | sort > tempfile
diff tempfile output3
rm tempfile

