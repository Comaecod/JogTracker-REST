package com.comaecod.jogtracker.services;

import java.util.List;

import com.comaecod.jogtracker.payloads.AllJogPaginationResponse;
import com.comaecod.jogtracker.payloads.JogDTO;

public interface JogService {
	JogDTO createJogData(JogDTO dto, String userId, Integer categoryId);

	JogDTO updateJogData(JogDTO dto, Integer jogId);

	void deleteJogData(Integer jogId);

	JogDTO getOneJogDataById(Integer jogId);

//	List<JogDTO> getAllJogData(Integer pageNumber, Integer pageSize); @deprecated, using payload class for metadata for pagination
	AllJogPaginationResponse getAllJogData(Integer pageNumber, Integer pageSize);

	List<JogDTO> getJogDataByUser(String userId); // Get the jog data by user

	List<JogDTO> getJogDataByCategory(Integer categoryId); // Get the jog data by category

	List<JogDTO> getAllJogDataBySearch(String keyword);

}
