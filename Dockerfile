

#################### ADMIN ######################

# 1. JDK 11을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:11-jre-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./target/admin.jar /app/admin.jar

# 4. JVM 옵션 환경 변수 설정
ENV JAVA_OPTS=" -Xms512m -Xmx1024m -XX:+UseG1GC -Dspring.profiles.active=prod"

# 5. 애플리케이션 포트 노출
EXPOSE 8080

# 6. 헬스체크 추가 (Spring Boot Actuator)
HEALTHCHECK --interval=30s --timeout=10s --retries=3
CMD curl --fail http://localhost:8080/actuator/health || exit 1

# 7. 애플리케이션 실행 명령
CMD ["java", "$JAVA_OPTS", "-jar", "admin.jar"]

# 8. 비궡한 사용자 설정 (보안 강화)
RUN useradd -m appuser && chown -R appuser /app \
    USER appuser

# 9. 실행 명령어
CMD ["java", "$JAVA_OPTS", "-jar", "admin.jar"]






#################### B2B ######################

# 1. JDK 11을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:11-jre-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./target/b2b.jar /app/b2b.jar

# 4. JAR 파일에 실행 권한 부여
RUN chmod +x /app/b2b.jar

# 5. JVM 옵션 환경 변수 설정
ENV JAVA_OPTS=" -Xms512m -Xmx1024m -XX:+UseG1GC -Dspring.profiles.active=prod"

# 6. 애플리케이션 포트 노출
EXPOSE 8081

# 7. 애플리케이션 실행 명령
CMD ["java","$JAVA_OPTS", "-jar", "b2b.jar"]

# 8. 헬스체크 추가 (Spring Boot Actuator)
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl --fail http://localhost:8081/actuator/health || exit 1





#################### B2C ####################

# 1. JDK 11을 포함하는 OpenJDK 이미지 기반으로 시작
FROM openjdk:11-jre-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./target/admin.jar /app/b2c.jar

# 4. JAR 파일에 실행 권한 부여
RUN chmod +x /app/b2c.jar

# 5. JVM 옵션 환경 변수 설정
ENV JAVA_OPTS=" -Xms512m -Xmx1024m -XX:+UseG1GC -Dspring.profiles.active=prod"

# 6. 애플리케이션 포트 노출
EXPOSE 8082

# 7. 애플리케이션 실행 명령
CMD ["java", "$JAVA_OPTS", "-jar", "b2c.jar"]

# 8. 헬스체크 추가 (Spring Boot Actuator)
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl --fail http://localhost:8082/actuator/health || exit 1



