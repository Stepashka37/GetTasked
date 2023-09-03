package ru.dimax.mapper;

import javax.annotation.processing.Generated;
import ru.dimax.model.task.Task;
import ru.dimax.model.task.UpdateTaskRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-02T18:02:12+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
public class TaskUpdateMapperImpl implements TaskUpdateMapper {

    @Override
    public void updateTaskFromDto(UpdateTaskRequest dto, Task task) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            task.setDescription( dto.getDescription() );
        }
        if ( dto.getStart() != null ) {
            task.setStart( dto.getStart() );
        }
        if ( dto.getDeadline() != null ) {
            task.setDeadline( dto.getDeadline() );
        }
    }
}
