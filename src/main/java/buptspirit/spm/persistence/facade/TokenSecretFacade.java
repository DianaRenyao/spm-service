package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.TokenSecret;

import javax.inject.Singleton;

@Singleton
public class TokenSecretFacade extends AbstractFacade<TokenSecret> {

    public TokenSecretFacade() {
        super(TokenSecret.class);
    }
}
