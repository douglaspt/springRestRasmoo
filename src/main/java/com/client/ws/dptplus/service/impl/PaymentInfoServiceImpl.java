package com.client.ws.dptplus.service.impl;

import com.client.ws.dptplus.dto.PaymentProcessDto;
import com.client.ws.dptplus.dto.wsraspay.CustomerDto;
import com.client.ws.dptplus.dto.wsraspay.OrderDto;
import com.client.ws.dptplus.dto.wsraspay.PaymentDto;
import com.client.ws.dptplus.enums.UserTypeEnum;
import com.client.ws.dptplus.exception.BusinessException;
import com.client.ws.dptplus.exception.NotFoundException;
import com.client.ws.dptplus.integration.MailIntegration;
import com.client.ws.dptplus.integration.WsRaspayIntegration;
import com.client.ws.dptplus.mapper.UserPaymentInfoMapper;
import com.client.ws.dptplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.dptplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.dptplus.mapper.wsraspay.OrderMapper;
import com.client.ws.dptplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.dptplus.model.jpa.User;
import com.client.ws.dptplus.model.jpa.UserCredentials;
import com.client.ws.dptplus.model.jpa.UserPaymentInfo;
import com.client.ws.dptplus.repository.jpa.*;
import com.client.ws.dptplus.service.PaymentInfoService;
import com.client.ws.dptplus.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.dptplus.default.password}")
    private String defaultPass;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                           WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration,
                           UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository,
                           SubscriptionTypeRepository subscriptionTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }

        Boolean successPayment = getSucessPayment(dto, user);

        return createUserCredentials(dto, user, successPayment);
    }

    private boolean createUserCredentials(PaymentProcessDto dto, User user, Boolean successPayment) {
        if (Boolean.TRUE.equals(successPayment)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if (userTypeOpt.isEmpty()) {
                throw new NotFoundException("UserType não encontrado");
            }
            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), PasswordUtils.encode(defaultPass), userTypeOpt.get());
            userDetailsRepository.save(userCredentials);

            var subscriptionTypeOpt = subscriptionTypeRepository.findByProductKey(dto.getProductKey());

            if (subscriptionTypeOpt.isEmpty()) {
                throw new NotFoundException("SubscriptionType não encontrado");
            }
            user.setSubscriptionType(subscriptionTypeOpt.get());
            userRepository.save(user);

            mailIntegration.send(user.getEmail(), "Usuario: " + user.getEmail() + " - Senha: " + defaultPass, "Acesso liberado");
            return true;
        }
        return false;
    }

    private Boolean getSucessPayment(PaymentProcessDto dto, User user) {
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        return wsRaspayIntegration.processPayment(paymentDto);
    }
}
