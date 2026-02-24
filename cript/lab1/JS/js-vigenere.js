// Cu substitutie polialfabetica, fara spatii

const criptButton = document.getElementById("criptButton")
const decriptButton = document.getElementById("decriptButton")
const inputText = document.getElementById("inputText")
const inputKey = document.getElementById("inputKey")
const outputText = document.getElementById("outputText")


let arrayWithAlphabet = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
let arrayVigenere = [];
for (let i = 0; i < arrayWithAlphabet.length; i++) {
    arrayVigenere[i] = arrayWithAlphabet.slice(i).concat(arrayWithAlphabet.slice(0, i));
}

function findIndex(a) {
    return arrayWithAlphabet.indexOf(a.toUpperCase());
}

function cript (text, key) {

    let keyCopy = ""
    if (text.length === key.length) {
        keyCopy = key;
    } else {
        for (let i=0, k=0; i < text.length; i++, k++) {
            if (k === key.length) {
                k = 0;
            }
            keyCopy += key[k]
        }
    }

    let criptText = ""
    for (let i = 0; i < text.length; i++) {
        criptText += arrayVigenere[findIndex(keyCopy[i])][findIndex(text[i])];
    }

    return criptText
}

function decript (text, key) {

    let keyCopy = ""
    if (text.length === key.length) {
        keyCopy = key;
    } else {
        for (let i=0, k=0; i < text.length; i++, k++) {
            if (k === key.length) {
                k = 0;
            }
            keyCopy += key[k]
        }
    }

    let textDecript = ""
    for (let i = 0; i < text.length; i++) {
        let row = findIndex(keyCopy[i]);
        let col = arrayVigenere[row].indexOf(text[i].toUpperCase());
        textDecript += arrayWithAlphabet[col];
    }

    console.log(textDecript)
    return textDecript

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

cript("ASIMPLEEXAMPLE", "BATTISTA")
decript("BSBFXDXEYAFITW", "BATTISTA")

