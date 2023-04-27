package br.com.petshop.petshop.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class MapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D> D convertTo(Object bean, Class<D> dto) {
        return bean == null ? null : modelMapper.map(bean, dto);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }


}