// Cu substitutie polialfabetica, fara spatii

const criptButton = document.getElementById("criptButton")
const decriptButton = document.getElementById("decriptButton")
const inputText = document.getElementById("inputText")
const inputKey = document.getElementById("inputKey")
const outputText = document.getElementById("outputText")

let arrayWithAlphabet5x5 = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
let arrayWithAlphabet6x6 = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
// Am scos J-ul pentru ca se scoate o litera cu frecventa redusa

function containtsNumbers (text) {
    let regex = /\d/g
    if (regex.test(text)) {
        return true
    } else {
        return false
    }
}

function removeRep (text) {
    let unique = [...new Set(text)].join('');
    return unique
}

function fillArray(key, size) {

    key = removeRep(key)
    let array = []
    let varKey = 0
    let numAlph = 0

    if (size === 5) {
        array = arrayWithAlphabet5x5.filter(c => !key.includes(c));
    } else if (size === 6) {
        // eliminam caracterele din cheia introdusă
        array = arrayWithAlphabet6x6.filter(c => !key.includes(c));
    }

    let arrayFinal = new Array(size).fill(" ")
    for (let i = 0 ;i < arrayFinal.length; i++) {
        arrayFinal[i] = new Array(size).fill(" ")
            for (let j = 0; j < arrayFinal.length; j++) {
                if (varKey < key.length) {
                    arrayFinal[i][j] = key[varKey];
                    varKey++;
                } else {
                    arrayFinal[i][j] = array[numAlph];
                    numAlph++;
                }
            }
    }
    return arrayFinal;
}

function getPosition(char, arrayFinal) {
    for (let i = 0; i < arrayFinal.length; i++) {
        for (let j = 0; j < arrayFinal[i].length; j++) {
            if (arrayFinal[i][j] == char) return [i, j];
        }
    }
    return null;
}

function cript(text, key) {
    let arrayFinal;
    let size;

    if (containtsNumbers(text)) {
        size = 6;
        arrayFinal = fillArray(key, 6);
    } else {
        size = 5;
        arrayFinal = fillArray(key, 5);
    }

    text = text.replace(/\s+/g, '').toUpperCase(); // ignorăm spațiile
    let result = '';
    let i = 0;

    while (i < text.length) {
        let a = text[i];
        let b = text[i+1] || (size === 5 ? 'X' : '0'); // dacă nu există a doua literă

        // Daca bigramul are litere identice, inserăm litera de frecvență redusă
        if (a === b) {
            b = size === 5 ? 'X' : '0';
            i++;
        } else {
            i += 2;
        }

        let [rowA, colA] = getPosition(a, arrayFinal);
        let [rowB, colB] = getPosition(b, arrayFinal);

        // aceeasi linie
        if (rowA === rowB) {
            result += arrayFinal[rowA][(colA + 1) % size];
            result += arrayFinal[rowB][(colB + 1) % size];// % size face ca sa revina la primu index
        }
        // aceeasi coloană
        else if (colA === colB) {
            result += arrayFinal[(rowA + 1) % size][colA];
            result += arrayFinal[(rowB + 1) % size][colB];// % face sa revina la prima coloana
        }
        // colturi dreptunghi
        else {
            result += arrayFinal[rowA][colB];
            result += arrayFinal[rowB][colA];
        }
    }

    return result;
}

function decript(text, key) {
    let arrayFinal;
    let size;

    if (containtsNumbers(text)) {
        size = 6;
        arrayFinal = fillArray(key, 6);
    } else {
        size = 5;
        arrayFinal = fillArray(key, 5);
    }

    text = text.replace(/\s+/g, '').toUpperCase(); // ignoram spațiile
    let result = '';
    let i = 0;

    while (i < text.length) {
        let a = text[i];
        let b = text[i+1] || (size === 5 ? 'X' : '0'); // daca nu exista a doua literă
        i += 2;

        let [rowA, colA] = getPosition(a, arrayFinal);
        let [rowB, colB] = getPosition(b, arrayFinal);

        // aceeasi linie → mutăm la stânga
        if (rowA === rowB) {
            result += arrayFinal[rowA][(colA - 1 + size) % size];
            result += arrayFinal[rowB][(colB - 1 + size) % size];
        }
        // aceeasi coloană → mutăm în sus
        else if (colA === colB) {
            result += arrayFinal[(rowA - 1 + size) % size][colA];
            result += arrayFinal[(rowB - 1 + size) % size][colB];
        }
        // colturi dreptunghi → interschimbăm coloanele
        else {
            result += arrayFinal[rowA][colB];
            result += arrayFinal[rowB][colA];
        }
    }

    return result;
}

criptButton.addEventListener("click", () => {
    if (inputKey.value === "" || inputText.value === "") {
        window.alert("Nu ai pus cheia sau textul!")
    } else {
        let result = cript(inputText.value, inputKey.value)
        outputText.innerText = result
    }
})

decriptButton.addEventListener("click", () => {
    if (inputKey.value === "" || inputText.value === "") {
        window.alert("Nu ai pus cheia sau textul!")
    } else {
        let result = decript(inputText.value, inputKey.value)
        outputText.innerText = result
    }
})

let arrayTest = fillArray("CATALIN", 5);
console.log(arrayTest);
console.log(cript("CLOCKSWILLRUNMOREQUICKLYDURINGFREETIME", "MURPHY"));