package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.SubscriptionTypeDto;
import com.client.ws.dptplus.model.jpa.SubscriptionType;

import java.util.List;

public interface SubscriptionTypeService {

    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDto dto);

    SubscriptionType update(Long id, SubscriptionTypeDto dto);

    void delete(Long id);
}
