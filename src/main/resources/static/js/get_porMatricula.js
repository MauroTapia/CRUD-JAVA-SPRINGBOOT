function submitForm(e){
e.preventDefault();
       const url = '/odontologos/matricula/' + document.querySelector('#numero').value;
            const settings = {
              method: 'GET'
            }
            fetch(url,settings)
            .then(response => response.json())
            .then(data => {
                          let odontologos = data;
                            document.querySelector('#odontologo_id').value = odontologos.id;
                            document.querySelector('#nombre').value = odontologos.nombre;
                            document.querySelector('#apellido').value = odontologos.apellido;
                            document.querySelector('#matricula').value = odontologos.matricula;
                            //el formulario por default esta oculto y al editar se habilita
                            document.querySelector('#div_odontologo_updating').style.display = "block";
                      }).catch(error => {
                          alert("Error al cargar la matrícula");

                  let deleteButton = '<button' +
                                            ' id=' + '\"' + 'btn_delete_' + odontologos.id + '\"' +
                                            ' type="button" onclick="deleteBy('+odontologos.id+')" class="btn rounded-circle btn-danger btn_delete">' +
                                            '&times' +
                                            '</button>';

                  let updateButton = '<button' +
                                            ' id=' + '\"' + 'btn_id_' + odontologos.id + '\"' +
                                            ' type="button" onclick="findBy('+odontologos.id+')" class="btn btn-primary rounded-circle btn-success btn_id">' +
                                            odontologos.id +
                                            '</button>';

                  odontologoRow.innerHTML = '<td>' + updateButton + '</td>' +
                          '<td class=\"td_nombre\">' + odontologos.nombre.toUpperCase() + '</td>' +
                          '<td class=\"td_apellido\">' + odontologos.apellido.toUpperCase() + '</td>' +
                          '<td>' + deleteButton + '</td>';
});
}

const form = document.getElementById("find_by_matricula");

form.addEventListener("submit", submitForm);
