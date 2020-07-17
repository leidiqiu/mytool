#! /bin/bash

if [[ ! ${1} ]]; then
    echo "  usage: $0 \${DIR}"
    exit 0
fi

dir=$1

cat $(find ${dir} -name "*.java") | wc -l

