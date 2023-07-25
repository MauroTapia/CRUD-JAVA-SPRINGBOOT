window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_paciente_form');

        formulario.addEventListener('submit', function (event) {
        
        let pacienteId = document.querySelector('#paciente_id').value;

        const formData = {
                        id: document.querySelector('#paciente_id').value,
                        nombre: document.querySelector('#nombre').value,
                        apellido: document.querySelector('#apellido').value,
                        documento:document.querySelector('#documento').value,
                        fechaIngreso: document.querySelector('#fechaIngreso').value,
                        email: document.querySelector('#email').value,
                        domicilio:{
                            calle: document.querySelector('#calle').value,
                            numero : document.querySelector('#numero').value,
                            localidad : document.querySelector('#localidad').value,
                            provincia : document.querySelector('#provincia').value
                        }
        };

        const url = '/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
                    .then(response => response.text())
                    .then(text => {alert(text)
                    window.location.reload();
                });
    })
 })

    function findBy(id) {
          const url = '/pacientes'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let pacientes = data;
                document.querySelector('#paciente_id').value = pacientes.id;
                document.querySelector('#nombre').value = pacientes.nombre;
                document.querySelector('#apellido').value = pacientes.apellido;
                document.querySelector('#documento').value = pacientes.documento;
                document.querySelector('#fechaIngreso').value = pacientes.fechaIngreso;
                document.querySelector('#email').value = pacientes.email;
                document.querySelector('#calle').value = pacientes.domicilio.calle;
                document.querySelector('#numero').value = pacientes.domicilio.numero;
                document.querySelector('#localidad').value = pacientes.domicilio.localidad;
                document.querySelector('#provincia').value = pacientes.domicilio.provincia;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_paciente_updating').style.display = "block";
          }).catch(error => {
              alert("Ocurrió un error al actualizar el paciente con el Id " + paciente.id + "con el email " + paciente.email);
          })
      }