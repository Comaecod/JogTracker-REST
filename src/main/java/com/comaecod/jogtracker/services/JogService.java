package com.comaecod.jogtracker.services;

import java.util.List;

import com.comaecod.jogtracker.payloads.AllJogPaginationResponse;
import com.comaecod.jogtracker.payloads.JogDTO;

public interface JogService {
	JogDTO createJogData(JogDTO dto, String userId, Integer categoryId);

	JogDTO updateJogData(JogDTO dto, Integer jogId);

	void deleteJogData(Integer jogId);

	JogDTO getOneJogDataById(Integer jogId);

	// Get all jog data - Paginated(with metadata of pages) - @Comaecod
	// @deprecated, using payload class for metadata for pagination
	// List<JogDTO> getAllJogData(Integer pageNumber, Integer pageSize);
	AllJogPaginationResponse getAllJogData(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// Get all jog data by User - Paginated(with metadata of pages) - @Comaecod
	AllJogPaginationResponse getJogDataByUser(String userId, Integer pageNumber, Integer pageSize);

	// Get the jog data by category
	List<JogDTO> getJogDataByCategory(Integer categoryId);

	List<JogDTO> getAllJogDataBySearch(String keyword);

}
