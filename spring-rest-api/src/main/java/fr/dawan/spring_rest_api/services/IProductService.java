package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.CountDto;
import fr.dawan.spring_rest_api.dtos.ProductDto;
import org.mariadb.jdbc.plugin.codec.LineStringCodec;

import java.util.List;

public interface IProductService {
    List<ProductDto> getAllBy(String description, int page, int size) throws Exception;
    CountDto countBy(String description)throws Exception;
    ProductDto saveOrUpdate(ProductDto productDto) throws Exception;
    void deleteById(long id) throws Exception;
    ProductDto getById(long id) throws Exception;
}
