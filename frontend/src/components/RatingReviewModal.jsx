import React, { useState, useEffect } from 'react';

function RatingReviewModal({ driver, onClose }) {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        if (driver) {
            const url = `http://localhost:7070/api/v1/reviews/driver/${driver}`;
            console.log("Fetching reviews from:", url);
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Reviews loaded:", data);
                    setReviews(data);
                })
                .catch(err => {
                    console.error("Failed to fetch reviews:", err);
                });
        }
    }, [driver]);

    const modalStyle = {
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0,0,0,0.7)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 1000
    };

    const modalContentStyle = {
        backgroundColor: "black",
        padding: 20,
        borderRadius: 5,
        width: "80%",
        maxWidth: 500,
        color: "white",
        overflowY: "auto",
        maxHeight: "80vh"
    };

    const closeButtonStyle = {
        padding: 10,
        marginTop: 20,
        cursor: 'pointer'
    };

    return (
        <div style={modalStyle}>
            <div style={modalContentStyle}>
                <h2>Ride Reviews</h2>
                {reviews.length > 0 ? (
                    reviews.map((review, index) => (
                        <div key={index} style={{ marginBottom: 20 }}>
                            <p><strong>Ride ID:</strong> {review.id}</p>
                            <p><strong>Description:</strong> {review.description}</p>
                            <p><strong>Rating:</strong> {review.rating}</p>
                            <p><strong>Title:</strong> {review.title}</p>
                            <p><strong>Route ID:</strong> {review.route_id}</p>
                            <p><strong>User ID:</strong> {review.usermock_id}</p>
                        </div>
                    ))
                ) : (
                    <p>No reviews available.</p>
                )}
                <button onClick={onClose} style={closeButtonStyle}>Close</button>
            </div>
        </div>
    );
}

export default RatingReviewModal;
