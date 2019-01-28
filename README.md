# noize-warner

It will replace some funtionality of Anton K. =)

Monitors a noize level and triggers an event if the level is too high.
The solution is based on soundmeter + simple python script + appriate launch with bash :)

Setup:
1) Install os packages requirements for soundmeter (https://pypi.org/project/soundmeter/)
2.0) Might be needed: pip install --upgrade setuptools
2.1) For Ubuntu: sudo apt-get install python3-dev mpg123
3) pip install -r requirements.txt
4) Place a config file at ~/.soundmeter/config or make a symlink:
mkdir ~/.soundmeter && cp config ~/.soundmeter/config 
It should contain rms_as_trigger_arg = True - needed to pass rms value to out trigger script

It has some issues: soundmeter won't wait while the trigger ends and will continue to trigger and trigger and triger
How to launch in a operable way:

while true; do soundmeter --profile main --trigger +4000 5 --action  exec-stop --exec ./make_a_warning.py; sleep 5; done

+4000 - the enrance level. ./make_a_warning.py contains all logic for choosing what action to take next (depending on rms level)
if rms will be above 4000 5 timew in raw, it will trigger ./make_a_warning.py
So above loop will restart soundmeter. The voice messages are short so 5 sec is enough.

P.S.: If you have some free time, fix soundmeter behaviour ;)

JFI: 
Допустимые уровни шума http://www.acoustic.ua/directory/135

--- from Yan

1. Install paprefs
2. Enable network access (UI) - reboot may be needed
3. run 'pax11publish' and find out the port in 'Server' section

4. Run container like this. IPADDRESS is the IP of your host machine. 
Port is the one from  pax11publish output
docker run -it --device=/dev/snd -e PULSE_SERVER=tcp:<IPADDRESS>:4713



OR
1. add this to /etc/pulse/default.pa on HOST

 load-module module-native-protocol-tcp auth-ip-acl=172.17.0.1/24 auth-anonymous=1

2. restart PulseAudio on HOST:
pulseaudio -k && pulseaudio --start

OR 

1. On Host, create conf file in $HOME and include main default.pa there:

Ubutnu:
echo "
.include /etc/pulse/default.pa

load-module module-native-protocol-tcp auth-ip-acl=172.17.0.1/24 auth-anonymous=1
" > ~/.config/pulse/default.pa

IP is the LAN subnet for which you allow the access from 

Mac:

echo "
.include /usr/local/etc/pulse/default.pa

load-module module-native-protocol-tcp auth-ip-acl=172.17.0.1/24 auth-anonymous=1
" > ~/.config/pulse/default.pa

2. Run docker with this:

docker run -it -e PULSE_SERVER=tcp:<IPADDRESS>

## this seems to be odd

docker run -it --device=/dev/snd -e PULSE_SERVER=tcp image <command>

Only <IPADDRESS> (and NO port) is needed to run the sound correctly


For Mac:
Pre-requisites 
1. Install pulseaudio:
brew install pulseaudio
2. start pulse audio with: pulseaudio -D

It seems that it is necessary to run pulseaudio -D before each start of 'docker run'. At least on mac

---
Dont forget to copy/paste Kubernetes config from $HOME/.kube to Jenkins user $HOME
