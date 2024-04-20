
function createCharacterElement(character){

    const characterElement = document.createElement('div');
    characterElement.classList.add('characterElement');
    const img = new Image();
    img.classList.add('hidden');
    img.onload = function () {
        img.classList.remove('hidden');
    };
    img.src = character.image;
    img.alt = `imagem de ${character.name}`;

    characterElement.innerHTML =
     `
        <div class="image-container">

        </div>
        <div class="infoDiv">
            <h3 class="text-title">${character.name}</h3>
            <p>
                <span>Outros Nomes: </span> <br> ${character.otherNames}<br>
                <span>Gênero:       </span> <br> ${character.gender}    <br>
                <span>Descrição:    </span> <br> ${character.description}
            </p>
        </div>
    `;
    characterElement.querySelector('.image-container').appendChild(img);
    return characterElement;
}
function getCharacters() {
    fetch('http://localhost:8080/character/all')
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
            const characterContainer = document.querySelector('.characterContainer');

            for(let i =0;i < numberOfCharacters && i < 6;i++){
                characterContainer.appendChild(createCharacterElement(data[i]))                
            }
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

document.addEventListener('DOMContentLoaded', getCharacters);