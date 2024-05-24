import RequestButton from "./RequestButton";

function RideModal({ ride, onClose }) {
    if (!ride) return null;

    const modalStyle = {
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0,0,0,0)",
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
                <p><strong>Departure Time:</strong> {ride.departureTime}</p>
                <RequestButton ride={ride} />
                <button onClick={onClose} style={closeButtonStyle}>Close</button>
            </div>
        </div>
    );
}

export default RideModal;