package com.dkserver.danielServer.interceptor;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import static com.dkserver.danielServer.utils.Constants.DEFAULT_TENANT;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId =  TenantContext.getCurrentTenant();
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
