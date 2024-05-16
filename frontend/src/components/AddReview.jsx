import { useState } from "react";
import StarRating from "./minis/StarRatingMini/StarRating";
import facade from "../util/apiFacade";

function AddReview({ route }) {
  const [value, setValue] = useState(0);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [review, setReview] = useState({});

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
        <button type="submit" className="btn btn-success w-full">
          Submit
        </button>
      </div>
    </form>
  );
}

export default AddReview;
