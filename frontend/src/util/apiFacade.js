const URL = 'http://localhost:7070/api/v1'

function apiFacade() {


    const handleHttpErrors = (res) => {
        if (!res.ok) {
            return Promise.reject({ status: res.status, fullError: res.json() })
        }
        return res.json()
    }


    const fetchData = (endpoint, method, payload) => {
        const options = makeOptions(method, payload, true);
        return fetch(URL + endpoint, options).then(handleHttpErrors);

    }

    const makeOptions = (method, payload) => {

        const opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            }
        }
    }

    if (payload) {
        opts.body = JSON.stringify(payload)
    }

    return opts;

    return {
        fetchData,
        makeOptions
    }

}



const facade = apiFacade();
export default facade;


