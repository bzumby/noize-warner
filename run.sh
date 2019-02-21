#! /bin/bash

while true; do
	if [ -f suspend.execution ]; then
		# stop and wait 15 minutes
		echo "Waiting..." \
		&& echo "Will continue in 15 minutes"\
		&& sleep 900 \
		&& rm stop.talking
	else
		# run noize warner checks
		soundmeter --profile main --trigger +700 7 \
		--action exec-stop --exec ./make_a_warning.py; sleep 5;
	fi
done
