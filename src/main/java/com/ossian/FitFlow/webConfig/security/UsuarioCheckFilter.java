package com.ossian.FitFlow.webConfig.security;

import com.ossian.FitFlow.repository.UserRepository;
import org.springframework.web.filter.OncePerRequestFilter;

public class UsuarioCheckFilter extends OncePerRequestFilter {
    UserRepository usuarioRepository;


    //Constructor
    public UsuarioCheckFilter(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el encabezado Usuario-Check
        logger.info("UsuarioCheckFilter: Start processing request " + request.getRequestURI());

        String usuarioCheckHeader = request.getHeader("Usuario-Check");
        //Sacamos el username del token
        String auth = request.getHeader("Authorization");
        if (false && auth != null && !auth.isEmpty()) {
            String[] a1 = auth.split(" ");
            String[] a2 = a1[1].split("\\.");
            String[] a3 = a2[1].split("\\.");
            String username = new String(Base64.getDecoder().decode(a3[0])).split(":")[2].split(",")[0].replace("\"", "");
            logger.info("UsuarioCheckFilter SEXOOOO: Authentication succeeded for user " + username + " with password " );
        }


        if (usuarioCheckHeader != null && !usuarioCheckHeader.isEmpty()) {
            // Parsear el UsuarioCheckDTO desde el encabezado
            CheckPasswordDTO usuarioCheckDTO = parseUsuarioCheckDTO(usuarioCheckHeader);
            Usuario usuario = usuarioRepository.findByID(usuarioCheckDTO.getID());
            if (usuario == null || !usuarioCheckDTO.getPassword().equals(usuario.getPassword())) {
                logger.warn("UsuarioCheckFilter: Eres un puto listo de mierda | Authentication failed for user ID  " + usuarioCheckDTO.getID());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            logger.info("UsuarioCheckFilter: Authentication succeeded for user ID " + usuarioCheckDTO.getID());
        } else {
            if (request.getRequestURI().startsWith("/api/auth/") || request.getRequestURI().startsWith("/api/file/download")) {
                logger.info("UsuarioCheckFilter: Authentication not required for this request");
            } else {
                logger.warn("UsuarioCheckFilter: No Usuario-Check header found");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
        if (response.getStatus() >= 400 && response.getStatus() <= 599) {
            String fecha = LocalDateTime.now().toLocalDate().toString();
            String hora = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String codigo = String.valueOf(response.getStatus());
            String url = request.getRequestURI();
            String usuario = "Sin usuario";
            if (usuarioCheckHeader != null && !usuarioCheckHeader.isEmpty()) {
                usuario = String.valueOf(parseUsuarioCheckDTO(usuarioCheckHeader).getID());
            }
            File file = new File("logs/" + fecha + "-log.csv");
            boolean newFile = file.createNewFile();
            try (FileWriter writer = new FileWriter(file, true)) { //El true es para que no sobreescriba
                if (newFile) writer.write("Fecha,Hora,Codigo,URL,IDUsuario\n");
                writer.write(fecha + "," + hora + "," + codigo + "," + url + "," + usuario + "\n");
            } catch (IOException e) {
                logger.error("Error writing to log file", e);
            }
            logger.error("Error processing request " + request.getRequestURI() + " with status " + response.getStatus());
        }

        logger.info("UsuarioCheckFilter: Finished processing request " + request.getRequestURI());
    }

    private CheckPasswordDTO parseUsuarioCheckDTO(String usuarioCheckHeader) {
        String[] a1 = usuarioCheckHeader.replaceAll("[\",\\[\\]}]|password", "").split(":");
        return new CheckPasswordDTO(Long.parseLong(a1[1]), a1[2]);
    }
}
