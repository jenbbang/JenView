package org.sparta.jenview.jwt_security.mapper;

public interface MapperInterface<D, E> {

    D toDTO(E entity);

    E toEntity(D dto);

}
