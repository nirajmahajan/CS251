#!/bin/bash

./recursiveSearch.sh "the" "and" "in" "end"| sort > tempfile
diff tempfile output4
rm tempfile




