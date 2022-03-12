package org.sebas.java.jdbc.repository;

import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public interface Repositorio<T> {

    List<T> getAll();

    T findById(Long id);

    void save(T t);

    void deleteById(Long id);

}
