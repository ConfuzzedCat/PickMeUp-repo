import { useState } from "react"
import routeFetching from "../datafetching/routeFetching"

const SearchForRoutes = () => {
  const [userAddress, setUserAddress] = useState([])
  const [userPostalCode, setUserPostalCode] = useState([])
  const [destinationAddress, setDestinationAddress] = useState([])
  const [destinationPostalCode, setDestinationPostalCode] = useState([])
  const [routesList, setRoutesList] = useState([])

  const submitHandler = (event) => {
    event.preventDefault()
    routeFetching(userAddress, userPostalCode, destinationAddress, destinationPostalCode, (data) => {
      setRoutesList(data)
    })
  }

  return (
    <div>
      <form onSubmit={submitHandler}>
        <h2>Your location:</h2>

        <input type="text" placeholder="Address" value={userAddress} onChange={(e) => setUserAddress(e.target.value)} />
        <input type="number" placeholder="Postal code" value={userPostalCode} onChange={(e) => setUserPostalCode(e.target.value)} />

        <h2>Your destination:</h2>

        <input type="text" placeholder="Address" value={destinationAddress} onChange={(e) => setDestinationAddress(e.target.value)} />
        <input type="number" placeholder="Postal code" value={destinationPostalCode} onChange={(e) => setDestinationPostalCode(e.target.value)} />
        <button type="submit">Find routes</button>
      </form>
      <div>
        {routesList.length > 0 ? (
          <div>
            <table>
              <thead>
                <tr>
                  <th>Starting Location:</th>
                  <th>End Location</th>
                </tr>
              </thead>
              <tbody>
                {routesList.map((route) => (
                  <tr key={route.id}>
                    <td>{route.startLocation + " " + route.startPostalCode}</td>
                    <td>{route.endLocation + " " + route.endPostalCode}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  )
}

export default SearchForRoutes
