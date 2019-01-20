FROM ubuntu:latest

LABEL maintainer="valera"

COPY . /app
WORKDIR /app

RUN apt-get update && \
apt-get install -y \
libasound-dev portaudio19-dev libportaudio2 \
libportaudiocpp0 pulseaudio mplayer alsa-utils libpulse0 python-dev \
python python-pip && \
pip install --upgrade setuptools && \
pip install virtualenv

RUN mkdir ~/.soundmeter && \
    cp config ~/.soundmeter/config

CMD ["/bin/bash"]