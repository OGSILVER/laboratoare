        function bubleSort(arr) {

        while(true){
            let changes = 0
            for(let i = 0; i < arr.length; i++){
                if(arr[i]>arr[i+1]){
                    let temp = arr[i+1]
                    arr[i+1] = arr[i]
                    arr[i] = temp;
                    changes++
                }
            }
            if(!changes){
                return arr
            }
        }
    }


    function insertionSort(arr) {


            for(let i = 1; i < arr.length; i++){
                if(arr[i]>arr[i-1]){
                    continue
                }else{
                    for(let j = i; j != 0; j--){
                        if(arr[j] < arr[j-1]){
                            let temp = arr[j-1]
                            arr[j-1] = arr[j]
                            arr[j] = temp                            
                        }
                    }
                }
            }
            return arr 
        }


        function selectionSort(arr) {
            for(let i = 0; i < arr.length; i++){
                let min = Infinity
                let minIndex
                for(let j = i; j < arr.length; j++){
                    if(arr[j] < min){
                        min = arr[j]
                        minIndex = j
                    }
                }
                if(i!=minIndex){
                    let temp = arr[minIndex]
                    arr[minIndex] = arr[i]
                    arr[i] = temp
                }
            }
            return arr
        }


    function quickSort(arr) {
        if (arr.length <= 1) {
            return arr;
        }

        const pivot = arr[arr.length - 1]; 
        const left = [];
        const right = [];

        for (let i = 0; i < arr.length - 1; i++) {
            if (arr[i] < pivot) {
                left.push(arr[i]);
            } else {
                right.push(arr[i]);
            }
        }
        return [...quickSort(left), pivot, ...quickSort(right)];
    }


    function mergeSort(arr) {
        if (arr.length == 1) {
            return arr
        }

        let left = []
        let right = []


        let mid = Math.floor(arr.length/2)
        left = mergeSort(arr.slice(0,mid))
        right = mergeSort(arr.slice(mid))

        console.log("test")
        return merge(left,right)

    }


    function merge(left,right){

        let result = []
        let i = 0
        let j = 0

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result.push(left[i])
                i++
            }else{
                result.push(right[j])
                j++
            }
        }

        if(i < left.length){
            result.push(...left.slice(i))
        }

        if (j < right.length) {
            result.push(...right.slice(j))
        }

        return result
    }




        let randomArray = []

        for(let i = 0; i < 20; i++){
            randomArray.push(Math.floor(Math.random()*100))
        }



        function binarySearch(arr, target) {
            
        }


    console.log(randomArray)

<<<<<<< HEAD
    console.log(binarySearch(randomArray))
=======
    console.log(insertionSort(randomArray))
>>>>>>> refs/remotes/origin/master



