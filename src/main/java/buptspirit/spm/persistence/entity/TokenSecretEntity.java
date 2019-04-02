package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "token_secret", schema = "spm", catalog = "")
public class TokenSecretEntity {
    private int id;
    private String secret;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "secret")
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
        return id == that.id &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secret);
    }
}
