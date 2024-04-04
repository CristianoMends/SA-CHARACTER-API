document.getElementById('form').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append('name', document.getElementById('name').value);
    formData.append('otherNames', document.getElementById('otherNames').value);
    formData.append('gender', document.getElementById('gender').value);
    formData.append('description', document.getElementById('description').value);
    formData.append('image', document.getElementById('image').files[0]);

    fetch('/api/character/save', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('Cadastro realizado com sucesso!');
        } else {
            alert('Erro ao cadastrar!');
        }
    })
    .catch(error => console.error('Erro ao criar personagem!', error));
});
let currentPage = 1;
const charactersPerPage = 5;

function showCharacters(jsonData) {
    const charactersList = document.getElementById('charactersList');
    charactersList.innerHTML = '';

    const startIndex = (currentPage - 1) * charactersPerPage;
    const endIndex = Math.min(startIndex + charactersPerPage, jsonData.length);

    for (let i = startIndex; i < endIndex; i++) {
        const character = jsonData[i];
        const characterDiv = document.createElement('div');
        characterDiv.classList.add('characterDiv');
        characterDiv.innerHTML = `
            <div class="image-container">
                <img src="${character.image}" alt="Imagem do Personagem ${character.name}">
            </div>
            <div class="infoDiv">
            <h3 class="text-title">${character.name}</h3>
            <p>
                <span>Outros Nomes:</span> <br> ${character.otherNames} <br>
                <span>Gênero:</span><br> ${character.gender}<br>
                <span>Descrição:</span><br> ${character.description}
            </p>
            </div>
        `;
        charactersList.appendChild(characterDiv);
    };
}
function getCharacters(){
    /*data = 
    [
    {
        "name":"Carls Jonhoson",
        "otherNames":"Cj",
        "gender":"Male",
        "description":"agaragan",
        "image":"images/Carl_Jonhson.jpg"
    },
    {
        "name":"Carls Jonhoson",
        "otherNames":"Cj, sds,sds,sd",
        "gender":"Male",
        "description":"agaragan sdasda sd asd asda sd asda sdasdasdasd asdasdasdasd",
        "image":"images/Carl_Jonhson.jpg"
    },
    {
        "name":"Carls Jonhoson",
        "otherNames":"Cj",
        "gender":"Male",
        "description":"agaragan",
        "image":"images/Carl_Jonhson.jpg"
    },
    {
        "name":"Carls Jonhoson",
        "otherNames":"Cj",
        "gender":"Male",
        "description":"agaragan",
        "image":"images/Carl_Jonhson.jpg"
    }
    ]
    showCharacters(data);*/

    fetch('http://localhost:8080/api/character/all')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao receber os dados.');
            }
            return response.json();
        })
        .then(data => {
            console.log('Dados recebidos:', data);
            const numberOfCharacters = data.length;
            document.getElementById('charactersQtd').innerText += ` ${numberOfCharacters}`;
            showCharacters(data);
        })
        .catch(error => {
            console.error('Erro:', error);
    });
}
function nextPage() {
    currentPage++;
    getCharacters();
}

function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        getCharacters();
    }
}

document.addEventListener('DOMContentLoaded',getCharacters);