window.addEventListener('load', function () {

    const formulario = document.getElementById('add_new_paciente');

    formulario.addEventListener('submit', function (event) {

    event.preventDefault();
        const formData = {
             nombre: document.querySelector('#nombre').value,
             apellido: document.querySelector('#apellido').value,
             email: document.querySelector('#email').value,
             documento:document.querySelector('#documento').value,
             fechaIngreso: document.querySelector('#fechaIngreso').value,
             domicilio:{
                 calle: document.querySelector('#calle').value,
                 numero : document.querySelector('#numero').value,
                 localidad : document.querySelector('#localidad').value,
                 provincia : document.querySelector('#provincia').value
             }
        };
        const url = '/pacientes';
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
                     '<strong></strong> Paciente agregado con éxito</div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<strong>Ocurrió un error al intentar agregar un paciente </strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                    })
    });


    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#documento').value = "";
        document.querySelector('#fechaIngreso').value = "";
        document.querySelector('#calle').value = "";
        document.querySelector('#numero').value = "";
        document.querySelector('#localidad').value = "";
        document.querySelector('#provincia').value = "";
        document.querySelector('#email').value= "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "./post_pacientes.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});