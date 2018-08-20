FROM maven:3.3.3

ARG projectDir
#ARG portName
ARG appName

ADD . /tmp/build/


#构建应用
RUN cd /tmp/build/$projectDir &&  mvn -q -DskipTests=true package \
    && ls && mv target/*.jar /$appName.jar \
    #清理编译痕迹
    && cd / && rm -rf /tmp/build
VOLUME /tmp

#EXPOSE $portName
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom"]
