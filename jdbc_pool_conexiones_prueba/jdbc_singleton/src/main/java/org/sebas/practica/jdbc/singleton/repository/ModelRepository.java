package org.sebas.practica.jdbc.singleton.repository;

import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 26/02/2022
 */
public interface ModelRepository<T> {


    // Listar, agregar, actualizar, eliminar

    List<T> getAll();

    T findById(Long id);

    void save(T t);

    void delete(Long id);

}
