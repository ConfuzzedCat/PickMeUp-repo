import React, { useState, useEffect } from "react";

function RideModal({ ride, onClose }) {
    if (!ride) return null;

    // Definer styles osv, for at undgå at bruge App.css fordi der blev snakket om den skulle slettes
    const modalStyle = {
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 1000
    };

    const modalContentStyle = {
        backgroundColor: "grey",
        padding: 20,
        borderRadius: 5,
        width: "80%",
        maxWidth: 500
    };

    const closeButtonStyle = {
        padding: 10,
        marginTop: 20,
        cursor: 'pointer'
    };

    return (
        <div style={modalStyle}>
            <div style={modalContentStyle}>
                <h2>Ride Details</h2>
                <p><strong>Start Location:</strong> {ride.startLocation}</p>
                <p><strong>End Location:</strong> {ride.endLocation}</p>
                <p><strong>Driver ID:</strong> {ride.driverId}</p>
                <p><strong>Route Length:</strong> {ride.routeLength} km</p>
                <p><strong>Time in Minutes:</strong> {ride.timeInMinutes} minutes</p>
                <p><strong>Departure Time:</strong> {`${ride.departureTime[0]}:${ride.departureTime[1]}`}</p>
                <button onClick={onClose} style={closeButtonStyle}>Close</button>
            </div>
        </div>
    );
}

function Rides() {
    const [rides, setRides] = useState([]);
    const [loading, setLoading] = useState(false);
    const [selectedRide, setSelectedRide] = useState(null);
    const [searchIndex, setSearchIndex] = useState('');
    const [showAll, setShowAll] = useState(false);

    // URL til vores API
    const baseUrl = "http://localhost:7070/api/v1/rides";

    async function fetchRideByIndex(index) {
        if (!index) return;
        setLoading(true);
        try {
            const response = await fetch(`${baseUrl}/${index}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setRides([data]);
            setShowAll(false);
        } catch (error) {
            console.error('Failed to fetch ride:', error);
            setRides([]);
        }
        setLoading(false);
    }

    const handleViewAll = () => {
        setShowAll(true);
    };

    const handleChangeIndex = (e) => {
        setSearchIndex(e.target.value);
        fetchRideByIndex(e.target.value);
    };

    useEffect(() => {
        if (showAll) {
            setLoading(true);
            fetch(baseUrl)
                .then(response => response.json())
                .then(data => {
                    setRides(data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error('Failed to fetch rides:', error);
                    setLoading(false);
                });
        }
    }, [showAll]);

    return (
        <div style={{ width: "100%", maxWidth: "600px", margin: "0 auto", padding: "20px", textAlign: "center" }}>
            <h1 style={{ color: "#333" }}>Rides</h1>
            <button
                onClick={handleViewAll}
                disabled={loading}
                style={{
                    padding: "10px 20px",
                    fontSize: "16px",
                    borderRadius: "5px",
                    backgroundColor: "#4CAF50",
                    color: "white",
                    border: "none",
                    cursor: "pointer",
                    margin: "10px 0"
                }}
            >
                {loading ? "Loading..." : "View All Rides"}
            </button>
            <input 
                type="number"
                placeholder="Enter Ride Index"
                value={searchIndex}
                onChange={handleChangeIndex}
                style={{
                    padding: "10px",
                    margin: "10px 0",
                    borderRadius: "5px",
                    border: "1px solid #ccc",
                    width: "calc(100% - 22px)" 
                }}
            />
            {rides.length > 0 && (
                <ul style={{ listStyleType: "none", padding: 0 }}>
                    {rides.map((ride, index) => (
                        <li key={index} style={{ background: "#f9f9f9", padding: "10px", margin: "10px 0", borderRadius: "5px", cursor: 'pointer' }}>
                            <a onClick={() => setSelectedRide(ride)} style={{
                                textDecoration: "none",
                                color: "black",
                                fontWeight: "bold"
                            }}>
                                {ride.startLocation} to {ride.endLocation}
                            </a>
                        </li>
                    ))}
                </ul>
            )}
            {selectedRide && <RideModal ride={selectedRide} onClose={() => setSelectedRide(null)} />}
        </div>
    );
}

export default Rides;
