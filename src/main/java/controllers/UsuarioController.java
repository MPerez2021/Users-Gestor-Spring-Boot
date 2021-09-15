package controllers;

import dao.UsuarioDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JWTUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios/{id}")
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Pérez");
        usuario.setEmail("l@hotmail.com");
        usuario.setPassword("123");
        usuario.setTelefono("24524");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    //value = 'Authorization' y se guarda en la variable token lo que devuelva la cabecera
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){
            return null;
        }
         return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        //Extrae el id del usuario del token
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId !=null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //Encriptación con HASH
        //i: iteraciones en las que se hace el proceso de encriptación
        //i1: espacio en memoria
        //i2: paralelismo para ejecutar varios procesos al mismo tiempos
        // usario.getPassword = se coloca el texto que se va a encriptar
        String hash = argon2.hash(1,1024,1, usuario.getPassword());

        //Se setea la nueva contraseña encriptada
        usuario.setPassword(hash);
        usuarioDao.registar(usuario);
    }

    @RequestMapping(value="api/editUsuario/{id}", method = RequestMethod.GET)
    public Usuario findById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){
            return null;
        }
        return usuarioDao.findById(id);
    }

    @RequestMapping(value="api/editUsuarios/{id}", method = RequestMethod.PUT)
    public Usuario editar(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){
            return null;
        }
        return usuarioDao.editar(id);
    }


    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }


}
