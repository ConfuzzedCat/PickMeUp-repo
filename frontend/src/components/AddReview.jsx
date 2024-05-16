import { useState, useEffect } from "react";
import StarRating from "./minis/StarRatingMini/StarRating"; // Adjust the import path as needed
import facade from "../util/apiFacade";

function AddReview({ route }) {
  const [value, setValue] = useState(0);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [review, setReview] = useState({});
  const [isRouteFinished, setIsRouteFinished] = useState(false);

  useEffect(() => {
    const departureTime = new Date(route.departureTime);
    const endTime = new Date(departureTime.getTime() + route.timeInMinutes * 60000); // Add minutes to the departure time
    setIsRouteFinished(new Date() > endTime);
  }, [route.departureTime, route.timeInMinutes]);

  const addReview = async (endpoint, requestType, payload) => {
    facade.fetchData(endpoint, requestType, payload).then((data) => setReview(data));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const data = {
      title: title,
      description: description,
      rating: value,
      routeId: route.id,
    };
    addReview("reviews", "POST", data);
    console.log(data);
  };

  return (
    <div className="container mx-auto p-4">
      {isRouteFinished ? (
        <form className="space-y-4" onSubmit={handleSubmit}>
          <div className="form-control">
            <label className="label" htmlFor="title">
              <span className="label-text">Title</span>
            </label>
            <input
              type="text"
              id="title"
              className="input input-bordered w-full"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </div>
          <div className="form-control">
            <label className="label" htmlFor="description">
              <span className="label-text">Description</span>
            </label>
            <textarea
              id="description"
              className="textarea textarea-bordered w-full"
              rows="4"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
            />
          </div>
          <div className="form-control">
            <label className="label">
              <span className="label-text">Rating</span>
            </label>
            <StarRating totalStars={5} initialRating={value} setRating={setValue} />
          </div>
          <div className="form-control mt-4">
            <button type="submit" className="btn btn-success w-full" onSubmit={handleSubmit}>
              Submit
            </button>
          </div>
        </form>
      ) : (
        <div className="alert alert-warning">
          <div className="flex-1">
            <label>The route must be finished before you can write a review.</label>
          </div>
        </div>
      )}
    </div>
  );
}

export default AddReview;
