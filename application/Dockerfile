FROM harbor.dfocus.co/base/openjdk:8u131

MAINTAINER baozi

ARG JAR_FILE
ADD ./target/$JAR_FILE app.jar

VOLUME /tmp

ENTRYPOINT java ${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm -jar  app.jar