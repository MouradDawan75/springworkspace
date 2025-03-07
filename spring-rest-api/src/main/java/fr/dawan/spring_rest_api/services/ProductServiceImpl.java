package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.CountDto;
import fr.dawan.spring_rest_api.dtos.ProductDto;
import fr.dawan.spring_rest_api.entities.Category;
import fr.dawan.spring_rest_api.entities.Product;
import fr.dawan.spring_rest_api.repositories.CategoryRepository;
import fr.dawan.spring_rest_api.repositories.ProductRepository;
import fr.dawan.spring_rest_api.tools.DtoTool;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Bonnes pratiques:
SOLID:
Single Of Responsability: une classe ne doit gérer qu'une seule et unique tâche
Open/Close: une classe doit être ouverte à l'extension, mais fermée à la modification
Liskov Substitution: les objets enfants sont subtituables aux objets parents (Si A se comporte comme B, alors A hérite de B)
Interface Segregation: découpage d'interfaces complexes en petites interfaces ne gérant qu'1 besoin fonctionnel
Dependency Inversion/Injection

Tell dont ask: dites, ne posez pas de questions. Dites à vos objets ce qu'ils doivent faire, ne leur posez pas de questions sur leur état

 */

@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllBy(String description, int page, int size) throws Exception {

        List<ProductDto> result = new ArrayList<>();

        List<Product> entities = productRepository.findByDescriptionContaining(description, PageRequest.of(page - 1, size)).getContent();

        for(Product p : entities){
            /*
            ProductDto dto = new ProductDto();
            dto.setId(p.getId());
            dto.setDescription(p.getDescription());
            dto.setVersion(p.getVersion());
            dto.setImagePath(p.getImagePath());
            dto.setPrice(p.getPrice());
            dto.setCategoryId(p.getCategory().getId());

            /*
            ObjectMapper:
            ModelMapper: Convertir des entity en dto et vice versa
            Doc: https://modelmapper.org/getting-started/
             */


            result.add(DtoTool.convert(p, ProductDto.class));
        }

        return result;
    }

    @Override
    public CountDto countBy(String description) throws Exception {
        long nb = productRepository.countByDescriptionContaining(description);
        CountDto dto = new CountDto();
        dto.setNb(nb);
        return dto;
    }

    @Override
    public ProductDto saveOrUpdate(ProductDto productDto) throws Exception {

        Product p = DtoTool.convert(productDto, Product.class);

        //Gérer le ManyToOne avec Category
        Category cat = categoryRepository.findById(productDto.getCategoryId()).get();
        p.setCategory(cat);
        //cat.getProducts().add(p);
        //categoryRepository.deleteAllInBatch();

        Product savedProduct = productRepository.saveAndFlush(p);
        /*
        save de JpaRepository gère l'ajout et la modif en BD
        si id == 0 -> génère la commande INSERT SQL
        sinon: génère la commande UPDATE SQL
         */


        return DtoTool.convert(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteById(long id) throws Exception {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getById(long id) throws Exception {

        Optional<Product> optional = productRepository.findById(id);


        if(optional.isPresent()){

            return DtoTool.convert(optional.get(), ProductDto.class);
        }

        return null;
    }
}
