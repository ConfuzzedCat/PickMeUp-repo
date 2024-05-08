import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";

function Rides() {
    const [rides, setRides] = useState([]);
    const [loading, setLoading] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');
    const navigate = useNavigate();

    // Funktion til at hente alle ture fra API
    async function fetchRides() {
        setLoading(true);
        try {
            const response = await fetch("/api/rides");
            const data = await response.json();
            setRides(data);
        } catch (error) {
            console.error('Failed to fetch rides:', error);
            // Optional: handle error state here
        }
        setLoading(false);
    }

    // Effect til at indlæse ture ved initial load
    useEffect(() => {
        fetchRides();
    }, []);

    // Filtrerede ture baseret på søgning
    const filteredRides = rides.filter(ride =>
        ride.routeId.toString().includes(searchTerm)
    );

    return (
        <div>
            <h1>Rides</h1>
            <br />

            <button onClick={fetchRides} disabled={loading}>
                {loading ? "Loading..." : "View All Rides"}
            </button>
            <br />
            <br />

            <br />

            <input 
                type="text"
                placeholder="Search by Route ID"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <ul>
                {filteredRides.map((ride) => (
                    <li key={ride.routeId}>
                        <NavLink to={`/rides/${ride.routeId}`}>
                            {ride.startLocation} to {ride.endLocation}
                        </NavLink>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Rides;
