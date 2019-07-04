package com.scastellanos.mastermind.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestResponseDTO<T> implements Serializable{
	
	@JsonInclude(Include.NON_NULL)
	private String message;
	
	@JsonInclude(Include.NON_NULL)
	private String code;
    
	private T data;

    public RestResponseDTO(){
        
    }
    public RestResponseDTO(T data) {
        this.data = data;
    }
    
    public RestResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }
    
    public RestResponseDTO(String code,String message, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }


    public void setSuccess(boolean success) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
