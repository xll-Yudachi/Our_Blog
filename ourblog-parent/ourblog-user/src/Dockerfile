FROM java:8

#将打成的jar包拷贝成名为 ourblog-user的jar包
COPY *.jar /ourblog-user.jar

#默认就是8080
CMD ["--server.port=9002"]

#暴露端口
EXPOSE 9002

#容器运行的命令
ENTRYPOINT ["java","-jar","/ourblog-user.jar"]