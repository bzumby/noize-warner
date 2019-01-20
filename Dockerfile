FROM ubuntu:latest

LABEL maintainer="valera"

COPY . /app
WORKDIR /app

CMD ["/bin/bash"]