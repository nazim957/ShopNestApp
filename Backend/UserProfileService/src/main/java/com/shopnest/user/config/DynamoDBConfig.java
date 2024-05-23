//package com.newsapp.user.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//
//@Configuration
//public class DynamoDBConfig {
//
//	@Value("${aws.dynamodb.endpoint}")
//	private String dynamoDBEndPoint;
//
//	@Value("${aws.dynamodb.accesskey}")
//	private String awsAccessKey;
//
//	@Value("${aws.dynamodb.secretkey}")
//	private String awsSecretKey;
//
//	@Bean
//	public DynamoDBMapper dynamoDBMapper() {
//		return new DynamoDBMapper(amazonDymanoDB());
//	}
//
//	private AmazonDynamoDB amazonDymanoDB() {
//		// TODO Auto-generated method stub
//		return AmazonDynamoDBClientBuilder.standard()
//				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndPoint, "eu-north-1"))
//				.withCredentials(amazonDynamoDBCredentials()).build();
//	}
//
//	private AWSCredentialsProvider amazonDynamoDBCredentials() {
//		// TODO Auto-generated method stub
//		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
//	}
//}
