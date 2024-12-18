package com.sparta.impostor.commerce.backend.common.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.s3.region}")
    private String region;
	@Value("${cloud.aws.s3.endpoint:}") // 값이 있는 경우만 로컬스택 S3 서비스 사용
    private String endpoint;

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));

        if (endpoint != null && !endpoint.isEmpty()) {
            builder = builder.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(endpoint, region)
            ).enablePathStyleAccess();
        } else {
            builder = builder.withRegion(region);
        }

        return builder.build();
    }
}
