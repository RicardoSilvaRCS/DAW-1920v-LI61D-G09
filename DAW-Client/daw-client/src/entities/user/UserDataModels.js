
function loginDataModel(userName, password) {
    return {
        userName: userName,
        password: password
    }
}

function registerDataModel(userName, fullName, email, age, genre, phoneNumber, password) {
    return {
        userName: userName,
        fullName: fullName,
        email: email,
        age: age,
        genre: genre,
        phoneNumber: phoneNumber,
        password: password
    }
}

export default {
    loginDataModel,
    registerDataModel
}