package com.example.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.crossstore.ChangeSetPersister;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessNotFoundException extends ChangeSetPersister.NotFoundException {
    public BusinessNotFoundException() {
        super();
    }
}
