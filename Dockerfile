# 使用这个目前国内能直接拉取的地址
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/eclipse-temurin:17-jdk-alpine

# 2. 描述
LABEL authors="liu"

# 3. 设置工作目录
WORKDIR /app

# 4. 把 target 里的 jar 包考进去，并改个短名字
COPY target/*.jar app.jar

# 5. 🌟 重点：处理你之前的头像上传路径
# 在 Docker 里我们创建一个专门存图片的文件夹
RUN mkdir -p /app/upload/student_management

# 6. 暴露 8080 端口
EXPOSE 8080

# 7. 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]