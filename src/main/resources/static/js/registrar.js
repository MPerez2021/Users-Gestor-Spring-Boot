// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function registrarUsuario(){
    let datos = {};
    datos.nombre = document.getElementById("txtNombre").value;
    datos.apellido = document.getElementById("txtApellido").value;
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;

    let repetirPassoword = document.getElementById("txtRepetirPassword").value;

    if(repetirPassoword != datos.password){
        alert('La contraseña que escribiste es diferente. ')
        return;
    }
    //Realiza la petición al servidor
    const request = await fetch('api/usuarios',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type' : ' application/json'
        },
        //JSON.stringfy = transforma cualquier objeto de javascript a un string de JSON
        body: JSON.stringify(datos)
    });
    alert("La cuenta fue creada con éxito. ")
    window.location.href = "login.html"
 }
