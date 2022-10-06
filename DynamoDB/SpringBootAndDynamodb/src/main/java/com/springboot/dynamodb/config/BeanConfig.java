package com.springboot.dynamodb.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.github.javafaker.Faker;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.springboot.dynamodb.repo")
public class BeanConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String endpoint;
    @Value("${amazon.aws.access-key}")
    private String accessKey;
    @Value("${amazon.aws.secret-key}")
    private String secretKey;
    @Value("${amazon.aws.region}")
    private String region;

    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(endpoint, region);
    }

    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration())
                .withCredentials(awsCredentialsProvider())
                .build();
    }

    @Bean
    public Faker faker() {
        return new Faker(new Locale("en-US"));
    }
}
