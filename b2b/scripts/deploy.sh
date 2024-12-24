#!/bin/bash

# 프로세스가 실행 중이라면 종료
pids=$(ps aux | grep 'java -jar' | grep -v grep | awk '{print $2}')
if [ -n "$pids" ]; then
    echo "Killing process with PID: $pids"
    echo "$pids" | xargs kill -9
else
    echo "No java processes found to kill."
fi

# b2b jar 파일 실행
nohup java -jar /opt/impostor/b2b/b2b.jar --spring.profiles.active=prod > /opt/impostor/b2b/application.log 2>&1 &

echo "B2B Deployment complete!"