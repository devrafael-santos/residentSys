fetch("http://localhost:8080/api/v1/moradores")
  .then((response) => response.text())
  .then((result) => {
    let usuarios = JSON.parse(result);

    usuarios.forEach((usuario) => {
      const moradoresTableBody = document.getElementById(
        "moradores-table-body"
      );
      const newRow = document.createElement("tr");
      newRow.dataset.id = usuario.cpf;
      newRow.dataset.cpf = usuario.cpf;
      newRow.dataset.nome = usuario.nome;
      newRow.dataset.email = usuario.email;
      newRow.dataset.telefone = usuario.telefone;
      newRow.innerHTML = `
                        <td class="border-t py-2 px-4">${usuario.nome}</td>
                        <td class="border-t py-2 px-4">${usuario.cpf}</td>
                        <td class="border-t py-2 px-4">${usuario.telefone}</td>
                        <td class="border-t py-2 px-4">${usuario.email}</td>
                        <td class="border-t py-2 px-4 text-center">
                            <button class="edit-btn text-blue-500 hover:text-blue-700 ml-2">Editar</button>
                            <button class="delete-btn text-red-500 hover:text-red-700 ml-2">Excluir</button>
                            `;
      moradoresTableBody.append(newRow);
    });
  });

document.addEventListener("DOMContentLoaded", () => {
  const dashboardScreen = document.getElementById("dashboard-screen");
  const moradoresScreen = document.getElementById("moradores-screen");
  const moradoresBtn = document.getElementById("moradores-btn");
  const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");
  const addMoradorBtn = document.getElementById("add-morador-btn");
  const moradorFormContainer = document.getElementById(
    "morador-form-container"
  );
  const cancelFormBtn = document.getElementById("cancel-form-btn");
  const moradorForm = document.getElementById("morador-form");
  const formTitle = document.getElementById("form-title");
  const moradoresTableBody = document.getElementById("moradores-table-body");
  const searchInput = document.getElementById("search-input");

  const showDashboard = () => {
    dashboardScreen.classList.remove("hidden");
    moradoresScreen.classList.add("hidden");
  };

  const showMoradores = () => {
    dashboardScreen.classList.add("hidden");
    moradoresScreen.classList.remove("hidden");
  };

  const showForm = () => {
    moradorFormContainer.classList.remove("hidden");
    addMoradorBtn.classList.add("hidden");
    document.getElementById("cpf").readOnly = false;
  };

  const hideForm = () => {
    moradorFormContainer.classList.add("hidden");
    addMoradorBtn.classList.remove("hidden");
    moradorForm.reset();
    document.getElementById("morador-id").value = "";
    formTitle.textContent = "Adicionar Novo Morador";
  };

  moradoresBtn.addEventListener("click", showMoradores);
  backToDashboardBtn.addEventListener("click", showDashboard);
  addMoradorBtn.addEventListener("click", showForm);
  cancelFormBtn.addEventListener("click", hideForm);

  moradorForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const nome = document.getElementById("nome").value;
    const cpf = document.getElementById("cpf").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;

    if (!email && !telefone) {
      alert("É obrigatório informar um e-mail ou telefone.");
      return;
    }

    if(formTitle.textContent === "Editar Morador"){
      
      fetch(`http://localhost:8080/api/v1/moradores/${cpf}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome, email, telefone }),
    })
      .then((response) => {
        if (response.ok) {
          globalThis.location.reload();
          return response.text();
        } else {
          return response.text().then((text) => {
            JSON.parse(text);

            alert("Erro ao salvar morador");
          });
        }
      })
      
    } else {

      fetch("http://localhost:8080/api/v1/moradores", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ cpf, nome, email, telefone }),
    })
      .then((response) => {
        if (response.ok) {
          return response.text();
        } else {
          return response.text().then((text) => {
            JSON.parse(text);

            alert("Erro ao salvar morador");
          });
        }
      })
      .then((result) => {
        let usuarioAdicionado = JSON.parse(result);

        const moradoresTableBody = document.getElementById(
          "moradores-table-body"
        );
        const newRow = document.createElement("tr");
        newRow.dataset.id = usuarioAdicionado.cpf;
        newRow.dataset.cpf = usuarioAdicionado.cpf;
        newRow.dataset.nome = usuarioAdicionado.nome;
        newRow.dataset.email = usuarioAdicionado.email;
        newRow.dataset.telefone = usuarioAdicionado.telefone;
        newRow.innerHTML = `
                        <td class="border-t py-2 px-4">${usuarioAdicionado.nome}</td>
                        <td class="border-t py-2 px-4">${usuarioAdicionado.cpf}</td>
                        <td class="border-t py-2 px-4">${usuarioAdicionado.telefone}</td>
                        <td class="border-t py-2 px-4">${usuarioAdicionado.email}</td>
                        <td class="border-t py-2 px-4 text-center">
                            <button class="edit-btn text-blue-500 hover:text-blue-700 ml-2">Editar</button>
                            <button class="delete-btn text-red-500 hover:text-red-700 ml-2">Excluir</button>
                            `;
        moradoresTableBody.append(newRow);
      });
      
    }

    hideForm();
  });

  moradoresTableBody.addEventListener("click", (e) => {
    const button = e.target.closest("button");
    if (!button) return;

    const row = e.target.closest("tr");
    if (!row) return;

    const id = row.getAttribute("data-id");
    const nome = row.dataset.nome;
    const email = row.dataset.email;
    const telefone = row.dataset.telefone;
    const cpf = row.dataset.cpf;

    if (button.classList.contains("delete-btn")) {

      if (confirm(`Tem certeza que deseja excluir o morador ${nome}?`)) {

        fetch(`http://localhost:8080/api/v1/moradores/${id}`, {
          method: "DELETE",
        })
          .then((response) => {
            if (response.ok) {
              globalThis.location.reload();
              return response.text();
            } else {
              return response.text().then((text) => {
                JSON.parse(text);
                alert("Erro ao excluir morador");
              })
            }
          });

        row.remove();
      };
    }

    if (button.classList.contains("edit-btn")) {
      formTitle.textContent = "Editar Morador";
      document.getElementById("morador-id").value = id;
      document.getElementById("nome").value = nome;
      document.getElementById("email").value = email;
      document.getElementById("telefone").value = telefone;
      document.getElementById("cpf").value = cpf;
      document.getElementById("cpf").readOnly = true;

      showForm();
      window.scrollTo(0, 0);
    }
  });
});
