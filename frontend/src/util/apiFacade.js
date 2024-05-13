const URL = 'http://localhost:7070/api/v1/'

function apiFacade() {


    const handleHttpErrors = (res) => {
        if (!res.ok) {
            return Promise.reject({ status: res.status, fullError: res.json() })
        }
        return res.json()
    }


    const fetchData = async (endpoint, method, payload) => {
        const options = makeOptions(method, payload, true);
        const res = await fetch(URL + endpoint, options)
        return handleHttpErrors(res)

    }

    const makeOptions = (method, payload) => {

        const opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            }
        }

        if (payload) {
            opts.body = JSON.stringify(payload)
        }

        return opts;
    }

    const createRoute = (routeData) => {
        const options = makeOptions('POST', routeData, true);
        return fetch(URL + ROUTES_ROUTE, options).then(handleHttpErrors);
    };

    const updateRoute = (id, updatedData) => {
        const options = makeOptions('PUT', updatedData, true);
        return fetch(URL + ROUTES_ROUTE + '/' + id, options).then(handleHttpErrors);
    };

    return {
        fetchData,
        makeOptions,
        createRoute,
        updateRoute,
    }

}



const facade = apiFacade();
export default facade;


