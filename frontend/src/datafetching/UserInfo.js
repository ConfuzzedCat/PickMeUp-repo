export function userFetching(Id, callback) {
    const url = 'http://localhost:7070/api/v1/users';

    const handleHttpErrors = (res) => {
        if(!res.ok){
            return Promise.reject({status: res.status, fullError: res.json()});
        }
        return res.json();
    }

    const makeOptions = (requestMethod) => {
        const options = {
            method: requestMethod,
            headers: {
                "content-type": "application/json",
                "accept": "application/json"
            }
        }
        return options;
    }
    
    const getUser = (Id, callback) => {
        const options = makeOptions("GET");

        return fetch(url + "/" + Id, options)
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
    
    getUser(Id, callback)

}