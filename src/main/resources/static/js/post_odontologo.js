window.addEventListener('load', function () {

    const formulario = document.getElementById('add_new_odontologo');

    formulario.addEventListener('submit', function (event) {

        event.preventDefault();
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            matricula: document.querySelector('#matricula').value,
        };

        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers : {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
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
                     '<strong></strong> Odontologo agregado con éxito</div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<strong> Ocurrió un error al intentar agregar un odontólogo </strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";

                  })
    });


    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#matricula').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/index.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});