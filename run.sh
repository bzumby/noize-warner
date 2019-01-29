#! /bin/bash

while true; do
	soundmeter --profile main --trigger +700 7 \
	--action exec-stop --exec ./make_a_warning.py; sleep 5;
done
