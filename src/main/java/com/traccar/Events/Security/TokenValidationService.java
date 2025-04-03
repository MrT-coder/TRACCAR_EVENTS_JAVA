package com.traccar.Events.Security;

import com.traccar.api.security.LoginResult;
import com.traccar.api.security.LoginService;
import com.traccar.api.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.GeneralSecurityException;
import java.io.IOException;

@Service
public class TokenValidationService {

    @Autowired
    private TokenManager tokenManager;  // Usado para validar y generar tokens

    @Autowired
    private LoginService loginService;  // Para autenticar el token y obtener detalles del usuario

    /**
     * Método para validar el token y obtener el UserId.
     * @param token El token recibido en la solicitud.
     * @return El ID del usuario si el token es válido.
     * @throws GeneralSecurityException Si el token no es válido o ha expirado.
     * @throws IOException Si ocurre un error al procesar el token.
     */
    public Long validateToken(String token) throws GeneralSecurityException, IOException {
        // Validamos el token
        LoginResult loginResult = loginService.login(token);

        if (loginResult == null) {
            throw new GeneralSecurityException("Token inválido o expirado");
        }

        // Obtener el UserId del resultado de la autenticación
        return loginResult.getUser().getId();
    }
}
