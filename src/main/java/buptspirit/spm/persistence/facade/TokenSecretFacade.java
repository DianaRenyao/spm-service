package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.TokenSecretEntity;

import javax.inject.Singleton;

@Singleton
public class TokenSecretFacade extends AbstractFacade<TokenSecretEntity> {

    public TokenSecretFacade() {
        super(TokenSecretEntity.class);
    }
}
