function rideRequestFetching(props, callback) {
  const url = "http://localhost:7070/api/v1/requests/requests"

  const handleHttpErrors = (res) => {
    if (res.status === 409) {
      return Promise.reject({ status: res.status, callback })
    }
    return res.json()
  }

  const options = {
    method: "POST",
    headers: {
      "content-type": "application/json",
      accept: "application/json",
    },
    body: JSON.stringify(props),
  }
  fetch(`${url}/`, options)
    .then(handleHttpErrors)
    .then((data) => console.log(data))
    .catch((err) => {
      if (err.status === 409) {
        err.callback()
      } else {
        console.error("An error occurred:", err)
      }
    })
}

export default rideRequestFetching
