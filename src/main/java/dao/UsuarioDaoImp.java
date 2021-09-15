package dao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import models.Usuario;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        //Se coloca el nombre de la clase(Usuario) y no la tabla de la BD
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Usuario findById(Long id){
        Usuario usuario = entityManager.find(Usuario.class, id);
        if(usuario != null){
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario editar(Long id){
        Usuario usuario = entityManager.find(Usuario.class, id);
        usuario.setNombre(usuario.getNombre());
        return entityManager.merge(usuario);
    }

    @Override
    public void eliminar(Long id){
        //Busca el usuario por id
        Usuario usuario = entityManager.find(Usuario.class,id);
        //Elimina el usuario encontrado
        entityManager.remove(usuario);
    }

    @Override
    public void registar(Usuario usuario) {
        //Merge = añadir a la base de datos
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario){
        //Los 2 puntos evitan la inyección de sql :email y :password
        String query = "FROM Usuario where email = :email";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        //Verifico si la lista esta vacia para evitar un error de tipo NUll POINT EXCEPTION
        if(lista.isEmpty()){
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //Verifico si la contrase{a de la BD es igual a la contraseña ingresada por el usuario
       if(argon2.verify(passwordHashed, usuario.getPassword())){
           return lista.get(0);
       }
        return null;
    }
}
