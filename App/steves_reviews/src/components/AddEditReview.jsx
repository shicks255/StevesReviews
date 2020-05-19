import React, {useContext} from 'react';
import {UserContext} from "./UserContext";

export default function AddEditReview(props) {
    const review = props.review;
    const context = useContext(UserContext);

    function submitForm(e) {
        e.preventDefault();

        const content = e.target.content.value;
        const rating = e.target.rating.value;
        const id = review ? review.id : null;

        const body = {
            'content': content,
            'rating': rating,
            'id': id
        }

        const result = fetch('/api/review/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + context.cookie
            },
            body: JSON.stringify(body)
        });
    }

    return (
        <div>
            <form onSubmit={submitForm}>
                <select id='rating' value={review ? review.rating : 1}>
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
                <textarea id='content' value={review ? review.content : ''} />

                <br/>
                <button type='submit'>{review ? 'Update' : 'Create'}</button>
            </form>
        </div>
    );

}