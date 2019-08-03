#!/bin/bash

./recursiveSearch.sh "the" | sort > tempfile
diff tempfile output1
rm tempfile




