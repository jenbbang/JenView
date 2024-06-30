package org.sparta.jenview.mapper;

public interface MapperInterface<D, E> {

    D toDTO(E entity);

    E toEntity(D dto);

}
