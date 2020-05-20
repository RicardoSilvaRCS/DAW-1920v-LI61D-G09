module.exports = (mlseconds) => {
    const date = new Date(mlseconds)
    return `${date.getDate()}/${months[date.getMonth()]}/${date.getFullYear()}`
}


// We need this because the date.getMonth return nº of the month -1 

const months =  [
    1, //january 
    2, //February
    3, //March
    4, //April
    5, //MAy
    6, //June
    7, //july
    8, //August
    9, //September
    10, //October
    11, //November
    12 //December
]