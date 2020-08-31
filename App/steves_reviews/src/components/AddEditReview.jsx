import React, {useContext, useEffect, useState} from 'react';
import {UserContext} from "./UserContext";

export default function AddEditReview(props) {
    const review = props.review;
    const albumId = props.albumId;
    const context = useContext(UserContext);

    const [reviewContent, setReviewContent]
        = useState('');
    const [rating, setRating]
        = useState(1);

    useEffect(() => {
        setReviewContent(review ? review.review.content : '');
        setRating(review ? review.review.rating : 1);
    }, [props.review]);

    function changeRating(e) {
        e.preventDefault();
        setRating(e.target.value);
    }

    function changeContent(e) {
        e.preventDefault();
        setReviewContent(e.target.value);
    }

    function submitForm(e) {
        e.preventDefault();

        const id = review ? review.review.id : null;
        const body = {
            'content': reviewContent,
            'rating': rating,
            'albumId': albumId,
            'id': id
        }

        const result = fetch('/api/review/upsert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + context.cookie
            },
            body: JSON.stringify(body)

        });
    }

    return (
        <div className='has-text-left'>
            Your review
            <form onSubmit={submitForm}>
                <select onChange={changeRating} id='rating' value={rating}>
                    <option>1</option>
                    <option>1.5</option>
                    <option>2</option>
                    <option>2.5</option>
                    <option>3</option>
                    <option>3.5</option>
                    <option>4</option>
                    <option>4.5</option>
                    <option>5</option>
                </select>
                <br/>
                <textarea rows='8'
                          className='editReviewContent'
                          onChange={changeContent}
                          id='content'
                          value={reviewContent} />

                <br/>
                <button className='button is-info' type='submit'>{review ? 'Update' : 'Create'}</button>
            </form>
        </div>
    );

}