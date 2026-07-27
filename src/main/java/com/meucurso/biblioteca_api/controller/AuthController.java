package com.meucurso.biblioteca_api.controller;

import com.meucurso.biblioteca_api.dto.LoginRequest;
import com.meucurso.biblioteca_api.dto.TokenResponse;
import com.meucurso.biblioteca_api.security.AutenticacaoService;
import com.meucurso.biblioteca_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // 1. Autentica o usuário (valida email e senha)
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getSenha())
        );
        // 2. Carrega o usuário do banco
        UserDetails usuario = autenticacaoService.loadUserByUsername(request.getEmail());
        // 3. Gera o JWT e devolve
        String token = jwtService.gerarToken(usuario);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}


