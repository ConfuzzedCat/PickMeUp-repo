export function routeFetching(userAddress, userPostalCode, destinationAddress, destinationPostalCode, callback) {

    const url = 'http://localhost:7070/api/v1/route';

    const handleHttpErrors = (res) => {
        if(!res.ok){
            return Promise.reject({status: res.status, fullError: res.json()});
        }
        return res.json();
    }

    const makeOptions = (requestMethod, requestBody) => {
        const options = {
            method: requestMethod,
            headers: {
                "content-type": "application/json",
                "accept": "application/json"
            },
            body: JSON.stringify(requestBody),
        }
        return options;
    }
    
    const getAvailableRoutes = (userAddress, userPostalCode, destinationAddress, destinationPostalCode, callback) => {
        userAddress = userAddress.replaceAll(" ", ",")
        destinationAddress = destinationAddress.replaceAll(" ", ",")

        const payload = {
            startLocation: userAddress + "," + userPostalCode,
            endLocation: destinationAddress + "," + destinationPostalCode,
        }

        const options = makeOptions("POST", payload);

        return fetch(url + "/available_routes", options)
            .then(handleHttpErrors)
            .then(callback)
            .catch(error => {
                if(error.status){
                    error.fullError.then(e => console.log(e));
                } else {
                    console.log("Fatal server error.", error);
                }
            })
    }
    
    getAvailableRoutes(userAddress, userPostalCode, destinationAddress, destinationPostalCode, callback)
   
}

export default routeFetching;