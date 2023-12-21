package com.dkserver.danielServer.services;

import com.dkserver.danielServer.interceptor.TenantContext;
import org.springframework.stereotype.Service;

import static com.dkserver.danielServer.utils.Constants.DEFAULT_TENANT_ID;

@Service
public class TenantService {

    public void switchToDefaultTenant() {
        TenantContext.setCurrentTenant(DEFAULT_TENANT_ID);
    }

}

