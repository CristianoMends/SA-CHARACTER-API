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

function showCharacters(jsonData) {
    if(jsonData.length == 0){
        return;
    }

    function shuffle(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));

            const aux = array[i];
            array[i] = array[j];
            array[j] = aux;
        }
        return array;
    }

    const shuffledData = shuffle(jsonData);

    const charactersList = document.getElementById('charactersList');
    charactersList.innerHTML = '';

    const charactersMax = 6;
    
    for (let i = 0; i < charactersMax; i++) {
        const character = shuffledData[i];

        console.log(character);

        const characterDiv = document.createElement('div');
        characterDiv.classList.add('characterDiv');
        const img = new Image();
        img.classList.add('hidden');
        img.onload = function() {
            img.classList.remove('hidden');
        };
        img.src = character.image;
        img.alt = `imagem de ${character.name}`;

        characterDiv.innerHTML = `
            <div class="image-container">

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
        characterDiv.querySelector('.image-container').appendChild(img);

        charactersList.appendChild(characterDiv);
    };
}
function getCharacters(){
    fetch('http://localhost:8080/api/character/all')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao receber os dados.');
            }
            return response.json();
        })
        .then(data => {
            const numberOfCharacters = data.length;
            console.log('Number Of Characters:', numberOfCharacters);

            document.getElementById('charactersQtd').innerText += ` ${numberOfCharacters}`;
            showCharacters(data);
        })
        .catch(error => {
            console.error('Erro:', error);
    });
}

document.addEventListener('DOMContentLoaded',getCharacters);