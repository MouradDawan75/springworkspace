package fr.dawan.spring_rest_api.tools;

import org.modelmapper.ModelMapper;

public class DtoTool {

    private static ModelMapper mapper = new ModelMapper();

    public static <TSource, TDestination> TDestination convert(TSource obj, Class<TDestination> clazz){

        /*
         ModelMapper: Convertir des entity en dto et vice versa
            Doc: https://modelmapper.org/getting-started/
        Si besoin ajouter une conf:
        mapper.typeMap(Contact.class, ContactDto.class)
				.addMappings(m -> {
			m.map(src -> src.getFirstName(),ContactDto::setNom);
			m.map(src -> src.getLastName(),ContactDto::setPrenom);

		});
         */
        return mapper.map(obj, clazz);
    }
}
