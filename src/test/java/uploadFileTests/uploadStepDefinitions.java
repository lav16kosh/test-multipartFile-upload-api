package uploadFileTests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

//API Testing: Test MultipartFile upload Request using Java, Spring framework and cucumber
public class uploadStepDefinitions{
	
	public String filePath="src/test/resources/Files";
	public String contentType="application/json";
	public String parameterName="file";
	public ResponseEntity<String> response;

	@Given("Invoke request to add single file {string}")
	public void invoke_request_to_add_single_file(String file) throws IOException {
		MultiValueMap<String,Object> body=new LinkedMultiValueMap();
		
		//Add file in ByteArray to body
		body.add(parameterName, getFileInByteArray(file));
		
		//Set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		//Add body and headers to HttpEntity
		HttpEntity<MultiValueMap<String, Object>> entity= new HttpEntity<>(body,headers);
		
		//Post the request
		response=new RestTemplate().postForEntity("http://localhost:8080/upload", entity, String.class);
	}
	
	@Then("Verify the files count is {int} in the response")
	public void verify_the_files_count_is_in_the_response(Integer count) throws ParseException {
		JSONObject obj= new JSONObject(response.getBody());
		int cnt=obj.getInt("count");
		assertEquals(true,cnt==count);
	}

	@Then("Verify response is {int}")
	public void verify_response_is(Integer code) {
	    assertEquals(true,code==response.getStatusCodeValue());
	}

	@Given("Invoke request to add multiple files <File>")
	public void invoke_request_to_add_multiple_files_file(io.cucumber.datatable.DataTable dataTable) throws IOException {
		List<String> data = dataTable.asList();
		
		MultiValueMap<String,Object> body=new LinkedMultiValueMap();
		
		//Add multiple files in ByteArray to body
		for (int i=1;i<data.size();i++) {
			body.add(parameterName, getFileInByteArray(data.get(i)));
		}
		
		//Set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		//Add body and headers to HttpEntity
		HttpEntity<MultiValueMap<String, Object>> entity= new HttpEntity<>(body,headers);
		
		//Post the request
		response=new RestTemplate().postForEntity("http://localhost:8080/upload", entity, String.class);
		
	}


	//Get File in ByteArray
	public HttpEntity<byte[]> getFileInByteArray(String fileName) throws IOException{
		//Convert File to MultipartFile
		MultipartFile file=getMultipartFile(fileName);
		
		//Get byteArray of the file
		LinkedMultiValueMap<String,String> headerMap = new LinkedMultiValueMap<>();
		headerMap.add("Content-disposition", "form-data; name=" + parameterName + "; filename=" +file.getOriginalFilename());
		headerMap.add("Content-type", contentType);
		HttpEntity<byte[]> doc = new HttpEntity<byte[]>(file.getBytes(),headerMap);
		return doc;
	}
	
	//Convert File to MultipartFile
	public MultipartFile getMultipartFile(String fileName) {
		Path path=Paths.get(filePath);
		String name= fileName;String originalFileName = fileName;String contType = contentType;
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		}catch (final IOException e) {}
		MultipartFile result = new MockMultipartFile(name, originalFileName, contType, content);
		return result;
	}
}