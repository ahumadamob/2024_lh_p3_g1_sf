package Grupo1.G1P3LH.util;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase DTOResponse representa una respuesta genérica para una API.
 * Utiliza un tipo genérico <T> para la propiedad 'data', lo que permite
 * manejar diferentes tipos de datos en la respuesta de manera flexible.
 *
 * @param <T> El tipo de dato que contendrá la respuesta, permitiendo
 *            la personalización según las necesidades de la API.
 */
public class DTOResponse<T> {

    // Código de estado de la respuesta HTTP (por ejemplo, 200 para éxito, 404 para no encontrado, 500 para error interno).
    private int status;
    
    // Lista de mensajes de error, si existen, para proporcionar detalles sobre fallos.
    private List<String> error;
    
    // El cuerpo de la respuesta que contiene los datos, de tipo genérico T.
    private T data;

    /**
     * Constructor que inicializa la respuesta con el código de estado, la lista de errores y los datos.
     *
     * @param status Código de estado HTTP que indica el resultado de la solicitud.
     * @param error Lista de mensajes de error que pueden estar presentes en la respuesta.
     * @param data Los datos que contiene la respuesta, de tipo T, que puede ser cualquier tipo de objeto.
     */
    public DTOResponse(int status, List<String> error, T data) {
        super();
        this.status = status;
        this.error = error;
        this.data = data;
    }

    /**
     * Constructor alternativo que inicializa la respuesta con un único mensaje de error en vez de una lista.
     * Este constructor crea automáticamente una lista que contiene el mensaje de error proporcionado.
     *
     * @param status Código de estado HTTP que indica el resultado de la solicitud.
     * @param error Mensaje de error que describe el problema encontrado.
     * @param data Los datos que contiene la respuesta, de tipo T.
     */
    public DTOResponse(int status, String error, T data) {
        super();
        this.status = status;
        // Crea una lista y agrega el único error pasado como parámetro.
        List<String> errores = new ArrayList<>();
        errores.add(error);
        this.error = errores;
        this.data = data;
    }

    /**
     * Constructor vacío que permite crear una respuesta vacía o inicializar sin valores.
     * Este constructor puede ser útil para instancias que se llenarán posteriormente.
     */
    public DTOResponse() {

    }

    // Getters y Setters para cada uno de los atributos

    /**
     * Obtiene el código de estado de la respuesta.
     *
     * @return Código de estado HTTP que indica el resultado de la solicitud.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Establece el código de estado de la respuesta.
     *
     * @param status Código de estado HTTP que indica el resultado de la solicitud.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtiene la lista de errores de la respuesta.
     *
     * @return Lista de mensajes de error que describe problemas en la solicitud.
     */
    public List<String> getError() {
        return error;
    }

    /**
     * Establece la lista de errores para la respuesta.
     *
     * @param error Lista de mensajes de error que describe problemas en la solicitud.
     */
    public void setError(List<String> error) {
        this.error = error;
    }

    /**
     * Obtiene los datos de la respuesta, de tipo genérico T.
     *
     * @return Los datos contenidos en la respuesta, que pueden ser de cualquier tipo.
     */
    public T getData() {
        return data;
    }

    /**
     * Establece los datos de la respuesta.
     *
     * @param data Los datos que contiene la respuesta, de tipo T.
     */
    public void setData(T data) {
        this.data = data;
    }
}
