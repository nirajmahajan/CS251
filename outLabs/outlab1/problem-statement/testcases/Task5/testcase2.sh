#!/bin/bash

./recursiveSearch.sh "the" "and" | sort > tempfile
diff tempfile output2
rm tempfile




