package com.codecool.brightxchange.springSecurity.security;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@Configuration
public class PaypalConfig {

        private final PaypalConfigProp paypalConfigProp;

        @Autowired
        public PaypalConfig(PaypalConfigProp paypalConfigProp) {
                this.paypalConfigProp = paypalConfigProp;
        }

        public Map<String, String> paypalSdkConfig(){
                Map<String, String> configMap = new HashMap<>();
                configMap.put("mode", paypalConfigProp.getMode());
                return configMap;
        }
        @Bean
        public OAuthTokenCredential oAuthTokenCredential(){
           return new OAuthTokenCredential(paypalConfigProp.getClientId(), paypalConfigProp.getClientSecret(),paypalSdkConfig());
        }
        @Bean
        public APIContext apiContext() throws PayPalRESTException {
                APIContext context=new APIContext(oAuthTokenCredential().getAccessToken());
                context.setConfigurationMap(paypalSdkConfig());
                return context;
        }

}
