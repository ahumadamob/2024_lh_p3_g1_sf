package Grupo1.G1P3LH.util;

import java.util.ArrayList;
import java.util.List;


public class DTOresponse<T> {

	private int status;
	private List<String> message;
	private T data;

	public DTOresponse(int status, List<String> message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public DTOresponse(int status, String message, T data) {
		super();
		this.status = status;
		List<String> messages = new ArrayList();
		messages.add(message);
		this.message = messages;
		this.data = data;
	}
	
	public DTOresponse() {
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	}
