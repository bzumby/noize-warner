#! /bin/bash

while true; do
	soundmeter --profile main --trigger +40 5 \
	--action exec-stop --exec ./make_a_warning.py; sleep 5;
done
