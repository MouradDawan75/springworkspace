package fr.dawan.spring_rest_api.apidocs;

import fr.dawan.spring_rest_api.dtos.CountDto;
import fr.dawan.spring_rest_api.dtos.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "BearerToken")
public interface ProductApi {


    @Operation(summary = "Get al Products paged", description = "Get all products from Database")
    @ApiResponse(responseCode = "200", description = "Return list of products")

    @GetMapping(value = {"/{page}/{size}/{description}", "/{page}/{size}"}, produces = "application/json")
    List<ProductDto> getAllBy(
            @Parameter(description = "page number", required = true) @PathVariable("page") int page,
            @Parameter(description = "number of product in page", required = true) @PathVariable("size") int size,
            @Parameter(description = "filter products by description", required = false) @PathVariable(value = "description", required = false) Optional<String> description) throws Exception;

    @GetMapping(value = {"/count/{description}", "/count"}, produces = "application/json")
    CountDto countBy(@PathVariable(value = "description", required = false) Optional<String> description) throws Exception;

    @DeleteMapping(value = "/delete/{id}", produces = "text/plain")
    ResponseEntity<String> delete(@PathVariable("id") long id) throws Exception;

    @GetMapping(value = "/find/{id}")
    ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception;

    @PostMapping(value = "/save", produces = "application/json", consumes = "multipart/form-data")
    ResponseEntity<ProductDto> addProduct(@RequestParam("productString") String productString, @RequestParam("file") MultipartFile file) throws Exception;

    @GetMapping(value = "/image/{productId}", produces = "application/octet-stream")
    ResponseEntity<Resource> getProductImage(@PathVariable("productId") long id) throws Exception;
}
