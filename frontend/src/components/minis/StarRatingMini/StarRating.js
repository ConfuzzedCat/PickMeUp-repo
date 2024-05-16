import React, {useState} from "react";
import './StarRating.css';

const StarRating = ({ totalStars = 5, initialRating = 0}) => {
    const [rating, setRating] = useState(initialRating);

    const handleRatingChange = (newRating) => {
        setRating(newRating);
    }


return (
    <div className="rating">
        {[...Array(totalStars)].map((_, index) => (
            <input
        key={index}
        type="radio"
        name="rating"
        className="mask mask-star-2 bg-orange-400"
        checked={rating === index + 1}
        onChange={() => handleRatingChange(index + 1)}
        />
        
        ))}

    </div>


)
}

export default StarRating;
