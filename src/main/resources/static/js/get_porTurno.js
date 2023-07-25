function submitForm(e){
e.preventDefault();
       const url = '/turnos/' + document.querySelector('#idTurno').value;
            const settings = {
              method: 'GET'
            }
            fetch(url,settings)
            .then(response => response.json())
            .then(data => {
                let turno = data;
                    document.querySelector('#turno_id').value = turno.id;
                    document.querySelector('#date').value = turno.fecha;
                    document.querySelector('#idPaciente').value = turno.paciente_id;
                    document.querySelector('#idOdontologo').value = turno.odontologo_id;
                    document.querySelector('#odontologoNombre').value = turno.odontologoNombre;
                    document.querySelector('#pacienteNombre').value = turno.pacienteNombre;
              //el formulario por default esta oculto y al editar se habilita
                    document.querySelector('#div_turno_updating').style.display = "block";

                    document.querySelector('#turno_id').readOnly = true;
                    document.querySelector('#date').readOnly = true;
                    document.querySelector('#pacienteNombre').readOnly = true;
                    document.querySelector('#odontologoNombre').readOnly = true;

                      }).catch(error => {
                          alert("Error al cargar el turno");

                   let deleteButton = '<button' +
                                                        ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                                                        ' type="button" onclick="deleteBy('+turno.id+')" class="btn rounded-circle btn-danger btn_delete">' +
                                                        '&times' +
                                                        '</button>';

                              let updateButton = '<button' +
                                                        ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                                                        ' type="button" onclick="findBy('+turno.id+')" class="btn btn-primary rounded-circle btn-success btn_id">' +
                                                        turno.id +
                                                        '</button>';

                              turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                                      '<td class=\"td_paciente\">' + turno.pacienteNombre.toUpperCase() + '</td>' +
                                      '<td class=\"td_odontologo\">' + turno.odontologoNombre.toUpperCase() + '</td>' +
                                      '<td>' + deleteButton + '</td>';
});
}
const form = document.getElementById("find_by_turno");

form.addEventListener("submit", submitForm);

