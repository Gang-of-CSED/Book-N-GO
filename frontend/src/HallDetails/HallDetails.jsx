import React, { useEffect, useState } from "react";
import image from "../assets/Alexandria-Library.png";
import styles from "./HallDetails.module.css";
import Rating from '@mui/material/Rating';
import StarIcon from '@mui/icons-material/Star';
import { useParams } from "react-router-dom";

export const HallDetails = () => {
  const {id} = useParams();
  
  const [data, setData] = useState({
    "name": "Alexandria bibliotheca Great Hall",
    "description": "The Great Hall of the Library of Alexandria in Alexandria, Egypt, was the largest and most famous part of the Library of Alexandria, which was part of the research institution called the Musaeum. The Musaeum was a part of the Royal Library of Alexandria, an institution that was part of the Museum of Alexandria. The Museum was a place of learning in ancient Alexandria, and many great thinkers worked there.",
    "rating": 3,
    "comments": [
      "This hall is great!",
      "I love this hall!",
      "This hall is amazing!"
    ]
  });

  useEffect(() => {
    fetch(`http://localhost:8000/hall/${id}`)
      .then(response => response.json())
      .then(data => setData(data))
      .catch(error => console.error(error));
    }
  , [id]);



  return (
    <div className={styles["hall-details__wrapper"]}>
      <div className={styles["hall-details__container"]}>
        <div className={styles["hall-details__preview"]}>
          <img src={image} alt="Hall Preview"/>
          <h1>{data['name']}</h1>
          <button>Book Now!</button>
        </div>
        <div className={styles["hall-details__info"]}>
          <div className={styles["hall-details__info__rating"]}>
            <h2>Ratings:</h2>
            <Rating 
              name="hall-rating" 
              value={data['rating']} 
              opacity={1} 
              icon={<StarIcon fontSize="inherit" />}
              emptyIcon={<StarIcon fontSize="inherit" sx={{color: "white"}} />}
            />
          </div>
          <div className={styles["hall-details__info__description"]}>
            <h2>Description:</h2>
            <p>{data['description']}</p>
          </div>
          <div className={styles["hall-details__info__comments"]}>
            <h2>Comments:</h2>
            <div className={styles["hall-details__info__comments__container"]}>
              {data['comments'].map((comment, index) => (
                <div key={index} className={styles["hall-details__info__comments__comment"]}>
                  <p>{comment}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
