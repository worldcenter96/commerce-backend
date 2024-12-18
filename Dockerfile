#################### ADMIN ######################

# 1. JDK 17.0.1을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:17.0.1-jdk-slim AS admin

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./admin/build/libs/admin.jar /app

# 4. 애플리케이션 포트 노출
EXPOSE 8080

# 5. 애플리케이션 실행 명령
CMD ["java", "$JAVA_OPTS", "-jar", "admin.jar"]

#################### B2B ######################

# 1. JDK 17.0.1을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:17.0.1-jdk-slim AS b2b

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./b2b/build/libs/b2b.jar /app

# 4. 애플리케이션 포트 노출
EXPOSE 8080

# 5. 애플리케이션 실행 명령
CMD ["java", "$JAVA_OPTS", "-jar", "b2b.jar"]

#################### B2C ####################

# 1. JDK 17.0.1을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:17.0.1-jdk-slim AS b2c

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./b2c/build/libs/b2c.jar /app

# 4. 애플리케이션 포트 노출
EXPOSE 8080

# 5. 애플리케이션 실행 명령
CMD ["java", "$JAVA_OPTS", "-jar", "b2c.jar"]
