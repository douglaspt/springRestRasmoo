package com.client.ws.dptplus.dto;

import com.client.ws.dptplus.model.SubscriptionType;
import com.client.ws.dptplus.model.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "valor não pode ser nulo ou vazio")
    @Size(min = 6, message = "valor mínimo igual a 6 caracteres")
    private String name;

    @Email(message = "inválido")
    private String email;

    @Size(min = 11, message = "valor mínimo igual a 11 dígitos")
    private String phone;

    @CPF(message = "inválido")
    private String cpf;

    private LocalDate dtSubscription = LocalDate.now();

    private LocalDate dtExpiration = LocalDate.now();

    @NotNull
    private Long userTypeId;

    private Long subscriptionTypeId;
}
