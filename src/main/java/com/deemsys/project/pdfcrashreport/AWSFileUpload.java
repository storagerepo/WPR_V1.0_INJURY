package com.deemsys.project.pdfcrashreport;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.deemsys.project.common.InjuryProperties;

@Service
public class AWSFileUpload {
	
	@Autowired
	InjuryProperties injuryProperties;

	public void uploadFileToAWSS3(String filePath,String fileName){
		
		try{
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess
		AWSCredentials credentials = new BasicAWSCredentials(
						injuryProperties.getProperty("accessKey"), 
						injuryProperties.getProperty("secretKey"));
		String bucketName =injuryProperties.getProperty("bucketName");
		String folderName =injuryProperties.getProperty("folderName");
		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		System.out.println("Create Connection..........................");
				
				/*// create bucket - name must be unique for all S3 users
				String bucketName = "javatutorial-net-example-bucket";
				s3client.createBucket(bucketName);
				
				// list buckets
				for (Bucket bucket : s3client.listBuckets()) {
					System.out.println(" - " + bucket.getName());
				}
				// create folder into bucket
				String folderName = "testfolder";
				createFolder(bucketName, folderName, s3client);
				*/
				
		// upload file to folder and set it to public
		s3client.putObject(new PutObjectRequest(bucketName, folderName+fileName, 
						new File(filePath))
						.withCannedAcl(CannedAccessControlList.PublicRead));
		System.out.println("File uploaded..........................");
		} catch (AmazonServiceException ase) {
			        System.out.println("Caught an AmazonServiceException, which " +
			        		"means your request made it " +
			                "to Amazon S3, but was rejected with an error response" +
			                " for some reason.");
			        System.out.println("Error Message:    " + ase.getMessage());
			        System.out.println("HTTP Status Code: " + ase.getStatusCode());
			        System.out.println("AWS Error Code:   " + ase.getErrorCode());
			        System.out.println("Error Type:       " + ase.getErrorType());
			        System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
		        System.out.println("Caught an AmazonClientException, which " +
		        		"means the client encountered " +
		                "an internal error while trying to " +
		                "communicate with S3, " +
		                "such as not being able to access the network.");
		        System.out.println("Error Message: " + ace.getMessage());
    }
		
	}
	
	// Delete File In AWS
	public void deleteFileInAWSS3(String fileName) {
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess
		AWSCredentials credentials = new BasicAWSCredentials(injuryProperties.getProperty("accessKey"),injuryProperties.getProperty("secretKey"));
		String bucketName =injuryProperties.getProperty("bucketName");
		String folderName =injuryProperties.getProperty("folderName");
		// create a client connection based on credentials
		System.out.println("Create Connection.......");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		s3client.deleteObject(bucketName, folderName+fileName);
		System.out.println("File Deleted........");
	}
	
}
