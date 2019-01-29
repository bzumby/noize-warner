FROM ubuntu:latest

LABEL maintainer="valera"

COPY . /app
WORKDIR /app

RUN apt-get update && \
apt-get install -y \
libasound-dev portaudio19-dev libportaudio2 \
libportaudiocpp0 pulseaudio mplayer alsa-utils \
libpulse0 mpg123 python-dev \
python python-pip && \
pip install --upgrade setuptools

RUN mkdir ~/.soundmeter && \
    cp config ~/.soundmeter/config

RUN pip install -r requirements.txt

CMD ["/bin/bash", "./run.sh"]
