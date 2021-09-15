// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailUsuario();
});

function actualizarEmailUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios(){
    const request = await fetch('api/usuarios',{
        method: 'GET',
        headers: getHeaders()
    });
    const usuarios = await request.json();
    let listadoHtml = '';
    for(let usuario of usuarios){
    let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let botonEditar = '<a href="#" onclick="editarUsuario('+usuario.id+')" class="btn btn-primary btn-circle btn-sm"><i class="fas fa-pen"></i></a>';
    let telefono = usuario.telefono == null ? '-' : usuario.telefono;

    let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'+usuario.email+
                          '</td><td>'+telefono+
                          '</td><td>'+ botonEliminar +' '+botonEditar+'</td></td></tr>'
    listadoHtml += usuarioHtml
    }
    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}

function getHeaders(){
    return {
            'Accept': 'application/json',
            'Content-type' : ' application/json',
            'Authorization' : localStorage.token
    };
}

async function eliminarUsuario(id){
    //Si es falso se corta el flujo y no elimina
    if(!confirm('Desea eliminar este usuario?')){
        return;
    }
    const request = await fetch('api/usuarios/' + id,{
            method: 'DELETE',
            headers: getHeaders()
    });
    location.reload();
}

async function editarUsuario(id){
      const request = await fetch('api/editUsuario/' + id,{
                method: 'GET',
                headers: getHeaders()
        });
      const usuario = await request
      console.log(usuario);
     //window.location.href = "editUsuario.html";
}