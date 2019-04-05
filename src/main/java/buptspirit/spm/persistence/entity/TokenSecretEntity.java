package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "token_secret", schema = "spm")
public class TokenSecretEntity {
    private int tokenSecretId;
    private String secret;

    @Id
    @Column(name = "token_secret_id", nullable = false)
    public int getTokenSecretId() {
        return tokenSecretId;
    }

    public void setTokenSecretId(int tokenSecretId) {
        this.tokenSecretId = tokenSecretId;
    }

    @Basic
    @Column(name = "secret", nullable = true, length = 256)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenSecretEntity that = (TokenSecretEntity) o;
        return tokenSecretId == that.tokenSecretId &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenSecretId, secret);
    }
}
