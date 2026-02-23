// Cu substitutie monoalfabetica, cu spatii

const criptButton = document.getElementById("criptButton")
const decriptButton = document.getElementById("decriptButton")
const inputText = document.getElementById("inputText")
const inputKey = document.getElementById("inputKey")
const outputText = document.getElementById("outputText")

let arrayWithAlphabet = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

function removeRep (text) {
    let unique = [...new Set(text)].join('');
    return unique
}

function fillArray(key) {

    key = removeRep(key)
    let array = arrayWithAlphabet.filter(c => !key.includes(c));
    let varKey = 0
    let numAlph = 0
    let foo = false

    let arrayFinal = new Array(4).fill(" ")
    for (let i = 0 ;i < 4; i++) {
        arrayFinal[i] = new Array(10).fill(" ")
            for (let j = 0; j < 10; j++) {
                if (i === 0 && (j === 1 || j === 2 || j === 3)) 
                {
                    arrayFinal[i][j] = "-"
                } else if (varKey < key.length) {
                    arrayFinal[i][j] = key[varKey]
                    varKey++
                } else if (varKey === key.length && foo === false) {
                    arrayFinal[i][j] = " "
                    foo = true
                } else {
                    arrayFinal[i][j] = array[numAlph]
                    numAlph++
                }
            }
    }
    return arrayFinal;
}

function getPosition (array, char) {
    for (let i=0; i < 4; i++) {
        for (let j=0; j < 10; j++) {
            if (array[i][j] === char) {
                return [i, j]
            }
        }
    }
}

function cript (text, key) {
    let array = fillArray(key)
    let response = ""

    for (let i = 0; i < text.length; i++) {

        let [row, column] = getPosition(array, text[i])
        if (row > 0) {
            response += `${row}${column}`
        } else {
            response += `${column}`
        }
    }

    response = response.split("").map((val, ind) => {
        if (ind % 6 === 0) {
            return ` ${val}`
        } else {
            return val
        }
    }).join("")

    return response
}

function decript(text, key) {
    const array = fillArray(key);
    // eliminam spațiile adaugate la criptare
    text = String(text).replace(/\s+/g, '');

    let result = "";
    let i = 0;
    while (i < text.length) {
        const d = Number(text[i]);

        // dacă intalnim 1,2 sau 3 -> prefix pentru cod pe 2 cifre
        if (d === 1 || d === 2 || d === 3) {
            const col = Number(text[i + 1]);
            result += array[d][col];
            i += 2;
        } else {
            // cod pe o cifră -> rând 0
            result += array[0][d];
            i += 1;
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

let array = fillArray("MURPHY")
console.log(cript("CLOCKS WILL RUN MORE QUICKLY DURING FREE TIME", "MURPHY"));
console.log(array)