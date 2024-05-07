const routesData = [
  {
    id: 1,
    destination: "Lyngby",
    handicapAccessibility: true,
    time: "08:00",
    date: "2024-05-10",
    passengers: 4,
    carSize: "Large",
    driver: "John Doeson",
  },
  {
    id: 2,
    destination: "City B",
    handicapAccessibility: false,
    time: "09:00",
    date: "2024-05-11",
    passengers: 2,
    carSize: "Medium",
    driver: "Jane Smithsen",
  },
  {
    id: 3,
    destination: "City C",
    handicapAccessibility: false,
    time: "10:00",
    date: "2024-05-12",
    passengers: 3,
    carSize: "Small",
    driver: "John Doesen",
  },
]

// functions to get all routes
export const getAllRoutes = () => {
  return routesData
}

// function to get a single route by ID
export const getRouteById = (id) => {
  return routesData.find((route) => route.id === id)
}

// hej med dig

export default { getAllRoutes, getRouteById }
