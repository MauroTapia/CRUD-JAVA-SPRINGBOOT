function submitForm(e){
e.preventDefault();
       const url = '/pacientes/email/' + document.querySelector('#mail').value;
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

                          document.querySelector('#div_paciente_updating').style.display = "block";
                      }).catch(error => {
                          alert("Error al cargar el Email");

                  let deleteButton = '<button' +
                                            ' id=' + '\"' + 'btn_delete_' + pacientes.id + '\"' +
                                            ' type="button" onclick="deleteBy('+pacientes.id+')" class="btn rounded-circle btn-danger btn_delete">' +
                                            '&times' +
                                            '</button>';

                  let updateButton = '<button' +
                                            ' id=' + '\"' + 'btn_id_' + pacientes.id + '\"' +
                                            ' type="button" onclick="findBy('+pacientes.id+')" class="btn btn-primary rounded-circle btn-success btn_id">' +
                                            pacientes.id +
                                            '</button>';

                  pacienteRow.innerHTML = '<td>' + updateButton + '</td>' +
                          '<td class=\"td_nombre\">' + pacientes.nombre.toUpperCase() + '</td>' +
                          '<td class=\"td_apellido\">' + pacientes.apellido.toUpperCase() + '</td>' +
                          '<td>' + deleteButton + '</td>';
});
}

const form = document.getElementById("find_by_mail");

form.addEventListener("submit", submitForm);
