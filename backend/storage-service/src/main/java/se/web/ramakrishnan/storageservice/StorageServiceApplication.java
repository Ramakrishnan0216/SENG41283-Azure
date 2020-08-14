package se.web.ramakrishnan.storageservice;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import se.web.ramakrishnan.storageservice.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;


@SpringBootApplication
public class StorageServiceApplication extends SpringBootServletInitializer {

	@Autowired
	CloudBlobContainer blobContainer;


	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

	@Bean
	public Function<Employee,StorageResponse> uploadtoaz(){
		return employee ->upload(employee,blobContainer);

	}

	public StorageResponse upload(Employee employee, CloudBlobContainer cloudBlobContainer){
		URI uri = null;
		boolean error = false;
		CloudBlockBlob blob = null;
		try {
			blob = cloudBlobContainer.getBlockBlobReference(employee.getId()+".txt");
			blob.uploadText(employee.toString());
		//	blob.upload(new ByteArrayInputStream(multipartFile), -1);
			uri = blob.getUri();
		} catch (URISyntaxException e) {
			error =true;
			e.printStackTrace();
		} catch (StorageException e) {
			error =true;
			e.printStackTrace();
		}catch (IOException e) {
			error =true;
			e.printStackTrace();
		}

		return new StorageResponse(uri.toString(),error, HttpStatus.OK.toString());
	}



}
