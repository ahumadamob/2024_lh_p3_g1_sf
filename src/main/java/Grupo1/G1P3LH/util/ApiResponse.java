package Grupo1.G1P3LH.util;

public class ApiResponse<T> {

	private String error;
	private T data;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
