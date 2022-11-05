FROM python:3

WORKDIR /usr/src/app

# RUN apt update
# RUN apt install -y wget

COPY ./requirements.txt ./requirements.txt
RUN pip install --no-cache-dir -r requirements.txt


COPY ./source/ ./
EXPOSE 4080

CMD [ "/bin/bash", "start.sh" ]
