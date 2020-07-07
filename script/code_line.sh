#! /bin/bash

# 已完成 PCK 组件: PckBadge、PckButton、PckCheckBox、PckDialog、PckIcon、PckMessage、PckProgress、PckSwitch、PckTag、PckToast

if [[ ! ${1} ]]; then
    echo "  usage: $0 \${DIR}"
    exit 0
fi

dir=$1

cat $(find ${dir} -name "*.java") | wc -l

