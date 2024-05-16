import React, { useState, useEffect } from "react";
import './Rating.css';

const StarRating = ({ totalStars = 5, initialRating = 0, setRating }) => {
  const [rating, setLocalRating] = useState(initialRating);

  useEffect(() => {
    setLocalRating(initialRating);
  }, [initialRating]);

  const handleRatingChange = (newRating) => {
    setLocalRating(newRating);
    setRating(newRating); // Update parent's state
  };

  return (
    <div className="rating">
      {[...Array(totalStars)].map((_, index) => (
        <React.Fragment key={index}>
          <input
            type="radio"
            id={`star${index + 1}`}
            name="rating"
            value={index + 1}
            checked={rating === index + 1}
            onChange={() => handleRatingChange(index + 1)}
          />
          <label htmlFor={`star${index + 1}`} className="mask mask-star-2"></label>
        </React.Fragment>
      ))}
    </div>
  );
};

export default StarRating;
