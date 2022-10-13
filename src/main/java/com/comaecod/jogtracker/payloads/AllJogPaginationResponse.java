package com.comaecod.jogtracker.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AllJogPaginationResponse {
	
	private List<JogDTO> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private long totalRecords;
	
	private int totalPages;
	
	private boolean lastPage;
}
