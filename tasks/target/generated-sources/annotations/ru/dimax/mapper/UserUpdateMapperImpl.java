package ru.dimax.mapper;

import javax.annotation.processing.Generated;
import ru.dimax.model.UpdateUserRequest;
import ru.dimax.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-26T11:55:20+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
public class UserUpdateMapperImpl implements UserUpdateMapper {

    @Override
    public void updateUserFromDto(UpdateUserRequest dto, User user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            user.setName( dto.getName() );
        }
        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
        if ( dto.getSpec() != null ) {
            user.setSpec( dto.getSpec() );
        }
        if ( dto.getGrade() != null ) {
            user.setGrade( dto.getGrade() );
        }
    }
}
