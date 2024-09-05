package org.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sagemakerruntime.SageMakerRuntimeClient;
import software.amazon.awssdk.services.sagemakerruntime.model.InvokeEndpointRequest;
import software.amazon.awssdk.services.sagemakerruntime.model.InvokeEndpointResponse;

import java.nio.charset.Charset;

public class SentimentAnalysis {
    public static String getPrediction(String text) {
        System.out.println(text);
        String endpointName = "your-sagemaker-endpoint-name";
        String contentType = "application/x-text";
        // make sure that you specify the region where you deployed endpoint
        Region region = Region.US_EAST_1;
        // make sure the role has permissions to invoke a correct endpoint
        AwsBasicCredentials credentials = AwsBasicCredentials.create("accessKeyId", "secretAccessKey");
        SageMakerRuntimeClient runtimeClient = SageMakerRuntimeClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(region)
                .build();

        // Create request
        InvokeEndpointRequest endpointRequest = InvokeEndpointRequest.builder()
                .endpointName(endpointName)
                .contentType(contentType)
                .body(SdkBytes.fromString(text, Charset.defaultCharset()))
                .build();

        // Invoke the endpoint
        InvokeEndpointResponse response = runtimeClient.invokeEndpoint(endpointRequest);

        // Handle response
        String responseBody = response.body().asString(Charset.defaultCharset());

        // Parse and handle the response as needed
        runtimeClient.close();
        return responseBody;
    }
}
