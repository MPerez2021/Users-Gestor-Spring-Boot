// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function iniciarSesion(){
    let datos = {};
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;

    const request = await fetch('api/login',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-type' : 'application/json'
        },
        //JSON.stringfy = transforma cualquier objeto de javascript a un string de JSON
        body: JSON.stringify(datos)
    });
    const respuesta = await request.text();

    if(respuesta != "FAIL"){
        //Se guarda el token en el localStorage del navegador
        localStorage.token = respuesta;
        localStorage.email = datos.email;
        window.location.href = "usuarios.html"
    }else{
        alert('Credenciales incorrectas. Por favor intente nuevamente. ')

    }
}
