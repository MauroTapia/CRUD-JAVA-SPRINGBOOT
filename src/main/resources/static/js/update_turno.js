window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {

        let turnoId = document.querySelector('#turno_id').value;

        const formData = {
            id: document.querySelector('#turno_id').value,
            paciente:{id: document.querySelector('#idPaciente').value},
            odontologo:{ id: document.querySelector('#idOdontologo').value},
            fecha: document.querySelector('#date').value,

        };

        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => {
           alert(response.text("Error al guargar los nuevos datos del turno con el id: " + turno.id));
           window.location.reload();
           });
    })
 })

    function findBy(id) {
          const url = '/turnos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turno = data;
              document.querySelector('#turno_id').value = turno.id;
              document.querySelector('#idPaciente').value = turno.paciente_id;
              document.querySelector('#idOdontologo').value = turno.odontologo_id;
              document.querySelector('#odontologoNombre').value = turno.odontologoNombre;
              document.querySelector('#pacienteNombre').value = turno.pacienteNombre;
              document.querySelector('#date').value = turno.fecha;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_turno_updating').style.display = "block";

              document.querySelector('#turno_id').readOnly = true;
              document.querySelector('#pacienteNombre').readOnly = true;
              document.querySelector('#odontologoNombre').readOnly = true;

          }).catch(error => {
              alert("Error al cargar los nuevos datos del turno con el id: " + turno.id);
          })
      }