import React from 'react';


export default function UserStats(props) {

    const {user, userStats} = props;

    return (
        <article className="message">
            <div className="message-header">
                {user.username}
            </div>
            <div className="message-body">
                <table className="table">
                    <tbody>
                    <tr>
                        <td>Reviews:</td>
                        <td>{userStats.reviews}</td>
                    </tr>
                    <tr>
                        <td>5-Star Reviews:</td>
                        <td>{userStats.fiveStarReviews}</td>
                    </tr>
                    <tr>
                        <td>Average Rating:</td>
                        <td>{userStats.averageRating}</td>
                    </tr>
                    <tr>
                        <td>Average Review Length:</td>
                        <td>{userStats.averageReviewLength} words</td>
                    </tr>
                    <tr>
                        <td>Last Review:</td>
                        <td>{userStats.lastReview}</td>
                    </tr>
                    </tbody>

                </table>
            </div>
        </article>
    );
}