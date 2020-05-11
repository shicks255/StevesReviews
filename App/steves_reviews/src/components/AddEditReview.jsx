import React from 'react';


export default function AddEditReview(props) {
    const review = props.review;
    console.log(review);

    function submitForm(e) {
        e.preventDefault();
        console.log(e.target);
    }

    return (
        <div>
            <form onSubmit={submitForm}>
                <select value={review.rating}>
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
                <textarea>
                    {review ? review.content : ''}
                </textarea>

                <br/>
                <button type='submit'>{review ? 'Update' : 'Create'}</button>

            </form>

        </div>
    );

}