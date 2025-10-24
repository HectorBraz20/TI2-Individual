const API_URL = 'http://localhost:4567/carro';

const form = document.getElementById('carro-form');
const tableBody = document.querySelector('#carro-table tbody');
const submitButton = document.getElementById('submit-button');
const cancelButton = document.getElementById('cancel-button');
const carroIdField = document.getElementById('carro-id');

const searchInput = document.getElementById('search-id');
const searchButton = document.getElementById('search-button');
const searchResult = document.getElementById('search-result');

// FUNÇÃO DE LISTAGEM 
async function fetchCarros() {
    try {
        const response = await fetch(API_URL);
        const carros = await response.json();
        tableBody.innerHTML = '';
        carros.forEach(carro => {
            const row = tableBody.insertRow();
            row.insertCell().textContent = carro.id;
            row.insertCell().textContent = carro.nome;
            row.insertCell().textContent = carro.marca || '-';
            row.insertCell().textContent = carro.ano;
            row.insertCell().textContent = carro.cor;
            row.insertCell().textContent = carro.linha || '-';

            const actionsCell = row.insertCell();

            const editBtn = document.createElement('button');
            editBtn.textContent = 'Editar';
            editBtn.onclick = () => loadCarroToForm(carro);

            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = 'Excluir';
            deleteBtn.onclick = () => deleteCarro(carro.id);

            actionsCell.appendChild(editBtn);
            actionsCell.appendChild(deleteBtn);
        });
    } catch (error) {
        console.error('Erro ao buscar carros:', error);
        alert('Erro ao conectar com a API.');
    }
}

// FUNÇÃO DE CRIAÇÃO / EDIÇÃO
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const carro = {
        nome: document.getElementById('nome').value,
        cor: document.getElementById('cor').value,
        ano: parseInt(document.getElementById('ano').value),
        marca: document.getElementById('marca').value,
        linha: document.getElementById('linha').value
    };

    const carroId = carroIdField.value;

    let url = API_URL;
    let method = 'POST';

    if (carroId) {
        url = `${API_URL}/${carroId}`;
        method = 'PUT';
    }

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(carro)
        });
        if (response.ok) {
            alert(carroId ? 'Carro atualizado!' : 'Carro cadastrado!');
            form.reset();
            resetFormMode();
            fetchCarros();
        } else {
            const errorText = await response.json();
            alert(`Erro: ${JSON.stringify(errorText)}`);
        }
    } catch (error) {
        console.error('Erro de rede:', error);
        alert('Erro ao comunicar com a API.');
    }
});

// FUNÇÃO DE EXCLUSÃO
async function deleteCarro(id) {
    if (!confirm(`Deseja excluir o carro ID ${id}?`)) return;
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert(`Carro ID ${id} excluído.`);
            fetchCarros();
        } else {
            const errorText = await response.json();
            alert(`Erro ao excluir: ${JSON.stringify(errorText)}`);
        }
    } catch (error) {
        console.error('Erro ao excluir:', error);
    }
}

// FUNÇÕES DE EDIÇÃO
function loadCarroToForm(carro) {
    document.getElementById('nome').value = carro.nome;
    document.getElementById('cor').value = carro.cor;
    document.getElementById('ano').value = carro.ano;
    document.getElementById('marca').value = carro.marca;
    document.getElementById('linha').value = carro.linha;
    carroIdField.value = carro.id;

    submitButton.textContent = 'Salvar Edição';
    cancelButton.style.display = 'inline-block';
    document.querySelector('#form-section h2').textContent = 'Editar Carro ID ' + carro.id;
}

function resetFormMode() {
    carroIdField.value = '';
    submitButton.textContent = 'Cadastrar';
    cancelButton.style.display = 'none';
    document.querySelector('#form-section h2').textContent = 'Cadastrar Novo Carro';
}

cancelButton.addEventListener('click', () => {
    form.reset();
    resetFormMode();
});

// FUNÇÃO DE BUSCA POR ID
searchButton.addEventListener('click', async () => {
    const id = searchInput.value.trim();
    if (!id) {
        alert('Digite um ID válido');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (response.ok) {
            const carro = await response.json();
            searchResult.innerHTML = `
                <strong>ID:</strong> ${carro.id} <br>
                <strong>Nome:</strong> ${carro.nome} <br>
                <strong>Marca:</strong> ${carro.marca || '-'} <br>
                <strong>Ano:</strong> ${carro.ano} <br>
                <strong>Cor:</strong> ${carro.cor} <br>
                <strong>Linha:</strong> ${carro.linha || '-'} <br>
            `;
        } else {
            searchResult.textContent = `Carro ID ${id} não encontrado.`;
        }
    } catch (error) {
        console.error('Erro ao buscar carro:', error);
        alert('Erro ao buscar carro na API.');
    }
});

// ===================== INICIALIZAÇÃO =====================
document.addEventListener('DOMContentLoaded', fetchCarros);
