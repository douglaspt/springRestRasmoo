package com.client.ws.dptplus.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    ROFESSOR(1L),
    ADMINISTRADOR(2L),
    ALUNO(3L);

    private Long id;

    UserTypeEnum(Long id){
        this.id = id;
    }
}
