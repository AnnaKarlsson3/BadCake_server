package com.dkserver.danielServer.interceptor;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import static com.dkserver.danielServer.config.Constants.DEFAULT_TENANT;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        System.out.println("resolveCurrentTenantIdentifier, first ");
        String tenantId =  TenantContext.getCurrentTenant();
        System.out.println("resolveCurrentTenantIdentifier, second " + tenantId);
        if(tenantId!=null){
            return tenantId;
        } else {
            return DEFAULT_TENANT;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
