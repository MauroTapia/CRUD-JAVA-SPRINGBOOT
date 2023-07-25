window.addEventListener('load', function () {

    const formulario = document.getElementById('add_new_turno');

    formulario.addEventListener('submit', function (event) {

    event.preventDefault();
        const formData = {
            paciente:{ id: document.querySelector('#paciente').value},
            odontologo: {id: document.querySelector('#odontologo').value},
            fecha: document.querySelector('#fechaTurno').value
        };
        const url = '/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
            if(data.message){
                               throw new Error(data.message)
                               }
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                                    '<strong></strong> Turno agregado correctamente</div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<strong>Existe un error al crear un nuevo turno</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     })
    });


    function resetUploadForm(){
        document.querySelector('#paciente').value = "";
        document.querySelector('#odontologo').value = "";
        document.querySelector('#fechaTurno').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/turnoFunciones.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});