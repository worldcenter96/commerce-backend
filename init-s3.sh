#!/bin/bash

echo "Waiting for LocalStack to be ready..."
until awslocal s3 ls > /dev/null 2>&1; do
  echo "Waiting for S3 service..."
  sleep 5
done

# S3 Bucket 생성
awslocal s3 mb s3://local-bucket
echo "S3 Bucket 'local-bucket' created"