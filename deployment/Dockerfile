### Dockerfile

FROM openjdk:8

COPY . /usr/src/stocks

CMD java -Xmx512m -Dapp.home=/usr/src/stocks/home -jar /usr/src/stocks/stocks.jar -port $PORT