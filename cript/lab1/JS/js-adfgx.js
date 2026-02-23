// Cifruri MIXTE CU PERMUTARE ȘI SUBSTITUȚIE

const criptButton = document.getElementById("criptButton")
const decriptButton = document.getElementById("decriptButton")
const inputText = document.getElementById("inputText")
const inputKey = document.getElementById("inputKey") // Va fi folosit pentru cheia Polybius
const outputText = document.getElementById("outputText")
const inputKeyFinal = document.getElementById("inputKeyFinal") // Va fi folosit pentru cheia de permutare

let arrayWithAlphabet5x5 = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]

function removeRep (text) {
    let unique = [...new Set(text)].join('');
    return unique
}

function getPosition(char, arrayFinal) {
    for (let i = 1; i < arrayFinal.length; i++) {
        for (let j = 1; j < arrayFinal.length; j++) {
            if (arrayFinal[i][j] === char) return [arrayFinal[i][0], arrayFinal[0][j]];
        }
    }
    return null;
}

// Funcția folosește 'polybusKey' pentru a construi pătratul.
function fillArray(polybusKey) {
    
    polybusKey = removeRep(polybusKey)
    let array = []
    let varKey = 0
    let numAlph = 0
    let ADFGXarr = ["A", "D", "F", "G", "X"]
    let ADFGXnum = 0

    array = arrayWithAlphabet5x5.filter(c => !polybusKey.includes(c));
    
    let arrayFinal = new Array(6)
    for (let i = 0; i < arrayFinal.length; i++) {
        arrayFinal[i] = new Array(6).fill(" ")
    }
    
    for (let i = 1; i < arrayFinal.length; i++) {
        arrayFinal[i][0] = ADFGXarr[ADFGXnum]  // pe coloana 0
        arrayFinal[0][i] = ADFGXarr[ADFGXnum]  // pe linie 0
        ADFGXnum++
    }

    for (let i = 1 ;i < arrayFinal.length; i++) {
            for (let j = 1; j < arrayFinal.length; j++) {
                if (varKey < polybusKey.length) {
                    arrayFinal[i][j] = polybusKey[varKey];
                    varKey++;
                } else {
                    arrayFinal[i][j] = array[numAlph];
                    numAlph++;
                }
            }
    }
    
    return arrayFinal
}

// Funcția accepta cheia polybus si cea de permutare.
function cript(text, polybusKey, permutationKey) {
    // Patratul se umple cu cheia Polybius.
    let arrayFinal = fillArray(polybusKey);
    let result = "";
    for (let ch of text) {
        let [row, col] = getPosition(ch, arrayFinal);
        result += row + col;
    }

    let arrayCriptFinal = []
    let rowChecker = 0
    let iterator = 0
    
    // Latimea grilei de permutare este definită de 'permutationKey'.
    arrayCriptFinal = new Array(permutationKey.length).fill(" ")
    while (iterator < result.length) {
        // Lungimea rândurilor depinde de 'permutationKey.length'.
        arrayCriptFinal[rowChecker] = new Array(permutationKey.length).fill(" ")
        if (rowChecker === 0) {
            // Primul rand este populat cu 'permutationKey'.
            for (let i = 0; i < permutationKey.length; i++) {
                arrayCriptFinal[rowChecker][i] = permutationKey[i]
            }
        } else if (rowChecker === 1) {
            // Al doilea rând (de sortare) este calculat pe baza 'permutationKey'.
            for (let i = 0; i < permutationKey.length; i++) {
                arrayCriptFinal[rowChecker][i] = arrayWithAlphabet5x5.findIndex(c => c === permutationKey[i])
            }
        } else {
            // Logica pentru umplerea cu textul intermediar
            for (let i = 0; i < permutationKey.length; i++) {
                if (iterator < result.length) {
                    arrayCriptFinal[rowChecker][i] = result[iterator]
                    iterator++
                } else {
                    arrayCriptFinal[rowChecker][i] = " "
                }
            }
        }
        rowChecker++
    }

    let criptedString = "";

    // Logica pentru sortare si citirea pe coloane
    let order = arrayCriptFinal[1].map((val, idx) => ({ val, idx }));
    order.sort((a, b) => a.val - b.val);

    for (let o of order) {
        let colIndex = o.idx;
        for (let r = 2; r < arrayCriptFinal.length; r++) {
            criptedString += arrayCriptFinal[r][colIndex];
        }
    }

    console.log(arrayCriptFinal)
    console.log(result)
    return criptedString;
}

function decript(criptedString, polybusKey, permutationKey) {

    // Lungimea cheii este cea a cheii de permutare.
    let keyLength = permutationKey.length;
    // Patratul se reconstruiește cu cheia Polybius.
    let arrayFinal = fillArray(polybusKey);

    // Reconstruim randurile transpozite
    let numRows = Math.ceil(criptedString.length / keyLength);
    let rows = new Array(numRows).fill(null).map(() => new Array(keyLength).fill(""));
    
    // Ordinea coloanelor se calculeaza pe baza 'permutationKey'.
    let order = permutationKey.split("").map(c => arrayWithAlphabet5x5.findIndex(x => x === c))
        .map((val, idx) => ({ val, idx }))
        .sort((a, b) => a.val - b.val);

    // Logica pentru reconstruirea grilei
    let iterator = 0;
    for (let o of order) {
        for (let r = 0; r < numRows; r++) {
            if (iterator < criptedString.length) rows[r][o.idx] = criptedString[iterator++];
        }
    }

    // Logica pentru citirea textului intermediar 
    let intermediate = "";
    for (let r = 0; r < numRows; r++) {
        for (let c = 0; c < keyLength; c++) {
            if (rows[r][c]) intermediate += rows[r][c];
        }
    }

    // Logica pentru decodarea finala
    let plain = "";
    for (let i = 0; i < intermediate.length; i += 2) {
        let rowSym = intermediate[i];
        let colSym = intermediate[i + 1];
        if (!rowSym || !colSym) continue;

        let rowIndex = -1, colIndex = -1;
        for (let r = 1; r < 6; r++) if (arrayFinal[r][0] === rowSym) rowIndex = r;
        for (let c = 1; c < 6; c++) if (arrayFinal[0][c] === colSym) colIndex = c;
        if (rowIndex !== -1 && colIndex !== -1) plain += arrayFinal[rowIndex][colIndex];
    }

    return plain;
}

criptButton.addEventListener("click", () => {
    if (inputKey.value === "" || inputKeyFinal.value === "" || inputText.value === "") {
        window.alert("Nu ai pus textul sau una dintre chei!")
    } else {
        let result = cript(inputText.value, inputKey.value, inputKeyFinal.value)
        outputText.innerText = result
    }
})

decriptButton.addEventListener("click", () => {
    if (inputKey.value === "" || inputKeyFinal.value === "" || inputText.value === "") {
        window.alert("Nu ai pus textul sau una dintre chei!")
    } else {
        let result = decript(inputText.value, inputKey.value, inputKeyFinal.value)
        outputText.innerText = result
    }
})

// 🔹 Exemplu de utilizare:
// MODIFICAT: Definim ambele chei pentru exemplul din consolă.
let polybusKeyEx = "FAILURE";
let permutationKeyEx = "VICTORY";
let text = "TRYINGISTHEFIRSTSTEPTOFAILURE";

let cripted = cript(text, polybusKeyEx, permutationKeyEx);
console.log("Text criptat:", cripted);

let decrypted = decript(cripted, permutationKeyEx, permutationKeyEx);
console.log("Text decriptat:", decrypted);