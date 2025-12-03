package br.com.shiroshima.dto;

public class ResultDTO<T> {
    private boolean success;
    private String message;
    private T data;


    public ResultDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultDTO<T> ok(T data) {
        return new ResultDTO<>(true, null, data);
    }

    public static <T> ResultDTO<T> fail(String message) {
        return new ResultDTO<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
