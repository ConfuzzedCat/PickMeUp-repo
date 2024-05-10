import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import './App.css'; // Sørg for at denne linje er tilføjet for at importere din CSS

function Rides() {
    const [rides, setRides] = useState([]);
    const [loading, setLoading] = useState(false);
    const [searchIndex, setSearchIndex] = useState('');

    async function fetchRides() {
        setLoading(true);
        try {
            const response = await fetch("http://localhost:7070/api/v1/rides");
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setRides(data);
        } catch (error) {
            console.error('Failed to fetch rides:', error);
        }
        setLoading(false);
    }

    useEffect(() => {
        fetchRides();
    }, []);

    const rideToShow = searchIndex !== '' && parseInt(searchIndex) < rides.length && parseInt(searchIndex) >= 0
        ? [rides[parseInt(searchIndex)]]
        : [];

    return (
        <div className="US15-container">
            <h1 className="US15-title">Rides</h1>
            <button
                onClick={fetchRides}
                disabled={loading}
                className="US15-button"
            >
                {loading ? "Loading..." : "View All Rides"}
            </button>
            <input
                type="number"
                placeholder="Enter Ride Index"
                value={searchIndex}
                onChange={(e) => setSearchIndex(e.target.value)}
                className="US15-input"
            />
            <ul className="US15-list">
                {rideToShow.length > 0 ? rideToShow.map((ride, index) => (
                    <li key={index} className="US15-list-item">
                        <NavLink
                            to={`/rides/${index}`}
                            className="US15-link"
                        >
                            {ride.startLocation} to {ride.endLocation}
                        </NavLink>
                    </li>
                )) : searchIndex !== '' && <p>No ride found at that index.</p>}
            </ul>
        </div>
    );
}

export default Rides;