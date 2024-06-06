package com.ossian.FitFlow.webConfig.security;

import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final JwtTokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserServiceImpl usuarioService;

    @Autowired
    UserRepository usuarioRepository;



    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody JwtTokenRequest jwtTokenRequest) {
        String username = jwtTokenRequest.username();
        User usuario;
        if (username.contains("@")){
            usuario = usuarioRepository.findByEmail(jwtTokenRequest.username());
        }else {
            usuario = usuarioRepository.findByEmail(jwtTokenRequest.username());
        }
        System.err.println("Login request: " + usuario.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (usuario == null) {
            logger.info("User: {} failed to authenticate", jwtTokenRequest.username());
            return ResponseEntity.badRequest().build();
        }
        if (usuario.getActivo() == 0) {
            return ResponseEntity.status(104).build();
        }

        if (!encoder.matches(jwtTokenRequest.password(), usuario.getPassword())) {
            logger.info("User: {} failed to authenticate", jwtTokenRequest.username());
            return ResponseEntity.badRequest().build();
        }
        logger.info("User: {} authenticated", jwtTokenRequest.username());

        //  JwtTokenRequest jwtTokenRequest1 = new JwtTokenRequest(jwtTokenRequest.username(), "sexo");
        var authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        jwtTokenRequest.username(),
                        jwtTokenRequest.password());

        var authentication =
                authenticationManager.authenticate(authenticationToken);

        var token = tokenService.generateToken(authentication);

        System.out.println("token: " + usuario.getUserConfig());
        return ResponseEntity.ok(new JwtTokenResponse(token, usuario));
    }

    //registrarUsuario
    @PostMapping("/register")
    public ResponseEntity<User> registerUsuario(@RequestBody User usuario) {
        Usuario newUser = usuarioService.registerUsuario(usuario);
        System.out.println("newUser: " + newUser.getID());
        if (newUser.getID() < 0) return ResponseEntity.status((int) (newUser.getID() * -1)).body(null);
        else return ResponseEntity.ok(newUser);
    }


    @PostMapping("/recoverPassword")
    public ResponseEntity<Boolean> changePasswordWithRecoveryCode(@RequestBody RecoverCodeDTO recoverCode) {
        System.out.println("recoverCode: " + recoverCode);
        Long userID = usuarioService.validateRecoveryCode(recoverCode);
        if (userID != -1) {
            CheckPasswordDTO checkPasswordDTO = new CheckPasswordDTO(userID, recoverCode.getNewPass());
            usuarioService.updatePassword(checkPasswordDTO);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}
