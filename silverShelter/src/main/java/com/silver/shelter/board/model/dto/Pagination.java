package com.silver.shelter.board.model.dto;

public class Pagination {

	
	private int currentPage;     // 현제 페이지 번호
	private int listCount;       // 전체 게시글 수
	
	private int limit = 10;      // 한 페이지 번호 수
	private int pageSize = 10;   // 보여질 페이지 번호 수 
	
	private int maxPage;			// 마지막 페이지 번호
	private int startPage;			// 보여지는 맨 앞 페이지 번호
	private int endPage;			// 보여지는 맨 뒤 페이지 번호
	
	private int prevPage;			// 이전 페이지 모음의 마지막 번호
	private int nextPage;			// 다음 페이지 모음의 시작 번호
	
	
	
	public Pagination(int currentPage, int listCount) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		calculate();
	}


	public Pagination(int currentPage, int listCount, int limit, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.limit = limit;
		this.pageSize = pageSize;
		calculate();
	}
	
	
	public int getCurrentPage() {
		return currentPage;
	}


	public int getListCount() {
		return listCount;
	}


	public int getLimit() {
		return limit;
	}


	public int getPageSize() {
		return pageSize;
	}


	public int getMaxPage() {
		return maxPage;
	}


	public int getStartPage() {
		return startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public int getPrevPage() {
		return prevPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		calculate();
	}


	public void setListCount(int listCount) {
		this.listCount = listCount;
		calculate();
	}


	public void setLimit(int limit) {
		this.limit = limit;
		calculate();
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		calculate();
	}


	@Override
	public String toString() {
		return "Pagination [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pageSize=" + pageSize + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + "]";
	}
	
	
	
	
	
	
	
	private void calculate() {
		
		
		// 전체 페이지 수 계산
	    // 전체 게시글 수를 한 페이지에 보여줄 게시글 수로 나누고, 올림하여 정수로 변환합니다.
	    // 예를 들어, 게시글 수가 105개이고 페이지당 10개의 게시글을 보여줄 경우, 총 페이지 수는 11페이지가 됩니다.
	    maxPage = (int)Math.ceil((double)listCount / limit);
	    
	    
	    // 현재 페이지 그룹의 시작 페이지 번호 계산
	    // (현재 페이지 번호 - 1)을 페이지 그룹의 크기로 나누고, 페이지 그룹의 크기만큼 곱한 후 1을 더합니다.
	    // 예를 들어, 현재 페이지가 15이고, 페이지 그룹 크기가 10이라면, 시작 페이지는 11이 됩니다.
	    startPage = (currentPage - 1) / pageSize * pageSize + 1;
	    
	    
	    // 현재 페이지 그룹의 끝 페이지 번호 계산
	    // 시작 페이지 번호에 페이지 그룹 크기를 더한 후 1을 뺍니다.
	    // 예를 들어, 시작 페이지가 11이고, 페이지 그룹 크기가 10이라면, 끝 페이지는 20이 됩니다.
	    endPage = startPage + pageSize - 1;
	    
	    
	    // 끝 페이지 번호가 전체 페이지 수를 넘지 않도록 설정
	    // 만약 끝 페이지가 전체 페이지 수를 초과하면, 끝 페이지 번호를 전체 페이지 수로 설정합니다.
	    if (endPage > maxPage) endPage = maxPage;
	    
	    
	    // 이전 페이지 그룹의 마지막 페이지 번호 계산
	    // 현재 페이지가 첫 번째 페이지 그룹에 속할 경우, 이전 페이지 그룹의 마지막 페이지는 1로 설정합니다.
	    // 그렇지 않은 경우, 시작 페이지에서 1을 뺀 값을 이전 페이지 그룹의 마지막 페이지 번호로 설정합니다.
	    if (currentPage <= pageSize) {
	        prevPage = 1; // 첫 페이지 그룹일 경우 1로 설정
	    } else {
	        prevPage = startPage - 1; // 그렇지 않으면 이전 페이지 그룹의 마지막 페이지 번호
	    }
	    
	    
	    // 다음 페이지 그룹의 시작 페이지 번호 계산
	    // 끝 페이지가 전체 페이지 수와 같을 경우, 다음 페이지 그룹의 시작 페이지는 전체 페이지 수로 설정합니다.
	    // 그렇지 않은 경우, 끝 페이지에 1을 더한 값을 다음 페이지 그룹의 시작 페이지 번호로 설정합니다.
	    if (endPage == maxPage) {
	        nextPage = maxPage; // 마지막 페이지 그룹일 경우 전체 페이지 수로 설정
	    } else {
	        nextPage = endPage + 1; // 그렇지 않으면 다음 페이지 그룹의 시작 페이지 번호
	    }
		
		
		
		
		
		
		
		
		
	}
	
}
