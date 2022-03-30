package controller;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.Directive;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import model.Metadata;
import model.Network;
import model.Node;
import model.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;


/**
 * @author Antonio Castronuovo
 *
 */
public class FileManager {

	private String path = "http://127.0.0.1:9000";
	private String usr = "administrator";
	private String pwd = "administrator";
	private Network network;

	public FileManager(Network network) {
		this.network = network;
	}

	private MinioClient connect() {
		// Create and return a minioClient with the MinIO server playground, its access key and secret key.
		return MinioClient.builder()
				.endpoint(path)
				.credentials(usr, pwd)
				.build();
	}

	/**
	 * @param bucketName
	 * @param objectName
	 * @param filePath
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public void upload(String bucketName, String objectName, String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {
			MinioClient minioClient = connect();
			// Make 'milantrip' bucket if not exist.
			boolean found =
					minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
			if (!found) {
				// Make a new bucket called 'asiatrip'.
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

			} else {
				System.out.println("Bucket "+ bucketName + " already exists.");
			}

			ListMultimap<String, String> userMetadata = createMetadataMap(network.getNodes().get(0),filePath,bucketName, ZonedDateTime.now());
			// Upload the file at the filePath position as object name objectName to bucket bucketName

			minioClient.uploadObject(
					UploadObjectArgs.builder()
					.bucket(bucketName)
					.object(objectName)
					.filename(filePath)
					.userMetadata(userMetadata)
					.build());

			System.out.println(objectName + " is successfully uploaded to bucket " + bucketName + ".");
		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace: " + e.httpTrace());
		}
	}

	/**
	 * @param bucketName
	 * @param objectName
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public void delete(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException{
		try {
			MinioClient minioClient = connect();
			// Check if bucket already exists.
			boolean found =
					minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
			if (found) {
				// Delete the object in the bucket 
				minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
				// Delete the bucket.
				minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
				System.out.println(
						objectName + " is successfully deleted from "
								+ "bucket " + bucketName + ".");
			} else {
				System.out.println("Bucket " + bucketName + " does not exist.");
			}

		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace: " + e.httpTrace());
		}
	}

	/**
	 * @param bucketName
	 * @param objectName
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws MinioException
	 */
	public void getAttributes(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException{
		MinioClient minioClient = connect();
		// Check if bucket already exists.
		boolean found =
				minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		if (found) {
			StatObjectResponse  stats =  minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
			System.out.println(stats.toString()+ " " + stats.headers());
			System.out.println("--------------------------------------------------------------------");
		} else {
			System.out.println("Bucket "+ bucketName + " does not exist.");
		}
	}

	/**
	 * @param bucketName
	 * @param objectName
	 * @param filePath
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws MinioException
	 */
	public void updateObject(String bucketName, String objectName, String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException{
		MinioClient minioClient = connect();
		// Check if bucket already exists.
		boolean found =
				minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

		if (found) {
			// Delete the object in the bucket 
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());

			// Upload from the 'filePath' the file as object name 'objectName' to bucket 'bucketName'.    
			
			minioClient.uploadObject(
					UploadObjectArgs.builder()
					.bucket(bucketName)
					.object(objectName)
					.filename(filePath)
					.build());
			//UpdateMetadata
			updateMetadata(bucketName, objectName, filePath);

			System.out.println("Object " + objectName +" in bucket "+ bucketName + " has been updated");
		} else {
			System.out.println("Bucket " + bucketName + " does not exist.");
		}
	}

	/**
	 * @param bucketName
	 * @param objectName
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws MinioException
	 */
	public boolean updateMetadata(String bucketName, String objectName, String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException{
		MinioClient minioClient = connect();
		// Check if bucket already exists.
		boolean found =
				minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		StatObjectResponse stats =  minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

		if (found) {     
			ListMultimap<String, String> userMetadata = createMetadataMap(network.getNodes().get(0) ,filePath,bucketName, ZonedDateTime.parse(stats.headers().get("x-amz-meta-insertiondate")));
			minioClient.copyObject(
					CopyObjectArgs.builder()
					.bucket(bucketName)
					.object(objectName)
					.source(
							CopySource.builder()
							.bucket(bucketName)
							.object(objectName)
							.build())
					.userMetadata(userMetadata)
					.metadataDirective(Directive.REPLACE)
					.build());
			System.out.println("Metadata of object " +  objectName + " in bucket " + bucketName + " have been updated");
		} else {
			System.out.println("Bucket " + bucketName + " does not exist.");
		}
		return true;
	}

	/**
	 * @param filePath
	 * @param bucketName
	 * @param insertionDate
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws MinioException
	 */
	public ListMultimap<String,String> createMetadataMap(Node node, String filePath, String bucketName, ZonedDateTime insertionDate) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException{
		
		Metadata metadata = new Metadata(insertionDate,false, node, new Value());

		File myFile = new File(filePath);
		Path path = myFile.toPath();
		BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);

		metadata.setLastUsage(ZonedDateTime.ofInstant(basicFileAttributes.lastAccessTime().toInstant(),ZoneId.systemDefault()));

		ListMultimap<String, String> userMetadata = ArrayListMultimap.create();
		userMetadata.put("InsertionDate", metadata.getInsertionDate().toString());
		userMetadata.put("RetentionLimit", metadata.getRetentionLimit().toString());
		userMetadata.put("OwnerNode", metadata.getNode().toString());
		userMetadata.put("IsCopy", metadata.getCopy().toString());
		userMetadata.put("IsDefective", metadata.getDefective().toString());
		userMetadata.put("IsDisposable", metadata.getDisposable().toString());
		userMetadata.put("IsOffLimits", metadata.getOffLimits().toString());
		userMetadata.put("Value", metadata.getValue().toString());
		userMetadata.put("LastUsage", metadata.getLastUsage().toString());

		return userMetadata;
	}
}



