package com.comaecod.jogtracker.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comaecod.jogtracker.config.AppConstantsDefaults;
import com.comaecod.jogtracker.payloads.AllJogPaginationResponse;
import com.comaecod.jogtracker.payloads.ApiResponse;
import com.comaecod.jogtracker.payloads.JogDTO;
import com.comaecod.jogtracker.services.FileService;
import com.comaecod.jogtracker.services.JogService;

@RestController
@RequestMapping("/api/")
public class JogController {

	@Autowired
	private JogService jogService;

	@Autowired
	private FileService fileService;

	// Path for storing uploaded images
	@Value("${project.image}")
	private String path;

	// POST -> Create Jog Data with Category 
	@PostMapping("/user/{userId}/category/{categoryId}/jogdata")
	public ResponseEntity<JogDTO> createJogData(@RequestBody JogDTO dto, @PathVariable String userId,
			@PathVariable Integer categoryId) {
		JogDTO createdJogData = jogService.createJogData(dto, userId, categoryId);
		return new ResponseEntity<JogDTO>(createdJogData, HttpStatus.CREATED);
	}

	// GET -> Get All Jog Data by User -> Paginated + Metadata
	@GetMapping("/user/{userId}/jogdata")
	public ResponseEntity<AllJogPaginationResponse> getJogsByUser(@PathVariable String userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstantsDefaults.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstantsDefaults.PAGE_SIZE, required = false) Integer pageSize) {
		AllJogPaginationResponse allJogdataByUser = jogService.getJogDataByUser(userId, pageNumber, pageSize);
		return new ResponseEntity<AllJogPaginationResponse>(allJogdataByUser, HttpStatus.OK);
	}

	// GET -> Get All Jog Data By Category -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/category/{categoryId}/jogdata")
	public ResponseEntity<List<JogDTO>> getJogsByCategory(@PathVariable Integer categoryId) {
		List<JogDTO> jogDataByCategory = jogService.getJogDataByCategory(categoryId);
		return new ResponseEntity<List<JogDTO>>(jogDataByCategory, HttpStatus.OK);
	}

	// GET -> Get All Jog Data -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/jogdata")
	public ResponseEntity<AllJogPaginationResponse> getAllJogData(
			@RequestParam(value = "pageNumber", defaultValue = AppConstantsDefaults.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstantsDefaults.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstantsDefaults.SORT_FIELD, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstantsDefaults.SORT_DIRECTION, required = false) String sortDirection) {
		AllJogPaginationResponse allJogData = jogService.getAllJogData(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<AllJogPaginationResponse>(allJogData, HttpStatus.OK);
	}

	// GET -> Get Jog Data By ID
	@GetMapping("/jogdata/{jogId}")
	public ResponseEntity<JogDTO> getJogDataById(@PathVariable Integer jogId) {
		JogDTO jogData = jogService.getOneJogDataById(jogId);
		return new ResponseEntity<JogDTO>(jogData, HttpStatus.OK);
	}

	//DELETE -> Delete Jog Data by ID
	@DeleteMapping("/jogdata/{jogId}")
	public ApiResponse deleteJogData(@PathVariable Integer jogId) {
		jogService.deleteJogData(jogId);
		return new ApiResponse("Jog Data with ID: " + jogId + " has been deleted successfully!", true);
	}

	// PUT -> Update Jog Data by ID
	@PutMapping("/jogdata/{jogId}")
	public ResponseEntity<JogDTO> updateJogData(@RequestBody JogDTO dto, @PathVariable Integer jogId) {
		JogDTO updateJogData = jogService.updateJogData(dto, jogId);
		return new ResponseEntity<JogDTO>(updateJogData, HttpStatus.OK);
	}

	// GET -> SEARCH Jog Data by Keyword Params
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/jogdata/search/{keyword}")
	public ResponseEntity<List<JogDTO>> searchJogByLocation(@PathVariable String keyword) {
		List<JogDTO> result = jogService.getAllJogDataBySearch(keyword);
		return new ResponseEntity<List<JogDTO>>(result, HttpStatus.OK);
	}

	// POST -> UPLOAD Jog Location Image
	@PostMapping("/jogdata/image/upload/{jogId}")
	public ResponseEntity<JogDTO> uploadJogLocationImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer jogId) throws IOException {
		JogDTO jogdataDTO = jogService.getOneJogDataById(jogId);
		String fileName = fileService.uploadImage(path, image);
		jogdataDTO.setLocationImg(fileName);
		JogDTO updatedJogData = jogService.updateJogData(jogdataDTO, jogId);
		return new ResponseEntity<JogDTO>(updatedJogData, HttpStatus.OK);
	}

	// GET -> Fetch Uploaded Image
	@GetMapping(value = "/jogdata/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
