function rideRequestFetching(props) {
  const url = "http://localhost:7070/api/v1/requests/requests"
  const options = {
    method: "POST",
    headers: {
      "content-type": "application/json",
      accept: "application/json",
    },
    body: JSON.stringify(props),
  }
  fetch(`${url}/`, options)
    .then((res) => res.json())
    .then((data) => console.log(data))
}

export default rideRequestFetching
