package dao;

import models.Usuario;
import java.util.List;


public interface UsuarioDao {

    List<Usuario> getUsuarios();

    Usuario findById (Long id);

    Usuario editar(Long id);

    void eliminar(Long id);

    void registar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
