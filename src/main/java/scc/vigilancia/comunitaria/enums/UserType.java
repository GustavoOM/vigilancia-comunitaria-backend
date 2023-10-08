package scc.vigilancia.comunitaria.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum UserType implements GrantedAuthority {
    ADMINISTRADOR, MANTENEDOR, MEMBRO;

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static boolean isValid(String str) {
        if(str == null || str.isBlank())
            return false;
        return Objects.equals(UserType.ADMINISTRADOR.name(), str)
                || Objects.equals(UserType.MEMBRO.name(), str)
                || Objects.equals(UserType.MANTENEDOR.name(), str);
    }
}
