import StarRating from "./minis/StarRatingMini/StarRating";

function Review({review}) {

    const dateFormater = (date) => {
        const dateObj = new Date(date);
        const day = dateObj.getDate();
        const month = dateObj.getMonth();
        const year = dateObj.getFullYear();
        return `${day}/${month}/${year}`;
    }





    return (
        <div className="p-4 bg-white rounded shadow-md">
        <div className="flex justify-between mb-4">
          <div className="w-1/4">
            <img src="https://placehold.co/600x400" alt="" className="rounded" />
          </div>
          <div className="w-3/4 pl-4">
            <h2 className="text-xl font-bold mb-2">{review.title}</h2>
            <StarRating totalStars={5} initialRating={review.rating} />
            <p className="mt-2">{review.description}</p>
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div>
            <b>{review.user.firstName} {review.user.lastName}</b>
          </div>
          <div>
            <i>{dateFormater(review.createdAt)}</i>
          </div>
        </div>
      </div>
    );
}

export default Review;
