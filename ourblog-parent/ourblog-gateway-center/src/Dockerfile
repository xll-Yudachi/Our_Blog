FROM java:8

#将打成的jar包拷贝成名为 ourblog-com.ourblog.gateway-center的jar包
COPY *.jar /ourblog-gateway-center.jar

#默认就是8080
CMD ["--server.port=9000"]

#暴露端口
EXPOSE 9000

#容器运行的命令
ENTRYPOINT ["java","-jar","/ourblog-gateway-center.jar"]