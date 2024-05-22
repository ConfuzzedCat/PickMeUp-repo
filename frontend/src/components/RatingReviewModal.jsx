import React from 'react';

function RatingReviewModal({ driver, onClose }) {
    if (!driver || driver.length === 0) return null;


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
        cursor: 'pointer',
        backgroundColor: 'red',
        color: 'white',
        border: 'none',
        borderRadius: 5
    };


    const mockReviews = [
        {
            rating: 4.5,
            review: "Great ride, very comfortable and smooth."
        },
        {
            rating: 3.8,
            review: "Good ride but the car could have been cleaner. He did play Kendrick Lamarr though!"
        },
        {
            rating: 5.0,
            review: "Excellent experience! The driver was very polite and punctual."
        },
        {
            rating: 4.2,
            review: "Nice ride, but the traffic was terrible."
        },
        {
            rating: 2.9,
            review: "The driver was late and the car was not well-maintained."
        },
        {
            rating: 3.5,
            review: "Average ride, nothing special but got me to my destination."
        },
        {
            rating: 4.7,
            review: "Very pleasant ride. The car was clean and the driver was friendly."
        }
    ];
    




    return (
        <div style={modalStyle}>
            <div style={modalContentStyle}>
                <h2>Ride Reviews</h2>
                {mockReviews.map((review, index) => (
                    <div key={index} style={{ marginBottom: 20 }}>
                        <p><strong>Ride {index + 1}</strong></p>
                        <p><strong>Rating:</strong> {review.rating}</p>
                        <p><strong>Review:</strong> {review.review}</p>
                    </div>
                ))}
                <button onClick={onClose} style={closeButtonStyle}>Close</button>
            </div>
        </div>
    );
}

export default RatingReviewModal;
