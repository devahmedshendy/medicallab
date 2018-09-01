package medicallab.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import medicallab.misc.Uri;

@Controller
@RequestMapping("/uri")
public class UriController {
	
	private final Uri uri;
	
	public UriController(Uri uri) {
		this.uri = uri;
	}

	@GetMapping
	public ResponseEntity<String> getUri(@RequestParam Map<String, String> params) {
		String result = uri.get(params.values().toArray(new String[params.size()]));
		
		return (result.length() == 0) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(result, HttpStatus.OK); 
	}
}
