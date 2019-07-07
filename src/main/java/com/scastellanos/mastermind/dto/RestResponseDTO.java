package com.scastellanos.mastermind.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is used just to wrapper all the rest resposes, we can set a generic data, a code and message in case of necessary  
 * @author scastellanos
 *
 * @param <T>
 */
public class RestResponseDTO<T> implements Serializable{
	
	/**
	 * If we want to expose a message to the client we can set it in this variable
	 */
	@JsonInclude(Include.NON_NULL)
	private String message;
	
	/**
	 * If we want to expose an error code to the client we can set it in this variable
	 */
	@JsonInclude(Include.NON_NULL)
	private String code;
    
	/**
	 * The response data
	 */
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
